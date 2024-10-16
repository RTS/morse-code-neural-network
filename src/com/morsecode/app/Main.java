package com.morsecode.app;

import com.morsecode.ModelPersistence;
import com.morsecode.activation.SigmoidActivation;
import com.morsecode.data.MorseCodeDataSet;
import com.morsecode.initialization.XavierInitialization;
import com.morsecode.network.NeuralNetwork;
import com.morsecode.util.Utils;

import java.io.File;

public class Main {

	public static void main(String[] args) {

		// Initialize the dataset
		MorseCodeDataSet dataSet = new MorseCodeDataSet();

		// Create and configure the neural network
		NeuralNetwork nn = new NeuralNetwork(0.5); // Learning rate set to 0.5

		// Configure the neural network layers
		nn.addLayer(5, new SigmoidActivation(), new XavierInitialization());    // Input layer with 5 neurons
		nn.addLayer(20, new SigmoidActivation(), new XavierInitialization());   // Hidden layer with 20 neurons
		nn.addLayer(36, new SigmoidActivation(), new XavierInitialization());   // Output layer with 36 neurons

		// Generate model file name based on the network structure
		String modelFileName = ModelPersistence.generateModelFileName(nn);

		// Check if model file exists
		File modelFile = new File(modelFileName);
		if (modelFile.exists()) {
			// Load the model
			ModelPersistence.loadModel(modelFileName, nn);
			System.out.println("Model loaded from " + modelFileName);
		} else {

			// Train the neural network
			int epochs = 10_000;
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
			ModelPersistence.saveModel(modelFileName, nn);
			System.out.println("Current working directory: " + System.getProperty("user.dir"));
			System.out.println("Model saved to " + modelFileName);
		}

		// Test the neural network with sample Morse codes
		String[] testMorseCodes = {"-", "....", "..", "...", "..", "...", ".-", "-", ".", "...", "-"};

		StringBuilder message = new StringBuilder();

		for(String morseCode : testMorseCodes) {
			double[] inputVector = Utils.encodeMorseCode(morseCode, 5);
			double[] outputVector = nn.predict(inputVector);
			String predictedLetter = Utils.decodeOutput(outputVector);

			message.append(predictedLetter);
			System.out.println("Morse Code: " + morseCode + " -> Predicted Letter: " + predictedLetter);
		}

		System.out.println("Decoded Message: " + message.toString());
	}

}
