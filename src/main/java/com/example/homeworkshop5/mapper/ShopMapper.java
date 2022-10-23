package com.example.homeworkshop5.mapper;

import com.example.homeworkshop5.dto.ShopCreationDto;
import com.example.homeworkshop5.dto.ShopDto;
import com.example.homeworkshop5.model.Shop;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ShopMapper {
    private final ProductMapper productMapper;

    public Shop toShop(ShopCreationDto shopCreationDTO) {
        Shop shop = new Shop();
        shop.setName(shopCreationDTO.getName());
        return shop;
    }

    public ShopDto toShopDTO(Shop shop) {
        return new ShopDto(
                shop.getId(),
                shop.getName(),
                shop.getProducts().stream()
                        .map(productMapper::toProductDTO)
                        .collect(Collectors.toList()));
    }
}
