package com.teamgreen.bacefook.service;

import com.teamgreen.bacefook.entity.User;
import com.teamgreen.bacefook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  /*** ### Save User and Encrypted Password ### ***/

  public void saveUser(User user) {
    byte[] salt = generateSalt();
    String saltString = convertByteToStringForDB(salt);
    String hashedPassword = createSecureHashPass(user.getPassword(), salt);

    if (!hashedPassword.equals("")) {
      user.setSalt(saltString);
      user.setPassword(hashedPassword);
      userRepository.save(user);
    }
  }

  /*** ### Password: Create Hash ### ***/

  public String createSecureHashPass(String plainTextPassword, byte[] salt) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(salt);
      byte[] hashedPass = md.digest(plainTextPassword.getBytes());
      return convertByteToStringForDB(hashedPass);

    } catch (NoSuchAlgorithmException ex) {
      ex.printStackTrace();
    }
    return "";
  }

  /*** ### Password: Convert Hashed Password to String ### ***/

  private String convertByteToStringForDB(byte[] hashedPass) {
    return DatatypeConverter.printHexBinary(hashedPass).toLowerCase();
  }

  /*** ### Password: Convert String Password to Byte ### ***/

  private byte[] convertStringToByteForDB(String dbPassword) {
    return DatatypeConverter.parseHexBinary(dbPassword);
  }

  /*** ### Password: Generate Salt ### ***/

  private byte[] generateSalt() {
    SecureRandom sr = new SecureRandom();
    byte[] hashedSalt = sr.generateSeed(12);
    return hashedSalt;
  }

  /*** ### Authenticate User by Checking DB for Username and Comparing Passwords ***/

  public boolean authUser(String username, String password) {
    User dbUser = userRepository.findByUsername(username);
    if (dbUser == null) {
      System.out.println("Username is not correct " + username);
      return false;
    }
    String passwordToCompare = createSecureHashPass(password, convertStringToByteForDB(dbUser.getSalt()));
    return dbUser.getPassword().equals(passwordToCompare);
  }

  /*** ### List All Users ### ***/

  public List<User> findAllUsers() {
    return userRepository.findAll();
  }

  /*** ### Find One User by Id ### ***/

  public User findUserById(long id) {
    return userRepository.findById(id).orElseThrow();
  }

  /*** ### Find One User by Username ### ***/

  public User findUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  /*** ### Update User Credentials and Save to Database ### ***/

  public void updateUser(User user) {
    User userDB = userRepository.findById(user.getId()).orElseThrow();
    userDB.setUsername(user.getUsername());
    userDB.setAddress(user.getAddress());
    userRepository.save(userDB);
  }

  /*** ### Remove User from Database ### ***/

  public void deleteUser(long id) {
    userRepository.deleteById(id);
  }

}
