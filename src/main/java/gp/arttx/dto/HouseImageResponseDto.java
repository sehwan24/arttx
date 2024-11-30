package gp.arttx.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HouseImageResponseDto {

    private List<String> interpreter;
    private Map<String, Integer> scores;

}
