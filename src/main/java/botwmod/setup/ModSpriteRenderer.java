package botwmod.setup;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IRendersAsItem;

public class ModSpriteRenderer<T extends Entity & IRendersAsItem> extends SpriteRenderer<T> {
    public ModSpriteRenderer(EntityRendererManager renderManagerIn) {

        super(renderManagerIn, Minecraft.getInstance().getItemRenderer());
    }
}