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

    public PointsUpdateMessage(String username, Integer point){
        this.username = username;
        this.point = point;
    }

    public String getUsername() {
        return username;
    }

    public Integer getPoint() {
        return point;
    }
}
