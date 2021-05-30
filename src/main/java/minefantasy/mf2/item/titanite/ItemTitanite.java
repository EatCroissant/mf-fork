package minefantasy.mf2.item.titanite;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.item.list.CreativeTabMF;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.List;

public class ItemTitanite extends ItemEnchantedBook {
    static String[] types = new String[]{"basic", "ice", "fire", "lightning", "chaos", "dark", "holy", "unique" };
    public int type;
    // static Enchantment[] enchantment;

    static{
//        int id = 0;
//        Enchantment[] s = Enchantment.enchantmentsList;
//        enchantment = new Enchantment[types.length];
//        for(int j=0;j<types.length;j++) {
//            for (int i = 0; i < s.length; i++) if (s[id = i] == null) break;
//            enchantment[j] = new Enchantment(id, 0, EnumEnchantmentType.all) {
//
//            }.setName(types[j].toUpperCase(Locale.ROOT));
//        }
    }

    private final IIcon[] icons = new IIcon[types.length];

    public ItemTitanite(){
        super();
        setMaxStackSize(16);
        setTextureName("minefantasy2:titanite/" + types[type]);
        GameRegistry.registerItem(this,MineFantasyII.MODID+":MF_Titanite_" + types[type] );
        setCreativeTab(CreativeTabMF.tabGadget);
        this.type = type;
    }
    @Override
    public boolean getIsRepairable(ItemStack p_82789_1_, ItemStack p_82789_2_)
    {
        return true;
    }
    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        return true;
    }
    public boolean isRepairable()
    {
        return canRepair && isDamageable();
    }
    @Override
    public boolean getHasSubtypes() {
        return true;
    }


    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage) {
        return this.icons[damage];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        for (int i = 0; i < types.length; ++i) {
            this.icons[i] = register.registerIcon(MineFantasyII.MODID+":titanite/" + types[type]);
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack i) {
          return "MF_Titanite_" + types[getDamage(i)];

    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for(int i=0;i<types.length;i++){
            ItemStack itemStack = new ItemStack(this, 1, i);//.setStackDisplayName(NBTTitanite.TYPE.values()[i].toString());
            list.add(itemStack);
        }
    }

    public ItemStack applyEffect(ItemStack itemStack) {
        if(itemStack == null)return null;
        ItemStack applied = itemStack;

        return applied;
    }

    public static class NBTTitanite{
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
         *   */
        public TYPE titanite_class;
        public boolean unique=false;
        public int level=0;

        private NBTTitanite(boolean unique, TYPE class_, int level){this.titanite_class=class_;this.unique=unique;this.level=level;}
        //parse
        public NBTTitanite(String str){
            parse(str);
        }

        public NBTTitanite(ItemStack i){
            NBTTagCompound global = i.hasTagCompound() ? i.getTagCompound() : new NBTTagCompound();
            NBTTagCompound titanite = i.hasTagCompound() && i.getTagCompound().hasKey("titanite") ? i.getTagCompound().getCompoundTag("titanite") : new NBTTagCompound();
            if(i.hasTagCompound() && i.getTagCompound().hasKey("titanite") && titanite.hasKey("data")){
                parse(titanite.getString("data"));
                return;
            }
            titanite_class = TYPE.BASIC;
            level = 0;
            unique = false;
            setUpgrade(i);
        }

        boolean parse(String str) {
            String[] parts = str.split("\\|");
            try {
                this.titanite_class = TYPE.getTypeByName(parts[1]);
                this.unique = Integer.parseInt(parts[0]) == 1;
                this.level = Integer.parseInt(parts[2]);

                // System.out.println(toString());
            }
            catch(Exception e){
                return false;
            }
            return true;
        }

        public float[] getAddedDamage(float initial){
            float[] f = new float[7];
            if(unique){
                switch (titanite_class){
                    case UNIQUE_BASIC:      f[0] = initial * ( level + 5 ) * 0.06f;     f[3] = initial * 0.9f;      break;
                    case UNIQUE_ICE:        f[1] = initial * level * 0.06f;             f[2] = initial * 0.6f;      f[0] = initial * 0.45f;     break;
                    case UNIQUE_FIRE:       f[2] = initial * level * 0.06f;             f[1] = initial * 0.6f;      f[0] = initial * 0.45f;     break;
                    case UNIQUE_LIGHTNING:  f[3] = initial * level * 0.06f;             f[0] = initial * 1.15f;     f[0] = initial * 0.45f;     break;
                    case UNIQUE_CHAOS:      f[4] = initial * level * 0.06f;             f[2] = initial * 0.45f;     f[0] = initial * 0.45f;     break;
                    case UNIQUE_DARK:       f[5] = initial * level * 0.06f;             f[5] = initial * 0.45f;     f[6] = initial * 0.45f;     break;
                }
            } else {
                switch (titanite_class){
                    case BASIC:     f[0] = initial * level * 0.06f;         break;
                    case ICE:       f[1] = initial * level * 0.06f;         f[0] = initial * 0.3f;      break;
                    case FIRE:      f[2] = initial * level * 0.06f;         f[0] = initial * 0.3f;      break;
                    case LIGHTNING: f[3] = initial * level * 0.06f;         f[0] = initial * 0.3f;      break;
                    case CHAOS:     f[4] = initial * level * 0.06f;         f[0] = initial * 0.3f;      f[2] = initial * (level + 5)* 0.06f;    break;
                    case DARK:      f[5] = initial * level * 0.06f;         f[0] = initial * 0.3f;      f[6] = initial * (level + 5)* 0.06f;    break;
                    case HOLY:      f[6] = initial * level * 0.06f;         f[0] = initial * 0.3f;      break;
                }
            }
            return f;
        }

        public static boolean upgrade(ItemStack titanite, ItemStack upgradable){
            NBTTitanite upg = new NBTTitanite(upgradable);
            int type = -1;

            if( titanite.getItem() instanceof ItemTitanite) type = titanite.getItemDamage();
            else return false;

            List<Integer> available = upg.getUpgradeList();

            if(available.contains(type)){
                if(upg.level == upg.titanite_class.max_lvl && type == 8 - upg.titanite_class.id){
                    upg.level = 1;
                    upg.unique = true;
                    upg.titanite_class = TYPE.getTypeByName("U"+upg.titanite_class.name);
                    upg.setUpgrade(upgradable);

                    return true;
                } else if(upg.level != upg.titanite_class.max_lvl && ( type == upg.titanite_class.id - 1 || type == 8 - upg.titanite_class.id ) ){
                    if(upg.level == 0) {
                        upg.titanite_class = TYPE.values()[type];
                        upg.level = 0;
                    }
                    upg.level++;
                    upg.setUpgrade(upgradable);
                    return true;
                } else if(type < 7 && type > 0){
                    upg.level = 1;
                    if(!upg.unique)
                    upg.titanite_class = TYPE.values()[type];
                    upg.setUpgrade(upgradable);
                    return true;
                }
            }
            return false;
        }

        public static ArrayList<ItemStack> constructTopTitanitedItemArray(ItemStack item){
            ArrayList<ItemStack> items = new ArrayList<>();
            for(TYPE i : TYPE.values())
                items.add(new NBTTitanite(i.name.contains("U"), i, i.max_lvl).setUpgrade(item.copy()));
            return items;
        }

        public static ArrayList<NBTTitanite> constructArrayTopTitanite(){
            ArrayList<NBTTitanite> items = new ArrayList<>();
            for(TYPE i : TYPE.values())
                items.add(new NBTTitanite(i.name.contains("U"), i, i.max_lvl));
            return items;
        }


        public List<Integer> getUpgradeList(){
            ArrayList<Integer> list = new ArrayList<>();
            if(titanite_class == null)return list;
            if(!unique){
                if(level == 5){
                    switch (titanite_class){
                        case BASIC:
                            list.add(1);
                            list.add(2);
                            list.add(3);
                            list.add(6);
                            break;
                        case HOLY:
                            list.add(5);
                            break;
                        case FIRE:
                            list.add(4);
                            break;
                    }
                }
                if(level < titanite_class.max_lvl) {
                    list.add(titanite_class.id - 1);
                }
                else if(level == titanite_class.max_lvl){
                    list.add(7);
                }
            }
            else {
                if(level < titanite_class.max_lvl){
                    list.add(titanite_class.id - 8);
                }
            }
            return list;
        }

        @Override
        public String toString() {
            return encode();
        }

        ItemStack setUpgrade(ItemStack i){
            NBTTagCompound globalTag = i.hasTagCompound() ? i.getTagCompound() : new NBTTagCompound();
            NBTTagCompound titanite = globalTag.hasKey("titanite") ? globalTag.getCompoundTag("titanite") : new NBTTagCompound();
            titanite.setString("data", toString());
            globalTag.setTag("titanite", titanite);
            i.setTagCompound(globalTag);
            return i;
        }

        String encode(){
            String s = "";
            s += unique ? "1" : "0";
            s += "|";
            s += titanite_class != null ? titanite_class.name :  "B";
            s += "|";
            s += level;
            return s;
        }

        public String getTranslatedTitaniteName(){
            return TYPE.getName(this.titanite_class);
        }

        private enum TYPE{
            BASIC(1, "B", 15),
            ICE(2, "I", 10),
            FIRE(3, "F", 10),
            LIGHTNING(4, "L", 10),
            CHAOS(5, "C", 5),
            DARK(6, "D", 5),
            HOLY(7, "H", 10),
            UNIQUE_LIGHTNING(8, "UL", 15),
            UNIQUE_FIRE(9, "UF", 10),
            UNIQUE_ICE(10, "UI", 10),
            UNIQUE_BASIC(11, "UB", 10),
            UNIQUE_DARK(12, "UD", 5),
            UNIQUE_CHAOS(13, "UC", 5);

            int id;
            String name;
            int max_lvl;

            public static String getName(TYPE t){
                return StatCollector.translateToLocal("item.titanite." + t.name + ".name");
            }

            public static TYPE getTypeByName(String nane){
                for(TYPE t : TYPE.values()){
                    if(t.name.equals(nane))return t;
                }
                return BASIC;
            }

            TYPE(int id, String string, int max){
                this.name = string;
                this.id=id;
                this.max_lvl = max;
            }
        }
    }
}
