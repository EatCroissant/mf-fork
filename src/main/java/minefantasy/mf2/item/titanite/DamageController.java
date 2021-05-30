package minefantasy.mf2.item.titanite;

import net.minecraft.util.DamageSource;

import java.util.Stack;

public class DamageController {
    float damage;
    String damager;
    String receiver;

    static Stack<DamageController> registry = new Stack<DamageController>();

    private DamageController(float d, String dm, String rc){
        damage=d;
        damager=dm;
        receiver=rc;
        registry.push(this);
    }

    public static String getDamage(){
        try {
            DamageController dc = registry.peek();
            return  dc.toString();
        }catch (Exception ignored){}
        return new DamageController(0, "nobody", "nobody").toString();
    }

    public static void registerDamage(float damage, String hitter, String receiver){
        new DamageController(damage,hitter,receiver) ;}

    @Override
    public String toString() {
        return "damage=" + damage + ", damager='" + damager + '\'' + ", receiver='" + receiver + '\'';
    }
}
