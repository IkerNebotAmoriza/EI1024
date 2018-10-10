package Practicas_Laboratorio.src.practica2.EntregableCasa;

import static Practicas_Laboratorio.src.practica2.EntregableCasa.EjemploFuncionCostosa1a.evaluaFuncion;

public class MiHebraFuncionCostosaCiclica extends Thread{
    double [] vectorX, vectorY;
    int miId, n, nHebras;

    public MiHebraFuncionCostosaCiclica(int miId, int n, int nHebras, double [] vectorX, double [] vectorY) {
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
