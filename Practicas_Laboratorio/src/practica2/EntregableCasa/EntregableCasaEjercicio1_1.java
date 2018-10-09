package practica2.EntregableCasa;

public class EntregableCasaEjercicio1_1 {
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
                MiHebraCiclicaEntregable hebra = new MiHebraCiclicaEntregable(id,h,n);
                hebra.start();
            }

        }catch (NumberFormatException e) {
            System.out.println("El tipo de alguno de los argumento es incorrecto. Ambos argumentos deben ser números enteros.");
            System.exit(-1);
        }
    }
}