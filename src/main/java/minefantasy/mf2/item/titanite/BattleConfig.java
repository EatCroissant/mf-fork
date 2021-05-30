package minefantasy.mf2.item.titanite;

import com.google.common.primitives.Floats;
import minefantasy.mf2.api.armour.ArmourMaterialMF;
import minefantasy.mf2.api.armour.CustomArmourEntry;
import minefantasy.mf2.api.tier.IToolMaterial;
import minefantasy.mf2.client.render.HudHandlerMF;
import minefantasy.mf2.item.armour.ArmourDesign;
import minefantasy.mf2.item.armour.ItemArmourMF;
import minefantasy.mf2.item.list.CustomArmourListMF;
import minefantasy.mf2.item.list.CustomToolListMF;
import minefantasy.mf2.item.titanite.handlers.BattleHandler;
import minefantasy.mf2.material.BaseMaterialMF;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.*;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;

public class BattleConfig {

    /*   5 > 30%, 10 > 60%
    *   15 > + 90%
    *
    *   Unique: Fire 10 Ice 10
    *   Unique: Chaos 5 Dark 5
    *   Unique: Damage 15 Lightning 10
    *
    *   Armour 10 > +20%
    *   Armour 15 > +20% elemental + 10% from 1 (10-15)
    *   Armour 5 > 10% all elemental
    *   Armour 5 > Fire 5 > Ice 5 > 10% all + 50% > 60% all + 85% ice and fire
    *   Holy resist only holy, dark resist holy and dark as 85% at each maximum and 65% of all elemental
    *   Chaos resist fire and chaos > 85% fire and 85% chaos, with 70% all elemental
    *   15 with lightning resist 70% all elemental, 90% damage and 80% lightning
    *
    *   Armour has unique additional defence
    *
    * */

    static protected Object[][] custom_data = {
            {CustomToolListMF.standard_katana.getUnlocalizedName(), new float[]{1f, 0f, 0f, 0f, 0f, 0f, 0f}, 2},
            // Custom item initial damage | total  | physical  | ice | fire | light | chaos | dark | holy
            {CustomToolListMF.standard_hoe.getUnlocalizedName(), new float[] { 13f,    0f,         0f,    1f,      0f,     1f,     0f,     0f}, 2},
    };

    static public float[] armourRates = { .5f, .7f, .65f, .4f};
    static public String[] colors = new String[]{String.valueOf(EnumChatFormatting.DARK_GREEN), String.valueOf(EnumChatFormatting.BLUE), String.valueOf(EnumChatFormatting.RED), String.valueOf(EnumChatFormatting.AQUA), String.valueOf(EnumChatFormatting.DARK_RED), String.valueOf(EnumChatFormatting.DARK_GRAY), String.valueOf(EnumChatFormatting.GRAY)};

    /*
     * 1 - armour
     * 2 - weapon
     * 3 - enchant
     * 4 - material
     * */
    int type;
    float[] values;

