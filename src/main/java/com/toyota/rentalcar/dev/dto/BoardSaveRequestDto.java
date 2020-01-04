package com.toyota.rentalcar.dev.dto;


import com.toyota.rentalcar.dev.domain.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class BoardSaveRequestDto {

    private String userName;
    private String password;
    private String email;
    private String title;
    private String content;

    @Builder
    BoardSaveRequestDto(String userName, String password, String email, String title, String content){
        this.userName = userName;
        this.password = password;
        this.email    = email;
        this.title    = title;
        this.content  = content;
    }

    public Board toEntity(){
        return Board.builder()
                .userName(userName)
                .password(password)
                .email(email)
                .title(title)
                .content(content)
                .build();
    }
}
