package com.morsecode.app;

import com.morsecode.network.NeuralNetwork;

public class Tester {
	public static void main(String[] args) {
		// Assume the neural network is trained and saved in 'nn'
		NeuralNetwork nn = new NeuralNetwork(5, 10, 36, 0.5);
		// Load weights if saved (not implemented here)

		// Example Morse code input
		String morseInput = "-";
		double[] inputVector = encodeMorseCode(morseInput);

		double[] outputVector = nn.predict(inputVector);

		String predictedLetter = decodeOutput(outputVector);
		System.out.println("Predicted Letter: " + predictedLetter);
	}

	private static double[] encodeMorseCode(String morseCode) {
		int maxLength = 5; // Should match the encoding in com.morsecode.data.DataSet
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

	private static String decodeOutput(double[] outputVector) {
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

	private static String getLetterFromIndex(int index) {
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
