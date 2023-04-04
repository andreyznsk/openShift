package addressbook.repository;

import addressbook.model.AddressBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressBookRepository extends JpaRepository<AddressBook, Long> {

    @Override
    List<AddressBook> findAll();

    Page<AddressBook> findAll(Pageable pageable);

    Optional<AddressBook> findById(Long id);


}
