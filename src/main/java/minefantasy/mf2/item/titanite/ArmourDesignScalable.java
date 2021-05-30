package minefantasy.mf2.item.titanite;

import minefantasy.mf2.api.armour.ArmourMaterialMF;
import minefantasy.mf2.item.armour.ArmourDesign;
import minefantasy.mf2.item.armour.ItemArmourMF;
import minefantasy.mf2.item.armour.ItemCustomArmour;
import minefantasy.mf2.material.BaseMaterialMF;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.util.HashMap;

public class ArmourDesignScalable extends MaterialScales{
    private static float armourBaseScale = 4;
    static float[] emptyRates = {0, 0, 0, 0, 0, 0, 0};

    ArmourMaterialMF material;
    /* changed from ArmourMaterialMF to String*/
    static HashMap<ArmourDesign, ArmourDesignScalable> designes = new HashMap<>();
    static HashMap<String, MaterialScales> materials = new HashMap<>();

    ArmourDesignScalable(float physical, float ice, float fire, float lightning, float chaos, float dark, float holy){
        super(physical, ice, fire, lightning, chaos, dark, holy);
    }

    public void setMaterial(ArmourMaterialMF material) {
        this.material = material;
    }

    public static boolean isArmour(Item i){
        return  (i instanceof ItemArmor) || (i instanceof ItemArmourMF);
    }

    public static boolean hasDesign(ItemStack i){
        try {
            if (i.getItem() == null || !(isArmour(i.getItem()))) return false;
            Item item = i.getItem();
            ArmourMaterialMF material = item instanceof ItemCustomArmour ? ((ItemCustomArmour) item).getMaterial() : getMaterial(i);
            ArmourDesign design = ArmourDesign.SOLID;
            if (item instanceof ItemArmourMF) design = ((ItemArmourMF) item).design;

            ArmourDesignScalable scalable = designes.get(design);


            // OK > design found, searching material that can be empty
            if (material == null) return scalable != null;


            MaterialScales scales = materials.get(material.name);

            // material can be empty   || scales != null
            return scales != null;
        }catch (Exception er){
            return false;
        }
    }

    static public float[] designScales(ItemStack i){
        Item item = i.getItem();
        ArmourMaterialMF material = item instanceof  ItemCustomArmour ? ((ItemCustomArmour) item).getMaterial() : getMaterial(i);
        ArmourDesign design = ArmourDesign.SOLID;
        if(item instanceof ItemArmourMF) design = ((ItemArmourMF) item).design;
        if(material != null){
            MaterialScales scales = materials.get(material.name);
            ArmourDesignScalable scalable = designes.get(design);
            return scalePart(scalable.scales(scales), ((ItemArmor)item).armorType);
        } else {
            ArmourDesignScalable scalable = designes.get(design);
            return scalePart(scalable.scales(MaterialScales.emptyScale), ((ItemArmor)item).armorType);
        }
    }

    float[] scales(MaterialScales scalable){
        return new float[]{this.physical * scalable.physical, this.ice * scalable.ice, this.fire * scalable.fire, this.lightning * scalable.lightning, this.chaos+ scalable.chaos, this.dark + scalable.dark, this.holy + scalable.holy  };
    }

    float[] scales(MaterialScales scalable, ItemStack tool){
        float[] addedDamage = new ItemTitanite.NBTTitanite(tool).getAddedDamage(physical);
        return new float[]{this.physical * scalable.physical + addedDamage[0], this.ice * scalable.ice + addedDamage[1], this.fire * scalable.fire + addedDamage[2], this.lightning * scalable.lightning + addedDamage[3], this.chaos + scalable.chaos + addedDamage[4], this.dark + scalable.dark + addedDamage[5], this.holy + scalable.holy + addedDamage[6]  };
    }

    float[] scales(MaterialScales scalable, ItemTitanite.NBTTitanite titanite){
        float[] addedDamage = titanite.getAddedDamage(physical);
        return new float[]{this.physical * scalable.physical + addedDamage[0], this.ice * scalable.ice + addedDamage[1], this.fire * scalable.fire + addedDamage[2], this.lightning * scalable.lightning + addedDamage[3], this.chaos + scalable.chaos + addedDamage[4], this.dark + scalable.dark + addedDamage[5], this.holy + scalable.holy + addedDamage[6]  };
    }

    public static float[] getFullScaleRates(EntityLivingBase hit){
        float[] armourRates = null;
        for (int i = 0; i < 4; i++) {
            ItemStack armour = hit.getEquipmentInSlot(i + 1);
            if (armour != null && armourRates == null) {
                armourRates = ArmourDesignScalable.hasDesign(armour) ? ArmourDesignScalable.designScales(armour) : null;

            }
            if (armour != null && armourRates != null) {
                armourRates = concat(armourRates, ArmourDesignScalable.hasDesign(armour) ? ArmourDesignScalable.designScales(armour) : null);
            }
        }
        if(armourRates == null)return emptyRates;
        return armourRates;
    }

