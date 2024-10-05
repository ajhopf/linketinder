package com.linketinder.util

import groovy.sql.Sql
import spock.lang.Specification

class SqlFactoryTest extends Specification {

    void "newInstance() returns sql instance"() {
        given:

        when:
        Sql instance = SqlFactory.newInstance();

        then:
        instance instanceof Sql
    }
}
