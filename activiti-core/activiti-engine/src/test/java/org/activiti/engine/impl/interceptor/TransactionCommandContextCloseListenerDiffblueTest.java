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
package org.activiti.engine.impl.interceptor;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import jakarta.transaction.SystemException;
import jakarta.transaction.Transaction;
import jakarta.transaction.TransactionManager;
import org.activiti.engine.impl.cfg.TransactionContext;
import org.activiti.engine.impl.cfg.jta.JtaTransactionContext;
import org.junit.Test;

public class TransactionCommandContextCloseListenerDiffblueTest {
  /**
   * Test
   * {@link TransactionCommandContextCloseListener#TransactionCommandContextCloseListener(TransactionContext)}.
   * <p>
   * Method under test:
   * {@link TransactionCommandContextCloseListener#TransactionCommandContextCloseListener(TransactionContext)}
   */
  @Test
  public void testNewTransactionCommandContextCloseListener() {
    // Arrange, Act and Assert
    assertTrue((new TransactionCommandContextCloseListener(new JtaTransactionContext(
        mock(TransactionManager.class)))).transactionContext instanceof JtaTransactionContext);
  }

  /**
   * Test
   * {@link TransactionCommandContextCloseListener#closeFailure(CommandContext)}.
   * <ul>
   *   <li>Given {@link Transaction} {@link Transaction#getStatus()} return
   * one.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then calls {@link Transaction#getStatus()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TransactionCommandContextCloseListener#closeFailure(CommandContext)}
   */
  @Test
  public void testCloseFailure_givenTransactionGetStatusReturnOne_whenNull_thenCallsGetStatus()
      throws SystemException, IllegalStateException {
    // Arrange
    Transaction transaction = mock(Transaction.class);
    when(transaction.getStatus()).thenReturn(1);
    doNothing().when(transaction).setRollbackOnly();
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getTransaction()).thenReturn(transaction);

    // Act
    (new TransactionCommandContextCloseListener(new JtaTransactionContext(transactionManager))).closeFailure(null);

    // Assert that nothing has changed
    verify(transaction).getStatus();
    verify(transaction).setRollbackOnly();
    verify(transactionManager).getTransaction();
  }
}
