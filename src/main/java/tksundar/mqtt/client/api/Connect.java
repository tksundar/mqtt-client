package tksundar.mqtt.client.api;

import org.eclipse.paho.mqttv5.common.MqttException;

public interface Connect {

    void connect() throws MqttException;
    void disconnect()throws MqttException;
}
