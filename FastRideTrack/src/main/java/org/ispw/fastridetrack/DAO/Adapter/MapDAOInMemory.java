package org.ispw.fastridetrack.dao.adapter;



public abstract class MapDAOInMemory implements MapDAO {
    @Override
    public String showRoute(String origin, String destination) {
        return "<html><body><h3>[inmemory] Percorso simulato da " + origin + " a " + destination + "</h3></body></html>";
    }

}