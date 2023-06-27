package it.polimi.it.network.server.Exceptions;

import java.io.Serializable;

/**
 * Exception thrown whenever a TCP client isn't found online
 */
public class NotTcpUserException extends Exception  implements Serializable {
    private static final long serialVersionUID = 2453973977976098909L;

    public NotTcpUserException(String message){
        super(message);
    }
}
