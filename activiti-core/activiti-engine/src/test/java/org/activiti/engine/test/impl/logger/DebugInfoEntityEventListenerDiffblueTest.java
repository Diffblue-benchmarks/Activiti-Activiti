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

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiProcessCancelledEventImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

public class DebugInfoEntityEventListenerDiffblueTest {
  /**
   * Test
   * {@link DebugInfoEntityEventListener#DebugInfoEntityEventListener(ProcessExecutionLogger)}.
   * <p>
   * Method under test:
   * {@link DebugInfoEntityEventListener#DebugInfoEntityEventListener(ProcessExecutionLogger)}
   */
  @Test
  public void testNewDebugInfoEntityEventListener() {
    // Arrange and Act
    DebugInfoEntityEventListener actualDebugInfoEntityEventListener = new DebugInfoEntityEventListener(
        new ProcessExecutionLogger());

    // Assert
    ProcessExecutionLogger processExecutionLogger = actualDebugInfoEntityEventListener.processExecutionLogger;
    assertTrue(processExecutionLogger.createdExecutions.isEmpty());
    assertTrue(processExecutionLogger.debugInfoMap.isEmpty());
    assertTrue(processExecutionLogger.deletedExecutions.isEmpty());
    assertTrue(actualDebugInfoEntityEventListener.isFailOnException());
  }

