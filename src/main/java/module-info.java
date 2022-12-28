module tksundar.mqtt.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.eclipse.paho.mqttv5.client;

    opens tksundar.mqtt.client to javafx.fxml;
    exports tksundar.mqtt.client;
}