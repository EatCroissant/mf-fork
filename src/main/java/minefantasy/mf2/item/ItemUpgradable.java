package minefantasy.mf2.item;

import minefantasy.mf2.item.titanite.ItemTitanite;
import minefantasy.mf2.item.weapon.ItemWeaponMF;
import net.minecraft.item.ItemStack;

public abstract class ItemUpgradable {



    ItemTitanite.NBTTitanite getItemUpgrade(ItemStack i){
        return new ItemTitanite.NBTTitanite(i);
    }

    float[] getTraits(ItemStack i){
        if(i == null || i.getItem() == null)return null;
        if(i.getItem() instanceof ItemWeaponMF){
            ItemTitanite.NBTTitanite upgrades = new ItemTitanite.NBTTitanite(i);


        }
        return new float[0];
    }

    ItemStack upgradeItem(){
return null;
    }

//    getDamage(){
//
//    }
//
//    getDefence(){
//
//    }
//
//    applyDamage(){
//
//    }
}
