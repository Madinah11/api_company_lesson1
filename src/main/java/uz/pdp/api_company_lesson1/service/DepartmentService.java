package uz.pdp.api_company_lesson1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.api_company_lesson1.entity.Address;
import uz.pdp.api_company_lesson1.entity.Company;
import uz.pdp.api_company_lesson1.entity.Department;
import uz.pdp.api_company_lesson1.payload.ApiResponse;
import uz.pdp.api_company_lesson1.payload.CompanyDto;
import uz.pdp.api_company_lesson1.payload.DepartmentDto;
import uz.pdp.api_company_lesson1.repository.AddressRepository;
import uz.pdp.api_company_lesson1.repository.CompanyRepository;
import uz.pdp.api_company_lesson1.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    CompanyRepository  companyRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    /**
     * Bo'lim qo'shadigan metod
     * @return ApiResponse
     */
    public ApiResponse add(DepartmentDto departmentDto) {
        boolean existsByNameAndCompanyId = departmentRepository.existsByNameAndCompanyId(departmentDto.getName(), departmentDto.getCompanyId());
        if (existsByNameAndCompanyId)
            return new ApiResponse("Bunday bo'lim allaqachon mavjud", false);
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent())
            return new ApiResponse("Bunday Companiya topilmadi", false);
        Company company = optionalCompany.get();
        Department department=new Department(null,departmentDto.getName(),company);
        departmentRepository.save(department);
        return new ApiResponse("Yangi Bo'lim saqlandi", true);
    }

    /**
     * Bo'lim listini qaytaradigan metod
     * @return List<Department>
     */
    public List<Department> get() {
        List<Department> departmentList = departmentRepository.findAll();
        return departmentList;
    }

    /**
     * Bo'lim  ID si bo'yicha qaytaradigan metod
     * @param id Integer
     * @return Bo'lim
     * Bo'lim  bolmasa null qaytadi
     */
    public Department getByID(Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment.orElse(null);
    }

    /**
     * Bo'lim  ID si bo'yicha udalit qiladigan metod
     * @param id Integer
     * @return ApiResponse
     */
    public ApiResponse delete(Integer id) {
        try {
            departmentRepository.deleteById(id);
            return new ApiResponse("Bo'lim  o'chirildi", true);
        } catch (Exception e) {
            return new ApiResponse("Bo'lim  topilmadi", false);
        }
    }

    /**
     * Bo'limni ID si bo'yicha tahrirlaydigan metod
     * @param id      Integer
     * @param departmentDto DepartmentDto
     * @return ApiResponse
     */
    public ApiResponse edit(Integer id, DepartmentDto departmentDto) {
        boolean existsByNameAndCompanyId = departmentRepository.existsByNameAndCompanyId(departmentDto.getName(), departmentDto.getCompanyId());
        if (existsByNameAndCompanyId)
            return new ApiResponse("Bunday bo'lim allaqachon mavjud", false);
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (!optionalDepartment.isPresent())
            return new ApiResponse("Bunday Bo'lim topilmadi", false);
        Department editingDepartment = optionalDepartment.get();
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent())
            return new ApiResponse("Bunday Companiya topilmadi", false);
        Company company = optionalCompany.get();
        editingDepartment.setName(departmentDto.getName());
        editingDepartment.setCompany(company);
        return new ApiResponse("Bo'lim tahrirlandi",true);
    }
}
