# mallModules

>商城模块化开发项目，方便后端快速开发
>
>将商城中通用模块抽出进行复用

### 模块

#### commonMallModule 

> 通用模块，包含商品，普通订单，用户地址，优惠券，评价，购物车，售后等通用模块

#### fightGroupModule

> 拼团模块

#### spikeModule

> 秒杀模块

### 分支计划

#### master

>  主分支更新维护 1.6-SNAPSHOT
>

#### dev

> dev 分支开发 1.7-SNAPSHOT

#### 稳定版已发布分支

```xml
<dependency>
  <groupId>com.github.chenlijia1111</groupId>
  <artifactId>commonMallModule</artifactId>
  <version>1.6-RELEASE</version>
</dependency>
```

#### 快照版已发布分支

```xml
<dependency>
  <groupId>com.github.chenlijia1111</groupId>
  <artifactId>commonMallModule</artifactId>
  <version>1.7-SNAPSHOT</version>
</dependency>
```

### 修改记录

#### 2020-12-03

新增商品标签价格，方便商品价格无限扩展，如市场价，vip 价，黄金用户价，白银用户价

下单时支持传入特定的标签，下单优先使用传入标签获取标签价格，如果没传，使用商品的标准价


