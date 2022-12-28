package tksundar.mqtt.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Author : Sundar Krishnamachari
 * Created: 2022/12/28
 * email: tksrajan@gmail.com
 */
public class PostConnectionController {

    public void openPublishScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MQTTApplication.class.getResource("mqtt-publisher.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 200);
        Stage stage = new Stage();
        stage.setTitle("Publish");
        stage.setScene(scene);
        stage.show();

    }

    public  void openSubscribeScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MQTTApplication.class.getResource("mqtt-subscriber.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 200);
        Stage stage = new Stage();
        stage.setTitle("Subscribe");
        stage.setScene(scene);
        stage.show();
    }

}
