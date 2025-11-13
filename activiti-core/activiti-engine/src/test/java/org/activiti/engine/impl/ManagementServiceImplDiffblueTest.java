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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.transaction.SystemException;
import jakarta.transaction.TransactionManager;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.event.EventLogEntry;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.cmd.CustomSqlExecution;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.interceptor.JtaTransactionInterceptor;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntity;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityImpl;
import org.activiti.engine.impl.persistence.entity.JobEntity;
import org.activiti.engine.impl.persistence.entity.JobEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.management.TableMetaData;
import org.activiti.engine.management.TablePageQuery;
import org.activiti.engine.runtime.DeadLetterJobQuery;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.runtime.JobQuery;
import org.activiti.engine.runtime.SuspendedJobQuery;
import org.activiti.engine.runtime.TimerJobQuery;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ManagementServiceImplDiffblueTest {
  /**
   * Test {@link ManagementServiceImpl#getTableCount()}.
   *
   * <ul>
   *   <li>Given {@link CommandInterceptor} {@link CommandInterceptor#execute(CommandConfig,
   *       Command)} return {@link HashMap#HashMap()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#getTableCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ManagementServiceImpl.getTableCount()"})
  public void testGetTableCount_givenCommandInterceptorExecuteReturnHashMap_thenReturnEmpty() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Map<String, Long>>>any()))
        .thenReturn(new HashMap<>());
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Map<String, Long> actualTableCount = managementServiceImpl.getTableCount();

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualTableCount.isEmpty());
  }

  /**
   * Test {@link ManagementServiceImpl#getTableCount()}.
   *
   * <ul>
   *   <li>Then calls {@link TransactionManager#getStatus()}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#getTableCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ManagementServiceImpl.getTableCount()"})
  public void testGetTableCount_thenCallsGetStatus() throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getStatus()).thenReturn(1);

    CommandInterceptor next = mock(CommandInterceptor.class);
    when(next.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Map<String, Long>>>any()))
        .thenReturn(new HashMap<>());

    JtaTransactionInterceptor commandInterceptor =
        new JtaTransactionInterceptor(transactionManager);
    commandInterceptor.setNext(next);

    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(new CommandConfig(), mock(CommandInterceptor.class));
    commandExecutor.setFirst(commandInterceptor);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Map<String, Long> actualTableCount = managementServiceImpl.getTableCount();

    // Assert
    verify(transactionManager).getStatus();
    verify(next).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualTableCount.isEmpty());
  }

  /**
   * Test {@link ManagementServiceImpl#getTableName(Class)}.
   *
   * <ul>
   *   <li>Given {@link CommandInterceptor} {@link CommandInterceptor#execute(CommandConfig,
   *       Command)} return {@code Execute}.
   *   <li>Then return {@code Execute}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#getTableName(Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ManagementServiceImpl.getTableName(Class)"})
  public void testGetTableName_givenCommandInterceptorExecuteReturnExecute_thenReturnExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<String>>any()))
        .thenReturn("Execute");
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);
    Class<Object> activitiEntityClass = Object.class;

    // Act
    String actualTableName = managementServiceImpl.getTableName(activitiEntityClass);

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertEquals("Execute", actualTableName);
  }

  /**
   * Test {@link ManagementServiceImpl#getTableName(Class)}.
   *
   * <ul>
   *   <li>Then calls {@link TransactionManager#getStatus()}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#getTableName(Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ManagementServiceImpl.getTableName(Class)"})
  public void testGetTableName_thenCallsGetStatus() throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getStatus()).thenReturn(1);

    CommandInterceptor next = mock(CommandInterceptor.class);
    when(next.execute(Mockito.<CommandConfig>any(), Mockito.<Command<String>>any()))
        .thenReturn("Execute");

    JtaTransactionInterceptor commandInterceptor =
        new JtaTransactionInterceptor(transactionManager);
    commandInterceptor.setNext(next);

    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(new CommandConfig(), mock(CommandInterceptor.class));
    commandExecutor.setFirst(commandInterceptor);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);
    Class<Object> activitiEntityClass = Object.class;

    // Act
    String actualTableName = managementServiceImpl.getTableName(activitiEntityClass);

    // Assert
    verify(transactionManager).getStatus();
    verify(next).execute(isA(CommandConfig.class), isA(Command.class));
    assertEquals("Execute", actualTableName);
  }

  /**
   * Test {@link ManagementServiceImpl#getTableMetaData(String)}.
   *
   * <ul>
   *   <li>Then calls {@link TransactionManager#getStatus()}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#getTableMetaData(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TableMetaData ManagementServiceImpl.getTableMetaData(String)"})
  public void testGetTableMetaData_thenCallsGetStatus() throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getStatus()).thenReturn(1);

    CommandInterceptor next = mock(CommandInterceptor.class);
    TableMetaData tableMetaData = new TableMetaData("Table Name");
    when(next.execute(Mockito.<CommandConfig>any(), Mockito.<Command<TableMetaData>>any()))
        .thenReturn(tableMetaData);

    JtaTransactionInterceptor commandInterceptor =
        new JtaTransactionInterceptor(transactionManager);
    commandInterceptor.setNext(next);

    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(new CommandConfig(), mock(CommandInterceptor.class));
    commandExecutor.setFirst(commandInterceptor);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    TableMetaData actualTableMetaData = managementServiceImpl.getTableMetaData("Table Name");

    // Assert
    verify(transactionManager).getStatus();
    verify(next).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(tableMetaData, actualTableMetaData);
  }

  /**
   * Test {@link ManagementServiceImpl#getTableMetaData(String)}.
   *
   * <ul>
   *   <li>Then return {@link TableMetaData#TableMetaData(String)} with {@code Table Name}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#getTableMetaData(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TableMetaData ManagementServiceImpl.getTableMetaData(String)"})
  public void testGetTableMetaData_thenReturnTableMetaDataWithTableName() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    TableMetaData tableMetaData = new TableMetaData("Table Name");
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<TableMetaData>>any()))
        .thenReturn(tableMetaData);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

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
   *
   * <ul>
   *   <li>Then calls {@link CommandConfig#isContextReusePossible()}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#executeJob(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ManagementServiceImpl.executeJob(String)"})
  public void testExecuteJob_thenCallsIsContextReusePossible() {
    // Arrange
    CommandConfig defaultConfig = mock(CommandConfig.class);
    when(defaultConfig.isContextReusePossible())
        .thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class, () -> managementServiceImpl.executeJob("42"));
    verify(defaultConfig).isContextReusePossible();
  }

  /**
   * Test {@link ManagementServiceImpl#executeJob(String)}.
   *
   * <ul>
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#executeJob(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ManagementServiceImpl.executeJob(String)"})
  public void testExecuteJob_thenDoesNotThrow() {
    // Arrange
    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(mock(CommandConfig.class), mock(CommandInterceptor.class));
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act and Assert
    managementServiceImpl.executeJob("42");
  }

  /**
   * Test {@link ManagementServiceImpl#executeJob(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#executeJob(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ManagementServiceImpl.executeJob(String)"})
  public void testExecuteJob_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class, () -> new ManagementServiceImpl().executeJob(null));
  }

  /**
   * Test {@link ManagementServiceImpl#moveTimerToExecutableJob(String)}.
   *
   * <ul>
   *   <li>Then calls {@link TransactionManager#getStatus()}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#moveTimerToExecutableJob(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Job ManagementServiceImpl.moveTimerToExecutableJob(String)"})
  public void testMoveTimerToExecutableJob_thenCallsGetStatus() throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getStatus()).thenReturn(1);

    CommandInterceptor next = mock(CommandInterceptor.class);
    JobEntityImpl jobEntityImpl = new JobEntityImpl();
    when(next.execute(Mockito.<CommandConfig>any(), Mockito.<Command<JobEntity>>any()))
        .thenReturn(jobEntityImpl);

    JtaTransactionInterceptor commandInterceptor =
        new JtaTransactionInterceptor(transactionManager);
    commandInterceptor.setNext(next);

    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(new CommandConfig(), mock(CommandInterceptor.class));
    commandExecutor.setFirst(commandInterceptor);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Job actualMoveTimerToExecutableJobResult = managementServiceImpl.moveTimerToExecutableJob("42");

    // Assert
    verify(transactionManager).getStatus();
    verify(next).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(jobEntityImpl, actualMoveTimerToExecutableJobResult);
  }

  /**
   * Test {@link ManagementServiceImpl#moveTimerToExecutableJob(String)}.
   *
   * <ul>
   *   <li>Then return {@link JobEntityImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#moveTimerToExecutableJob(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Job ManagementServiceImpl.moveTimerToExecutableJob(String)"})
  public void testMoveTimerToExecutableJob_thenReturnJobEntityImpl() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    JobEntityImpl jobEntityImpl = new JobEntityImpl();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<JobEntity>>any()))
        .thenReturn(jobEntityImpl);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Job actualMoveTimerToExecutableJobResult = managementServiceImpl.moveTimerToExecutableJob("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(jobEntityImpl, actualMoveTimerToExecutableJobResult);
  }

  /**
   * Test {@link ManagementServiceImpl#moveJobToDeadLetterJob(String)}.
   *
   * <ul>
   *   <li>Then calls {@link TransactionManager#getStatus()}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#moveJobToDeadLetterJob(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Job ManagementServiceImpl.moveJobToDeadLetterJob(String)"})
  public void testMoveJobToDeadLetterJob_thenCallsGetStatus() throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getStatus()).thenReturn(1);

    CommandInterceptor next = mock(CommandInterceptor.class);
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    when(next.execute(Mockito.<CommandConfig>any(), Mockito.<Command<DeadLetterJobEntity>>any()))
        .thenReturn(deadLetterJobEntityImpl);

    JtaTransactionInterceptor commandInterceptor =
        new JtaTransactionInterceptor(transactionManager);
    commandInterceptor.setNext(next);

    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(new CommandConfig(), mock(CommandInterceptor.class));
    commandExecutor.setFirst(commandInterceptor);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Job actualMoveJobToDeadLetterJobResult = managementServiceImpl.moveJobToDeadLetterJob("42");

    // Assert
    verify(transactionManager).getStatus();
    verify(next).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(deadLetterJobEntityImpl, actualMoveJobToDeadLetterJobResult);
  }

  /**
   * Test {@link ManagementServiceImpl#moveJobToDeadLetterJob(String)}.
   *
   * <ul>
   *   <li>Then return {@link DeadLetterJobEntityImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#moveJobToDeadLetterJob(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Job ManagementServiceImpl.moveJobToDeadLetterJob(String)"})
  public void testMoveJobToDeadLetterJob_thenReturnDeadLetterJobEntityImpl() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<DeadLetterJobEntity>>any()))
        .thenReturn(deadLetterJobEntityImpl);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Job actualMoveJobToDeadLetterJobResult = managementServiceImpl.moveJobToDeadLetterJob("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(deadLetterJobEntityImpl, actualMoveJobToDeadLetterJobResult);
  }

  /**
   * Test {@link ManagementServiceImpl#moveDeadLetterJobToExecutableJob(String, int)}.
   *
   * <ul>
   *   <li>Then calls {@link TransactionManager#getStatus()}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#moveDeadLetterJobToExecutableJob(String,
   * int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Job ManagementServiceImpl.moveDeadLetterJobToExecutableJob(String, int)"})
  public void testMoveDeadLetterJobToExecutableJob_thenCallsGetStatus() throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getStatus()).thenReturn(1);

    CommandInterceptor next = mock(CommandInterceptor.class);
    JobEntityImpl jobEntityImpl = new JobEntityImpl();
    when(next.execute(Mockito.<CommandConfig>any(), Mockito.<Command<JobEntity>>any()))
        .thenReturn(jobEntityImpl);

    JtaTransactionInterceptor commandInterceptor =
        new JtaTransactionInterceptor(transactionManager);
    commandInterceptor.setNext(next);

    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(new CommandConfig(), mock(CommandInterceptor.class));
    commandExecutor.setFirst(commandInterceptor);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Job actualMoveDeadLetterJobToExecutableJobResult =
        managementServiceImpl.moveDeadLetterJobToExecutableJob("42", 1);

    // Assert
    verify(transactionManager).getStatus();
    verify(next).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(jobEntityImpl, actualMoveDeadLetterJobToExecutableJobResult);
  }

  /**
   * Test {@link ManagementServiceImpl#moveDeadLetterJobToExecutableJob(String, int)}.
   *
   * <ul>
   *   <li>Then return {@link JobEntityImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#moveDeadLetterJobToExecutableJob(String,
   * int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Job ManagementServiceImpl.moveDeadLetterJobToExecutableJob(String, int)"})
  public void testMoveDeadLetterJobToExecutableJob_thenReturnJobEntityImpl() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    JobEntityImpl jobEntityImpl = new JobEntityImpl();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<JobEntity>>any()))
        .thenReturn(jobEntityImpl);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Job actualMoveDeadLetterJobToExecutableJobResult =
        managementServiceImpl.moveDeadLetterJobToExecutableJob("42", 1);

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(jobEntityImpl, actualMoveDeadLetterJobToExecutableJobResult);
  }

  /**
   * Test {@link ManagementServiceImpl#deleteJob(String)}.
   *
   * <ul>
   *   <li>Given {@link CommandInterceptor} {@link CommandInterceptor#execute(CommandConfig,
   *       Command)} return {@link JSONObject#NULL}.
   *   <li>Then calls {@link CommandInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#deleteJob(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ManagementServiceImpl.deleteJob(String)"})
  public void testDeleteJob_givenCommandInterceptorExecuteReturnNull_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    managementServiceImpl.deleteJob("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link ManagementServiceImpl#deleteJob(String)}.
   *
   * <ul>
   *   <li>Then calls {@link TransactionManager#getStatus()}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#deleteJob(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ManagementServiceImpl.deleteJob(String)"})
  public void testDeleteJob_thenCallsGetStatus() throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getStatus()).thenReturn(1);

    CommandInterceptor next = mock(CommandInterceptor.class);
    when(next.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);

    JtaTransactionInterceptor commandInterceptor =
        new JtaTransactionInterceptor(transactionManager);
    commandInterceptor.setNext(next);

    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(new CommandConfig(), mock(CommandInterceptor.class));
    commandExecutor.setFirst(commandInterceptor);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    managementServiceImpl.deleteJob("42");

    // Assert
    verify(transactionManager).getStatus();
    verify(next).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link ManagementServiceImpl#deleteTimerJob(String)}.
   *
   * <ul>
   *   <li>Given {@link CommandInterceptor} {@link CommandInterceptor#execute(CommandConfig,
   *       Command)} return {@link JSONObject#NULL}.
   *   <li>Then calls {@link CommandInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#deleteTimerJob(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ManagementServiceImpl.deleteTimerJob(String)"})
  public void testDeleteTimerJob_givenCommandInterceptorExecuteReturnNull_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    managementServiceImpl.deleteTimerJob("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link ManagementServiceImpl#deleteTimerJob(String)}.
   *
   * <ul>
   *   <li>Then calls {@link TransactionManager#getStatus()}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#deleteTimerJob(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ManagementServiceImpl.deleteTimerJob(String)"})
  public void testDeleteTimerJob_thenCallsGetStatus() throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getStatus()).thenReturn(1);

    CommandInterceptor next = mock(CommandInterceptor.class);
    when(next.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);

    JtaTransactionInterceptor commandInterceptor =
        new JtaTransactionInterceptor(transactionManager);
    commandInterceptor.setNext(next);

    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(new CommandConfig(), mock(CommandInterceptor.class));
    commandExecutor.setFirst(commandInterceptor);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    managementServiceImpl.deleteTimerJob("42");

    // Assert
    verify(transactionManager).getStatus();
    verify(next).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link ManagementServiceImpl#deleteDeadLetterJob(String)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#deleteDeadLetterJob(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ManagementServiceImpl.deleteDeadLetterJob(String)"})
  public void testDeleteDeadLetterJob_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    managementServiceImpl.deleteDeadLetterJob("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link ManagementServiceImpl#deleteDeadLetterJob(String)}.
   *
   * <ul>
   *   <li>Then calls {@link TransactionManager#getStatus()}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#deleteDeadLetterJob(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ManagementServiceImpl.deleteDeadLetterJob(String)"})
  public void testDeleteDeadLetterJob_thenCallsGetStatus() throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getStatus()).thenReturn(1);

    CommandInterceptor next = mock(CommandInterceptor.class);
    when(next.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);

    JtaTransactionInterceptor commandInterceptor =
        new JtaTransactionInterceptor(transactionManager);
    commandInterceptor.setNext(next);

    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(new CommandConfig(), mock(CommandInterceptor.class));
    commandExecutor.setFirst(commandInterceptor);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    managementServiceImpl.deleteDeadLetterJob("42");

    // Assert
    verify(transactionManager).getStatus();
    verify(next).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link ManagementServiceImpl#setJobRetries(String, int)}.
   *
   * <ul>
   *   <li>Given {@link CommandInterceptor} {@link CommandInterceptor#execute(CommandConfig,
   *       Command)} return {@code null}.
   *   <li>Then calls {@link CommandInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#setJobRetries(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ManagementServiceImpl.setJobRetries(String, int)"})
  public void testSetJobRetries_givenCommandInterceptorExecuteReturnNull_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    managementServiceImpl.setJobRetries("42", 1);

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link ManagementServiceImpl#setJobRetries(String, int)}.
   *
   * <ul>
   *   <li>Then calls {@link TransactionManager#getStatus()}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#setJobRetries(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ManagementServiceImpl.setJobRetries(String, int)"})
  public void testSetJobRetries_thenCallsGetStatus() throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getStatus()).thenReturn(1);

    CommandInterceptor next = mock(CommandInterceptor.class);
    when(next.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any())).thenReturn(null);

    JtaTransactionInterceptor commandInterceptor =
        new JtaTransactionInterceptor(transactionManager);
    commandInterceptor.setNext(next);

    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(new CommandConfig(), mock(CommandInterceptor.class));
    commandExecutor.setFirst(commandInterceptor);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    managementServiceImpl.setJobRetries("42", 1);

    // Assert
    verify(transactionManager).getStatus();
    verify(next).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link ManagementServiceImpl#setTimerJobRetries(String, int)}.
   *
   * <ul>
   *   <li>Given {@link CommandInterceptor} {@link CommandInterceptor#execute(CommandConfig,
   *       Command)} return {@code null}.
   *   <li>Then calls {@link CommandInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#setTimerJobRetries(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ManagementServiceImpl.setTimerJobRetries(String, int)"})
  public void testSetTimerJobRetries_givenCommandInterceptorExecuteReturnNull_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    managementServiceImpl.setTimerJobRetries("42", 1);

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link ManagementServiceImpl#setTimerJobRetries(String, int)}.
   *
   * <ul>
   *   <li>Then calls {@link TransactionManager#getStatus()}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#setTimerJobRetries(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ManagementServiceImpl.setTimerJobRetries(String, int)"})
  public void testSetTimerJobRetries_thenCallsGetStatus() throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getStatus()).thenReturn(1);

    CommandInterceptor next = mock(CommandInterceptor.class);
    when(next.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any())).thenReturn(null);

    JtaTransactionInterceptor commandInterceptor =
        new JtaTransactionInterceptor(transactionManager);
    commandInterceptor.setNext(next);

    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(new CommandConfig(), mock(CommandInterceptor.class));
    commandExecutor.setFirst(commandInterceptor);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    managementServiceImpl.setTimerJobRetries("42", 1);

    // Assert
    verify(transactionManager).getStatus();
    verify(next).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link ManagementServiceImpl#createTablePageQuery()}.
   *
   * <p>Method under test: {@link ManagementServiceImpl#createTablePageQuery()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TablePageQuery ManagementServiceImpl.createTablePageQuery()"})
  public void testCreateTablePageQuery() {
    // Arrange and Act
    TablePageQuery actualCreateTablePageQueryResult =
        new ManagementServiceImpl().createTablePageQuery();

    // Assert
    assertTrue(actualCreateTablePageQueryResult instanceof TablePageQueryImpl);
    assertNull(((TablePageQueryImpl) actualCreateTablePageQueryResult).getOrder());
    assertNull(((TablePageQueryImpl) actualCreateTablePageQueryResult).getTableName());
    assertNull(((TablePageQueryImpl) actualCreateTablePageQueryResult).commandExecutor);
    assertEquals(0, ((TablePageQueryImpl) actualCreateTablePageQueryResult).firstResult);
    assertEquals(0, ((TablePageQueryImpl) actualCreateTablePageQueryResult).maxResults);
  }

  /**
   * Test {@link ManagementServiceImpl#createJobQuery()}.
   *
   * <p>Method under test: {@link ManagementServiceImpl#createJobQuery()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JobQuery ManagementServiceImpl.createJobQuery()"})
  public void testCreateJobQuery() {
    // Arrange and Act
    JobQuery actualCreateJobQueryResult = new ManagementServiceImpl().createJobQuery();

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
   * Test {@link ManagementServiceImpl#createTimerJobQuery()}.
   *
   * <p>Method under test: {@link ManagementServiceImpl#createTimerJobQuery()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerJobQuery ManagementServiceImpl.createTimerJobQuery()"})
  public void testCreateTimerJobQuery() {
    // Arrange and Act
    TimerJobQuery actualCreateTimerJobQueryResult =
        new ManagementServiceImpl().createTimerJobQuery();

    // Assert
    assertTrue(actualCreateTimerJobQueryResult instanceof TimerJobQueryImpl);
    assertEquals("RES.ID_ asc", ((TimerJobQueryImpl) actualCreateTimerJobQueryResult).getOrderBy());
    assertEquals(
        "RES.ID_ asc", ((TimerJobQueryImpl) actualCreateTimerJobQueryResult).getOrderByColumns());
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
    assertEquals(
        Integer.MAX_VALUE, ((TimerJobQueryImpl) actualCreateTimerJobQueryResult).getLastRow());
    assertEquals(
        Integer.MAX_VALUE, ((TimerJobQueryImpl) actualCreateTimerJobQueryResult).getMaxResults());
  }

  /**
   * Test {@link ManagementServiceImpl#createSuspendedJobQuery()}.
   *
   * <p>Method under test: {@link ManagementServiceImpl#createSuspendedJobQuery()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"SuspendedJobQuery ManagementServiceImpl.createSuspendedJobQuery()"})
  public void testCreateSuspendedJobQuery() {
    // Arrange and Act
    SuspendedJobQuery actualCreateSuspendedJobQueryResult =
        new ManagementServiceImpl().createSuspendedJobQuery();

    // Assert
    assertTrue(actualCreateSuspendedJobQueryResult instanceof SuspendedJobQueryImpl);
    assertEquals(
        "RES.ID_ asc", ((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getOrderBy());
    assertEquals(
        "RES.ID_ asc",
        ((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getOrderByColumns());
    assertNull(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getParameter());
    assertNull(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getDatabaseType());
    assertNull(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getExceptionMessage());
    assertNull(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getExecutionId());
    assertNull(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getId());
    assertNull(
        ((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getProcessDefinitionId());
    assertNull(
        ((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getProcessInstanceId());
    assertNull(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getTenantId());
    assertNull(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getTenantIdLike());
    assertNull(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).orderBy);
    assertNull(
        ((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getDuedateHigherThan());
    assertNull(
        ((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult)
            .getDuedateHigherThanOrEqual());
    assertNull(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getDuedateLowerThan());
    assertNull(
        ((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getDuedateLowerThanOrEqual());
    assertNull(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).nullHandlingOnOrder);
    assertNull(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).resultType);
    assertNull(((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).commandContext);
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
    assertEquals(
        Integer.MAX_VALUE,
        ((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getLastRow());
    assertEquals(
        Integer.MAX_VALUE,
        ((SuspendedJobQueryImpl) actualCreateSuspendedJobQueryResult).getMaxResults());
  }

  /**
   * Test {@link ManagementServiceImpl#createDeadLetterJobQuery()}.
   *
   * <p>Method under test: {@link ManagementServiceImpl#createDeadLetterJobQuery()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeadLetterJobQuery ManagementServiceImpl.createDeadLetterJobQuery()"})
  public void testCreateDeadLetterJobQuery() {
    // Arrange and Act
    DeadLetterJobQuery actualCreateDeadLetterJobQueryResult =
        new ManagementServiceImpl().createDeadLetterJobQuery();

    // Assert
    assertTrue(actualCreateDeadLetterJobQueryResult instanceof DeadLetterJobQueryImpl);
    assertEquals(
        "RES.ID_ asc",
        ((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getOrderBy());
    assertEquals(
        "RES.ID_ asc",
        ((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getOrderByColumns());
    assertNull(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getParameter());
    assertNull(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getDatabaseType());
    assertNull(
        ((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getExceptionMessage());
    assertNull(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getExecutionId());
    assertNull(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getId());
    assertNull(
        ((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getProcessDefinitionId());
    assertNull(
        ((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getProcessInstanceId());
    assertNull(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getTenantId());
    assertNull(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getTenantIdLike());
    assertNull(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).orderBy);
    assertNull(
        ((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getDuedateHigherThan());
    assertNull(
        ((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult)
            .getDuedateHigherThanOrEqual());
    assertNull(
        ((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getDuedateLowerThan());
    assertNull(
        ((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult)
            .getDuedateLowerThanOrEqual());
    assertNull(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).nullHandlingOnOrder);
    assertNull(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).resultType);
    assertNull(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).commandContext);
    assertNull(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).commandExecutor);
    assertNull(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).orderProperty);
    assertEquals(
        0, ((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getFirstResult());
    assertEquals(1, ((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getFirstRow());
    assertFalse(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getExecutable());
    assertFalse(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).isOnlyMessages());
    assertFalse(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).isOnlyTimers());
    assertFalse(((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).isWithException());
    assertFalse(
        ((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).isWithoutTenantId());
    assertEquals(
        Integer.MAX_VALUE,
        ((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getLastRow());
    assertEquals(
        Integer.MAX_VALUE,
        ((DeadLetterJobQueryImpl) actualCreateDeadLetterJobQueryResult).getMaxResults());
  }

  /**
   * Test {@link ManagementServiceImpl#getJobExceptionStacktrace(String)}.
   *
   * <ul>
   *   <li>Then calls {@link TransactionManager#getStatus()}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#getJobExceptionStacktrace(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ManagementServiceImpl.getJobExceptionStacktrace(String)"})
  public void testGetJobExceptionStacktrace_thenCallsGetStatus() throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getStatus()).thenReturn(1);

    CommandInterceptor next = mock(CommandInterceptor.class);
    when(next.execute(Mockito.<CommandConfig>any(), Mockito.<Command<String>>any()))
        .thenReturn("Execute");

    JtaTransactionInterceptor commandInterceptor =
        new JtaTransactionInterceptor(transactionManager);
    commandInterceptor.setNext(next);

    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(new CommandConfig(), mock(CommandInterceptor.class));
    commandExecutor.setFirst(commandInterceptor);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    String actualJobExceptionStacktrace = managementServiceImpl.getJobExceptionStacktrace("42");

    // Assert
    verify(transactionManager).getStatus();
    verify(next).execute(isA(CommandConfig.class), isA(Command.class));
    assertEquals("Execute", actualJobExceptionStacktrace);
  }

  /**
   * Test {@link ManagementServiceImpl#getJobExceptionStacktrace(String)}.
   *
   * <ul>
   *   <li>Then return {@code Execute}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#getJobExceptionStacktrace(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ManagementServiceImpl.getJobExceptionStacktrace(String)"})
  public void testGetJobExceptionStacktrace_thenReturnExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<String>>any()))
        .thenReturn("Execute");
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    String actualJobExceptionStacktrace = managementServiceImpl.getJobExceptionStacktrace("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertEquals("Execute", actualJobExceptionStacktrace);
  }

  /**
   * Test {@link ManagementServiceImpl#getTimerJobExceptionStacktrace(String)}.
   *
   * <ul>
   *   <li>Then calls {@link TransactionManager#getStatus()}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#getTimerJobExceptionStacktrace(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ManagementServiceImpl.getTimerJobExceptionStacktrace(String)"})
  public void testGetTimerJobExceptionStacktrace_thenCallsGetStatus() throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getStatus()).thenReturn(1);

    CommandInterceptor next = mock(CommandInterceptor.class);
    when(next.execute(Mockito.<CommandConfig>any(), Mockito.<Command<String>>any()))
        .thenReturn("Execute");

    JtaTransactionInterceptor commandInterceptor =
        new JtaTransactionInterceptor(transactionManager);
    commandInterceptor.setNext(next);

    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(new CommandConfig(), mock(CommandInterceptor.class));
    commandExecutor.setFirst(commandInterceptor);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    String actualTimerJobExceptionStacktrace =
        managementServiceImpl.getTimerJobExceptionStacktrace("42");

    // Assert
    verify(transactionManager).getStatus();
    verify(next).execute(isA(CommandConfig.class), isA(Command.class));
    assertEquals("Execute", actualTimerJobExceptionStacktrace);
  }

  /**
   * Test {@link ManagementServiceImpl#getTimerJobExceptionStacktrace(String)}.
   *
   * <ul>
   *   <li>Then return {@code Execute}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#getTimerJobExceptionStacktrace(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ManagementServiceImpl.getTimerJobExceptionStacktrace(String)"})
  public void testGetTimerJobExceptionStacktrace_thenReturnExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<String>>any()))
        .thenReturn("Execute");
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    String actualTimerJobExceptionStacktrace =
        managementServiceImpl.getTimerJobExceptionStacktrace("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertEquals("Execute", actualTimerJobExceptionStacktrace);
  }

  /**
   * Test {@link ManagementServiceImpl#getSuspendedJobExceptionStacktrace(String)}.
   *
   * <ul>
   *   <li>Then calls {@link TransactionManager#getStatus()}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#getSuspendedJobExceptionStacktrace(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ManagementServiceImpl.getSuspendedJobExceptionStacktrace(String)"})
  public void testGetSuspendedJobExceptionStacktrace_thenCallsGetStatus() throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getStatus()).thenReturn(1);

    CommandInterceptor next = mock(CommandInterceptor.class);
    when(next.execute(Mockito.<CommandConfig>any(), Mockito.<Command<String>>any()))
        .thenReturn("Execute");

    JtaTransactionInterceptor commandInterceptor =
        new JtaTransactionInterceptor(transactionManager);
    commandInterceptor.setNext(next);

    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(new CommandConfig(), mock(CommandInterceptor.class));
    commandExecutor.setFirst(commandInterceptor);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    String actualSuspendedJobExceptionStacktrace =
        managementServiceImpl.getSuspendedJobExceptionStacktrace("42");

    // Assert
    verify(transactionManager).getStatus();
    verify(next).execute(isA(CommandConfig.class), isA(Command.class));
    assertEquals("Execute", actualSuspendedJobExceptionStacktrace);
  }

  /**
   * Test {@link ManagementServiceImpl#getSuspendedJobExceptionStacktrace(String)}.
   *
   * <ul>
   *   <li>Then return {@code Execute}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#getSuspendedJobExceptionStacktrace(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ManagementServiceImpl.getSuspendedJobExceptionStacktrace(String)"})
  public void testGetSuspendedJobExceptionStacktrace_thenReturnExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<String>>any()))
        .thenReturn("Execute");
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    String actualSuspendedJobExceptionStacktrace =
        managementServiceImpl.getSuspendedJobExceptionStacktrace("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertEquals("Execute", actualSuspendedJobExceptionStacktrace);
  }

  /**
   * Test {@link ManagementServiceImpl#getDeadLetterJobExceptionStacktrace(String)}.
   *
   * <ul>
   *   <li>Then calls {@link TransactionManager#getStatus()}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#getDeadLetterJobExceptionStacktrace(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ManagementServiceImpl.getDeadLetterJobExceptionStacktrace(String)"})
  public void testGetDeadLetterJobExceptionStacktrace_thenCallsGetStatus() throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getStatus()).thenReturn(1);

    CommandInterceptor next = mock(CommandInterceptor.class);
    when(next.execute(Mockito.<CommandConfig>any(), Mockito.<Command<String>>any()))
        .thenReturn("Execute");

    JtaTransactionInterceptor commandInterceptor =
        new JtaTransactionInterceptor(transactionManager);
    commandInterceptor.setNext(next);

    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(new CommandConfig(), mock(CommandInterceptor.class));
    commandExecutor.setFirst(commandInterceptor);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    String actualDeadLetterJobExceptionStacktrace =
        managementServiceImpl.getDeadLetterJobExceptionStacktrace("42");

    // Assert
    verify(transactionManager).getStatus();
    verify(next).execute(isA(CommandConfig.class), isA(Command.class));
    assertEquals("Execute", actualDeadLetterJobExceptionStacktrace);
  }

  /**
   * Test {@link ManagementServiceImpl#getDeadLetterJobExceptionStacktrace(String)}.
   *
   * <ul>
   *   <li>Then return {@code Execute}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#getDeadLetterJobExceptionStacktrace(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ManagementServiceImpl.getDeadLetterJobExceptionStacktrace(String)"})
  public void testGetDeadLetterJobExceptionStacktrace_thenReturnExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<String>>any()))
        .thenReturn("Execute");
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    String actualDeadLetterJobExceptionStacktrace =
        managementServiceImpl.getDeadLetterJobExceptionStacktrace("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertEquals("Execute", actualDeadLetterJobExceptionStacktrace);
  }

  /**
   * Test {@link ManagementServiceImpl#getProperties()}.
   *
   * <ul>
   *   <li>Given {@link CommandInterceptor} {@link CommandInterceptor#execute(CommandConfig,
   *       Command)} return {@link HashMap#HashMap()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#getProperties()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ManagementServiceImpl.getProperties()"})
  public void testGetProperties_givenCommandInterceptorExecuteReturnHashMap_thenReturnEmpty() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Map<String, String>>>any()))
        .thenReturn(new HashMap<>());
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Map<String, String> actualProperties = managementServiceImpl.getProperties();

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualProperties.isEmpty());
  }

  /**
   * Test {@link ManagementServiceImpl#getProperties()}.
   *
   * <ul>
   *   <li>Then calls {@link TransactionManager#getStatus()}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#getProperties()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ManagementServiceImpl.getProperties()"})
  public void testGetProperties_thenCallsGetStatus() throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getStatus()).thenReturn(1);

    CommandInterceptor next = mock(CommandInterceptor.class);
    when(next.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Map<String, String>>>any()))
        .thenReturn(new HashMap<>());

    JtaTransactionInterceptor commandInterceptor =
        new JtaTransactionInterceptor(transactionManager);
    commandInterceptor.setNext(next);

    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(new CommandConfig(), mock(CommandInterceptor.class));
    commandExecutor.setFirst(commandInterceptor);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Map<String, String> actualProperties = managementServiceImpl.getProperties();

    // Assert
    verify(transactionManager).getStatus();
    verify(next).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualProperties.isEmpty());
  }

  /**
   * Test {@link ManagementServiceImpl#databaseSchemaUpgrade(Connection, String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link TransactionManager#getStatus()}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#databaseSchemaUpgrade(Connection, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String ManagementServiceImpl.databaseSchemaUpgrade(Connection, String, String)"
  })
  public void testDatabaseSchemaUpgrade_thenCallsGetStatus() throws SystemException {
    // Arrange
    CommandConfig defaultConfig = mock(CommandConfig.class);
    when(defaultConfig.transactionNotSupported()).thenReturn(new CommandConfig());

    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getStatus()).thenReturn(1);

    CommandInterceptor next = mock(CommandInterceptor.class);
    when(next.execute(Mockito.<CommandConfig>any(), Mockito.<Command<String>>any()))
        .thenReturn("Execute");

    JtaTransactionInterceptor commandInterceptor =
        new JtaTransactionInterceptor(transactionManager);
    commandInterceptor.setNext(next);

    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, mock(CommandInterceptor.class));
    commandExecutor.setFirst(commandInterceptor);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    String actualDatabaseSchemaUpgradeResult =
        managementServiceImpl.databaseSchemaUpgrade(mock(Connection.class), "Catalog", "Schema");

    // Assert
    verify(transactionManager).getStatus();
    verify(defaultConfig).transactionNotSupported();
    verify(next).execute(isA(CommandConfig.class), isA(Command.class));
    assertEquals("Execute", actualDatabaseSchemaUpgradeResult);
  }

  /**
   * Test {@link ManagementServiceImpl#databaseSchemaUpgrade(Connection, String, String)}.
   *
   * <ul>
   *   <li>Then return {@code Execute}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#databaseSchemaUpgrade(Connection, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String ManagementServiceImpl.databaseSchemaUpgrade(Connection, String, String)"
  })
  public void testDatabaseSchemaUpgrade_thenReturnExecute() {
    // Arrange
    CommandConfig defaultConfig = mock(CommandConfig.class);
    when(defaultConfig.transactionNotSupported()).thenReturn(new CommandConfig());

    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<String>>any()))
        .thenReturn("Execute");

    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(defaultConfig, first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    String actualDatabaseSchemaUpgradeResult =
        managementServiceImpl.databaseSchemaUpgrade(mock(Connection.class), "Catalog", "Schema");

    // Assert
    verify(defaultConfig).transactionNotSupported();
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertEquals("Execute", actualDatabaseSchemaUpgradeResult);
  }

  /**
   * Test {@link ManagementServiceImpl#executeCommand(Command)} with {@code command}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#executeCommand(Command)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ManagementServiceImpl.executeCommand(Command)"})
  public void testExecuteCommandWithCommand_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new ManagementServiceImpl().executeCommand(null));
  }

  /**
   * Test {@link ManagementServiceImpl#executeCommand(CommandConfig, Command)} with {@code config},
   * {@code command}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#executeCommand(CommandConfig, Command)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ManagementServiceImpl.executeCommand(CommandConfig, Command)"})
  public void testExecuteCommandWithConfigCommand_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> managementServiceImpl.executeCommand(new CommandConfig(), null));
  }

  /**
   * Test {@link ManagementServiceImpl#executeCommand(CommandConfig, Command)} with {@code config},
   * {@code command}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#executeCommand(CommandConfig, Command)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ManagementServiceImpl.executeCommand(CommandConfig, Command)"})
  public void testExecuteCommandWithConfigCommand_thenThrowActivitiIllegalArgumentException2() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new ManagementServiceImpl().executeCommand(null, mock(Command.class)));
  }

  /**
   * Test {@link ManagementServiceImpl#executeCustomSql(CustomSqlExecution)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#executeCustomSql(CustomSqlExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ManagementServiceImpl.executeCustomSql(CustomSqlExecution)"})
  public void testExecuteCustomSql_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();

    CustomSqlExecution<Object, Object> customSqlExecution = mock(CustomSqlExecution.class);
    when(customSqlExecution.getMapperClass())
        .thenThrow(new ActivitiIllegalArgumentException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> managementServiceImpl.executeCustomSql(customSqlExecution));
    verify(customSqlExecution).getMapperClass();
  }

  /**
   * Test {@link ManagementServiceImpl#getEventLogEntries(Long, Long)}.
   *
   * <ul>
   *   <li>Then calls {@link TransactionManager#getStatus()}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#getEventLogEntries(Long, Long)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ManagementServiceImpl.getEventLogEntries(Long, Long)"})
  public void testGetEventLogEntries_thenCallsGetStatus() throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getStatus()).thenReturn(1);

    CommandInterceptor next = mock(CommandInterceptor.class);
    when(next.execute(Mockito.<CommandConfig>any(), Mockito.<Command<List<EventLogEntry>>>any()))
        .thenReturn(new ArrayList<>());

    JtaTransactionInterceptor commandInterceptor =
        new JtaTransactionInterceptor(transactionManager);
    commandInterceptor.setNext(next);

    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(new CommandConfig(), mock(CommandInterceptor.class));
    commandExecutor.setFirst(commandInterceptor);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    List<EventLogEntry> actualEventLogEntries = managementServiceImpl.getEventLogEntries(1L, 3L);

    // Assert
    verify(transactionManager).getStatus();
    verify(next).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualEventLogEntries.isEmpty());
  }

  /**
   * Test {@link ManagementServiceImpl#getEventLogEntries(Long, Long)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#getEventLogEntries(Long, Long)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ManagementServiceImpl.getEventLogEntries(Long, Long)"})
  public void testGetEventLogEntries_thenReturnEmpty() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<List<EventLogEntry>>>any()))
        .thenReturn(new ArrayList<>());
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    List<EventLogEntry> actualEventLogEntries = managementServiceImpl.getEventLogEntries(1L, 3L);

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualEventLogEntries.isEmpty());
  }

  /**
   * Test {@link ManagementServiceImpl#getEventLogEntriesByProcessInstanceId(String)}.
   *
   * <ul>
   *   <li>Then calls {@link TransactionManager#getStatus()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ManagementServiceImpl#getEventLogEntriesByProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ManagementServiceImpl.getEventLogEntriesByProcessInstanceId(String)"})
  public void testGetEventLogEntriesByProcessInstanceId_thenCallsGetStatus()
      throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getStatus()).thenReturn(1);

    CommandInterceptor next = mock(CommandInterceptor.class);
    when(next.execute(Mockito.<CommandConfig>any(), Mockito.<Command<List<EventLogEntry>>>any()))
        .thenReturn(new ArrayList<>());

    JtaTransactionInterceptor commandInterceptor =
        new JtaTransactionInterceptor(transactionManager);
    commandInterceptor.setNext(next);

    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(new CommandConfig(), mock(CommandInterceptor.class));
    commandExecutor.setFirst(commandInterceptor);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    List<EventLogEntry> actualEventLogEntriesByProcessInstanceId =
        managementServiceImpl.getEventLogEntriesByProcessInstanceId("42");

    // Assert
    verify(transactionManager).getStatus();
    verify(next).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualEventLogEntriesByProcessInstanceId.isEmpty());
  }

  /**
   * Test {@link ManagementServiceImpl#getEventLogEntriesByProcessInstanceId(String)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ManagementServiceImpl#getEventLogEntriesByProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ManagementServiceImpl.getEventLogEntriesByProcessInstanceId(String)"})
  public void testGetEventLogEntriesByProcessInstanceId_thenReturnEmpty() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<List<EventLogEntry>>>any()))
        .thenReturn(new ArrayList<>());
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    List<EventLogEntry> actualEventLogEntriesByProcessInstanceId =
        managementServiceImpl.getEventLogEntriesByProcessInstanceId("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualEventLogEntriesByProcessInstanceId.isEmpty());
  }

  /**
   * Test {@link ManagementServiceImpl#deleteEventLogEntry(long)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#deleteEventLogEntry(long)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ManagementServiceImpl.deleteEventLogEntry(long)"})
  public void testDeleteEventLogEntry_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    managementServiceImpl.deleteEventLogEntry(1L);

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link ManagementServiceImpl#deleteEventLogEntry(long)}.
   *
   * <ul>
   *   <li>Then calls {@link TransactionManager#getStatus()}.
   * </ul>
   *
   * <p>Method under test: {@link ManagementServiceImpl#deleteEventLogEntry(long)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ManagementServiceImpl.deleteEventLogEntry(long)"})
  public void testDeleteEventLogEntry_thenCallsGetStatus() throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getStatus()).thenReturn(1);

    CommandInterceptor next = mock(CommandInterceptor.class);
    when(next.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any())).thenReturn(null);

    JtaTransactionInterceptor commandInterceptor =
        new JtaTransactionInterceptor(transactionManager);
    commandInterceptor.setNext(next);

    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(new CommandConfig(), mock(CommandInterceptor.class));
    commandExecutor.setFirst(commandInterceptor);

    ManagementServiceImpl managementServiceImpl = new ManagementServiceImpl();
    managementServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    managementServiceImpl.deleteEventLogEntry(1L);

    // Assert
    verify(transactionManager).getStatus();
    verify(next).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test new {@link ManagementServiceImpl} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link ManagementServiceImpl}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ManagementServiceImpl.<init>()"})
  public void testNewManagementServiceImpl() {
    // Arrange, Act and Assert
    assertNull(new ManagementServiceImpl().getCommandExecutor());
  }
}
