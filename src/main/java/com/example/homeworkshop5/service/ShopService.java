package com.example.homeworkshop5.service;

import com.example.homeworkshop5.model.Shop;

import java.util.List;

public interface ShopService {

    Shop createShop(Shop shop);

    List<Shop> getShops();

    void deleteShopById(Integer id);
}
