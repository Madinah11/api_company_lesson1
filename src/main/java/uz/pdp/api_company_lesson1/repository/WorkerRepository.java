package uz.pdp.api_company_lesson1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.api_company_lesson1.entity.Department;
import uz.pdp.api_company_lesson1.entity.Worker;

public interface WorkerRepository extends JpaRepository<Worker,Integer> {

   boolean existsByPhoneNumber(String phoneNumber);
}
