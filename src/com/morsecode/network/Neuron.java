package com.morsecode.network;

public class Neuron {
	public double[] weights;
	public double bias;
	public double output;
	public double delta;
	ActivationFunction activationFunction;

	public Neuron(int inputSize, ActivationFunction activationFunction) {
		this.activationFunction = activationFunction;
		weights = new double[inputSize];
		// Initialize weights and bias
		for (int i = 0; i < inputSize; i++) {
			weights[i] = Math.random() - 0.5;
		}
		bias = Math.random() - 0.5;
	}

	public double activate(double[] inputs) {
		double sum = bias;
		for (int i = 0; i < weights.length; i++) {
			sum += weights[i] * inputs[i];
		}
		output = activationFunction.activate(sum);
		return output;
	}
}
