@PostConstruct
public void initSchema() throws SQLException {
    try (var c = jt.getDataSource().getConnection()) {
        var res = new ClassPathResource("schema.sql", SpringNameDao.class);
        ScriptUtils.executeSqlScript(c, res);
    }
}