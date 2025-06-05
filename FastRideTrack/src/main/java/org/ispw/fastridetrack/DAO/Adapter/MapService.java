package org.ispw.fastridetrack.DAO.Adapter;

import org.ispw.fastridetrack.Bean.MapRequestBean;
import org.ispw.fastridetrack.Model.Map;

public interface MapService {
    Map calculateRoute(MapRequestBean requestBean);
}




