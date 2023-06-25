
package com.parkking.connector.cities.opendata.utils;

import java.util.Optional;

/**
 * Utilitaire pour Open data.
 * 
 * @author hary.ratsimba
 *
 */
public class OpenDataUtils {

    private OpenDataUtils() {
        throw new IllegalAccessError("Utils class");
    }

    /**
     * Ajoute a une url le parametre "geofilter.distance".
     * 
     * @param url url.
     * @param lat latitude.
     * @param lng longitude.
     * @param distance en metres.
     * @return l'url avec le parametre "geofilter.distance".
     */
    public static String appendUrlGeoParams(String url, Optional<Double> lat, Optional<Double> lng, Optional<Integer> distance) {
        StringBuilder urlBuilder = new StringBuilder(url);

        // Ex: &geofilter.distance=46.58369748714056,0.3324571251869201,50
        if (lat.isPresent() && lng.isPresent() && distance.isPresent()) {
            urlBuilder.append("&geofilter.distance=");
            urlBuilder.append(String.format("%f,%f,%d", lat.get(), lng.get(), distance.get()));
        }

        return urlBuilder.toString();
    }

}
