package it.polimi.it.view.GUI;

import it.polimi.it.network.client.ClientInterface;


/**
 * Interface used to abstract GUI controllers
 */
public interface GuiInterface {
    /**
     * Setter method for the reference of the client
     * @param client clientRef is the reference of the client
     */
    void setClient(ClientInterface client);
}