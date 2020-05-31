package project.ticket;

import project.ticket.dao.TicketDao;
import project.ticket.dto.TicketDto2;

import java.util.Date;
import java.util.List;

public class TicketService {


    public List<TicketDao> getTickets(String originCity, String destinationCity, String departDate, String returnDate, boolean oneWay, boolean twoWay, String companyName) {
        return TicketRepository.getInstance().getTickets(originCity, destinationCity, departDate, returnDate, oneWay, twoWay, companyName);
    }

    public String deleteTicket(int id) {
        return TicketRepository.getInstance().deleteTicket(id);
    }

    public String updateTicket(int id, TicketDto2 newTicket) {
        return TicketRepository.getInstance().updateTicket(id, newTicket);
    }

    public String newTicket(TicketDto2 t){
        return TicketRepository.getInstance().newTicket(t);
    }

    public Ticket getTicket(int id){
        return TicketRepository.getInstance().getTicket(id);
    }

    public TicketDao getTicketDaoFromTicket(int id){
        return  TicketRepository.getInstance().getDaoFromTicket(id);
    }

}
