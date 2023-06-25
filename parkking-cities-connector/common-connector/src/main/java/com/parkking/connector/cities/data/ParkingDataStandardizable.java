
package com.parkking.connector.cities.data;

/**
 * Contrat standard auquel doivent repondre les donnees de parking.
 * 
 * @author hary.ratsimba
 *
 */
public interface ParkingDataStandardizable {

    /**
     * @return le nom du parking.
     */
    public String getName();

    /**
     * @return la capacite du parking.
     */
    public int getCapacity();

    /**
     * @return le nb de places restantes.
     */
    public Integer getRemaining();

    /**
     * @return la latitude.
     */
    public Double getLat();

    /**
     * @return la longitude.
     */
    public Double getLng();

}
