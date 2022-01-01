package net.tnemc.core.common.compatibility;

import net.tnemc.core.common.EconomyManager;
import net.tnemc.core.common.account.Account;
import net.tnemc.core.common.currency.Currency;
import net.tnemc.core.common.currency.item.ItemCurrrency;

import java.math.BigDecimal;

/**
 * A class that acts as a bridge between various player objects on different server software providers.
 *
 * @since 0.1.1.17
 * @author creatorfromhell
 */
public interface PlayerProvider {

  /**
   * Used to get the amount of experience this player has.
   *
   * @return The amount of levels this player has.
   */
  float getExp();

  /**
   * Used to set the amount of experience this player has.
   *
   * @param exp The amount of experience to set for this player.
   */
  void setExp(float exp);

  /**
   * Used to get the amount of experience levels this player has.
   *
   * @return The amount of experience levels this player has.
   */
  int getExpLevel();

  /**
   * Used to set the amount of experience levels this player has.
   *
   * @param level The amount of experience levels to set for this player.
   */
  void setExpLevel(int level);

  /**
   * Used to get an inventory object.
   *
   * @param ender True if the ender chest object should be returned, otherwise false.
   * @return The inventory object.
   */
  Object getInventory(boolean ender);

  BigDecimal getItems(Currency currency, Object inventory);

  default void setItems(Account account, Currency currency, BigDecimal amount, Object inventory,
                        boolean remove) {

    boolean consolidate = EconomyManager.instance().canConsolidate();
    if(currency instanceof ItemCurrrency) {
      consolidate = ((ItemCurrrency)currency).isConsolidate();
    }
    setItems(account, currency, amount, inventory, remove, consolidate);
  }

  void setItems(Account account, Currency currency, BigDecimal amount, Object inventory,
                boolean remove, boolean consolidate);
}