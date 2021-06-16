package io.jaeyeon.myquerydsl.board.entity;

import io.jaeyeon.myquerydsl.board.model.BoardCommentDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

import static io.jaeyeon.myquerydsl.board.model.BoardCommentDto.*;

@Entity(name = "BOARD_COMMENT")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class BoardCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "COMMENT_SEQ", nullable = true)
    private int commentSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARTICLE_SEQ")
    private BoardArticleEntity boardArticle;

    @Column(name = "USER_NAME", length = 50, nullable = true)
    private String userName;

    @Column(name = "CONTENT", length = 4000, nullable = true)
    private String content;

    @Column(name = "REG_DATE", nullable = true)
    @CreatedDate
    private LocalDateTime regDate;

    @Column(name = "DELETE_F", nullable = true)
    @Type(type = "yes_no")
    private boolean deleteF;

    public static BoardCommentEntity of(BoardCommentItemDto boardCommentDto) {
        BoardCommentEntity entity = new BoardCommentEntity();
        entity.content = boardCommentDto.getContent();
        entity.userName = boardCommentDto.getUserName();
        entity.setBoardArticle(BoardArticleEntity.of(boardCommentDto.getArticleSeq()));
        return entity;
    }

    public void update(BoardCommentUpdateDto updateComment) {
        this.content = updateComment.getContent();
        this.userName = updateComment.getUserName();
    }


    public void applyDelete() {
        this.deleteF = true;
    }
}
