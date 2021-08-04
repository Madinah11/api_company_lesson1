package uz.pdp.api_company_lesson1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.api_company_lesson1.entity.Address;
import uz.pdp.api_company_lesson1.entity.Company;
import uz.pdp.api_company_lesson1.entity.Department;
import uz.pdp.api_company_lesson1.entity.Worker;
import uz.pdp.api_company_lesson1.payload.ApiResponse;
import uz.pdp.api_company_lesson1.payload.DepartmentDto;
import uz.pdp.api_company_lesson1.payload.WorkerDto;
import uz.pdp.api_company_lesson1.repository.AddressRepository;
import uz.pdp.api_company_lesson1.repository.CompanyRepository;
import uz.pdp.api_company_lesson1.repository.DepartmentRepository;
import uz.pdp.api_company_lesson1.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    WorkerRepository workerRepository;

    /**
     * Xodim qo'shadigan metod
     * @return ApiResponse
     */
    public ApiResponse add(WorkerDto workerDto) {
        boolean existsByPhoneNumber = workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber());
        if (existsByPhoneNumber)
            return new ApiResponse("Bunday telefon raqamli xodim mavjud", false);
        Optional<Address> byIdAddress = addressRepository.findById(workerDto.getAddressId());
        if (!byIdAddress.isPresent())
            return new ApiResponse("Bunday address topilmadi", false);
        Address address = byIdAddress.get();
        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent())
            return new ApiResponse("Bunday bo'lim topilmadi", false);
        Department department1 = optionalDepartment.get();
        Worker worker=new Worker(null,workerDto.getFullName(),workerDto.getPhoneNumber(),address,department1);
        workerRepository.save(worker);
        return new ApiResponse("Yangi xodim saqlandi", true);
    }

    /**
     * Xodim listini qaytaradigan metod
     * @return List<Worker>
     */
    public List<Worker> get() {
        List<Worker> workerList = workerRepository.findAll();
        return workerList;
    }

    /**
     * Xodim ID si bo'yicha qaytaradigan metod
     * @param id Integer
     * @return Xodim
     * Xodim  bolmasa null qaytadi
     */
    public Worker getByID(Integer id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        return optionalWorker.orElse(null);
    }

    /**
     * Xodim  ID si bo'yicha udalit qiladigan metod
     * @param id Integer
     * @return ApiResponse
     */
    public ApiResponse delete(Integer id) {
        try {
            workerRepository.deleteById(id);
            return new ApiResponse("Xodim o'chirildi", true);
        } catch (Exception e) {
            return new ApiResponse("Xodim  topilmadi", false);
        }
    }

    /**
     * Xodimi ID si bo'yicha tahrirlaydigan metod
     * @param id      Integer
     * @param workerDto WorkerDto
     * @return ApiResponse
     */
    public ApiResponse edit(Integer id, WorkerDto workerDto) {
        boolean existsByPhoneNumber = workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber());
        if (existsByPhoneNumber)
            return new ApiResponse("Bunday telefon raqamli xodim mavjud", false);
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (!optionalWorker.isPresent())
            return new ApiResponse("Bunday xodim topilmadi", false);
        Worker editingWorker = optionalWorker.get();
        Optional<Address> byIdAddress = addressRepository.findById(workerDto.getAddressId());
        if (!byIdAddress.isPresent())
            return new ApiResponse("Bunday address topilmadi", false);
        Address address = byIdAddress.get();
        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent())
            return new ApiResponse("Bunday bo'lim topilmadi", false);
        Department department1 = optionalDepartment.get();
        editingWorker.setFullName(workerDto.getFullName());
        editingWorker.setPhoneNumber(workerDto.getPhoneNumber());
        editingWorker.setAddress(address);
        editingWorker.setDepartment(department1);
        workerRepository.save(editingWorker);
        return new ApiResponse("Xodim ma'lumotlari tahrirlandi",true);
    }
}
