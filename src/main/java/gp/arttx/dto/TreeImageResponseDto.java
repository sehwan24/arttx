package gp.arttx.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TreeImageResponseDto {

    private List<String> interpreter;
    private Map<String, Integer> scores = new LinkedHashMap<>();
}
