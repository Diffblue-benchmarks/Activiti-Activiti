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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.transaction.SystemException;
import jakarta.transaction.TransactionManager;
import java.util.Collection;
import java.util.List;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionExecutionListener;
import org.springframework.transaction.jta.JtaTransactionManager;

public class SpringTransactionContextDiffblueTest {
  /**
   * Test {@link SpringTransactionContext#SpringTransactionContext(PlatformTransactionManager,
   * CommandContext)}.
   *
   * <p>Method under test: {@link
   * SpringTransactionContext#SpringTransactionContext(PlatformTransactionManager, CommandContext)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SpringTransactionContext.<init>(PlatformTransactionManager, CommandContext)"
  })
  public void testNewSpringTransactionContext() {
    // Arrange and Act
    SpringTransactionContext actualSpringTransactionContext =
        new SpringTransactionContext(new DataSourceTransactionManager(), null);

    // Assert
    PlatformTransactionManager platformTransactionManager =
        actualSpringTransactionContext.transactionManager;
    Collection<TransactionExecutionListener> transactionExecutionListeners =
        ((DataSourceTransactionManager) platformTransactionManager)
            .getTransactionExecutionListeners();
    assertTrue(transactionExecutionListeners instanceof List);
    assertTrue(platformTransactionManager instanceof DataSourceTransactionManager);
    assertNull(((DataSourceTransactionManager) platformTransactionManager).getDataSource());
    assertNull(actualSpringTransactionContext.commandContext);
    assertEquals(
        -1, ((DataSourceTransactionManager) platformTransactionManager).getDefaultTimeout());
    assertEquals(
        0,
        ((DataSourceTransactionManager) platformTransactionManager)
            .getTransactionSynchronization());
    assertFalse(((DataSourceTransactionManager) platformTransactionManager).isEnforceReadOnly());
    assertFalse(
        ((DataSourceTransactionManager) platformTransactionManager)
            .isFailEarlyOnGlobalRollbackOnly());
    assertFalse(
        ((DataSourceTransactionManager) platformTransactionManager).isRollbackOnCommitFailure());
    assertFalse(
        ((DataSourceTransactionManager) platformTransactionManager)
            .isValidateExistingTransaction());
    assertTrue(transactionExecutionListeners.isEmpty());
    assertTrue(
        ((DataSourceTransactionManager) platformTransactionManager)
            .isGlobalRollbackOnParticipationFailure());
    assertTrue(
        ((DataSourceTransactionManager) platformTransactionManager).isNestedTransactionAllowed());
    assertEquals(
        Integer.MAX_VALUE,
        actualSpringTransactionContext.transactionSynchronizationAdapterOrder.intValue());
  }

  /**
   * Test {@link SpringTransactionContext#SpringTransactionContext(PlatformTransactionManager,
   * CommandContext, Integer)}.
   *
   * <p>Method under test: {@link
   * SpringTransactionContext#SpringTransactionContext(PlatformTransactionManager, CommandContext,
   * Integer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SpringTransactionContext.<init>(PlatformTransactionManager, CommandContext, Integer)"
  })
  public void testNewSpringTransactionContext2() {
    // Arrange and Act
    SpringTransactionContext actualSpringTransactionContext =
        new SpringTransactionContext(new DataSourceTransactionManager(), null, 1);

    // Assert
    PlatformTransactionManager platformTransactionManager =
        actualSpringTransactionContext.transactionManager;
    Collection<TransactionExecutionListener> transactionExecutionListeners =
        ((DataSourceTransactionManager) platformTransactionManager)
            .getTransactionExecutionListeners();
    assertTrue(transactionExecutionListeners instanceof List);
    assertTrue(platformTransactionManager instanceof DataSourceTransactionManager);
    assertNull(((DataSourceTransactionManager) platformTransactionManager).getDataSource());
    assertNull(actualSpringTransactionContext.commandContext);
    assertEquals(
        -1, ((DataSourceTransactionManager) platformTransactionManager).getDefaultTimeout());
    assertEquals(
        0,
        ((DataSourceTransactionManager) platformTransactionManager)
            .getTransactionSynchronization());
    assertEquals(
        1, actualSpringTransactionContext.transactionSynchronizationAdapterOrder.intValue());
    assertFalse(((DataSourceTransactionManager) platformTransactionManager).isEnforceReadOnly());
    assertFalse(
        ((DataSourceTransactionManager) platformTransactionManager)
            .isFailEarlyOnGlobalRollbackOnly());
    assertFalse(
        ((DataSourceTransactionManager) platformTransactionManager).isRollbackOnCommitFailure());
    assertFalse(
        ((DataSourceTransactionManager) platformTransactionManager)
            .isValidateExistingTransaction());
    assertTrue(transactionExecutionListeners.isEmpty());
    assertTrue(
        ((DataSourceTransactionManager) platformTransactionManager)
            .isGlobalRollbackOnParticipationFailure());
    assertTrue(
        ((DataSourceTransactionManager) platformTransactionManager).isNestedTransactionAllowed());
  }

  /**
   * Test {@link SpringTransactionContext#SpringTransactionContext(PlatformTransactionManager,
   * CommandContext, Integer)}.
   *
   * <p>Method under test: {@link
   * SpringTransactionContext#SpringTransactionContext(PlatformTransactionManager, CommandContext,
   * Integer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SpringTransactionContext.<init>(PlatformTransactionManager, CommandContext, Integer)"
  })
  public void testNewSpringTransactionContext3() {
    // Arrange and Act
    SpringTransactionContext actualSpringTransactionContext =
        new SpringTransactionContext(new DataSourceTransactionManager(), null, null);

    // Assert
    PlatformTransactionManager platformTransactionManager =
        actualSpringTransactionContext.transactionManager;
    Collection<TransactionExecutionListener> transactionExecutionListeners =
        ((DataSourceTransactionManager) platformTransactionManager)
            .getTransactionExecutionListeners();
    assertTrue(transactionExecutionListeners instanceof List);
    assertTrue(platformTransactionManager instanceof DataSourceTransactionManager);
    assertNull(((DataSourceTransactionManager) platformTransactionManager).getDataSource());
    assertNull(actualSpringTransactionContext.commandContext);
    assertEquals(
        -1, ((DataSourceTransactionManager) platformTransactionManager).getDefaultTimeout());
    assertEquals(
        0,
        ((DataSourceTransactionManager) platformTransactionManager)
            .getTransactionSynchronization());
    assertFalse(((DataSourceTransactionManager) platformTransactionManager).isEnforceReadOnly());
    assertFalse(
        ((DataSourceTransactionManager) platformTransactionManager)
            .isFailEarlyOnGlobalRollbackOnly());
    assertFalse(
        ((DataSourceTransactionManager) platformTransactionManager).isRollbackOnCommitFailure());
    assertFalse(
        ((DataSourceTransactionManager) platformTransactionManager)
            .isValidateExistingTransaction());
    assertTrue(transactionExecutionListeners.isEmpty());
    assertTrue(
        ((DataSourceTransactionManager) platformTransactionManager)
            .isGlobalRollbackOnParticipationFailure());
    assertTrue(
        ((DataSourceTransactionManager) platformTransactionManager).isNestedTransactionAllowed());
    assertEquals(
        Integer.MAX_VALUE,
        actualSpringTransactionContext.transactionSynchronizationAdapterOrder.intValue());
  }

  /**
   * Test {@link SpringTransactionContext#rollback()}.
   *
   * <ul>
   *   <li>Given {@link TransactionManager} {@link TransactionManager#getStatus()} return one.
   *   <li>Then calls {@link TransactionManager#getStatus()}.
   * </ul>
   *
   * <p>Method under test: {@link SpringTransactionContext#rollback()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SpringTransactionContext.rollback()"})
  public void testRollback_givenTransactionManagerGetStatusReturnOne_thenCallsGetStatus()
      throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getStatus()).thenReturn(1);
    JtaTransactionManager transactionManager2 = new JtaTransactionManager(transactionManager);
    SpringTransactionContext springTransactionContext =
        new SpringTransactionContext(transactionManager2, null);

    // Act
    springTransactionContext.rollback();

    // Assert
    verify(transactionManager).getStatus();
  }
}
