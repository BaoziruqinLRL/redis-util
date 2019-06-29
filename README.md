# redis-util
redis util tool
### 该工具是一个单机项目下的redis工具，默认是连接localhost:6079下的redis服务，对于单机项目可以开箱即用
依赖方式，pom文件添加依赖
<dependency>
    <groupId>com.baozi.commons.redis</groupId>
    <artifactId>redis-util</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>

### 使用方式
目前实现了5种redis接口  
1.HashRedisHandler  
- 使用hash结构存储数据，封装基本常用的接口
2.ListRedisHandler  
- 使用list结构存储数据，封装基本接口，包括用于队列操作的push和pop
3.ObjectRedisHandler  
- 使用String结构存储数据，比较常用的数据结构，封装了基本接口
4.SessionRedisHandler  
- 仅有一个实现方法，根据sessionId获取redis-session数据，存储封装在[redis-session](https://github.com/BaoziruqinLRL/redis-session)工具包中
5.ZSetRedisHandler  
- 一个有序映射结构，基于分值实现排序，封装基本接口

使用使可通过注入接口来使用
@Resource
private H 
