package Practicas_Laboratorio.src.practica7.EntregableCasa;

import java.util.concurrent.LinkedBlockingQueue;

import static Practicas_Laboratorio.src.practica7.EntregableCasa.EjemploTemperaturaProvincia.ProcesaPueblo;

public class MiHebraConsumidora extends  Thread{
    String fecha;
    PuebloMaximaMinima MaxMin;
    LinkedBlockingQueue<Tarea> cola;
    Tarea tarea;

    public MiHebraConsumidora(String fecha, PuebloMaximaMinima MaxMin, LinkedBlockingQueue<Tarea> cola) {
        this.fecha = fecha;
        this.MaxMin = MaxMin;
        this.cola = cola;
    }

    @Override
    public void run() {
        try {
            tarea = cola.take();

            while (!tarea.esVeneno()) {
                ProcesaPueblo(fecha, tarea.getCodPueblo(), MaxMin, false);
                tarea = cola.take();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
