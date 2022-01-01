package net.tnemc.core.common.account;

import net.tnemc.core.common.EconomyManager;
import net.tnemc.core.common.compatibility.PlayerProvider;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents an account that is associated with a player.
 *
 * @see Account
 * @since 0.1.1.17
 * @author creatorfromhell
 */
public class PlayerAccount extends Account {

  protected long lastOnline;
  protected String language;

  public PlayerAccount(UUID identifier, String name) {
    super(identifier, name);

    //Defaults
    this.lastOnline = new Date().getTime();
    this.language = "TNE_DEFAULT";
  }

  @Override
  public String world() {
    return EconomyManager.connector().worldConnectionProvider().resolveWorld(identifier);
  }

  public boolean isOnline() {
    return getPlayer().isPresent();
  }

  /**
   * Attempts to find a {@link PlayerProvider player} based on an {@link UUID identifier}.
   *
   * @return An Optional containing the located {@link PlayerProvider player}, or an empty
   *         Optional if no player is located.
   */
  public Optional<PlayerProvider> getPlayer() {
    return EconomyManager.instance().findPlayer(identifier);
  }

  public long getLastOnline() {
    return lastOnline;
  }

  public void setLastOnline(long lastOnline) {
    this.lastOnline = lastOnline;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }
}