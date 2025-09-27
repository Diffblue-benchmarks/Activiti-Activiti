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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class CommandStatsDiffblueTest {
  /**
   * Test {@link CommandStats#CommandStats(List)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code foo} is one.
   *   <li>Then return DbSelects size is one.
   * </ul>
   *
   * <p>Method under test: {@link CommandStats#CommandStats(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CommandStats.<init>(List)"})
  public void testNewCommandStats_givenHashMapFooIsOne_thenReturnDbSelectsSizeIsOne() {
    // Arrange
    HashMap<String, Long> dbSelects = new HashMap<>();
    dbSelects.put("foo", 1L);

    CommandExecutionResult commandExecutionResult = new CommandExecutionResult();
    commandExecutionResult.setDbSelects(dbSelects);
    commandExecutionResult.setDbInserts(new HashMap<>());
    commandExecutionResult.setDbUpdates(new HashMap<>());
    commandExecutionResult.setDbDeletes(new HashMap<>());

    ArrayList<CommandExecutionResult> executions = new ArrayList<>();
    executions.add(commandExecutionResult);

    // Act
    CommandStats actualCommandStats = new CommandStats(executions);

    // Assert
    assertEquals(1, actualCommandStats.commandExecutionTimings.size());
    assertEquals(1, actualCommandStats.databaseTimings.size());
    Map<String, Long> dbSelects2 = actualCommandStats.getDbSelects();
    assertEquals(1, dbSelects2.size());
    assertEquals(1L, dbSelects2.get("foo").longValue());
    assertEquals(1L, actualCommandStats.getCount());
  }

  /**
   * Test {@link CommandStats#CommandStats(List)}.
   *
   * <ul>
   *   <li>Then return {@link CommandStats#commandExecutionTimings} first longValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link CommandStats#CommandStats(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CommandStats.<init>(List)"})
  public void testNewCommandStats_thenReturnCommandExecutionTimingsFirstLongValueIsZero() {
    // Arrange
    CommandExecutionResult commandExecutionResult = new CommandExecutionResult();
    commandExecutionResult.setDbSelects(new HashMap<>());
    commandExecutionResult.setDbInserts(new HashMap<>());
    commandExecutionResult.setDbUpdates(new HashMap<>());
    commandExecutionResult.setDbDeletes(new HashMap<>());

    ArrayList<CommandExecutionResult> executions = new ArrayList<>();
    executions.add(commandExecutionResult);

    // Act
    CommandStats actualCommandStats = new CommandStats(executions);

    // Assert
    List<Long> resultLongList = actualCommandStats.commandExecutionTimings;
    assertEquals(1, resultLongList.size());
    assertEquals(0L, resultLongList.get(0).longValue());
    List<Long> resultLongList2 = actualCommandStats.databaseTimings;
    assertEquals(1, resultLongList2.size());
    assertEquals(0L, resultLongList2.get(0).longValue());
    assertEquals(1L, actualCommandStats.getCount());
    assertTrue(actualCommandStats.getDbSelects().isEmpty());
  }

  /**
   * Test {@link CommandStats#CommandStats(List)}.
   *
   * <ul>
   *   <li>Then return {@link CommandStats#commandExecutionTimings} size is two.
   * </ul>
   *
   * <p>Method under test: {@link CommandStats#CommandStats(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CommandStats.<init>(List)"})
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
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return Count is zero.
   * </ul>
   *
   * <p>Method under test: {@link CommandStats#CommandStats(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CommandStats.<init>(List)"})
  public void testNewCommandStats_whenArrayList_thenReturnCountIsZero() {
    // Arrange and Act
    CommandStats actualCommandStats = new CommandStats(new ArrayList<>());

    // Assert
    assertEquals(0L, actualCommandStats.getCount());
    assertTrue(actualCommandStats.commandExecutionTimings.isEmpty());
    assertTrue(actualCommandStats.databaseTimings.isEmpty());
    assertTrue(actualCommandStats.getDbSelects().isEmpty());
  }

  /**
   * Test {@link CommandStats#getCount()}.
   *
   * <p>Method under test: {@link CommandStats#getCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long CommandStats.getCount()"})
  public void testGetCount() {
    // Arrange, Act and Assert
    assertEquals(0L, new CommandStats(new ArrayList<>()).getCount());
  }

  /**
   * Test {@link CommandStats#getAverageExecutionTime()}.
   *
   * <p>Method under test: {@link CommandStats#getAverageExecutionTime()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double CommandStats.getAverageExecutionTime()"})
  public void testGetAverageExecutionTime() {
    // Arrange, Act and Assert
    assertEquals(0.0d, new CommandStats(new ArrayList<>()).getAverageExecutionTime(), 0.0);
  }

  /**
   * Test {@link CommandStats#getAverageExecutionTime()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link CommandExecutionResult} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link CommandStats#getAverageExecutionTime()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double CommandStats.getAverageExecutionTime()"})
  public void testGetAverageExecutionTime_givenArrayListAddCommandExecutionResult() {
    // Arrange
    ArrayList<CommandExecutionResult> executions = new ArrayList<>();
    executions.add(new CommandExecutionResult());

    // Act and Assert
    assertEquals(0.0d, new CommandStats(executions).getAverageExecutionTime(), 0.0);
  }

  /**
   * Test {@link CommandStats#getAverageDatabaseExecutionTimePercentage()}.
   *
   * <p>Method under test: {@link CommandStats#getAverageDatabaseExecutionTimePercentage()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double CommandStats.getAverageDatabaseExecutionTimePercentage()"})
  public void testGetAverageDatabaseExecutionTimePercentage() {
    // Arrange, Act and Assert
    assertEquals(
        0.0d, new CommandStats(new ArrayList<>()).getAverageDatabaseExecutionTimePercentage(), 0.0);
  }

  /**
   * Test {@link CommandStats#getAverageDatabaseExecutionTimePercentage()}.
   *
   * <p>Method under test: {@link CommandStats#getAverageDatabaseExecutionTimePercentage()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double CommandStats.getAverageDatabaseExecutionTimePercentage()"})
  public void testGetAverageDatabaseExecutionTimePercentage2() {
    // Arrange
    ArrayList<CommandExecutionResult> executions = new ArrayList<>();
    executions.add(new CommandExecutionResult());

    // Act and Assert
    assertEquals(
        0.0d, new CommandStats(executions).getAverageDatabaseExecutionTimePercentage(), 0.0);
  }

  /**
   * Test {@link CommandStats#getAverageDatabaseExecutionTime()}.
   *
   * <p>Method under test: {@link CommandStats#getAverageDatabaseExecutionTime()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double CommandStats.getAverageDatabaseExecutionTime()"})
  public void testGetAverageDatabaseExecutionTime() {
    // Arrange, Act and Assert
    assertEquals(0.0d, new CommandStats(new ArrayList<>()).getAverageDatabaseExecutionTime(), 0.0);
  }

  /**
   * Test {@link CommandStats#getAverageDatabaseExecutionTime()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link CommandExecutionResult} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link CommandStats#getAverageDatabaseExecutionTime()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double CommandStats.getAverageDatabaseExecutionTime()"})
  public void testGetAverageDatabaseExecutionTime_givenArrayListAddCommandExecutionResult() {
    // Arrange
    ArrayList<CommandExecutionResult> executions = new ArrayList<>();
    executions.add(new CommandExecutionResult());

    // Act and Assert
    assertEquals(0.0d, new CommandStats(executions).getAverageDatabaseExecutionTime(), 0.0);
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
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
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map CommandStats.getDbDeletes()",
    "Map CommandStats.getDbInserts()",
    "Map CommandStats.getDbSelects()",
    "Map CommandStats.getDbUpdates()",
    "long CommandStats.getGetTotalCommandTime()",
    "void CommandStats.setDbDeletes(Map)",
    "void CommandStats.setDbInserts(Map)",
    "void CommandStats.setDbSelects(Map)",
    "void CommandStats.setDbUpdates(Map)"
  })
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

    // Assert
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
