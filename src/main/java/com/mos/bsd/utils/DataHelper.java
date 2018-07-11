package com.mos.bsd.utils;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;

import oracle.jdbc.OracleTypes;

/**
 * @className: DataHelper
 * @description: 取数据公共类
 * @author jeff
 * @date 2016年10月24日 上午11:14:20
 * @version 1.0
 */
public class DataHelper {
    /**
     * 获取单据系统自编号（Bill_ID）
     * 
     * @param jdbcTemplate:数据库连接
     * @param userKey:用户Key
     * @param dateValue:日期格式的长整型(yyyyMMdd)
     * @param tableCode:表名
     * @return 返回单据编号: yyyyMMdd-XXXX-0001
     */
    public static String getBillUserId(JdbcTemplate jdbcTemplate, String useKey, long dateValue, String tableCode) {
        return (String) jdbcTemplate.execute(new CallableStatementCreator() {
            public CallableStatement createCallableStatement(Connection con) throws SQLException {
                String storedProc = "{CALL PKG_DRP_GETID.GetBillUserId(?, ?, ?, ?)}";// 调用的sql
                CallableStatement cs = con.prepareCall(storedProc);
                cs.setLong(1, dateValue);// 设置输入参数的值
                cs.setString(2, useKey);
                cs.setString(3, tableCode);
                cs.registerOutParameter(4, OracleTypes.VARCHAR);// 注册输出参数的类型
                return cs;
            }
        }, new CallableStatementCallback<Object>() {
            public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                cs.execute();
                return cs.getString(4);// 获取输出参数的值
            }
        });
    }

    /**
     * 获取单据系统编号
     * 
     * @param jdbcTemplate:数据库连接
     * @param dateValue:日期格式的长整型(yyyyMMdd)
     * @param tableCode:表名
     * @return 返回单据编号
     */
    public static String getBillSystemId(JdbcTemplate jdbcTemplate, long dateValue, String tableCode) {
        return getBillSystemId(jdbcTemplate, dateValue, tableCode, 1);
    }

    /**
     * 获取单据系统编号(按指定步长)
     * 
     * @param jdbcTemplate:数据库连接
     * @param dateValue:日期格式的长整型(yyyyMMdd)
     * @param tableCode:表名
     * @param step:步长
     * @return 返回单据编号
     */
    public static String getBillSystemId(JdbcTemplate jdbcTemplate, long dateValue, String tableCode, int step) {
        return (String) jdbcTemplate.execute(new CallableStatementCreator() {
            public CallableStatement createCallableStatement(Connection con) throws SQLException {
                String storedProc = "{CALL PKG_DRP_GETID.GetBillSystemId(?, ?, ?, ?)}";// 调用的sql
                CallableStatement cs = con.prepareCall(storedProc);
                cs.setLong(1, dateValue);// 设置输入参数的值
                cs.setString(2, tableCode);
                cs.setInt(4, step);
                cs.registerOutParameter(3, OracleTypes.VARCHAR);// 注册输出参数的类型
                return cs;
            }
        }, new CallableStatementCallback<Object>() {
            public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                cs.execute();
                return cs.getString(3);// 获取输出参数的值
            }
        });
    }

}
