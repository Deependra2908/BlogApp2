package com.blog.service.imp;

import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.CommentDto;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import com.blog.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private PostRepository postRepo;
    private CommentRepository commentRepo;

    public CommentServiceImpl(PostRepository postRepo,CommentRepository commentRepo) {
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
    }
    @Override
    public  CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post Not found with id: " + postId));
        Comment comment = new Comment();
        comment.setBody(commentDto.getBody());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());

        comment.setPost(post);
        Comment  savedComment = commentRepo.save(comment);

        CommentDto dto = new CommentDto();
        dto.setId(savedComment.getId());
        dto.setName(savedComment.getName());
        dto.setBody(savedComment.getBody());
        dto.setEmail(savedComment.getEmail());
        return dto;
    }

    @Override
    public void deleteComment(long commentId) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("CommentId is Not Found " + commentId)
        );
        commentRepo.deleteById(commentId);
    }

    @Override
    public  List<CommentDto> getCommentByPostId(long postId) {
        List<Comment> comments = commentRepo.findByPostId(postId);
        List<CommentDto> commentDtos = comments.stream().map(c -> mapToDto(c)).collect(Collectors.toList());
        return commentDtos ;
    }

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> comments = commentRepo.findAll();
        List<CommentDto> dtos = comments.stream().map(c -> mapToDto(c)).collect(Collectors.toList());
        return dtos;

    }

    @Override
    public CommentDto updateComment(long commentId, CommentDto updatedCommentDto) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(
                ()-> new ResourceNotFoundException("Comment id is Not Found :" + commentId)
        );
        comment.setBody(updatedCommentDto.getBody());
        comment.setEmail(updatedCommentDto.getEmail());
        comment.setName(updatedCommentDto.getName());

        Comment  saveComment = commentRepo.save(comment);
         CommentDto dto= mapToDto(saveComment);
            return dto ;
    }

    CommentDto mapToDto(Comment comment){
         CommentDto dto = new CommentDto();
         dto.setId(comment.getId());
         dto.setName(comment.getName());
         dto.setBody(comment.getBody());
         dto.setEmail(comment.getEmail());
         return dto;
    }
}
