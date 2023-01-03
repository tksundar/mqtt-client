package tksundar.mqtt.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.eclipse.paho.mqttv5.client.IMqttClient;

import java.io.IOException;

import static tksundar.mqtt.client.ConnectionController.doDisconnect;
import static tksundar.mqtt.client.ConnectionController.getClient;

/**
 * Author : Sundar Krishnamachari
 * Created: 2022/12/28
 * email: tksrajan@gmail.com
 */
public class MQTTApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, windowEvent -> closeWindowAndExit());

        TabPane tabPane = new TabPane();

        Tab connectTab = new Tab("Connect",
                new FXMLLoader(MQTTApplication.class.getResource("mqtt-connect.fxml")).load());

        Tab publishTab = new Tab("Publish",
                new FXMLLoader(MQTTApplication.class.getResource("mqtt-publisher.fxml")).load());
        publishTab.setDisable(true);

        Tab subscribeTab = new Tab("Subscribe",
                new FXMLLoader(MQTTApplication.class.getResource("mqtt-subscriber.fxml")).load());
        subscribeTab.setDisable(true);

        tabPane.getTabs().add(connectTab);
        tabPane.getTabs().add(publishTab);
        tabPane.getTabs().add(subscribeTab);

        MQTTApplicationController.setTabs(publishTab,subscribeTab);

        VBox vBox = new VBox(tabPane);
        Scene scene = new Scene(vBox);

        stage.setScene(scene);
        stage.setTitle("mqtt-client");

        stage.show();
    }

    private static void closeWindowAndExit(){
        IMqttClient client = getClient();
        if (client != null && client.isConnected()) {
           doDisconnect();
            System.exit(0);
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}