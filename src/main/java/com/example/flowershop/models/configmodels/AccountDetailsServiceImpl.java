package com.example.flowershop.models.configmodels;

import com.example.flowershop.models.account.Account;
import com.example.flowershop.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AccountDetailsServiceImpl implements UserDetailsService {


    @Autowired
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account acc = accountRepository.findAccountByUsername(username);
        if (acc == null) throw new UsernameNotFoundException(username + " is not found or not sign in");

        return new CustomUserDetails(acc);
    }
}
