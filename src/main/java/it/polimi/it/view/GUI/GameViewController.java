package it.polimi.it.view.GUI;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.network.client.ClientInterface;
import it.polimi.it.network.client.TurnStages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller of GameTurn that manages the client's turn and all of its operation
 */
public class GameViewController implements GuiInterface, Initializable {

    private static ClientInterface client;
    private static ArrayList<Tile> Tiles;

    @FXML
    GridPane LivingRoom;
    @FXML
    Label Player1;
    @FXML
    Label Player2;
    @FXML
    Label Player3;
    @FXML
    Label Player4;

    @FXML
    GridPane Player1Grid;
    @FXML
    GridPane Player2Grid;
    @FXML
    GridPane Player3Grid;
    @FXML
    GridPane Player4Grid;

    @FXML
    Label Points1;
    @FXML
    Label Points2;
    @FXML
    Label Points3;
    @FXML
    Label Points4;


    @FXML
    Label labelPoints1;
    @FXML
    Label labelPoints2;
    @FXML
    Label labelPoints3;
    @FXML
    Label labelPoints4;

    @FXML
    ImageView shelfieP2;
    @FXML
    ImageView shelfieP3;
    @FXML
    ImageView shelfieP4;

    @FXML
    VBox vBoxGrid;

    @FXML
    VBox vBoxShelfie;
    @FXML
    VBox vBoxLabelPoints;

    @FXML
    VBox vBoxPoints;



    @FXML
    Label Text;
    @FXML
    TextField num;
    @FXML
    TextField chatMessage;
    @FXML
    TextArea textMessage;


    /**
     * Method that initialize the scene and all of his parts like the living room, the bookshelves, the nicknames, the chat and the points
     * @param location the location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources the resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tiles = new ArrayList<>(0);
        Image[][] board;
        board = GUIApplication.getBoard();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != null) {
                    ImageView imageView = new ImageView();
                    imageView.setFitHeight(45);
                    imageView.setFitWidth(47);
                    imageView.setImage(board[i][j]);
                    LivingRoom.add(imageView, j, i);
                    int finalI = i;
                    int finalJ = j;
                    imageView.setOnMouseClicked(mouseEvent -> {
                        try {
                            imageView.setOpacity(0.5);
                            chooseTiles(imageView, finalI, finalJ);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            }
        }


        HashMap<Integer, GridPane> gridOfPlayers = new HashMap<>(4);
        HashMap<Integer, Label> nicknames = new HashMap<>(4);
        HashMap<Integer, Label> points = new HashMap<>(4);

        nicknames.put(0, Player1);
        gridOfPlayers.put(0, Player1Grid);
        points.put(0,Points1);

        nicknames.put(1, Player2);
        gridOfPlayers.put(1, Player2Grid);
        points.put(1,Points2);

        nicknames.put(2, Player3);
        gridOfPlayers.put(2, Player3Grid);
        points.put(2,Points3);

        nicknames.put(3, Player4);
        gridOfPlayers.put(3, Player4Grid);
        points.put(3,Points4);

        int numPlayer = GUIApplication.getPlayers().size();
        switch (numPlayer){
            case 3:
                shelfieP3.setOpacity(1);
                labelPoints3.setOpacity(1);
                Points3.setOpacity(1);
                break;
            case 4:
                shelfieP3.setOpacity(1);
                labelPoints3.setOpacity(1);
                Points3.setOpacity(1);
                shelfieP4.setOpacity(1);
                labelPoints4.setOpacity(1);
                Points4.setOpacity(1);
                break;
            default:
                break;
        }



        nicknames.forEach((k, v) -> {
            if (k < GUIApplication.getPlayers().size() && GUIApplication.getPlayers().get(k) != null){
                v.setText(GUIApplication.getPlayers().get(k));
                v.setOpacity(1);
            }
        });
        Player1.setTextFill(Color.BLUE);

        points.forEach((k, v) -> {
            if (k < GUIApplication.getPlayers().size() && GUIApplication.getPoints().get(k) != null){
                v.setText(GUIApplication.getPoints().get(k).toString());
                v.setOpacity(1);
            }
        });

        gridOfPlayers.forEach((k, v) -> {
            if (k < GUIApplication.getPlayers().size() && GUIApplication.getPlayers().get(k) != null) {
                Image[][] shelfImage1;
                if (GUIApplication.getShelfies() != null && GUIApplication.getShelfies().get(GUIApplication.getPlayers().get(k)) != null) {
                    shelfImage1 = GUIApplication.getShelfies().get(GUIApplication.getPlayers().get(k));
                    for (int i = 0; i < 6; i++) {
                        for (int j = 0; j < 5; j++) {
                            if (shelfImage1[i][j] != null) {
                                ImageView imageView = new ImageView();
                                if (k == 0) {
                                    imageView.setFitWidth(43);
                                    imageView.setFitHeight(40);
                                } else {
                                    imageView.setFitHeight(20);
                                    imageView.setFitWidth(19);
                                }
                                imageView.setImage(shelfImage1[i][j]);
                                v.add(imageView, j, 5 - i);
                            }
                        }
                    }
                }
            }

        });

        if(GUIApplication.getCurrentChat() != null)
            textMessage.setText(GUIApplication.getCurrentChat());
    }

    public void setClient(ClientInterface clientRef){
        client = clientRef;
    }

    /**
     * Method that triggers when the button "Show personal goal card" is clicked, it shows a pop-up with the card
     * @param actionEvent is the event that trigger this method and in this case it's the click of the button "Show personal goal card"
     * @throws IOException .
     */
    public void GoToPersonalGoalCard(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Personal Goal Card");

        Image image = new Image(GUIApplication.getPersonalCard().toString());

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);

