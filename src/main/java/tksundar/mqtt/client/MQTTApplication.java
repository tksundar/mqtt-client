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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

import static tksundar.mqtt.client.ConnectionController.*;

/**
 * Author : Sundar Krishnamachari
 * Created: 2022/12/28
 * email: tksrajan@gmail.com
 */
public class MQTTApplication extends Application {
    private static final String[] STYLES_PRIMARY = {"btn btn-xs", "btn-primary"};
    private static final String[] STYLES_OTHER = {"btn btn-xs", "btn-danger"};
    private static final String CONNECT_BUTTON = "connect";
    private static final String DISCONNECT_BUTTON = "disconnect";
    private static final String PUBLISH_BUTTON = "publish";
    private static final String SUBSCRIBE_BUTTON = "subscribe";
    private static final String CLEAR_BUTTON = "clear";
    private static final String TITLE = "mqtt-client";
    private static final String HASH = "#";

    private static final Logger LOGGER = LoggerFactory.getLogger(MQTTApplication.class.getSimpleName());

    @Override
    public void start(Stage stage) throws IOException {
        LOGGER.info("Starting mqtt-client...");
        stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, windowEvent -> closeWindowAndExit());

        TabPane tabPane = new TabPane();

        VBox box = new FXMLLoader(MQTTApplication.class.getResource("mqtt-connect.fxml")).load();
        addStyles(box, CONNECT_BUTTON, STYLES_PRIMARY);
        addStyles(box, DISCONNECT_BUTTON, STYLES_OTHER);
        Tab connectTab = new Tab("Connect", box);

        box = new FXMLLoader(MQTTApplication.class.getResource("mqtt-publisher.fxml")).load();
        addStyles(box, PUBLISH_BUTTON, STYLES_PRIMARY);
        Tab publishTab = new Tab("Publish", box);
        publishTab.setDisable(true);

        box = new FXMLLoader(MQTTApplication.class.getResource("mqtt-subscriber.fxml")).load();
        addStyles(box, SUBSCRIBE_BUTTON, STYLES_PRIMARY);
        addStyles(box, CLEAR_BUTTON, STYLES_OTHER);
        Tab subscribeTab = new Tab("Subscribe", box);
        subscribeTab.setDisable(true);

        tabPane.getTabs().add(connectTab);
        tabPane.getTabs().add(publishTab);
        tabPane.getTabs().add(subscribeTab);

        setTabs(publishTab, subscribeTab);

        VBox vBox = new VBox(tabPane);
        Scene scene = new Scene(vBox, 500, 312);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        stage.setScene(scene);
        stage.setTitle(TITLE);
        stage.setResizable(false);

        stage.show();
    }

    private void addStyles(Node container, String id, String... styles) {
        if (!id.startsWith(HASH)) {// This is critical for lookup :-)
            id = HASH + id;
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