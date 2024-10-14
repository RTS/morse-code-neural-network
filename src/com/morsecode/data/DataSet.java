package com.morsecode.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataSet {
	public List<double[]> inputs;
	public List<double[]> outputs;

	public DataSet() {
		inputs = new ArrayList<>();
		outputs = new ArrayList<>();

		for (Map.Entry<String, String> entry : MorseCode.MORSE_TO_LETTER.entrySet()) {
			String morseCode = entry.getKey();
			String letter = entry.getValue();

			double[] inputVector = encodeMorseCode(morseCode);
			double[] outputVector = encodeLetter(letter);

			inputs.add(inputVector);
			outputs.add(outputVector);
		}
	}

	private double[] encodeMorseCode(String morseCode) {
		int maxLength = 5; // Maximum length of Morse code symbols for a character
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

	private double[] encodeLetter(String letter) {
		double[] vector = new double[36]; // 26 letters + 10 digits
		int index = getLetterIndex(letter);
		vector[index] = 1.0;
		return vector;
	}

	private int getLetterIndex(String letter) {
		if (letter.equals(" ")) {
			return 35; // Index for space
		} else if (Character.isDigit(letter.charAt(0))) {
			return 26 + (letter.charAt(0) - '0'); // Digits index from 26 to 35
		} else {
			return letter.charAt(0) - 'A'; // Letters index from 0 to 25
		}
	}
}
