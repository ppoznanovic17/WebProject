package project.company;

import project.reservation.ReservationRepository;
import project.ticket.TicketRepository;
import project.ticket.dao.TicketDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyRepository {


    private Connection con;

    public static final Object LOCK = new Object();

    private static CompanyRepository instance = null;

    private CompanyRepository() throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/web_project";
        String username = "root";
        String password = "";
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(url, username, password);
    }

    public List<String> getCompaniesForComboBox(){
        List<String> companies = new ArrayList<>();

        try{
            String sql = "SELECT * FROM company";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                String company = rs.getInt(1) + " - " + rs.getString(2);
                companies.add(company);
            }
            return companies;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Company getCompany(int id){
        try{
            String sql = "SELECT * FROM company";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                if(rs.getInt(1 ) == id){
                    Company c = new Company();
                    c.setName(rs.getString(2));
                    c.setId(rs.getInt(1));
                    return c;
                }
            }
            return null;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<TicketDao> getCompaniesTickets(int id){
        Company c = getCompany(id);
        return TicketRepository.getInstance().getTickets("","",null,null,true,true, c.getName());
    }

    public String deleteCompany(int id){

        try{
            String sql = "DELETE FROM company WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,id);
            ps.executeUpdate();
            return "Uspesno je obrisana kompanija.";
        }catch (SQLException e){
            e.printStackTrace();
        }


        try{
            String sql = "DELETE FROM company WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,id);
            ps.executeUpdate();
            return "Uspesno je obrisana kompanija.";
        }catch (SQLException e){
            e.printStackTrace();
        }
        return "Doslo je do greske!";
    }

    public String newCompany(Company c) {
        try {

            String sql = "INSERT INTO company (name , version) VALUES (? , 1)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1,c.getName());
            st.executeUpdate();
            return "Uspesno ste napravili novu kompaniju.";


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "Doslo je do greske pri kreiranju kompanije.";
    }

    public String updateCompany(Company c){
        int previousVersion = c.getVersion();
        int currVersion = 0;
        if(c.getName().isEmpty()){
            return "Ime kompanije ne sme ostati prazno";
        }
        if(c.getName().length()>20){
            return "Ime kompanije ne sme biti duze od 20 karaktera";
        }
        synchronized (LOCK){


            try{
                String sql = "SELECT * FROM company";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()){
                    if(rs.getInt(1) == c.getId()){
                        currVersion = rs.getInt(3);
                    }
                }

            }catch (SQLException e){
                e.printStackTrace();
                return "Ne postoji.";
            }

            if(previousVersion != currVersion){
                return "Greska pri menjanju (optimistic lock).";
            }

            try{
                String sql = "UPDATE company SET name = ? , version = ? WHERE id = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1,c.getName());
                ps.setInt(2,c.getVersion()+1);
                ps.setInt(3, c.getId());
                ps.executeUpdate();
                return "Upsesno promenjeno ime kompanije.";

            }catch (SQLException e){
                e.printStackTrace();

            }

        }
        return "GRESKA.";
    }


    public static CompanyRepository getInstance() {
        if(instance == null) {
            try {
                instance = new CompanyRepository();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
}
