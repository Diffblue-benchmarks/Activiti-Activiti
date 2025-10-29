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

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.engine.impl.asyncexecutor.AsyncExecutor;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.persistence.entity.JobEntityImpl;
import org.activiti.engine.runtime.Job;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

public class SpringCallerRunsRejectedJobsHandlerDiffblueTest {
  /**
   * Method under test:
   * {@link SpringCallerRunsRejectedJobsHandler#jobRejected(AsyncExecutor, Job)}
   */
  @Test
  public void testJobRejected() {
    // Arrange
    SpringCallerRunsRejectedJobsHandler springCallerRunsRejectedJobsHandler = new SpringCallerRunsRejectedJobsHandler();
    JobEntityImpl job = mock(JobEntityImpl.class);
    when(job.getId()).thenReturn("42");

    // Act
    springCallerRunsRejectedJobsHandler.jobRejected(null, job);

    // Assert that nothing has changed
    verify(job).getId();
  }

  /**
   * Method under test:
   * {@link SpringCallerRunsRejectedJobsHandler#jobRejected(AsyncExecutor, Job)}
   */
  @Test
  public void testJobRejected2() {
    // Arrange
    SpringCallerRunsRejectedJobsHandler springCallerRunsRejectedJobsHandler = new SpringCallerRunsRejectedJobsHandler();

    SpringAsyncExecutor asyncExecutor = new SpringAsyncExecutor();
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

  /**
   * Method under test:
   * {@link SpringCallerRunsRejectedJobsHandler#jobRejected(AsyncExecutor, Job)}
   */
  @Test
  public void testJobRejected3() {
    // Arrange
    SpringCallerRunsRejectedJobsHandler springCallerRunsRejectedJobsHandler = new SpringCallerRunsRejectedJobsHandler();
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    CommandConfig defaultConfig = new CommandConfig();
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(
        new CommandExecutorImpl(defaultConfig, new SpringTransactionInterceptor(new DataSourceTransactionManager())));

    SpringAsyncExecutor asyncExecutor = new SpringAsyncExecutor();
    asyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    JobEntityImpl job = mock(JobEntityImpl.class);
    when(job.isExclusive()).thenReturn(true);
    when(job.getId()).thenReturn("42");

    // Act
    springCallerRunsRejectedJobsHandler.jobRejected(asyncExecutor, job);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(job, atLeast(1)).getId();
    verify(job).isExclusive();
  }

  /**
   * Method under test:
   * {@link SpringCallerRunsRejectedJobsHandler#jobRejected(AsyncExecutor, Job)}
   */
  @Test
  public void testJobRejected4() {
    // Arrange
    SpringCallerRunsRejectedJobsHandler springCallerRunsRejectedJobsHandler = new SpringCallerRunsRejectedJobsHandler();
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    CommandConfig defaultConfig = new CommandConfig();
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    SpringAsyncExecutor asyncExecutor = new SpringAsyncExecutor();
    asyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    JobEntityImpl job = mock(JobEntityImpl.class);
    when(job.isExclusive()).thenReturn(true);
    when(job.getId()).thenReturn("42");

    // Act
    springCallerRunsRejectedJobsHandler.jobRejected(asyncExecutor, job);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(job, atLeast(1)).getId();
    verify(job).isExclusive();
  }

  /**
   * Method under test:
   * {@link SpringCallerRunsRejectedJobsHandler#jobRejected(AsyncExecutor, Job)}
   */
  @Test
  public void testJobRejected5() {
    // Arrange
    SpringCallerRunsRejectedJobsHandler springCallerRunsRejectedJobsHandler = new SpringCallerRunsRejectedJobsHandler();
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn("Execute");
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(new CommandConfig(), first));

    SpringAsyncExecutor asyncExecutor = new SpringAsyncExecutor();
    asyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    JobEntityImpl job = mock(JobEntityImpl.class);
    when(job.isExclusive()).thenReturn(true);
    when(job.getId()).thenReturn("42");

    // Act
    springCallerRunsRejectedJobsHandler.jobRejected(asyncExecutor, job);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(first, atLeast(1)).execute(isA(CommandConfig.class), Mockito.<Command<Object>>any());
    verify(job).getId();
    verify(job, atLeast(1)).isExclusive();
  }

