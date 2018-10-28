package Mqtt;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

class senzor {

    String ime;
    Timer timer;
    int min;
    int max;
    int faktor;
    int broj;
    MqttMessage poruka;
    MjeracVode m;
    
    public senzor(){}
    public senzor (String Ime, int Min, int Max, int Faktor, MjeracVode M) {
        ime = Ime;
        min = Min;
        max = Max;
        faktor = Faktor;
        timer = new Timer(); 
        m = M;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
               new Thread(new Runnable() {
                    @Override
                    public void run() {
                        broj = generiraj(min, max);
                        
                    }
            
                }).start();
                poruka = new MqttMessage((Integer.toString(broj)).getBytes()); 
                poruka.setQos(2);
                try {m.client.publish(m.tema + "/" + m.clientID + "/" + ime, poruka);  
                } catch (MqttException me) {
                System.out.println("reason "+me.getReasonCode());
                System.out.println("msg "+me.getMessage());
                System.out.println("loc "+me.getLocalizedMessage());
                System.out.println("cause "+me.getCause());
                System.out.println("excep "+me);
                me.printStackTrace();
                }
                
            }
        }, 0, faktor*1000);
        
    }
    
    public int generiraj(int min, int max) {
        broj = min + (int) (Math.random() * max);
        return broj;
    }

   
}
