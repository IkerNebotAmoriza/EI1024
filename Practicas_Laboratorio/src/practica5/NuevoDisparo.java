package Practicas_Laboratorio.src.practica5;

public class NuevoDisparo {
    double velocidad;
    double angulo;
    ProyectilBloqueante p;

    public NuevoDisparo(double velocidad, double angulo) {
        this.velocidad = velocidad;
        this.angulo = angulo;
        this.p = new ProyectilBloqueante(velocidad, angulo);
    }
    public ProyectilBloqueante getProyectil(){
        return this.p;
    }
}
