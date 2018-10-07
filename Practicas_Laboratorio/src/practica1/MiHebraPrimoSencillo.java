package practica1;

public class MiHebraPrimoSencillo extends Thread{

    long numero;

    public MiHebraPrimoSencillo(long numero) {  //CONSTRUCTOR SI SE LE PASA UN NUMERO A COMPROBAR
        this.numero=numero;
    }

    public  void run() {
        System.out.println("Examinando numero: " + numero);
        boolean primo = esPrimo(numero);    //LLAMAMOS AL METODO DE COMPROBACION
        if (primo) {
            System.out.println("El numero " + numero + " SI es primo.");
        } else {
            System.out.println("El numero " + numero + " NO es primo.");
        }
    }
    static boolean esPrimo(long num) {  //CALCULA SI UN NUMERO ES PRIMO
        boolean primo;
        if (num < 2) {
            primo = false;
        } else {
            primo = true;
            long i = 2;
            while ((i < num) && (primo)) {
                primo = (num % i != 0);
                i++;
            }
        }
        return (primo);
    }
}
