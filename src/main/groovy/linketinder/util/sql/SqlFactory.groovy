package linketinder.util.sql

import groovy.sql.Sql
import groovy.transform.CompileStatic

import java.sql.SQLException

@CompileStatic
class SqlFactory {
    static synchronized Sql newInstance(ISqlConfig config) throws SQLException, ClassNotFoundException {
        return Sql.newInstance(config.url, config.user, config.password, config.driver)
    }
}
