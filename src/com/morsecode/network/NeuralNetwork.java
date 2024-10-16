package com.morsecode.network;

import com.morsecode.activation.ActivationFunction;
import com.morsecode.initialization.InitializationFunction;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {
	private List<Layer> layers;
	private double learningRate;
	private double initialLearningRate;

	public NeuralNetwork(double learningRate) {
		this.learningRate = learningRate;
		this.initialLearningRate = learningRate;
		this.layers = new ArrayList<>();
	}

	public List<Layer> getLayers() {
		return layers;
	}

	public void setLayers(List<Layer> layers) {
		this.layers = layers;
	}

	public double getLearningRate() {
		return learningRate;
	}

	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}

	public double getInitialLearningRate() {
		return initialLearningRate;
	}

	public void setInitialLearningRate(double initialLearningRate) {
		this.initialLearningRate = initialLearningRate;
	}

	public void addLayer(
					int neuronCount,
					ActivationFunction activationFunction,
					InitializationFunction initializationFunction
	) {
		int inputSize = layers.isEmpty() ? neuronCount : layers.get(layers.size() - 1).getNeurons().length;
		layers.add(new Layer(neuronCount, inputSize, activationFunction, initializationFunction));
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
			double[] nextErrors = new double[layer.getNeurons()[0].getWeights().length];

			for(int j = 0; j < layer.getNeurons().length; j++) {
				Neuron neuron = layer.getNeurons()[j];

				// Calculate delta
				neuron.setDelta(errors[j] * neuron.getActivationFunction().derivative(neuron.getOutput()));

				// Update weights and biases
				double[] inputsToUse = i == 0 ? inputs : layers.get(i - 1).getOutputs();
				for(int k = 0; k < neuron.getWeights().length; k++) {
					neuron.getWeights()[k] += learningRate * neuron.getDelta() * inputsToUse[k];
					nextErrors[k] += neuron.getWeights()[k] * neuron.getDelta();
				}
				neuron.setBias(neuron.getBias() + learningRate * neuron.getDelta());
			}
			errors = nextErrors;
		}
	}

}

