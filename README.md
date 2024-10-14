# Morse Code Translator Using Neural Network

## Overview

This project implements a simple neural network from scratch in Java to translate Morse code sequences into English text. The neural network is built without using any external machine learning libraries, providing an educational example of how neural networks operate under the hood.

## Features

- **Vanilla Java Implementation**: No external dependencies; purely built using Java.
- **Modular Code Structure**: Organized into packages for clarity and maintainability.
- **Custom Neural Network**: Includes implementations of neurons, layers, activation functions, and the neural network itself.
- **Training and Testing**: Ability to train the network on Morse code data and test it with sample inputs.

## Project Structure

```
project_root/ 
├── src/ 
│ └── com/ 
│ └── morsecode/ 
│ ├── app/ 
│ │ └── Main.java 
│ ├── data/ 
│ │ ├── DataSet.java 
│ │ └── MorseCode.java 
│ ├── network/ 
│ │ ├── ActivationFunction.java 
│ │ ├── Layer.java 
│ │ ├── NeuralNetwork.java 
│ │ └── Neuron.java 
│ └── util/ 
│ └── Utils.java 
└── README.md
```

## Getting Started

### **Prerequisites**

- Java Development Kit (JDK) 8 or higher

### **Setup Instructions**

1. **Clone the Repository**

   ```bash
   git clone https://github.com/rts/morse-code-neural-network.git
