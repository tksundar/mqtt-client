
package tksundar.mqtt.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.eclipse.paho.mqttv5.client.IMqttClient;
import org.eclipse.paho.mqttv5.common.MqttException;

import java.io.IOException;

import static tksundar.mqtt.client.MQTTApplicationController.getClient;

/**
 * Author : Sundar Krishnamachari
 * Created: 2022/12/28
 * email: tksrajan@gmail.com
 */
public class MQTTApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MQTTApplication.class.getResource("mqtt_client.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 520, 340);
        stage.setTitle("mqtt client");
        stage.setScene(scene);
        stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, windowEvent ->{
            try {
                IMqttClient client = getClient();
                if (client!=null && client.isConnected()){
                    client.disconnect();
                }
            } catch (MqttException e) {
                throw new RuntimeException(e);
            }
            System.exit(0);
        } );
        stage.show();

    }

    public static void main(String[] args) {

        System.out.println("Main thread "+Thread.currentThread().getId());
        launch();
    }
}