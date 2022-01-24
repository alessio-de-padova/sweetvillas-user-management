package com.nimesia.sweetvillas.controllers;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.nimesia.sweetvillas.bean.ApiError;
import com.nimesia.sweetvillas.dto.AccountDTO;
import com.nimesia.sweetvillas.entities.UserEntity;
import com.nimesia.sweetvillas.services.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class AbsController {
    @Autowired
    private HttpServletRequest context;
    @Autowired
    private UserService userService;
    private @Getter
    String ADM = "ADM";

    public ArrayList<ApiError> validatePwd(
            AccountDTO account
    ) {
        ArrayList<ApiError> errors = new ArrayList<>();

        if (!account.pdwMatch()) {
            ApiError error = new ApiError();
            error.setType("PwdNotMatching");
            error.setPropertyPath("pwd");
            errors.add(error);
        }

        if (!account.isPwdValid()) {
            ApiError error = new ApiError();
            error.setType("PwdNotValid");
            error.setPropertyPath("pwd");
            errors.add(error);
        }

        return errors;
    }


    public UserEntity getRequestUser() {
        DecodedJWT decoded = (DecodedJWT) context.getAttribute("decoded");
        return userService.get(decoded.getSubject()).get();
    }
}
