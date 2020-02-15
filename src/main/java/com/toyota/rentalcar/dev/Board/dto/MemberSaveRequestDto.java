package com.toyota.rentalcar.dev.Board.dto;

import com.toyota.rentalcar.dev.Board.model.Member;
import com.toyota.rentalcar.dev.Board.model.MemberRole;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberSaveRequestDto {

    private MemberRole role;
    private String userName;
    private String userPass;
    private String userEmail;

    public Member toEntity(){
        return Member.builder()
                .memberRole(role)
                .userEmail(userEmail)
                .userName(userName)
                .userPass(userPass)
                .build();
    }
}
