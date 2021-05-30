package minefantasy.mf2.item.titanite.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import minefantasy.mf2.api.helpers.TextureHelperMF;
import minefantasy.mf2.client.render.MineFantasyHUD;
import minefantasy.mf2.item.titanite.ToolDesignScalable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.util.Arrays;

public class HUDHandler {
    private static Minecraft mc = Minecraft.getMinecraft();
    public void bindTexture(String image) {
        mc.renderEngine.bindTexture(TextureHelperMF.getResource(image));
    }
    
    @SubscribeEvent
    public void postRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.type == RenderGameOverlayEvent.ElementType.HOTBAR) {
            if (mc.thePlayer != null) {
                EntityPlayer player = mc.thePlayer;
                renderProtections(player);
            }
        }
    }
    private void renderProtections(EntityPlayer player){
        bindTexture("textures/gui/armour.png");
        ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        double sw = scaledresolution.getScaledWidth()/(float)mc.displayWidth, sh = scaledresolution.getScaledHeight()/(float)mc.displayHeight;
        double h = scaledresolution.getScaledHeight(), h2 = h - 2*64*sh;
        double w = 64*sw, w2 = 3*64*sw;
        double cw = 64*sw*2, ch = h-64*sh;
        
        
        //drawTexturedModalRect(0,0,0,0,178,178);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(w, h, 0.0f,0, 1);
        tessellator.addVertexWithUV(w2, h, 0.0f, 1, 1);
        tessellator.addVertexWithUV(w2, h2 , 0.0f, 1, 0);
        tessellator.addVertexWithUV(w, h2, 0.0f, 0, 0);
        tessellator.draw();

//        for(int i=0;i<6;i++){
//            String text = String.format("%s%d%s",BattleConfig.getResColor(i+1),(int)(100*BattleConfig.getTotalProtection(player, i+1)), "%");
//            int X = (int)Math.round(cw - w*2.0/3.0*Math.sin(i*Math.PI/3.0)) - mc.fontRenderer.getStringWidth(text) / 2;
//            int Y = (int)Math.round(ch+w*2.0/3.0*Math.cos(i*Math.PI/3.0)) -mc.fontRenderer.FONT_HEIGHT/2 ;
//            mc.fontRenderer.drawStringWithShadow(text, X, Y, 0 );
//        }
//        String text = String.format("%s%d",BattleConfig.getResColor(0),(int)(BattleConfig.getTotalProtection(player, 0)) );
        if(player != null && player.getHeldItem() != null && ToolDesignScalable.hasDesign(player.getHeldItem())){
            String[] text = ToolDesignScalable.getFormattedString(player.getHeldItem());
            mc.fontRenderer.drawStringWithShadow(Arrays.toString(text), (int)cw-mc.fontRenderer.getStringWidth((Arrays.toString(text)))/2, (int)ch-mc.fontRenderer.FONT_HEIGHT/2, 0 );
        }
    }
}
