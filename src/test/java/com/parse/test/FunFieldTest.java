package com.parse.test;

import com.alibaba.druid.sql.ast.expr.SQLMethodInvokeExpr;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.parse.sql.SQLParseServer;
import com.parse.sql.manager.LikeManager;
import com.parse.sql.manager.Manager;
import com.parse.sql.repository.SQLCondition;

import java.util.List;

public class FunFieldTest {

    public static void main(String[] args) {
        String sql= "select * from (select m.name,m.age,n.score from student m left join stu_score n left join aaaaa a on m.id=n.id where  to_char(n_time,'yyyy-MM-dd')='2021-01-10')x left join bbbb b on x.name=b.name; ";

        SchemaStatVisitor pgSchemaStatVisitor = SQLParseServer.getSchemaStaV(sql);
        List<SQLCondition> selfConditions = pgSchemaStatVisitor.getSelfConditions();
        List<SQLMethodInvokeExpr> functions = pgSchemaStatVisitor.getFunctions();
        System.out.println(functions.toString());


    }
}
