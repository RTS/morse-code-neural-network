package com.morsecode.decay;

public class InverseTimeDecay implements DecayFunction {
	private double initialLearningRate;
	private double decayRate;

	public InverseTimeDecay(double initialLearningRate, double decayRate) {
		this.initialLearningRate = initialLearningRate;
		this.decayRate = decayRate;
	}

	@Override
	public double getLearningRate(int epoch) {
		return initialLearningRate / (1 + decayRate * epoch);
	}
}

