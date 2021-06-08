package com.example.services;

import com.example.common.model.Address;
import com.example.common.model.User;
import com.example.common.request.UserRequest;
import com.example.common.response.CommonResponse;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices {

    @Autowired
    UserRepository userRepository;

    public boolean createUser(UserRequest userRequest){
        if (userRequest != null)  {
            User newUser = new User();
            Address newAddress = userRequest.getAndress();
            newUser.setFirstName(userRequest.getFirstName());
            newUser.setLastName(userRequest.getLastName());
            newUser.setBirthDay(userRequest.getBirthDay());
            newUser.setId(userRequest.getId());
            newUser.setAndress(newAddress);
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
        List result = userRepository.findUserByFirstNameEqualsOrLastNameEqualsOrAndress_AndressOrAndress_DistricOrAndress_CityOrId(keyword);
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

    public boolean updateUser(int id, UserRequest request){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            User update = user.get();
            update.setFirstName(request.getFirstName());
            update.setLastName(request.getLastName());
            update.setAndress(request.getAndress());
            update.setBirthDay(request.getBirthDay());
            update.setCitizenId(request.getCitizenID());
            update.setImage(request.getImage());
            update.setRole(request.getRole());
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