        DialogPane dialogPane = new DialogPane();
        dialogPane.setContent(new VBox(10, imageView));

        alert.setDialogPane(dialogPane);
        alert.getButtonTypes().add(ButtonType.OK);
        alert.showAndWait();
    }

    /**
     * Method that triggers when the button "Show common goal card" is clicked, it shows a pop-up with the cards
     * @param actionEvent is the event that trigger this method and in this case it's the click of the button "Show common goal card"
     * @throws IOException .
     */
    public void GoToCommonGoalCards(ActionEvent actionEvent) throws IOException {
        int id1 = GUIApplication.getIDCommon1();
        int id2 = GUIApplication.getIDCommon2();

        Gson gson = new Gson();

        JsonReader reader = new JsonReader((new InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("CardsDescription.json")))));
        JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);

        JsonObject jsonObject1 = jsonArray.get(id1 - 1).getAsJsonObject();
        String description1 = jsonObject1.get("description").getAsString();

        JsonObject jsonObject2 = jsonArray.get(id2 - 1).getAsJsonObject();
        String description2 = jsonObject2.get("description").getAsString();


        Label label1 = new Label(description1);
        label1.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
        Label label2 = new Label(description2);
        label2.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Common Goal Card");

        Image common1 = new Image(GUIApplication.getCommonCard1().toString());
        Image common2 = new Image(GUIApplication.getCommonCard2().toString());

        ImageView imageCommon1 = new ImageView(common1);
        ImageView imageCommon2 = new ImageView(common2);
        imageCommon1.setFitWidth(400);
        imageCommon1.setPreserveRatio(true);
        imageCommon2.setFitWidth(400);
        imageCommon2.setPreserveRatio(true);

        VBox vbox1 = new VBox(10, imageCommon1, label1);
        VBox vbox2 = new VBox(10, imageCommon2, label2);

        HBox hbox = new HBox(20, vbox1, vbox2);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(100);
        gridPane.setVgap(20);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(hbox, 0, 0);

        DialogPane dialogPane = new DialogPane();
        dialogPane.setContent(gridPane);

        alert.setDialogPane(dialogPane);
        alert.getButtonTypes().add(ButtonType.OK);
        alert.showAndWait();
    }

    /**
     * Method to select the tiles from the Living room that changes the opacity of the tile when is selected
     * @param imageView is the image associated with the tile
     * @param row is the row of the tile in the living room
     * @param column is the column of the tile in the living room
     * @throws IOException .
     */
    public void chooseTiles(ImageView imageView, int row, int column) throws IOException {
        PossibleColors color = getColor(imageView);
        Tile t = new Tile(row,column,color);
        Tiles.add(t);
        imageView.setOnMouseClicked(mouseEvent -> {
            imageView.setOpacity(1);
            try {
                removeTiles(imageView,row,column);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Method used to deselect the tiles that I have chosen
     * @param imageView is the image associated with the tile
     * @param i row is the row of the tile in the living room
     * @param j is the column of the tile in the living room
     * @throws IOException .
     */
    public void removeTiles(ImageView imageView, int i, int j) throws IOException {
        PossibleColors color = getColor(imageView);
        Tile t = new Tile(i,j,color);
        Tiles.remove(t);
        GUIApplication.changeScene();
    }

    /**
     * Method that triggers when the button "Choose selected tiles" is pressed and sends the list of selected tiles to the server
     * @param actionEvent is the trigger of this method and in this case it's the click of the button "Choose selected tiles"
     * @throws IOException .
     */
    public void ChooseTiles(ActionEvent actionEvent) throws IOException {
        if(!client.getGameStage().equals(TurnStages.CHOOSETILES)){
            GUIApplication.showAlert(Alert.AlertType.INFORMATION, "Game rules", "It's not time to choose the tiles");
        }else {
            if(Tiles.size() == 0){
                GUIApplication.showAlert(Alert.AlertType.WARNING, "Tiles error", "Invalid number, try again");
            }else {
                client.selectedTiles(Tiles);
            }
        }

    }

    /**
     * Method that trigger when one of the button that represents a column it's pressed and send the chosen column to the server
     * @param actionEvent is the trigger of this method and in this case it's the click of a button that represents a column
     * @throws IOException .
     */
    public void ChooseColumn(ActionEvent actionEvent) throws IOException {
        if(!client.getGameStage().equals(TurnStages.CHOOSECOLUMN)) {
            GUIApplication.showAlert(Alert.AlertType.INFORMATION, "Game rules", "It's not time to choose the column");
        }else {
            int id = Integer.parseInt(((Button) (actionEvent.getSource())).getId());
            client.chooseColumn(id);
        }

    }

    /**
     * Method used to send to the server the number of tiles that the client wants to take; it triggers when the client write a number in
     * the text field and press the button "Confirm"
     * @param actionEvent  is the trigger of this method and in this case it's the click of the button "Confirm"
     * @throws IOException .
     */
    public void ChooseNumberOfTiles(ActionEvent actionEvent) throws IOException {
        if(!client.getGameStage().equals(TurnStages.TILESNUM)) {
            GUIApplication.showAlert(Alert.AlertType.INFORMATION, "Game rules", "It's not time to choose the number of tiles");
        }else {
            if(num.getText().length() == 0){
                GUIApplication.showAlert(Alert.AlertType.WARNING, "Tiles error", "Invalid number, try again");
            }else {
                String tmp = num.getText();
                int num = Integer.parseInt(tmp);
                client.tilesNumMessage(num);
            }
        }
    }

    /**
     * Method that, starting from the image that represents the tile, returns the color
     * @param image is the image associated with the tile
     * @return the color of the tile
     */
    public PossibleColors getColor(ImageView image){

        if((image.getImage().getUrl().equals((Objects.requireNonNull(getClass().getResource("/Images/Trofei1.1.png"))).toString()) || (image.getImage().getUrl().equals((Objects.requireNonNull(getClass().getResource("/Images/Trofei1.2.png"))).toString()))
                || (image.getImage().getUrl().equals((Objects.requireNonNull(getClass().getResource("/Images/Trofei1.3.png"))).toString())))){
            return PossibleColors.CYAN;
        }


        if((image.getImage().getUrl().equals((Objects.requireNonNull(getClass().getResource("/Images/Piante1.1.png"))).toString()) || (image.getImage().getUrl().equals((Objects.requireNonNull(getClass().getResource("/Images/Piante1.2.png"))).toString()))
                || (image.getImage().getUrl().equals((Objects.requireNonNull(getClass().getResource("/Images/Piante1.3.png"))).toString())))){
            return PossibleColors.PINK;
        }


        if((image.getImage().getUrl().equals((Objects.requireNonNull(getClass().getResource("/Images/Giochi1.1.png"))).toString()) || (image.getImage().getUrl().equals((Objects.requireNonNull(getClass().getResource("/Images/Giochi1.2.png"))).toString()))
                || (image.getImage().getUrl().equals((Objects.requireNonNull(getClass().getResource("/Images/Giochi1.3.png"))).toString())))){
            return PossibleColors.YELLOW;
        }


        if((image.getImage().getUrl().equals((Objects.requireNonNull(getClass().getResource("/Images/Cornici1.png"))).toString()) || (image.getImage().getUrl().equals((Objects.requireNonNull(getClass().getResource("/Images/Cornici1.2.png"))).toString()))
                || (image.getImage().getUrl().equals((Objects.requireNonNull(getClass().getResource("/Images/Cornici1.3.png"))).toString())))){
            return PossibleColors.BLUE;
        }


        if((image.getImage().getUrl().equals((Objects.requireNonNull(getClass().getResource("/Images/Gatti1.1.png"))).toString()) || (image.getImage().getUrl().equals((Objects.requireNonNull(getClass().getResource("/Images/Gatti1.2.png"))).toString()))
                || (image.getImage().getUrl().equals((Objects.requireNonNull(getClass().getResource("/Images/Gatti1.3.png"))).toString())))){
            return PossibleColors.GREEN;
        }

        if((image.getImage().getUrl().equals((Objects.requireNonNull(getClass().getResource("/Images/Libri1.1.png"))).toString()) || (image.getImage().getUrl().equals((Objects.requireNonNull(getClass().getResource("/Images/Libri1.2.png"))).toString()))
                || (image.getImage().getUrl().equals((Objects.requireNonNull(getClass().getResource("/Images/Libri1.3.png"))).toString())))){
            return PossibleColors.WHITE;
        }

        return null;

    }

    /**
     * Method used to send the chat message (private or public) written by the client in the text Field when the button "Send" it's pressed
     * @param actionEvent  is the trigger of this method and in this case it's the click of the button "Send"
     * @throws IOException .
     */
    public void sendMessage(ActionEvent actionEvent) throws IOException{
        if(chatMessage.getText().length()!=0){
            String sender = client.getNickname().trim();
            if(chatMessage.getText().contains("<<")){
                String[] splittedMessage = chatMessage.getText().split("<<");
                if(splittedMessage.length == 1){
                    GUIApplication.showAlert(Alert.AlertType.WARNING, "Empty message error", "You didn't write anything");
                }else{
                    String message = "\u001B[33m" + sender + "\u001B[39m" + ": " + splittedMessage[1];
                    String receiver = splittedMessage[0];
                    client.sendChatPrivateMessage(message, receiver);
                }
            }else{
                client.sendChatMessage(sender + ": " + chatMessage.getText());
            }
        }else{
            GUIApplication.showAlert(Alert.AlertType.WARNING, "Empty message error", "You didn't write anything");
        }
    }


}