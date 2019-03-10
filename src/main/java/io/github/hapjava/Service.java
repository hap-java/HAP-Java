package io.github.hapjava;

import io.github.hapjava.characteristics.Characteristic;
import java.util.List;

/**
 * Interface for a Service offered by an accessory.
 *
 * @author Andy Lintner
 */
public interface Service {

  /**
   * Characteristics are the variables offered for reading, updating, and eventing by the Service
   * over the Homekit protocol.
   *
   * <p>It is important to maintain the order of this list and not change its contents between
   * invocations, or a pairing error will result.
   *
   * @return the list of Characteristics.
   */
  List<Characteristic> getCharacteristics();

  /**
   * The type is a UUID that uniquely identifies the type of Service offered. Apple defines several
   * types for standard Services, however UUIDs outside this range are allowed for custom Services.
   *
   * @return A string representation of the UUID, with hexadecimal digits in the format
   *     ########-####-####-####-############.
   */
  String getType();
}
