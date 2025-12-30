package com.example.etf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * ETF基准信息实体类
 */
@Data
@TableName("etf_benchmark")
public class ETFBenchmark {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    @TableField("security_id")
    private String securityId;
    
    @TableField("benchmark_name")
    private String benchmarkName;
    
    @TableField("benchmark_id")
    private String benchmarkId;
    
    @TableField("primary_benchmark")
    private Boolean primaryBenchmark;
    
    @TableField("created_at")
    private LocalDateTime createdAt;
    
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
