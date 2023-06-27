package it.polimi.it.view.GUI;

import it.polimi.it.network.client.ClientInterface;


//interfaccia utilizzata per astrarre i controller, tutti i controllere delle scene implementano questi metodi
public interface GuiInterface {
    void setClient(ClientInterface client);
}