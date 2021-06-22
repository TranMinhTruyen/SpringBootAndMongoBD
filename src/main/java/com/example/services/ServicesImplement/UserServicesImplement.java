package com.example.services.ServicesImplement;

import com.example.common.model.*;
import com.example.common.request.LoginRequest;
import com.example.common.request.UserRequest;
import com.example.common.response.CommonResponse;
import com.example.common.response.UserResponse;
import com.example.repository.mongo.UserRepository;
import com.example.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
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
        List<User> last  = new AutoIncrement(userRepository).getLastOfCollection();
        if (userRequest != null)  {
            User newUser = new User();
            Address newAddress = userRequest.getAddress();
            newUser.setAccount(userRequest.getAccount());
            newUser.setPassword(userRequest.getPassword());
            newUser.setFirstName(userRequest.getFirstName());
            newUser.setLastName(userRequest.getLastName());
            newUser.setBirthDay(userRequest.getBirthDay());
            if (last != null)
                newUser.setId(last.get(0).getId()+1);
            else newUser.setId(1);
            newUser.setAddress(newAddress);
            newUser.setRole(userRequest.getRole());
            userRepository.save(newUser);
            return true;
        }
        else return false;
    }

    @Override
    public CommonResponse getAllUser(int page, int size){
        CommonResponse commonResponse = new CommonResponse();
        List result = userRepository.findAll();
        int offset = (page - 1) * size;
        int total = result.size();
        int totalPage = (total%size) == 0 ? (int)(total/size) : (int)((total / size) + 1);
        Object[] data = result.stream().skip(offset).limit(size).toArray();
        commonResponse.setData(data);
        commonResponse.setTotalPage(totalPage);
        commonResponse.setTotalRecord(total);
        commonResponse.setPage(page);
        commonResponse.setSize(size);
        return commonResponse;
    }

    @Override
    public CommonResponse getUserByKeyWord(int page, int size, String keyword){
        CommonResponse commonResponse = new CommonResponse();
        List result = userRepository.findUserByFirstNameContainingOrLastNameContaining(keyword,keyword);
        if (result != null){
            int offset = (page - 1) * size;
            int total = result.size();
            int totalPage = (total%size) == 0 ? (int)(total/size) : (int)((total / size) + 1);
            Object[] data = result.stream().skip(offset).limit(size).toArray();
            commonResponse.setData(data);
            commonResponse.setTotalPage(totalPage);
            commonResponse.setTotalRecord(total);
            commonResponse.setPage(page);
            commonResponse.setSize(size);
            return commonResponse;
        }
        else return getAllUser(page, size);
    }

    @Override
    public User Login(LoginRequest loginRequest) {
        User result = userRepository.findUserByAccountAndPassword(loginRequest.getAccount(), loginRequest.getPassword());
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
