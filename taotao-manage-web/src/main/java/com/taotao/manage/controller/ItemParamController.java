package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.pojo.ItemParam;
import com.taotao.manage.service.ItemParamService;

@Controller
@RequestMapping(value = "item/param")
public class ItemParamController {
    
    @Autowired
    private ItemParamService itemParamService;

    /**
     * 根据itemCatId查询ItemParam
     * @param itemCatId
     * @return
     */
    @RequestMapping(value = "{itemCatId}", method = RequestMethod.GET)
    public ResponseEntity<ItemParam> queryItemParam(@PathVariable("itemCatId") Long itemCatId) {
        ItemParam record = new ItemParam();
        record.setItemCatId(itemCatId);
        try {
            ItemParam itemParam = this.itemParamService.queryOne(record );
            if (itemParam == null) {
                // 404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            // 200
            return ResponseEntity.status(HttpStatus.OK).body(itemParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
    /**
     * 保存ItemParam
     * @param paramData
     * @param itemCatId
     * @return
     */
    @RequestMapping(value = "{itemCatId}", method = RequestMethod.POST)
    public ResponseEntity<Void> saveItemParam(
            @RequestParam("paramData") String paramData, 
            @PathVariable("itemCatId") Long itemCatId) {
        ItemParam record = new ItemParam();
        record.setItemCatId(itemCatId);
        record.setParamData(paramData);
        try {
            Integer count = this.itemParamService.saveSelective(record );
            if (count == 1) {
                // 201
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
            // 400
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryPage(
            @RequestParam("page") Integer page, 
            @RequestParam("rows") Integer rows) {
        try {
            EasyUIResult result = this.itemParamService.queryPage(page, rows);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
