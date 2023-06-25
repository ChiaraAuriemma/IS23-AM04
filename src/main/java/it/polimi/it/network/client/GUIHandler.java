package it.polimi.it.network.client;

import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.view.GUI.*;
import it.polimi.it.view.ViewInterface;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GUIHandler implements ViewInterface {

    private ClientInterface client;
    private ViewInterface view;




    // metodo per settare il riferimento al tipo di view inteface usata
    public void setView(ViewInterface view) {
        this.view = view;
    }

    //metodo che riceve un Arrylist di stringhe contenente i nomi dei giocatori ordinati in base all'ordine di gioco,
    // lo invia alla GUI così che possa stampare a schermo i nickname
    @Override
    public void setOrderView(ArrayList<String> order) {
        Platform.runLater(new Thread(()-> {
            try {
                GUIApplication.setPlayers(order);
                GUIApplication.changeScene();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        Thread.interrupted();

    }

    // metodo per inviare alla GUI lo stato della board
    @Override
    public void setBoardView(Tile[][] matrix) {
        Image[][] board = new Image[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String color = matrix[i][j].getColor();
                Image image = chooseAnImage(color);
                board[i][j] = image;
            }
        }
        GUIApplication.setBoard(board);
    }

    // associa ad ogni tile (al metodo arriva solo il colore) una della 3 immagini disponibili per il colore assegnato
    public Image chooseAnImage(String color){
        Random random = new Random();
        int randomNumber = random.nextInt(3) + 1;
        if(color.equals("CYAN")){
            if(randomNumber == 1){
                URL imageUrl = getClass().getResource("/Images/Trofei1.1.png");
                assert imageUrl != null;
                return new Image(imageUrl.toString());

            }
            if(randomNumber == 2){
                URL imageUrl = getClass().getResource("/Images/Trofei1.2.png");
                assert imageUrl != null;
                return new Image(imageUrl.toString());

            }else {
                URL imageUrl = getClass().getResource("/Images/Trofei1.3.png");
                assert imageUrl != null;
                return new Image(imageUrl.toString());

            }

        }
        if(color.equals("PINK")){
            if(randomNumber == 1){
                URL imageUrl = getClass().getResource("/Images/Piante1.1.png");
                assert imageUrl != null;
                return new Image(imageUrl.toString());

            }
            if(randomNumber == 2){
                URL imageUrl = getClass().getResource("/Images/Piante1.2.png");
                assert imageUrl != null;
                return new Image(imageUrl.toString());

            }else {
                URL imageUrl = getClass().getResource("/Images/Piante1.3.png");
                assert imageUrl != null;
                return new Image(imageUrl.toString());

            }

        }
        if(color.equals("YELLOW")){
            if(randomNumber == 1){
                URL imageUrl = getClass().getResource("/Images/Giochi1.1.png");
                assert imageUrl != null;
                return new Image(imageUrl.toString());

            }
            if(randomNumber == 2){
                URL imageUrl = getClass().getResource("/Images/Giochi1.2.png");
                assert imageUrl != null;
                return new Image(imageUrl.toString());

            }else {
                URL imageUrl = getClass().getResource("/Images/Giochi1.3.png");
                assert imageUrl != null;
                return new Image(imageUrl.toString());

            }

        }
        if(color.equals("BLUE")){
            if(randomNumber == 1){
                URL imageUrl = getClass().getResource("/Images/Cornici1.png");
                assert imageUrl != null;
                return new Image(imageUrl.toString());

            }
            if(randomNumber == 2){
                URL imageUrl = getClass().getResource("/Images/Cornici1.2.png");
                assert imageUrl != null;
                return new Image(imageUrl.toString());

            }else {
                URL imageUrl = getClass().getResource("/Images/Cornici1.3.png");
                assert imageUrl != null;
                return new Image(imageUrl.toString());

            }

        }
        if(color.equals("GREEN")){
            if(randomNumber == 1){
                URL imageUrl = getClass().getResource("/Images/Gatti1.1.png");
                assert imageUrl != null;
                return new Image(imageUrl.toString());

            }
            if(randomNumber == 2){
                URL imageUrl = getClass().getResource("/Images/Gatti1.2.png");
                assert imageUrl != null;
                return new Image(imageUrl.toString());

            }else {
                URL imageUrl = getClass().getResource("/Images/Gatti1.3.png");
                assert imageUrl != null;
                return new Image(imageUrl.toString());

            }

        }
        if(color.equals("WHITE")){
            if(randomNumber == 1){
                URL imageUrl = getClass().getResource("/Images/Libri1.1.png");
                assert imageUrl != null;
                return new Image(imageUrl.toString());

            }
            if(randomNumber == 2){
                URL imageUrl = getClass().getResource("/Images/Libri1.2.png");
                assert imageUrl != null;
                return new Image(imageUrl.toString());

            }else {
                URL imageUrl = getClass().getResource("/Images/Libri1.3.png");
                assert imageUrl != null;
                return new Image(imageUrl.toString());

            }

        }

        return null;
    }

    // metodo che invia alla GUI lo stato della shelfie legata a un determinato player
    @Override
    public void setPlayersShelfiesView(String player, Tile[][] shelfie) {
        Image[][] shelfImage = new Image[6][5];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                String color = shelfie[i][j].getColor();
                Image image = chooseAnImage(color);
                shelfImage[i][j] = image;
            }
        }
        GUIApplication.setShelfies(player,shelfImage);
    }

    // in base alla carta personal estratta (in particolare in base al suo id) viene associata l'immagine corrispondente e settata nella GUI
    @Override
    public void setPlayersPersonalCardView(PersonalGoalCard card) {
        int id = card.getId();
        if(id == 1){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals.png");
            GUIApplication.setPersonalCard(imageUrl);
        }
        if(id==2){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals2.png");
            GUIApplication.setPersonalCard(imageUrl);
        }
        if(id==3){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals3.png");
            GUIApplication.setPersonalCard(imageUrl);
        }
        if(id==4){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals4.png");
            GUIApplication.setPersonalCard(imageUrl);
        }
        if(id==5){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals5.png");
            GUIApplication.setPersonalCard(imageUrl);
        }
        if(id==6){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals6.png");
            GUIApplication.setPersonalCard(imageUrl);
        }
        if(id==7){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals7.png");
            GUIApplication.setPersonalCard(imageUrl);
        }
        if(id==8){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals8.png");
            GUIApplication.setPersonalCard(imageUrl);
        }
        if(id==9){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals9.png");
            GUIApplication.setPersonalCard(imageUrl);
        }
        if(id==10){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals10.png");
            GUIApplication.setPersonalCard(imageUrl);
        }
        if(id==11){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals11.png");
            GUIApplication.setPersonalCard(imageUrl);
        }
        if(id==12){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals12.png");
            GUIApplication.setPersonalCard(imageUrl);
        }
    }

    // in base alla carta common1 estratta (in particolare in base al suo id) viene associata l'immagine corrispondente e settata nella GUI
    @Override
    public void setCommon1View(int id) {
        if(id == 1){
            URL imageUrl = getClass().getResource("/Images/1.jpg");
            GUIApplication.setCommonCard1(imageUrl, id);
        }
        if(id==2){
            URL imageUrl = getClass().getResource("/Images/2.jpg");
            GUIApplication.setCommonCard1(imageUrl, id);
        }
        if(id==3){
            URL imageUrl = getClass().getResource("/Images/3.jpg");
            GUIApplication.setCommonCard1(imageUrl, id);
        }
        if(id==4){
            URL imageUrl = getClass().getResource("/Images/4.jpg");
            GUIApplication.setCommonCard1(imageUrl, id);
        }
        if(id==5){
            URL imageUrl = getClass().getResource("/Images/5.jpg");
            GUIApplication.setCommonCard1(imageUrl, id);
        }
        if(id==6){
            URL imageUrl = getClass().getResource("/Images/6.jpg");
            GUIApplication.setCommonCard1(imageUrl, id);
        }
        if(id==7){
            URL imageUrl = getClass().getResource("/Images/7.jpg");
            GUIApplication.setCommonCard1(imageUrl, id);
        }
        if(id==8){
            URL imageUrl = getClass().getResource("/Images/8.jpg");
            GUIApplication.setCommonCard1(imageUrl, id);
        }
        if(id==9){
            URL imageUrl = getClass().getResource("/Images/9.jpg");
            GUIApplication.setCommonCard1(imageUrl, id);
        }
        if(id==10){
            URL imageUrl = getClass().getResource("/Images/10.jpg");
            GUIApplication.setCommonCard1(imageUrl, id);
        }
        if(id==11){
            URL imageUrl = getClass().getResource("/Images/11.jpg");
            GUIApplication.setCommonCard1(imageUrl, id);
        }
        if(id==12){
            URL imageUrl = getClass().getResource("/Images/12.jpg");
            GUIApplication.setCommonCard1(imageUrl, id);
        }
    }

    // in base alla carta common2 estratta (in particolare in base al suo id) viene associata l'immagine corrispondente e settata nella GUI
    @Override
    public void setCommon2View(int id) {
        if(id == 1){
            URL imageUrl = getClass().getResource("/Images/1.jpg");
            GUIApplication.setCommonCard2(imageUrl, id);
        }
        if(id==2){
            URL imageUrl = getClass().getResource("/Images/2.jpg");
            GUIApplication.setCommonCard2(imageUrl, id);
        }
        if(id==3){
            URL imageUrl = getClass().getResource("/Images/3.jpg");
            GUIApplication.setCommonCard2(imageUrl, id);
        }
        if(id==4){
            URL imageUrl = getClass().getResource("/Images/4.jpg");
            GUIApplication.setCommonCard2(imageUrl, id);
        }
        if(id==5){
            URL imageUrl = getClass().getResource("/Images/5.jpg");
            GUIApplication.setCommonCard2(imageUrl, id);
        }
        if(id==6){
            URL imageUrl = getClass().getResource("/Images/6.jpg");
            GUIApplication.setCommonCard2(imageUrl, id);
        }
        if(id==7){
            URL imageUrl = getClass().getResource("/Images/7.jpg");
            GUIApplication.setCommonCard2(imageUrl, id);
        }
        if(id==8){
            URL imageUrl = getClass().getResource("/Images/8.jpg");
            GUIApplication.setCommonCard2(imageUrl, id);
        }
        if(id==9){
            URL imageUrl = getClass().getResource("/Images/9.jpg");
            GUIApplication.setCommonCard2(imageUrl, id);
        }
        if(id==10){
            URL imageUrl = getClass().getResource("/Images/10.jpg");
            GUIApplication.setCommonCard2(imageUrl, id);
        }
        if(id==11){
            URL imageUrl = getClass().getResource("/Images/11.jpg");
            GUIApplication.setCommonCard2(imageUrl, id);
        }
        if(id==12){
            URL imageUrl = getClass().getResource("/Images/12.jpg");
            GUIApplication.setCommonCard2(imageUrl, id);
        }
    }

    //metodo che io non uso
    @Override
    public void takeableTiles(List<List<Tile>> choosableTilesList, int num) {

    }

    // metodo non usato
    @Override
    public void setPossibleColumns(boolean[] choosableColumns) {

    }

    // metodo al quale arriva un errore che poi stampa lato GUI
    @Override
    public void printError(String error) {
        Platform.runLater(new Thread(()-> {
            GUIApplication.showAlert(Alert.AlertType.WARNING, "Error", error);
            try {
                GUIApplication.changeScene();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        Thread.interrupted();
    }

    // metodo per il settaggio dei punti associati a un determinato player
    @Override
    public void setPlayersPointsView(String player, int points) {
        Platform.runLater(new Thread(()-> {
            int i = 0;
            while (!GUIApplication.getPlayers().get(i).equals(player)){
                i++;
            }
            GUIApplication.setPoints(i,points);
        }));
        Thread.interrupted();
    }

    // metodo al quale arrivano i punti di fine partita legati ai player, si occupa di lancaire la schermata finale per visualizzare
    // vincitore e punteggi
    @Override
    public void setFinalPoints(List<String> users, ArrayList<Integer> points) throws IOException {
        Platform.runLater(new Thread(()-> {
            try {
                GUIApplication.setFinalPoint(users, points);
                GUIApplication.changeScene();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        Thread.interrupted();

    }

    // metodo che lancia un pop up lato GUI per comunicare al player l'id legato al game che ha creato
    @Override
    public void setGameID(int gameId) {
        Platform.runLater(new Thread(()-> {
            if(GUIApplication.getCreateOrJoin().equals("CREATE"))
                GUIApplication.showAlert(Alert.AlertType.INFORMATION, "Create Game ID", "Your game id is " + gameId);
        }));
        Thread.interrupted();


    }

    // non usato
    @Override
    public void setEndToken(String user) {

    }

    //metodo che notifica alla Gui l'inizio della parta e fa partire la schermata di gioco
    @Override
    public void NotifyTurnStart(int maxValueofTiles, String username) {
        Platform.runLater(new Thread(()-> {
            try {
                GUIApplication.changeScene();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }));
        Thread.interrupted();
    }

    // metodo che aggiorna la schermata della Gui nel momento in cui ci sono nuove informazioni che arrivano lato server(le shelfie
    // o le board sono cambiate o altro...)
    @Override
    public void update() {
        Platform.runLater(new Thread(()-> {
            try {
                GUIApplication.changeScene();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }));
        Thread.interrupted();
    }

    // metodo che aggiorna la chat
    @Override
    public void updateChat(List<String> currentChat) throws IOException {
        Platform.runLater(new Thread(()-> {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                for (String str : currentChat) {
                    if(str.length()>33){
                        str = "[DM] " + str;
                    }
                    stringBuilder.append(str).append("\n");
                }
                String joinedString = stringBuilder.toString().trim();
                //String joinedString = String.join("\n", currentChat);
                GUIApplication.setCurrentChat(joinedString);
                GUIApplication.changeScene();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }));
        Thread.interrupted();
    }


    // non usato
    @Override
    public void setThisNick(String nickname) {

    }

    // non usato
    @Override
    public String getTileColor(int row, int col) {
        return null;
    }

    //non usato
    @Override
    public void askNickname() {
    }

    // metodo che fa visualizzare la schermata di scelta join o create
    @Override
    public void joinOrCreate(String username) throws IOException {
        Platform.runLater(new Thread(()-> {
            try {
                GUIApplication.changeScene();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        Thread.interrupted();
    }

    //non usato
    @Override
    public void printTile(String color, int row, int column) {

    }

    //non usato
    @Override
    public void printThings(String s) {

    }

    //non usato
    @Override
    public void printCommands() {

    }

    //metodo che notifica alla Gui che è il momento di chiedere all'utente la colonna
    @Override
    public void askColumn() throws IOException {
        Platform.runLater(new Thread(()-> {
            try {
                GUIApplication.changeScene();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        Thread.interrupted();
    }






    // non usato
    @Override
    public void boardRefill() {

    }

    // immaggino sia un metodo legato alla riconnessione (chiedere ad Alby)
    @Override
    public void recover(int gameID, Tile[][] matrix, ArrayList<Tile[][]> shelfies, int id1, int id2, PersonalGoalCard personalGoalCard, ArrayList<Integer> points, List<String> playerList) {
        setOrderView(new ArrayList<>(playerList));
        setBoardView(matrix);
        setCommon1View(id1);
        setCommon2View(id2);
        //this.gameID=gameID;
        setPlayersPersonalCardView(personalGoalCard);
        for(int i=0; i<playerList.size(); i++){
            setPlayersShelfiesView(playerList.get(i), shelfies.get(i));
            setPlayersPointsView(playerList.get(i), points.get(i));
        }
        update();
    }

    // metodo per cancellare tutti i dati legati alla partita precedente in caso un player volesse giocare ancora
    @Override
    public void clean() {

    }

    // metodo getter per il client
    public ClientInterface getClient(){
        return client;
    }


}