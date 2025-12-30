package com.example.etf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * ETF文档信息实体类
 */
@Data
@TableName("etf_document")
public class ETFDocument {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    @TableField("security_id")
    private String securityId;
    
    @TableField("document_type")
    private String documentType;
    
    @TableField("document_name")
    private String documentName;
    
    @TableField("document_url")
    private String documentUrl;
    
    @TableField("document_date")
    private LocalDate documentDate;
    
    @TableField("created_at")
    private LocalDateTime createdAt;
    
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
