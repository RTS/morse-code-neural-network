package com.morsecode.network;

import com.morsecode.activation.ActivationFunction;
import com.morsecode.initialization.InitializationFunction;

public class Layer {

	private Neuron[] neurons;
	private double[] outputs;
	private ActivationFunction activationFunction;
	private InitializationFunction initializationFunction;

	public Neuron[] getNeurons() {
		return neurons;
	}

	public void setNeurons(Neuron[] neurons) {
		this.neurons = neurons;
	}

	public double[] getOutputs() {
		return outputs;
	}

	public void setOutputs(double[] outputs) {
		this.outputs = outputs;
	}

	public ActivationFunction getActivationFunction() {
		return activationFunction;
	}

	public void setActivationFunction(ActivationFunction activationFunction) {
		this.activationFunction = activationFunction;
	}

	public InitializationFunction getInitializationFunction() {
		return initializationFunction;
	}

	public void setInitializationFunction(InitializationFunction initializationFunction) {
		this.initializationFunction = initializationFunction;
	}

	public Layer(
					int neuronCount,
					int inputSize,
					ActivationFunction activationFunction,
					InitializationFunction initializationFunction
	) {
		neurons = new Neuron[neuronCount];
		this.activationFunction = activationFunction;
		this.initializationFunction = initializationFunction;
		for(int i = 0; i < neuronCount; i++) {
			neurons[i] = new Neuron(initializationFunction.init(inputSize), 0., activationFunction);
		}
	}

	public double[] forward(double[] inputs) {
		outputs = new double[neurons.length];
		for(int i = 0; i < neurons.length; i++) {
			outputs[i] = neurons[i].activate(inputs);
		}
		return outputs;
	}

}

