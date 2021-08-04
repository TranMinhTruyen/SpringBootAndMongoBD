package com.example.services.ServicesImplement;

import com.example.common.jwt.CustomUserDetail;
import com.example.common.model.*;
import com.example.common.request.LoginRequest;
import com.example.common.request.UserRequest;
import com.example.common.response.CommonResponse;
import com.example.repository.mongo.UserRepository;
import com.example.services.UserServices;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

/**
 * @author Tran Minh Truyen
 */

@Service
public class UserServicesImplement implements UserDetailsService, UserServices {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean createUser(UserRequest userRequest) {
        if (userRequest != null && userRepository.findUserByAccount(userRequest.getAccount()) == null)  {
            List<User> last  = new AutoIncrement(userRepository).getLastOfCollection();
            User newUser = new User();
            Address newAddress = userRequest.getAddress();
            newUser.setAccount(userRequest.getAccount());
            newUser.setPassword(Hashing.sha512().hashString(userRequest.getPassword(), StandardCharsets.UTF_8).toString());
            newUser.setFirstName(userRequest.getFirstName());
            newUser.setLastName(userRequest.getLastName());
            newUser.setBirthDay(userRequest.getBirthDay());
            if (last != null)
                newUser.setId(last.get(0).getId()+1);
            else newUser.setId(1);
            newUser.setAddress(newAddress);
            newUser.setRole(userRequest.getRole());
            newUser.setActive(true);
            userRepository.save(newUser);
            return true;
        }
        else return false;
    }

    @Override
    public CommonResponse getAllUser(int page, int size){
        List result = userRepository.findAll();
        if (result != null){
            return new CommonResponse().getCommonResponse(page, size, result);
        }
        return null;
    }

    @Override
    public CommonResponse getUserByKeyWord(int page, int size, String keyword){
        CommonResponse commonResponse = new CommonResponse();
        List result = userRepository.findUserByFirstNameContainingOrLastNameContaining(keyword, keyword);
        if (result != null){
            return new CommonResponse().getCommonResponse(page, size, result);
        }
        else return getAllUser(page, size);
    }

    @Override
    public User Login(LoginRequest loginRequest) {
        User result = userRepository.findUsersByAccountEqualsAndPasswordContains(loginRequest.getAccount(), Hashing.sha512().hashString(loginRequest.getPassword(), StandardCharsets.UTF_8).toString());
        if (result != null){
            return result;
        }
        else return null;
    }

    @Override
    public boolean updateUser(int id, UserRequest request) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            User update = user.get();
            update.setFirstName(request.getFirstName());
            update.setLastName(request.getLastName());
            update.setAddress(request.getAddress());
            update.setBirthDay(request.getBirthDay());
            update.setCitizenId(request.getCitizenID());
            update.setImage(request.getImage());
            update.setActive(request.isActive());
            userRepository.save(update);
            return true;
        }
        else return false;
    }

    @Override
    public boolean deleteUser(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            userRepository.deleteById(id);
            return true;
        }
        else return false;
    }

    @Override
    public boolean accountIsExists(String account) {
        if (userRepository.findUserByAccount(account) != null)
            return true;
        else return false;
    }

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        User user = userRepository.findUserByAccount(account);
        if (user != null){
            return new CustomUserDetail(user);
        }
        else throw new UsernameNotFoundException("User not found");
    }

    @Override
    public UserDetails loadUserById(int id) {
        Optional<User> result = userRepository.findById(id);
        if (result.isPresent()){
            User user = result.get();
            return new CustomUserDetail(user);
        }
        else throw new UsernameNotFoundException("User not found with id: " + id);
    }
}
