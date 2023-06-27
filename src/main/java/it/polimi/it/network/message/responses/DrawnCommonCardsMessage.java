package it.polimi.it.network.message.responses;

import it.polimi.it.network.message.Payload;
import java.io.Serializable;
import java.util.List;

/**
 * DrawnCommonCards Message: notifies a TCP client about which CommonCards were extracted.
 */
public class DrawnCommonCardsMessage extends Payload  implements Serializable {

    private static final long serialVersionUID = 8895460901257459083L;
    private int id1;
    private int id2;
    private List<Integer> commonToken1;
    private List<Integer> commonToken2;

    public DrawnCommonCardsMessage(int id1, int id2, List<Integer> commonToken1, List<Integer> commonToken2){
        this.id1 = id1;
        this.id2 = id2;
        this.commonToken1 = commonToken1;
        this.commonToken2 = commonToken2;
    }

    public int getId1() {
        return id1;
    }

    public int getId2() {
        return id2;
    }

    public List<Integer> getCommonToken1() {
        return commonToken1;
    }

    public List<Integer> getCommonToken2() {
        return commonToken2;
    }
}
