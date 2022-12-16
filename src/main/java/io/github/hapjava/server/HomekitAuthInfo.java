package io.github.hapjava.server;

import io.github.hapjava.server.impl.HomekitServer;
import io.github.hapjava.server.impl.crypto.HAPSetupCodeUtils;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

/**
 * Authentication info that must be provided when constructing a new {@link HomekitServer}. You will
 * need to implement this interface yourself to provide the necessary callbacks to a persistent
 * storage mechanism. All values provided must be constant across invocations or the accessories
 * will require re-pairing within iOS.
 *
 * @author Andy Lintner
 */
public interface HomekitAuthInfo {

  /**
   * A pin code used for pairing the device. This pin will be required within iOS in order to
   * complete pairing. The numbers cannot be sequential and should not have a repeating pattern.
   *
   * @return the pin code, in the form ###-##-###
   */
  String getPin();

  /**
   * A setup Id used for pairing the device using QR Code. It can be any alphanumeric combination of
   * the length of 4.
   *
   * @return setup id
   */
  default String getSetupId() {
    return HAPSetupCodeUtils.generateSetupId();
  }

  /**
   * A unique MAC address to be advertised with the HomeKit information. This does not have to be
   * the MAC address of the network interface. You can generate this using {@link
   * HomekitServer#generateMac()}.
   *
   * @return the unique MAC address.
   */
  String getMac();

  /**
   * The Salt that will be used when hashing the pin code to send to the client. You should generate
   * this using {@link HomekitServer#generateSalt()}.
   *
   * @return the Salt.
   */
  BigInteger getSalt();

  /**
   * The private key used by the server during pairing and message encryption. You should generate
   * this using {@link HomekitServer#generateKey()}
   *
   * @return the private key.
   */
  byte[] getPrivateKey();

  /**
   * Called during the pairing process, you should store the user and public key in a manner that
   * the public key can later be retrieved using {@link #getUserPublicKey(String)}. This must be
   * stored in a persistent store as pairing will need to be reset if the information is lost.
   *
   * @param username the iOS device's username. The value will not be meaningful to anything but
   *     iOS.
   * @param publicKey the iOS device's public key.
   * @param isAdmin if the user is an admin, authorized to and/remove other users
   */
  default void createUser(String username, byte[] publicKey, boolean isAdmin) {
    createUser(username, publicKey);
  }

  /**
   * Deprecated method to add a user, assuming all users are admins.
   *
   * <p>At least one of the createUser methods must be implemented.
   *
   * @param username the iOS device's username.
   * @param publicKey the iOS device's public key.
   */
  default void createUser(String username, byte[] publicKey) {
    createUser(username, publicKey, true);
  }

  /**
   * Called when an iOS device needs to remove an existing pairing. Subsequent calls to {@link
   * #getUserPublicKey(String)} for this username return null.
   *
   * @param username the username to delete from the persistent store.
   */
  void removeUser(String username);

  /**
   * List all users which have been authenticated.
   *
   * @return the previously stored list of users.
   */
  default Collection<String> listUsers() {
    return List.of();
  }

  /**
   * Called when an already paired iOS device is re-connecting. The public key returned by this
   * method will be compared with the signature of the pair verification request to validate the
   * device.
   *
   * @param username the username of the iOS device to retrieve the public key for.
   * @return the previously stored public key for this user.
   */
  byte[] getUserPublicKey(String username);

  /**
   * Determine if the specified user is an admin.
   *
   * @param username the username of the iOS device to retrieve permissions for.
   * @return the previously stored permissions.
   */
  default boolean userIsAdmin(String username) {
    return true;
  }

  /**
   * Called to check if a user has been created. The homekit accessory advertises whether the
   * accessory has already been paired. At this time, it's unclear whether multiple users can be
   * created, however it is known that advertising as unpaired will break in iOS 9. The default
   * value has been provided to maintain API compatibility for implementations targeting iOS 8.
   *
   * @return whether a user has been created and stored
   */
  default boolean hasUser() {
    return false;
  };
}
