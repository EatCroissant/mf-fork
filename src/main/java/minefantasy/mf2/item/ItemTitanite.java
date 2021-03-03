package minefantasy.mf2.item;

import cpw.mods.fml.common.registry.GameRegistry;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.item.list.CreativeTabMF;
import net.minecraft.item.Item;

public class ItemTitanite extends Item {
    static String[] types = new String[]{"chaos","basic", "dark", "fire", "holy", "ice", "lightning"};

    public ItemTitanite(int type){

        setMaxStackSize(16);
        setTextureName("minefantasy2:titanite/" + types[type]);
        GameRegistry.registerItem(this, "MF_Titanite_" + types[type], MineFantasyII.MODID);
        setCreativeTab(CreativeTabMF.tabGadget);
        this.setUnlocalizedName(types[type]);
    }


}