    public static float[] concat(float[] a, float[] b){
        if(b==null)return a;
        float[] c = new float[Math.min(a.length,b.length)];
        for(int i=0;i<c.length;i++){
            c[i] = a[i] + b[i];
        }
        return c;
    }


    static float[] scalePart(float[] stats, int part) {
        float[] newStats = new float[stats.length];
        for (int i = 0; i < stats.length; i++)
            newStats[i] = stats[i] * BattleConfig.armourRates[part] * armourBaseScale;
        return newStats;
    }

//    public static float getTotalProtection(EntityLivingBase user, int id) {
//        float armourDT = 0;
//        for (int a = 0; a < 4; a++) {
//            ItemStack armour = user.getEquipmentInSlot(a + 1);
//            if (armour != null && armour.getItem() instanceof ItemArmourMF) {
//                float threshold = getResistance(armour,((ItemArmourMF)armour.getItem()).design, id) * ((ItemArmourMF) armour.getItem()).scalePiece();
//                armourDT += id == 0 ? threshold * 4 : threshold;
//            }
//        }
//        return armourDT;
//    }

    static ArmourDesignScalable getArmourDesign(Item item){
        ArmourDesign design = item instanceof ItemArmourMF ?  ((ItemArmourMF) item).design : ArmourDesign.SOLID;
        return designes.get(design);
    }

    public static MaterialScales getMaterialScales(ItemStack tool){
        ArmourMaterialMF material = getMaterial(tool);
        if(material == null)return  MaterialScales.emptyScale;
        return materials.get(material.name);
    }

    public static String getFormattedString(ItemStack tool){
        StringBuilder info = new StringBuilder();
        if(tool == null || !hasDesign(tool)) return info.toString() +"ERROR: Has no design";

        ArmourDesignScalable armourDesign = getArmourDesign(tool.getItem());

        MaterialScales materialScales = getMaterialScales(tool);

        if(armourDesign == null || materialScales == null)
            return info.toString()+"ERROR: failed to fetch design";

        float[] originalScales = armourDesign.scales(materialScales);
        float[] scaledCharacteristics = armourDesign.scales(materialScales, tool);
        float[] addedScales = originalScales.clone();

        for(int i = 0; i < addedScales.length; i++) {
            addedScales[i] -= scaledCharacteristics[i];
            if(addedScales[i]>=1)info.append(String.format("%s %.0f%s%s (+%.0f%s)%s  ", BattleConfig.colors[i], originalScales[i]*100, "%", EnumChatFormatting.GREEN, addedScales[i]*100, "%", EnumChatFormatting.RESET));
            else info.append(String.format("%s %.1f%s%s ", BattleConfig.colors[i], i==0 ?originalScales[i]: originalScales[i] * 100, i==0?"":"%", EnumChatFormatting.RESET));
        }

        // System.out.println(info.toString()

        return info.toString();
    }

    public static void registerDesign(ArmourDesign design, ArmourDesignScalable stats){
        designes.put(design, stats);
    }

    public static void registerMaterial(ArmourMaterialMF material, MaterialScales stats){
        materials.put(material.name, stats);
    }

    public static ArmourMaterialMF getMaterial(ItemStack item){
        return item == null || item.getItem() == null ? null :
                item.hasTagCompound() && item.getTagCompound().hasKey("MF_CustomMaterials")  && item.getTagCompound().getCompoundTag("MF_CustomMaterials").hasKey("main_metal") &&
                        !item.getTagCompound().getCompoundTag("MF_CustomMaterials").getString("main_metal").equals("") && BaseMaterialMF.getMaterial(item.getTagCompound().getCompoundTag("MF_CustomMaterials").getString("main_metal")) != null ?
                        BaseMaterialMF.getMFArmourMaterial(BaseMaterialMF.getMaterial(item.getTagCompound().getCompoundTag("MF_CustomMaterials").getString("main_metal"))) :
                        item.getItem() instanceof ItemCustomArmour ? ((ItemCustomArmour) item.getItem()).getMaterial() : null;
    }

    public static void registerMaterial(String material, MaterialScales stats){
        ArmourMaterialMF mat = BaseMaterialMF.getMaterial(material).getArmourConversion();
        if(mat!=null) {
            stats.setPhysical(mat.baseAR);
            materials.put(mat.name, stats);
        }
    }
}
