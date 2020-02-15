package com.toyota.rentalcar.dev.Board.service;

import com.toyota.rentalcar.dev.Board.dto.MemberSaveRequestDto;
import com.toyota.rentalcar.dev.Board.model.Member;
import com.toyota.rentalcar.dev.Board.repositories.MemberRepository;
import com.toyota.rentalcar.dev.commons.exceptions.ApiException;
import com.toyota.rentalcar.dev.commons.model.ApiExceptionData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void saveMember(MemberSaveRequestDto requestDto){
        Member member = requestDto.toEntity();
        member.setUserPass(requestDto.getUserPass());
        memberRepository.save(member);
    }

    public Member getByUserEmail(String userEmail) throws ApiException {
        Optional<Member> optMember = memberRepository.findByUserEmail(userEmail);
        if(optMember.isPresent()){
            return optMember.get();
        }
        throw new ApiException("NOT_FOUND_MEMBER", "회원을 찾을 수 없습니다.", new ApiExceptionData().add("target", userEmail));
    }
}
