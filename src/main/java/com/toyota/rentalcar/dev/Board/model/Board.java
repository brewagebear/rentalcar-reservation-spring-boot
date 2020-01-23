package com.toyota.rentalcar.dev.Board.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.toyota.rentalcar.dev.commons.model.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Table(name = "TBL_BOARD")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String userName;

    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    private String email;
    private String title;

    @Lob
    private String content;

    @ColumnDefault("0")
    private Integer hit;

    @JsonManagedReference
    @OneToMany(mappedBy = "board",
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY)
    private List<Reply> replies;

    @JsonManagedReference
    @OneToMany(mappedBy = "board",
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY)
    private List<File> files;

    @Builder
    public Board(BoardType boardType, String userName, String password, String email, String title, String content){
        this.boardType = boardType;
        this.userName = userName;
        this.password = password;
        this.email    = email;
        this.title    = title;
        this.content  = content;
    }

    @PrePersist
    public void perPersist(){
        if(this.hit == null) this.hit = 0;
    }

    public void updateViewHit(Integer viewHit){
        this.hit = hit + viewHit;
    }

    public void updateBoardType(BoardType type){
        this.boardType = type;
    }
}
