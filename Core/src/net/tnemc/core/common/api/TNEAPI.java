package net.tnemc.core.common.api;

import net.tnemc.core.TNECore;
import net.tnemc.core.common.EconomyManager;
import net.tnemc.core.common.currency.Currency;
import net.tnemc.core.common.currency.CurrencyType;
import net.tnemc.core.common.currency.formatter.CurrencyFormatter;
import net.tnemc.core.common.transaction.tax.TaxEntry;
import net.tnemc.core.common.transaction.tax.TaxType;
import net.tnemc.core.common.transaction.type.TransactionType;
import net.tnemc.core.common.utils.TopBalance;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * The New Economy Minecraft Server Plugin
 *
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 * Created by Daniel on 8/3/2017.
 */

@Deprecated
public class TNEAPI {

  private TNECore core;

  public TNEAPI(TNECore core) {
    this.core = core;
  }


  public boolean hasModule(String name) {
    return TNE.loader().hasModule(name);
  }

  public boolean hasModuleVersion(String name, String version) {
    String[] check = version.split("\\.");
    String[] current = TNE.loader().getModule(name).version().split("\\.");

    int i = 0;
    for(String str : current) {

      int checkTest = 0;
      int currentTest = 0;
      try {
        if(i >= check.length) return true;
        checkTest = Integer.parseInt(check[i]);
        currentTest = Integer.parseInt(str);

      } catch(Exception ignore) {

      }
      if(currentTest > checkTest) return true;
      if(currentTest < checkTest) return false;

      i++;
    }
    return false;
  }

  /**
   * @return Whether or not this implementation has bank support.
   */
  public boolean hasBanks() {
    return false;
  }

  /**
   * Checks to see if a {@link Currency} exists with this name.
   *
   * @param name The name of the {@link Currency} to search for.
   * @return True if the currency exists, else false.
   */
  public boolean hasCurrency(String name) {
    return hasCurrency(name, plugin.defaultWorld);
  }

  /**
   * Checks to see if a {@link Currency} exists with this name.
   * @param name The name of the {@link Currency} to search for.
   * @param world The name of the world to check for this {@link Currency} in.
   * @return True if the currency exists, else false.
   */
  public boolean hasCurrency(String name, String world) {
    return TNE.manager().currencyManager().contains(world, name);
  }

  /**
   * Finds the default {@link Currency} for the server.
   * @return The default {@link Currency} for the server.
   */
  public Currency getDefault() {
    return TNE.manager().currencyManager().get(plugin.defaultWorld);
  }

  /**
   * Finds the default {@link Currency} for a world
   * @param world The name of the world to use.
   * @return The default {@link Currency} for this world.
   */
  public Currency getDefault(String world) {
    return TNE.manager().currencyManager().get(world);
  }

  /**
   * Grabs a {@link Set} of {@link Currency} objects that exist.
   * @return A Set containing all the {@link Currency} objects that exist on this server.
   */
  public Set<Currency> getCurrencies() {
    return new HashSet<>(TNE.manager().currencyManager().getCurrencies());
  }

  /**
   * Grabs a {@link Set} of {@link Currency} objects that exist in a world
   * @param world The name of the world to use in this search.
   * @return A Set containing all the {@link Currency} objects that exist on this world.
   */
  public Set<Currency> getCurrencies(String world) {
    return new HashSet<>(TNE.manager().currencyManager().getWorldCurrencies(world));
  }

  /**
   * Checks to see if a {@link Currency} has the specified tier.
   * @param name The name of the {@link TNETier} to search for.
   * @param currency The {@link Currency} to search
   * @return True if the tier exists, otherwise false.
   */
  public boolean hasTier(String name, Currency currency) {
    return currency.hasTier(name);
  }

  /**
   * Checks to see if a {@link Currency} has the specified tier.
   * @param name The name of the {@link TNETier} to search for.
   * @param currency The {@link Currency} to search
   * @param world The name of the world to use for search purposes.
   * @return True if the tier exists, otherwise false.
   */
  public boolean hasTier(String name, Currency currency, String world) {
    return currency.hasTier(name);
  }

  /**
   * Returns a {@link Set} of {@link TNETier} objects associated with the specified {@link Currency}.
   * @param currency The {@link Currency} to grab the tiers from.
   * @return A Set containing all the {@link TNETier} objects belonging to this {@link Currency}.
   */
  public Set<TNETier> getTiers(Currency currency) {
    return currency.getTiers();
  }

