package Practicas_Laboratorio.src.practica1;

public class MiHebraEjemploIncrementos extends Thread {

    private int miId;
    CuentaIncrementos1a contador;

    public MiHebraEjemploIncrementos(int miId) {  //CONSTRUCTOR SI SOLO SE LE PASA SU ID
        this.miId=miId;
    }

    public MiHebraEjemploIncrementos(int miId, CuentaIncrementos1a contador) {    //CONSTRUCTOR SI LE PASAMOS SU ID Y EL CONTADOR

        this.miId=miId;
        this.contador=contador;

    }

    public void run() {

        System.out.println("Hola soy la hebra: "+miId+" y voy a empezar a contar desde: "+contador.dameContador());
        for (int i=0; i < 1000000; i++) {

            contador.incrementaContador();

        }
        System.out.println("Hola soy la hebra: "+miId+" y he acabado de contar en el numero: "+contador.dameContador());
    }

}
