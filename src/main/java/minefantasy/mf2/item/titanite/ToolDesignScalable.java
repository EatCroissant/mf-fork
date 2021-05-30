package minefantasy.mf2.item.titanite;

import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.tier.IToolMaterial;
import minefantasy.mf2.item.weapon.ItemWeaponMF;
import minefantasy.mf2.material.BaseMaterialMF;
import net.minecraft.item.*;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ToolDesignScalable extends MaterialScales{
    float physical_base;
    Item.ToolMaterial material;

    static HashMap<Item, ToolDesignScalable> tools = new HashMap<>();
    static HashMap<Item.ToolMaterial, MaterialScales> materials = new HashMap<>();

    public ToolDesignScalable(float physical){
        super(0,0,0,0,0,0);
        this.physical = physical;
    }

    public ToolDesignScalable setBase(float physical){
        this.physical_base = physical;
        return this;
    }

    public void setMaterial(Item.ToolMaterial material) {
        this.material = material;
    }

    static { }

    public static boolean isTool(Item i){
        return i instanceof ItemShears || i instanceof ItemHoe || i instanceof ItemTool || i instanceof ItemWeaponMF;
    }

    static Item.ToolMaterial getMaterial(ItemStack itemStack){
        return itemStack == null || itemStack.getItem() == null ? null :CustomToolHelper.getCustomPrimaryMaterial(itemStack) != null && BaseMaterialMF.getMaterial( CustomToolHelper.getCustomPrimaryMaterial(itemStack).name) != null ?
                        BaseMaterialMF.getMaterial( CustomToolHelper.getCustomPrimaryMaterial(itemStack).name).getToolConversion() :
                itemStack.getItem() instanceof IToolMaterial ?
                                ((IToolMaterial) itemStack.getItem()).getMaterial() :
                                itemStack.getItem() instanceof ItemTool ? ((ItemTool) itemStack.getItem()).func_150913_i() : null;
    }

    public static boolean hasDesign(ItemStack undefinedItemStack){
        try {
            if (undefinedItemStack != null && undefinedItemStack.getItem() == null || !isTool(undefinedItemStack.getItem()))
                return false;
            Item item = undefinedItemStack.getItem();
            Item.ToolMaterial material = getMaterial(undefinedItemStack);
            // OK > design found, searching material
            if (material == null) return false;
            MaterialScales scales = materials.get(material);
            ToolDesignScalable scalable = tools.get(item);

            return scales != null && scalable != null;
        }catch (NullPointerException npe){
            return false;
        }
    }

    public static MaterialScales getMaterialScales(ItemStack tool){
        Item.ToolMaterial material = getMaterial(tool);
        return materials.get(material);
    }

    public static ToolDesignScalable getToolDesignScalable(ItemStack tool){
        Item item = tool.getItem();
        return tools.get(item);
    }

    public static float[] designScales(ItemStack i){
        if(i == null || i.getItem() == null)return emptyScale.getRates();
        Item item = i.getItem();
        Item.ToolMaterial material = getMaterial(i);
        MaterialScales scales = materials.get(material);
        ToolDesignScalable scalable = tools.get(item);
        return scalable.scales(scales, i);
    }

    float[] scales(MaterialScales scalable){
        return new float[]{this.physical_base + this.physical * scalable.physical, this.ice * scalable.ice, this.fire * scalable.fire, this.lightning * scalable.lightning, this.chaos+ scalable.chaos, this.dark + scalable.dark, this.holy + scalable.holy  };
    }

    float[] scales(MaterialScales scalable, ItemStack tool){
        float[] addedDamage = new ItemTitanite.NBTTitanite(tool).getAddedDamage(physical);
        return new float[]{
                this.physical_base + this.physical * scalable.physical + addedDamage[0],
                this.ice * scalable.ice + addedDamage[1],
                this.fire * scalable.fire + addedDamage[2],
                this.lightning * scalable.lightning + addedDamage[3],
                this.chaos + scalable.chaos + addedDamage[4],
                this.dark + scalable.dark + addedDamage[5],
                this.holy + scalable.holy + addedDamage[6]
        };
    }

    private float[] scales(MaterialScales scalable, ItemTitanite.NBTTitanite titanite){
        float[] addedDamage = titanite.getAddedDamage(physical);
        return new float[]{this.physical_base + this.physical * scalable.physical + addedDamage[0], this.ice * scalable.ice + addedDamage[1], this.fire * scalable.fire + addedDamage[2], this.lightning * scalable.lightning + addedDamage[3], this.chaos + scalable.chaos + addedDamage[4], this.dark + scalable.dark + addedDamage[5], this.holy + scalable.holy + addedDamage[6]  };
    }

    public static String[] getFormattedString(ItemStack tool){
        //StringBuilder info = new StringBuilder();
        ArrayList<String> info = new ArrayList<String>(0);
        if(tool == null || !hasDesign(tool)) return new String[0];
        ToolDesignScalable toolDesignScalable = getToolDesignScalable(tool);
        MaterialScales materialScales = getMaterialScales(tool);
        float[] originalScales = toolDesignScalable.scales(materialScales);
        float[] scaledCharacteristics = toolDesignScalable.scales(materialScales, tool);
        float[] addedScales = scaledCharacteristics.clone();

        for(int i = 0; i < addedScales.length; i++) {
            addedScales[i] -= originalScales[i];
            if(scaledCharacteristics[i] >= .99f){
                String[] list = {
                        "attribute.weapon.damage.dam",
                        "attribute.weapon.damage.ice",
                        "attribute.weapon.damage.fire",
                        "attribute.weapon.damage.lightning",
                        "attribute.weapon.damage.chaos",
                        "attribute.weapon.damage.dark",
                        "attribute.weapon.damage.holy",
                };

                if(addedScales[i]>=1)info.add(StatCollector.translateToLocal(list[i]) + String.format("%s%.0f%s(+%.0f)%s ", BattleConfig.colors[i], originalScales[i], EnumChatFormatting.GREEN, addedScales[i], EnumChatFormatting.RESET));
                else info.add(StatCollector.translateToLocal(list[i]) +String.format("%s%.0f", BattleConfig.colors[i], originalScales[i]));

            }

        }

        return info.toArray(new String[0]);
    }

    public static void registerTool(Item tool, ToolDesignScalable stats){
        tools.put(tool, stats);
    }

    public static void registerMaterial(Item.ToolMaterial material, MaterialScales stats){
        materials.put(material, stats);
    }
    public static void registerMaterial(String material, MaterialScales stats){
        Item.ToolMaterial mat = BaseMaterialMF.getMaterial(material).getToolConversion();
        if(mat != null) {
            materials.put(mat, stats);
        } else System.out.println("failed to register material "+material);
    }

    public static boolean isUpgraded(ItemStack weapon) {
        if(weapon != null && weapon.getItem() != null && hasDesign(weapon)){
            ItemTitanite.NBTTitanite titantine = new ItemTitanite.NBTTitanite(weapon);
            return titantine.level != 0 || titantine.unique ;
        }
        return false;
    }

    public static void debug(){
        for(Map.Entry<Item, ToolDesignScalable> p: tools.entrySet()){
            for(Map.Entry<Item.ToolMaterial, MaterialScales> d: materials.entrySet()){
                for(ItemTitanite.NBTTitanite titanite : ItemTitanite.NBTTitanite.constructArrayTopTitanite()){
                    MaterialScales mat = d.getValue();
                    ToolDesignScalable des = p.getValue();

                    StringBuilder info = new StringBuilder();
                    float[] originalScales = des.scales(mat);
                    float[] scaledCharacteristics = des.scales(mat, titanite);
                    float[] addedScales = originalScales.clone();
                    for(int i = 0; i < addedScales.length; i++) {
                        addedScales[i] -= scaledCharacteristics[i];
                        info.append(String.format("%.0f(+%.0f) ", originalScales[i], addedScales[i]));
                    }

                    System.out.println( info.toString() );
                }
            }
        }
    }
}
