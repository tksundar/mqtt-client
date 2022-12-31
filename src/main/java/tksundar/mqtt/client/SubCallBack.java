package tksundar.mqtt.client;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttCallback;
import org.eclipse.paho.mqttv5.client.MqttDisconnectResponse;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;
import tksundar.mqtt.client.util.Commons;

import java.util.logging.Logger;

import static tksundar.mqtt.client.MQTTApplicationController.getClient;

/**
 * Author : Sundar Krishnamachari
 * Created: 2022/12/28
 * email: tksrajan@gmail.com
 */
public class SubCallBack implements MqttCallback {
    private static final Logger LOGGER = Commons.getLogger(SubCallBack.class.getName(),
            Commons.LoggerType.FILE, Commons.LoggerType.CONSOLE);
    private static TextArea copy;

    @FXML
    private TextField topic;

    @FXML
    private TextArea received;

    private final StringBuffer buffer = new StringBuffer();


    @FXML
    public void subscribe() throws MqttException {
        LOGGER.info("subscribing to topic " + topic.getText() + "\n");
        getClient().subscribe(topic.getText(), 0);
        copy = received;
    }

    @Override
    public void disconnected(MqttDisconnectResponse mqttDisconnectResponse) {

    }

    @Override
    public void mqttErrorOccurred(MqttException e) {

    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) {
        String msg = mqttMessage.toString();
        System.out.printf("\nReceived message %s on Topic %s", msg, s);
        buffer.append(msg).append("\n");
        copy.setText(buffer.toString());
    }

    @Override
    public void deliveryComplete(IMqttToken iMqttToken) {

    }

    @Override
    public void connectComplete(boolean b, String s) {

    }

    @Override
    public void authPacketArrived(int i, MqttProperties mqttProperties) {

    }
}
