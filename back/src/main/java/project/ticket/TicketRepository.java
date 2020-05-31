package project.ticket;


import project.city.City;
import project.company.Company;
import project.flight.Flight;
import project.flight.dao.FlightDao;
import project.ticket.dao.TicketDao;
import project.ticket.dto.TicketDto2;
import project.utils.Util;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TicketRepository {

    private Connection con;

    public static final Object LOCK = new Object();

    private static TicketRepository instance = null;

    private TicketRepository() throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/web_project";
        String username = "root";
        String password = "";
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(url, username, password);
    }

    public List<TicketDao> getTickets(String originCity, String destinationCity, String departDateString, String returnDateString, boolean oneWay, boolean twoWay, String companyName){
       return getTicketsRepository( originCity,  destinationCity,  departDateString,  returnDateString,  oneWay,  twoWay, companyName);
    }

    public String deleteTicket(int id){
        return deleteTicketRepository(id);
    }

    public String updateTicket(int id, TicketDto2 newTicket){
        return updateTicketRepository(id, newTicket);
    }

    public String newTicket(TicketDto2 t){
        return newTicketRepository(t);
    }

    public Ticket getTicket(int id){
        return  getTicketRepository(id);
    }

    public TicketDao getDaoFromTicket(int id) {
        Ticket ticket = null;

        FlightDao flightDao = new FlightDao();

        try{
            String sql = "SELECT * FROM ticket";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                if(rs.getInt(1) == id){
                    ticket = new Ticket();
                    ticket.setId(rs.getInt(1));
                    ticket.setOneWay(rs.getInt(2));
                    ticket.setDepartDate(rs.getDate(3));
                    ticket.setReturnDate(rs.getDate(4));
                    ticket.setTicketCount(rs.getInt(5));
                    ticket.setCompanyId(rs.getInt(6));
                    ticket.setFlightId(rs.getInt(7));
                    ticket.setVersion(rs.getInt(8));
                    break;
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
            return null;

        }

        if(ticket == null) return null;

        try{
            String sql = "SELECT * FROM flight";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                if(rs.getInt(1) == ticket.getFlightId()){
                    flightDao.setOriginCity(rs.getInt(2));
                    flightDao.setDestinationCity(rs.getInt(3));
                    break;
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
            return null;

        }

        try{
            String sql = "SELECT * FROM city";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                if(rs.getInt(1) == flightDao.getOriginCity()){
                    flightDao.setOriginCityName(rs.getString(2));

                }
                if(rs.getInt(1) == flightDao.getDestinationCity()){
                    flightDao.setDestinationCityName(rs.getString(2));
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }

        String company = null;
        try{
            String sql = "SELECT * FROM company";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                if(rs.getInt(1) == ticket.getCompanyId()){

                    company = rs.getString(2);
                    break;
                }

            }

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }

        TicketDao ticketDao = new TicketDao();
        ticketDao.setId(ticket.getId());
        ticketDao.setOneWay(ticket.getOneWay());
        ticketDao.setDepartDate(ticket.getDepartDate());
        ticketDao.setReturnDate(ticket.getReturnDate());
        ticketDao.setCompanyName(company);
        ticketDao.setOriginCity(flightDao.getOriginCityName());
        ticketDao.setDestinationCity(flightDao.getDestinationCityName());
        ticketDao.setCompanyId(ticket.getCompanyId());
        ticketDao.setFlightId(ticket.getFlightId());

        return ticketDao;
    }


    private Ticket getTicketRepository(int id){
        try{
            String sql = "SELECT * FROM ticket";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                if(rs.getInt(1) == id){
                    Ticket t = new Ticket();
                    t.setId(rs.getInt(1));
                    t.setOneWay(rs.getInt(2));
                    t.setDepartDate(rs.getDate(3));
                    t.setReturnDate(rs.getDate(4));
                    t.setTicketCount(rs.getInt(5));
                    t.setCompanyId(rs.getInt(6));
                    t.setFlightId(rs.getInt(7));
                    t.setVersion(rs.getInt(8));
                    return t;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<TicketDao> getTicketsRepository(String originCity, String destinationCity, String departDateString, String returnDateString, boolean oneWay, boolean twoWay, String companyName) {
        originCity = Util.toEmpty(originCity);
        destinationCity = Util.toEmpty(destinationCity);
        String ticketType = ticketType(oneWay, twoWay);
        if(ticketType.equals("one")) returnDateString = null;
        List<Ticket> ticketList = new ArrayList();
        List<TicketDao> ticketDaoList = new ArrayList();
        HashMap<Integer, Flight> flightHashMap = new HashMap();
        HashMap<Integer, City> cityHashMap = new HashMap();
        HashMap<Integer, Company> companyHashMap = new HashMap();

        try{
            String sql = "SELECT * FROM ticket";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                if((ticketType.equals("one") && rs.getInt(2)==1) || (ticketType.equals("two") && rs.getInt(2)==0) || ticketType.equals("both")){
                    if(Util.isEmpty(departDateString) && Util.isEmpty(returnDateString)){ //|| rs.getDate(3).equals(departDate) && rs.getDate(4).equals(returnDate)){
                        Ticket t = new Ticket();
                        t.setOneWay(rs.getInt(2));
                        t.setDepartDate(rs.getDate(3));
                        if(t.getOneWay() == 1){
                            t.setReturnDate(null);
                        }else {
                            t.setReturnDate(rs.getDate(4));
                        }
                        t.setId(rs.getInt(1));
                        t.setCompanyId(rs.getInt(6));
                        t.setFlightId(rs.getInt(7));
                        ticketList.add(t);

                    }else if(!Util.isEmpty(departDateString) && Util.isEmpty(returnDateString)){
                        Ticket t = new Ticket();
                        t.setOneWay(rs.getInt(2));
                        t.setDepartDate(rs.getDate(3));
                        if(t.getOneWay() == 1){
                            t.setReturnDate(null);
                        }else {
                            t.setReturnDate(rs.getDate(4));
                        }
                        t.setId(rs.getInt(1));
                        t.setCompanyId(rs.getInt(6));
                        t.setFlightId(rs.getInt(7));
                        System.out.println(departDateString + " " + t.getDepartDate().toString());
                        if(departDateString.equals(t.getDepartDate().toString())){
                            System.out.println("a");
                            ticketList.add(t);
                        }

                    }else if(Util.isEmpty(departDateString) && !Util.isEmpty(departDateString)){
                        Ticket t = new Ticket();
                        t.setOneWay(rs.getInt(2));
                        t.setDepartDate(rs.getDate(3));
                        if(t.getOneWay() == 1){
                            t.setReturnDate(null);
                        }else {
                            t.setReturnDate(rs.getDate(4));
                        }
                        t.setId(rs.getInt(1));
                        t.setCompanyId(rs.getInt(6));
                        t.setFlightId(rs.getInt(7));
                        if(returnDateString.equals(t.getDepartDate())) ticketList.add(t);
                    }else {
                        Ticket t = new Ticket();
                        t.setOneWay(rs.getInt(2));
                        t.setDepartDate(rs.getDate(3));
                        if(t.getOneWay() == 1){
                            t.setReturnDate(null);
                        }else {
                            t.setReturnDate(rs.getDate(4));
                        }
                        t.setId(rs.getInt(1));
                        t.setCompanyId(rs.getInt(6));
                        t.setFlightId(rs.getInt(7));
                        if(returnDateString.equals(t.getDepartDate()) && departDateString.equals(t.getReturnDate())) ticketList.add(t);
                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try{
            String sql = "SELECT * FROM flight";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                Flight f = new Flight();
                f.setOriginCity(rs.getInt(2));
                f.setDestinationCity(rs.getInt(3));
                f.setId(rs.getInt(1));
                flightHashMap.put(f.getId(), f);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        try{
            String sql = "SELECT * FROM city";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                City c = new City();
                c.setName(rs.getString(2));
                c.setId(rs.getInt(1));
                cityHashMap.put(c.getId(), c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try{
            String sql = "SELECT * FROM company";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                Company c = new Company();
                c.setName(rs.getString(2));
                c.setId(rs.getInt(1));
                companyHashMap.put(c.getId(), c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(Ticket t: ticketList){
            TicketDao ticketDao = new TicketDao(t);
            Flight f = flightHashMap.get(ticketDao.getFlightId());
            String origin = cityHashMap.get(f.getOriginCity()).getName();
            String destination = cityHashMap.get(f.getDestinationCity()).getName();
            Company c = companyHashMap.get(t.getCompanyId());
            if(origin.toLowerCase().contains(originCity.toLowerCase()) && destination.toLowerCase().contains(destinationCity.toLowerCase()) && c.getName().toLowerCase().contains(companyName.toLowerCase())){
                ticketDao.setOriginCity(origin);
                ticketDao.setDestinationCity(destination);
                ticketDao.setCompanyName(c.getName());
                ticketDaoList.add(ticketDao);

            }
        }

        return ticketDaoList;
    }

    private String deleteTicketRepository(int id){

        try {
            String sql = "DELETE FROM ticket WHERE id = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1,id);
            st.executeUpdate();
            return "Uspesno brisanje";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Doslo je do greske.";
    }

    private synchronized String updateTicketRepository(int id, TicketDto2 newTicket) {
        Ticket old = getTicket(id);
        TicketDto2 t = newTicket;
        try {
            if(old.getVersion() == newTicket.getVersion()){
                String sql = "UPDATE ticket SET one_way = ? , depart_date = ?, return_date = ?, ticket_count = ?, company_id = ? , flight_id = ? , version = ? WHERE id = ?";
                PreparedStatement st1 = con.prepareStatement(sql);
                st1.setInt(1,t.getOneWay());
                System.out.println(t.getDepartDate());
                System.out.println(t.getReturnDate());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(t.getDepartDate());
                st1.setDate(2, new java.sql.Date(date.getTime()));
                if(t.getOneWay()==1){
                    st1.setDate(3, null);
                }else{
                    if(t.getReturnDate()!= null){
                        date = sdf.parse(t.getReturnDate());
                        st1.setDate(3, new java.sql.Date(date.getTime()));
                    }else{
                        return "Morate uneti datum povratka jer je karta u dva smera!";
                    }
                }
                st1.setInt(4,t.getTicketCount());
                st1.setInt(5,t.getCompanyId());
                st1.setInt(6,t.getFlightId());
                st1.setInt(7,t.getVersion()+1);
                st1.setInt(8, id);
                st1.executeUpdate();
                return "Uspesno napravljene izmene.";
            }
            return "Doslo je do greske ( optimistic lock).";


        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }

        return "Neocekivana greska.";
    }

    private String newTicketRepository(TicketDto2 t){
        try {

                String sql = "INSERT INTO ticket (one_way , depart_date , return_date , ticket_count , company_id , flight_id , version) VALUES (? , ? , ? , ? , ? , ? , 1)";
                PreparedStatement st1 = con.prepareStatement(sql);
                st1.setInt(1,t.getOneWay());
                System.out.println(t.getDepartDate());
                System.out.println(t.getReturnDate());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(t.getDepartDate());
                st1.setDate(2, new java.sql.Date(date.getTime()));
                if(t.getOneWay()==1){
                    st1.setDate(3, null);
                }else{
                    if(t.getReturnDate()!= null){
                        date = sdf.parse(t.getReturnDate());
                        st1.setDate(3, new java.sql.Date(date.getTime()));
                    }else{
                        return "Morate uneti datum povratka jer je karta u dva smera!";
                    }
                }
                st1.setInt(4,t.getTicketCount());
                st1.setInt(5,t.getCompanyId());
                st1.setInt(6,t.getFlightId());
                st1.executeUpdate();
                return "Uspesno dodata karta.";


        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }

        return "Nepredvidjena greska.";
    }

    public String reserve(int id){

        Ticket ticket = null;

        try{
            String sql = "SELECT * FROM ticket";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                if(rs.getInt(1) == id){
                    Ticket t = new Ticket();
                    t.setId(rs.getInt(1));
                    t.setOneWay(rs.getInt(2));
                    t.setDepartDate(rs.getDate(3));
                    t.setReturnDate(rs.getDate(4));
                    t.setTicketCount(rs.getInt(5));
                    t.setCompanyId(rs.getInt(6));
                    t.setFlightId(rs.getInt(7));
                    t.setVersion(rs.getInt(8));
                    ticket = t;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(ticket == null){
            return "Nepredvidjena greska.";
        }

        try {
            if(ticket.getTicketCount() == 0){
                return "Nema vise karata.";
            }
                String sql = "UPDATE ticket SET ticket_count = ?, version = ? WHERE id = ?";
                PreparedStatement st1 = con.prepareStatement(sql);
                st1.setInt(1, ticket.getTicketCount()-1);
                st1.setInt(2, ticket.getVersion()+1);
                st1.setInt(3, ticket.getId());
                st1.executeUpdate();
                return "Uspesno napravljene izmene.";




        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "Greska.";
    }

    private String ticketType(boolean oneWay, boolean twoWay) {
        if(oneWay && twoWay) {
            return "both";
        }
        else{
            if(oneWay) return "one";
            else return "two";
        }

    }

    public static TicketRepository getInstance() {
        if(instance == null) {
            try {
                instance = new TicketRepository();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
}
