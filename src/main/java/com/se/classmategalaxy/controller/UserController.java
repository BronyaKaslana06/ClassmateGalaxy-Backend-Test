package com.se.classmategalaxy.controller;

import com.se.classmategalaxy.dto.*;
import com.se.classmategalaxy.entity.User;
import com.se.classmategalaxy.service.UserRegisterValidatorService;
import com.se.classmategalaxy.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRegisterValidatorService validatorService;

//    @GetMapping("/login")
//    @ApiOperation(notes = "用户登录", value = "用户登录接口")
//    public HashMap<String,Object>login(User user){
//        HashMap<String,Object> map=new HashMap<>();
//        map.put("code",0);
//        if(StringUtils.isEmpty(user.getAccount())||StringUtils.isEmpty(user.getPassword())){
//            map.put("msg","用户或密码为空！");
//            return map;
//        }
//        User resultUser = userService.getByAccount(user.getAccount());
//        if(resultUser!=null){
//
//            String token= TokenUtil.generateToken(resultUser);
//            map.put("cod",1);
//            map.put("data",resultUser);
//            map.put("token",token);
//        }else {
//            map.put("msg","用户名或密码错误!");
//        }
//        return map;
//    }

    /**
     * @description 用户注册
     * @author wyx20
     * @param[1] registerInfo
     * @throws
     * @return HashMap<Object>
     * @time 2023/12/11 14:49
     */
    @PostMapping("/register")
    @ApiOperation(notes="存储注册信息(包括账号密码，电话，邮箱，个性标签)",value="用户注册接口")
    public HashMap<String,Object> setRegisterInfo(@RequestBody RegisterInfo registerInfo){
        HashMap<String,Object> registerResult=new HashMap<>();
        //先检测用户输入的用户名密码是否合规
        if (validatorService.isUsernameValid(registerInfo.getAccount()) && validatorService.isPasswordValid(registerInfo.getPassword())) {
            // 进行用户注册逻辑
            //Todo
            //验证用户手机号邮箱（第三方）...
            userService.registerUser(registerInfo);
            registerResult.put("status",1);
            registerResult.put("Message","User registered successfully!");
        } else if(!validatorService.isUsernameValid(registerInfo.getAccount())) {
            registerResult.put("status",0);
            registerResult.put("Message","User account is not valid!");
        } else if(!validatorService.isPasswordValid(registerInfo.getPassword())) {
            registerResult.put("status",0);
            registerResult.put("Message","User password is not valid!");
        }
        return registerResult;
    }

    /**
     * @description 用户登录
     * @author wyx20
     * @param[1] loginRequest
     * @throws
     * @return HashMap<Object>
     * @time 2023/12/11 14:50
     */
    @PostMapping("/login")
    @ApiOperation(notes = "用户登录", value = "用户登录接口")
    public HashMap<String,Object> loginUser(@RequestBody LoginRequest loginRequest){
        HashMap<String,Object> loginResult=new HashMap<>();
        User user=userService.getByAccount(loginRequest.getAccount());
        if(user!=null&&userService.authenticateUser(loginRequest.getPassword(), user.getPassword())){
            //密码正确，登录成功
            loginResult.put("status",1);
            loginResult.put("user_id",user.getUserId());
            loginResult.put("user",user);

            //更新上一次登录时间
            if(userService.updateLoginTime(user.getUserId())){
                loginResult.put("message","Login time has been updated.");
            }
            else{
                loginResult.put("message","Login time failed to update!");
            }
        }
        else{
            //密码正确，登录成功
            loginResult.put("status",0);
            loginResult.put("message","Incorrect username or password!Please check and try again.");
        }
        return  loginResult;
    }


    @GetMapping("/getUserInfo")
    @ApiOperation(notes = "根据user_id获取用户信息,tagList为标签列表", value = "根据user_id获取用户信息")
    public HashMap<String,Object> findUserById(@RequestParam Integer userId){
        return userService.selectById(userId);
    }

    @PostMapping("/saveUserInfo")
    @ApiOperation(notes = "更新用户信息，返回用户全部信息在data中", value = "修改保存用户的信息")
    public HashMap<String,Object> updateUserInfo(@RequestBody UserUpdateDto userUpdateDto){
        return userService.updateUserInfo(userUpdateDto);
    }

    @GetMapping("/getFollow")
    @ApiOperation(notes = "根据user_id获取关注列表", value = "根据user_id获取用户关注列表")
    public HashMap<String,Object> getFollow(@RequestParam Integer userId){
        return userService.getFollow(userId);
    }


    @PostMapping("/cancelFollow")
    @ApiOperation(notes = "成功status为1，失败为0", value = "取消关注")
    public HashMap<String,Object> cancelFollow(@RequestBody FollowDto followDto){
        return userService.cancelFollow(followDto);
    }

    @PostMapping("/addFollow")
    @ApiOperation(notes = "成功status为1，失败为0", value = "新增关注关系")
    public HashMap<String,Object> addFollow(@RequestBody FollowDto followDto){
        return userService.addFollow(followDto);
    }


    @GetMapping("/page")
    @ApiOperation(notes = "", value = "分页搜索用户")
    public List<User> findUserByPage(@RequestParam String keyword, @RequestParam int paegNum, @RequestParam int pageSize){
        return userService.selectUserPage(keyword, paegNum, pageSize);
    }

    @GetMapping("/count")
    @ApiOperation(notes = "返回int类型数值，表示用户数", value = "获取软件用户总数")
    public int findTotalUserCount(){
        return userService.getTotalUserCount();
    }
}
