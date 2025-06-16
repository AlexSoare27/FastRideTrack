package org.ispw.fastridetrack.dao.adapter;

import org.ispw.fastridetrack.bean.MapRequestBean;
import org.ispw.fastridetrack.exception.MapServiceException;
import org.ispw.fastridetrack.model.Map;

public interface MapService {
    Map calculateRoute(MapRequestBean requestBean) throws MapServiceException;
    String getAddressFromCoordinates(double latitude, double longitude) throws MapServiceException;
}





