package com.taotao.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;

@Service
public class ItemService extends BaseService<Item> {

    @Autowired
    private ItemDescService itemDescService;

    public void save(Item item, String desc) {

        item.setStatus(1);      //1-正常
        //设置id为null
        item.setId(null);
        super.save(item);
        
        ItemDesc record = new ItemDesc();
        record.setItemDesc(desc);
        record.setItemId(item.getId());
        this.itemDescService.save(record);
        
    }
}
