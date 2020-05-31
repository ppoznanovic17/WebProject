package project.ticket.dto;

import java.io.Serializable;
import java.util.Date;


public class TicketDto implements Serializable {

    private String originCity;
    private String destinationCity;
    private String departDate;
    private String returnDate;
    private String companyName;
    private boolean oneWay;
    private boolean twoWay;

    public TicketDto(String originCity, String destinationCity, String departDate, String returnDate, boolean oneWay, boolean twoWay) {
        this.originCity = originCity;
        this.destinationCity = destinationCity;
        this.departDate = departDate;
        this.returnDate = returnDate;
        this.oneWay = oneWay;
        this.twoWay = twoWay;
    }

    public TicketDto() {

    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOriginCity() {
        return originCity;
    }

    public void setOriginCity(String originCity) {
        this.originCity = originCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public String getDepartDate() {
        return departDate;
    }

    public void setDepartDate(String departDate) {
        this.departDate = departDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isOneWay() {
        return oneWay;
    }

    public void setOneWay(boolean oneWay) {
        this.oneWay = oneWay;
    }

    public boolean isTwoWay() {
        return twoWay;
    }

    public void setTwoWay(boolean twoWay) {
        this.twoWay = twoWay;
    }
}


