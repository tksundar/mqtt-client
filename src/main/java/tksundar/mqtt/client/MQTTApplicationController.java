package tksundar.mqtt.client;

import javafx.scene.control.Alert;
import org.eclipse.paho.mqttv5.client.IMqttClient;

import static tksundar.mqtt.client.ConnectionController.getClient;

/**
 * Author : Sundar Krishnamachari
 * Created: 2023/01/02
 * email: tksrajan@gmail.com
 */
public class MQTTApplicationController {

    protected boolean hasConnectWarning(){
        final IMqttClient client = getClient();
        if(client == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Not connected to a broker");
            alert.show();
            return true;
        }

        return false;

    }

}
