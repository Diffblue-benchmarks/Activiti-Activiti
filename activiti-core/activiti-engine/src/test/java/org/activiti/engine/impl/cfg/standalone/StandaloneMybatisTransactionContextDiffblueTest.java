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
package org.activiti.engine.impl.cfg.standalone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.List;
import java.util.Map;
import org.activiti.engine.ActivitiEngineAgendaFactory;
import org.activiti.engine.impl.agenda.DefaultActivitiEngineAgenda;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.TransactionListener;
import org.activiti.engine.impl.cfg.TransactionState;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class StandaloneMybatisTransactionContextDiffblueTest {
  /**
   * Test {@link StandaloneMybatisTransactionContext#addTransactionListener(TransactionState, TransactionListener)}.
   * <p>
   * Method under test: {@link StandaloneMybatisTransactionContext#addTransactionListener(TransactionState, TransactionListener)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void StandaloneMybatisTransactionContext.addTransactionListener(TransactionState, TransactionListener)"})
  public void testAddTransactionListener() {
    // Arrange
    StandaloneMybatisTransactionContext standaloneMybatisTransactionContext = new StandaloneMybatisTransactionContext(
        null);
    TransactionListener transactionListener = mock(TransactionListener.class);

    // Act
    standaloneMybatisTransactionContext.addTransactionListener(TransactionState.COMMITTED, transactionListener);

    // Assert
    Map<TransactionState, List<TransactionListener>> transactionStateListMap = standaloneMybatisTransactionContext.stateTransactionListeners;
    assertEquals(1, transactionStateListMap.size());
    List<TransactionListener> getResult = transactionStateListMap.get(TransactionState.COMMITTED);
    assertEquals(1, getResult.size());
    assertSame(transactionListener, getResult.get(0));
  }

  /**
   * Test {@link StandaloneMybatisTransactionContext#addTransactionListener(TransactionState, TransactionListener)}.
   * <p>
   * Method under test: {@link StandaloneMybatisTransactionContext#addTransactionListener(TransactionState, TransactionListener)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void StandaloneMybatisTransactionContext.addTransactionListener(TransactionState, TransactionListener)"})
  public void testAddTransactionListener2() {
    // Arrange
    StandaloneMybatisTransactionContext standaloneMybatisTransactionContext = new StandaloneMybatisTransactionContext(
        null);
    standaloneMybatisTransactionContext.addTransactionListener(TransactionState.ROLLED_BACK,
        mock(TransactionListener.class));
    TransactionListener transactionListener = mock(TransactionListener.class);

    // Act
    standaloneMybatisTransactionContext.addTransactionListener(TransactionState.COMMITTED, transactionListener);

    // Assert
    Map<TransactionState, List<TransactionListener>> transactionStateListMap = standaloneMybatisTransactionContext.stateTransactionListeners;
    assertEquals(2, transactionStateListMap.size());
    List<TransactionListener> getResult = transactionStateListMap.get(TransactionState.COMMITTED);
    assertEquals(1, getResult.size());
    assertTrue(transactionStateListMap.containsKey(TransactionState.ROLLED_BACK));
    assertSame(transactionListener, getResult.get(0));
  }

  /**
   * Test {@link StandaloneMybatisTransactionContext#addTransactionListener(TransactionState, TransactionListener)}.
   * <p>
   * Method under test: {@link StandaloneMybatisTransactionContext#addTransactionListener(TransactionState, TransactionListener)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void StandaloneMybatisTransactionContext.addTransactionListener(TransactionState, TransactionListener)"})
  public void testAddTransactionListener3() {
    // Arrange
    StandaloneMybatisTransactionContext standaloneMybatisTransactionContext = new StandaloneMybatisTransactionContext(
        null);
    standaloneMybatisTransactionContext.addTransactionListener(TransactionState.COMMITTED,
        mock(TransactionListener.class));
    TransactionListener transactionListener = mock(TransactionListener.class);

    // Act
    standaloneMybatisTransactionContext.addTransactionListener(TransactionState.COMMITTED, transactionListener);

    // Assert
    Map<TransactionState, List<TransactionListener>> transactionStateListMap = standaloneMybatisTransactionContext.stateTransactionListeners;
    assertEquals(1, transactionStateListMap.size());
    List<TransactionListener> getResult = transactionStateListMap.get(TransactionState.COMMITTED);
    assertEquals(2, getResult.size());
    assertSame(transactionListener, getResult.get(1));
  }

  /**
   * Test {@link StandaloneMybatisTransactionContext#rollback()}.
   * <p>
   * Method under test: {@link StandaloneMybatisTransactionContext#rollback()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void StandaloneMybatisTransactionContext.rollback()"})
  public void testRollback() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    StandaloneMybatisTransactionContext standaloneMybatisTransactionContext = new StandaloneMybatisTransactionContext(
        new CommandContext(mock(Command.class), processEngineConfiguration));

    // Act
    standaloneMybatisTransactionContext.rollback();

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    Throwable exception = standaloneMybatisTransactionContext.commandContext.getException();
    assertEquals("Cannot invoke \"java.util.Map.get(Object)\" because \"that\" is null",
        exception.getLocalizedMessage());
    assertEquals("Cannot invoke \"java.util.Map.get(Object)\" because \"that\" is null", exception.getMessage());
    assertNull(exception.getCause());
    assertEquals(0, exception.getSuppressed().length);
  }
}
