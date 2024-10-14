package com.morsecode.network;

public class NeuralNetwork {
	private Layer inputLayer;
	private Layer hiddenLayer;
	private Layer outputLayer;
	private double learningRate;

	public NeuralNetwork(int inputSize, int hiddenSize, int outputSize, double learningRate) {
		this.learningRate = learningRate;
		inputLayer = new Layer(inputSize, inputSize); // Identity layer
		hiddenLayer = new Layer(hiddenSize, inputSize);
		outputLayer = new Layer(outputSize, hiddenSize);
	}

	public double[] predict(double[] inputs) {
		double[] hiddenOutputs = hiddenLayer.forward(inputs);
		double[] finalOutputs = outputLayer.forward(hiddenOutputs);
		return finalOutputs;
	}

	public void train(double[] inputs, double[] targets) {
		// Forward pass
		double[] hiddenOutputs = hiddenLayer.forward(inputs);
		double[] finalOutputs = outputLayer.forward(hiddenOutputs);

		// Calculate output layer errors
		double[] outputErrors = new double[finalOutputs.length];
		for (int i = 0; i < outputErrors.length; i++) {
			outputErrors[i] = targets[i] - finalOutputs[i];
		}

		// Calculate output layer deltas
		for (int i = 0; i < outputLayer.neurons.length; i++) {
			Neuron neuron = outputLayer.neurons[i];
			neuron.delta = outputErrors[i] * ActivationFunction.sigmoidDerivative(neuron.output);
		}

		// Calculate hidden layer errors
		double[] hiddenErrors = new double[hiddenOutputs.length];
		for (int i = 0; i < hiddenLayer.neurons.length; i++) {
			double error = 0.0;
			for (int j = 0; j < outputLayer.neurons.length; j++) {
				error += outputLayer.neurons[j].weights[i] * outputLayer.neurons[j].delta;
			}
			hiddenErrors[i] = error;
		}

		// Calculate hidden layer deltas
		for (int i = 0; i < hiddenLayer.neurons.length; i++) {
			Neuron neuron = hiddenLayer.neurons[i];
			neuron.delta = hiddenErrors[i] * ActivationFunction.sigmoidDerivative(neuron.output);
		}

		// Update output layer weights
		for (int i = 0; i < outputLayer.neurons.length; i++) {
			Neuron neuron = outputLayer.neurons[i];
			for (int j = 0; j < neuron.weights.length; j++) {
				neuron.weights[j] += learningRate * neuron.delta * hiddenOutputs[j];
			}
			neuron.bias += learningRate * neuron.delta;
		}

		// Update hidden layer weights
		for (int i = 0; i < hiddenLayer.neurons.length; i++) {
			Neuron neuron = hiddenLayer.neurons[i];
			for (int j = 0; j < neuron.weights.length; j++) {
				neuron.weights[j] += learningRate * neuron.delta * inputs[j];
			}
			neuron.bias += learningRate * neuron.delta;
		}
	}
}
