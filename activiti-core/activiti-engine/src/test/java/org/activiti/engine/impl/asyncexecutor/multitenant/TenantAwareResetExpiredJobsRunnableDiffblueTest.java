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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Collection;
import java.util.Set;
import org.activiti.engine.impl.asyncexecutor.AsyncExecutor;
import org.activiti.engine.impl.asyncexecutor.DefaultAsyncJobExecutor;
import org.activiti.engine.impl.cfg.multitenant.TenantInfoHolder;
import org.activiti.engine.test.cfg.multitenant.DummyTenantInfoHolder;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class TenantAwareResetExpiredJobsRunnableDiffblueTest {
  /**
   * Test {@link
   * TenantAwareResetExpiredJobsRunnable#TenantAwareResetExpiredJobsRunnable(AsyncExecutor,
   * TenantInfoHolder, String)}.
   *
   * <p>Method under test: {@link
   * TenantAwareResetExpiredJobsRunnable#TenantAwareResetExpiredJobsRunnable(AsyncExecutor,
   * TenantInfoHolder, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void TenantAwareResetExpiredJobsRunnable.<init>(AsyncExecutor, TenantInfoHolder, String)"
  })
  public void testNewTenantAwareResetExpiredJobsRunnable() {
    // Arrange
    DefaultAsyncJobExecutor asyncExecutor = new DefaultAsyncJobExecutor();

    // Act
    TenantAwareResetExpiredJobsRunnable actualTenantAwareResetExpiredJobsRunnable =
        new TenantAwareResetExpiredJobsRunnable(asyncExecutor, new DummyTenantInfoHolder(), "42");

    // Assert
    TenantInfoHolder tenantInfoHolder = actualTenantAwareResetExpiredJobsRunnable.tenantInfoHolder;
    Collection<String> allTenants = tenantInfoHolder.getAllTenants();
    assertTrue(allTenants instanceof Set);
    assertTrue(tenantInfoHolder instanceof DummyTenantInfoHolder);
    assertEquals("42", actualTenantAwareResetExpiredJobsRunnable.tenantId);
    assertNull(tenantInfoHolder.getCurrentTenantId());
    assertNull(((DummyTenantInfoHolder) tenantInfoHolder).getCurrentUserId());
    assertTrue(allTenants.isEmpty());
  }

  /**
   * Test {@link TenantAwareResetExpiredJobsRunnable#getTenantAwareAsyncExecutor()}.
   *
   * <p>Method under test: {@link TenantAwareResetExpiredJobsRunnable#getTenantAwareAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutorPerTenantAsyncExecutor TenantAwareResetExpiredJobsRunnable.getTenantAwareAsyncExecutor()"
  })
  public void testGetTenantAwareAsyncExecutor() {
    // Arrange
    ExecutorPerTenantAsyncExecutor asyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    TenantAwareResetExpiredJobsRunnable tenantAwareResetExpiredJobsRunnable =
        new TenantAwareResetExpiredJobsRunnable(asyncExecutor, new DummyTenantInfoHolder(), "42");

    // Act and Assert
    assertSame(asyncExecutor, tenantAwareResetExpiredJobsRunnable.getTenantAwareAsyncExecutor());
  }
}
