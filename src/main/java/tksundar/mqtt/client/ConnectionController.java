package tksundar.mqtt.client;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.eclipse.paho.mqttv5.client.IMqttClient;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.common.MqttException;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import static java.lang.String.format;
import static tksundar.mqtt.client.util.Commons.LoggerType;
import static tksundar.mqtt.client.util.Commons.getLogger;

/**
 * Author : Sundar Krishnamachari
 * Created: 2022/12/28
 * email: tksrajan@gmail.com
 */
public class ConnectionController {

    private static final String clientId = getMacAddress();

    private static IMqttClient client;

    private static final Logger LOGGER = getLogger(ConnectionController.class.getName(),
            LoggerType.CONSOLE);


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
    }


    public void connect(String url) {
        if (client != null) {
            LOGGER.info("Already connected");
            createConnectedAlertWithOK().show();
            return;
        }
        try {
            client = new MqttClient(url, clientId);
            client.setCallback(new Subscriber());
            client.connect();
            LOGGER.info(format("Connected to broker %s", url));

        } catch (MqttException e) {
            LOGGER.throwing(ConnectionController.class.getName(),
                    "connect", new RuntimeException(e));

        }
    }


    private static String getMacAddress() {
        byte[] hardwareAddress;
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            NetworkInterface ni = NetworkInterface.getByInetAddress(localHost);
            hardwareAddress = ni.getHardwareAddress();
        } catch (UnknownHostException | SocketException e) {
            throw new RuntimeException(e);
        }
        String[] hexadecimal = new String[hardwareAddress.length];
        for (int i = 0; i < hardwareAddress.length; i++) {
            hexadecimal[i] = String.format("%02X", hardwareAddress[i]);
        }
        return String.join("-", hexadecimal);

    }


/*    @FXML
    public void openPublishScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MQTTApplication.class.getResource("mqtt-publisher.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 500);
        Stage stage = new Stage();
        stage.setTitle("Publish");
        stage.setScene(scene);
        stage.show();

    }*/

 /*   @FXML
    public void openSubscribeScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MQTTApplication.class.getResource("mqtt-subscriber.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 500);
        Stage stage = new Stage();
        stage.setTitle("Subscribe");
        stage.setScene(scene);
        stage.show();
    }*/

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

    private Alert createConnectedAlertWithOK() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //alert.setOnCloseRequest(dialogEvent -> alert.close());
        alert.setTitle("Connection Status");
        alert.setContentText("Already Connected");
        LOGGER.info(alert.toString());
        return alert;
    }
}

