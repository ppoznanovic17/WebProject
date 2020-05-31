package project.reservation.dao;

import project.reservation.Reservation;
import project.ticket.dao.TicketDao;

import java.io.Serializable;

public class ReservationDao implements Serializable {

    private int id;
    private int isAvailable;
    private TicketDao ticketDao;
    private String username;
    private int userId;
    private int ticketId;

    public ReservationDao() {
    }

    public ReservationDao (Reservation res){
        id = res.getId();
        isAvailable = res.getIsAvailable();
        userId = res.getUserId();
        ticketDao = null;
        ticketId = res.getTicketId();
    }


    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getUserId() {
        return userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(int isAvailable) {
        this.isAvailable = isAvailable;
    }

    public TicketDao getTicketDao() {
        return ticketDao;
    }

    public void setTicketDao(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }
}
