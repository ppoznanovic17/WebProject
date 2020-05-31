package project.flight;

import java.io.Serializable;

public class Flight implements Serializable {

    private int id;
    private int originCity;
    private int destinationCity;


    public Flight() {

    }

    public Flight(int originCity, int destinationCity) {
        this.originCity = originCity;
        this.destinationCity = destinationCity;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
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
}
