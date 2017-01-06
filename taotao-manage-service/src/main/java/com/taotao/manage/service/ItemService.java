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
import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.pojo.ItemParamItem;

@Service
public class ItemService extends BaseService<Item> {

    @Autowired
    private ItemMapper itemMapper;
    
    @Autowired
    private ItemDescService itemDescService;
    
    @Autowired
    private ItemCatService itemCatService;
    
    @Autowired
    private ItemParamItemService itemParamItemService;

    public void save(Item item, String desc, String itemParams) {

        item.setStatus(1);      //1-正常
        //设置id为null
        item.setId(null);
        super.save(item);
        
        // 保存商品描述
        ItemDesc record = new ItemDesc();
        record.setItemDesc(desc);
        record.setItemId(item.getId());
        this.itemDescService.save(record);
        
        // 保存商品规格数据
        ItemParamItem record2 = new ItemParamItem();
        record2.setItemId(item.getId());
        record2.setParamData(itemParams);
        this.itemParamItemService.save(record2 );
        
    }

    public EasyUIResult queryItemList(Integer page, Integer rows) {
        //设置分页参数
        PageHelper.startPage(page, rows);
        
        Example example = new Example(Item.class);
        //根据创建时间倒序
        example.setOrderByClause("created DESC");
        List<Item> list = this.itemMapper.selectByExample(example );
        //用cid查询itemCat的name添加到item返回页面
        ItemCat itemCat = new ItemCat();
        for (Item item : list) {
            itemCat.setId(item.getCid());
            ItemCat itemCat2 = this.itemCatService.queryOne(itemCat);
            item.setName(itemCat2.getName());
        }
        PageInfo<Item> info = new PageInfo<Item>(list);
        return new EasyUIResult(info.getTotal(), info.getList());
    }

    public Boolean update(Item item, String desc, String itemParams) {
        item.setCreated(null);
        item.setStatus(null);   //状态不能被更新
        Integer count1 = super.updateSelective(item);
        
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        Integer count2 = this.itemDescService.updateSelective(itemDesc);
        
        Integer count3 = this.itemParamItemService.updateParamsByItemId(item.getId(), itemParams);
        
        return count1.intValue() == 1 && count2.intValue() == 1 && count3 == 1;
    }
}
