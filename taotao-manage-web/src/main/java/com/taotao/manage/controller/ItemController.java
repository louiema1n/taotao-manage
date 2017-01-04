package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemDescService;
import com.taotao.manage.service.ItemService;

@Controller
@RequestMapping(value = "item")
public class ItemController {
    
    @Autowired
    private ItemService itemService;
    
    /**
     * 新增item
     * @return 
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> add(Item item, @RequestParam("desc") String desc) {
        try {
            this.itemService.save(item, desc);

            return ResponseEntity.status(HttpStatus.CREATED).build();   //创建成功
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); //创建失败
    }

}
