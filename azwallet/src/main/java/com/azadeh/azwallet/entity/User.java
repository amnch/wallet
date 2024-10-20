package com.azadeh.azwallet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")

public class User {

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Account> accounts = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //اعتبار سنجی شماره موبایل
    @NotBlank(message = "شماره تلفن نمی‌تواند خالی باشد")
    @Pattern(
            regexp = "^0[0-9]{10}$",
            message = "شماره موبایل باید با 0 شروع شود و دقیقاً 11 رقم باشد"
    )
    private String phoneNumber;
    private String firstname;
    private String lastname;
    private String dateOfBirth;
    private String gender;

    //اعتبار سنجی email
    @NotBlank(message = "ایمیل نمی‌تواند خالی باشد")
    @Email(message = "لطفاً یک ایمیل معتبر وارد کنید")
    private String email;
    private String militaryService;

    //اعتبار سنجی کد ملی
    @Pattern(regexp = "\\d{10}", message = "کد ملی باید 10 رقم باشد")
    @NotBlank(message = "کد ملی نمی‌تواند خالی باشد")
    private String CodeMelli;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhonenumber() {
        return phoneNumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phoneNumber = phonenumber;
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

    public String getDateofbirth() {
        return dateOfBirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateOfBirth = dateofbirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMilitaryservice() {
        return militaryService;
    }

    public void setMilitaryservice(String militaryservice) {
        this.militaryService = militaryservice;
    }

    public String getCodeMelli() {
        return CodeMelli;
    }

    public void setCodeMelli(String codeMelli) {
        CodeMelli = codeMelli;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", phoneNumber=" + phoneNumber +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", militaryService='" + militaryService + '\'' +
                ", CodeMelli='" + CodeMelli + '\'' +
                '}';
    }

}
