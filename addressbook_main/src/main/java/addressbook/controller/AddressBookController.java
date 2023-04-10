package addressbook.controller;


import addressbook.dto.AddressBookAgeDto;
import addressbook.dto.AddressBookDto;
import addressbook.service.AddressBookCacheService;
import addressbook.service.AgeFunctionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AddressBookController {

    private final AddressBookCacheService addressBookCacheService;
    private final AgeFunctionService ageFunctionService;

    @GetMapping("addressbook/{id}")
    public ResponseEntity<AddressBookDto> getAddressBookById(@PathVariable Long id) {
        log.info("get rq with id:{}", id);
        Optional<AddressBookDto> byId = addressBookCacheService.findById(id);
        return byId
                .map(addressBook -> new ResponseEntity<>(addressBook, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("addressbooks")
    public List<AddressBookDto> getAddressBooks() {
        log.info("get all");
        return addressBookCacheService.findAll();
    }

    @GetMapping("addressbookAge/{id}")
    public ResponseEntity<AddressBookAgeDto> getAddressBookDtoById(@PathVariable Long id) {
        log.info("get rq with age, by id: {}", id);
        Optional<AddressBookDto> byId = addressBookCacheService.findById(id);

        return byId
                .map(addressBook ->
                        new ResponseEntity<>(
                                new AddressBookAgeDto(addressBook, getAge(addressBook.getBirthday())), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private int getAge(Timestamp birthday) {
        return ageFunctionService.calcAge(birthday);
    }

    @PostMapping("addressbook")
    public AddressBookDto save(@RequestBody AddressBookDto addressBook) {
        log.info("Save: {}", addressBook);
        return addressBookCacheService.save(addressBook);
    }

    @DeleteMapping("addressbook/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        boolean deleted = addressBookCacheService.delete(id);
        if (deleted) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


}
