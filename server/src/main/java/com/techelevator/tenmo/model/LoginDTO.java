package com.techelevator.tenmo.model;

/*
    DTO = "data transfer object". This class transfers data between the client and the server. LoginDto = the data a client
    must pass to the server for a login endpoint.
 */
public class LoginDTO {

   private String username;
   private String password;

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   @Override
   public String toString() {
      return "LoginDTO{" +
              "username='" + username + '\'' +
              ", password='" + password + '\'' +
              '}';
   }
}
