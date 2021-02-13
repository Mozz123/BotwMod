package botwmod.setup;

import botwmod.BotwMod;
import botwmod.client.render.entity.projectile.BombEntityRender;
import botwmod.client.render.entity.projectile.MasterSwordBeamEntityRender;
import botwmod.client.render.entity.projectile.arrow.BombArrowRender;
import botwmod.client.render.entity.projectile.arrow.IceArrowRender;
import botwmod.registry.ModEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = BotwMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {

    public static void init(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.MASTER_SWORD.get(), manager -> new MasterSwordBeamEntityRender(manager));
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.BOMB.get(), manager -> new BombEntityRender(manager));
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.BOMB_ARROW.get(), manager -> new BombArrowRender(manager));
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.ICE_ARROW.get(), manager -> new IceArrowRender(manager));
    }
}