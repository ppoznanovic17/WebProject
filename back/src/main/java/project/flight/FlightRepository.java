package project.flight;

import project.city.City;
import project.company.CompanyRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FlightRepository {


    private Connection con;

    public static final Object LOCK = new Object();

    private static FlightRepository instance = null;

    private FlightRepository() throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/web_project";
        String username = "root";
        String password = "";
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(url, username, password);
    }


    public List<String> getComboBoxFlight(){
        HashMap<Integer, String> cityMap = new HashMap<>();
        List<Flight> flights = new ArrayList<>();
        List<String> returnList = new ArrayList<>();

        try{
            String sql = "SELECT * FROM flight";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                Flight f = new Flight();
                f.setId(rs.getInt(1));
                f.setOriginCity(rs.getInt(2));
                f.setDestinationCity(rs.getInt(3));
                flights.add(f);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        try{
            String sql = "SELECT * FROM city";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                cityMap.put(rs.getInt(1), rs.getString(2));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        for(Flight f : flights){
            returnList.add(f.getId() + " - " + cityMap.get(f.getOriginCity()) + " - " + cityMap.get(f.getDestinationCity()) );
        }
        return returnList;
    }

    public static FlightRepository getInstance() {
        if(instance == null) {
            try {
                instance = new FlightRepository();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
}
