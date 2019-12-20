package com.toyota.rentalcar.dev.dto;

import com.toyota.rentalcar.dev.domain.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TagSaveRequestDto {

    private String title;

    public Tag toEntity(){
        return Tag.builder()
                .title(title)
                .build();
    }
}
