package addressbook.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressBookAgeDto {

    private Long id;

    private String first_name;

    private String last_name;

    private String phone;

    private Timestamp birthday;

    private Integer age;

    public AddressBookAgeDto(AddressBookDto addressBook, int age) {
        this.setId(addressBook.getId());
        this.setPhone(addressBook.getPhone());
        this.setFirst_name(addressBook.getFirst_name());
        this.setLast_name(addressBook.getLast_name());
        this.setBirthday(addressBook.getBirthday());
        this.age = age;

    }


}
