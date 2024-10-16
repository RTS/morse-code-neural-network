package com.morsecode.data;

import com.morsecode.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MorseCodeDataSet {

	public static final Map<String, String> MORSE_TO_LETTER = new HashMap<>();
	public static final Map<String, String> LETTER_TO_MORSE = new HashMap<>();

	static {
		String[][] codes = {
						{"A", ".-"},    {"B", "-..."},  {"C", "-.-."},  {"D", "-.."},
						{"E", "."},     {"F", "..-."},  {"G", "--."},   {"H", "...."},
						{"I", ".."},    {"J", ".---"},  {"K", "-.-"},   {"L", ".-.."},
						{"M", "--"},    {"N", "-."},    {"O", "---"},   {"P", ".--."},
						{"Q", "--.-"},  {"R", ".-."},   {"S", "..."},   {"T", "-"},
						{"U", "..-"},   {"V", "...-"},  {"W", ".--"},   {"X", "-..-"},
						{"Y", "-.--"},  {"Z", "--.."},  {"1", ".----"}, {"2", "..---"},
						{"3", "...--"}, {"4", "....-"}, {"5", "....."}, {"6", "-...."},
						{"7", "--..."}, {"8", "---.."}, {"9", "----."}, {"0", "-----"},
						{" ", "/"},     // Using '/' to represent space between words
		};

		for (String[] code : codes) {
			String letter = code[0];
			String morse = code[1];
			MORSE_TO_LETTER.put(morse, letter);
			LETTER_TO_MORSE.put(letter, morse);
		}
	}

	public List<double[]> inputs;
	public List<double[]> outputs;

	public MorseCodeDataSet() {
		inputs = new ArrayList<>();
		outputs = new ArrayList<>();

		for (Map.Entry<String, String> entry : MorseCodeDataSet.MORSE_TO_LETTER.entrySet()) {
			String morseCode = entry.getKey();
			String letter = entry.getValue();

			double[] inputVector = Utils.encodeMorseCode(morseCode, 5);
			double[] outputVector = encodeLetter(letter);

			inputs.add(inputVector);
			outputs.add(outputVector);
		}
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
