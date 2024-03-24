package minefantasy.mf2.integration.minetweaker.helpers;

import minefantasy.mf2.api.crafting.anvil.AnvilCraftMatrix;
import minefantasy.mf2.api.crafting.anvil.IAnvilRecipe;
import minefantasy.mf2.api.rpg.Skill;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;

import java.util.List;

public class TweakedShapedAnvilRecipe implements IAnvilRecipe {

    private int hammer, anvil, time, recipeWidth, recipeHeight;
    private float exp;
    private IItemStack result;
    public IIngredient[][] ingredients;
    private Skill skill;
    private String research, tool;
    private boolean hot;

    public TweakedShapedAnvilRecipe(IIngredient[][] input, IItemStack output, String tool, int time, int hammer,
                                    int anvil, boolean hot, String research, Skill skill) {
        this.ingredients = input;
        this.result = output;
        this.tool = tool;
        this.hammer = hammer;
        this.anvil = anvil;
        this.hot = hot;
        this.research = research;
        this.skill = skill;
        this.time = time;
        calculateRecipeSize();
    }

    @Override
    public int getAnvil() {
        return anvil;
    }

    @Override
    public int getCraftTime() {
        return time;
    }

    @Override
    public ItemStack getCraftingResult(AnvilCraftMatrix arg0) {
        return this.getRecipeOutput().copy();
    }

    @Override
    public int getRecipeHammer() {
        return hammer;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return MineTweakerMC.getItemStack(result).copy();
    }

    @Override
    public int getRecipeSize() {
        return this.recipeWidth * this.recipeHeight;
    }

    @Override
    public String getResearch() {
        return research;
    }

    @Override
    public Skill getSkill() {
        return this.skill;
    }

    @Override
    public String getToolType() {
        return tool;
    }

   /**
   public boolean matches(AnvilCraftMatrix matrix) {
        for (int x = 0; x <= this.recipeWidth; ++x) {
            if(ingredients.length > this.recipeWidth || ingredients[x].length > this.recipeHeight){
                return false;
            }
            for (int y = 0; y <= this.recipeHeight; ++y) {
                ItemStack inputItem = matrix.getStackInRowAndColumn(x, y);
                /*if(x >= this.recipeWidth || y >= this.recipeHeight) {
                    return false;
                }

                if(ingredients[x][y] == null && inputItem == null){
                    continue;
                } else if (inputItem != null && ingredients[x][y] != null) {
                    IIngredient recipeStack = ingredients[x][y];
                    if(recipeStack == inputItem.getItem()){
    } **/

   @Override
   public boolean matches(AnvilCraftMatrix inv) {
       // Ширина и высота вашей матрицы
       int width = 6;
       int height = 4;
       int recHeight = ingredients.length;
       int recWidth = ingredients[0].length;
       boolean matches = false;

       // Проходимся по подматрицам
       for (int offsetX = 0; offsetX <= width - recWidth; offsetX++) {
           for (int offsetY = 0; offsetY <= height - recHeight; offsetY++) {
               // Проверяем каждую подматрицу
               System.out.println(offsetX + " "+ offsetY);
               if (checkSubMatrix(inv, offsetX, offsetY)) {
                   System.out.println("GOT IT");
                   return true;
               }
           }

       }

       return matches;
   }

    private boolean checkSubMatrix(AnvilCraftMatrix inv, int offsetX, int offsetY) {
        int recHeight = ingredients.length;
        int recWidth = ingredients[0].length;

        for (int x = 0; x < recWidth; x++) {
            for (int y = 0; y < recHeight; y++) {
                ItemStack stack = inv.getStackInRowAndColumn(offsetX + x, offsetY + y);
                IIngredient ii = ingredients[y][x];
                // Проверяем совпадение ингредиента в подматрице с ингредиентом из массива
                if (stack == null &&  ii == null) {
                    continue;
                }
                if (stack == null || ii == null) {
                    return false; // Несоответствие
                }

                for (IItemStack i : ii.getItems()) {
                    ItemStack ingred = MineTweakerMC.getItemStack(i);
                    if (stack.getItem() != ingred.getItem() || stack.getItemDamage() != ingred.getItemDamage()) {
                        return false;
                    } else {
                        break;
                    }
                }
            }
        }
        return true;
    }


    private boolean isInList(ItemStack inputStack, List<IItemStack> items) {
        for (IItemStack i : items) {
            ItemStack recipeStack = MineTweakerMC.getItemStack(i);
            if (inputStack.getItem() == recipeStack.getItem() && inputStack.getItemDamage() == recipeStack.getItemDamage()) {
                /*if (!CustomToolHelper.doesMatchForRecipe(inputStack, recipeStack)) {
                    return false;
                }*/
                return true;
            }
        }
        return false;
    }

    private void calculateRecipeSize() {
        this.recipeHeight = this.ingredients.length;
        for (int x = 0; x < this.recipeHeight; x++) {
            if (this.ingredients[x] != null && this.ingredients[x].length > this.recipeWidth) {
                this.recipeWidth = this.ingredients[x].length;
            }
        }
    }

    @Override
    public boolean outputHot() {
        return hot;
    }

    @Override
    public boolean useCustomTiers() {
        return false;
    }

}
