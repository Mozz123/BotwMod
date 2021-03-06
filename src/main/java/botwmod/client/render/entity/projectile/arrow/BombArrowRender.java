package botwmod.client.render.entity.projectile.arrow;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BombArrowRender extends ArrowRenderer {
    private static final ResourceLocation TEXTURE = new ResourceLocation("botwmod:textures/entity/bomb_arrow.png");

    public BombArrowRender(EntityRendererManager render) {
        super(render);
    }

    @Override
    public ResourceLocation getTextureLocation(Entity entity) {
        return TEXTURE;
    }
}