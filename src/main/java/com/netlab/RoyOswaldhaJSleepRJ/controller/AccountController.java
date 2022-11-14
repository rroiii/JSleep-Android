package com.netlab.RoyOswaldhaJSleepRJ.controller;

import com.netlab.RoyOswaldhaJSleepRJ.*;
import com.netlab.RoyOswaldhaJSleepRJ.dbjson.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.bind.annotation.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/account")

public class AccountController implements BasicGetController<Account>
{
    public static final String REGEX_EMAIL = "^[A-Za-z0-9]+@[A-Za-z]+\\.[A-Za-z.]+[^.]$";
    public static final String REGEX_PASSWORD =  "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
    public static final Pattern REGEX_PATTERN_EMAIL = Pattern.compile(REGEX_EMAIL);
    public static final Pattern REGEX_PATTERN_PASSWORD = Pattern.compile(REGEX_PASSWORD);

    @JsonAutowired(filepath ="C:\\Users\\Roy\\OneDrive - UNIVERSITAS INDONESIA\\Semester 3\\Pemrograman Berorientasi Objek dan Praktikum\\Praktikum\\JSleep\\src\\main\\java\\com\\netlab\\RoyOswaldhaJSleepRJ\\json\\account.json", value = Account.class)
    public static JsonTable<Account> accountTable;

    @GetMapping
    String index() {return "Account Page";}
    public JsonTable<Account> getJsonTable(){
        return accountTable;
    }
    @PostMapping("/login")
    Account login(@RequestParam String email, @RequestParam String password){
        Account accountFound = Algorithm.<Account>find(accountTable, account -> account.email.equals(email));
        String passwordHashed = null;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            passwordHashed = sb.toString();
        }catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if (accountFound != null && accountFound.password.equals(passwordHashed)) {
            return accountFound;
        }else {
            return null;
        }
    }

    @PostMapping("/register")
    Account register(@RequestParam String name, @RequestParam String email, @RequestParam String password){
        Matcher emailMatch = REGEX_PATTERN_EMAIL.matcher(email);
        Matcher passwordMatch = REGEX_PATTERN_PASSWORD.matcher(password);

        if (name.isBlank()) {
            return null;
        }
        else if(emailMatch.find() && passwordMatch.find()) {
            String passwordHashed = null;

            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(password.getBytes());
                byte[] bytes = md.digest();
                StringBuilder sb = new StringBuilder();

                for(int i = 0; i < bytes.length; i++) {
                    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                }

                passwordHashed = sb.toString();
            }catch(NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            Account newAccount = new Account(name, email, passwordHashed);
            accountTable.add(newAccount);
            return newAccount ;
        }
        else {
            return null;
        }
    }

    @PostMapping("/{id}/registerRenter")
    Renter registerRenter(@PathVariable int id, @RequestParam String username, @RequestParam String address, @RequestParam String phoneNumber){
        Account found = Algorithm.<Account>find(accountTable, account -> account.id == id);

        if (found.renter == null) {
            found.renter = new Renter(username,phoneNumber, address);
            return found.renter;
        }else {
            return null;
        }
    }

    @PostMapping("/{id}/topUp")
    boolean topUp(@PathVariable int id, @RequestParam double balance){
        Account found = Algorithm.<Account>find(accountTable,prod -> prod.id == id);
        if (found != null) {
            found.balance += balance;
            return true;
        }else {
            return false;
        }
    }
}