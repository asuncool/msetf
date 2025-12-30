package com.example.etf.controller;

import com.example.etf.dto.MorningstarETFResponse;
import com.example.etf.service.ETFDataParserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * ETF数据控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/etf")
public class ETFDataController {
    
    @Autowired
    private ETFDataParserService parserService;
    
    /**
     * 解析并保存ETF数据
     * 
     * @param response Morningstar ETF API 响应数据
     * @return 处理结果
     */
    @PostMapping("/parse")
    public ResponseEntity<Map<String, Object>> parseETFData(@RequestBody MorningstarETFResponse response) {
        log.info("接收到ETF数据解析请求，SecurityId: {}", response.getSecurityId());
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 验证必要字段
            if (response.getSecurityId() == null || response.getSecurityId().trim().isEmpty()) {
                result.put("success", false);
                result.put("message", "SecurityId不能为空");
                return ResponseEntity.badRequest().body(result);
            }
            
            // 调用服务层解析和保存数据
            parserService.parseAndSaveETFData(response);
            
            result.put("success", true);
            result.put("message", "ETF数据解析并保存成功");
            result.put("securityId", response.getSecurityId());
            
            log.info("ETF数据解析请求处理成功，SecurityId: {}", response.getSecurityId());
            
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("ETF数据解析请求处理失败，SecurityId: {}, Error: {}", 
                     response.getSecurityId(), e.getMessage(), e);
            
            result.put("success", false);
            result.put("message", "ETF数据解析失败: " + e.getMessage());
            result.put("securityId", response.getSecurityId());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }
    
    /**
     * 健康检查接口
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> result = new HashMap<>();
        result.put("status", "UP");
        result.put("service", "ETF Data Parser Service");
        return ResponseEntity.ok(result);
    }
}
