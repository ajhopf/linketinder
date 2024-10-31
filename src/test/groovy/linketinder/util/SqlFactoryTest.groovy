package linketinder.util

import groovy.sql.Sql
import linketinder.util.sql.PostgreSqlConnection
import linketinder.util.sql.SqlFactory
import spock.lang.Specification

class SqlFactoryTest extends Specification {

    void "newInstance() returns sql instance"() {
        given:
        PostgreSqlConnection postgreSqlConnection = new PostgreSqlConnection()

        when:
        Sql instance = SqlFactory.newInstance(postgreSqlConnection)

        then:
        instance instanceof Sql
    }
}
