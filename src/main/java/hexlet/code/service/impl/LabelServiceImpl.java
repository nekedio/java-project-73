package hexlet.code.service.impl;

import hexlet.code.dto.LabelDto;
import hexlet.code.model.Label;
import hexlet.code.repository.LabelRepository;
import hexlet.code.service.LabelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LabelServiceImpl implements LabelService {

    private final LabelRepository labelRepository;

    @Override
    public Label createNewLabel(LabelDto labelDto) {

        Label label = fromDto(labelDto);

        return labelRepository.save(label);
    }

    @Override
    public Label updateLabel(Long id, LabelDto labelDto) {

        Label label = labelRepository.findById(id).get();
        merge(label, labelDto);

        return labelRepository.save(label);
    }

    private void merge(Label label, LabelDto labelDto) {
        Label newLabel = fromDto(labelDto);
        label.setName(newLabel.getName());
    }

    private Label fromDto(LabelDto labelDto) {
        return Label.builder()
                .name(labelDto.getName())
                .build();
    }
}
