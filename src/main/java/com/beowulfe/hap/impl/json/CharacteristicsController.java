package com.beowulfe.hap.impl.json;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beowulfe.hap.characteristics.Characteristic;
import com.beowulfe.hap.characteristics.EventableCharacteristic;
import com.beowulfe.hap.impl.HomekitRegistry;
import com.beowulfe.hap.impl.connections.SubscriptionManager;
import com.beowulfe.hap.impl.http.HomekitClientConnection;
import com.beowulfe.hap.impl.http.HttpRequest;
import com.beowulfe.hap.impl.http.HttpResponse;
import com.beowulfe.hap.impl.responses.NotFoundResponse;

public class CharacteristicsController {
	
	Logger logger = LoggerFactory.getLogger(CharacteristicsController.class);

	private final HomekitRegistry registry;
	private final SubscriptionManager subscriptions;
	
	public CharacteristicsController(HomekitRegistry registry, SubscriptionManager subscriptions) {
		this.registry = registry;
		this.subscriptions = subscriptions;
	}
	
	public HttpResponse get(HttpRequest request) throws Exception {
		String uri = request.getUri();
		String query = uri.substring("/characteristics?q=".length()+1);
		String[] parts = query.split("\\.");
		if (parts.length != 2) {
			logger.error("Unexpected characteristics request: "+uri);
			return new NotFoundResponse();
		}
		int aid = Integer.parseInt(parts[0]);
		int iid = Integer.parseInt(parts[1]);
		JsonObjectBuilder characteristic = Json.createObjectBuilder();
		registry.getCharacteristics(aid).get(iid).supplyValue(characteristic);
		
		JsonObject result = Json.createObjectBuilder()
			.add("characteristics", Json.createArrayBuilder()
					.add(characteristic
							.add("aid", aid)
							.add("iid", iid)
							.build()
					).build()
		).build();
		try(ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			Json.createWriter(baos).write(result);
			return new HapJsonResponse(baos.toByteArray());
		}
	}

	public HttpResponse put(HttpRequest request, HomekitClientConnection connection) throws Exception {
		try(ByteArrayInputStream bais = new ByteArrayInputStream(request.getBody())) {
			JsonArray jsonCharacteristics = Json.createReader(bais)
					.readObject().getJsonArray("characteristics");
			for (JsonValue value: jsonCharacteristics) {
				JsonObject jsonCharacteristic = (JsonObject) value;
				int aid = jsonCharacteristic.getInt("aid");
				int iid = jsonCharacteristic.getInt("iid");
				Characteristic characteristic = registry.getCharacteristics(aid).get(iid);
				
				if (jsonCharacteristic.containsKey("value")) {
					characteristic.setValue(jsonCharacteristic.get("value"));
				}
				if (jsonCharacteristic.containsKey("ev") && characteristic instanceof EventableCharacteristic) {
					if (jsonCharacteristic.getBoolean("ev")) {
						subscriptions.addSubscription(aid, iid, (EventableCharacteristic) characteristic, connection);
					} else {
						subscriptions.removeSubscription((EventableCharacteristic) characteristic, connection);
					}
				}
			}
		}
		return new HapJsonNoContentResponse();
	}

}
