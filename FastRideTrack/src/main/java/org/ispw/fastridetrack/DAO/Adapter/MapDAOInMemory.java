package org.ispw.fastridetrack.DAO.Adapter;



public abstract class MapDAOInMemory implements MapDAO {
    @Override
    public String showRoute(String origin, String destination) {
        return "<html><body><h3>[InMemory] Percorso simulato da " + origin + " a " + destination + "</h3></body></html>";
    }

}