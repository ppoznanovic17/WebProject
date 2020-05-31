package project.reservation;

import java.io.Serializable;

public class Reservation implements Serializable {

    private int id;
    private int isAvailable;
    private int flightId;
    private int ticketId;
    private int userId;


    public Reservation(int flightId, int ticketId, int userId) {
        this.flightId = flightId;
        this.ticketId = ticketId;
        this.userId = userId;
        isAvailable = 1;
    }

    public Reservation() {
        isAvailable = 1;
    }

    public void setIsAvailable(int isAvailable) {
        this.isAvailable = isAvailable;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public int getIsAvailable() {
        return isAvailable;
    }

    public int getFlightId() {
        return flightId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public int getUserId() {
        return userId;
    }
}
