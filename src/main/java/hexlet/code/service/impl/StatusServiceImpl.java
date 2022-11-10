package hexlet.code.service.impl;

import hexlet.code.dto.StatusDto;
import hexlet.code.model.Status;
import hexlet.code.repository.StatusRepository;
import hexlet.code.service.StatusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;

    @Override
    public Status createNewStatus(StatusDto statusDto) {
        Status status = fromDto(statusDto);

        return statusRepository.save(status);
    }

    @Override
    public Status updateStatus(Long id, StatusDto statusDto) {
        Status status = statusRepository.findById(id).get();
        merge(status, statusDto);

        return statusRepository.save(status);
    }

    private void merge(Status status, StatusDto statusDto) {
        Status newStatus = fromDto(statusDto);
        status.setName(newStatus.getName());
    }

    private Status fromDto(StatusDto statusDto) {
        return Status.builder()
                .name(statusDto.getName())
                .build();
    }
}
