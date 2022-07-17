package allG.weato.controller;

import allG.weato.config.auth.dto.SessionMember;
import allG.weato.domain.Attachment;
import allG.weato.domain.Member;
import allG.weato.domain.Post;
import allG.weato.domain.PostLike;
import allG.weato.dto.AddLikeDto;
import allG.weato.dto.post.create.CreatePostRequest;
import allG.weato.dto.post.create.CreatePostResponse;
import allG.weato.dto.post.retrieve.PostDetail;
import allG.weato.dto.post.retrieve.PostDto;
import allG.weato.dto.error.Error400;
import allG.weato.dto.error.Error404;
import allG.weato.dto.error.Error500;
import allG.weato.dto.post.update.UpdatePostRequest;
import allG.weato.dto.post.update.UpdatePostResponse;
import allG.weato.service.MemberService;
import allG.weato.service.PostService;
import allG.weato.validation.CommonErrorCode;
import allG.weato.validation.RestException;
import com.sun.net.httpserver.HttpsExchange;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.criteria.CriteriaBuilder;
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


    @Operation(summary = "get all posts", description = "게시글 전체조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "Ok", content = @Content(schema = @Schema(implementation = PostDto.class))),
            @ApiResponse(responseCode = "400",description = "BAD REQUEST", content = @Content(schema = @Schema(implementation =Error400.class ))),
            @ApiResponse(responseCode = "404",description = "NOT FOUND", content = @Content(schema = @Schema(implementation =Error404.class ))),
            @ApiResponse(responseCode = "500",description = "INTERNAL SERVER ERROR", content = @Content(schema = @Schema(implementation =Error500.class )))
    })
    @GetMapping("/api/posts")
    public ResultForPaging showPosts(@RequestParam("page") Integer page) {

        Page<Post> findPosts = postService.findPostWithPaging(page);
        if(findPosts.isEmpty()) throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);
        List<Post> posts = findPosts.getContent();
        int lastPageNum = findPosts.getTotalPages();
        int current = findPosts.getNumber();
        int min = 1+current/10*10;
        int max =10+current/10*10;
        if(max>=lastPageNum) max = lastPageNum;
        List<PostDto> result = posts.stream()
                .map(p -> new PostDto(p))
                .collect(Collectors.toList());

        return new ResultForPaging(result, min, max, current);
    }




    @Operation(summary = "Create posts", description = "게시글 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "201",description = "created successfully", content = @Content(schema = @Schema(implementation =CreatePostResponse.class ))),
            @ApiResponse(responseCode = "400",description = "BAD REQUEST", content = @Content(schema = @Schema(implementation = Error400.class ))),
            @ApiResponse(responseCode = "404",description = "NOT FOUND", content = @Content(schema = @Schema(implementation =Error404.class ))),
            @ApiResponse(responseCode = "500",description = "INTERNAL SERVER ERROR", content = @Content(schema = @Schema(implementation = Error500.class )))
    })
    @PostMapping("/api/posts")
    public CreatePostResponse createPost(@RequestBody @Valid CreatePostRequest request){
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Member findMember = memberService.findByEmail(member.getEmail());
        Post post = new Post(request.getTitle(),request.getContent(),request.getBoardType(),LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        post.setOwner(findMember);
        for (String  s : request.getImageUrls()) {
            Attachment attachment = new Attachment(s);
            post.addAttachments(attachment);
        }
        postService.save(post);
        CreatePostResponse response = new CreatePostResponse(post);
        return response;
    }

    @Operation(summary = "get specific post", description = "게시글 단건조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "Ok"),
            @ApiResponse(responseCode = "400",description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404",description = "NOT FOUND"),
            @ApiResponse(responseCode = "500",description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/api/posts/{id}")
    public PostDetail showPost(@PathVariable("id") Long id) {
      Post post = postService.findPostById(id);
      if(post==null) throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);
      postService.addViews(post);
      PostDetail postDetail = new PostDetail(post);

      return postDetail;
    }


    @PatchMapping("/api/posts/{id}")
    public UpdatePostResponse updatePost(@PathVariable("id") Long id, @RequestBody @Valid UpdatePostRequest request){
       Post post = postService.findPostById(id);
       if(post==null) throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);
        if(request.getTitle()!=null&&request.getContent()==null){
           postService.updatePostTitle(post, request.getTitle());
       } else if (request.getContent() != null &&request.getTitle()==null) {
           postService.updatePostContent(post, request.getContent());
       }else postService.updatePost(post, request.getTitle(), request.getContent());

        UpdatePostResponse updatePostResponse = new UpdatePostResponse(id, LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        return updatePostResponse;
    }
    @Operation(summary = "delete post", description = "게시글 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "204",description = "Ok"),
            @ApiResponse(responseCode = "400",description = "BAD REQUEST", content = @Content(schema = @Schema(implementation = Error400.class ))),
            @ApiResponse(responseCode = "404",description = "NOT FOUND", content = @Content(schema = @Schema(implementation =Error404.class ))),
            @ApiResponse(responseCode = "500",description = "INTERNAL SERVER ERROR", content = @Content(schema = @Schema(implementation = Error500.class )))
    })
    @DeleteMapping("/api/posts/{id}")
    public String deletePost(@PathVariable("id") Long id)
    {
        Post post = postService.findPostById(id);
        if(post==null) throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);
        postService.DeletePost(post);
        return "successfully deleted";
    }

    @PostMapping("/api/posts/{postId}/likes")
    public AddLikeDto likeToPost(@PathVariable("postId") Long id){

        Post post = postService.findPostById(id);
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Member findMember = memberService.findByEmail(member.getEmail());
        if(findMember==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        PostLike postLike = new PostLike();
        postService.addLike(findMember,post,postLike);
        return new AddLikeDto(post.getId(),post.getLikeCount());

    }

//    @DeleteMapping("/api/posts/{postId}/likes")
//    public





    @Data
    @AllArgsConstructor
    static class ResultForPaging<T>{
        private T data;
        private int min;
        private int max;
        private int current;
    }
}


