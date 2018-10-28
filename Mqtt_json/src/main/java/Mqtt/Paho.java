
package Mqtt;


import java.io.*;

import java.util.logging.Level;
import java.util.logging.Logger;

        
        
        public class Paho{

        
               public static void main(String[] args){
       

            MjeracVode m=new MjeracVode();
        }
    }
/*public static void main(String[] args) {
            try {
                MjeracVode m=new MjeracVode("tcp://192.168.0.17:1883", "vjezba3", "andjela");
                m.dodajSenzor("temperatura", -3266, 3266, 1, false);
                m.dodajSenzor("tlak", 0,65, 1, false);
                m.dodajSenzor("protok", 0, 65336, 3, true);*/
                
               /* Gson g=new GsonBuilder().create();
                MjeracVode m=g.fromJson(new FileReader("mjerac.json"), MjeracVode.class);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Paho.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
    
