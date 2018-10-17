package Practicas_Laboratorio.src.practica3.EntregableCasa;

import static Practicas_Laboratorio.src.practica3.EntregableCasa.EjemploMuestraPrimosEnVector2a.esPrimo;


public class MiHebraPrimoDistCiclica extends Thread{
    int miId;
    int numHebras;
    long [] vectorNumeros;

    public MiHebraPrimoDistCiclica(int miId, int numHebras, long [] vectorNumeros){
        this.miId = miId;
        this.numHebras = numHebras;
        this.vectorNumeros = vectorNumeros;
    }

    @Override
    public void run(){
        for (int i = miId; i < vectorNumeros.length; i+=numHebras) {
            if(esPrimo(vectorNumeros[i])) {
                System.out.println("  Encontrado primo: "+vectorNumeros[i]);
            }
        }
    }
}
