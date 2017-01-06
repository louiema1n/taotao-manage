package com.taotao.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.mapper.ItemParamMapper;
import com.taotao.manage.pojo.ItemParam;

@Service
public class ItemParamService extends BaseService<ItemParam> {

    @Autowired
    private ItemParamMapper itemParamMapper;
    
    @Autowired
    private ItemCatService itemCatService;
    
    public EasyUIResult queryPage(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        Example example = new Example(ItemParam.class);
        example.setOrderByClause("created DESC");
        List<ItemParam> list = this.itemParamMapper.selectByExample(example );
        
        // 将itemparam中的ItemCatName赋值
        for (ItemParam itemParam : list) {
            itemParam.setItemCatName(this.itemCatService.queryById(itemParam.getItemCatId()).getName());
        }
        
        PageInfo<ItemParam> info = new PageInfo<ItemParam>(list);
        
        return new EasyUIResult(info.getTotal(), info.getList());
    }

}
