package com.morsecode.data;

import java.util.HashMap;
import java.util.Map;

public class MorseCode {
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
}
