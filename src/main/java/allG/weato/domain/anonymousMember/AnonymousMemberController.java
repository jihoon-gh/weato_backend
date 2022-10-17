package allG.weato.domain.anonymousMember;

import allG.weato.domain.anonymousMember.dtos.create.CreateAnonymousMemberResponse;
import allG.weato.domain.anonymousMember.dtos.retrieve.DetailRetrieveAnonymousMember;
import allG.weato.domain.anonymousMember.dtos.retrieve.RetrieveAnonymousMemberResponse;
import allG.weato.domain.anonymousMember.dtos.update.UpdateAnonymousMemberRequest;
import allG.weato.domain.anonymousMember.dtos.update.UpdateAnonymousMemberResponse;
import allG.weato.domain.anonymousMember.entities.AnonymousMember;
import allG.weato.domain.anonymousMember.dtos.create.CreateAnonymousMemberRequest;
import allG.weato.dto.ResultForList;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AnonymousMemberController {

    private final AnonymousMemberService amService;

    @PostMapping("/landing")
    public CreateAnonymousMemberResponse createAnonymousMember(@RequestBody @Valid CreateAnonymousMemberRequest request){

        AnonymousMember am = new AnonymousMember(request);
        amService.saveAnonymousMember(am);

        return new CreateAnonymousMemberResponse(am);
    }

    @PatchMapping("/landing/{id}")
    public UpdateAnonymousMemberResponse updateAnonymousMember(@PathVariable("id")Long id,
                                                               @RequestBody @Valid UpdateAnonymousMemberRequest request){

        AnonymousMember am = amService.findById(id);
        amService.updateAm(request,am);

        return new UpdateAnonymousMemberResponse(am);
    }

    @GetMapping("/landing")
    public ResultForList getAllAm(){
        List<AnonymousMember> allAm = amService.findAllAm();

        List<RetrieveAnonymousMemberResponse> result = allAm.stream()
                .map(a -> new RetrieveAnonymousMemberResponse(a))
                .collect(Collectors.toList());
        return new ResultForList(result);
    }

    @GetMapping("/landing/{id}")
    public DetailRetrieveAnonymousMember getDetailAm(@PathVariable("id")Long id){
        AnonymousMember am = amService.findById(id);

        return new DetailRetrieveAnonymousMember(am);
    }

}

