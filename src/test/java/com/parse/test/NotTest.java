package com.parse.test;

import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.parse.sql.SQLParseServer;
import com.parse.sql.manager.LikeManager;
import com.parse.sql.manager.Manager;
import com.parse.sql.manager.NotManager;
import com.parse.sql.repository.SQLCondition;

import java.util.List;

public class NotTest {

    public static void main(String[] args) {
        String sql= "select m.name,m.age,n.score from student m left join stu_score n left join aaaaa a on m.id=n.id where  m.name  not in ('a') ";

        SchemaStatVisitor pgSchemaStatVisitor = SQLParseServer.getSchemaStaV(sql);
        List<SQLCondition> selfConditions = pgSchemaStatVisitor.getSelfConditions();

        Manager notSuggest = new NotManager();
        if (notSuggest.getType(selfConditions)){
            System.out.println(notSuggest.getSuggest(selfConditions));
        }
    }
}
