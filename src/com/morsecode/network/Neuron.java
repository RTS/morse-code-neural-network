package com.morsecode.network;

public class Neuron {
	public double[] weights;
	public double bias;
	public double output;
	public double delta;

	public Neuron(int inputSize) {
		weights = new double[inputSize];
		// Initialize weights and bias with small random values
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
		output = ActivationFunction.sigmoid(sum);
		return output;
	}
}