    public static void registerChangedValues(){
        ArmourDesignScalable.registerDesign(ArmourDesign.CLOTH,     new ArmourDesignScalable(0.2f,.2f,.1f,.5f,.0f,.0f,.0f)); // Ткань
        ArmourDesignScalable.registerDesign(ArmourDesign.SOLID,     new ArmourDesignScalable(0.7f,.3f,.4f,.2f,.0f,.0f,.4f)); // Стандартная
        ArmourDesignScalable.registerDesign(ArmourDesign.MAIL,      new ArmourDesignScalable(0.5f,.3f,.4f,.2f,.0f,.0f,.4f)); // Кольчуга
        ArmourDesignScalable.registerDesign(ArmourDesign.PLATE,     new ArmourDesignScalable(0.7f,.4f,.2f,.3f,.0f,.0f,.0f)); // Пластины
        ArmourDesignScalable.registerDesign(ArmourDesign.LEATHER,   new ArmourDesignScalable(0.3f,.4f,.2f,.5f,.0f,.0f,.0f)); // Кожа
        ArmourDesignScalable.registerDesign(ArmourDesign.PADDING,   new ArmourDesignScalable(0.3f,.3f,.1f,.4f,.0f,.0f,.0f)); // Набитая
        ArmourDesignScalable.registerDesign(ArmourDesign.STUDDED,   new ArmourDesignScalable(0.4f,.3f,.2f,.3f,.0f,.0f,.0f)); // Оббитая
        ArmourDesignScalable.registerDesign(ArmourDesign.SCALEMAIL, new ArmourDesignScalable(0.6f,.2f,.4f,.2f,.0f,.0f,.0f)); // Большая кольчуга
        ArmourDesignScalable.registerDesign(ArmourDesign.CHAINMAIL, new ArmourDesignScalable(0.5f,.3f,.3f,.2f,.0f,.0f,.0f)); // Обычная кольчуга
        ArmourDesignScalable.registerDesign(ArmourDesign.SPLINTMAIL,new ArmourDesignScalable(0.5f,.2f,.3f,.3f,.0f,.0f,.0f)); // Полукольчуга
        ArmourDesignScalable.registerDesign(ArmourDesign.FIELDPLATE,new ArmourDesignScalable(0.8f,.3f,.5f,.3f,.0f,.0f,.0f)); // Полноплитная
        ArmourDesignScalable.registerDesign(ArmourDesign.COGWORK,   new ArmourDesignScalable(0.9f,.3f,.1f,.5f,.0f,.0f,.0f)); // Механическая

        ArmourDesignScalable.registerMaterial("BlackSteelWeak", new MaterialScales(.5f, .5f, .5f, .2f, .0f, .0f));
        ArmourDesignScalable.registerMaterial("RedSteelWeak",   new MaterialScales(.5f, .5f, .5f, .0f, .2f, .0f));
        ArmourDesignScalable.registerMaterial("BlueSteelWeak",  new MaterialScales(.5f, .5f, .5f, .0f, .0f, .2f));
        ArmourDesignScalable.registerMaterial("Tin",            new MaterialScales(.5f, .5f, .5f, .05f,.05f,.05f));
        ArmourDesignScalable.registerMaterial("PigIron",        new MaterialScales(.4f, .4f, .4f, .0f, .0f, .0f));
        ArmourDesignScalable.registerMaterial("Silver",         new MaterialScales(.5f, .5f, .5f, .0f, .2f, .0f));
        ArmourDesignScalable.registerMaterial("Gold",           new MaterialScales(.3f, .3f, .3f, .0f, .0f, .2f));
        ArmourDesignScalable.registerMaterial("Ornate",         new MaterialScales(.3f, .3f, .3f, .0f, .0f, .0f));
        ArmourDesignScalable.registerMaterial("Tungsten",       new MaterialScales(.7f, .7f, .7f, .2f, .0f, .0f));
        ArmourDesignScalable.registerMaterial("Copper",         new MaterialScales(.5f, .6f, .4f, .0f, .0f, .0f));
        ArmourDesignScalable.registerMaterial("Bronze",         new MaterialScales(.6f, .6f, .8f, .1f, .0f, .0f));
        ArmourDesignScalable.registerMaterial("Iron",           new MaterialScales(.6f, .7f, .7f, .0f, .0f, .0f));
        ArmourDesignScalable.registerMaterial("Steel",          new MaterialScales(.8f, .7f, .8f, .0f, .0f, .0f));
        ArmourDesignScalable.registerMaterial("Encrusted",      new MaterialScales(.7f, .8f, 1f,  .1f, .2f, .2f));
        ArmourDesignScalable.registerMaterial("Obsidian",       new MaterialScales(.6f, 1f,  .7f, .4f, .0f, .0f));
        ArmourDesignScalable.registerMaterial("BlackSteel",     new MaterialScales(.9f, .9f, .9f, .4f, .1f, .1f));
        ArmourDesignScalable.registerMaterial("Dragonforge",    new MaterialScales(.8f, .8f, .8f, .4f, .3f, .3f));
        ArmourDesignScalable.registerMaterial("RedSteel",       new MaterialScales(1f,  1f,  1f,  .5f, .3f, .3f));
        ArmourDesignScalable.registerMaterial("BlueSteel",      new MaterialScales(1f,  1f,  1f,  .4f, .4f, .4f));
        ArmourDesignScalable.registerMaterial("Adamantium",     new MaterialScales(1f,  1.2f,1.2f,.5f, .3f, .3f));
        ArmourDesignScalable.registerMaterial("Mithril",        new MaterialScales(1.3f,1.1f,1.1f,.5f, .3f, .4f));
        ArmourDesignScalable.registerMaterial("Ignotumite",     new MaterialScales(1.4f,1.5f,1.5f,.5f, .5f, .5f));
        ArmourDesignScalable.registerMaterial("Mithium",        new MaterialScales(1.4f,1.5f,1.5f,.5f, .4f, .6f));
        ArmourDesignScalable.registerMaterial("Ender",          new MaterialScales(1.5f,1.5f,1.5f,.6f, .6f, .4f));
        ArmourDesignScalable.registerMaterial("CompositeAlloy", new MaterialScales(.9f, .8f, 1f,  .2f, .1f, .1f));

        ToolDesignScalable.registerMaterial("BlackSteelWeak", new MaterialScales(.3f));
        ToolDesignScalable.registerMaterial("RedSteelWeak", new MaterialScales(.3f));
        ToolDesignScalable.registerMaterial("BlueSteelWeak", new MaterialScales(.3f));
        ToolDesignScalable.registerMaterial("Tin", new MaterialScales(.4f));
        ToolDesignScalable.registerMaterial("PigIron", new MaterialScales(.6f));
        ToolDesignScalable.registerMaterial("Silver", new MaterialScales(.4f, 0f, 0f, 0f, 0f, 0f, .3f));
        ToolDesignScalable.registerMaterial("Gold", new MaterialScales(.3f, 0f, 0f, 0f, 0f, 0f, .3f));
        ToolDesignScalable.registerMaterial("Ornate", new MaterialScales(.5f));
        ToolDesignScalable.registerMaterial("Tungsten", new MaterialScales(1f));
        ToolDesignScalable.registerMaterial("Copper", new MaterialScales(.4f));
        ToolDesignScalable.registerMaterial("Bronze", new MaterialScales(.6f));
        ToolDesignScalable.registerMaterial("Iron", new MaterialScales(.7f));
        ToolDesignScalable.registerMaterial("Steel", new MaterialScales(1.1f));
        ToolDesignScalable.registerMaterial("Encrusted", new MaterialScales(1f));
        ToolDesignScalable.registerMaterial("Obsidian", new MaterialScales(1f));
        ToolDesignScalable.registerMaterial("BlackSteel", new MaterialScales(1.2f));
        ToolDesignScalable.registerMaterial("Dragonforge", new MaterialScales(1.5f));
        ToolDesignScalable.registerMaterial("RedSteel", new MaterialScales(1.5f));
        ToolDesignScalable.registerMaterial("BlueSteel", new MaterialScales(1.75f));
        ToolDesignScalable.registerMaterial("Adamantium", new MaterialScales(1.7f));
        ToolDesignScalable.registerMaterial("Mithril", new MaterialScales(1.9f));
        ToolDesignScalable.registerMaterial("Ignotumite", new MaterialScales(2f));
        ToolDesignScalable.registerMaterial("Mithium", new MaterialScales(2.1f));
        ToolDesignScalable.registerMaterial("Ender", new MaterialScales(2f));
        ToolDesignScalable.registerMaterial("CompositeAlloy", new MaterialScales(1.3f));

        ToolDesignScalable.registerTool(CustomToolListMF.standard_sword,    new ToolDesignScalable(8f).setBase(5));
        ToolDesignScalable.registerTool(CustomToolListMF.standard_waraxe,   new ToolDesignScalable(6f).setBase(6));
        ToolDesignScalable.registerTool(CustomToolListMF.standard_mace,     new ToolDesignScalable(5f).setBase(6));
        ToolDesignScalable.registerTool(CustomToolListMF.standard_dagger,   new ToolDesignScalable(5f).setBase(4));
        ToolDesignScalable.registerTool(CustomToolListMF.standard_spear,    new ToolDesignScalable(6f).setBase(7));

        ToolDesignScalable.registerTool(CustomToolListMF.standard_greatsword,   new ToolDesignScalable(7.5f).setBase(7));
        ToolDesignScalable.registerTool(CustomToolListMF.standard_battleaxe,    new ToolDesignScalable(5f).setBase(7));
        ToolDesignScalable.registerTool(CustomToolListMF.standard_warhammer,    new ToolDesignScalable(9f).setBase(7));
        ToolDesignScalable.registerTool(CustomToolListMF.standard_katana,       new ToolDesignScalable(8.5f).setBase(7));
        ToolDesignScalable.registerTool(CustomToolListMF.standard_halbeard,     new ToolDesignScalable(8.5f).setBase(7));
        ToolDesignScalable.registerTool(CustomToolListMF.standard_lance,        new ToolDesignScalable(7f).setBase(7));

        ToolDesignScalable.registerTool(CustomToolListMF.standard_pick,       new ToolDesignScalable(4f).setBase(3));
        ToolDesignScalable.registerTool(CustomToolListMF.standard_axe,        new ToolDesignScalable(5f).setBase(3));
        ToolDesignScalable.registerTool(CustomToolListMF.standard_spade,      new ToolDesignScalable(3f).setBase(3));
        ToolDesignScalable.registerTool(CustomToolListMF.standard_hoe,        new ToolDesignScalable(2f).setBase(2));
        ToolDesignScalable.registerTool(CustomToolListMF.standard_hvypick,    new ToolDesignScalable(4f).setBase(5));
        ToolDesignScalable.registerTool(CustomToolListMF.standard_hvyshovel,  new ToolDesignScalable(4f).setBase(4));
        ToolDesignScalable.registerTool(CustomToolListMF.standard_handpick,   new ToolDesignScalable(2f).setBase(2));
        ToolDesignScalable.registerTool(CustomToolListMF.standard_trow,       new ToolDesignScalable(2f).setBase(3));
        ToolDesignScalable.registerTool(CustomToolListMF.standard_scythe,     new ToolDesignScalable(7f).setBase(7));
        ToolDesignScalable.registerTool(CustomToolListMF.standard_mattock,    new ToolDesignScalable(4f).setBase(4));
        ToolDesignScalable.registerTool(CustomToolListMF.standard_lumber,     new ToolDesignScalable(3f).setBase(6));

        ToolDesignScalable.registerTool(CustomToolListMF.standard_lumber,     new ToolDesignScalable(5f).setBase(5));
        ToolDesignScalable.registerTool(CustomToolListMF.standard_hvyhammer,  new ToolDesignScalable(6f).setBase(6));
        ToolDesignScalable.registerTool(CustomToolListMF.standard_tongs,      new ToolDesignScalable(3f).setBase(3));
        ToolDesignScalable.registerTool(CustomToolListMF.standard_shears,     new ToolDesignScalable(1f).setBase(1));
        ToolDesignScalable.registerTool(CustomToolListMF.standard_knife,      new ToolDesignScalable(5f).setBase(3));
        ToolDesignScalable.registerTool(CustomToolListMF.standard_needle,     new ToolDesignScalable(1f).setBase(1));
        ToolDesignScalable.registerTool(CustomToolListMF.standard_saw,        new ToolDesignScalable(2f).setBase(1));
        ToolDesignScalable.registerTool(CustomToolListMF.standard_spanner,    new ToolDesignScalable(2f).setBase(2));
        ToolDesignScalable.registerTool(CustomToolListMF.standard_spoon,      new ToolDesignScalable(1f).setBase(0));
        ToolDesignScalable.registerTool(CustomToolListMF.standard_mallet,     new ToolDesignScalable(2f).setBase(1));

        ToolDesignScalable.debug();
//        if(ToolDesignScalable.hasDesign(new ItemStack(CustomToolListMF.standard_saw)))System.out.println("TEST 1 PASSED");
        if(ArmourDesignScalable.hasDesign(new ItemStack(CustomArmourListMF.standard_scale_helmet)))System.out.println("TEST 2 PASSED");

    }

