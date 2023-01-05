package tksundar.mqtt.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.eclipse.paho.mqttv5.client.IMqttClient;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.util.Arrays;

import static tksundar.mqtt.client.ConnectionController.doDisconnect;
import static tksundar.mqtt.client.ConnectionController.getClient;

/**
 * Author : Sundar Krishnamachari
 * Created: 2022/12/28
 * email: tksrajan@gmail.com
 */
public class MQTTApplication extends Application {
    private static final String[] styles = {"btn btn-lg", "btn-primary"};

    @Override
    public void start(Stage stage) throws IOException {
        stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, windowEvent -> closeWindowAndExit());

        TabPane tabPane = new TabPane();

        VBox box = new FXMLLoader(MQTTApplication.class.getResource("mqtt-connect.fxml")).load();
        addStyles(box, "connect", styles);
        addStyles(box, "disconnect", styles);
        Tab connectTab = new Tab("Connect", box);

        box = new FXMLLoader(MQTTApplication.class.getResource("mqtt-publisher.fxml")).load();
        addStyles(box, "pub", styles);
        Tab publishTab = new Tab("Publish", box);
        publishTab.setDisable(true);

        box = new FXMLLoader(MQTTApplication.class.getResource("mqtt-subscriber.fxml")).load();
        addStyles(box, "sub", styles);
        Tab subscribeTab = new Tab("Subscribe", box);
        subscribeTab.setDisable(true);

        tabPane.getTabs().add(connectTab);
        tabPane.getTabs().add(publishTab);
        tabPane.getTabs().add(subscribeTab);

        MQTTApplicationController.setTabs(publishTab, subscribeTab);

        VBox vBox = new VBox(tabPane);
        Scene scene = new Scene(vBox);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        stage.setScene(scene);
        stage.setTitle("mqtt-client");

        stage.show();
    }

    private void addStyles(Node container, String id, String... styles) {
        if (!id.startsWith("#")) {// This is critical for lookup :-)
            id = "#" + id;
        }
        Node node = container.lookup(id);
        if (node != null) {
            node.getStyleClass().addAll(Arrays.asList(styles));
        }
    }

    private static void closeWindowAndExit() {
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