package uz.pdp.api_company_lesson1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.api_company_lesson1.entity.Company;
import uz.pdp.api_company_lesson1.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department,Integer> {

   boolean existsByNameAndCompanyId(String name, Integer company_id);
}
