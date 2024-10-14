package com.morsecode.util;

public class Utils {
	public static double[] encodeMorseCode(String morseCode, int maxLength) {
		double[] vector = new double[maxLength];

		for (int i = 0; i < maxLength; i++) {
			if (i < morseCode.length()) {
				char c = morseCode.charAt(i);
				if (c == '.') {
					vector[i] = 1.0;
				} else if (c == '-') {
					vector[i] = -1.0;
				} else {
					vector[i] = 0.0;
				}
			} else {
				vector[i] = 0.0; // Padding
			}
		}
		return vector;
	}

	public static String decodeOutput(double[] outputVector) {
		int index = -1;
		double max = -Double.MAX_VALUE;
		for (int i = 0; i < outputVector.length; i++) {
			if (outputVector[i] > max) {
				max = outputVector[i];
				index = i;
			}
		}

		return getLetterFromIndex(index);
	}

	public static String getLetterFromIndex(int index) {
		if (index >= 0 && index < 26) {
			return String.valueOf((char) ('A' + index));
		} else if (index >= 26 && index < 36) {
			return String.valueOf((char) ('0' + index - 26));
		} else if (index == 35) {
			return " ";
		} else {
			return "?";
		}
	}
}
