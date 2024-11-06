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
import java.util.Collection;
import java.util.List;
import org.activiti.engine.impl.cfg.TransactionContext;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.junit.Test;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionExecutionListener;

public class SpringTransactionContextFactoryDiffblueTest {
  /**
   * Test
   * {@link SpringTransactionContextFactory#SpringTransactionContextFactory(PlatformTransactionManager)}.
   * <p>
   * Method under test:
   * {@link SpringTransactionContextFactory#SpringTransactionContextFactory(PlatformTransactionManager)}
   */
  @Test
  public void testNewSpringTransactionContextFactory() {
    // Arrange and Act
    SpringTransactionContextFactory actualSpringTransactionContextFactory = new SpringTransactionContextFactory(
        new DataSourceTransactionManager());

    // Assert
    PlatformTransactionManager platformTransactionManager = actualSpringTransactionContextFactory.transactionManager;
    Collection<TransactionExecutionListener> transactionExecutionListeners = ((DataSourceTransactionManager) platformTransactionManager)
        .getTransactionExecutionListeners();
    assertTrue(transactionExecutionListeners instanceof List);
    assertTrue(platformTransactionManager instanceof DataSourceTransactionManager);
    assertNull(actualSpringTransactionContextFactory.transactionSynchronizationAdapterOrder);
    assertNull(((DataSourceTransactionManager) platformTransactionManager).getDataSource());
    assertEquals(-1, ((DataSourceTransactionManager) platformTransactionManager).getDefaultTimeout());
    assertEquals(0, ((DataSourceTransactionManager) platformTransactionManager).getTransactionSynchronization());
    assertFalse(((DataSourceTransactionManager) platformTransactionManager).isEnforceReadOnly());
    assertFalse(((DataSourceTransactionManager) platformTransactionManager).isFailEarlyOnGlobalRollbackOnly());
    assertFalse(((DataSourceTransactionManager) platformTransactionManager).isRollbackOnCommitFailure());
    assertFalse(((DataSourceTransactionManager) platformTransactionManager).isValidateExistingTransaction());
    assertTrue(transactionExecutionListeners.isEmpty());
    assertTrue(((DataSourceTransactionManager) platformTransactionManager).isGlobalRollbackOnParticipationFailure());
    assertTrue(((DataSourceTransactionManager) platformTransactionManager).isNestedTransactionAllowed());
  }

  /**
   * Test
   * {@link SpringTransactionContextFactory#SpringTransactionContextFactory(PlatformTransactionManager, Integer)}.
   * <p>
   * Method under test:
   * {@link SpringTransactionContextFactory#SpringTransactionContextFactory(PlatformTransactionManager, Integer)}
   */
  @Test
  public void testNewSpringTransactionContextFactory2() {
    // Arrange, Act and Assert
    assertEquals(1, (new SpringTransactionContextFactory(new DataSourceTransactionManager(),
        1)).transactionSynchronizationAdapterOrder.intValue());
  }

  /**
   * Test
   * {@link SpringTransactionContextFactory#openTransactionContext(CommandContext)}.
   * <p>
   * Method under test:
   * {@link SpringTransactionContextFactory#openTransactionContext(CommandContext)}
   */
  @Test
  public void testOpenTransactionContext() {
    // Arrange and Act
    TransactionContext actualOpenTransactionContextResult = (new SpringTransactionContextFactory(
        new DataSourceTransactionManager())).openTransactionContext(null);

    // Assert
    PlatformTransactionManager platformTransactionManager = ((SpringTransactionContext) actualOpenTransactionContextResult).transactionManager;
    Collection<TransactionExecutionListener> transactionExecutionListeners = ((DataSourceTransactionManager) platformTransactionManager)
        .getTransactionExecutionListeners();
    assertTrue(transactionExecutionListeners instanceof List);
    assertTrue(actualOpenTransactionContextResult instanceof SpringTransactionContext);
    assertTrue(platformTransactionManager instanceof DataSourceTransactionManager);
    assertNull(((DataSourceTransactionManager) platformTransactionManager).getDataSource());
    assertNull(((SpringTransactionContext) actualOpenTransactionContextResult).commandContext);
    assertEquals(-1, ((DataSourceTransactionManager) platformTransactionManager).getDefaultTimeout());
    assertEquals(0, ((DataSourceTransactionManager) platformTransactionManager).getTransactionSynchronization());
    assertFalse(((DataSourceTransactionManager) platformTransactionManager).isEnforceReadOnly());
    assertFalse(((DataSourceTransactionManager) platformTransactionManager).isFailEarlyOnGlobalRollbackOnly());
    assertFalse(((DataSourceTransactionManager) platformTransactionManager).isRollbackOnCommitFailure());
    assertFalse(((DataSourceTransactionManager) platformTransactionManager).isValidateExistingTransaction());
    assertTrue(transactionExecutionListeners.isEmpty());
    assertTrue(((DataSourceTransactionManager) platformTransactionManager).isGlobalRollbackOnParticipationFailure());
    assertTrue(((DataSourceTransactionManager) platformTransactionManager).isNestedTransactionAllowed());
    assertEquals(Integer.MAX_VALUE,
        ((SpringTransactionContext) actualOpenTransactionContextResult).transactionSynchronizationAdapterOrder
            .intValue());
  }

  /**
   * Test
   * {@link SpringTransactionContextFactory#openTransactionContext(CommandContext)}.
   * <p>
   * Method under test:
   * {@link SpringTransactionContextFactory#openTransactionContext(CommandContext)}
   */
  @Test
  public void testOpenTransactionContext2() {
    // Arrange and Act
    TransactionContext actualOpenTransactionContextResult = (new SpringTransactionContextFactory(
        new DataSourceTransactionManager(), 1)).openTransactionContext(null);

    // Assert
    PlatformTransactionManager platformTransactionManager = ((SpringTransactionContext) actualOpenTransactionContextResult).transactionManager;
    Collection<TransactionExecutionListener> transactionExecutionListeners = ((DataSourceTransactionManager) platformTransactionManager)
        .getTransactionExecutionListeners();
    assertTrue(transactionExecutionListeners instanceof List);
    assertTrue(actualOpenTransactionContextResult instanceof SpringTransactionContext);
    assertTrue(platformTransactionManager instanceof DataSourceTransactionManager);
    assertNull(((DataSourceTransactionManager) platformTransactionManager).getDataSource());
    assertNull(((SpringTransactionContext) actualOpenTransactionContextResult).commandContext);
    assertEquals(-1, ((DataSourceTransactionManager) platformTransactionManager).getDefaultTimeout());
    assertEquals(0, ((DataSourceTransactionManager) platformTransactionManager).getTransactionSynchronization());
    assertEquals(1,
        ((SpringTransactionContext) actualOpenTransactionContextResult).transactionSynchronizationAdapterOrder
            .intValue());
    assertFalse(((DataSourceTransactionManager) platformTransactionManager).isEnforceReadOnly());
    assertFalse(((DataSourceTransactionManager) platformTransactionManager).isFailEarlyOnGlobalRollbackOnly());
    assertFalse(((DataSourceTransactionManager) platformTransactionManager).isRollbackOnCommitFailure());
    assertFalse(((DataSourceTransactionManager) platformTransactionManager).isValidateExistingTransaction());
    assertTrue(transactionExecutionListeners.isEmpty());
    assertTrue(((DataSourceTransactionManager) platformTransactionManager).isGlobalRollbackOnParticipationFailure());
    assertTrue(((DataSourceTransactionManager) platformTransactionManager).isNestedTransactionAllowed());
  }
}
