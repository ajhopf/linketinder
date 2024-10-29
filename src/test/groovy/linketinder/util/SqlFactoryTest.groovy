package linketinder.util

import groovy.sql.Sql
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
