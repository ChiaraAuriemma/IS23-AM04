package it.polimi.it.network.message.responses;

import it.polimi.it.network.message.Payload;

public class PossibleColumnsResponse extends Payload {

    private boolean[] choosableColumns;

    public PossibleColumnsResponse(boolean[] choosableColumns){
        this.choosableColumns = choosableColumns;
    }

    public boolean[] getChoosableColumns() {
        return choosableColumns;
    }
}
