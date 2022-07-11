package allG.weato.controller;

import allG.weato.config.auth.dto.SessionMember;
import allG.weato.domain.Comment;
import allG.weato.domain.Member;
import allG.weato.domain.Post;
import allG.weato.dto.create.CreateCommentRequest;
import allG.weato.dto.create.CreateCommentResponse;
import allG.weato.service.MemberService;
import allG.weato.service.PostService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CommentsController {
    private final PostService postService;
    private final MemberService memberService;
    private final HttpSession httpSession;

    @PostMapping("/api/post/{id}/comments")
    @ResponseBody
    public CreateCommentResponse addComment(@PathVariable("id") Long id, @RequestBody @Valid CreateCommentRequest request)
    {
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Member findMember=memberService.findByEmail(member.getEmail());
        Post post = postService.findPostById(id);
        Comment comment = new Comment();
        comment.changeContent(request.getContent());
        post.addComment(comment);
        findMember.addComment(comment);
        return new CreateCommentResponse(comment);
    }



    @Data
    @AllArgsConstructor
    static class Result<T>{
        int count;
        private T data;
    }
}
