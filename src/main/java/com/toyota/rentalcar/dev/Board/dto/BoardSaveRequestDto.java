package com.toyota.rentalcar.dev.Board.dto;


import com.toyota.rentalcar.dev.Board.model.Board;
import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class BoardSaveRequestDto {

    private String userName;
    private String userPass;
    private String email;
    private String title;
    private String content;
    private List<String> files;

    @Builder
    BoardSaveRequestDto(String userName, String password, String email, String title, String content, List<String> files){
        this.userName = userName;
        this.userPass = password;
        this.email    = email;
        this.title    = title;
        this.content  = content;
        this.files    = files;
    }

    public Board toEntity(){
        return Board.builder()
                .userName(userName)
                .userPass(userPass)
                .email(email)
                .title(title)
                .content(content)
                .build();
    }
}
