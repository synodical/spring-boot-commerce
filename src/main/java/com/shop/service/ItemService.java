package com.shop.service;

import com.shop.dto.ItemFormDto;
import com.shop.dto.ItemSearchDto;
import com.shop.dto.MainItemDto;
import com.shop.entity.Item;
import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;
    private final ItemImgService itemImgService;

    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {
        Item item = itemFormDto.createItem(); // 상품 등록 폼 데이터로 아이템 객체 생성
        itemRepository.save(item);
        itemImgFileList.forEach(file -> {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            itemImg.setRepimgYn(itemImgFileList.indexOf(file) == 0 ? "Y" : "N");
            try {
                itemImgService.saveItemImg(itemImg, file);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return item.getId();
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getMainItemPage(itemSearchDto, pageable);
    }
}
