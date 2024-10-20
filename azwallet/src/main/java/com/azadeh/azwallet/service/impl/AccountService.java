package com.azadeh.azwallet.service.impl;


import com.azadeh.azwallet.entity.Account;
import com.azadeh.azwallet.entity.Bill;
import com.azadeh.azwallet.exception.InsufficientBalanceException;
import com.azadeh.azwallet.repository.AccountRepository;
import com.azadeh.azwallet.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;



@Service
public class AccountService {

    private Double accountBalance;
    @Autowired
    private BillService billService;

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BillRepository billRepository;
    //ساخت اکانت جدید
    public Account createAccount(Account user) {
        return accountRepository.save(user);
    }

    // دریاغت لیست تمامی حساب ها
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // دریافت یک حساب با  id مشخض
    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    // به روز رسانی اطلاعات حساب
    public Account updateAccount(Long id, Account accountDetails) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("account not found"));
        account.setAccountNumber(account.getAccountNumber());
        account.setAccountBalance(accountDetails.getAccountBalance());
        account.setShebaNumber(accountDetails.getShebaNumber());
        return accountRepository.save(account);
    }

    // پاک کردن حساب
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    //واریز به حساب
    public void deposit(Long id, Double amount){
    Account account = getAccountById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "حساب مورد نظر یافت نشد"));
        if (amount <= 0) {
            throw new IllegalArgumentException("مقدار واریز باید بیشتر از صفر باشد");}
        // به‌روزرسانی موجودی حساب
        account.setAccountBalance(account.getAccountBalance() + amount);  // به‌روز کردن موجودی
        accountRepository.save(account);  // ذخیره تغییرات موجودی در پایگاه داده
        // ایجاد و ذخیره بیل
        Bill bill = new Bill();
        bill.setAccount(account);
        bill.setCost(amount);
        bill.setTime(LocalDateTime.now());
        bill.setTransactionType("Deposit");
        billRepository.save(bill);  // ذخیره Bill
    }

    //برداشت از حساب
    public void withdraw(Long id, Double amount) throws Exception {
        Account account = getAccountById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "حساب مورد نظر یافت نشد"));
        if (amount <= 0) {
            throw new IllegalArgumentException("مقدار برداشت باید بیشتر از صفر باشد");
        }
        if (account.getAccountBalance() < amount) {
            throw new InsufficientBalanceException("موجودی کافی نیست");
        }
        // بررسی سقف برداشت روزانه
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = LocalDateTime.now().toLocalDate().atTime(23, 59, 59);
        List<Bill> todayWithdrawals = billService.findByAccountAndTimeBetween(account, startOfDay, endOfDay);
        Double totalWithdrawalsToday = todayWithdrawals.stream()
                .filter(bill -> bill.getTransactionType().equals("withdraw"))
                .mapToDouble(Bill::getCost)
                .sum();
        if (totalWithdrawalsToday + amount > 10_000_000) {
            throw new Exception("مجموع برداشت‌های روزانه نمی‌تواند بیشتر از 10,000,000 ریال باشد.");
        }
        // بررسی حداقل موجودی بعد از برداشت
        if (account.getAccountBalance() - amount < 1000) {
            throw new Exception("موجودی حساب نباید کمتر از 1000 ریال باشد.");
        }
        // عملیات برداشت
        account.setAccountBalance(account.getAccountBalance() - amount);
        accountRepository.save(account);

        // ذخیره اطلاعات تراکنش
        Bill bill = new Bill();
        bill.setAccount(account);
        bill.setCost(amount);
        bill.setTransactionType("withdraw");
        bill.setTime(LocalDateTime.now());
        billService.save(bill);
    }


}
