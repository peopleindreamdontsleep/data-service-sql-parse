package com.parse.sql.manager;

import com.parse.sql.common.Constants;
import com.parse.sql.repository.SQLCondition;
import com.parse.sql.rules.SQLRules;

import java.util.List;

/**
 * like不走索引，存在一定的风险
 */
public class LikeManager implements Manager {

    @Override
    public  String getSuggest(List<SQLCondition> cons) {
        for (SQLCondition eachCon:cons) {
            if (eachCon.getOperator().contains(Constants.PG_LIKE)){
                List<Object> values = eachCon.getValues();
                if (values.toString().contains(Constants.PG_PER)){
                    return SQLRules.condition_like_true.getRuleContent();
                }else{
                    return SQLRules.condition_like_false.getRuleContent();
                }
            }
        }
        return "";
    }

    @Override
    public String getOperate(List<SQLCondition> cons) {
        return null;
    }

    @Override
    public String getLeft(List<SQLCondition> cons) {
        return null;
    }

    @Override
    public String getRight(List<SQLCondition> cons) {
        return null;
    }

    @Override
    public boolean getType(List<SQLCondition> cons) {
        for (SQLCondition eachCon:cons) {
            if (eachCon.getOperator().contains(Constants.PG_LIKE)){
                return true;
            }
        }
        return false;
    }
}
