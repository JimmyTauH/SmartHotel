package com.example.mapper;


import com.example.entity.SignIn;

import java.util.List;

public interface SignInMapper {
    void insert(SignIn signIn);

    SignIn selectByUserId(Integer userId);

    List<SignIn> selectAll(SignIn signIn);

    Integer selectCount(Integer userId);
}
