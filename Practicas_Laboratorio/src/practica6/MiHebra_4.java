package Practicas_Laboratorio.src.practica6;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class MiHebra_4 extends Thread{
    private int miId;
    private int numHebras;
    Vector<String> vectorLineas;
    ConcurrentHashMap<String, Integer> chmCuentaPalabras;

    public MiHebra_4(int miId, int numHebras, Vector<String> vectorLineas, ConcurrentHashMap<String, Integer> chmCuentaPalabras) {
        this.miId = miId;
        this.numHebras = numHebras;
        this.vectorLineas = vectorLineas;
        this.chmCuentaPalabras = chmCuentaPalabras;
    }

    private static void contabilizaPalabra4(ConcurrentHashMap<String,Integer> cuentaPalabras, String palabra ) {
        boolean modif;
        Integer valorActual = cuentaPalabras.putIfAbsent(palabra, 1);
        if (valorActual != null) {
            do {
                valorActual = cuentaPalabras.get(palabra);
                modif = cuentaPalabras.replace(palabra, valorActual, valorActual + 1);
            }while (!modif);
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
                    contabilizaPalabra4( chmCuentaPalabras, palabraActual );
                }
            }
        }
    }
}
