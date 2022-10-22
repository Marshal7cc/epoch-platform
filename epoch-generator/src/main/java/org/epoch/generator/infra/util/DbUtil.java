package org.epoch.generator.infra.util;

import org.apache.ibatis.session.SqlSession;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Marshal
 * @date 2019/5/18
 */
public class DbUtil {

    private static final String COLUMN_NAME = "COLUMN_NAME";
    private static final Pattern PATTERN = Pattern.compile("[A-Z]([a-z\\d]+)?");

    private DbUtil() {

    }

    public static Connection getConnectionBySqlSession(SqlSession sqlSession) throws SQLException {
        return sqlSession.getConfiguration().getEnvironment().getDataSource().getConnection();
    }

    public static List<String> showAllTables(Connection conn) throws SQLException {
        List<String> tables = new ArrayList<>();
        DatabaseMetaData dbmd = conn.getMetaData();
        String database = conn.getCatalog();
        ResultSet rs = dbmd.getTables(database, null, null, new String[]{"TABLE"});
        while (rs.next()) {
            tables.add(rs.getString("TABLE_NAME"));
        }
        return tables;
    }

    public static ResultSet getTableColumnInfo(String table, DatabaseMetaData dbmd, String catalog)
            throws SQLException {
        return dbmd.getColumns(catalog, null, table, null);
    }

    public static List<String> getNotNullColumn(String table, DatabaseMetaData dbmd, String catalog)
            throws SQLException {
        List<String> result = new ArrayList<>();
        ResultSet rs = dbmd.getColumns(catalog, null, table, null);
        while (rs.next()) {
            if ("NO".equalsIgnoreCase(rs.getString("IS_NULLABLE"))) {
                result.add(rs.getString(DbUtil.COLUMN_NAME));
            }
        }
        return result;
    }

    public static String getPrimaryKey(String table, DatabaseMetaData dbmd, String catalog) throws SQLException {
        String columnPk = null;

        ResultSet rs = dbmd.getPrimaryKeys(catalog, null, table);
        while (rs.next()) {
            columnPk = rs.getString(DbUtil.COLUMN_NAME);
        }
        return columnPk;
    }

    public static void closeConnection(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public static void closeResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
    }

    public static void closeSqlSession(SqlSession sqlSession) throws SQLException {
        if (sqlSession != null) {
            sqlSession.close();
        }
    }

    public static String camel2Underline(String line) {
        if (line == null || "".equals(line)) {
            return "";
        }
        line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
        StringBuffer sb = new StringBuffer();
        Matcher matcher = PATTERN.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(word.toUpperCase());
            sb.append(matcher.end() == line.length() ? "" : "_");
        }
        return sb.toString();
    }
}
