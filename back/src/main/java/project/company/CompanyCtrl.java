package project.company;

import project.ticket.dao.TicketDao;
import project.user.AuthService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("company")
public class CompanyCtrl {

    private final CompanyService companyService;
    private final AuthService authService;


    public CompanyCtrl() {
        companyService = new CompanyService();
        this.authService = new AuthService();
    }

    /***
     * vracam listi stringova tipa id - name radi ubacivanja u combo box u frontu za lepsi prikaz
     * samo admin
     * jer samo on ce imati combobox sa pravljenjem karata
     * */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/combo")
    public List<String> getCompaniesForComboBox(@HeaderParam("Authorization")String auth){

        if(authService.isAdmin(auth)){
            return companyService.getCompaniesForComboBox();
        }
        return null;

    }

    /***
     * vracam kompaniju sa odredjenim id-jem
     * svi tipovu usera
     * */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Company getCompany(@HeaderParam("Authorization")String auth, @PathParam("id") int id){

        if(authService.isAuthenticate(auth)){
            return companyService.getCompany(id);
        }
        return null;


    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{name}/company")
    public Company getCompanyByName(@HeaderParam("Authorization")String auth, @PathParam("name") String name){

        if(authService.isAuthenticate(auth)){
            return companyService.getCompanyByName(name);
        }
        return null;


    }

    /***
     * vracam karte odredjene firma
     * u pozadini to radim funkcijom za pretragu i filtriranje koja se nalazi u ticket repo-u
     * svi tipovi usera
     * */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ticket/{id}")
    public List<TicketDao> getCompaniesTickets(@HeaderParam("Authorization")String auth, @PathParam("id") int id){

        if(authService.isAuthenticate(auth)){
            return companyService.getCompaniesTickets(id);
        }
        return null;

    }

    /***
     * brisem kompaniju
     * samo admin
     * */
    //@DELETE
        @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/delete")
    public String deleteCompany(@HeaderParam("Authorization")String auth, @PathParam("id") int id){

        if(authService.isAdmin(auth)){
            return companyService.deleteCompany(id);
        }
        return "Nemate ovlascenje za ovu akciju.";

    }

    /***
     * pravim novu kompaniju
     * samo admin
     * */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/new")
    public String newCompany(@HeaderParam("Authorization")String auth, Company c){
        if(authService.isAdmin(auth)){
            return companyService.newCompany(c);
        }
        return "Nemate ovlascenje za ovu akciju.";


    }

    /***
     * update-jem ime kompanije
     * samo admin
     * */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/update")
    public String updateCompany(@HeaderParam("Authorization")String auth, Company c){

        if(authService.isAdmin(auth)){
            return companyService.updateCompany(c);
        }
        return "Nemate ovlascenje za ovu akciju.";

    }
}
