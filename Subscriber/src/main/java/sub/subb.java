
package sub;

import java.awt.FlowLayout;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.eclipse.paho.client.mqttv3.*;
import org.json.*;


public class subb extends JFrame implements MqttCallback{
    
    
    JSONObject j=null;
    JSONArray a=null;
    MqttClient client;
    ArrayList<JLabel> l;
    ArrayList<Integer> mins;
    ArrayList<Integer> maxs;
    ArrayList<String> jedinice;
    
    public subb(){
        super();
        l=new ArrayList<JLabel>();
        mins=new ArrayList<Integer>();
        maxs=new ArrayList<Integer>();
        jedinice=new ArrayList<String>();
        try {
            j=new JSONObject(new JSONTokener(new java.io.FileInputStream("subscriber.json")));
        } catch (FileNotFoundException ex) {
        }
        
        String broker=j.getString("broker");
        String clientID=j.getString("clientID");
        String tema=j.getString("tema");
        a=j.getJSONArray("senzori");
        for(int i=0;i<a.length();i++)
        {
            JSONObject senzor=a.getJSONObject(i);
            String topic=senzor.getString("topic");
            int min=senzor.getInt("min");
            int max=senzor.getInt("max");
            String jedinica=senzor.getString("jedinica");
            JLabel L1=new JLabel();
            L1.setName(topic);
            L1.setVisible(true);
            l.add(L1);
            mins.add(min);
            maxs.add(max);
            jedinice.add(jedinica);
            add(L1);
        }
        try {
            client=new MqttClient(broker,clientID);
            client.connect();
            client.setCallback(this);
            client.subscribe(tema);
        } catch (MqttException ex) {}
        
        
        
	setLayout(new FlowLayout()); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setSize(900, 100);
	setVisible(true);

        
    }

    @Override
    public void connectionLost(Throwable cause) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        for(int i=0;i<l.size();i++)
        {
            
            if(l.get(i).getName().equals(topic))
            {
                
                l.get(i).setText("[min: " + mins.get(i)+" max: "+maxs.get(i)+ "]  " + message.toString() +" " + jedinice.get(i) + "                  ");
            }
            
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
