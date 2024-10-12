package api_public_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import api_public_service.entity.Pet;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service  
public class PetServiceImpl implements PetService {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${CLIENT_ID}")
    private String clientID;

    @Value("${CLIENT_SECRET}")
    private String clientSecret;

    @Value("${DOMAIN_PET_SERVICE_URL}")
    private String domainPetServiceURL;

    @Value("${PET_SERVICE_PATH}")
    private String servicePetPath;

    @Override
    public List<Pet> getAllPets(String queryParamStatus) {

        List<Pet> pets = new ArrayList<>();

        if (clientID == null || clientSecret == null) {
            // log.error("Client ID OR Client Secret is empty ");
            pets = new ArrayList<>();
        }

        String url = domainPetServiceURL+servicePetPath+"/findByStatus?status="+queryParamStatus;

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            int statusCode = response.getStatusCodeValue();
            String body = response.getBody();

            if (statusCode == 200) {
                if (body == null || body.isEmpty()) {
                    pets = new ArrayList<>();
                } else {
                    // pets = Arrays.copyOf(stringArray, stringArray.length);
                    // Mengonversi JSON string ke List<Pet>
                    ObjectMapper objectMapper = new ObjectMapper();
                    pets = objectMapper.readValue(body, new TypeReference<List<Pet>>() {});
                }

            } else {
                // Tangani status lain jika diperlukan
                throw new RuntimeException("Failed to fetch data from third party. Status: " + statusCode);
            }

        } catch (Exception e) {
            // log.error("Error calling Pet Service API", e);
            return pets;  // Mengembalikan list kosong jika terjadi error
        }

        return pets;
    }
}