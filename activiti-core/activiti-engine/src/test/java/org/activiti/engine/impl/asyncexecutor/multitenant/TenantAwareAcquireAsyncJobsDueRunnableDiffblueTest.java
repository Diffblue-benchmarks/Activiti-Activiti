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
import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.impl.asyncexecutor.AsyncExecutor;
import org.activiti.engine.impl.cfg.multitenant.TenantInfoHolder;
import org.activiti.engine.test.cfg.multitenant.DummyTenantInfoHolder;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TenantAwareAcquireAsyncJobsDueRunnableDiffblueTest {
  @Mock
  private AsyncExecutor asyncExecutor;

  @Mock
  private TenantInfoHolder tenantInfoHolder;

  /**
   * Test {@link TenantAwareAcquireAsyncJobsDueRunnable#TenantAwareAcquireAsyncJobsDueRunnable(AsyncExecutor, TenantInfoHolder, String)}.
   * <p>
   * Method under test: {@link TenantAwareAcquireAsyncJobsDueRunnable#TenantAwareAcquireAsyncJobsDueRunnable(AsyncExecutor, TenantInfoHolder, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void TenantAwareAcquireAsyncJobsDueRunnable.<init>(AsyncExecutor, TenantInfoHolder, String)"})
  public void testNewTenantAwareAcquireAsyncJobsDueRunnable() {
    // Arrange and Act
    TenantAwareAcquireAsyncJobsDueRunnable actualTenantAwareAcquireAsyncJobsDueRunnable = new TenantAwareAcquireAsyncJobsDueRunnable(
        asyncExecutor, tenantInfoHolder, "42");

    // Assert
    assertEquals("42", actualTenantAwareAcquireAsyncJobsDueRunnable.tenantId);
    assertEquals(0L, actualTenantAwareAcquireAsyncJobsDueRunnable.getMillisToWait());
  }

  /**
   * Test {@link TenantAwareAcquireAsyncJobsDueRunnable#getTenantAwareAsyncExecutor()}.
   * <p>
   * Method under test: {@link TenantAwareAcquireAsyncJobsDueRunnable#getTenantAwareAsyncExecutor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ExecutorPerTenantAsyncExecutor TenantAwareAcquireAsyncJobsDueRunnable.getTenantAwareAsyncExecutor()"})
  public void testGetTenantAwareAsyncExecutor() {
    // Arrange
    ExecutorPerTenantAsyncExecutor asyncExecutor = new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());

    // Act and Assert
    assertSame(asyncExecutor,
        (new TenantAwareAcquireAsyncJobsDueRunnable(asyncExecutor, new DummyTenantInfoHolder(), "42"))
            .getTenantAwareAsyncExecutor());
  }
}
