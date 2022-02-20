package net.tnemc.discord;

import net.tnemc.core.TNE;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;

/**
 * The New Economy Minecraft Server Plugin
 * <p>
 * Created by creatorfromhell on 12/19/2021.
 * <p>
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 * Created by creatorfromhell on 06/30/2017.
 */
public class PluginLoadedListener implements Listener {

  private TNE plugin;

  public PluginLoadedListener(TNE plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onEvent(PluginEnableEvent event) {
    if(event.getPlugin().getName().equalsIgnoreCase("DiscordSRV")) {
      DiscordModule.instance().initialize(plugin);
    }
  }
}