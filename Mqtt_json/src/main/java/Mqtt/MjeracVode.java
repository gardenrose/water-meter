package Mqtt;

import java.util.ArrayList;
import java.util.Timer;
import java.io.*;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class MjeracVode {

    String clientID;
    String tema;
    String broker;
    MqttClient client;
    MqttConnectOptions conOpts;
    MemoryPersistence persistence;
    ArrayList<senzor> senzori;

    public MjeracVode() {
        //String file = "mjerac.json";
        JSONObject j = null;
        JSONArray a = null;
        try {
            j = new JSONObject(new JSONTokener(new FileInputStream("mjerac.json")));
        } catch (FileNotFoundException ex) {
        }
        String clientID = j.getString("clientID");
        String broker = j.getString("broker");
        String tema = j.getString("tema");
        a = j.getJSONArray("senzori");
        MjeracVode m = new MjeracVode(broker, tema, clientID);
        for (int i = 0; i < a.length(); i++) {
            JSONObject o = a.getJSONObject(i);
            String ime = o.getString("ime");
            int min = o.getInt("min");
            int max = o.getInt("max");
            int faktor = o.getInt("faktor");
            m.dodajSenzor(ime, min, max, faktor);
        }

    }

    public MjeracVode(String Broker, String Tema, String ClientID) {
        broker = Broker;
        clientID = ClientID;
        tema = Tema;
        senzori = new ArrayList();
        try {
            conOpts = new MqttConnectOptions();
            client = new MqttClient(broker, clientID);
            conOpts.setCleanSession(true);
            System.out.println("Connecting to broker: " + broker);
            client.connect(conOpts);
            System.out.println("Connected");
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }

    public void dodajSenzor(String Ime, int min, int max, int faktor) {
        senzor s;
        s = new senzor(Ime, min, max, faktor, this); //{};
        this.senzori.add(s);
    }

    public String getBroker() {
        return this.broker;
    }

    public void setBroker(String Broker) {
        this.broker = Broker;
    }

    public String getTema() {
        return this.tema;
    }

    public void setTema(String Tema) {
        this.tema = Tema;
    }

    public ArrayList<senzor> getSenzor() {
        return this.senzori;
    }

    public void setSenzor(ArrayList<senzor> Senzori) {
        this.senzori = Senzori;
    }

    public String setClientID() {
        return this.clientID;
    }

    public void setClientID(String ClientID) {
        this.clientID = ClientID;
    }
}