  /**
   * Checks to see if an account exists for this identifier. This method should be used for non-player accounts.
   * @param identifier The identifier of the account.
   * @return True if an account exists for this player, else false.
   */
  public boolean hasAccount(String identifier) {
    return TNE.manager().exists(IDFinder.getID(identifier));
  }

  /**
   * Checks to see if an account exists for this identifier. This method should be used for player accounts.
   * @param identifier The {@link UUID} of the account.
   * @return True if an account exists for this player, else false.
   */
  public boolean hasAccount(UUID identifier) {
    return TNE.manager().exists(identifier);
  }

  /**
   * Attempts to retrieve an account by the specified identifier. This method should be used for non-player accounts.
   * @param identifier The of the account.
   * @return The instance of the account if it exists, otherwise null.
   */
  public TNEAccount getAccount(String identifier) {
    return TNE.manager().getAccount(IDFinder.getID(identifier));
  }

  /**
   * Attempts to retrieve an account by the specified identifier. This method should be used for player accounts.
   * @param identifier The {@link UUID} of the account.
   * @return The instance of the account if it exists, otherwise null.
   */
  public TNEAccount getAccount(UUID identifier) {
    return TNE.manager().getAccount(identifier);
  }

  /**
   * Attempts to create an account for this identifier. This method should be used for non-player accounts.
   * @param identifier The identifier of the account.
   * @return True if an account was created, else false.
   */
  public boolean createAccount(String identifier) {
    return TNE.manager().createAccount(IDFinder.getID(identifier), IDFinder.getUsername(identifier));
  }

  /**
   * Attempts to create an account for this identifier. This method should be used for player accounts.
   * @param identifier The {@link UUID} of the account.
   * @return True if an account was created, else false.
   */
  public boolean createAccount(UUID identifier) {
    return TNE.manager().createAccount(identifier, IDFinder.getUsername(identifier.toString()));
  }

  /**
   * Attempts to create an account for this identifier. This method should be used for player accounts.
   * @param identifier The {@link UUID} of the account.
   * @param username The username to use
   * @return True if an account was created, else false.
   */
  public boolean createAccount(UUID identifier, String username) {
    return TNE.manager().createAccount(identifier, username);
  }

  /**
   * This is a shortcut method that combines getAccount with createAccount. This method should be used for non-player
   * Accounts.
   * @param identifier The of the account.
   * @return The instance of the account.
   */
  public TNEAccount getOrCreate(String identifier) {
    if(!hasAccount(identifier)) createAccount(identifier);
    return getAccount(identifier);
  }

  /**
   * This is a shortcut method that combines getAccount with createAccount. This method should be used for non-player
   * Accounts.
   * @param identifier The {@link UUID} of the account.
   * @return The instance of the account.
   */
  public TNEAccount getOrCreate(UUID identifier) {
    if(!hasAccount(identifier)) createAccount(identifier);
    return getAccount(identifier);
  }

  /**
   * Formats a monetary amount into a more text-friendly version.
   * @param amount The amount of currency to format.
   * @param world The world in which this format operation is occurring.
   * @return The formatted amount.
   */
  public String format(BigDecimal amount, String world) {
    return CurrencyFormatter.format(EconomyManager.instance().currencyManager().getDefault(world), world, amount, "");
  }

  /**
   * Formats a monetary amount into a more text-friendly version.
   * @param amount The amount of currency to format.
   * @param currency The {@link Currency} associated with the amount to be formatted.
   * @param world The world in which this format operation is occuring.
   * @return The formatted amount.
   */
  public String format(BigDecimal amount, Currency currency, String world) {
    return CurrencyFormatter.format(currency, world, amount, "");
  }

  /**
   * Used to get the balance of an account.
   * @param identifier The identifier of the account. This may be a {@link UUID}, or a player's name.
   * @return The balance of the account.
   */
  public BigDecimal getHoldings(String identifier) {
    return TNE.manager().getAccount(IDFinder.getID(identifier)).getHoldings();
  }

  /**
   * Used to get the balance of an account.
   * @param identifier The identifier of the account. This may be a {@link UUID}, or a player's name.
   * @param world The name of the world associated with the balance.
   * @return The balance of the account.
   */
  public BigDecimal getHoldings(String identifier, String world) {
    return TNE.manager().getAccount(IDFinder.getID(identifier)).getHoldings(WorldFinder.getWorldName(world, WorldVariant.BALANCE));
  }

