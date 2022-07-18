package allG.weato.controller;

import allG.weato.config.auth.dto.SessionMember;
import allG.weato.domain.Comment;
import allG.weato.domain.CommentLike;
import allG.weato.domain.Member;
import allG.weato.domain.Post;
import allG.weato.dto.AddLikeDto;
import allG.weato.dto.comment.create.CreateCommentRequest;
import allG.weato.dto.comment.create.CreateCommentResponse;
import allG.weato.dto.comment.update.UpdatedCommentDto;
import allG.weato.service.CommentService;
import allG.weato.service.MemberService;
import allG.weato.service.PostService;
import allG.weato.validation.CommonErrorCode;
import allG.weato.validation.ErrorCode;
import allG.weato.validation.RestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentsController {
    private final PostService postService;
    private final MemberService memberService;
    private final HttpSession httpSession;

    private final CommentService commentService;

    @PostMapping("/api/post/{id}/comments")
    public CreateCommentResponse addComment(@PathVariable("id") Long id, @RequestBody @Valid CreateCommentRequest request)
    {
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Member findMember=memberService.findByEmail(member.getEmail());
        Post post = postService.findPostById(id);
        if(post==null) throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);
        Comment comment = new Comment();
        comment.changeContent(request.getContent());
        postService.addComment(post,comment);
        memberService.addComment(findMember,comment);
        return new CreateCommentResponse(comment);
    }

    @PatchMapping("/api/post/{postId}/comments/{commentId}")
    public UpdatedCommentDto updateComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId,String content){
        Comment comment = commentService.findCommentById(commentId);
        if(comment==null) throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);
        commentService.updateComment(commentId,content);
        return new UpdatedCommentDto(comment);
    }

    @DeleteMapping("/api/post/{postId}/comments/{commentId}")
    public void deleteComment(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId){
        Comment comment = commentService.findCommentById(commentId);
        if(comment==null) throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);
        commentService.deleteComment(comment);
    }

    @PostMapping("/api/posts/{postId}/comments/{commentId}/likes")
    public AddLikeDto addLikes(@PathVariable("postId") Long postId,
                               @PathVariable("commentId") Long commentId){

        Comment findComment = commentService.findCommentById(commentId);
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Member findMember = memberService.findByEmail(member.getEmail());
        CommentLike commentLike = new CommentLike();
        List<CommentLike> commentLikes = findComment.getCommentLikeList();
        for (CommentLike like : commentLikes) {
            if(like.getMember().getId()==findMember.getId()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You already liked it");
            }
        }
        commentService.addCommentLike(findMember,findComment,commentLike);
        return new AddLikeDto(findComment.getId(),findComment.getLikeCount());
    }

    @DeleteMapping("/api/posts/{postId}/comments/{commentId}/likes")
    public void deleteCommentLike(@PathVariable("postId") Long postId,
                                  @PathVariable("commentId") Long commentId){
        Comment comment = commentService.findCommentById(commentId);
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Member findMember = memberService.findByEmail(member.getEmail());
        for(CommentLike likes: comment.getCommentLikeList()){
            if(likes.getMember().getId()== findMember.getId()){
//                commentService.deleteCommentLike(co);
            }
        }

    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        int count;
        private T data;
    }
}
