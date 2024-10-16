package com.morsecode.util;

import com.morsecode.activation.ActivationFunction;
import com.morsecode.activation.ReLUActivation;
import com.morsecode.activation.SigmoidActivation;
import com.morsecode.activation.TanhActivation;
import com.morsecode.initialization.InitializationFunction;
import com.morsecode.initialization.LeCunInitialization;
import com.morsecode.initialization.XavierInitialization;

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

	public static ActivationFunction getActivationFunctionByName(String name) {
		return switch(name) {
			case "SigmoidActivation" -> new SigmoidActivation();
			case "ReLUActivation" -> new ReLUActivation();
			case "TanhActivation" -> new TanhActivation();
			default -> throw new IllegalArgumentException("Unknown activation function: " + name);
		};
	}

	public static InitializationFunction getInitializationFunctionByName(String name) {
		return switch(name) {
			case "XavierInitialization" -> new XavierInitialization();
			case "LeCunInitialization" -> new LeCunInitialization();
			default -> throw new IllegalArgumentException("Unknown initialization function: " + name);
		};
	}
}
