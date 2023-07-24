package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
@Entity
@Table(name = "item")
@Getter
@Setter
@ToString
public class Item {
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 50)
    private String itemNm;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockNumber;

    @Lob
    @Column(nullable = false)
    private String itemDetail;

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;

    // 기본 생성자
    public Item() {
    }

    @Builder
    public Item(Long id, String itemNm, int price, int stockNumber, String itemDetail,
                ItemSellStatus itemSellStatus, LocalDateTime regTime, LocalDateTime updateTime) {
        this.id = id;
        this.itemNm = itemNm;
        this.price = price;
        this.stockNumber = stockNumber;
        this.itemDetail = itemDetail;
        this.itemSellStatus = itemSellStatus;
        this.regTime = regTime;
        this.updateTime = updateTime;
    }
}