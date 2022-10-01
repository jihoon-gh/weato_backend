package allG.weato.domainTest.sendingLetters;

import allG.weato.domainTest.sendingLetters.dto.update.UpdateSendingLetterRequestDto;
import allG.weato.domainTest.sendingLetters.entities.SendingLetter;
import allG.weato.validation.CommonErrorCode;
import allG.weato.validation.RestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SendingLetterService {
    private final SendingLetterRepository sendingLetterRepository;

    @Transactional
    public SendingLetter saveSendingLetter(SendingLetter sendingLetter){
        return sendingLetterRepository.save(sendingLetter);
    }

    public List<SendingLetter> checkLettersofThisWeek(){
        PageRequest pageRequest = PageRequest.of(0,6);
        return sendingLetterRepository
                .findLettersOfThisWeek(LocalDateTime.now(ZoneId.of("Asia/Seoul")).minusDays(7),pageRequest)
                .getContent();
    }


    public SendingLetter findSendingLetterById(Long id) {
        return sendingLetterRepository.findById(id)
                .orElseGet(()->{throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);});
    }

    @Transactional
    public void deleteSendingLetter(SendingLetter sendingLetter){
        sendingLetterRepository.delete(sendingLetter);
    }

    @Transactional
    public void updateSendingLetter(SendingLetter sl, UpdateSendingLetterRequestDto request) {
        Boolean checker=false;
        if(request.getTitle()!=null){
            sl.changeTitle(request.getTitle());
            checker=true;
        }
        if(request.getContent()!=null){
            sl.changeContent(request.getContent());
            checker=true;
        }
        if(checker) sl.changeLocalDateTimeByNow();
    }
}
