package com.toyota.rentalcar.dev.Board.service;

import com.toyota.rentalcar.dev.Board.model.Member;
import com.toyota.rentalcar.dev.Board.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findByUserEmail(userEmail);
        if(!member.isPresent()) throw new UsernameNotFoundException(userEmail);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(member.get().getRole().name()));

        return new User(member.get().getUserEmail(), member.get().getUserPass(), grantedAuthorities);
    }
}
