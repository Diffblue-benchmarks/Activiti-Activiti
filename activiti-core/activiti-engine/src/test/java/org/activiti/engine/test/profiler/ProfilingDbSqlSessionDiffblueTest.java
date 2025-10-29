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
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import javax.sql.DataSource;
import org.activiti.engine.impl.db.DbSqlSessionFactory;
import org.activiti.engine.impl.persistence.cache.CachedEntity;
import org.activiti.engine.impl.persistence.cache.EntityCache;
import org.activiti.engine.impl.persistence.cache.EntityCacheImpl;
import org.activiti.engine.impl.persistence.entity.AttachmentEntityImpl;
import org.activiti.engine.impl.persistence.entity.Entity;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.junit.Test;
import org.mockito.Mockito;

public class ProfilingDbSqlSessionDiffblueTest {
  /**
   * Method under test: {@link ProfilingDbSqlSession#flush()}
   */
  @Test
  public void testFlush() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    (new ProfilingDbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).flush();

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test: {@link ProfilingDbSqlSession#commit()}
   */
  @Test
  public void testCommit() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    (new ProfilingDbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).commit();

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test:
   * {@link ProfilingDbSqlSession#selectById(Class, String, boolean)}
   */
  @Test
  public void testSelectById() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
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
   * Method under test:
   * {@link ProfilingDbSqlSession#flushBulkInsert(Collection, Class)}
   */
  @Test
  public void testFlushBulkInsert() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getBulkInsertStatement(Mockito.<Class<Object>>any())).thenReturn("Bulk Insert Statement");
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.mapStatement(Mockito.<String>any())).thenReturn("Map Statement");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
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
   * Method under test: {@link ProfilingDbSqlSession#flushUpdates()}
   */
  @Test
  public void testFlushUpdates() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
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
   * Method under test:
   * {@link ProfilingDbSqlSession#flushDeleteEntities(Class, Collection)}
   */
  @Test
  public void testFlushDeleteEntities() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
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
   * Method under test: {@link ProfilingDbSqlSession#flushBulkDeletes(Class)}
   */
  @Test
  public void testFlushBulkDeletes() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    ProfilingDbSqlSession profilingDbSqlSession = new ProfilingDbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    Class<Entity> entityClass = Entity.class;

    // Act
    profilingDbSqlSession.flushBulkDeletes(entityClass);

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test: {@link ProfilingDbSqlSession#getCurrentCommandExecution()}
   */
  @Test
  public void testGetCurrentCommandExecution() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    CommandExecutionResult actualCurrentCommandExecution = (new ProfilingDbSqlSession(dbSqlSessionFactory,
        new EntityCacheImpl())).getCurrentCommandExecution();

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    assertNull(actualCurrentCommandExecution);
  }

  /**
   * Method under test:
   * {@link ProfilingDbSqlSession#ProfilingDbSqlSession(DbSqlSessionFactory, EntityCache)}
   */
  @Test
  public void testNewProfilingDbSqlSession() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    Configuration configuration = new Configuration(
        new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)));
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
   * Method under test:
   * {@link ProfilingDbSqlSession#ProfilingDbSqlSession(DbSqlSessionFactory, EntityCache)}
   */
  @Test
  public void testNewProfilingDbSqlSession2() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    Configuration configuration = new Configuration(
        new Environment("42", new ManagedTransactionFactory(), mock(DataSource.class)));
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
   * Method under test:
   * {@link ProfilingDbSqlSession#ProfilingDbSqlSession(DbSqlSessionFactory, EntityCache, Connection, String, String)}
   */
  @Test
  public void testNewProfilingDbSqlSession3() throws SQLException {
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
}
