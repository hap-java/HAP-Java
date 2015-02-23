package com.beowulfe.hap.impl.pairing;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * This class is modified from the nimbus SRP library to provide methods that are compatible
 * with some pecularities of Homekit. Namely, the need for a 3072 bit private value
 * 
 * @author Vladimir Dzhuvinov
 */
public class HomekitSRP6Routines {

	public static BigInteger generatePrivateValue(BigInteger N, SecureRandom random) {
		final int minBits = Math.min(3072, N.bitLength() / 2);
		
		BigInteger min = BigInteger.ONE.shiftLeft(minBits - 1);
		BigInteger max = N.subtract(BigInteger.ONE);
		
		return createRandomBigIntegerInRange(min, max, random);  
	}

	/**
	 * Returns a random big integer in the specified range [min, max].
	 *
	 * @param min    The least value that may be generated. Must not be
	 *               {@code null}.
	 * @param max    The greatest value that may be generated. Must not be
	 *               {@code null}.
	 * @param random Source of randomness. Must not be {@code null}.
	 *
	 * @return A random big integer in the range [min, max].
	 */
	protected static BigInteger createRandomBigIntegerInRange(final BigInteger min, 
	                                                          final BigInteger max,
	                                                          final SecureRandom random) {
	
		final int cmp = min.compareTo(max);
		
		if (cmp >= 0) {
			
			if (cmp > 0)
				throw new IllegalArgumentException("'min' may not be greater than 'max'");
		
			return min;
		}
	
		if (min.bitLength() > max.bitLength() / 2)
			return createRandomBigIntegerInRange(BigInteger.ZERO, max.subtract(min), random).add(min);
		
		final int MAX_ITERATIONS = 1000;
		
		for (int i = 0; i < MAX_ITERATIONS; ++i) {
		
			BigInteger x = new BigInteger(max.bitLength(), random);
			
			if (x.compareTo(min) >= 0 && x.compareTo(max) <= 0)
				return x;
		}
	
		// fall back to a faster (restricted) method
		return new BigInteger(max.subtract(min).bitLength() - 1, random).add(min);
	}

}
