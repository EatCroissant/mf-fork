package minefantasy.mf2.item.titanite.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import minefantasy.mf2.api.helpers.PowerArmour;
import minefantasy.mf2.api.helpers.TacticalManager;
import minefantasy.mf2.item.titanite.DamageController;
import minefantasy.mf2.item.titanite.ToolDesignScalable;
import minefantasy.mf2.mechanics.EventManagerMF;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class BattleHandler {

    private float provideDamage(EntityLivingBase hitter, EntityLivingBase victim) {
        ItemStack sourceDamageItem = hitter != null &&
                ( hitter).getHeldItem() != null ? ( hitter).getHeldItem() : null;

        if(ToolDesignScalable.hasDesign(sourceDamageItem) ) {
            float[] damageOfPlayer = ToolDesignScalable.designScales(sourceDamageItem);

            if(damageOfPlayer !=null ){
                float baseDamage = 0;
                for(float floats: damageOfPlayer)
                    baseDamage+=floats;
                DamageController.registerDamage(baseDamage, hitter.getCommandSenderName(),victim.getCommandSenderName() );
                System.out.println("all ok");
                return baseDamage;//processDamage(damageOfPlayer, victim);
            } else {
                System.out.println("Cant create damage");
            }
        } else {
            System.out.println("has no design");
        }
        System.out.println("all wrong");
        return 0;
    }

    @SubscribeEvent
    public void onHit(LivingHurtEvent event) {
        DamageSource src = event.source;
        EntityLivingBase hit = event.entityLiving;
        World world = hit.worldObj;
        float damage = event.ammount ;//modifyDamage(src, world, hit, event.ammount, true);
        if(src.getEntity() instanceof EntityLivingBase) {
            System.out.println("Try to apply hit ");
            damage= provideDamage((EntityLivingBase)src.getEntity(),  hit);
            event.ammount = damage;

            //event.entityLiving.attackEntityFrom(src, damage);
            return;
        } else {
            System.out.println("Cant apply hit");
        }
    }
    /**
     * gets the melee hitter
     */
    private EntityLivingBase getHitter(DamageSource source) {
        if (source != null && source.getEntity() != null && source.getEntity() == source.getSourceOfDamage()
                && source.getEntity() instanceof EntityLivingBase) {
            return (EntityLivingBase) source.getEntity();
        }
        return null;
    }



    @SubscribeEvent
    public void initAttack(LivingAttackEvent event) {
        EntityLivingBase hitter = getHitter(event.source);
        int spd = EventManagerMF.getHitspeedTime(hitter);
        if (hitter != null && !hitter.worldObj.isRemote) {
            if (spd > 0 && !(event.entityLiving instanceof EntityPlayer || event.entityLiving instanceof EntityEnderman)) {
                event.setCanceled(true);
                return;
            }
        }
        float damage = event.ammount;
        DamageSource src = event.source;
        EntityLivingBase hit = event.entityLiving;
        World world = hit.worldObj;

        if(src.getEntity() instanceof EntityLivingBase) {
            damage = provideDamage((EntityLivingBase)src.getEntity(),  hit);

            hit.attackEntityFrom(new DamageSource("player"), damage);
            System.out.println("try to init hit");
        } else {
            System.out.println("cant init hit");
        }

    }
}
