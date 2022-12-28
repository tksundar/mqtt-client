package tksundar.mqtt.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
/**
 * Author : Sundar Krishnamachari
 * Created: 2022/12/28
 * email: tksrajan@gmail.com
 */
public class MQTTApplicationController {

    private static IMqttClient client;

    private String clientId;


    @FXML
    private TextField brokerAddress;


    public static IMqttClient getClient(){
        return client;
    }

    public MQTTApplicationController(){
        try {
            this.clientId = getMacAddress();
        }catch(Exception e){
            System.out.println("exception getting mac address. Using classname for clientId");
            this.clientId = getClass().getCanonicalName();
        }
    }

    @FXML
    protected void connect() {
        String mqttAddress = brokerAddress.getText();
        System.out.println(mqttAddress);
        try {
             client = new MqttClient("tcp://"+mqttAddress, clientId);
             client.setCallback(new SubCallBack());
            if (connect(client)) {
                try {
                    showConfirmation();
                }catch(IOException ioe){
                    System.out.println(ioe.getMessage());
                }
            }
        } catch (MqttException mqe) {
            throw new RuntimeException(mqe);
        }
    }
    @FXML
    protected void disconnect() throws MqttException {
        if (client !=null && client.isConnected()){
            client.disconnect();
            System.exit(0);
        }
    }

    private void showConfirmation() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MQTTApplication.class.getResource("connect-confirmation.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 50);
        Stage stage = new Stage();
        stage.setTitle("Connected");
        stage.setScene(scene);
        stage.show();
    }

    public boolean connect(final IMqttClient client) {
        boolean retVal;
        try {
            client.connect();
            retVal = true;
        } catch (MqttException e) {
            System.out.println(e.getMessage());
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
}