package com.blog.service;

import com.blog.entity.Post;
import com.blog.payload.PostDto;

import java.util.List;

public interface PostService {

//    public void createPost(PostDto postDto); // it is use only post
    public PostDto createPost(PostDto postDto);

    void deletePost(long id);

    List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto updatePost(long postId, PostDto postDto);

}
