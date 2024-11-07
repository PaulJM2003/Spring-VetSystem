package au.edu.rmit.sept.webapp.models;

public record User(
    Long userID,
    String name,
    String password,
    String email,
    String phoneNumber,
    String address 
) {}
