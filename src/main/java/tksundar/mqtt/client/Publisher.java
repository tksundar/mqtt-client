package tksundar.mqtt.client;

import org.eclipse.paho.mqttv5.client.IMqttClient;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;

public class Publisher {

    private IMqttClient client;

    public void publish(final MqttClient client, String topic,final String message){
        MqttMessage msg = new MqttMessage(message.getBytes());
        try {
            client.publish(topic,msg);
            client.disconnect();
            System.out.println("published");
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

}
