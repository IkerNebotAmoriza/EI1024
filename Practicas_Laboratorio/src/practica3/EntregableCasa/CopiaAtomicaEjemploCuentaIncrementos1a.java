package Practicas_Laboratorio.src.practica3.EntregableCasa;

import java.util.concurrent.atomic.AtomicInteger;

// ============================================================================
class CopiaAtomicaCuentaIncrementos1a {
// ============================================================================
  AtomicInteger numIncrementos = new AtomicInteger(0);

  // --------------------------------------------------------------------------
  void incrementaNumIncrementos() {
    numIncrementos.getAndIncrement();
  }

  // --------------------------------------------------------------------------
  int dameNumIncrementos() {
    return( numIncrementos.get() );
  }
}


// ============================================================================
class CopiaAtomicaMiHebra1a extends Thread {
// ============================================================================
  int                  tope;
  CopiaAtomicaCuentaIncrementos1a c;

  // --------------------------------------------------------------------------
  public CopiaAtomicaMiHebra1a(int tope, CopiaAtomicaCuentaIncrementos1a c ) {
    this.tope  = tope;
    this.c     = c;
  }

  // --------------------------------------------------------------------------
  public void run() {
    for( int i = 0; i < tope; i++ ) {
      c.incrementaNumIncrementos();
    }
  }
}

// ============================================================================
class CopiaAtomicaEjemploCuentaIncrementos1a {
// ============================================================================

  // --------------------------------------------------------------------------
  public static void main( String args[] ) {
    long    t1, t2;
    double  tt;
    int     numHebras, tope;

    // Comprobacion y extraccion de los argumentos de entrada.
    if( args.length != 2 ) {
      System.err.println( "Uso: java programa <numHebras> <tope>" );
      System.exit( -1 );
    }
    try {
      numHebras = Integer.parseInt( args[ 0 ] );
      tope      = Integer.parseInt( args[ 1 ] );
    } catch( NumberFormatException ex ) {
      numHebras = -1;
      tope      = -1;
      System.out.println( "ERROR: Argumentos numericos incorrectos." );
      System.exit( -1 );
    }

    System.out.println( "numHebras: " + numHebras );
    System.out.println( "tope:      " + tope );
    
    CopiaAtomicaMiHebra1a v[] = new CopiaAtomicaMiHebra1a[ numHebras ];
    CopiaAtomicaCuentaIncrementos1a c = new CopiaAtomicaCuentaIncrementos1a();
    t1 = System.nanoTime();
    System.out.println( "Creando y arrancando " + numHebras + " hebras." );
    for( int i = 0; i < numHebras; i++ ) {
      v[ i ] = new CopiaAtomicaMiHebra1a( tope, c );
      v[ i ].start();
    }
    for( int i = 0; i < numHebras; i++ ) {
      try {
        v[ i ].join();
      } catch( InterruptedException ex ) {
        ex.printStackTrace();
      }
    }
    t2 = System.nanoTime();
    tt = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.println( "Total de incrementos: " + c.dameNumIncrementos() );
    System.out.println( "Tiempo transcurrido en segs.: " + tt );
  }
}

