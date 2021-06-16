package io.jaeyeon.myquerydsl.repository;

import io.jaeyeon.myquerydsl.board.entity.BoardCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardCommentRepository extends JpaRepository<BoardCommentEntity, Integer>, BoardCommentRepositoryCustom {

}
