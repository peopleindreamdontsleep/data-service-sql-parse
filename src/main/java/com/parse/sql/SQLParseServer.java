package com.parse.sql;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.postgresql.visitor.PGSchemaStatVisitor;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.util.JdbcConstants;
import com.parse.sql.repository.SQLCondition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLParseServer {

    public static List<String> sqlStrList;

    public static SchemaStatVisitor getSchemaStaV(String sql){

        String dbType = JdbcConstants.POSTGRESQL;

        List<SQLStatement> statementList = SQLUtils.parseStatements(sql, dbType);

        SQLSelectStatement pgsqlStatement= (SQLSelectStatement)statementList.get(0);

        String pgSQL = pgsqlStatement.getSelect().toString();

        sqlStrList = Arrays.asList(pgSQL.split("\n"));

        PGSchemaStatVisitor pgSchemaStatVisitor = new PGSchemaStatVisitor();

        pgsqlStatement.accept(pgSchemaStatVisitor);

        return pgSchemaStatVisitor;
    }
}
