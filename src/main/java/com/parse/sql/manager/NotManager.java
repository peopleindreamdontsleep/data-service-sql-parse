package com.parse.sql.manager;

import com.parse.sql.common.Constants;
import com.parse.sql.repository.SQLCondition;
import com.parse.sql.rules.SQLRules;

import java.util.List;

/**
 * condition_not
 * not不走索引
 */
public class NotManager implements Manager{

    @Override
    public String getSuggest(Object cons) {
        List<SQLCondition> conditions = (List<SQLCondition>)cons;
        for (SQLCondition eachCon:conditions) {

            if (eachCon.getOperator().contains(Constants.PG_NOT)){
               return SQLRules.condition_not.getRuleContent();
            }
        }
        return null;
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
        List<SQLCondition> conditions = (List<SQLCondition>)cons;
        for (SQLCondition eachCon:conditions) {

            if (eachCon.getOperator().contains(Constants.PG_NOT)){
                return true;
            }
        }
        return false;
    }
}
