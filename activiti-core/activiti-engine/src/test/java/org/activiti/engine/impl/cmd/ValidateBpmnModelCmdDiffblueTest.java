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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ActivitiEngineAgendaFactory;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.agenda.DefaultActivitiEngineAgenda;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.junit.Test;
import org.mockito.Mockito;

public class ValidateBpmnModelCmdDiffblueTest {
  /**
   * Test {@link ValidateBpmnModelCmd#ValidateBpmnModelCmd(BpmnModel)}.
   * <p>
   * Method under test:
   * {@link ValidateBpmnModelCmd#ValidateBpmnModelCmd(BpmnModel)}
   */
  @Test
  public void testNewValidateBpmnModelCmd() {
    // Arrange, Act and Assert
    BpmnModel bpmnModel = (new ValidateBpmnModelCmd(new BpmnModel())).bpmnModel;
    assertTrue(bpmnModel.getResources() instanceof List);
    assertTrue(bpmnModel.getSignals() instanceof List);
    assertNull(bpmnModel.getEventSupport());
    assertNull(bpmnModel.getSourceSystemId());
    assertNull(bpmnModel.getTargetNamespace());
    assertNull(bpmnModel.getStartEventFormTypes());
    assertNull(bpmnModel.getUserTaskFormTypes());
    assertNull(bpmnModel.getMainProcess());
    assertFalse(bpmnModel.hasDiagramInterchangeInfo());
    assertTrue(bpmnModel.getMessages().isEmpty());
    assertTrue(bpmnModel.getGlobalArtifacts().isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(bpmnModel.getDataStores().isEmpty());
    assertTrue(bpmnModel.getDefinitionsAttributes().isEmpty());
    assertTrue(bpmnModel.getErrors().isEmpty());
    assertTrue(bpmnModel.getFlowLocationMap().isEmpty());
    assertTrue(bpmnModel.getItemDefinitions().isEmpty());
    assertTrue(bpmnModel.getLabelLocationMap().isEmpty());
    assertTrue(bpmnModel.getLocationMap().isEmpty());
    assertTrue(bpmnModel.getMessageFlows().isEmpty());
    assertTrue(bpmnModel.getNamespaces().isEmpty());
  }

  /**
   * Test {@link ValidateBpmnModelCmd#execute(CommandContext)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValidateBpmnModelCmd#execute(CommandContext)}
   */
  @Test
  public void testExecute_thenThrowActivitiException() {
    // Arrange
    ValidateBpmnModelCmd validateBpmnModelCmd = new ValidateBpmnModelCmd(new BpmnModel());
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> validateBpmnModelCmd.execute(new CommandContext(mock(Command.class), processEngineConfiguration)));
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
  }
}
