package io.jaeyeon.myquerydsl.board.service;

import io.jaeyeon.myquerydsl.board.entity.BoardArticleEntity;
import io.jaeyeon.myquerydsl.board.model.BoardArticleDto;
import io.jaeyeon.myquerydsl.board.model.SearchForm;
import io.jaeyeon.myquerydsl.repository.BoardArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static io.jaeyeon.myquerydsl.board.model.BoardArticleDto.*;

@Service
@RequiredArgsConstructor
public class BoardArticleService {

    private final BoardArticleRepository boardArticleRepository;

    public Page<BoardArticleItemDto> pageArticle(SearchForm search, Pageable pageable) {
        Page<BoardArticleItemDto> pageResult = boardArticleRepository.pageArticle(search, pageable);
        return pageResult;
    }

    public BoardArticleItemDto getArticle(int articleSeq) {
        BoardArticleEntity article = boardArticleRepository.findById(articleSeq).orElseThrow();
        if (article.isDeleteF()) {
            throw new RuntimeException("삭제된 게시물 입니다.");
        }
        return BoardArticleItemDto.of(article);

    }

    @Transactional
    public BoardArticleItemDto createArticle(BoardArticleItemDto article) {
        BoardArticleEntity articleEntity = BoardArticleEntity.of(article);
        boardArticleRepository.save(articleEntity);
        return BoardArticleItemDto.of(articleEntity);
    }

    @Transactional
    public BoardArticleItemDto updateArticle(BoardArticleUpdateDto updateArticle) {
        BoardArticleEntity article = boardArticleRepository.findById(updateArticle.getArticleSeq()).orElseThrow();
        article.update(updateArticle);
        return BoardArticleItemDto.of(article);
    }

    @Transactional
    public void deleteArticle(int articleSeq) {
        BoardArticleEntity article = boardArticleRepository.findById(articleSeq).orElseThrow();
        article.applyDelete();
    }

}
