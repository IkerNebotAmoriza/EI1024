package Practicas_Laboratorio.src.practica2.EntregableCasa;

import Practicas_Laboratorio.src.practica2.MiHebraFuncionCostosaBloques;

// ============================================================================
public class EjemploFuncionCostosa1a {
// ============================================================================

    // --------------------------------------------------------------------------
    public static void main(String args[]) {
        int n, numHebras;
        long t1, t2;
        double tt, tc, tb, sumaX, sumaY;

        // Comprobacion y extraccion de los argumentos de entrada.
        if (args.length != 2) {
            System.err.println("Uso: java programa <numHebras> <tamanyo>");
            System.exit(-1);
        }
        try {
            numHebras = Integer.parseInt(args[0]);
            n = Integer.parseInt(args[1]);
        } catch (NumberFormatException ex) {
            numHebras = -1;
            n = -1;
            System.out.println("ERROR: Argumentos numericos incorrectos.");
            System.exit(-1);
        }

        // Crea los vectores.
        double vectorX[] = new double[n];
        double vectorY[] = new double[n];

        //
        // Implementacion secuencial.
        //
        inicializaVectorX(vectorX);
        inicializaVectorY(vectorY);
        t1 = System.nanoTime();

        for (int i = 0; i < n; i++) {
            vectorY[i] = evaluaFuncion(vectorX[i]);
        }
        t2 = System.nanoTime();
        tt = ((double) (t2 - t1)) / 1.0e9;
        System.out.println("///////////////////////////////////////////////////////////////////////");
        System.out.println("Tiempo secuencial (seg.):                    " + tt);
        //// imprimeResultado( vectorX, vectorY );
        // Comprueba el resultado.
        sumaX = sumaVector(vectorX);
        sumaY = sumaVector(vectorY);
        System.out.println("Suma del vector X:          " + sumaX);
        System.out.println("Suma del vector Y:          " + sumaY);


        System.out.println("Fin del programa secuencial.");
        System.out.println("///////////////////////////////////////////////////////////////////////");
        //
        // Implementacion Paralela Cíclica
        //
        inicializaVectorX(vectorX);
        inicializaVectorY(vectorY);

        t1 = System.nanoTime();
        MiHebraFuncionCostosaCiclica[] vectorHebrasCiclicas = new MiHebraFuncionCostosaCiclica[numHebras];

        for (int i = 0; i < numHebras; i++) {
            //Creamos las hebras que evaluarán la función repartiendose la carga de forma cíclica
            vectorHebrasCiclicas[i] = new MiHebraFuncionCostosaCiclica(i, n, numHebras, vectorX, vectorY);
            //Ejecutamos las hebras
            vectorHebrasCiclicas[i].start();
        }

        for (int i = 0; i < vectorHebrasCiclicas.length; i++){
            try{
                //Esperamos a que todas las hebras terminen
                vectorHebrasCiclicas[i].join();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        t2 = System.nanoTime();
        tc = ((double) (t2 - t1)) / 1.0e9;
        System.out.println("Tiempo paralelo cíclico (seg.):                    " + tc);
        System.out.println("SpeedUp:                                           "+tt/tc);
        //// imprimeResultado( vectorX, vectorY );
        // Comprueba el resultado.
        sumaX = sumaVector(vectorX);
        sumaY = sumaVector(vectorY);
        System.out.println("Suma del vector X:          " + sumaX);
        System.out.println("Suma del vector Y:          " + sumaY);


        System.out.println("Fin del programa paralelo cíclico.");
        System.out.println("///////////////////////////////////////////////////////////////////////");
        //
        // Implementacion Paralela Bloques
        //
        inicializaVectorX(vectorX);
        inicializaVectorY(vectorY);

        t1 = System.nanoTime();
        MiHebraFuncionCostosaBloques [] vectorHebrasBloque = new MiHebraFuncionCostosaBloques[numHebras];

        for (int i = 0; i < numHebras; i++) {
            //Creamos las hebras que evaluarán la función repartiendose la carga de forma cíclica
            vectorHebrasBloque[i] = new MiHebraFuncionCostosaBloques(i, n, numHebras, vectorX, vectorY);
            //Ejecutamos las hebras
            vectorHebrasBloque[i].start();
        }

        for (int i = 0; i < vectorHebrasBloque.length; i++){
            try{
                //Esperamos a que todas las hebras terminen
                vectorHebrasBloque[i].join();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        t2 = System.nanoTime();
        tb = ((double) (t2 - t1)) / 1.0e9;
        System.out.println("Tiempo paralelo por bloques (seg.):                    " + tb);
        System.out.println("SpeedUp:                                               "+tt/tb);

        //// imprimeResultado( vectorX, vectorY );
        // Comprueba el resultado.
        sumaX = sumaVector(vectorX);
        sumaY = sumaVector(vectorY);
        System.out.println("Suma del vector X:          " + sumaX);
        System.out.println("Suma del vector Y:          " + sumaY);


        System.out.println("Fin del programa paralelo por bloques.");
        System.out.println("///////////////////////////////////////////////////////////////////////");
    }

    // --------------------------------------------------------------------------
    static void inicializaVectorX(double vectorX[]) {
        if (vectorX.length == 1) {
            vectorX[0] = 0.0;
        } else {
            for (int i = 0; i < vectorX.length; i++) {
                vectorX[i] = 10.0 * (double) i / ((double) vectorX.length - 1);
            }
        }
    }

    // --------------------------------------------------------------------------
    static void inicializaVectorY(double vectorY[]) {
        for (int i = 0; i < vectorY.length; i++) {
            vectorY[i] = 0.0;
        }
    }

    // --------------------------------------------------------------------------
    static double sumaVector(double vector[]) {
        double suma = 0.0;
        for (int i = 0; i < vector.length; i++) {
            suma += vector[i];
        }
        return suma;
    }

    // --------------------------------------------------------------------------
    public static double evaluaFuncion(double x) {
        return Math.sin(Math.exp(-x) + Math.log(1 + x));
    }

    // --------------------------------------------------------------------------
    static void imprimeVector(double vector[]) {
        for (int i = 0; i < vector.length; i++) {
            System.out.println(" vector[ " + i + " ] = " + vector[i]);
        }
    }

    // --------------------------------------------------------------------------
    static void imprimeResultado(double vectorX[], double vectorY[]) {
        for (int i = 0; i < Math.min(vectorX.length, vectorY.length); i++) {
            System.out.println("  i: " + i +
                    "  x: " + vectorX[i] +
                    "  y: " + vectorY[i]);
        }
    }

}

