package com.parse.test;

import com.alibaba.druid.sql.ast.expr.SQLMethodInvokeExpr;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.parse.sql.SQLParseServer;
import com.parse.sql.manager.CastManager;
import com.parse.sql.manager.FunManager;
import com.parse.sql.manager.LikeManager;
import com.parse.sql.manager.Manager;
import com.parse.sql.repository.SQLCondition;

import java.util.List;

public class FunFieldTest {

    public static void main(String[] args) {
        String sql= "select * from (select m.name,to_char(n_time,'yyyy-MM-dd') from student m left join stu_score n left join aaaaa a on m.id=n.id where concat(N_time,',','1')='2021-01-10' and  m.name=10 and m.name  not in ('a') )x left join bbbb b on x.name=b.name; ";

        SchemaStatVisitor pgSchemaStatVisitor = SQLParseServer.getSchemaStaV(sql);
        List<SQLCondition> selfConditions = pgSchemaStatVisitor.getSelfConditions();
        List<SQLMethodInvokeExpr> functions = pgSchemaStatVisitor.getFunctions();

        Manager funSuggest = new FunManager();

        System.out.println(funSuggest.hasType(functions));
        System.out.println(funSuggest.getSuggest(""));


    }
}
