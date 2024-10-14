package com.morsecode.network;

public interface ActivationFunction {
	double activate(double x);
	double derivative(double x);
}
