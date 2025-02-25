package com.hx.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hx.common.Result;
import com.hx.common.enums.AppHttpCodeEnum;
import com.hx.entity.DTO.user.LoginDTO;
import com.hx.entity.DTO.user.RegisterDTO;
import com.hx.entity.DTO.user.UpdateUserDTO;
import com.hx.entity.PO.User;
import com.hx.mapper.UserMapper;
import com.hx.service.UserService;
import com.hx.utils.EmailUtil;
import com.hx.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dhx
 * @date 2025/1/8 16:33
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Result register(RegisterDTO registerDTO) {
        String username = registerDTO.getUsername();
        String email = registerDTO.getEmail();
        String verifyCode = registerDTO.getVerifyCode();
        String password = registerDTO.getPassword();
        if(username==null||email==null||password==null||!EmailUtil.isValidEmail(email)){
            return Result.errorResult(AppHttpCodeEnum.PARAM_ERROR);
        }
        if(!EmailUtil.isValidVerificationCode(email,verifyCode)){
            return Result.errorResult(AppHttpCodeEnum.VERIFYCODE_ERROR);
        }
        if(userMapper.exists(new QueryWrapper<User>().eq("user_username",username))){
            return Result.errorResult(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(userMapper.exists(new QueryWrapper<User>().eq("user_email",email))){
            return Result.errorResult(AppHttpCodeEnum.EMAIL_EXIST);
        }
        User user = new User(username,email,password,username,"","");
        if(userMapper.insert(user)==1){
            return Result.okResult(200,"注册成功");
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public Result sendVerifyCode(String email) {
        if(!EmailUtil.isValidEmail(email)){
            return Result.errorResult(AppHttpCodeEnum.PARAM_ERROR);
        }
        if(!EmailUtil.sendVerificationCode(email)){
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return Result.okResult(200,"发送成功");
    }

    @Override
    public Result login(LoginDTO loginDTO) {
        String account = loginDTO.getAccount();
        String password = loginDTO.getPassword();
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_username",account).eq("user_password",password));
        if(user == null){
            user = userMapper.selectOne(new QueryWrapper<User>().eq("user_email",account).eq("user_password",password));
        }
        if(user == null){
            return Result.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }
        return Result.okResult(JwtUtil.generateToken(user.getId()));
    }
    @Override
    public Result getUserInfo(String token) {
        Long id = JwtUtil.getUserIdFromToken(token);
        return Result.okResult(userMapper.selectById(id));
    }

    @Override
    public Result updateUserInfo(UpdateUserDTO updateUserDTO,String token) {
        Long id = JwtUtil.getUserIdFromToken(token);
        String nickname = updateUserDTO.getNickname();
        String avatar = updateUserDTO.getAvatar();
        String description = updateUserDTO.getDescription();
        User user = userMapper.selectById(id);
        user.setNickname(nickname);
        user.setAvatar(avatar);
        user.setDescription(description);
        if(userMapper.updateById(user)==1){
            return Result.okResult(200,"修改成功");
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }
}
