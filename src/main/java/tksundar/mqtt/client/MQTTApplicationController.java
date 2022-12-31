package tksundar.mqtt.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.eclipse.paho.mqttv5.client.IMqttClient;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.common.MqttException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import static tksundar.mqtt.client.util.Commons.LoggerType;
import static tksundar.mqtt.client.util.Commons.getLogger;

/**
 * Author : Sundar Krishnamachari
 * Created: 2022/12/28
 * email: tksrajan@gmail.com
 */
public class MQTTApplicationController {

    private static final Logger LOGGER = getLogger(MQTTApplicationController.class.getName(),
            LoggerType.CONSOLE, LoggerType.FILE);

    private static IMqttClient client;

    private String clientId;

    @FXML
    private Label title;

    @FXML
    private Button connectButton;
    @FXML
    private Menu choose;

    @FXML
    private TextField brokerAddress;


    public static IMqttClient getClient() {
        return client;
    }

    public MQTTApplicationController() {
        try {
            this.clientId = getMacAddress();
        } catch (Exception e) {
            LOGGER.info("exception getting mac address. Using classname for clientId");
            this.clientId = getClass().getCanonicalName();
        }
    }

    @FXML
    protected void connect() {
        String mqttAddress = brokerAddress.getText();
        LOGGER.info(mqttAddress);
        try {
            client = new MqttClient("tcp://" + mqttAddress, clientId);
            client.setCallback(new SubCallBack());
            if (connect(client)) {
                choose.setDisable(false);
                connectButton.setDisable(true);
                title.setText("Connected");
            }
        } catch (MqttException mqe) {
            throw new RuntimeException(mqe);
        }
    }


    @FXML
    protected void disconnect() throws MqttException {
        if (client != null && client.isConnected()) {
            client.disconnect();
            System.exit(0);
        }
    }

    public boolean connect(final IMqttClient client) {
        boolean retVal;
        try {
            client.connect();
            retVal = true;
        } catch (MqttException e) {
            LOGGER.info(e.getMessage());
            retVal = false;
        }

        return retVal;
    }

    private static String getMacAddress() throws UnknownHostException, SocketException {
        InetAddress localHost = InetAddress.getLocalHost();
        NetworkInterface ni = NetworkInterface.getByInetAddress(localHost);
        byte[] hardwareAddress = ni.getHardwareAddress();
        String[] hexadecimal = new String[hardwareAddress.length];
        for (int i = 0; i < hardwareAddress.length; i++) {
            hexadecimal[i] = String.format("%02X", hardwareAddress[i]);
        }
        return String.join("-", hexadecimal);

    }


    @FXML
    public void openPublishScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MQTTApplication.class.getResource("mqtt-publisher.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 500);
        Stage stage = new Stage();
        stage.setTitle("Publish");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void openSubscribeScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MQTTApplication.class.getResource("mqtt-subscriber.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 500);
        Stage stage = new Stage();
        stage.setTitle("Subscribe");
        stage.setScene(scene);
        stage.show();
    }
}