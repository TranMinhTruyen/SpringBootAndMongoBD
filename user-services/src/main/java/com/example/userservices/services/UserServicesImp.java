package com.example.userservices.services;

import com.core.entity.Product;
import com.core.model.*;
import com.core.repository.mongo.ConfirmKeyRepository;
import com.core.repository.mongo.UserRepository;
import com.core.request.LoginRequest;
import com.core.request.UserRequest;
import com.core.response.CommonResponse;
import com.core.response.ProductResponse;
import com.core.response.UserResponse;
import com.google.common.hash.Hashing;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServicesImp implements UserServices, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private ConfirmKeyRepository confirmKeyRepository;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        if (userRequest != null && !accountIsExists(userRequest.getAccount(), userRequest.getEmail()))  {
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
            newUser.setEmail(userRequest.getEmail());
            newUser.setRole(userRequest.getRole());
            newUser.setActive(true);
            User after = userRepository.save(newUser);
            return getUserAfterUpdateOrCreate(after);
        }
        else return null;
    }

    @Override
    public CommonResponse getAllUser(int page, int size) {
        List result = userRepository.findAll();
        if (result != null){
            return new CommonResponse().getCommonResponse(page, size, result);
        }
        return null;
    }

    @Override
    public CommonResponse getUserByKeyWord(int page, int size, String keyword) {
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
    public User resetPassword(String email) {
        User user = userRepository.findByEmailEquals(email);
        if (user != null) {
            String newPassword = RandomStringUtils.randomAlphanumeric(10);
            user.setPassword(Hashing.sha512().hashString(newPassword, StandardCharsets.UTF_8).toString());
            User after = userRepository.save(user);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email);
            mailMessage.setSubject("GG-App Reset Password");
            mailMessage.setText("This is your new password: " + newPassword + "\n" + "Please change your password");
            emailSender.send(mailMessage);
            return after;
        }
        else return null;
    }

    @Override
    public void sendEmailConfirmKey(String email, String confirmKey) {
        ConfirmKey newConfirmKey = new ConfirmKey();
        newConfirmKey.setEmail(email);
        newConfirmKey.setKey(confirmKey);
        Date now = new Date();
        Date expireTime = new Date(now.getTime() + 300000);
        newConfirmKey.setExpire(expireTime);
        ConfirmKey isKeyExists = confirmKeyRepository.findByEmailEquals(email);
        if (isKeyExists != null){
            confirmKeyRepository.deleteByEmail(email);
        }
        confirmKeyRepository.save(newConfirmKey);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("GG-App Confirm Key");
        mailMessage.setText("This is your confirm key: " + confirmKey + "\n" + "Key will expired in 5 minutes");
        emailSender.send(mailMessage);
    }

    @Override
    public boolean checkConfirmKey(String email, String confirmKey) {
        ConfirmKey isKeyExists = confirmKeyRepository.findByEmailEquals(email);
        Date now = new Date();
        if (isKeyExists != null && isKeyExists.getKey().equals(confirmKey)) {
            if (now.before(isKeyExists.getExpire())) {
                confirmKeyRepository.deleteByEmail(email);
                return true;
            }
            else {
                confirmKeyRepository.deleteByEmail(email);
                return false;
            }
        }
        else return false;
    }

    @Override
    public UserResponse updateUser(int id, UserRequest request) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            User update = user.get();
            update.setFirstName(request.getFirstName());
            update.setPassword(Hashing.sha512().hashString(request.getPassword(), StandardCharsets.UTF_8).toString());
            update.setLastName(request.getLastName());
            update.setAddress(request.getAddress());
            update.setBirthDay(request.getBirthDay());
            update.setCitizenId(request.getCitizenID());
            update.setEmail(request.getEmail());
            update.setImage(request.getImage());
            update.setActive(request.isActive());
            User after = userRepository.save(update);
            return getUserAfterUpdateOrCreate(after);
        }
        else return null;
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
    public boolean accountIsExists(String account, String email) {
        if (userRepository.findUserByAccount(account) != null && userRepository.findByEmailEquals(email) != null)
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

    public UserResponse getUserAfterUpdateOrCreate(User user){
        UserResponse response = new UserResponse();
        response.setAccount(user.getAccount());
        response.setAddress(user.getAddress());
        response.setBirthDay(user.getBirthDay());
        response.setLastName(user.getLastName());
        response.setFirstName(user.getFirstName());
        response.setCitizenID(user.getCitizenId());
        response.setActive(user.isActive());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setImage(user.getImage());
        return response;
    }
}
