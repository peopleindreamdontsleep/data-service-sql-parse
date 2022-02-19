package com.parse.sql.manager;

import com.alibaba.druid.sql.ast.expr.SQLMethodInvokeExpr;
import com.parse.sql.SQLParseServer;
import com.parse.sql.common.Constants;
import com.parse.sql.repository.SQLCondition;
import com.parse.sql.rules.SQLRules;

import java.util.List;
import java.util.Locale;

/**
 * function_field
 * 字段上面加函数，不走索引警告
 */
public class FunManager implements Manager {
    @Override
    public String getSuggest(Object cons) {
        return SQLRules.function_field.getRuleContent();
    }

    @Override
    public String getOperate(Object cons) {
        return null;
    }

    @Override
    public String getLeft(Object cons) {
        return null;
    }

    @Override
    public String getRight(Object cons) {
        return null;
    }

    @Override
    public boolean hasType(Object cons) {

        List<SQLMethodInvokeExpr> functions = (List<SQLMethodInvokeExpr>) cons;

        for (String s:SQLParseServer.sqlStrList) {
            for (SQLMethodInvokeExpr t:functions) {
                //确保函数不是在case when上
                if (s.contains(t.toString()) && s.contains("=") && !s.toLowerCase(Locale.ROOT).contains("when")){
                    return true;
                }
            }
        }
        return false;
    }
}
