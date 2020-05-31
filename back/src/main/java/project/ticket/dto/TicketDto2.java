package project.ticket.dto;

import java.io.Serializable;
import java.util.Date;

public class TicketDto2 implements Serializable {

    private int id;
    private int oneWay;
    private String departDate;
    private String returnDate;
    private int ticketCount;
    private int companyId;
    private int flightId;
    private int version;

    public TicketDto2(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOneWay() {
        return oneWay;
    }

    public void setOneWay(int oneWay) {
        this.oneWay = oneWay;
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

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int countTicket) {
        this.ticketCount = countTicket;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
