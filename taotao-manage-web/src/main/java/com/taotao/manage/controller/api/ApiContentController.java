package com.taotao.manage.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.pojo.Content;
import com.taotao.manage.service.ContentService;

@Controller
@RequestMapping("api/content")
public class ApiContentController {

    @Autowired
    private ContentService contentService;

    /**
     * 新增content
     * 
     * @param content
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveContent(Content content) {
        try {
            this.contentService.save(content);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    
    /**
     * 根据CategoryId查询
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryByCategoryId(
            @RequestParam("categoryId") Long categoryId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "30") Integer rows) {
        try {
            EasyUIResult result = this.contentService.queryByCategoryId(categoryId, page, rows);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

}
