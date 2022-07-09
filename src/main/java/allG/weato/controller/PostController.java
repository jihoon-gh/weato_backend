package allG.weato.controller;

import allG.weato.config.auth.dto.SessionMember;
import allG.weato.domain.Attachment;
import allG.weato.domain.Comment;
import allG.weato.domain.Member;
import allG.weato.domain.Post;
import allG.weato.dto.CreatePostRequest;
import allG.weato.dto.CreatePostResponse;
import allG.weato.repository.MemberRepository;
import allG.weato.repository.PostRepository;
import allG.weato.service.MemberService;
import allG.weato.service.PostService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostRepository postRepository;
    private final MemberService memberService;
    private final PostService postService;
    private final HttpSession httpSession;

    @GetMapping("/api/posts")
    public List<PostsDto> showPosts() {
        List<Post> posts = postRepository.findAll();

        List<PostsDto> result = posts.stream()
                .map(p -> new PostsDto(p))
                .collect(Collectors.toList());

        return result;
    }

    @GetMapping("/api/posts/{id}")
    public PostDetail showPost(@PathVariable("id") Long id) {
      Post post = postRepository.findPostById(id);
      PostDetail postDetail = new PostDetail(post);
      return postDetail;
    }

    @PostMapping("/api/posts")
    public CreatePostResponse createPost(@RequestBody @Valid CreatePostRequest request){
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Post post = new Post(request.getTitle(),request.getContent(),request.getBoardType(),LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        Member findMember = memberService.findByEmail(member.getEmail());
        post.setOwner(findMember);
        if(request.getImageUrls().size()!=0)
        {
            for (String a : request.getImageUrls()){
                Attachment attachment = new Attachment(a);
                post.addAttachments(attachment);
            }
        }

        CreatePostResponse response = new CreatePostResponse(findMember.getName(),post.getTitle(),post.getContent(),post.getCreateAt(),post.getBoardType(),post.getAttachmentList());
        return response;
    }




    @Data
    static class PostsDto {
        private Long id;
        private String title;
        private LocalDateTime createdAt;
        private String author;
        public PostsDto(Post post) {
            id = post.getId();
            title = post.getTitle();
            createdAt = post.getCreateAt();
            author = post.getMember().getName();
        }
    }

    @Data
    static class PostDetail{
        String Author;
        String title;
        String content;
        LocalDateTime createdAt;
        int likeCount;
        List<Comment> comments;

        public PostDetail(Post post) {
            this.title = post.getTitle();
            this.content = post.getContent();
            this.createdAt = post.getCreateAt();
            this.likeCount = post.getLikeCount();
            this.comments = post.getCommentList();
            this.Author=post.getMember().getName();
        }
    }
}


