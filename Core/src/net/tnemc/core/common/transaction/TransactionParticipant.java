package net.tnemc.core.common.transaction;

import net.tnemc.core.common.transaction.charge.Charge;

import java.util.UUID;

/**
 * Represents a participant that is apart of an {@link Transaction transaction}.
 *
 * @see Transaction
 * @since 0.1.1.17
 * @author creatorfromhell
 */
public class TransactionParticipant {

  /**
   * The UUID associated with this {@link TransactionParticipant participant}.
   */
  private UUID id;

  /**
   * The {@link Charge charge} associated with this participant.
   */
  private Charge charge;

  /**
   * Construct a new participant object for an {@link Transaction transaction}.
   * @param id The {@link UUID transaction id}.
   * @param charge The {@link Charge charge} associated with this participant.
   */
  public TransactionParticipant(UUID id, Charge charge) {
    this.id = id;
    this.charge = charge;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Charge getCharge() {
    return charge;
  }

  public void setCharge(Charge charge) {
    this.charge = charge;
  }
}