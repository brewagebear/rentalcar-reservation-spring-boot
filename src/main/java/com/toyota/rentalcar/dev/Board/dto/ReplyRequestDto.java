package com.toyota.rentalcar.dev.Board.dto;

import com.toyota.rentalcar.dev.Board.model.Board;
import com.toyota.rentalcar.dev.Board.model.Reply;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class ReplyRequestDto {

    @NotNull
    private String content;

    @NotNull
    private String userName;

    @NotNull
    private String userPass;

    @NotNull
    private Board board;

    public Reply toEntity(){
     return Reply.builder()
                .content(content)
                .userName(userName)
                .userPass(userPass)
                .board(board)
                .build();
    }
}
