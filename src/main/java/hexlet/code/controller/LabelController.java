package hexlet.code.controller;

import hexlet.code.dto.LabelDto;
import hexlet.code.model.Label;
import hexlet.code.repository.LabelRepository;
import hexlet.code.service.LabelService;
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
@RequestMapping("/api/labels")
@SecurityRequirement(name = "javainuseapi")
@AllArgsConstructor
public class LabelController {

    @Autowired
    private final LabelService labelService;

    @Autowired
    private final LabelRepository labelRepository;

    @Operation(summary = "Create new label")
    @ApiResponse(
            responseCode = "201",
            description = "Label created"
    )
    @PostMapping("")
    @ResponseStatus(CREATED)
    public Label createLabel(
            @Parameter(description = "User data to save")
            @RequestBody @Valid LabelDto labelDto
    ) {
        return labelService.createNewLabel(labelDto);
    }

    @Operation(summary = "Get list of all labels")
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "List of all labels"
        )
    })
    @GetMapping("")
    public List<Label> getLabels() {
        return labelRepository.findAll();
    }

    @Operation(summary = "Get specific label by his id")
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Label found"
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Label not found"
        )
    })
    @GetMapping("{id}")
    public Label getLabel(
            @Parameter(description = "Id of label to be found")
            @PathVariable() Long id
    ) {
        return labelRepository.findById(id).get();
    }

    @Operation(summary = "Update specific label by his id")
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Updated label"
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Label not found"
        )
    })
    @PutMapping("{id}")
    public Label updateLabel(
            @Parameter(description = "Id of label to be updated")
            @PathVariable() Long id,
            @Parameter(description = "Label data to update")
            @RequestBody @Valid LabelDto labelDto) {
//       ! Label label = labelRepository.findById(id).get();
        return labelService.updateLabel(id, labelDto);
    }

    @Operation(summary = "Delete specific label by his id")
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Deleted label"
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Label not found"
        )
    })
    @DeleteMapping("{id}")
    public void deleteLabel(
            @Parameter(description = "Id of label to be deleted")
            @PathVariable() Long id
    ) {
//      !  Label label = labelRepository.findById(id).get();
//        labelRepository.delete(label);
        labelRepository.deleteById(id);
    }
}
