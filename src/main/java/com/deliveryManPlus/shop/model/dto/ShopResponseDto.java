package com.deliveryManPlus.shop.model.dto;

import com.deliveryManPlus.shop.constant.ShopStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
public class ResponseDto {
    private Long id;
    private String registNumber;
    private String name;
    private String address;
    private BigDecimal minimumOrderAmount;
    private ShopStatus 

}
