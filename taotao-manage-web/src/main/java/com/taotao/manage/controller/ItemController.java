package com.taotao.manage.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemDescService;
import com.taotao.manage.service.ItemService;

@Controller
@RequestMapping(value = "item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    // 日志
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

    /**
     * 新增item
     * 
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> add(Item item, @RequestParam("desc") String desc) {
        try {

            LOGGER.debug("传入参数成功，item = {}; desc = {}", item, desc);
            this.itemService.save(item, desc);
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("新增商品成功，item = {}", item);
            }
            return ResponseEntity.status(HttpStatus.CREATED).build(); // 创建成功
        } catch (Exception e) {
            LOGGER.error("新增商品失败,item = {}", item, e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 创建失败
    }

    /**
     * 查询item列表
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryItemList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "30") Integer rows) {
        try {
            EasyUIResult easyUIResult = this.itemService.queryItemList(page, rows);
            return ResponseEntity.ok(easyUIResult);
        } catch (Exception e) {
            LOGGER.error("查询商品失败，page = " + page +", rows = " + rows, e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

}
