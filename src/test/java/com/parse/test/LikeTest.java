package com.parse.test;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.postgresql.visitor.PGSchemaStatVisitor;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.util.JdbcConstants;
import com.parse.sql.SQLParseServer;
import com.parse.sql.manager.CastManager;
import com.parse.sql.manager.LikeManager;
import com.parse.sql.manager.Manager;
import com.parse.sql.repository.SQLCondition;

import java.util.List;

public class LikeTest {

    public static void main(String[] args) {
        String sql= "select * from (select m.name,m.age,n.score from student m left join stu_score n left join aaaaa a on m.id=n.id where  m.age=20 and m.age like '%20')x left join bbbb b on x.name=b.name; ";

        SchemaStatVisitor pgSchemaStatVisitor = SQLParseServer.getSchemaStaV(sql);
        List<SQLCondition> selfConditions = pgSchemaStatVisitor.getSelfConditions();

        if (pgSchemaStatVisitor.has_like){
            Manager likeSuggest = new LikeManager();
            System.out.println(likeSuggest.getSuggest(selfConditions));
        }
    }
}
