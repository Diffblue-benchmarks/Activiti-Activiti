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
package org.activiti.engine.impl.cfg.jta;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.transaction.RollbackException;
import jakarta.transaction.Synchronization;
import jakarta.transaction.SystemException;
import jakarta.transaction.Transaction;
import jakarta.transaction.TransactionManager;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.cfg.TransactionListener;
import org.activiti.engine.impl.cfg.TransactionState;
import org.activiti.engine.impl.cfg.jta.JtaTransactionContext.TransactionStateSynchronization;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class JtaTransactionContextDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link JtaTransactionContext#JtaTransactionContext(TransactionManager)}
   *   <li>{@link JtaTransactionContext#commit()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void JtaTransactionContext.<init>(TransactionManager)",
    "void JtaTransactionContext.commit()"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    JtaTransactionContext actualJtaTransactionContext =
        new JtaTransactionContext(mock(TransactionManager.class));
    actualJtaTransactionContext.commit();

    // Assert
    assertNull(actualJtaTransactionContext.getTransaction());
  }

  /**
   * Test {@link JtaTransactionContext#rollback()}.
   *
   * <p>Method under test: {@link JtaTransactionContext#rollback()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JtaTransactionContext.rollback()"})
  public void testRollback() throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getTransaction()).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class, () -> new JtaTransactionContext(transactionManager).rollback());
    verify(transactionManager).getTransaction();
  }

  /**
   * Test {@link JtaTransactionContext#rollback()}.
   *
   * <p>Method under test: {@link JtaTransactionContext#rollback()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JtaTransactionContext.rollback()"})
  public void testRollback2() throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getTransaction()).thenThrow(new SystemException(1));

    // Act and Assert
    assertThrows(
        ActivitiException.class, () -> new JtaTransactionContext(transactionManager).rollback());
    verify(transactionManager).getTransaction();
  }

  /**
   * Test {@link JtaTransactionContext#rollback()}.
   *
   * <ul>
   *   <li>Given {@link Transaction} {@link Transaction#getStatus()} return four.
   *   <li>Then calls {@link Transaction#getStatus()}.
   * </ul>
   *
   * <p>Method under test: {@link JtaTransactionContext#rollback()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JtaTransactionContext.rollback()"})
  public void testRollback_givenTransactionGetStatusReturnFour_thenCallsGetStatus()
      throws SystemException {
    // Arrange
    Transaction transaction = mock(Transaction.class);
    when(transaction.getStatus()).thenReturn(4);

    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getTransaction()).thenReturn(transaction);

    // Act
    new JtaTransactionContext(transactionManager).rollback();

    // Assert
    verify(transaction).getStatus();
    verify(transactionManager).getTransaction();
  }

  /**
   * Test {@link JtaTransactionContext#rollback()}.
   *
   * <ul>
   *   <li>Given {@link Transaction} {@link Transaction#getStatus()} return one.
   *   <li>Then calls {@link Transaction#setRollbackOnly()}.
   * </ul>
   *
   * <p>Method under test: {@link JtaTransactionContext#rollback()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JtaTransactionContext.rollback()"})
  public void testRollback_givenTransactionGetStatusReturnOne_thenCallsSetRollbackOnly()
      throws SystemException, IllegalStateException {
    // Arrange
    Transaction transaction = mock(Transaction.class);
    when(transaction.getStatus()).thenReturn(1);
    doNothing().when(transaction).setRollbackOnly();

    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getTransaction()).thenReturn(transaction);

    // Act
    new JtaTransactionContext(transactionManager).rollback();

    // Assert
    verify(transaction).getStatus();
    verify(transaction).setRollbackOnly();
    verify(transactionManager).getTransaction();
  }

  /**
   * Test {@link JtaTransactionContext#rollback()}.
   *
   * <ul>
   *   <li>Given {@link Transaction} {@link Transaction#getStatus()} return six.
   *   <li>Then calls {@link Transaction#getStatus()}.
   * </ul>
   *
   * <p>Method under test: {@link JtaTransactionContext#rollback()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JtaTransactionContext.rollback()"})
  public void testRollback_givenTransactionGetStatusReturnSix_thenCallsGetStatus()
      throws SystemException {
    // Arrange
    Transaction transaction = mock(Transaction.class);
    when(transaction.getStatus()).thenReturn(6);

    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getTransaction()).thenReturn(transaction);

    // Act
    new JtaTransactionContext(transactionManager).rollback();

    // Assert
    verify(transaction).getStatus();
    verify(transactionManager).getTransaction();
  }

  /**
   * Test {@link JtaTransactionContext#rollback()}.
   *
   * <ul>
   *   <li>Given {@link Transaction} {@link Transaction#getStatus()} throw {@link
   *       IllegalStateException#IllegalStateException()}.
   * </ul>
   *
   * <p>Method under test: {@link JtaTransactionContext#rollback()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JtaTransactionContext.rollback()"})
  public void testRollback_givenTransactionGetStatusThrowIllegalStateException()
      throws SystemException {
    // Arrange
    Transaction transaction = mock(Transaction.class);
    when(transaction.getStatus()).thenThrow(new IllegalStateException());

    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getTransaction()).thenReturn(transaction);

    // Act and Assert
    assertThrows(
        ActivitiException.class, () -> new JtaTransactionContext(transactionManager).rollback());
    verify(transaction).getStatus();
    verify(transactionManager).getTransaction();
  }

  /**
   * Test {@link JtaTransactionContext#rollback()}.
   *
   * <ul>
   *   <li>Given {@link TransactionManager} {@link TransactionManager#getTransaction()} throw {@link
   *       IllegalStateException#IllegalStateException()}.
   * </ul>
   *
   * <p>Method under test: {@link JtaTransactionContext#rollback()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JtaTransactionContext.rollback()"})
  public void testRollback_givenTransactionManagerGetTransactionThrowIllegalStateException()
      throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getTransaction()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        ActivitiException.class, () -> new JtaTransactionContext(transactionManager).rollback());
    verify(transactionManager).getTransaction();
  }

  /**
   * Test {@link JtaTransactionContext#getTransaction()}.
   *
   * <ul>
   *   <li>Given {@link TransactionManager} {@link TransactionManager#getTransaction()} return
   *       {@link Transaction}.
   * </ul>
   *
   * <p>Method under test: {@link JtaTransactionContext#getTransaction()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Transaction JtaTransactionContext.getTransaction()"})
  public void testGetTransaction_givenTransactionManagerGetTransactionReturnTransaction()
      throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getTransaction()).thenReturn(mock(Transaction.class));

    // Act
    new JtaTransactionContext(transactionManager).getTransaction();

    // Assert
    verify(transactionManager).getTransaction();
  }

  /**
   * Test {@link JtaTransactionContext#getTransaction()}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link JtaTransactionContext#getTransaction()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Transaction JtaTransactionContext.getTransaction()"})
  public void testGetTransaction_thenThrowActivitiException() throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getTransaction()).thenThrow(new SystemException(1));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> new JtaTransactionContext(transactionManager).getTransaction());
    verify(transactionManager).getTransaction();
  }

  /**
   * Test {@link JtaTransactionContext#getTransaction()}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link JtaTransactionContext#getTransaction()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Transaction JtaTransactionContext.getTransaction()"})
  public void testGetTransaction_thenThrowIllegalStateException() throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getTransaction()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> new JtaTransactionContext(transactionManager).getTransaction());
    verify(transactionManager).getTransaction();
  }

  /**
   * Test {@link JtaTransactionContext#addTransactionListener(TransactionState,
   * TransactionListener)}.
   *
   * <p>Method under test: {@link JtaTransactionContext#addTransactionListener(TransactionState,
   * TransactionListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void JtaTransactionContext.addTransactionListener(TransactionState, TransactionListener)"
  })
  public void testAddTransactionListener() throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getTransaction()).thenThrow(new SystemException(1));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            new JtaTransactionContext(transactionManager)
                .addTransactionListener(
                    TransactionState.COMMITTED, mock(TransactionListener.class)));
    verify(transactionManager).getTransaction();
  }

  /**
   * Test {@link JtaTransactionContext#addTransactionListener(TransactionState,
   * TransactionListener)}.
   *
   * <p>Method under test: {@link JtaTransactionContext#addTransactionListener(TransactionState,
   * TransactionListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void JtaTransactionContext.addTransactionListener(TransactionState, TransactionListener)"
  })
  public void testAddTransactionListener2()
      throws RollbackException, SystemException, IllegalStateException {
    // Arrange
    Transaction transaction = mock(Transaction.class);
    doThrow(new IllegalStateException())
        .when(transaction)
        .registerSynchronization(Mockito.<Synchronization>any());

    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getTransaction()).thenReturn(transaction);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            new JtaTransactionContext(transactionManager)
                .addTransactionListener(
                    TransactionState.COMMITTED, mock(TransactionListener.class)));
    verify(transaction).registerSynchronization(isA(Synchronization.class));
    verify(transactionManager).getTransaction();
  }

  /**
   * Test {@link JtaTransactionContext#addTransactionListener(TransactionState,
   * TransactionListener)}.
   *
   * <p>Method under test: {@link JtaTransactionContext#addTransactionListener(TransactionState,
   * TransactionListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void JtaTransactionContext.addTransactionListener(TransactionState, TransactionListener)"
  })
  public void testAddTransactionListener3()
      throws RollbackException, SystemException, IllegalStateException {
    // Arrange
    Transaction transaction = mock(Transaction.class);
    doThrow(new ActivitiException("An error occurred"))
        .when(transaction)
        .registerSynchronization(Mockito.<Synchronization>any());

    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getTransaction()).thenReturn(transaction);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            new JtaTransactionContext(transactionManager)
                .addTransactionListener(
                    TransactionState.COMMITTED, mock(TransactionListener.class)));
    verify(transaction).registerSynchronization(isA(Synchronization.class));
    verify(transactionManager).getTransaction();
  }

  /**
   * Test {@link JtaTransactionContext#addTransactionListener(TransactionState,
   * TransactionListener)}.
   *
   * <p>Method under test: {@link JtaTransactionContext#addTransactionListener(TransactionState,
   * TransactionListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void JtaTransactionContext.addTransactionListener(TransactionState, TransactionListener)"
  })
  public void testAddTransactionListener4()
      throws RollbackException, SystemException, IllegalStateException {
    // Arrange
    Transaction transaction = mock(Transaction.class);
    doThrow(new SystemException(1))
        .when(transaction)
        .registerSynchronization(Mockito.<Synchronization>any());

    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getTransaction()).thenReturn(transaction);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            new JtaTransactionContext(transactionManager)
                .addTransactionListener(
                    TransactionState.COMMITTED, mock(TransactionListener.class)));
    verify(transaction).registerSynchronization(isA(Synchronization.class));
    verify(transactionManager).getTransaction();
  }

  /**
   * Test {@link JtaTransactionContext#addTransactionListener(TransactionState,
   * TransactionListener)}.
   *
   * <p>Method under test: {@link JtaTransactionContext#addTransactionListener(TransactionState,
   * TransactionListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void JtaTransactionContext.addTransactionListener(TransactionState, TransactionListener)"
  })
  public void testAddTransactionListener5()
      throws RollbackException, SystemException, IllegalStateException {
    // Arrange
    Transaction transaction = mock(Transaction.class);
    doThrow(new RollbackException("Msg"))
        .when(transaction)
        .registerSynchronization(Mockito.<Synchronization>any());

    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getTransaction()).thenReturn(transaction);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            new JtaTransactionContext(transactionManager)
                .addTransactionListener(
                    TransactionState.COMMITTED, mock(TransactionListener.class)));
    verify(transaction).registerSynchronization(isA(Synchronization.class));
    verify(transactionManager).getTransaction();
  }

  /**
   * Test {@link JtaTransactionContext#addTransactionListener(TransactionState,
   * TransactionListener)}.
   *
   * <ul>
   *   <li>Given {@link Transaction} {@link Transaction#registerSynchronization(Synchronization)}
   *       does nothing.
   * </ul>
   *
   * <p>Method under test: {@link JtaTransactionContext#addTransactionListener(TransactionState,
   * TransactionListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void JtaTransactionContext.addTransactionListener(TransactionState, TransactionListener)"
  })
  public void testAddTransactionListener_givenTransactionRegisterSynchronizationDoesNothing()
      throws RollbackException, SystemException, IllegalStateException {
    // Arrange
    Transaction transaction = mock(Transaction.class);
    doNothing().when(transaction).registerSynchronization(Mockito.<Synchronization>any());

    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getTransaction()).thenReturn(transaction);

    // Act
    new JtaTransactionContext(transactionManager)
        .addTransactionListener(TransactionState.COMMITTED, mock(TransactionListener.class));

    // Assert
    verify(transaction).registerSynchronization(isA(Synchronization.class));
    verify(transactionManager).getTransaction();
  }

  /**
   * Test {@link JtaTransactionContext#addTransactionListener(TransactionState,
   * TransactionListener)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link JtaTransactionContext#addTransactionListener(TransactionState,
   * TransactionListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void JtaTransactionContext.addTransactionListener(TransactionState, TransactionListener)"
  })
  public void testAddTransactionListener_thenThrowIllegalStateException() throws SystemException {
    // Arrange
    TransactionManager transactionManager = mock(TransactionManager.class);
    when(transactionManager.getTransaction()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            new JtaTransactionContext(transactionManager)
                .addTransactionListener(
                    TransactionState.COMMITTED, mock(TransactionListener.class)));
    verify(transactionManager).getTransaction();
  }

  /**
   * Test TransactionStateSynchronization {@link
   * TransactionStateSynchronization#afterCompletion(int)}.
   *
   * <ul>
   *   <li>Then calls {@link TransactionListener#execute(CommandContext)}.
   * </ul>
   *
   * <p>Method under test: {@link TransactionStateSynchronization#afterCompletion(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TransactionStateSynchronization.afterCompletion(int)"})
  public void testTransactionStateSynchronizationAfterCompletion_thenCallsExecute() {
    // Arrange
    TransactionListener transactionListener = mock(TransactionListener.class);
    doNothing().when(transactionListener).execute(Mockito.<CommandContext>any());
    TransactionStateSynchronization transactionStateSynchronization =
        new TransactionStateSynchronization(TransactionState.COMMITTED, transactionListener, null);

    // Act
    transactionStateSynchronization.afterCompletion(3);

    // Assert
    verify(transactionListener).execute(isNull());
  }

  /**
   * Test TransactionStateSynchronization {@link
   * TransactionStateSynchronization#afterCompletion(int)}.
   *
   * <ul>
   *   <li>Then calls {@link TransactionListener#execute(CommandContext)}.
   * </ul>
   *
   * <p>Method under test: {@link TransactionStateSynchronization#afterCompletion(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TransactionStateSynchronization.afterCompletion(int)"})
  public void testTransactionStateSynchronizationAfterCompletion_thenCallsExecute2() {
    // Arrange
    TransactionListener transactionListener = mock(TransactionListener.class);
    doNothing().when(transactionListener).execute(Mockito.<CommandContext>any());
    TransactionStateSynchronization transactionStateSynchronization =
        new TransactionStateSynchronization(
            TransactionState.ROLLED_BACK, transactionListener, null);

    // Act
    transactionStateSynchronization.afterCompletion(4);

    // Assert
    verify(transactionListener).execute(isNull());
  }

  /**
   * Test TransactionStateSynchronization {@link
   * TransactionStateSynchronization#beforeCompletion()}.
   *
   * <p>Method under test: {@link TransactionStateSynchronization#beforeCompletion()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TransactionStateSynchronization.beforeCompletion()"})
  public void testTransactionStateSynchronizationBeforeCompletion() {
    // Arrange
    TransactionListener transactionListener = mock(TransactionListener.class);
    doNothing().when(transactionListener).execute(Mockito.<CommandContext>any());
    TransactionStateSynchronization transactionStateSynchronization =
        new TransactionStateSynchronization(TransactionState.COMMITTING, transactionListener, null);

    // Act
    transactionStateSynchronization.beforeCompletion();

    // Assert
    verify(transactionListener).execute(isNull());
  }

  /**
   * Test TransactionStateSynchronization {@link
   * TransactionStateSynchronization#beforeCompletion()}.
   *
   * <p>Method under test: {@link TransactionStateSynchronization#beforeCompletion()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TransactionStateSynchronization.beforeCompletion()"})
  public void testTransactionStateSynchronizationBeforeCompletion2() {
    // Arrange
    TransactionListener transactionListener = mock(TransactionListener.class);
    doNothing().when(transactionListener).execute(Mockito.<CommandContext>any());
    TransactionStateSynchronization transactionStateSynchronization =
        new TransactionStateSynchronization(
            TransactionState.ROLLINGBACK, transactionListener, null);

    // Act
    transactionStateSynchronization.beforeCompletion();

    // Assert
    verify(transactionListener).execute(isNull());
  }
}
