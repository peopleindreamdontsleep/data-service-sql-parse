# data-service-sql-parse
使用durid，解析postgresql，生成风险提示，用于SQL优化
主要分为两部分
#### 第一部分：解析SQL语句，根据事先预配的规则生成通用建议（doing）
##### 规则1、使用cast将str转成int有风险，请确保str内容不为空或null或无脏数据 cast(name bigint)>10
##### 规则2、在字段上使用函数，该字段将不会走索引，请谨慎使用 to_char(n_time, 'yyyy-MM-dd')>'2021-01-10'
##### 规则3、不加通配符，like和等号等同 name like 'apple'
##### 规则4、使用like加通配符，该条件将不会走索引，请谨慎使用 name like '%apple%'
##### 规则5、使用not null或not in，该条件将不会走索引，请谨慎使用 name is not null or name not in ('a','b')
#### 第二部分：解析执行计划，根据执行计划的返回结果来给出更加实用性的建议（todo）
