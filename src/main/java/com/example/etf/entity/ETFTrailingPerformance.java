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
 * ETF业绩表现实体类
 */
@Data
@TableName("etf_trailing_performance")
public class ETFTrailingPerformance {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    @TableField("security_id")
    private String securityId;
    
    @TableField("period")
    private String period;
    
    @TableField("return_type")
    private String returnType;
    
    @TableField("return_value")
    private BigDecimal returnValue;
    
    @TableField("as_of_date")
    private LocalDate asOfDate;
    
    @TableField("created_at")
    private LocalDateTime createdAt;
    
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
