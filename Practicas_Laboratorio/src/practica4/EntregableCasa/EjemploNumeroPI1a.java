package Practicas_Laboratorio.src.practica4.EntregableCasa;

import static Practicas_Laboratorio.src.practica4.EntregableCasa.EjemploNumeroPI1a.f;

// ===========================================================================
class Acumula {
// ===========================================================================
  double suma;

  // -------------------------------------------------------------------------
  Acumula() {
    suma = 0;
  }

  // -------------------------------------------------------------------------
  synchronized void acumulaDato( double dato ) {
    suma += dato;
  }

  // -------------------------------------------------------------------------
  synchronized double dameDato() {
    return suma;
  }
}

// ===========================================================================
class MiHebraMultAcumulaciones1b extends Thread {
// ===========================================================================
  int      miId, numHebras;
  long     numRectangulos;
  Acumula  a;
  double   baseRectangulo;

  // -------------------------------------------------------------------------
  MiHebraMultAcumulaciones1b( int miId, int numHebras, long numRectangulos, Acumula a ) {
    this.miId = miId;
    this.numHebras = numHebras;
    this.numRectangulos = numRectangulos;
    this.a = a;
    baseRectangulo = 1.0 / ( ( double ) numRectangulos );
  }

  // -------------------------------------------------------------------------
  public void run() {
    for( int i=miId; i < numRectangulos; i+=numHebras ){
      a.acumulaDato(f(baseRectangulo * ( ( ( double ) i ) + 0.5 )));
    }
  }
}

// ===========================================================================
class MiHebraUnicaAcumulacion1b extends Thread {
  // ===========================================================================
  int      miId, numHebras;
  long     numRectangulos;
  Acumula  a;
  double   baseRectangulo;

  // -------------------------------------------------------------------------
  MiHebraUnicaAcumulacion1b( int miId, int numHebras, long numRectangulos, Acumula a ) {
    this.miId = miId;
    this.numHebras = numHebras;
    this.numRectangulos = numRectangulos;
    this.a = a;
    baseRectangulo = 1.0 / ( ( double ) numRectangulos );
  }

  // -------------------------------------------------------------------------
  public void run() {
    double suma = 0.0;
    for( int i=miId; i < numRectangulos; i+=numHebras ){
      suma += f(baseRectangulo * ( ( ( double ) i ) + 0.5 ));
    }
    a.acumulaDato(suma);
  }
}

// ===========================================================================
class EjemploNumeroPI1a {
// ===========================================================================

  // -------------------------------------------------------------------------
  public static void main( String args[] ) {
    long                        numRectangulos;
    double                      baseRectangulo, x, suma, pi;
    int                         numHebras;
    MiHebraMultAcumulaciones1b  vt[];
    MiHebraUnicaAcumulacion1b   vtu[];
    Acumula                     a;
    long                        t1, t2;
    double                      tSec, tPar;

    // Comprobacion de los argumentos de entrada.
    if( args.length != 2 ) {
      System.out.println( "ERROR: numero de argumentos incorrecto.");
      System.out.println( "Uso: java programa <numHebras> <numRectangulos>" );
      System.exit( -1 );
    }
    try {
      numHebras      = Integer.parseInt( args[ 0 ] );
      numRectangulos = Long.parseLong( args[ 1 ] );
    } catch( NumberFormatException ex ) {
      numHebras      = -1;
      numRectangulos = -1;
      System.out.println( "ERROR: Numeros de entrada incorrectos." );
      System.exit( -1 );
    }

    System.out.println();
    System.out.println( "Calculo del numero PI mediante integracion." );

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Calculo del numero PI de forma secuencial.
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    System.out.println();
    System.out.println( "Comienzo del calculo secuencial." );
    t1 = System.nanoTime();
    baseRectangulo = 1.0 / ( ( double ) numRectangulos );
    suma           = 0.0;
    for( long i = 0; i < numRectangulos; i++ ) {
      x = baseRectangulo * ( ( ( double ) i ) + 0.5 );
      suma += f( x );
    }
    pi = baseRectangulo * suma;
    t2 = System.nanoTime();
    tSec = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.println( "Version Secuencial. Numero PI:                     " + pi );
    System.out.println( "Tiempo transcurrido (s.):                          " + tSec );

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Calculo del numero PI de forma paralela: 
    // Multiples acumulaciones por hebra.
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    System.out.println();
    System.out.println( "Comienzo del calculo paralelo ciclico con multiples incrementos." );
    t1 = System.nanoTime();
    a = new Acumula();
    vt = new MiHebraMultAcumulaciones1b[numHebras];

    for( int i = 0; i < numHebras; i++ ) {
      vt[i] = new MiHebraMultAcumulaciones1b(i, numHebras, numRectangulos, a);
      vt[i].start();
    }

    try {
      for ( int i = 0; i < numHebras; i++ ) {
        vt[i].join();
      }
    }catch (InterruptedException e){
      e.printStackTrace();
    }

    pi = baseRectangulo * a.dameDato();
    t2 = System.nanoTime();
    tPar = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.println( "Version Paralela Multiples Incrementos. Numero PI: " + pi );
    System.out.println( "Tiempo transcurrido (s.):                          " + tPar );
    System.out.println("SpeedUp:                                           "+tSec/tPar);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Calculo del numero PI de forma paralela:
    // Unica acumulacion por hebra.
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    System.out.println();
    System.out.println( "Comienzo del calculo paralelo ciclico con unico incremento." );
    t1 = System.nanoTime();
    a = new Acumula();
    vtu = new MiHebraUnicaAcumulacion1b[numHebras];

    for( int i = 0; i < numHebras; i++ ) {
      vtu[i] = new MiHebraUnicaAcumulacion1b(i, numHebras, numRectangulos, a);
      vtu[i].start();
    }

    try {
      for ( int i = 0; i < numHebras; i++ ) {
        vtu[i].join();
      }
    }catch (InterruptedException e){
      e.printStackTrace();
    }

    pi = baseRectangulo * a.dameDato();
    t2 = System.nanoTime();
    tPar = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.println( "Version Paralela Unico Incremento. Numero PI:      " + pi );
    System.out.println( "Tiempo transcurrido (s.):                          " + tPar );
    System.out.println("SpeedUp:                                           "+tSec/tPar);

    // ...

    System.out.println();
    System.out.println( "Fin de programa." );
  }

  // -------------------------------------------------------------------------
  static double f( double x ) {
    return ( 4.0/( 1.0 + x*x ) );
  }
}

