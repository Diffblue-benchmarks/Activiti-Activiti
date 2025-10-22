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
package org.activiti.engine.test.impl.logger;

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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import org.activiti.engine.ActivitiEngineAgendaFactory;
import org.activiti.engine.impl.agenda.AbstractOperation;
import org.activiti.engine.impl.agenda.DefaultActivitiEngineAgenda;
import org.activiti.engine.impl.agenda.ExecuteInactiveBehaviorsOperation;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class DebugInfoOperationExecutedDiffblueTest {
  /**
   * Test {@link DebugInfoOperationExecuted#DebugInfoOperationExecuted(AbstractOperation)}.
   * <ul>
   *   <li>Then {@link DebugInfoOperationExecuted#dateFormat} NumberFormat return {@link DecimalFormat}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DebugInfoOperationExecuted#DebugInfoOperationExecuted(AbstractOperation)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DebugInfoOperationExecuted.<init>(AbstractOperation)"})
  public void testNewDebugInfoOperationExecuted_thenDateFormatNumberFormatReturnDecimalFormat() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    ExecuteInactiveBehaviorsOperation operation = new ExecuteInactiveBehaviorsOperation(
        new CommandContext(mock(Command.class), processEngineConfiguration));

    // Act
    DebugInfoOperationExecuted actualDebugInfoOperationExecuted = new DebugInfoOperationExecuted(operation);

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    SimpleDateFormat simpleDateFormat = actualDebugInfoOperationExecuted.dateFormat;
    assertTrue(simpleDateFormat.getNumberFormat() instanceof DecimalFormat);
    assertTrue(simpleDateFormat.getCalendar() instanceof GregorianCalendar);
    assertEquals("yyyy/MM/dd HH:mm:ss:SSS", simpleDateFormat.toPattern());
    assertNull(actualDebugInfoOperationExecuted.getFlowElementClass());
    assertNull(actualDebugInfoOperationExecuted.getExecutionId());
    assertNull(actualDebugInfoOperationExecuted.getFlowElementId());
    assertEquals(0L, actualDebugInfoOperationExecuted.getPostExecutionTime());
    assertEquals(0L, actualDebugInfoOperationExecuted.getPreExecutionTime());
    assertTrue(simpleDateFormat.isLenient());
    assertTrue(actualDebugInfoOperationExecuted.getExecutionTrees().isEmpty());
    assertSame(operation, actualDebugInfoOperationExecuted.getOperation());
  }
}
