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
package org.activiti.spring;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.impl.asyncexecutor.AsyncExecutor;
import org.activiti.engine.impl.asyncexecutor.DefaultAsyncJobExecutor;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.persistence.entity.JobEntityImpl;
import org.activiti.engine.runtime.Job;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

public class SpringCallerRunsRejectedJobsHandlerDiffblueTest {
  /**
   * Test {@link SpringCallerRunsRejectedJobsHandler#jobRejected(AsyncExecutor, Job)}.
   *
   * <p>Method under test: {@link SpringCallerRunsRejectedJobsHandler#jobRejected(AsyncExecutor,
   * Job)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SpringCallerRunsRejectedJobsHandler.jobRejected(AsyncExecutor, Job)"})
  public void testJobRejected() {
    // Arrange
    SpringCallerRunsRejectedJobsHandler springCallerRunsRejectedJobsHandler =
        new SpringCallerRunsRejectedJobsHandler();

    SpringProcessEngineConfiguration processEngineConfiguration =
        new SpringProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(
            defaultConfig, new SpringTransactionInterceptor(new DataSourceTransactionManager()));
    processEngineConfiguration.setCommandExecutor(commandExecutor);

    DefaultAsyncJobExecutor asyncExecutor = new DefaultAsyncJobExecutor();
    asyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    JobEntityImpl job = mock(JobEntityImpl.class);
    when(job.isExclusive()).thenReturn(true);
    when(job.getId()).thenReturn("42");

    // Act
    springCallerRunsRejectedJobsHandler.jobRejected(asyncExecutor, job);

    // Assert
    verify(job, atLeast(1)).getId();
    verify(job).isExclusive();
  }

  /**
   * Test {@link SpringCallerRunsRejectedJobsHandler#jobRejected(AsyncExecutor, Job)}.
   *
   * <p>Method under test: {@link SpringCallerRunsRejectedJobsHandler#jobRejected(AsyncExecutor,
   * Job)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SpringCallerRunsRejectedJobsHandler.jobRejected(AsyncExecutor, Job)"})
  public void testJobRejected2() {
    // Arrange
    SpringCallerRunsRejectedJobsHandler springCallerRunsRejectedJobsHandler =
        new SpringCallerRunsRejectedJobsHandler();

    SpringProcessEngineConfiguration processEngineConfiguration =
        new SpringProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    processEngineConfiguration.setCommandExecutor(commandExecutor);

    DefaultAsyncJobExecutor asyncExecutor = new DefaultAsyncJobExecutor();
    asyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    JobEntityImpl job = mock(JobEntityImpl.class);
    when(job.isExclusive()).thenReturn(true);
    when(job.getId()).thenReturn("42");

    // Act
    springCallerRunsRejectedJobsHandler.jobRejected(asyncExecutor, job);

    // Assert
    verify(job, atLeast(1)).getId();
    verify(job).isExclusive();
  }

  /**
   * Test {@link SpringCallerRunsRejectedJobsHandler#jobRejected(AsyncExecutor, Job)}.
   *
   * <ul>
   *   <li>Given {@link CommandInterceptor} {@link CommandInterceptor#execute(CommandConfig,
   *       Command)} return {@code Execute}.
   *   <li>Then calls {@link CommandInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link SpringCallerRunsRejectedJobsHandler#jobRejected(AsyncExecutor,
   * Job)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SpringCallerRunsRejectedJobsHandler.jobRejected(AsyncExecutor, Job)"})
  public void testJobRejected_givenCommandInterceptorExecuteReturnExecute_thenCallsExecute() {
    // Arrange
    SpringCallerRunsRejectedJobsHandler springCallerRunsRejectedJobsHandler =
        new SpringCallerRunsRejectedJobsHandler();

    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn("Execute");
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    SpringProcessEngineConfiguration processEngineConfiguration =
        new SpringProcessEngineConfiguration();
    processEngineConfiguration.setCommandExecutor(commandExecutor);

    DefaultAsyncJobExecutor asyncExecutor = new DefaultAsyncJobExecutor();
    asyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    JobEntityImpl job = mock(JobEntityImpl.class);
    when(job.isExclusive()).thenReturn(true);
    when(job.getId()).thenReturn("42");

    // Act
    springCallerRunsRejectedJobsHandler.jobRejected(asyncExecutor, job);

    // Assert
    verify(first, atLeast(1)).execute(isA(CommandConfig.class), Mockito.<Command<Object>>any());
    verify(job).getId();
    verify(job, atLeast(1)).isExclusive();
  }

  /**
   * Test {@link SpringCallerRunsRejectedJobsHandler#jobRejected(AsyncExecutor, Job)}.
   *
   * <ul>
   *   <li>Given {@link RuntimeException#RuntimeException()}.
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link SpringCallerRunsRejectedJobsHandler#jobRejected(AsyncExecutor,
   * Job)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SpringCallerRunsRejectedJobsHandler.jobRejected(AsyncExecutor, Job)"})
  public void testJobRejected_givenRuntimeException_thenThrowRuntimeException() {
    // Arrange
    SpringCallerRunsRejectedJobsHandler springCallerRunsRejectedJobsHandler =
        new SpringCallerRunsRejectedJobsHandler();

    JobEntityImpl job = mock(JobEntityImpl.class);
    when(job.getId()).thenThrow(new RuntimeException());

    // Act and Assert
    assertThrows(
        RuntimeException.class, () -> springCallerRunsRejectedJobsHandler.jobRejected(null, job));
    verify(job).getId();
  }

  /**
   * Test {@link SpringCallerRunsRejectedJobsHandler#jobRejected(AsyncExecutor, Job)}.
   *
   * <ul>
   *   <li>Given {@link SpringProcessEngineConfiguration#SpringProcessEngineConfiguration()}.
   *   <li>Then calls {@link JobEntityImpl#isExclusive()}.
   * </ul>
   *
   * <p>Method under test: {@link SpringCallerRunsRejectedJobsHandler#jobRejected(AsyncExecutor,
   * Job)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SpringCallerRunsRejectedJobsHandler.jobRejected(AsyncExecutor, Job)"})
  public void testJobRejected_givenSpringProcessEngineConfiguration_thenCallsIsExclusive() {
    // Arrange
    SpringCallerRunsRejectedJobsHandler springCallerRunsRejectedJobsHandler =
        new SpringCallerRunsRejectedJobsHandler();

    DefaultAsyncJobExecutor asyncExecutor = new DefaultAsyncJobExecutor();
    asyncExecutor.setProcessEngineConfiguration(new SpringProcessEngineConfiguration());

    JobEntityImpl job = mock(JobEntityImpl.class);
    when(job.isExclusive()).thenReturn(true);
    when(job.getId()).thenReturn("42");

    // Act
    springCallerRunsRejectedJobsHandler.jobRejected(asyncExecutor, job);

    // Assert
    verify(job, atLeast(1)).getId();
    verify(job).isExclusive();
  }
}
