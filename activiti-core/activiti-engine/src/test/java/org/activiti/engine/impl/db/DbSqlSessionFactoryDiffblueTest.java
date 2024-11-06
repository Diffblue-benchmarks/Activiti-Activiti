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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.impl.cfg.IdGenerator;
import org.activiti.engine.impl.persistence.entity.Entity;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DbSqlSessionFactoryDiffblueTest {
  @InjectMocks
  private DbSqlSessionFactory dbSqlSessionFactory;

  /**
   * Test {@link DbSqlSessionFactory#mapStatement(String)}.
   * <p>
   * Method under test: {@link DbSqlSessionFactory#mapStatement(String)}
   */
  @Test
  public void testMapStatement() {
    // Arrange, Act and Assert
    assertEquals("MD", dbSqlSessionFactory.mapStatement("MD"));
  }

  /**
   * Test {@link DbSqlSessionFactory#isBulkInsertable(Class)}.
   * <ul>
   *   <li>Given {@link DbSqlSessionFactory} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DbSqlSessionFactory#isBulkInsertable(Class)}
   */
  @Test
  public void testIsBulkInsertable_givenDbSqlSessionFactory() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    Class<Entity> entityClass = Entity.class;

    // Act and Assert
    assertFalse(dbSqlSessionFactory.isBulkInsertable(entityClass));
  }

  /**
   * Test {@link DbSqlSessionFactory#isBulkInsertable(Class)}.
   * <ul>
   *   <li>Given {@link DbSqlSessionFactory} (default constructor) IdGenerator is
   * {@link IdGenerator}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DbSqlSessionFactory#isBulkInsertable(Class)}
   */
  @Test
  public void testIsBulkInsertable_givenDbSqlSessionFactoryIdGeneratorIsIdGenerator() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setIdGenerator(mock(IdGenerator.class));
    Class<Entity> entityClass = Entity.class;

    // Act and Assert
    assertFalse(dbSqlSessionFactory.isBulkInsertable(entityClass));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link DbSqlSessionFactory}
   *   <li>{@link DbSqlSessionFactory#setBulkDeleteStatements(Map)}
   *   <li>{@link DbSqlSessionFactory#setBulkInsertStatements(Map)}
   *   <li>{@link DbSqlSessionFactory#setDatabaseCatalog(String)}
   *   <li>{@link DbSqlSessionFactory#setDatabaseSchema(String)}
   *   <li>{@link DbSqlSessionFactory#setDatabaseTablePrefix(String)}
   *   <li>{@link DbSqlSessionFactory#setDbHistoryUsed(boolean)}
   *   <li>{@link DbSqlSessionFactory#setDeleteStatements(Map)}
   *   <li>{@link DbSqlSessionFactory#setIdGenerator(IdGenerator)}
   *   <li>{@link DbSqlSessionFactory#setInsertStatements(Map)}
   *   <li>{@link DbSqlSessionFactory#setMaxNrOfStatementsInBulkInsert(int)}
   *   <li>{@link DbSqlSessionFactory#setSelectStatements(Map)}
   *   <li>{@link DbSqlSessionFactory#setSqlSessionFactory(SqlSessionFactory)}
   *   <li>{@link DbSqlSessionFactory#setStatementMappings(Map)}
   *   <li>{@link DbSqlSessionFactory#setTablePrefixIsSchema(boolean)}
   *   <li>{@link DbSqlSessionFactory#setUpdateStatements(Map)}
   *   <li>{@link DbSqlSessionFactory#getBulkDeleteStatements()}
   *   <li>{@link DbSqlSessionFactory#getBulkInsertStatements()}
   *   <li>{@link DbSqlSessionFactory#getDatabaseCatalog()}
   *   <li>{@link DbSqlSessionFactory#getDatabaseSchema()}
   *   <li>{@link DbSqlSessionFactory#getDatabaseTablePrefix()}
   *   <li>{@link DbSqlSessionFactory#getDatabaseType()}
   *   <li>{@link DbSqlSessionFactory#getDeleteStatements()}
   *   <li>{@link DbSqlSessionFactory#getIdGenerator()}
   *   <li>{@link DbSqlSessionFactory#getInsertStatements()}
   *   <li>{@link DbSqlSessionFactory#getMaxNrOfStatementsInBulkInsert()}
   *   <li>{@link DbSqlSessionFactory#getSelectStatements()}
   *   <li>{@link DbSqlSessionFactory#getSessionType()}
   *   <li>{@link DbSqlSessionFactory#getSqlSessionFactory()}
   *   <li>{@link DbSqlSessionFactory#getStatementMappings()}
   *   <li>{@link DbSqlSessionFactory#getUpdateStatements()}
   *   <li>{@link DbSqlSessionFactory#isDbHistoryUsed()}
   *   <li>{@link DbSqlSessionFactory#isTablePrefixIsSchema()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    DbSqlSessionFactory actualDbSqlSessionFactory = new DbSqlSessionFactory();
    HashMap<Class<?>, String> bulkDeleteStatements = new HashMap<>();
    actualDbSqlSessionFactory.setBulkDeleteStatements(bulkDeleteStatements);
    HashMap<Class<?>, String> bulkInsertStatements = new HashMap<>();
    actualDbSqlSessionFactory.setBulkInsertStatements(bulkInsertStatements);
    actualDbSqlSessionFactory.setDatabaseCatalog("Database Catalog");
    actualDbSqlSessionFactory.setDatabaseSchema("Database Schema");
    actualDbSqlSessionFactory.setDatabaseTablePrefix("Database Table Prefix");
    actualDbSqlSessionFactory.setDbHistoryUsed(true);
    HashMap<Class<?>, String> deleteStatements = new HashMap<>();
    actualDbSqlSessionFactory.setDeleteStatements(deleteStatements);
    IdGenerator idGenerator = mock(IdGenerator.class);
    actualDbSqlSessionFactory.setIdGenerator(idGenerator);
    HashMap<Class<?>, String> insertStatements = new HashMap<>();
    actualDbSqlSessionFactory.setInsertStatements(insertStatements);
    actualDbSqlSessionFactory.setMaxNrOfStatementsInBulkInsert(3);
    HashMap<Class<?>, String> selectStatements = new HashMap<>();
    actualDbSqlSessionFactory.setSelectStatements(selectStatements);
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(new Configuration());
    actualDbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
    HashMap<String, String> statementMappings = new HashMap<>();
    actualDbSqlSessionFactory.setStatementMappings(statementMappings);
    actualDbSqlSessionFactory.setTablePrefixIsSchema(true);
    HashMap<Class<?>, String> updateStatements = new HashMap<>();
    actualDbSqlSessionFactory.setUpdateStatements(updateStatements);
    Map<Class<?>, String> actualBulkDeleteStatements = actualDbSqlSessionFactory.getBulkDeleteStatements();
    Map<Class<?>, String> actualBulkInsertStatements = actualDbSqlSessionFactory.getBulkInsertStatements();
    String actualDatabaseCatalog = actualDbSqlSessionFactory.getDatabaseCatalog();
    String actualDatabaseSchema = actualDbSqlSessionFactory.getDatabaseSchema();
    String actualDatabaseTablePrefix = actualDbSqlSessionFactory.getDatabaseTablePrefix();
    actualDbSqlSessionFactory.getDatabaseType();
    Map<Class<?>, String> actualDeleteStatements = actualDbSqlSessionFactory.getDeleteStatements();
    IdGenerator actualIdGenerator = actualDbSqlSessionFactory.getIdGenerator();
    Map<Class<?>, String> actualInsertStatements = actualDbSqlSessionFactory.getInsertStatements();
    int actualMaxNrOfStatementsInBulkInsert = actualDbSqlSessionFactory.getMaxNrOfStatementsInBulkInsert();
    Map<Class<?>, String> actualSelectStatements = actualDbSqlSessionFactory.getSelectStatements();
    Class<?> actualSessionType = actualDbSqlSessionFactory.getSessionType();
    SqlSessionFactory actualSqlSessionFactory = actualDbSqlSessionFactory.getSqlSessionFactory();
    Map<String, String> actualStatementMappings = actualDbSqlSessionFactory.getStatementMappings();
    Map<Class<?>, String> actualUpdateStatements = actualDbSqlSessionFactory.getUpdateStatements();
    boolean actualIsDbHistoryUsedResult = actualDbSqlSessionFactory.isDbHistoryUsed();
    boolean actualIsTablePrefixIsSchemaResult = actualDbSqlSessionFactory.isTablePrefixIsSchema();

    // Assert that nothing has changed
    assertEquals("Database Catalog", actualDatabaseCatalog);
    assertEquals("Database Schema", actualDatabaseSchema);
    assertEquals("Database Table Prefix", actualDatabaseTablePrefix);
    assertEquals(3, actualMaxNrOfStatementsInBulkInsert);
    assertTrue(actualBulkDeleteStatements.isEmpty());
    assertTrue(actualBulkInsertStatements.isEmpty());
    assertTrue(actualDeleteStatements.isEmpty());
    assertTrue(actualInsertStatements.isEmpty());
    assertTrue(actualSelectStatements.isEmpty());
    assertTrue(actualStatementMappings.isEmpty());
    assertTrue(actualUpdateStatements.isEmpty());
    assertTrue(actualIsDbHistoryUsedResult);
    assertTrue(actualIsTablePrefixIsSchemaResult);
    Class<DbSqlSession> expectedSessionType = DbSqlSession.class;
    assertEquals(expectedSessionType, actualSessionType);
    assertSame(bulkDeleteStatements, actualBulkDeleteStatements);
    assertSame(bulkInsertStatements, actualBulkInsertStatements);
    assertSame(deleteStatements, actualDeleteStatements);
    assertSame(insertStatements, actualInsertStatements);
    assertSame(selectStatements, actualSelectStatements);
    assertSame(statementMappings, actualStatementMappings);
    assertSame(updateStatements, actualUpdateStatements);
    assertSame(sqlSessionFactory, actualSqlSessionFactory);
    assertSame(idGenerator, actualIdGenerator);
  }
}
