package project.user;

import project.user.dto.UserDto;

public class UserService {



    public String createUser(UserDto userDto){
        return UserRepository.getInstance().createUser(userDto);
    }

    public String createAdmin(UserDto userDto){
        return UserRepository.getInstance().createAdmin(userDto);
    }

}
