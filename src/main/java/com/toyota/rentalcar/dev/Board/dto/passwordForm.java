package com.toyota.rentalcar.dev.Board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class passwordForm {

    private String password;
    private boolean isBoard;

    public passwordForm(String password, boolean isBoard) {
        this.password = password;
        this.isBoard  = isBoard;
    }

}
