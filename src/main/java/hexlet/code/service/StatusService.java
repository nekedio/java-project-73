package hexlet.code.service;

import hexlet.code.dto.StatusDto;
import hexlet.code.model.Status;
import hexlet.code.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    public Status createNewStatus(StatusDto statusDto) {
        Status newStatus = new Status();
        newStatus.setName(statusDto.getName());
        return statusRepository.save(newStatus);
    }

    public Status updateStatus(Status status, StatusDto statusDto) {
        status.setName(statusDto.getName());
        return statusRepository.save(status);
    }
}
