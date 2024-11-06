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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.db.DbSqlSession;
import org.activiti.engine.impl.db.DbSqlSessionFactory;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ActivitiProfilerDiffblueTest {
  @InjectMocks
  private ActivitiProfiler activitiProfiler;

  /**
   * Test {@link ActivitiProfiler#beforeInit(ProcessEngineConfigurationImpl)}.
   * <p>
   * Method under test:
   * {@link ActivitiProfiler#beforeInit(ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testBeforeInit() {
    // Arrange
    ActivitiProfiler instance = ActivitiProfiler.getInstance();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    instance.beforeInit(processEngineConfiguration);

    // Assert
    DbSqlSessionFactory dbSqlSessionFactory = processEngineConfiguration.getDbSqlSessionFactory();
    assertTrue(dbSqlSessionFactory instanceof ProfilingDbSqlSessionFactory);
    List<CommandInterceptor> customPreCommandInterceptors = processEngineConfiguration
        .getCustomPreCommandInterceptors();
    assertEquals(1, customPreCommandInterceptors.size());
    CommandInterceptor getResult = customPreCommandInterceptors.get(0);
    assertTrue(getResult instanceof TotalExecutionTimeCommandInterceptor);
    assertEquals("", dbSqlSessionFactory.getDatabaseTablePrefix());
    assertNull(dbSqlSessionFactory.getDatabaseCatalog());
    assertNull(dbSqlSessionFactory.getDatabaseSchema());
    assertNull(dbSqlSessionFactory.getDatabaseType());
    assertNull(dbSqlSessionFactory.getStatementMappings());
    assertNull(dbSqlSessionFactory.getIdGenerator());
    assertNull(getResult.getNext());
    assertNull(dbSqlSessionFactory.getSqlSessionFactory());
    assertEquals(100, dbSqlSessionFactory.getMaxNrOfStatementsInBulkInsert());
    assertFalse(dbSqlSessionFactory.isTablePrefixIsSchema());
    assertTrue(dbSqlSessionFactory.getBulkDeleteStatements().isEmpty());
    assertTrue(dbSqlSessionFactory.getBulkInsertStatements().isEmpty());
    assertTrue(dbSqlSessionFactory.getDeleteStatements().isEmpty());
    assertTrue(dbSqlSessionFactory.getInsertStatements().isEmpty());
    assertTrue(dbSqlSessionFactory.getSelectStatements().isEmpty());
    assertTrue(dbSqlSessionFactory.getUpdateStatements().isEmpty());
    assertTrue(dbSqlSessionFactory.isDbHistoryUsed());
    Class<DbSqlSession> expectedSessionType = DbSqlSession.class;
    assertEquals(expectedSessionType, dbSqlSessionFactory.getSessionType());
    assertSame(instance.INSTANCE, ((TotalExecutionTimeCommandInterceptor) getResult).activitiProfiler);
  }

  /**
   * Test {@link ActivitiProfiler#reset()}.
   * <ul>
   *   <li>Given Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiProfiler#reset()}
   */
  @Test
  public void testReset_givenInstance() {
    // Arrange
    ActivitiProfiler instance = ActivitiProfiler.getInstance();

    // Act
    instance.reset();

    // Assert
    assertNull(instance.getCurrentProfileSession());
    assertTrue(instance.getProfileSessions().isEmpty());
  }

  /**
   * Test {@link ActivitiProfiler#reset()}.
   * <ul>
   *   <li>Given Instance startProfileSession {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiProfiler#reset()}
   */
  @Test
  public void testReset_givenInstanceStartProfileSessionNull() {
    // Arrange
    ActivitiProfiler instance = ActivitiProfiler.getInstance();
    instance.startProfileSession(null);

    // Act
    instance.reset();

    // Assert
    assertNull(instance.getCurrentProfileSession());
    assertTrue(instance.getProfileSessions().isEmpty());
  }

  /**
   * Test {@link ActivitiProfiler#startProfileSession(String)}.
   * <p>
   * Method under test: {@link ActivitiProfiler#startProfileSession(String)}
   */
  @Test
  public void testStartProfileSession() {
    // Arrange and Act
    activitiProfiler.startProfileSession("Name");

    // Assert
    ProfileSession currentProfileSession = activitiProfiler.getCurrentProfileSession();
    assertEquals("Name", currentProfileSession.getName());
    assertNull(currentProfileSession.getEndTime());
    assertNull(currentProfileSession.currentCommandExecution.get());
    assertNull(currentProfileSession.getCurrentCommandExecution());
    assertEquals(0L, currentProfileSession.getTotalTime());
    assertTrue(currentProfileSession.getCommandExecutions().isEmpty());
  }

  /**
   * Test {@link ActivitiProfiler#stopCurrentProfileSession()}.
   * <ul>
   *   <li>Then Instance CurrentProfileSession is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiProfiler#stopCurrentProfileSession()}
   */
  @Test
  public void testStopCurrentProfileSession_thenInstanceCurrentProfileSessionIsNull() {
    // Arrange
    ActivitiProfiler instance = ActivitiProfiler.getInstance();
    instance.startProfileSession("Name");

    // Act
    instance.stopCurrentProfileSession();

    // Assert
    assertNull(instance.getCurrentProfileSession());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ActivitiProfiler}
   *   <li>{@link ActivitiProfiler#setCurrentProfileSession(ProfileSession)}
   *   <li>{@link ActivitiProfiler#setProfileSessions(List)}
   *   <li>{@link ActivitiProfiler#configure(ProcessEngineConfigurationImpl)}
   *   <li>{@link ActivitiProfiler#getInstance()}
   *   <li>{@link ActivitiProfiler#getCurrentProfileSession()}
   *   <li>{@link ActivitiProfiler#getPriority()}
   *   <li>{@link ActivitiProfiler#getProfileSessions()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    ActivitiProfiler actualActivitiProfiler = new ActivitiProfiler();
    ProfileSession currentProfileSession = new ProfileSession("Name");
    actualActivitiProfiler.setCurrentProfileSession(currentProfileSession);
    ArrayList<ProfileSession> profileSessions = new ArrayList<>();
    actualActivitiProfiler.setProfileSessions(profileSessions);
    actualActivitiProfiler.configure(new JtaProcessEngineConfiguration());
    ActivitiProfiler actualInstance = actualActivitiProfiler.getInstance();
    ProfileSession actualCurrentProfileSession = actualActivitiProfiler.getCurrentProfileSession();
    int actualPriority = actualActivitiProfiler.getPriority();
    List<ProfileSession> actualProfileSessions = actualActivitiProfiler.getProfileSessions();

    // Assert that nothing has changed
    assertEquals(0, actualPriority);
    assertTrue(actualProfileSessions.isEmpty());
    assertSame(profileSessions, actualProfileSessions);
    assertSame(currentProfileSession, actualCurrentProfileSession);
    assertSame(actualInstance.INSTANCE, actualInstance);
  }
}