  /**
   * Method under test:
   * {@link SpringCallerRunsRejectedJobsHandler#jobRejected(AsyncExecutor, Job)}
   */
  @Test
  public void testJobRejected6() {
    // Arrange
    SpringCallerRunsRejectedJobsHandler springCallerRunsRejectedJobsHandler = new SpringCallerRunsRejectedJobsHandler();
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn("Execute");
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(new CommandConfig(), first));

    SpringAsyncExecutor asyncExecutor = new SpringAsyncExecutor();
    asyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    JobEntityImpl job = mock(JobEntityImpl.class);
    when(job.isExclusive()).thenReturn(false);
    when(job.getId()).thenReturn("42");

    // Act
    springCallerRunsRejectedJobsHandler.jobRejected(asyncExecutor, job);

    // Assert
    verify(processEngineConfiguration).getCommandExecutor();
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    verify(job).getId();
    verify(job, atLeast(1)).isExclusive();
  }

  /**
   * Method under test:
   * {@link SpringCallerRunsRejectedJobsHandler#jobRejected(AsyncExecutor, Job)}
   */
  @Test
  public void testJobRejected7() {
    // Arrange
    SpringCallerRunsRejectedJobsHandler springCallerRunsRejectedJobsHandler = new SpringCallerRunsRejectedJobsHandler();
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(null);

    SpringAsyncExecutor asyncExecutor = new SpringAsyncExecutor();
    asyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    JobEntityImpl job = mock(JobEntityImpl.class);
    when(job.isExclusive()).thenReturn(false);
    when(job.getId()).thenReturn("42");

    // Act
    springCallerRunsRejectedJobsHandler.jobRejected(asyncExecutor, job);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(job, atLeast(1)).getId();
    verify(job).isExclusive();
  }

  /**
   * Method under test:
   * {@link SpringCallerRunsRejectedJobsHandler#jobRejected(AsyncExecutor, Job)}
   */
  @Test
  public void testJobRejected8() {
    // Arrange
    SpringCallerRunsRejectedJobsHandler springCallerRunsRejectedJobsHandler = new SpringCallerRunsRejectedJobsHandler();

    CommandExecutorImpl commandExecutorImpl = new CommandExecutorImpl(new CommandConfig(),
        mock(CommandInterceptor.class));
    commandExecutorImpl.setFirst(new SpringTransactionInterceptor(new DataSourceTransactionManager()));
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    SpringAsyncExecutor asyncExecutor = new SpringAsyncExecutor();
    asyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    JobEntityImpl job = mock(JobEntityImpl.class);
    when(job.isExclusive()).thenReturn(false);
    when(job.getId()).thenReturn("42");

    // Act
    springCallerRunsRejectedJobsHandler.jobRejected(asyncExecutor, job);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(job, atLeast(1)).getId();
    verify(job).isExclusive();
  }

  /**
   * Method under test:
   * {@link SpringCallerRunsRejectedJobsHandler#jobRejected(AsyncExecutor, Job)}
   */
  @Test
  public void testJobRejected9() {
    // Arrange
    SpringCallerRunsRejectedJobsHandler springCallerRunsRejectedJobsHandler = new SpringCallerRunsRejectedJobsHandler();

    CommandExecutorImpl commandExecutorImpl = new CommandExecutorImpl(new CommandConfig(),
        mock(CommandInterceptor.class));
    commandExecutorImpl.setFirst(new CommandContextInterceptor());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    SpringAsyncExecutor asyncExecutor = new SpringAsyncExecutor();
    asyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    JobEntityImpl job = mock(JobEntityImpl.class);
    when(job.isExclusive()).thenReturn(false);
    when(job.getId()).thenReturn("42");

    // Act
    springCallerRunsRejectedJobsHandler.jobRejected(asyncExecutor, job);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(job, atLeast(1)).getId();
    verify(job).isExclusive();
  }
}
