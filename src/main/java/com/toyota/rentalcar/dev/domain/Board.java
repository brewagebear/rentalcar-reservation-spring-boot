package com.toyota.rentalcar.dev.domain;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Table(name = "TBL_BOARD")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String userName;
    private String password;
    private String email;
    private String title;
    private String content;

    private int hit;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<Reply> replies;

    @Builder
    public Board(String userName, String password, String email, String title, String content){
        this.userName = userName;
        this.password = password;
        this.email    = email;
        this.title    = title;
        this.content  = content;
    }

}
