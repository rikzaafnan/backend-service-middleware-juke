package api_public_service.service;

import java.util.List;

import api_public_service.entity.Pet;

public interface PetService {
    List<Pet> getAllPets(String queryParamStatus);
}
