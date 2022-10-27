package hexlet.code.controller;

import hexlet.code.dto.StatusDto;
import hexlet.code.model.Status;
import hexlet.code.repository.StatusRepository;
import hexlet.code.service.StatusService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.CREATED;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statuses")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @Autowired
    private StatusRepository statusRepository;

    @PostMapping("")
    @ResponseStatus(CREATED)
    public Status createStatus(@RequestBody StatusDto statusDto) {
        return statusService.createNewStatus(statusDto);
    }

    @GetMapping("")
    public List<Status> getStatuses() {
        return statusRepository.findAll();
    }

    @GetMapping("{id}")
    public Status getStatus(@PathVariable() Long id) {
        return statusRepository.findById(id).get();
    }

    @PutMapping("{id}")
    public Status updateStatus(@PathVariable() Long id, @RequestBody StatusDto stausDto) {
        Status status = statusRepository.findById(id).get();
        return statusService.updateStatus(status, stausDto);
    }

    @DeleteMapping("{id}")
    public void deleteStatus(@PathVariable() Long id) {
        Status status = statusRepository.findById(id).get();
        statusRepository.delete(status);
    }

}
