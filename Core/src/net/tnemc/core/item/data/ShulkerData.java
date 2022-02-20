package net.tnemc.core.item.data;

import net.tnemc.core.TNECore;
import net.tnemc.core.item.JSONHelper;
import net.tnemc.core.item.SerialItem;
import net.tnemc.core.item.SerialItemData;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * The New Economy Minecraft Server Plugin
 *
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 * Created by Daniel on 11/11/2017.
 */
public class ShulkerData implements SerialItemData {

  private Map<Integer, SerialItem> items = new HashMap<>();

  private boolean valid = false;

  @Override
  public SerialItemData initialize(ItemStack stack) {
    TNECore.log().debug("ShulkerData.initialize.");
    if(SerialItem.isShulker(stack.getType())) {
      TNECore.log().debug("Passed shulker check");
      ItemMeta meta = stack.getItemMeta();
      if(meta instanceof BlockStateMeta) {
        TNECore.log().debug("Is BlockStateMeta");
        BlockStateMeta state = (BlockStateMeta)meta;
        if(state.getBlockState() instanceof ShulkerBox) {
          TNECore.log().debug("Is ShulkerBox BlockState");
          valid = true;
          ShulkerBox shulker = (ShulkerBox)state.getBlockState();
          Inventory inventory = shulker.getInventory();
          TNECore.log().debug("Initializing shulker box..");
          for(int i = 0; i < inventory.getSize(); i++) {
            if(inventory.getItem(i) == null || inventory.getItem(i).getType().equals(Material.AIR)) {
              if(items.containsKey(i)) {
                items.remove(i);
                TNECore.log().debug("Removing item from slot " + i);
              }
            } else {
              TNECore.log().debug("Adding Item: " + inventory.getItem(i).getType().name());
              TNECore.log().debug("Item Slot: " + i);
              items.put(i, new SerialItem(inventory.getItem(i)));
            }
          }
        }
      }
    }
    return this;
  }

  @Override
  public ItemStack build(ItemStack stack) {
    if(valid) {
      TNECore.log().debug("Building shulker");
      BlockStateMeta meta = (BlockStateMeta)stack.getItemMeta();
      TNECore.log().debug("Building shulker");
      ShulkerBox shulkerBox = (ShulkerBox)meta.getBlockState();
      TNECore.log().debug("Building shulker");
      items.forEach((slot, item)->shulkerBox.getInventory().setItem(slot, item.getStack()));
      TNECore.log().debug("Building shulker");
      meta.setBlockState(shulkerBox);
      TNECore.log().debug("Building shulker");
      stack.setItemMeta(meta);
      TNECore.log().debug("Built shulker");
    }
    return stack;
  }

  @Override
  public JSONObject toJSON() {
    JSONObject json = new JSONObject();
    json.put("name", "shulker");
    JSONObject itemsObj = new JSONObject();
    items.forEach((slot, item)->{
      TNECore.log().debug("Item Null?: " + (item == null));
      TNECore.log().debug("Item Type: " + item.getMaterial().name());
      itemsObj.put(slot, item.toJSON());
    });
    json.put("items", itemsObj);
    return json;
  }

  @Override
  public void readJSON(JSONHelper json) {
    valid = true;
    TNECore.log().debug("Shulker Data Start");
    json.getJSON("items").forEach((key, value)->{
      TNECore.log().debug("Slot: " + String.valueOf(key));
      TNECore.log().debug("Item Data: " + ((JSONObject)value).toJSONString());
      final int slot = Integer.valueOf(String.valueOf(key));
      TNECore.log().debug("Amount: " + slot);
      items.put(slot, SerialItem.fromJSON((JSONObject)value));
      TNECore.log().debug("Item Size: " + items.size());
    });
    TNECore.log().debug("Shulker Data END");
  }

  public Map<Integer, SerialItem> getItems() {
    return items;
  }

  public void setItems(Map<Integer, SerialItem> items) {
    this.items = items;
  }
}