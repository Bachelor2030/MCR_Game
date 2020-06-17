package gui.gameWindows;

import gui.buttons.GameButton;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ParameterWindow extends GameWindow {
  private VBox body;
  private TextField playerNameField, playerIpField, playerPortField;

  public ParameterWindow(BorderPane racine, HBox navigation, Stage stage, boolean isGaming) {
    super(racine, navigation, isGaming, stage);
    body = new VBox(10);
    this.stage = stage;
    body.getStyleClass().add("parameters-body");
    generateBody();
  }

  private void generateBody() {
    body.prefWidthProperty().bind(stage.widthProperty().multiply(0.80));

    // Titre : Settings
    Label settingsTitle = new Label("Settings");
    settingsTitle.getStyleClass().add("instructions-title");

    // Nom du joueur (Bouffon n°1 si pas changé :D)
    Label playerName = new Label("Nom");
    playerName.getStyleClass().add("parameters-label");

    playerNameField = new TextField();
    playerNameField.setText("Bouffon n°1");
    playerNameField.setMinWidth(50);
    playerNameField.setPrefWidth(50);
    playerNameField.setMaxWidth(200);

    // Adresse IP du joueur
    Label playerIP = new Label("Adresse IP");
    playerIP.getStyleClass().add("parameters-label");

    playerIpField = new TextField();
    playerIpField.setMinWidth(50);
    playerIpField.setPrefWidth(50);
    playerIpField.setMaxWidth(200);
    try(final DatagramSocket socket = new DatagramSocket()){
      socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
      playerIpField.setText(
              String.valueOf(socket.getLocalAddress().getHostAddress()));
    } catch (UnknownHostException | SocketException e) {
      e.printStackTrace();
    }

    /*
    playerIpField.setText(
        String.valueOf(InetAddress.getLoopbackAddress())); // récupère l'adresse IP
     */

    // Port du joueur
    Label playerPort = new Label("Port");
    playerPort.getStyleClass().add("parameters-label");

    playerPortField = new TextField();
    playerPortField.setText("1337"); // récupère l'adresse IP
    playerPortField.setMinWidth(50);
    playerPortField.setPrefWidth(50);
    playerPortField.setMaxWidth(200);

    body.getChildren()
        .addAll(
            settingsTitle,
            playerName,
            playerNameField,
            playerIP,
            playerIpField,
            playerPort,
            playerPortField);
    body.setAlignment(Pos.CENTER);
    body.setSpacing(25); // espace entre les éléments
  }

  public void addGameButton(GameButton button) {
    body.getChildren().add(button.getButton());
  }

  public TextField getPlayerNameField() {
    return playerNameField;
  }

  public TextField getPlayerIpField() {
    return playerIpField;
  }

  public TextField getPlayerPortField() {
    return playerPortField;
  }

  public VBox getBody() {
    return body;
  }
}
