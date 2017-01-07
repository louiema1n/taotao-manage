package com.taotao.manage.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.bean.ItemCatResult;
import com.taotao.manage.service.ItemCatService;

@Controller
@RequestMapping(value = "api/item/cat")
public class ApiItemCatController {

    @Autowired
    private ItemCatService itemCatService;
    
    private static final ObjectMapper MAPPER = new ObjectMapper(); 
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> queryItemList(
            @PathVariable(value = "callback") String callback) throws Exception {
        ItemCatResult result = this.itemCatService.queryAllToTree();
        String rs = MAPPER.writeValueAsString(result);
        String json = callback + "(" + result +");" ;
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }
    
}
