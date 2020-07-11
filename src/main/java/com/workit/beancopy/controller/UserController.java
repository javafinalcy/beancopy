package com.workit.beancopy.controller;

import com.workit.beancopy.bean.UserBO;
import com.workit.beancopy.bean.UserVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;

@RestController
public class UserController {
  final static   UserBO bo = new UserBO();
    static {
        bo.setAge(1);
        bo.setUserName("java金融");
        bo.setEmail("888@qq.com");
        bo.setIdCard("2222");
        bo.setSex("M");
    }

    @RequestMapping("/")
    public String save() throws InvocationTargetException, IllegalAccessException {
        UserVO vo = new UserVO();
        org.apache.commons.beanutils.BeanUtils.copyProperties(vo, bo);
        saveDb(vo);
        return "success";
    }


    private void saveDb(UserVO bo) {
    }


}
