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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import javax.sql.DataSource;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.persistence.cache.EntityCacheImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Test;

public class ProfilingDbSqlSessionFactoryDiffblueTest {
  /**
   * Method under test: default or parameterless constructor of
   * {@link ProfilingDbSqlSessionFactory}
   */
  @Test
  public void testNewProfilingDbSqlSessionFactory() {
    // Arrange and Act
    ProfilingDbSqlSessionFactory actualProfilingDbSqlSessionFactory = new ProfilingDbSqlSessionFactory();

    // Assert
    assertEquals("", actualProfilingDbSqlSessionFactory.getDatabaseTablePrefix());
    assertNull(actualProfilingDbSqlSessionFactory.getDatabaseCatalog());
    assertNull(actualProfilingDbSqlSessionFactory.getDatabaseSchema());
    assertNull(actualProfilingDbSqlSessionFactory.getDatabaseType());
    assertNull(actualProfilingDbSqlSessionFactory.getStatementMappings());
    assertNull(actualProfilingDbSqlSessionFactory.getIdGenerator());
    assertNull(actualProfilingDbSqlSessionFactory.getSqlSessionFactory());
    assertEquals(100, actualProfilingDbSqlSessionFactory.getMaxNrOfStatementsInBulkInsert());
    assertFalse(actualProfilingDbSqlSessionFactory.isTablePrefixIsSchema());
    assertTrue(actualProfilingDbSqlSessionFactory.getBulkDeleteStatements().isEmpty());
    assertTrue(actualProfilingDbSqlSessionFactory.getBulkInsertStatements().isEmpty());
    assertTrue(actualProfilingDbSqlSessionFactory.getDeleteStatements().isEmpty());
    assertTrue(actualProfilingDbSqlSessionFactory.getInsertStatements().isEmpty());
    assertTrue(actualProfilingDbSqlSessionFactory.getSelectStatements().isEmpty());
    assertTrue(actualProfilingDbSqlSessionFactory.getUpdateStatements().isEmpty());
    assertTrue(actualProfilingDbSqlSessionFactory.isDbHistoryUsed());
  }

  /**
   * Method under test:
   * {@link ProfilingDbSqlSessionFactory#openSession(CommandContext)}
   */
  @Test
  public void testOpenSession() {
    // Arrange
    ProfilingDbSqlSessionFactory profilingDbSqlSessionFactory = new ProfilingDbSqlSessionFactory();
    Configuration configuration = new Configuration(
        new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)));
    profilingDbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));
    CommandContext commandContext = mock(CommandContext.class);
    when(commandContext.getEntityCache()).thenReturn(new EntityCacheImpl());

    // Act
    Session actualOpenSessionResult = profilingDbSqlSessionFactory.openSession(commandContext);

    // Assert
    verify(commandContext).getEntityCache();
    assertTrue(actualOpenSessionResult instanceof ProfilingDbSqlSession);
    SqlSession sqlSession = ((ProfilingDbSqlSession) actualOpenSessionResult).getSqlSession();
    assertTrue(sqlSession instanceof DefaultSqlSession);
    assertNull(((ProfilingDbSqlSession) actualOpenSessionResult).getCurrentCommandExecution());
    assertNull(((ProfilingDbSqlSession) actualOpenSessionResult).commandExecutionResult);
    assertSame(profilingDbSqlSessionFactory,
        ((ProfilingDbSqlSession) actualOpenSessionResult).getDbSqlSessionFactory());
    assertSame(configuration, sqlSession.getConfiguration());
  }
}
