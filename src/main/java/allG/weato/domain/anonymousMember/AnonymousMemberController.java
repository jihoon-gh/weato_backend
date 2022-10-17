package allG.weato.domain.anonymousMember;

import allG.weato.domain.anonymousMember.dtos.create.CreateAnonymousMemberResponse;
import allG.weato.domain.anonymousMember.dtos.retrieve.DetailRetrieveAnonymousMember;
import allG.weato.domain.anonymousMember.dtos.retrieve.RetrieveAnonymousMemberResponse;
import allG.weato.domain.anonymousMember.dtos.update.UpdateAnonymousMemberRequest;
import allG.weato.domain.anonymousMember.dtos.update.UpdateAnonymousMemberResponse;
import allG.weato.domain.anonymousMember.entities.AnonymousMember;
import allG.weato.domain.anonymousMember.dtos.create.CreateAnonymousMemberRequest;
import allG.weato.dto.ResultForList;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "create AnonymousMember", description = "뉴스레터 전용 익명유저 생성")
    @PostMapping("/landing")
    public CreateAnonymousMemberResponse createAnonymousMember(@RequestBody @Valid CreateAnonymousMemberRequest request){

        AnonymousMember am = new AnonymousMember(request);
        amService.saveAnonymousMember(am);

        return new CreateAnonymousMemberResponse(am);
    }

    @Operation(summary = "update AnonymousMember", description = "익명 유저 정보 수정")
    @PatchMapping("/landing/{id}")
    public UpdateAnonymousMemberResponse updateAnonymousMember(@PathVariable("id")Long id,
                                                               @RequestBody @Valid UpdateAnonymousMemberRequest request){

        AnonymousMember am = amService.findById(id);
        amService.updateAm(request,am);

        return new UpdateAnonymousMemberResponse(am);
    }

    @Operation(summary = "Retrieve all AnonymousMembers", description = "익명 유저 전체 조회")
    @GetMapping("/landing")
    public ResultForList getAllAm(){
        List<AnonymousMember> allAm = amService.findAllAm();

        List<RetrieveAnonymousMemberResponse> result = allAm.stream()
                .map(a -> new RetrieveAnonymousMemberResponse(a))
                .collect(Collectors.toList());
        return new ResultForList(result);
    }

    @Operation(summary = "Retrieve specific AnonymousMember", description = "id를 통한 익명유저 조회")
    @GetMapping("/landing/{id}")
    public DetailRetrieveAnonymousMember getDetailAm(@PathVariable("id")Long id){
        AnonymousMember am = amService.findById(id);

        return new DetailRetrieveAnonymousMember(am);
    }

}

