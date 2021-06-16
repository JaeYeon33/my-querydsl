package io.jaeyeon.myquerydsl.board.controller;

import io.jaeyeon.myquerydsl.board.model.BoardArticleDto;
import io.jaeyeon.myquerydsl.board.model.SearchForm;
import io.jaeyeon.myquerydsl.board.service.BoardArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import static io.jaeyeon.myquerydsl.board.model.BoardArticleDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@Api(tags = {"게시물 API"})
@Validated
public class BoardArticleController {

    private final BoardArticleService boardArticleService;

    @ApiOperation(value = "게시물 페이징")
    @GetMapping("/articlePage")
    public ResponseEntity<Page<BoardArticleItemDto>> pageArticle(@Valid SearchForm search, Pageable pageable) {
        Page<BoardArticleItemDto> pageResult = boardArticleService.pageArticle(search, pageable);
        return ResponseEntity.ok(pageResult);
    }

    @ApiOperation(value = "단일 게시물 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleSeq", value = "게시물 아이디", required = true, paramType = "path"),
    })
    @GetMapping("/article/{articleSeq}")
    public ResponseEntity<BoardArticleItemDto> getArticle(@PathVariable("articleSeq") @Positive int articleSeq) {
        BoardArticleItemDto article = boardArticleService.getArticle(articleSeq);
        return ResponseEntity.ok(article);
    }

    @ApiOperation(value = "게시물 등록")
    @PostMapping("/article")
    public ResponseEntity<BoardArticleItemDto> createArticle(@Valid BoardArticleItemDto article) {
        BoardArticleItemDto boardArticle = boardArticleService.createArticle(article);
        return ResponseEntity.ok(boardArticle);
    }

    @ApiOperation(value = "게시물 수정")
    @PutMapping("/article/{articleSeq}")
    public ResponseEntity<BoardArticleItemDto> updateArticle(@PathVariable("articleSeq") @Positive int articleSeq,
                                                             @Valid BoardArticleUpdateDto updateArticle) {
        updateArticle.setArticleSeq(articleSeq);
        BoardArticleItemDto boardArticle = boardArticleService.updateArticle(updateArticle);
        return ResponseEntity.ok(boardArticle);
    }

    @ApiOperation(value = "게시물 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleSeq", value = "게시물 아이디", required = true, paramType = "path"),
    })
    @DeleteMapping("/article/{articleSeq}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("articleSeq") @Positive int articleSeq) {
        boardArticleService.deleteArticle(articleSeq);
        return ResponseEntity.ok().build();
    }

}
