package minefantasy.mf2.integration.biomes;

import biomesoplenty.api.content.BOPCBlocks;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.registry.GameRegistry;
import minefantasy.mf2.api.MineFantasyAPI;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.api.rpg.SkillList;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.item.ItemComponentMF;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.recipe.CarpenterRecipes;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

//@Optional(modid = "BiomesOPlenty")
public class BIOPIntegration {
    private static final String[] types = new String[]{
            "sacredoak",
            "cherry",
            "dark",
            "fir",
            "ethereal",
            "magical",
            "mangrove",
            "palm",
            "redwood",
            "willow",
            "dead",
            "pine",
            "hellbark",
            "jacaranda",
            "mahogany"
    };

    private final int[][] colors = new int[][]{
        {189,153,99, 3, 14, 20, 26, 40, 25  },    // sacredoak священный дуб    3 тир; 5кг; Дуб+++
        {161,57,54,  1, 9,  13, 17, 28, 5   },     // cherry   вишня            1 тир; 500г; Дуб+
        {91,85,94,   2, 10, 35, 15, 75, 9   },      // dark темное дерево       2 тир; 900г; (Серебряное дерево)
        {193,169,140,1, 1,  5,  5,  10, 3 },   // fir  пихта                    0 тир; 300г; (Отходный брусок)
        {94,168,133, 3, 1,  40, 40, 85, 0  },    // ethereal эфирное дерево    3 тир; 0г; Дуб+++++ (самый лучший материал)
        {116,136,204,2, 7,  40, 20, 85, 7 },   // magic волшебное дерево       2 тир; 700г; (Серебряное древо)+(Чуть чуть лучше сделать)
        {255,255,237,1, 3,  10, 10, 25, 1 },   // mangrove мархровия           1 тир,(Отходный брусок)
        {217,150,86, 1, 2,  9,  10, 20, 4 },    // palm пальма                  1 тир; 400г; (Ель)
        {192,113,87, 2, 13, 20, 17, 30, 10, },    // redwood красное дерево     2 тир; 1кг; (Очищенная древесина)
        {170,179,131,2, 7,  20, 25, 40, 8 },   // willow ива                    1 тир; 800г; (Тис)
        {180,161,131,1, 1,  5,  15,  5, 1 },   // dead дохлая деревяшка         1 тир,(Отходный брусок)
        {195,155,123,1, 2,  7,  9,  15, 4},   // pine сосна                     1 тир, 400г; (ель-)
        {215,167,122,2, 9,  30, 12,100, 30 },   // hellbark адская древесина    2 тир; 3кг; (Эбенн-)
        {145,218,209,1, 7,  10, 13, 22, 5},   // jacaranda жакандра             1 тир; 500г; (Дуб)
        {211,153,144,1, 8,  33, 15, 20, 9}    // mahogany махоебия                           1 тир; 900г; (Пропитанные бруски)
    };
    //                                  tier  hard   dur    flex   res  dens
    //        getOrAddWood("ScrapWood",     0, 0.10F, 0.50F, 0.50F, 10F, 0.5F, 100, 95, 80);
    //        getOrAddWood("OakWood",       1, 0.70F, 1.00F, 1.30F, 40F, 0.8F, 149, 119, 70).setCrafterTiers(1);
    //        getOrAddWood("SpruceWood",    1, 0.20F, 0.90F, 1.00F, 20F, 0.4F, 102, 79, 47).setCrafterTiers(1);
    //        getOrAddWood("BirchWood",     1, 0.50F, 0.90F, 1.30F, 10F, 0.7F, 200, 183, 122).setCrafterTiers(1);
    //        getOrAddWood("JungleWood",    1, 0.40F, 1.00F, 1.60F, 50F, 0.6F, 159, 113, 74).setCrafterTiers(1);
    //        getOrAddWood("AcaciaWood",    1, 0.50F, 1.20F, 1.00F, 20F, 0.6F, 173, 93, 50).setCrafterTiers(1);
    //        getOrAddWood("DarkOakWood",   1, 1.20F, 1.50F, 1.30F, 50F, 1.0F, 62, 41, 18).setCrafterTiers(1)
    //        getOrAddWood("RefinedWood",   2, 0.80F, 2.00F, 1.30F, 50F, 0.8F, 95, 40, 24).setCrafterTiers(2).setRarity(1);
    //        getOrAddWood("YewWood",       2, 0.70F, 2.00F, 2.50F, 40F, 0.7F, 195, 138, 54).setCrafterTiers(2).setRarity(1);
    //        getOrAddWood("IronbarkWood",  2, 0.90F, 3.50F, 1.10F, 50F, 0.9F, 202, 92, 29).setCrafterTiers(2).setRarity(1);
    //
    //        getOrAddWood("EbonyWood",     3, 1.30F, 4.00F, 1.60F, 80F, 1.0F, 50, 46, 40).setCrafterTiers(3).setRarity(2);
    //        getOrAddWood("SilverwoodWood",2, 1.00F, 3.50F, 1.50F, 75F, 0.8F, 224, 220, 208).setCrafterTiers(2);
    //        getOrAddWood("GreatwoodWood", 2, 1.20F, 1.50F, 1.30F, 50F, 1.5F, 37, 25, 23).setCrafterTiers(2);


    Item logs1;

    public BIOPIntegration() {
        if (Loader.isModLoaded("BiomesOPlenty")) register();

    }

    @Optional.Method(modid = "BiomesOPlenty")
    public int register() {
        CustomMaterial[] wood_apply = new CustomMaterial[types.length];

        for (int i = 0; i < types.length; i++) {
            wood_apply[i] = CustomMaterial.getOrAddMaterial(types[i], "wood",
                    colors[i][3], colors[i][4]*.1f, colors[i][5]*.1f, colors[i][6]*.1f, colors[i][6]*.1f, colors[i][7]*1f, colors[i][8]*.1f,
                    colors[i][0], colors[i][1], colors[i][2]
            ).setCrafterTiers(colors[i][3]);

            ItemStack plank_std = new ItemStack(BOPCBlocks.planks, 1, i);
            ItemStack plank = ComponentListMF.plank.construct(types[i]);

            GameRegistry.addShapedRecipe(ComponentListMF.plank.construct(types[i],4), new Object[]{"S", "S", 'S', plank_std });


            ItemStack plank_cut = ((ItemComponentMF) ComponentListMF.plank_cut).construct(types[i]);
            MineFantasyAPI.addShapelessCarpenterRecipe(SkillList.construction,
                    plank_cut, "", CarpenterRecipes.basic, "saw", 0, 1, ComponentListMF.plank.construct(types[i],1));

            ItemStack plank_pane = ((ItemComponentMF) ComponentListMF.plank_pane).construct(types[i]);

            MineFantasyAPI.addCarpenterRecipe(SkillList.construction,
                    plank_pane, "", CarpenterRecipes.basic, "hammer", 0, 1,
                    "NNN ", "CPC ", "CPC ", "    ", 'P', plank, 'C', plank_cut, 'N', ComponentListMF.nail);


        }
        return 1;
    }

}
