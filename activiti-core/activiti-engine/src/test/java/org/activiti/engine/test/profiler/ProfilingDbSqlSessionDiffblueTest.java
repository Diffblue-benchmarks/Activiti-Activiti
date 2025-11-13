/*
 * Copyright 2010-2020 Alfresco Software, Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.engine.test.profiler;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.activiti.engine.impl.db.DbSqlSessionFactory;
import org.activiti.engine.impl.persistence.cache.EntityCache;
import org.activiti.engine.impl.persistence.cache.EntityCacheImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.Environment.Builder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ProfilingDbSqlSessionDiffblueTest {
  /**
   * Test {@link ProfilingDbSqlSession#ProfilingDbSqlSession(DbSqlSessionFactory, EntityCache)}.
   *
   * <p>Method under test: {@link ProfilingDbSqlSession#ProfilingDbSqlSession(DbSqlSessionFactory,
   * EntityCache)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProfilingDbSqlSession.<init>(DbSqlSessionFactory, EntityCache)"})
  public void testNewProfilingDbSqlSession() {
    // Arrange
    ProfilingDbSqlSessionFactory dbSqlSessionFactory = new ProfilingDbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));

    // Act
    ProfilingDbSqlSession actualProfilingDbSqlSession =
        new ProfilingDbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Assert
    DbSqlSessionFactory dbSqlSessionFactory2 = actualProfilingDbSqlSession.getDbSqlSessionFactory();
    assertTrue(dbSqlSessionFactory2 instanceof ProfilingDbSqlSessionFactory);
    SqlSession sqlSession = actualProfilingDbSqlSession.getSqlSession();
    assertTrue(sqlSession instanceof DefaultSqlSession);
    SqlSessionFactory sqlSessionFactory = dbSqlSessionFactory2.getSqlSessionFactory();
    assertTrue(sqlSessionFactory instanceof DefaultSqlSessionFactory);
    assertSame(dbSqlSessionFactory, dbSqlSessionFactory2);
    assertSame(sqlSession.getConfiguration(), sqlSessionFactory.getConfiguration());
  }

  /**
   * Test {@link ProfilingDbSqlSession#ProfilingDbSqlSession(DbSqlSessionFactory, EntityCache)}.
   *
   * <ul>
   *   <li>Then return DbSqlSessionFactory is {@link DbSqlSessionFactory} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ProfilingDbSqlSession#ProfilingDbSqlSession(DbSqlSessionFactory,
   * EntityCache)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProfilingDbSqlSession.<init>(DbSqlSessionFactory, EntityCache)"})
  public void testNewProfilingDbSqlSession_thenReturnDbSqlSessionFactoryIsDbSqlSessionFactory() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));

    // Act
    ProfilingDbSqlSession actualProfilingDbSqlSession =
        new ProfilingDbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Assert
    SqlSession sqlSession = actualProfilingDbSqlSession.getSqlSession();
    assertTrue(sqlSession instanceof DefaultSqlSession);
    DbSqlSessionFactory dbSqlSessionFactory2 = actualProfilingDbSqlSession.getDbSqlSessionFactory();
    SqlSessionFactory sqlSessionFactory = dbSqlSessionFactory2.getSqlSessionFactory();
    assertTrue(sqlSessionFactory instanceof DefaultSqlSessionFactory);
    assertSame(dbSqlSessionFactory, dbSqlSessionFactory2);
    assertSame(sqlSession.getConfiguration(), sqlSessionFactory.getConfiguration());
  }

  /**
   * Test {@link ProfilingDbSqlSession#ProfilingDbSqlSession(DbSqlSessionFactory, EntityCache,
   * Connection, String, String)}.
   *
   * <ul>
   *   <li>Then SqlSession return {@link DefaultSqlSession}.
   * </ul>
   *
   * <p>Method under test: {@link ProfilingDbSqlSession#ProfilingDbSqlSession(DbSqlSessionFactory,
   * EntityCache, Connection, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProfilingDbSqlSession.<init>(DbSqlSessionFactory, EntityCache, Connection, String, String)"
  })
  public void testNewProfilingDbSqlSession_thenSqlSessionReturnDefaultSqlSession()
      throws SQLException {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    Configuration configuration = new Configuration();
    when(dbSqlSessionFactory.getSqlSessionFactory())
        .thenReturn(new DefaultSqlSessionFactory(configuration));
    EntityCacheImpl entityCache = new EntityCacheImpl();

    Connection connection = mock(Connection.class);
    when(connection.getAutoCommit()).thenReturn(true);

    // Act
    ProfilingDbSqlSession actualProfilingDbSqlSession =
        new ProfilingDbSqlSession(
            dbSqlSessionFactory, entityCache, connection, "Catalog", "Schema");

    // Assert
    verify(connection).getAutoCommit();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    SqlSession sqlSession = actualProfilingDbSqlSession.getSqlSession();
    assertTrue(sqlSession instanceof DefaultSqlSession);
    assertNull(actualProfilingDbSqlSession.getCurrentCommandExecution());
    assertNull(actualProfilingDbSqlSession.commandExecutionResult);
    assertSame(configuration, sqlSession.getConfiguration());
    assertSame(dbSqlSessionFactory, actualProfilingDbSqlSession.getDbSqlSessionFactory());
  }

  /**
   * Test {@link ProfilingDbSqlSession#getCurrentCommandExecution()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProfilingDbSqlSession#getCurrentCommandExecution()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.test.profiler.CommandExecutionResult ProfilingDbSqlSession.getCurrentCommandExecution()"
  })
  public void testGetCurrentCommandExecution_thenReturnNull() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));
    ProfilingDbSqlSession profilingDbSqlSession =
        new ProfilingDbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    assertNull(profilingDbSqlSession.getCurrentCommandExecution());
  }
}
