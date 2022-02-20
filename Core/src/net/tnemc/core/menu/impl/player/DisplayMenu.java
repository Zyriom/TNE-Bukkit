package net.tnemc.core.menu.impl.player;

import net.tnemc.core.TNECore;
import net.tnemc.core.common.WorldVariant;
import net.tnemc.core.common.account.WorldFinder;
import net.tnemc.core.common.api.IDFinder;
import net.tnemc.core.common.currency.TNECurrency;
import net.tnemc.core.menu.Menu;
import net.tnemc.core.menu.icons.display.DisplayCurrencyIcon;
import net.tnemc.core.menu.icons.shared.BackIcon;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

/**
 * The New Economy Minecraft Server Plugin
 *
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 * Created by Daniel on 11/5/2017.
 */
public class DisplayMenu extends Menu {
  public DisplayMenu() {
    super("display", "[TNE]Action", 1);

    icons.put(0, new BackIcon("main", 0));
  }

  @Override
  public Inventory buildInventory(Player player) {
    TNECore.log().debug("=====START DisplayScreen.initializeCurrencies =====");
    String world = (String)TNE.menuManager().getViewerData(IDFinder.getID(player), "action_world");
    UUID p = (UUID)TNE.menuManager().getViewerData(IDFinder.getID(player), "action_player");

    TNECore.log().debug("Player: " + p);
    TNECore.log().debug("World: " + world);
    if(world == null) world = WorldFinder.getWorld(p, WorldVariant.BALANCE);
    int i = 1;
    for(TNECurrency currency : TNE.instance().api().getCurrencies(world)) {
      icons.put(i, new DisplayCurrencyIcon(currency.name(), i));
      i++;
    }

    Integer size = icons.size() / 9;
    if((icons.size() % 9) > 0) size++;
    if(size < 10) rows = 1;
    else rows = (size / 9);

    TNECore.log().debug("Icons: " + icons.size());
    TNECore.log().debug("Rows: " + getRows());

    return super.buildInventory(player);
  }
}