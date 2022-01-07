package net.tnemc.core.common.transaction.charge;

import net.tnemc.core.common.account.Account;
import net.tnemc.core.common.transaction.Transaction;

import java.math.BigDecimal;

/**
 * An object that represents a charge during any form of transaction.
 *
 * @see Transaction
 * @since 0.1.1.17
 * @author creatorfromhell
 */
public interface Charge {

  /**
   * The {@link Account account} involved.
   * @return The {@link Account account} involved.
   */
  Account account();

  /**
   * The amount charged during this transaction.
   * @return The amount charged during this transaction.
   */
  BigDecimal amount();

  /**
   * Performs the charge given the specified variables. Returns the response associated with the outcome of the
   * charge after performing.
   *
   * @return The response associated with the outcome of the charge after performing.
   */
  ChargeResponse perform();
}