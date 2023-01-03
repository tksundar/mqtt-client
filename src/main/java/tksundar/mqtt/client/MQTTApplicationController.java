package tksundar.mqtt.client;

import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import org.eclipse.paho.mqttv5.client.IMqttClient;

import static tksundar.mqtt.client.ConnectionController.getClient;

/**
 * Author : Sundar Krishnamachari
 * Created: 2023/01/02
 * email: tksrajan@gmail.com
 */
public class MQTTApplicationController {

    protected static Tab publishTab;
    protected static Tab subscribeTab;

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

    public static void setTabs(Tab publishTab, Tab subscribeTab){
        MQTTApplicationController.subscribeTab = subscribeTab;
        MQTTApplicationController.publishTab = publishTab;
    }

}
