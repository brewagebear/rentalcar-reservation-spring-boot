package com.toyota.rentalcar.dev.Board.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.toyota.rentalcar.dev.commons.model.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "TBL_REPLIES")
@NoArgsConstructor
public class Reply extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    @Lob
    private String content;
    private String userName;

    @JsonBackReference
    @JoinColumn(name = "board_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

//    @JsonBackReference
//    @JoinColumn(name = "member_id")
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Member member;

    @JsonIgnore
    private String userPass;

    @Builder
    public Reply(String content, String userName, String userPass, Board board){
        this.content  = content;
        this.userName = userName;
        this.userPass = userPass;
        this.board    = board;
    }

    public void update(String replyText, String replier, Board board){
        this.content = replyText;
        this.userName = replier;
        this.board   = board;
    }
}
