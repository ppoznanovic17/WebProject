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
        dto.setUserId(authService.logId(auth));
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
    @Path("/count")
    public int numOfReservations(@HeaderParam("Authorization")String auth){
        int userId = authService.logId(auth);
        System.out.println(userId);
        if(authService.isUser(auth)){
            return reservationService.numberOfReservationsForUser(userId);
        }
        return -1;

    }

    /***
     *rezervacije korisnika
     * samo obican user
     * */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ReservationDao> userReservations(@HeaderParam("Authorization")String auth){
        int userId = authService.logId(auth);
        System.out.println(userId);
        if(authService.isUser(auth)){
            return reservationService.getUserReservations(userId);
        }
        return null;


    }
    /***
     * brisem rezervaciju ali samo ako je datum polaska rezervacije dva dana posle otkazivanja
     * samo obican user
     * */
    //@DELETE
    // Koristio sam get za brisanje, a post za menjanje jer mi CORS ne radi za delete i put metode
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{reservationId}/delete")
    public String cancelReservation(@HeaderParam("Authorization")String auth, @PathParam("reservationId") int reservationId){
        int userId = authService.logId(auth);
        System.out.println(userId);
        if(authService.isUser(auth)){
            return reservationService.cancelReservation(userId, reservationId);
        }
        return null;
    }

}
