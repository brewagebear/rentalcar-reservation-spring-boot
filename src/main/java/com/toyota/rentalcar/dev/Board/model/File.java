package com.toyota.rentalcar.dev.Board.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.toyota.rentalcar.dev.commons.model.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "TBL_FILES")
@JsonIgnoreProperties({"id", "originalName"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @JoinColumn(name = "board_id")
    @ManyToOne(cascade = CascadeType.ALL,
                fetch = FetchType.LAZY)
    private Board board;

    private String fileName;
    private String uri;

    @Builder
    public File(Board board, String fileName, String uri){
        this.board      = board;
        this.fileName   = fileName;
        this.uri        = uri;
    }

    public void updateBoardFK(Board board){
        this.board = board;
    }
}
