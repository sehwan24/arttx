package gp.arttx.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    /* USER */
    SUCCESS_USER_SIGN_UP(HttpStatus.CREATED, "U00000", "회원 가입에 성공했습니다."),
    SUCCESS_USER_LOGIN(HttpStatus.OK, "U00100", "로그인에 성공했습니다."),
    SUCCESS_USER_LOGOUT(HttpStatus.OK, "U00200", "로그아웃에 성공했습니다"),
    SUCCESS_SEND_VERIFICATION_NUMBER_BY_SMS(HttpStatus.OK, "U00300", "인증 번호를 발송했습니다"),
    SUCCESS_UPDATE_USER_INFO(HttpStatus.OK, "U00400", "회원 정보 수정에 성공했습니다."),
    SUCCESS_UPDATE_USER_PASSWORD(HttpStatus.OK, "U00401", "비밀 번호 변경에 성공했습니다."),
    SUCCESS_GET_USER_INFO(HttpStatus.OK, "U00500", "회원 정보 조회에 성공했습니다."),
    SUCCESS_GET_USER_POINT(HttpStatus.OK, "U00501", "회원 포인트 조회에 성공했습니다."),
    SUCCESS_DELETE_USER(HttpStatus.OK, "U00600", "회원 탈퇴에 성공했습니다."),


    /* GLOBAL */
    OK(HttpStatus.OK, "200", "성공");

    private HttpStatus httpStatus;
    private String code;
    private String message;

}
