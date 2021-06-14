package com.example.services;

import com.example.common.model.Address;
import com.example.common.model.AutoIncrement;
import com.example.common.model.Role;
import com.example.common.model.User;
import com.example.common.request.LoginRequest;
import com.example.common.request.UserRequest;
import com.example.common.response.CommonResponse;
import com.example.common.response.UserResponse;
import com.example.repository.mongo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Tran Minh Truyen
 */

@Service
public class UserServices {

    @Autowired
    UserRepository userRepository;

    public boolean createUser(UserRequest userRequest) {
        List<User> last  = new AutoIncrement(userRepository).getLastOfCollection();
        if (userRequest != null)  {
            User newUser = new User();
            List<Role> newListRole = userRequest.getRole();
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
            newUser.setRole(newListRole);
            userRepository.save(newUser);
            return true;
        }
        else return false;
    }

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

    public CommonResponse getUserByKeyWord(int page, int size, String keyword){
        CommonResponse commonResponse = new CommonResponse();
        List<Role> find =new ArrayList<Role>();
        Role key = new Role();
        key.setRole(keyword);
        find.add(key);
        List result = userRepository.findUserByRoleContains(find);
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

    public UserResponse Login(LoginRequest loginRequest){
        User result = userRepository.findUserByAccountAndPassword(loginRequest.getAccount(), loginRequest.getPassword());
        if (result != null){
            UserResponse response = new UserResponse();
            response.setFirstName(result.getFirstName());
            response.setLastName(result.getLastName());
            response.setBirthDay(result.getBirthDay());
            response.setAddress(result.getAddress());
            response.setCitizenID(result.getCitizenId());
            response.setImage(result.getImage());
            response.setRole(result.getRole());
            response.setActive(result.isActive());
            return response;
        }
        else return null;
    }

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
//            update.setRole(request.getRole());
            update.setActive(request.isActive());
            userRepository.save(update);
            return true;
        }
        else return false;
    }

    public boolean deleteUser(int id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            userRepository.deleteById(id);
            return true;
        }
        else return false;
    }
}
