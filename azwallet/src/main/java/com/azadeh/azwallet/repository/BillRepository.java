package com.azadeh.azwallet.repository;

import com.azadeh.azwallet.entity.Account;
import com.azadeh.azwallet.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BillRepository extends JpaRepository<Bill,Long> {
    List<Bill> findByAccountId(Long accountId);
    // پیدا کردن لیست صورت‌ حساب‌های مرتبط با یک حساب خاص بین دو تاریخ مشخص (در یک روز)
    List<Bill> findByAccountAndTimeBetween(Account account, LocalDateTime startTime, LocalDateTime endTime);
}
