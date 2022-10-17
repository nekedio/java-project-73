package hexlet.code.service;

import hexlet.code.dto.LabelDto;
import hexlet.code.dto.StatusDto;
import hexlet.code.model.Label;
import hexlet.code.model.Status;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LabelService {

    @Autowired
    private LabelRepository labelRepository;

    public Label createNewLabel(LabelDto labelDto) {
        Label label = new Label();
        label.setName(labelDto.getName());
        return labelRepository.save(label);
    }

    public Label updateLabel(Label label, LabelDto labelDto) {
        label.setName(labelDto.getName());
        return labelRepository.save(label);
    }
}
