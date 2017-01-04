package com.taotao.manage.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manage.pojo.BasePojo;

/**
 * 通用service
 * @author ly
 *
 */
public abstract class BaseService<T extends BasePojo> {
    
    @Autowired  //spring 4.x泛型自动注入
    private Mapper<T> mapper;
    
    //通过虚方法，让继承该类的子类在自己的方法中返回其注入的mapper
//    public abstract Mapper<T> getMapper();
    
    /**
     * 1.根据主键查询
     */
    public T queryById(Long id) {
        return this.mapper.selectByPrimaryKey(id);
    }
    
    /**
     * 2.查询所有
     */
    public List<T> queryAll() {
        return this.mapper.select(null);
    }
    
    /**
     * 3.根据条件查询一条数据，如果该条件查询的结果不是一条数据，则抛出异常
     */
    public T queryOne(T record) {
        return this.mapper.selectOne(record);
    }
    
    /**
     * 4.根据查询条件查询多条数据
     */
    public List<T> queryListByWhere(T record) {
        return this.mapper.select(record);
    }
    
    /**
     * 5.根据条件分页查询数据
     */
    public PageInfo<T> queryPageListByWhere(T record, Integer page, Integer rows) {
        //设置分页参数
        PageHelper.startPage(page, rows);
        return new PageInfo<T>(this.mapper.selectByExample(record));
    }
    
    /**
     * 6.新增数据
     */
    public Integer save(T record) {
        record.setCreated(new Date());
        record.setUpdated(record.getCreated());
        return this.mapper.insert(record);
    }
    
    /**
     * 66.选择不为null的字段作为插入数据的字段来插入数据
     */
    public Integer saveSelective(T record) {
        record.setCreated(new Date());
        record.setUpdated(record.getCreated());
        return this.mapper.insertSelective(record);
    }
    
    /**
     * 7.修改数据
     */
    public Integer update(T record) {
        record.setUpdated(new Date());
        return this.mapper.updateByPrimaryKey(record);
    }
    
    /**
     * 77.选择不为null的字段作为更新数据的字段来更新数据
     */
    public Integer updateSelective(T record) {
        record.setUpdated(new Date());
        //将创建时间设为null，则不会更新该字段
        record.setCreated(null);
        return this.mapper.updateByPrimaryKeySelective(record);
    }
    
    /**
     * 8.根据主键删除数据（物理删除）
     */
    public Integer deleteById(Long id) {
        return this.mapper.deleteByPrimaryKey(id);
    }
    
    /**
     * 9.根据条件批量删除数据
     */
    public Integer deleteByIds(List<Object> ids, Class clazz, String property) {
        Example example = new Example(clazz);
        example.createCriteria().andIn(property, ids);
        return this.mapper.deleteByExample(example);
    }
    
    /**
     * 10.根据条件删除
     */
    public Integer deleteByWhere(T record) {
        return this.mapper.delete(record);
    }
}