  /**
   * Used to get the balance of an account.
   * @param identifier The identifier of the account. This may be a {@link UUID}, or a player's name.
   * @param world The name of the world associated with the balance.
   * @param currency The {@link Currency} object associated with the balance.
   * @return The balance of the account.
   */
  public BigDecimal getHoldings(String identifier, String world, Currency currency) {
    return TNE.manager().getAccount(IDFinder.getID(identifier)).getHoldings(world, currency);
  }

  /**
   * Used to get the balance of an account.
   * @param identifier The identifier of the account. This may be a {@link UUID}, or a player's name.
   * @param currency The {@link Currency} object associated with the balance.
   * @return The balance of the account for the specified {@link Currency}.
   */
  public BigDecimal getHoldings(String identifier, Currency currency) {
    UUID id = IDFinder.getID(identifier);
    return TNE.manager().getAccount(id).getHoldings(WorldFinder.getWorldName(plugin.defaultWorld, WorldVariant.BALANCE), currency);
  }

  /**
   * Used to determine if an account has at least an amount of funds.
   * @param identifier The identifier of the account. This may be a {@link UUID}, or a player's name.
   * @param amount The amount you wish to use for this check.
   * @return True if the account has at least the specified amount of funds, otherwise false.
   */
  public boolean hasHoldings(String identifier, BigDecimal amount) {
    return TNE.manager().getAccount(IDFinder.getID(identifier)).hasHoldings(amount);
  }

  /**
   * Used to determine if an account has at least an amount of funds.
   * @param identifier The identifier of the account. This may be a {@link UUID}, or a player's name.
   * @param amount The amount you wish to use for this check.
   * @param world The name of the world associated with the amount.
   * @return True if the account has at least the specified amount of funds, otherwise false.
   */
  public boolean hasHoldings(String identifier, BigDecimal amount, String world) {
    return TNE.manager().getAccount(IDFinder.getID(identifier)).hasHoldings(amount, world);
  }

  /**
   * Used to determine if an account has at least an amount of funds.
   * @param identifier The identifier of the account. This may be a {@link UUID}, or a player's name.
   * @param amount The amount you wish to use for this check.
   * @param currency The {@link Currency} object associated with the amount.
   * @return True if the account has at least the specified amount of funds, otherwise false.
   */
  public boolean hasHoldings(String identifier, BigDecimal amount, Currency currency) {
    return TNE.manager().getAccount(IDFinder.getID(identifier)).hasHoldings(amount, currency);
  }

  /**
   * Used to determine if an account has at least an amount of funds.
   * @param identifier The identifier of the account. This may be a {@link UUID}, or a player's name.
   * @param amount The amount you wish to use for this check.
   * @param currency The {@link Currency} object associated with the amount.
   * @param world The name of the world associated with the amount.
   * @return True if the account has at least the specified amount of funds, otherwise false.
   */
  public boolean hasHoldings(String identifier, BigDecimal amount, Currency currency, String world) {
    return TNE.manager().getAccount(IDFinder.getID(identifier)).hasHoldings(amount, currency, WorldFinder.getWorldName(world, WorldVariant.BALANCE));
  }

  /**
   * Used to add funds to an account.
   * @param identifier The identifier of the account. This may be a {@link UUID}, or a player's name.
   * @param amount The amount you wish to add to this account.
   * @return True if the funds were added to the account, otherwise false.
   */
  public boolean addHoldings(String identifier, BigDecimal amount) {
    return TNE.manager().getAccount(IDFinder.getID(identifier)).addHoldings(amount);
  }

  /**
   * Used to add funds to an account.
   * @param identifier The identifier of the account. This may be a {@link UUID}, or a player's name.
   * @param amount The amount you wish to add to this account.
   * @param world The name of the world associated with the amount.
   * @return True if the funds were added to the account, otherwise false.
   */
  public boolean addHoldings(String identifier, BigDecimal amount, String world) {
    return TNE.manager().getAccount(IDFinder.getID(identifier)).addHoldings(amount, WorldFinder.getWorldName(world, WorldVariant.BALANCE));
  }

