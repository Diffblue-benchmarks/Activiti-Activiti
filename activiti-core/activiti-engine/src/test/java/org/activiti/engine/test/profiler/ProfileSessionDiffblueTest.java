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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ProfileSessionDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ProfileSession#ProfileSession(String)}
   *   <li>{@link ProfileSession#setCommandExecutions(Map)}
   *   <li>{@link ProfileSession#setName(String)}
   *   <li>{@link ProfileSession#setStartTime(Date)}
   *   <li>{@link ProfileSession#setTotalTime(long)}
   *   <li>{@link ProfileSession#getCommandExecutions()}
   *   <li>{@link ProfileSession#getEndTime()}
   *   <li>{@link ProfileSession#getName()}
   *   <li>{@link ProfileSession#getStartTime()}
   *   <li>{@link ProfileSession#getTotalTime()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProfileSession.<init>(String)",
    "Map ProfileSession.getCommandExecutions()",
    "Date ProfileSession.getEndTime()",
    "String ProfileSession.getName()",
    "Date ProfileSession.getStartTime()",
    "long ProfileSession.getTotalTime()",
    "void ProfileSession.setCommandExecutions(Map)",
    "void ProfileSession.setName(String)",
    "void ProfileSession.setStartTime(Date)",
    "void ProfileSession.setTotalTime(long)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    ProfileSession actualProfileSession = new ProfileSession("Name");
    HashMap<String, List<CommandExecutionResult>> commandExecutionResults = new HashMap<>();
    actualProfileSession.setCommandExecutions(commandExecutionResults);
    actualProfileSession.setName("Name");
    Date startTime =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualProfileSession.setStartTime(startTime);
    actualProfileSession.setTotalTime(1L);
    Map<String, List<CommandExecutionResult>> actualCommandExecutions =
        actualProfileSession.getCommandExecutions();
    Date actualEndTime = actualProfileSession.getEndTime();
    String actualName = actualProfileSession.getName();
    Date actualStartTime = actualProfileSession.getStartTime();
    long actualTotalTime = actualProfileSession.getTotalTime();

    // Assert
    assertEquals("Name", actualName);
    assertNull(actualEndTime);
    assertNull(actualProfileSession.currentCommandExecution.get());
    assertEquals(1L, actualTotalTime);
    assertTrue(actualCommandExecutions.isEmpty());
    assertSame(commandExecutionResults, actualCommandExecutions);
    assertSame(startTime, actualStartTime);
  }

  /**
   * Test {@link ProfileSession#getCurrentCommandExecution()}.
   *
   * <p>Method under test: {@link ProfileSession#getCurrentCommandExecution()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"CommandExecutionResult ProfileSession.getCurrentCommandExecution()"})
  public void testGetCurrentCommandExecution() {
    // Arrange, Act and Assert
    assertNull(new ProfileSession("Name").getCurrentCommandExecution());
  }

  /**
   * Test {@link ProfileSession#addCommandExecution(String, CommandExecutionResult)}.
   *
   * <p>Method under test: {@link ProfileSession#addCommandExecution(String,
   * CommandExecutionResult)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProfileSession.addCommandExecution(String, CommandExecutionResult)"})
  public void testAddCommandExecution() {
    // Arrange
    ProfileSession profileSession = new ProfileSession("Name");
    CommandExecutionResult commandExecutionResult = new CommandExecutionResult();

    // Act
    profileSession.addCommandExecution("Class Fqn", commandExecutionResult);

    // Assert
    Map<String, List<CommandExecutionResult>> commandExecutions =
        profileSession.getCommandExecutions();
    assertEquals(1, commandExecutions.size());
    List<CommandExecutionResult> getResult = commandExecutions.get("Class Fqn");
    assertEquals(1, getResult.size());
    assertSame(commandExecutionResult, getResult.get(0));
  }

  /**
   * Test {@link ProfileSession#addCommandExecution(String, CommandExecutionResult)}.
   *
   * <p>Method under test: {@link ProfileSession#addCommandExecution(String,
   * CommandExecutionResult)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProfileSession.addCommandExecution(String, CommandExecutionResult)"})
  public void testAddCommandExecution2() {
    // Arrange
    ProfileSession profileSession = new ProfileSession("Name");
    CommandExecutionResult commandExecutionResult = new CommandExecutionResult();
    profileSession.addCommandExecution("Class Fqn", commandExecutionResult);
    CommandExecutionResult commandExecutionResult2 = new CommandExecutionResult();

    // Act
    profileSession.addCommandExecution("Class Fqn", commandExecutionResult2);

    // Assert
    Map<String, List<CommandExecutionResult>> commandExecutions =
        profileSession.getCommandExecutions();
    assertEquals(1, commandExecutions.size());
    List<CommandExecutionResult> getResult = commandExecutions.get("Class Fqn");
    assertEquals(2, getResult.size());
    assertSame(commandExecutionResult, getResult.get(0));
    assertSame(commandExecutionResult2, getResult.get(1));
  }

  /**
   * Test {@link ProfileSession#setEndTime(Date)}.
   *
   * <ul>
   *   <li>Given {@link ProfileSession#ProfileSession(String)} with {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link ProfileSession#setEndTime(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProfileSession.setEndTime(Date)"})
  public void testSetEndTime_givenProfileSessionWithName() {
    // Arrange
    ProfileSession profileSession = new ProfileSession("Name");
    Date endTimeStamp =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    profileSession.setEndTime(endTimeStamp);

    // Assert
    assertSame(endTimeStamp, profileSession.getEndTime());
  }

  /**
   * Test {@link ProfileSession#setEndTime(Date)}.
   *
   * <ul>
   *   <li>Given {@link ProfileSession#ProfileSession(String)} with {@code Name} StartTime is {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link ProfileSession#setEndTime(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProfileSession.setEndTime(Date)"})
  public void testSetEndTime_givenProfileSessionWithNameStartTimeIsNull() {
    // Arrange
    ProfileSession profileSession = new ProfileSession("Name");
    profileSession.setStartTime(null);
    Date endTimeStamp =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    profileSession.setEndTime(endTimeStamp);

    // Assert
    assertSame(endTimeStamp, profileSession.getEndTime());
  }

  /**
   * Test {@link ProfileSession#calculateSummaryStatistics()}.
   *
   * <ul>
   *   <li>Given {@link ProfileSession#ProfileSession(String)} with {@code Name}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ProfileSession#calculateSummaryStatistics()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ProfileSession.calculateSummaryStatistics()"})
  public void testCalculateSummaryStatistics_givenProfileSessionWithName_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(new ProfileSession("Name").calculateSummaryStatistics().isEmpty());
  }

  /**
   * Test {@link ProfileSession#calculateSummaryStatistics()}.
   *
   * <ul>
   *   <li>Then return {@code Class Fqn} DbSelects Empty.
   * </ul>
   *
   * <p>Method under test: {@link ProfileSession#calculateSummaryStatistics()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ProfileSession.calculateSummaryStatistics()"})
  public void testCalculateSummaryStatistics_thenReturnClassFqnDbSelectsEmpty() {
    // Arrange
    ProfileSession profileSession = new ProfileSession("Name");
    profileSession.addCommandExecution("Class Fqn", new CommandExecutionResult());

    // Act
    Map<String, CommandStats> actualCalculateSummaryStatisticsResult =
        profileSession.calculateSummaryStatistics();

    // Assert
    assertEquals(1, actualCalculateSummaryStatisticsResult.size());
    CommandStats getResult = actualCalculateSummaryStatisticsResult.get("Class Fqn");
    assertEquals(0.0d, getResult.getAverageDatabaseExecutionTime(), 0.0);
    assertEquals(0.0d, getResult.getAverageDatabaseExecutionTimePercentage(), 0.0);
    assertEquals(0.0d, getResult.getAverageExecutionTime(), 0.0);
    assertEquals(0L, getResult.getGetTotalCommandTime());
    List<Long> resultLongList = getResult.commandExecutionTimings;
    assertEquals(1, resultLongList.size());
    assertEquals(1L, getResult.getCount());
    assertTrue(getResult.getDbDeletes().isEmpty());
    assertTrue(getResult.getDbInserts().isEmpty());
    assertTrue(getResult.getDbSelects().isEmpty());
    assertTrue(getResult.getDbUpdates().isEmpty());
    assertEquals(resultLongList, getResult.databaseTimings);
  }

  /**
   * Test {@link ProfileSession#calculateSummaryStatistics()}.
   *
   * <ul>
   *   <li>Then return {@code Class Fqn} DbSelects size is one.
   * </ul>
   *
   * <p>Method under test: {@link ProfileSession#calculateSummaryStatistics()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ProfileSession.calculateSummaryStatistics()"})
  public void testCalculateSummaryStatistics_thenReturnClassFqnDbSelectsSizeIsOne() {
    // Arrange
    CommandExecutionResult commandExecutionResult = new CommandExecutionResult();
    commandExecutionResult.addDbSelect("Select");

    ProfileSession profileSession = new ProfileSession("Name");
    profileSession.addCommandExecution("Class Fqn", commandExecutionResult);

    // Act
    Map<String, CommandStats> actualCalculateSummaryStatisticsResult =
        profileSession.calculateSummaryStatistics();

    // Assert
    assertEquals(1, actualCalculateSummaryStatisticsResult.size());
    CommandStats getResult = actualCalculateSummaryStatisticsResult.get("Class Fqn");
    assertEquals(0.0d, getResult.getAverageDatabaseExecutionTime(), 0.0);
    assertEquals(0.0d, getResult.getAverageDatabaseExecutionTimePercentage(), 0.0);
    assertEquals(0.0d, getResult.getAverageExecutionTime(), 0.0);
    assertEquals(0L, getResult.getGetTotalCommandTime());
    List<Long> resultLongList = getResult.commandExecutionTimings;
    assertEquals(1, resultLongList.size());
    Map<String, Long> dbSelects = getResult.getDbSelects();
    assertEquals(1, dbSelects.size());
    assertEquals(1L, getResult.getCount());
    assertTrue(dbSelects.containsKey("Select"));
    assertTrue(getResult.getDbDeletes().isEmpty());
    assertTrue(getResult.getDbInserts().isEmpty());
    assertTrue(getResult.getDbUpdates().isEmpty());
    assertEquals(resultLongList, getResult.databaseTimings);
  }
}
