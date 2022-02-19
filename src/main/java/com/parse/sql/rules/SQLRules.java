package com.parse.sql.rules;

public enum SQLRules {

    function_cast("fun001", "使用cast将str转成int有风险，请确保str内容不为空或null或无脏数据","cast(name bigint)>10 or name::20<8"),

    function_field("fun002", "在字段上使用函数，该字段将不会走索引，请谨慎使用","to_char(n_time, 'yyyy-MM-dd')>'2021-01-10'"),

    condition_like("con002", "使用like加通配符，该条件可能不会走索引，请谨慎使用","name like '%apple%'"),

    condition_not("con003", "使用not null或not in，该条件将不会走索引，请谨慎使用","name is not null or name not in ('a','b')"),

    condition_or("con004", "使用or会存在性能问题，尝试用union或者in改写","name = 'zs' or age =10 ");

    private String ruleName;

    private String ruleContent;

    private String ruleCase;

    SQLRules(String ruleName, String ruleContent, String ruleCase) {
        this.ruleName = ruleName;
        this.ruleContent = ruleContent;
        this.ruleCase = ruleCase;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleContent() {
        return ruleContent;
    }

    public void setRuleContent(String ruleContent) {
        this.ruleContent = ruleContent;
    }

    public String getRuleCase() {
        return ruleCase;
    }

    public void setRuleCase(String ruleCase) {
        this.ruleCase = ruleCase;
    }
}
