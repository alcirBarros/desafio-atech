package br.com.atech.request;

import org.hibernate.validator.constraints.Length;

public class UserRequest {

    private Integer id;

    private String name;

    private String email;

    @Length.List({
            @Length(min = 6, message = "The field must be at least 5 characters")
    })
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
