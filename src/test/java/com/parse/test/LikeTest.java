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
        String sql= "WITH supinfo AS (\n" +
                "\tSELECT A\n" +
                "\t\t.erp_code,\n" +
                "\t\tA.spu_code,\n" +
                "\t\tA.tenant_id,\n" +
                "\t\tA.spu_flag,\n" +
                "\t\tA.sku_title,\n" +
                "\t\tA.barcode,\n" +
                "\t\tA.sku_no,\n" +
                "\t\tA.sku_unit,\n" +
                "\t\tA.sku_spec_model,\n" +
                "\t\tA.sku_purchase_price,\n" +
                "\t\tA.sku_retail_price,\n" +
                "\t\tA.erp_cate_code_last,\n" +
                "\t\tA.erp_cate_name_last,\n" +
                "\t\tA.brand_id,\n" +
                "\t\tA.colour,\n" +
                "\t\tA.SIZE,\n" +
                "\t\tA.brand_name,\n" +
                "\t\tA.member_price,\n" +
                "\t\tA.whole_sale_price,\n" +
                "\t\tA.distribution_price,\n" +
                "\t\tA.erp_cate_name1,\n" +
                "\t\tA.erp_cate_name2,\n" +
                "\t\tA.erp_cate_name3,\n" +
                "\t\tA.erp_cate_name4,\n" +
                "\t\tA.erp_cate_name5,\n" +
                "\t\tA.sku_season_desc,\n" +
                "\t\tA.sku_market_years,\n" +
                "\t\tto_char( A.sku_add_time, 'YYYY-MM-DD' ) sku_add_time,\n" +
                "\t\tA.workstatecode,\n" +
                "\t\tb.management_state_name \n" +
                "\tFROM\n" +
                "\t\tdim_goods_sku_brand_cate\n" +
                "\t\tA LEFT JOIN tb_cms_management_state b ON A.tenant_id = b.tenant_id \n" +
                "\t\tAND A.workstatecode = b.management_state_no \n" +
                "\tWHERE\n" +
                "\t\tA.tenant_id = '151323' \n" +
                "\t\tAND A.spu_flag IN ( 0, 3 ) \n" +
                "\t\tAND A.barcode IN ( '6941174496753' ) or A.erp_code IN ( SELECT erpcode FROM tb_cms_sku_packrate WHERE shard_key = '151323' AND barcode IN ( '6941174496753' ) ) \n" +
                "\t) SELECT T\n" +
                "\t.dept_code,\n" +
                "\tT.dept_name,\n" +
                "\tT.sku_goods_code,\n" +
                "\tT.attr_name1,\n" +
                "\tT.attr_name2,\n" +
                "\tT.bill_number,\n" +
                "\tT.spu_goods_code,\n" +
                "\tT.goods_name,\n" +
                "\tT.bar_code,\n" +
                "\tT.goods_no,\n" +
                "\tT.basic_unit,\n" +
                "\tT.goods_spec,\n" +
                "\tT.busi_type_name,\n" +
                "\tT.busi_type,\n" +
                "\tT.occur_date,\n" +
                "\tT.category_code,\n" +
                "\tT.category_name,\n" +
                "\tT.brand_code,\n" +
                "\tT.brand_name,\n" +
                "\tT.supplier_code,\n" +
                "\tT.supplier_name,\n" +
                "\tT.remark,\n" +
                "\tT.member_price,\n" +
                "\tT.whole_sale_price,\n" +
                "\tT.distribution_price,\n" +
                "\tT.erp_cate_name1,\n" +
                "\tT.erp_cate_name2,\n" +
                "\tT.erp_cate_name3,\n" +
                "\tT.erp_cate_name4,\n" +
                "\tT.erp_cate_name5,\n" +
                "\tT.sku_season_desc,\n" +
                "\tT.sku_market_years,\n" +
                "\tT.sku_add_time,\n" +
                "\tT.workstatecode,\n" +
                "\tT.management_state_name,\n" +
                "\t(\n" +
                "\tCASE\n" +
                "\t\t\t\n" +
                "\t\t\tWHEN T.busi_type_name = '成本调整单' THEN\n" +
                "\t\t\t0 \n" +
                "\t\t\tWHEN T.busi_type_name = '退补单' THEN\n" +
                "\t\t\t0 \n" +
                "\t\t\tWHEN T.busi_type_name = '盘点-盘升单' THEN\n" +
                "\t\t\tSUM ( T.amount ) \n" +
                "\t\t\tWHEN T.busi_type_name = '盘点-盘损单' THEN\n" +
                "\t\t\t0-sum ( T.amount ) ELSE SUM ( T.amount ) \n" +
                "\t\tEND \n" +
                "\t\t) AS amount,\n" +
                "\t\tSUM ( T.purch_price ) purch_price,\n" +
                "\t\t(\n" +
                "\t\tCASE\n" +
                "\t\t\t\t\n" +
                "\t\t\t\tWHEN T.busi_type_name = '盘点-盘升单' THEN\n" +
                "\t\t\t\tSUM ( T.purch_money ) \n" +
                "\t\t\t\tWHEN T.busi_type_name = '盘点-盘损单' THEN\n" +
                "\t\t\t\t0-sum ( T.purch_money ) ELSE SUM ( T.purch_money ) \n" +
                "\t\t\tEND \n" +
                "\t\t\t) AS purch_money,\n" +
                "\t\t\tSUM ( T.sku_purchase_price ) sku_purchase_price,\n" +
                "\t\t\tSUM ( T.purchase_price_amount ) purchase_price_amount,\n" +
                "\t\t\tSUM ( T.sku_retail_price ) sku_retail_price,\n" +
                "\t\t\t(\n" +
                "\t\t\tCASE\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\tWHEN T.amount > 0 THEN\n" +
                "\t\t\t\t\tSUM ( T.the_price_amount ) \n" +
                "\t\t\t\t\tWHEN T.amount < 0 \n" +
                "\t\t\t\t\tAND SUM ( T.the_price_amount ) > 0 THEN\n" +
                "\t\t\t\t\t\t0-sum ( T.the_price_amount ) ELSE SUM ( T.the_price_amount ) \n" +
                "\t\t\t\t\tEND \n" +
                "\t\t\t\t\t) AS the_price_amount,\n" +
                "\t\t\t\t\tSUM ( T.tax_cost_price ) AS tax_cost_price,\n" +
                "\t\t\t\t\t(\n" +
                "\t\t\t\t\tCASE\n" +
                "\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\tWHEN T.busi_type_name = '盘点-盘升单' THEN\n" +
                "\t\t\t\t\t\t\tSUM ( T.tax_cost_money ) \n" +
                "\t\t\t\t\t\t\tWHEN T.busi_type_name = '盘点-盘损单' THEN\n" +
                "\t\t\t\t\t\t\t0-sum ( T.tax_cost_money ) ELSE SUM ( T.tax_cost_money ) \n" +
                "\t\t\t\t\t\tEND \n" +
                "\t\t\t\t\t\t) AS tax_cost_money,\n" +
                "\t\t\t\t\t\t(\n" +
                "\t\t\t\t\t\tCASE\n" +
                "\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\tWHEN T.busi_type_name = '盘点-盘升单' THEN\n" +
                "\t\t\t\t\t\t\t\tSUM ( T.no_tax_cost_money ) \n" +
                "\t\t\t\t\t\t\t\tWHEN T.busi_type_name = '盘点-盘损单' THEN\n" +
                "\t\t\t\t\t\t\t\t0-sum ( T.no_tax_cost_money ) ELSE SUM ( T.no_tax_cost_money ) \n" +
                "\t\t\t\t\t\t\tEND \n" +
                "\t\t\t\t\t\t\t) AS no_tax_cost_money,\n" +
                "\t\t\t\t\t\t\tSUM ( T.no_tax_cost_price ) no_tax_cost_price \n" +
                "\t\t\t\t\t\tFROM\n" +
                "\t\t\t\t\t\t\t(\n" +
                "\t\t\t\t\t\t\tSELECT\n" +
                "\t\t\t\t\t\t\t\t(\n" +
                "\t\t\t\t\t\t\t\tCASE\n" +
                "\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\tWHEN ( t1.busi_type = 12 OR t1.busi_type = 13 ) \n" +
                "\t\t\t\t\t\t\t\t\t\tAND t1.bill_type != 1 THEN\n" +
                "\t\t\t\t\t\t\t\t\t\t\tt1.insideId ELSE 0 \n" +
                "\t\t\t\t\t\t\t\t\t\tEND \n" +
                "\t\t\t\t\t\t\t\t\t\t) insideId,\n" +
                "\t\t\t\t\t\t\t\t\t\tt1.dept_code,\n" +
                "\t\t\t\t\t\t\t\t\t\tt2.dept_name,\n" +
                "\t\t\t\t\t\t\t\t\t\tt1.bill_number,\n" +
                "\t\t\t\t\t\t\t\t\t\tt3.spu_code AS spu_goods_code,\n" +
                "\t\t\t\t\t\t\t\t\t\tt3.erp_code AS sku_goods_code,\n" +
                "\t\t\t\t\t\t\t\t\t\tt3.sku_title AS goods_name,\n" +
                "\t\t\t\t\t\t\t\t\t\tt3.barcode AS bar_code,\n" +
                "\t\t\t\t\t\t\t\t\t\tt3.sku_no AS goods_no,\n" +
                "\t\t\t\t\t\t\t\t\t\tt3.sku_unit AS basic_unit,\n" +
                "\t\t\t\t\t\t\t\t\t\tt3.sku_spec_model AS goods_spec,\n" +
                "\t\t\t\t\t\t\t\t\tCASE\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\tWHEN t1.busi_type = 0 \n" +
                "\t\t\t\t\t\t\t\t\t\t\tOR t1.busi_type = 2 THEN\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t'采购验收单' \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\tWHEN t1.busi_type = 1 \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\tOR t1.busi_type = 3 THEN\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t'验收退货单' \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN t1.busi_type = 4 THEN\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t'商品拨入单' \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN t1.busi_type = 5 \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\tOR t1.busi_type = 6 THEN\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t'商品拨出单' \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN t1.busi_type = 7 THEN\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t'配送退货收货单' \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN ( t1.busi_type = 8 OR t1.busi_type = 9 ) \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\tAND t1.bill_type != 1 THEN\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t'库存调整单' \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN t1.busi_type = 10 THEN\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t'寄存单' \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN t1.busi_type = 11 THEN\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t'寄存提货单' \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN ( t1.busi_type = 12 OR t1.busi_type = 13 ) \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tAND t1.bill_type != 1 THEN\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t'零售单' \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN t1.busi_type = 16 THEN\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t'批销单' \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN t1.busi_type = 17 THEN\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t'批销退货单' \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN t1.busi_type = 14 THEN\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t'成本调整单' \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN t1.busi_type = 8 \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tAND t1.bill_type = 1 THEN\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t'盘点-盘升单' \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN t1.busi_type = 9 \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tAND t1.bill_type = 1 THEN\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t'盘点-盘损单' \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN t1.busi_type = 15 THEN\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t'退补单' \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN t1.busi_type = 99 THEN\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t'库存导入' \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN ( t1.busi_type = 12 OR t1.busi_type = 13 ) \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tAND t1.bill_type = 1 THEN\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t'积分兑换单' ELSE'其他' \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tEND busi_type_name,\n" +
                "\t\t\t\tt1.busi_type,\n" +
                "\t\t\t\tt1.occur_date,\n" +
                "\t\t\t\t(\n" +
                "\t\t\t\tCASE\n" +
                "\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\tWHEN t1.warehouse_type = 1 THEN\n" +
                "\t\t\t\t\t\tROUND( 0-t1.amount * 0.0001, 0 ) ELSE ROUND( t1.amount * 0.0001, 0 ) \n" +
                "\t\t\t\t\tEND \n" +
                "\t\t\t\t\t) AS amount,\n" +
                "\t\t\t\t\tROUND( t1.purch_price * 0.0001, 4 ) AS purch_price,\n" +
                "\t\t\t\t\t(\n" +
                "\t\t\t\t\tCASE\n" +
                "\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\tWHEN t1.warehouse_type = 1 THEN\n" +
                "\t\t\t\t\t\t\tROUND( - t1.purch_money * 0.01, 2 ) ELSE ROUND( t1.purch_money * 0.01, 2 ) \n" +
                "\t\t\t\t\t\tEND \n" +
                "\t\t\t\t\t\t) AS purch_money,\n" +
                "\t\t\t\t\t\tROUND( t3.sku_purchase_price * 0.0001, 4 ) AS sku_purchase_price,\n" +
                "\t\t\t\t\t\tROUND( t1.amount * t3.sku_purchase_price * 0.00000001, 2 ) AS purchase_price_amount,\n" +
                "\t\t\t\t\t\tROUND( t3.sku_retail_price * 0.01, 4 ) AS sku_retail_price,\n" +
                "\t\t\t\t\t\tROUND( t1.amount * t3.sku_retail_price * 0.000001, 2 ) AS the_price_amount,\n" +
                "\t\t\t\t\t\tROUND( t1.tax_cost_price * 0.0001, 4 ) tax_cost_price,\n" +
                "\t\t\t\t\t\t(\n" +
                "\t\t\t\t\t\tCASE\n" +
                "\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\tWHEN t1.warehouse_type = 1 THEN\n" +
                "\t\t\t\t\t\t\t\tROUND( 0-t1.tax_cost_money * 0.01, 2 ) ELSE ROUND( t1.tax_cost_money * 0.01, 2 ) \n" +
                "\t\t\t\t\t\t\tEND \n" +
                "\t\t\t\t\t\t\t) AS tax_cost_money,\n" +
                "\t\t\t\t\t\t\t(\n" +
                "\t\t\t\t\t\t\tCASE\n" +
                "\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\tWHEN t1.warehouse_type = 1 THEN\n" +
                "\t\t\t\t\t\t\t\t\tROUND( - ( t1.tax_cost_money - t1.tax_money ) * 0.01, 2 ) ELSE ROUND( ( t1.tax_cost_money - t1.tax_money ) * 0.01, 2 ) \n" +
                "\t\t\t\t\t\t\t\tEND \n" +
                "\t\t\t\t\t\t\t\t) no_tax_cost_money,\n" +
                "\t\t\t\t\t\t\t\tROUND( ROUND( t1.tax_cost_price * 0.0001, 4 ) / ( 1+tax_rate * 0.0001 ), 4 ) no_tax_cost_price,\n" +
                "\t\t\t\t\t\t\t\tt3.erp_cate_code_last AS category_code,\n" +
                "\t\t\t\t\t\t\t\tt3.erp_cate_name_last AS category_name,\n" +
                "\t\t\t\t\t\t\t\tt3.brand_id AS brand_code,\n" +
                "\t\t\t\t\t\t\t\tt3.brand_name,\n" +
                "\t\t\t\t\t\t\t\tt4.supplier_code,\n" +
                "\t\t\t\t\t\t\t\tt4.supplier_name,\n" +
                "\t\t\t\t\t\t\t\tt3.colour attr_name1,\n" +
                "\t\t\t\t\t\t\t\tt3.SIZE attr_name2,\n" +
                "\t\t\t\t\t\t\t\tt1.remark,\n" +
                "\t\t\t\t\t\t\t\tROUND( t3.member_price * 0.01, 4 ) member_price,\n" +
                "\t\t\t\t\t\t\t\tROUND( t3.whole_sale_price * 0.01, 4 ) whole_sale_price,\n" +
                "\t\t\t\t\t\t\t\tROUND( t3.distribution_price * 0.01, 4 ) distribution_price,\n" +
                "\t\t\t\t\t\t\t\tt3.erp_cate_name1,\n" +
                "\t\t\t\t\t\t\t\tt3.erp_cate_name2,\n" +
                "\t\t\t\t\t\t\t\tt3.erp_cate_name3,\n" +
                "\t\t\t\t\t\t\t\tt3.erp_cate_name4,\n" +
                "\t\t\t\t\t\t\t\tt3.erp_cate_name5,\n" +
                "\t\t\t\t\t\t\t\tt3.sku_season_desc,\n" +
                "\t\t\t\t\t\t\t\tt3.sku_market_years,\n" +
                "\t\t\t\t\t\t\t\tt3.sku_add_time,\n" +
                "\t\t\t\t\t\t\t\tt3.workstatecode,\n" +
                "\t\t\t\t\t\t\t\tt3.management_state_name \n" +
                "\t\t\t\t\t\t\tFROM\n" +
                "\t\t\t\t\t\t\t\tbb_erp_dept_goods_detail t1\n" +
                "\t\t\t\t\t\t\t\tLEFT JOIN dim_tenant_dept t2 ON t2.tenant_id = t1.tenant_id \n" +
                "\t\t\t\t\t\t\t\tAND t2.dept_code = t1.dept_code\n" +
                "\t\t\t\t\t\t\t\tINNER JOIN supinfo t3 ON t3.tenant_id = t1.tenant_id \n" +
                "\t\t\t\t\t\t\t\tAND t3.erp_code = t1.goods_code\n" +
                "\t\t\t\t\t\t\t\tLEFT JOIN bb_erp_supp_goods_dept_d_to_u_rt t4 ON t4.shard_key = '151323' \n" +
                "\t\t\t\t\t\t\t\tAND t4.shard_key = t1.shard_key \n" +
                "\t\t\t\t\t\t\t\tAND t4.dept_code = t1.dept_code \n" +
                "\t\t\t\t\t\t\t\tAND t4.goods_code = t1.goods_code \n" +
                "\t\t\t\t\t\t\tWHERE\n" +
                "\t\t\t\t\t\t\t\tt1.shard_key = '151323' \n" +
                "\t\t\t\t\t\t\t\tAND t3.spu_flag IN ( 0, 3 ) \n" +
                "\t\t\t\t\t\t\t\tAND t1.dept_code IN ( '0007' ) \n" +
                "\t\t\t\t\t\t\t\tAND 1 = 1 \n" +
                "\t\t\t\t\t\t\t\tAND to_char(t1.occur_date,'yymmdd') <= '20220219' \n" +
                "\t\t\t\t\t\t\t\tAND '20220101' <= t1.occur_date \n" +
                "\t\t\t\t\t\t\t) T \n" +
                "\t\t\t\t\t\tWHERE\n" +
                "\t\t\t\t\t\t\t1 = 1 \n" +
                "\t\t\t\t\t\tGROUP BY\n" +
                "\t\t\t\t\t\t\tT.dept_code,\n" +
                "\t\t\t\t\t\t\tT.dept_name,\n" +
                "\t\t\t\t\t\t\tT.bill_number,\n" +
                "\t\t\t\t\t\t\tT.spu_goods_code,\n" +
                "\t\t\t\t\t\t\tT.sku_goods_code,\n" +
                "\t\t\t\t\t\t\tT.attr_name1,\n" +
                "\t\t\t\t\t\t\tT.attr_name2,\n" +
                "\t\t\t\t\t\t\tT.goods_name,\n" +
                "\t\t\t\t\t\t\tT.bar_code,\n" +
                "\t\t\t\t\t\t\tT.amount,\n" +
                "\t\t\t\t\t\t\tT.goods_no,\n" +
                "\t\t\t\t\t\t\tT.basic_unit,\n" +
                "\t\t\t\t\t\t\tT.goods_spec,\n" +
                "\t\t\t\t\t\t\tT.busi_type_name,\n" +
                "\t\t\t\t\t\t\tT.busi_type,\n" +
                "\t\t\t\t\t\t\tT.occur_date,\n" +
                "\t\t\t\t\t\t\tT.category_code,\n" +
                "\t\t\t\t\t\t\tT.category_name,\n" +
                "\t\t\t\t\t\t\tT.brand_code,\n" +
                "\t\t\t\t\t\t\tT.brand_name,\n" +
                "\t\t\t\t\t\t\tT.supplier_code,\n" +
                "\t\t\t\t\t\t\tT.supplier_name,\n" +
                "\t\t\t\t\t\t\tT.no_tax_cost_price,\n" +
                "\t\t\t\t\t\t\tT.remark,\n" +
                "\t\t\t\t\t\t\tT.member_price,\n" +
                "\t\t\t\t\t\t\tT.whole_sale_price,\n" +
                "\t\t\t\t\t\t\tT.distribution_price,\n" +
                "\t\t\t\t\t\t\tT.erp_cate_name1,\n" +
                "\t\t\t\t\t\t\tT.erp_cate_name2,\n" +
                "\t\t\t\t\t\t\tT.erp_cate_name3,\n" +
                "\t\t\t\t\t\t\tT.erp_cate_name4,\n" +
                "\t\t\t\t\t\t\tT.erp_cate_name5,\n" +
                "\t\t\t\t\t\t\tT.sku_season_desc,\n" +
                "\t\t\t\t\t\t\tT.sku_market_years,\n" +
                "\t\t\t\t\t\t\tT.sku_add_time,\n" +
                "\t\t\t\t\t\t\tT.workstatecode,\n" +
                "\t\t\t\t\t\t\tT.insideId,\n" +
                "\t\t\t\t\t\t\tT.management_state_name \n" +
                "\t\t\t\t\t\tORDER BY\n" +
                "\t\t\t\t\t\toccur_date DESC \n" +
                "\tLIMIT 1000 OFFSET 0";

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
