package Paquete.Beans;

import org.neuroph.core.NeuralNetwork;

public class ConexionNeurona {
        
        //Cargar red neuronal
        private String neuralNetworkModelFilePath = "RedSustancias.nnet";
        NeuralNetwork neuralNetwork = NeuralNetwork.createFromFile("Elementos\\Neurona\\RedSustancias.nnet");
        public int ent1;
        public int ent2;
        public int ent3;
        public int ent4;
        public int ent5;
        public int ent6;
        public int ent7;
        public int ent8;
        public int ent9;
        public double[] networkOutput;
        
        public void variables(int e1,int e2,int e3, int e4, int e5, int e6, int e7, int e8, int e9){
            ent1=e1;
            ent2=e2;
            ent3=e3;
            ent4=e4;
            ent5=e5;
            ent6=e6;
            ent7=e7;
            ent8=e8;
            ent9=e9;
        }
        
        public void RedNeuronal(){
            //Entradas de la red neuronal
            neuralNetwork.setInput(ent1, ent2, ent3, ent4, ent5, ent6, ent7, ent8, ent9);    
        
            //Calcular semaforo
            neuralNetwork.calculate();
        
            //Salida del semaforo
            networkOutput = neuralNetwork.getOutput();
        
            System.out.println("Salida Importante: " + networkOutput[0] + "," + 
                               networkOutput[1] + "," + networkOutput[2]);
        }
}

