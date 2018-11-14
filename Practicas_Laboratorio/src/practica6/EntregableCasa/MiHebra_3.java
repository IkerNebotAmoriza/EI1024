package Practicas_Laboratorio.src.practica6.EntregableCasa;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class MiHebra_3 extends Thread{
    private int miId;
    private int numHebras;
    Vector<String> vectorLineas;
    ConcurrentHashMap<String, Integer> chmCuentaPalabras;

    public MiHebra_3(int miId, int numHebras, Vector<String> vectorLineas, ConcurrentHashMap<String, Integer> chmCuentaPalabras) {
        this.miId = miId;
        this.numHebras = numHebras;
        this.vectorLineas = vectorLineas;
        this.chmCuentaPalabras = chmCuentaPalabras;
    }

    synchronized private static void contabilizaPalabra3(ConcurrentHashMap<String,Integer> cuentaPalabras, String palabra ) {
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
                    contabilizaPalabra3( chmCuentaPalabras, palabraActual );
                }
            }
        }
    }
}
