package com.taotao.manage.mapper;

import java.util.List;

import com.github.abel533.mapper.Mapper;
import com.taotao.manage.pojo.Content;

public interface ContentMapper extends Mapper<Content> {

    /**
     * 根据CategoryId查询Content列表,并根据updated降序
     * 
     * @param categoryId
     * @return
     */
    public List<Content> queryListByCategoryId(Long categoryId);

}
