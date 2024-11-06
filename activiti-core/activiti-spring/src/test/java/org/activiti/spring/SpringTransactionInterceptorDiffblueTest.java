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
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import jakarta.transaction.InvalidTransactionException;
import jakarta.transaction.SystemException;
import jakarta.transaction.Transaction;
import jakarta.transaction.TransactionManager;
import org.activiti.engine.impl.cfg.TransactionPropagation;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.transaction.jta.ManagedTransactionAdapter;

public class SpringTransactionInterceptorDiffblueTest {
  /**
   * Test
   * {@link SpringTransactionInterceptor#SpringTransactionInterceptor(PlatformTransactionManager)}.
   * <p>
   * Method under test:
   * {@link SpringTransactionInterceptor#SpringTransactionInterceptor(PlatformTransactionManager)}
   */
  @Test
  public void testNewSpringTransactionInterceptor() {
    // Arrange, Act and Assert
    assertNull((new SpringTransactionInterceptor(new DataSourceTransactionManager())).getNext());
  }

  /**
   * Test {@link SpringTransactionInterceptor#execute(CommandConfig, Command)}.
   * <ul>
   *   <li>Given {@code NOT_SUPPORTED}.</li>
   *   <li>Then calls {@link TransactionManager#resume(Transaction)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SpringTransactionInterceptor#execute(CommandConfig, Command)}
   */
  @Test
  public void testExecute_givenNotSupported_thenCallsResume()
      throws InvalidTransactionException, SystemException, IllegalStateException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    doNothing().when(transactionManager).resume(Mockito.<Transaction>any());
    when(transactionManager.suspend()).thenReturn(new ManagedTransactionAdapter(mock(TransactionManager.class)));
    when(transactionManager.getStatus()).thenReturn(1);
    JtaTransactionManager transactionManager2 = new JtaTransactionManager(transactionManager);
    CommandContextInterceptor next = mock(CommandContextInterceptor.class);
    when(next.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn("Execute");

    SpringTransactionInterceptor springTransactionInterceptor = new SpringTransactionInterceptor(transactionManager2);
    springTransactionInterceptor.setNext(next);
    CommandConfig config = mock(CommandConfig.class);
    when(config.getTransactionPropagation()).thenReturn(TransactionPropagation.NOT_SUPPORTED);

    // Act
    Object actualExecuteResult = springTransactionInterceptor.<Object>execute(config, mock(Command.class));

    // Assert
    verify(transactionManager).getStatus();
    verify(transactionManager).resume(isA(Transaction.class));
    verify(transactionManager).suspend();
    verify(config, atLeast(1)).getTransactionPropagation();
    verify(next).execute(isA(CommandConfig.class), isA(Command.class));
    assertEquals("Execute", actualExecuteResult);
  }

  /**
   * Test {@link SpringTransactionInterceptor#execute(CommandConfig, Command)}.
   * <ul>
   *   <li>Given {@code REQUIRED}.</li>
   *   <li>Then return {@code Execute}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SpringTransactionInterceptor#execute(CommandConfig, Command)}
   */
  @Test
  public void testExecute_givenRequired_thenReturnExecute() throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getStatus()).thenReturn(1);
    JtaTransactionManager transactionManager2 = new JtaTransactionManager(transactionManager);
    CommandContextInterceptor next = mock(CommandContextInterceptor.class);
    when(next.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn("Execute");

    SpringTransactionInterceptor springTransactionInterceptor = new SpringTransactionInterceptor(transactionManager2);
    springTransactionInterceptor.setNext(next);
    CommandConfig config = mock(CommandConfig.class);
    when(config.getTransactionPropagation()).thenReturn(TransactionPropagation.REQUIRED);

    // Act
    Object actualExecuteResult = springTransactionInterceptor.<Object>execute(config, mock(Command.class));

    // Assert
    verify(transactionManager).getStatus();
    verify(config, atLeast(1)).getTransactionPropagation();
    verify(next).execute(isA(CommandConfig.class), isA(Command.class));
    assertEquals("Execute", actualExecuteResult);
  }
}
