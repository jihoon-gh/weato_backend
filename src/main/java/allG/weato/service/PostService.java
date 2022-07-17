package allG.weato.service;

import allG.weato.domain.Comment;
import allG.weato.domain.Member;
import allG.weato.domain.Post;
import allG.weato.domain.PostLike;
import allG.weato.domain.enums.BoardType;
import allG.weato.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public void save(Post post) {
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

    public Page<Post> findPostWithPaging(Integer page){
        PageRequest pageRequest = PageRequest.of(page,20, Sort.by(Sort.Direction.DESC,"createAt"));
        return postRepository.findAll(pageRequest);
    }

    public Page<Post> findPostPageWithBoardType(Integer page, BoardType boardType){
        PageRequest pageRequest = PageRequest.of(page,20,Sort.by(Sort.Direction.DESC,"createAt"));
        return postRepository.findPostsByBoardType(pageRequest);
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

    @Transactional
    public void addLike(Member member, Post post, PostLike postLike) {
        postLike.setOwner(member);
        post.addLike(postLike);
    }
}

