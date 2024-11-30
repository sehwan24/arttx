package gp.arttx.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonImageResponseDto {

    private List<String> interpreter;
    private Map<String, Integer> scores;
}
