package org.featherwhisker.fixes.mixin;

import net.minecraft.util.math.MathHelper;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

// This class uses code adapted from FpsPlus by abandenz
// Original mod is here https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/1290619-freeuse-no-permission-needed-fpsplus-1-5-2-1-7-10

@Mixin(MathHelper.class)
public class FpsPlus {
	// Start of FpsPlus variables
	@Unique static private final float PI = 3.1415927f;
	@Unique static private final int SIN_BITS = 14; // 16KB. Adjust for accuracy.
	@Unique static private final int SIN_MASK = ~(-1 << SIN_BITS);
	@Unique static private final int SIN_COUNT = SIN_MASK + 1;
	@Unique static private final float radFull = PI * 2;
	@Unique static private final float degFull = 360;
	@Unique static private final float radToIndex = SIN_COUNT / radFull;
	@Unique static private final float degToIndex = SIN_COUNT / degFull;
	@Unique static private final float degreesToRadians = PI / 180;
	@Unique static private final float[] table = new float[SIN_COUNT];
	static {
		for (int i = 0; i < SIN_COUNT; i++)
			table[i] = (float)Math.sin((i + 0.5f) / SIN_COUNT * radFull);
		for (int i = 0; i < 360; i += 90)
			table[(int)(i * degToIndex) & SIN_MASK] = (float)Math.sin(i * degreesToRadians);
	}
	// End of FpsPlus variables

	/**
	 * @author abandenz
	 * @reason Replacing sin function
	 * Returns the sine in radians from a lookup table
	 */
	@Overwrite
	static public final float sin(float radians) {
		return table[(int)(radians * radToIndex) & SIN_MASK];
	}

	/**
	 * @author abandenz
	 * @reason Replacing cos function
	 * Returns the cosine in radians from a lookup table.
	 */
	@Overwrite
	static public final float cos (float radians) {
		return table[(int)((radians + PI / 2) * radToIndex) & SIN_MASK];
	}

	/**
	 * @author Featherwhisker
	 * @reason Nothing can go wrong, right?
	 */
	@Overwrite
	public static int floor(double d) {
		return (int)(d + 512.0) - 512;
	}

	/**
	 * @author Featherwhisker
	 * @reason This is like 1ms faster!!
	 */
	@Overwrite
	public static double absMax(double a, double b) {
		a = a >= 0.0F ? a : -a;
		b = b >= 0.0F ? b : -b;
		return a > b ? a : b;
	}
}
