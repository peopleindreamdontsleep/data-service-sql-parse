package com.parse.sql.manager;

import com.parse.sql.common.Constants;
import com.parse.sql.repository.SQLCondition;
import com.parse.sql.rules.SQLRules;

import java.util.List;

/**
 * condition_like
 * like不走索引，存在一定的风险
 */
public class LikeManager implements Manager {

    @Override
    public  String getSuggest(Object cons) {
        List<SQLCondition> conditions = (List<SQLCondition>)cons;
        for (SQLCondition eachCon:conditions) {
            if (eachCon.getOperator().contains(Constants.PG_LIKE)){

                return SQLRules.condition_like.getRuleContent();

            }
        }
        return "";
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
            if (eachCon.getOperator().contains(Constants.PG_LIKE)){
                return true;
            }
        }
        return false;
    }
}
