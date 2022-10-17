package allG.weato.domain.anonymousMember;

import allG.weato.domain.anonymousMember.dtos.update.UpdateAnonymousMemberRequest;
import allG.weato.domain.anonymousMember.entities.AnonymousMember;
import allG.weato.validation.CommonErrorCode;
import allG.weato.validation.RestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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


}
