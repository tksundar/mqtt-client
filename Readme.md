This project is a GUI app in java that provides the ability to publish and subscribe to a topic using MQTT
THe GUI is developed using javafx as a single screen application. Eclipse paho libraries are used for MQTT client
implementation

I have used bootstrapFx library from org.kordamp.bootstrapfx. They have proted part of the bootstrap css to javafx. I have used their styles just for the buttons.I hope to used full bootstrap styling for all nodes , may be in a web view.

**This has sample usage for the following nodes and controls**

1. VBox
2. HBox
3. TabPane
4. ScrollPane
5. Tab
6. Button
7. TextField
8. Label
9. Alert

The code uses FXML binding wherever convenient. The primary stage however is created programatically to better control
the Tab state

**Opening Screen**
<p><img src="/images/main.png?raw=true"/></p>
**After Broker Connected**
<p><img src="/images/main_connected.png?raw=true"/></p>
**Publisher Screen**
<p><img src="/images/publish?raw=true"/></p>
**Subscriber screen**
<p><img src="/images/subscribe.png?raw=true"/></p>
