package botwmod.setup;

import botwmod.BotwMod;
import botwmod.client.render.entity.layer.FrozenEffectLayer;
import botwmod.client.render.entity.projectile.BombEntityRender;
import botwmod.client.render.entity.projectile.MasterSwordBeamEntityRender;
import botwmod.client.render.entity.projectile.arrow.BombArrowRender;
import botwmod.client.render.entity.projectile.arrow.IceArrowRender;
import botwmod.registry.ModEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraft.entity.LivingEntity;

@Mod.EventBusSubscriber(modid = BotwMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {

    public static void init(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.MASTER_SWORD.get(), manager -> new MasterSwordBeamEntityRender(manager));
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.BOMB.get(), manager -> new BombEntityRender(manager));
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.BOMB_ARROW.get(), manager -> new BombArrowRender(manager));
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.ICE_ARROW.get(), manager -> new IceArrowRender(manager));
        for (EntityRenderer<?> e : Minecraft.getInstance().getRenderManager().renderers.values()) {
            if (e instanceof LivingRenderer) {
                ((LivingRenderer) e).addLayer(new FrozenEffectLayer((LivingRenderer) e));
            }

        }
    }
}