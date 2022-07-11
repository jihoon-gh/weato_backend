package allG.weato.service;

import allG.weato.domain.Post;
import allG.weato.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public void join(Post post){
        postRepository.save(post);
    }

    public List<Post> findAll(){
        return postRepository.findAll();
    }

    public Post findPostById(Long id){
        return postRepository.findPostById(id);
    }

    public Post findPostByTitle(String title){
        return postRepository.findPostByTitle(title);
    }

    @Transactional
    public void updatePostContent(Long id,String content ) {
        Post post = postRepository.findPostById(id);
        post.changeContent(content);
    }
    @Transactional
    public void updatePostTitle(Long id,String title) {
        Post post = postRepository.findPostById(id);
        post.changeTitle(title);
    }
    @Transactional
    public void updatePost(Long id,String title, String content) {
        Post post = postRepository.findPostById(id);
        post.changeTitle(title);
        post.changeContent(content);
    }

    @Transactional
    public void DeletePost(Post post){
        postRepository.delete(post);
    }


}
