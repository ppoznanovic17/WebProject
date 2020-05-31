package project.user;



import project.ticket.TicketService;
import project.ticket.dao.TicketDao;
import project.user.dto.UserDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.util.List;

@Path("user")
public class UserCtrl {

    private final AuthService authService;
    private final UserService userService;
    private final TicketService ticketService;

    public UserCtrl() {
        this.userService = new UserService();
        this.authService = new AuthService();
        this.ticketService = new TicketService();
    }


    @OPTIONS
    @Path("{path : .*}")
    public Response options() {
        return Response.ok("")
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .build();
    }

    /***
     * vraca token ako su dobri kredencijali
     * bilo koji user
     * */
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String logIn(UserDto auth) {
        return authService.logInToken(auth.getUsername(), auth.getPassword());
    }


    /***
     * registracija
     * bilo ko
     * */
    @POST
    @Path("reg")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String register(UserDto userDto) {
        return userService.createUser(userDto);
    }


    /***
     * kreiranje admina
     * samo admin
     * */
    @POST
    @Path("create_admin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createAdmin(@HeaderParam("Authorization")String auth, UserDto userDto) {
        if(authService.isAdmin(auth)){
            return userService.createAdmin(userDto);
        }
            return "Nemate dozvolu za kreiranje novog admina.";

    }


}
