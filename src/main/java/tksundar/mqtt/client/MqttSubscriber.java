package tksundar.mqtt.client;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.eclipse.paho.mqttv5.common.MqttException;

import static tksundar.mqtt.client.MQTTApplicationController.getClient;
/**
 * Author : Sundar Krishnamachari
 * Created: 2022/12/28
 * email: tksrajan@gmail.com
 */
public class MqttSubscriber {

    @FXML
    private TextField topic;


    public void subscribe() throws MqttException {
        System.out.println("subscribing to topic "+topic.getText());
        getClient().subscribe(topic.getText(),0);

    }
}
