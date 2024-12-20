package techmail.domain.user.controller;

import techmail.domain.user.dto.SendVerifyCodeReqDto;
import techmail.domain.user.dto.CreateUserReqDto;
import techmail.domain.user.dto.DeleteUserReqDto;
import techmail.domain.user.service.UserService;
import techmail.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users/verify/send")
    public ApiResponse<Void> sendVerifyCode(@RequestBody SendVerifyCodeReqDto request) {
        userService.sendVerifyCode(request);
        return ApiResponse.success(null);
    }

    @PostMapping("/users")
    public ApiResponse<Void> createUser(@RequestBody CreateUserReqDto request) {
        userService.createUser(request);
        return ApiResponse.success(null);
    }

    @DeleteMapping("/users")
    public ApiResponse<Void> deleteUser(@RequestBody DeleteUserReqDto request) {
        userService.deleteUser(request);
        return ApiResponse.success(null);
    }

}
