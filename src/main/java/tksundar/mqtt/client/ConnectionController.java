package tksundar.mqtt.client;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.eclipse.paho.mqttv5.client.IMqttClient;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.format;

/**
 * Author : Sundar Krishnamachari
 * Created: 2022/12/28
 * email: tksrajan@gmail.com
 */
public class ConnectionController extends MQTTApplicationController {

    private static final String clientId = "DESKTOP-SUH6DBG";

    private static IMqttClient client;

    private static final Logger  LOGGER = LoggerFactory.getLogger(ConnectionController.class.getSimpleName());


    @FXML
    private TextField brokerAddress;


    @FXML
    private TextField port;


    public static IMqttClient getClient() {
        return client;
    }

    @FXML
    protected void connect() {
        String mqttAddress = brokerAddress.getText();
        String serverUrl = "tcp://" + mqttAddress + ":" + port.getText();
        connect(serverUrl);
    }


    @FXML
    protected void disconnect() {
        doDisconnect();
        publishTab.setDisable(true);
        subscribeTab.setDisable(true);
    }

    public void connect(String url) {
        if (client != null) {
            createConnectedAlertWithOK();
            return;
        }
        try {
            client = new MqttClient(url, clientId);
            client.setCallback(new Subscriber());
            client.connect();
            LOGGER.info(format("Connected to broker %s", url));
            publishTab.setDisable(false);
            subscribeTab.setDisable(false);

        } catch (MqttException e) {
            LOGGER.error(ConnectionController.class.getName(),
                    "connect", new RuntimeException(e));

        }
    }

    private void createConnectedAlertWithOK() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Already Connected");
        alert.setContentText("Broker already connected.To connect to a different broker, disconnect first");
        alert.show();
    }

    public static void doDisconnect() {
        if (client != null && client.isConnected()) {
            try {
                client.disconnect();
            } catch (MqttException e) {
                throw new RuntimeException(e);
            }
            LOGGER.info(format("Disconnected from %s", client.getServerURI()));
            client = null;

        }
    }
}

