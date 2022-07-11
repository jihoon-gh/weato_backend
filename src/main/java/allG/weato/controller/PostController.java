package allG.weato.controller;

import allG.weato.config.auth.dto.SessionMember;
import allG.weato.domain.Attachment;
import allG.weato.domain.Comment;
import allG.weato.domain.Member;
import allG.weato.domain.Post;
import allG.weato.dto.create.CreateCommentRequest;
import allG.weato.dto.create.CreateCommentResponse;
import allG.weato.dto.create.CreatePostRequest;
import allG.weato.dto.create.CreatePostResponse;
import allG.weato.dto.PostDetail;
import allG.weato.dto.PostDto;
import allG.weato.dto.update.UpdatePostRequest;
import allG.weato.dto.update.UpdatePostResponse;
import allG.weato.service.MemberService;
import allG.weato.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final MemberService memberService;
    private final PostService postService;
    private final HttpSession httpSession;
    private PostService postService1;


    @Operation(summary = "get all posts", description = "모든 게시글 가져오기")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "Ok", content = @Content(schema = @Schema(implementation = PostDto.class))),
            @ApiResponse(responseCode = "400",description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404",description = "NOT FOUND"),
            @ApiResponse(responseCode = "500",description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/api/posts")
    public Result showPosts() {
        List<Post> posts = postService.findAll();

        List<PostDto> result = posts.stream()
                .map(p -> new PostDto(p))
                .collect(Collectors.toList());

        return new Result(result.size(), result);
    }

    @PostMapping("/api/posts")
    @ResponseBody
    public CreatePostResponse createPost(@RequestBody @Valid CreatePostRequest request){
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Member findMember = memberService.findByEmail(member.getEmail());
        Post post = new Post(request.getTitle(),request.getContent(),request.getBoardType(),LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        post.setOwner(findMember);
        for (String  s : request.getImageUrls()) {
            Attachment attachment = new Attachment(s);
            post.addAttachments(attachment);
        }
        postService.join(post);
        CreatePostResponse response = new CreatePostResponse(post);
        return response;
    }

    @GetMapping("/api/posts/{id}")
    public PostDetail showPost(@PathVariable("id") Long id) {
      Post post = postService.findPostById(id);
      post.addViews();
      PostDetail postDetail = new PostDetail(post);
      return postDetail;
    }



    @PatchMapping("/api/posts/{id}")
    @ResponseBody
    public UpdatePostResponse updatePost(@PathVariable("id") Long id, @RequestBody @Valid UpdatePostRequest request){
       if(request.getTitle()!=null&&request.getContent()==null){
           postService.updatePostTitle(id, request.getTitle());
       } else if (request.getContent() != null &&request.getTitle()==null) {
           postService.updatePostContent(id, request.getContent());
       }else postService.updatePost(id,request.getTitle(), request.getContent());

        UpdatePostResponse updatePostResponse = new UpdatePostResponse(id, LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        return updatePostResponse;
    }
    @DeleteMapping("/api/posts/{id}")
    @ResponseBody
    public String deletePost(@PathVariable("id") Long id)
    {
        Post post = postService.findPostById(id);
        postService.DeletePost(post);
        return "successfully deleted";
    }





    @Data
    @AllArgsConstructor
    static class Result<T>{
        int count;
        private T data;
    }
}


