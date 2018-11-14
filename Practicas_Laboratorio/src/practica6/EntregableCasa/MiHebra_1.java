package Practicas_Laboratorio.src.practica6.EntregableCasa;

import java.util.HashMap;
import java.util.Vector;

public class MiHebra_1 extends Thread{
    private int miId;
    private int numHebras;
    Vector<String> vectorLineas;
    HashMap<String, Integer> hmCuentaPalabras;

    public MiHebra_1(int miId, int numHebras, Vector<String> vectorLineas, HashMap<String, Integer> hmCuentaPalabras) {
        this.miId = miId;
        this.numHebras = numHebras;
        this.vectorLineas = vectorLineas;
        this.hmCuentaPalabras = hmCuentaPalabras;
    }

    synchronized private static void contabilizaPalabra1(HashMap<String,Integer> cuentaPalabras, String palabra ) {
        Integer numVeces = cuentaPalabras.get( palabra );
        if( numVeces != null ) {
            cuentaPalabras.put( palabra, numVeces+1 );
        } else {
            cuentaPalabras.put( palabra, 1 );
        }
    }

    @Override
    public void run(){
        String palabraActual;
        for( int i = miId; i < vectorLineas.size(); i += numHebras ) {
            // Procesa la linea "i".
            String[] palabras = vectorLineas.get( i ).split( "\\W+" );
            for( int j = 0; j < palabras.length; j++ ) {
                // Procesa cada palabra de la linea "i", si es distinta de blancos.
                palabraActual = palabras[ j ].trim();
                if( palabraActual.length() > 0 ) {
                    contabilizaPalabra1( hmCuentaPalabras, palabraActual );
                }
            }
        }
    }
}
