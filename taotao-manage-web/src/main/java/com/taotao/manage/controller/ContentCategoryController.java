package com.taotao.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.manage.pojo.ContentCategory;
import com.taotao.manage.service.ContentCategoryService;

@Controller
@RequestMapping("content/category")
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    /**
     * 查询所有节点
     * 
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ContentCategory>> queryByPid(
            @RequestParam(value = "id", defaultValue = "0") Long id) {
        try {
            ContentCategory record = new ContentCategory();
            record.setParentId(id);
            List<ContentCategory> list = this.contentCategoryService.queryListByWhere(record);
            if (list == null || list.size() == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 新增contentcatgory
     * 
     * @param parentId
     * @param name
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ContentCategory> add(Long parentId, String name) {
        try {
            ContentCategory catgory = this.contentCategoryService.saveContentCatgory(parentId, name);

            // 201
            return ResponseEntity.status(HttpStatus.CREATED).body(catgory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 重命名
     * 
     * @param id
     * @param name
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> rename(Long id, String name) {
        try {
            ContentCategory record = new ContentCategory();
            record.setId(id);
            record.setName(name);
            this.contentCategoryService.updateSelective(record);
            // 204
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 删除
     * 
     * @param parentId
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(Long parentId, Long id) {
        try {
            this.contentCategoryService.deleteAll(parentId, id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
