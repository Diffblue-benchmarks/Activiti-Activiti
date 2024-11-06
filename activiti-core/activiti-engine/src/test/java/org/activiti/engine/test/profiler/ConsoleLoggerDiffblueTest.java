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

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import org.junit.Test;
import org.mockito.Mockito;

public class ConsoleLoggerDiffblueTest {
  /**
   * Test {@link ConsoleLogger#log()}.
   * <p>
   * Method under test: {@link ConsoleLogger#log()}
   */
  @Test
  public void testLog() {
    // Arrange
    HashMap<String, CommandStats> stringCommandStatsMap = new HashMap<>();
    stringCommandStatsMap.put("#############################################", new CommandStats(new ArrayList<>()));
    ProfileSession profileSession = mock(ProfileSession.class);
    when(profileSession.getName()).thenReturn("Name");
    when(profileSession.getEndTime())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(profileSession.getStartTime())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(profileSession.calculateSummaryStatistics()).thenReturn(stringCommandStatsMap);
    when(profileSession.getTotalTime()).thenReturn(1L);
    doNothing().when(profileSession).addCommandExecution(Mockito.<String>any(), Mockito.<CommandExecutionResult>any());
    profileSession.addCommandExecution("Class Fqn", new CommandExecutionResult());

    ArrayList<ProfileSession> profileSessionList = new ArrayList<>();
    profileSessionList.add(0, profileSession);
    ActivitiProfiler profiler = mock(ActivitiProfiler.class);
    when(profiler.getProfileSessions()).thenReturn(profileSessionList);

    // Act
    (new ConsoleLogger(profiler)).log();

    // Assert that nothing has changed
    verify(profiler).getProfileSessions();
    verify(profileSession).addCommandExecution(eq("Class Fqn"), isA(CommandExecutionResult.class));
    verify(profileSession).calculateSummaryStatistics();
    verify(profileSession).getEndTime();
    verify(profileSession).getName();
    verify(profileSession).getStartTime();
    verify(profileSession, atLeast(1)).getTotalTime();
  }

  /**
   * Test {@link ConsoleLogger#log()}.
   * <ul>
   *   <li>Given {@link ProfileSession} {@link ProfileSession#getName()} return
   * {@code Name}.</li>
   *   <li>Then calls
   * {@link ProfileSession#addCommandExecution(String, CommandExecutionResult)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConsoleLogger#log()}
   */
  @Test
  public void testLog_givenProfileSessionGetNameReturnName_thenCallsAddCommandExecution() {
    // Arrange
    ProfileSession profileSession = mock(ProfileSession.class);
    when(profileSession.getName()).thenReturn("Name");
    when(profileSession.getEndTime())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(profileSession.getStartTime())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(profileSession.calculateSummaryStatistics()).thenReturn(new HashMap<>());
    when(profileSession.getTotalTime()).thenReturn(1L);
    doNothing().when(profileSession).addCommandExecution(Mockito.<String>any(), Mockito.<CommandExecutionResult>any());
    profileSession.addCommandExecution("Class Fqn", new CommandExecutionResult());

    ArrayList<ProfileSession> profileSessionList = new ArrayList<>();
    profileSessionList.add(0, profileSession);
    ActivitiProfiler profiler = mock(ActivitiProfiler.class);
    when(profiler.getProfileSessions()).thenReturn(profileSessionList);

    // Act
    (new ConsoleLogger(profiler)).log();

    // Assert that nothing has changed
    verify(profiler).getProfileSessions();
    verify(profileSession).addCommandExecution(eq("Class Fqn"), isA(CommandExecutionResult.class));
    verify(profileSession).calculateSummaryStatistics();
    verify(profileSession).getEndTime();
    verify(profileSession).getName();
    verify(profileSession).getStartTime();
    verify(profileSession).getTotalTime();
  }

  /**
   * Test {@link ConsoleLogger#log()}.
   * <ul>
   *   <li>Then calls {@link ActivitiProfiler#getProfileSessions()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConsoleLogger#log()}
   */
  @Test
  public void testLog_thenCallsGetProfileSessions() {
    // Arrange
    ActivitiProfiler profiler = mock(ActivitiProfiler.class);
    when(profiler.getProfileSessions()).thenReturn(new ArrayList<>());

    // Act
    (new ConsoleLogger(profiler)).log();

    // Assert that nothing has changed
    verify(profiler).getProfileSessions();
  }
}