    public static String getFormatResString(int id){
        return new String[]{"Physical damage reduction %.2f", "Ice resistance %.2f", "Fire resistance %.2f", "Lightning resistance %.2f", "Chaos resistance %.2f", "Dark resistance %.2f", "Holy resistance %.2f"}[id];
    }

    public static String getResColor(int id){
        return new String[]{String.valueOf(EnumChatFormatting.DARK_GREEN), String.valueOf(EnumChatFormatting.BLUE), String.valueOf(EnumChatFormatting.RED), String.valueOf(EnumChatFormatting.AQUA), String.valueOf(EnumChatFormatting.DARK_RED), String.valueOf(EnumChatFormatting.DARK_GRAY), String.valueOf(EnumChatFormatting.GRAY)}[id];
    }


    public static float getTotalProtection(EntityLivingBase user, int id) {
        float armourDT = 0;
        for (int a = 0; a < 4; a++) {
            ItemStack armour = user.getEquipmentInSlot(a + 1);
            if (armour != null && armour.getItem() instanceof ItemArmourMF) {
                float threshold = getResistance(armour,((ItemArmourMF)armour.getItem()).design, id) * ((ItemArmourMF) armour.getItem()).scalePiece();
                armourDT += id == 0 ? threshold * 4 : threshold;
            }
        }
        return armourDT;
    }

