package com.luvannie.springbootbookecommerce.service.user;

import com.luvannie.springbootbookecommerce.component.JwtTokenUtils;
import com.luvannie.springbootbookecommerce.component.LocalizationUtils;
import com.luvannie.springbootbookecommerce.dao.RoleRepository;
import com.luvannie.springbootbookecommerce.dao.TokenRepository;
import com.luvannie.springbootbookecommerce.dao.UserRepository;
import com.luvannie.springbootbookecommerce.dto.UpdateUserDTO;
import com.luvannie.springbootbookecommerce.dto.UserDTO;
import com.luvannie.springbootbookecommerce.dto.UserLoginDTO;
import com.luvannie.springbootbookecommerce.entity.User;
import com.luvannie.springbootbookecommerce.exceptions.DataNotFoundException;
import com.luvannie.springbootbookecommerce.exceptions.InvalidPasswordException;
import com.luvannie.springbootbookecommerce.utils.MessageKeys;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final LocalizationUtils localizationUtils;
    private static String UPLOADS_FOLDER = "uploads";



//    private BCryptPasswordEncoder passwordEncoder;



    public User register(User user) {
        System.out.println("register: " + user.getUsername() + " " + user.getAccount() + " " + user.getPassword() + " " + user.getPhoneNumber() + " " + user.getEmail() );
        user.setUsername(user.getUsername());
        user.setAccount(user.getAccount());
        user.setPassword(user.getPassword());
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPhoneNumber(user.getPhoneNumber());
        user.setEmail(user.getEmail());
        return userRepository.save(user);
    }

//    public User login(String account, String password) {
//        User user = userRepository.findByAccount(account);
////        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
////            return user;
////        }
//        if(user != null && password.equals(user.getPassword())){
//            return user;
//        }
//        return null;
//    }

    @Override
    @Transactional
    public User createUser(UserDTO userDTO) throws Exception {
        // Check if the account already exists
        if (userRepository.existsByAccount(userDTO.getAccount())) {
            throw new DataIntegrityViolationException("Account already exists");
        }

        // Check if the phone number already exists
        if (!userDTO.getPhoneNumber().isBlank() && userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())) {
            throw new DataIntegrityViolationException("Phone number already exists");
        }

        // Check if the email already exists
        if (!userDTO.getEmail().isBlank() && userRepository.existsByEmail(userDTO.getEmail())) {
            throw new DataIntegrityViolationException("Email already exists");
        }

        // Convert from UserDTO to User
        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setAccount(userDTO.getAccount());
        String encodedpassword = passwordEncoder.encode(userDTO.getPassword());
        newUser.setPassword(encodedpassword); // Consider using password encoding here

        newUser.setPhoneNumber(userDTO.getPhoneNumber());
        newUser.setEmail(userDTO.getEmail());

        return userRepository.save(newUser);
    }



    @Override
    public String login(UserLoginDTO userLoginDTO) throws Exception {
        // Check if the user exists by account
        Optional<User> optionalUser = userRepository.findByAccount(userLoginDTO.getAccount());

        // If user is not found, throw an exception
        if (optionalUser.isEmpty()) {
            throw new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageKeys.WRONG_ACCOUNT_PASSWORD));
        }

        // Get the existing user
        User existingUser = optionalUser.get();

        //check password
        if (existingUser.getFacebookAccountId() == 0
                && existingUser.getGoogleAccountId() == 0) {
            if(!passwordEncoder.matches(userLoginDTO.getPassword(), existingUser.getPassword())) {
                throw new BadCredentialsException(localizationUtils.getLocalizedMessage(MessageKeys.WRONG_ACCOUNT_PASSWORD));
            }
        }

        if(!existingUser.isActive()) {
            throw new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageKeys.USER_IS_LOCKED));
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userLoginDTO.getAccount(), userLoginDTO.getPassword(),
                existingUser.getAuthorities()
        );

        //authenticate with Java Spring security
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }

    @Override
    public User getUserDetailsFromToken(String token) throws Exception {
        return null;
    }

    @Override
    public User getUserDetailsFromRefreshToken(String token) throws Exception {
        return null;
    }

//    @Override
//    public User updateUser(Long userId, UpdateUserDTO updatedUserDTO) throws Exception {
//        return null;
//    }

    @Transactional
    @Override
    public User updateUser(Long userId, UpdateUserDTO updatedUserDTO) throws Exception {
        // Find the existing user by userId
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        // Check if the account is being changed and if it already exists for another user
        String newAccount = updatedUserDTO.getAccount();
        if (!existingUser.getAccount().equals(newAccount) &&
                userRepository.existsByAccount(newAccount)) {
            throw new DataIntegrityViolationException("Account already exists");
        }

        // Update user information based on the DTO
        if (updatedUserDTO.getUsername() != null) {
            existingUser.setUsername(updatedUserDTO.getUsername());
        }
        if (newAccount != null) {
            existingUser.setAccount(newAccount);
        }


        if (0 < updatedUserDTO.getFacebookAccountId()) {
            existingUser.setFacebookAccountId(updatedUserDTO.getFacebookAccountId());
        }
        if (updatedUserDTO.getGoogleAccountId() > 0) {
            existingUser.setGoogleAccountId(updatedUserDTO.getGoogleAccountId());
        }

        // Update the password if it is provided in the DTO
        if (updatedUserDTO.getPassword() != null
                && !updatedUserDTO.getPassword().isEmpty()) {
            if(!updatedUserDTO.getPassword().equals(updatedUserDTO.getRetypePassword())) {
                throw new DataNotFoundException("Password and retype password not the same");
            }
            String newPassword = updatedUserDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(newPassword);
            existingUser.setPassword(encodedPassword);
        }

        // Save the updated user
        return userRepository.save(existingUser);
    }

    @Override
    public void resetPassword(Long userId, String newPassword) throws InvalidPasswordException, DataNotFoundException {

    }

    @Override
    public void blockOrEnable(Long userId, Boolean active) throws DataNotFoundException {

    }


}