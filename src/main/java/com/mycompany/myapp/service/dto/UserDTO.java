package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.User;

/**
 * A DTO representing a user, with only the public attributes.
 */
public class UserDTO {

    private Long id;

    //    private String login;
    private String firstName;

    public UserDTO() {
        // Empty constructor needed for Jackson.
    }

    public UserDTO(User user) {
        this.id = user.getId();
        // Customize it here if you need, or not, firstName/lastName/etc
        //        this.login = user.getLogin();
        this.firstName = user.getFirstName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //    public String getLogin() {
    //        return login;
    //    }
    public String getFirstName() {
        return firstName;
    }

    //    public void setLogin(String login) {
    //        this.login = login;
    //    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // prettier-ignore
//    @Override
//    public String toString() {
//        return "UserDTO{" +
//            "id='" + id + '\'' +
//            ", login='" + login + '\'' +
//            "}";
//    }
    @Override
    public String toString() {
        return "UserDTO{" +
            "id='" + id + '\'' +
            ", login='" + firstName + '\'' +
            "}";
    }
}
