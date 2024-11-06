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
package org.activiti.engine.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import jakarta.transaction.TransactionManager;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.event.EventLogEntry;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.cmd.CustomSqlExecution;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.interceptor.JtaRetryInterceptor;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.management.TableMetaData;
import org.activiti.engine.management.TablePageQuery;
import org.activiti.engine.runtime.DeadLetterJobQuery;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.runtime.JobQuery;
import org.activiti.engine.runtime.SuspendedJobQuery;
import org.activiti.engine.runtime.TimerJobQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ManagementServiceImplDiffblueTest {
  @InjectMocks
  private ManagementServiceImpl managementServiceImpl;

  /**
   * Test {@link ManagementServiceImpl#getTableCount()}.
   * <ul>
   *   <li>Given {@link CommandInterceptor}
   * {@link CommandInterceptor#execute(CommandConfig, Command)} return
   * {@link HashMap#HashMap()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManagementServiceImpl#getTableCount()}
   */
  @Test
  public void testGetTableCount_givenCommandInterceptorExecuteReturnHashMap_thenReturnEmpty() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(new HashMap<>());
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Map<String, Long> actualTableCount = managementServiceImpl.getTableCount();

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualTableCount.isEmpty());
  }

  /**
   * Test {@link ManagementServiceImpl#getTableName(Class)}.
   * <ul>
   *   <li>Given {@link CommandInterceptor}
   * {@link CommandInterceptor#execute(CommandConfig, Command)} return
   * {@code foo}.</li>
   *   <li>Then return {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManagementServiceImpl#getTableName(Class)}
   */
  @Test
  public void testGetTableName_givenCommandInterceptorExecuteReturnFoo_thenReturnFoo() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn("foo");
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);
    Class<Object> activitiEntityClass = Object.class;

    // Act
    String actualTableName = managementServiceImpl.getTableName(activitiEntityClass);

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertEquals("foo", actualTableName);
  }

  /**
   * Test {@link ManagementServiceImpl#getTableMetaData(String)}.
   * <ul>
   *   <li>Then return {@link TableMetaData#TableMetaData(String)} with
   * {@code Table Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManagementServiceImpl#getTableMetaData(String)}
   */
  @Test
  public void testGetTableMetaData_thenReturnTableMetaDataWithTableName() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    TableMetaData tableMetaData = new TableMetaData("Table Name");
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(tableMetaData);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    TableMetaData actualTableMetaData = managementServiceImpl.getTableMetaData("Table Name");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(tableMetaData, actualTableMetaData);
  }

  /**
   * Test {@link ManagementServiceImpl#executeJob(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManagementServiceImpl#executeJob(String)}
   */
  @Test
  public void testExecuteJob_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> managementServiceImpl.executeJob(null));
  }

  /**
   * Test {@link ManagementServiceImpl#moveTimerToExecutableJob(String)}.
   * <ul>
   *   <li>Then return {@link DeadLetterJobEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ManagementServiceImpl#moveTimerToExecutableJob(String)}
   */
  @Test
  public void testMoveTimerToExecutableJob_thenReturnDeadLetterJobEntityImpl() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDeleted(true);
    deadLetterJobEntityImpl
        .setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    deadLetterJobEntityImpl
        .setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    deadLetterJobEntityImpl.setExceptionMessage("An error occurred");
    deadLetterJobEntityImpl.setExclusive(true);
    deadLetterJobEntityImpl.setExecutionId("42");
    deadLetterJobEntityImpl.setId("42");
    deadLetterJobEntityImpl.setInserted(true);
    deadLetterJobEntityImpl.setJobHandlerConfiguration("Job Handler Configuration");
    deadLetterJobEntityImpl.setJobHandlerType("Job Handler Type");
    deadLetterJobEntityImpl.setJobType("Job Type");
    deadLetterJobEntityImpl.setMaxIterations(3);
    deadLetterJobEntityImpl.setProcessDefinitionId("42");
    deadLetterJobEntityImpl.setProcessInstanceId("42");
    deadLetterJobEntityImpl.setRepeat("Repeat");
    deadLetterJobEntityImpl.setRetries(2);
    deadLetterJobEntityImpl.setRevision(2);
    deadLetterJobEntityImpl.setTenantId("42");
    deadLetterJobEntityImpl.setUpdated(true);
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(deadLetterJobEntityImpl);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Job actualMoveTimerToExecutableJobResult = managementServiceImpl.moveTimerToExecutableJob("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(deadLetterJobEntityImpl, actualMoveTimerToExecutableJobResult);
  }

  /**
   * Test {@link ManagementServiceImpl#moveJobToDeadLetterJob(String)}.
   * <ul>
   *   <li>Then return {@link DeadLetterJobEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ManagementServiceImpl#moveJobToDeadLetterJob(String)}
   */
  @Test
  public void testMoveJobToDeadLetterJob_thenReturnDeadLetterJobEntityImpl() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDeleted(true);
    deadLetterJobEntityImpl
        .setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    deadLetterJobEntityImpl
        .setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    deadLetterJobEntityImpl.setExceptionMessage("An error occurred");
    deadLetterJobEntityImpl.setExclusive(true);
    deadLetterJobEntityImpl.setExecutionId("42");
    deadLetterJobEntityImpl.setId("42");
    deadLetterJobEntityImpl.setInserted(true);
    deadLetterJobEntityImpl.setJobHandlerConfiguration("Job Handler Configuration");
    deadLetterJobEntityImpl.setJobHandlerType("Job Handler Type");
    deadLetterJobEntityImpl.setJobType("Job Type");
    deadLetterJobEntityImpl.setMaxIterations(3);
    deadLetterJobEntityImpl.setProcessDefinitionId("42");
    deadLetterJobEntityImpl.setProcessInstanceId("42");
    deadLetterJobEntityImpl.setRepeat("Repeat");
    deadLetterJobEntityImpl.setRetries(2);
    deadLetterJobEntityImpl.setRevision(2);
    deadLetterJobEntityImpl.setTenantId("42");
    deadLetterJobEntityImpl.setUpdated(true);
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(deadLetterJobEntityImpl);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Job actualMoveJobToDeadLetterJobResult = managementServiceImpl.moveJobToDeadLetterJob("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(deadLetterJobEntityImpl, actualMoveJobToDeadLetterJobResult);
  }

  /**
   * Test
   * {@link ManagementServiceImpl#moveDeadLetterJobToExecutableJob(String, int)}.
   * <ul>
   *   <li>Then return {@link DeadLetterJobEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ManagementServiceImpl#moveDeadLetterJobToExecutableJob(String, int)}
   */
  @Test
  public void testMoveDeadLetterJobToExecutableJob_thenReturnDeadLetterJobEntityImpl() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDeleted(true);
    deadLetterJobEntityImpl
        .setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    deadLetterJobEntityImpl
        .setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    deadLetterJobEntityImpl.setExceptionMessage("An error occurred");
    deadLetterJobEntityImpl.setExclusive(true);
    deadLetterJobEntityImpl.setExecutionId("42");
    deadLetterJobEntityImpl.setId("42");
    deadLetterJobEntityImpl.setInserted(true);
    deadLetterJobEntityImpl.setJobHandlerConfiguration("Job Handler Configuration");
    deadLetterJobEntityImpl.setJobHandlerType("Job Handler Type");
    deadLetterJobEntityImpl.setJobType("Job Type");
    deadLetterJobEntityImpl.setMaxIterations(3);
    deadLetterJobEntityImpl.setProcessDefinitionId("42");
    deadLetterJobEntityImpl.setProcessInstanceId("42");
    deadLetterJobEntityImpl.setRepeat("Repeat");
    deadLetterJobEntityImpl.setRetries(2);
    deadLetterJobEntityImpl.setRevision(2);
    deadLetterJobEntityImpl.setTenantId("42");
    deadLetterJobEntityImpl.setUpdated(true);
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(deadLetterJobEntityImpl);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Job actualMoveDeadLetterJobToExecutableJobResult = managementServiceImpl.moveDeadLetterJobToExecutableJob("42", 1);

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(deadLetterJobEntityImpl, actualMoveDeadLetterJobToExecutableJobResult);
  }

  /**
   * Test {@link ManagementServiceImpl#deleteJob(String)}.
   * <ul>
   *   <li>Given {@link CommandInterceptor}
   * {@link CommandInterceptor#execute(CommandConfig, Command)} return
   * {@link JSONObject#NULL}.</li>
   *   <li>Then calls
   * {@link CommandInterceptor#execute(CommandConfig, Command)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManagementServiceImpl#deleteJob(String)}
   */
  @Test
  public void testDeleteJob_givenCommandInterceptorExecuteReturnNull_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    managementServiceImpl.deleteJob("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link ManagementServiceImpl#deleteTimerJob(String)}.
   * <ul>
   *   <li>Given {@link CommandInterceptor}
   * {@link CommandInterceptor#execute(CommandConfig, Command)} return
   * {@link JSONObject#NULL}.</li>
   *   <li>Then calls
   * {@link CommandInterceptor#execute(CommandConfig, Command)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManagementServiceImpl#deleteTimerJob(String)}
   */
  @Test
  public void testDeleteTimerJob_givenCommandInterceptorExecuteReturnNull_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    managementServiceImpl.deleteTimerJob("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link ManagementServiceImpl#deleteDeadLetterJob(String)}.
   * <ul>
   *   <li>Then calls
   * {@link CommandInterceptor#execute(CommandConfig, Command)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManagementServiceImpl#deleteDeadLetterJob(String)}
   */
  @Test
  public void testDeleteDeadLetterJob_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    managementServiceImpl.deleteDeadLetterJob("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link ManagementServiceImpl#setJobRetries(String, int)}.
   * <ul>
   *   <li>Given {@link CommandInterceptor}
   * {@link CommandInterceptor#execute(CommandConfig, Command)} return
   * {@link JSONObject#NULL}.</li>
   *   <li>Then calls
   * {@link CommandInterceptor#execute(CommandConfig, Command)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManagementServiceImpl#setJobRetries(String, int)}
   */
  @Test
  public void testSetJobRetries_givenCommandInterceptorExecuteReturnNull_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    managementServiceImpl.setJobRetries("42", 1);

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link ManagementServiceImpl#setTimerJobRetries(String, int)}.
   * <ul>
   *   <li>Given {@link CommandInterceptor}
   * {@link CommandInterceptor#execute(CommandConfig, Command)} return
   * {@link JSONObject#NULL}.</li>
   *   <li>Then calls
   * {@link CommandInterceptor#execute(CommandConfig, Command)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ManagementServiceImpl#setTimerJobRetries(String, int)}
   */
  @Test
  public void testSetTimerJobRetries_givenCommandInterceptorExecuteReturnNull_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    managementServiceImpl.setTimerJobRetries("42", 1);

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link ManagementServiceImpl#createTablePageQuery()}.
   * <ul>
   *   <li>Given {@link ManagementServiceImpl} (default constructor).</li>
   *   <li>Then return Order is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManagementServiceImpl#createTablePageQuery()}
   */
  @Test
  public void testCreateTablePageQuery_givenManagementServiceImpl_thenReturnOrderIsNull() {
    // Arrange
    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();

    // Act
    TablePageQuery actualCreateTablePageQueryResult = managementServiceImpl.createTablePageQuery();

    // Assert
    assertTrue(actualCreateTablePageQueryResult instanceof TablePageQueryImpl);
    assertNull(((TablePageQueryImpl) actualCreateTablePageQueryResult).getOrder());
    assertNull(((TablePageQueryImpl) actualCreateTablePageQueryResult).getTableName());
    assertNull(managementServiceImpl.getCommandExecutor());
    assertNull(((TablePageQueryImpl) actualCreateTablePageQueryResult).commandExecutor);
    assertEquals(0, ((TablePageQueryImpl) actualCreateTablePageQueryResult).firstResult);
    assertEquals(0, ((TablePageQueryImpl) actualCreateTablePageQueryResult).maxResults);
  }

  /**
   * Test {@link ManagementServiceImpl#createTablePageQuery()}.
   * <ul>
   *   <li>Then {@link TablePageQueryImpl#commandExecutor} return
   * {@link CommandExecutorImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManagementServiceImpl#createTablePageQuery()}
   */
  @Test
  public void testCreateTablePageQuery_thenCommandExecutorReturnCommandExecutorImpl() {
    // Arrange
    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    CommandConfig defaultConfig = new CommandConfig();
    JtaRetryInterceptor first = new JtaRetryInterceptor(mock(TransactionManager.class));
    managementServiceImpl.setCommandExecutor(new CommandExecutorImpl(defaultConfig, first));

    // Act
    TablePageQuery actualCreateTablePageQueryResult = managementServiceImpl.createTablePageQuery();

    // Assert
    assertTrue(actualCreateTablePageQueryResult instanceof TablePageQueryImpl);
    CommandExecutor commandExecutor = ((TablePageQueryImpl) actualCreateTablePageQueryResult).commandExecutor;
    assertTrue(commandExecutor instanceof CommandExecutorImpl);
    assertSame(defaultConfig, commandExecutor.getDefaultConfig());
    assertSame(first, ((CommandExecutorImpl) commandExecutor).getFirst());
    CommandExecutor expectedCommandExecutor = ((TablePageQueryImpl) actualCreateTablePageQueryResult).commandExecutor;
    assertSame(expectedCommandExecutor, managementServiceImpl.getCommandExecutor());
  }

  /**
   * Test {@link ManagementServiceImpl#createJobQuery()}.
   * <ul>
   *   <li>Given {@link ManagementServiceImpl} (default constructor).</li>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManagementServiceImpl#createJobQuery()}
   */
  @Test
  public void testCreateJobQuery_givenManagementServiceImpl_thenReturnOrderByIsResIdAsc() {
    // Arrange
    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();

    // Act
    JobQuery actualCreateJobQueryResult = managementServiceImpl.createJobQuery();

    // Assert
    assertTrue(actualCreateJobQueryResult instanceof JobQueryImpl);
    assertEquals("RES.ID_ asc", ((JobQueryImpl) actualCreateJobQueryResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((JobQueryImpl) actualCreateJobQueryResult).getOrderByColumns());
    assertNull(((JobQueryImpl) actualCreateJobQueryResult).getParameter());
    assertNull(((JobQueryImpl) actualCreateJobQueryResult).getDatabaseType());
    assertNull(((JobQueryImpl) actualCreateJobQueryResult).getExceptionMessage());
    assertNull(((JobQueryImpl) actualCreateJobQueryResult).getExecutionId());
    assertNull(((JobQueryImpl) actualCreateJobQueryResult).getId());
    assertNull(((JobQueryImpl) actualCreateJobQueryResult).getProcessDefinitionId());
    assertNull(((JobQueryImpl) actualCreateJobQueryResult).getProcessInstanceId());
    assertNull(((JobQueryImpl) actualCreateJobQueryResult).getTenantId());
    assertNull(((JobQueryImpl) actualCreateJobQueryResult).getTenantIdLike());
    assertNull(((JobQueryImpl) actualCreateJobQueryResult).orderBy);
    assertNull(((JobQueryImpl) actualCreateJobQueryResult).getDuedateHigherThan());
    assertNull(((JobQueryImpl) actualCreateJobQueryResult).getDuedateHigherThanOrEqual());
    assertNull(((JobQueryImpl) actualCreateJobQueryResult).getDuedateLowerThan());
    assertNull(((JobQueryImpl) actualCreateJobQueryResult).getDuedateLowerThanOrEqual());
    assertNull(((JobQueryImpl) actualCreateJobQueryResult).nullHandlingOnOrder);
    assertNull(((JobQueryImpl) actualCreateJobQueryResult).resultType);
    assertNull(((JobQueryImpl) actualCreateJobQueryResult).commandContext);
    assertNull(managementServiceImpl.getCommandExecutor());
    assertNull(((JobQueryImpl) actualCreateJobQueryResult).commandExecutor);
    assertNull(((JobQueryImpl) actualCreateJobQueryResult).orderProperty);
    assertEquals(0, ((JobQueryImpl) actualCreateJobQueryResult).getFirstResult());
    assertEquals(1, ((JobQueryImpl) actualCreateJobQueryResult).getFirstRow());
    assertFalse(((JobQueryImpl) actualCreateJobQueryResult).getExecutable());
    assertFalse(((JobQueryImpl) actualCreateJobQueryResult).getRetriesLeft());
    assertFalse(((JobQueryImpl) actualCreateJobQueryResult).isNoRetriesLeft());
    assertFalse(((JobQueryImpl) actualCreateJobQueryResult).isOnlyLocked());
    assertFalse(((JobQueryImpl) actualCreateJobQueryResult).isOnlyMessages());
    assertFalse(((JobQueryImpl) actualCreateJobQueryResult).isOnlyTimers());
    assertFalse(((JobQueryImpl) actualCreateJobQueryResult).isOnlyUnlocked());
    assertFalse(((JobQueryImpl) actualCreateJobQueryResult).isWithException());
    assertFalse(((JobQueryImpl) actualCreateJobQueryResult).isWithoutTenantId());
    assertEquals(Integer.MAX_VALUE, ((JobQueryImpl) actualCreateJobQueryResult).getLastRow());
    assertEquals(Integer.MAX_VALUE, ((JobQueryImpl) actualCreateJobQueryResult).getMaxResults());
  }

  /**
   * Test {@link ManagementServiceImpl#createJobQuery()}.
   * <ul>
   *   <li>Then {@link AbstractQuery#commandExecutor} return
   * {@link CommandExecutorImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManagementServiceImpl#createJobQuery()}
   */
  @Test
  public void testCreateJobQuery_thenCommandExecutorReturnCommandExecutorImpl() {
    // Arrange
    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    CommandConfig defaultConfig = new CommandConfig();
    JtaRetryInterceptor first = new JtaRetryInterceptor(mock(TransactionManager.class));
    managementServiceImpl.setCommandExecutor(new CommandExecutorImpl(defaultConfig, first));

    // Act
    JobQuery actualCreateJobQueryResult = managementServiceImpl.createJobQuery();

    // Assert
    assertTrue(actualCreateJobQueryResult instanceof JobQueryImpl);
    CommandExecutor commandExecutor = ((JobQueryImpl) actualCreateJobQueryResult).commandExecutor;
    assertTrue(commandExecutor instanceof CommandExecutorImpl);
    assertSame(defaultConfig, commandExecutor.getDefaultConfig());
    assertSame(first, ((CommandExecutorImpl) commandExecutor).getFirst());
    CommandExecutor expectedCommandExecutor = ((AbstractQuery<JobQuery, Job>) actualCreateJobQueryResult).commandExecutor;
    assertSame(expectedCommandExecutor, managementServiceImpl.getCommandExecutor());
  }

  /**
   * Test {@link ManagementServiceImpl#createTimerJobQuery()}.
   * <ul>
   *   <li>Given {@link ManagementServiceImpl} (default constructor).</li>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManagementServiceImpl#createTimerJobQuery()}
   */
  @Test
  public void testCreateTimerJobQuery_givenManagementServiceImpl_thenReturnOrderByIsResIdAsc() {
    // Arrange
    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();

    // Act
    TimerJobQuery actualCreateTimerJobQueryResult = managementServiceImpl.createTimerJobQuery();

    // Assert
    assertTrue(actualCreateTimerJobQueryResult instanceof TimerJobQueryImpl);
    assertEquals("RES.ID_ asc", ((TimerJobQueryImpl) actualCreateTimerJobQueryResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((TimerJobQueryImpl) actualCreateTimerJobQueryResult).getOrderByColumns());
    assertNull(((TimerJobQueryImpl) actualCreateTimerJobQueryResult).getParameter());
    assertNull(((TimerJobQueryImpl) actualCreateTimerJobQueryResult).getDatabaseType());
    assertNull(((TimerJobQueryImpl) actualCreateTimerJobQueryResult).getExceptionMessage());
    assertNull(((TimerJobQueryImpl) actualCreateTimerJobQueryResult).getExecutionId());
    assertNull(((TimerJobQueryImpl) actualCreateTimerJobQueryResult).getId());
    assertNull(((TimerJobQueryImpl) actualCreateTimerJobQueryResult).getProcessDefinitionId());
    assertNull(((TimerJobQueryImpl) actualCreateTimerJobQueryResult).getProcessInstanceId());
    assertNull(((TimerJobQueryImpl) actualCreateTimerJobQueryResult).getTenantId());
    assertNull(((TimerJobQueryImpl) actualCreateTimerJobQueryResult).getTenantIdLike());
    assertNull(((TimerJobQueryImpl) actualCreateTimerJobQueryResult).orderBy);
    assertNull(((TimerJobQueryImpl) actualCreateTimerJobQueryResult).getDuedateHigherThan());
    assertNull(((TimerJobQueryImpl) actualCreateTimerJobQueryResult).getDuedateHigherThanOrEqual());
    assertNull(((TimerJobQueryImpl) actualCreateTimerJobQueryResult).getDuedateLowerThan());
    assertNull(((TimerJobQueryImpl) actualCreateTimerJobQueryResult).getDuedateLowerThanOrEqual());
    assertNull(((TimerJobQueryImpl) actualCreateTimerJobQueryResult).nullHandlingOnOrder);
    assertNull(((TimerJobQueryImpl) actualCreateTimerJobQueryResult).resultType);
    assertNull(((TimerJobQueryImpl) actualCreateTimerJobQueryResult).commandContext);
    assertNull(managementServiceImpl.getCommandExecutor());
    assertNull(((TimerJobQueryImpl) actualCreateTimerJobQueryResult).commandExecutor);
    assertNull(((TimerJobQueryImpl) actualCreateTimerJobQueryResult).orderProperty);
    assertEquals(0, ((TimerJobQueryImpl) actualCreateTimerJobQueryResult).getFirstResult());
    assertEquals(1, ((TimerJobQueryImpl) actualCreateTimerJobQueryResult).getFirstRow());
    assertFalse(((TimerJobQueryImpl) actualCreateTimerJobQueryResult).getExecutable());
    assertFalse(((TimerJobQueryImpl) actualCreateTimerJobQueryResult).getRetriesLeft());
    assertFalse(((TimerJobQueryImpl) actualCreateTimerJobQueryResult).isNoRetriesLeft());
    assertFalse(((TimerJobQueryImpl) actualCreateTimerJobQueryResult).isOnlyMessages());
    assertFalse(((TimerJobQueryImpl) actualCreateTimerJobQueryResult).isOnlyTimers());
    assertFalse(((TimerJobQueryImpl) actualCreateTimerJobQueryResult).isWithException());
    assertFalse(((TimerJobQueryImpl) actualCreateTimerJobQueryResult).isWithoutTenantId());
    assertEquals(Integer.MAX_VALUE, ((TimerJobQueryImpl) actualCreateTimerJobQueryResult).getLastRow());
    assertEquals(Integer.MAX_VALUE, ((TimerJobQueryImpl) actualCreateTimerJobQueryResult).getMaxResults());
  }

  /**
   * Test {@link ManagementServiceImpl#createTimerJobQuery()}.
   * <ul>
   *   <li>Then {@link AbstractQuery#commandExecutor} return
   * {@link CommandExecutorImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManagementServiceImpl#createTimerJobQuery()}
   */
  @Test
  public void testCreateTimerJobQuery_thenCommandExecutorReturnCommandExecutorImpl() {
    // Arrange
    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    CommandConfig defaultConfig = new CommandConfig();
    JtaRetryInterceptor first = new JtaRetryInterceptor(mock(TransactionManager.class));
    managementServiceImpl.setCommandExecutor(new CommandExecutorImpl(defaultConfig, first));

    // Act
    TimerJobQuery actualCreateTimerJobQueryResult = managementServiceImpl.createTimerJobQuery();

    // Assert
    assertTrue(actualCreateTimerJobQueryResult instanceof TimerJobQueryImpl);
    CommandExecutor commandExecutor = ((TimerJobQueryImpl) actualCreateTimerJobQueryResult).commandExecutor;
    assertTrue(commandExecutor instanceof CommandExecutorImpl);
    assertSame(defaultConfig, commandExecutor.getDefaultConfig());
    assertSame(first, ((CommandExecutorImpl) commandExecutor).getFirst());
    CommandExecutor expectedCommandExecutor = ((AbstractQuery<TimerJobQuery, Job>) actualCreateTimerJobQueryResult).commandExecutor;
    assertSame(expectedCommandExecutor, managementServiceImpl.getCommandExecutor());
  }

  /**
   * Test {@link ManagementServiceImpl#createSuspendedJobQuery()}.
   * <ul>
   *   <li>Then {@link AbstractQuery#commandExecutor} return
   * {@link CommandExecutorImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManagementServiceImpl#createSuspendedJobQuery()}
   */
  @Test
  public void testCreateSuspendedJobQuery_thenCommandExecutorReturnCommandExecutorImpl() {
    // Arrange
    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    CommandConfig defaultConfig = new CommandConfig();
    JtaRetryInterceptor first = new JtaRetryInterceptor(mock(TransactionManager.class));
    managementServiceImpl.setCommandExecutor(new CommandExecutorImpl(defaultConfig, first));

    // Act
    SuspendedJobQuery actualCreateSuspendedJobQueryResult = managementServiceImpl.createSuspendedJobQuery();

    // Assert
    assertTrue(actualCreateSuspendedJobQueryResult instanceof SuspendedJobQueryImpl);
    CommandExecutor commandExecutor = ((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).commandExecutor;
    assertTrue(commandExecutor instanceof CommandExecutorImpl);
    assertSame(defaultConfig, commandExecutor.getDefaultConfig());
    assertSame(first, ((CommandExecutorImpl) commandExecutor).getFirst());
    CommandExecutor expectedCommandExecutor = ((AbstractQuery<SuspendedJobQuery, Job>) actualCreateSuspendedJobQueryResult).commandExecutor;
    assertSame(expectedCommandExecutor, managementServiceImpl.getCommandExecutor());
  }

  /**
   * Test {@link ManagementServiceImpl#createSuspendedJobQuery()}.
   * <ul>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManagementServiceImpl#createSuspendedJobQuery()}
   */
  @Test
  public void testCreateSuspendedJobQuery_thenReturnOrderByIsResIdAsc() {
    // Arrange
    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();

    // Act
    SuspendedJobQuery actualCreateSuspendedJobQueryResult = managementServiceImpl.createSuspendedJobQuery();

    // Assert
    assertTrue(actualCreateSuspendedJobQueryResult instanceof SuspendedJobQueryImpl);
    assertEquals("RES.ID_ asc", ((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getOrderByColumns());
    assertNull(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getParameter());
    assertNull(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getDatabaseType());
    assertNull(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getExceptionMessage());
    assertNull(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getExecutionId());
    assertNull(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getId());
    assertNull(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getProcessDefinitionId());
    assertNull(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getProcessInstanceId());
    assertNull(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getTenantId());
    assertNull(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getTenantIdLike());
    assertNull(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).orderBy);
    assertNull(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getDuedateHigherThan());
    assertNull(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getDuedateHigherThanOrEqual());
    assertNull(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getDuedateLowerThan());
    assertNull(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getDuedateLowerThanOrEqual());
    assertNull(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).nullHandlingOnOrder);
    assertNull(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).resultType);
    assertNull(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).commandContext);
    assertNull(managementServiceImpl.getCommandExecutor());
    assertNull(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).commandExecutor);
    assertNull(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).orderProperty);
    assertEquals(0, ((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getFirstResult());
    assertEquals(1, ((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getFirstRow());
    assertFalse(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getExecutable());
    assertFalse(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getRetriesLeft());
    assertFalse(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).isNoRetriesLeft());
    assertFalse(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).isOnlyMessages());
    assertFalse(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).isOnlyTimers());
    assertFalse(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).isWithException());
    assertFalse(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).isWithoutTenantId());
    assertEquals(Integer.MAX_VALUE, ((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getLastRow());
    assertEquals(Integer.MAX_VALUE, ((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getMaxResults());
  }

  /**
   * Test {@link ManagementServiceImpl#createDeadLetterJobQuery()}.
   * <ul>
   *   <li>Then {@link AbstractQuery#commandExecutor} return
   * {@link CommandExecutorImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManagementServiceImpl#createDeadLetterJobQuery()}
   */
  @Test
  public void testCreateDeadLetterJobQuery_thenCommandExecutorReturnCommandExecutorImpl() {
    // Arrange
    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    CommandConfig defaultConfig = new CommandConfig();
    JtaRetryInterceptor first = new JtaRetryInterceptor(mock(TransactionManager.class));
    managementServiceImpl.setCommandExecutor(new CommandExecutorImpl(defaultConfig, first));

    // Act
    DeadLetterJobQuery actualCreateDeadLetterJobQueryResult = managementServiceImpl.createDeadLetterJobQuery();

    // Assert
    assertTrue(actualCreateDeadLetterJobQueryResult instanceof DeadLetterJobQueryImpl);
    CommandExecutor commandExecutor = ((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).commandExecutor;
    assertTrue(commandExecutor instanceof CommandExecutorImpl);
    assertSame(defaultConfig, commandExecutor.getDefaultConfig());
    assertSame(first, ((CommandExecutorImpl) commandExecutor).getFirst());
    CommandExecutor expectedCommandExecutor = ((AbstractQuery<DeadLetterJobQuery, Job>) actualCreateDeadLetterJobQueryResult).commandExecutor;
    assertSame(expectedCommandExecutor, managementServiceImpl.getCommandExecutor());
  }

  /**
   * Test {@link ManagementServiceImpl#createDeadLetterJobQuery()}.
   * <ul>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManagementServiceImpl#createDeadLetterJobQuery()}
   */
  @Test
  public void testCreateDeadLetterJobQuery_thenReturnOrderByIsResIdAsc() {
    // Arrange
    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();

    // Act
    DeadLetterJobQuery actualCreateDeadLetterJobQueryResult = managementServiceImpl.createDeadLetterJobQuery();

    // Assert
    assertTrue(actualCreateDeadLetterJobQueryResult instanceof DeadLetterJobQueryImpl);
    assertEquals("RES.ID_ asc", ((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getOrderByColumns());
    assertNull(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getParameter());
    assertNull(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getDatabaseType());
    assertNull(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getExceptionMessage());
    assertNull(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getExecutionId());
    assertNull(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getId());
    assertNull(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getProcessDefinitionId());
    assertNull(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getProcessInstanceId());
    assertNull(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getTenantId());
    assertNull(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getTenantIdLike());
    assertNull(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).orderBy);
    assertNull(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getDuedateHigherThan());
    assertNull(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getDuedateHigherThanOrEqual());
    assertNull(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getDuedateLowerThan());
    assertNull(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getDuedateLowerThanOrEqual());
    assertNull(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).nullHandlingOnOrder);
    assertNull(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).resultType);
    assertNull(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).commandContext);
    assertNull(managementServiceImpl.getCommandExecutor());
    assertNull(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).commandExecutor);
    assertNull(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).orderProperty);
    assertEquals(0, ((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getFirstResult());
    assertEquals(1, ((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getFirstRow());
    assertFalse(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getExecutable());
    assertFalse(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).isOnlyMessages());
    assertFalse(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).isOnlyTimers());
    assertFalse(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).isWithException());
    assertFalse(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).isWithoutTenantId());
    assertEquals(Integer.MAX_VALUE, ((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getLastRow());
    assertEquals(Integer.MAX_VALUE, ((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getMaxResults());
  }

  /**
   * Test {@link ManagementServiceImpl#getJobExceptionStacktrace(String)}.
   * <ul>
   *   <li>Then return {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ManagementServiceImpl#getJobExceptionStacktrace(String)}
   */
  @Test
  public void testGetJobExceptionStacktrace_thenReturnFoo() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn("foo");
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    String actualJobExceptionStacktrace = managementServiceImpl.getJobExceptionStacktrace("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertEquals("foo", actualJobExceptionStacktrace);
  }

  /**
   * Test {@link ManagementServiceImpl#getTimerJobExceptionStacktrace(String)}.
   * <ul>
   *   <li>Then return {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ManagementServiceImpl#getTimerJobExceptionStacktrace(String)}
   */
  @Test
  public void testGetTimerJobExceptionStacktrace_thenReturnFoo() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn("foo");
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    String actualTimerJobExceptionStacktrace = managementServiceImpl.getTimerJobExceptionStacktrace("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertEquals("foo", actualTimerJobExceptionStacktrace);
  }

  /**
   * Test
   * {@link ManagementServiceImpl#getSuspendedJobExceptionStacktrace(String)}.
   * <ul>
   *   <li>Then return {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ManagementServiceImpl#getSuspendedJobExceptionStacktrace(String)}
   */
  @Test
  public void testGetSuspendedJobExceptionStacktrace_thenReturnFoo() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn("foo");
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    String actualSuspendedJobExceptionStacktrace = managementServiceImpl.getSuspendedJobExceptionStacktrace("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertEquals("foo", actualSuspendedJobExceptionStacktrace);
  }

  /**
   * Test
   * {@link ManagementServiceImpl#getDeadLetterJobExceptionStacktrace(String)}.
   * <ul>
   *   <li>Then return {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ManagementServiceImpl#getDeadLetterJobExceptionStacktrace(String)}
   */
  @Test
  public void testGetDeadLetterJobExceptionStacktrace_thenReturnFoo() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn("foo");
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    String actualDeadLetterJobExceptionStacktrace = managementServiceImpl.getDeadLetterJobExceptionStacktrace("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertEquals("foo", actualDeadLetterJobExceptionStacktrace);
  }

  /**
   * Test {@link ManagementServiceImpl#getProperties()}.
   * <ul>
   *   <li>Given {@link CommandInterceptor}
   * {@link CommandInterceptor#execute(CommandConfig, Command)} return
   * {@link HashMap#HashMap()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManagementServiceImpl#getProperties()}
   */
  @Test
  public void testGetProperties_givenCommandInterceptorExecuteReturnHashMap_thenReturnEmpty() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(new HashMap<>());
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Map<String, String> actualProperties = managementServiceImpl.getProperties();

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualProperties.isEmpty());
  }

  /**
   * Test
   * {@link ManagementServiceImpl#databaseSchemaUpgrade(Connection, String, String)}.
   * <ul>
   *   <li>Then return {@code Execute}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ManagementServiceImpl#databaseSchemaUpgrade(Connection, String, String)}
   */
  @Test
  public void testDatabaseSchemaUpgrade_thenReturnExecute() {
    // Arrange
    CommandConfig defaultConfig = mock(CommandConfig.class);
    when(defaultConfig.transactionNotSupported()).thenReturn(new CommandConfig());
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<String>>any())).thenReturn("Execute");
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(defaultConfig, first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    String actualDatabaseSchemaUpgradeResult = managementServiceImpl.databaseSchemaUpgrade(mock(Connection.class),
        "Catalog", "Schema");

    // Assert
    verify(defaultConfig).transactionNotSupported();
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertEquals("Execute", actualDatabaseSchemaUpgradeResult);
  }

  /**
   * Test {@link ManagementServiceImpl#executeCommand(Command)} with
   * {@code command}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManagementServiceImpl#executeCommand(Command)}
   */
  @Test
  public void testExecuteCommandWithCommand_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new ManagementServiceImpl()).executeCommand(null));
  }

  /**
   * Test {@link ManagementServiceImpl#executeCommand(CommandConfig, Command)}
   * with {@code config}, {@code command}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ManagementServiceImpl#executeCommand(CommandConfig, Command)}
   */
  @Test
  public void testExecuteCommandWithConfigCommand_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ManagementServiceImpl()).executeCommand(null, null));
  }

  /**
   * Test {@link ManagementServiceImpl#executeCommand(CommandConfig, Command)}
   * with {@code config}, {@code command}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ManagementServiceImpl#executeCommand(CommandConfig, Command)}
   */
  @Test
  public void testExecuteCommandWithConfigCommand_thenThrowActivitiIllegalArgumentException2() {
    // Arrange
    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> managementServiceImpl.executeCommand(new CommandConfig(), null));
  }

  /**
   * Test {@link ManagementServiceImpl#executeCustomSql(CustomSqlExecution)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ManagementServiceImpl#executeCustomSql(CustomSqlExecution)}
   */
  @Test
  public void testExecuteCustomSql_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    CustomSqlExecution<Object, Object> customSqlExecution = mock(CustomSqlExecution.class);
    when(customSqlExecution.getMapperClass()).thenThrow(new ActivitiIllegalArgumentException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> managementServiceImpl.executeCustomSql(customSqlExecution));
    verify(customSqlExecution).getMapperClass();
  }

  /**
   * Test {@link ManagementServiceImpl#getEventLogEntries(Long, Long)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ManagementServiceImpl#getEventLogEntries(Long, Long)}
   */
  @Test
  public void testGetEventLogEntries_thenReturnEmpty() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(new ArrayList<>());
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    List<EventLogEntry> actualEventLogEntries = managementServiceImpl.getEventLogEntries(1L, 3L);

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualEventLogEntries.isEmpty());
  }

  /**
   * Test
   * {@link ManagementServiceImpl#getEventLogEntriesByProcessInstanceId(String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ManagementServiceImpl#getEventLogEntriesByProcessInstanceId(String)}
   */
  @Test
  public void testGetEventLogEntriesByProcessInstanceId_thenReturnEmpty() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(new ArrayList<>());
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    List<EventLogEntry> actualEventLogEntriesByProcessInstanceId = managementServiceImpl
        .getEventLogEntriesByProcessInstanceId("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualEventLogEntriesByProcessInstanceId.isEmpty());
  }

  /**
   * Test {@link ManagementServiceImpl#deleteEventLogEntry(long)}.
   * <ul>
   *   <li>Then calls
   * {@link CommandInterceptor#execute(CommandConfig, Command)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManagementServiceImpl#deleteEventLogEntry(long)}
   */
  @Test
  public void testDeleteEventLogEntry_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    managementServiceImpl.deleteEventLogEntry(1L);

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test new {@link ManagementServiceImpl} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link ManagementServiceImpl}
   */
  @Test
  public void testNewManagementServiceImpl() {
    // Arrange, Act and Assert
    assertNull((new ManagementServiceImpl()).getCommandExecutor());
  }
}
