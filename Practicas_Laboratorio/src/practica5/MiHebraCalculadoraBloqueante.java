package Practicas_Laboratorio.src.practica5;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class MiHebraCalculadoraBloqueante extends Thread{
    CanvasCampoTiroBloqueante canvas;
    JTextField txfMensajes;
    LinkedBlockingQueue<NuevoDisparo> ZonaDeIntercambio;
    NuevoDisparo disp;
    ArrayList<ProyectilBloqueante> enAire;

    public MiHebraCalculadoraBloqueante(CanvasCampoTiroBloqueante canvas, JTextField txfMensajes, LinkedBlockingQueue<NuevoDisparo> ZonaDeIntercambio){
        this.canvas = canvas;
        this.txfMensajes = txfMensajes;
        this.ZonaDeIntercambio = ZonaDeIntercambio;
        this.enAire = new ArrayList<ProyectilBloqueante>();
    }

    @Override
    public void run(){
        while( true )  {
            while (enAire.isEmpty() || !ZonaDeIntercambio.isEmpty()) {
                try{
                    disp = ZonaDeIntercambio.take();
                }catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                enAire.add(disp.getProyectil());
            }
            for ( int i = 0; i < enAire.size(); i++ ){
                enAire.get(i).muestra();
                enAire.get(i).mueveDuranteUnIncremental(canvas.objetivoX, canvas.objetivoY);
                enAire.get(i).dibujaProyectil(canvas);

                if (enAire.get(i).getEstadoProyectil() != 0){
                    final String mensaje;
                    if (enAire.get(i).getEstadoProyectil() == 2){
                        mensaje = "Destruido!";
                    }else {
                        mensaje = "Fallado. El objetivo está en: "+canvas.objetivoX+" y has disparado a: "+enAire.get(i).getIntPosX();
                    }
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            txfMensajes.setText(mensaje);
                        }
                    });
                    enAire.remove(enAire.get(i));
                }
            }
        }
    }
}
