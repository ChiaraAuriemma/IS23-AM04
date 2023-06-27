package it.polimi.it.network.message.responses;

import it.polimi.it.network.message.Payload;
import java.io.Serializable;
import java.util.List;

/**
 * PointsUpdate Message: notifies a TCP client about notifies a TCP client about the latest players' points.
 */
public class PointsUpdateMessage extends Payload  implements Serializable {

    private static final long serialVersionUID = -8843338650621711142L;
    private String username;
    private Integer point;
    private List<Integer> commonToken1;
    private List<Integer> commonToken2;

    public PointsUpdateMessage(String username, Integer point, List<Integer> commonToken1, List<Integer> commonToken2){
        this.username = username;
        this.point = point;
        this.commonToken1 = commonToken1;
        this.commonToken2 = commonToken2;
    }

    public String getUsername() {
        return username;
    }

    public Integer getPoint() {
        return point;
    }

    public List<Integer> getCommonToken2() {
        return commonToken2;
    }

    public List<Integer> getCommonToken1() {
        return commonToken1;
    }
}
