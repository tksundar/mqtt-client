package tksundar.mqtt.client.api;

import org.eclipse.paho.mqttv5.common.MqttException;

public interface Subscribe {

    void subscribe() throws MqttException;
}
