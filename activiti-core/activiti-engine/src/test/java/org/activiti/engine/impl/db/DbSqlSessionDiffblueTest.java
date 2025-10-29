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
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.ProcessDefinitionQueryImpl;
import org.activiti.engine.impl.ProcessInstanceQueryImpl;
import org.activiti.engine.impl.TaskQueryImpl;
import org.activiti.engine.impl.cfg.IdGenerator;
import org.activiti.engine.impl.persistence.cache.EntityCache;
import org.activiti.engine.impl.persistence.cache.EntityCacheImpl;
import org.activiti.engine.impl.persistence.entity.AttachmentEntityImpl;
import org.activiti.engine.impl.persistence.entity.Entity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.ibatis.executor.BatchExecutor;
import org.apache.ibatis.executor.CachingExecutor;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransaction;
import org.junit.Test;
import org.mockito.Mockito;

public class DbSqlSessionDiffblueTest {
  /**
   * Method under test: {@link DbSqlSession#selectList(String, Object, int, int)}
   */
  @Test
  public void testSelectList() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.mapStatement(Mockito.<String>any())).thenReturn("Map Statement");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    List actualSelectListResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).selectList("MD",
        JSONObject.NULL, -1, 3);

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).mapStatement(eq("MD"));
    assertTrue(actualSelectListResult.isEmpty());
  }

  /**
   * Method under test: {@link DbSqlSession#selectList(String, Object, int, int)}
   */
  @Test
  public void testSelectList2() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.mapStatement(Mockito.<String>any())).thenReturn("Map Statement");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    List actualSelectListResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).selectList("MD",
        JSONObject.NULL, 1, -1);

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).mapStatement(eq("MD"));
    assertTrue(actualSelectListResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link DbSqlSession#selectList(String, Object, int, int, boolean)}
   */
  @Test
  public void testSelectList3() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.mapStatement(Mockito.<String>any())).thenReturn("Map Statement");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    List actualSelectListResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).selectList("MD",
        JSONObject.NULL, -1, 3, true);

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).mapStatement(eq("MD"));
    assertTrue(actualSelectListResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link DbSqlSession#selectList(String, Object, int, int, boolean)}
   */
  @Test
  public void testSelectList4() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.mapStatement(Mockito.<String>any())).thenReturn("Map Statement");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    List actualSelectListResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).selectList("MD",
        JSONObject.NULL, 1, -1, true);

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).mapStatement(eq("MD"));
    assertTrue(actualSelectListResult.isEmpty());
  }

  /**
   * Method under test: {@link DbSqlSession#selectList(String, Object, Page)}
   */
  @Test
  public void testSelectList5() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.mapStatement(Mockito.<String>any())).thenReturn("Map Statement");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    List actualSelectListResult = dbSqlSession.selectList("MD", JSONObject.NULL, new Page(-1, 3));

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).mapStatement(eq("MD"));
    assertTrue(actualSelectListResult.isEmpty());
  }

  /**
   * Method under test: {@link DbSqlSession#selectList(String, Object, Page)}
   */
  @Test
  public void testSelectList6() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.mapStatement(Mockito.<String>any())).thenReturn("Map Statement");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    List actualSelectListResult = dbSqlSession.selectList("MD", JSONObject.NULL, new Page(1, -1));

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).mapStatement(eq("MD"));
    assertTrue(actualSelectListResult.isEmpty());
  }

  /**
   * Method under test: {@link DbSqlSession#selectList(String, Object, Page)}
   */
  @Test
  public void testSelectList7() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    Page page = mock(Page.class);
    when(page.getFirstResult()).thenThrow(new RuntimeException("foo"));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> dbSqlSession.selectList("MD", JSONObject.NULL, page));
    verify(page).getFirstResult();
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test:
   * {@link DbSqlSession#selectList(String, Object, Page, boolean)}
   */
  @Test
  public void testSelectList8() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.mapStatement(Mockito.<String>any())).thenReturn("Map Statement");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    List actualSelectListResult = dbSqlSession.selectList("MD", JSONObject.NULL, new Page(-1, 3), true);

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).mapStatement(eq("MD"));
    assertTrue(actualSelectListResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link DbSqlSession#selectList(String, Object, Page, boolean)}
   */
  @Test
  public void testSelectList9() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.mapStatement(Mockito.<String>any())).thenReturn("Map Statement");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    List actualSelectListResult = dbSqlSession.selectList("MD", JSONObject.NULL, new Page(1, -1), true);

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).mapStatement(eq("MD"));
    assertTrue(actualSelectListResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link DbSqlSession#selectList(String, Object, Page, boolean)}
   */
  @Test
  public void testSelectList10() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    Page page = mock(Page.class);
    when(page.getFirstResult()).thenThrow(new RuntimeException("foo"));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> dbSqlSession.selectList("MD", JSONObject.NULL, page, true));
    verify(page).getFirstResult();
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test:
   * {@link DbSqlSession#selectList(String, ListQueryParameterObject, Page)}
   */
  @Test
  public void testSelectList11() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    ListQueryParameterObject parameter = mock(ListQueryParameterObject.class);
    doThrow(new RuntimeException("foo")).when(parameter).setFirstResult(anyInt());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> dbSqlSession.selectList("MD", parameter, new Page(1, 3)));
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(parameter).setFirstResult(eq(1));
  }

  /**
   * Method under test:
   * {@link DbSqlSession#selectList(String, ListQueryParameterObject, Page)}
   */
  @Test
  public void testSelectList12() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    ListQueryParameterObject parameter = mock(ListQueryParameterObject.class);
    when(parameter.getFirstResult()).thenThrow(new RuntimeException("foo"));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> dbSqlSession.selectList("MD", parameter, null));
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(parameter).getFirstResult();
  }

  /**
   * Method under test:
   * {@link DbSqlSession#selectList(String, ListQueryParameterObject, Page)}
   */
  @Test
  public void testSelectList13() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    ListQueryParameterObject parameter = mock(ListQueryParameterObject.class);
    Page page = mock(Page.class);
    when(page.getFirstResult()).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.selectList("MD", parameter, page));
    verify(page).getFirstResult();
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test:
   * {@link DbSqlSession#selectList(String, ListQueryParameterObject, Page, boolean)}
   */
  @Test
  public void testSelectList14() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    ListQueryParameterObject parameter = mock(ListQueryParameterObject.class);
    doThrow(new RuntimeException("foo")).when(parameter).setFirstResult(anyInt());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> dbSqlSession.selectList("MD", parameter, new Page(1, 3), true));
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(parameter).setFirstResult(eq(1));
  }

  /**
   * Method under test:
   * {@link DbSqlSession#selectList(String, ListQueryParameterObject, Page, boolean)}
   */
  @Test
  public void testSelectList15() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    ListQueryParameterObject parameter = mock(ListQueryParameterObject.class);
    when(parameter.getFirstResult()).thenThrow(new RuntimeException("foo"));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> dbSqlSession.selectList("MD", parameter, null, true));
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(parameter).getFirstResult();
  }

  /**
   * Method under test:
   * {@link DbSqlSession#selectList(String, ListQueryParameterObject, Page, boolean)}
   */
  @Test
  public void testSelectList16() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    ListQueryParameterObject parameter = mock(ListQueryParameterObject.class);
    Page page = mock(Page.class);
    when(page.getFirstResult()).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.selectList("MD", parameter, page, true));
    verify(page).getFirstResult();
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test:
   * {@link DbSqlSession#selectList(String, ListQueryParameterObject, boolean)}
   */
  @Test
  public void testSelectList17() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.mapStatement(Mockito.<String>any())).thenReturn("Map Statement");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    List actualSelectListResult = dbSqlSession.selectList("MD", new ListQueryParameterObject(JSONObject.NULL, -1, 3),
        true);

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).mapStatement(eq("MD"));
    assertTrue(actualSelectListResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link DbSqlSession#selectListWithRawParameter(String, Object, int, int)}
   */
  @Test
  public void testSelectListWithRawParameter() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.mapStatement(Mockito.<String>any())).thenReturn("Map Statement");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    List actualSelectListWithRawParameterResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .selectListWithRawParameter("MD", JSONObject.NULL, -1, 3);

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).mapStatement(eq("MD"));
    assertTrue(actualSelectListWithRawParameterResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link DbSqlSession#selectListWithRawParameter(String, Object, int, int)}
   */
  @Test
  public void testSelectListWithRawParameter2() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.mapStatement(Mockito.<String>any())).thenReturn("Map Statement");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    List actualSelectListWithRawParameterResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .selectListWithRawParameter("MD", JSONObject.NULL, 1, -1);

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).mapStatement(eq("MD"));
    assertTrue(actualSelectListWithRawParameterResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link DbSqlSession#selectListWithRawParameter(String, Object, int, int, boolean)}
   */
  @Test
  public void testSelectListWithRawParameter3() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.mapStatement(Mockito.<String>any())).thenReturn("Map Statement");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    List actualSelectListWithRawParameterResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .selectListWithRawParameter("MD", JSONObject.NULL, -1, 3, true);

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).mapStatement(eq("MD"));
    assertTrue(actualSelectListWithRawParameterResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link DbSqlSession#selectListWithRawParameter(String, Object, int, int, boolean)}
   */
  @Test
  public void testSelectListWithRawParameter4() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.mapStatement(Mockito.<String>any())).thenReturn("Map Statement");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    List actualSelectListWithRawParameterResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .selectListWithRawParameter("MD", JSONObject.NULL, 1, -1, true);

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).mapStatement(eq("MD"));
    assertTrue(actualSelectListWithRawParameterResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link DbSqlSession#selectListWithRawParameterWithoutFilter(String, Object, int, int)}
   */
  @Test
  public void testSelectListWithRawParameterWithoutFilter() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.mapStatement(Mockito.<String>any())).thenReturn("Map Statement");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    List actualSelectListWithRawParameterWithoutFilterResult = (new DbSqlSession(dbSqlSessionFactory,
        new EntityCacheImpl())).selectListWithRawParameterWithoutFilter("MD", JSONObject.NULL, -1, 3);

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).mapStatement(eq("MD"));
    assertTrue(actualSelectListWithRawParameterWithoutFilterResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link DbSqlSession#selectListWithRawParameterWithoutFilter(String, Object, int, int)}
   */
  @Test
  public void testSelectListWithRawParameterWithoutFilter2() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.mapStatement(Mockito.<String>any())).thenReturn("Map Statement");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    List actualSelectListWithRawParameterWithoutFilterResult = (new DbSqlSession(dbSqlSessionFactory,
        new EntityCacheImpl())).selectListWithRawParameterWithoutFilter("MD", JSONObject.NULL, 1, -1);

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).mapStatement(eq("MD"));
    assertTrue(actualSelectListWithRawParameterWithoutFilterResult.isEmpty());
  }

  /**
   * Method under test: {@link DbSqlSession#selectById(Class, String)}
   */
  @Test
  public void testSelectById() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    EntityCache entityCache = mock(EntityCache.class);
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    when(entityCache.findInCache(Mockito.<Class<Entity>>any(), Mockito.<String>any())).thenReturn(attachmentEntityImpl);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, entityCache);
    Class<Entity> entityClass = Entity.class;

    // Act
    Entity actualSelectByIdResult = dbSqlSession.selectById(entityClass, "42");

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(entityCache).findInCache(isA(Class.class), eq("42"));
    assertSame(attachmentEntityImpl, actualSelectByIdResult);
  }

  /**
   * Method under test: {@link DbSqlSession#selectById(Class, String, boolean)}
   */
  @Test
  public void testSelectById2() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    EntityCache entityCache = mock(EntityCache.class);
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    when(entityCache.findInCache(Mockito.<Class<Entity>>any(), Mockito.<String>any())).thenReturn(attachmentEntityImpl);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, entityCache);
    Class<Entity> entityClass = Entity.class;

    // Act
    Entity actualSelectByIdResult = dbSqlSession.selectById(entityClass, "42", true);

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(entityCache).findInCache(isA(Class.class), eq("42"));
    assertSame(attachmentEntityImpl, actualSelectByIdResult);
  }

  /**
   * Method under test: {@link DbSqlSession#flush()}
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
    (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).flush();

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test: {@link DbSqlSession#flush()}
   */
  @Test
  public void testFlush2() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    EntityCacheImpl entityCache = new EntityCacheImpl();
    entityCache.put(new AttachmentEntityImpl(), true);

    // Act
    (new DbSqlSession(dbSqlSessionFactory, entityCache)).flush();

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test: {@link DbSqlSession#flush()}
   */
  @Test
  public void testFlush3() {
    // Arrange
    IdGenerator idGenerator = mock(IdGenerator.class);
    when(idGenerator.getNextId()).thenReturn("42");
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getInsertStatement(Mockito.<Entity>any())).thenReturn("Insert Statement");
    when(dbSqlSessionFactory.mapStatement(Mockito.<String>any())).thenReturn(null);
    when(dbSqlSessionFactory.getIdGenerator()).thenReturn(idGenerator);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    dbSqlSession.insert(new AttachmentEntityImpl());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.flush());
    verify(idGenerator).getNextId();
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getIdGenerator();
    verify(dbSqlSessionFactory).getInsertStatement(isA(Entity.class));
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).mapStatement(eq("Insert Statement"));
  }

  /**
   * Method under test:
   * {@link DbSqlSession#DbSqlSession(DbSqlSessionFactory, EntityCache, Connection, String, String)}
   */
  @Test
  public void testNewDbSqlSession2() throws SQLException {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    Configuration configuration = new Configuration();
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(configuration));
    EntityCacheImpl entityCache = new EntityCacheImpl();
    Connection connection = mock(Connection.class);
    when(connection.getAutoCommit()).thenReturn(true);

    // Act
    DbSqlSession actualDbSqlSession = new DbSqlSession(dbSqlSessionFactory, entityCache, connection, "Catalog",
        "Schema");

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
   * Method under test: {@link DbSqlSession#removeUnnecessaryOperations()}
   */
  @Test
  public void testRemoveUnnecessaryOperations() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).removeUnnecessaryOperations();

    // Assert that nothing has changed
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test: {@link DbSqlSession#determineUpdatedObjects()}
   */
  @Test
  public void testDetermineUpdatedObjects() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).determineUpdatedObjects();

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test: {@link DbSqlSession#determineUpdatedObjects()}
   */
  @Test
  public void testDetermineUpdatedObjects2() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    EntityCacheImpl entityCache = new EntityCacheImpl();
    entityCache.put(new AttachmentEntityImpl(), true);

    // Act
    (new DbSqlSession(dbSqlSessionFactory, entityCache)).determineUpdatedObjects();

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test: {@link DbSqlSession#determineUpdatedObjects()}
   */
  @Test
  public void testDetermineUpdatedObjects3() {
    // Arrange
    IdGenerator idGenerator = mock(IdGenerator.class);
    when(idGenerator.getNextId()).thenReturn("42");
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getIdGenerator()).thenReturn(idGenerator);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    dbSqlSession.insert(new AttachmentEntityImpl());

    // Act
    dbSqlSession.determineUpdatedObjects();

    // Assert
    verify(idGenerator).getNextId();
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getIdGenerator();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test: {@link DbSqlSession#determineUpdatedObjects()}
   */
  @Test
  public void testDetermineUpdatedObjects4() {
    // Arrange
    IdGenerator idGenerator = mock(IdGenerator.class);
    when(idGenerator.getNextId()).thenReturn("42");
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getIdGenerator()).thenReturn(idGenerator);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    EntityCacheImpl entityCache = new EntityCacheImpl();
    entityCache.put(new AttachmentEntityImpl(), true);

    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, entityCache);
    dbSqlSession.insert(new AttachmentEntityImpl());

    // Act
    dbSqlSession.determineUpdatedObjects();

    // Assert
    verify(idGenerator).getNextId();
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getIdGenerator();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test: {@link DbSqlSession#debugFlush()}
   */
  @Test
  public void testDebugFlush() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).debugFlush();

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test: {@link DbSqlSession#debugFlush()}
   */
  @Test
  public void testDebugFlush2() {
    // Arrange
    IdGenerator idGenerator = mock(IdGenerator.class);
    when(idGenerator.getNextId()).thenReturn("42");
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getIdGenerator()).thenReturn(idGenerator);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    dbSqlSession.insert(new AttachmentEntityImpl());

    // Act
    dbSqlSession.debugFlush();

    // Assert
    verify(idGenerator).getNextId();
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getIdGenerator();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test: {@link DbSqlSession#isEntityInserted(Entity)}
   */
  @Test
  public void testIsEntityInserted() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    boolean actualIsEntityInsertedResult = dbSqlSession.isEntityInserted(new AttachmentEntityImpl());

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    assertFalse(actualIsEntityInsertedResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isEntityInserted(Entity)}
   */
  @Test
  public void testIsEntityInserted2() {
    // Arrange
    IdGenerator idGenerator = mock(IdGenerator.class);
    when(idGenerator.getNextId()).thenReturn("42");
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getIdGenerator()).thenReturn(idGenerator);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    dbSqlSession.insert(new AttachmentEntityImpl());

    // Act
    boolean actualIsEntityInsertedResult = dbSqlSession.isEntityInserted(new AttachmentEntityImpl());

    // Assert
    verify(idGenerator).getNextId();
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getIdGenerator();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    assertFalse(actualIsEntityInsertedResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isEntityToBeDeleted(Entity)}
   */
  @Test
  public void testIsEntityToBeDeleted() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    boolean actualIsEntityToBeDeletedResult = dbSqlSession.isEntityToBeDeleted(new AttachmentEntityImpl());

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    assertFalse(actualIsEntityToBeDeletedResult);
  }

  /**
   * Method under test: {@link DbSqlSession#flushInserts()}
   */
  @Test
  public void testFlushInserts() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).flushInserts();

    // Assert that nothing has changed
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test: {@link DbSqlSession#flushInserts()}
   */
  @Test
  public void testFlushInserts2() {
    // Arrange
    IdGenerator idGenerator = mock(IdGenerator.class);
    when(idGenerator.getNextId()).thenReturn("42");
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getInsertStatement(Mockito.<Entity>any())).thenReturn("Insert Statement");
    when(dbSqlSessionFactory.mapStatement(Mockito.<String>any())).thenReturn(null);
    when(dbSqlSessionFactory.getIdGenerator()).thenReturn(idGenerator);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    dbSqlSession.insert(new AttachmentEntityImpl());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.flushInserts());
    verify(idGenerator).getNextId();
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getIdGenerator();
    verify(dbSqlSessionFactory).getInsertStatement(isA(Entity.class));
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).mapStatement(eq("Insert Statement"));
  }

  /**
   * Method under test:
   * {@link DbSqlSession#flushInsertEntities(Class, Collection)}
   */
  @Test
  public void testFlushInsertEntities() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getBulkInsertStatement(Mockito.<Class<Object>>any())).thenReturn("Bulk Insert Statement");
    when(dbSqlSessionFactory.mapStatement(Mockito.<String>any())).thenReturn("Map Statement");
    when(dbSqlSessionFactory.isBulkInsertable(Mockito.<Class<Entity>>any())).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    Class<Entity> entityClass = Entity.class;

    // Act
    dbSqlSession.flushInsertEntities(entityClass, new ArrayList<>());

    // Assert that nothing has changed
    verify(dbSqlSessionFactory).getBulkInsertStatement(isA(Class.class));
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isBulkInsertable(isA(Class.class));
    verify(dbSqlSessionFactory).mapStatement(eq("Bulk Insert Statement"));
  }

  /**
   * Method under test:
   * {@link DbSqlSession#flushInsertEntities(Class, Collection)}
   */
  @Test
  public void testFlushInsertEntities2() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getBulkInsertStatement(Mockito.<Class<Object>>any()))
        .thenThrow(new RuntimeException("foo"));
    when(dbSqlSessionFactory.isBulkInsertable(Mockito.<Class<Entity>>any())).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    Class<Entity> entityClass = Entity.class;

    // Act and Assert
    assertThrows(RuntimeException.class, () -> dbSqlSession.flushInsertEntities(entityClass, new ArrayList<>()));
    verify(dbSqlSessionFactory).getBulkInsertStatement(isA(Class.class));
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isBulkInsertable(isA(Class.class));
  }

  /**
   * Method under test:
   * {@link DbSqlSession#flushInsertEntities(Class, Collection)}
   */
  @Test
  public void testFlushInsertEntities3() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.isBulkInsertable(Mockito.<Class<Entity>>any())).thenReturn(false);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    Class<Entity> entityClass = Entity.class;

    // Act
    dbSqlSession.flushInsertEntities(entityClass, new ArrayList<>());

    // Assert that nothing has changed
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isBulkInsertable(isA(Class.class));
  }

  /**
   * Method under test:
   * {@link DbSqlSession#flushInsertEntities(Class, Collection)}
   */
  @Test
  public void testFlushInsertEntities4() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getInsertStatement(Mockito.<Entity>any())).thenReturn("Insert Statement");
    when(dbSqlSessionFactory.mapStatement(Mockito.<String>any())).thenReturn(null);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    Class<Entity> entityClass = Entity.class;

    ArrayList<Entity> entitiesToInsert = new ArrayList<>();
    entitiesToInsert.add(new AttachmentEntityImpl());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.flushInsertEntities(entityClass, entitiesToInsert));
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getInsertStatement(isA(Entity.class));
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).mapStatement(eq("Insert Statement"));
  }

  /**
   * Method under test:
   * {@link DbSqlSession#flushInsertEntities(Class, Collection)}
   */
  @Test
  public void testFlushInsertEntities5() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getBulkInsertStatement(Mockito.<Class<Object>>any())).thenReturn("Bulk Insert Statement");
    when(dbSqlSessionFactory.mapStatement(Mockito.<String>any())).thenReturn(null);
    when(dbSqlSessionFactory.isBulkInsertable(Mockito.<Class<Entity>>any())).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    Class<Entity> entityClass = Entity.class;

    ArrayList<Entity> entitiesToInsert = new ArrayList<>();
    entitiesToInsert.add(new AttachmentEntityImpl());
    entitiesToInsert.add(new AttachmentEntityImpl());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.flushInsertEntities(entityClass, entitiesToInsert));
    verify(dbSqlSessionFactory).getBulkInsertStatement(isA(Class.class));
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isBulkInsertable(isA(Class.class));
    verify(dbSqlSessionFactory).mapStatement(eq("Bulk Insert Statement"));
  }

  /**
   * Method under test:
   * {@link DbSqlSession#flushInsertEntities(Class, Collection)}
   */
  @Test
  public void testFlushInsertEntities6() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getMaxNrOfStatementsInBulkInsert()).thenThrow(new ActivitiException("An error occurred"));
    when(dbSqlSessionFactory.getBulkInsertStatement(Mockito.<Class<Object>>any())).thenReturn("Bulk Insert Statement");
    when(dbSqlSessionFactory.mapStatement(Mockito.<String>any())).thenReturn("Map Statement");
    when(dbSqlSessionFactory.isBulkInsertable(Mockito.<Class<Entity>>any())).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    Class<Entity> entityClass = Entity.class;

    ArrayList<Entity> entitiesToInsert = new ArrayList<>();
    entitiesToInsert.add(new AttachmentEntityImpl());
    entitiesToInsert.add(new AttachmentEntityImpl());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.flushInsertEntities(entityClass, entitiesToInsert));
    verify(dbSqlSessionFactory).getBulkInsertStatement(isA(Class.class));
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getMaxNrOfStatementsInBulkInsert();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isBulkInsertable(isA(Class.class));
    verify(dbSqlSessionFactory).mapStatement(eq("Bulk Insert Statement"));
  }

  /**
   * Method under test: {@link DbSqlSession#orderExecutionEntities(Map, boolean)}
   */
  @Test
  public void testOrderExecutionEntities() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    Collection<Entity> actualOrderExecutionEntitiesResult = dbSqlSession.orderExecutionEntities(new HashMap<>(), true);

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    assertTrue(actualOrderExecutionEntitiesResult instanceof List);
    assertTrue(actualOrderExecutionEntitiesResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link DbSqlSession#collectChildExecutionsForInsertion(List, Map, Set, String, boolean)}
   */
  @Test
  public void testCollectChildExecutionsForInsertion() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    ArrayList<Entity> result = new ArrayList<>();
    HashMap<String, List<ExecutionEntity>> parentToChildrenMapping = new HashMap<>();

    // Act
    dbSqlSession.collectChildExecutionsForInsertion(result, parentToChildrenMapping, new HashSet<>(), "42", true);

    // Assert that nothing has changed
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test:
   * {@link DbSqlSession#collectChildExecutionsForInsertion(List, Map, Set, String, boolean)}
   */
  @Test
  public void testCollectChildExecutionsForInsertion2() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    ArrayList<Entity> result = new ArrayList<>();
    result.add(new AttachmentEntityImpl());
    HashMap<String, List<ExecutionEntity>> parentToChildrenMapping = new HashMap<>();

    // Act
    dbSqlSession.collectChildExecutionsForInsertion(result, parentToChildrenMapping, new HashSet<>(), "42", true);

    // Assert that nothing has changed
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test:
   * {@link DbSqlSession#collectChildExecutionsForInsertion(List, Map, Set, String, boolean)}
   */
  @Test
  public void testCollectChildExecutionsForInsertion3() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    ArrayList<Entity> result = new ArrayList<>();
    result.add(new AttachmentEntityImpl());
    result.add(new AttachmentEntityImpl());
    HashMap<String, List<ExecutionEntity>> parentToChildrenMapping = new HashMap<>();

    // Act
    dbSqlSession.collectChildExecutionsForInsertion(result, parentToChildrenMapping, new HashSet<>(), "42", true);

    // Assert that nothing has changed
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test:
   * {@link DbSqlSession#collectChildExecutionsForInsertion(List, Map, Set, String, boolean)}
   */
  @Test
  public void testCollectChildExecutionsForInsertion4() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    ArrayList<Entity> result = new ArrayList<>();
    HashMap<String, List<ExecutionEntity>> parentToChildrenMapping = new HashMap<>();

    HashSet<String> handledExecutionIds = new HashSet<>();
    handledExecutionIds.add("foo");

    // Act
    dbSqlSession.collectChildExecutionsForInsertion(result, parentToChildrenMapping, handledExecutionIds, "42", true);

    // Assert that nothing has changed
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test:
   * {@link DbSqlSession#collectChildExecutionsForInsertion(List, Map, Set, String, boolean)}
   */
  @Test
  public void testCollectChildExecutionsForInsertion5() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    ArrayList<Entity> result = new ArrayList<>();
    HashMap<String, List<ExecutionEntity>> parentToChildrenMapping = new HashMap<>();

    HashSet<String> handledExecutionIds = new HashSet<>();
    handledExecutionIds.add("42");
    handledExecutionIds.add("foo");

    // Act
    dbSqlSession.collectChildExecutionsForInsertion(result, parentToChildrenMapping, handledExecutionIds, "42", true);

    // Assert that nothing has changed
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test:
   * {@link DbSqlSession#collectChildExecutionsForInsertion(List, Map, Set, String, boolean)}
   */
  @Test
  public void testCollectChildExecutionsForInsertion6() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    ArrayList<Entity> result = new ArrayList<>();

    HashMap<String, List<ExecutionEntity>> parentToChildrenMapping = new HashMap<>();
    parentToChildrenMapping.put("42", new ArrayList<>());

    HashSet<String> handledExecutionIds = new HashSet<>();
    handledExecutionIds.add("foo");

    // Act
    dbSqlSession.collectChildExecutionsForInsertion(result, parentToChildrenMapping, handledExecutionIds, "42", true);

    // Assert that nothing has changed
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test: {@link DbSqlSession#flushRegularInsert(Entity, Class)}
   */
  @Test
  public void testFlushRegularInsert() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getInsertStatement(Mockito.<Entity>any())).thenReturn("Insert Statement");
    when(dbSqlSessionFactory.mapStatement(Mockito.<String>any())).thenReturn(null);
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    AttachmentEntityImpl entity = new AttachmentEntityImpl();
    Class<Entity> clazz = Entity.class;

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.flushRegularInsert(entity, clazz));
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getInsertStatement(isA(Entity.class));
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).mapStatement(eq("Insert Statement"));
  }

  /**
   * Method under test: {@link DbSqlSession#flushBulkInsert(Collection, Class)}
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
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    ArrayList<Entity> entities = new ArrayList<>();
    Class<Entity> clazz = Entity.class;

    // Act
    dbSqlSession.flushBulkInsert(entities, clazz);

    // Assert that nothing has changed
    verify(dbSqlSessionFactory).getBulkInsertStatement(isA(Class.class));
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).mapStatement(eq("Bulk Insert Statement"));
  }

  /**
   * Method under test: {@link DbSqlSession#flushBulkInsert(Collection, Class)}
   */
  @Test
  public void testFlushBulkInsert2() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getBulkInsertStatement(Mockito.<Class<Object>>any())).thenReturn("Bulk Insert Statement");
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.mapStatement(Mockito.<String>any())).thenReturn(null);
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    ArrayList<Entity> entities = new ArrayList<>();
    entities.add(new AttachmentEntityImpl());
    Class<Entity> clazz = Entity.class;

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.flushBulkInsert(entities, clazz));
    verify(dbSqlSessionFactory).getBulkInsertStatement(isA(Class.class));
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).mapStatement(eq("Bulk Insert Statement"));
  }

  /**
   * Method under test: {@link DbSqlSession#flushBulkInsert(Collection, Class)}
   */
  @Test
  public void testFlushBulkInsert3() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getMaxNrOfStatementsInBulkInsert()).thenThrow(new ActivitiException("An error occurred"));
    when(dbSqlSessionFactory.getBulkInsertStatement(Mockito.<Class<Object>>any())).thenReturn("Bulk Insert Statement");
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.mapStatement(Mockito.<String>any())).thenReturn("Map Statement");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    ArrayList<Entity> entities = new ArrayList<>();
    entities.add(new AttachmentEntityImpl());
    Class<Entity> clazz = Entity.class;

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.flushBulkInsert(entities, clazz));
    verify(dbSqlSessionFactory).getBulkInsertStatement(isA(Class.class));
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getMaxNrOfStatementsInBulkInsert();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).mapStatement(eq("Bulk Insert Statement"));
  }

  /**
   * Method under test: {@link DbSqlSession#incrementRevision(Entity)}
   */
  @Test
  public void testIncrementRevision() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    dbSqlSession.incrementRevision(new AttachmentEntityImpl());

    // Assert that nothing has changed
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test: {@link DbSqlSession#incrementRevision(Entity)}
   */
  @Test
  public void testIncrementRevision2() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    AttachmentEntityImpl insertedObject = mock(AttachmentEntityImpl.class);
    when(insertedObject.getRevision()).thenReturn(0);
    when(insertedObject.getRevisionNext()).thenReturn(1);
    doNothing().when(insertedObject).setRevision(anyInt());

    // Act
    dbSqlSession.incrementRevision(insertedObject);

    // Assert that nothing has changed
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(insertedObject).getRevision();
    verify(insertedObject).getRevisionNext();
    verify(insertedObject).setRevision(eq(1));
  }

  /**
   * Method under test: {@link DbSqlSession#flushUpdates()}
   */
  @Test
  public void testFlushUpdates() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).flushUpdates();

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test: {@link DbSqlSession#flushDeletes()}
   */
  @Test
  public void testFlushDeletes() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).flushDeletes();

    // Assert that nothing has changed
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test: {@link DbSqlSession#flushBulkDeletes(Class)}
   */
  @Test
  public void testFlushBulkDeletes() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    Class<Entity> entityClass = Entity.class;

    // Act
    dbSqlSession.flushBulkDeletes(entityClass);

    // Assert that nothing has changed
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test:
   * {@link DbSqlSession#flushDeleteEntities(Class, Collection)}
   */
  @Test
  public void testFlushDeleteEntities() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    Class<Entity> entityClass = Entity.class;

    // Act
    dbSqlSession.flushDeleteEntities(entityClass, new ArrayList<>());

    // Assert that nothing has changed
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test:
   * {@link DbSqlSession#flushDeleteEntities(Class, Collection)}
   */
  @Test
  public void testFlushDeleteEntities2() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDeleteStatement(Mockito.<Class<Object>>any())).thenReturn("Delete Statement");
    when(dbSqlSessionFactory.mapStatement(Mockito.<String>any())).thenReturn(null);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    Class<Entity> entityClass = Entity.class;

    ArrayList<Entity> entitiesToDelete = new ArrayList<>();
    entitiesToDelete.add(new AttachmentEntityImpl());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dbSqlSession.flushDeleteEntities(entityClass, entitiesToDelete));
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDeleteStatement(isA(Class.class));
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).mapStatement(eq("Delete Statement"));
  }

  /**
   * Method under test:
   * {@link DbSqlSession#flushDeleteEntities(Class, Collection)}
   */
  @Test
  public void testFlushDeleteEntities3() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDeleteStatement(Mockito.<Class<Object>>any())).thenThrow(new RuntimeException("foo"));
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    Class<Entity> entityClass = Entity.class;

    ArrayList<Entity> entitiesToDelete = new ArrayList<>();
    entitiesToDelete.add(new AttachmentEntityImpl());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> dbSqlSession.flushDeleteEntities(entityClass, entitiesToDelete));
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDeleteStatement(isA(Class.class));
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test: {@link DbSqlSession#close()}
   */
  @Test
  public void testClose() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).close();

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test: {@link DbSqlSession#commit()}
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
    (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).commit();

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test: {@link DbSqlSession#rollback()}
   */
  @Test
  public void testRollback() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).rollback();

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test: {@link DbSqlSession#addMissingComponent(String, String)}
   */
  @Test
  public void testAddMissingComponent() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    String actualAddMissingComponentResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .addMissingComponent("Missing Components", "Component");

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    assertEquals("Missing Components, Component", actualAddMissingComponentResult);
  }

  /**
   * Method under test: {@link DbSqlSession#addMissingComponent(String, String)}
   */
  @Test
  public void testAddMissingComponent2() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    String actualAddMissingComponentResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .addMissingComponent(null, "Component");

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    assertEquals("Tables missing for component(s) Component", actualAddMissingComponentResult);
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaCreate()}
   */
  @Test
  public void testDbSchemaCreate() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.mapStatement(Mockito.<String>any())).thenThrow(new ActivitiException("An error occurred"));
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaCreate());
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"), eq("ACT_RU_EXECUTION"),
        isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    verify(dbSqlSessionFactory).mapStatement(eq("selectDbSchemaVersion"));
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaCreate()}
   */
  @Test
  public void testDbSchemaCreate2() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenThrow(new RuntimeException("ACT_RU_EXECUTION"));
    doThrow(new RuntimeException("ACT_RU_EXECUTION")).when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    JdbcTransactionFactory transactionFactory = mock(JdbcTransactionFactory.class);
    when(transactionFactory.newTransaction(Mockito.<DataSource>any(), Mockito.<TransactionIsolationLevel>any(),
        anyBoolean())).thenReturn(new JdbcTransaction(connection));
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", transactionFactory, mock(DataSource.class))));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaCreate());
    verify(connection).getMetaData();
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"), eq("ACT_RU_EXECUTION"),
        isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    verify(transactionFactory).newTransaction(isA(DataSource.class), isNull(), eq(false));
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaCreate()}
   */
  @Test
  public void testDbSchemaCreate3() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(false).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    JdbcTransactionFactory transactionFactory = mock(JdbcTransactionFactory.class);
    when(transactionFactory.newTransaction(Mockito.<DataSource>any(), Mockito.<TransactionIsolationLevel>any(),
        anyBoolean())).thenReturn(new JdbcTransaction(connection));
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", transactionFactory, mock(DataSource.class))));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaCreate());
    verify(connection).getMetaData();
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"), eq("ACT_RU_EXECUTION"),
        isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    verify(transactionFactory).newTransaction(isA(DataSource.class), isNull(), eq(false));
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaCreate()}
   */
  @Test
  public void testDbSchemaCreate4() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenThrow(new ActivitiException("An error occurred"));
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    JdbcTransactionFactory transactionFactory = mock(JdbcTransactionFactory.class);
    when(transactionFactory.newTransaction(Mockito.<DataSource>any(), Mockito.<TransactionIsolationLevel>any(),
        anyBoolean())).thenReturn(new ManagedTransaction(connection, true));
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", transactionFactory, mock(DataSource.class))));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaCreate());
    verify(connection).getMetaData();
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"), eq("ACT_RU_EXECUTION"),
        isA(String[].class));
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    verify(transactionFactory).newTransaction(isA(DataSource.class), isNull(), eq(false));
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaCreate()}
   */
  @Test
  public void testDbSchemaCreate5() throws SQLException {
    // Arrange
    new ActivitiException("An error occurred");
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenThrow(new ActivitiException("An error occurred"));
    JdbcTransaction jdbcTransaction = mock(JdbcTransaction.class);
    when(jdbcTransaction.getConnection()).thenReturn(connection);
    JdbcTransactionFactory transactionFactory = mock(JdbcTransactionFactory.class);
    when(transactionFactory.newTransaction(Mockito.<DataSource>any(), Mockito.<TransactionIsolationLevel>any(),
        anyBoolean())).thenReturn(jdbcTransaction);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", transactionFactory, mock(DataSource.class))));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaCreate());
    verify(connection).getMetaData();
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    verify(jdbcTransaction).getConnection();
    verify(transactionFactory).newTransaction(isA(DataSource.class), isNull(), eq(false));
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaCreateHistory()}
   */
  @Test
  public void testDbSchemaCreateHistory() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaCreateHistory());
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaCreateHistory()}
   */
  @Test
  public void testDbSchemaCreateHistory2() throws SQLException {
    // Arrange
    Statement statement = mock(Statement.class);
    when(statement.execute(Mockito.<String>any())).thenReturn(true);
    doNothing().when(statement).close();
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.createStatement()).thenReturn(statement);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("oracle");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaCreateHistory();

    // Assert
    verify(connection, atLeast(1)).createStatement();
    verify(connection).getAutoCommit();
    verify(connection).setAutoCommit(eq(false));
    verify(statement, atLeast(1)).close();
    verify(statement, atLeast(1)).execute(Mockito.<String>any());
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaCreateHistory()}
   */
  @Test
  public void testDbSchemaCreateHistory3() throws SQLException {
    // Arrange
    Statement statement = mock(Statement.class);
    when(statement.execute(Mockito.<String>any())).thenThrow(new RuntimeException("create"));
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.createStatement()).thenReturn(statement);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("oracle");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaCreateHistory());
    verify(connection, atLeast(1)).createStatement();
    verify(connection).getAutoCommit();
    verify(connection).setAutoCommit(eq(false));
    verify(statement, atLeast(1)).execute(Mockito.<String>any());
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaCreateHistory()}
   */
  @Test
  public void testDbSchemaCreateHistory4() throws SQLException {
    // Arrange
    Statement statement = mock(Statement.class);
    when(statement.execute(Mockito.<String>any())).thenReturn(true);
    doNothing().when(statement).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getDatabaseMajorVersion()).thenReturn(1);
    when(databaseMetaData.getDatabaseMinorVersion()).thenReturn(1);
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.createStatement()).thenReturn(statement);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("mysql");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaCreateHistory();

    // Assert
    verify(connection, atLeast(1)).createStatement();
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getDatabaseMajorVersion();
    verify(databaseMetaData).getDatabaseMinorVersion();
    verify(statement, atLeast(1)).close();
    verify(statement, atLeast(1)).execute(Mockito.<String>any());
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaCreateHistory()}
   */
  @Test
  public void testDbSchemaCreateHistory5() throws SQLException {
    // Arrange
    Statement statement = mock(Statement.class);
    when(statement.execute(Mockito.<String>any())).thenReturn(true);
    doNothing().when(statement).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getDatabaseMajorVersion()).thenThrow(new RuntimeException("create"));
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.createStatement()).thenReturn(statement);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("mysql");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaCreateHistory();

    // Assert
    verify(connection, atLeast(1)).createStatement();
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getDatabaseMajorVersion();
    verify(statement, atLeast(1)).close();
    verify(statement, atLeast(1)).execute(Mockito.<String>any());
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaCreateHistory()}
   */
  @Test
  public void testDbSchemaCreateHistory6() throws SQLException {
    // Arrange
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenThrow(new RuntimeException("create"));
    when(connection.createStatement()).thenThrow(new RuntimeException("create"));
    TransactionFactory transactionFactory = mock(TransactionFactory.class);
    when(transactionFactory.newTransaction(Mockito.<DataSource>any(), Mockito.<TransactionIsolationLevel>any(),
        anyBoolean())).thenReturn(new JdbcTransaction(connection));
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", transactionFactory, mock(DataSource.class))));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("mysql");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaCreateHistory());
    verify(connection).createStatement();
    verify(connection).getMetaData();
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(transactionFactory).newTransaction(isA(DataSource.class), isNull(), eq(false));
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaCreateEngine()}
   */
  @Test
  public void testDbSchemaCreateEngine() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaCreateEngine());
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaCreateEngine()}
   */
  @Test
  public void testDbSchemaCreateEngine2() throws SQLException {
    // Arrange
    Statement statement = mock(Statement.class);
    when(statement.execute(Mockito.<String>any())).thenReturn(true);
    doNothing().when(statement).close();
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.createStatement()).thenReturn(statement);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("oracle");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaCreateEngine();

    // Assert
    verify(connection, atLeast(1)).createStatement();
    verify(connection).getAutoCommit();
    verify(connection).setAutoCommit(eq(false));
    verify(statement, atLeast(1)).close();
    verify(statement, atLeast(1)).execute(Mockito.<String>any());
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaCreateEngine()}
   */
  @Test
  public void testDbSchemaCreateEngine3() throws SQLException {
    // Arrange
    Statement statement = mock(Statement.class);
    when(statement.execute(Mockito.<String>any())).thenThrow(new RuntimeException("create"));
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.createStatement()).thenReturn(statement);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("oracle");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaCreateEngine());
    verify(connection, atLeast(1)).createStatement();
    verify(connection).getAutoCommit();
    verify(connection).setAutoCommit(eq(false));
    verify(statement, atLeast(1)).execute(Mockito.<String>any());
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaCreateEngine()}
   */
  @Test
  public void testDbSchemaCreateEngine4() throws SQLException {
    // Arrange
    Statement statement = mock(Statement.class);
    when(statement.execute(Mockito.<String>any())).thenReturn(true);
    doNothing().when(statement).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getDatabaseMajorVersion()).thenReturn(1);
    when(databaseMetaData.getDatabaseMinorVersion()).thenReturn(1);
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.createStatement()).thenReturn(statement);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("mysql");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaCreateEngine();

    // Assert
    verify(connection, atLeast(1)).createStatement();
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getDatabaseMajorVersion();
    verify(databaseMetaData).getDatabaseMinorVersion();
    verify(statement, atLeast(1)).close();
    verify(statement, atLeast(1)).execute(Mockito.<String>any());
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaCreateEngine()}
   */
  @Test
  public void testDbSchemaCreateEngine5() throws SQLException {
    // Arrange
    Statement statement = mock(Statement.class);
    when(statement.execute(Mockito.<String>any())).thenReturn(true);
    doNothing().when(statement).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getDatabaseMajorVersion()).thenThrow(new RuntimeException("create"));
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.createStatement()).thenReturn(statement);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("mysql");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaCreateEngine();

    // Assert
    verify(connection, atLeast(1)).createStatement();
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getDatabaseMajorVersion();
    verify(statement, atLeast(1)).close();
    verify(statement, atLeast(1)).execute(Mockito.<String>any());
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaCreateEngine()}
   */
  @Test
  public void testDbSchemaCreateEngine6() throws SQLException {
    // Arrange
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenThrow(new RuntimeException("create"));
    when(connection.createStatement()).thenThrow(new RuntimeException("create"));
    TransactionFactory transactionFactory = mock(TransactionFactory.class);
    when(transactionFactory.newTransaction(Mockito.<DataSource>any(), Mockito.<TransactionIsolationLevel>any(),
        anyBoolean())).thenReturn(new JdbcTransaction(connection));
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", transactionFactory, mock(DataSource.class))));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("mysql");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaCreateEngine());
    verify(connection).createStatement();
    verify(connection).getMetaData();
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(transactionFactory).newTransaction(isA(DataSource.class), isNull(), eq(false));
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaDrop()}
   */
  @Test
  public void testDbSchemaDrop() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaDrop());
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaDrop()}
   */
  @Test
  public void testDbSchemaDrop2() throws SQLException {
    // Arrange
    Statement statement = mock(Statement.class);
    when(statement.execute(Mockito.<String>any())).thenReturn(true);
    doNothing().when(statement).close();
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.createStatement()).thenReturn(statement);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.isDbHistoryUsed()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("oracle");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaDrop();

    // Assert
    verify(connection, atLeast(1)).createStatement();
    verify(connection).getAutoCommit();
    verify(connection).setAutoCommit(eq(false));
    verify(statement, atLeast(1)).close();
    verify(statement, atLeast(1)).execute(Mockito.<String>any());
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isDbHistoryUsed();
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaDrop()}
   */
  @Test
  public void testDbSchemaDrop3() throws SQLException {
    // Arrange
    Statement statement = mock(Statement.class);
    when(statement.execute(Mockito.<String>any())).thenThrow(new RuntimeException("drop"));
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.createStatement()).thenReturn(statement);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("oracle");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaDrop());
    verify(connection, atLeast(1)).createStatement();
    verify(connection).getAutoCommit();
    verify(connection).setAutoCommit(eq(false));
    verify(statement, atLeast(1)).execute(Mockito.<String>any());
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaDrop()}
   */
  @Test
  public void testDbSchemaDrop4() throws SQLException {
    // Arrange
    Statement statement = mock(Statement.class);
    when(statement.execute(Mockito.<String>any())).thenReturn(true);
    doNothing().when(statement).close();
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.createStatement()).thenReturn(statement);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.isDbHistoryUsed()).thenReturn(false);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("oracle");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaDrop();

    // Assert
    verify(connection, atLeast(1)).createStatement();
    verify(connection).getAutoCommit();
    verify(connection).setAutoCommit(eq(false));
    verify(statement, atLeast(1)).close();
    verify(statement, atLeast(1)).execute(Mockito.<String>any());
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isDbHistoryUsed();
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaDrop()}
   */
  @Test
  public void testDbSchemaDrop5() throws SQLException {
    // Arrange
    Statement statement = mock(Statement.class);
    when(statement.execute(Mockito.<String>any())).thenReturn(true);
    doNothing().when(statement).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getDatabaseMajorVersion()).thenReturn(1);
    when(databaseMetaData.getDatabaseMinorVersion()).thenReturn(1);
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.createStatement()).thenReturn(statement);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.isDbHistoryUsed()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("mysql");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaDrop();

    // Assert
    verify(connection, atLeast(1)).createStatement();
    verify(connection).getAutoCommit();
    verify(connection, atLeast(1)).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData, atLeast(1)).getDatabaseMajorVersion();
    verify(databaseMetaData, atLeast(1)).getDatabaseMinorVersion();
    verify(statement, atLeast(1)).close();
    verify(statement, atLeast(1)).execute(Mockito.<String>any());
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isDbHistoryUsed();
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaDrop()}
   */
  @Test
  public void testDbSchemaDrop6() throws SQLException {
    // Arrange
    Statement statement = mock(Statement.class);
    when(statement.execute(Mockito.<String>any())).thenReturn(true);
    doNothing().when(statement).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getDatabaseMajorVersion()).thenThrow(new RuntimeException("drop"));
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.createStatement()).thenReturn(statement);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.isDbHistoryUsed()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("mysql");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaDrop();

    // Assert
    verify(connection, atLeast(1)).createStatement();
    verify(connection).getAutoCommit();
    verify(connection, atLeast(1)).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData, atLeast(1)).getDatabaseMajorVersion();
    verify(statement, atLeast(1)).close();
    verify(statement, atLeast(1)).execute(Mockito.<String>any());
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isDbHistoryUsed();
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaDrop()}
   */
  @Test
  public void testDbSchemaDrop7() throws SQLException {
    // Arrange
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenThrow(new RuntimeException("drop"));
    when(connection.createStatement()).thenThrow(new RuntimeException("drop"));
    TransactionFactory transactionFactory = mock(TransactionFactory.class);
    when(transactionFactory.newTransaction(Mockito.<DataSource>any(), Mockito.<TransactionIsolationLevel>any(),
        anyBoolean())).thenReturn(new JdbcTransaction(connection));
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", transactionFactory, mock(DataSource.class))));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("mysql");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaDrop());
    verify(connection).createStatement();
    verify(connection).getMetaData();
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(transactionFactory).newTransaction(isA(DataSource.class), isNull(), eq(false));
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaPrune()}
   */
  @Test
  public void testDbSchemaPrune() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.isDbHistoryUsed()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaPrune();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"), eq("ACT_HI_PROCINST"),
        isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isDbHistoryUsed();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaPrune()}
   */
  @Test
  public void testDbSchemaPrune2() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.isDbHistoryUsed()).thenThrow(new ActivitiException("An error occurred"));
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaPrune());
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"), eq("ACT_HI_PROCINST"),
        isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isDbHistoryUsed();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaPrune()}
   */
  @Test
  public void testDbSchemaPrune3() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.isDbHistoryUsed()).thenReturn(false);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaPrune());
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"), eq("ACT_HI_PROCINST"),
        isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isDbHistoryUsed();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaPrune()}
   */
  @Test
  public void testDbSchemaPrune4() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.isDbHistoryUsed()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("postgres");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaPrune();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"), eq("act_hi_procinst"),
        isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isDbHistoryUsed();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaPrune()}
   */
  @Test
  public void testDbSchemaPrune5() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.isDbHistoryUsed()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("oracle");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaPrune();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("DATABASE SCHEMA"), eq("ACT_HI_PROCINST"),
        isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isDbHistoryUsed();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaPrune()}
   */
  @Test
  public void testDbSchemaPrune6() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.isDbHistoryUsed()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(false);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getDatabaseTablePrefix()).thenReturn("Database Table Prefix");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaPrune();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"),
        eq("Database Table PrefixACT_HI_PROCINST"), isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseTablePrefix();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isDbHistoryUsed();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaPrune()}
   */
  @Test
  public void testDbSchemaPrune7() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.isDbHistoryUsed()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn(null);
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaPrune();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(isNull(), eq("Database Schema"), eq("ACT_HI_PROCINST"), isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isDbHistoryUsed();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaPrune()}
   */
  @Test
  public void testDbSchemaPrune8() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.isDbHistoryUsed()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaPrune();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(isNull(), eq("Database Schema"), eq("ACT_HI_PROCINST"), isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isDbHistoryUsed();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaPrune()}
   */
  @Test
  public void testDbSchemaPrune9() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.isDbHistoryUsed()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn(null);
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaPrune();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(eq("Database Catalog"), isNull(), eq("ACT_HI_PROCINST"), isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isDbHistoryUsed();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaPrune()}
   */
  @Test
  public void testDbSchemaPrune10() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.isDbHistoryUsed()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaPrune();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq(""), eq("ACT_HI_PROCINST"), isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isDbHistoryUsed();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaPrune()}
   */
  @Test
  public void testDbSchemaPrune11() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenThrow(new RuntimeException("ACT_HI_PROCINST"));
    doThrow(new RuntimeException("ACT_HI_PROCINST")).when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    JdbcTransactionFactory transactionFactory = mock(JdbcTransactionFactory.class);
    when(transactionFactory.newTransaction(Mockito.<DataSource>any(), Mockito.<TransactionIsolationLevel>any(),
        anyBoolean())).thenReturn(new JdbcTransaction(connection));
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", transactionFactory, mock(DataSource.class))));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaPrune());
    verify(connection).getMetaData();
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"), eq("ACT_HI_PROCINST"),
        isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    verify(transactionFactory).newTransaction(isA(DataSource.class), isNull(), eq(false));
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaPrune()}
   */
  @Test
  public void testDbSchemaPrune12() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(false).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    JdbcTransactionFactory transactionFactory = mock(JdbcTransactionFactory.class);
    when(transactionFactory.newTransaction(Mockito.<DataSource>any(), Mockito.<TransactionIsolationLevel>any(),
        anyBoolean())).thenReturn(new JdbcTransaction(connection));
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", transactionFactory, mock(DataSource.class))));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaPrune();

    // Assert that nothing has changed
    verify(connection).getMetaData();
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"), eq("ACT_HI_PROCINST"),
        isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    verify(transactionFactory).newTransaction(isA(DataSource.class), isNull(), eq(false));
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaPrune()}
   */
  @Test
  public void testDbSchemaPrune13() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenThrow(new ActivitiException("An error occurred"));
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    JdbcTransactionFactory transactionFactory = mock(JdbcTransactionFactory.class);
    when(transactionFactory.newTransaction(Mockito.<DataSource>any(), Mockito.<TransactionIsolationLevel>any(),
        anyBoolean())).thenReturn(new ManagedTransaction(connection, true));
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", transactionFactory, mock(DataSource.class))));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaPrune());
    verify(connection).getMetaData();
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"), eq("ACT_HI_PROCINST"),
        isA(String[].class));
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    verify(transactionFactory).newTransaction(isA(DataSource.class), isNull(), eq(false));
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaPrune()}
   */
  @Test
  public void testDbSchemaPrune14() throws SQLException {
    // Arrange
    new ActivitiException("An error occurred");
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenThrow(new ActivitiException("An error occurred"));
    JdbcTransaction jdbcTransaction = mock(JdbcTransaction.class);
    when(jdbcTransaction.getConnection()).thenReturn(connection);
    JdbcTransactionFactory transactionFactory = mock(JdbcTransactionFactory.class);
    when(transactionFactory.newTransaction(Mockito.<DataSource>any(), Mockito.<TransactionIsolationLevel>any(),
        anyBoolean())).thenReturn(jdbcTransaction);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", transactionFactory, mock(DataSource.class))));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaPrune());
    verify(connection).getMetaData();
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    verify(jdbcTransaction).getConnection();
    verify(transactionFactory).newTransaction(isA(DataSource.class), isNull(), eq(false));
  }

  /**
   * Method under test:
   * {@link DbSqlSession#executeMandatorySchemaResource(String, String)}
   */
  @Test
  public void testExecuteMandatorySchemaResource() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .executeMandatorySchemaResource("Operation", "Component"));
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaUpdate()}
   */
  @Test
  public void testDbSchemaUpdate() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getSelectStatement(Mockito.<Class<Object>>any()))
        .thenThrow(new RuntimeException("ACT_RU_EXECUTION"));
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act and Assert
    assertThrows(RuntimeException.class,
        () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaUpdate());
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"), eq("ACT_RU_EXECUTION"),
        isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSelectStatement(isA(Class.class));
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaUpdate()}
   */
  @Test
  public void testDbSchemaUpdate2() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenThrow(new RuntimeException("ACT_RU_EXECUTION"));
    doThrow(new RuntimeException("ACT_RU_EXECUTION")).when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    TransactionFactory transactionFactory = mock(TransactionFactory.class);
    when(transactionFactory.newTransaction(Mockito.<DataSource>any(), Mockito.<TransactionIsolationLevel>any(),
        anyBoolean())).thenReturn(new JdbcTransaction(connection));
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", transactionFactory, mock(DataSource.class))));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaUpdate());
    verify(connection).getMetaData();
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"), eq("ACT_RU_EXECUTION"),
        isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    verify(transactionFactory).newTransaction(isA(DataSource.class), isNull(), eq(false));
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaUpdate()}
   */
  @Test
  public void testDbSchemaUpdate3() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(false).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    TransactionFactory transactionFactory = mock(TransactionFactory.class);
    when(transactionFactory.newTransaction(Mockito.<DataSource>any(), Mockito.<TransactionIsolationLevel>any(),
        anyBoolean())).thenReturn(new JdbcTransaction(connection));
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", transactionFactory, mock(DataSource.class))));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaUpdate());
    verify(connection).getMetaData();
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"), eq("ACT_RU_EXECUTION"),
        isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    verify(transactionFactory).newTransaction(isA(DataSource.class), isNull(), eq(false));
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaUpdate()}
   */
  @Test
  public void testDbSchemaUpdate4() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenThrow(new ActivitiException("An error occurred"));
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    TransactionFactory transactionFactory = mock(TransactionFactory.class);
    when(transactionFactory.newTransaction(Mockito.<DataSource>any(), Mockito.<TransactionIsolationLevel>any(),
        anyBoolean())).thenReturn(new ManagedTransaction(connection, true));
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", transactionFactory, mock(DataSource.class))));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaUpdate());
    verify(connection).getMetaData();
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"), eq("ACT_RU_EXECUTION"),
        isA(String[].class));
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    verify(transactionFactory).newTransaction(isA(DataSource.class), isNull(), eq(false));
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaUpdate()}
   */
  @Test
  public void testDbSchemaUpdate5() throws SQLException {
    // Arrange
    new ActivitiException("An error occurred");
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenThrow(new ActivitiException("An error occurred"));
    JdbcTransaction jdbcTransaction = mock(JdbcTransaction.class);
    when(jdbcTransaction.getConnection()).thenReturn(connection);
    TransactionFactory transactionFactory = mock(TransactionFactory.class);
    when(transactionFactory.newTransaction(Mockito.<DataSource>any(), Mockito.<TransactionIsolationLevel>any(),
        anyBoolean())).thenReturn(jdbcTransaction);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", transactionFactory, mock(DataSource.class))));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaUpdate());
    verify(connection).getMetaData();
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    verify(transactionFactory).newTransaction(isA(DataSource.class), isNull(), eq(false));
    verify(jdbcTransaction).getConnection();
  }

  /**
   * Method under test: {@link DbSqlSession#findMatchingVersionIndex(String)}
   */
  @Test
  public void testFindMatchingVersionIndex() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    int actualFindMatchingVersionIndexResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .findMatchingVersionIndex("1.0.2");

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    assertEquals(-1, actualFindMatchingVersionIndexResult);
  }

  /**
   * Method under test: {@link DbSqlSession#findMatchingVersionIndex(String)}
   */
  @Test
  public void testFindMatchingVersionIndex2() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    int actualFindMatchingVersionIndexResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .findMatchingVersionIndex("5.7");

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    assertEquals(0, actualFindMatchingVersionIndexResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isEngineTablePresent()}
   */
  @Test
  public void testIsEngineTablePresent() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    boolean actualIsEngineTablePresentResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .isEngineTablePresent();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"), eq("ACT_RU_EXECUTION"),
        isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    assertTrue(actualIsEngineTablePresentResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isEngineTablePresent()}
   */
  @Test
  public void testIsEngineTablePresent2() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenThrow(new RuntimeException("ACT_RU_EXECUTION"));
    doThrow(new RuntimeException("ACT_RU_EXECUTION")).when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).isEngineTablePresent());
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"), eq("ACT_RU_EXECUTION"),
        isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
  }

  /**
   * Method under test: {@link DbSqlSession#isEngineTablePresent()}
   */
  @Test
  public void testIsEngineTablePresent3() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("postgres");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    boolean actualIsEngineTablePresentResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .isEngineTablePresent();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"), eq("act_ru_execution"),
        isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    assertTrue(actualIsEngineTablePresentResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isEngineTablePresent()}
   */
  @Test
  public void testIsEngineTablePresent4() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("oracle");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    boolean actualIsEngineTablePresentResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .isEngineTablePresent();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("DATABASE SCHEMA"), eq("ACT_RU_EXECUTION"),
        isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    assertTrue(actualIsEngineTablePresentResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isEngineTablePresent()}
   */
  @Test
  public void testIsEngineTablePresent5() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(false);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getDatabaseTablePrefix()).thenReturn("Database Table Prefix");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    boolean actualIsEngineTablePresentResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .isEngineTablePresent();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"),
        eq("Database Table PrefixACT_RU_EXECUTION"), isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseTablePrefix();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    assertTrue(actualIsEngineTablePresentResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isEngineTablePresent()}
   */
  @Test
  public void testIsEngineTablePresent6() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn(null);
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    boolean actualIsEngineTablePresentResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .isEngineTablePresent();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(isNull(), eq("Database Schema"), eq("ACT_RU_EXECUTION"), isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    assertTrue(actualIsEngineTablePresentResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isEngineTablePresent()}
   */
  @Test
  public void testIsEngineTablePresent7() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    boolean actualIsEngineTablePresentResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .isEngineTablePresent();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(isNull(), eq("Database Schema"), eq("ACT_RU_EXECUTION"), isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    assertTrue(actualIsEngineTablePresentResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isEngineTablePresent()}
   */
  @Test
  public void testIsEngineTablePresent8() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn(null);
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    boolean actualIsEngineTablePresentResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .isEngineTablePresent();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(eq("Database Catalog"), isNull(), eq("ACT_RU_EXECUTION"), isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    assertTrue(actualIsEngineTablePresentResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isEngineTablePresent()}
   */
  @Test
  public void testIsEngineTablePresent9() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    boolean actualIsEngineTablePresentResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .isEngineTablePresent();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq(""), eq("ACT_RU_EXECUTION"), isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    assertTrue(actualIsEngineTablePresentResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isEngineTablePresent()}
   */
  @Test
  public void testIsEngineTablePresent10() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(false).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    JdbcTransactionFactory transactionFactory = mock(JdbcTransactionFactory.class);
    when(transactionFactory.newTransaction(Mockito.<DataSource>any(), Mockito.<TransactionIsolationLevel>any(),
        anyBoolean())).thenReturn(new JdbcTransaction(connection));
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", transactionFactory, mock(DataSource.class))));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    boolean actualIsEngineTablePresentResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .isEngineTablePresent();

    // Assert
    verify(connection).getMetaData();
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"), eq("ACT_RU_EXECUTION"),
        isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    verify(transactionFactory).newTransaction(isA(DataSource.class), isNull(), eq(false));
    assertFalse(actualIsEngineTablePresentResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isEngineTablePresent()}
   */
  @Test
  public void testIsEngineTablePresent11() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenThrow(new ActivitiException("An error occurred"));
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    JdbcTransactionFactory transactionFactory = mock(JdbcTransactionFactory.class);
    when(transactionFactory.newTransaction(Mockito.<DataSource>any(), Mockito.<TransactionIsolationLevel>any(),
        anyBoolean())).thenReturn(new ManagedTransaction(connection, true));
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", transactionFactory, mock(DataSource.class))));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).isEngineTablePresent());
    verify(connection).getMetaData();
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"), eq("ACT_RU_EXECUTION"),
        isA(String[].class));
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    verify(transactionFactory).newTransaction(isA(DataSource.class), isNull(), eq(false));
  }

  /**
   * Method under test: {@link DbSqlSession#isEngineTablePresent()}
   */
  @Test
  public void testIsEngineTablePresent12() throws SQLException {
    // Arrange
    new ActivitiException("An error occurred");
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenThrow(new ActivitiException("An error occurred"));
    JdbcTransaction jdbcTransaction = mock(JdbcTransaction.class);
    when(jdbcTransaction.getConnection()).thenReturn(connection);
    JdbcTransactionFactory transactionFactory = mock(JdbcTransactionFactory.class);
    when(transactionFactory.newTransaction(Mockito.<DataSource>any(), Mockito.<TransactionIsolationLevel>any(),
        anyBoolean())).thenReturn(jdbcTransaction);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", transactionFactory, mock(DataSource.class))));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).isEngineTablePresent());
    verify(connection).getMetaData();
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    verify(jdbcTransaction).getConnection();
    verify(transactionFactory).newTransaction(isA(DataSource.class), isNull(), eq(false));
  }

  /**
   * Method under test: {@link DbSqlSession#isHistoryTablePresent()}
   */
  @Test
  public void testIsHistoryTablePresent() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    boolean actualIsHistoryTablePresentResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .isHistoryTablePresent();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"), eq("ACT_HI_PROCINST"),
        isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    assertTrue(actualIsHistoryTablePresentResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isHistoryTablePresent()}
   */
  @Test
  public void testIsHistoryTablePresent2() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenThrow(new RuntimeException("ACT_HI_PROCINST"));
    doThrow(new RuntimeException("ACT_HI_PROCINST")).when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).isHistoryTablePresent());
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"), eq("ACT_HI_PROCINST"),
        isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
  }

  /**
   * Method under test: {@link DbSqlSession#isHistoryTablePresent()}
   */
  @Test
  public void testIsHistoryTablePresent3() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("postgres");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    boolean actualIsHistoryTablePresentResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .isHistoryTablePresent();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"), eq("act_hi_procinst"),
        isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    assertTrue(actualIsHistoryTablePresentResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isHistoryTablePresent()}
   */
  @Test
  public void testIsHistoryTablePresent4() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("oracle");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    boolean actualIsHistoryTablePresentResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .isHistoryTablePresent();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("DATABASE SCHEMA"), eq("ACT_HI_PROCINST"),
        isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    assertTrue(actualIsHistoryTablePresentResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isHistoryTablePresent()}
   */
  @Test
  public void testIsHistoryTablePresent5() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(false);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getDatabaseTablePrefix()).thenReturn("Database Table Prefix");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    boolean actualIsHistoryTablePresentResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .isHistoryTablePresent();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"),
        eq("Database Table PrefixACT_HI_PROCINST"), isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseTablePrefix();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    assertTrue(actualIsHistoryTablePresentResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isHistoryTablePresent()}
   */
  @Test
  public void testIsHistoryTablePresent6() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn(null);
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    boolean actualIsHistoryTablePresentResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .isHistoryTablePresent();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(isNull(), eq("Database Schema"), eq("ACT_HI_PROCINST"), isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    assertTrue(actualIsHistoryTablePresentResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isHistoryTablePresent()}
   */
  @Test
  public void testIsHistoryTablePresent7() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    boolean actualIsHistoryTablePresentResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .isHistoryTablePresent();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(isNull(), eq("Database Schema"), eq("ACT_HI_PROCINST"), isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    assertTrue(actualIsHistoryTablePresentResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isHistoryTablePresent()}
   */
  @Test
  public void testIsHistoryTablePresent8() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn(null);
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    boolean actualIsHistoryTablePresentResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .isHistoryTablePresent();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(eq("Database Catalog"), isNull(), eq("ACT_HI_PROCINST"), isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    assertTrue(actualIsHistoryTablePresentResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isHistoryTablePresent()}
   */
  @Test
  public void testIsHistoryTablePresent9() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    boolean actualIsHistoryTablePresentResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .isHistoryTablePresent();

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq(""), eq("ACT_HI_PROCINST"), isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    assertTrue(actualIsHistoryTablePresentResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isHistoryTablePresent()}
   */
  @Test
  public void testIsHistoryTablePresent10() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(false).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    JdbcTransactionFactory transactionFactory = mock(JdbcTransactionFactory.class);
    when(transactionFactory.newTransaction(Mockito.<DataSource>any(), Mockito.<TransactionIsolationLevel>any(),
        anyBoolean())).thenReturn(new JdbcTransaction(connection));
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", transactionFactory, mock(DataSource.class))));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    boolean actualIsHistoryTablePresentResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .isHistoryTablePresent();

    // Assert
    verify(connection).getMetaData();
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"), eq("ACT_HI_PROCINST"),
        isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    verify(transactionFactory).newTransaction(isA(DataSource.class), isNull(), eq(false));
    assertFalse(actualIsHistoryTablePresentResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isHistoryTablePresent()}
   */
  @Test
  public void testIsHistoryTablePresent11() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenThrow(new ActivitiException("An error occurred"));
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    JdbcTransactionFactory transactionFactory = mock(JdbcTransactionFactory.class);
    when(transactionFactory.newTransaction(Mockito.<DataSource>any(), Mockito.<TransactionIsolationLevel>any(),
        anyBoolean())).thenReturn(new ManagedTransaction(connection, true));
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", transactionFactory, mock(DataSource.class))));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).isHistoryTablePresent());
    verify(connection).getMetaData();
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"), eq("ACT_HI_PROCINST"),
        isA(String[].class));
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    verify(transactionFactory).newTransaction(isA(DataSource.class), isNull(), eq(false));
  }

  /**
   * Method under test: {@link DbSqlSession#isHistoryTablePresent()}
   */
  @Test
  public void testIsHistoryTablePresent12() throws SQLException {
    // Arrange
    new ActivitiException("An error occurred");
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenThrow(new ActivitiException("An error occurred"));
    JdbcTransaction jdbcTransaction = mock(JdbcTransaction.class);
    when(jdbcTransaction.getConnection()).thenReturn(connection);
    JdbcTransactionFactory transactionFactory = mock(JdbcTransactionFactory.class);
    when(transactionFactory.newTransaction(Mockito.<DataSource>any(), Mockito.<TransactionIsolationLevel>any(),
        anyBoolean())).thenReturn(jdbcTransaction);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", transactionFactory, mock(DataSource.class))));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).isHistoryTablePresent());
    verify(connection).getMetaData();
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    verify(jdbcTransaction).getConnection();
    verify(transactionFactory).newTransaction(isA(DataSource.class), isNull(), eq(false));
  }

  /**
   * Method under test: {@link DbSqlSession#isTablePresent(String)}
   */
  @Test
  public void testIsTablePresent() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    boolean actualIsTablePresentResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .isTablePresent("Table Name");

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"), eq("Table Name"),
        isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    assertTrue(actualIsTablePresentResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isTablePresent(String)}
   */
  @Test
  public void testIsTablePresent2() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenThrow(new RuntimeException("postgres"));
    doThrow(new RuntimeException("postgres")).when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).isTablePresent("Table Name"));
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"), eq("Table Name"),
        isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
  }

  /**
   * Method under test: {@link DbSqlSession#isTablePresent(String)}
   */
  @Test
  public void testIsTablePresent3() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("postgres");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    boolean actualIsTablePresentResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .isTablePresent("Table Name");

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"), eq("table name"),
        isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    assertTrue(actualIsTablePresentResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isTablePresent(String)}
   */
  @Test
  public void testIsTablePresent4() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("oracle");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    boolean actualIsTablePresentResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .isTablePresent("Table Name");

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("DATABASE SCHEMA"), eq("Table Name"),
        isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    assertTrue(actualIsTablePresentResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isTablePresent(String)}
   */
  @Test
  public void testIsTablePresent5() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(false);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getDatabaseTablePrefix()).thenReturn("Database Table Prefix");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    boolean actualIsTablePresentResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .isTablePresent("Table Name");

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"),
        eq("Database Table PrefixTable Name"), isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseTablePrefix();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    assertTrue(actualIsTablePresentResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isTablePresent(String)}
   */
  @Test
  public void testIsTablePresent6() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn(null);
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    boolean actualIsTablePresentResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .isTablePresent("Table Name");

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(isNull(), eq("Database Schema"), eq("Table Name"), isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    assertTrue(actualIsTablePresentResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isTablePresent(String)}
   */
  @Test
  public void testIsTablePresent7() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    boolean actualIsTablePresentResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .isTablePresent("Table Name");

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(isNull(), eq("Database Schema"), eq("Table Name"), isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    assertTrue(actualIsTablePresentResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isTablePresent(String)}
   */
  @Test
  public void testIsTablePresent8() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn(null);
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    boolean actualIsTablePresentResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .isTablePresent("Table Name");

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(eq("Database Catalog"), isNull(), eq("Table Name"), isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    assertTrue(actualIsTablePresentResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isTablePresent(String)}
   */
  @Test
  public void testIsTablePresent9() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    doNothing().when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    boolean actualIsTablePresentResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .isTablePresent("Table Name");

    // Assert
    verify(connection).getAutoCommit();
    verify(connection).getMetaData();
    verify(connection).setAutoCommit(eq(false));
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq(""), eq("Table Name"), isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    assertTrue(actualIsTablePresentResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isTablePresent(String)}
   */
  @Test
  public void testIsTablePresent10() throws SQLException {
    // Arrange
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(false).thenReturn(true).thenReturn(false);
    doNothing().when(resultSet).close();
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenReturn(resultSet);
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    JdbcTransactionFactory transactionFactory = mock(JdbcTransactionFactory.class);
    when(transactionFactory.newTransaction(Mockito.<DataSource>any(), Mockito.<TransactionIsolationLevel>any(),
        anyBoolean())).thenReturn(new JdbcTransaction(connection));
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", transactionFactory, mock(DataSource.class))));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act
    boolean actualIsTablePresentResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .isTablePresent("Table Name");

    // Assert
    verify(connection).getMetaData();
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"), eq("Table Name"),
        isA(String[].class));
    verify(resultSet).close();
    verify(resultSet).next();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    verify(transactionFactory).newTransaction(isA(DataSource.class), isNull(), eq(false));
    assertFalse(actualIsTablePresentResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isTablePresent(String)}
   */
  @Test
  public void testIsTablePresent11() throws SQLException {
    // Arrange
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    when(databaseMetaData.getTables(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String[]>any())).thenThrow(new ActivitiException("An error occurred"));
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenReturn(databaseMetaData);
    JdbcTransactionFactory transactionFactory = mock(JdbcTransactionFactory.class);
    when(transactionFactory.newTransaction(Mockito.<DataSource>any(), Mockito.<TransactionIsolationLevel>any(),
        anyBoolean())).thenReturn(new ManagedTransaction(connection, true));
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", transactionFactory, mock(DataSource.class))));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).isTablePresent("Table Name"));
    verify(connection).getMetaData();
    verify(databaseMetaData).getTables(eq("Database Catalog"), eq("Database Schema"), eq("Table Name"),
        isA(String[].class));
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseCatalog();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    verify(transactionFactory).newTransaction(isA(DataSource.class), isNull(), eq(false));
  }

  /**
   * Method under test: {@link DbSqlSession#isTablePresent(String)}
   */
  @Test
  public void testIsTablePresent12() throws SQLException {
    // Arrange
    new ActivitiException("An error occurred");
    Connection connection = mock(Connection.class);
    when(connection.getMetaData()).thenThrow(new ActivitiException("An error occurred"));
    JdbcTransaction jdbcTransaction = mock(JdbcTransaction.class);
    when(jdbcTransaction.getConnection()).thenReturn(connection);
    JdbcTransactionFactory transactionFactory = mock(JdbcTransactionFactory.class);
    when(transactionFactory.newTransaction(Mockito.<DataSource>any(), Mockito.<TransactionIsolationLevel>any(),
        anyBoolean())).thenReturn(jdbcTransaction);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", transactionFactory, mock(DataSource.class))));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.isTablePrefixIsSchema()).thenReturn(true);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).isTablePresent("Table Name"));
    verify(connection).getMetaData();
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(dbSqlSessionFactory).isTablePrefixIsSchema();
    verify(jdbcTransaction).getConnection();
    verify(transactionFactory).newTransaction(isA(DataSource.class), isNull(), eq(false));
  }

  /**
   * Method under test: {@link DbSqlSession#prependDatabaseTablePrefix(String)}
   */
  @Test
  public void testPrependDatabaseTablePrefix() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getDatabaseTablePrefix()).thenReturn("Database Table Prefix");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    String actualPrependDatabaseTablePrefixResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .prependDatabaseTablePrefix("Table Name");

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseTablePrefix();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    assertEquals("Database Table PrefixTable Name", actualPrependDatabaseTablePrefixResult);
  }

  /**
   * Method under test: {@link DbSqlSession#dbSchemaUpgrade(String, int)}
   */
  @Test
  public void testDbSchemaUpgrade() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).dbSchemaUpgrade("Component", 1);

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory, atLeast(1)).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test:
   * {@link DbSqlSession#getResourceForDbOperation(String, String, String)}
   */
  @Test
  public void testGetResourceForDbOperation() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    String actualResourceForDbOperation = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .getResourceForDbOperation("/directory", "Operation", "Component");

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    assertEquals("org/activiti/db//directory/activiti.Database Type.Operation.Component.sql",
        actualResourceForDbOperation);
  }

  /**
   * Method under test:
   * {@link DbSqlSession#executeSchemaResource(String, String, String, boolean)}
   */
  @Test
  public void testExecuteSchemaResource() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).executeSchemaResource("Operation", "Component",
        "Resource Name", true);

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test:
   * {@link DbSqlSession#executeSchemaResource(String, String, String, boolean)}
   */
  @Test
  public void testExecuteSchemaResource2() throws SQLException {
    // Arrange
    Connection connection = mock(Connection.class);
    doThrow(new ActivitiException("An error occurred")).when(connection).setAutoCommit(anyBoolean());
    when(connection.getAutoCommit()).thenReturn(true);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), dataSource)));
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .executeSchemaResource("Operation", "Component", "", true));
    verify(connection).getAutoCommit();
    verify(connection).setAutoCommit(eq(false));
    verify(dataSource).getConnection();
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
  }

  /**
   * Method under test:
   * {@link DbSqlSession#updateDdlForMySqlVersionLowerThan56(String)}
   */
  @Test
  public void testUpdateDdlForMySqlVersionLowerThan56() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    String actualUpdateDdlForMySqlVersionLowerThan56Result = (new DbSqlSession(dbSqlSessionFactory,
        new EntityCacheImpl())).updateDdlForMySqlVersionLowerThan56("Ddl Statements");

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    assertEquals("Ddl Statements", actualUpdateDdlForMySqlVersionLowerThan56Result);
  }

  /**
   * Method under test: {@link DbSqlSession#addSqlStatementPiece(String, String)}
   */
  @Test
  public void testAddSqlStatementPiece() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    String actualAddSqlStatementPieceResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .addSqlStatementPiece("Sql Statement", "Line");

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    assertEquals("Sql Statement \nLine", actualAddSqlStatementPieceResult);
  }

  /**
   * Method under test: {@link DbSqlSession#addSqlStatementPiece(String, String)}
   */
  @Test
  public void testAddSqlStatementPiece2() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    String actualAddSqlStatementPieceResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .addSqlStatementPiece(null, "Line");

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    assertEquals("Line", actualAddSqlStatementPieceResult);
  }

  /**
   * Method under test: {@link DbSqlSession#readNextTrimmedLine(BufferedReader)}
   */
  @Test
  public void testReadNextTrimmedLine() throws IOException {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    String actualReadNextTrimmedLineResult = dbSqlSession
        .readNextTrimmedLine(new BufferedReader(new StringReader("foo"), 1));

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    assertEquals("foo", actualReadNextTrimmedLineResult);
  }

  /**
   * Method under test: {@link DbSqlSession#readNextTrimmedLine(BufferedReader)}
   */
  @Test
  public void testReadNextTrimmedLine2() throws IOException {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    String actualReadNextTrimmedLineResult = dbSqlSession
        .readNextTrimmedLine(new BufferedReader(new StringReader(""), 1));

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    assertNull(actualReadNextTrimmedLineResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isMissingTablesException(Exception)}
   */
  @Test
  public void testIsMissingTablesException() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    boolean actualIsMissingTablesExceptionResult = dbSqlSession.isMissingTablesException(new Exception("foo"));

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    assertFalse(actualIsMissingTablesExceptionResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isMissingTablesException(Exception)}
   */
  @Test
  public void testIsMissingTablesException2() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    boolean actualIsMissingTablesExceptionResult = dbSqlSession.isMissingTablesException(new Exception("Table"));

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    assertFalse(actualIsMissingTablesExceptionResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isMissingTablesException(Exception)}
   */
  @Test
  public void testIsMissingTablesException3() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    boolean actualIsMissingTablesExceptionResult = dbSqlSession.isMissingTablesException(new Exception("table"));

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    assertFalse(actualIsMissingTablesExceptionResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isMissingTablesException(Exception)}
   */
  @Test
  public void testIsMissingTablesException4() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    boolean actualIsMissingTablesExceptionResult = dbSqlSession.isMissingTablesException(new Exception("relation"));

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    assertFalse(actualIsMissingTablesExceptionResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isMissingTablesException(Exception)}
   */
  @Test
  public void testIsMissingTablesException5() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Act
    boolean actualIsMissingTablesExceptionResult = dbSqlSession.isMissingTablesException(new Exception((String) null));

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    assertFalse(actualIsMissingTablesExceptionResult);
  }

  /**
   * Method under test: {@link DbSqlSession#getCustomMapper(Class)}
   */
  @Test
  public void testGetCustomMapper() {
    // Arrange
    Configuration configuration = mock(Configuration.class);
    when(configuration.getMapper(Mockito.<Class<Object>>any(), Mockito.<SqlSession>any())).thenReturn(JSONObject.NULL);
    Configuration configuration2 = new Configuration();
    when(configuration.newExecutor(Mockito.<Transaction>any(), Mockito.<ExecutorType>any())).thenReturn(
        new CachingExecutor(new BatchExecutor(configuration2, new JdbcTransaction(mock(Connection.class)))));
    when(configuration.getEnvironment())
        .thenReturn(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)));
    when(configuration.getDefaultExecutorType()).thenReturn(ExecutorType.SIMPLE);
    DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configuration);
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(defaultSqlSessionFactory);
    DbSqlSession dbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());
    Class<Object> type = Object.class;

    // Act
    dbSqlSession.getCustomMapper(type);

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    verify(configuration).getDefaultExecutorType();
    verify(configuration).getEnvironment();
    verify(configuration).getMapper(isA(Class.class), isA(SqlSession.class));
    verify(configuration).newExecutor(isA(Transaction.class), eq(ExecutorType.SIMPLE));
  }

  /**
   * Method under test: {@link DbSqlSession#isMysql()}
   */
  @Test
  public void testIsMysql() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    boolean actualIsMysqlResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).isMysql();

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    assertFalse(actualIsMysqlResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isMysql()}
   */
  @Test
  public void testIsMysql2() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("mysql");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    boolean actualIsMysqlResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).isMysql();

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    assertTrue(actualIsMysqlResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isMariaDb()}
   */
  @Test
  public void testIsMariaDb() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    boolean actualIsMariaDbResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).isMariaDb();

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    assertFalse(actualIsMariaDbResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isMariaDb()}
   */
  @Test
  public void testIsMariaDb2() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("mariadb");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    boolean actualIsMariaDbResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).isMariaDb();

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    assertTrue(actualIsMariaDbResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isOracle()}
   */
  @Test
  public void testIsOracle() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("Database Type");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    boolean actualIsOracleResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).isOracle();

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    assertFalse(actualIsOracleResult);
  }

  /**
   * Method under test: {@link DbSqlSession#isOracle()}
   */
  @Test
  public void testIsOracle2() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getDatabaseType()).thenReturn("oracle");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    boolean actualIsOracleResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl())).isOracle();

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getDatabaseType();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    assertTrue(actualIsOracleResult);
  }

  /**
   * Method under test: {@link DbSqlSession#createDeploymentQuery()}
   */
  @Test
  public void testCreateDeploymentQuery() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    DeploymentQueryImpl actualCreateDeploymentQueryResult = (new DbSqlSession(dbSqlSessionFactory,
        new EntityCacheImpl())).createDeploymentQuery();

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
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
    assertSame(actualCreateDeploymentQueryResult, actualCreateDeploymentQueryResult.getParameter());
  }

  /**
   * Method under test: {@link DbSqlSession#createModelQueryImpl()}
   */
  @Test
  public void testCreateModelQueryImpl() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    ModelQueryImpl actualCreateModelQueryImplResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .createModelQueryImpl();

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
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
    assertSame(actualCreateModelQueryImplResult, actualCreateModelQueryImplResult.getParameter());
  }

  /**
   * Method under test: {@link DbSqlSession#createProcessDefinitionQuery()}
   */
  @Test
  public void testCreateProcessDefinitionQuery() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    ProcessDefinitionQueryImpl actualCreateProcessDefinitionQueryResult = (new DbSqlSession(dbSqlSessionFactory,
        new EntityCacheImpl())).createProcessDefinitionQuery();

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
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
    assertSame(actualCreateProcessDefinitionQueryResult, actualCreateProcessDefinitionQueryResult.getParameter());
  }

  /**
   * Method under test: {@link DbSqlSession#createProcessInstanceQuery()}
   */
  @Test
  public void testCreateProcessInstanceQuery() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    ProcessInstanceQueryImpl actualCreateProcessInstanceQueryResult = (new DbSqlSession(dbSqlSessionFactory,
        new EntityCacheImpl())).createProcessInstanceQuery();

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
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
    assertFalse(actualCreateProcessInstanceQueryResult.isIncludeChildExecutionsWithBusinessKeyQuery());
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
    assertSame(actualCreateProcessInstanceQueryResult, actualCreateProcessInstanceQueryResult.getParameter());
  }

  /**
   * Method under test: {@link DbSqlSession#createExecutionQuery()}
   */
  @Test
  public void testCreateExecutionQuery() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    ExecutionQueryImpl actualCreateExecutionQueryResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .createExecutionQuery();

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
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
    assertSame(actualCreateExecutionQueryResult, actualCreateExecutionQueryResult.getParameter());
  }

  /**
   * Method under test: {@link DbSqlSession#createTaskQuery()}
   */
  @Test
  public void testCreateTaskQuery() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    TaskQueryImpl actualCreateTaskQueryResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .createTaskQuery();

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
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
    assertSame(actualCreateTaskQueryResult, actualCreateTaskQueryResult.getParameter());
  }

  /**
   * Method under test: {@link DbSqlSession#createJobQuery()}
   */
  @Test
  public void testCreateJobQuery() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    JobQueryImpl actualCreateJobQueryResult = (new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl()))
        .createJobQuery();

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
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
    assertSame(actualCreateJobQueryResult, actualCreateJobQueryResult.getParameter());
  }

  /**
   * Method under test: {@link DbSqlSession#createHistoricProcessInstanceQuery()}
   */
  @Test
  public void testCreateHistoricProcessInstanceQuery() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    HistoricProcessInstanceQueryImpl actualCreateHistoricProcessInstanceQueryResult = (new DbSqlSession(
        dbSqlSessionFactory, new EntityCacheImpl())).createHistoricProcessInstanceQuery();

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    assertEquals("RES.ID_ asc", actualCreateHistoricProcessInstanceQueryResult.getOrderBy());
    assertEquals("RES.ID_ asc", actualCreateHistoricProcessInstanceQueryResult.getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc", actualCreateHistoricProcessInstanceQueryResult.getMssqlOrDB2OrderBy());
    assertEquals("null:%:%", actualCreateHistoricProcessInstanceQueryResult.getProcessDefinitionIdLike());
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
    assertSame(actualCreateHistoricProcessInstanceQueryResult,
        actualCreateHistoricProcessInstanceQueryResult.getParameter());
  }

  /**
   * Method under test: {@link DbSqlSession#createHistoricActivityInstanceQuery()}
   */
  @Test
  public void testCreateHistoricActivityInstanceQuery() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    HistoricActivityInstanceQueryImpl actualCreateHistoricActivityInstanceQueryResult = (new DbSqlSession(
        dbSqlSessionFactory, new EntityCacheImpl())).createHistoricActivityInstanceQuery();

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    assertEquals("RES.ID_ asc", actualCreateHistoricActivityInstanceQueryResult.getOrderBy());
    assertEquals("RES.ID_ asc", actualCreateHistoricActivityInstanceQueryResult.getOrderByColumns());
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
    assertEquals(Integer.MAX_VALUE, actualCreateHistoricActivityInstanceQueryResult.getMaxResults());
    assertSame(actualCreateHistoricActivityInstanceQueryResult,
        actualCreateHistoricActivityInstanceQueryResult.getParameter());
  }

  /**
   * Method under test: {@link DbSqlSession#createHistoricTaskInstanceQuery()}
   */
  @Test
  public void testCreateHistoricTaskInstanceQuery() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    HistoricTaskInstanceQueryImpl actualCreateHistoricTaskInstanceQueryResult = (new DbSqlSession(dbSqlSessionFactory,
        new EntityCacheImpl())).createHistoricTaskInstanceQuery();

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    assertEquals("RES.ID_ asc", actualCreateHistoricTaskInstanceQueryResult.getOrderBy());
    assertEquals("RES.ID_ asc", actualCreateHistoricTaskInstanceQueryResult.getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc", actualCreateHistoricTaskInstanceQueryResult.getMssqlOrDB2OrderBy());
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
    assertNull(actualCreateHistoricTaskInstanceQueryResult.getProcessInstanceBusinessKeyLikeIgnoreCase());
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
    assertSame(actualCreateHistoricTaskInstanceQueryResult, actualCreateHistoricTaskInstanceQueryResult.getParameter());
  }

  /**
   * Method under test: {@link DbSqlSession#createHistoricDetailQuery()}
   */
  @Test
  public void testCreateHistoricDetailQuery() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    HistoricDetailQueryImpl actualCreateHistoricDetailQueryResult = (new DbSqlSession(dbSqlSessionFactory,
        new EntityCacheImpl())).createHistoricDetailQuery();

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
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
    assertSame(actualCreateHistoricDetailQueryResult, actualCreateHistoricDetailQueryResult.getParameter());
  }

  /**
   * Method under test: {@link DbSqlSession#createHistoricVariableInstanceQuery()}
   */
  @Test
  public void testCreateHistoricVariableInstanceQuery() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = mock(DbSqlSessionFactory.class);
    when(dbSqlSessionFactory.getDatabaseCatalog()).thenReturn("Database Catalog");
    when(dbSqlSessionFactory.getDatabaseSchema()).thenReturn("Database Schema");
    when(dbSqlSessionFactory.getSqlSessionFactory()).thenReturn(new DefaultSqlSessionFactory(
        new Configuration(new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)))));

    // Act
    HistoricVariableInstanceQueryImpl actualCreateHistoricVariableInstanceQueryResult = (new DbSqlSession(
        dbSqlSessionFactory, new EntityCacheImpl())).createHistoricVariableInstanceQuery();

    // Assert
    verify(dbSqlSessionFactory).getDatabaseCatalog();
    verify(dbSqlSessionFactory).getDatabaseSchema();
    verify(dbSqlSessionFactory).getSqlSessionFactory();
    assertEquals("RES.ID_ asc", actualCreateHistoricVariableInstanceQueryResult.getOrderBy());
    assertEquals("RES.ID_ asc", actualCreateHistoricVariableInstanceQueryResult.getOrderByColumns());
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
    assertEquals(Integer.MAX_VALUE, actualCreateHistoricVariableInstanceQueryResult.getMaxResults());
    assertSame(actualCreateHistoricVariableInstanceQueryResult,
        actualCreateHistoricVariableInstanceQueryResult.getParameter());
  }

  /**
   * Method under test:
   * {@link DbSqlSession#DbSqlSession(DbSqlSessionFactory, EntityCache)}
   */
  @Test
  public void testNewDbSqlSession() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    Configuration configuration = new Configuration(
        new Environment("42", new JdbcTransactionFactory(), mock(DataSource.class)));
    dbSqlSessionFactory.setSqlSessionFactory(new DefaultSqlSessionFactory(configuration));

    // Act
    DbSqlSession actualDbSqlSession = new DbSqlSession(dbSqlSessionFactory, new EntityCacheImpl());

    // Assert
    EntityCache entityCache = actualDbSqlSession.entityCache;
    assertTrue(entityCache instanceof EntityCacheImpl);
    SqlSession sqlSession = actualDbSqlSession.getSqlSession();
    assertTrue(sqlSession instanceof DefaultSqlSession);
    assertNull(actualDbSqlSession.connectionMetadataDefaultCatalog);
    assertNull(actualDbSqlSession.connectionMetadataDefaultSchema);
    assertTrue(actualDbSqlSession.updatedObjects.isEmpty());
    assertTrue(entityCache.getAllCachedEntities().isEmpty());
    assertTrue(actualDbSqlSession.bulkDeleteOperations.isEmpty());
    assertTrue(actualDbSqlSession.deletedObjects.isEmpty());
    assertTrue(actualDbSqlSession.insertedObjects.isEmpty());
    assertSame(dbSqlSessionFactory, actualDbSqlSession.getDbSqlSessionFactory());
    assertSame(configuration, sqlSession.getConfiguration());
  }
}
