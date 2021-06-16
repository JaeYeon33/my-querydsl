package io.jaeyeon.myquerydsl.repository;

import io.jaeyeon.myquerydsl.board.entity.BoardArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardArticleRepository extends JpaRepository<BoardArticleEntity, Integer>, BoardArticleRepositoryCustom {
}
