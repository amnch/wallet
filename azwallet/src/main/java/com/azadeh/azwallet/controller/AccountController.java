package com.azadeh.azwallet.controller;
import com.azadeh.azwallet.entity.Account;
import com.azadeh.azwallet.service.impl.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@Validated
public class AccountController {


        @Autowired
        private AccountService accountService;
        //ایجاد یک خساب جدید
        @PostMapping
        public Account createAccount(@Valid @RequestBody Account account) {
            return accountService.createAccount(account);
        }
        //دریافت لیست تمامی حساب ها
       @GetMapping
        public List<Account> getAllAccounts() {
            return accountService.getAllAccounts();
        }

        //دریافت حساب بر اساس id
        @GetMapping("/{id}")
        public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
            Account account = accountService.getAccountById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "جساب مورد نظر یافت نشد "));
            return ResponseEntity.ok(account);
        }
        //به روزرسانی حساب
        @PutMapping("/{id}")
        public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account accountDetails) {
            Account updatedAccount = accountService.updateAccount(id, accountDetails);
            return ResponseEntity.ok(updatedAccount);
        }

        //حذف یک حساب با استفاده از id مشخص
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
            accountService.deleteAccount(id);
            return ResponseEntity.noContent().build();
        }

        //واریز مبلغ به حسابی مشخص
        @PatchMapping("/{id}/deposit")
        public ResponseEntity<String> deposit(@PathVariable Long id, @RequestParam Double amount) {
        try {
            accountService.deposit(id, amount);
            return ResponseEntity.ok("واریز موفقیت‌آمیز بود");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

        //برداشت مبلغ از حسابی مشخص
         @PatchMapping("/{id}/withdraw")
         public ResponseEntity<String> withdraw(@PathVariable Long id, @RequestParam Double amount) {
        try {
            accountService.withdraw(id, amount);
            return ResponseEntity.ok("برداشت موفقیت‌آمیز بود");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}

