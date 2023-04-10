package addressbook.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressBookDto {

    private Long id;
    private String first_name;
    private String last_name;
    private String phone;
    private Timestamp birthday;

}

