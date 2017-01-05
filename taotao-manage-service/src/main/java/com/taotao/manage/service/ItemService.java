package com.taotao.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.mapper.ItemMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;

@Service
public class ItemService extends BaseService<Item> {

    @Autowired
    private ItemMapper itemMapper;
    
    @Autowired
    private ItemDescService itemDescService;

    public void save(Item item, String desc) {

        item.setStatus(1);      //1-正常
        //设置id为null
        item.setId(null);
        super.save(item);
        
        ItemDesc record = new ItemDesc();
        record.setItemDesc(desc);
        record.setItemId(item.getId());
        this.itemDescService.save(record);
        
    }

    public EasyUIResult queryItemList(Integer page, Integer rows) {
        //设置分页参数
        PageHelper.startPage(page, rows);
        
        Example example = new Example(Item.class);
        //根据创建时间倒序
        example.setOrderByClause("created DESC");
        List<Item> list = this.itemMapper.selectByExample(example );
        
        PageInfo<Item> info = new PageInfo<Item>(list);
        return new EasyUIResult(info.getTotal(), info.getList());
    }
}
