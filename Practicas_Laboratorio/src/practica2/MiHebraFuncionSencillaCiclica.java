package Practicas_Laboratorio.src.practica2;

import static Practicas_Laboratorio.src.practica2.EjemploFuncionSencilla1a.evaluaFuncion;

public class MiHebraFuncionSencillaCiclica extends Thread{
    double [] vectorX, vectorY;
    int miId, n, nHebras;

    public MiHebraFuncionSencillaCiclica(int miId, int n, int nHebras, double [] vectorX, double [] vectorY) {
        this.miId = miId;
        this.n = n;
        this.nHebras=nHebras;
        this.vectorX = vectorX;
        this.vectorY = vectorY;
    }
    @Override
    public void run(){
        for ( int i = miId; i < n; i+=nHebras ) {
            vectorY[ i ] = evaluaFuncion( vectorX[ i ] );
        }
    }
}
