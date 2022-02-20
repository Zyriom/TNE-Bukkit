package net.tnemc.core.common.menu;

/**
 * The New Economy Minecraft Server Plugin
 * <p>
 * Created by creatorfromhell on 2/13/2022.
 * <p>
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 * Created by creatorfromhell on 06/30/2017.
 */

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a layout of icons for a menu.
 *
 * @see net.tnemc.core.common.menu.Icon
 * @since 0.1.1.17
 * @author creatorfromhell
 */
public class MenuLayout {

  private Map<Integer, Icon> icons = new HashMap<>();

  private String name;
}