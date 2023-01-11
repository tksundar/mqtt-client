package tksundar.mqtt.client;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static tksundar.mqtt.client.ConnectionController.getClient;

/**
 * Author : Sundar Krishnamachari
 * Created: 2022/12/28
 * email: tksrajan@gmail.com
 */
public class Subscriber extends SubscriberBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(Subscriber.class.getSimpleName());
    private static TextArea copy;

    @FXML
    private TextField topic;


    @FXML
    private TextArea textArea;


    private static final StringBuffer buffer = new StringBuffer();


    @FXML
    public void subscribe() throws MqttException {
        LOGGER.info("subscribing to topic " + topic.getText() + "\n");
        getClient().subscribe(topic.getText(), 0);
        copy = textArea;

    }
    @FXML
    public void clear(){
        copy.clear();
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) {
        String msg = mqttMessage.toString();
        LOGGER.info(String.format("Received message %s on Topic %s", msg, s));
        buffer.append(s).append("|").append(msg).append("\n");
        copy.setText(buffer.toString());

    }


}
