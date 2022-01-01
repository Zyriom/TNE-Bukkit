package net.tnemc.core.common.account;

import java.util.UUID;

/**
 * Represents an account that is not associated with a player.
 *
 * @see Account
 * @since 0.1.1.17
 * @author creatorfromhell
 */
public class NonPlayerAccount extends SharedAccount {

  public NonPlayerAccount(UUID identifier, String name) {
    super(identifier, name);
  }
}