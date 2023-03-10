package tksundar.mqtt.client;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import org.eclipse.paho.mqttv5.client.IMqttClient;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tksundar.mqtt.client.api.Connect;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static java.lang.String.format;

/**
 * Author : Sundar Krishnamachari
 * Created: 2022/12/28
 * email: tksrajan@gmail.com
 */
public class ConnectionController implements Connect {

    private static Tab publishTab;

    private static Tab subscribeTab;

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
    public void connect() {
        String mqttAddress = brokerAddress.getText();
        String serverUrl = "tcp://" + mqttAddress + ":" + port.getText();
        connect(serverUrl);
    }


    @FXML
    public void disconnect() {
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
            client = new MqttClient(url, getHostName());
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

    private String getHostName() {
        InetAddress myHost;
        String hostname;
        try {
            myHost = InetAddress.getLocalHost();
            hostname = myHost.getHostName();
        } catch (UnknownHostException e) {
            LOGGER.warn("Exception resolving hostname. Defaulting to current time in millis");
           hostname=String.valueOf(System.currentTimeMillis());
        }
        return hostname;
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

    public static void setTabs(Tab publishTab, Tab subscribeTab) {
        ConnectionController.subscribeTab = subscribeTab;
        ConnectionController.publishTab = publishTab;
    }
}

