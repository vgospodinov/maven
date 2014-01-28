
Additional Dependencies
------------------------

Download mySQL JDBC driver, place it in the libs/ folder and rename it to
    mysql-connector-java-5.1.14-bin.jar


Download Oracle JDBC driver, place it in the libs/ folder and rename it to
    jdbc_11g_java6-11.1.0.jar
    

Then, install it into your local maven repository

  mvn install:install-file -Dfile=libs/mysql-connector-java-5.1.14-bin.jar -DgroupId=com.initech.ini.thirdparty -DartifactId=mysql-jdbc-driver -Dversion=5.1.14 -Dpackaging=jar
  mvn install:install-file -Dfile=libs/jdbc_11g_java6-11.1.0.jar -DgroupId=com.initech.ini.thirdparty -DartifactId=oracle-jdbc-java6 -Dversion=11.1.0 -Dpackaging=jar
  
  
  