package dev.renantormen.starwarsplanetapi.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;

@Startup
@Singleton
public class DatabaseUtil {

    @Resource(lookup = "jdbc/h2")
    private DataSource dataSource;

    private final Logger LOGGER = Logger.getLogger(DatabaseUtil.class.getName());

    @PostConstruct
    public void migrateDatabase() {
        try {
            LOGGER.info("Aplicando migrations");
            Flyway flyaway = new Flyway();
            flyaway.setDataSource(dataSource);
            flyaway.setBaselineOnMigrate(true);
            for (MigrationInfo migration : flyaway.info().all()) {
                LOGGER.info("Migration: " + migration.getVersion() + " : " + migration.getDescription() + " \n do arquivo: " + migration.getScript());
            }
            flyaway.migrate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Houve um erro ao aplicar migrations: ", e);
        }
    }

    

}