  /**
   * Used to add funds to an account.
   * @param identifier The identifier of the account. This may be a {@link UUID}, or a player's name.
   * @param amount The amount you wish to add to this account.
   * @param currency The {@link Currency} object associated with the amount.
   * @return True if the funds were added to the account, otherwise false.
   */
  public boolean addHoldings(String identifier, BigDecimal amount, Currency currency) {
    return TNE.manager().getAccount(IDFinder.getID(identifier)).addHoldings(amount, currency);
  }

  /**
   * Used to add funds to an account.
   * @param identifier The identifier of the account. This may be a {@link UUID}, or a player's name.
   * @param amount The amount you wish to add to this account.
   * @param currency The {@link Currency} object associated with the amount.
   * @param world The name of the world associated with the amount.
   * @return True if the funds were added to the account, otherwise false.
   */
  public boolean addHoldings(String identifier, BigDecimal amount, Currency currency, String world) {
    return TNE.manager().getAccount(IDFinder.getID(identifier)).addHoldings(amount, currency, WorldFinder.getWorldName(world, WorldVariant.BALANCE));
  }

  /**
   * Used to determine if a call to the corresponding addHoldings method would be successful. This method does not
   * affect an account's funds.
   *
   * @param identifier The identifier of the account. This may be a {@link UUID}, or a player's name.
   * @param amount     The amount you wish to add to this account.
   * @return True if a call to the corresponding addHoldings method would return true, otherwise false.
   */
  public boolean canAddHoldings(String identifier, BigDecimal amount) {
    return true;
  }

  /**
   * Used to determine if a call to the corresponding addHoldings method would be successful. This method does not
   * affect an account's funds.
   *
   * @param identifier The identifier of the account. This may be a {@link UUID}, or a player's name.
   * @param amount     The amount you wish to add to this account.
   * @param world      The name of the world associated with the amount.
   * @return True if a call to the corresponding addHoldings method would return true, otherwise false.
   */
  public boolean canAddHoldings(String identifier, BigDecimal amount, String world) {
    return true;
  }

  /**
   * Used to determine if a call to the corresponding addHoldings method would be successful. This method does not
   * affect an account's funds.
   *
   * @param identifier The identifier of the account. This may be a {@link UUID}, or a player's name.
   * @param amount     The amount you wish to add to this account.
   * @param currency   The {@link Currency} object associated with the amount.
   * @return True if a call to the corresponding addHoldings method would return true, otherwise false.
   */
  public boolean canAddHoldings(String identifier, BigDecimal amount, Currency currency) {
    return true;
  }

  /**
   * Used to determine if a call to the corresponding addHoldings method would be successful. This method does not
   * affect an account's funds.
   *
   * @param identifier The identifier of the account. This may be a {@link UUID}, or a player's name.
   * @param amount     The amount you wish to add to this account.
   * @param currency   The {@link Currency} object associated with the amount.
   * @param world      The name of the world associated with the amount.
   * @return True if a call to the corresponding addHoldings method would return true, otherwise false.
   */
  public boolean canAddHoldings(String identifier, BigDecimal amount, Currency currency, String world) {
    return true;
  }

  /**
   * Used to remove funds from an account.
   * @param identifier The identifier of the account. This may be a {@link UUID}, or a player's name.
   * @param amount The amount you wish to remove from this account.
   * @return True if the funds were removed from the account, otherwise false.
   */
  public boolean removeHoldings(String identifier, BigDecimal amount) {
    return TNE.manager().getAccount(IDFinder.getID(identifier)).removeHoldings(amount);
  }

  /**
   * Used to remove funds from an account.
   * @param identifier The identifier of the account. This may be a {@link UUID}, or a player's name.
   * @param amount The amount you wish to remove from this account.
   * @param world The name of the world associated with the amount.
   * @return True if the funds were removed from the account, otherwise false.
   */
  public boolean removeHoldings(String identifier, BigDecimal amount, String world) {
    return TNE.manager().getAccount(IDFinder.getID(identifier)).removeHoldings(amount, WorldFinder.getWorldName(world, WorldVariant.BALANCE));
  }

  /**
   * Used to remove funds from an account.
   * @param identifier The identifier of the account. This may be a {@link UUID}, or a player's name.
   * @param amount The amount you wish to remove from this account.
   * @param currency The {@link Currency} object associated with the amount.
   * @return True if the funds were removed from the account, otherwise false.
   */
  public boolean removeHoldings(String identifier, BigDecimal amount, Currency currency) {
    return TNE.manager().getAccount(IDFinder.getID(identifier)).removeHoldings(amount, currency);
  }

