package addressbook.controller;


import addressbook.dto.AddressBookDto;
import addressbook.model.AddressBook;
import addressbook.repository.AddressBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/cache")
@RequiredArgsConstructor
public class AddressBookController {

    private final AddressBookRepository addressBookRepository;

    @GetMapping("addressbook/{id}")
    public ResponseEntity<AddressBookDto> getAddressBookById(@PathVariable Long id) {
        log.info("get rq with id:{}", id);
        Optional<AddressBook> byId = addressBookRepository.findById(id);
        return byId
                .map(addressBook -> {
                    AddressBookDto addressBookDto = getAddressBookDto(addressBook);
                    return new ResponseEntity<>(addressBookDto, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(null, HttpStatus.OK));
    }

    @GetMapping("addressbooks")
    public List<AddressBookDto> getAddressBooks() {
        log.info("get all");
        return addressBookRepository.findAll().stream().map(this::getAddressBookDto).collect(Collectors.toList());
    }

    @PostMapping("addressbook")
    public AddressBookDto save(@RequestBody AddressBookDto addressBookDto) {
        log.info("Save: {}", addressBookDto);
        return getAddressBookDto(addressBookRepository.save(getAddressBook(addressBookDto)));
    }

    @DeleteMapping("addressbook/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(addressBookRepository.deleteAllById(id), HttpStatus.OK);
    }


    private AddressBookDto getAddressBookDto(AddressBook addressBook) {
        AddressBookDto addressBookDto = new AddressBookDto();
        addressBookDto.setId(addressBook.getId());
        addressBookDto.setBirthday(addressBook.getBirthday());
        addressBookDto.setLast_name(addressBook.getLast_name());
        addressBookDto.setFirst_name(addressBook.getFirst_name());
        addressBookDto.setPhone(addressBook.getPhone());
        return addressBookDto;
    }

    private AddressBook getAddressBook(AddressBookDto addressBookDto) {
        AddressBook addressBook = new AddressBook();
        addressBook.setId(addressBook.getId());
        addressBook.setBirthday(addressBookDto.getBirthday());
        addressBook.setLast_name(addressBookDto.getLast_name());
        addressBook.setFirst_name(addressBookDto.getFirst_name());
        addressBook.setPhone(addressBookDto.getPhone());
        return addressBook;
    }

}
