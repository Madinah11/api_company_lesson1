package uz.pdp.api_company_lesson1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.api_company_lesson1.entity.Company;

public interface CompanyRepository extends JpaRepository<Company,Integer> {

    boolean existsByCorpNameAndDirectorName(String corpName, String directorName);
}
