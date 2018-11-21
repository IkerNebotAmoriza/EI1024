package Practicas_Laboratorio.src.practica7;

import Practicas_Laboratorio.src.practica7.EntregableCasa.PuebloMaximaMinima;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

public class TareaCallable implements Callable {
    private int codPueblo;
    private String fecha;
    private PuebloMaximaMinima MaxMin;
    private ExecutorService pool;

    public TareaCallable(int codPueblo, String fecha, PuebloMaximaMinima MaxMin, ExecutorService pool) {
        this.codPueblo = codPueblo;
        this.fecha = fecha;
        this.MaxMin = MaxMin;
        this.pool = pool;
    }

    public int getCodPueblo()  {
        return this.codPueblo;
    }

    @Override
    public Object call() throws Exception {
        // Procesamiento de la informacion XML asociada a codPueblo
        // Actualizacion de MaxMin de acuerdo a los valores obtenidos

        URL url;
        InputStream is = null;
        BufferedReader br;
        String line, poblacion = new String(), provincia = new String();
        int state, num[] = new int[2];
        PuebloMaximaMinima res = null;

        try {
            String urlStr = "http://www.aemet.es/xml/municipios/localidad_" +
                    String.format("%05d", codPueblo) + ".xml";
            url = new URL(urlStr);
            is = url.openStream();  // throws an IOException
            br = new BufferedReader(new InputStreamReader(is));

            state = 0;
            while (((line = br.readLine()) != null) && (state < 6)) {
                //        System.out.println (line);
                if ((state == 0) && (line.contains("nombre"))) {
                    poblacion = line.split(">")[1].split("<")[0].split("/")[0];
                    state++;
                } else if ((state == 1) && (line.contains("provincia"))) {
                    provincia = line.split(">")[1].split("<")[0].split("/")[0];
                    state++;
                } else if ((state == 2) && (line.contains(fecha))) {
                    state++;
                } else if ((state == 3) && (line.contains("temperatura"))) {
                    state++;
                } else if ((state > 3) && ((line.contains("maxima")) || (line.contains("minima")))) {
                    num[state - 4] = Integer.parseInt(line.split(">")[1].split("<")[0]);
                    state++;
                }
            }
            // System.out.println("(" + codPueblo + ") " + poblacion + "(" + provincia + ") => " +
            //                    "(" + num[0] + " , " + num[1] + ")");
            res = new PuebloMaximaMinima(poblacion, codPueblo, num[0], num[1]);
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            //      ioe.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {
                // nothing to see here
            }
        }
        return res;
    }
}
