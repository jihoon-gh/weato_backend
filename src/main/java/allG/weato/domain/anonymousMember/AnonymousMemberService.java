package allG.weato.domain.anonymousMember;

import allG.weato.domain.anonymousMember.dtos.update.UpdateAnonymousMemberRequest;
import allG.weato.domain.anonymousMember.entities.AnonymousMember;
import allG.weato.validation.CommonErrorCode;
import allG.weato.validation.RestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnonymousMemberService {

    private final AnonymousMemberRepository amRepository;

    @Transactional
    public void saveAnonymousMember(AnonymousMember am){
        amRepository.save(am);
    }

    public AnonymousMember findById(Long id){
        return amRepository.findById(id).orElseThrow(()->{
            throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);
        });
    }

    @Transactional
    public void updateAm(UpdateAnonymousMemberRequest request, AnonymousMember am){
        am.updateAnonymousMember(request);
    }

    public List<AnonymousMember> findAllAm(){
        return amRepository.findAll();
    }

    public Boolean checkDuplication(String newsletterEmail){
        List<String> existEmailList = amRepository.findAll()
                .stream()
                .map(a->a.getNewsletterEmail())
                .collect(Collectors.toList());
        if(existEmailList.contains(newsletterEmail)){
            return false;
        }
        return true;
    }


}
