package practica1.EntregableCasa;

public class MiHebraEntregable extends Thread{

    int miId;
    int n1, n2;

    public MiHebraEntregable(int miId, int n1, int n2) {

        this.miId = miId;
        this.n1 = n1;
        this.n2 = n2;

    }

    public void run() {

        int suma = 0;

        for (int i = n1 + 1; i < n2; i++) {

            suma += i;

        }
        System.out.println("Mi Id es " + miId + " y la suma es :" + suma);
    }

}
