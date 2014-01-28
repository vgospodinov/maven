package com.initech.ini.dao;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.initech.ini.model.EShopOrder;

/**
 * Extension of Springframework's HibernateDaoSupport, which enables
 * JDBC operation via JdbcTemplate besides regular HibernateTemplate
 * operations.
 * <p>
 * Extending HibernateDaooSupport is necessary, because we use Springframework's
 * auto wiring of dependencies, which is not the case with HibernateDaoSupport.
 * 
 * 
 * @author pahne01
 *
 */
public class IniDaoSupport extends HibernateDaoSupport{

	private DataSource dataSource;
	
    protected final JdbcTemplate getJdbcTemplate() {
        JdbcTemplate template = new JdbcTemplate();
        template.setDataSource(dataSource);
        return template;
    }
	
	/**
	 * The session factory has to be injected by Spring. We need to
	 * auto wire it, because the super class has no autowiring. 
	 * @param sessionFactory
	 */
	@Autowired @Required
    public void injectSessionFactoy(SessionFactory sessionFactory){
        setSessionFactory(sessionFactory);
    }
	
    /**
     * @param dataSource the dataSource to set
     */
	@Autowired @Required
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

	
}
