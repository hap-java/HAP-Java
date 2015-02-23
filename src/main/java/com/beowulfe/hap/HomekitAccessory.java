package com.beowulfe.hap;

import java.util.Collection;

/**
 * Base interface for all Homekit Accessories. You can implement this interface directly, but most users will
 * prefer to use the more full featured interfaces in {@link com.beowulfe.hap.accessories} which include a 
 * default implementation of {@link #getServices()}. 
 * 
 * @author Andy Lintner
 */
public interface HomekitAccessory {
	
	/**
	 * A unique identifier that must remain static across invocations to prevent errors with paired iOS devices. When used
	 * as a child of a Bridge, this value must be greater than 1, as that ID is reserved for the Bridge itself.
	 * 
	 * @return the unique identifier.
	 */
	int getId();

	/**
	 * Returns a label to display in iOS.
	 * 
	 * @return the label.
	 */
	String getLabel();
	
	/**
	 * Performs an operation that can be used to identify the accessory. This action can be performed without
	 * pairing.
	 */
	void identify();

	/**
	 * Returns a serial number to display in iOS.
	 * 
	 * @return the serial number, or null.
	 */
	String getSerialNumber();

	/**
	 * Returns a model name to display in iOS.
	 * 
	 * @return the model name, or null.
	 */
	String getModel();

	/**
	 * Returns a manufacturer to display in iOS.
	 * 
	 * @return the manufacturer, or null.
	 */
	String getManufacturer();
	
	/**
	 * The collection of Services this accessory supports. Services are the primary way to interact with the accessory via
	 * Homekit. Besides the Services offered here, the accessory will automatically include the required information service.
	 * 
	 * This method will only be useful if you're implementing your own accessory type. For the standard accessories, use
	 * the default implementation provided by the interfaces in {@link com.beowulfe.hap.accessories}.
	 * 
	 * @return the collection of services.
	 */
	Collection<Service> getServices();
}
