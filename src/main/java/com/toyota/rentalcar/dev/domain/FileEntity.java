package com.toyota.rentalcar.dev.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class FileEntity extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "board_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    private String fileName;

    @Builder
    public FileEntity(Board board, String fileName){
        this.board      = board;
        this.fileName   = fileName;
    }
}
