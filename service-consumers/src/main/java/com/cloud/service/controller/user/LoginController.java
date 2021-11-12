package com.cloud.service.controller.user;

import com.cloud.service.result.R;
import org.springframework.web.bind.annotation.*;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: LoginController
 * @description: TODO
 * @date 2021/11/5 16:11
 */
@CrossOrigin //跨域
@RestController
@RequestMapping("/user")
public class LoginController {
    /**
     * 登录
     *
     * @return
     */
    @PostMapping("login")
    public R login() {
        return R.ok().data("token", "admin");
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping("info")
    public R info() {
        return R.ok()
                .data("roles", "[admin]")
                .data("name", "admin")
                .data("avatar", "https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("logout")
    public R logout() {
        return R.ok();
    }
}
