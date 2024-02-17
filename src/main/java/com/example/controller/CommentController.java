package com.example.controller;

import com.example.dto.article.ArticleDTO;
import com.example.dto.article.ArticleFilterDTO;
import com.example.dto.comment.CommentDTO;
import com.example.dto.comment.CommentFilterDTO;
import com.example.dto.comment.UpdateCommentDTO;
import com.example.enums.ProfileRole;
import com.example.service.CommentService;
import com.example.utils.HttpRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<CommentDTO> create(@RequestBody CommentDTO dto) {
        return ResponseEntity.ok(commentService.create(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UpdateCommentDTO> update(@PathVariable("id") Integer id,
                                                   @RequestBody(required = false) UpdateCommentDTO dto,
                                                   HttpServletRequest request) {
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN, ProfileRole.ROLE_MODERATOR,
                ProfileRole.ROLE_PUBLISHER, ProfileRole.ROLE_USER);
        return ResponseEntity.ok(commentService.update(id,dto,profileId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id ){
        return ResponseEntity.ok(commentService.delete(id));
    }

    @GetMapping("/getList/{id}")
    public ResponseEntity<List<CommentDTO>> getList(@PathVariable("id") String id){
        return ResponseEntity.ok(commentService.getList(id));
    }

    @GetMapping("/adm/pagination")
    public ResponseEntity<PageImpl<CommentDTO>> paginationRegion(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                 @RequestParam(value = "size", defaultValue = "2") Integer size,
                                                                 HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(commentService.getPagination(page, size));
    }

    @PostMapping("/adm/filter")
    public ResponseEntity<PageImpl<CommentDTO>> filter(@RequestBody CommentFilterDTO dto,
                                                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "2") Integer size,
                                                       HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN);
        PageImpl<CommentDTO> result = commentService.filter(dto, page, size);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getRepliedComment/{id}")
    public ResponseEntity<List<CommentDTO>> getByCommentId(@PathVariable("id") Integer id){
        return ResponseEntity.ok(commentService.getByCommentId(id));
    }




}
