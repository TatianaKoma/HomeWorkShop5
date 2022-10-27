package com.example.homeworkshop5.service.impl;

import com.example.homeworkshop5.exception.NotFoundException;
import com.example.homeworkshop5.model.Shop;
import com.example.homeworkshop5.repository.ShopRepository;
import com.example.homeworkshop5.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.homeworkshop5.ResponseMessages.SHOP_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    @Override
    public Shop createShop(Shop shop) {
        return shopRepository.save(shop);
    }

    @Override
    public List<Shop> getShops() {
        return shopRepository.findAll();
    }

    @Override
    public void deleteShopById(Integer id) {
        Shop shopForDelete = shopRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(SHOP_NOT_FOUND, id)));
        shopRepository.delete(shopForDelete);
    }
}
