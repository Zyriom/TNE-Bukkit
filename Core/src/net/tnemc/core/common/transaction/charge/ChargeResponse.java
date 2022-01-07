package net.tnemc.core.common.transaction.charge;

/**
 * The New Economy Minecraft Server Plugin
 * <p>
 * Created by creatorfromhell on 1/6/2022.
 * <p>
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 * Created by creatorfromhell on 06/30/2017.
 */

/**
 * Represents a response for a {@link Charge charge}.
 *
 * @see Charge
 * @since 0.1.1.17
 * @author creatorfromhell
 */
public class ChargeResponse {

  private boolean successful;
  private String reason;

  public ChargeResponse(boolean successful, String reason) {
    this.successful = successful;
    this.reason = reason;
  }

  public boolean isSuccessful() {
    return successful;
  }

  public void setSuccessful(boolean successful) {
    this.successful = successful;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }
}