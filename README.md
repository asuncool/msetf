# Morningstar ETF 数据解析系统

一个完整的 Morningstar ETF API 数据解析和存储系统，使用 Spring Boot + MyBatis Plus + PostgreSQL 技术栈实现。

## 项目简介

本项目实现了对 Morningstar ETF API 返回的 JSON 数据进行解析、分类和存储的完整功能。系统能够自动将 ETF 数据拆分成不同的信息块，并存储到 PostgreSQL 数据库的对应表中。

## 技术栈

- **Java**: 11+
- **Spring Boot**: 2.7.18
- **MyBatis Plus**: 3.5.5
- **PostgreSQL**: 12+
- **Lombok**: 简化 Java 代码
- **Jackson**: JSON 数据处理

## 功能特性

### 数据解析模块
- 解析 Morningstar ETF API JSON 响应
- 支持多种日期格式自动识别
- 完善的异常处理和日志记录

### 数据存储模块
- 13 张数据库表结构设计
- 自动去重检查（更新或插入）
- 支持事务管理，保证数据一致性
- 外键关联，级联删除

### RESTful API
- POST `/api/etf/parse` - 接收和解析 ETF 数据
- GET `/api/etf/health` - 健康检查接口

## 数据库表结构

### 1. etf_base_info - ETF基础信息表
存储 ETF 的基本信息，如名称、类型、成立日期、投资策略等。

### 2. etf_risk_rating - 风险评级表
存储 ETF 的风险评分和评级信息。

### 3. etf_benchmark - 基准信息表
存储 ETF 的基准指数信息。

### 4. etf_trailing_performance - 业绩表现表
存储不同时间周期的业绩表现数据。

### 5. etf_historical_performance - 历史业绩表
存储按年度的历史收益数据。

### 6. etf_risk_statistics - 风险统计表
存储各种风险统计指标。

### 7. etf_manager - 基金经理表
存储基金经理信息和任职记录。

### 8. etf_document - 文档信息表
存储相关文档的信息和链接。

### 9. etf_distribution_history - 分红历史表
存储分红记录和详细信息。

### 10. etf_asset_allocation - 资产配置表
存储资产配置比例信息。

### 11. etf_portfolio - 持仓组合表
存储持仓组合的概览信息。

### 12. etf_portfolio_holdings - 持仓明细表
存储具体的持仓明细。

### 13. etf_equity_statistics - 股票统计表
存储股票相关的统计数据。

## 项目结构

```
msetf/
├── src/
│   ├── main/
│   │   ├── java/com/example/etf/
│   │   │   ├── entity/           # 实体类 (13个)
│   │   │   │   ├── ETFBaseInfo.java
│   │   │   │   ├── ETFRiskRating.java
│   │   │   │   ├── ETFBenchmark.java
│   │   │   │   ├── ETFPortfolio.java
│   │   │   │   ├── ETFPortfolioHolding.java
│   │   │   │   ├── ETFTrailingPerformance.java
│   │   │   │   ├── ETFHistoricalPerformance.java
│   │   │   │   ├── ETFRiskStatistics.java
│   │   │   │   ├── ETFManager.java
│   │   │   │   ├── ETFDocument.java
│   │   │   │   ├── ETFDistributionHistory.java
│   │   │   │   ├── ETFAssetAllocation.java
│   │   │   │   └── ETFEquityStatistics.java
│   │   │   ├── mapper/           # Mapper接口 (13个)
│   │   │   ├── dto/              # 数据传输对象
│   │   │   │   └── MorningstarETFResponse.java
│   │   │   ├── service/          # 服务层
│   │   │   │   └── ETFDataParserService.java
│   │   │   ├── controller/       # 控制器
│   │   │   │   └── ETFDataController.java
│   │   │   └── MsetfApplication.java  # 启动类
│   │   └── resources/
│   │       ├── application.yml   # 配置文件
│   │       └── db/
│   │           └── schema.sql    # 数据库脚本
│   └── test/                     # 测试代码
├── pom.xml                       # Maven 配置
├── .gitignore                    # Git 忽略文件
└── README.md                     # 项目文档
```

## 快速开始

### 1. 环境准备

确保已安装以下软件：
- Java 11 或更高版本
- Maven 3.6+
- PostgreSQL 12+

### 2. 数据库配置

创建数据库：
```sql
CREATE DATABASE etf_db;
```

执行数据库脚本：
```bash
psql -U postgres -d etf_db -f src/main/resources/db/schema.sql
```

### 3. 修改配置

编辑 `src/main/resources/application.yml`，修改数据库连接信息：
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/etf_db
    username: postgres
    password: your_password
```

### 4. 编译项目

```bash
mvn clean package
```

### 5. 运行项目

```bash
mvn spring-boot:run
```

或者运行打包后的 jar：
```bash
java -jar target/msetf-1.0.0.jar
```

启动成功后，访问 http://localhost:8080/api/etf/health 检查服务状态。

## API 使用示例

### 解析 ETF 数据

**请求:**
```bash
POST http://localhost:8080/api/etf/parse
Content-Type: application/json

