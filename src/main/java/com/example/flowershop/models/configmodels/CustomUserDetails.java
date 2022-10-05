package com.example.flowershop.models.configmodels;

import com.example.flowershop.models.account.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private Account acc;

    public Account getAccount() {
        return this.acc;
    }

    public CustomUserDetails(Account acc) {
        this.acc = acc;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        int roleId = acc.getRole();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        //- 1: is Admin
        //- 2: is User
        if (roleId == 1) authorities.add(new SimpleGrantedAuthority("ADMIN"));
        else authorities.add(new SimpleGrantedAuthority("USER"));
        return authorities;
    }

    @Override
    public String getPassword() {
        return acc.getPassword();
    }

    @Override
    public String getUsername() {
        return acc.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        if (acc.getStatus() == 1)
            return true;
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
