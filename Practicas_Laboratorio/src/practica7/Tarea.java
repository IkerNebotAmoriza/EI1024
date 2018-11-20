package Practicas_Laboratorio.src.practica7;

public class Tarea {
    private boolean esVeneno;
    private int codPueblo;

    public Tarea(boolean esVeneno, int codPueblo) {
        this.esVeneno = esVeneno;
        this.codPueblo = codPueblo;
    }

    public boolean esVeneno() {
        return this.esVeneno;
    }

    public int getCodPueblo()  {
        return this.codPueblo;
    }
}
