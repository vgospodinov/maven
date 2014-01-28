package com.initech.ini;

import java.io.File;
import java.util.Properties;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Export of SQL schema to a file. This is mainly as an aid for developing.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
    "classpath:/applicationContext.xml" 
})
public class SchemaExportIntegrationTest extends AbstractJUnit4SpringContextTests {

    private static final String SCHEMA_GEN_CONFIG = "./src/main/resources/schema-gen.hibernate.cfg.xml";

	private static File targetDir;

    /**
     * Target-Dir erzeugen, ggf. altes Schema entfernen
     */
    @BeforeClass
    public static void setUp() throws Exception {

        targetDir = new File("." + File.separator + "target");

         if(!targetDir.exists()) {
             boolean created = targetDir.mkdirs();
             if(!created) {
                 throw new Exception("Can't create target directory");
             }
         }

    }



    @Test
    public void testSchemaExport_mysql() throws Exception {

        Configuration config = new AnnotationConfiguration();
		
        Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		config.addProperties(properties);
		config.configure(new File(SCHEMA_GEN_CONFIG));
		SchemaExport schemaExport = new SchemaExport(config);
		schemaExport.setOutputFile("./target/" + "mysql-schema-generated.sql");
        schemaExport.setDelimiter(";");
        schemaExport.create(true, false);

    }
    
    @Test
    public void testSchemaExport_oracle() throws Exception {

        Configuration config = new AnnotationConfiguration();
		
        Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.Oracle9Dialect");
		config.addProperties(properties);
		config.configure(new File(SCHEMA_GEN_CONFIG));
		SchemaExport schemaExport = new SchemaExport(config);
		schemaExport.setOutputFile("./target/" + "oracle-schema-generated.sql");
        schemaExport.setDelimiter(";");
        schemaExport.create(true, false);

    }
    
    




}
