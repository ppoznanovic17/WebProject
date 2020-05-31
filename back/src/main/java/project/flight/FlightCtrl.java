package project.flight;

import project.user.AuthService;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("flight")
public class FlightCtrl {

    private final FlightService flightService;
    private final AuthService authService;


    public FlightCtrl() {
        flightService = new FlightService();
        this.authService = new AuthService();
    }


    /***
     *  vracam listu stringova formata  id - originCity - destinationCity radi lepseg prikaza u comboboxu
     *  samo za admina jer ce samo on kreirati karte
     * */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/combo")
    public List<String> getCompaniesForComboBox(@HeaderParam("Authorization")String auth){
        if(authService.isAdmin(auth)){
            return flightService.getComboBoxFlight();
        }
        return null;


    }
}
