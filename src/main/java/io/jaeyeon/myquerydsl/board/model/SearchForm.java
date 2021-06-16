package io.jaeyeon.myquerydsl.board.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class SearchForm {

    /**
     * 제목 검색
     */
    @Length(max = 100)
    private String title;

    /**
     * 본문 검색
     */
    @Length(max = 100)
    private String content;
}
