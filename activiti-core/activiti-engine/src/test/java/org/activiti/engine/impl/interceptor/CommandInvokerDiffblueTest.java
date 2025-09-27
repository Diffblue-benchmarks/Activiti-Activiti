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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiEngineAgendaFactory;
import org.activiti.engine.impl.agenda.DefaultActivitiEngineAgenda;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class CommandInvokerDiffblueTest {
  /**
   * Test {@link CommandInvoker#executeOperations(CommandContext)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEngineAgendaFactory#createAgenda(CommandContext)}.
   * </ul>
   *
   * <p>Method under test: {@link CommandInvoker#executeOperations(CommandContext)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CommandInvoker.executeOperations(CommandContext)"})
  public void testExecuteOperations_thenCallsCreateAgenda() {
    // Arrange
    CommandInvoker commandInvoker = new CommandInvoker();

    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext =
        new CommandContext(mock(Command.class), processEngineConfiguration);

    // Act
    commandInvoker.executeOperations(commandContext);

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }

  /**
   * Test {@link CommandInvoker#executeOperation(Runnable)}.
   *
   * <ul>
   *   <li>Then throw {@link UnsupportedOperationException}.
   * </ul>
   *
   * <p>Method under test: {@link CommandInvoker#executeOperation(Runnable)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CommandInvoker.executeOperation(Runnable)"})
  public void testExecuteOperation_thenThrowUnsupportedOperationException() {
    // Arrange
    CommandInvoker commandInvoker = new CommandInvoker();

    Runnable runnable = mock(Runnable.class);
    doThrow(new UnsupportedOperationException()).when(runnable).run();

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class, () -> commandInvoker.executeOperation(runnable));
    verify(runnable).run();
  }

  /**
   * Test {@link CommandInvoker#executeOperation(Runnable)}.
   *
   * <ul>
   *   <li>When {@link Runnable} {@link Runnable#run()} does nothing.
   * </ul>
   *
   * <p>Method under test: {@link CommandInvoker#executeOperation(Runnable)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CommandInvoker.executeOperation(Runnable)"})
  public void testExecuteOperation_whenRunnableRunDoesNothing() {
    // Arrange
    CommandInvoker commandInvoker = new CommandInvoker();

    Runnable runnable = mock(Runnable.class);
    doNothing().when(runnable).run();

    // Act
    commandInvoker.executeOperation(runnable);

    // Assert
    verify(runnable).run();
  }

  /**
   * Test {@link CommandInvoker#setNext(CommandInterceptor)}.
   *
   * <p>Method under test: {@link CommandInvoker#setNext(CommandInterceptor)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CommandInvoker.setNext(CommandInterceptor)"})
  public void testSetNext() {
    // Arrange
    CommandInvoker commandInvoker = new CommandInvoker();

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> commandInvoker.setNext(new CommandContextInterceptor()));
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link CommandInvoker}
   *   <li>{@link CommandInvoker#getNext()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CommandInvoker.<init>()", "CommandInterceptor CommandInvoker.getNext()"})
  public void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertNull(new CommandInvoker().getNext());
  }
}
