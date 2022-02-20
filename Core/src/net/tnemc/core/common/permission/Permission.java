package net.tnemc.core.common.permission;

/**
 * The New Economy Minecraft Server Plugin
 * <p>
 * Created by creatorfromhell on 2/16/2022.
 * <p>
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 * Created by creatorfromhell on 06/30/2017.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class that represents a permission node, which is custom to TNE.
 *
 * @since 0.1.1.17
 * @author creatorfromhell
 */
public class Permission {

  private List<Permission> children = new ArrayList<>();

  private Permission parent;

  private String node;
  private String description;
  private boolean registerAsterisk;

  public Permission(String node, String description, boolean registerAsterisk) {
    this.node = node;
    this.description = description;
    this.registerAsterisk = registerAsterisk;
  }

  public Map<String, String> registerPermissions() {
    return new HashMap<>();
  }
}