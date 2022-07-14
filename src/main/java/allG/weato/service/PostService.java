package allG.weato.service;

import allG.weato.domain.Comment;
import allG.weato.domain.Post;
import allG.weato.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public void addComment(Post post, Comment comment) {
        post.addComment(comment);
        return;
    }

    @Transactional
    public void join(Post post) {
        postRepository.save(post);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Transactional
    public Post findPostById(Long id) {
        return postRepository.findPostById(id);
    }

    public Post findPostByTitle(String title) {
        return postRepository.findPostByTitle(title);
    }

    @Transactional
    public void updatePostContent(Post post, String content) {
        post.changeContent(content);
    }

    @Transactional
    public void updatePostTitle(Post post, String title) {
        post.changeTitle(title);
    }

    @Transactional
    public void addViews(Post post)
    {
        post.addViews();
    }

    @Transactional
    @Modifying
    public void updatePost(Post post, String title, String content) {

        post.changeTitle(title);
        post.changeContent(content);
    }

    @Transactional
    @Modifying
    public void DeletePost(Post post) {
        postRepository.delete(post);
    }

}

