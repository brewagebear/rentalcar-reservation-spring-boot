package com.toyota.rentalcar.dev.Board.dto;


import com.toyota.rentalcar.dev.Board.model.Board;
import com.toyota.rentalcar.dev.Board.model.BoardType;
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
    private BoardType boardType;
    private List<String> files;

    public Board toEntity(){
        return Board.builder()
                .userName(userName)
                .userPass(userPass)
                .email(email)
                .title(title)
                .content(content)
                .boardType(boardType)
                .build();
    }
}
