package project.flight.dao;


import project.flight.Flight;
import java.io.Serializable;

public class FlightDao implements Serializable {

    private int id;
    private int originCity;
    private int destinationCity;
    private String originCityName;
    private String destinationCityName;

    public FlightDao() {

    }

    public FlightDao(Flight f) {
        id = f.getId();
        originCity = f.getOriginCity();
        destinationCity = f.getDestinationCity();
        destinationCityName = "";
        originCityName = "";
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOriginCity() {
        return originCity;
    }

    public void setOriginCity(int originCity) {
        this.originCity = originCity;
    }

    public int getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(int destinationCity) {
        this.destinationCity = destinationCity;
    }

    public String getOriginCityName() {
        return originCityName;
    }

    public void setOriginCityName(String originCityName) {
        this.originCityName = originCityName;
    }

    public String getDestinationCityName() {
        return destinationCityName;
    }

    public void setDestinationCityName(String destinationCityName) {
        this.destinationCityName = destinationCityName;
    }
}
