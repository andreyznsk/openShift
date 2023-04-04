package addressbook.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AddressBookController {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @PostMapping("calcAge")
    public Integer getAddressBookById(@RequestBody Timestamp birthDate) {


        log.info("age function requested with date: " + birthDate);

        return Period.between(birthDate.toLocalDateTime().toLocalDate(), LocalDate.now()).getYears();
    }


}
