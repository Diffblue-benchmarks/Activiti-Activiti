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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.impl.cfg.IdGenerator;
import org.activiti.engine.impl.persistence.entity.Entity;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class DbSqlSessionFactoryDiffblueTest {
  /**
   * Test {@link DbSqlSessionFactory#mapStatement(String)}.
   *
   * <ul>
   *   <li>Given {@link DbSqlSessionFactory} (default constructor) StatementMappings is {@link
   *       HashMap#HashMap()}.
   *   <li>Then return {@code MD}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSessionFactory#mapStatement(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String DbSqlSessionFactory.mapStatement(String)"})
  public void testMapStatement_givenDbSqlSessionFactoryStatementMappingsIsHashMap_thenReturnMd() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setStatementMappings(new HashMap<>());

    // Act and Assert
    assertEquals("MD", dbSqlSessionFactory.mapStatement("MD"));
  }

  /**
   * Test {@link DbSqlSessionFactory#mapStatement(String)}.
   *
   * <ul>
   *   <li>Given {@link DbSqlSessionFactory} (default constructor).
   *   <li>When {@code MD}.
   *   <li>Then return {@code MD}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSessionFactory#mapStatement(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String DbSqlSessionFactory.mapStatement(String)"})
  public void testMapStatement_givenDbSqlSessionFactory_whenMd_thenReturnMd() {
    // Arrange, Act and Assert
    assertEquals("MD", new DbSqlSessionFactory().mapStatement("MD"));
  }

  /**
   * Test {@link DbSqlSessionFactory#mapStatement(String)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 42} is {@code Value}.
   *   <li>When {@code 42}.
   *   <li>Then return {@code Value}.
   * </ul>
   *
   * <p>Method under test: {@link DbSqlSessionFactory#mapStatement(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String DbSqlSessionFactory.mapStatement(String)"})
  public void testMapStatement_givenHashMap42IsValue_when42_thenReturnValue() {
    // Arrange
    HashMap<String, String> statementMappings = new HashMap<>();
    statementMappings.put("42", "Value");

    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    dbSqlSessionFactory.setStatementMappings(statementMappings);

    // Act and Assert
    assertEquals("Value", dbSqlSessionFactory.mapStatement("42"));
  }

  /**
   * Test {@link DbSqlSessionFactory#isBulkInsertable(Class)}.
   *
   * <p>Method under test: {@link DbSqlSessionFactory#isBulkInsertable(Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.Boolean DbSqlSessionFactory.isBulkInsertable(Class)"})
  public void testIsBulkInsertable() {
    // Arrange
    DbSqlSessionFactory dbSqlSessionFactory = new DbSqlSessionFactory();
    Class<Entity> entityClass = Entity.class;

    // Act and Assert
    assertFalse(dbSqlSessionFactory.isBulkInsertable(entityClass));
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
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
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DbSqlSessionFactory.<init>()",
    "Map DbSqlSessionFactory.getBulkDeleteStatements()",
    "Map DbSqlSessionFactory.getBulkInsertStatements()",
    "String DbSqlSessionFactory.getDatabaseCatalog()",
    "String DbSqlSessionFactory.getDatabaseSchema()",
    "String DbSqlSessionFactory.getDatabaseTablePrefix()",
    "String DbSqlSessionFactory.getDatabaseType()",
    "Map DbSqlSessionFactory.getDeleteStatements()",
    "IdGenerator DbSqlSessionFactory.getIdGenerator()",
    "Map DbSqlSessionFactory.getInsertStatements()",
    "int DbSqlSessionFactory.getMaxNrOfStatementsInBulkInsert()",
    "Map DbSqlSessionFactory.getSelectStatements()",
    "Class DbSqlSessionFactory.getSessionType()",
    "SqlSessionFactory DbSqlSessionFactory.getSqlSessionFactory()",
    "Map DbSqlSessionFactory.getStatementMappings()",
    "Map DbSqlSessionFactory.getUpdateStatements()",
    "boolean DbSqlSessionFactory.isDbHistoryUsed()",
    "boolean DbSqlSessionFactory.isTablePrefixIsSchema()",
    "void DbSqlSessionFactory.setBulkDeleteStatements(Map)",
    "void DbSqlSessionFactory.setBulkInsertStatements(Map)",
    "void DbSqlSessionFactory.setDatabaseCatalog(String)",
    "void DbSqlSessionFactory.setDatabaseSchema(String)",
    "void DbSqlSessionFactory.setDatabaseTablePrefix(String)",
    "void DbSqlSessionFactory.setDbHistoryUsed(boolean)",
    "void DbSqlSessionFactory.setDeleteStatements(Map)",
    "void DbSqlSessionFactory.setIdGenerator(IdGenerator)",
    "void DbSqlSessionFactory.setInsertStatements(Map)",
    "void DbSqlSessionFactory.setMaxNrOfStatementsInBulkInsert(int)",
    "void DbSqlSessionFactory.setSelectStatements(Map)",
    "void DbSqlSessionFactory.setSqlSessionFactory(SqlSessionFactory)",
    "void DbSqlSessionFactory.setStatementMappings(Map)",
    "void DbSqlSessionFactory.setTablePrefixIsSchema(boolean)",
    "void DbSqlSessionFactory.setUpdateStatements(Map)"
  })
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
    Map<Class<?>, String> actualBulkDeleteStatements =
        actualDbSqlSessionFactory.getBulkDeleteStatements();
    Map<Class<?>, String> actualBulkInsertStatements =
        actualDbSqlSessionFactory.getBulkInsertStatements();
    String actualDatabaseCatalog = actualDbSqlSessionFactory.getDatabaseCatalog();
    String actualDatabaseSchema = actualDbSqlSessionFactory.getDatabaseSchema();
    String actualDatabaseTablePrefix = actualDbSqlSessionFactory.getDatabaseTablePrefix();
    String actualDatabaseType = actualDbSqlSessionFactory.getDatabaseType();
    Map<Class<?>, String> actualDeleteStatements = actualDbSqlSessionFactory.getDeleteStatements();
    IdGenerator actualIdGenerator = actualDbSqlSessionFactory.getIdGenerator();
    Map<Class<?>, String> actualInsertStatements = actualDbSqlSessionFactory.getInsertStatements();
    int actualMaxNrOfStatementsInBulkInsert =
        actualDbSqlSessionFactory.getMaxNrOfStatementsInBulkInsert();
    Map<Class<?>, String> actualSelectStatements = actualDbSqlSessionFactory.getSelectStatements();
    Class<?> actualSessionType = actualDbSqlSessionFactory.getSessionType();
    SqlSessionFactory actualSqlSessionFactory = actualDbSqlSessionFactory.getSqlSessionFactory();
    Map<String, String> actualStatementMappings = actualDbSqlSessionFactory.getStatementMappings();
    Map<Class<?>, String> actualUpdateStatements = actualDbSqlSessionFactory.getUpdateStatements();
    boolean actualIsDbHistoryUsedResult = actualDbSqlSessionFactory.isDbHistoryUsed();
    boolean actualIsTablePrefixIsSchemaResult = actualDbSqlSessionFactory.isTablePrefixIsSchema();

    // Assert
    assertEquals("Database Catalog", actualDatabaseCatalog);
    assertEquals("Database Schema", actualDatabaseSchema);
    assertEquals("Database Table Prefix", actualDatabaseTablePrefix);
    assertNull(actualDatabaseType);
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
