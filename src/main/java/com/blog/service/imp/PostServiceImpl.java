package com.blog.service.imp;

import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.PostDto;
import com.blog.repository.PostRepository;
import com.blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepo;
    public PostServiceImpl(PostRepository postRepo) {  // It is Constructor based dependencies
        this.postRepo = postRepo;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

      //  postRepo.save(post);
        Post savePost = postRepo.save(post);

        PostDto dto = new PostDto();
        dto.setId(savePost.getId());
        dto.setTitle(savePost.getTitle());
        dto.setContent(savePost.getContent());
        dto.setDescription(savePost.getDescription());
        return dto;
    }

    @Override
    public void deletePost(long id) {
//        Optional<Post> byId = postRepo.findById(id);
//        if(byId.isPresent()) {
//            postRepo.deleteById(id);
//        }else {
//            throw new ResourceNotFoundException("Post Not found with id:" +id);
//    }
        // using functional interface with Lambda expression throw exception
        Post post = postRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Post not found with id:" +id));
        postRepo.deleteById(id);
    }

    @Override
    public List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
       Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
        Page<Post> postPage = postRepo.findAll(pageable);
        List<Post> posts = postPage.getContent();

        List<PostDto> dtos = posts.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
        return dtos ;
    }

    @Override
    public PostDto updatePost(long postId, PostDto postDto) {
        Post post = postRepo.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("Post Not Found with id: "+ postId)
        );
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        Post savePost= postRepo.save(post);

        PostDto dto = mapToDto(savePost);
            return dto;
    }
    PostDto mapToDto(Post post){
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setDescription(post.getDescription());
        return dto;
    }
}
