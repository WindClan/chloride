package org.featherwhisker.fixes.mixin;

import net.minecraft.util.ChatUtil;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.network.AbstractClientPlayerEntity;

import org.spongepowered.asm.mixin.Overwrite;

@Mixin(AbstractClientPlayerEntity.class)
public class AbstractClientPlayerEntityMixin {
	/**
	 * @author Featherwhisker
	 * @reason Replaces old skin call with new one
	 */
	@Overwrite
	public static String method_5717(String string) {
		return String.format("http://skins.betacraft.uk/MinecraftSkins/%s.png", ChatUtil.stripTextFormat(string));
	}

	/**
	 * @author Featherwhisker
	 * @reason Replaces old cape call with new one
	 */
	@Overwrite
	public static String method_5718(String string) {
		return String.format("http://skins.betacraft.uk/MinecraftCloaks/%s.png", ChatUtil.stripTextFormat(string));
	}
}
