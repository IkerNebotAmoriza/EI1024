package Practicas_Laboratorio.src.practica3;

import static Practicas_Laboratorio.src.practica3.EntregableCasa.EjemploMuestraPrimosEnVector2a.esPrimo;
import Practicas_Laboratorio.src.practica3.EntregableCasa.EjemploMuestraPrimosEnVector2a;

public class MiHebraPrimoDistPorBloques extends Thread{
    int miId;
    int numHebras;
    long [] vectorNumeros;

    public MiHebraPrimoDistPorBloques(int miId, int numHebras, long [] vectorNumeros) {
        this.miId = miId;
        this.numHebras = numHebras;
        this.vectorNumeros = vectorNumeros;
    }

    @Override
    public void run(){
        int tam = ((vectorNumeros.length)+numHebras-1)/numHebras;
        int ini = tam * miId;
        int fin = Math.min(vectorNumeros.length, tam+ini);

        for(int i=ini; i < fin; i++) {
            if(EjemploMuestraPrimosEnVector2a.esPrimo(vectorNumeros[i])){
                System.out.println("  Encontrado primo: "+vectorNumeros[i]);
            }
        }
    }

}
