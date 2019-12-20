package com.toyota.rentalcar.dev.dto;

import com.toyota.rentalcar.dev.domain.Tag;
import com.toyota.rentalcar.dev.repositories.TagRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Commit
class TagSaveRequestDtoTest {

    @Autowired
    private TagRepository tagRepo;

    public void 태그저장_불러오기(){

        TagSaveRequestDto dto = new TagSaveRequestDto();
        dto.setTitle("하이");

        tagRepo.save(dto.toEntity());

        Tag tag = tagRepo.findById(1L)
                .orElse(null);

        assertThat(tag).isNotNull();
    }
}