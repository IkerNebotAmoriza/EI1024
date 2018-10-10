package Practicas_Laboratorio.src.practica2.EntregableCasa;

import static java.lang.Math.min;

public class MiHebraBloquesEntregable extends Thread{
    int miId, nHebras, num, tam, ini, fin;

    public MiHebraBloquesEntregable(int miId, int nHebras, int num) {
        this.miId = miId;
        this.nHebras = nHebras;
        this.num = num;
    }
    @Override
    public void run() {
        tam = ( (num / (nHebras - 1) ) / nHebras );
        ini = ( tam * miId );
        fin = min( (ini + tam), num);

        for ( int i = ini; i < fin; i++) {
            System.out.println("Hola, soy la hebra "+miId+" e imprimo el número: "+i+" CON FIN EN:"+fin);
        }
    }
}