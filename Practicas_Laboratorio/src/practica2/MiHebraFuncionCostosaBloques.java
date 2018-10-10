package Practicas_Laboratorio.src.practica2;

import static Practicas_Laboratorio.src.practica2.EntregableCasa.EjemploFuncionCostosa1a.evaluaFuncion;
import static java.lang.Math.min;

public class MiHebraFuncionCostosaBloques extends Thread{
    double [] vectorX, vectorY;
    int miId, n, nHebras, tam, ini, fin;

    public MiHebraFuncionCostosaBloques(int miId, int n, int nHebras, double [] vectorX, double [] vectorY) {
        this.miId = miId;
        this.n = n;
        this.nHebras=nHebras;
        this.vectorX = vectorX;
        this.vectorY = vectorY;
    }
    @Override
    public void run(){
        tam = (n / ( nHebras - 1 ) / nHebras );
        ini = ( miId * tam );
        fin = min((ini + tam), n);

        for ( int i = ini; i < fin; i++ ) {
            vectorY[ i ] = evaluaFuncion( vectorX[ i ] );
        }
    }
}
