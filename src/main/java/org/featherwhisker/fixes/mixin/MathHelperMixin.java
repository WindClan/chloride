package org.featherwhisker.fixes.mixin;

import net.minecraft.util.math.MathHelper;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

// This code was not written by me
// It was adapted from FpsPlus by abandenz
// Original mod is here https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/1290619-freeuse-no-permission-needed-fpsplus-1-5-2-1-7-10

@Mixin(MathHelper.class)
public class MathHelperMixin {
	private static final float PI = 3.1415927f;
	static private final int SIN_BITS = 14; // 16KB. Adjust for accuracy.
	static private final int SIN_MASK = ~(-1 << SIN_BITS);
	static private final int SIN_COUNT = SIN_MASK + 1;
	static private final float radFull = PI * 2;
	static private final float degFull = 360;
	static private final float radToIndex = SIN_COUNT / radFull;
	static private final float degToIndex = SIN_COUNT / degFull;
	static private final float degreesToRadians = PI / 180;
	static private final float[] table = new float[SIN_COUNT];
	static {
		for (int i = 0; i < SIN_COUNT; i++)
			table[i] = (float)Math.sin((i + 0.5f) / SIN_COUNT * radFull);
		for (int i = 0; i < 360; i += 90)
			table[(int)(i * degToIndex) & SIN_MASK] = (float)Math.sin(i * degreesToRadians);
	}

	/** Returns the sine in radians from a lookup table. */
	/**
	 * @author N/A
	 * @reason Removing log spam from Fabric
	 */
	@Overwrite
	static public final float sin(float radians) {
		return table[(int)(radians * radToIndex) & SIN_MASK];
	}

	/** Returns the cosine in radians from a lookup table. */
	/**
	 * @author N/A
	 * @reason Removing log spam from Fabric
	 */
	@Overwrite
	static public final float cos (float radians) {
		return table[(int)((radians + PI / 2) * radToIndex) & SIN_MASK];
	}
}
