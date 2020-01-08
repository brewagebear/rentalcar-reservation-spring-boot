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

    @Lob
    private String content;

    private int hit = 0;

    @OneToMany(mappedBy = "board",
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY)
    private List<Reply> replies;

    @OneToMany(mappedBy = "board",
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY)
    private List<FileEntity> files;

    @Builder
    public Board(String userName, String password, String email, String title, String content){
        this.userName = userName;
        this.password = password;
        this.email    = email;
        this.title    = title;
        this.content  = content;
    }

    public void addFile(FileEntity file){
        this.files.add(file);
    }
    public void addReply(Reply reply){
        this.replies.add(reply);
    }
}