{
  "secId": "FOUSA00FS1",
  "name": "Vanguard 500 Index Fund",
  "legalName": "Vanguard 500 Index Fund ETF Shares",
  "categoryName": "Large Blend",
  "investmentType": "Equity",
  "holdingTypeId": "EQ",
  "inceptionDate": "2010-05-31",
  "currency": {
    "id": "USD",
    "name": "US Dollar"
  },
  "closePrice": 425.35,
  "dayChange": 2.15,
  "investmentStrategy": "The investment seeks to track the performance of the S&P 500 Index...",
  "managementCompany": "The Vanguard Group, Inc.",
  "riskRating": {
    "effectiveDate": "2024-01-01",
    "riskScore": 3.5,
    "riskRating": "Average",
    "riskScale": 5
  },
  "trailingPerformance": [
    {
      "period": "1Y",
      "return": {
        "type": "NAV",
        "value": 15.23
      },
      "asOfDate": "2024-12-30"
    }
  ],
  "historicalPerformance": [
    {
      "year": 2023,
      "return": 26.29
    }
  ],
  "managers": [
    {
      "name": "John Smith",
      "startDate": "2010-05-31",
      "tenure": 13.5,
      "biography": "John has over 20 years of investment experience..."
    }
  ],
  "distributions": [
    {
      "distributionDate": "2024-03-20",
      "exDate": "2024-03-18",
      "recordDate": "2024-03-19",
      "payableDate": "2024-03-21",
      "amount": 1.25,
      "type": "Dividend",
      "reinvestPrice": 420.50
    }
  ],
  "portfolio": {
    "date": "2024-12-30",
    "totalHoldings": 505,
    "netAssets": 850000000000,
    "holdings": [
      {
        "securityId": "0P000000GY",
        "name": "Apple Inc",
        "ticker": "AAPL",
        "holdingType": "Equity",
        "weight": 7.15,
        "shares": 180000000,
        "marketValue": 31500000000,
        "sector": "Technology",
        "country": "United States"
      }
    ]
  },
  "benchmarks": [
    {
      "name": "S&P 500 Index",
      "id": "S&P500",
      "isPrimary": true
    }
  ]
}
```

**响应:**
```json
{
  "success": true,
  "message": "ETF数据解析并保存成功",
  "securityId": "FOUSA00FS1"
}
```

### 批量解析 ETF 数据（数组格式）

如果您的API返回的是数组格式的数据，使用批量解析端点：

**请求:**
```bash
POST http://localhost:8080/api/etf/parse/batch
Content-Type: application/json

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

**响应:**
```json
{
  "success": true,
  "total": 2,
  "successCount": 2,
  "failedCount": 0,
  "successIds": ["FOUSA00FS1", "FOUSA00FS2"]
}
```

如果有失败的记录，响应会包含失败详情：
```json
{
  "success": false,
  "total": 2,
  "successCount": 1,
  "failedCount": 1,
  "successIds": ["FOUSA00FS1"],
  "failedItems": [
    {
      "securityId": "FOUSA00FS2",
      "error": "解析错误详情"
    }
  ]
}
```

### 健康检查

**请求:**
```bash
GET http://localhost:8080/api/etf/health
```

**响应:**
```json
{
  "status": "UP",
  "service": "ETF Data Parser Service"
}
```

## 代码特性

### 1. Lombok 注解
使用 `@Data` 注解自动生成 getter、setter、toString 等方法。

### 2. MyBatis Plus
- 继承 `BaseMapper` 获得基础 CRUD 操作
- 使用注解进行表映射
- 支持自动填充时间字段

### 3. 事务管理
使用 `@Transactional` 注解确保数据一致性。

### 4. 数据去重
在插入前检查数据是否存在，存在则更新，不存在则插入。

### 5. 日期解析
支持多种日期格式的自动识别和解析。

### 6. 异常处理
完善的异常捕获和日志记录。

## 开发指南

### 添加新的数据解析逻辑

1. 在 `ETFDataParserService` 中添加新的解析方法
2. 在 `parseAndSaveETFData` 方法中调用新方法
3. 确保添加适当的日志和异常处理

### 数据库迁移

如需修改表结构：
1. 更新 `schema.sql` 文件
2. 更新对应的 Entity 类
3. 测试数据插入和查询

## 常见问题

### Q: 数据库连接失败
A: 检查 PostgreSQL 是否正在运行，以及 `application.yml` 中的连接信息是否正确。

### Q: 日期解析失败
A: 检查日期格式是否符合支持的格式（ISO、yyyy-MM-dd、MM/dd/yyyy、dd/MM/yyyy）。

### Q: 数据重复插入
A: 系统已实现去重逻辑，相同的 security_id 和主要标识字段会触发更新而非插入。

## 性能优化建议

1. **批量操作**: 对于大量数据，可以使用 MyBatis Plus 的批量插入功能
2. **索引优化**: 根据查询需求在数据库中添加合适的索引
3. **连接池**: 调整 HikariCP 连接池参数以适应实际负载
4. **异步处理**: 对于大数据量，可考虑使用异步处理

## 许可证

本项目采用 MIT 许可证。

## 贡献

欢迎提交 Issue 和 Pull Request。

## 联系方式

如有问题，请通过 GitHub Issues 联系。
