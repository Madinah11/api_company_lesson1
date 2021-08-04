package uz.pdp.api_company_lesson1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.api_company_lesson1.entity.Address;

public interface AddressRepository extends JpaRepository<Address,Integer> {
    boolean existsByStreetAndHomeNumber(String street, String homeNumber);
}