  /**
   * Used to remove funds from an account.
   * @param identifier The identifier of the account. This may be a {@link UUID}, or a player's name.
   * @param amount The amount you wish to remove from this account.
   * @param currency The {@link Currency} object associated with the amount.
   * @param world The name of the world associated with the amount.
   * @return True if the funds were removed from the account, otherwise false.
   */
  public boolean removeHoldings(String identifier, BigDecimal amount, Currency currency, String world) {
    return TNE.manager().getAccount(IDFinder.getID(identifier)).removeHoldings(amount, currency, WorldFinder.getWorldName(world, WorldVariant.BALANCE));
  }

  /**
   * Used to determine if a call to the corresponding removeHoldings method would be successful. This method does not
   * affect an account's funds.
   *
   * @param identifier The identifier of the account. This may be a {@link UUID}, or a player's name.
   * @param amount     The amount you wish to remove from this account.
   * @return True if a call to the corresponding removeHoldings method would return true, otherwise false.
   */
  public boolean canRemoveHoldings(String identifier, BigDecimal amount) {
    String world = plugin.defaultWorld;
    Currency currency = TNE.manager().currencyManager().get(world);
    return hasCurrency(currency.name(), world) && hasHoldings(identifier, amount, currency, WorldFinder.getWorldName(world, WorldVariant.BALANCE));
  }

  /**
   * Used to determine if a call to the corresponding removeHoldings method would be successful. This method does not
   * affect an account's funds.
   *
   * @param identifier The identifier of the account. This may be a {@link UUID}, or a player's name.
   * @param amount     The amount you wish to remove from this account.
   * @param world      The name of the world associated with the amount.
   * @return True if a call to the corresponding removeHoldings method would return true, otherwise false.
   */
  public boolean canRemoveHoldings(String identifier, BigDecimal amount, String world) {
    Currency currency = TNE.manager().currencyManager().get(world);
    return hasCurrency(currency.name(), world) && hasHoldings(identifier, amount, currency, WorldFinder.getWorldName(world, WorldVariant.BALANCE));
  }

  /**
   * Used to determine if a call to the corresponding removeHoldings method would be successful. This method does not
   * affect an account's funds.
   *
   * @param identifier The identifier of the account. This may be a {@link UUID}, or a player's name.
   * @param amount     The amount you wish to remove from this account.
   * @param currency   The {@link Currency} object associated with the amount.
   * @return True if a call to the corresponding removeHoldings method would return true, otherwise false.
   */
  public boolean canRemoveHoldings(String identifier, BigDecimal amount, Currency currency) {
    return hasCurrency(currency.name()) && hasHoldings(identifier, amount, currency);
  }

  /**
   * Used to determine if a call to the corresponding removeHoldings method would be successful. This method does not
   * affect an account's funds.
   *
   * @param identifier The identifier of the account. This may be a {@link UUID}, or a player's name.
   * @param amount     The amount you wish to remove from this account.
   * @param currency   The {@link Currency} object associated with the amount.
   * @param world      The name of the world associated with the amount.
   * @return True if a call to the corresponding removeHoldings method would return true, otherwise false.
   */
  public boolean canRemoveHoldings(String identifier, BigDecimal amount, Currency currency, String world) {
    return hasCurrency(currency.name(), world) && hasHoldings(identifier, amount, currency, WorldFinder.getWorldName(world, WorldVariant.BALANCE));
  }

  /**
   * Performs a {@link TNETransaction}.
   * @param transaction The {@link TNETransaction} to perform.
   * @return The {@link TransactionResult} of the {@link TNETransaction}.
   */
  public TransactionResult performTransaction(TNETransaction transaction) {
    return TNE.transactionManager().perform(transaction);
  }

  /**
   * Attempts to get the {@link TNETransaction} associated with the specified {@link UUID}.
   *
   * @param uuid The {@link UUID} of the {@link TNETransaction}.
   * @return A non-empty {@link Optional} if a {@link TNETransaction} exists with the specified {@link UUID}.
   */
  public Optional<TNETransaction> getTransaction(UUID uuid) {
    return Optional.ofNullable(TNE.transactionManager().get(uuid));
  }

  /**
   * Attempts to void the {@link TNETransaction} with the specified {@link UUID}.
   *
   * @param uuid The {@link UUID} of the {@link TNETransaction}.
   * @return True if the {@link TNETransaction} was voided, otherwise false.
   */
  public boolean voidTransaction(UUID uuid) {
    return TNE.transactionManager().voidTransaction(uuid);
  }

