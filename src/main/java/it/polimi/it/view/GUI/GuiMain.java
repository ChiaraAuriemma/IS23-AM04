package it.polimi.it.view.GUI;

import it.polimi.it.model.Card.CommonGoalCards.CommonGoalCard;
import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.view.ViewInterface;

import java.util.ArrayList;
import java.util.List;

public class GuiMain implements ViewInterface {

    private GameStartController gameStartController;
    private GameViewController gameViewController;
    private CreateGameViewController createGameViewController;
    private JoinGameController joinGameController;
    private LoginController loginController;
    private OtherShelfieController otherShelfieController;
    private PersonalGoalCardController personalGoalCardController;
    private CommonGoalCardController commonGoalCardController;


    @Override
    public void setOrderView(ArrayList<String> order) {

    }

    @Override
    public void setBoardView(Tile[][] matrix) {

    }

    @Override
    public void setPlayersShelfiesView(String player, Tile[][] shelfie) {

    }

    @Override
    public void setPlayersPersonalCardView(PersonalGoalCard card) {

    }

    @Override
    public void setCommon1View(CommonGoalCard card1) {

    }

    @Override
    public void setCommon2View(CommonGoalCard card2) {

    }

    @Override
    public void takeableTiles(List<List<Tile>> choosableTilesList) {

    }

    @Override
    public void setPossibleColumns(boolean[] choosableColumns) {

    }

    @Override
    public void printError(String error) {

    }

    @Override
    public void setPlayersPointsView(String player, int points) {

    }

    @Override
    public void setFinalPoints(List<String> users, ArrayList<Integer> points) {

    }

    @Override
    public void setGameID(int gameId) {

    }

    @Override
    public void setEndToken(String user) {

    }

    @Override
    public void NotifyTurnStart(int maxValueofTiles, String username) {

    }

    @Override
    public void update() {

    }

    @Override
    public void updateChat(List<String> currentChat) {

    }

    @Override
    public void setThisNick(String nickname) {

    }

    @Override
    public String getTileColor(int row, int col) {
        return null;
    }

    @Override
    public void askNickname() {

    }

    @Override
    public void joinOrCreate(String username) {

    }

    @Override
    public void printTile(String color, int row, int column) {

    }

    @Override
    public void printThings(String s) {

    }

    @Override
    public void printCommands() {

    }

    @Override
    public void askColumn() {

    }

    @Override
    public void askNicknameAgain(String errorMessage) {

    }

    @Override
    public void askNumPlayerAgain() {

    }

    @Override
    public void askIDAgain() {

    }

    @Override
    public void askNumTilesAgain() {

    }

    @Override
    public void askTilesAgain() {

    }

    @Override
    public void askColumnAgain() {

    }

    @Override
    public void boardRefill() {

    }
}
