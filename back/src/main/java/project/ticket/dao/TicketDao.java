package project.ticket.dao;

import project.ticket.Ticket;

import java.io.Serializable;
import java.util.Date;


public class TicketDao implements Serializable {

    private int id;
    private int oneWay;
    private Date departDate;
    private Date returnDate;
    private String companyName;
    private String originCity;
    private String destinationCity;
    private int companyId;
    private int flightId;

    public TicketDao(Ticket t) {
        id = t.getId();
        oneWay = t.getOneWay();
        departDate = t.getDepartDate();
        returnDate = t.getReturnDate();
        companyId = t.getCompanyId();
        flightId = t.getFlightId();
        companyName = "";
        originCity = "";
        destinationCity = "";
    }

    public TicketDao(int id, int oneWay, Date departDate, Date returnDate, String companyName, String originCity, String destinationCity, int companyId, int flightId) {
        this.id = id;
        this.oneWay = oneWay;
        this.departDate = departDate;
        this.returnDate = returnDate;
        this.companyName = companyName;
        this.originCity = originCity;
        this.destinationCity = destinationCity;
        this.companyId = companyId;
        this.flightId = flightId;
    }

    public TicketDao() {

    }


    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOneWay(int oneWay) {
        this.oneWay = oneWay;
    }

    public void setDepartDate(Date departDate) {
        this.departDate = departDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setOriginCity(String originCity) {
        this.originCity = originCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public int getId() {
        return id;
    }

    public int getOneWay() {
        return oneWay;
    }

    public Date getDepartDate() {
        return departDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getOriginCity() {
        return originCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }
}
