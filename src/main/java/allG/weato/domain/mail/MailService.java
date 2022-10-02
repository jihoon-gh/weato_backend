package allG.weato.domain.mail;

import allG.weato.domain.mail.dto.SendingMailDto;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class MailService {

    private JavaMailSender javaMailSender;

    private static final String FROM_ADDRESS = "weato4u@gmail.com";

    public void mailSend(SendingMailDto sendingMailDto, int num){
        MimeMessage message = javaMailSender.createMimeMessage();
        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,true,"UTF-8");
            System.out.println("sending..");
            mimeMessageHelper.setFrom(FROM_ADDRESS);
            mimeMessageHelper.setTo(sendingMailDto.getAddress());
            mimeMessageHelper.setSubject("[WEATO] 뉴스레터 수신 이메일 인증 번호입니다.");
            mimeMessageHelper.setText("<h3><strong>WEATO 뉴스레터 수신 이메일 인증 메일입니다.</strong></h3><br>"+
               "<h2><strong>"+ num +"</strong></h2>"+" 을 입력해 주시기 바랍니다.",true);
            javaMailSender.send(message);
        }catch (MessagingException e){
            System.out.println("e.getMessage() = " + e.getMessage());
        }
    }
}
