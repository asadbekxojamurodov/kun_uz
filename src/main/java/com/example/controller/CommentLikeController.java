package com.example.controller;

import com.example.dto.comment.CommentLikeDTO;
import com.example.service.CommentLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/commentLike")
public class CommentLikeController {

    @Autowired
    private CommentLikeService commentLikeService;


    @PostMapping("/like")
    public ResponseEntity<CommentLikeDTO> createLike(@RequestBody CommentLikeDTO dto) {
        return ResponseEntity.ok(commentLikeService.createLike(dto));
    }

    @PostMapping("/dislike")
    public ResponseEntity<CommentLikeDTO> createDislike(@RequestBody CommentLikeDTO dto) {
        return ResponseEntity.ok(commentLikeService.createDislike(dto));
    }

    @DeleteMapping("/remove/{cId}/{pId}")
    public ResponseEntity<Boolean> remove(@PathVariable("cId") Integer cId,
                                          @PathVariable("pId") Integer pId) {
        return ResponseEntity.ok(commentLikeService.remove(cId, pId));
    }


}
