package minefantasy.mf2.config;

import cpw.mods.fml.common.registry.GameRegistry;
import minefantasy.mf2.api.MineFantasyAPI;
import minefantasy.mf2.api.crafting.anvil.IAnvilRecipe;
import minefantasy.mf2.api.crafting.carpenter.ICarpenterRecipe;
import minefantasy.mf2.api.rpg.RPGElements;
import minefantasy.mf2.api.rpg.Skill;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Property;

import java.lang.reflect.Field;
import java.util.*;
//sample :     S:item1="artisanry minecraft:apple ? hand ? 0 5 minecraft:iron null null null null null null null null null null null null null null null"
// Skill skill, ItemStack result, String research, boolean hot, String toolType, int hammerType, int anvil, int forgeTime, Object... input
//sample :     S:item1="artisanry minecraft:apple ? (true/?|false/anything) hammer 0 2 5 minecraft:iron minecraft:iron null null null null null null null null null null null null null null null null null null null null null null"
public class ConfigRecipes extends ConfigurationBaseMF {

    public static final String CARPENTER = "Carpenter";
    public static final String ANVIL = "Anvil";

    @Override
    protected void loadConfig() {
        for (Map.Entry<String, Property> entry : config.getCategory(CARPENTER).getValues().entrySet()) {
            if(parse(entry.getValue().getString()) == null){
                System.out.println("Cannot process carpenter recipe: "+entry.toString());
            }
        }
        for (Map.Entry<String, Property> entry : config.getCategory(ANVIL).getValues().entrySet()) {
            if(parseAnvil(entry.getValue().getString()) == null){
                System.out.println("Cannot process anvil recipe: "+entry.toString());
            }

        }
    }

    static Item item = Items.apple;
    static Block block = Blocks.brick_block;

    static ItemStack getItemStackFromName(String name) {
        try {
            if(name.contains(":")){
                String[] sp = name.split(":");
                if(sp.length>=2) {
                    int meta =(sp.length>2 && Integer.parseInt(sp[2]) > 0)? Integer.parseInt(sp[2]): 0;

                    ItemStack is = GameRegistry.findItemStack(sp[0],sp[1], 1);

                    Item isi = GameRegistry.findItem(sp[0],sp[1]);
                    Block isb = GameRegistry.findBlock(sp[0],sp[1]);

                    if(meta>0){
                        is.setItemDamage(meta);
                        return is;
                    }
                    return is;
                }
            }

            String separator = ".";
            int sepPos = name.lastIndexOf(separator);
            if (name == "null" || sepPos == -1) return null;
            Class clazz = Class.forName(name.substring(0, sepPos));

            Field itemOrBlock = clazz.getDeclaredField(name.substring(sepPos + 1));
            Class c = itemOrBlock.getType();

            Item i;
            Block b;
            if (c == Item.class) {
                i = (Item) itemOrBlock.get(item);
                return new ItemStack(i);
            }
            if (c == Block.class) {
                b = (Block) itemOrBlock.get(block);
                return new ItemStack(b);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public static Object[] getRecipe(String[] params, int offset, int length) {
        HashMap<ItemStack, Character> association = new HashMap<ItemStack, Character>();
        String[] map = {"","","",""};
        int charter = (int)'a';
        for (int i = offset; i < params.length; i++) {
            ItemStack is = getItemStackFromName(params[i]);
            if (is == null) {
                map[(i - offset) / length] += " ";
            } else if ( association.containsKey(is)) {
                map[(i - offset) / length] += association.get(is);
            } else {
                map[(i - offset) / length] += (char) charter;
                association.put(is, (char) charter++);
            }
        }
        ArrayList objs = new ArrayList();
        Collections.addAll(objs, map);
        Set<Map.Entry<ItemStack, Character>> entries = association.entrySet();
        for (Map.Entry<ItemStack, Character> entity : entries) {
            objs.add(entity.getValue());
            objs.add(entity.getKey());
        }
        return objs.toArray();
    }

    public static ICarpenterRecipe parse(String config) {
        String[] recipe = config.split("\\s+");
        Skill skill = RPGElements.getSkillByName(recipe[0]);
        ItemStack output = getItemStackFromName(recipe[1]);
        if(output == null)return null;
        Object[] matrix = getRecipe(recipe, 7, 4);

        if(matrix.length==4)return null;
        return MineFantasyAPI.addCarpenterRecipe(skill, output, recipe[2].equals("?")?"":recipe[2], recipe[3].equals("?")?"":recipe[3],
                recipe[4].equals("?")?"":recipe[4], Integer.parseInt(recipe[5]), Integer.parseInt(recipe[6]), matrix);
    }
    public static IAnvilRecipe parseAnvil(String config) {
        String[] recipe = config.split("\\s+");
        Skill skill = RPGElements.getSkillByName(recipe[0]);
        ItemStack output = getItemStackFromName(recipe[1]);
        if(output == null)return null;
        boolean hot = recipe[3].equals("true") || recipe[3].equals("+");
        Object[] matrix = getRecipe(recipe, 8,6);
        if(matrix.length==4)return null;
        return MineFantasyAPI.addAnvilRecipe(skill, output, recipe[2].equals("?")?"":recipe[2], hot,
                recipe[4].equals("?")?"":recipe[4], Integer.parseInt(recipe[5]), Integer.parseInt(recipe[6]), Integer.parseInt(recipe[7]), matrix);
    }
}