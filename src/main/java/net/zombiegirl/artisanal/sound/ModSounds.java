package net.zombiegirl.artisanal.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.zombiegirl.artisanal.Artisanal;

public class ModSounds {
        public static final SoundEvent GREAT_SWORD_BLOCK = registerSoundEvent("great_sword_block");





    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(Artisanal.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        Artisanal.LOGGER.info("Registering Sounds for" + Artisanal.MOD_ID);
    }
}
