package hexlet.code.service;

import hexlet.code.dto.StatusDto;
import hexlet.code.model.Status;

public interface StatusService {

    public Status createNewStatus(StatusDto statusDto);

    public Status updateStatus(Long id, StatusDto statusDto);
}
