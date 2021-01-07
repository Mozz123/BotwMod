package botwmod.registry;

import botwmod.BotwMod;
import botwmod.entity.projectile.MasterSwordBeamEntity;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static botwmod.BotwMod.REGISTRATE;

public class ModEntities {
    private static final Logger LOGGER = LogManager.getLogger(BotwMod.MODID + "-Entities");

    public static final RegistryEntry<EntityType<MasterSwordBeamEntity>> MASTER_SWORD = REGISTRATE.<MasterSwordBeamEntity>entity("master_sword", MasterSwordBeamEntity::new, EntityClassification.MISC)
            .properties(prop -> prop.size(0.5F, 0.5F).setCustomClientFactory(MasterSwordBeamEntity::new)).register();

    public static void load() {
        LOGGER.info("Entities Registered");
    }
}