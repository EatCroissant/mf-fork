package minefantasy.mf2.api.crafting.carpenter;

import minefantasy.mf2.api.crafting.anvil.IAnvilRecipe;
import minefantasy.mf2.api.rpg.Skill;
import minefantasy.mf2.integration.minetweaker.helpers.TweakedShapedAnvilRecipe;
import minefantasy.mf2.integration.minetweaker.helpers.TweakedShapedCBRecipes;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.*;

/**
 * @author AnonymousProductions
 */
public class CraftingManagerCarpenter {
    /**
     * The static instance of this class
     */
    private static final CraftingManagerCarpenter instance = new CraftingManagerCarpenter();

    /**
     * A list of all the recipes added
     */
    public List recipes = new ArrayList();

    private CraftingManagerCarpenter() {
        Collections.sort(this.recipes, new RecipeSorterCarpenter(this));
        System.out.println("MineFantasy: Anvil recipes initiating");
    }

    /**
     * Returns the static instance of this class
     */
    public static CraftingManagerCarpenter getInstance() {
        return instance;
    }

    /**
     * Adds a recipe. See spreadsheet on first page for details.
     */
    public ICarpenterRecipe addRecipe(ItemStack result, Skill skill, String research, String sound, float exp,
                                      String tool, int hammer, int anvil, int time, Object... input) {
        return addRecipe(result, skill, research, sound, exp, tool, hammer, anvil, time, (byte) 0, input);
    }

    public ICarpenterRecipe addToolRecipe(ItemStack result, Skill skill, String research, String sound, float exp,
                                          String tool, int hammer, int anvil, int time, Object... input) {
        return addRecipe(result, skill, research, sound, exp, tool, hammer, anvil, time, (byte) 1, input);
    }

    /**
     * Adds a recipe. See spreadsheet on first page for details.
     */
    public ICarpenterRecipe addRecipe(ItemStack result, Skill skill, String research, String sound, float exp,
                                      String tool, int hammer, int anvil, int time, byte id, Object... input) {
        String somestring = "";
        int i = 0;
        int j = 0;
        int k = 0;
        int n;

        if (input[i] instanceof String[]) {
            String[] strings = ((String[]) input[i++]);
            String[] strings1 = strings;
            n = strings.length;

            for (int p = 0; p < n; ++p) {
                String str = strings1[p];
                ++k;
                j = str.length();
                somestring = somestring + str;
            }
        } else {
            while (input[i] instanceof String) {
                String var13 = (String) input[i++];
                ++k;
                j = var13.length();
                somestring = somestring + var13;
            }
        }

        HashMap hashMap;

        for (hashMap = new HashMap(); i < input.length; i += 2) {
            Character var16 = (Character) input[i];
            ItemStack var17 = null;

            if (input[i + 1] instanceof Item) {
                var17 = new ItemStack((Item) input[i + 1], 1, 32767);
            } else if (input[i + 1] instanceof Block) {
                var17 = new ItemStack((Block) input[i + 1], 1, 32767);
            } else if (input[i + 1] instanceof ItemStack) {
                var17 = (ItemStack) input[i + 1];
            }

            hashMap.put(var16, var17);
        }

        ItemStack[] var15 = new ItemStack[j * k];

        for (n = 0; n < j * k; ++n) {
            char var18 = somestring.charAt(n);

            if (hashMap.containsKey(Character.valueOf(var18))) {
                var15[n] = ((ItemStack) hashMap.get(Character.valueOf(var18))).copy();
            } else {
                var15[n] = null;
            }
        }
        ICarpenterRecipe recipe;

        if (id == (byte) 1) {
            recipe = new CustomToolRecipeCarpenter(j, k, var15, result, tool, time, hammer, anvil, exp, false,
                    sound, research, skill);
        } else {
            recipe = new ShapedCarpenterRecipes(j, k, var15, result, tool, time, hammer, anvil, exp, false, sound,
                    research, skill);
        }
        this.recipes.add(recipe);
        return recipe;
    }

    public ICarpenterRecipe addShapelessRecipe(ItemStack output, Skill skill, String research, String sound,
                                               float experience, String tool, int hammer, int anvil, int time, Object... input) {
        ArrayList var3 = new ArrayList();
        Object[] var4 = input;
        int var5 = input.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            Object var7 = var4[var6];

            if (var7 instanceof ItemStack) {
                var3.add(((ItemStack) var7).copy());
            } else if (var7 instanceof Item) {
                var3.add(new ItemStack((Item) var7));
            } else {
                if (!(var7 instanceof Block)) {
                    throw new RuntimeException("MineFantasy: Invalid shapeless anvil recipe!");
                }

                var3.add(new ItemStack((Block) var7));
            }
        }

