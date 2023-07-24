package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    // jpa repo는 2개의 제네릭 타입 사용
    // 첫번째 - 엔티티 타입 클래스
    // 두번째 - 기본키 타입

    List<Item> findByItemNm(String itemNm);

    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    List<Item> findByPriceLessThan(Integer price);

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    @Query("select i from Item i where i.itemDetail like " +
            "%:itemDetail% order by i.price desc ")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);
    @Query(value = "select * from item i where i.item_detail like " +
            "%:itemDetail% order by i.price desc ", nativeQuery = true)
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);
}
