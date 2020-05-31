package project.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import project.user.dto.UserDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private Connection con;

    public static final Object LOCK = new Object();

    private static UserRepository instance = null;

    private UserRepository() throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/web_project";
        String username = "root";
        String password = "";
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(url, username, password);
    }



    public String logInToken(String username, String password) {

        String sql;
        sql = "SELECT * FROM user";
        List<User> user = new ArrayList();
        Integer id = 0;
        try {

            Statement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){

                if(rs.getString(2).equals(username) && rs.getString(3).equals(password)){
                    User u = new User(rs.getString(2), rs.getString(3), rs.getString(4));
                    id = rs.getInt(1);
                    String idStr = String.valueOf(id);
                    //System.out.println(rs.getInt(1) + "aaaaaaaaa");
                    Algorithm alg = Algorithm.HMAC256("petar");
                    String token = JWT.create().withIssuer("auth0")
                            .withClaim("username", u.getUsername())
                            .withClaim("id", idStr)
                            .withClaim("role", u.getRole())
                            .sign(alg);

                    return token + " " + u.getId() + " " + u.getUsername() + " " +  u.getRole();
                }

            }
            return password;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    public synchronized String createUser(UserDto userDto){
        String sql;
        sql = "SELECT * FROM user";
        List<User> user = new ArrayList();
        Integer id = 0;
        try {

                Statement statement = con.prepareStatement(sql);
                ResultSet rs = statement.executeQuery(sql);
                while (rs.next()){
                    if(rs.getString(2).toLowerCase().equals(userDto.getUsername().toLowerCase())){
                        return "User already exist.";
                    }
                }


        } catch (SQLException e) {
            e.printStackTrace();
        }


        try{
            String url = "jdbc:mysql://localhost:3306/web_project";
            String username = "root";
            String password = "";
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);

            String sql2 = "INSERT INTO USER (username , password, user_type) VALUES (? , ? , ?)";
            PreparedStatement st = con.prepareStatement(sql2);
            st.setString(1, userDto.getUsername());
            st.setString(2, userDto.getPassword());
            st.setString(3, "USER");
            st.executeUpdate();

            return "Uspesno ste kreirali novi nalog";
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "Doslo je do greske.";
    }


    public synchronized String createAdmin(UserDto userDto){
        String sql;
        sql = "SELECT * FROM user";
        List<User> user = new ArrayList();
        Integer id = 0;
        try {

            Statement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
                if(rs.getString(2).toLowerCase().equals(userDto.getUsername().toLowerCase())){
                    return "User already exist.";
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


        try{
            String url = "jdbc:mysql://localhost:3306/web_project";
            String username = "root";
            String password = "";
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);

            String sql2 = "INSERT INTO USER (username , password, user_type) VALUES (? , ? , ?)";
            PreparedStatement st = con.prepareStatement(sql2);
            st.setString(1, userDto.getUsername());
            st.setString(2, userDto.getPassword());
            st.setString(3, "ADMIN");
            st.executeUpdate();

            return "Uspesno ste kreirali novog admina.";
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "Doslo je do greske.";
    }


    public static UserRepository getInstance() {
        if(instance == null) {
            try {
                instance = new UserRepository();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
}
