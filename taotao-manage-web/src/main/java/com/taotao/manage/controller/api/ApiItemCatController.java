package com.taotao.manage.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.bean.ItemCatResult;
import com.taotao.manage.service.ItemCatService;

@Controller
@RequestMapping(value = "api/item/cat")
public class ApiItemCatController {

    @Autowired
    private ItemCatService itemCatService;
    
    //private static final ObjectMapper MAPPER = new ObjectMapper(); 
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ItemCatResult> queryItemList() {
        try {
            ItemCatResult result = this.itemCatService.queryAllToTree();
            //json = MAPPER.writeValueAsString(result);
            if (result == null) {
                // 404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            // 200
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
}
