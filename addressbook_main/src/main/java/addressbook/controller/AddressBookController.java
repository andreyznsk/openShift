package addressbook.controller;


import addressbook.dto.AddressBookDto;
import addressbook.model.AddressBook;
import addressbook.repository.AddressBookRepository;
import addressbook.service.AgeFunctionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AddressBookController {

    private final AddressBookRepository addressBookRepository;
    private final AgeFunctionService ageFunctionService;

    @GetMapping("addressbook/{id}")
    public ResponseEntity<AddressBook> getAddressBookById(@PathVariable Long id) {
        log.info("get rq with id:{}", id);
        Optional<AddressBook> byId = addressBookRepository.findById(id);
        return byId
                .map(addressBook -> new ResponseEntity<>(addressBook, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("addressbooks")
    public List<AddressBook> getAddressBooks(@RequestParam("page") Optional<String> page,
                                             @RequestParam("size") Optional<String> size) {
        log.info("get all");
        PageRequest pageRequest = PageRequest.of(
                page.map(Integer::valueOf).orElse(0),
                size.map(Integer::valueOf).orElse(100));
        log.info("page:{}, size:{}", page, size);
         return addressBookRepository.findAll(pageRequest).stream().collect(Collectors.toList());
    }

    @GetMapping("addressbookAge/{id}")
    public  ResponseEntity<AddressBookDto> getAddressBookDtoById(@PathVariable Long id) {
        log.info("get rq with age, by id: {}", id);
        Optional<AddressBook> byId = addressBookRepository.findById(id);

        return byId
                .map(addressBook ->
                        new ResponseEntity<>(
                                new AddressBookDto(addressBook, getAge(addressBook.getBirthday())), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private int getAge(Timestamp birthday) {
        return ageFunctionService.calcAge(birthday);
    }

    @PostMapping("addressbook")
    public AddressBook save(@RequestBody AddressBook addressBook) {
        log.info("Save: {}", addressBook);
        return addressBookRepository.save(addressBook);
    }

    @DeleteMapping("addressbook/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        return addressBookRepository.findById(id)
                .map(this::deleteAddressBook)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private ResponseEntity<Void> deleteAddressBook(AddressBook addressBook) {
          addressBookRepository.delete(addressBook);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


}
