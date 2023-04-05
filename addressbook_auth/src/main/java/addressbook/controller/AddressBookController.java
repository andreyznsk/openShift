package addressbook.controller;


import addressbook.dto.RequestLog;
import addressbook.service.RepoUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AddressBookController {

    private final RepoUser repoUser;

    @PostMapping("auth")
    public Boolean getAddressBookById(@RequestBody RequestLog rq) {


        log.info("user requested with rq: " + rq);

        return repoUser.checkByLogin(rq.getLogin());

    }

}
