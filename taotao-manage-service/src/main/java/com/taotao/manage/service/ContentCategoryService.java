package com.taotao.manage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.taotao.manage.pojo.ContentCategory;

@Service
public class ContentCategoryService extends BaseService<ContentCategory> {

    public ContentCategory saveContentCatgory(Long parentId, String name) {
        ContentCategory record = new ContentCategory();
        record.setIsParent(false);
        record.setName(name);
        record.setParentId(parentId);
        record.setSortOrder(1);
        record.setStatus(1);
        record.setId(null);
        super.save(record);

        // 查询父节点的IsParent是否为true,否则改为true
        ContentCategory parent = super.queryById(parentId);
        if (!parent.getIsParent()) {
            parent.setIsParent(true);
            super.update(parent);
        }

        return record;
    }

    public void deleteAll(Long parentId, Long id) {
        List<Object> ids = new ArrayList<Object>();
        // 通过id查找ContentCatgory
        ContentCategory category = super.queryById(id);
        // 删除自己
        ids.add(id);
        // 如果当前节点为父节点,遍历所有当前id下所有子节点并删除
        if(category.getIsParent()) {
            // 调用方法获取所有子节点
            getIds(id, ids);
        }
        
        // 批量删除
        super.deleteByIds(ids, ContentCategory.class, "id");
        
        // 查找当前节点的所有兄弟节点,如没有,则将parentId所在节点的isParent改为FALSE
        ContentCategory record = new ContentCategory();
        record.setParentId(parentId);
        List<ContentCategory> list = super.queryListByWhere(record);
        if (list == null || list.size() == 0) {
            // 没有,则将parentId所在节点的isParent改为FALSE
            ContentCategory parent = new ContentCategory();
            parent.setId(parentId);
            parent.setIsParent(false);
            super.updateSelective(parent);
        }
    }

    /**
     * 获取所有子节点
     * 
     * @param id
     * @param ids
     */
    private void getIds(Long id, List<Object> ids) {
        // 查询当前节点下的所有子节点
        ContentCategory son = new ContentCategory();
        son.setParentId(id);
        List<ContentCategory> sonList = super.queryListByWhere(son);

        // 添加子节点
        for (ContentCategory contentCategory : sonList) {
            ids.add(contentCategory.getId());
            // 如果当前子节点的isParent为true,递归获取所有子节点
            if (contentCategory.getIsParent()) {
                getIds(contentCategory.getId(), ids);
            }
        }
    }

}