    public static float protect(float[] DAMAGE, float[] ARMOURS, int protectionLevel){
        // physical
        double damage = Math.max((DAMAGE[0] - ARMOURS[0]), 0) * (1 - Math.min(ARMOURS[0]/21,.95)) / (protectionLevel + 1);
        //System.out.printf( "[%f] :%f:\\%f\\ =%s= =%s= \n", damage, (DAMAGE[0] - ARMOURS[0]), (1 - Math.min(ARMOURS[0]/21,.95)), Arrays.toString(DAMAGE), Arrays.toString(ARMOURS) );

        for(int i=1;i<DAMAGE.length;i++){
            damage += DAMAGE[i] * (1 - Math.min(ARMOURS[i], .95)) / (1+protectionLevel);
        }
        System.out.println(damage);
        return (float) damage;
    }

    public static float getResistance(ItemStack scaled, ArmourDesign ad, int resistanceType){
        ArmourMaterialMF material = ArmourDesignScalable.getMaterial(scaled);
        switch(resistanceType){
            case 0:
            return material == null ? ad.getTraits(resistanceType) : material.baseAR * ad.getTraits(0);
            case 1: case 2: case 3:
                return material == null ? ad.getTraits(resistanceType) : material.resistances[resistanceType-1] * ad.getTraits(resistanceType);
            case 4: case 5: case 6:
                return material == null ? ad.getTraits(resistanceType) : material.resistances[resistanceType-1] + ad.getTraits(resistanceType);
            default:
                return 0f;
        }
    }

    public static BaseMaterialMF getMaterial(ItemStack item, boolean h){
        String materialName = item.hasTagCompound() && item.getTagCompound().hasKey("MF_CustomMaterials")  && item.getTagCompound().getCompoundTag("MF_CustomMaterials").hasKey("main_metal") ?
                item.getTagCompound().getCompoundTag("MF_CustomMaterials").getString("main_metal") : "";
        if(materialName.equals("") || BaseMaterialMF.getMaterial(materialName) == null ) return null;
        return BaseMaterialMF.getMaterial(materialName);
    }

    public static void registerHandlers() {
        MinecraftForge.EVENT_BUS.register(new HudHandlerMF());
        MinecraftForge.EVENT_BUS.register(new BattleHandler());
    }


    public float[] getValues() {
        return values;
    }

    public int getType() {
        return type;
    }
}
