package com.parse.test;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLMethodInvokeExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.postgresql.visitor.PGSchemaStatVisitor;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.util.JdbcConstants;
import com.parse.sql.SQLParseServer;
import com.parse.sql.manager.*;
import com.parse.sql.repository.SQLCondition;

import java.util.List;

import static com.parse.sql.rules.SQLRules.*;

public class LikeTest {

    public static void main(String[] args) {
        String sql= "sql";

        SchemaStatVisitor pgSchemaStatVisitor = SQLParseServer.getSchemaStaV(sql);
        List<SQLCondition> selfConditions = pgSchemaStatVisitor.getSelfConditions();
        List<SQLMethodInvokeExpr> functions = pgSchemaStatVisitor.getFunctions();

        if (pgSchemaStatVisitor.has_like){
            System.out.println(condition_like.getRuleContent());
        }

        if (pgSchemaStatVisitor.has_or){
            System.out.println(condition_or.getRuleContent());
        }

        if (pgSchemaStatVisitor.has_cast){
            System.out.println(function_cast.getRuleContent());
        }

        if (pgSchemaStatVisitor.has_fun){
            System.out.println(function_field.getRuleContent());
        }

        if (pgSchemaStatVisitor.has_not){
            System.out.println(condition_not.getRuleContent());
        }
    }
}
