package Practicas_Laboratorio.src.practica2.EntregableCasa;

public class EntregableCasaEjercicio1_2 {
    public static void main(String[]args) {

        if (args.length != 2) {
            System.out.println("Se requieren dos argumentos. 1 = Limite superior numérico , 2 = Número de hebras requeridas.");
            System.exit(-1);
        }
        try{
            int n = Integer.parseInt(String.valueOf(args[0]));
            int h = Integer.parseInt(String.valueOf(args[1]));

            for(int i=0; i < h; i++) {
                int id = i;
                MiHebraBloquesEntregable hebra = new MiHebraBloquesEntregable(id,h,n);
                hebra.start();
            }

        }catch (NumberFormatException e) {
            System.out.println("El tipo de alguno de los argumento es incorrecto. Ambos argumentos deben ser números enteros.");
            e.printStackTrace();
            System.exit(-1);
        }
    }
}