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
 * ETF分红历史实体类
 */
@Data
@TableName("etf_distribution_history")
public class ETFDistributionHistory {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    @TableField("security_id")
    private String securityId;
    
    @TableField("distribution_date")
    private LocalDate distributionDate;
    
    @TableField("ex_date")
    private LocalDate exDate;
    
    @TableField("record_date")
    private LocalDate recordDate;
    
    @TableField("payable_date")
    private LocalDate payableDate;
    
    @TableField("distribution_amount")
    private BigDecimal distributionAmount;
    
    @TableField("distribution_type")
    private String distributionType;
    
    @TableField("reinvest_price")
    private BigDecimal reinvestPrice;
    
    @TableField("created_at")
    private LocalDateTime createdAt;
    
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
