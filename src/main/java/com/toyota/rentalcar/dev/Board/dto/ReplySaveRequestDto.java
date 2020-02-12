package com.toyota.rentalcar.dev.Board.dto;

import com.toyota.rentalcar.dev.Board.model.Board;
import com.toyota.rentalcar.dev.Board.model.Reply;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReplySaveRequestDto {

    private String content;
    private String userName;
    private String userPass;
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
