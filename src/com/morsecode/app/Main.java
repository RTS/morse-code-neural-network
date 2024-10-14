package com.morsecode.app;

import com.morsecode.data.DataSet;
import com.morsecode.network.NeuralNetwork;
import com.morsecode.network.SigmoidActivation;

import java.io.File;

public class Main {
	public static void main(String[] args) {
		// Initialize the dataset
		DataSet dataSet = new DataSet();

		// Create and configure the neural network
		NeuralNetwork nn = new NeuralNetwork(0.5); // Learning rate set to 0.5

		// Configure the neural network layers
		nn.addLayer(5, new SigmoidActivation());    // Input layer with 5 neurons
		nn.addLayer(20, new SigmoidActivation());   // Hidden layer with 20 neurons
		nn.addLayer(36, new SigmoidActivation());   // Output layer with 36 neurons

		// Generate model file name based on the network structure
		String modelFileName = nn.generateModelFileName();

		// Check if model file exists
		File modelFile = new File(modelFileName);
		if (modelFile.exists()) {
			// Load the model
			nn.loadModel(modelFileName);
			System.out.println("Model loaded from " + modelFileName);
		} else {

			// Train the neural network
			int epochs = 1_000_000;
			for(int epoch = 0; epoch < epochs; epoch++) {

				// learning rate schedule
				double initialLearningRate = 0.5;
				double decayRate = 0.0001;
				nn.setLearningRate(initialLearningRate / (1 + decayRate * epoch));

				double totalError = 0.0;
				for(int i = 0; i < dataSet.inputs.size(); i++) {
					double[] inputs = dataSet.inputs.get(i);
					double[] targets = dataSet.outputs.get(i);

					nn.train(inputs, targets);

					double[] outputs = nn.predict(inputs);
					for(int j = 0; j < targets.length; j++) {
						double error = targets[j] - outputs[j];
						totalError += error * error;
					}
				}

				if (epoch % 1000 == 0) {
					System.out.println("Epoch " + epoch + ", Error: " + totalError);
				}
			}

			// Save the model after training
			nn.saveModel(modelFileName);
			System.out.println("Current working directory: " + System.getProperty("user.dir"));
			System.out.println("Model saved to " + modelFileName);
		}

		// Test the neural network with sample Morse codes
		String[] testMorseCodes = {"-", "....", "..", "...", "..", "...", ".-", "-", ".", "...", "-"};

		StringBuilder message = new StringBuilder();

		for(String morseCode : testMorseCodes) {
			double[] inputVector = encodeMorseCode(morseCode);
			double[] outputVector = nn.predict(inputVector);
			String predictedLetter = decodeOutput(outputVector);

			message.append(predictedLetter);
			System.out.println("Morse Code: " + morseCode + " -> Predicted Letter: " + predictedLetter);
		}

		System.out.println("Decoded Message: " + message.toString());
	}

	private static double[] encodeMorseCode(String morseCode) {
		int maxLength = 5; // Should match the encoding in DataSet
		double[] vector = new double[maxLength];

		for(int i = 0; i < maxLength; i++) {
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
		for(int i = 0; i < outputVector.length; i++) {
			if (outputVector[i] > max) {
				max = outputVector[i];
				index = i;
			}
		}

		return getLetterFromIndex(index);
	}

	private static String getLetterFromIndex(int index) {
		if (index >= 0 && index < 26) {
			return String.valueOf((char)('A' + index));
		} else if (index >= 26 && index < 36) {
			return String.valueOf((char)('0' + index - 26));
		} else if (index == 35) {
			return " ";
		} else {
			return "?";
		}
	}
}
