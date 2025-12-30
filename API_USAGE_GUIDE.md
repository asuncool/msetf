# API使用说明 - 解决JSON数组解析问题

## 问题描述

如果您在调用 `/api/etf/parse` 端点时遇到以下错误：

```
JSON parse error: Cannot deserialize value of type `com.example.etf.dto.MorningstarETFResponse` 
from Array value (token `JsonToken.START_ARRAY`)
```

这表明您的API返回的是JSON数组格式 `[{...}, {...}]`，而不是单个对象 `{...}`。

## 解决方案

我们提供了两个端点来支持不同的数据格式：

### 1. 单个ETF数据 (对象格式)

**端点:** `POST /api/etf/parse`

**请求数据格式:**
```json
{
  "secId": "FOUSA00FS1",
  "name": "Vanguard 500 Index Fund",
  ...
}
```

**使用示例:**
```bash
curl -X POST http://localhost:8080/api/etf/parse \
  -H "Content-Type: application/json" \
  -d @sample-data.json
```

### 2. 批量ETF数据 (数组格式)

**端点:** `POST /api/etf/parse/batch`

**请求数据格式:**
```json
[
  {
    "secId": "FOUSA00FS1",
    "name": "Vanguard 500 Index Fund",
    ...
  },
  {
    "secId": "FOUSA00FS2",
    "name": "Another ETF Fund",
    ...
  }
]
```

**使用示例:**
```bash
curl -X POST http://localhost:8080/api/etf/parse/batch \
  -H "Content-Type: application/json" \
  -d @sample-data-batch.json
```

**响应示例:**
```json
{
  "success": true,
  "total": 2,
  "successCount": 2,
  "failedCount": 0,
  "successIds": ["FOUSA00FS1", "FOUSA00FS2"]
}
```

## 如何选择使用哪个端点？

1. **检查您的数据格式:**
   - 如果以 `{` 开头 → 使用 `/api/etf/parse`
   - 如果以 `[` 开头 → 使用 `/api/etf/parse/batch`

2. **查看API响应:**
   ```bash
   # 检查Morningstar API返回的数据格式
   curl https://your-morningstar-api-endpoint | jq '.' | head -1
   ```

## 测试脚本

运行提供的测试脚本可以测试两个端点：

```bash
chmod +x test-api.sh
./test-api.sh
```

该脚本会依次测试：
1. 健康检查端点
2. 单个ETF解析
3. 批量ETF解析

## 批量处理的优势

使用批量端点的好处：

1. **性能更好**: 一次请求处理多个ETF数据
2. **事务管理**: 每个ETF独立处理，一个失败不影响其他
3. **详细反馈**: 返回成功和失败的详细信息
4. **错误恢复**: 可以针对失败的记录单独重试

## 注意事项

- 批量端点支持部分成功，即使某些记录失败，其他记录仍会成功保存
- 每个ETF的 `securityId` 必须唯一且非空
- 数据去重逻辑会自动更新已存在的记录

## 错误排查

如果仍然遇到问题：

1. **检查Content-Type**: 确保设置为 `application/json`
2. **验证JSON格式**: 使用 `jq` 或在线工具验证JSON格式
3. **查看日志**: 检查应用日志获取详细错误信息
4. **测试健康端点**: 确保服务正常运行
   ```bash
   curl http://localhost:8080/api/etf/health
   ```
