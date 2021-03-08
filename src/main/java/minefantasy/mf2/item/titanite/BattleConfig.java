package minefantasy.mf2.item.titanite;

import com.google.common.primitives.Floats;
import minefantasy.mf2.api.armour.ArmourMaterialMF;
import minefantasy.mf2.item.ItemTitanite;
import minefantasy.mf2.item.armour.ArmourDesign;
import minefantasy.mf2.item.armour.ItemArmourMF;
import minefantasy.mf2.item.list.CustomToolListMF;
import minefantasy.mf2.item.tool.ItemHoeMF;
import minefantasy.mf2.material.BaseMaterialMF;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.*;
import net.minecraft.util.EnumChatFormatting;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;

public class BattleConfig {
    /*
    *   Armour and weapon configuration. Available types
    *   Damage, Ice, Fire, Lightning, Chaos, Dark, Holy
    *
    *   Upgrade pathes:
    *   Damage 5 > Fire 5(10) > Chaos
    *   Damage 5 > Lightning 10
    *   Damage 5 > Ice 10
    *   Damage 5 > Holy 5(10) > Dark 5
    *   Damage 15
    *
    *   5 > 30%, 10 > 60%
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

    static HashMap<String, BattleConfig> changedValues = new HashMap<>();
    static HashMap<String, Float> materialSharpness = new HashMap<>();
    static HashMap<String, BattleConfig> toolSharpness= new HashMap<>();


    static protected Object[][] custom_data = {
            {CustomToolListMF.standard_katana.getUnlocalizedName(), new float[]{1f, 0f, 0f, 0f, 0f, 0f, 0f}, 2},
            /*  points*D*T  p*D+p*T    */
            /* Damage,  Ice,    Fire,Lightning, Chaos,  Dark,   Holy */
            {ArmourDesign.CLOTH,    new float[]{    0.2f,   .2f,    .1f,    .5f,    .0f,    .0f,    .0f }, 1}, // Ткань
            {ArmourDesign.SOLID,    new float[]{    0.7f,   .3f,    .4f,    .2f,    .0f,    .0f,    .4f }, 1}, // Стандартная
            {ArmourDesign.MAIL,     new float[]{    0.5f,   .3f,    .4f,    .2f,    .0f,    .0f,    .4f }, 1}, // Кольчуга
            {ArmourDesign.PLATE,    new float[]{    0.7f,   .4f,    .2f,    .3f,    .0f,    .0f,    .0f }, 1}, // Пластины
            {ArmourDesign.LEATHER,  new float[]{    0.3f,   .4f,    .2f,    .5f,    .0f,    .0f,    .0f }, 1}, // Кожа
            {ArmourDesign.PADDING,  new float[]{    0.3f,   .3f,    .1f,    .4f,    .0f,    .0f,    .0f }, 1}, // Набитая
            {ArmourDesign.STUDDED,  new float[]{    0.4f,   .3f,    .2f,    .3f,    .0f,    .0f,    .0f }, 1}, // Оббитая
            {ArmourDesign.SCALEMAIL,new float[]{    0.6f,   .2f,    .4f,    .2f,    .0f,    .0f,    .0f }, 1}, // Большая кольчуга
            {ArmourDesign.CHAINMAIL,new float[]{    0.5f,   .3f,    .3f,    .2f,    .0f,    .0f,    .0f }, 1}, // Обычная кольчуга
            {ArmourDesign.SPLINTMAIL,new float[]{   0.5f,   .2f,    .3f,    .3f,    .0f,    .0f,    .0f }, 1}, // Полукольчуга
            {ArmourDesign.FIELDPLATE,new float[]{   0.8f,   .3f,    .5f,    .3f,    .0f,    .0f,    .0f }, 1}, // Полноплитная
            {ArmourDesign.COGWORK,  new float[]{    0.9f,   .3f,    .1f,    .5f,    .0f,    .0f,    .0f }, 1}, // Механическая
                                                                    /*  physical ice fire light chaos dark holy*/
            {BaseMaterialMF.getMaterial("BlackSteelWeak"),  new float[]{   .5f, .5f, .5f, .2f, .0f, .0f }, 4}, // 0 тир
            {BaseMaterialMF.getMaterial("RedSteelWeak"),    new float[]{   .5f, .5f, .5f, .0f, .2f, .0f }, 4}, // 0 тир
            {BaseMaterialMF.getMaterial("BlueSteelWeak"),   new float[]{   .5f, .5f, .5f, .0f, .0f, .2f }, 4}, // 0 тир
            {BaseMaterialMF.getMaterial("Tin"),             new float[]{   .5f, .5f, .5f, .05f, .05f, .05f }, 4}, // 0 тир
            {BaseMaterialMF.getMaterial("PigIron"),         new float[]{   .4f, .4f, .4f, .0f, .0f, .0f }, 4}, // 0 тир
            {BaseMaterialMF.getMaterial("Silver"),          new float[]{   .5f, .5f, .5f, .0f, .2f, .0f }, 4}, // 0 тир
            {BaseMaterialMF.getMaterial("Gold"),            new float[]{   .3f, .3f, .3f, .0f, .0f, .2f }, 4}, // 0 тир
            {BaseMaterialMF.getMaterial("Ornate"),          new float[]{   .3f, .3f, .3f, .0f, .0f, .0f }, 4}, // 0 тир
            {BaseMaterialMF.getMaterial("Tungsten"),        new float[]{   .7f, .7f, .7f, .2f, .0f, .0f }, 4}, // 2 тир
            {BaseMaterialMF.getMaterial("Copper"),          new float[]{   .5f, .6f, .4f, .0f, .0f, .0f }, 4}, // 0 тир
            {BaseMaterialMF.getMaterial("Bronze"),          new float[]{   .6f, .6f, .8f, .1f, .0f, .0f }, 4}, // 1 тир
            {BaseMaterialMF.getMaterial("Iron"),            new float[]{   .6f, .7f, .7f, .0f, .0f, .0f }, 4}, // 2 тир
            {BaseMaterialMF.getMaterial("Steel"),           new float[]{   .8f, .7f, .8f, .0f, .0f, .0f }, 4}, // 3 тир
            {BaseMaterialMF.getMaterial("Encrusted"),       new float[]{   .7f, .8f,  1f, .1f, .2f, .2f }, 4}, // 3 тир
            {BaseMaterialMF.getMaterial("Obsidian"),        new float[]{   .6f,  1f, .7f, .4f, .0f, .0f }, 4}, // 3 тир
            {BaseMaterialMF.getMaterial("BlackSteel"),      new float[]{   .9f, .9f, .9f, .4f, .1f, .1f }, 4}, // 4 тир
            {BaseMaterialMF.getMaterial("Dragonforge"),     new float[]{   .8f, .8f, .8f, .4f, .3f, .3f }, 4}, // 4 тир
            {BaseMaterialMF.getMaterial("RedSteel"),        new float[]{   1f,   1f,  1f, .5f, .3f, .3f }, 4}, // 5 тир
            {BaseMaterialMF.getMaterial("BlueSteel"),       new float[]{   1f,   1f,  1f, .4f, .4f, .4f }, 4}, // 5 тир
            {BaseMaterialMF.getMaterial("Adamantium"),      new float[]{   1f,  1.2f, 1.2f, .5f, .3f, .3f }, 4}, // 6 тир
            {BaseMaterialMF.getMaterial("Mithril"),         new float[]{   1.3f,1.1f, 1.1f, .5f, .3f, .4f }, 4}, // 6 тир
            {BaseMaterialMF.getMaterial("Ignotumite"),      new float[]{   1.4f,1.5f, 1.5f, .5f, .5f, .5f }, 4}, // 7 тир
            {BaseMaterialMF.getMaterial("Mithium"),         new float[]{   1.4f,1.5f, 1.5f, .5f, .4f, .6f }, 4}, // 7 тир
            {BaseMaterialMF.getMaterial("Ender"),           new float[]{   1.5f,1.5f, 1.5f, .6f, .6f, .4f }, 4}, // 7 тир
            {BaseMaterialMF.getMaterial("CompositeAlloy"),  new float[]{   .9f, .8f, 1f,  .2f, .1f, .1f }, 4}, // 4 тир

