package project.user;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class AuthService {

    public String logInToken(String username, String password) {
        return UserRepository.getInstance().logInToken(username, password);
    }

    public boolean isAuthenticate (String auth){
        String token = auth.substring("Bearer".length()).trim();

        try{
            Algorithm algorithm = Algorithm.HMAC256("petar");
            JWTVerifier verifier = JWT.require(algorithm)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            /*String username = jwt.getClaim("username").asString();
            String id = jwt.getClaim("id").asString();
            String role = jwt.getClaim("role").asString();*/
            return true;
        }catch (JWTVerificationException e){
            return false;
        }
    }

    public boolean isUser(String auth){
        String token = auth.substring("Bearer".length()).trim();

        try{
            Algorithm algorithm = Algorithm.HMAC256("petar");
            JWTVerifier verifier = JWT.require(algorithm)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            String role = jwt.getClaim("role").asString();
            return role.equals("USER");
        }catch (JWTVerificationException e){
            return false;
        }
    }

    public boolean isAdmin(String auth){
        String token = auth.substring("Bearer".length()).trim();

        try{
            Algorithm algorithm = Algorithm.HMAC256("petar");
            JWTVerifier verifier = JWT.require(algorithm)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            String role = jwt.getClaim("role").asString();
            return role.equals("ADMIN");
        }catch (JWTVerificationException e){
            return false;
        }
    }

    public String logUsername(String auth){
        String token = auth.substring("Bearer".length()).trim();

        try{
            Algorithm algorithm = Algorithm.HMAC256("petar");
            JWTVerifier verifier = JWT.require(algorithm)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("username").asString();

        }catch (JWTVerificationException e){
            return "";
        }
    }

    public int logId(String auth){
        String token = auth.substring("Bearer".length()).trim();

        try{
            Algorithm algorithm = Algorithm.HMAC256("petar");
            JWTVerifier verifier = JWT.require(algorithm)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("id").asInt();

        }catch (JWTVerificationException e){
            return -1;
        }
    }

}
