package io.jaeyeon.myquerydsl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static io.jaeyeon.myquerydsl.board.model.BoardCommentDto.*;

public interface BoardCommentRepositoryCustom {

    Page<BoardCommentItemDto> pageComment(int articleSeq, Pageable pageable);
}