  /**
   * Grabs a {@link Set} of {@link TransactionType} objects available for use.
   *
   * @return A {@link Set} of {@link TransactionType} objects.
   */
  public Set<TransactionType> getTransactionTypes() {
    return new HashSet<>(TNE.transactionManager().getTypes());
  }

  /**
   * Returns a {@link Map} of all {@link TNETransaction} objects that have been recorded by this {@link EconomyAPI}
   * implementation.
   * <p>
   * The key is the {@link UUID} of the {@link TNETransaction}, while the value is the {@link TNETransaction} object itself.
   *
   * @return A {@link Map} of all recorded {@link TNETransaction} objects.
   */
  public Map<UUID, TNETransaction> getTransactions() {
    Map<UUID, TNETransaction> transactions = new HashMap<>();
    transactions.putAll(TNE.transactionManager().getTransactions());

    return transactions;
  }

  /**
   * Returns a {@link Map} of all {@link TNETransaction} objects that have been recorded by this {@link EconomyAPI}
   * implementation, which involve the account with the specified identifier.
   * <p>
   * The key is the {@link UUID} of the {@link TNETransaction}, while the value is the {@link TNETransaction} object itself.
   *
   * @param identifier The identifier of the account. This may be a {@link UUID}, or a player's name.
   * @return A {@link Map} of all recorded {@link TNETransaction} objects, which involve the account with the specified
   * identifier.
   */
  public Map<UUID, TNETransaction> getTransactions(String identifier) {
    Map<UUID, TNETransaction> transactions = new HashMap<>();
    transactions.putAll(TNE.transactionManager().getTransactions(IDFinder.getID(identifier)));

    return transactions;
  }

  /**
   * @return Whether or not this implementation supports multiple-players sharing a bank account.
   */
  public boolean sharedBanks() {
    return false;
  }


  /**
   * Register a custom {@link TransactionType}.
   *
   * @param type The {@link TransactionType type} to register.
   * @return True if the {@link TransactionType type} was registered.
   */
  public boolean registerTransactionType(TransactionType type) {
    TNE.transactionManager().addType(type);
    return true;
  }

  /**
   * Register a custom {@link TransactionResult}.
   *
   * @param result The {@link TransactionResult result} to register.
   * @return True if the {@link TransactionResult result} was registered.
   */
  public boolean registerTransactionResult(TransactionResult result) {
    TNE.transactionManager().addResult(result);
    return true;
  }

  //TODO: implement the new methods.
  /**
   * Attempt to find a {@link TransactionResult}.
   * @param name The name of the {@link TransactionResult result}.
   * @return Optional with {@link TransactionResult result} if exists, or empty Optional.
   */
  public Optional<TransactionResult> findTransactionResult(String name) {
    return Optional.empty();
  }

  /**
   * Used to remove a tax exception from an account from a given {@link TransactionType}.
   * @param identifier The identifier associated with the account that should have the exception removed
   * from it.
   * @param transactionType The name of the {@link TransactionType} to remove the exception from.
   * @return True if the tax exception was removed successfully, otherwise false.
   */
  public boolean removeTaxException(String identifier, String transactionType) {
    return false;
  }
  /**
   * Used to register an exception to taxation of a certain {@link TransactionType} for an account with
   * the specified identifier.
   * @param identifier The identifier associated with the account that should have the exception placed
   * on it.
   * @param transactionType The name of the {@link TransactionType} to add the exception for.
   * @param exception The new {@link TaxEntry} for the account in the exception.
   * @return True if the tax exception was registered successfully, otherwise false.
   */
  public boolean registerTaxException(String identifier, String transactionType, TaxEntry exception) {
    return false;
  }
  /**
   * Used to register a {@link TaxType taxType} to the economy provider.
   * @param taxType The {@link TaxType taxType} to register.
   * @return True if it was registered, otherwise false.
   */
  public boolean registerTaxType(TaxType taxType) {
    return false;
  }

  /**
   * Attempt to find a {@link TaxType}.
   * @param name The name of the {@link TaxType tax type}.
   * @return Optional with {@link TaxType tax type} if exists, or empty Optional.
   */
  public Optional<TaxType> findTaxType(String name) {
    return Optional.empty();
  }

