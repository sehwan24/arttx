package gp.arttx.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private String code;
    private String message;
    private T data;

    public static <K> ApiResponse<K> of(String code, String message, K data) {
        return new ApiResponse<>(code, message, data);
    }


}
