package com.morsecode.network;

public class Layer {
	public Neuron[] neurons;

	public Layer(int neuronCount, int inputSize) {
		neurons = new Neuron[neuronCount];
		for (int i = 0; i < neuronCount; i++) {
			neurons[i] = new Neuron(inputSize);
		}
	}

	public double[] forward(double[] inputs) {
		double[] outputs = new double[neurons.length];
		for (int i = 0; i < neurons.length; i++) {
			outputs[i] = neurons[i].activate(inputs);
		}
		return outputs;
	}
}
