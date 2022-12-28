package tksundar.mqtt.client;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;

import static tksundar.mqtt.client.MQTTApplicationController.getClient;
/**
 * Author : Sundar Krishnamachari
 * Created: 2022/12/28
 * email: tksrajan@gmail.com
 */
public class MqttPublisher {

    @FXML
    private TextField topic;

    @FXML
    private TextField message;

    public void publish() throws MqttException {

        System.out.println("publishing to topic "+topic.getText()+" message "+message.getText());
        getClient().publish(topic.getText(),new MqttMessage(message.getText().getBytes()));
    }
}
