package Practicas_Laboratorio.src.practica3.EntregableCasa;

import Practicas_Laboratorio.src.practica3.MiHebraPrimoDistDinamica;
import Practicas_Laboratorio.src.practica3.MiHebraPrimoDistPorBloques;

import java.util.concurrent.atomic.AtomicInteger;

// ===========================================================================
public class EjemploMuestraPrimosEnVector2a {
// ===========================================================================

  // -------------------------------------------------------------------------
  public static void main( String args[] ) {
    int     numHebras;
    long    t1, t2;
    double  tt,tc,tb,td;

     long    vectorNumeros[] = {
                 200000033L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                 200000039L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                 200000051L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                 200000069L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                 200000081L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                 200000083L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                 200000089L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                 200000093L, 4L, 4L, 4L, 4L, 4L, 4L, 4L
             };

    // Comprobacion y extraccion de los argumentos de entrada.
    if( args.length != 1 ) {
      System.err.println( "Uso: java programa <numHebras>" );
      System.exit( -1 );
    }
    try {
      numHebras = Integer.parseInt( args[ 0 ] );
    } catch( NumberFormatException ex ) {
      numHebras = -1;
      System.out.println( "ERROR: Argumentos numericos incorrectos." );
      System.exit( -1 );
    }


    //
    // Implementacion secuencial.
    //
    System.out.println( "" );
    System.out.println( "Implementacion secuencial." );
    t1 = System.nanoTime();
    for( int i = 0; i < vectorNumeros.length; i++ ) {
      if( esPrimo( vectorNumeros[ i ] ) ) {
        System.out.println( "  Encontrado primo: " + vectorNumeros[ i ] );
      }
    }
    t2 = System.nanoTime();
    tt = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.println( "Tiempo secuencial (seg.):                    "+tt );

    // -------------------------------------------------------------------------

    //
    // Implementacion Paralela Cíclica.
    //
    System.out.println( "" );
    System.out.println( "Implementacion Paralela Cíclica." );
    t1 = System.nanoTime();
    MiHebraPrimoDistCiclica [] vectorHebras = new MiHebraPrimoDistCiclica[numHebras];

    for( int i = 0; i < numHebras; i++ ) {
        vectorHebras[i] = new MiHebraPrimoDistCiclica(i, numHebras, vectorNumeros);
        vectorHebras[i].start();
    }
    for (int i=0; i<numHebras; i++){
        try {
            vectorHebras[i].join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    t2 = System.nanoTime();
    tc = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.println("Tiempo ciclica (seg.):                       "+tc );
      System.out.println("Incremento velocidad cíclica:                "+(tt/tc));

    // -------------------------------------------------------------------------

      // Implementacion bloques.
      System.out.println("");
      System.out.println("Implementacion paralela bloques");
      t1=System.nanoTime();
      MiHebraPrimoDistPorBloques[] vectorBloques= new MiHebraPrimoDistPorBloques[numHebras];
      for(int i=0;i<numHebras;i++) {
          vectorBloques[i]=new MiHebraPrimoDistPorBloques(i,numHebras,vectorNumeros);
          vectorBloques[i].start();

      }
      for(int i=0;i<numHebras;i++) {
          try {
              vectorBloques[i].join();
          }catch (InterruptedException ex) {
              ex.printStackTrace();
          }
      }
      t2=System.nanoTime();
      tb = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
      System.out.println("Tiempo paralela bloques (seg. ):             "+tb);
      System.out.println("Incremento velocidad bloques:                "+(tt/tb));



      // Implementacion dinamica.
      System.out.println("");
      System.out.println("Implementacion paralela dinamica");
      t1=System.nanoTime();
      MiHebraPrimoDistDinamica[] vectorDinamico= new MiHebraPrimoDistDinamica[numHebras];
      AtomicInteger indice= new AtomicInteger(0);
      for(int i=0;i<numHebras;i++) {
          vectorDinamico[i]=new MiHebraPrimoDistDinamica(i,numHebras,vectorNumeros,indice);
          vectorDinamico[i].start();

      }
      for(int i=0;i<numHebras;i++) {
          try {
              vectorDinamico[i].join();
          }catch (InterruptedException ex) {
              ex.printStackTrace();
          }
      }
      t2=System.nanoTime();
      td = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
      System.out.println("Tiempo paralela dinamica (seg. ):            "+td);
      System.out.println("Incremento velocidad dinamica:               "+ (tt/td));

}

  // -------------------------------------------------------------------------



  public static boolean esPrimo(long num) {
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

