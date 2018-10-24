package Practicas_Laboratorio.src.practica4.EntregableCasa;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.atomic.AtomicLong;
import javax.swing.*;
import javax.swing.event.*;

import static Practicas_Laboratorio.src.practica4.EntregableCasa.GUISecuenciaPrimos1a.esPrimo;

// ===========================================================================
  class ZonaIntercambio {
// ===========================================================================
  volatile long tEspera;

  public ZonaIntercambio(long tEspera) {
    this.tEspera = tEspera;
    }

  public long dameValor(){
    return tEspera;
    }

  public void setValor(long valor){
    tEspera = valor;
    }
  }

// ===========================================================================
  class HebraTrabajadora extends Thread {
// ===========================================================================
  volatile boolean flag;
  JTextField mensaje;
  ZonaIntercambio z;

  public HebraTrabajadora(JTextField mensaje, ZonaIntercambio z){
    this.flag = true;
    this.mensaje = mensaje;
    this.z = z;
  }

  public void cancela(){
    this.flag = false;
  }

  @Override
  public void run(){
    long n = 2;
    while (flag) {
      try {
        Thread.sleep(z.dameValor());
      }catch (InterruptedException e) {
        e.printStackTrace();
      }
      if(esPrimo(n)){
        final long n2 = n;
        SwingUtilities.invokeLater(new Runnable() {
          @Override
          public void run() {
            mensaje.setText(Long.valueOf(n2).toString());
          }
        });
      }
      n++;
    }
  }
}

// ===========================================================================
public class GUISecuenciaPrimos1a {
// ===========================================================================
  JFrame            container;
  JPanel            jpanel;
  JTextField        txfMensajes;
  JButton           btnComienzaSecuencia, btnCancelaSecuencia;
  JSlider           sldEspera;
  HebraTrabajadora  h;
  ZonaIntercambio   z;

  // -------------------------------------------------------------------------
  public static void main( String args[] ) {
    GUISecuenciaPrimos1a gui = new GUISecuenciaPrimos1a();
    SwingUtilities.invokeLater(new Runnable(){
      public void run(){
        gui.go();
      }
    });
  }

  // -------------------------------------------------------------------------
  public void go() {
    // Constantes.
    final int valorMaximo = 1000;
    final int valorMedio  = 500;
    z = new ZonaIntercambio((long) valorMedio);

    // Variables.
    JPanel  tempPanel;

    // Crea el JFrame principal.
    container = new JFrame( "GUI Secuencia de Primos 1a" );

    // Consigue el panel principal del Frame "container".
    jpanel = ( JPanel ) container.getContentPane();
    jpanel.setLayout( new GridLayout( 3, 1 ) );

    // Crea e inserta la etiqueta y el campo de texto para los mensajes.
    txfMensajes = new JTextField( 20 );
    txfMensajes.setEditable( false );
    tempPanel = new JPanel();
    tempPanel.setLayout( new FlowLayout() );
    tempPanel.add( new JLabel( "Secuencia: " ) );
    tempPanel.add( txfMensajes );
    jpanel.add( tempPanel );

    // Crea e inserta los botones de Comienza secuencia y Cancela secuencia.
    btnComienzaSecuencia = new JButton( "Comienza secuencia" );
    btnCancelaSecuencia = new JButton( "Cancela secuencia" );
    tempPanel = new JPanel();
    tempPanel.setLayout( new FlowLayout() );
    tempPanel.add( btnComienzaSecuencia );
    tempPanel.add( btnCancelaSecuencia );
    jpanel.add( tempPanel );

    // Crea e inserta el slider para controlar el tiempo de espera.
    sldEspera = new JSlider( JSlider.HORIZONTAL, 0, valorMaximo , valorMedio );
    tempPanel = new JPanel();
    tempPanel.setLayout( new BorderLayout() );
    tempPanel.add( new JLabel( "Tiempo de espera: " ) );
    tempPanel.add( sldEspera );
    jpanel.add( tempPanel );
    
    // Activa inicialmente los 2 botones.
    btnComienzaSecuencia.setEnabled( true );
    btnCancelaSecuencia.setEnabled( false );

    // Anyade codigo para procesar el evento del boton de Comienza secuencia.
    btnComienzaSecuencia.addActionListener( new ActionListener() {
        public void actionPerformed( ActionEvent e ) {
          h = new HebraTrabajadora(txfMensajes,z);
          h.start();

          btnComienzaSecuencia.setEnabled(false);
          btnCancelaSecuencia.setEnabled(true);
        }
    } );

    // Anyade codigo para procesar el evento del boton de Cancela secuencia.
    btnCancelaSecuencia.addActionListener( new ActionListener() {
        public void actionPerformed( ActionEvent e ) {
          h.cancela();

          btnComienzaSecuencia.setEnabled( true );
          btnCancelaSecuencia.setEnabled( false );
        }
    } );

    // Anyade codigo para procesar el evento del slider " Espera " .
    sldEspera.addChangeListener( new ChangeListener() {
      public void stateChanged( ChangeEvent e ) {
        JSlider sl = ( JSlider ) e.getSource();
        if ( ! sl.getValueIsAdjusting() ) {
          long tiempoMilisegundos = ( long ) sl.getValue();
          z.setValor(tiempoMilisegundos);
        }
      }
    } );

    // Fija caracteristicas del container.
    container.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    container.pack();
    container.setResizable( false );
    container.setVisible( true );

    System.out.println( "% End of routine: go.\n" );
  }

  // -------------------------------------------------------------------------
  static boolean esPrimo( long num ) {
    boolean primo;
    if( num < 2 ) {
      primo = false;
    } else {
      primo = true;
      long i = 2;
      while( ( i < num )&&( primo ) ) {
        primo = ( num % i != 0 );
        i++;
      }
    }
    return( primo );
  }
}

