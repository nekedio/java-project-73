package hexlet.code.controller;

import hexlet.code.dto.StatusDto;
import hexlet.code.model.Status;
import hexlet.code.repository.StatusRepository;
import hexlet.code.service.StatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
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
@SecurityRequirement(name = "javainuseapi")
@AllArgsConstructor
public class StatusController {

    @Autowired
    private final StatusService statusService;

    @Autowired
    private final StatusRepository statusRepository;

    @Operation(summary = "Create new status")
    @ApiResponse(
            responseCode = "201",
            description = "Status created"
    )
    @PostMapping("")
    @ResponseStatus(CREATED)
    public Status createStatus(@RequestBody @Valid StatusDto statusDto) {
        return statusService.createNewStatus(statusDto);
    }

    @Operation(summary = "Get list of all statuses")
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "List of all statuses"
        )
    })
    @GetMapping("")
    public List<Status> getStatuses() {
        return statusRepository.findAll();
    }

    @Operation(summary = "Get specific status by his id")
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Status found"
        ),
        @ApiResponse(
                responseCode = "404",
                description = "User not found"
        )
    })
    @GetMapping("{id}")
    public Status getStatus(
            @Parameter(description = "Id of status to be updated")
            @PathVariable() Long id
    ) {
        return statusRepository.findById(id).get();
    }

    @Operation(summary = "Update specific status by his id")
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Updated status"
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Status not found"
        )
    })
    @PutMapping("{id}")
    public Status updateStatus(
            @Parameter(description = "Id of status to be updated")
            @PathVariable() Long id,
            @Parameter(description = "Status data to update")
            @RequestBody @Valid StatusDto stausDto
    ) {
        return statusService.updateStatus(id, stausDto);
    }

    @Operation(summary = "Delete specific status by his id")
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Deleted status"
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Status not found"
        )
    })
    @DeleteMapping("{id}")
    public void deleteStatus(
            @Parameter(description = "Id of status to be deleted")
            @PathVariable() Long id
    ) {
        statusRepository.deleteById(id);
    }

}
