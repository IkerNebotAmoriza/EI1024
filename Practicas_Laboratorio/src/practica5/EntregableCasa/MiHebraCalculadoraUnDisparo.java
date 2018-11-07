package Practicas_Laboratorio.src.practica5.EntregableCasa;

import javax.swing.*;

public class MiHebraCalculadoraUnDisparo extends  Thread{
    CanvasCampoTiro1a canvas;
    JTextField txfMensajes;
    Proyectil1a proyectil;
    String mensaje;

    public MiHebraCalculadoraUnDisparo(CanvasCampoTiro1a canvas, JTextField txfMensajes, Proyectil1a proyectil){
        this.canvas = canvas;
        this.txfMensajes = txfMensajes;
        this.proyectil = proyectil;
    }

    @Override
    public void run(){
        while( proyectil.getEstadoProyectil() == 0 )  {

            // Muestra en pantalla los datos del proyectil p.
            proyectil.muestra();

            // Mueve el proyectil durante un incremental de tiempo.
            proyectil.mueveDuranteUnIncremental( canvas.getObjetivoX(),
                    canvas.getObjetivoY() );

            // Dibuja el proyectil.
            proyectil.dibujaProyectil( canvas );

            // Comprueba si el proyectil ha impactado contra el suelo o no.
            if( proyectil.getEstadoProyectil() != 0 ) {
                // El proyectil ha impactado contra el suelo.
                // Construye y muestra mensaje adecuado.
                if( proyectil.getEstadoProyectil() == 2 ) {
                    mensaje = "Destruido!";
                } else {
                    mensaje = "Fallado. El objetivo esta en: " +
                            canvas.getObjetivoX() +
                            "  Has disparado a: " + proyectil.getIntPosX();
                }
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        txfMensajes.setText( mensaje );
                    }
                });
            }
        }
    }
}
