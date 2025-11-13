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
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Collection;
import java.util.Set;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cfg.multitenant.TenantInfoHolder;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityImpl;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.test.cfg.multitenant.DummyTenantInfoHolder;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class TenantAwareExecuteAsyncRunnableFactoryDiffblueTest {
  /**
   * Test {@link
   * TenantAwareExecuteAsyncRunnableFactory#TenantAwareExecuteAsyncRunnableFactory(TenantInfoHolder,
   * String)}.
   *
   * <p>Method under test: {@link
   * TenantAwareExecuteAsyncRunnableFactory#TenantAwareExecuteAsyncRunnableFactory(TenantInfoHolder,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void TenantAwareExecuteAsyncRunnableFactory.<init>(TenantInfoHolder, String)"
  })
  public void testNewTenantAwareExecuteAsyncRunnableFactory() {
    // Arrange and Act
    TenantAwareExecuteAsyncRunnableFactory actualTenantAwareExecuteAsyncRunnableFactory =
        new TenantAwareExecuteAsyncRunnableFactory(new DummyTenantInfoHolder(), "42");

    // Assert
    TenantInfoHolder tenantInfoHolder =
        actualTenantAwareExecuteAsyncRunnableFactory.tenantInfoHolder;
    Collection<String> allTenants = tenantInfoHolder.getAllTenants();
    assertTrue(allTenants instanceof Set);
    assertTrue(tenantInfoHolder instanceof DummyTenantInfoHolder);
    assertEquals("42", actualTenantAwareExecuteAsyncRunnableFactory.tenantId);
    assertNull(tenantInfoHolder.getCurrentTenantId());
    assertNull(((DummyTenantInfoHolder) tenantInfoHolder).getCurrentUserId());
    assertTrue(allTenants.isEmpty());
  }

  /**
   * Test {@link TenantAwareExecuteAsyncRunnableFactory#createExecuteAsyncRunnable(Job,
   * ProcessEngineConfigurationImpl)}.
   *
   * <ul>
   *   <li>Then {@link TenantAwareExecuteAsyncRunnable#tenantInfoHolder} AllTenants return {@link
   *       Set}.
   * </ul>
   *
   * <p>Method under test: {@link
   * TenantAwareExecuteAsyncRunnableFactory#createExecuteAsyncRunnable(Job,
   * ProcessEngineConfigurationImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Runnable TenantAwareExecuteAsyncRunnableFactory.createExecuteAsyncRunnable(Job, ProcessEngineConfigurationImpl)"
  })
  public void testCreateExecuteAsyncRunnable_thenTenantInfoHolderAllTenantsReturnSet() {
    // Arrange
    TenantAwareExecuteAsyncRunnableFactory tenantAwareExecuteAsyncRunnableFactory =
        new TenantAwareExecuteAsyncRunnableFactory(new DummyTenantInfoHolder(), "42");
    DeadLetterJobEntityImpl job = new DeadLetterJobEntityImpl();

    // Act
    Runnable actualCreateExecuteAsyncRunnableResult =
        tenantAwareExecuteAsyncRunnableFactory.createExecuteAsyncRunnable(
            job, new JtaProcessEngineConfiguration());

    // Assert
    TenantInfoHolder tenantInfoHolder =
        ((TenantAwareExecuteAsyncRunnable) actualCreateExecuteAsyncRunnableResult).tenantInfoHolder;
    Collection<String> allTenants = tenantInfoHolder.getAllTenants();
    assertTrue(allTenants instanceof Set);
    assertTrue(actualCreateExecuteAsyncRunnableResult instanceof TenantAwareExecuteAsyncRunnable);
    assertTrue(tenantInfoHolder instanceof DummyTenantInfoHolder);
    assertEquals(
        "42", ((TenantAwareExecuteAsyncRunnable) actualCreateExecuteAsyncRunnableResult).tenantId);
    assertNull(tenantInfoHolder.getCurrentTenantId());
    assertNull(((DummyTenantInfoHolder) tenantInfoHolder).getCurrentUserId());
    assertTrue(allTenants.isEmpty());
  }
}
