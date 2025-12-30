package com.example.etf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.etf.entity.ETFDocument;
import org.apache.ibatis.annotations.Mapper;

/**
 * ETF文档信息 Mapper接口
 */
@Mapper
public interface ETFDocumentMapper extends BaseMapper<ETFDocument> {
}
