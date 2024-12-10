package gp.arttx.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExitMessageResponseDto {

    private String drawing_analysis;
    private String psychological_scores;
    private String psychological_needs;
    private String conversation_details;
    private String healing_methods;
}
