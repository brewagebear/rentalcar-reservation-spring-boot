package com.toyota.rentalcar.dev.commons.utils;

import com.toyota.rentalcar.dev.Board.model.Member;
import com.toyota.rentalcar.dev.Board.model.MemberRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class SecurityUser extends User {

    private static final String ROLE_PREFIX = "ROLE_";
    private Member member;

    public SecurityUser(Member member){
        super(member.getUserName(), member.getUserPass(), makeGrantedAuthority(member.getRole()));
    }

    private static Collection<? extends GrantedAuthority> makeGrantedAuthority(MemberRole role) {
        List<GrantedAuthority> list = new ArrayList<>();

        list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.getRole()));
        return list;
    }

    public SecurityUser(String username,
                        String password,
                        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
