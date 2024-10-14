package com.morsecode.app;

import com.morsecode.data.DataSet;
import com.morsecode.network.NeuralNetwork;

public class Main {
	public static void main(String[] args) {
		// Create the dataset
		DataSet dataSet = new DataSet();

		// Initialize the neural network
		int inputSize = 5;    // Length of input vector (should match maxLength in com.morsecode.data.DataSet)
		int hiddenSize = 10;  // Number of neurons in the hidden layer
		int outputSize = 36;  // Number of possible output classes (26 letters + 10 digits)
		double learningRate = 0.5;

		NeuralNetwork nn = new NeuralNetwork(inputSize, hiddenSize, outputSize, learningRate);

		// Train the neural network
		int epochs = 100000;
		for (int epoch = 0; epoch < epochs; epoch++) {
			double totalError = 0.0;
			for (int i = 0; i < dataSet.inputs.size(); i++) {
				double[] inputs = dataSet.inputs.get(i);
				double[] targets = dataSet.outputs.get(i);

				nn.train(inputs, targets);

				double[] outputs = nn.predict(inputs);
				for (int j = 0; j < targets.length; j++) {
					double error = targets[j] - outputs[j];
					totalError += error * error;
				}
			}

			if (epoch % 1000 == 0) {
				System.out.println("Epoch " + epoch + ", Error: " + totalError);
			}
		}

		// Test the neural network with sample Morse codes
		String[] testMorseCodes = { "-","....","..","...","..","...",".-","-",".","...","-"};

		for (String morseCode : testMorseCodes) {
			double[] inputVector = encodeMorseCode(morseCode);
			double[] outputVector = nn.predict(inputVector);
			String predictedLetter = decodeOutput(outputVector);

			System.out.println("Morse Code: " + morseCode + " -> Predicted Letter: " + predictedLetter);
		}
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
