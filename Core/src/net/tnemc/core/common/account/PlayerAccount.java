package net.tnemc.core.common.account;

import net.tnemc.core.TNECore;
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

  //Is this player logging in currently?
  protected boolean loggingIn;

  //Has this player died?
  protected boolean dead;

  public PlayerAccount(UUID identifier, String name) {
    super(identifier, name);

    //Defaults
    this.lastOnline = new Date().getTime();
    this.language = "TNE_DEFAULT";
  }

  @Override
  public String world() {
    return TNECore.connector().worldConnectionProvider().resolveWorld(identifier);
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
    return TNECore.instance().findPlayer(identifier);
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

  public boolean isLoggingIn() {
    return loggingIn;
  }

  public void setLoggingIn(boolean loggingIn) {
    this.loggingIn = loggingIn;
  }

  public boolean isDead() {
    return dead;
  }

  public void setDead(boolean dead) {
    this.dead = dead;
  }
}