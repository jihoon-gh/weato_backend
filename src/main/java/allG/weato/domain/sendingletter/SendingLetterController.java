package allG.weato.domain.sendingletter;

import allG.weato.domain.sendingletter.dto.create.CreateSendingLetterRequestDto;
import allG.weato.domain.sendingletter.dto.create.CreateSendingLetterResultDto;
import allG.weato.domain.sendingletter.dto.retrieve.RetrieveSendingLettersDto;
import allG.weato.domain.sendingletter.dto.retrieve.SendingLetterDetailDto;
import allG.weato.domain.sendingletter.dto.update.UpdateSendingLetterRequestDto;
import allG.weato.domain.sendingletter.dto.update.UpdateSendingLetterResponseDto;
import allG.weato.domain.sendingletter.entities.SendingLetter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SendingLetterController {

    private final SendingLetterService sendingLetterService;

    @GetMapping("/sendingletters")
    public List<RetrieveSendingLettersDto> getLetters(){

        List<RetrieveSendingLettersDto> result = sendingLetterService.checkLettersofThisWeek()
                .stream()
                .map(sl->new RetrieveSendingLettersDto(sl)).collect(Collectors.toList());

        return result;
    }

    @GetMapping("/sendingletters/{id}")
    public SendingLetterDetailDto getOneSendingLetter(@PathVariable("id")Long id){
        SendingLetter sl = sendingLetterService.findSendingLetterById(id);
        return new SendingLetterDetailDto(sl);
    }

    @PostMapping("/sendingletters")
    public CreateSendingLetterResultDto createSendingLetter(
            @RequestBody @Valid CreateSendingLetterRequestDto request){
        SendingLetter sendingLetter = new SendingLetter(request.getTitle(),request.getAuthor(), request.getContent(), request.getTagType());
        sendingLetterService.saveSendingLetter(sendingLetter);
        return new CreateSendingLetterResultDto(sendingLetter);
    }

    @PatchMapping("/sendingletters/{id}")
    public UpdateSendingLetterResponseDto updateSendingLetter(@RequestBody @Valid UpdateSendingLetterRequestDto request
            ,@PathVariable("id")Long id){

        SendingLetter sl = sendingLetterService.findSendingLetterById(id);
        sendingLetterService.updateSendingLetter(sl,request);
        return new UpdateSendingLetterResponseDto(sl);
    }

    @DeleteMapping("/sendingletters/{id}")
    public HttpStatus deleteSendingLetter(@PathVariable("id")Long id){

        SendingLetter sl = sendingLetterService.findSendingLetterById(id);
        sendingLetterService.deleteSendingLetter(sl);
        return HttpStatus.NO_CONTENT;

    }





}
