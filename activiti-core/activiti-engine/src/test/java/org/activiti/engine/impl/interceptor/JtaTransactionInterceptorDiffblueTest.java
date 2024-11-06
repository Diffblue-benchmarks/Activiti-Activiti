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

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import jakarta.transaction.InvalidTransactionException;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.SystemException;
import jakarta.transaction.Transaction;
import jakarta.transaction.TransactionManager;
import org.activiti.engine.impl.cfg.TransactionPropagation;
import org.junit.Test;
import org.mockito.Mockito;

public class JtaTransactionInterceptorDiffblueTest {
  /**
   * Test
   * {@link JtaTransactionInterceptor#JtaTransactionInterceptor(TransactionManager)}.
   * <p>
   * Method under test:
   * {@link JtaTransactionInterceptor#JtaTransactionInterceptor(TransactionManager)}
   */
  @Test
  public void testNewJtaTransactionInterceptor() {
    // Arrange, Act and Assert
    assertNull((new JtaTransactionInterceptor(mock(TransactionManager.class))).getNext());
  }

  /**
   * Test {@link JtaTransactionInterceptor#execute(CommandConfig, Command)}.
   * <p>
   * Method under test:
   * {@link JtaTransactionInterceptor#execute(CommandConfig, Command)}
   */
  @Test
  public void testExecute() throws InvalidTransactionException, NotSupportedException, SystemException,
      IllegalStateException, SecurityException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    doNothing().when(transactionManager).begin();
    doNothing().when(transactionManager).rollback();
    when(transactionManager.getStatus()).thenReturn(6);
    TransactionManager transactionManager2 = mock(TransactionManager.class);
    doThrow(new RuntimeException("Running command with propagation {}")).when(transactionManager2)
        .resume(Mockito.<Transaction>any());
    when(transactionManager2.suspend()).thenReturn(mock(Transaction.class));
    doNothing().when(transactionManager2).begin();
    doNothing().when(transactionManager2).rollback();
    when(transactionManager2.getStatus()).thenReturn(1);
    JtaTransactionInterceptor next = new JtaTransactionInterceptor(transactionManager2);

    JtaTransactionInterceptor jtaTransactionInterceptor = new JtaTransactionInterceptor(transactionManager);
    jtaTransactionInterceptor.setNext(next);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> jtaTransactionInterceptor
        .<Object>execute(new CommandConfig(true, TransactionPropagation.REQUIRES_NEW), mock(Command.class)));
    verify(transactionManager).begin();
    verify(transactionManager2).begin();
    verify(transactionManager).getStatus();
    verify(transactionManager2).getStatus();
    verify(transactionManager2).resume(isA(Transaction.class));
    verify(transactionManager).rollback();
    verify(transactionManager2).rollback();
    verify(transactionManager2).suspend();
  }

  /**
   * Test {@link JtaTransactionInterceptor#execute(CommandConfig, Command)}.
   * <ul>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JtaTransactionInterceptor#execute(CommandConfig, Command)}
   */
  @Test
  public void testExecute_thenThrowRuntimeException() throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.suspend()).thenThrow(new RuntimeException("Running command with propagation {}"));
    when(transactionManager.getStatus()).thenReturn(1);
    TransactionManager transactionManager2 = mock(TransactionManager.class);
    when(transactionManager2.suspend()).thenReturn(mock(Transaction.class));
    when(transactionManager2.getStatus()).thenReturn(1);
    JtaTransactionInterceptor next = new JtaTransactionInterceptor(transactionManager2);

    JtaTransactionInterceptor jtaTransactionInterceptor = new JtaTransactionInterceptor(transactionManager);
    jtaTransactionInterceptor.setNext(next);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> jtaTransactionInterceptor
        .<Object>execute(new CommandConfig(true, TransactionPropagation.REQUIRES_NEW), mock(Command.class)));
    verify(transactionManager).getStatus();
    verify(transactionManager).suspend();
  }
}
