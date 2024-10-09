package linketinder.util

import groovy.sql.Sql
import groovy.transform.CompileStatic

import java.sql.SQLException

@CompileStatic
class SqlFactory {
    static Sql newInstance() throws SQLException, ClassNotFoundException {
        final String url = 'jdbc:postgresql://localhost:5432/linketinder'

        final String user = 'andre'

        final String password = '020917'

        final String driver = 'org.postgresql.Driver'

        return Sql.newInstance(url, user, password, driver)
    }
}
