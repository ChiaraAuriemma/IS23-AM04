package it.polimi.it.network.message.responses;

import it.polimi.it.model.User;
import it.polimi.it.network.message.Payload;

import java.util.List;

public class PointsUpdateMessage extends Payload {

    private User user;
    private Integer point;
    private List<Integer> commonToken1;
    private List<Integer> commonToken2;

    public PointsUpdateMessage(User user, Integer point, List<Integer> commonToken1, List<Integer> commonToken2){
        this.user = user;
        this.point = point;
        this.commonToken1 = commonToken1;
        this.commonToken2 = commonToken2;
    }

    public User getUser() {
        return user;
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