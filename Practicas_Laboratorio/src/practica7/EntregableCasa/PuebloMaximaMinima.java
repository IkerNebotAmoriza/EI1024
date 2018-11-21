package Practicas_Laboratorio.src.practica7.EntregableCasa;

public class PuebloMaximaMinima {
    String poblacion;
    int codigo, max, min;


    // --------------------------------------------------------------------------
    public PuebloMaximaMinima() {
        poblacion = null;
        codigo = -1;
        max = -1;
        min = -1;
    }

    public PuebloMaximaMinima(String poblacion, int codigo, int max, int min) {
        this.poblacion = poblacion;
        this.codigo = codigo;
        this.max = max;
        this.min = min;
    }

    // --------------------------------------------------------------------------
    public synchronized void actualizaMaxMin(String poblacion, int codigo, int max, int min) {
        if ((this.poblacion == null) || ((this.max - this.min) < (max - min)) ||
                (((this.max - this.min) == (max - min)) && (this.min > min)) ||
                (((this.max - this.min) == (max - min)) && (this.min == min) && (this.codigo > codigo))
        ) {
            //      (((this.max-this.min) == (max-min)) && (this.max < max))) {
            this.poblacion = poblacion;
            this.codigo = codigo;
            this.max = max;
            this.min = min;
        }
    }

    // --------------------------------------------------------------------------
    public synchronized String damePueblo() {
        return this.poblacion + "(" + this.codigo + ")";
    }

    // --------------------------------------------------------------------------
    public synchronized int dameCodigo() {
        return this.codigo;
    }

    // --------------------------------------------------------------------------
    public synchronized int dameTemperaturaMaxima() {
        return this.max;
    }

    // --------------------------------------------------------------------------
    public synchronized int dameTemperaturaMinima() {
        return this.min;
    }
}