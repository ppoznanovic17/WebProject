package project.reservation;


import project.reservation.dao.ReservationDao;
import project.reservation.dto.ReservationDto;
import project.user.AuthService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("reservation")
public class ReservationCtrl {

    private final ReservationService reservationService;
    private final AuthService authService;

    public ReservationCtrl() {
        reservationService = new ReservationService();
        this.authService = new AuthService();
    }


    /***
     * rezervisanje karte
     * samo obican user
     * */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String reserveTicket(@HeaderParam("Authorization")String auth, ReservationDto dto){

        if(authService.isUser(auth)){
            return reservationService.reserveTicket(dto);
        }
        return "Nemate ovlascenje za ovu akciju.";



    }

    /***
     * broj rezervacija
     * samo obican user
     * */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/count/{userId}")
    public int numOfReservations(@HeaderParam("Authorization")String auth, @PathParam("userId") int id){
        if(authService.isUser(auth)){
            return reservationService.numberOfReservationsForUser(id);
        }
        return -1;

    }

    /***
     *rezervacije korisnika
     * samo obican user
     * */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}")
    public List<ReservationDao> userReservations(@HeaderParam("Authorization")String auth, @PathParam("userId") int id){

        if(authService.isUser(auth)){
            return reservationService.getUserReservations(id);
        }
        return null;


    }

}
