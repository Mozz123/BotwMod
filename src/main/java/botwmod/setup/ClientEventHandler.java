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
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.system.CallbackI;

import java.lang.reflect.Field;
import java.util.Map;

@Mod.EventBusSubscriber(modid = BotwMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {

    public static void init(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.MASTER_SWORD.get(), manager -> new MasterSwordBeamEntityRender(manager));
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.BOMB.get(), manager -> new BombEntityRender(manager));
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.BOMB_ARROW.get(), manager -> new BombArrowRender(manager));
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.ICE_ARROW.get(), manager -> new IceArrowRender(manager));
        for (Map.Entry<EntityType<?>, EntityRenderer<?>> entry : Minecraft.getInstance().getRenderManager().renderers.entrySet()) {
            EntityRenderer render = entry.getValue();
            if (render instanceof LivingRenderer) {
                ((LivingRenderer) render).addLayer(new FrozenEffectLayer((LivingRenderer) render));
            }
        }
        for (Map.Entry<String, PlayerRenderer> entry : Minecraft.getInstance().getRenderManager().getSkinMap().entrySet()) {
            PlayerRenderer render = entry.getValue();
            render.addLayer(new FrozenEffectLayer(render));
        }
    }
}