package allG.weato.controller;

import allG.weato.config.auth.dto.SessionMember;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final HttpSession httpSession;

    @GetMapping("/member")
    public SessionMember loginUserApi(Model model){
        SessionMember member = (SessionMember) httpSession.getAttribute("member");

        if(member!=null){
            model.addAttribute("memberName",member.getName());
        }
        return member;
    }


    @GetMapping("/")
    public String login1(Model model){
        SessionMember member = (SessionMember) httpSession.getAttribute("member");

        if(member!=null){
            model.addAttribute("memberName",member.getName());
        }
        return "home";
    }



}
