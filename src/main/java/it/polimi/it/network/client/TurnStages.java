package it.polimi.it.network.client;

public enum TurnStages {
    NOTURN,

    NOTHING, //hai creato il gioco, ma non ci sono i giocatori quindi non puoi neanche usare la chat
    LOGIN,
    CREATEorJOIN,
    TILESNUM,
    CHOOSETILES,
    CHOOSECOLUMN;
}
