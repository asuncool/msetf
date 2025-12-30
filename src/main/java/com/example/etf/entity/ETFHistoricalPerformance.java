package com.example.etf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * ETF历史业绩实体类
 */
@Data
@TableName("etf_historical_performance")
public class ETFHistoricalPerformance {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    @TableField("security_id")
    private String securityId;
    
    @TableField("calendar_year")
    private Integer calendarYear;
    
    @TableField("return_value")
    private BigDecimal returnValue;
    
    @TableField("created_at")
    private LocalDateTime createdAt;
    
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
