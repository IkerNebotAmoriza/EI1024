package Practicas_Laboratorio.src.practica7;

import Practicas_Laboratorio.src.practica7.EntregableCasa.PuebloMaximaMinima;

import java.util.concurrent.ExecutorService;

import static Practicas_Laboratorio.src.practica7.EntregableCasa.EjemploTemperaturaProvincia.ProcesaPueblo;

public class TareaPool implements Runnable{
    private int codPueblo;
    private String fecha;
    private PuebloMaximaMinima MaxMin;
    private ExecutorService pool;

    public TareaPool(int codPueblo, String fecha, PuebloMaximaMinima MaxMin, ExecutorService pool) {
        this.codPueblo = codPueblo;
        this.fecha = fecha;
        this.MaxMin = MaxMin;
        this.pool = pool;
    }

    public int getCodPueblo()  {
        return this.codPueblo;
    }

    @Override
    public void run() {
            ProcesaPueblo(fecha, getCodPueblo(), MaxMin, false);
    }
}
