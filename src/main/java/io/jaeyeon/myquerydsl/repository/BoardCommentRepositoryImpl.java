package io.jaeyeon.myquerydsl.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static io.jaeyeon.myquerydsl.board.entity.QBoardCommentEntity.*;
import static io.jaeyeon.myquerydsl.board.model.BoardCommentDto.*;

@Repository
@RequiredArgsConstructor
public class BoardCommentRepositoryImpl implements BoardCommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<BoardCommentItemDto> pageComment(int articleSeq, Pageable pageable) {
        QueryResults<BoardCommentItemDto> result = queryFactory
                .from(boardCommentEntity)
                .select(Projections.fields(
                        BoardCommentItemDto.class,
                        boardCommentEntity.commentSeq,
                        boardCommentEntity.userName,
                        boardCommentEntity.content,
                        boardCommentEntity.regDate
                ))
                .where(
                        boardCommentEntity.deleteF.eq(false),
                        boardCommentEntity.boardArticle.articleSeq.eq(articleSeq)
                )
                .orderBy(boardCommentEntity.commentSeq.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }
}
