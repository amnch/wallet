package com.azadeh.azwallet.controller;

import com.azadeh.azwallet.entity.Account;
import com.azadeh.azwallet.entity.Bill;
import com.azadeh.azwallet.service.impl.AccountService;
import com.azadeh.azwallet.service.impl.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BillController {

    @Autowired
    private BillService billService;
    @Autowired
    private AccountService accountService;


    @GetMapping("/accounts/bills/{accountId}")
    public ResponseEntity<Map<String, Object>> getBillsAndAccountBalance(@PathVariable Long accountId) {

        //دریافت لیست صورتحساب ها
        List<Bill> bills = billService.getBillsByAccount(accountId);

        //دریافت اطلاعات حساب
        Account account = accountService.getAccountById(accountId)
                .orElseThrow(() -> new RuntimeException("حساب موردنظر یافت نشد"));

        Map<String, Object> billsAndBalanceResponse = new HashMap<>();
        billsAndBalanceResponse.put("bills", bills);  // لیست صورت‌حساب‌ها
        billsAndBalanceResponse.put("accountBalance", account.getAccountBalance());  // موجودی حساب

        return ResponseEntity.ok(billsAndBalanceResponse);
    }
}
