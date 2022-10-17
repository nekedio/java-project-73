package hexlet.code.controller;

import hexlet.code.dto.LabelDto;
import hexlet.code.dto.StatusDto;
import hexlet.code.model.Label;
import hexlet.code.model.Status;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.StatusRepository;
import hexlet.code.service.LabelService;
import hexlet.code.service.StatusService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/labels")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @Autowired
    private LabelRepository labelRepository;

    @PostMapping("")
    public Label createLabel(@RequestBody LabelDto labelDto) {
        return labelService.createNewLabel(labelDto);
    }

    @GetMapping("")
    public List<Label> getLabels() {
//        return "qwe";
        return labelRepository.findAll();
    }

    @GetMapping("{id}")
    public Label getLabel(@PathVariable() Long id) {
        return labelRepository.findById(id).get();
    }

    @PutMapping("{id}")
    public Label updateLabel(@PathVariable() Long id, @RequestBody LabelDto labelDto) {
        Label label = labelRepository.findById(id).get();
        return labelService.updateLabel(label, labelDto);
    }

    @DeleteMapping("{id}")
    public void deleteLabel(@PathVariable() Long id) {
        Label label = labelRepository.findById(id).get();
        labelRepository.delete(label);
    }
}
