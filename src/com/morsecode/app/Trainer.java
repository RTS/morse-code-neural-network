package com.morsecode.app;

import com.morsecode.data.DataSet;
import com.morsecode.network.NeuralNetwork;

public class Trainer {
	public static void main(String[] args) {
		DataSet dataSet = new DataSet();
		NeuralNetwork nn = new NeuralNetwork(5, 10, 36, 0.5);

		int epochs = 10000;
		for (int epoch = 0; epoch < epochs; epoch++) {
			double totalError = 0.0;
			for (int i = 0; i < dataSet.inputs.size(); i++) {
				double[] inputs = dataSet.inputs.get(i);
				double[] targets = dataSet.outputs.get(i);

				nn.train(inputs, targets);

				double[] outputs = nn.predict(inputs);
				for (int j = 0; j < targets.length; j++) {
					double error = targets[j] - outputs[j];
					totalError += error * error;
				}
			}

			if (epoch % 1000 == 0) {
				System.out.println("Epoch " + epoch + ", Error: " + totalError);
			}
		}
	}
}
