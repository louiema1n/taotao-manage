package com.taotao.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;

@Controller
@RequestMapping(value = "item/cat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;
    
    /**
     * 根据pid查询商品类目
     * @param pid
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ItemCat>> queryItemCatByPid(
            @RequestParam(value = "id", defaultValue = "0") Long pid) {
        try {
            ItemCat record = new ItemCat();
            record.setParentId(pid);
            List<ItemCat> list = itemCatService.queryListByWhere(record);
            if (list == null || list.isEmpty()) {
                //没有找到资源
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            //找到资源
            return ResponseEntity.status(HttpStatus.OK).body(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //请求错误
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

}
