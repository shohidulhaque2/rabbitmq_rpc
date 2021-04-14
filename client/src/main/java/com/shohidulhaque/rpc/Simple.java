package com.shohidulhaque.rpc;

public class Simple {
  String name = "name";
  String lastName = "lastName";


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Override
  public String toString() {
    return "SimpleJson{" +
        "name='" + name + '\'' +
        ", lastName='" + lastName + '\'' +
        '}';
  }
}
