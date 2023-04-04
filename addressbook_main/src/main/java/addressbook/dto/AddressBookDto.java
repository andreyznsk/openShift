package addressbook.dto;

import addressbook.model.AddressBook;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class AddressBookDto extends AddressBook {
    private Integer age;

    public AddressBookDto (AddressBook addressBook, int age) {
        super();
        this.setId(addressBook.getId());
        this.setFirst_name(addressBook.getFirst_name());
        this.setLast_name(addressBook.getLast_name());
        this.setPhone(addressBook.getPhone());
        this.setBirthday(addressBook.getBirthday());
        this.setAge(age);
    }

}
