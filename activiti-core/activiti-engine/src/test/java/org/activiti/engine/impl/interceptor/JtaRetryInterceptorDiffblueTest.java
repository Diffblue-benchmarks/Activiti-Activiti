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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import jakarta.transaction.SystemException;
import jakarta.transaction.TransactionManager;
import org.activiti.engine.ActivitiException;
import org.junit.Test;

public class JtaRetryInterceptorDiffblueTest {
  /**
   * Test {@link JtaRetryInterceptor#JtaRetryInterceptor(TransactionManager)}.
   * <p>
   * Method under test:
   * {@link JtaRetryInterceptor#JtaRetryInterceptor(TransactionManager)}
   */
  @Test
  public void testNewJtaRetryInterceptor() {
    // Arrange and Act
    JtaRetryInterceptor actualJtaRetryInterceptor = new JtaRetryInterceptor(mock(TransactionManager.class));

    // Assert
    assertNull(actualJtaRetryInterceptor.getNext());
    assertEquals(3, actualJtaRetryInterceptor.getNumOfRetries());
    assertEquals(5, actualJtaRetryInterceptor.getWaitIncreaseFactor());
    assertEquals(50, actualJtaRetryInterceptor.getWaitTimeInMs());
  }

  /**
   * Test {@link JtaRetryInterceptor#execute(CommandConfig, Command)}.
   * <p>
   * Method under test:
   * {@link JtaRetryInterceptor#execute(CommandConfig, Command)}
   */
  @Test
  public void testExecute() throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getStatus()).thenThrow(new ActivitiException("An error occurred"));
    JtaRetryInterceptor jtaRetryInterceptor = new JtaRetryInterceptor(transactionManager);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> jtaRetryInterceptor.<Object>execute(new CommandConfig(), mock(Command.class)));
    verify(transactionManager).getStatus();
  }

  /**
   * Test {@link JtaRetryInterceptor#execute(CommandConfig, Command)}.
   * <ul>
   *   <li>Given {@link TransactionManager} {@link TransactionManager#getStatus()}
   * throw {@link SystemException#SystemException(int)} with errcode is six.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JtaRetryInterceptor#execute(CommandConfig, Command)}
   */
  @Test
  public void testExecute_givenTransactionManagerGetStatusThrowSystemExceptionWithErrcodeIsSix()
      throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getStatus()).thenThrow(new SystemException(6));
    JtaRetryInterceptor jtaRetryInterceptor = new JtaRetryInterceptor(transactionManager);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> jtaRetryInterceptor.<Object>execute(new CommandConfig(), mock(Command.class)));
    verify(transactionManager).getStatus();
  }

  /**
   * Test {@link JtaRetryInterceptor#calledInsideTransaction()}.
   * <p>
   * Method under test: {@link JtaRetryInterceptor#calledInsideTransaction()}
   */
  @Test
  public void testCalledInsideTransaction() throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getStatus()).thenThrow(new SystemException(6));

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new JtaRetryInterceptor(transactionManager)).calledInsideTransaction());
    verify(transactionManager).getStatus();
  }

  /**
   * Test {@link JtaRetryInterceptor#calledInsideTransaction()}.
   * <p>
   * Method under test: {@link JtaRetryInterceptor#calledInsideTransaction()}
   */
  @Test
  public void testCalledInsideTransaction2() throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getStatus()).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new JtaRetryInterceptor(transactionManager)).calledInsideTransaction());
    verify(transactionManager).getStatus();
  }

  /**
   * Test {@link JtaRetryInterceptor#calledInsideTransaction()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JtaRetryInterceptor#calledInsideTransaction()}
   */
  @Test
  public void testCalledInsideTransaction_thenReturnFalse() throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getStatus()).thenReturn(6);

    // Act
    boolean actualCalledInsideTransactionResult = (new JtaRetryInterceptor(transactionManager))
        .calledInsideTransaction();

    // Assert
    verify(transactionManager).getStatus();
    assertFalse(actualCalledInsideTransactionResult);
  }

  /**
   * Test {@link JtaRetryInterceptor#calledInsideTransaction()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JtaRetryInterceptor#calledInsideTransaction()}
   */
  @Test
  public void testCalledInsideTransaction_thenReturnTrue() throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getStatus()).thenReturn(1);

    // Act
    boolean actualCalledInsideTransactionResult = (new JtaRetryInterceptor(transactionManager))
        .calledInsideTransaction();

    // Assert
    verify(transactionManager).getStatus();
    assertTrue(actualCalledInsideTransactionResult);
  }
}
