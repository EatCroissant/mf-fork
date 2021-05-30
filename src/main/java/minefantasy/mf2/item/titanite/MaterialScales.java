package minefantasy.mf2.item.titanite;

import net.minecraft.item.ItemStack;

public class MaterialScales extends AbstractScales{
    static MaterialScales emptyScale = new MaterialScales(1,1,1,1,0,0,0);

    MaterialScales(float physical, float ice, float fire, float lightning, float chaos, float dark, float holy) {
        super(physical, ice, fire, lightning, chaos, dark, holy);
    }
    MaterialScales(float ice, float fire, float lightning, float chaos, float dark, float holy) {
        super( ice, fire, lightning, chaos, dark, holy);
    }
    MaterialScales(float physical) {
        super(physical);
    }

    static float[] designScales(ItemStack i) {
        return new float[0];
    }

    @Override
    float[] scales(MaterialScales scalable) {
        return new float[0];
    }

    @Override
    float[] scales(MaterialScales scalable, ItemStack tool) {
        return new float[0];
    }

}
