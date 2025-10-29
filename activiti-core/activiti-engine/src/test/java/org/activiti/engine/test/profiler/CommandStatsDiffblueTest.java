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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.Test;

public class CommandStatsDiffblueTest {
  /**
   * Method under test: {@link CommandStats#addToDbOperation(Map, Map)}
   */
  @Test
  public void testAddToDbOperation() {
    // Arrange
    CommandStats commandStats = new CommandStats(new ArrayList<>());
    HashMap<String, Long> executionMap = new HashMap<>();

    // Act
    commandStats.addToDbOperation(executionMap, new HashMap<>());

    // Assert that nothing has changed
    assertTrue(commandStats.getDbInserts().isEmpty());
    assertTrue(commandStats.getDbSelects().isEmpty());
    assertTrue(commandStats.getDbUpdates().isEmpty());
  }

  /**
   * Method under test: {@link CommandStats#addToDbOperation(Map, Map)}
   */
  @Test
  public void testAddToDbOperation2() {
    // Arrange
    CommandStats commandStats = new CommandStats(new ArrayList<>());

    HashMap<String, Long> executionMap = new HashMap<>();
    executionMap.put("foo", 1L);

    // Act
    commandStats.addToDbOperation(executionMap, new HashMap<>());

    // Assert
    assertTrue(commandStats.getDbInserts().isEmpty());
    assertTrue(commandStats.getDbSelects().isEmpty());
    assertTrue(commandStats.getDbUpdates().isEmpty());
  }

  /**
   * Method under test: {@link CommandStats#addToDbOperation(Map, Map)}
   */
  @Test
  public void testAddToDbOperation3() {
    // Arrange
    CommandStats commandStats = new CommandStats(new ArrayList<>());

    HashMap<String, Long> executionMap = new HashMap<>();
    executionMap.computeIfPresent("foo", mock(BiFunction.class));
    executionMap.put("foo", 1L);

    // Act
    commandStats.addToDbOperation(executionMap, new HashMap<>());

    // Assert
    assertTrue(commandStats.getDbInserts().isEmpty());
    assertTrue(commandStats.getDbSelects().isEmpty());
    assertTrue(commandStats.getDbUpdates().isEmpty());
  }

  /**
   * Method under test: {@link CommandStats#addToDbOperation(Map, Map)}
   */
  @Test
  public void testAddToDbOperation4() {
    // Arrange
    CommandStats commandStats = new CommandStats(new ArrayList<>());

    HashMap<String, Long> executionMap = new HashMap<>();
    executionMap.put("foo", 1L);

    HashMap<String, Long> globalMap = new HashMap<>();
    globalMap.put("foo", 0L);

    // Act
    commandStats.addToDbOperation(executionMap, globalMap);

    // Assert
    assertTrue(commandStats.getDbInserts().isEmpty());
    assertTrue(commandStats.getDbSelects().isEmpty());
    assertTrue(commandStats.getDbUpdates().isEmpty());
  }

  /**
   * Method under test: {@link CommandStats#getCount()}
   */
  @Test
  public void testGetCount() {
    // Arrange, Act and Assert
    assertEquals(0L, (new CommandStats(new ArrayList<>())).getCount());
  }

  /**
   * Method under test: {@link CommandStats#getAverageExecutionTime()}
   */
  @Test
  public void testGetAverageExecutionTime() {
    // Arrange, Act and Assert
    assertEquals(0.0d, (new CommandStats(new ArrayList<>())).getAverageExecutionTime(), 0.0);
  }

  /**
   * Method under test: {@link CommandStats#getAverageExecutionTime()}
   */
  @Test
  public void testGetAverageExecutionTime2() {
    // Arrange
    ArrayList<CommandExecutionResult> executions = new ArrayList<>();
    executions.add(new CommandExecutionResult());

    // Act and Assert
    assertEquals(0.0d, (new CommandStats(executions)).getAverageExecutionTime(), 0.0);
  }

