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
package org.activiti.engine.impl.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.ValuedDataObject;
import org.activiti.engine.ActivitiEngineAgendaFactory;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.agenda.DefaultActivitiEngineAgenda;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProcessInstanceHelperDiffblueTest {
  @InjectMocks
  private ProcessInstanceHelper processInstanceHelper;

  /**
   * Test {@link ProcessInstanceHelper#getInitialFlowElement(Process, String)}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).</li>
   *   <li>Then return {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessInstanceHelper#getInitialFlowElement(Process, String)}
   */
  @Test
  public void testGetInitialFlowElement_givenAdhocSubProcess_thenReturnAdhocSubProcess() {
    // Arrange
    Process process = mock(Process.class);
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    when(process.getInitialFlowElement()).thenReturn(adhocSubProcess);

    // Act
    FlowElement actualInitialFlowElement = processInstanceHelper.getInitialFlowElement(process,
        "Process Definition ID");

    // Assert
    verify(process).getInitialFlowElement();
    assertSame(adhocSubProcess, actualInitialFlowElement);
  }

  /**
   * Test {@link ProcessInstanceHelper#getInitialFlowElement(Process, String)}.
   * <ul>
   *   <li>When {@link Process} (default constructor).</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessInstanceHelper#getInitialFlowElement(Process, String)}
   */
  @Test
  public void testGetInitialFlowElement_whenProcess_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class,
        () -> processInstanceHelper.getInitialFlowElement(new Process(), "Process Definition ID"));
  }

  /**
   * Test {@link ProcessInstanceHelper#processDataObjects(Collection)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()} add {@link BooleanDataObject} (default
   * constructor).</li>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessInstanceHelper#processDataObjects(Collection)}
   */
  @Test
  public void testProcessDataObjects_whenArrayListAddBooleanDataObject_thenReturnSizeIsOne() {
    // Arrange
    ProcessInstanceHelper processInstanceHelper = new ProcessInstanceHelper();

    ArrayList<ValuedDataObject> dataObjects = new ArrayList<>();
    dataObjects.add(new BooleanDataObject());
    dataObjects.add(new BooleanDataObject());

    // Act
    Map<String, Object> actualProcessDataObjectsResult = processInstanceHelper.processDataObjects(dataObjects);

    // Assert
    assertEquals(1, actualProcessDataObjectsResult.size());
    assertNull(actualProcessDataObjectsResult.get(null));
  }

  /**
   * Test {@link ProcessInstanceHelper#processDataObjects(Collection)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessInstanceHelper#processDataObjects(Collection)}
   */
  @Test
  public void testProcessDataObjects_whenArrayList_thenReturnEmpty() {
    // Arrange
    ProcessInstanceHelper processInstanceHelper = new ProcessInstanceHelper();

    // Act and Assert
    assertTrue(processInstanceHelper.processDataObjects(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link ProcessInstanceHelper#processDataObjects(Collection)}.
   * <ul>
   *   <li>When {@link LinkedHashSet#LinkedHashSet()} add {@link BooleanDataObject}
   * (default constructor).</li>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessInstanceHelper#processDataObjects(Collection)}
   */
  @Test
  public void testProcessDataObjects_whenLinkedHashSetAddBooleanDataObject_thenReturnSizeIsOne() {
    // Arrange
    ProcessInstanceHelper processInstanceHelper = new ProcessInstanceHelper();

    LinkedHashSet<ValuedDataObject> dataObjects = new LinkedHashSet<>();
    dataObjects.add(new BooleanDataObject());

    // Act
    Map<String, Object> actualProcessDataObjectsResult = processInstanceHelper.processDataObjects(dataObjects);

    // Assert
    assertEquals(1, actualProcessDataObjectsResult.size());
    assertNull(actualProcessDataObjectsResult.get(null));
  }

  /**
   * Test {@link ProcessInstanceHelper#processDataObjects(Collection)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessInstanceHelper#processDataObjects(Collection)}
   */
  @Test
  public void testProcessDataObjects_whenNull_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue((new ProcessInstanceHelper()).processDataObjects(null).isEmpty());
  }

  /**
   * Test
   * {@link ProcessInstanceHelper#getCorrelationKey(CommandContext, MessageEventDefinition, DelegateExecution)}.
   * <ul>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessInstanceHelper#getCorrelationKey(CommandContext, MessageEventDefinition, DelegateExecution)}
   */
  @Test
  public void testGetCorrelationKey_thenReturnNotPresent() {
    // Arrange
    ProcessInstanceHelper processInstanceHelper = new ProcessInstanceHelper();
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(new DefaultActivitiEngineAgenda(null));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);
    CommandContext commandContext = new CommandContext(mock(Command.class), processEngineConfiguration);

    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();

    // Act
    Optional<String> actualCorrelationKey = processInstanceHelper.getCorrelationKey(commandContext,
        messageEventDefinition, ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    assertFalse(actualCorrelationKey.isPresent());
  }
}