        ICarpenterRecipe recipe = new ShapelessCarpenterRecipes(output, tool, experience, hammer, anvil, time, var3,
                false, sound, research, skill);
        this.recipes.add(recipe);
        return recipe;
    }

    public ItemStack findMatchingRecipe(CarpenterCraftMatrix matrix) {
        int matches = 0;
        ItemStack var3 = null;
        ItemStack var4 = null;

        for (int i = 0; i < matrix.getSizeInventory(); ++i) {
            ItemStack is = matrix.getStackInSlot(i);

            if (is != null) {
                if (matches == 0) {
                    var3 = is;
                }

                if (matches == 1) {
                    var4 = is;
                }

                ++matches;
            }
        }

        if (matches == 2 && var3.getItem() == var4.getItem() && var3.stackSize == 1 && var4.stackSize == 1
                && var3.getItem().isRepairable()) {
            Item var10 = var3.getItem();
            int var12 = var10.getMaxDamage() - var3.getItemDamageForDisplay();
            int var7 = var10.getMaxDamage() - var4.getItemDamageForDisplay();
            int var8 = var12 + var7 + var10.getMaxDamage() * 10 / 100;
            int var9 = var10.getMaxDamage() - (var8) * 2;

            if (var9 < 0) {
                var9 = 0;
            }

            return new ItemStack(var3.getItem(), 1, var9);
        } else {
            Iterator var11 = this.recipes.iterator();
            ICarpenterRecipe var13;

            do {
                if (!var11.hasNext()) {
                    return null;
                }

                var13 = (ICarpenterRecipe) var11.next();
            } while (!var13.matches(matrix));

            return var13.getCraftingResult(matrix);
        }
    }

    public ItemStack findMatchingRecipe(ICarpenter bench, CarpenterCraftMatrix matrix) {
        int time = 200;
        int anvi = 1;
        boolean hot = false;
        int hammer = 0;
        int var2 = 0;
        String toolType = "hands";
        String sound = "basic";
        ItemStack var3 = null;
        ItemStack var4 = null;

        for (int var5 = 0; var5 < matrix.getSizeInventory(); ++var5) {
            ItemStack var6 = matrix.getStackInSlot(var5);

            if (var6 != null) {
                if (var2 == 0) {
                    var3 = var6;
                }

                if (var2 == 1) {
                    var4 = var6;
                }

                ++var2;
            }
        }

        if (var2 == 2 && var3.getItem() == var4.getItem() && var3.stackSize == 1 && var4.stackSize == 1
                && var3.getItem().isRepairable()) {
            Item var10 = var3.getItem();
            int var12 = var10.getMaxDamage() - var3.getItemDamageForDisplay();
            int var7 = var10.getMaxDamage() - var4.getItemDamageForDisplay();
            int var8 = var12 + var7 + var10.getMaxDamage() * 10 / 100;
            int var9 = var10.getMaxDamage() - var8;

            if (var9 < 0) {
                var9 = 0;
            }

            return new ItemStack(var3.getItem(), 1, var9);
        } else {
            Iterator var11 = this.recipes.iterator();
            ICarpenterRecipe var13 = null;

            while (var11.hasNext()) {
                ICarpenterRecipe rec = (ICarpenterRecipe) var11.next();

                if (rec.matches(matrix)) {
                    var13 = rec;
                }
            }

            if (var13 != null) {
                time = var13.getCraftTime();
                hammer = var13.getRecipeHammer();
                anvi = var13.getAnvil();
                hot = var13.outputHot();
                toolType = var13.getToolType();
                sound = var13.getSound();

                bench.setForgeTime(time);
                bench.setToolTier(hammer);
                bench.setRequiredCarpenter(anvi);
                bench.setHotOutput(hot);
                bench.setToolType(toolType);
                bench.setCraftingSound(sound);
                bench.setResearch(var13.getResearch());
                bench.setSkill(var13.getSkill());

                return var13.getCraftingResult(matrix);
            }
            return null;
        }
    }

    /**
     * returns the List<> of all recipes
     */
    public List getRecipeList() {
        return this.recipes;
    }
}
