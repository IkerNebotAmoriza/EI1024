package Practicas_Laboratorio.src.practica6.EntregableCasa;

import Practicas_Laboratorio.src.practica6.MiHebra_4;
import Practicas_Laboratorio.src.practica6.MiHebra_5;
import Practicas_Laboratorio.src.practica6.MiHebra_6;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

// ============================================================================
class EjemploPalabraMasUsada1a {
// ============================================================================

  // -------------------------------------------------------------------------
  public static void main( String args[] ) {
    long                                      t1, t2;
    double                                    tt, tp1, tp2, tp3, tp4, tp5, tp6, tp7;
    int                                       numHebras;
    String                                    nombreFichero, palabraActual;
    Vector<String>                            vectorLineas;
    HashMap<String,Integer>                   hmCuentaPalabras;
    Hashtable<String, Integer>                htCuentaPalabras;
    ConcurrentHashMap<String, Integer>        chmCuentaPalabras;
    ConcurrentHashMap<String, AtomicInteger>  chmAtomicCuentaPalabras;
    Map<String, Long>                         stCuentaPalabras;

    // Comprobacion y extraccion de los argumentos de entrada.
    if( args.length != 2 ) {
      System.err.println( "Uso: java programa <numHebras> <fichero>" );
      System.exit( -1 );
    }
    try {
      numHebras     = Integer.parseInt( args[ 0 ] );
      nombreFichero = args[ 1 ];
    } catch( NumberFormatException ex ) {
      numHebras = -1;
      nombreFichero = "";
      System.out.println( "ERROR: Argumentos numericos incorrectos." );
      System.exit( -1 );
    }

    // Lectura y carga de lineas en "vectorLineas".
    vectorLineas = readFile( nombreFichero );
    System.out.println( "Numero de lineas leidas: " + vectorLineas.size() );
    System.out.println();

    //
    // Implementacion secuencial sin temporizar.
    //
    hmCuentaPalabras = new HashMap<String,Integer>( 1000, 0.75F );
    for( int i = 0; i < vectorLineas.size(); i++ ) {
      // Procesa la linea "i".
      String[] palabras = vectorLineas.get( i ).split( "\\W+" );
      for( int j = 0; j < palabras.length; j++ ) {
        // Procesa cada palabra de la linea "i", si es distinta de blancos.
        palabraActual = palabras[ j ].trim();
        if( palabraActual.length() > 0 ) {
          contabilizaPalabra( hmCuentaPalabras, palabraActual );
        }
      }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Implementacion secuencial.
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    t1 = System.nanoTime();
    hmCuentaPalabras = new HashMap<String,Integer>( 1000, 0.75F );
    for( int i = 0; i < vectorLineas.size(); i++ ) {
      // Procesa la linea "i".
      String[] palabras = vectorLineas.get( i ).split( "\\W+" );
      for( int j = 0; j < palabras.length; j++ ) {
        // Procesa cada palabra de la linea "i", si es distinta de blancos.
        palabraActual = palabras[ j ].trim();
        if( palabraActual.length() > 0 ) {
          contabilizaPalabra( hmCuentaPalabras, palabraActual );
        }
      }
    }
    t2 = System.nanoTime();
    tt = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.print( "Implemen. secuencial:                                                                " );
    imprimePalabraMasUsadaYVeces( hmCuentaPalabras );
    System.out.println( " Tiempo(s): " + tt );
    System.out.println( "Num. elems. tabla hash: " + hmCuentaPalabras.size() );
    System.out.println();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Implementacion paralela con HashMap con cerrojos
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    t1 = System.nanoTime();
    hmCuentaPalabras = new HashMap<String,Integer>( 1000, 0.75F );
    Collections.synchronizedMap(hmCuentaPalabras);
    MiHebra_1 [] vectorHebras1 = new MiHebra_1[numHebras];
    // Creamos y arrancamos las hebras
    for (int i=0; i < numHebras; i++) {
      vectorHebras1[i] = new MiHebra_1(i, numHebras, vectorLineas, hmCuentaPalabras);
      vectorHebras1[i].start();
    }
    // Esperamos a que las hebras terminen
    for (int i=0; i < vectorHebras1.length; i++) {
      try {
        vectorHebras1[i].join();
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      }
    }
    t2 = System.nanoTime();
    tp1 = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.print( "Implemen. paralela usando HashMap con cerrojos:                                      " );
    imprimePalabraMasUsadaYVeces( hmCuentaPalabras );
    System.out.print( " Tiempo(s): " + tp1 );
    System.out.println( " SpeedUp: " + tt/tp1 );
    System.out.println( "Num. elems. tabla hash: " + hmCuentaPalabras.size() );
    System.out.println();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Implementacion paralela con HashTable con cerrojos
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    t1 = System.nanoTime();
    htCuentaPalabras = new Hashtable<String,Integer>( 1000, 0.75F );
    MiHebra_2 [] vectorHebras2 = new MiHebra_2[numHebras];
    // Creamos y arrancamos las hebras
    for (int i=0; i < numHebras; i++) {
      vectorHebras2[i] = new MiHebra_2(i, numHebras, vectorLineas, htCuentaPalabras);
      vectorHebras2[i].start();
    }
    // Esperamos a que las hebras terminen
    for (int i=0; i < vectorHebras2.length; i++) {
      try {
        vectorHebras2[i].join();
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      }
    }
    t2 = System.nanoTime();
    tp2 = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.print( "Implemen. paralela usando HashTable con cerrojos:                                    " );
    imprimePalabraMasUsadaYVeces( htCuentaPalabras );
    System.out.print( " Tiempo(s): " + tp2 );
    System.out.println( " SpeedUp: " + tt/tp2 );
    System.out.println( "Num. elems. tabla hash: " + htCuentaPalabras.size() );
    System.out.println();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Implementacion paralela con ConcurrentHashMap con cerrojos
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    t1 = System.nanoTime();
    chmCuentaPalabras = new ConcurrentHashMap<String, Integer>( 1000, 0.75F );
    MiHebra_3 [] vectorHebras3 = new MiHebra_3[numHebras];
    // Creamos y arrancamos las hebras
    for (int i=0; i < numHebras; i++) {
      vectorHebras3[i] = new MiHebra_3(i, numHebras, vectorLineas, chmCuentaPalabras);
      vectorHebras3[i].start();
    }
    // Esperamos a que las hebras terminen
    for (int i=0; i < vectorHebras3.length; i++) {
      try {
        vectorHebras3[i].join();
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      }
    }
    t2 = System.nanoTime();
    tp3 = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.print( "Implemen. parelela usando ConcurrentHashMap con cerrojos:                            " );
    imprimePalabraMasUsadaYVeces( chmCuentaPalabras );
    System.out.print( " Tiempo(s): " + tp3 );
    System.out.println( " SpeedUp: " + tt/tp3 );
    System.out.println( "Num. elems. tabla hash: " + chmCuentaPalabras.size() );
    System.out.println();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Implementacion paralela con ConcurrentHashMap con bucle de reintentos
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    t1 = System.nanoTime();
    chmCuentaPalabras = new ConcurrentHashMap<String, Integer>( 1000, 0.75F );
    MiHebra_4[] vectorHebras4 = new MiHebra_4[numHebras];
    // Creamos y arrancamos las hebras
    for (int i=0; i < numHebras; i++) {
      vectorHebras4[i] = new MiHebra_4(i, numHebras, vectorLineas, chmCuentaPalabras);
      vectorHebras4[i].start();
    }
    // Esperamos a que las hebras terminen
    for (int i=0; i < vectorHebras4.length; i++) {
      try {
        vectorHebras4[i].join();
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      }
    }
    t2 = System.nanoTime();
    tp4 = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.print( "Implemen. parelela usando ConcurrentHashMap con bucle de reintentos:                 " );
    imprimePalabraMasUsadaYVeces( chmCuentaPalabras );
    System.out.print( " Tiempo(s): " + tp4 );
    System.out.println( " SpeedUp: " + tt/tp4 );
    System.out.println( "Num. elems. tabla hash: " + chmCuentaPalabras.size() );
    System.out.println();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Implementacion paralela con ConcurrentHashMap con bucle de reintentos y AtomicInteger
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    t1 = System.nanoTime();
    chmAtomicCuentaPalabras = new ConcurrentHashMap<String, AtomicInteger>( 1000, 0.75F );
    MiHebra_5[] vectorHebras5 = new MiHebra_5[numHebras];
    // Creamos y arrancamos las hebras
    for (int i=0; i < numHebras; i++) {
      vectorHebras5[i] = new MiHebra_5(i, numHebras, vectorLineas, chmAtomicCuentaPalabras);
      vectorHebras5[i].start();
    }
    // Esperamos a que las hebras terminen
    for (int i=0; i < vectorHebras5.length; i++) {
      try {
        vectorHebras5[i].join();
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      }
    }
    t2 = System.nanoTime();
    tp5 = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.print( "Implemen. parelela usando ConcurrentHashMap con bucle de reintentos y AtomicInteger: " );
    imprimePalabraMasUsadaYVecesAtomic( chmAtomicCuentaPalabras );
    System.out.print( " Tiempo(s): " + tp5 );
    System.out.println( " SpeedUp: " + tt/tp5 );
    System.out.println( "Num. elems. tabla hash: " + chmAtomicCuentaPalabras.size() );
    System.out.println();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Implementacion paralela con ConcurrentHashMap con AtomicInteger y ConcurrencyLevel
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    t1 = System.nanoTime();
    chmAtomicCuentaPalabras = new ConcurrentHashMap<String, AtomicInteger>( 1000, 0.75F, 256 );
    MiHebra_6[] vectorHebras6 = new MiHebra_6[numHebras];
    // Creamos y arrancamos las hebras
    for (int i=0; i < numHebras; i++) {
      vectorHebras6[i] = new MiHebra_6(i, numHebras, vectorLineas, chmAtomicCuentaPalabras);
      vectorHebras6[i].start();
    }
    // Esperamos a que las hebras terminen
    for (int i=0; i < vectorHebras6.length; i++) {
      try {
        vectorHebras6[i].join();
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      }
    }
    t2 = System.nanoTime();
    tp6 = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.print( "Implemen. parelela usando ConcurrentHashMap con AtomicInteger y ConcurrencyLevel:    " );
    imprimePalabraMasUsadaYVecesAtomic( chmAtomicCuentaPalabras );
    System.out.print( " Tiempo(s): " + tp6 );
    System.out.println( " SpeedUp: " + tt/tp6 );
    System.out.println( "Num. elems. tabla hash: " + chmAtomicCuentaPalabras.size() );
    System.out.println();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Implementacion paralela usando Parallel Streams
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    t1 = System.nanoTime();

    stCuentaPalabras = vectorLineas.parallelStream().filter(s -> s != null)
            .map(s -> s.split(("\\W+"))).flatMap(Arrays::stream).filter(s -> (s.length() > 0))
            .collect(groupingBy(s -> s, counting()));

    t2 = System.nanoTime();
    tp7 = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.print( "Implemen. usando Parallel Streams:                                                   " );
    imprimePalabraMasUsadaYVecesLong( stCuentaPalabras );
    System.out.print( " Tiempo(s): " + tt );
    System.out.println( " SpeedUp: " + tt/tp7 );
    System.out.println( "Num. elems. tabla hash: " + stCuentaPalabras.size() );
    System.out.println();

    System.out.println( "Fin de programa." );
  }

  // -------------------------------------------------------------------------
  public static Vector<String> readFile( String fileName ) {
    BufferedReader br; 
    String         linea;
    Vector<String> data = new Vector<String>();

    try {
      br = new BufferedReader( new FileReader( fileName ) );
      while( ( linea = br.readLine() ) != null ) {
        //// System.out.println( "Leida linea: " + linea );
        data.add( linea );
      }
      br.close(); 
    } catch( FileNotFoundException ex ) {
      ex.printStackTrace();
    } catch( IOException ex ) {
      ex.printStackTrace();
    }
    return data;
  }

  // -------------------------------------------------------------------------
  public static void contabilizaPalabra( 
                         HashMap<String,Integer> cuentaPalabras,
                         String palabra ) {
    Integer numVeces = cuentaPalabras.get( palabra );
    if( numVeces != null ) {
      cuentaPalabras.put( palabra, numVeces+1 );
    } else {
      cuentaPalabras.put( palabra, 1 );
    }
  }   

  // --------------------------------------------------------------------------
  static void imprimePalabraMasUsadaYVeces(
                  Map<String,Integer> cuentaPalabras ) {
    Vector<Map.Entry> lista = 
        new Vector<Map.Entry>( cuentaPalabras.entrySet() );

    String palabraMasUsada = "";
    int    numVecesPalabraMasUsada = 0;
    // Calcula la palabra mas usada.
    for( int i = 0; i < lista.size(); i++ ) {
      String palabra = ( String ) lista.get( i ).getKey();
      int numVeces = ( Integer ) lista.get( i ).getValue();
      if( i == 0 ) {
        palabraMasUsada = palabra;
        numVecesPalabraMasUsada = numVeces;
      } else if( numVecesPalabraMasUsada < numVeces ) {
        palabraMasUsada = palabra;
        numVecesPalabraMasUsada = numVeces;
      }
    }
    // Imprime resultado.
    System.out.print( "( Palabra: '" + palabraMasUsada + "' " + 
                         "veces: " + numVecesPalabraMasUsada + " )" );
  }

  // --------------------------------------------------------------------------
  static void imprimePalabraMasUsadaYVecesLong(
          Map<String,Long> cuentaPalabras ) {
    Vector<Map.Entry> lista =
            new Vector<Map.Entry>( cuentaPalabras.entrySet() );

    String palabraMasUsada = "";
    long numVecesPalabraMasUsada = 0;
    // Calcula la palabra mas usada.
    for( int i = 0; i < lista.size(); i++ ) {
      String palabra = ( String ) lista.get( i ).getKey();
      long numVeces = ( Long ) lista.get( i ).getValue();
      if( i == 0 ) {
        palabraMasUsada = palabra;
        numVecesPalabraMasUsada = numVeces;
      } else if( numVecesPalabraMasUsada < numVeces ) {
        palabraMasUsada = palabra;
        numVecesPalabraMasUsada = numVeces;
      }
    }
    // Imprime resultado.
    System.out.print( "( Palabra: '" + palabraMasUsada + "' " +
            "veces: " + numVecesPalabraMasUsada + " )" );
  }

  // --------------------------------------------------------------------------
  static void imprimePalabraMasUsadaYVecesAtomic(Map<String,AtomicInteger> cuentaPalabras ) {
    Vector<Map.Entry> lista = new Vector<Map.Entry>( cuentaPalabras.entrySet() );

    String palabraMasUsada = "";
    AtomicInteger numVecesPalabraMasUsada = new AtomicInteger(0);
    // Calcula la palabra mas usada.
    for( int i = 0; i < lista.size(); i++ ) {
      String palabra = ( String ) lista.get( i ).getKey();
      int numVeces = ((AtomicInteger) lista.get( i ).getValue()).get();
      if( i == 0 ) {
        palabraMasUsada = palabra;
        numVecesPalabraMasUsada = new AtomicInteger(numVeces);
      } else if( numVecesPalabraMasUsada.get() < numVeces ) {
        palabraMasUsada = palabra;
        numVecesPalabraMasUsada = new AtomicInteger(numVeces);
      }
    }
    // Imprime resultado.
    System.out.print( "( Palabra: '" + palabraMasUsada + "' " +
            "veces: " + numVecesPalabraMasUsada + " )" );
  }

  // --------------------------------------------------------------------------
  static void printCuentaPalabrasOrdenadas(
                  HashMap<String,Integer> cuentaPalabras ) {
    int             i, numVeces;
    List<Map.Entry> list = new Vector<Map.Entry>( cuentaPalabras.entrySet() );

    // Ordena por valor.
    Collections.sort( 
        list,
        new Comparator<Map.Entry>() {
            public int compare( Map.Entry e1, Map.Entry e2 ) {
              Integer i1 = ( Integer ) e1.getValue();
              Integer i2 = ( Integer ) e2.getValue();
              return i2.compareTo( i1 );
            }
        }
    );
    // Muestra contenido.
    i = 1;
    System.out.println( "Veces Palabra" );
    System.out.println( "-----------------" );
    for( Map.Entry e : list ) {
      numVeces = ( ( Integer ) e.getValue () ).intValue();
      System.out.println( i + " " + e.getKey() + " " + numVeces );
      i++;
    }
    System.out.println( "-----------------" );
  }
}


