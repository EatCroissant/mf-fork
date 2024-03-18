package minefantasy.mf2.mechanics;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.item.gadget.ItemCrossbow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class KeyBindProcessor {
    public static KeyBinding crossBowReload;
    public static KeyBinding crossBowScope;
    public static KeyBinding crossBowAttack;

    public static void registerBindings(){
        crossBowReload = new KeyBinding("key.crossBowReload", Keyboard.KEY_R, "key.mf.crossbow");
        crossBowScope = new KeyBinding("key.crossBowScope", Keyboard.KEY_KANJI, "key.mf.crossbow");
        crossBowAttack = new KeyBinding("key.crossBowAttack", Mouse.getButtonIndex(Mouse.getButtonName(1)), "key.mf.crossbow");

        ClientRegistry.registerKeyBinding(crossBowReload);
        ClientRegistry.registerKeyBinding(crossBowScope);
        ClientRegistry.registerKeyBinding(crossBowAttack);
    }

    @SubscribeEvent
    public void processEvent(InputEvent.KeyInputEvent event){
        if (crossBowReload.isPressed())
        {
            ItemStack heldItem = Minecraft.getMinecraft().thePlayer.getHeldItem();
            if( heldItem != null && heldItem.getItem() instanceof ItemCrossbow){
                Minecraft.getMinecraft().thePlayer.openGui(MineFantasyII.instance, 1, Minecraft.getMinecraft().thePlayer.worldObj, 1, 0, 0);
            }
        }
        if (crossBowScope.isPressed())
        {
            ItemStack heldItem = Minecraft.getMinecraft().thePlayer.getHeldItem();
            if( heldItem != null && heldItem.getItem() instanceof ItemCrossbow){
                // Open scope
            }
        }


    }

}
