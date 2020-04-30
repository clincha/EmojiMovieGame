package uk.co.emg.service;

import org.springframework.stereotype.Service;
import uk.co.emg.entity.Mutation;
import uk.co.emg.repository.MutationRepository;

@Service
public class MutationService {

    private final MutationRepository mutationRepository;

    public MutationService(MutationRepository mutationRepository) {
        this.mutationRepository = mutationRepository;
    }

    public void save(Mutation mutation) {
        mutationRepository.save(mutation);
    }
}
