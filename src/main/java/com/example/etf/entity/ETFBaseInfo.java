package com.example.etf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * ETF基础信息实体类
 */
@Data
@TableName("etf_base_info")
public class ETFBaseInfo {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    @TableField("security_id")
    private String securityId;
    
    @TableField("name")
    private String name;
    
    @TableField("legal_name")
    private String legalName;
    
    @TableField("category_name")
    private String categoryName;
    
    @TableField("investment_type")
    private String investmentType;
    
    @TableField("holding_type")
    private String holdingType;
    
    @TableField("inception_date")
    private LocalDate inceptionDate;
    
    @TableField("currency_id")
    private String currencyId;
    
    @TableField("currency_name")
    private String currencyName;
    
    @TableField("close_price")
    private BigDecimal closePrice;
    
    @TableField("day_change")
    private BigDecimal dayChange;
    
    @TableField("investment_strategy")
    private String investmentStrategy;
    
    @TableField("management_company")
    private String managementCompany;
    
    @TableField("advisor")
    private String advisor;
    
    @TableField("sub_advisor")
    private String subAdvisor;
    
    @TableField("administrator")
    private String administrator;
    
    @TableField("created_at")
    private LocalDateTime createdAt;
    
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
