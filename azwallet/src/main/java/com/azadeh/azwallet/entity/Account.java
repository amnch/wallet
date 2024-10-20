package com.azadeh.azwallet.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "accounts")
public class Account {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Pattern(regexp = "^[0-9]{16}$", message = "شماره حساب باید دقیقا 16 رقم باشد.")
    private String accountNumber;
    @Pattern(regexp = "^IR[0-9]{22}$", message = "شماره شبا باید با IR شروع شود و حتما 22 رقم باشد.")
    private String shebaNumber;
    @Min(value = 1000, message = "حداقل موجودی برای افتتاح حساب باید 10000 ریال باشد.")
    private Double accountBalance;
    @CreationTimestamp
    private LocalDateTime createAt;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Bill> bills;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {

        this.accountNumber = accountNumber;
    }

    public String getShebaNumber() {
        return shebaNumber;
    }

    public void setShebaNumber(String shebaNumber) {
        shebaNumber = shebaNumber;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Bill> getBills() {
        return bills;
    }


    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }
    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", shebaNumber='" + shebaNumber + '\'' +
                ", accountBalance=" + accountBalance +
                ", createAt=" + createAt +
                ", user=" + user +
                ", bills=" + bills +
                '}';
    }
}
