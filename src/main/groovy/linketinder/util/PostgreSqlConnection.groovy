package linketinder.util

class PostgreSqlConnection implements ISqlConfig{
    @Override
    String getUrl() {
        return 'jdbc:postgresql://localhost:5432/linketinder'
    }

    @Override
    String getUser() {
        return 'andre'
    }

    @Override
    String getPassword() {
        return '020917'
    }

    @Override
    String getDriver() {
        return 'org.postgresql.Driver'
    }
}
