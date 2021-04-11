package botwmod.setup;

import botwmod.BotwMod;
import botwmod.client.render.entity.layer.FrozenEffectLayer;
import botwmod.client.render.entity.projectile.BombEntityRender;
import botwmod.client.render.entity.projectile.MasterSwordBeamEntityRender;
import botwmod.client.render.entity.projectile.arrow.BombArrowRender;
import botwmod.client.render.entity.projectile.arrow.FireArrowRender;
import botwmod.client.render.entity.projectile.arrow.IceArrowRender;
import botwmod.client.render.entity.projectile.arrow.ShockArrowRender;
import botwmod.client.render.tile.SwordPedestalTESR;
import botwmod.registry.ModEntities;
import botwmod.registry.ModTiles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Map;

@Mod.EventBusSubscriber(modid = BotwMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {

    public static void init(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.MASTER_SWORD.get(), manager -> new MasterSwordBeamEntityRender(manager));
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.BOMB.get(), manager -> new BombEntityRender(manager));
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.BOMB_ARROW.get(), manager -> new BombArrowRender(manager));
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.ICE_ARROW.get(), manager -> new IceArrowRender(manager));
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.FIRE_ARROW.get(), manager -> new FireArrowRender(manager));
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.SHOCK_ARROW.get(), manager -> new ShockArrowRender(manager));
        ClientRegistry.bindTileEntityRenderer(ModTiles.SWORD_PEDESTAL.get(), SwordPedestalTESR::new);
        for (Map.Entry<EntityType<?>, EntityRenderer<?>> entry : Minecraft.getInstance().getRenderManager().renderers.entrySet()) {
            EntityRenderer render = entry.getValue();
            if (render instanceof LivingRenderer) {
                ((LivingRenderer) render).addLayer(new FrozenEffectLayer((LivingRenderer) render));
            }
        }
    }
}