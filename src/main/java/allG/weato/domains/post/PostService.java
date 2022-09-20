package allG.weato.domains.post;

import allG.weato.domains.comment.entities.Comment;
import allG.weato.domains.member.entities.Member;
import allG.weato.domains.post.dto.update.UpdatePostRequest;
import allG.weato.domains.post.entities.Post;
import allG.weato.domains.post.entities.PostLike;
import allG.weato.domains.enums.BoardType;
import allG.weato.domains.post.entities.Scrap;
import allG.weato.validation.CommonErrorCode;
import allG.weato.validation.RestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public void addComment(Post post, Comment comment) {
        post.addComment(comment);
    }

    @Transactional
    public void save(Post post) {
        postRepository.save(post);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findPostById(Long id) {
        return postRepository.findPostById(id).orElseGet(()->{
            throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);
        });
    }


    public Post findPostFetchById(Long id){
        return postRepository.findPostFecthJoin(id).orElseGet(()->{
            throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);
        });
    }

    public Post findPostByTitle(String title) {
        return postRepository.findPostByTitle(title);
    }

    public Page<Post> findPostWithPaging(Integer page){
        PageRequest pageRequest = PageRequest.of(page,6, Sort.by(Sort.Direction.DESC,"createdAt"));
        return postRepository.findAll(pageRequest);
    }

    public Page<Post> findPostPageWithBoardType(Integer page, BoardType boardType){
        PageRequest pageRequest = PageRequest.of(page,6,Sort.by(Sort.Direction.DESC,"createdAt"));
        return postRepository.findPostsByBoardType(pageRequest,boardType);
    }

    public Page<Post> searchPostsWithKeyword(Integer page, String keyword){
        PageRequest pageRequest = PageRequest.of(page,6,Sort.by(Sort.Direction.DESC,"createdAt"));
        return postRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword,keyword,pageRequest);
    }

    @Transactional
    public void updatePostContent(Post post, String content) {
        post.changeContent(content);
    }

    @Transactional
    public void updatePostTitle(Post post, String title) {
        post.changeTitle(title);
    }

//    @Transactional
//    public void addViews(Post post) {
//        post.addViews();
//    }

    @Transactional
    public void updatePost(Post post, UpdatePostRequest request) {
        Boolean checker = false;
        if(request.getTitle()!=null){
            post.changeTitle(request.getTitle());
            checker=true;
        }
        if(request.getContent()!=null){
            post.changeContent(request.getContent());
            checker=true;
        }
        if(checker) post.updateLocalDateTime(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
    }

    @Transactional
    public void deletePost(Post post) {
        Member member = post.getMember();
        member.deletePost(post);
        postRepository.delete(post);
    }

    @Transactional
    public void addLike(Member member, Post post, PostLike postLike) {
        member.addPostLike(postLike);
        member.addPostLikeChecker(post.getId());
        post.addLike(postLike);
    }

    @Transactional
    public void deleteLike(Member member, Post post, PostLike postLike){
        member.deletePostLike(postLike);
        member.deletePostLikeChecker(post.getId());
        post.deleteLike(postLike);
        postRepository.deletePostLikeById(postLike.getId());
    }

    @Transactional
    public void scrapPost(Member member, Post post, Scrap scrap) {
        member.addScrap(scrap);
        member.addScrapChecker(post.getId());
        post.addScrap(scrap);
    }

    @Transactional
    public void deleteScrap(Member member, Post post, Scrap scrap){
        member.deleteScrap(scrap);
        member.deleteScrapChecker(post.getId());
        post.deleteScrap(scrap);
        postRepository.deleteScrapById(scrap.getId());
    }

    public List<Post> retrieveHotTopicsOfWeek(){
        LocalDateTime std = LocalDateTime.now(ZoneId.of("Asia/Seoul")).minusDays(7);
        PageRequest pageRequest = PageRequest.of(0,10);
        return postRepository.sortPostsByLikeCount(std,pageRequest).getContent();
    }

    public List<Post> retrieveMostScrapedOfWeek(){
        LocalDateTime std = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        PageRequest pageRequest = PageRequest.of(0,10);
        return postRepository.sortPostByScrapCount(std,pageRequest).getContent();
    }

    public Post findOneByIdWithLikes(Long id){
        return postRepository.findPostByIdWithLikes(id);
    }
    public Post findOneByIdWithScrap(Long id){
        return postRepository.findPostByIdWithScrap(id);
    }
}

