package net.tnemc.core.common.account;

import net.tnemc.core.TNECore;
import net.tnemc.core.common.EconomyManager;
import net.tnemc.core.common.account.balance.HoldingsManager;
import net.tnemc.core.common.currency.Currency;
import net.tnemc.core.common.currency.CurrencyType;
import net.tnemc.core.common.io.Datable;
import net.tnemc.core.common.io.Queryable;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * An object that is used to represent an Account within the economy plugin.
 *
 * @since 0.1.1.17
 * @author creatorfromhell
 */
public class Account implements Datable<String, Account> {

  protected HoldingsManager holdingsManager;

  protected UUID identifier;
  protected String name;
  protected long creationDate;
  protected String pin;

  protected AccountStatus status;

  public Account(UUID identifier, String name) {
    this.identifier = identifier;
    this.name = name;

    //Defaults
    this.creationDate = new Date().getTime();
    this.pin = "";
    this.status = AccountStatus.NORMAL;
  }

  public void setHoldings(@NotNull BigDecimal amount) {
    final String world = world();
    setHoldings(amount, world, EconomyManager.instance().currencyManager().getDefault(world),
                false);
  }

  public void setHoldings(@NotNull BigDecimal amount, @NotNull String world) {
    final String resolved = resolveWorld(world);
    setHoldings(amount, resolved, EconomyManager.instance().currencyManager().getDefault(resolved),
                false);
  }

  public void setHoldings(@NotNull BigDecimal amount, @NotNull String world,
                          @NotNull Currency currency) {
    setHoldings(amount, world, currency, false);
  }

  public void setHoldings(@NotNull BigDecimal amount, @NotNull String world,
                          @NotNull Currency currency, boolean skipDB) {

    Optional<CurrencyType> type = currency.getType();

    if(type.isPresent()) {
      try {
        type.get().setHoldings(this, world, currency, amount, skipDB);
      } catch(SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public BigDecimal getHoldings(@NotNull String world) {
    final String resolved = resolveWorld(world);
    return getHoldings(resolved, EconomyManager.instance().currencyManager().getDefault(resolved));
  }

  public BigDecimal getHoldings(@NotNull String world, @NotNull Currency currency) {

    final Optional<CurrencyType> type = currency.getType();

    if(type.isPresent()) {
      try {
        type.get().getHoldings(this, resolveWorld(world), currency, false);
      } catch(SQLException e) {
        e.printStackTrace();
      }
    }
    return BigDecimal.ZERO;
  }

  public boolean hasHoldings(@NotNull BigDecimal amount) {
    final String world = world();
    return hasHoldings(amount, world, EconomyManager.instance().currencyManager().getDefault(world));
  }

  public boolean hasHoldings(@NotNull BigDecimal amount, @NotNull String world) {
    return hasHoldings(amount, world, EconomyManager.instance().currencyManager().getDefault(world));
  }

  public boolean hasHoldings(@NotNull BigDecimal amount, @NotNull String world, @NotNull Currency currency) {
    return getHoldings(resolveWorld(world), currency).compareTo(amount) >= 0;
  }

  public String resolveWorld(@NotNull String world) {
    return TNECore.connector().worldConnectionProvider().resolveWorld(world);
  }

  public String world() {
    return resolveWorld(TNECore.connector().defaultWorld());
  }

  /**
   * Used to determine if this {@link Account account} represents a Player.
   *
   * @return True if this {@link Account account} represents a Player, otherwise false.
   */
  public boolean isPlayer() {
    return (this instanceof PlayerAccount);
  }

  /**
   * Used to determine if this {@link Account account} supports balances based on inventories.
   * This is usually item-based currencies, or experience-based currencies. This should only return
   * true if the account is a {@link PlayerAccount player}.
   *
   * @return True if this {@link Account account} supports balances based on inventories.
   */
  public boolean supportsInventoryBalances() {
    return isPlayer();
  }

  public HoldingsManager getHoldingsManager() {
    return holdingsManager;
  }

  public void setHoldingsManager(HoldingsManager holdingsManager) {
    this.holdingsManager = holdingsManager;
  }

  public UUID getIdentifier() {
    return identifier;
  }

  public void setIdentifier(UUID identifier) {
    this.identifier = identifier;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(long creationDate) {
    this.creationDate = creationDate;
  }

  public String getPin() {
    return pin;
  }

  public void setPin(String pin) {
    this.pin = pin;
  }

  public AccountStatus getStatus() {
    return status;
  }

  public void setStatus(AccountStatus status) {
    this.status = status;
  }

  /**
   * Used to get the {@link Queryable data part} of this object. This is what houses all the
   * IO logic.
   *
   * @return The {@link Queryable data part} of this object. This is what houses all the
   * IO logic.
   */
  @Override
  public Queryable<String, Account> getData() {
    //TODO: implement this
    return null;
  }

  /**
   * Sets the {@link Queryable data part} of this object. This is what houses all the
   * IO logic.
   *
   * @param dataObject The data object to set to.
   */
  @Override
  public void setData(Queryable<String, Account> dataObject) {
    //TODO: implement this
  }
}