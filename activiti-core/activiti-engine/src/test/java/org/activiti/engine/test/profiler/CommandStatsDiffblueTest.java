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
   * Test {@link CommandStats#CommandStats(List)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandStats#CommandStats(List)}
   */
  @Test
  public void testNewCommandStats_givenHashMapComputeIfPresentFooAndBiFunction() {
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
    assertEquals(1, actualCommandStats.commandExecutionTimings.size());
    assertEquals(1, actualCommandStats.databaseTimings.size());
    Map<String, Long> dbInserts2 = actualCommandStats.getDbInserts();
    assertEquals(1, dbInserts2.size());
    assertEquals(1L, dbInserts2.get("foo").longValue());
    assertEquals(1L, actualCommandStats.getCount());
  }

  /**
   * Test {@link CommandStats#CommandStats(List)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code foo} is one.</li>
   *   <li>Then return DbInserts size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandStats#CommandStats(List)}
   */
  @Test
  public void testNewCommandStats_givenHashMapFooIsOne_thenReturnDbInsertsSizeIsOne() {
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
    assertEquals(1, actualCommandStats.commandExecutionTimings.size());
    assertEquals(1, actualCommandStats.databaseTimings.size());
    Map<String, Long> dbInserts2 = actualCommandStats.getDbInserts();
    assertEquals(1, dbInserts2.size());
    assertEquals(1L, dbInserts2.get("foo").longValue());
    assertEquals(1L, actualCommandStats.getCount());
  }

  /**
   * Test {@link CommandStats#CommandStats(List)}.
   * <ul>
   *   <li>Then return {@link CommandStats#commandExecutionTimings} size is
   * two.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandStats#CommandStats(List)}
   */
  @Test
  public void testNewCommandStats_thenReturnCommandExecutionTimingsSizeIsTwo() {
    // Arrange
    ArrayList<CommandExecutionResult> executions = new ArrayList<>();
    executions.add(new CommandExecutionResult());
    executions.add(new CommandExecutionResult());

    // Act
    CommandStats actualCommandStats = new CommandStats(executions);

    // Assert
    List<Long> resultLongList = actualCommandStats.commandExecutionTimings;
    assertEquals(2, resultLongList.size());
    assertEquals(0L, resultLongList.get(1).longValue());
    List<Long> resultLongList2 = actualCommandStats.databaseTimings;
    assertEquals(2, resultLongList2.size());
    assertEquals(0L, resultLongList2.get(1).longValue());
    assertEquals(2L, actualCommandStats.getCount());
  }

  /**
   * Test {@link CommandStats#CommandStats(List)}.
   * <ul>
   *   <li>Then return DbInserts Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandStats#CommandStats(List)}
   */
  @Test
  public void testNewCommandStats_thenReturnDbInsertsEmpty() {
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
    assertEquals(1, actualCommandStats.commandExecutionTimings.size());
    assertEquals(1, actualCommandStats.databaseTimings.size());
    assertEquals(1L, actualCommandStats.getCount());
    assertTrue(actualCommandStats.getDbInserts().isEmpty());
  }

  /**
   * Test {@link CommandStats#CommandStats(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Count is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandStats#CommandStats(List)}
   */
  @Test
  public void testNewCommandStats_whenArrayList_thenReturnCountIsZero() {
    // Arrange and Act
    CommandStats actualCommandStats = new CommandStats(new ArrayList<>());

    // Assert
    assertEquals(0L, actualCommandStats.getCount());
    assertTrue(actualCommandStats.commandExecutionTimings.isEmpty());
    assertTrue(actualCommandStats.databaseTimings.isEmpty());
    assertTrue(actualCommandStats.getDbInserts().isEmpty());
  }

  /**
   * Test {@link CommandStats#getCount()}.
   * <p>
   * Method under test: {@link CommandStats#getCount()}
   */
  @Test
  public void testGetCount() {
    // Arrange, Act and Assert
    assertEquals(0L, (new CommandStats(new ArrayList<>())).getCount());
  }

  /**
   * Test {@link CommandStats#getAverageExecutionTime()}.
   * <p>
   * Method under test: {@link CommandStats#getAverageExecutionTime()}
   */
  @Test
  public void testGetAverageExecutionTime() {
    // Arrange, Act and Assert
    assertEquals(0.0d, (new CommandStats(new ArrayList<>())).getAverageExecutionTime(), 0.0);
  }

  /**
   * Test {@link CommandStats#getAverageExecutionTime()}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link CommandExecutionResult}
   * (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandStats#getAverageExecutionTime()}
   */
  @Test
  public void testGetAverageExecutionTime_givenArrayListAddCommandExecutionResult() {
    // Arrange
    ArrayList<CommandExecutionResult> executions = new ArrayList<>();
    executions.add(new CommandExecutionResult());

    // Act and Assert
    assertEquals(0.0d, (new CommandStats(executions)).getAverageExecutionTime(), 0.0);
  }

  /**
   * Test {@link CommandStats#getAverageDatabaseExecutionTimePercentage()}.
   * <p>
   * Method under test:
   * {@link CommandStats#getAverageDatabaseExecutionTimePercentage()}
   */
  @Test
  public void testGetAverageDatabaseExecutionTimePercentage() {
    // Arrange, Act and Assert
    assertEquals(0.0d, (new CommandStats(new ArrayList<>())).getAverageDatabaseExecutionTimePercentage(), 0.0);
  }

  /**
   * Test {@link CommandStats#getAverageDatabaseExecutionTimePercentage()}.
   * <p>
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
   * Test {@link CommandStats#getAverageDatabaseExecutionTime()}.
   * <p>
   * Method under test: {@link CommandStats#getAverageDatabaseExecutionTime()}
   */
  @Test
  public void testGetAverageDatabaseExecutionTime() {
    // Arrange, Act and Assert
    assertEquals(0.0d, (new CommandStats(new ArrayList<>())).getAverageDatabaseExecutionTime(), 0.0);
  }

  /**
   * Test {@link CommandStats#getAverageDatabaseExecutionTime()}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link CommandExecutionResult}
   * (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandStats#getAverageDatabaseExecutionTime()}
   */
  @Test
  public void testGetAverageDatabaseExecutionTime_givenArrayListAddCommandExecutionResult() {
    // Arrange
    ArrayList<CommandExecutionResult> executions = new ArrayList<>();
    executions.add(new CommandExecutionResult());

    // Act and Assert
    assertEquals(0.0d, (new CommandStats(executions)).getAverageDatabaseExecutionTime(), 0.0);
  }

  /**
   * Test getters and setters.
   * <p>
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
}
