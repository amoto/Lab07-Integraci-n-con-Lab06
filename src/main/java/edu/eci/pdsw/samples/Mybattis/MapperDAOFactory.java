/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.Mybattis;

import edu.eci.pdsw.samples.persistence.DaoFactory;
import edu.eci.pdsw.samples.persistence.DaoPaciente;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 *
 * @author 2105684
 */
public class MapperDAOFactory extends DaoFactory{
    
    public static SqlSessionFactory sqlsf;
    private SqlSession sqlss=null;
    
    public MapperDAOFactory(Properties prop){
        SqlSessionFactory sqlSessionFactory = null;
        if (sqlSessionFactory == null) {
            InputStream inputStream;
            try {
                inputStream = Resources.getResourceAsStream(prop.getProperty("config"));
                //inputStream = Resources.getResourceAsStream(prop.getProperty("mybatis-config.xml"));
                sqlsf = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e.getCause());
            }
        }
    }
    
    
    
    @Override
    public void beginSession() throws PersistenceException {
        if(sqlss==null){
            sqlss=sqlsf.openSession();
        }
    }

    @Override
    public DaoPaciente getDaoPaciente() {
        
        return new MapperDAOPaciente(sqlss);
    }

    @Override
    public void commitTransaction() throws PersistenceException {
        if(sqlss==null ){
            throw new PersistenceException("Conection is null or is already closed.");
        }else{
            sqlss.commit();
        }
    }

    @Override
    public void rollbackTransaction() throws PersistenceException {
        if(sqlss==null ){
            throw new PersistenceException("Conection is null");
        }else{
            sqlss.rollback();
        }
    }

    @Override
    public void endSession() throws PersistenceException {
        if(sqlss==null ){
            throw new PersistenceException("Conection is null");
        }else{
            sqlss.close();
        }
    }
    
}
