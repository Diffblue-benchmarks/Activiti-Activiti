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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Collection;
import java.util.Set;
import org.activiti.engine.impl.asyncexecutor.AsyncExecutor;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.multitenant.TenantInfoHolder;
import org.activiti.engine.test.cfg.multitenant.DummyTenantInfoHolder;
import org.junit.Test;

public class TenantAwareAcquireTimerJobsRunnableDiffblueTest {
  /**
   * Test
   * {@link TenantAwareAcquireTimerJobsRunnable#TenantAwareAcquireTimerJobsRunnable(AsyncExecutor, TenantInfoHolder, String)}.
   * <p>
   * Method under test:
   * {@link TenantAwareAcquireTimerJobsRunnable#TenantAwareAcquireTimerJobsRunnable(AsyncExecutor, TenantInfoHolder, String)}
   */
  @Test
  public void testNewTenantAwareAcquireTimerJobsRunnable() {
    // Arrange
    ExecutorPerTenantAsyncExecutor asyncExecutor = mock(ExecutorPerTenantAsyncExecutor.class);
    when(asyncExecutor.getProcessEngineConfiguration()).thenReturn(new JtaProcessEngineConfiguration());

    // Act
    TenantAwareAcquireTimerJobsRunnable actualTenantAwareAcquireTimerJobsRunnable = new TenantAwareAcquireTimerJobsRunnable(
        asyncExecutor, new DummyTenantInfoHolder(), "42");

    // Assert
    verify(asyncExecutor).getProcessEngineConfiguration();
    TenantInfoHolder tenantInfoHolder = actualTenantAwareAcquireTimerJobsRunnable.tenantInfoHolder;
    Collection<String> allTenants = tenantInfoHolder.getAllTenants();
    assertTrue(allTenants instanceof Set);
    assertTrue(tenantInfoHolder instanceof DummyTenantInfoHolder);
    assertEquals("42", actualTenantAwareAcquireTimerJobsRunnable.tenantId);
    assertNull(tenantInfoHolder.getCurrentTenantId());
    assertNull(((DummyTenantInfoHolder) tenantInfoHolder).getCurrentUserId());
    assertEquals(0L, actualTenantAwareAcquireTimerJobsRunnable.getMillisToWait());
    assertTrue(allTenants.isEmpty());
    assertSame(asyncExecutor, actualTenantAwareAcquireTimerJobsRunnable.getTenantAwareAsyncExecutor());
  }

  /**
   * Test
   * {@link TenantAwareAcquireTimerJobsRunnable#getTenantAwareAsyncExecutor()}.
   * <ul>
   *   <li>Then calls
   * {@link ExecutorPerTenantAsyncExecutor#getProcessEngineConfiguration()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TenantAwareAcquireTimerJobsRunnable#getTenantAwareAsyncExecutor()}
   */
  @Test
  public void testGetTenantAwareAsyncExecutor_thenCallsGetProcessEngineConfiguration() {
    // Arrange
    ExecutorPerTenantAsyncExecutor asyncExecutor = mock(ExecutorPerTenantAsyncExecutor.class);
    when(asyncExecutor.getProcessEngineConfiguration()).thenReturn(new JtaProcessEngineConfiguration());

    // Act
    (new TenantAwareAcquireTimerJobsRunnable(asyncExecutor, new DummyTenantInfoHolder(), "42"))
        .getTenantAwareAsyncExecutor();

    // Assert
    verify(asyncExecutor).getProcessEngineConfiguration();
  }
}
