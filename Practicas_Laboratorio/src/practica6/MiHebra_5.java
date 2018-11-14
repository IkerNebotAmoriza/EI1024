package Practicas_Laboratorio.src.practica6;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MiHebra_5 extends Thread{
    private int miId;
    private int numHebras;
    Vector<String> vectorLineas;
    ConcurrentHashMap<String, AtomicInteger> chmCuentaPalabrasAtomic;

    public MiHebra_5(int miId, int numHebras, Vector<String> vectorLineas, ConcurrentHashMap<String, AtomicInteger> chmCuentaPalabrasAtomic) {
        this.miId = miId;
        this.numHebras = numHebras;
        this.vectorLineas = vectorLineas;
        this.chmCuentaPalabrasAtomic = chmCuentaPalabrasAtomic;
    }

    private static void contabilizaPalabra5(ConcurrentHashMap<String,AtomicInteger> cuentaPalabras, String palabra ) {
        AtomicInteger valorActual = cuentaPalabras.putIfAbsent(palabra, new AtomicInteger(1));
        if (valorActual != null) {
            cuentaPalabras.get(palabra).getAndIncrement();
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
                    contabilizaPalabra5( chmCuentaPalabrasAtomic, palabraActual );
                }
            }
        }
    }
}
