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
package org.activiti.engine.impl.cmd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiEngineAgendaFactory;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiActivityCancelledEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.impl.agenda.DefaultActivitiEngineAgenda;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class DispatchEventCommandDiffblueTest {
  /**
   * Test {@link DispatchEventCommand#DispatchEventCommand(ActivitiEvent)}.
   * <p>
   * Method under test: {@link DispatchEventCommand#DispatchEventCommand(ActivitiEvent)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DispatchEventCommand.<init>(ActivitiEvent)"})
  public void testNewDispatchEventCommand() {
    // Arrange, Act and Assert
    ActivitiEvent activitiEvent = (new DispatchEventCommand(new ActivitiActivityCancelledEventImpl())).event;
    assertTrue(activitiEvent instanceof ActivitiActivityCancelledEventImpl);
    assertNull(((ActivitiActivityCancelledEventImpl) activitiEvent).getCause());
    assertNull(activitiEvent.getExecutionId());
    assertNull(activitiEvent.getProcessDefinitionId());
    assertNull(activitiEvent.getProcessInstanceId());
    assertNull(((ActivitiActivityCancelledEventImpl) activitiEvent).getActivityId());
    assertNull(((ActivitiActivityCancelledEventImpl) activitiEvent).getActivityName());
    assertNull(((ActivitiActivityCancelledEventImpl) activitiEvent).getActivityType());
    assertNull(((ActivitiActivityCancelledEventImpl) activitiEvent).getBehaviorClass());
    assertNull(((ActivitiActivityCancelledEventImpl) activitiEvent).getReason());
    assertEquals(ActivitiEventType.ACTIVITY_CANCELLED, activitiEvent.getType());
  }

  /**
   * Test {@link DispatchEventCommand#execute(CommandContext)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DispatchEventCommand#execute(CommandContext)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.Void DispatchEventCommand.execute(CommandContext)"})
  public void testExecute_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    DispatchEventCommand dispatchEventCommand = new DispatchEventCommand(null);

    ActivitiEventDispatcherImpl eventDispatcher = new ActivitiEventDispatcherImpl();
    eventDispatcher.setEnabled(false);
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    processEngineConfiguration.setEventDispatcher(eventDispatcher);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> dispatchEventCommand.execute(new CommandContext(mock(Command.class), processEngineConfiguration)));
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }
}
