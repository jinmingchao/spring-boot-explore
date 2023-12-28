package explore.springboot.data.transaction.utils;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

//@Component
public class P6SpyLogger implements MessageFormattingStrategy {

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    /**
     * 自定义sql日志打印
     *
     * @param connectionId 连接标识
     * @param now          执行时间
     * @param elapsed      执行秒数ms
     * @param category     statement
     * @param prepared     准备sql语句
     * @param sql          sql语句
     * @param s4           数据库url连接
     * @return {@link String}
     */
    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String s4) {
        System.out.println();
        if (!"".equals(sql.trim())) {
            String sqlBegin = "============== SQL LOGGER BEGIN ==============";

            String sqlExecuteTime = "SQL 执行时间       :" + this.format.format(new Date()) + "\n";
            String elapsedStr = "SQL 执行毫秒       :" + elapsed + "ms" + "\n";
            String sqlPrint = "SQL 执行语句       :" + sql;

            //String sqlPrint = !"".equals(sql.trim()) ? this.format.format(new Date()) + " | took " + elapsed + "ms | " + category + " | connection " + connectionId + "\n " + sql + ";" : "";
            String sqlEnd = "==============  SQL LOGGER END  ==============";

            return sqlBegin + "\r\n" + sqlExecuteTime + elapsedStr + sqlPrint + "\r\n" + sqlEnd;
        }
        return "";
    }
}
