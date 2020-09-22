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
            parse(entry.getValue().getString());
        }
        for (Map.Entry<String, Property> entry : config.getCategory(ANVIL).getValues().entrySet()) {
            parseAnvil(entry.getValue().getString());
        }
    }

    static Item item = Items.apple;
    static Block block = Blocks.brick_block;

    static ItemStack getItemStackFromName(String name) {
        try {
            if(name.contains(":")){
                String[] sp = name.split(":");
                if(sp.length>=2) {
                    ItemStack is = GameRegistry.findItemStack(sp[0],sp[1], 1);
                    if(sp.length>2 && is !=null && Integer.getInteger(sp[2]) > 0 ){
                        is.setItemDamage(Integer.getInteger(sp[2]));
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
        HashMap<Item, Character> association = new HashMap<Item, Character>();
        String[] map = {"","","",""};
        int charter = (int)'a';
        for (int i = offset; i < params.length; i++) {
            ItemStack is = getItemStackFromName(params[i]);
            if (is == null) {
                map[(i - offset) / length] += " ";
            } else if (is.getItem() != null && association.containsKey(is.getItem())) {
                map[(i - offset) / length] += association.get(is.getItem());
            } else {
                map[(i - offset) / length] += (char) charter;
                association.put(is.getItem(), (char) charter++);
            }
        }
        ArrayList objs = new ArrayList();
        Collections.addAll(objs, map);
        Set<Map.Entry<Item, Character>> entries = association.entrySet();
        for (Map.Entry<Item, Character> entity : entries) {
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
        return MineFantasyAPI.addCarpenterRecipe(skill, output, recipe[2].equals("?")?"":recipe[2], recipe[3].equals("?")?"":recipe[3],
                recipe[4].equals("?")?"":recipe[4], Integer.parseInt(recipe[5]), Integer.parseInt(recipe[6]), matrix);
    }
    public static IAnvilRecipe parseAnvil(String config) {
        String[] recipe = config.split("\\s+");
        Skill skill = RPGElements.getSkillByName(recipe[0]);
        ItemStack output = getItemStackFromName(recipe[1]);
        if(output == null)return null;
        Object[] matrix = getRecipe(recipe, 8,6);
        return MineFantasyAPI.addAnvilRecipe(skill, output, recipe[2].equals("?")?"":recipe[2], recipe[3].equals("?") || recipe[3].equals("false") ?true:false,
                recipe[4].equals("?")?"":recipe[4], Integer.parseInt(recipe[5]), Integer.parseInt(recipe[6]), Integer.parseInt(recipe[7]), matrix);
    }
}