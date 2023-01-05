package tksundar.mqtt.client;

import javafx.scene.control.Tab;

/**
 * Author : Sundar Krishnamachari
 * Created: 2023/01/02
 * email: tksrajan@gmail.com
 */
public class MQTTApplicationController {

    protected static Tab publishTab;
    protected static Tab subscribeTab;


    public static void setTabs(Tab publishTab, Tab subscribeTab) {
        MQTTApplicationController.subscribeTab = subscribeTab;
        MQTTApplicationController.publishTab = publishTab;
    }

}
