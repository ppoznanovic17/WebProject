package project.reservation;

import project.company.Company;
import project.reservation.dao.ReservationDao;
import project.reservation.dto.ReservationDto;
import project.ticket.Ticket;
import project.ticket.TicketRepository;
import project.ticket.dao.TicketDao;

import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ReservationRepository {

    private Connection con;

    public static final Object LOCK = new Object();


    private static ReservationRepository instance = null;

    private ReservationRepository() throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/web_project";
        String username = "root";
        String password = "";
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(url, username, password);
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

    public synchronized String reserveTicket(ReservationDto reservationDto){
        try {

            String msg = TicketRepository.getInstance().reserve(reservationDto.getTicketId());

            if(msg.toLowerCase().contains("greska") || msg.toLowerCase().contains("nema vise karata")){
                return msg;
            }


            String sql = "INSERT INTO reservation (is_available , flight_id , ticket_id , user_id) VALUES (1 , ? , ? , ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1,reservationDto.getFlightId());
            st.setInt(2,reservationDto.getTicketId());
            st.setInt(3,reservationDto.getUserId());
            st.executeUpdate();
            return "Uspesno ste rezervisali.";


        } catch (SQLException e) {
            e.printStackTrace();
        }
       return "Greska.";
    }

  /*  public Reservation getReservation(int id){

        try{
            String sql = "SELECT * FROM reservation";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                if(rs.getInt(1 ) == id){

                }
            }
            return null;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;

    }*/

    public int countReservations(int userId){
        try {
            String sql = "SELECT * FROM reservation";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            int cnt = 0;
            while(rs.next()){
                if(rs.getInt(5) == userId){
                    cnt++;
                }
            }
            return cnt;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public List<ReservationDao> getUserReservation(int id) {
        List<Reservation> reservations = new ArrayList();
        List<ReservationDao> reservationDaoList = new ArrayList();
        HashMap<Integer,TicketDao> ticketDaoList = new HashMap<>();

        try{
            String sql = "SELECT * FROM reservation";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                if(rs.getInt(5) == id){
                    Reservation r = new Reservation();
                    r.setIsAvailable(rs.getInt(2));
                    r.setFlightId(rs.getInt(3));
                    r.setTicketId(rs.getInt(4));
                    r.setUserId(rs.getInt(5));
                    reservations.add(r);
                }
            }

            if(reservations.size() == 0) return null;

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }

        for(Reservation r : reservations){
            reservationDaoList.add(new ReservationDao(r));
        }

        try{
            String sql = "SELECT * FROM ticket";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                for(ReservationDao r : reservationDaoList){

                    Date d = new Date();
                    if(r.getTicketId() == rs.getInt(1)){
                        TicketDao dao = TicketRepository.getInstance().getDaoFromTicket(rs.getInt(1));
                        if(d.after(dao.getDepartDate())) r.setIsAvailable(0);
                        r.setTicketDao(dao);
                    }
                }
            }

            if(reservationDaoList.size() == 0) return null;
            return reservationDaoList;

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }



    }



    public static ReservationRepository getInstance() {
        if(instance == null) {
            try {
                instance = new ReservationRepository();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
}
