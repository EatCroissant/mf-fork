package minefantasy.mf2.item.titanite;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import minefantasy.mf2.api.tier.IToolMaterial;
import minefantasy.mf2.item.ItemTitanite;
import minefantasy.mf2.item.tool.ItemHoeMF;
import minefantasy.mf2.item.weapon.ItemWeaponMF;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.event.AnvilUpdateEvent;

public class BattleConfigAnvil {
    @SubscribeEvent
    public void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.left;
        ItemStack right = event.right;
        if (left.getItem() instanceof ItemArmor || left.getItem() instanceof ItemWeaponMF || left.getItem() instanceof IToolMaterial && right.getItem() instanceof ItemTitanite) {
            event.cost = 1;
            new ItemTitanite.NBTTitanite(left);
            event.materialCost = 1;
            ItemStack result = left.copy();
            boolean resolved = ItemTitanite.NBTTitanite.upgrade(right, result);
            if(!resolved){
                event.setResult(Event.Result.DENY);
            } else {
                event.setResult(Event.Result.ALLOW);
                event.output = result;
            }
        }
    }
}
