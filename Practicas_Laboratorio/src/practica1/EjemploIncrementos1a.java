package practica1;

// ============================================================================
class CuentaIncrementos1a {
    // ============================================================================
    long contador = 0;

    // --------------------------------------------------------------------------
    void incrementaContador() {
        contador++;
    }

    // --------------------------------------------------------------------------
    long dameContador() {
        return (contador);
    }
}


// ============================================================================
class EjemploIncrementos1a {
// ============================================================================

    // --------------------------------------------------------------------------
    public static void main(String args[]) {
        int numHebras;

        // Comprobacion y extraccion de los argumentos de entrada.
        if (args.length != 1) {
            System.err.println("Uso: java programa <numHebras>");
            System.exit(-1);
        }
        try {
            numHebras = Integer.parseInt(args[0]);

            CuentaIncrementos1a contador = new CuentaIncrementos1a(); //CREAMOS EL OBJETO CONTADOR
            System.out.println(contador.dameContador());  //MOSTRAMOS POR PANTALLA EL VALOR DEL CONTADOR

            for (int i = 0; i < numHebras; i++) {

                MiHebraEjemploIncrementos h = new MiHebraEjemploIncrementos(i, contador);  //CREAMOS LAS HEBRAS
                h.start();    //INICIAMOS LAS HEBRAS

                try {
                    h.join(); //ESPERAMOS A QUE LA HEBRA ACTUAL TERMINE
                } catch (InterruptedException e) {
                    e.printStackTrace();  //SI OCURRE UNA EXCEPCION LA IMPRIMIMOS POR PANTALLA
                }

            }

        } catch (NumberFormatException ex) {
            numHebras = -1;
            System.out.println("ERROR: Argumentos numericos incorrectos.");
            System.exit(-1);
        }

        System.out.println("numHebras: " + numHebras);
    }
}