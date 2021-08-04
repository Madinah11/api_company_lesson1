package uz.pdp.api_company_lesson1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.api_company_lesson1.entity.Address;
import uz.pdp.api_company_lesson1.entity.Company;
import uz.pdp.api_company_lesson1.payload.ApiResponse;
import uz.pdp.api_company_lesson1.payload.CompanyDto;
import uz.pdp.api_company_lesson1.repository.AddressRepository;
import uz.pdp.api_company_lesson1.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    CompanyRepository companyRepository;

    /**
     * Companiya qo'shadigan metod
     * @return ApiResponse
     */
    public ApiResponse add(CompanyDto companyDto) {
        boolean existsByCorpNameAndDirectorName = companyRepository.existsByCorpNameAndDirectorName(companyDto.getCorpName(), companyDto.getDirectorName());
        if (existsByCorpNameAndDirectorName)
            return new ApiResponse("Bunday companiya mavjud", false);
        Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddressId());
        if (!optionalAddress.isPresent())
            return new ApiResponse("Address topilmadi",false);
        Address address = optionalAddress.get();
        Company company=new Company(null,companyDto.getCorpName(),companyDto.getDirectorName(),address);
        companyRepository.save(company);
        return new ApiResponse("Companiya saqlandi", true);
    }

    /**
     * Companiya listini qaytaradigan metod
     * @return List<Company>
     */
    public List<Company> get() {
        List<Company> companyList = companyRepository.findAll();
        return companyList;
    }

    /**
     * Companiya ID si bo'yicha qaytaradigan metod
     * @param id Integer
     * @return Companiya
     * Companiya bolmasa null qaytadi
     */
    public Company getByID(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        return optionalCompany.orElse(null);
    }

    /**
     * Companiya ID si bo'yicha udalit qiladigan metod
     * @param id Integer
     * @return ApiResponse
     */
    public ApiResponse delete(Integer id) {
        try {
            companyRepository.deleteById(id);
            return new ApiResponse("Companiya o'chirildi", true);
        } catch (Exception e) {
            return new ApiResponse("Companiya topilmadi", false);
        }
    }

    /**
     * Companiyanung ID si bo'yicha tahrirlaydigan metod
     * @param id      Integer
     * @param companyDto CompanyDto
     * @return ApiResponse
     */
    public ApiResponse edit(Integer id, CompanyDto companyDto) {
        boolean existsByCorpNameAndDirectorName = companyRepository.existsByCorpNameAndDirectorName(companyDto.getCorpName(), companyDto.getDirectorName());
        if (existsByCorpNameAndDirectorName)
            return new ApiResponse("Bunday companiya mavjud", false);
        Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddressId());
        if (!optionalAddress.isPresent())
            return new ApiResponse("Address topilmadi",false);
        Address address = optionalAddress.get();
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (!optionalCompany.isPresent())
            return new ApiResponse("Companiya topilmadi",false);
        Company editingCompany = optionalCompany.get();
        editingCompany.setCorpName(companyDto.getCorpName());
        editingCompany.setDirectorName(companyDto.getDirectorName());
        editingCompany.setAddress(address);
        companyRepository.save(editingCompany);
        return new ApiResponse("Companiya tahrirlandi",true);


    }
}
