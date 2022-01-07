package net.tnemc.core.common.transaction;


import net.tnemc.core.common.io.Datable;
import net.tnemc.core.common.io.Queryable;
import net.tnemc.core.common.transaction.charge.Charge;
import net.tnemc.core.common.transaction.consumer.TransactionResult;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * An object that represents an economy transaction.
 *
 * @since 0.1.1.17
 * @author creatorfromhell
 */
public class Transaction implements Datable<UUID, Transaction> {
  //TODO: Finish this.

  /**
   * The {@link TransactionParticipant sender} for this transaction. In a pay these are
   * the user paying the money. In a take this is the user taking the money from
   * the receivers.
   */
  private TransactionParticipant sender;

  /**
   * The {@link TransactionParticipant receivers} for this transaction. In a pay these are
   * the users receiving the money. In a take this is the users the money is being taken from.
   */
  private Map<UUID, TransactionParticipant> receivers = new HashMap<>();

  /**
   * The identifier for the transaction.
   */
  private final UUID id;

  /**
   * The timestamp that the transaction took place.
   */
  private final long timestamp;

  /**
   * If this transaction was voided or not.
   */
  private boolean voided = false;

  /**
   * The {@link TransactionSource source} of this transaction.
   */
  private TransactionSource source;

  /**
   * Consumer for if the transaction succeeds.
   */
  private Consumer<TransactionResult> onSuccess;

  /**
   * Consumer for if the transaction fails to perform.
   */
  private Consumer<TransactionResult> onFail;

  /**
   * The identifier of the transaction type that is occurring.
   */
  private String type;

  /**
   * This is the amount applied to {@link Charge charges} in the transaction if
   * no amount is specified.
   */
  private BigDecimal amount = BigDecimal.ZERO;

  public Transaction() {
    this.id = UUID.randomUUID();
    this.timestamp = new Date().getTime();
  }

  public Transaction(UUID id, long timestamp) {
    this.id = id;
    this.timestamp = timestamp;
  }

  public Transaction from(final UUID id, final String world, final String currency)
      throws UnsupportedOperationException {

    return this;
  }

  public Transaction to(final UUID id, final String world, final String currency)
      throws UnsupportedOperationException {

    return this;
  }

  public void perform(Consumer<TransactionResult> onSuccess, Consumer<TransactionResult> onFail) {

  }

  /**
   * Used to set the transaction type of this transaction.
   * @param type The identifier of the transaction type.
   * @return An instance of the Transaction object with the new
   * transaction type.
   */
  public Transaction of(String type) {
    this.type = type;
    return this;
  }

  /**
   * Used to set {@link TransactionSource source} of this transaction.
   * @param source The {@link TransactionSource source} of this transaction.
   * @return An instance of the Transaction object with the new {@link TransactionSource source}.
   */
  public Transaction source(TransactionSource source) {
    this.source = source;
    return this;
  }

  /**
   * Used to get a string-based representation of the timestamp. This should be human-friendly.
   * @return The human-friendly string representation of the timestamp.
   */
  public String format() {
    return "" + timestamp;
  }

  /**
   * Used to get the {@link Queryable data part} of this object. This is what houses all the
   * IO logic.
   *
   * @return The {@link Queryable data part} of this object. This is what houses all the
   * IO logic.
   */
  @Override
  public Queryable<UUID, Transaction> getData() {
    return null;
  }

  /**
   * Sets the {@link Queryable data part} of this object. This is what houses all the
   * IO logic.
   *
   * @param dataObject The data object to set to.
   */
  @Override
  public void setData(Queryable<UUID, Transaction> dataObject) {

  }
}