  /**
   * Test {@link DebugInfoEntityEventListener#onCreate(ActivitiEvent)}.
   * <p>
   * Method under test:
   * {@link DebugInfoEntityEventListener#onCreate(ActivitiEvent)}
   */
  @Test
  public void testOnCreate() {
    // Arrange
    FlowElement flowElement = mock(FlowElement.class);
    when(flowElement.getId()).thenReturn("42");
    ExecutionEntity executionEntity = mock(ExecutionEntity.class);
    when(executionEntity.getCurrentFlowElement()).thenReturn(flowElement);
    DebugInfoExecutionCreated debugInfo = new DebugInfoExecutionCreated(executionEntity);
    ProcessExecutionLogger processExecutionLogger = mock(ProcessExecutionLogger.class);
    doNothing().when(processExecutionLogger).executionCreated(Mockito.<ExecutionEntity>any());
    doNothing().when(processExecutionLogger).addDebugInfo(Mockito.<AbstractDebugInfo>any());
    processExecutionLogger.addDebugInfo(debugInfo);
    DebugInfoEntityEventListener debugInfoEntityEventListener = new DebugInfoEntityEventListener(
        processExecutionLogger);

    // Act
    debugInfoEntityEventListener
        .onCreate(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    // Assert
    verify(flowElement).getId();
    verify(executionEntity, atLeast(1)).getCurrentFlowElement();
    verify(processExecutionLogger, atLeast(1)).addDebugInfo(Mockito.<AbstractDebugInfo>any());
    verify(processExecutionLogger).executionCreated(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link DebugInfoEntityEventListener#onCreate(ActivitiEvent)}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).</li>
   *   <li>Then calls {@link ExecutionEntityImpl#getCurrentFlowElement()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DebugInfoEntityEventListener#onCreate(ActivitiEvent)}
   */
  @Test
  public void testOnCreate_givenAdhocSubProcess_thenCallsGetCurrentFlowElement() {
    // Arrange
    FlowElement flowElement = mock(FlowElement.class);
    when(flowElement.getId()).thenReturn("42");
    ExecutionEntity executionEntity = mock(ExecutionEntity.class);
    when(executionEntity.getCurrentFlowElement()).thenReturn(flowElement);
    DebugInfoExecutionCreated debugInfo = new DebugInfoExecutionCreated(executionEntity);
    ProcessExecutionLogger processExecutionLogger = mock(ProcessExecutionLogger.class);
    doNothing().when(processExecutionLogger).executionCreated(Mockito.<ExecutionEntity>any());
    doNothing().when(processExecutionLogger).addDebugInfo(Mockito.<AbstractDebugInfo>any());
    processExecutionLogger.addDebugInfo(debugInfo);
    DebugInfoEntityEventListener debugInfoEntityEventListener = new DebugInfoEntityEventListener(
        processExecutionLogger);
    ExecutionEntityImpl processInstance = mock(ExecutionEntityImpl.class);
    when(processInstance.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());

    // Act
    debugInfoEntityEventListener.onCreate(new ActivitiProcessCancelledEventImpl(processInstance));

    // Assert
    verify(flowElement).getId();
    verify(executionEntity, atLeast(1)).getCurrentFlowElement();
    verify(processInstance, atLeast(1)).getCurrentFlowElement();
    verify(processExecutionLogger, atLeast(1)).addDebugInfo(Mockito.<AbstractDebugInfo>any());
    verify(processExecutionLogger).executionCreated(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link DebugInfoEntityEventListener#onCreate(ActivitiEvent)}.
   * <ul>
   *   <li>When
   * {@link ActivitiEntityEventImpl#ActivitiEntityEventImpl(Object, ActivitiEventType)}
   * with entity is {@link JSONObject#NULL} and type is
   * {@code ENTITY_CREATED}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DebugInfoEntityEventListener#onCreate(ActivitiEvent)}
   */
  @Test
  public void testOnCreate_whenActivitiEntityEventImplWithEntityIsNullAndTypeIsEntityCreated() {
    // Arrange
    FlowElement flowElement = mock(FlowElement.class);
    when(flowElement.getId()).thenReturn("42");
    ExecutionEntity executionEntity = mock(ExecutionEntity.class);
    when(executionEntity.getCurrentFlowElement()).thenReturn(flowElement);
    DebugInfoExecutionCreated debugInfo = new DebugInfoExecutionCreated(executionEntity);
    ProcessExecutionLogger processExecutionLogger = mock(ProcessExecutionLogger.class);
    doNothing().when(processExecutionLogger).addDebugInfo(Mockito.<AbstractDebugInfo>any());
    processExecutionLogger.addDebugInfo(debugInfo);
    DebugInfoEntityEventListener debugInfoEntityEventListener = new DebugInfoEntityEventListener(
        processExecutionLogger);

    // Act
    debugInfoEntityEventListener
        .onCreate(new ActivitiEntityEventImpl(JSONObject.NULL, ActivitiEventType.ENTITY_CREATED));

    // Assert that nothing has changed
    verify(flowElement).getId();
    verify(executionEntity, atLeast(1)).getCurrentFlowElement();
    verify(processExecutionLogger).addDebugInfo(isA(AbstractDebugInfo.class));
  }

  /**
   * Test {@link DebugInfoEntityEventListener#onDelete(ActivitiEvent)}.
   * <ul>
   *   <li>Then calls
   * {@link ProcessExecutionLogger#executionDeleted(ExecutionEntity)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DebugInfoEntityEventListener#onDelete(ActivitiEvent)}
   */
  @Test
  public void testOnDelete_thenCallsExecutionDeleted() {
    // Arrange
    FlowElement flowElement = mock(FlowElement.class);
    when(flowElement.getId()).thenReturn("42");
    ExecutionEntity executionEntity = mock(ExecutionEntity.class);
    when(executionEntity.getCurrentFlowElement()).thenReturn(flowElement);
    DebugInfoExecutionCreated debugInfo = new DebugInfoExecutionCreated(executionEntity);
    ProcessExecutionLogger processExecutionLogger = mock(ProcessExecutionLogger.class);
    doNothing().when(processExecutionLogger).executionDeleted(Mockito.<ExecutionEntity>any());
    doNothing().when(processExecutionLogger).addDebugInfo(Mockito.<AbstractDebugInfo>any());
    processExecutionLogger.addDebugInfo(debugInfo);
    DebugInfoEntityEventListener debugInfoEntityEventListener = new DebugInfoEntityEventListener(
        processExecutionLogger);

    // Act
    debugInfoEntityEventListener
        .onDelete(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    // Assert
    verify(flowElement).getId();
    verify(executionEntity, atLeast(1)).getCurrentFlowElement();
    verify(processExecutionLogger, atLeast(1)).addDebugInfo(Mockito.<AbstractDebugInfo>any());
    verify(processExecutionLogger).executionDeleted(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link DebugInfoEntityEventListener#onDelete(ActivitiEvent)}.
   * <ul>
   *   <li>When
   * {@link ActivitiEntityEventImpl#ActivitiEntityEventImpl(Object, ActivitiEventType)}
   * with entity is {@link JSONObject#NULL} and type is
   * {@code ENTITY_CREATED}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DebugInfoEntityEventListener#onDelete(ActivitiEvent)}
   */
  @Test
  public void testOnDelete_whenActivitiEntityEventImplWithEntityIsNullAndTypeIsEntityCreated() {
    // Arrange
    FlowElement flowElement = mock(FlowElement.class);
    when(flowElement.getId()).thenReturn("42");
    ExecutionEntity executionEntity = mock(ExecutionEntity.class);
    when(executionEntity.getCurrentFlowElement()).thenReturn(flowElement);
    DebugInfoExecutionCreated debugInfo = new DebugInfoExecutionCreated(executionEntity);
    ProcessExecutionLogger processExecutionLogger = mock(ProcessExecutionLogger.class);
    doNothing().when(processExecutionLogger).addDebugInfo(Mockito.<AbstractDebugInfo>any());
    processExecutionLogger.addDebugInfo(debugInfo);
    DebugInfoEntityEventListener debugInfoEntityEventListener = new DebugInfoEntityEventListener(
        processExecutionLogger);

    // Act
    debugInfoEntityEventListener
        .onDelete(new ActivitiEntityEventImpl(JSONObject.NULL, ActivitiEventType.ENTITY_CREATED));

    // Assert that nothing has changed
    verify(flowElement).getId();
    verify(executionEntity, atLeast(1)).getCurrentFlowElement();
    verify(processExecutionLogger).addDebugInfo(isA(AbstractDebugInfo.class));
  }

  /**
   * Test {@link DebugInfoEntityEventListener#getExecutionEntity(ActivitiEvent)}.
   * <ul>
   *   <li>Then return createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DebugInfoEntityEventListener#getExecutionEntity(ActivitiEvent)}
   */
  @Test
  public void testGetExecutionEntity_thenReturnCreateWithEmptyRelationshipCollections() {
    // Arrange
    FlowElement flowElement = mock(FlowElement.class);
    when(flowElement.getId()).thenReturn("42");
    ExecutionEntity executionEntity = mock(ExecutionEntity.class);
    when(executionEntity.getCurrentFlowElement()).thenReturn(flowElement);
    DebugInfoExecutionCreated debugInfo = new DebugInfoExecutionCreated(executionEntity);
    ProcessExecutionLogger processExecutionLogger = mock(ProcessExecutionLogger.class);
    doNothing().when(processExecutionLogger).addDebugInfo(Mockito.<AbstractDebugInfo>any());
    processExecutionLogger.addDebugInfo(debugInfo);
    DebugInfoEntityEventListener debugInfoEntityEventListener = new DebugInfoEntityEventListener(
        processExecutionLogger);
    ExecutionEntityImpl processInstance = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    ExecutionEntity actualExecutionEntity = debugInfoEntityEventListener
        .getExecutionEntity(new ActivitiProcessCancelledEventImpl(processInstance));

    // Assert
    verify(flowElement).getId();
    verify(executionEntity, atLeast(1)).getCurrentFlowElement();
    verify(processExecutionLogger).addDebugInfo(isA(AbstractDebugInfo.class));
    assertSame(processInstance, actualExecutionEntity);
  }

  /**
   * Test {@link DebugInfoEntityEventListener#getExecutionEntity(ActivitiEvent)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DebugInfoEntityEventListener#getExecutionEntity(ActivitiEvent)}
   */
  @Test
  public void testGetExecutionEntity_thenReturnNull() {
    // Arrange
    FlowElement flowElement = mock(FlowElement.class);
    when(flowElement.getId()).thenReturn("42");
    ExecutionEntity executionEntity = mock(ExecutionEntity.class);
    when(executionEntity.getCurrentFlowElement()).thenReturn(flowElement);
    DebugInfoExecutionCreated debugInfo = new DebugInfoExecutionCreated(executionEntity);
    ProcessExecutionLogger processExecutionLogger = mock(ProcessExecutionLogger.class);
    doNothing().when(processExecutionLogger).addDebugInfo(Mockito.<AbstractDebugInfo>any());
    processExecutionLogger.addDebugInfo(debugInfo);
    DebugInfoEntityEventListener debugInfoEntityEventListener = new DebugInfoEntityEventListener(
        processExecutionLogger);

    // Act
    ExecutionEntity actualExecutionEntity = debugInfoEntityEventListener
        .getExecutionEntity(new ActivitiEntityEventImpl(JSONObject.NULL, ActivitiEventType.ENTITY_CREATED));

    // Assert
    verify(flowElement).getId();
    verify(executionEntity, atLeast(1)).getCurrentFlowElement();
    verify(processExecutionLogger).addDebugInfo(isA(AbstractDebugInfo.class));
    assertNull(actualExecutionEntity);
  }
}
