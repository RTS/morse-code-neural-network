package com.morsecode.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {
	private List<Layer> layers;
	private double learningRate;

	public NeuralNetwork(double learningRate) {
		this.learningRate = learningRate;
		this.layers = new ArrayList<>();
	}

	public void addLayer(int neuronCount, ActivationFunction activationFunction) {
		int inputSize = layers.isEmpty() ? neuronCount : layers.get(layers.size() - 1).neurons.length;
		layers.add(new Layer(neuronCount, inputSize, activationFunction));
	}

	public double[] predict(double[] inputs) {
		double[] outputs = inputs;
		for(Layer layer : layers) {
			outputs = layer.forward(outputs);
		}
		return outputs;
	}

	public void train(double[] inputs, double[] targets) {
		double[] outputs = predict(inputs);

		// Backpropagation
		double[] errors = new double[outputs.length];
		for(int i = 0; i < outputs.length; i++) {
			errors[i] = targets[i] - outputs[i];
		}

		for(int i = layers.size() - 1; i >= 0; i--) {
			Layer layer = layers.get(i);
			double[] nextErrors = new double[layer.neurons[0].weights.length];

			for(int j = 0; j < layer.neurons.length; j++) {
				Neuron neuron = layer.neurons[j];

				// Calculate delta
				neuron.delta = errors[j] * neuron.activationFunction.derivative(neuron.output);

				// Update weights and biases
				double[] inputsToUse = i == 0 ? inputs : layers.get(i - 1).getOutputs();
				for(int k = 0; k < neuron.weights.length; k++) {
					neuron.weights[k] += learningRate * neuron.delta * inputsToUse[k];
					nextErrors[k] += neuron.weights[k] * neuron.delta;
				}
				neuron.bias += learningRate * neuron.delta;
			}
			errors = nextErrors;
		}
	}

	public String generateModelFileName() {
		StringBuilder sb = new StringBuilder();
		sb.append("model_");
		for(Layer layer : layers) {
			sb.append(layer.neurons.length).append("-");
		}
		sb.deleteCharAt(sb.length() - 1); // Remove trailing '-'
		sb.append("_");
		sb.append(this.learningRate);
		sb.append(".txt");
		return sb.toString();
	}

	public void saveModel(String fileName) {
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			writer.write("LayerCount:" + layers.size());
			writer.newLine();
			writer.write("LayerSizes:");
			for(Layer layer : layers) {
				writer.write(layer.neurons.length + ",");
			}
			writer.newLine();
			writer.write("LearningRate:" + learningRate);
			writer.newLine();
			writer.newLine();

			for(int i = 0; i < layers.size(); i++) {
				Layer layer = layers.get(i);
				writer.write("Layer " + i);
				writer.newLine();
				for(int j = 0; j < layer.neurons.length; j++) {
					Neuron neuron = layer.neurons[j];
					writer.write("Neuron " + j);
					writer.newLine();
					writer.write("Weights:");
					for(double weight : neuron.weights) {
						writer.write(weight + ",");
					}
					writer.newLine();
					writer.write("Bias:" + neuron.bias);
					writer.newLine();
				}
				writer.newLine();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void loadModel(String fileName) {
		try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			layers.clear();
			String line;
			int layerCount = 0;
			int[] layerSizes = null;

			// Read LayerCount
			line = reader.readLine();
			if (line != null && line.startsWith("LayerCount:")) {
				layerCount = Integer.parseInt(line.split(":")[1]);
			}

			// Read LayerSizes
			line = reader.readLine();
			if (line != null && line.startsWith("LayerSizes:")) {
				String[] sizes = line.split(":")[1].split(",");
				layerSizes = new int[sizes.length];
				for(int i = 0; i < sizes.length; i++) {
					layerSizes[i] = Integer.parseInt(sizes[i]);
				}
			}

			// Read LearningRate
			line = reader.readLine();
			if (line != null && line.startsWith("LearningRate:")) {
				learningRate = Double.parseDouble(line.split(":")[1]);
			}

			// Skip empty line
			reader.readLine();

			// Initialize layers
			for(int i = 0; i < layerCount; i++) {
				int inputSize = (i == 0) ? layerSizes[i] : layerSizes[i - 1];
				Layer layer = new Layer(layerSizes[i], inputSize, new SigmoidActivation());
				layers.add(layer);

				// Read Layer Header
				reader.readLine(); // "Layer i"

				// Read Neurons
				for(int j = 0; j < layer.neurons.length; j++) {
					reader.readLine(); // "Neuron j"
					// Read Weights
					line = reader.readLine();
					if (line != null && line.startsWith("Weights:")) {
						String[] weightStrings = line.split(":")[1].split(",");
						for(int k = 0; k < layer.neurons[j].weights.length; k++) {
							layer.neurons[j].weights[k] = Double.parseDouble(weightStrings[k]);
						}
					}
					// Read Bias
					line = reader.readLine();
					if (line != null && line.startsWith("Bias:")) {
						layer.neurons[j].bias = Double.parseDouble(line.split(":")[1]);
					}
				}
				// Skip empty line
				reader.readLine();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}

