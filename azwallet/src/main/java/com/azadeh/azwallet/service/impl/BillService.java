package com.azadeh.azwallet.service.impl;

import com.azadeh.azwallet.entity.Account;
import com.azadeh.azwallet.entity.Bill;
import com.azadeh.azwallet.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    // ذخیره صورت حساب
    public Bill save(Bill bill) {
        return billRepository.save(bill);
    }
    //  دریافت لیست تراکنش های یک حساب در مدت زمان مشخص
    public List<Bill> getBillsByAccount(Long accountId) {
        return billRepository.findByAccountId(accountId);
    }
    public List<Bill> findByAccountAndTimeBetween(Account account, LocalDateTime startTime, LocalDateTime endTime) {
        return billRepository.findByAccountAndTimeBetween(account, startTime, endTime);
    }
}
