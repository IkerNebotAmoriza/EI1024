package practica1.EntregableCasa;

public class EntregableCasa {

    public static void main (String[]args) {

        System.out.println("INICIO DEL PROGRAMA");
        int n1,n2;

        if (args.length != 2 ){

            System.out.println("Son necesarios 2 argumentos.");
            System.exit(-1);

        }

        n1=Integer.parseInt(args[0]);
        n2=Integer.parseInt(args[1]);

        MiHebraEntregable h1 = new MiHebraEntregable(0, n1, n2);
        MiHebraEntregable h2 = new MiHebraEntregable(1, n1, n2);
        h1.start();
        h2.start();

        try{
            h1.join();
            h2.join();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("FIN DEL PROGRAMA");
    }

}
