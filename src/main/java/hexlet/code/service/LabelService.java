package hexlet.code.service;

import hexlet.code.dto.LabelDto;
import hexlet.code.model.Label;

public interface LabelService {

    public Label createNewLabel(LabelDto labelDto);

    public Label updateLabel(Long id, LabelDto labelDto);
}
