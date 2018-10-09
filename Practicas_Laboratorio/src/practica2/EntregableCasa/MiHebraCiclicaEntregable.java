package practica2.EntregableCasa;

public class MiHebraCiclicaEntregable extends Thread{
    int miId, nHebras, num;

    public MiHebraCiclicaEntregable(int miId, int nHebras, int num) {
        this.miId = miId;
        this.nHebras = nHebras;
        this.num=num;
    }
    @Override
    public void run() {
        for ( int i = miId; i < num; i += nHebras) {
            System.out.println("Hola, soy la hebra "+miId+" e imprimo el número: "+i);
        }
    }
}