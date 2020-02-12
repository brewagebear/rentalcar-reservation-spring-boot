package com.toyota.rentalcar.dev.commons.utils;

import com.toyota.rentalcar.dev.Board.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return memberRepository.findById(userName)
                .filter(member -> member != null)
                .map(member -> new SecurityUser(member)).get();
    }
}
