package com.teamgreen.bacefook.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Identity {

  @Column(name = "first_name", nullable = false, length = 20)
  private String firstname;

  @Column(name = "last_name", nullable = false, length = 20)
  private String lastname;

  @Column(nullable = false, unique = true, length = 45)
  private String email;

  private String address;

  public Identity() {
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

}