  /**
   * Method under test:
   * {@link CommandStats#getAverageDatabaseExecutionTimePercentage()}
   */
  @Test
  public void testGetAverageDatabaseExecutionTimePercentage() {
    // Arrange, Act and Assert
    assertEquals(0.0d, (new CommandStats(new ArrayList<>())).getAverageDatabaseExecutionTimePercentage(), 0.0);
  }

  /**
   * Method under test:
   * {@link CommandStats#getAverageDatabaseExecutionTimePercentage()}
   */
  @Test
  public void testGetAverageDatabaseExecutionTimePercentage2() {
    // Arrange
    ArrayList<CommandExecutionResult> executions = new ArrayList<>();
    executions.add(new CommandExecutionResult());

    // Act and Assert
    assertEquals(0.0d, (new CommandStats(executions)).getAverageDatabaseExecutionTimePercentage(), 0.0);
  }

  /**
   * Method under test: {@link CommandStats#getAverageDatabaseExecutionTime()}
   */
  @Test
  public void testGetAverageDatabaseExecutionTime() {
    // Arrange, Act and Assert
    assertEquals(0.0d, (new CommandStats(new ArrayList<>())).getAverageDatabaseExecutionTime(), 0.0);
  }

  /**
   * Method under test: {@link CommandStats#getAverageDatabaseExecutionTime()}
   */
  @Test
  public void testGetAverageDatabaseExecutionTime2() {
    // Arrange
    ArrayList<CommandExecutionResult> executions = new ArrayList<>();
    executions.add(new CommandExecutionResult());

    // Act and Assert
    assertEquals(0.0d, (new CommandStats(executions)).getAverageDatabaseExecutionTime(), 0.0);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CommandStats#setDbDeletes(Map)}
   *   <li>{@link CommandStats#setDbInserts(Map)}
   *   <li>{@link CommandStats#setDbSelects(Map)}
   *   <li>{@link CommandStats#setDbUpdates(Map)}
   *   <li>{@link CommandStats#getDbDeletes()}
   *   <li>{@link CommandStats#getDbInserts()}
   *   <li>{@link CommandStats#getDbSelects()}
   *   <li>{@link CommandStats#getDbUpdates()}
   *   <li>{@link CommandStats#getGetTotalCommandTime()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    CommandStats commandStats = new CommandStats(new ArrayList<>());
    HashMap<String, Long> dbDeletes = new HashMap<>();

    // Act
    commandStats.setDbDeletes(dbDeletes);
    HashMap<String, Long> dbInserts = new HashMap<>();
    commandStats.setDbInserts(dbInserts);
    HashMap<String, Long> dbSelects = new HashMap<>();
    commandStats.setDbSelects(dbSelects);
    HashMap<String, Long> dbUpdates = new HashMap<>();
    commandStats.setDbUpdates(dbUpdates);
    Map<String, Long> actualDbDeletes = commandStats.getDbDeletes();
    Map<String, Long> actualDbInserts = commandStats.getDbInserts();
    Map<String, Long> actualDbSelects = commandStats.getDbSelects();
    Map<String, Long> actualDbUpdates = commandStats.getDbUpdates();

    // Assert that nothing has changed
    assertEquals(0L, commandStats.getGetTotalCommandTime());
    assertTrue(actualDbDeletes.isEmpty());
    assertTrue(actualDbInserts.isEmpty());
    assertTrue(actualDbSelects.isEmpty());
    assertTrue(actualDbUpdates.isEmpty());
    assertSame(dbDeletes, actualDbDeletes);
    assertSame(dbInserts, actualDbInserts);
    assertSame(dbSelects, actualDbSelects);
    assertSame(dbUpdates, actualDbUpdates);
  }

  /**
   * Method under test: {@link CommandStats#CommandStats(List)}
   */
  @Test
  public void testNewCommandStats() {
    // Arrange and Act
    CommandStats actualCommandStats = new CommandStats(new ArrayList<>());

    // Assert
    assertEquals(0.0d, actualCommandStats.getAverageDatabaseExecutionTime(), 0.0);
    assertEquals(0.0d, actualCommandStats.getAverageDatabaseExecutionTimePercentage(), 0.0);
    assertEquals(0.0d, actualCommandStats.getAverageExecutionTime(), 0.0);
    assertEquals(0L, actualCommandStats.getCount());
    assertEquals(0L, actualCommandStats.getGetTotalCommandTime());
    assertTrue(actualCommandStats.commandExecutionTimings.isEmpty());
    assertTrue(actualCommandStats.databaseTimings.isEmpty());
    assertTrue(actualCommandStats.getDbDeletes().isEmpty());
    assertTrue(actualCommandStats.getDbInserts().isEmpty());
    assertTrue(actualCommandStats.getDbSelects().isEmpty());
    assertTrue(actualCommandStats.getDbUpdates().isEmpty());
  }

  /**
   * Method under test: {@link CommandStats#CommandStats(List)}
   */
  @Test
  public void testNewCommandStats2() {
    // Arrange
    CommandExecutionResult commandExecutionResult = new CommandExecutionResult();
    commandExecutionResult.setDbInserts(new HashMap<>());
    commandExecutionResult.setDbSelects(new HashMap<>());
    commandExecutionResult.setDbUpdates(new HashMap<>());
    commandExecutionResult.setDbDeletes(new HashMap<>());

    ArrayList<CommandExecutionResult> executions = new ArrayList<>();
    executions.add(commandExecutionResult);

    // Act
    CommandStats actualCommandStats = new CommandStats(executions);

    // Assert
    assertEquals(0.0d, actualCommandStats.getAverageDatabaseExecutionTime(), 0.0);
    assertEquals(0.0d, actualCommandStats.getAverageDatabaseExecutionTimePercentage(), 0.0);
    assertEquals(0.0d, actualCommandStats.getAverageExecutionTime(), 0.0);
    List<Long> resultLongList = actualCommandStats.commandExecutionTimings;
    assertEquals(1, resultLongList.size());
    assertEquals(0L, resultLongList.get(0).longValue());
    List<Long> resultLongList2 = actualCommandStats.databaseTimings;
    assertEquals(1, resultLongList2.size());
    assertEquals(0L, resultLongList2.get(0).longValue());
    assertEquals(0L, actualCommandStats.getGetTotalCommandTime());
    assertEquals(1L, actualCommandStats.getCount());
    assertTrue(actualCommandStats.getDbDeletes().isEmpty());
    assertTrue(actualCommandStats.getDbInserts().isEmpty());
    assertTrue(actualCommandStats.getDbSelects().isEmpty());
    assertTrue(actualCommandStats.getDbUpdates().isEmpty());
  }

  /**
   * Method under test: {@link CommandStats#CommandStats(List)}
   */
  @Test
  public void testNewCommandStats3() {
    // Arrange
    HashMap<String, Long> dbInserts = new HashMap<>();
    dbInserts.put("foo", 1L);

    CommandExecutionResult commandExecutionResult = new CommandExecutionResult();
    commandExecutionResult.setDbInserts(dbInserts);
    commandExecutionResult.setDbSelects(new HashMap<>());
    commandExecutionResult.setDbUpdates(new HashMap<>());
    commandExecutionResult.setDbDeletes(new HashMap<>());

    ArrayList<CommandExecutionResult> executions = new ArrayList<>();
    executions.add(commandExecutionResult);

    // Act
    CommandStats actualCommandStats = new CommandStats(executions);

    // Assert
    assertEquals(0.0d, actualCommandStats.getAverageDatabaseExecutionTime(), 0.0);
    assertEquals(0.0d, actualCommandStats.getAverageDatabaseExecutionTimePercentage(), 0.0);
    assertEquals(0.0d, actualCommandStats.getAverageExecutionTime(), 0.0);
    List<Long> resultLongList = actualCommandStats.commandExecutionTimings;
    assertEquals(1, resultLongList.size());
    assertEquals(0L, resultLongList.get(0).longValue());
    List<Long> resultLongList2 = actualCommandStats.databaseTimings;
    assertEquals(1, resultLongList2.size());
    assertEquals(0L, resultLongList2.get(0).longValue());
    assertEquals(0L, actualCommandStats.getGetTotalCommandTime());
    Map<String, Long> dbInserts2 = actualCommandStats.getDbInserts();
    assertEquals(1, dbInserts2.size());
    assertEquals(1L, dbInserts2.get("foo").longValue());
    assertEquals(1L, actualCommandStats.getCount());
    assertTrue(actualCommandStats.getDbDeletes().isEmpty());
    assertTrue(actualCommandStats.getDbSelects().isEmpty());
    assertTrue(actualCommandStats.getDbUpdates().isEmpty());
  }

  /**
   * Method under test: {@link CommandStats#CommandStats(List)}
   */
  @Test
  public void testNewCommandStats4() {
    // Arrange
    HashMap<String, Long> dbInserts = new HashMap<>();
    dbInserts.computeIfPresent("foo", mock(BiFunction.class));
    dbInserts.put("foo", 1L);

    CommandExecutionResult commandExecutionResult = new CommandExecutionResult();
    commandExecutionResult.setDbInserts(dbInserts);
    commandExecutionResult.setDbSelects(new HashMap<>());
    commandExecutionResult.setDbUpdates(new HashMap<>());
    commandExecutionResult.setDbDeletes(new HashMap<>());

    ArrayList<CommandExecutionResult> executions = new ArrayList<>();
    executions.add(commandExecutionResult);

    // Act
    CommandStats actualCommandStats = new CommandStats(executions);

    // Assert
    assertEquals(0.0d, actualCommandStats.getAverageDatabaseExecutionTime(), 0.0);
    assertEquals(0.0d, actualCommandStats.getAverageDatabaseExecutionTimePercentage(), 0.0);
    assertEquals(0.0d, actualCommandStats.getAverageExecutionTime(), 0.0);
    List<Long> resultLongList = actualCommandStats.commandExecutionTimings;
    assertEquals(1, resultLongList.size());
    assertEquals(0L, resultLongList.get(0).longValue());
    List<Long> resultLongList2 = actualCommandStats.databaseTimings;
    assertEquals(1, resultLongList2.size());
    assertEquals(0L, resultLongList2.get(0).longValue());
    assertEquals(0L, actualCommandStats.getGetTotalCommandTime());
    Map<String, Long> dbInserts2 = actualCommandStats.getDbInserts();
    assertEquals(1, dbInserts2.size());
    assertEquals(1L, dbInserts2.get("foo").longValue());
    assertEquals(1L, actualCommandStats.getCount());
    assertTrue(actualCommandStats.getDbDeletes().isEmpty());
    assertTrue(actualCommandStats.getDbSelects().isEmpty());
    assertTrue(actualCommandStats.getDbUpdates().isEmpty());
  }

  /**
   * Method under test: {@link CommandStats#CommandStats(List)}
   */
  @Test
  public void testNewCommandStats5() {
    // Arrange
    ArrayList<CommandExecutionResult> executions = new ArrayList<>();
    executions.add(new CommandExecutionResult());
    executions.add(new CommandExecutionResult());

    // Act
    CommandStats actualCommandStats = new CommandStats(executions);

    // Assert
    assertEquals(0.0d, actualCommandStats.getAverageDatabaseExecutionTime(), 0.0);
    assertEquals(0.0d, actualCommandStats.getAverageDatabaseExecutionTimePercentage(), 0.0);
    assertEquals(0.0d, actualCommandStats.getAverageExecutionTime(), 0.0);
    List<Long> resultLongList = actualCommandStats.commandExecutionTimings;
    assertEquals(2, resultLongList.size());
    assertEquals(0L, resultLongList.get(0).longValue());
    assertEquals(0L, resultLongList.get(1).longValue());
    assertEquals(0L, actualCommandStats.getGetTotalCommandTime());
    assertEquals(2L, actualCommandStats.getCount());
    assertTrue(actualCommandStats.getDbDeletes().isEmpty());
    assertTrue(actualCommandStats.getDbInserts().isEmpty());
    assertTrue(actualCommandStats.getDbSelects().isEmpty());
    assertTrue(actualCommandStats.getDbUpdates().isEmpty());
    assertEquals(actualCommandStats.commandExecutionTimings, actualCommandStats.databaseTimings);
  }
}
