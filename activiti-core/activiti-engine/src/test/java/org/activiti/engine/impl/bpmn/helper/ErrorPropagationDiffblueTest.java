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
package org.activiti.engine.impl.bpmn.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Event;
import org.activiti.bpmn.model.MapExceptionEntry;
import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.test.util.TestProcessUtil;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class ErrorPropagationDiffblueTest {
  /**
   * Test {@link ErrorPropagation#propagateError(BpmnError, DelegateExecution)} with {@code error},
   * {@code execution}.
   *
   * <ul>
   *   <li>Then throw {@link BpmnError}.
   * </ul>
   *
   * <p>Method under test: {@link ErrorPropagation#propagateError(BpmnError, DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ErrorPropagation.propagateError(BpmnError, DelegateExecution)"})
  public void testPropagateErrorWithErrorExecution_thenThrowBpmnError() {
    // Arrange
    BpmnError error = new BpmnError("An error occurred");

    // Act and Assert
    assertThrows(
        BpmnError.class,
        () ->
            ErrorPropagation.propagateError(
                error, ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link ErrorPropagation#propagateError(BpmnError, DelegateExecution)} with {@code error},
   * {@code execution}.
   *
   * <ul>
   *   <li>Then throw {@link BpmnError}.
   * </ul>
   *
   * <p>Method under test: {@link ErrorPropagation#propagateError(BpmnError, DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ErrorPropagation.propagateError(BpmnError, DelegateExecution)"})
  public void testPropagateErrorWithErrorExecution_thenThrowBpmnError2() {
    // Arrange, Act and Assert
    assertThrows(
        BpmnError.class,
        () -> ErrorPropagation.propagateError(new BpmnError("An error occurred"), null));
  }

  /**
   * Test {@link ErrorPropagation#propagateError(String, DelegateExecution)} with {@code errorRef},
   * {@code execution}.
   *
   * <ul>
   *   <li>When {@code An error occurred}.
   *   <li>Then throw {@link BpmnError}.
   * </ul>
   *
   * <p>Method under test: {@link ErrorPropagation#propagateError(String, DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ErrorPropagation.propagateError(String, DelegateExecution)"})
  public void testPropagateErrorWithErrorRefExecution_whenAnErrorOccurred_thenThrowBpmnError() {
    // Arrange, Act and Assert
    assertThrows(
        BpmnError.class,
        () ->
            ErrorPropagation.propagateError(
                "An error occurred", ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link ErrorPropagation#propagateError(String, DelegateExecution)} with {@code errorRef},
   * {@code execution}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link BpmnError}.
   * </ul>
   *
   * <p>Method under test: {@link ErrorPropagation#propagateError(String, DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ErrorPropagation.propagateError(String, DelegateExecution)"})
  public void testPropagateErrorWithErrorRefExecution_whenNull_thenThrowBpmnError() {
    // Arrange, Act and Assert
    assertThrows(BpmnError.class, () -> ErrorPropagation.propagateError("An error occurred", null));
  }

  /**
   * Test {@link ErrorPropagation#executeCatch(Map, DelegateExecution, String)}.
   *
   * <p>Method under test: {@link ErrorPropagation#executeCatch(Map, DelegateExecution, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ErrorPropagation.executeCatch(Map, DelegateExecution, String)"})
  public void testExecuteCatch() {
    // Arrange
    HashMap<String, List<Event>> eventMap = new HashMap<>();

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getCurrentFlowElement()).thenThrow(new BpmnError("An error occurred"));

    ExecutionEntityImpl delegateExecution = mock(ExecutionEntityImpl.class);
    when(delegateExecution.getActivityId()).thenReturn("42");
    when(delegateExecution.getParent()).thenReturn(executionEntityImpl);

    // Act and Assert
    assertThrows(
        BpmnError.class,
        () -> ErrorPropagation.executeCatch(eventMap, delegateExecution, "An error occurred"));
    verify(delegateExecution).getActivityId();
    verify(executionEntityImpl).getCurrentFlowElement();
    verify(delegateExecution).getParent();
  }

  /**
   * Test {@link ErrorPropagation#executeCatch(Map, DelegateExecution, String)}.
   *
   * <p>Method under test: {@link ErrorPropagation#executeCatch(Map, DelegateExecution, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ErrorPropagation.executeCatch(Map, DelegateExecution, String)"})
  public void testExecuteCatch2() {
    // Arrange
    HashMap<String, List<Event>> eventMap = new HashMap<>();

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getActivityId()).thenThrow(new BpmnError("An error occurred"));
    when(executionEntityImpl.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());

    ExecutionEntityImpl delegateExecution = mock(ExecutionEntityImpl.class);
    when(delegateExecution.getActivityId()).thenReturn("42");
    when(delegateExecution.getParent()).thenReturn(executionEntityImpl);

    // Act and Assert
    assertThrows(
        BpmnError.class,
        () -> ErrorPropagation.executeCatch(eventMap, delegateExecution, "An error occurred"));
    verify(delegateExecution).getActivityId();
    verify(executionEntityImpl).getActivityId();
    verify(executionEntityImpl, atLeast(1)).getCurrentFlowElement();
    verify(delegateExecution).getParent();
  }

  /**
   * Test {@link ErrorPropagation#executeCatch(Map, DelegateExecution, String)}.
   *
   * <p>Method under test: {@link ErrorPropagation#executeCatch(Map, DelegateExecution, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ErrorPropagation.executeCatch(Map, DelegateExecution, String)"})
  public void testExecuteCatch3() {
    // Arrange
    HashMap<String, List<Event>> eventMap = new HashMap<>();

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getParent()).thenThrow(new BpmnError("An error occurred"));
    when(executionEntityImpl.getActivityId()).thenReturn("42");
    when(executionEntityImpl.getParentId()).thenReturn("42");
    when(executionEntityImpl.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());

    ExecutionEntityImpl delegateExecution = mock(ExecutionEntityImpl.class);
    when(delegateExecution.getActivityId()).thenReturn("42");
    when(delegateExecution.getParent()).thenReturn(executionEntityImpl);

    // Act and Assert
    assertThrows(
        BpmnError.class,
        () -> ErrorPropagation.executeCatch(eventMap, delegateExecution, "An error occurred"));
    verify(delegateExecution).getActivityId();
    verify(executionEntityImpl).getActivityId();
    verify(executionEntityImpl, atLeast(1)).getCurrentFlowElement();
    verify(delegateExecution).getParent();
    verify(executionEntityImpl).getParent();
    verify(executionEntityImpl).getParentId();
  }

  /**
   * Test {@link ErrorPropagation#executeCatch(Map, DelegateExecution, String)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#getParentId()} return empty
   *       string.
   * </ul>
   *
   * <p>Method under test: {@link ErrorPropagation#executeCatch(Map, DelegateExecution, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ErrorPropagation.executeCatch(Map, DelegateExecution, String)"})
  public void testExecuteCatch_givenExecutionEntityImplGetParentIdReturnEmptyString() {
    // Arrange
    HashMap<String, List<Event>> eventMap = new HashMap<>();

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getActivityId()).thenReturn("42");
    when(executionEntityImpl.getParentId()).thenReturn("");
    when(executionEntityImpl.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());

    ExecutionEntityImpl executionEntityImpl2 = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl2.getParent()).thenReturn(executionEntityImpl);
    when(executionEntityImpl2.getActivityId()).thenReturn("42");
    when(executionEntityImpl2.getParentId()).thenReturn("42");
    when(executionEntityImpl2.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());

    ExecutionEntityImpl delegateExecution = mock(ExecutionEntityImpl.class);
    when(delegateExecution.getActivityId()).thenReturn("42");
    when(delegateExecution.getParent()).thenReturn(executionEntityImpl2);

    // Act
    boolean actualExecuteCatchResult =
        ErrorPropagation.executeCatch(eventMap, delegateExecution, "An error occurred");

    // Assert
    verify(delegateExecution).getActivityId();
    verify(executionEntityImpl2).getActivityId();
    verify(executionEntityImpl).getActivityId();
    verify(executionEntityImpl2, atLeast(1)).getCurrentFlowElement();
    verify(executionEntityImpl, atLeast(1)).getCurrentFlowElement();
    verify(delegateExecution).getParent();
    verify(executionEntityImpl2).getParent();
    verify(executionEntityImpl2).getParentId();
    verify(executionEntityImpl).getParentId();
    assertFalse(actualExecuteCatchResult);
  }

  /**
   * Test {@link ErrorPropagation#executeCatch(Map, DelegateExecution, String)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#getParent()} return {@code
   *       null}.
   *   <li>Then calls {@link ExecutionEntityImpl#getId()}.
   * </ul>
   *
   * <p>Method under test: {@link ErrorPropagation#executeCatch(Map, DelegateExecution, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ErrorPropagation.executeCatch(Map, DelegateExecution, String)"})
  public void testExecuteCatch_givenExecutionEntityImplGetParentReturnNull_thenCallsGetId() {
    // Arrange
    HashMap<String, List<Event>> eventMap = new HashMap<>();

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getParent()).thenReturn(null);
    when(executionEntityImpl.getActivityId()).thenReturn("42");
    when(executionEntityImpl.getParentId()).thenReturn("42");
    when(executionEntityImpl.getId())
        .thenReturn("ProcessDefinitionHelper is not set for the current context.");
    when(executionEntityImpl.getProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl.getCurrentFlowElement()).thenReturn(null);

    ExecutionEntityImpl executionEntityImpl2 = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl2.getParent()).thenReturn(executionEntityImpl);
    when(executionEntityImpl2.getActivityId()).thenReturn("42");
    when(executionEntityImpl2.getParentId()).thenReturn("42");
    when(executionEntityImpl2.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());

    ExecutionEntityImpl delegateExecution = mock(ExecutionEntityImpl.class);
    when(delegateExecution.getActivityId()).thenReturn("42");
    when(delegateExecution.getParent()).thenReturn(executionEntityImpl2);

    // Act
    boolean actualExecuteCatchResult =
        ErrorPropagation.executeCatch(eventMap, delegateExecution, "An error occurred");

    // Assert
    verify(executionEntityImpl).getId();
    verify(delegateExecution).getActivityId();
    verify(executionEntityImpl2).getActivityId();
    verify(executionEntityImpl).getActivityId();
    verify(executionEntityImpl).getCurrentFlowElement();
    verify(executionEntityImpl2, atLeast(1)).getCurrentFlowElement();
    verify(delegateExecution).getParent();
    verify(executionEntityImpl2).getParent();
    verify(executionEntityImpl).getParent();
    verify(executionEntityImpl2).getParentId();
    verify(executionEntityImpl).getParentId();
    verify(executionEntityImpl).getProcessInstanceId();
    assertFalse(actualExecuteCatchResult);
  }

  /**
   * Test {@link ErrorPropagation#executeCatch(Map, DelegateExecution, String)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#getParent()} return {@code
   *       null}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ErrorPropagation#executeCatch(Map, DelegateExecution, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ErrorPropagation.executeCatch(Map, DelegateExecution, String)"})
  public void testExecuteCatch_givenExecutionEntityImplGetParentReturnNull_thenReturnFalse() {
    // Arrange
    HashMap<String, List<Event>> eventMap = new HashMap<>();

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getParent()).thenReturn(null);
    when(executionEntityImpl.getActivityId()).thenReturn("42");
    when(executionEntityImpl.getParentId()).thenReturn("42");
    when(executionEntityImpl.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());

    ExecutionEntityImpl delegateExecution = mock(ExecutionEntityImpl.class);
    when(delegateExecution.getActivityId()).thenReturn("42");
    when(delegateExecution.getParent()).thenReturn(executionEntityImpl);

    // Act
    boolean actualExecuteCatchResult =
        ErrorPropagation.executeCatch(eventMap, delegateExecution, "An error occurred");

    // Assert
    verify(delegateExecution).getActivityId();
    verify(executionEntityImpl).getActivityId();
    verify(executionEntityImpl, atLeast(1)).getCurrentFlowElement();
    verify(delegateExecution).getParent();
    verify(executionEntityImpl).getParent();
    verify(executionEntityImpl).getParentId();
    assertFalse(actualExecuteCatchResult);
  }

  /**
   * Test {@link ErrorPropagation#executeCatch(Map, DelegateExecution, String)}.
   *
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#getProcessDefinitionId()}.
   * </ul>
   *
   * <p>Method under test: {@link ErrorPropagation#executeCatch(Map, DelegateExecution, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ErrorPropagation.executeCatch(Map, DelegateExecution, String)"})
  public void testExecuteCatch_thenCallsGetProcessDefinitionId() {
    // Arrange
    HashMap<String, List<Event>> eventMap = new HashMap<>();

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getProcessDefinitionId())
        .thenThrow(new BpmnError("An error occurred"));
    when(executionEntityImpl.getId()).thenReturn("42");
    when(executionEntityImpl.getProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl.getCurrentFlowElement()).thenReturn(null);

    ExecutionEntityImpl executionEntityImpl2 = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl2.getParent()).thenReturn(executionEntityImpl);
    when(executionEntityImpl2.getActivityId()).thenReturn("42");
    when(executionEntityImpl2.getParentId()).thenReturn("42");
    when(executionEntityImpl2.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());

    ExecutionEntityImpl delegateExecution = mock(ExecutionEntityImpl.class);
    when(delegateExecution.getActivityId()).thenReturn("42");
    when(delegateExecution.getParent()).thenReturn(executionEntityImpl2);

    // Act and Assert
    assertThrows(
        BpmnError.class,
        () -> ErrorPropagation.executeCatch(eventMap, delegateExecution, "An error occurred"));
    verify(executionEntityImpl).getId();
    verify(delegateExecution).getActivityId();
    verify(executionEntityImpl2).getActivityId();
    verify(executionEntityImpl).getCurrentFlowElement();
    verify(executionEntityImpl2, atLeast(1)).getCurrentFlowElement();
    verify(delegateExecution).getParent();
    verify(executionEntityImpl2).getParent();
    verify(executionEntityImpl2).getParentId();
    verify(executionEntityImpl).getProcessDefinitionId();
    verify(executionEntityImpl).getProcessInstanceId();
  }

  /**
   * Test {@link ErrorPropagation#executeCatch(Map, DelegateExecution, String)}.
   *
   * <ul>
   *   <li>When createWithEmptyRelationshipCollections.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ErrorPropagation#executeCatch(Map, DelegateExecution, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ErrorPropagation.executeCatch(Map, DelegateExecution, String)"})
  public void testExecuteCatch_whenCreateWithEmptyRelationshipCollections_thenReturnFalse() {
    // Arrange
    HashMap<String, List<Event>> eventMap = new HashMap<>();

    // Act
    boolean actualExecuteCatchResult =
        ErrorPropagation.executeCatch(
            eventMap,
            ExecutionEntityImpl.createWithEmptyRelationshipCollections(),
            "An error occurred");

    // Assert
    assertFalse(actualExecuteCatchResult);
  }

  /**
   * Test {@link ErrorPropagation#mapException(Exception, ExecutionEntity, List)}.
   *
   * <p>Method under test: {@link ErrorPropagation#mapException(Exception, ExecutionEntity, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ErrorPropagation.mapException(Exception, ExecutionEntity, List)"})
  public void testMapException() {
    // Arrange
    Exception e = new Exception();
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    ArrayList<MapExceptionEntry> exceptionMap = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry = new MapExceptionEntry("not empty", "", false);
    exceptionMap.add(mapExceptionEntry);

    // Act and Assert
    assertThrows(BpmnError.class, () -> ErrorPropagation.mapException(e, execution, exceptionMap));
  }

  /**
   * Test {@link ErrorPropagation#mapException(Exception, ExecutionEntity, List)}.
   *
   * <p>Method under test: {@link ErrorPropagation#mapException(Exception, ExecutionEntity, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ErrorPropagation.mapException(Exception, ExecutionEntity, List)"})
  public void testMapException2() {
    // Arrange
    Exception e = new Exception();
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    ArrayList<MapExceptionEntry> exceptionMap = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry = new MapExceptionEntry("", "", false);
    exceptionMap.add(mapExceptionEntry);

    // Act
    boolean actualMapExceptionResult = ErrorPropagation.mapException(e, execution, exceptionMap);

    // Assert
    assertFalse(actualMapExceptionResult);
  }

  /**
   * Test {@link ErrorPropagation#mapException(Exception, ExecutionEntity, List)}.
   *
   * <p>Method under test: {@link ErrorPropagation#mapException(Exception, ExecutionEntity, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ErrorPropagation.mapException(Exception, ExecutionEntity, List)"})
  public void testMapException3() {
    // Arrange
    Exception e = new Exception();
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    ArrayList<MapExceptionEntry> exceptionMap = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry = new MapExceptionEntry(null, "", false);
    exceptionMap.add(mapExceptionEntry);

    // Act
    boolean actualMapExceptionResult = ErrorPropagation.mapException(e, execution, exceptionMap);

    // Assert
    assertFalse(actualMapExceptionResult);
  }

  /**
   * Test {@link ErrorPropagation#mapException(Exception, ExecutionEntity, List)}.
   *
   * <p>Method under test: {@link ErrorPropagation#mapException(Exception, ExecutionEntity, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ErrorPropagation.mapException(Exception, ExecutionEntity, List)"})
  public void testMapException4() {
    // Arrange
    Exception e = new Exception();
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    ArrayList<MapExceptionEntry> exceptionMap = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry =
        new MapExceptionEntry("An error occurred", "java.lang.Exception", true);
    exceptionMap.add(mapExceptionEntry);
    MapExceptionEntry mapExceptionEntry2 =
        new MapExceptionEntry("An error occurred", "Class Name", true);
    exceptionMap.add(mapExceptionEntry2);

    // Act and Assert
    assertThrows(BpmnError.class, () -> ErrorPropagation.mapException(e, execution, exceptionMap));
  }

  /**
   * Test {@link ErrorPropagation#mapException(Exception, ExecutionEntity, List)}.
   *
   * <p>Method under test: {@link ErrorPropagation#mapException(Exception, ExecutionEntity, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ErrorPropagation.mapException(Exception, ExecutionEntity, List)"})
  public void testMapException5() {
    // Arrange
    Exception e = new Exception();
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    ArrayList<MapExceptionEntry> exceptionMap = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry =
        new MapExceptionEntry("An error occurred", "Class Name", false);
    exceptionMap.add(mapExceptionEntry);

    // Act
    boolean actualMapExceptionResult = ErrorPropagation.mapException(e, execution, exceptionMap);

    // Assert
    assertFalse(actualMapExceptionResult);
  }

  /**
   * Test {@link ErrorPropagation#mapException(Exception, ExecutionEntity, List)}.
   *
   * <p>Method under test: {@link ErrorPropagation#mapException(Exception, ExecutionEntity, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ErrorPropagation.mapException(Exception, ExecutionEntity, List)"})
  public void testMapException6() {
    // Arrange
    Exception e = new Exception();

    ArrayList<MapExceptionEntry> exceptionMap = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry = new MapExceptionEntry("An error occurred", "", true);
    exceptionMap.add(mapExceptionEntry);

    // Act and Assert
    assertThrows(BpmnError.class, () -> ErrorPropagation.mapException(e, null, exceptionMap));
  }

  /**
   * Test {@link ErrorPropagation#mapException(Exception, ExecutionEntity, List)}.
   *
   * <p>Method under test: {@link ErrorPropagation#mapException(Exception, ExecutionEntity, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ErrorPropagation.mapException(Exception, ExecutionEntity, List)"})
  public void testMapException7() {
    // Arrange
    Exception e = new Exception();

    ArrayList<MapExceptionEntry> exceptionMap = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry = new MapExceptionEntry("An error occurred", "", true);
    exceptionMap.add(mapExceptionEntry);
    MapExceptionEntry mapExceptionEntry2 = new MapExceptionEntry("An error occurred", "", true);
    exceptionMap.add(mapExceptionEntry2);

    // Act and Assert
    assertThrows(BpmnError.class, () -> ErrorPropagation.mapException(e, null, exceptionMap));
  }

  /**
   * Test {@link ErrorPropagation#mapException(Exception, ExecutionEntity, List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ErrorPropagation#mapException(Exception, ExecutionEntity, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ErrorPropagation.mapException(Exception, ExecutionEntity, List)"})
  public void testMapException_whenArrayList_thenReturnFalse() {
    // Arrange
    Exception e = new Exception();
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    boolean actualMapExceptionResult =
        ErrorPropagation.mapException(e, execution, new ArrayList<>());

    // Assert
    assertFalse(actualMapExceptionResult);
  }

  /**
   * Test {@link ErrorPropagation#findMatchingExceptionMapping(Exception, List)}.
   *
   * <p>Method under test: {@link ErrorPropagation#findMatchingExceptionMapping(Exception, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ErrorPropagation.findMatchingExceptionMapping(Exception, List)"})
  public void testFindMatchingExceptionMapping() {
    // Arrange
    Exception e = new Exception();

    ArrayList<MapExceptionEntry> exceptionMap = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry = new MapExceptionEntry("not empty", "", false);
    exceptionMap.add(mapExceptionEntry);

    // Act and Assert
    assertEquals("not empty", ErrorPropagation.findMatchingExceptionMapping(e, exceptionMap));
  }

  /**
   * Test {@link ErrorPropagation#findMatchingExceptionMapping(Exception, List)}.
   *
   * <p>Method under test: {@link ErrorPropagation#findMatchingExceptionMapping(Exception, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ErrorPropagation.findMatchingExceptionMapping(Exception, List)"})
  public void testFindMatchingExceptionMapping2() {
    // Arrange
    Exception e = new Exception();

    ArrayList<MapExceptionEntry> exceptionMap = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry = new MapExceptionEntry("not empty", null, false);
    exceptionMap.add(mapExceptionEntry);

    // Act and Assert
    assertEquals("not empty", ErrorPropagation.findMatchingExceptionMapping(e, exceptionMap));
  }

  /**
   * Test {@link ErrorPropagation#findMatchingExceptionMapping(Exception, List)}.
   *
   * <p>Method under test: {@link ErrorPropagation#findMatchingExceptionMapping(Exception, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ErrorPropagation.findMatchingExceptionMapping(Exception, List)"})
  public void testFindMatchingExceptionMapping3() {
    // Arrange
    Exception e = new Exception();

    ArrayList<MapExceptionEntry> exceptionMap = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry = new MapExceptionEntry("", "", false);
    exceptionMap.add(mapExceptionEntry);

    // Act and Assert
    assertNull(ErrorPropagation.findMatchingExceptionMapping(e, exceptionMap));
  }

  /**
   * Test {@link ErrorPropagation#findMatchingExceptionMapping(Exception, List)}.
   *
   * <p>Method under test: {@link ErrorPropagation#findMatchingExceptionMapping(Exception, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ErrorPropagation.findMatchingExceptionMapping(Exception, List)"})
  public void testFindMatchingExceptionMapping4() {
    // Arrange
    Exception e = new Exception();

    ArrayList<MapExceptionEntry> exceptionMap = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry =
        new MapExceptionEntry("An error occurred", "Class Name", false);
    exceptionMap.add(mapExceptionEntry);

    // Act and Assert
    assertNull(ErrorPropagation.findMatchingExceptionMapping(e, exceptionMap));
  }

  /**
   * Test {@link ErrorPropagation#findMatchingExceptionMapping(Exception, List)}.
   *
   * <ul>
   *   <li>Given {@link Throwable#Throwable()}.
   * </ul>
   *
   * <p>Method under test: {@link ErrorPropagation#findMatchingExceptionMapping(Exception, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ErrorPropagation.findMatchingExceptionMapping(Exception, List)"})
  public void testFindMatchingExceptionMapping_givenThrowable() {
    // Arrange
    IOException e = new IOException();
    e.addSuppressed(new Throwable());

    ArrayList<MapExceptionEntry> exceptionMap = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry =
        new MapExceptionEntry("An error occurred", "java.lang.Exception", true);
    exceptionMap.add(mapExceptionEntry);
    MapExceptionEntry mapExceptionEntry2 =
        new MapExceptionEntry("An error occurred", "Class Name", true);
    exceptionMap.add(mapExceptionEntry2);

    // Act and Assert
    assertEquals(
        "An error occurred", ErrorPropagation.findMatchingExceptionMapping(e, exceptionMap));
  }

  /**
   * Test {@link ErrorPropagation#findMatchingExceptionMapping(Exception, List)}.
   *
   * <ul>
   *   <li>Then return {@code An error occurred}.
   * </ul>
   *
   * <p>Method under test: {@link ErrorPropagation#findMatchingExceptionMapping(Exception, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ErrorPropagation.findMatchingExceptionMapping(Exception, List)"})
  public void testFindMatchingExceptionMapping_thenReturnAnErrorOccurred() {
    // Arrange
    Exception e = new Exception();

    ArrayList<MapExceptionEntry> exceptionMap = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry =
        new MapExceptionEntry("An error occurred", "java.lang.Exception", true);
    exceptionMap.add(mapExceptionEntry);
    MapExceptionEntry mapExceptionEntry2 =
        new MapExceptionEntry("An error occurred", "Class Name", true);
    exceptionMap.add(mapExceptionEntry2);

    // Act and Assert
    assertEquals(
        "An error occurred", ErrorPropagation.findMatchingExceptionMapping(e, exceptionMap));
  }

  /**
   * Test {@link ErrorPropagation#findMatchingExceptionMapping(Exception, List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ErrorPropagation#findMatchingExceptionMapping(Exception, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ErrorPropagation.findMatchingExceptionMapping(Exception, List)"})
  public void testFindMatchingExceptionMapping_whenArrayList_thenReturnNull() {
    // Arrange
    Exception e = new Exception();

    // Act and Assert
    assertNull(ErrorPropagation.findMatchingExceptionMapping(e, new ArrayList<>()));
  }

  /**
   * Test {@link ErrorPropagation#retrieveErrorCode(BpmnModel, String)}.
   *
   * <ul>
   *   <li>Given {@code An error occurred}.
   *   <li>When createOneTaskBpmnModel.
   * </ul>
   *
   * <p>Method under test: {@link ErrorPropagation#retrieveErrorCode(BpmnModel, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ErrorPropagation.retrieveErrorCode(BpmnModel, String)"})
  public void testRetrieveErrorCode_givenAnErrorOccurred_whenCreateOneTaskBpmnModel() {
    // Arrange
    BpmnModel bpmnModel = TestProcessUtil.createOneTaskBpmnModel();
    bpmnModel.addError("An error occurred", "An error occurred", "An error occurred");

    // Act and Assert
    assertEquals(
        "An error occurred", ErrorPropagation.retrieveErrorCode(bpmnModel, "An error occurred"));
  }

  /**
   * Test {@link ErrorPropagation#retrieveErrorCode(BpmnModel, String)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.
   *   <li>Then calls {@link BpmnModel#addError(String, String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link ErrorPropagation#retrieveErrorCode(BpmnModel, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ErrorPropagation.retrieveErrorCode(BpmnModel, String)"})
  public void testRetrieveErrorCode_givenHashMap_thenCallsAddError() {
    // Arrange
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getErrors()).thenReturn(new HashMap<>());
    when(bpmnModel.containsErrorRef(Mockito.<String>any())).thenReturn(true);
    doNothing()
        .when(bpmnModel)
        .addError(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    bpmnModel.addError("An error occurred", "An error occurred", "An error occurred");

    // Act
    String actualRetrieveErrorCodeResult =
        ErrorPropagation.retrieveErrorCode(bpmnModel, "An error occurred");

    // Assert
    verify(bpmnModel).addError("An error occurred", "An error occurred", "An error occurred");
    verify(bpmnModel).containsErrorRef("An error occurred");
    verify(bpmnModel).getErrors();
    assertEquals("An error occurred", actualRetrieveErrorCodeResult);
  }

  /**
   * Test {@link ErrorPropagation#retrieveErrorCode(BpmnModel, String)}.
   *
   * <ul>
   *   <li>When createOneTaskBpmnModel.
   *   <li>Then return {@code An error occurred}.
   * </ul>
   *
   * <p>Method under test: {@link ErrorPropagation#retrieveErrorCode(BpmnModel, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ErrorPropagation.retrieveErrorCode(BpmnModel, String)"})
  public void testRetrieveErrorCode_whenCreateOneTaskBpmnModel_thenReturnAnErrorOccurred() {
    // Arrange, Act and Assert
    assertEquals(
        "An error occurred",
        ErrorPropagation.retrieveErrorCode(
            TestProcessUtil.createOneTaskBpmnModel(), "An error occurred"));
  }
}
