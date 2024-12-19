package com.deliveryManPlus.shop.repository;

import com.deliveryManPlus.shop.model.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop,Long> {
    @Query("select s from Shop s where s.status!='CLOSED_DOWN'")
    List<Shop> findAllNotClosedDown();

    boolean existsShopByRegistNumber(String registNumber);
}
