package com.blog.controller;

import com.blog.payload.CommentDto;
import com.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private CommentService commentService ;

    public CommentController(CommentService commentService) {

        this.commentService = commentService;
    }
// http://localhost:8080/api/comments?postId=15
    @PostMapping
    public ResponseEntity<CommentDto>createComment(@RequestParam("postId") long postId,@RequestBody CommentDto commentDto){
        CommentDto dto = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
// http://localhost:8080/api/comments/10
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable long commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity<>("Post is deleted !!",HttpStatus.OK);
    }
  //  http://localhost:8080/api/comments/18
    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto>>getCommentByPostId(@PathVariable long postId){
        List<CommentDto> commentDto = commentService.getCommentByPostId(postId);
        return new ResponseEntity<>(commentDto,HttpStatus.OK);
    }
//    http://localhost:8080/api/comments
    @GetMapping
    public ResponseEntity<List<CommentDto>>getAllComments(){
       List<CommentDto> commentDtos = commentService.getAllComments();
       return new ResponseEntity<>(commentDtos,HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<CommentDto>updateComment(@RequestParam("commentId") long commentId, @RequestBody CommentDto updatedCommentDto) {
        CommentDto updatedDto = commentService.updateComment(commentId, updatedCommentDto);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }
}