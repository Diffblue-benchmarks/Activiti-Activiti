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
package org.activiti.engine.impl.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.DeploymentQueryImpl;
import org.activiti.engine.impl.ExecutionQueryImpl;
import org.activiti.engine.impl.HistoricActivityInstanceQueryImpl;
import org.activiti.engine.impl.HistoricDetailQueryImpl;
import org.activiti.engine.impl.HistoricProcessInstanceQueryImpl;
import org.activiti.engine.impl.HistoricTaskInstanceQueryImpl;
import org.activiti.engine.impl.HistoricVariableInstanceQueryImpl;
import org.activiti.engine.impl.JobQueryImpl;
import org.activiti.engine.impl.ModelQueryImpl;
import org.activiti.engine.impl.ProcessDefinitionQueryImpl;
import org.activiti.engine.impl.ProcessInstanceQueryImpl;
import org.activiti.engine.impl.TaskQueryImpl;
import org.activiti.engine.impl.persistence.cache.EntityCache;
import org.activiti.engine.impl.persistence.cache.EntityCacheImpl;
import org.activiti.engine.impl.persistence.entity.AttachmentEntityImpl;
import org.activiti.engine.impl.persistence.entity.Entity;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.test.profiler.ProfilingDbSqlSessionFactory;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.Environment.Builder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.h2.jdbc.JdbcResultSet;
import org.h2.tools.SimpleResultSet;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class DbSqlSessionDiffblueTest {
  /**
   * Test {@link DbSqlSession#DbSqlSession(DbSqlSessionFactory, EntityCache)}.
   *
   * <ul>
   *   <li>Then DbSqlSessionFactory return {@link ProfilingDbSqlSessionFactory}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#DbSqlSession(DbSqlSessionFactory, EntityCache)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DbSqlSession.<init>(DbSqlSessionFactory, EntityCache)"})
  public void testNewDbSqlSession_thenDbSqlSessionFactoryReturnProfilingDbSqlSessionFactory() {
    // Arrange
    ProfilingDbSqlSessionFactory dbSqlSessionFactory = new ProfilingDbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));

    // Act
    DbSqlSession actualDbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Assert
    DbSqlSessionFactory dbSqlSessionFactory2 = actualDbSqlSession.getDbSqlSessionFactory();
    assertTrue(dbSqlSessionFactory2 instanceof ProfilingDbSqlSessionFactory);
    SqlSession sqlSession = actualDbSqlSession.getSqlSession();
    assertTrue(sqlSession instanceof DefaultSqlSession);
    SqlSessionFactory sqlSessionFactory = dbSqlSessionFactory2.getSqlSessionFactory();
    assertTrue(sqlSessionFactory instanceof DefaultSqlSessionFactory);
    List<Entity> entityList = actualDbSqlSession.updatedObjects;
    Configuration configuration2 = sqlSession.getConfiguration();
    assertEquals(entityList, configuration2.getIncompleteCacheRefs());
    assertEquals(entityList, configuration2.getIncompleteMethods());
    assertEquals(entityList, configuration2.getIncompleteResultMaps());
    assertEquals(entityList, configuration2.getIncompleteStatements());
    assertSame(dbSqlSessionFactory, dbSqlSessionFactory2);
    assertSame(configuration2, sqlSessionFactory.getConfiguration());
  }

  /**
   * Test {@link DbSqlSession#DbSqlSession(DbSqlSessionFactory, EntityCache, Connection, String,
   * String)}.
   *
   * <ul>
   *   <li>Then {@link DbSqlSession#entityCache} return {@link EntityCacheImpl}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#DbSqlSession(DbSqlSessionFactory, EntityCache,
   * Connection, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DbSqlSession.<init>(DbSqlSessionFactory, EntityCache, Connection, String, String)"
  })
  public void testNewDbSqlSession_thenEntityCacheReturnEntityCacheImpl() throws SQLException {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    Configuration configuration = new Configuration();
    when(dbSqlSessionFactory.getSqlSessionFactory())
        .thenReturn(new DefaultSqlSessionFactory(configuration));
    EntityCacheImpl entityCache = new EntityCacheImpl();

    Connection connection = mock(Connection.class);
    when(connection.getAutoCommit()).thenReturn(true);

    // Act
    DbSqlSession actualDbSqlSession =
        new DbSqlSession(dbSqlSessionFactory, entityCache, connection, "Catalog", "Schema");

    // Assert
    verify(connection).getAutoCommit();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    EntityCache entityCache2 = actualDbSqlSession.entityCache;
    assertTrue(entityCache2 instanceof EntityCacheImpl);
    SqlSession sqlSession = actualDbSqlSession.getSqlSession();
    assertTrue(sqlSession instanceof DefaultSqlSession);
    assertEquals("Catalog", actualDbSqlSession.connectionMetadataDefaultCatalog);
    assertEquals("Schema", actualDbSqlSession.connectionMetadataDefaultSchema);
    assertTrue(actualDbSqlSession.updatedObjects.isEmpty());
    assertTrue(entityCache2.getAllCachedEntities().isEmpty());
    assertTrue(actualDbSqlSession.bulkDeleteOperations.isEmpty());
    assertTrue(actualDbSqlSession.deletedObjects.isEmpty());
    assertTrue(actualDbSqlSession.insertedObjects.isEmpty());
    assertSame(configuration, sqlSession.getConfiguration());
    assertSame(dbSqlSessionFactory, actualDbSqlSession.getDbSqlSessionFactory());
  }

  /**
   * Test {@link DbSqlSession#DbSqlSession(DbSqlSessionFactory, EntityCache)}.
   *
   * <ul>
   *   <li>Then return DbSqlSessionFactory is {@link DbSqlSessionFactory} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#DbSqlSession(DbSqlSessionFactory, EntityCache)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DbSqlSession.<init>(DbSqlSessionFactory, EntityCache)"})
  public void testNewDbSqlSession_thenReturnDbSqlSessionFactoryIsDbSqlSessionFactory() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));

    // Act
    DbSqlSession actualDbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Assert
    SqlSession sqlSession = actualDbSqlSession.getSqlSession();
    assertTrue(sqlSession instanceof DefaultSqlSession);
    DbSqlSessionFactory dbSqlSessionFactory2 = actualDbSqlSession.getDbSqlSessionFactory();
    SqlSessionFactory sqlSessionFactory = dbSqlSessionFactory2.getSqlSessionFactory();
    assertTrue(sqlSessionFactory instanceof DefaultSqlSessionFactory);
    List<Entity> entityList = actualDbSqlSession.updatedObjects;
    Configuration configuration2 = sqlSession.getConfiguration();
    assertEquals(entityList, configuration2.getIncompleteCacheRefs());
    assertEquals(entityList, configuration2.getIncompleteMethods());
    assertEquals(entityList, configuration2.getIncompleteResultMaps());
    assertEquals(entityList, configuration2.getIncompleteStatements());
    assertSame(dbSqlSessionFactory, dbSqlSessionFactory2);
    assertSame(configuration2, sqlSessionFactory.getConfiguration());
  }

  /**
   * Test {@link DbSqlSession#determineUpdatedObjects()}.
   *
   * <p>Method under test: {@link DbSqlSession#determineUpdatedObjects()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DbSqlSession.determineUpdatedObjects()"})
  public void testDetermineUpdatedObjects() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    dbSqlSession.determineUpdatedObjects();

    // Assert that nothing has changed
    assertTrue(dbSqlSession.updatedObjects.isEmpty());
  }

  /**
   * Test {@link DbSqlSession#determineUpdatedObjects()}.
   *
   * <p>Method under test: {@link DbSqlSession#determineUpdatedObjects()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DbSqlSession.determineUpdatedObjects()"})
  public void testDetermineUpdatedObjects2() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));

    EntityCacheImpl entityCache = new EntityCacheImpl();
    AttachmentEntityImpl entity = new AttachmentEntityImpl();
    entityCache.put(entity, false);

    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, entityCache);

    // Act
    dbSqlSession.determineUpdatedObjects();

    // Assert
    List<Entity> entityList = dbSqlSession.updatedObjects;
    assertEquals(1, entityList.size());
    Entity getResult = entityList.get(0);
    assertTrue(getResult instanceof AttachmentEntityImpl);
    assertSame(entity, getResult);
  }

  /**
   * Test {@link DbSqlSession#determineUpdatedObjects()}.
   *
   * <ul>
   *   <li>Then calls {@link Entity#getId()}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#determineUpdatedObjects()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DbSqlSession.determineUpdatedObjects()"})
  public void testDetermineUpdatedObjects_thenCallsGetId() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));

    Entity entity = mock(Entity.class);
    when(entity.getPersistentState()).thenReturn(JSONObject.NULL);
    when(entity.getId()).thenReturn("42");

    EntityCacheImpl entityCache = new EntityCacheImpl();
    entityCache.put(entity, false);

    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, entityCache);

    // Act
    dbSqlSession.determineUpdatedObjects();

    // Assert that nothing has changed
    verify(entity).getId();
    verify(entity, atLeast(1)).getPersistentState();
    assertTrue(dbSqlSession.updatedObjects.isEmpty());
  }

  /**
   * Test {@link DbSqlSession#orderExecutionEntities(Map, boolean)}.
   *
   * <ul>
   *   <li>Then return {@link List}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#orderExecutionEntities(Map, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Collection DbSqlSession.orderExecutionEntities(Map, boolean)"})
  public void testOrderExecutionEntities_thenReturnList() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    Collection<Entity> actualOrderExecutionEntitiesResult =
        dbSqlSession.orderExecutionEntities(new HashMap<>(), true);

    // Assert
    assertTrue(actualOrderExecutionEntitiesResult instanceof List);
    assertTrue(actualOrderExecutionEntitiesResult.isEmpty());
  }

  /**
   * Test {@link DbSqlSession#addMissingComponent(String, String)}.
   *
   * <ul>
   *   <li>Then return {@code Missing Components, Component}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#addMissingComponent(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String DbSqlSession.addMissingComponent(String, String)"})
  public void testAddMissingComponent_thenReturnMissingComponentsComponent() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    assertEquals(
        "Missing Components, Component",
        dbSqlSession.addMissingComponent("Missing Components", "Component"));
  }

  /**
   * Test {@link DbSqlSession#dbSchemaCreate()}.
   *
   * <p>Method under test: {@link DbSqlSession#dbSchemaCreate()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DbSqlSession.dbSchemaCreate()"})
  public void testDbSchemaCreate() throws SQLException {
    // Arrange
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenThrow(new ActivitiException("An error occurred"));

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.dbSchemaCreate());
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link DbSqlSession#dbSchemaCreate()}.
   *
   * <p>Method under test: {@link DbSqlSession#dbSchemaCreate()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DbSqlSession.dbSchemaCreate()"})
  public void testDbSchemaCreate2() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.dbSchemaCreate());
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData)
        .getTables(isNull(), isNull(), eq("ACT_RU_EXECUTION"), isA(String[].class));
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link DbSqlSession#dbSchemaCreate()}.
   *
   * <ul>
   *   <li>Given {@link DbSqlSessionFactory} (default constructor) DatabaseCatalog is {@code
   *       ACT_RU_EXECUTION}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#dbSchemaCreate()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DbSqlSession.dbSchemaCreate()"})
  public void testDbSchemaCreate_givenDbSqlSessionFactoryDatabaseCatalogIsActRuExecution()
      throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenReturn(new SimpleResultSet());

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setDatabaseCatalog("ACT_RU_EXECUTION");
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.dbSchemaCreate());
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData)
        .getTables(eq("ACT_RU_EXECUTION"), isNull(), eq("ACT_RU_EXECUTION"), isA(String[].class));
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link DbSqlSession#dbSchemaCreate()}.
   *
   * <ul>
   *   <li>Given {@link DbSqlSessionFactory} (default constructor) DatabaseSchema is {@code
   *       ACT_RU_EXECUTION}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#dbSchemaCreate()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DbSqlSession.dbSchemaCreate()"})
  public void testDbSchemaCreate_givenDbSqlSessionFactoryDatabaseSchemaIsActRuExecution()
      throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenReturn(new SimpleResultSet());

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setDatabaseSchema("ACT_RU_EXECUTION");
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.dbSchemaCreate());
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData)
        .getTables(isNull(), eq("ACT_RU_EXECUTION"), eq("ACT_RU_EXECUTION"), isA(String[].class));
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link DbSqlSession#dbSchemaCreate()}.
   *
   * <ul>
   *   <li>Given {@link DbSqlSessionFactory} (default constructor) TablePrefixIsSchema is {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#dbSchemaCreate()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DbSqlSession.dbSchemaCreate()"})
  public void testDbSchemaCreate_givenDbSqlSessionFactoryTablePrefixIsSchemaIsTrue()
      throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenReturn(new SimpleResultSet());

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setTablePrefixIsSchema(true);
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.dbSchemaCreate());
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData)
        .getTables(isNull(), isNull(), eq("ACT_RU_EXECUTION"), isA(String[].class));
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link DbSqlSession#dbSchemaCreate()}.
   *
   * <ul>
   *   <li>Then calls {@link Connection#getAutoCommit()}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#dbSchemaCreate()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DbSqlSession.dbSchemaCreate()"})
  public void testDbSchemaCreate_thenCallsGetAutoCommit() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenReturn(new SimpleResultSet());

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.dbSchemaCreate());
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData)
        .getTables(isNull(), isNull(), eq("ACT_RU_EXECUTION"), isA(String[].class));
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link DbSqlSession#dbSchemaCreateHistory()}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#dbSchemaCreateHistory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DbSqlSession.dbSchemaCreateHistory()"})
  public void testDbSchemaCreateHistory_thenThrowActivitiException() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.dbSchemaCreateHistory());
  }

  /**
   * Test {@link DbSqlSession#dbSchemaCreateEngine()}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#dbSchemaCreateEngine()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DbSqlSession.dbSchemaCreateEngine()"})
  public void testDbSchemaCreateEngine_thenThrowActivitiException() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.dbSchemaCreateEngine());
  }

  /**
   * Test {@link DbSqlSession#dbSchemaDrop()}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#dbSchemaDrop()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DbSqlSession.dbSchemaDrop()"})
  public void testDbSchemaDrop_thenThrowActivitiException() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.dbSchemaDrop());
  }

  /**
   * Test {@link DbSqlSession#dbSchemaPrune()}.
   *
   * <p>Method under test: {@link DbSqlSession#dbSchemaPrune()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DbSqlSession.dbSchemaPrune()"})
  public void testDbSchemaPrune() throws SQLException {
    // Arrange
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenThrow(new ActivitiException("An error occurred"));

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.dbSchemaPrune());
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link DbSqlSession#dbSchemaPrune()}.
   *
   * <p>Method under test: {@link DbSqlSession#dbSchemaPrune()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DbSqlSession.dbSchemaPrune()"})
  public void testDbSchemaPrune2() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.dbSchemaPrune());
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData)
        .getTables(isNull(), isNull(), eq("ACT_HI_PROCINST"), isA(String[].class));
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link DbSqlSession#dbSchemaPrune()}.
   *
   * <ul>
   *   <li>Given array of {@link Object} with {@link JSONObject#NULL}.
   *   <li>Then calls {@link Connection#getAutoCommit()}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#dbSchemaPrune()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DbSqlSession.dbSchemaPrune()"})
  public void testDbSchemaPrune_givenArrayOfObjectWithNull_thenCallsGetAutoCommit()
      throws SQLException {
    // Arrange
    SimpleResultSet simpleResultSet = new SimpleResultSet();
    simpleResultSet.addRow(JSONObject.NULL);

    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenReturn(simpleResultSet);

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    dbSqlSession.dbSchemaPrune();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData)
        .getTables(isNull(), isNull(), eq("ACT_HI_PROCINST"), isA(String[].class));
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link DbSqlSession#dbSchemaPrune()}.
   *
   * <ul>
   *   <li>Given {@link DatabaseMetaData} {@link DatabaseMetaData#getTables(String, String, String,
   *       String[])} return {@link SimpleResultSet#SimpleResultSet()}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#dbSchemaPrune()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DbSqlSession.dbSchemaPrune()"})
  public void testDbSchemaPrune_givenDatabaseMetaDataGetTablesReturnSimpleResultSet()
      throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenReturn(new SimpleResultSet());

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    dbSqlSession.dbSchemaPrune();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData)
        .getTables(isNull(), isNull(), eq("ACT_HI_PROCINST"), isA(String[].class));
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link DbSqlSession#dbSchemaPrune()}.
   *
   * <ul>
   *   <li>Given {@link DbSqlSessionFactory} (default constructor) DatabaseCatalog is {@code
   *       ACT_HI_PROCINST}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#dbSchemaPrune()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DbSqlSession.dbSchemaPrune()"})
  public void testDbSchemaPrune_givenDbSqlSessionFactoryDatabaseCatalogIsActHiProcinst()
      throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenReturn(new SimpleResultSet());

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setDatabaseCatalog("ACT_HI_PROCINST");
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    dbSqlSession.dbSchemaPrune();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData)
        .getTables(eq("ACT_HI_PROCINST"), isNull(), eq("ACT_HI_PROCINST"), isA(String[].class));
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link DbSqlSession#dbSchemaPrune()}.
   *
   * <ul>
   *   <li>Given {@link DbSqlSessionFactory} (default constructor) DatabaseSchema is {@code
   *       ACT_HI_PROCINST}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#dbSchemaPrune()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DbSqlSession.dbSchemaPrune()"})
  public void testDbSchemaPrune_givenDbSqlSessionFactoryDatabaseSchemaIsActHiProcinst()
      throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenReturn(new SimpleResultSet());

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setDatabaseSchema("ACT_HI_PROCINST");
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    dbSqlSession.dbSchemaPrune();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData)
        .getTables(isNull(), eq("ACT_HI_PROCINST"), eq("ACT_HI_PROCINST"), isA(String[].class));
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link DbSqlSession#dbSchemaPrune()}.
   *
   * <ul>
   *   <li>Given {@link DbSqlSessionFactory} (default constructor) TablePrefixIsSchema is {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#dbSchemaPrune()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DbSqlSession.dbSchemaPrune()"})
  public void testDbSchemaPrune_givenDbSqlSessionFactoryTablePrefixIsSchemaIsTrue()
      throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenReturn(new SimpleResultSet());

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setTablePrefixIsSchema(true);
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    dbSqlSession.dbSchemaPrune();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData)
        .getTables(isNull(), isNull(), eq("ACT_HI_PROCINST"), isA(String[].class));
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link DbSqlSession#executeMandatorySchemaResource(String, String)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#executeMandatorySchemaResource(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DbSqlSession.executeMandatorySchemaResource(String, String)"})
  public void testExecuteMandatorySchemaResource_thenThrowActivitiException() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> dbSqlSession.executeMandatorySchemaResource("Operation", "Component"));
  }

  /**
   * Test {@link DbSqlSession#dbSchemaUpdate()}.
   *
   * <p>Method under test: {@link DbSqlSession#dbSchemaUpdate()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String DbSqlSession.dbSchemaUpdate()"})
  public void testDbSchemaUpdate() throws SQLException {
    // Arrange
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenThrow(new ActivitiException("An error occurred"));

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.dbSchemaUpdate());
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link DbSqlSession#dbSchemaUpdate()}.
   *
   * <p>Method under test: {@link DbSqlSession#dbSchemaUpdate()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String DbSqlSession.dbSchemaUpdate()"})
  public void testDbSchemaUpdate2() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.dbSchemaUpdate());
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData)
        .getTables(isNull(), isNull(), eq("ACT_RU_EXECUTION"), isA(String[].class));
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link DbSqlSession#dbSchemaUpdate()}.
   *
   * <ul>
   *   <li>Given {@link DbSqlSessionFactory} (default constructor) DatabaseCatalog is {@code
   *       ACT_RU_EXECUTION}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#dbSchemaUpdate()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String DbSqlSession.dbSchemaUpdate()"})
  public void testDbSchemaUpdate_givenDbSqlSessionFactoryDatabaseCatalogIsActRuExecution()
      throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenReturn(new SimpleResultSet());

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setDatabaseCatalog("ACT_RU_EXECUTION");
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.dbSchemaUpdate());
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData)
        .getTables(eq("ACT_RU_EXECUTION"), isNull(), eq("ACT_RU_EXECUTION"), isA(String[].class));
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link DbSqlSession#dbSchemaUpdate()}.
   *
   * <ul>
   *   <li>Given {@link DbSqlSessionFactory} (default constructor) DatabaseSchema is {@code
   *       ACT_RU_EXECUTION}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#dbSchemaUpdate()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String DbSqlSession.dbSchemaUpdate()"})
  public void testDbSchemaUpdate_givenDbSqlSessionFactoryDatabaseSchemaIsActRuExecution()
      throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenReturn(new SimpleResultSet());

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setDatabaseSchema("ACT_RU_EXECUTION");
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.dbSchemaUpdate());
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData)
        .getTables(isNull(), eq("ACT_RU_EXECUTION"), eq("ACT_RU_EXECUTION"), isA(String[].class));
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link DbSqlSession#dbSchemaUpdate()}.
   *
   * <ul>
   *   <li>Given {@link DbSqlSessionFactory} (default constructor) TablePrefixIsSchema is {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#dbSchemaUpdate()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String DbSqlSession.dbSchemaUpdate()"})
  public void testDbSchemaUpdate_givenDbSqlSessionFactoryTablePrefixIsSchemaIsTrue()
      throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenReturn(new SimpleResultSet());

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setTablePrefixIsSchema(true);
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.dbSchemaUpdate());
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData)
        .getTables(isNull(), isNull(), eq("ACT_RU_EXECUTION"), isA(String[].class));
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link DbSqlSession#dbSchemaUpdate()}.
   *
   * <ul>
   *   <li>Then calls {@link Connection#getAutoCommit()}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#dbSchemaUpdate()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String DbSqlSession.dbSchemaUpdate()"})
  public void testDbSchemaUpdate_thenCallsGetAutoCommit() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenReturn(new SimpleResultSet());

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.dbSchemaUpdate());
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData)
        .getTables(isNull(), isNull(), eq("ACT_RU_EXECUTION"), isA(String[].class));
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link DbSqlSession#findMatchingVersionIndex(String)}.
   *
   * <ul>
   *   <li>Then return minus one.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#findMatchingVersionIndex(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int DbSqlSession.findMatchingVersionIndex(String)"})
  public void testFindMatchingVersionIndex_thenReturnMinusOne() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    assertEquals(-1, dbSqlSession.findMatchingVersionIndex("1.0.2"));
  }

  /**
   * Test {@link DbSqlSession#findMatchingVersionIndex(String)}.
   *
   * <ul>
   *   <li>When {@code 5.7}.
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#findMatchingVersionIndex(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int DbSqlSession.findMatchingVersionIndex(String)"})
  public void testFindMatchingVersionIndex_when57_thenReturnZero() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    assertEquals(0, dbSqlSession.findMatchingVersionIndex("5.7"));
  }

  /**
   * Test {@link DbSqlSession#isEngineTablePresent()}.
   *
   * <p>Method under test: {@link DbSqlSession#isEngineTablePresent()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DbSqlSession.isEngineTablePresent()"})
  public void testIsEngineTablePresent() throws SQLException {
    // Arrange
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenThrow(new ActivitiException("An error occurred"));

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.isEngineTablePresent());
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link DbSqlSession#isEngineTablePresent()}.
   *
   * <p>Method under test: {@link DbSqlSession#isEngineTablePresent()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DbSqlSession.isEngineTablePresent()"})
  public void testIsEngineTablePresent2() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.isEngineTablePresent());
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData)
        .getTables(isNull(), isNull(), eq("ACT_RU_EXECUTION"), isA(String[].class));
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link DbSqlSession#isEngineTablePresent()}.
   *
   * <ul>
   *   <li>Given array of {@link Object} with {@link JSONObject#NULL}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#isEngineTablePresent()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DbSqlSession.isEngineTablePresent()"})
  public void testIsEngineTablePresent_givenArrayOfObjectWithNull_thenReturnTrue()
      throws SQLException {
    // Arrange
    SimpleResultSet simpleResultSet = new SimpleResultSet();
    simpleResultSet.addRow(JSONObject.NULL);

    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenReturn(simpleResultSet);

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    boolean actualIsEngineTablePresentResult = dbSqlSession.isEngineTablePresent();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData)
        .getTables(isNull(), isNull(), eq("ACT_RU_EXECUTION"), isA(String[].class));
    verify(dataSource).getConnection();
    assertTrue(actualIsEngineTablePresentResult);
  }

  /**
   * Test {@link DbSqlSession#isEngineTablePresent()}.
   *
   * <ul>
   *   <li>Given {@link DbSqlSessionFactory} (default constructor) DatabaseCatalog is {@code
   *       ACT_RU_EXECUTION}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#isEngineTablePresent()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DbSqlSession.isEngineTablePresent()"})
  public void testIsEngineTablePresent_givenDbSqlSessionFactoryDatabaseCatalogIsActRuExecution()
      throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenReturn(new SimpleResultSet());

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setDatabaseCatalog("ACT_RU_EXECUTION");
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    boolean actualIsEngineTablePresentResult = dbSqlSession.isEngineTablePresent();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData)
        .getTables(eq("ACT_RU_EXECUTION"), isNull(), eq("ACT_RU_EXECUTION"), isA(String[].class));
    verify(dataSource).getConnection();
    assertFalse(actualIsEngineTablePresentResult);
  }

  /**
   * Test {@link DbSqlSession#isEngineTablePresent()}.
   *
   * <ul>
   *   <li>Given {@link DbSqlSessionFactory} (default constructor) DatabaseSchema is {@code
   *       ACT_RU_EXECUTION}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#isEngineTablePresent()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DbSqlSession.isEngineTablePresent()"})
  public void testIsEngineTablePresent_givenDbSqlSessionFactoryDatabaseSchemaIsActRuExecution()
      throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenReturn(new SimpleResultSet());

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setDatabaseSchema("ACT_RU_EXECUTION");
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    boolean actualIsEngineTablePresentResult = dbSqlSession.isEngineTablePresent();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData)
        .getTables(isNull(), eq("ACT_RU_EXECUTION"), eq("ACT_RU_EXECUTION"), isA(String[].class));
    verify(dataSource).getConnection();
    assertFalse(actualIsEngineTablePresentResult);
  }

  /**
   * Test {@link DbSqlSession#isEngineTablePresent()}.
   *
   * <ul>
   *   <li>Given {@link DbSqlSessionFactory} (default constructor) TablePrefixIsSchema is {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#isEngineTablePresent()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DbSqlSession.isEngineTablePresent()"})
  public void testIsEngineTablePresent_givenDbSqlSessionFactoryTablePrefixIsSchemaIsTrue()
      throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenReturn(new SimpleResultSet());

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setTablePrefixIsSchema(true);
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    boolean actualIsEngineTablePresentResult = dbSqlSession.isEngineTablePresent();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData)
        .getTables(isNull(), isNull(), eq("ACT_RU_EXECUTION"), isA(String[].class));
    verify(dataSource).getConnection();
    assertFalse(actualIsEngineTablePresentResult);
  }

  /**
   * Test {@link DbSqlSession#isEngineTablePresent()}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#isEngineTablePresent()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DbSqlSession.isEngineTablePresent()"})
  public void testIsEngineTablePresent_thenReturnFalse() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenReturn(new SimpleResultSet());

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    boolean actualIsEngineTablePresentResult = dbSqlSession.isEngineTablePresent();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData)
        .getTables(isNull(), isNull(), eq("ACT_RU_EXECUTION"), isA(String[].class));
    verify(dataSource).getConnection();
    assertFalse(actualIsEngineTablePresentResult);
  }

  /**
   * Test {@link DbSqlSession#isHistoryTablePresent()}.
   *
   * <p>Method under test: {@link DbSqlSession#isHistoryTablePresent()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DbSqlSession.isHistoryTablePresent()"})
  public void testIsHistoryTablePresent() throws SQLException {
    // Arrange
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenThrow(new ActivitiException("An error occurred"));

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.isHistoryTablePresent());
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link DbSqlSession#isHistoryTablePresent()}.
   *
   * <p>Method under test: {@link DbSqlSession#isHistoryTablePresent()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DbSqlSession.isHistoryTablePresent()"})
  public void testIsHistoryTablePresent2() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.isHistoryTablePresent());
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData)
        .getTables(isNull(), isNull(), eq("ACT_HI_PROCINST"), isA(String[].class));
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link DbSqlSession#isHistoryTablePresent()}.
   *
   * <ul>
   *   <li>Given array of {@link Object} with {@link JSONObject#NULL}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#isHistoryTablePresent()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DbSqlSession.isHistoryTablePresent()"})
  public void testIsHistoryTablePresent_givenArrayOfObjectWithNull_thenReturnTrue()
      throws SQLException {
    // Arrange
    SimpleResultSet simpleResultSet = new SimpleResultSet();
    simpleResultSet.addRow(JSONObject.NULL);

    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenReturn(simpleResultSet);

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    boolean actualIsHistoryTablePresentResult = dbSqlSession.isHistoryTablePresent();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData)
        .getTables(isNull(), isNull(), eq("ACT_HI_PROCINST"), isA(String[].class));
    verify(dataSource).getConnection();
    assertTrue(actualIsHistoryTablePresentResult);
  }

  /**
   * Test {@link DbSqlSession#isHistoryTablePresent()}.
   *
   * <ul>
   *   <li>Given {@link DbSqlSessionFactory} (default constructor) DatabaseCatalog is {@code
   *       ACT_HI_PROCINST}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#isHistoryTablePresent()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DbSqlSession.isHistoryTablePresent()"})
  public void testIsHistoryTablePresent_givenDbSqlSessionFactoryDatabaseCatalogIsActHiProcinst()
      throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenReturn(new SimpleResultSet());

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setDatabaseCatalog("ACT_HI_PROCINST");
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    boolean actualIsHistoryTablePresentResult = dbSqlSession.isHistoryTablePresent();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData)
        .getTables(eq("ACT_HI_PROCINST"), isNull(), eq("ACT_HI_PROCINST"), isA(String[].class));
    verify(dataSource).getConnection();
    assertFalse(actualIsHistoryTablePresentResult);
  }

  /**
   * Test {@link DbSqlSession#isHistoryTablePresent()}.
   *
   * <ul>
   *   <li>Given {@link DbSqlSessionFactory} (default constructor) DatabaseSchema is {@code
   *       ACT_HI_PROCINST}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#isHistoryTablePresent()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DbSqlSession.isHistoryTablePresent()"})
  public void testIsHistoryTablePresent_givenDbSqlSessionFactoryDatabaseSchemaIsActHiProcinst()
      throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenReturn(new SimpleResultSet());

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setDatabaseSchema("ACT_HI_PROCINST");
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    boolean actualIsHistoryTablePresentResult = dbSqlSession.isHistoryTablePresent();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData)
        .getTables(isNull(), eq("ACT_HI_PROCINST"), eq("ACT_HI_PROCINST"), isA(String[].class));
    verify(dataSource).getConnection();
    assertFalse(actualIsHistoryTablePresentResult);
  }

  /**
   * Test {@link DbSqlSession#isHistoryTablePresent()}.
   *
   * <ul>
   *   <li>Given {@link DbSqlSessionFactory} (default constructor) TablePrefixIsSchema is {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#isHistoryTablePresent()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DbSqlSession.isHistoryTablePresent()"})
  public void testIsHistoryTablePresent_givenDbSqlSessionFactoryTablePrefixIsSchemaIsTrue()
      throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenReturn(new SimpleResultSet());

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setTablePrefixIsSchema(true);
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    boolean actualIsHistoryTablePresentResult = dbSqlSession.isHistoryTablePresent();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData)
        .getTables(isNull(), isNull(), eq("ACT_HI_PROCINST"), isA(String[].class));
    verify(dataSource).getConnection();
    assertFalse(actualIsHistoryTablePresentResult);
  }

  /**
   * Test {@link DbSqlSession#isHistoryTablePresent()}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#isHistoryTablePresent()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DbSqlSession.isHistoryTablePresent()"})
  public void testIsHistoryTablePresent_thenReturnFalse() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenReturn(new SimpleResultSet());

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    boolean actualIsHistoryTablePresentResult = dbSqlSession.isHistoryTablePresent();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData)
        .getTables(isNull(), isNull(), eq("ACT_HI_PROCINST"), isA(String[].class));
    verify(dataSource).getConnection();
    assertFalse(actualIsHistoryTablePresentResult);
  }

  /**
   * Test {@link DbSqlSession#isTablePresent(String)}.
   *
   * <p>Method under test: {@link DbSqlSession#isTablePresent(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DbSqlSession.isTablePresent(String)"})
  public void testIsTablePresent() throws SQLException {
    // Arrange
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenThrow(new ActivitiException("An error occurred"));

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.isTablePresent("Table Name"));
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link DbSqlSession#isTablePresent(String)}.
   *
   * <p>Method under test: {@link DbSqlSession#isTablePresent(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DbSqlSession.isTablePresent(String)"})
  public void testIsTablePresent2() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.isTablePresent("Table Name"));
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData).getTables(isNull(), isNull(), eq("Table Name"), isA(String[].class));
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link DbSqlSession#isTablePresent(String)}.
   *
   * <ul>
   *   <li>Given {@link DbSqlSessionFactory} (default constructor) DatabaseCatalog is empty string.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#isTablePresent(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DbSqlSession.isTablePresent(String)"})
  public void testIsTablePresent_givenDbSqlSessionFactoryDatabaseCatalogIsEmptyString()
      throws SQLException {
    // Arrange
    JdbcResultSet jdbcResultSet = mock(JdbcResultSet.class);
    when(jdbcResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(jdbcResultSet).close();

    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenReturn(jdbcResultSet);

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setDatabaseCatalog("");
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    boolean actualIsTablePresentResult = dbSqlSession.isTablePresent("Table Name");

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData).getTables(isNull(), isNull(), eq("Table Name"), isA(String[].class));
    verify(dataSource).getConnection();
    verify(jdbcResultSet).close();
    verify(jdbcResultSet).next();
    assertTrue(actualIsTablePresentResult);
  }

  /**
   * Test {@link DbSqlSession#isTablePresent(String)}.
   *
   * <ul>
   *   <li>Given {@link DbSqlSessionFactory} (default constructor) DatabaseCatalog is {@code
   *       postgres}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#isTablePresent(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DbSqlSession.isTablePresent(String)"})
  public void testIsTablePresent_givenDbSqlSessionFactoryDatabaseCatalogIsPostgres()
      throws SQLException {
    // Arrange
    JdbcResultSet jdbcResultSet = mock(JdbcResultSet.class);
    when(jdbcResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(jdbcResultSet).close();

    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenReturn(jdbcResultSet);

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setDatabaseCatalog("postgres");
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    boolean actualIsTablePresentResult = dbSqlSession.isTablePresent("Table Name");

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData)
        .getTables(eq("postgres"), isNull(), eq("Table Name"), isA(String[].class));
    verify(dataSource).getConnection();
    verify(jdbcResultSet).close();
    verify(jdbcResultSet).next();
    assertTrue(actualIsTablePresentResult);
  }

  /**
   * Test {@link DbSqlSession#isTablePresent(String)}.
   *
   * <ul>
   *   <li>Given {@link DbSqlSessionFactory} (default constructor) DatabaseSchema is empty string.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#isTablePresent(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DbSqlSession.isTablePresent(String)"})
  public void testIsTablePresent_givenDbSqlSessionFactoryDatabaseSchemaIsEmptyString()
      throws SQLException {
    // Arrange
    JdbcResultSet jdbcResultSet = mock(JdbcResultSet.class);
    when(jdbcResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(jdbcResultSet).close();

    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenReturn(jdbcResultSet);

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setDatabaseSchema("");
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    boolean actualIsTablePresentResult = dbSqlSession.isTablePresent("Table Name");

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData).getTables(isNull(), eq(""), eq("Table Name"), isA(String[].class));
    verify(dataSource).getConnection();
    verify(jdbcResultSet).close();
    verify(jdbcResultSet).next();
    assertTrue(actualIsTablePresentResult);
  }

  /**
   * Test {@link DbSqlSession#isTablePresent(String)}.
   *
   * <ul>
   *   <li>Given {@link DbSqlSessionFactory} (default constructor) DatabaseSchema is {@code
   *       postgres}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#isTablePresent(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DbSqlSession.isTablePresent(String)"})
  public void testIsTablePresent_givenDbSqlSessionFactoryDatabaseSchemaIsPostgres()
      throws SQLException {
    // Arrange
    JdbcResultSet jdbcResultSet = mock(JdbcResultSet.class);
    when(jdbcResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(jdbcResultSet).close();

    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenReturn(jdbcResultSet);

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setDatabaseSchema("postgres");
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    boolean actualIsTablePresentResult = dbSqlSession.isTablePresent("Table Name");

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData)
        .getTables(isNull(), eq("postgres"), eq("Table Name"), isA(String[].class));
    verify(dataSource).getConnection();
    verify(jdbcResultSet).close();
    verify(jdbcResultSet).next();
    assertTrue(actualIsTablePresentResult);
  }

  /**
   * Test {@link DbSqlSession#isTablePresent(String)}.
   *
   * <ul>
   *   <li>Given {@link DbSqlSessionFactory} (default constructor) TablePrefixIsSchema is {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#isTablePresent(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DbSqlSession.isTablePresent(String)"})
  public void testIsTablePresent_givenDbSqlSessionFactoryTablePrefixIsSchemaIsTrue()
      throws SQLException {
    // Arrange
    JdbcResultSet jdbcResultSet = mock(JdbcResultSet.class);
    when(jdbcResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(jdbcResultSet).close();

    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenReturn(jdbcResultSet);

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setTablePrefixIsSchema(true);
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    boolean actualIsTablePresentResult = dbSqlSession.isTablePresent("Table Name");

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData).getTables(isNull(), isNull(), eq("Table Name"), isA(String[].class));
    verify(dataSource).getConnection();
    verify(jdbcResultSet).close();
    verify(jdbcResultSet).next();
    assertTrue(actualIsTablePresentResult);
  }

  /**
   * Test {@link DbSqlSession#isTablePresent(String)}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#isTablePresent(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DbSqlSession.isTablePresent(String)"})
  public void testIsTablePresent_givenTrue_thenReturnTrue() throws SQLException {
    // Arrange
    JdbcResultSet jdbcResultSet = mock(JdbcResultSet.class);
    when(jdbcResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(jdbcResultSet).close();

    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenReturn(jdbcResultSet);

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    boolean actualIsTablePresentResult = dbSqlSession.isTablePresent("Table Name");

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData).getTables(isNull(), isNull(), eq("Table Name"), isA(String[].class));
    verify(dataSource).getConnection();
    verify(jdbcResultSet).close();
    verify(jdbcResultSet).next();
    assertTrue(actualIsTablePresentResult);
  }

  /**
   * Test {@link DbSqlSession#isTablePresent(String)}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#isTablePresent(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DbSqlSession.isTablePresent(String)"})
  public void testIsTablePresent_thenReturnFalse() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String[]>any()))
        .thenReturn(new SimpleResultSet());

    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    boolean actualIsTablePresentResult = dbSqlSession.isTablePresent("Table Name");

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(false);
    verify(databaseMetaData).getTables(isNull(), isNull(), eq("Table Name"), isA(String[].class));
    verify(dataSource).getConnection();
    assertFalse(actualIsTablePresentResult);
  }

  /**
   * Test {@link DbSqlSession#prependDatabaseTablePrefix(String)}.
   *
   * <ul>
   *   <li>Then return {@code Table Name}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#prependDatabaseTablePrefix(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String DbSqlSession.prependDatabaseTablePrefix(String)"})
  public void testPrependDatabaseTablePrefix_thenReturnTableName() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    assertEquals("Table Name", dbSqlSession.prependDatabaseTablePrefix("Table Name"));
  }

  /**
   * Test {@link DbSqlSession#getResourceForDbOperation(String, String, String)}.
   *
   * <p>Method under test: {@link DbSqlSession#getResourceForDbOperation(String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String DbSqlSession.getResourceForDbOperation(String, String, String)"})
  public void testGetResourceForDbOperation() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    assertEquals(
        "org/activiti/db//directory/activiti.null.Operation.Component.sql",
        dbSqlSession.getResourceForDbOperation("/directory", "Operation", "Component"));
  }

  /**
   * Test {@link DbSqlSession#executeSchemaResource(String, String, String, boolean)} with {@code
   * operation}, {@code component}, {@code resourceName}, {@code isOptional}.
   *
   * <p>Method under test: {@link DbSqlSession#executeSchemaResource(String, String, String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DbSqlSession.executeSchemaResource(String, String, String, boolean)"})
  public void testExecuteSchemaResourceWithOperationComponentResourceNameIsOptional() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    dbSqlSession.executeSchemaResource("Operation", "Component", "Resource Name", true);
  }

  /**
   * Test {@link DbSqlSession#executeSchemaResource(String, String, String, boolean)} with {@code
   * operation}, {@code component}, {@code resourceName}, {@code isOptional}.
   *
   * <p>Method under test: {@link DbSqlSession#executeSchemaResource(String, String, String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DbSqlSession.executeSchemaResource(String, String, String, boolean)"})
  public void testExecuteSchemaResourceWithOperationComponentResourceNameIsOptional2()
      throws SQLException {
    // Arrange
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenThrow(new ActivitiException("An error occurred"));

    Builder dataSourceResult = new Builder("42").dataSource(dataSource);
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> dbSqlSession.executeSchemaResource("Operation", "Component", "", true));
    verify(dataSource).getConnection();
  }

  /**
   * Test {@link DbSqlSession#updateDdlForMySqlVersionLowerThan56(String)}.
   *
   * <ul>
   *   <li>Then return {@code Ddl Statements}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#updateDdlForMySqlVersionLowerThan56(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String DbSqlSession.updateDdlForMySqlVersionLowerThan56(String)"})
  public void testUpdateDdlForMySqlVersionLowerThan56_thenReturnDdlStatements() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    assertEquals(
        "Ddl Statements", dbSqlSession.updateDdlForMySqlVersionLowerThan56("Ddl Statements"));
  }

  /**
   * Test {@link DbSqlSession#addSqlStatementPiece(String, String)}.
   *
   * <ul>
   *   <li>Then return {@code Sql Statement Line}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#addSqlStatementPiece(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String DbSqlSession.addSqlStatementPiece(String, String)"})
  public void testAddSqlStatementPiece_thenReturnSqlStatementLine() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act and Assert
    assertEquals(
        "Sql Statement \nLine", dbSqlSession.addSqlStatementPiece("Sql Statement", "Line"));
  }

  /**
   * Test {@link DbSqlSession#createDeploymentQuery()}.
   *
   * <ul>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#createDeploymentQuery()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentQueryImpl DbSqlSession.createDeploymentQuery()"})
  public void testCreateDeploymentQuery_thenReturnOrderByIsResIdAsc() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    DeploymentQueryImpl actualCreateDeploymentQueryResult = dbSqlSession.createDeploymentQuery();

    // Assert
    assertEquals("RES.ID_ asc", actualCreateDeploymentQueryResult.getOrderBy());
    assertEquals("RES.ID_ asc", actualCreateDeploymentQueryResult.getOrderByColumns());
    assertNull(actualCreateDeploymentQueryResult.getDatabaseType());
    assertNull(actualCreateDeploymentQueryResult.getCategory());
    assertNull(actualCreateDeploymentQueryResult.getCategoryNotEquals());
    assertNull(actualCreateDeploymentQueryResult.getDeploymentId());
    assertNull(actualCreateDeploymentQueryResult.getName());
    assertNull(actualCreateDeploymentQueryResult.getNameLike());
    assertNull(actualCreateDeploymentQueryResult.getProcessDefinitionKey());
    assertNull(actualCreateDeploymentQueryResult.getProcessDefinitionKeyLike());
    assertNull(actualCreateDeploymentQueryResult.getTenantId());
    assertNull(actualCreateDeploymentQueryResult.getTenantIdLike());
    assertEquals(0, actualCreateDeploymentQueryResult.getFirstResult());
    assertEquals(1, actualCreateDeploymentQueryResult.getFirstRow());
    assertFalse(actualCreateDeploymentQueryResult.isLatestVersion());
    assertFalse(actualCreateDeploymentQueryResult.isWithoutTenantId());
    assertEquals(Integer.MAX_VALUE, actualCreateDeploymentQueryResult.getLastRow());
    assertEquals(Integer.MAX_VALUE, actualCreateDeploymentQueryResult.getMaxResults());
    Object actualParameter = actualCreateDeploymentQueryResult.getParameter();
    assertSame(actualCreateDeploymentQueryResult, actualParameter);
  }

  /**
   * Test {@link DbSqlSession#createModelQueryImpl()}.
   *
   * <ul>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#createModelQueryImpl()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ModelQueryImpl DbSqlSession.createModelQueryImpl()"})
  public void testCreateModelQueryImpl_thenReturnOrderByIsResIdAsc() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    ModelQueryImpl actualCreateModelQueryImplResult = dbSqlSession.createModelQueryImpl();

    // Assert
    assertEquals("RES.ID_ asc", actualCreateModelQueryImplResult.getOrderBy());
    assertEquals("RES.ID_ asc", actualCreateModelQueryImplResult.getOrderByColumns());
    assertNull(actualCreateModelQueryImplResult.getVersion());
    assertNull(actualCreateModelQueryImplResult.getDatabaseType());
    assertNull(actualCreateModelQueryImplResult.getCategory());
    assertNull(actualCreateModelQueryImplResult.getCategoryLike());
    assertNull(actualCreateModelQueryImplResult.getCategoryNotEquals());
    assertNull(actualCreateModelQueryImplResult.getDeploymentId());
    assertNull(actualCreateModelQueryImplResult.getId());
    assertNull(actualCreateModelQueryImplResult.getKey());
    assertNull(actualCreateModelQueryImplResult.getName());
    assertNull(actualCreateModelQueryImplResult.getNameLike());
    assertNull(actualCreateModelQueryImplResult.getTenantId());
    assertNull(actualCreateModelQueryImplResult.getTenantIdLike());
    assertEquals(0, actualCreateModelQueryImplResult.getFirstResult());
    assertEquals(1, actualCreateModelQueryImplResult.getFirstRow());
    assertFalse(actualCreateModelQueryImplResult.isDeployed());
    assertFalse(actualCreateModelQueryImplResult.isLatest());
    assertFalse(actualCreateModelQueryImplResult.isNotDeployed());
    assertFalse(actualCreateModelQueryImplResult.isWithoutTenantId());
    assertEquals(Integer.MAX_VALUE, actualCreateModelQueryImplResult.getLastRow());
    assertEquals(Integer.MAX_VALUE, actualCreateModelQueryImplResult.getMaxResults());
    Object actualParameter = actualCreateModelQueryImplResult.getParameter();
    assertSame(actualCreateModelQueryImplResult, actualParameter);
  }

  /**
   * Test {@link DbSqlSession#createProcessDefinitionQuery()}.
   *
   * <p>Method under test: {@link DbSqlSession#createProcessDefinitionQuery()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessDefinitionQueryImpl DbSqlSession.createProcessDefinitionQuery()"})
  public void testCreateProcessDefinitionQuery() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    ProcessDefinitionQueryImpl actualCreateProcessDefinitionQueryResult =
        dbSqlSession.createProcessDefinitionQuery();

    // Assert
    assertEquals("RES.ID_ asc", actualCreateProcessDefinitionQueryResult.getOrderBy());
    assertEquals("RES.ID_ asc", actualCreateProcessDefinitionQueryResult.getOrderByColumns());
    assertNull(actualCreateProcessDefinitionQueryResult.getVersion());
    assertNull(actualCreateProcessDefinitionQueryResult.getVersionGt());
    assertNull(actualCreateProcessDefinitionQueryResult.getVersionGte());
    assertNull(actualCreateProcessDefinitionQueryResult.getVersionLt());
    assertNull(actualCreateProcessDefinitionQueryResult.getVersionLte());
    assertNull(actualCreateProcessDefinitionQueryResult.getDatabaseType());
    assertNull(actualCreateProcessDefinitionQueryResult.getAuthorizationUserId());
    assertNull(actualCreateProcessDefinitionQueryResult.getCategory());
    assertNull(actualCreateProcessDefinitionQueryResult.getCategoryLike());
    assertNull(actualCreateProcessDefinitionQueryResult.getCategoryNotEquals());
    assertNull(actualCreateProcessDefinitionQueryResult.getDeploymentId());
    assertNull(actualCreateProcessDefinitionQueryResult.getEventSubscriptionName());
    assertNull(actualCreateProcessDefinitionQueryResult.getEventSubscriptionType());
    assertNull(actualCreateProcessDefinitionQueryResult.getId());
    assertNull(actualCreateProcessDefinitionQueryResult.getIdOrKey());
    assertNull(actualCreateProcessDefinitionQueryResult.getKey());
    assertNull(actualCreateProcessDefinitionQueryResult.getKeyLike());
    assertNull(actualCreateProcessDefinitionQueryResult.getName());
    assertNull(actualCreateProcessDefinitionQueryResult.getNameLike());
    assertNull(actualCreateProcessDefinitionQueryResult.getProcDefId());
    assertNull(actualCreateProcessDefinitionQueryResult.getResourceName());
    assertNull(actualCreateProcessDefinitionQueryResult.getResourceNameLike());
    assertNull(actualCreateProcessDefinitionQueryResult.getTenantId());
    assertNull(actualCreateProcessDefinitionQueryResult.getTenantIdLike());
    assertNull(actualCreateProcessDefinitionQueryResult.getAuthorizationGroups());
    assertNull(actualCreateProcessDefinitionQueryResult.getDeploymentIds());
    assertNull(actualCreateProcessDefinitionQueryResult.getIds());
    assertNull(actualCreateProcessDefinitionQueryResult.getKeys());
    assertNull(actualCreateProcessDefinitionQueryResult.getSuspensionState());
    assertEquals(0, actualCreateProcessDefinitionQueryResult.getFirstResult());
    assertEquals(1, actualCreateProcessDefinitionQueryResult.getFirstRow());
    assertFalse(actualCreateProcessDefinitionQueryResult.isLatest());
    assertFalse(actualCreateProcessDefinitionQueryResult.isWithoutTenantId());
    assertEquals(Integer.MAX_VALUE, actualCreateProcessDefinitionQueryResult.getLastRow());
    assertEquals(Integer.MAX_VALUE, actualCreateProcessDefinitionQueryResult.getMaxResults());
    Object actualParameter = actualCreateProcessDefinitionQueryResult.getParameter();
    assertSame(actualCreateProcessDefinitionQueryResult, actualParameter);
  }

  /**
   * Test {@link DbSqlSession#createProcessDefinitionQuery()}.
   *
   * <p>Method under test: {@link DbSqlSession#createProcessDefinitionQuery()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessDefinitionQueryImpl DbSqlSession.createProcessDefinitionQuery()"})
  public void testCreateProcessDefinitionQuery2() {
    // Arrange
    ProfilingDbSqlSessionFactory dbSqlSessionFactory = new ProfilingDbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    ProcessDefinitionQueryImpl actualCreateProcessDefinitionQueryResult =
        dbSqlSession.createProcessDefinitionQuery();

    // Assert
    assertEquals("RES.ID_ asc", actualCreateProcessDefinitionQueryResult.getOrderBy());
    assertEquals("RES.ID_ asc", actualCreateProcessDefinitionQueryResult.getOrderByColumns());
    assertNull(actualCreateProcessDefinitionQueryResult.getVersion());
    assertNull(actualCreateProcessDefinitionQueryResult.getVersionGt());
    assertNull(actualCreateProcessDefinitionQueryResult.getVersionGte());
    assertNull(actualCreateProcessDefinitionQueryResult.getVersionLt());
    assertNull(actualCreateProcessDefinitionQueryResult.getVersionLte());
    assertNull(actualCreateProcessDefinitionQueryResult.getDatabaseType());
    assertNull(actualCreateProcessDefinitionQueryResult.getAuthorizationUserId());
    assertNull(actualCreateProcessDefinitionQueryResult.getCategory());
    assertNull(actualCreateProcessDefinitionQueryResult.getCategoryLike());
    assertNull(actualCreateProcessDefinitionQueryResult.getCategoryNotEquals());
    assertNull(actualCreateProcessDefinitionQueryResult.getDeploymentId());
    assertNull(actualCreateProcessDefinitionQueryResult.getEventSubscriptionName());
    assertNull(actualCreateProcessDefinitionQueryResult.getEventSubscriptionType());
    assertNull(actualCreateProcessDefinitionQueryResult.getId());
    assertNull(actualCreateProcessDefinitionQueryResult.getIdOrKey());
    assertNull(actualCreateProcessDefinitionQueryResult.getKey());
    assertNull(actualCreateProcessDefinitionQueryResult.getKeyLike());
    assertNull(actualCreateProcessDefinitionQueryResult.getName());
    assertNull(actualCreateProcessDefinitionQueryResult.getNameLike());
    assertNull(actualCreateProcessDefinitionQueryResult.getProcDefId());
    assertNull(actualCreateProcessDefinitionQueryResult.getResourceName());
    assertNull(actualCreateProcessDefinitionQueryResult.getResourceNameLike());
    assertNull(actualCreateProcessDefinitionQueryResult.getTenantId());
    assertNull(actualCreateProcessDefinitionQueryResult.getTenantIdLike());
    assertNull(actualCreateProcessDefinitionQueryResult.getAuthorizationGroups());
    assertNull(actualCreateProcessDefinitionQueryResult.getDeploymentIds());
    assertNull(actualCreateProcessDefinitionQueryResult.getIds());
    assertNull(actualCreateProcessDefinitionQueryResult.getKeys());
    assertNull(actualCreateProcessDefinitionQueryResult.getSuspensionState());
    assertEquals(0, actualCreateProcessDefinitionQueryResult.getFirstResult());
    assertEquals(1, actualCreateProcessDefinitionQueryResult.getFirstRow());
    assertFalse(actualCreateProcessDefinitionQueryResult.isLatest());
    assertFalse(actualCreateProcessDefinitionQueryResult.isWithoutTenantId());
    assertEquals(Integer.MAX_VALUE, actualCreateProcessDefinitionQueryResult.getLastRow());
    assertEquals(Integer.MAX_VALUE, actualCreateProcessDefinitionQueryResult.getMaxResults());
    Object actualParameter = actualCreateProcessDefinitionQueryResult.getParameter();
    assertSame(actualCreateProcessDefinitionQueryResult, actualParameter);
  }

  /**
   * Test {@link DbSqlSession#createProcessInstanceQuery()}.
   *
   * <ul>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#createProcessInstanceQuery()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessInstanceQueryImpl DbSqlSession.createProcessInstanceQuery()"})
  public void testCreateProcessInstanceQuery_thenReturnOrderByIsResIdAsc() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    ProcessInstanceQueryImpl actualCreateProcessInstanceQueryResult =
        dbSqlSession.createProcessInstanceQuery();

    // Assert
    assertEquals("RES.ID_ asc", actualCreateProcessInstanceQueryResult.getOrderBy());
    assertEquals("RES.ID_ asc", actualCreateProcessInstanceQueryResult.getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc", actualCreateProcessInstanceQueryResult.getMssqlOrDB2OrderBy());
    assertNull(actualCreateProcessInstanceQueryResult.getProcessDefinitionVersion());
    assertNull(actualCreateProcessInstanceQueryResult.getProcessInstanceVariablesLimit());
    assertNull(actualCreateProcessInstanceQueryResult.getDatabaseType());
    assertNull(actualCreateProcessInstanceQueryResult.getActivityId());
    assertNull(actualCreateProcessInstanceQueryResult.getBusinessKey());
    assertNull(actualCreateProcessInstanceQueryResult.getDeploymentId());
    assertNull(actualCreateProcessInstanceQueryResult.getExecutionId());
    assertNull(actualCreateProcessInstanceQueryResult.getInvolvedUser());
    assertNull(actualCreateProcessInstanceQueryResult.getName());
    assertNull(actualCreateProcessInstanceQueryResult.getNameLike());
    assertNull(actualCreateProcessInstanceQueryResult.getNameLikeIgnoreCase());
    assertNull(actualCreateProcessInstanceQueryResult.getParentId());
    assertNull(actualCreateProcessInstanceQueryResult.getProcessDefinitionCategory());
    assertNull(actualCreateProcessInstanceQueryResult.getProcessDefinitionId());
    assertNull(actualCreateProcessInstanceQueryResult.getProcessDefinitionKey());
    assertNull(actualCreateProcessInstanceQueryResult.getProcessDefinitionName());
    assertNull(actualCreateProcessInstanceQueryResult.getProcessInstanceId());
    assertNull(actualCreateProcessInstanceQueryResult.getRootProcessInstanceId());
    assertNull(actualCreateProcessInstanceQueryResult.getStartedBy());
    assertNull(actualCreateProcessInstanceQueryResult.getSubProcessInstanceId());
    assertNull(actualCreateProcessInstanceQueryResult.getSuperProcessInstanceId());
    assertNull(actualCreateProcessInstanceQueryResult.getTenantId());
    assertNull(actualCreateProcessInstanceQueryResult.getTenantIdLike());
    assertNull(actualCreateProcessInstanceQueryResult.getStartedAfter());
    assertNull(actualCreateProcessInstanceQueryResult.getStartedBefore());
    assertNull(actualCreateProcessInstanceQueryResult.getDeploymentIds());
    assertNull(actualCreateProcessInstanceQueryResult.getInvolvedGroups());
    assertNull(actualCreateProcessInstanceQueryResult.getEventSubscriptions());
    assertNull(actualCreateProcessInstanceQueryResult.getProcessDefinitionIds());
    assertNull(actualCreateProcessInstanceQueryResult.getProcessDefinitionKeys());
    assertNull(actualCreateProcessInstanceQueryResult.getProcessInstanceIds());
    assertNull(actualCreateProcessInstanceQueryResult.getSuspensionState());
    assertEquals(0, actualCreateProcessInstanceQueryResult.getFirstResult());
    assertEquals(1, actualCreateProcessInstanceQueryResult.getFirstRow());
    assertFalse(actualCreateProcessInstanceQueryResult.hasLocalQueryVariableValue());
    assertFalse(actualCreateProcessInstanceQueryResult.hasNonLocalQueryVariableValue());
    assertFalse(actualCreateProcessInstanceQueryResult.isExcludeSubprocesses());
    assertFalse(
        actualCreateProcessInstanceQueryResult.isIncludeChildExecutionsWithBusinessKeyQuery());
    assertFalse(actualCreateProcessInstanceQueryResult.isIncludeProcessVariables());
    assertFalse(actualCreateProcessInstanceQueryResult.isOnlyChildExecutions());
    assertFalse(actualCreateProcessInstanceQueryResult.isOnlyProcessInstanceExecutions());
    assertFalse(actualCreateProcessInstanceQueryResult.isOnlySubProcessExecutions());
    assertFalse(actualCreateProcessInstanceQueryResult.isWithoutTenantId());
    assertTrue(actualCreateProcessInstanceQueryResult.getQueryVariableValues().isEmpty());
    assertTrue(actualCreateProcessInstanceQueryResult.getOrQueryObjects().isEmpty());
    assertTrue(actualCreateProcessInstanceQueryResult.getOnlyProcessInstances());
    assertEquals(Integer.MAX_VALUE, actualCreateProcessInstanceQueryResult.getLastRow());
    assertEquals(Integer.MAX_VALUE, actualCreateProcessInstanceQueryResult.getMaxResults());
    Object actualParameter = actualCreateProcessInstanceQueryResult.getParameter();
    assertSame(actualCreateProcessInstanceQueryResult, actualParameter);
  }

  /**
   * Test {@link DbSqlSession#createExecutionQuery()}.
   *
   * <ul>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#createExecutionQuery()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQueryImpl DbSqlSession.createExecutionQuery()"})
  public void testCreateExecutionQuery_thenReturnOrderByIsResIdAsc() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    ExecutionQueryImpl actualCreateExecutionQueryResult = dbSqlSession.createExecutionQuery();

    // Assert
    assertEquals("RES.ID_ asc", actualCreateExecutionQueryResult.getOrderBy());
    assertEquals("RES.ID_ asc", actualCreateExecutionQueryResult.getOrderByColumns());
    assertNull(actualCreateExecutionQueryResult.getProcessDefinitionVersion());
    assertNull(actualCreateExecutionQueryResult.getDatabaseType());
    assertNull(actualCreateExecutionQueryResult.getActivityId());
    assertNull(actualCreateExecutionQueryResult.getBusinessKey());
    assertNull(actualCreateExecutionQueryResult.getExecutionId());
    assertNull(actualCreateExecutionQueryResult.getInvolvedUser());
    assertNull(actualCreateExecutionQueryResult.getName());
    assertNull(actualCreateExecutionQueryResult.getNameLike());
    assertNull(actualCreateExecutionQueryResult.getNameLikeIgnoreCase());
    assertNull(actualCreateExecutionQueryResult.getParentId());
    assertNull(actualCreateExecutionQueryResult.getProcessDefinitionCategory());
    assertNull(actualCreateExecutionQueryResult.getProcessDefinitionId());
    assertNull(actualCreateExecutionQueryResult.getProcessDefinitionKey());
    assertNull(actualCreateExecutionQueryResult.getProcessDefinitionName());
    assertNull(actualCreateExecutionQueryResult.getProcessInstanceId());
    assertNull(actualCreateExecutionQueryResult.getProcessInstanceIds());
    assertNull(actualCreateExecutionQueryResult.getRootProcessInstanceId());
    assertNull(actualCreateExecutionQueryResult.getStartedBy());
    assertNull(actualCreateExecutionQueryResult.getSubProcessInstanceId());
    assertNull(actualCreateExecutionQueryResult.getSuperProcessInstanceId());
    assertNull(actualCreateExecutionQueryResult.getTenantId());
    assertNull(actualCreateExecutionQueryResult.getTenantIdLike());
    assertNull(actualCreateExecutionQueryResult.getStartedAfter());
    assertNull(actualCreateExecutionQueryResult.getStartedBefore());
    assertNull(actualCreateExecutionQueryResult.getInvolvedGroups());
    assertNull(actualCreateExecutionQueryResult.getEventSubscriptions());
    assertNull(actualCreateExecutionQueryResult.getProcessDefinitionIds());
    assertNull(actualCreateExecutionQueryResult.getProcessDefinitionKeys());
    assertNull(actualCreateExecutionQueryResult.getSuspensionState());
    assertEquals(0, actualCreateExecutionQueryResult.getFirstResult());
    assertEquals(1, actualCreateExecutionQueryResult.getFirstRow());
    assertFalse(actualCreateExecutionQueryResult.hasLocalQueryVariableValue());
    assertFalse(actualCreateExecutionQueryResult.hasNonLocalQueryVariableValue());
    assertFalse(actualCreateExecutionQueryResult.getOnlyProcessInstances());
    assertFalse(actualCreateExecutionQueryResult.isActive());
    assertFalse(actualCreateExecutionQueryResult.isExcludeSubprocesses());
    assertFalse(actualCreateExecutionQueryResult.isIncludeChildExecutionsWithBusinessKeyQuery());
    assertFalse(actualCreateExecutionQueryResult.isOnlyChildExecutions());
    assertFalse(actualCreateExecutionQueryResult.isOnlyProcessInstanceExecutions());
    assertFalse(actualCreateExecutionQueryResult.isOnlySubProcessExecutions());
    assertFalse(actualCreateExecutionQueryResult.isProcessInstancesOnly());
    assertFalse(actualCreateExecutionQueryResult.isWithoutTenantId());
    assertTrue(actualCreateExecutionQueryResult.getQueryVariableValues().isEmpty());
    assertEquals(Integer.MAX_VALUE, actualCreateExecutionQueryResult.getLastRow());
    assertEquals(Integer.MAX_VALUE, actualCreateExecutionQueryResult.getMaxResults());
    Object actualParameter = actualCreateExecutionQueryResult.getParameter();
    assertSame(actualCreateExecutionQueryResult, actualParameter);
  }

  /**
   * Test {@link DbSqlSession#createTaskQuery()}.
   *
   * <p>Method under test: {@link DbSqlSession#createTaskQuery()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TaskQueryImpl DbSqlSession.createTaskQuery()"})
  public void testCreateTaskQuery() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    TaskQueryImpl actualCreateTaskQueryResult = dbSqlSession.createTaskQuery();

    // Assert
    assertEquals("RES.ID_ asc", actualCreateTaskQueryResult.getOrderBy());
    assertEquals("RES.ID_ asc", actualCreateTaskQueryResult.getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc", actualCreateTaskQueryResult.getMssqlOrDB2OrderBy());
    assertNull(actualCreateTaskQueryResult.getMaxPriority());
    assertNull(actualCreateTaskQueryResult.getMinPriority());
    assertNull(actualCreateTaskQueryResult.getPriority());
    assertNull(actualCreateTaskQueryResult.getTaskVariablesLimit());
    assertNull(actualCreateTaskQueryResult.getDatabaseType());
    assertNull(actualCreateTaskQueryResult.getAssignee());
    assertNull(actualCreateTaskQueryResult.getAssigneeLike());
    assertNull(actualCreateTaskQueryResult.getAssigneeLikeIgnoreCase());
    assertNull(actualCreateTaskQueryResult.getCandidateGroup());
    assertNull(actualCreateTaskQueryResult.getCandidateUser());
    assertNull(actualCreateTaskQueryResult.getCategory());
    assertNull(actualCreateTaskQueryResult.getDelegationStateString());
    assertNull(actualCreateTaskQueryResult.getDeploymentId());
    assertNull(actualCreateTaskQueryResult.getDescription());
    assertNull(actualCreateTaskQueryResult.getDescriptionLike());
    assertNull(actualCreateTaskQueryResult.getDescriptionLikeIgnoreCase());
    assertNull(actualCreateTaskQueryResult.getExecutionId());
    assertNull(actualCreateTaskQueryResult.getInvolvedUser());
    assertNull(actualCreateTaskQueryResult.getKey());
    assertNull(actualCreateTaskQueryResult.getKeyLike());
    assertNull(actualCreateTaskQueryResult.getLocale());
    assertNull(actualCreateTaskQueryResult.getName());
    assertNull(actualCreateTaskQueryResult.getNameLike());
    assertNull(actualCreateTaskQueryResult.getNameLikeIgnoreCase());
    assertNull(actualCreateTaskQueryResult.getOwner());
    assertNull(actualCreateTaskQueryResult.getOwnerLike());
    assertNull(actualCreateTaskQueryResult.getOwnerLikeIgnoreCase());
    assertNull(actualCreateTaskQueryResult.getProcessDefinitionId());
    assertNull(actualCreateTaskQueryResult.getProcessDefinitionKey());
    assertNull(actualCreateTaskQueryResult.getProcessDefinitionKeyLike());
    assertNull(actualCreateTaskQueryResult.getProcessDefinitionKeyLikeIgnoreCase());
    assertNull(actualCreateTaskQueryResult.getProcessDefinitionName());
    assertNull(actualCreateTaskQueryResult.getProcessDefinitionNameLike());
    assertNull(actualCreateTaskQueryResult.getProcessInstanceBusinessKey());
    assertNull(actualCreateTaskQueryResult.getProcessInstanceBusinessKeyLike());
    assertNull(actualCreateTaskQueryResult.getProcessInstanceBusinessKeyLikeIgnoreCase());
    assertNull(actualCreateTaskQueryResult.getProcessInstanceId());
    assertNull(actualCreateTaskQueryResult.getTaskId());
    assertNull(actualCreateTaskQueryResult.getTaskParentTaskId());
    assertNull(actualCreateTaskQueryResult.getTenantId());
    assertNull(actualCreateTaskQueryResult.getTenantIdLike());
    assertNull(actualCreateTaskQueryResult.getUserIdForCandidateAndAssignee());
    assertNull(actualCreateTaskQueryResult.getCreateTime());
    assertNull(actualCreateTaskQueryResult.getCreateTimeAfter());
    assertNull(actualCreateTaskQueryResult.getCreateTimeBefore());
    assertNull(actualCreateTaskQueryResult.getDueAfter());
    assertNull(actualCreateTaskQueryResult.getDueBefore());
    assertNull(actualCreateTaskQueryResult.getDueDate());
    assertNull(actualCreateTaskQueryResult.getAssigneeIds());
    assertNull(actualCreateTaskQueryResult.getCandidateGroups());
    assertNull(actualCreateTaskQueryResult.getDeploymentIds());
    assertNull(actualCreateTaskQueryResult.getInvolvedGroups());
    assertNull(actualCreateTaskQueryResult.getNameList());
    assertNull(actualCreateTaskQueryResult.getNameListIgnoreCase());
    assertNull(actualCreateTaskQueryResult.getProcessCategoryInList());
    assertNull(actualCreateTaskQueryResult.getProcessCategoryNotInList());
    assertNull(actualCreateTaskQueryResult.getProcessDefinitionKeys());
    assertNull(actualCreateTaskQueryResult.getProcessInstanceIds());
    assertNull(actualCreateTaskQueryResult.getSuspensionState());
    assertNull(actualCreateTaskQueryResult.getDelegationState());
    assertEquals(0, actualCreateTaskQueryResult.getFirstResult());
    assertEquals(1, actualCreateTaskQueryResult.getFirstRow());
    assertFalse(actualCreateTaskQueryResult.hasLocalQueryVariableValue());
    assertFalse(actualCreateTaskQueryResult.hasNonLocalQueryVariableValue());
    assertFalse(actualCreateTaskQueryResult.getExcludeSubtasks());
    assertFalse(actualCreateTaskQueryResult.getNoDelegationState());
    assertFalse(actualCreateTaskQueryResult.getUnassigned());
    assertFalse(actualCreateTaskQueryResult.isBothCandidateAndAssigned());
    assertFalse(actualCreateTaskQueryResult.isIncludeProcessVariables());
    assertFalse(actualCreateTaskQueryResult.isIncludeTaskLocalVariables());
    assertFalse(actualCreateTaskQueryResult.isOrActive());
    assertFalse(actualCreateTaskQueryResult.isWithoutDueDate());
    assertFalse(actualCreateTaskQueryResult.isWithoutTenantId());
    assertTrue(actualCreateTaskQueryResult.getQueryVariableValues().isEmpty());
    assertTrue(actualCreateTaskQueryResult.getOrQueryObjects().isEmpty());
    assertEquals(Integer.MAX_VALUE, actualCreateTaskQueryResult.getLastRow());
    assertEquals(Integer.MAX_VALUE, actualCreateTaskQueryResult.getMaxResults());
    Object actualParameter = actualCreateTaskQueryResult.getParameter();
    assertSame(actualCreateTaskQueryResult, actualParameter);
  }

  /**
   * Test {@link DbSqlSession#createTaskQuery()}.
   *
   * <p>Method under test: {@link DbSqlSession#createTaskQuery()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TaskQueryImpl DbSqlSession.createTaskQuery()"})
  public void testCreateTaskQuery2() {
    // Arrange
    ProfilingDbSqlSessionFactory dbSqlSessionFactory = new ProfilingDbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    TaskQueryImpl actualCreateTaskQueryResult = dbSqlSession.createTaskQuery();

    // Assert
    assertEquals("RES.ID_ asc", actualCreateTaskQueryResult.getOrderBy());
    assertEquals("RES.ID_ asc", actualCreateTaskQueryResult.getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc", actualCreateTaskQueryResult.getMssqlOrDB2OrderBy());
    assertNull(actualCreateTaskQueryResult.getMaxPriority());
    assertNull(actualCreateTaskQueryResult.getMinPriority());
    assertNull(actualCreateTaskQueryResult.getPriority());
    assertNull(actualCreateTaskQueryResult.getTaskVariablesLimit());
    assertNull(actualCreateTaskQueryResult.getDatabaseType());
    assertNull(actualCreateTaskQueryResult.getAssignee());
    assertNull(actualCreateTaskQueryResult.getAssigneeLike());
    assertNull(actualCreateTaskQueryResult.getAssigneeLikeIgnoreCase());
    assertNull(actualCreateTaskQueryResult.getCandidateGroup());
    assertNull(actualCreateTaskQueryResult.getCandidateUser());
    assertNull(actualCreateTaskQueryResult.getCategory());
    assertNull(actualCreateTaskQueryResult.getDelegationStateString());
    assertNull(actualCreateTaskQueryResult.getDeploymentId());
    assertNull(actualCreateTaskQueryResult.getDescription());
    assertNull(actualCreateTaskQueryResult.getDescriptionLike());
    assertNull(actualCreateTaskQueryResult.getDescriptionLikeIgnoreCase());
    assertNull(actualCreateTaskQueryResult.getExecutionId());
    assertNull(actualCreateTaskQueryResult.getInvolvedUser());
    assertNull(actualCreateTaskQueryResult.getKey());
    assertNull(actualCreateTaskQueryResult.getKeyLike());
    assertNull(actualCreateTaskQueryResult.getLocale());
    assertNull(actualCreateTaskQueryResult.getName());
    assertNull(actualCreateTaskQueryResult.getNameLike());
    assertNull(actualCreateTaskQueryResult.getNameLikeIgnoreCase());
    assertNull(actualCreateTaskQueryResult.getOwner());
    assertNull(actualCreateTaskQueryResult.getOwnerLike());
    assertNull(actualCreateTaskQueryResult.getOwnerLikeIgnoreCase());
    assertNull(actualCreateTaskQueryResult.getProcessDefinitionId());
    assertNull(actualCreateTaskQueryResult.getProcessDefinitionKey());
    assertNull(actualCreateTaskQueryResult.getProcessDefinitionKeyLike());
    assertNull(actualCreateTaskQueryResult.getProcessDefinitionKeyLikeIgnoreCase());
    assertNull(actualCreateTaskQueryResult.getProcessDefinitionName());
    assertNull(actualCreateTaskQueryResult.getProcessDefinitionNameLike());
    assertNull(actualCreateTaskQueryResult.getProcessInstanceBusinessKey());
    assertNull(actualCreateTaskQueryResult.getProcessInstanceBusinessKeyLike());
    assertNull(actualCreateTaskQueryResult.getProcessInstanceBusinessKeyLikeIgnoreCase());
    assertNull(actualCreateTaskQueryResult.getProcessInstanceId());
    assertNull(actualCreateTaskQueryResult.getTaskId());
    assertNull(actualCreateTaskQueryResult.getTaskParentTaskId());
    assertNull(actualCreateTaskQueryResult.getTenantId());
    assertNull(actualCreateTaskQueryResult.getTenantIdLike());
    assertNull(actualCreateTaskQueryResult.getUserIdForCandidateAndAssignee());
    assertNull(actualCreateTaskQueryResult.getCreateTime());
    assertNull(actualCreateTaskQueryResult.getCreateTimeAfter());
    assertNull(actualCreateTaskQueryResult.getCreateTimeBefore());
    assertNull(actualCreateTaskQueryResult.getDueAfter());
    assertNull(actualCreateTaskQueryResult.getDueBefore());
    assertNull(actualCreateTaskQueryResult.getDueDate());
    assertNull(actualCreateTaskQueryResult.getAssigneeIds());
    assertNull(actualCreateTaskQueryResult.getCandidateGroups());
    assertNull(actualCreateTaskQueryResult.getDeploymentIds());
    assertNull(actualCreateTaskQueryResult.getInvolvedGroups());
    assertNull(actualCreateTaskQueryResult.getNameList());
    assertNull(actualCreateTaskQueryResult.getNameListIgnoreCase());
    assertNull(actualCreateTaskQueryResult.getProcessCategoryInList());
    assertNull(actualCreateTaskQueryResult.getProcessCategoryNotInList());
    assertNull(actualCreateTaskQueryResult.getProcessDefinitionKeys());
    assertNull(actualCreateTaskQueryResult.getProcessInstanceIds());
    assertNull(actualCreateTaskQueryResult.getSuspensionState());
    assertNull(actualCreateTaskQueryResult.getDelegationState());
    assertEquals(0, actualCreateTaskQueryResult.getFirstResult());
    assertEquals(1, actualCreateTaskQueryResult.getFirstRow());
    assertFalse(actualCreateTaskQueryResult.hasLocalQueryVariableValue());
    assertFalse(actualCreateTaskQueryResult.hasNonLocalQueryVariableValue());
    assertFalse(actualCreateTaskQueryResult.getExcludeSubtasks());
    assertFalse(actualCreateTaskQueryResult.getNoDelegationState());
    assertFalse(actualCreateTaskQueryResult.getUnassigned());
    assertFalse(actualCreateTaskQueryResult.isBothCandidateAndAssigned());
    assertFalse(actualCreateTaskQueryResult.isIncludeProcessVariables());
    assertFalse(actualCreateTaskQueryResult.isIncludeTaskLocalVariables());
    assertFalse(actualCreateTaskQueryResult.isOrActive());
    assertFalse(actualCreateTaskQueryResult.isWithoutDueDate());
    assertFalse(actualCreateTaskQueryResult.isWithoutTenantId());
    assertTrue(actualCreateTaskQueryResult.getQueryVariableValues().isEmpty());
    assertTrue(actualCreateTaskQueryResult.getOrQueryObjects().isEmpty());
    assertEquals(Integer.MAX_VALUE, actualCreateTaskQueryResult.getLastRow());
    assertEquals(Integer.MAX_VALUE, actualCreateTaskQueryResult.getMaxResults());
    Object actualParameter = actualCreateTaskQueryResult.getParameter();
    assertSame(actualCreateTaskQueryResult, actualParameter);
  }

  /**
   * Test {@link DbSqlSession#createJobQuery()}.
   *
   * <ul>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#createJobQuery()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JobQueryImpl DbSqlSession.createJobQuery()"})
  public void testCreateJobQuery_thenReturnOrderByIsResIdAsc() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    JobQueryImpl actualCreateJobQueryResult = dbSqlSession.createJobQuery();

    // Assert
    assertEquals("RES.ID_ asc", actualCreateJobQueryResult.getOrderBy());
    assertEquals("RES.ID_ asc", actualCreateJobQueryResult.getOrderByColumns());
    assertNull(actualCreateJobQueryResult.getDatabaseType());
    assertNull(actualCreateJobQueryResult.getExceptionMessage());
    assertNull(actualCreateJobQueryResult.getExecutionId());
    assertNull(actualCreateJobQueryResult.getId());
    assertNull(actualCreateJobQueryResult.getProcessDefinitionId());
    assertNull(actualCreateJobQueryResult.getProcessInstanceId());
    assertNull(actualCreateJobQueryResult.getTenantId());
    assertNull(actualCreateJobQueryResult.getTenantIdLike());
    assertNull(actualCreateJobQueryResult.getDuedateHigherThan());
    assertNull(actualCreateJobQueryResult.getDuedateHigherThanOrEqual());
    assertNull(actualCreateJobQueryResult.getDuedateLowerThan());
    assertNull(actualCreateJobQueryResult.getDuedateLowerThanOrEqual());
    assertEquals(0, actualCreateJobQueryResult.getFirstResult());
    assertEquals(1, actualCreateJobQueryResult.getFirstRow());
    assertFalse(actualCreateJobQueryResult.getExecutable());
    assertFalse(actualCreateJobQueryResult.getRetriesLeft());
    assertFalse(actualCreateJobQueryResult.isNoRetriesLeft());
    assertFalse(actualCreateJobQueryResult.isOnlyLocked());
    assertFalse(actualCreateJobQueryResult.isOnlyMessages());
    assertFalse(actualCreateJobQueryResult.isOnlyTimers());
    assertFalse(actualCreateJobQueryResult.isOnlyUnlocked());
    assertFalse(actualCreateJobQueryResult.isWithException());
    assertFalse(actualCreateJobQueryResult.isWithoutTenantId());
    assertEquals(Integer.MAX_VALUE, actualCreateJobQueryResult.getLastRow());
    assertEquals(Integer.MAX_VALUE, actualCreateJobQueryResult.getMaxResults());
    Object actualParameter = actualCreateJobQueryResult.getParameter();
    assertSame(actualCreateJobQueryResult, actualParameter);
  }

  /**
   * Test {@link DbSqlSession#createHistoricProcessInstanceQuery()}.
   *
   * <ul>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#createHistoricProcessInstanceQuery()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQueryImpl DbSqlSession.createHistoricProcessInstanceQuery()"
  })
  public void testCreateHistoricProcessInstanceQuery_thenReturnOrderByIsResIdAsc() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    HistoricProcessInstanceQueryImpl actualCreateHistoricProcessInstanceQueryResult =
        dbSqlSession.createHistoricProcessInstanceQuery();

    // Assert
    assertEquals("RES.ID_ asc", actualCreateHistoricProcessInstanceQueryResult.getOrderBy());
    assertEquals("RES.ID_ asc", actualCreateHistoricProcessInstanceQueryResult.getOrderByColumns());
    assertEquals(
        "TEMPRES_ID_ asc", actualCreateHistoricProcessInstanceQueryResult.getMssqlOrDB2OrderBy());
    assertEquals(
        "null:%:%", actualCreateHistoricProcessInstanceQueryResult.getProcessDefinitionIdLike());
    assertNull(actualCreateHistoricProcessInstanceQueryResult.getProcessDefinitionVersion());
    assertNull(actualCreateHistoricProcessInstanceQueryResult.getProcessInstanceVariablesLimit());
    assertNull(actualCreateHistoricProcessInstanceQueryResult.getDatabaseType());
    assertNull(actualCreateHistoricProcessInstanceQueryResult.getBusinessKey());
    assertNull(actualCreateHistoricProcessInstanceQueryResult.getDeploymentId());
    assertNull(actualCreateHistoricProcessInstanceQueryResult.getInvolvedUser());
    assertNull(actualCreateHistoricProcessInstanceQueryResult.getName());
    assertNull(actualCreateHistoricProcessInstanceQueryResult.getNameLike());
    assertNull(actualCreateHistoricProcessInstanceQueryResult.getNameLikeIgnoreCase());
    assertNull(actualCreateHistoricProcessInstanceQueryResult.getProcessDefinitionCategory());
    assertNull(actualCreateHistoricProcessInstanceQueryResult.getProcessDefinitionId());
    assertNull(actualCreateHistoricProcessInstanceQueryResult.getProcessDefinitionKey());
    assertNull(actualCreateHistoricProcessInstanceQueryResult.getProcessDefinitionName());
    assertNull(actualCreateHistoricProcessInstanceQueryResult.getProcessInstanceId());
    assertNull(actualCreateHistoricProcessInstanceQueryResult.getStartedBy());
    assertNull(actualCreateHistoricProcessInstanceQueryResult.getSuperProcessInstanceId());
    assertNull(actualCreateHistoricProcessInstanceQueryResult.getTenantId());
    assertNull(actualCreateHistoricProcessInstanceQueryResult.getTenantIdLike());
    assertNull(actualCreateHistoricProcessInstanceQueryResult.getFinishedAfter());
    assertNull(actualCreateHistoricProcessInstanceQueryResult.getFinishedBefore());
    assertNull(actualCreateHistoricProcessInstanceQueryResult.getStartedAfter());
    assertNull(actualCreateHistoricProcessInstanceQueryResult.getStartedBefore());
    assertNull(actualCreateHistoricProcessInstanceQueryResult.getDeploymentIds());
    assertNull(actualCreateHistoricProcessInstanceQueryResult.getInvolvedGroups());
    assertNull(actualCreateHistoricProcessInstanceQueryResult.getProcessDefinitionKeyIn());
    assertNull(actualCreateHistoricProcessInstanceQueryResult.getProcessKeyNotIn());
    assertNull(actualCreateHistoricProcessInstanceQueryResult.getProcessInstanceIds());
    assertEquals(0, actualCreateHistoricProcessInstanceQueryResult.getFirstResult());
    assertEquals(1, actualCreateHistoricProcessInstanceQueryResult.getFirstRow());
    assertFalse(actualCreateHistoricProcessInstanceQueryResult.hasLocalQueryVariableValue());
    assertFalse(actualCreateHistoricProcessInstanceQueryResult.hasNonLocalQueryVariableValue());
    assertFalse(actualCreateHistoricProcessInstanceQueryResult.isDeleted());
    assertFalse(actualCreateHistoricProcessInstanceQueryResult.isExcludeSubprocesses());
    assertFalse(actualCreateHistoricProcessInstanceQueryResult.isFinished());
    assertFalse(actualCreateHistoricProcessInstanceQueryResult.isIncludeProcessVariables());
    assertFalse(actualCreateHistoricProcessInstanceQueryResult.isNotDeleted());
    assertFalse(actualCreateHistoricProcessInstanceQueryResult.isOpen());
    assertFalse(actualCreateHistoricProcessInstanceQueryResult.isUnfinished());
    assertFalse(actualCreateHistoricProcessInstanceQueryResult.isWithException());
    assertFalse(actualCreateHistoricProcessInstanceQueryResult.isWithoutTenantId());
    assertTrue(actualCreateHistoricProcessInstanceQueryResult.getQueryVariableValues().isEmpty());
    assertTrue(actualCreateHistoricProcessInstanceQueryResult.getOrQueryObjects().isEmpty());
    assertEquals(Integer.MAX_VALUE, actualCreateHistoricProcessInstanceQueryResult.getLastRow());
    assertEquals(Integer.MAX_VALUE, actualCreateHistoricProcessInstanceQueryResult.getMaxResults());
    Object actualParameter = actualCreateHistoricProcessInstanceQueryResult.getParameter();
    assertSame(actualCreateHistoricProcessInstanceQueryResult, actualParameter);
  }

  /**
   * Test {@link DbSqlSession#createHistoricActivityInstanceQuery()}.
   *
   * <ul>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#createHistoricActivityInstanceQuery()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricActivityInstanceQueryImpl DbSqlSession.createHistoricActivityInstanceQuery()"
  })
  public void testCreateHistoricActivityInstanceQuery_thenReturnOrderByIsResIdAsc() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    HistoricActivityInstanceQueryImpl actualCreateHistoricActivityInstanceQueryResult =
        dbSqlSession.createHistoricActivityInstanceQuery();

    // Assert
    assertEquals("RES.ID_ asc", actualCreateHistoricActivityInstanceQueryResult.getOrderBy());
    assertEquals(
        "RES.ID_ asc", actualCreateHistoricActivityInstanceQueryResult.getOrderByColumns());
    assertNull(actualCreateHistoricActivityInstanceQueryResult.getDatabaseType());
    assertNull(actualCreateHistoricActivityInstanceQueryResult.getActivityId());
    assertNull(actualCreateHistoricActivityInstanceQueryResult.getActivityInstanceId());
    assertNull(actualCreateHistoricActivityInstanceQueryResult.getActivityName());
    assertNull(actualCreateHistoricActivityInstanceQueryResult.getActivityType());
    assertNull(actualCreateHistoricActivityInstanceQueryResult.getAssignee());
    assertNull(actualCreateHistoricActivityInstanceQueryResult.getDeleteReason());
    assertNull(actualCreateHistoricActivityInstanceQueryResult.getDeleteReasonLike());
    assertNull(actualCreateHistoricActivityInstanceQueryResult.getExecutionId());
    assertNull(actualCreateHistoricActivityInstanceQueryResult.getProcessDefinitionId());
    assertNull(actualCreateHistoricActivityInstanceQueryResult.getProcessInstanceId());
    assertNull(actualCreateHistoricActivityInstanceQueryResult.getTenantId());
    assertNull(actualCreateHistoricActivityInstanceQueryResult.getTenantIdLike());
    assertEquals(0, actualCreateHistoricActivityInstanceQueryResult.getFirstResult());
    assertEquals(1, actualCreateHistoricActivityInstanceQueryResult.getFirstRow());
    assertFalse(actualCreateHistoricActivityInstanceQueryResult.isFinished());
    assertFalse(actualCreateHistoricActivityInstanceQueryResult.isUnfinished());
    assertFalse(actualCreateHistoricActivityInstanceQueryResult.isWithoutTenantId());
    assertEquals(Integer.MAX_VALUE, actualCreateHistoricActivityInstanceQueryResult.getLastRow());
    assertEquals(
        Integer.MAX_VALUE, actualCreateHistoricActivityInstanceQueryResult.getMaxResults());
    Object actualParameter = actualCreateHistoricActivityInstanceQueryResult.getParameter();
    assertSame(actualCreateHistoricActivityInstanceQueryResult, actualParameter);
  }

  /**
   * Test {@link DbSqlSession#createHistoricTaskInstanceQuery()}.
   *
   * <ul>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#createHistoricTaskInstanceQuery()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricTaskInstanceQueryImpl DbSqlSession.createHistoricTaskInstanceQuery()"
  })
  public void testCreateHistoricTaskInstanceQuery_thenReturnOrderByIsResIdAsc() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    HistoricTaskInstanceQueryImpl actualCreateHistoricTaskInstanceQueryResult =
        dbSqlSession.createHistoricTaskInstanceQuery();

    // Assert
    assertEquals("RES.ID_ asc", actualCreateHistoricTaskInstanceQueryResult.getOrderBy());
    assertEquals("RES.ID_ asc", actualCreateHistoricTaskInstanceQueryResult.getOrderByColumns());
    assertEquals(
        "TEMPRES_ID_ asc", actualCreateHistoricTaskInstanceQueryResult.getMssqlOrDB2OrderBy());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getTaskMaxPriority());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getTaskMinPriority());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getTaskPriority());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getTaskVariablesLimit());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getDatabaseType());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getCandidateGroup());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getCandidateUser());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getCategory());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getDeploymentId());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getExecutionId());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getInvolvedUser());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getLocale());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getProcessDefinitionId());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getProcessDefinitionKey());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getProcessDefinitionKeyLike());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getProcessDefinitionKeyLikeIgnoreCase());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getProcessDefinitionName());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getProcessDefinitionNameLike());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getProcessInstanceBusinessKey());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getProcessInstanceBusinessKeyLike());
    assertNull(
        actualCreateHistoricTaskInstanceQueryResult.getProcessInstanceBusinessKeyLikeIgnoreCase());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getProcessInstanceId());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getTaskAssignee());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getTaskAssigneeLike());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getTaskAssigneeLikeIgnoreCase());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getTaskDefinitionKey());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getTaskDefinitionKeyLike());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getTaskDeleteReason());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getTaskDeleteReasonLike());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getTaskDescription());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getTaskDescriptionLike());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getTaskDescriptionLikeIgnoreCase());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getTaskId());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getTaskName());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getTaskNameLike());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getTaskNameLikeIgnoreCase());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getTaskOwner());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getTaskOwnerLike());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getTaskOwnerLikeIgnoreCase());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getTaskParentTaskId());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getTenantId());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getTenantIdLike());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getCompletedAfterDate());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getCompletedBeforeDate());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getCompletedDate());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getCreationAfterDate());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getCreationBeforeDate());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getCreationDate());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getDueAfter());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getDueBefore());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getDueDate());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getCandidateGroups());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getDeploymentIds());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getInvolvedGroups());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getProcessCategoryInList());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getProcessCategoryNotInList());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getProcessDefinitionKeys());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getProcessInstanceIds());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getTaskAssigneeIds());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getTaskNameList());
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getTaskNameListIgnoreCase());
    assertEquals(0, actualCreateHistoricTaskInstanceQueryResult.getFirstResult());
    assertEquals(1, actualCreateHistoricTaskInstanceQueryResult.getFirstRow());
    assertFalse(actualCreateHistoricTaskInstanceQueryResult.hasLocalQueryVariableValue());
    assertFalse(actualCreateHistoricTaskInstanceQueryResult.hasNonLocalQueryVariableValue());
    assertFalse(actualCreateHistoricTaskInstanceQueryResult.isFinished());
    assertFalse(actualCreateHistoricTaskInstanceQueryResult.isInOrStatement());
    assertFalse(actualCreateHistoricTaskInstanceQueryResult.isIncludeProcessVariables());
    assertFalse(actualCreateHistoricTaskInstanceQueryResult.isIncludeTaskLocalVariables());
    assertFalse(actualCreateHistoricTaskInstanceQueryResult.isProcessFinished());
    assertFalse(actualCreateHistoricTaskInstanceQueryResult.isProcessUnfinished());
    assertFalse(actualCreateHistoricTaskInstanceQueryResult.isUnfinished());
    assertFalse(actualCreateHistoricTaskInstanceQueryResult.isWithoutDueDate());
    assertFalse(actualCreateHistoricTaskInstanceQueryResult.isWithoutTenantId());
    assertTrue(actualCreateHistoricTaskInstanceQueryResult.getQueryVariableValues().isEmpty());
    assertTrue(actualCreateHistoricTaskInstanceQueryResult.getOrQueryObjects().isEmpty());
    assertEquals(Integer.MAX_VALUE, actualCreateHistoricTaskInstanceQueryResult.getLastRow());
    assertEquals(Integer.MAX_VALUE, actualCreateHistoricTaskInstanceQueryResult.getMaxResults());
    Object actualParameter = actualCreateHistoricTaskInstanceQueryResult.getParameter();
    assertSame(actualCreateHistoricTaskInstanceQueryResult, actualParameter);
  }

  /**
   * Test {@link DbSqlSession#createHistoricDetailQuery()}.
   *
   * <ul>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#createHistoricDetailQuery()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"HistoricDetailQueryImpl DbSqlSession.createHistoricDetailQuery()"})
  public void testCreateHistoricDetailQuery_thenReturnOrderByIsResIdAsc() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    HistoricDetailQueryImpl actualCreateHistoricDetailQueryResult =
        dbSqlSession.createHistoricDetailQuery();

    // Assert
    assertEquals("RES.ID_ asc", actualCreateHistoricDetailQueryResult.getOrderBy());
    assertEquals("RES.ID_ asc", actualCreateHistoricDetailQueryResult.getOrderByColumns());
    assertNull(actualCreateHistoricDetailQueryResult.getDatabaseType());
    assertNull(actualCreateHistoricDetailQueryResult.getActivityId());
    assertNull(actualCreateHistoricDetailQueryResult.getActivityInstanceId());
    assertNull(actualCreateHistoricDetailQueryResult.getExecutionId());
    assertNull(actualCreateHistoricDetailQueryResult.getId());
    assertNull(actualCreateHistoricDetailQueryResult.getProcessInstanceId());
    assertNull(actualCreateHistoricDetailQueryResult.getTaskId());
    assertNull(actualCreateHistoricDetailQueryResult.getType());
    assertEquals(0, actualCreateHistoricDetailQueryResult.getFirstResult());
    assertEquals(1, actualCreateHistoricDetailQueryResult.getFirstRow());
    assertFalse(actualCreateHistoricDetailQueryResult.getExcludeTaskRelated());
    assertEquals(Integer.MAX_VALUE, actualCreateHistoricDetailQueryResult.getLastRow());
    assertEquals(Integer.MAX_VALUE, actualCreateHistoricDetailQueryResult.getMaxResults());
    Object actualParameter = actualCreateHistoricDetailQueryResult.getParameter();
    assertSame(actualCreateHistoricDetailQueryResult, actualParameter);
  }

  /**
   * Test {@link DbSqlSession#createHistoricVariableInstanceQuery()}.
   *
   * <ul>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSession#createHistoricVariableInstanceQuery()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceQueryImpl DbSqlSession.createHistoricVariableInstanceQuery()"
  })
  public void testCreateHistoricVariableInstanceQuery_thenReturnOrderByIsResIdAsc() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();

    Builder dataSourceResult = new Builder("42").dataSource(mock(DataSource.class));
    Environment environment =
        dataSourceResult.transactionFactory(new JdbcTransactionFactory()).build();
    Configuration configuration = new Configuration(environment);
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    HistoricVariableInstanceQueryImpl actualCreateHistoricVariableInstanceQueryResult =
        dbSqlSession.createHistoricVariableInstanceQuery();

    // Assert
    assertEquals("RES.ID_ asc", actualCreateHistoricVariableInstanceQueryResult.getOrderBy());
    assertEquals(
        "RES.ID_ asc", actualCreateHistoricVariableInstanceQueryResult.getOrderByColumns());
    assertNull(actualCreateHistoricVariableInstanceQueryResult.getDatabaseType());
    assertNull(actualCreateHistoricVariableInstanceQueryResult.getActivityInstanceId());
    assertNull(actualCreateHistoricVariableInstanceQueryResult.getProcessInstanceId());
    assertNull(actualCreateHistoricVariableInstanceQueryResult.getTaskId());
    assertNull(actualCreateHistoricVariableInstanceQueryResult.getVariableName());
    assertNull(actualCreateHistoricVariableInstanceQueryResult.getVariableNameLike());
    assertNull(actualCreateHistoricVariableInstanceQueryResult.getQueryVariableValue());
    assertEquals(0, actualCreateHistoricVariableInstanceQueryResult.getFirstResult());
    assertEquals(1, actualCreateHistoricVariableInstanceQueryResult.getFirstRow());
    assertFalse(actualCreateHistoricVariableInstanceQueryResult.getExcludeTaskRelated());
    assertEquals(Integer.MAX_VALUE, actualCreateHistoricVariableInstanceQueryResult.getLastRow());
    assertEquals(
        Integer.MAX_VALUE, actualCreateHistoricVariableInstanceQueryResult.getMaxResults());
    Object actualParameter = actualCreateHistoricVariableInstanceQueryResult.getParameter();
    assertSame(actualCreateHistoricVariableInstanceQueryResult, actualParameter);
  }
}
