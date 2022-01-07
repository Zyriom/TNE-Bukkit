package net.tnemc.core.common.transaction.charge;

import net.tnemc.core.common.account.Account;

import java.math.BigDecimal;

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
 * Represents a charge that is associated with a currency.
 *
 * @see Charge
 * @since 0.1.1.17
 * @author creatorfromhell
 */
public class CurrencyCharge implements Charge {

  private Account account;
  private BigDecimal amount;
  private String world;
  private String currency;

  public CurrencyCharge(Account account, BigDecimal amount, String world, String currency) {
    this.account = account;
    this.amount = amount;
    this.world = world;
    this.currency = currency;
  }

  /**
   * The {@link Account account} involved.
   *
   * @return The {@link Account account} involved.
   */
  @Override
  public Account account() {
    return account;
  }

  /**
   * The amount charged during this transaction.
   *
   * @return The amount charged during this transaction.
   */
  @Override
  public BigDecimal amount() {
    return amount;
  }

  /**
   * Performs the charge given the specified variables. Returns the response associated with the outcome of the
   * charge after performing.
   *
   * @return The response associated with the outcome of the charge after performing.
   */
  @Override
  public ChargeResponse perform() {
    return null;
  }
}