package allG.weato.domains.post;

import allG.weato.domains.enums.BoardType;
import allG.weato.domains.enums.TagType;
import allG.weato.domains.post.entities.Attachment;
import allG.weato.domains.member.entities.Member;
import allG.weato.domains.post.entities.Post;
import allG.weato.domains.post.entities.PostLike;
import allG.weato.domains.post.entities.Scrap;
import allG.weato.domains.post.dto.create.PostScrapDto;
import allG.weato.dto.*;
import allG.weato.domains.post.dto.create.CreatePostRequest;
import allG.weato.domains.post.dto.create.CreatePostResponse;
import allG.weato.domains.post.dto.retrieve.PostDetailRetrieveDto;
import allG.weato.domains.post.dto.retrieve.PostRetrieveDto;
import allG.weato.dto.error.Error400;
import allG.weato.dto.error.Error404;
import allG.weato.dto.error.Error500;
import allG.weato.domains.post.dto.update.UpdatePostRequest;
import allG.weato.domains.post.dto.update.UpdatePostResponse;
import allG.weato.domains.member.MemberService;
import allG.weato.oauth2.JwtMemberDetails;
import allG.weato.validation.CommonErrorCode;
import allG.weato.validation.RestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final MemberService memberService;
    private final PostService postService;

    @Operation(summary = "retrieve data for community home", description = "커뮤니티 홈")
    @GetMapping("/community")
    public ResultForCommunity retrieveCommunityHome(){
        List<Post> posts = postService.findAll();

        List<PostRetrieveDto> hotTopics = posts.stream()
                .sorted(Comparator.comparing(Post::getLikeCount).reversed())
                .filter(p-> p.getLikeCount()>=1&& p.getCreatedAt().isAfter(LocalDateTime.now(ZoneId.of("Asia/Seoul")).minusDays(7)))
                .map(p->new PostRetrieveDto(p))
                .limit(6)
                .collect(Collectors.toList());

        
        List<PostRetrieveDto> questionPosts = posts.stream()
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .filter(p ->p.getBoardType()==BoardType.QUESTION)
                .map(p->new PostRetrieveDto(p))
                .limit(3)
                .collect(Collectors.toList());

        
        List<PostRetrieveDto> managementPosts = posts.stream()
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .filter(p->p.getBoardType()==BoardType.MANAGEMENT)
                .map(p->new PostRetrieveDto(p))
                .limit(3)
                .collect(Collectors.toList());


        return new ResultForCommunity(hotTopics, questionPosts, managementPosts);

    }
    @Operation(summary = "get all posts", description = "게시글 전체조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "Ok", content = @Content(schema = @Schema(implementation = PostRetrieveDto.class))),
            @ApiResponse(responseCode = "400",description = "BAD REQUEST", content = @Content(schema = @Schema(implementation =Error400.class ))),
            @ApiResponse(responseCode = "404",description = "NOT FOUND", content = @Content(schema = @Schema(implementation =Error404.class ))),
            @ApiResponse(responseCode = "500",description = "INTERNAL SERVER ERROR", content = @Content(schema = @Schema(implementation =Error500.class )))
    })
    @GetMapping("/posts")
    public ResultForPaging showPosts(@RequestParam(value="type",defaultValue = "all") String type
            ,@RequestParam(value = "page",defaultValue = "1") Integer page
            ,@RequestParam(value = "tag",defaultValue = "all") String code){

        BoardType boardType=BoardType.valueOf(type.toUpperCase());
        TagType tagType = TagType.valueOf(code.toUpperCase());
        Page<Post> findPosts;

        findPosts = postService.findPostWithPaging(page-1,boardType,tagType);

        List<Post> posts = findPosts.getContent();
        int lastPageNum = findPosts.getTotalPages();
        int current = page;
        int min = 1+current/10*10;
        int max =10+current/10*10;
        if(max>=lastPageNum) max = lastPageNum;
        List<PostRetrieveDto> result = posts.stream()
                .map(p -> new PostRetrieveDto(p))
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
    @PostMapping("/posts")
    public CreatePostResponse createPost(@RequestBody @Valid CreatePostRequest request) {
        JwtMemberDetails principal = (JwtMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = principal.getUsername();
        Member findMember = memberService.findByEmail(email);
        Post post = new Post(request.getTitle(),request.getContent(),request.getBoardType(),request.getTagType(),LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        post.setOwner(findMember);
        for (String  s : request.getImageUrls()) {
            Attachment attachment = new Attachment(s);
            post.addAttachments(attachment);
        }
        postService.save(post);
        return new CreatePostResponse(post);
    }

    @Operation(summary = "get specific post", description = "게시글 단건조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "Ok"),
            @ApiResponse(responseCode = "400",description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404",description = "NOT FOUND"),
            @ApiResponse(responseCode = "500",description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/posts/{id}")
    public PostDetailRetrieveDto showPost(@PathVariable("id") Long id) {

        JwtMemberDetails principal = (JwtMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = principal.getUsername();
        Member findMember = memberService.findByEmail(email);
        Post post = postService.findPostFetchById(id);
        postService.addView(post);

        return new PostDetailRetrieveDto(post,findMember);
    }


    @Operation(summary = "update post", description = "게시글 수정")
    @PatchMapping("/posts/{id}")
    public UpdatePostResponse updatePost(@PathVariable("id") Long id, @RequestBody @Valid UpdatePostRequest request){

        Post post = postService.findPostById(id);
        postService.updatePost(post, request);

       return new UpdatePostResponse(post);
    }
    @Operation(summary = "delete post", description = "게시글 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "204",description = "Ok"),
            @ApiResponse(responseCode = "400",description = "BAD REQUEST", content = @Content(schema = @Schema(implementation = Error400.class ))),
            @ApiResponse(responseCode = "404",description = "NOT FOUND", content = @Content(schema = @Schema(implementation =Error404.class ))),
            @ApiResponse(responseCode = "500",description = "INTERNAL SERVER ERROR", content = @Content(schema = @Schema(implementation = Error500.class )))
    })
    @DeleteMapping("/posts/{id}")
    public HttpStatus deletePost(@PathVariable("id") Long id)
    {
        Post post = postService.findPostById(id);
        postService.deletePost(post);
        return HttpStatus.NO_CONTENT;
    }

    @Operation(summary = "likes to post", description = "게시글 좋아요")
    @PostMapping("/posts/{postId}/likes")
    public AddLikeDto likeToPost(@PathVariable("postId") Long id){

        Post post = postService.findOneByIdWithLikes(id);
        JwtMemberDetails principal = (JwtMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = principal.getUsername();
        Member findMember = memberService.findByEmail(email);

        List<PostLike> postLikeList = post.getPostLikeList();
        for (PostLike like : postLikeList) {
            if(like.getMember().getId()==findMember.getId()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"You already liked it");
            }
        }
        PostLike postLike = new PostLike();
        postService.addLike(findMember,post,postLike);
        return new AddLikeDto(post.getId(),post.getLikeCount());
    }

    @Operation(summary = "cancel likes to post", description = "게시글 좋아요 취소")
    @DeleteMapping("/posts/{postId}/likes")
    public AddLikeDto deleteLike(@PathVariable("postId")Long id){

        Post post = postService.findOneByIdWithLikes(id);

        JwtMemberDetails principal = (JwtMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = principal.getUsername();
        Member findMember = memberService.findByEmail(email);

        for (PostLike like : post.getPostLikeList()) {
            if(like.getMember().getId()==findMember.getId()){
                postService.deleteLike(findMember,post,like);
                break;
            }
        }
        return new AddLikeDto(post.getId(),post.getLikeCount());
    }

    @Operation(summary = "scrap post", description = "게시글 스크랩")
    @PostMapping("/posts/{postId}/scraps")
    public PostScrapDto addScrap(@PathVariable("postId")Long postId){
        Post post = postService.findOneByIdWithScrap(postId);

        JwtMemberDetails principal = (JwtMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email =  principal.getUsername();
        Member member = memberService.findByEmail(email);

        Scrap scrap = new Scrap();
        List<Scrap> scraps = post.getScrapList();
        for (Scrap scrap1 : scraps) {
            if(scrap1.getMember().getId()==member.getId()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"You already scraped it");
            }
        }
        postService.scrapPost(member,post,scrap);

        return new PostScrapDto(post);
    }
    @Operation(summary = "Delete Scrap", description = "게시글 스크랩 취소")
    @DeleteMapping("posts/{postId}/scraps")
    public PostScrapDto deleteScrap(@PathVariable("postId") Long postId){
        Post post = postService.findOneByIdWithScrap(postId);


        JwtMemberDetails principal = (JwtMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email =  principal.getUsername();
        Member member = memberService.findByEmail(email);

        for(Scrap scrap : post.getScrapList()){
            if(scrap.getMember().getId()==member.getId()){
                postService.deleteScrap(member,post,scrap);
                break;
            }
        }
        return new PostScrapDto(post);

    }
    @Operation(summary = "search posts", description = "게시글 검색")
    @GetMapping("/posts/search")
    public ResultForSearch searchPostsByKeyword(@RequestParam(value = "keyword") String keyword,
                                                @RequestParam(value="page",defaultValue = "1") Integer page){
        Page<Post> searchedPosts = postService.searchPostsWithKeyword(page-1,keyword);
        long numOfTotalPosts = searchedPosts.getTotalElements();
        List<Post> posts = searchedPosts.getContent();
        int lastPageNum = searchedPosts.getTotalPages();
        int current = page;
        int min = 1+current/10*10;
        int max =10+current/10*10;
        if(max>=lastPageNum) max = lastPageNum;
        List<PostRetrieveDto> result = posts.stream()
                .map(p -> new PostRetrieveDto(p))
                .collect(Collectors.toList());

        return new ResultForSearch(result,min,max,current,numOfTotalPosts);

    }
    @Operation(summary = "get hot-topic posts", description = "핫토픽 조회")
    @GetMapping("/posts/hot-topics")
    public ResultForList getHotTopics(){

        List<Post> posts = postService.retrieveHotTopicsOfWeek();
        List<PostRetrieveDto> result = posts.stream()
                .map(p->new PostRetrieveDto(p)).collect(Collectors.toList());

        return new ResultForList(result);
    }
    @Operation(summary = "get most -scraped posts", description = "가장 많이 스크랩된 게시글들 조회")
    @GetMapping("/posts/most-scraped")
    public ResultForList getMostScraped(){

        List<Post> posts = postService.retrieveMostScrapedOfWeek();
        List<PostRetrieveDto> result = posts.stream()
                .map(p->new PostRetrieveDto(p)).collect(Collectors.toList());

        return new ResultForList(result);
    }

    @GetMapping("/posts/recommended-posts")
    public ResultForList getRecommendedPosts()
    {
        Integer std = 1;
        List<PostRetrieveDto> result = postService.findCandidatesOfRecommendPost(std)
                .stream()
                .map(p->new PostRetrieveDto(p,std))
                .collect(Collectors.toList());

        return new ResultForList(result);
    }

}