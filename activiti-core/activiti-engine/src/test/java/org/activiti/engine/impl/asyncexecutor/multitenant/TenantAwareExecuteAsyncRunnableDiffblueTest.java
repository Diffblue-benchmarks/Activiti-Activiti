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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cfg.multitenant.TenantInfoHolder;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.test.cfg.multitenant.DummyTenantInfoHolder;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TenantAwareExecuteAsyncRunnableDiffblueTest {
  @Mock
  private Job job;

  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  @Mock
  private TenantInfoHolder tenantInfoHolder;

  /**
   * Test {@link TenantAwareExecuteAsyncRunnable#TenantAwareExecuteAsyncRunnable(Job, ProcessEngineConfigurationImpl, TenantInfoHolder, String)}.
   * <p>
   * Method under test: {@link TenantAwareExecuteAsyncRunnable#TenantAwareExecuteAsyncRunnable(Job, ProcessEngineConfigurationImpl, TenantInfoHolder, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void TenantAwareExecuteAsyncRunnable.<init>(Job, ProcessEngineConfigurationImpl, TenantInfoHolder, String)"})
  public void testNewTenantAwareExecuteAsyncRunnable() {
    // Arrange
    when(job.getId()).thenReturn("42");

    // Act
    TenantAwareExecuteAsyncRunnable actualTenantAwareExecuteAsyncRunnable = new TenantAwareExecuteAsyncRunnable(job,
        processEngineConfigurationImpl, tenantInfoHolder, "42");

    // Assert
    verify(job).getId();
    assertEquals("42", actualTenantAwareExecuteAsyncRunnable.tenantId);
  }

  /**
   * Test {@link TenantAwareExecuteAsyncRunnable#run()}.
   * <ul>
   *   <li>Then calls {@link CommandInterceptor#execute(CommandConfig, Command)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TenantAwareExecuteAsyncRunnable#run()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void TenantAwareExecuteAsyncRunnable.run()"})
  public void testRun_thenCallsExecute() {
    // Arrange
    Job job = mock(Job.class);
    when(job.isExclusive()).thenReturn(true);
    when(job.getId()).thenReturn("42");
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCommandExecutor(commandExecutor);

    // Act
    (new TenantAwareExecuteAsyncRunnable(job, processEngineConfiguration, new DummyTenantInfoHolder(), "42")).run();

    // Assert
    verify(first, atLeast(1)).execute(isA(CommandConfig.class), Mockito.<Command<Object>>any());
    verify(job).getId();
    verify(job, atLeast(1)).isExclusive();
  }

  /**
   * Test {@link TenantAwareExecuteAsyncRunnable#run()}.
   * <ul>
   *   <li>Then calls {@link CommandInterceptor#execute(CommandConfig, Command)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TenantAwareExecuteAsyncRunnable#run()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void TenantAwareExecuteAsyncRunnable.run()"})
  public void testRun_thenCallsExecute2() {
    // Arrange
    Job job = mock(Job.class);
    when(job.isExclusive()).thenReturn(false);
    when(job.getId()).thenReturn("42");
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCommandExecutor(commandExecutor);

    // Act
    (new TenantAwareExecuteAsyncRunnable(job, processEngineConfiguration, new DummyTenantInfoHolder(), "42")).run();

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    verify(job).getId();
    verify(job, atLeast(1)).isExclusive();
  }
}
