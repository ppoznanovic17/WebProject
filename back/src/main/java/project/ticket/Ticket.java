package project.ticket;

import java.io.Serializable;
import java.util.Date;

public class Ticket implements Serializable {

    private int id;
    private int oneWay;
    private Date departDate;
    private Date returnDate;
    private int ticketCount;
    private int companyId;
    private int flightId;
    private int version;

    public Ticket() {

    }

    public Ticket(int oneWay, Date departDate, Date returnDate, int count, int companyId, int flightId) {
        this.oneWay = oneWay;
        this.departDate = departDate;
        this.returnDate = returnDate;
        this.ticketCount = count;
        this.companyId = companyId;
        this.flightId = flightId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
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



    public int getCompanyId() {
        return companyId;
    }

    public int getFlightId() {
        return flightId;
    }
}
