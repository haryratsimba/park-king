
package com.parkking.service;

import java.util.List;

import com.parkking.dto.City;

/**
 * Service des donnees de villes.
 * 
 * @author hary.ratsimba
 *
 */
public interface CityService {

    /**
     * @return Toutes les villes disponibles et activees.
     */
    public List<City> getAllCities();

    /**
     * Retourne les villes qui correspond aux coordonnees en parametres.
     * 
     * @param lat latitude.
     * @param lng longitude.
     * @return les villes qui correspondent aux coordonnees en parametres.
     */
    public List<City> getCoordsBelongingCities(double lat, double lng);

    /**
     * Retourne la ville qui correspond au code en parametre.
     * 
     * @param cityCode de la ville.
     * @return la ville qui correspond au code en parametre.
     */
    public City getCityByCode(String cityCode);

}
