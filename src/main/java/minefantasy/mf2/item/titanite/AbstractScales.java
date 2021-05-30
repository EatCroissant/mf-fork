package minefantasy.mf2.item.titanite;

import net.minecraft.item.ItemStack;

public abstract class AbstractScales {
    float physical;
    float ice;
    float fire;
    float lightning;
    float chaos;
    float dark;
    float holy;
    AbstractScales(float physical, float ice, float fire, float lightning, float chaos, float dark, float holy){
        this.physical = physical;
        this.ice = ice;
        this.fire = fire;
        this.lightning = lightning;
        this.chaos = chaos;
        this.dark = dark;
        this.holy = holy;
    }

    AbstractScales(float ice, float fire, float lightning, float chaos, float dark, float holy){
        this.ice = ice;
        this.fire = fire;
        this.lightning = lightning;
        this.chaos = chaos;
        this.dark = dark;
        this.holy = holy;
    }

    AbstractScales(float physical){
        this.physical = physical;
    }

    public void setPhysical(float physical) {
        this.physical = physical;
    }
    float[] getRates(){
        return new float[]{physical,ice,fire,lightning,chaos,dark,holy};
    }

    static boolean hasDesign(ItemStack undefinedItemStack) {
        return false;
    }

    static float[] designScales(ItemStack i){return null;};
    abstract float[] scales(MaterialScales scalable);
    abstract float[] scales(MaterialScales scalable, ItemStack tool);

}
