package com.example.homeworkshop5.controller;

import com.example.homeworkshop5.dto.ShopCreationDto;
import com.example.homeworkshop5.dto.ShopDto;
import com.example.homeworkshop5.mapper.ShopMapper;
import com.example.homeworkshop5.model.Shop;
import com.example.homeworkshop5.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Validated
public class ShopViewController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopMapper mapper;

    @RequestMapping("/getShops")
    @PreAuthorize("isAuthenticated()")
    public String getAllShops(Model model) {
        List<ShopDto> shopDto = shopService.getShops().stream()
                .map(mapper::toShopDTO)
                .collect(Collectors.toList());
        model.addAttribute("shop", shopDto);
        return "getShops";
    }

    @RequestMapping("/addNewShop")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addNewShop(Model model) {
        model.addAttribute("shop", new ShopDto());
        return "shopInfo";
    }

    @RequestMapping("/saveShop")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveShop(@Valid @ModelAttribute("shop") ShopCreationDto shopCreationDto,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "shopInfo";
        } else {
            Shop shop = mapper.toShop(shopCreationDto);
            shopService.createShop(shop);
            return "redirect:/getShops";
        }
    }

    @RequestMapping("/deleteShop")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteShop(@RequestParam("shopId") int id) {
        shopService.deleteShopById(id);
        return "redirect:/getShops";
    }
}
