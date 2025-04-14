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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.activiti.engine.impl.db.DbSqlSessionFactory;
import org.activiti.engine.impl.persistence.cache.CachedEntity;
import org.activiti.engine.impl.persistence.cache.EntityCache;
import org.activiti.engine.impl.persistence.cache.EntityCacheImpl;
import org.activiti.engine.impl.persistence.entity.AttachmentEntityImpl;
import org.activiti.engine.impl.persistence.entity.Entity;
import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.Environment.Builder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class ProfilingDbSqlSessionDiffblueTest {
  /**
   * Test {@link ProfilingDbSqlSession#ProfilingDbSqlSession(DbSqlSessionFactory, EntityCache)}.
   * <p>
   * Method under test: {@link ProfilingDbSqlSession#ProfilingDbSqlSession(DbSqlSessionFactory, EntityCache)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProfilingDbSqlSession.<init>(DbSqlSessionFactory, EntityCache)"})
  public void testNewProfilingDbSqlSession() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    Builder dataSourceResult = (new Builder("42")).dataSource(mock(DataSource.class));
    Environment environment = dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(configuration));

    // Act
    ProfilingDbSqlSession actualProfilingDbSqlSession = new ProfilingDbSqlSession(dbSqlSessionFactory,
        new EntityCacheImpl());

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    SqlSession sqlSession = actualProfilingDbSqlSession.getSqlSession();
    assertTrue(sqlSession instanceof DefaultSqlSession);
    assertNull(actualProfilingDbSqlSession.getCurrentCommandExecution());
    assertNull(actualProfilingDbSqlSession.commandExecutionResult);
    assertSame(configuration, sqlSession.getConfiguration());
    assertSame(dbSqlSessionFactory, actualProfilingDbSqlSession.getDbSqlSessionFactory());
  }

  /**
   * Test {@link ProfilingDbSqlSession#ProfilingDbSqlSession(DbSqlSessionFactory, EntityCache)}.
   * <p>
   * Method under test: {@link ProfilingDbSqlSession#ProfilingDbSqlSession(DbSqlSessionFactory, EntityCache)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProfilingDbSqlSession.<init>(DbSqlSessionFactory, EntityCache)"})
  public void testNewProfilingDbSqlSession2() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    Builder dataSourceResult = (new Builder("42")).dataSource(mock(DataSource.class));
    Environment environment = dataSourceResult.transactionFactory(new ManagedTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(configuration));

    // Act
    ProfilingDbSqlSession actualProfilingDbSqlSession = new ProfilingDbSqlSession(dbSqlSessionFactory,
        new EntityCacheImpl());

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    SqlSession sqlSession = actualProfilingDbSqlSession.getSqlSession();
    assertTrue(sqlSession instanceof DefaultSqlSession);
    assertNull(actualProfilingDbSqlSession.getCurrentCommandExecution());
    assertNull(actualProfilingDbSqlSession.commandExecutionResult);
    assertSame(configuration, sqlSession.getConfiguration());
    assertSame(dbSqlSessionFactory, actualProfilingDbSqlSession.getDbSqlSessionFactory());
  }

  /**
   * Test {@link ProfilingDbSqlSession#ProfilingDbSqlSession(DbSqlSessionFactory, EntityCache, Connection, String, String)}.
   * <ul>
   *   <li>Then SqlSession return {@link DefaultSqlSession}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProfilingDbSqlSession#ProfilingDbSqlSession(DbSqlSessionFactory, EntityCache, Connection, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProfilingDbSqlSession.<init>(DbSqlSessionFactory, EntityCache, Connection, String, String)"})
  public void testNewProfilingDbSqlSession_thenSqlSessionReturnDefaultSqlSession() throws SQLException {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    Configuration configuration = new Configuration();
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(configuration));
    EntityCacheImpl entityCache = new EntityCacheImpl();
    Connection connection = mock(Connection.class);
    when(connection.getAutoCommit()).thenReturn(true);

    // Act
    ProfilingDbSqlSession actualProfilingDbSqlSession = new ProfilingDbSqlSession(dbSqlSessionFactory, entityCache,
        connection, "Catalog", "Schema");

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
   * Test {@link ProfilingDbSqlSession#flush()}.
   * <ul>
   *   <li>Then calls {@link Connection#getAutoCommit()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProfilingDbSqlSession#flush()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProfilingDbSqlSession.flush()"})
  public void testFlush_thenCallsGetAutoCommit() throws SQLException {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(new Configuration()));
    Connection connection = mock(Connection.class);
    when(connection.getAutoCommit()).thenReturn(true);

    // Act
    (new ProfilingDbSqlSession(dbSqlSessionFactory, new EntityCacheImpl(), connection, "Catalog", "Schema")).flush();

    // Assert
    verify(connection).getAutoCommit();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Test {@link ProfilingDbSqlSession#commit()}.
   * <ul>
   *   <li>Then calls {@link Connection#getAutoCommit()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProfilingDbSqlSession#commit()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProfilingDbSqlSession.commit()"})
  public void testCommit_thenCallsGetAutoCommit() throws SQLException {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(new Configuration()));
    Connection connection = mock(Connection.class);
    when(connection.getAutoCommit()).thenReturn(true);

    // Act
    (new ProfilingDbSqlSession(dbSqlSessionFactory, new EntityCacheImpl(), connection, "Catalog", "Schema")).commit();

    // Assert
    verify(connection).getAutoCommit();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Test {@link ProfilingDbSqlSession#selectById(Class, String, boolean)} with {@code entityClass}, {@code id}, {@code useCache}.
   * <ul>
   *   <li>Then return {@link AttachmentEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProfilingDbSqlSession#selectById(Class, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Entity ProfilingDbSqlSession.selectById(Class, String, boolean)"})
  public void testSelectByIdWithEntityClassIdUseCache_thenReturnAttachmentEntityImpl() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    Builder dataSourceResult = (new Builder("42")).dataSource(mock(DataSource.class));
    Environment environment = dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    when(dbSqlSessionFactory.getSqlSessionFactory())
        .thenReturn(new DefaultSqlSessionFactory(new Configuration(environment)));
    EntityCacheImpl entityCache = mock(EntityCacheImpl.class);
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    when(entityCache.findInCache(Mockito.<Class<Entity>>any(), Mockito.<String>any())).thenReturn(attachmentEntityImpl);
    when(entityCache.put(Mockito.<Entity>any(), anyBoolean()))
        .thenReturn(new CachedEntity(new AttachmentEntityImpl(), true));
    entityCache.put(new AttachmentEntityImpl(), true);
    ProfilingDbSqlSession profilingDbSqlSession = new ProfilingDbSqlSession(dbSqlSessionFactory, entityCache);
    Class<Entity> entityClass = Entity.class;

    // Act
    Entity actualSelectByIdResult = profilingDbSqlSession.selectById(entityClass, "42", true);

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(entityCache).findInCache(isA(Class.class), eq("42"));
    verify(entityCache).put(isA(Entity.class), eq(true));
    assertSame(attachmentEntityImpl, actualSelectByIdResult);
  }

  /**
   * Test {@link ProfilingDbSqlSession#selectListWithRawParameter(String, Object, int, int, boolean)} with {@code statement}, {@code parameter}, {@code firstResult}, {@code maxResults}, {@code useCache}.
   * <p>
   * Method under test: {@link ProfilingDbSqlSession#selectListWithRawParameter(String, Object, int, int, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ProfilingDbSqlSession.selectListWithRawParameter(String, Object, int, int, boolean)"})
  public void testSelectListWithRawParameterWithStatementParameterFirstResultMaxResultsUseCache() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.mapStatement(Mockito.<String>any())).thenReturn("Map Statement");
    Builder dataSourceResult = (new Builder("42")).dataSource(mock(DataSource.class));
    Environment environment = dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    when(dbSqlSessionFactory.getSqlSessionFactory())
        .thenReturn(new DefaultSqlSessionFactory(new Configuration(environment)));
    doNothing().when(dbSqlSessionFactory).setStatementMappings(Mockito.<Map<String, String>>any());
    dbSqlSessionFactory.setStatementMappings(null);

    // Act
    List actualSelectListWithRawParameterResult = (new ProfilingDbSqlSession(dbSqlSessionFactory,
        new EntityCacheImpl())).selectListWithRawParameter("MD", JSONObject.NULL, -1, -1, false);

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).mapStatement(eq("MD"));
    verify(dbSqlSessionFactory).setStatementMappings(isNull());
    assertTrue(actualSelectListWithRawParameterResult.isEmpty());
  }

  /**
   * Test {@link ProfilingDbSqlSession#selectListWithRawParameterWithoutFilter(String, Object, int, int)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProfilingDbSqlSession#selectListWithRawParameterWithoutFilter(String, Object, int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ProfilingDbSqlSession.selectListWithRawParameterWithoutFilter(String, Object, int, int)"})
  public void testSelectListWithRawParameterWithoutFilter_thenReturnEmpty() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.mapStatement(Mockito.<String>any())).thenReturn("Map Statement");
    Builder dataSourceResult = (new Builder("42")).dataSource(mock(DataSource.class));
    Environment environment = dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    when(dbSqlSessionFactory.getSqlSessionFactory())
        .thenReturn(new DefaultSqlSessionFactory(new Configuration(environment)));
    doNothing().when(dbSqlSessionFactory).setStatementMappings(Mockito.<Map<String, String>>any());
    dbSqlSessionFactory.setStatementMappings(null);

    // Act
    List actualSelectListWithRawParameterWithoutFilterResult = (new ProfilingDbSqlSession(dbSqlSessionFactory,
        new EntityCacheImpl())).selectListWithRawParameterWithoutFilter("MD", JSONObject.NULL, -1, -1);

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).mapStatement(eq("MD"));
    verify(dbSqlSessionFactory).setStatementMappings(isNull());
    assertTrue(actualSelectListWithRawParameterWithoutFilterResult.isEmpty());
  }

  /**
   * Test {@link ProfilingDbSqlSession#flushBulkInsert(Collection, Class)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link DbSqlSessionFactory#getBulkInsertStatement(Class)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProfilingDbSqlSession#flushBulkInsert(Collection, Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProfilingDbSqlSession.flushBulkInsert(Collection, Class)"})
  public void testFlushBulkInsert_whenArrayList_thenCallsGetBulkInsertStatement() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getBulkInsertStatement(Mockito.<Class<Object>>any())).thenReturn("Bulk Insert Statement");
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.mapStatement(Mockito.<String>any())).thenReturn("Map Statement");
    Builder dataSourceResult = (new Builder("42")).dataSource(mock(DataSource.class));
    Environment environment = dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    when(dbSqlSessionFactory.getSqlSessionFactory())
        .thenReturn(new DefaultSqlSessionFactory(new Configuration(environment)));
    ProfilingDbSqlSession profilingDbSqlSession = new ProfilingDbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    ArrayList<Entity> entities = new ArrayList<>();
    Class<Entity> clazz = Entity.class;

    // Act
    profilingDbSqlSession.flushBulkInsert(entities, clazz);

    // Assert
    verify(dbSqlSessionFactory).getBulkInsertStatement(isA(Class.class));
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).mapStatement(eq("Bulk Insert Statement"));
  }

  /**
   * Test {@link ProfilingDbSqlSession#flushUpdates()}.
   * <ul>
   *   <li>Then calls {@link DbSqlSessionFactory#getDatabaseCatalog()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProfilingDbSqlSession#flushUpdates()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProfilingDbSqlSession.flushUpdates()"})
  public void testFlushUpdates_thenCallsGetDatabaseCatalog() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    Builder dataSourceResult = (new Builder("42")).dataSource(mock(DataSource.class));
    Environment environment = dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    when(dbSqlSessionFactory.getSqlSessionFactory())
        .thenReturn(new DefaultSqlSessionFactory(new Configuration(environment)));
    EntityCacheImpl entityCache = mock(EntityCacheImpl.class);
    when(entityCache.put(Mockito.<Entity>any(), anyBoolean()))
        .thenReturn(new CachedEntity(new AttachmentEntityImpl(), true));
    entityCache.put(new AttachmentEntityImpl(), true);

    // Act
    (new ProfilingDbSqlSession(dbSqlSessionFactory, entityCache)).flushUpdates();

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(entityCache).put(isA(Entity.class), eq(true));
  }

  /**
   * Test {@link ProfilingDbSqlSession#flushDeleteEntities(Class, Collection)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link DbSqlSessionFactory#getDatabaseCatalog()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProfilingDbSqlSession#flushDeleteEntities(Class, Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProfilingDbSqlSession.flushDeleteEntities(Class, Collection)"})
  public void testFlushDeleteEntities_whenArrayList_thenCallsGetDatabaseCatalog() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    Builder dataSourceResult = (new Builder("42")).dataSource(mock(DataSource.class));
    Environment environment = dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    when(dbSqlSessionFactory.getSqlSessionFactory())
        .thenReturn(new DefaultSqlSessionFactory(new Configuration(environment)));
    ProfilingDbSqlSession profilingDbSqlSession = new ProfilingDbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    Class<Entity> entityClass = Entity.class;

    // Act
    profilingDbSqlSession.flushDeleteEntities(entityClass, new ArrayList<>());

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Test {@link ProfilingDbSqlSession#flushBulkDeletes(Class)}.
   * <ul>
   *   <li>Then calls {@link Connection#getAutoCommit()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProfilingDbSqlSession#flushBulkDeletes(Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProfilingDbSqlSession.flushBulkDeletes(Class)"})
  public void testFlushBulkDeletes_thenCallsGetAutoCommit() throws SQLException {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(new Configuration()));
    Connection connection = mock(Connection.class);
    when(connection.getAutoCommit()).thenReturn(true);
    ProfilingDbSqlSession profilingDbSqlSession = new ProfilingDbSqlSession(dbSqlSessionFactory, new EntityCacheImpl(),
        connection, "Catalog", "Schema");
    Class<Entity> entityClass = Entity.class;

    // Act
    profilingDbSqlSession.flushBulkDeletes(entityClass);

    // Assert
    verify(connection).getAutoCommit();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Test {@link ProfilingDbSqlSession#getCurrentCommandExecution()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProfilingDbSqlSession#getCurrentCommandExecution()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CommandExecutionResult ProfilingDbSqlSession.getCurrentCommandExecution()"})
  public void testGetCurrentCommandExecution_thenReturnNull() throws SQLException {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(new Configuration()));
    Connection connection = mock(Connection.class);
    when(connection.getAutoCommit()).thenReturn(true);

    // Act
    CommandExecutionResult actualCurrentCommandExecution = (new ProfilingDbSqlSession(dbSqlSessionFactory,
        new EntityCacheImpl(), connection, "Catalog", "Schema")).getCurrentCommandExecution();

    // Assert
    verify(connection).getAutoCommit();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    assertNull(actualCurrentCommandExecution);
  }
}
