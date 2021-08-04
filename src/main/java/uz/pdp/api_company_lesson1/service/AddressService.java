package uz.pdp.api_company_lesson1.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.api_company_lesson1.entity.Address;
import uz.pdp.api_company_lesson1.payload.ApiResponse;
import uz.pdp.api_company_lesson1.repository.AddressRepository;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;

    /**
     * Addressni qo'shadigan metod
     *
     * @return ApiResponse
     */
    public ApiResponse addAddress(Address address) {
        boolean existsByStreetAndHomeNumber = addressRepository.existsByStreetAndHomeNumber(address.getStreet(), address.getHomeNumber());
        if (existsByStreetAndHomeNumber)
            return new ApiResponse("Bunday address mavjud", false);
        addressRepository.save(address);
        return new ApiResponse("Address saqlandi", true);
    }

    /**
     * Address listini qaytaradigan metod
     * @return List<Address>
     */
    public List<Address> get() {
        List<Address> allAddresses = addressRepository.findAll();
        return allAddresses;
    }

    /**
     * Addressni ID si bo'yicha qaytaradigan metod
     *
     * @param id Integer
     * @return Address
     * Address bolmasa null qaytadi
     */
    public Address getByID(Integer id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        return optionalAddress.orElse(null);
    }

    /**
     * Addressni ID si bo'yicha udalit qiladigan metod
     *
     * @param id Integer
     * @return ApiResponse
     */
    public ApiResponse delete(Integer id) {
        try {
            addressRepository.deleteById(id);
            return new ApiResponse("Address o'chirildi", true);
        } catch (Exception e) {
            return new ApiResponse("Address topilmadi", false);
        }
    }

    /**
     * Addressni ID si bo'yicha tahrirlaydigan metod
     *
     * @param id      Integer
     * @param address Address
     * @return ApiResponse
     */
    public ApiResponse edit(Integer id, Address address) {
        boolean existsByStreetAndHomeNumber = addressRepository.existsByStreetAndHomeNumber(address.getStreet(), address.getHomeNumber());
        if (existsByStreetAndHomeNumber)
            return new ApiResponse("Bunday manzil allaqachon mavjud", false);
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (!optionalAddress.isPresent())
            return new ApiResponse("Bunday Id li Address topilmadi", false);
        Address editingAddress = optionalAddress.get();
        editingAddress.setStreet(address.getStreet());
        editingAddress.setHomeNumber(address.getHomeNumber());
        addressRepository.save(editingAddress);
        return new ApiResponse("Address o'zgartirildi", true);
    }
}
