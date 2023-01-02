package tksundar.mqtt.client;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import tksundar.mqtt.client.util.Commons;

import java.util.logging.Logger;

import static tksundar.mqtt.client.ConnectionController.getClient;

/**
 * Author : Sundar Krishnamachari
 * Created: 2022/12/28
 * email: tksrajan@gmail.com
 */
public class Publisher extends MQTTApplicationController {
    private final Logger LOGGER = Commons.getLogger(Publisher.class.getName(),
            Commons.LoggerType.FILE, Commons.LoggerType.CONSOLE);
    @FXML
    private TextArea published;

    @FXML
    private TextField topic;

    @FXML
    private TextField message;

    private final StringBuffer buffer = new StringBuffer();


    public void publish() throws MqttException {
        if(hasConnectWarning())return;
        LOGGER.info("publishing to topic " + topic.getText() + " message " + message.getText());
        getClient().publish(topic.getText(), new MqttMessage(message.getText().getBytes()));
        buffer.append(message.getText()).append("\n");
        published.setText(buffer.toString());
    }

}
