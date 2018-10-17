package Practicas_Laboratorio.src.practica3;

import Practicas_Laboratorio.src.practica3.EntregableCasa.EjemploMuestraPrimosEnVector2a;

import java.util.concurrent.atomic.AtomicInteger;

public class MiHebraPrimoDistDinamica extends Thread{
    int miid;
    int numHebras;
    long[] vectorNumeros;
    AtomicInteger indice;

    public MiHebraPrimoDistDinamica(int miid, int numHebras, long[] vectorNumeros, AtomicInteger indice) {
        this.miid=miid;
        this.numHebras=numHebras;
        this.vectorNumeros=vectorNumeros;
        this.indice=indice;
    }
    public void run() {

        // bucle for equivalente al while for(int a= indice.getAndIncrement();a<N; a=indice.getAndIncrement())
        int a=indice.getAndIncrement();
        while (a<vectorNumeros.length) {
            if(EjemploMuestraPrimosEnVector2a.esPrimo(vectorNumeros[a])) {
                System.out.println( "  Encontrado primo: " + vectorNumeros[a] );
            }
            a=indice.getAndIncrement();
        }


    }
}
