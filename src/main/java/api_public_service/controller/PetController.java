package api_public_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api_public_service.entity.Pet;
import api_public_service.service.PetService;
import api_public_service.utils.ApiResponse;

@RestController
@RequestMapping("api/v1/pets")  // Base path for the controller
public class PetController {

    private final PetService petService;


    @Autowired  // Menginjeksi dependency PetService
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public ApiResponse getPets( 
        @RequestParam(value = "status", defaultValue = "available") String status
        ) {
        
        String queryParamsStatus = "available";
        // Validasi nilai status
        if (!status.equals("available") && !status.equals("pending") && !status.equals("sold")) {
            return new ApiResponse<>(400, "filter status only available, pendong and sold", null);
        }

        queryParamsStatus = status;
        List<Pet> pets = petService.getAllPets(status);
        return new ApiResponse<>(200, "success", pets);
    }
}
