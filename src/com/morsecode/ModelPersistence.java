package com.morsecode;

import com.morsecode.activation.ActivationFunction;
import com.morsecode.initialization.InitializationFunction;
import com.morsecode.network.Layer;
import com.morsecode.network.NeuralNetwork;
import com.morsecode.network.Neuron;
import com.morsecode.util.Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ModelPersistence {

	public static String generateModelFileName(NeuralNetwork neuralNetwork) {
		StringBuilder sb = new StringBuilder();
		sb.append("model_");
		for(Layer layer : neuralNetwork.getLayers()) {
			sb.append(layer.getNeurons().length).append("-");
		}
		sb.deleteCharAt(sb.length() - 1); // Remove trailing '-'
		sb.append("_");
		sb.append(neuralNetwork.getInitialLearningRate());
		sb.append(".txt");
		return sb.toString();
	}

	public static void saveModel(String fileName, NeuralNetwork neuralNetwork) {
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			// Write learning rate
			writer.write("LearningRate: " + neuralNetwork.getInitialLearningRate());
			writer.newLine();
			writer.newLine();

			// Write details of each layer
			for(int i = 0; i < neuralNetwork.getLayers().size(); i++) {
				Layer layer = neuralNetwork.getLayers().get(i);
				writer.write("InitLayer " + i + " - Size: " + layer.getNeurons().length +
								             ", Activation: " + layer.getActivationFunction().getClass().getSimpleName() +
								             ", Init: " + layer.getInitializationFunction().getClass().getSimpleName());
				writer.newLine();
			}

			writer.newLine();
			for(int i = 0; i < neuralNetwork.getLayers().size(); i++) {
				Layer layer = neuralNetwork.getLayers().get(i);
				writer.write("Layer " + i);
				writer.newLine();
				for(int j = 0; j < layer.getNeurons().length; j++) {
					Neuron neuron = layer.getNeurons()[j];
					writer.write("Neuron " + j);
					writer.newLine();
					writer.write("Weights:");
					for(double weight : neuron.getWeights()) {
						writer.write(weight + ",");
					}
					writer.newLine();
					writer.write("Bias:" + neuron.getBias());
					writer.newLine();
				}
				writer.newLine();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadModel(String fileName, NeuralNetwork neuralNetwork) {
		try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			neuralNetwork.getLayers().clear(); // Clear any existing layers
			String line;

			// Read the learning rate
			line = reader.readLine();
			if (line != null && line.startsWith("LearningRate:")) {
				neuralNetwork.setLearningRate(Double.parseDouble(line.split(":")[1].trim()));
			}

			// Skip empty line
			reader.readLine();

			// Read layer initialization information
			while((line = reader.readLine()) != null && line.startsWith("InitLayer")) {
				String[] parts = line.split(",");

				// Parse layer index and size
				String[] layerInfo = parts[0].split("-");
				int layerSize = Integer.parseInt(layerInfo[1].trim().split(":")[1].trim());

				// Parse activation function
				String activationName = parts[1].trim().split(":")[1].trim();
				ActivationFunction activationFunction = Utils.getActivationFunctionByName(activationName);

				// Parse initialization function
				String initName = parts[2].trim().split(":")[1].trim();
				InitializationFunction initializationFunction = Utils.getInitializationFunctionByName(initName);

				// Determine input size based on previous layer (or default to size for first layer)
				int inputSize = neuralNetwork.getLayers().isEmpty() ? layerSize :
								neuralNetwork.getLayers().get(neuralNetwork.getLayers().size() - 1).getNeurons().length;

				// Create and add layer to the network
				Layer layer = new Layer(layerSize, inputSize, activationFunction, initializationFunction);
				neuralNetwork.getLayers().add(layer);
			}

			// Now we read the weights and biases of each neuron
			int currentLayer = -1;
			Layer layer = null;
			while((line = reader.readLine()) != null) {
				if (line.startsWith("Layer")) {
					// New layer, increment layer index
					currentLayer++;
					layer = neuralNetwork.getLayers().get(currentLayer);
				} else if (line.startsWith("Neuron")) {
					// Read neuron weights
					int neuronIndex = Integer.parseInt(line.split(" ")[1]);
					Neuron neuron = layer.getNeurons()[neuronIndex];

					// Read weights
					line = reader.readLine();
					if (line != null && line.startsWith("Weights:")) {
						String[] weightStrings = line.split(":")[1].split(",");
						for(int i = 0; i < neuron.getWeights().length; i++) {
							neuron.getWeights()[i] = Double.parseDouble(weightStrings[i]);
						}
					}

					// Read bias
					line = reader.readLine();
					if (line != null && line.startsWith("Bias:")) {
						neuron.setBias(Double.parseDouble(line.split(":")[1]));
					}
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
