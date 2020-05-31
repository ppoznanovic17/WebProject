package project.ticket;

import project.ticket.dao.TicketDao;
import project.ticket.dto.TicketDto;
import project.ticket.dto.TicketDto2;
import project.user.AuthService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("ticket")
public class TicketCtrl {

    private final TicketService ticketService;
    private final AuthService authService;

    public TicketCtrl() {
        ticketService = new TicketService();
        this.authService = new AuthService();
    }


    @GET
    public String getStr(){
        return "ticket";
    }

    /***
     * funkcija za filtriranje karata po navedenim parametrima
     * bilo koji user
     * */
    @POST
    @Path("filter")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) //Bitno je da navedemo sta je rezultujuci content type ove metode
    public List<TicketDao> getTickets(@HeaderParam("Authorization")String auth,TicketDto dto) {
        if(authService.isAuthenticate(auth)){
            return ticketService.getTickets(dto.getOriginCity(), dto.getDestinationCity(), dto.getDepartDate(), dto.getReturnDate(), dto.isOneWay(), dto.isTwoWay(), dto.getCompanyName());
        }
        return null;
    }

    /***
     * pravljenje nove karte
     * samo admin
     * */
    @POST
    @Path("new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) //Bitno je da navedemo sta je rezultujuci content type ove metode
    public String newTicket(@HeaderParam("Authorization")String auth, TicketDto2 dto) {
        if(authService.isAdmin(auth)){
            return ticketService.newTicket(dto);
        }
        return "Nemate ovlascenje za ovu akciju.";

    }

    /***
     *  brisanje karata
     *  samo admin
     * */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteTicket(@HeaderParam("Authorization")String auth, @PathParam("id") int id){
        if(authService.isAdmin(auth)){
            return ticketService.deleteTicket(id);
        }
        return "Nemate ovlascenje za ovu akciju.";

    }

    /***
     *vracanje karte na osnovu id-a
     * bilo koji user
     * */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Ticket getTicket(@HeaderParam("Authorization")String auth, @PathParam("id") int id){

        if(authService.isAuthenticate(auth)){
            return ticketService.getTicket(id);
        }
        return null;

    }

    /***
     * vracanje dao liste gde je mnogo lepsi prikaz karata, svi strani kljucevi zamenjeni su atributima koji su citljiviji korisnicima
     * bilo koji user
     * */
    @GET
    @Path("dao/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public TicketDao getTicketDao(@HeaderParam("Authorization")String auth, @PathParam("id") int id){

        if(authService.isAuthenticate(auth)){
            return ticketService.getTicketDaoFromTicket(id);
        }
        return null;


    }

    /***
     * update karte
     * samo admin
     * */
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String updateTicket(@HeaderParam("Authorization")String auth, @PathParam("id") int id, TicketDto2 newTicket){

        if(authService.isAdmin(auth)){
            return ticketService.updateTicket(id, newTicket);
        }
        return "Nemate ovlascenje za ovu akciju.";


    }

}
