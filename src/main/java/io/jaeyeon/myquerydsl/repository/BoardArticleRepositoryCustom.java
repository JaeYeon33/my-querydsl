package io.jaeyeon.myquerydsl.repository;

import io.jaeyeon.myquerydsl.board.model.SearchForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static io.jaeyeon.myquerydsl.board.model.BoardArticleDto.*;

public interface BoardArticleRepositoryCustom {

    Page<BoardArticleItemDto> pageArticle(SearchForm searchForm, Pageable pageable);
}
