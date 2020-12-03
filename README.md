# mallModules

>商城模块化开发项目，方便后端快速开发
>
>将商城中通用模块抽出进行复用

### 模块

#### generalModule

> 通用模块，封装了普通系统的一些常用功能
> 验证码记录，角色权限管理，文件上传下载，系统配置等

#### commonMallModule 

> 通用商城模块，包含商品，普通订单，用户地址，优惠券，评价，购物车，售后等通用模块

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

新增产品快照表，每次添加商品或者修改商品都会将产品快照信息存入。因为之前直接将产品快照存入订单表中，导致大量产品冗余信息，订单表内容过大。喜餐1万条数据就占用了19M。

