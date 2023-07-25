package com.shop.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import com.shop.entity.QItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;
    @PersistenceContext
    EntityManager em;
    // 영속성 컨텍스트를 사용하기 위해 어노테이션을 사용해 엔티티 매니저 빈을 주입

    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest() {
        Item item = Item.builder()
                .itemNm("테스트 상품")
                .price(10000)
                .itemDetail("테스트 상품 상세 설명")
                .itemSellStatus(ItemSellStatus.SELL)
                .stockNumber(100)
                .regTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();

        Item savedItem = itemRepository.save(item);
        System.out.println(savedItem);
    }

    //    @Test
//    @DisplayName("상품 반복 저장 테스트")
    public void createItemList() {
        for (int i = 1; i <= 10; i++) {
            Item item = Item.builder()
                    .itemNm("테스트 상품" + i)
                    .price(10000 + i)
                    .itemDetail("테스트 상품 상세 설명" + i)
                    .itemSellStatus(ItemSellStatus.SELL)
                    .stockNumber(100)
                    .regTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .build();
            Item savedItem = itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNmTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNm("테스트 상품1");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("상품명, 상품상세설명 or 테스트")
    public void findByItemNmOrItemDetailTest() {
        this.createItemList();
        List<Item> itemList = itemRepository
                .findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByPriceLessThanTest() {
        this.createItemList();
        List<Item> itemList = itemRepository
                .findByPriceLessThan(10005);
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 내림차순 조회 테스트")
    public void findByPriceLessThanOrderByTest() {
        this.createItemList();
        List<Item> itemList = itemRepository
                .findByPriceLessThanOrderByPriceDesc(10005);
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("@Query를 사용한 상품 조회 테스트")
    public void findByItemDetailTest() {
        this.createItemList();
        List<Item> itemList = itemRepository
                .findByItemDetail("테스트 상품 상세 설명");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("nativeQuery 속성을 이용한 상품 조회 테스트")
    public void findByItemDetailByNativeTest() {
        this.createItemList();
        List<Item> itemList = itemRepository
                .findByItemDetailByNative("테스트 상품 상세 설명");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }
    @Test
    @DisplayName("Querydsl 조회 테스트1")
    public void querydslTest() {
        this.createItemList();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem qItem = QItem.item;
        // querydsl을 통해 쿼리를 생성하기 위해 플러그인을 통해 자동으로 생성된 qitem 객체 사용

        JPAQuery<Item> query = queryFactory.selectFrom(qItem)
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.itemDetail.like("%" + "테스트 상품 상세 설명" + "%"))
                .orderBy(qItem.price.desc());
        List<Item> itemList = query.fetch();
        // 쿼리 결과를 리스트로 반환
        // fetch 실행 시점으로 쿼리문 실행
        // 그 외: fetchOne, fetchFirst 등

        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }
    public void createItemList2() {
        for (int i = 1; i < 6; i++) {
            Item item = Item.builder()
                    .itemNm("테스트 상품" + i)
                    .price(10000 + i)
                    .itemDetail("테스트 상품 상세 설명" + i)
                    .itemSellStatus(ItemSellStatus.SELL)
                    .stockNumber(100)
                    .regTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .build();
            itemRepository.save(item);
        }
        for (int i = 6; i <= 10; i++) {
            Item item = Item.builder()
                    .itemNm("테스트 상품" + i)
                    .price(10000 + i)
                    .itemDetail("테스트 상품 상세 설명" + i)
                    .itemSellStatus(ItemSellStatus.SOLD_OUT)
                    .stockNumber(0)
                    .regTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .build();
            itemRepository.save(item);
        }
    }
    @Test
    @DisplayName("상품 Querydsl 조회 테스트 2")
    public void querydslTest2() {
        this.createItemList2();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        // query에 들어갈 조건을 만들어주는 빌더
        QItem item = QItem.item;
        String itemDetail = "테스트 상품 상세 설명";
        int price = 10003;
        String itemSellStat = "SELL";

        booleanBuilder.and(item.itemDetail.like("%" + itemDetail + "%"));
        booleanBuilder.and(item.price.gt(price));

        if (StringUtils.equals(itemSellStat, ItemSellStatus.SELL)) {
            booleanBuilder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));
        }
        Pageable pageable = PageRequest.of(0, 5);
        // 데이터를 페이징해 조회

        Page<Item> itemPagingResult = itemRepository.findAll(booleanBuilder, pageable);
        System.out.println("total " + itemPagingResult.getTotalElements());

        List<Item> res = itemPagingResult.getContent();
        for (Item resItem : res) {
            System.out.println(resItem.toString());
        }
    }
}