  /**
   * Register a {@link CurrencyType} to be used with TNE.
   * @param type The {@link CurrencyType} to register.
   */
  public void registerCurrencyType(CurrencyType type) {
    TNE.manager().currencyManager().addCurrencyType(type);
  }

  /**
   * @param name The name of the {@link CurrencyType} to look for.
   * @return an {@link CurrencyType} object if founder, otherwise false.
   */
  public CurrencyType getType(String name) {
    return TNE.manager().currencyManager().getType(name);
  }

  /**
   * Register a {@link Currency}  to be used by other plugins.
   *
   * @param currency The {@link Currency} to register.
   * @return True if the {@link Currency} was registered, otherwise false.
   */
  public boolean registerCurrency(Currency currency) {
    return registerCurrency(currency, plugin.defaultWorld);
  }

  /**
   * Register a {@link Currency}  to be used by other plugins.
   *
   * @param currency The {@link Currency} to register.
   * @param world    The name of the world to use during the registration process.
   * @return True if the {@link Currency}  was registered, otherwise false.
   */
  public boolean registerCurrency(Currency currency, String world) {
    TNE.manager().currencyManager().addCurrency(world, currency);
    return true;
  }

  /**
   * Register a {@link Currency} {@link TNETier} to be used by other plugins.
   *
   * @param tier     The {@link TNETier} to register.
   * @param currency The {@link Currency} to register this {@link TNETier} under.
   * @return True if the {@link TNETier} was registered, otherwise false.
   */
  public boolean registerTier(TNETier tier, Currency currency) {
    return registerTier(tier, currency, plugin.defaultWorld);
  }

  /**
   * Register a {@link Currency} {@link TNETier} to be used by other plugins.
   *
   * @param tier     The {@link TNETier} to register.
   * @param currency The {@link Currency} to register this {@link TNETier} under.
   * @param world    The name of the world to use during the registration process.
   * @return True if the {@link TNETier} was registered, otherwise false.
   */
  public boolean registerTier(TNETier tier, Currency currency, String world) {
    if(TNE.manager().currencyManager().contains(world, currency.name())) {
      if(tier.isMajor()) {
        TNE.manager().currencyManager().get(world, currency.name()).addTNEMajorTier(tier);
      } else {
        TNE.manager().currencyManager().get(world, currency.name()).addTNEMinorTier(tier);
      }
      return true;
    }
    return false;
  }

  /**
   * General API Methods
   */

  /**
   * Returns a {@link LinkedHashMap} containing each user's UUID and balance, in the form of a {@link BigDecimal} object.
   * @param pageLimit The amount of entries per page.
   * @param page The page you wish to grab.
   * @return {@link LinkedHashMap} containing each user's UUID and balance, in the form of a {@link BigDecimal} object.
   */
  public LinkedList<TopBalance> getBalTopPage(int pageLimit, int page) {
    try {
      return TNE.saveManager().getTNEManager().getTNEProvider().topBalances(TNE.instance().defaultWorld, TNE.manager().currencyManager().get(TNE.instance().defaultWorld).name(), pageLimit, page);
    } catch (SQLException e) {
      return new LinkedList<>();
    }
  }

  /**
   * Returns a {@link LinkedHashMap} containing each user's UUID and balance, in the form of a {@link BigDecimal} object.
   * @param world The world to use for the bal top check.
   * @param pageLimit The amount of entries per page.
   * @param page The page you wish to grab.
   * @return {@link LinkedHashMap} containing each user's UUID and balance, in the form of a {@link BigDecimal} object.
   */
  public LinkedList<TopBalance> getBalTopPage(String world, int pageLimit, int page) {
    try {
      return TNE.saveManager().getTNEManager().getTNEProvider().topBalances(world, TNE.manager().currencyManager().get(world).name(), pageLimit, page);
    } catch (SQLException e) {
      return new LinkedList<>();
    }
  }

  /**
   * Returns a {@link LinkedList} containing each user's UUID and balance, in the form of a {@link BigDecimal} object.
   * @param world The world to use for the bal top check.
   * @param currency The currency to use for the bal top check.
   * @param pageLimit The amount of entries per page.
   * @param page The page you wish to grab.
   * @return {@link LinkedList} containing each user's UUID and balance, in the form of a {@link BigDecimal} object.
   */
  public LinkedList<TopBalance> getBalTopPage(String world, String currency, int pageLimit, int page) {
    try {
      return TNE.saveManager().getTNEManager().getTNEProvider().topBalances(world, currency, pageLimit, page);
    } catch (SQLException e) {
      return new LinkedList<>();
    }
  }

