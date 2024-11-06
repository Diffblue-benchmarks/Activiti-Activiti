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
package org.activiti.engine.impl.asyncexecutor.multitenant;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.test.cfg.multitenant.DummyTenantInfoHolder;
import org.junit.Test;
import org.mockito.Mockito;

public class TenantAwareExecuteAsyncRunnableDiffblueTest {
  /**
   * Test {@link TenantAwareExecuteAsyncRunnable#run()}.
   * <ul>
   *   <li>Given {@link Job} {@link Job#isExclusive()} return {@code false}.</li>
   *   <li>Then calls
   * {@link ProcessEngineConfigurationImpl#getCommandExecutor()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TenantAwareExecuteAsyncRunnable#run()}
   */
  @Test
  public void testRun_givenJobIsExclusiveReturnFalse_thenCallsGetCommandExecutor() {
    // Arrange
    Job job = mock(Job.class);
    when(job.isExclusive()).thenReturn(false);
    when(job.getId()).thenReturn("42");
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(new CommandConfig(), first));

    // Act
    (new TenantAwareExecuteAsyncRunnable(job, processEngineConfiguration, new DummyTenantInfoHolder(), "42")).run();

    // Assert
    verify(processEngineConfiguration).getCommandExecutor();
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    verify(job).getId();
    verify(job, atLeast(1)).isExclusive();
  }

  /**
   * Test {@link TenantAwareExecuteAsyncRunnable#run()}.
   * <ul>
   *   <li>Given {@link Job} {@link Job#isExclusive()} return {@code true}.</li>
   *   <li>Then calls
   * {@link ProcessEngineConfigurationImpl#getCommandExecutor()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TenantAwareExecuteAsyncRunnable#run()}
   */
  @Test
  public void testRun_givenJobIsExclusiveReturnTrue_thenCallsGetCommandExecutor() {
    // Arrange
    Job job = mock(Job.class);
    when(job.isExclusive()).thenReturn(true);
    when(job.getId()).thenReturn("42");
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(new CommandConfig(), first));

    // Act
    (new TenantAwareExecuteAsyncRunnable(job, processEngineConfiguration, new DummyTenantInfoHolder(), "42")).run();

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(first, atLeast(1)).execute(isA(CommandConfig.class), Mockito.<Command<Object>>any());
    verify(job).getId();
    verify(job, atLeast(1)).isExclusive();
  }
}
