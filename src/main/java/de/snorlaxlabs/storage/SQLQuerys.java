package de.snorlaxlabs.storage;

public enum SQLQuerys {

    NULL();

    public enum EXTERNAL{
        INITIAL_DATABASE_QUERY("create table auth_users" +
                "(" +
                "    userID   int auto_increment" +
                "        primary key," +
                "    uuid     text null," +
                "    name     text null," +
                "    auth_key text null," +
                "    constraint auth_users_pk_2" +
                "        unique (userID)" +
                ");");

        private String sql;

        EXTERNAL(String sql) {
            this.sql = sql;
        }

        public String getSql() {
            return sql;
        }
    }

    public enum INTERNAL{
        INITIAL_DATABASE_QUERY("");

        private String sql;

        INTERNAL(String sql) {
            this.sql = sql;
        }

        public String getSql() {
            return sql;
        }
    }

}