  /**
   * Get the value of a BigDecimal configuration.
   * @param configuration The configuration node.
   * @return The value of the configuration.
   */
  public BigDecimal getBigDecimal(String configuration) {
    return getBigDecimal(configuration, TNECore.connector().defaultWorld());
  }

  /**
   * Get the value of a BigDecimal configuration.
   * @param configuration The configuration node.
   * @param world The name of the world to use.
   * @return The value of the configuration.
   */
  public BigDecimal getBigDecimal(String configuration, String world) {
    String value = getConfiguration(configuration, world, "").toString();
    return CurrencyFormatter.translateBigDecimal(value, world);
  }

  /**
   * Get the value of a BigDecimal configuration.
   * @param configuration The configuration node.
   * @param world The name of the world to use.
   * @param uuid The uuid of the player to use.
   * @return The value of the configuration.
   */
  public BigDecimal getBigDecimal(String configuration, String world, UUID uuid) {
    String value = getConfiguration(configuration, world, uuid).toString();
    return CurrencyFormatter.translateBigDecimal(value, world);
  }

  /**
   * Get the value of a BigDecimal configuration.
   * @param configuration The configuration node.
   * @param world The name of the world to use.
   * @param player The identifier of the player to use.
   * @return The value of the configuration.
   */
  public BigDecimal getBigDecimal(String configuration, String world, String player) {
    String value = getConfiguration(configuration, world, player).toString();
    return CurrencyFormatter.translateBigDecimal(value, world);
  }

  public UUID getID(String identifier) {
    return IDFinder.getID(identifier);
  }

  /*
   * Configuration-related Methods.
   */
  public String getString(String configuration) {
    return (String)getConfiguration(configuration, TNECore.connector().defaultWorld());
  }

  public String getString(String configuration, String world) {
    return (String)getConfiguration(configuration, world, "");
  }

  public String getString(String configuration, String world, UUID uuid) {
    return (String)getConfiguration(configuration, world, uuid.toString());
  }

  public String getString(String configuration, String world, String player) {
    return (String)getConfiguration(configuration, world, player);
  }

  public Boolean getBoolean(String configuration) {
    return Boolean.valueOf(getConfiguration(configuration, TNECore.connector().defaultWorld()).toString());
  }

  public Boolean getBoolean(String configuration, String world) {
    return Boolean.valueOf(getConfiguration(configuration, world, "").toString());
  }

  public Boolean getBoolean(String configuration, String world, UUID uuid) {
    return Boolean.valueOf(getConfiguration(configuration, world, uuid.toString()).toString());
  }

  public Boolean getBoolean(String configuration, String world, String player) {
    return Boolean.valueOf(getConfiguration(configuration, world, player).toString());
  }

  public Integer getInteger(String configuration) {
    return Integer.valueOf(getConfiguration(configuration, TNECore.connector().defaultWorld()).toString());
  }

  public Integer getInteger(String configuration, String world) {
    return Integer.valueOf(getConfiguration(configuration, world, "").toString());
  }

  public Integer getInteger(String configuration, String world, UUID uuid) {
    return Integer.valueOf(getConfiguration(configuration, world, uuid.toString()).toString());
  }

  public Integer getInteger(String configuration, String world, String player) {
    return Integer.valueOf(getConfiguration(configuration, world, player).toString());
  }

  public boolean hasConfiguration(String configuration) {
    if(configuration.toLowerCase().contains("database")) return false;
    return TNE.configurations().hasConfiguration(configuration);
  }

  public Object getConfiguration(String configuration) {
    return getConfiguration(configuration, TNECore.connector().defaultWorld());
  }

  public Object getConfiguration(String configuration, String world) {
    return getConfiguration(configuration, world, "");
  }

  public Object getConfiguration(String configuration, String world, UUID uuid) {
    return getConfiguration(configuration, world, uuid.toString());
  }

  public Object getConfiguration(String configuration, String world, String player) {
    if(configuration.toLowerCase().contains("database")) return "";
    return TNE.configurations().getConfiguration(configuration, world, player);
  }

  public void setConfiguration(String configuration, Object value) {
    if(configuration.toLowerCase().contains("database")) return;
    TNE.configurations().setConfiguration(configuration, value);
  }
}