            {BaseMaterialMF.getToolMaterial("BlackSteelWeak"),  .3f,    2}, // 0 тир
            {BaseMaterialMF.getToolMaterial("RedSteelWeak"),    .3f,    2}, // 0 тир
            {BaseMaterialMF.getToolMaterial("BlueSteelWeak"),   .3f,    2}, // 0 тир
            {BaseMaterialMF.getToolMaterial("Tin"),             .4f,    2}, // 0 тир
            {BaseMaterialMF.getToolMaterial("PigIron"),         .6f,    2}, // 0 тир
            {BaseMaterialMF.getToolMaterial("Silver"),          .4f,    2}, // 0 тир
            {BaseMaterialMF.getToolMaterial("Gold"),            .3f,    2}, // 0 тир
            {BaseMaterialMF.getToolMaterial("Ornate"),          .5f,    2}, // 0 тир
            {BaseMaterialMF.getToolMaterial("Tungsten"),        1f,     2}, // 2 тир
            {BaseMaterialMF.getToolMaterial("Copper"),          .4,     2}, // 0 тир
            {BaseMaterialMF.getToolMaterial("Bronze"),          .6f,    2}, // 1 тир
            {BaseMaterialMF.getToolMaterial("Iron"),            .7f,    2}, // 2 тир
            {BaseMaterialMF.getToolMaterial("Steel"),           1.1f,   2}, // 3 тир
            {BaseMaterialMF.getToolMaterial("Encrusted"),       1f,     2}, // 3 тир
            {BaseMaterialMF.getToolMaterial("Obsidian"),        1f,     2}, // 3 тир
            {BaseMaterialMF.getToolMaterial("BlackSteel"),      1.2f,   2}, // 4 тир
            {BaseMaterialMF.getToolMaterial("Dragonforge"),     1.5f,   2}, // 4 тир
            {BaseMaterialMF.getToolMaterial("RedSteel"),        1.5f,   2}, // 5 тир
            {BaseMaterialMF.getToolMaterial("BlueSteel"),       1.75f,  2}, // 5 тир
            {BaseMaterialMF.getToolMaterial("Adamantium"),      1.7,    2}, // 6 тир
            {BaseMaterialMF.getToolMaterial("Mithril"),         1.9f,   2}, // 6 тир
            {BaseMaterialMF.getToolMaterial("Ignotumite"),      2f,     2}, // 7 тир
            {BaseMaterialMF.getToolMaterial("Mithium"),         2.1f,   2}, // 7 тир
            {BaseMaterialMF.getToolMaterial("Ender"),           2f,     2}, // 7 тир
            {BaseMaterialMF.getToolMaterial("CompositeAlloy"),  1.3f,   2}, // 4 тир
            // Custom item initial damage | total  | physical  | ice | fire | light | chaos | dark | holy
            {CustomToolListMF.standard_hoe.getUnlocalizedName(), new float[] { 13f,    0f,         0f,    1f,      0f,     1f,     0f,     0f}, 2}


    };

    static public float[] armourRates = { .5f, .7f, .65f, .4f};


    /*
     * 1 - armour
     * 2 - weapon
     * 3 - enchant
     * 4 - material
     * */
    int type;
    float[] values;

    public static void registerChangedValues(){
        for(Object[] arr: custom_data){
            int type = ((Integer)arr[2]);
            if(arr[0] instanceof String && type == 2 && arr[1] instanceof float[] && ((float[]) arr[1]).length == 7){
                registerItem((String)arr[0], (float[])arr[1], (int)arr[2]);
            }
            else if(arr[0] instanceof ArmourDesign && type == 1){
                ((ArmourDesign)arr[0]).upd((float[])arr[1]);
            }
            else if(arr[0] instanceof BaseMaterialMF){
                switch (type){
                    case 4:
                        ((BaseMaterialMF)arr[0]).setElemental((float[])arr[1]);
                        continue;
                    case 2:
                        materialSharpness.put(((BaseMaterialMF)arr[0]).name, (float)arr[1]);
                        continue;
                    default:
                }
            }
            else if(arr[0] instanceof String && type == 2 && arr[1] instanceof float[] && ((float[]) arr[1]).length == 8){
                toolSharpness.put(((String)arr[0]), new BattleConfig(2, new float[]{
                        ((float[])arr[1])[0] * ((float[])arr[1])[1],
                        ((float[])arr[1])[0] * ((float[])arr[1])[2],
                        ((float[])arr[1])[0] * ((float[])arr[1])[3],
                        ((float[])arr[1])[0] * ((float[])arr[1])[4],
                        ((float[])arr[1])[0] * ((float[])arr[1])[5],
                        ((float[])arr[1])[0] * ((float[])arr[1])[6],
                        ((float[])arr[1])[0] * ((float[])arr[1])[7],
                }));
            }
        }
        ArmourDesign.show();
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

    public static float getResistance(ItemStack scaled, ArmourDesign ad, int resistanceType){
        ArmourMaterialMF material = getMaterial(scaled);
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

    public static float[] getAttackRates(String name){
        return toolSharpness.get(name) != null ? toolSharpness.get(name).values : changedValues.get(name) != null ? changedValues.get(name).values : null;
    }

    public static boolean isToolTitanable(Item i){
        return (i instanceof ItemHoe) || (i instanceof ItemPickaxe) || (i instanceof ItemAxe) || (i instanceof ItemShears) || (i instanceof ItemHoe) || ( i instanceof ItemTool) || (i instanceof ItemSpade) || (i instanceof ItemArmourMF) || (i instanceof ItemArmor);
    }

    public static float[] getAttackRates(ItemStack itemStack){
        Item item = itemStack.getItem();
        ItemTitanite.NBTTitanite titanite = new ItemTitanite.NBTTitanite(itemStack);
        float[] rates = getAttackRates(item.getUnlocalizedName());
        if(rates == null) {
            return null;
        } else {
            rates = rates.clone();
        }
        float damageRate = Floats.max(rates);
        float[] added_rates = titanite.getAddedDamage(damageRate);
        for(int i=0;i<added_rates.length;i++) rates[i] += added_rates[i];
        return rates;
    }

    public static ArmourMaterialMF getMaterial(ItemStack item){
        String materialName = item.hasTagCompound() && item.getTagCompound().hasKey("MF_CustomMaterials")  && item.getTagCompound().getCompoundTag("MF_CustomMaterials").hasKey("main_metal") ?
                item.getTagCompound().getCompoundTag("MF_CustomMaterials").getString("main_metal") : "";
        if(materialName.equals("") || BaseMaterialMF.getMaterial(materialName) == null ) return null;
        return BaseMaterialMF.getMFArmourMaterial(BaseMaterialMF.getMaterial(materialName));
    }

    public float[] getValues() {
        return values;
    }

    public int getType() {
        return type;
    }

    public static void registerItem(Item i, float values[], int type){
        changedValues.put(i.getUnlocalizedName(), new BattleConfig(type, values));
    }

    public static void registerItem(String id, float values[], int type){
        changedValues.put(id, new BattleConfig(type, values));
    }

    BattleConfig(int type, float[] values) {
        this.type = type;
        this.values = values;
    }

    public static BattleConfig getConfig(String id){
        return changedValues.get(id);
    }

    @Nullable
    public static BattleConfig getConfig(Item i){
        return changedValues.get(i.getUnlocalizedName());
    }



}
