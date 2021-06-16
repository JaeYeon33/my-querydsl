package io.jaeyeon.myquerydsl.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.jaeyeon.myquerydsl.board.model.SearchForm;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static io.jaeyeon.myquerydsl.board.entity.QBoardArticleEntity.*;
import static io.jaeyeon.myquerydsl.board.entity.QBoardCommentEntity.*;
import static io.jaeyeon.myquerydsl.board.model.BoardArticleDto.*;

@Repository
@RequiredArgsConstructor
public class BoardArticleRepositoryImpl implements BoardArticleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<BoardArticleItemDto> pageArticle(SearchForm searchForm, Pageable pageable) {
        QueryResults<BoardArticleItemDto> result = queryFactory
                .from(boardArticleEntity)
                .select(Projections.fields(
                        BoardArticleItemDto.class,
                        boardArticleEntity.articleSeq,
                        boardArticleEntity.userName,
                        boardArticleEntity.title,
                        boardArticleEntity.content,
                        boardArticleEntity.regDate,
                        boardArticleEntity.deleteF,
                        ExpressionUtils.as(JPAExpressions.select(boardCommentEntity.commentSeq.count())
                                        .from(boardCommentEntity)
                                        .where(boardCommentEntity.boardArticle.eq(boardArticleEntity)),
                                "commentCount")))
                .where(
                        boardArticleEntity.deleteF.eq(false),
                        containsTitle(searchForm.getTitle()),
                        containsContent(searchForm.getContent())
                )
                .orderBy(boardArticleEntity.articleSeq.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        PageImpl<BoardArticleItemDto> pageResult = new PageImpl<>(result.getResults(), pageable, result.getTotal());
        return pageResult;
    }

    private BooleanExpression containsTitle(String title) {
        return StringUtils.isEmpty(title) ? null : boardArticleEntity.title.contains(title);

    }

    private BooleanExpression containsContent(String content) {
        return StringUtils.isEmpty(content) ? null : boardArticleEntity.content.contains(content);
    }
}
