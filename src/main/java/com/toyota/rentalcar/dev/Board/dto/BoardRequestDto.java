package com.toyota.rentalcar.dev.Board.dto;


import com.toyota.rentalcar.dev.Board.model.Board;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class BoardRequestDto {

    @NotNull
    private String userName;

    @NotNull
    private String userPass;

    @NotNull
    private String email;

    @NotNull
    private String title;

    @NotNull
    private String content;
    private List<String> files;

    @Builder
    BoardRequestDto(String userName, String userPass, String email, String title, String content, List<String> files){
        this.userName = userName;
        this.userPass = userPass;
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
