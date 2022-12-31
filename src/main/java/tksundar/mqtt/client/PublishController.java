package tksundar.mqtt.client;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;

import static tksundar.mqtt.client.MQTTApplicationController.getClient;

/**
 * Author : Sundar Krishnamachari
 * Created: 2022/12/28
 * email: tksrajan@gmail.com
 */
public class PublishController {

    @FXML
    private TextArea published;

    @FXML
    private TextField topic;

    @FXML
    private TextField subTopic;

    @FXML
    private TextField message;

    static StringBuffer buffer= new StringBuffer("");


//    public void openPublishScreen() throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(MQTTApplication.class.getResource("mqtt-publisher.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 300, 200);
//        Stage stage = new Stage();
//        stage.setTitle("Publish");
//        stage.setScene(scene);
//        stage.show();
//
//    }
//
//    public  void openSubscribeScreen() throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(MQTTApplication.class.getResource("mqtt-subscriber.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 300, 200);
//        Stage stage = new Stage();
//        stage.setTitle("Subscribe");
//        stage.setScene(scene);
//        stage.show();
//    }

    public void publish() throws MqttException {
        System.out.println("publishing to topic "+topic.getText()+" message "+message.getText());
        getClient().publish(topic.getText(),new MqttMessage(message.getText().getBytes()));
        buffer.append(message.getText()+"\n");
        published.setText(buffer.toString());
    }

    @FXML
    public void subscribe() throws MqttException {
        System.out.println("subscribing to topic "+subTopic.getText());
        getClient().subscribe(subTopic.getText(),0);
        System.out.println(subTopic);
        System.out.println(message);



    }

}
