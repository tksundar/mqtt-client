package tksundar.mqtt.client.api;

import org.eclipse.paho.mqttv5.common.MqttException;

public interface Publish {

    void publish() throws MqttException;
}
