package com.example.mapper;

import com.example.entity.HomePage;

public interface HomePageMapper {

    void add(HomePage homePage);

    void update(HomePage homePage);

    HomePage selectById(Integer userId);
}
