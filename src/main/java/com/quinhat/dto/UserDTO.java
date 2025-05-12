package com.quinhat.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.quinhat.pojo.User;
import java.util.Date;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private Integer id;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private Date birthday;
    private String avatar;
    private String gender;

    public UserDTO(Integer id, String username, String fullName, String email, String phone, Date birthday, String avatar, String gender) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.avatar = avatar;
        this.gender = gender;
    }

    public UserDTO(User user, Set<String> fields) {
        if (fields.contains("id")) {
            this.id = user.getId();
        }
        if (fields.contains("username")) {
            this.username = user.getUsername();
        }
        if (fields.contains("fullName")) {
            this.fullName = user.getFullName();
        }
        if (fields.contains("email")) {
            this.email = user.getEmail();
        }
        if (fields.contains("phone")) {
            this.phone = user.getPhone();
        }
        if (fields.contains("birthday")) {
            this.birthday = user.getBirthday();
        }
        if (fields.contains("avatar")) {
            this.avatar = user.getAvatar();
        }
        if (fields.contains("gender")) {
            this.gender = user.getGender();
        }
    }

    // Getters and Setters
    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // Optional: toString (nếu bạn cần debug)
    @Override
    public String toString() {
        return "UserDTO{"
                + "username='" + username + '\''
                + ", fullName='" + fullName + '\''
                + ", email='" + email + '\''
                + ", phone='" + phone + '\''
                + ", birthday=" + birthday
                + ", avatar='" + avatar + '\''
                + ", gender='" + gender + '\''
                + '}';
    }

}
