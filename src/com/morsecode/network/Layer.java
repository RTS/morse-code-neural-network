package com.morsecode.network;

public class Layer {
	public Neuron[] neurons;
	private double[] outputs;

	public Layer(int neuronCount, int inputSize, ActivationFunction activationFunction) {
		neurons = new Neuron[neuronCount];
		for (int i = 0; i < neuronCount; i++) {
			neurons[i] = new Neuron(inputSize, activationFunction);
		}
	}

	public double[] forward(double[] inputs) {
		outputs = new double[neurons.length];
		for (int i = 0; i < neurons.length; i++) {
			outputs[i] = neurons[i].activate(inputs);
		}
		return outputs;
	}

	public double[] getOutputs() {
		return outputs;
	}
}

