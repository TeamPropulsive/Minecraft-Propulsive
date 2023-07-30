package com.june.propulsive.mixin;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(PlayerListEntry.class)
public final class CapeTextureMixin {

    @Shadow
    @Final
    private GameProfile profile;
    @Shadow @Final
    private Map<MinecraftProfileTexture.Type, Identifier> textures;
    @Shadow
    private boolean texturesLoaded;
    @Inject(at = @At("HEAD"), method = "loadTextures()V")
    private void CustomCapes(CallbackInfo info) {
        if(texturesLoaded) return;
        // TODO: Replace with texture and check the player
        this.textures.putIfAbsent(MinecraftProfileTexture.Type.CAPE, null);
    }
}