package botwmod.registry;

import botwmod.BotwMod;
import botwmod.entity.projectile.BombEntity;
import botwmod.entity.projectile.MasterSwordBeamEntity;
import botwmod.entity.projectile.arrows.BombArrowEntity;
import botwmod.entity.projectile.arrows.FireArrowEntity;
import botwmod.entity.projectile.arrows.IceArrowEntity;
import botwmod.entity.projectile.arrows.ShockArrowEntity;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static botwmod.BotwMod.REGISTRATE;

public class ModEntities {
    private static final Logger LOGGER = LogManager.getLogger(BotwMod.MODID + "-Entities");

    // Projectiles
    public static final RegistryEntry<EntityType<MasterSwordBeamEntity>> MASTER_SWORD = REGISTRATE
            .<MasterSwordBeamEntity>entity("master_sword", MasterSwordBeamEntity::new, EntityClassification.MISC)
            .properties(prop -> prop.size(0.5F, 0.5F).setCustomClientFactory(MasterSwordBeamEntity::new))
            .register();

    public static final RegistryEntry<EntityType<BombEntity>> BOMB = REGISTRATE
            .<BombEntity>entity("bomb", BombEntity::new, EntityClassification.MISC)
            .properties(prop -> prop.size(0.5F, 0.5F))
            .register();

    public static final RegistryEntry<EntityType<BombArrowEntity>> BOMB_ARROW = REGISTRATE
            .<BombArrowEntity>entity("bomb_arrow", BombArrowEntity::new, EntityClassification.MISC)
            .properties(prop -> prop.size(0.5F, 0.5F).setCustomClientFactory(BombArrowEntity::new))
            .register();

    public static final RegistryEntry<EntityType<IceArrowEntity>> ICE_ARROW = REGISTRATE
            .<IceArrowEntity>entity("ice_arrow", IceArrowEntity::new, EntityClassification.MISC)
            .properties(prop -> prop.size(0.5F, 0.5F).setCustomClientFactory(IceArrowEntity::new))
            .register();

    public static final RegistryEntry<EntityType<FireArrowEntity>> FIRE_ARROW = REGISTRATE
            .<FireArrowEntity>entity("fire_arrow", FireArrowEntity::new, EntityClassification.MISC)
            .properties(prop -> prop.size(0.5F, 0.5F).setCustomClientFactory(FireArrowEntity::new))
            .register();

    public static final RegistryEntry<EntityType<ShockArrowEntity>> SHOCK_ARROW = REGISTRATE
            .<ShockArrowEntity>entity("lightning_arrow", ShockArrowEntity::new, EntityClassification.MISC)
            .properties(prop -> prop.size(0.5F, 0.5F).setCustomClientFactory(ShockArrowEntity::new))
            .register();

    public static void load() {
        LOGGER.info("Entities Registered");
    }
}