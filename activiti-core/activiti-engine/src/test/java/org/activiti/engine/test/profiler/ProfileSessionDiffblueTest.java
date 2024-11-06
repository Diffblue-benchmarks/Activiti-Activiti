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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProfileSessionDiffblueTest {
  @InjectMocks
  private ProfileSession profileSession;

  @InjectMocks
  private String string;

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
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
  public void testGettersAndSetters() {
    // Arrange and Act
    ProfileSession actualProfileSession = new ProfileSession("Name");
    HashMap<String, List<CommandExecutionResult>> commandExecutionResults = new HashMap<>();
    actualProfileSession.setCommandExecutions(commandExecutionResults);
    actualProfileSession.setName("Name");
    Date startTime = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualProfileSession.setStartTime(startTime);
    actualProfileSession.setTotalTime(1L);
    Map<String, List<CommandExecutionResult>> actualCommandExecutions = actualProfileSession.getCommandExecutions();
    actualProfileSession.getEndTime();
    String actualName = actualProfileSession.getName();
    Date actualStartTime = actualProfileSession.getStartTime();

    // Assert that nothing has changed
    assertEquals("Name", actualName);
    assertEquals(1L, actualProfileSession.getTotalTime());
    assertTrue(actualCommandExecutions.isEmpty());
    assertSame(commandExecutionResults, actualCommandExecutions);
    assertSame(startTime, actualStartTime);
  }

  /**
   * Test {@link ProfileSession#getCurrentCommandExecution()}.
   * <p>
   * Method under test: {@link ProfileSession#getCurrentCommandExecution()}
   */
  @Test
  public void testGetCurrentCommandExecution() {
    // Arrange, Act and Assert
    assertNull((new ProfileSession("Name")).getCurrentCommandExecution());
  }

  /**
   * Test
   * {@link ProfileSession#addCommandExecution(String, CommandExecutionResult)}.
   * <ul>
   *   <li>When {@link CommandExecutionResult} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProfileSession#addCommandExecution(String, CommandExecutionResult)}
   */
  @Test
  public void testAddCommandExecution_whenCommandExecutionResult() {
    // Arrange and Act
    profileSession.addCommandExecution("Class Fqn", new CommandExecutionResult());

    // Assert
    assertEquals(1, profileSession.getCommandExecutions().size());
  }

  /**
   * Test
   * {@link ProfileSession#addCommandExecution(String, CommandExecutionResult)}.
   * <ul>
   *   <li>When {@link CommandExecutionResult}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProfileSession#addCommandExecution(String, CommandExecutionResult)}
   */
  @Test
  public void testAddCommandExecution_whenCommandExecutionResult2() {
    // Arrange and Act
    profileSession.addCommandExecution("Class Fqn", mock(CommandExecutionResult.class));

    // Assert
    assertEquals(1, profileSession.getCommandExecutions().size());
  }

  /**
   * Test {@link ProfileSession#setEndTime(Date)}.
   * <ul>
   *   <li>Given {@link ProfileSession#ProfileSession(String)} with
   * {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProfileSession#setEndTime(Date)}
   */
  @Test
  public void testSetEndTime_givenProfileSessionWithName() {
    // Arrange
    ProfileSession profileSession = new ProfileSession("Name");
    Date endTimeStamp = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    profileSession.setEndTime(endTimeStamp);

    // Assert
    assertSame(endTimeStamp, profileSession.getEndTime());
  }

  /**
   * Test {@link ProfileSession#setEndTime(Date)}.
   * <ul>
   *   <li>Then {@link ProfileSession#ProfileSession(String)} with {@code Name}
   * TotalTime is minus ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProfileSession#setEndTime(java.util.Date)}
   */
  @Test
  public void testSetEndTime_thenProfileSessionWithNameTotalTimeIsMinusTen() {
    // Arrange
    java.sql.Date startTime = mock(java.sql.Date.class);
    when(startTime.getTime()).thenReturn(10L);

    ProfileSession profileSession = new ProfileSession("Name");
    profileSession.setStartTime(startTime);
    java.util.Date endTimeStamp = java.util.Date
        .from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    profileSession.setEndTime(endTimeStamp);

    // Assert
    verify(startTime).getTime();
    assertEquals(-10L, profileSession.getTotalTime());
    assertSame(endTimeStamp, profileSession.getEndTime());
  }

  /**
   * Test {@link ProfileSession#setEndTime(Date)}.
   * <ul>
   *   <li>Then {@link ProfileSession#ProfileSession(String)} with {@code Name}
   * TotalTime is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProfileSession#setEndTime(Date)}
   */
  @Test
  public void testSetEndTime_thenProfileSessionWithNameTotalTimeIsZero() {
    // Arrange
    ProfileSession profileSession = new ProfileSession("Name");
    profileSession.setStartTime(null);
    Date endTimeStamp = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    profileSession.setEndTime(endTimeStamp);

    // Assert
    assertEquals(0L, profileSession.getTotalTime());
    assertSame(endTimeStamp, profileSession.getEndTime());
  }

  /**
   * Test {@link ProfileSession#calculateSummaryStatistics()}.
   * <ul>
   *   <li>Given {@link ProfileSession#ProfileSession(String)} with
   * {@code Name}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProfileSession#calculateSummaryStatistics()}
   */
  @Test
  public void testCalculateSummaryStatistics_givenProfileSessionWithName_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue((new ProfileSession("Name")).calculateSummaryStatistics().isEmpty());
  }

  /**
   * Test {@link ProfileSession#calculateSummaryStatistics()}.
   * <ul>
   *   <li>Then return {@code Class Fqn} DbSelects Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProfileSession#calculateSummaryStatistics()}
   */
  @Test
  public void testCalculateSummaryStatistics_thenReturnClassFqnDbSelectsEmpty() {
    // Arrange
    ProfileSession profileSession = new ProfileSession("Name");
    profileSession.addCommandExecution("Class Fqn", new CommandExecutionResult());

    // Act
    Map<String, CommandStats> actualCalculateSummaryStatisticsResult = profileSession.calculateSummaryStatistics();

    // Assert
    assertEquals(1, actualCalculateSummaryStatisticsResult.size());
    CommandStats getResult = actualCalculateSummaryStatisticsResult.get("Class Fqn");
    assertEquals(0.0d, getResult.getAverageDatabaseExecutionTime(), 0.0);
    assertEquals(0.0d, getResult.getAverageDatabaseExecutionTimePercentage(), 0.0);
    assertEquals(0.0d, getResult.getAverageExecutionTime(), 0.0);
    List<Long> resultLongList = getResult.commandExecutionTimings;
    assertEquals(1, resultLongList.size());
    assertEquals(0L, resultLongList.get(0).longValue());
    assertEquals(0L, getResult.getGetTotalCommandTime());
    assertEquals(1L, getResult.getCount());
    assertTrue(getResult.getDbDeletes().isEmpty());
    assertTrue(getResult.getDbInserts().isEmpty());
    assertTrue(getResult.getDbSelects().isEmpty());
    assertTrue(getResult.getDbUpdates().isEmpty());
    assertEquals(resultLongList, getResult.databaseTimings);
  }

  /**
   * Test {@link ProfileSession#calculateSummaryStatistics()}.
   * <ul>
   *   <li>Then return {@code Class Fqn} DbSelects size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProfileSession#calculateSummaryStatistics()}
   */
  @Test
  public void testCalculateSummaryStatistics_thenReturnClassFqnDbSelectsSizeIsOne() {
    // Arrange
    CommandExecutionResult commandExecutionResult = new CommandExecutionResult();
    commandExecutionResult.addDbSelect("Select");

    ProfileSession profileSession = new ProfileSession("Name");
    profileSession.addCommandExecution("Class Fqn", commandExecutionResult);

    // Act
    Map<String, CommandStats> actualCalculateSummaryStatisticsResult = profileSession.calculateSummaryStatistics();

    // Assert
    assertEquals(1, actualCalculateSummaryStatisticsResult.size());
    CommandStats getResult = actualCalculateSummaryStatisticsResult.get("Class Fqn");
    assertEquals(0.0d, getResult.getAverageDatabaseExecutionTime(), 0.0);
    assertEquals(0.0d, getResult.getAverageDatabaseExecutionTimePercentage(), 0.0);
    assertEquals(0.0d, getResult.getAverageExecutionTime(), 0.0);
    List<Long> resultLongList = getResult.commandExecutionTimings;
    assertEquals(1, resultLongList.size());
    assertEquals(0L, resultLongList.get(0).longValue());
    assertEquals(0L, getResult.getGetTotalCommandTime());
    Map<String, Long> dbSelects = getResult.getDbSelects();
    assertEquals(1, dbSelects.size());
    assertEquals(1L, dbSelects.get("Select").longValue());
    assertEquals(1L, getResult.getCount());
    assertTrue(getResult.getDbDeletes().isEmpty());
    assertTrue(getResult.getDbInserts().isEmpty());
    assertTrue(getResult.getDbUpdates().isEmpty());
    assertEquals(resultLongList, getResult.databaseTimings);
  }
}
