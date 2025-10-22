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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Event;
import org.activiti.bpmn.model.MapExceptionEntry;
import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.persistence.entity.AbstractEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class ErrorPropagationDiffblueTest {
  /**
   * Test {@link ErrorPropagation#propagateError(BpmnError, DelegateExecution)} with {@code error}, {@code execution}.
   * <ul>
   *   <li>Then throw {@link BpmnError}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ErrorPropagation#propagateError(BpmnError, DelegateExecution)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ErrorPropagation.propagateError(BpmnError, DelegateExecution)"})
  public void testPropagateErrorWithErrorExecution_thenThrowBpmnError() {
    // Arrange
    BpmnError error = new BpmnError("An error occurred");

    // Act and Assert
    assertThrows(BpmnError.class,
        () -> ErrorPropagation.propagateError(error, ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link ErrorPropagation#propagateError(BpmnError, DelegateExecution)} with {@code error}, {@code execution}.
   * <ul>
   *   <li>Then throw {@link BpmnError}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ErrorPropagation#propagateError(BpmnError, DelegateExecution)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ErrorPropagation.propagateError(BpmnError, DelegateExecution)"})
  public void testPropagateErrorWithErrorExecution_thenThrowBpmnError2() {
    // Arrange, Act and Assert
    assertThrows(BpmnError.class, () -> ErrorPropagation.propagateError(new BpmnError("An error occurred"), null));
  }

  /**
   * Test {@link ErrorPropagation#propagateError(String, DelegateExecution)} with {@code errorRef}, {@code execution}.
   * <ul>
   *   <li>When {@code An error occurred}.</li>
   *   <li>Then throw {@link BpmnError}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ErrorPropagation#propagateError(String, DelegateExecution)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ErrorPropagation.propagateError(String, DelegateExecution)"})
  public void testPropagateErrorWithErrorRefExecution_whenAnErrorOccurred_thenThrowBpmnError() {
    // Arrange, Act and Assert
    assertThrows(BpmnError.class, () -> ErrorPropagation.propagateError("An error occurred",
        ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link ErrorPropagation#propagateError(String, DelegateExecution)} with {@code errorRef}, {@code execution}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link BpmnError}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ErrorPropagation#propagateError(String, DelegateExecution)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ErrorPropagation.propagateError(String, DelegateExecution)"})
  public void testPropagateErrorWithErrorRefExecution_whenNull_thenThrowBpmnError() {
    // Arrange, Act and Assert
    assertThrows(BpmnError.class, () -> ErrorPropagation.propagateError("An error occurred", null));
  }

  /**
   * Test {@link ErrorPropagation#executeCatch(Map, DelegateExecution, String)}.
   * <p>
   * Method under test: {@link ErrorPropagation#executeCatch(Map, DelegateExecution, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ErrorPropagation.executeCatch(Map, DelegateExecution, String)"})
  public void testExecuteCatch() {
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
    assertThrows(BpmnError.class,
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
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link BoundaryEvent} (default constructor).</li>
   *   <li>Then calls {@link AbstractEntity#getId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ErrorPropagation#executeCatch(Map, DelegateExecution, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ErrorPropagation.executeCatch(Map, DelegateExecution, String)"})
  public void testExecuteCatch_givenArrayListAddBoundaryEvent_thenCallsGetId() {
    // Arrange
    ArrayList<Event> eventList = new ArrayList<>();
    eventList.add(new BoundaryEvent());

    HashMap<String, List<Event>> eventMap = new HashMap<>();
    eventMap.put("foo", eventList);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getActivityId()).thenThrow(new BpmnError("An error occurred"));
    when(executionEntityImpl.getParentId()).thenThrow(new BpmnError("An error occurred"));
    when(executionEntityImpl.getId()).thenReturn("42");
    when(executionEntityImpl.getProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());
    ExecutionEntityImpl executionEntityImpl2 = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl2.getParent()).thenReturn(executionEntityImpl);
    when(executionEntityImpl2.getActivityId()).thenReturn("42");
    when(executionEntityImpl2.getParentId()).thenReturn("42");
    when(executionEntityImpl2.getId()).thenReturn("ProcessDefinitionHelper is not set for the current context.");
    when(executionEntityImpl2.getProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl2.getCurrentFlowElement()).thenReturn(null);
    ExecutionEntityImpl executionEntityImpl3 = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl3.getParent()).thenReturn(executionEntityImpl2);
    when(executionEntityImpl3.getActivityId()).thenReturn("42");
    when(executionEntityImpl3.getParentId()).thenReturn("42");
    when(executionEntityImpl3.getId()).thenReturn("42");
    when(executionEntityImpl3.getProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl3.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());
    ExecutionEntityImpl delegateExecution = mock(ExecutionEntityImpl.class);
    when(delegateExecution.getActivityId()).thenReturn("42");
    when(delegateExecution.getParent()).thenReturn(executionEntityImpl3);

    // Act and Assert
    assertThrows(BpmnError.class,
        () -> ErrorPropagation.executeCatch(eventMap, delegateExecution, "An error occurred"));
    verify(executionEntityImpl2).getId();
    verify(delegateExecution).getActivityId();
    verify(executionEntityImpl3).getActivityId();
    verify(executionEntityImpl2).getActivityId();
    verify(executionEntityImpl).getActivityId();
    verify(executionEntityImpl2).getCurrentFlowElement();
    verify(executionEntityImpl3, atLeast(1)).getCurrentFlowElement();
    verify(executionEntityImpl, atLeast(1)).getCurrentFlowElement();
    verify(delegateExecution).getParent();
    verify(executionEntityImpl3).getParent();
    verify(executionEntityImpl2).getParent();
    verify(executionEntityImpl3).getParentId();
    verify(executionEntityImpl2).getParentId();
    verify(executionEntityImpl2).getProcessInstanceId();
  }

  /**
   * Test {@link ErrorPropagation#executeCatch(Map, DelegateExecution, String)}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#getParentId()} return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link ErrorPropagation#executeCatch(Map, DelegateExecution, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
    boolean actualExecuteCatchResult = ErrorPropagation.executeCatch(eventMap, delegateExecution, "An error occurred");

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
   * <ul>
   *   <li>Then throw {@link BpmnError}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ErrorPropagation#executeCatch(Map, DelegateExecution, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ErrorPropagation.executeCatch(Map, DelegateExecution, String)"})
  public void testExecuteCatch_thenThrowBpmnError() {
    // Arrange
    HashMap<String, List<Event>> eventMap = new HashMap<>();
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getActivityId()).thenThrow(new BpmnError("An error occurred"));
    when(executionEntityImpl.getParentId()).thenThrow(new BpmnError("An error occurred"));
    when(executionEntityImpl.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());
    ExecutionEntityImpl executionEntityImpl2 = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl2.getParent()).thenReturn(executionEntityImpl);
    when(executionEntityImpl2.getActivityId()).thenReturn("42");
    when(executionEntityImpl2.getParentId()).thenReturn("42");
    when(executionEntityImpl2.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());
    ExecutionEntityImpl delegateExecution = mock(ExecutionEntityImpl.class);
    when(delegateExecution.getActivityId()).thenReturn("42");
    when(delegateExecution.getParent()).thenReturn(executionEntityImpl2);

    // Act and Assert
    assertThrows(BpmnError.class,
        () -> ErrorPropagation.executeCatch(eventMap, delegateExecution, "An error occurred"));
    verify(delegateExecution).getActivityId();
    verify(executionEntityImpl2).getActivityId();
    verify(executionEntityImpl).getActivityId();
    verify(executionEntityImpl2, atLeast(1)).getCurrentFlowElement();
    verify(executionEntityImpl, atLeast(1)).getCurrentFlowElement();
    verify(delegateExecution).getParent();
    verify(executionEntityImpl2).getParent();
    verify(executionEntityImpl2).getParentId();
  }

  /**
   * Test {@link ErrorPropagation#executeCatch(Map, DelegateExecution, String)}.
   * <ul>
   *   <li>When createWithEmptyRelationshipCollections.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ErrorPropagation#executeCatch(Map, DelegateExecution, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ErrorPropagation.executeCatch(Map, DelegateExecution, String)"})
  public void testExecuteCatch_whenCreateWithEmptyRelationshipCollections_thenReturnFalse() {
    // Arrange
    HashMap<String, List<Event>> eventMap = new HashMap<>();

    // Act and Assert
    assertFalse(ErrorPropagation.executeCatch(eventMap, ExecutionEntityImpl.createWithEmptyRelationshipCollections(),
        "An error occurred"));
  }

  /**
   * Test {@link ErrorPropagation#mapException(Exception, ExecutionEntity, List)}.
   * <p>
   * Method under test: {@link ErrorPropagation#mapException(Exception, ExecutionEntity, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ErrorPropagation.mapException(Exception, ExecutionEntity, List)"})
  public void testMapException() {
    // Arrange
    Exception e = new Exception("foo");
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    ArrayList<MapExceptionEntry> exceptionMap = new ArrayList<>();
    exceptionMap.add(new MapExceptionEntry("An error occurred", "java.lang.Exception", true));
    exceptionMap.add(new MapExceptionEntry("An error occurred", "Class Name", true));

    // Act and Assert
    assertThrows(BpmnError.class, () -> ErrorPropagation.mapException(e, execution, exceptionMap));
  }

  /**
   * Test {@link ErrorPropagation#mapException(Exception, ExecutionEntity, List)}.
   * <p>
   * Method under test: {@link ErrorPropagation#mapException(Exception, ExecutionEntity, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ErrorPropagation.mapException(Exception, ExecutionEntity, List)"})
  public void testMapException2() {
    // Arrange
    Exception e = new Exception("foo");
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    ArrayList<MapExceptionEntry> exceptionMap = new ArrayList<>();
    exceptionMap.add(new MapExceptionEntry("", "Class Name", true));

    // Act and Assert
    assertFalse(ErrorPropagation.mapException(e, execution, exceptionMap));
  }

  /**
   * Test {@link ErrorPropagation#mapException(Exception, ExecutionEntity, List)}.
   * <p>
   * Method under test: {@link ErrorPropagation#mapException(Exception, ExecutionEntity, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ErrorPropagation.mapException(Exception, ExecutionEntity, List)"})
  public void testMapException3() {
    // Arrange
    Exception e = new Exception("foo");
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    ArrayList<MapExceptionEntry> exceptionMap = new ArrayList<>();
    exceptionMap.add(new MapExceptionEntry("An error occurred", "", true));

    // Act and Assert
    assertThrows(BpmnError.class, () -> ErrorPropagation.mapException(e, execution, exceptionMap));
  }

  /**
   * Test {@link ErrorPropagation#mapException(Exception, ExecutionEntity, List)}.
   * <p>
   * Method under test: {@link ErrorPropagation#mapException(Exception, ExecutionEntity, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ErrorPropagation.mapException(Exception, ExecutionEntity, List)"})
  public void testMapException4() {
    // Arrange
    Exception e = new Exception("foo");
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    ArrayList<MapExceptionEntry> exceptionMap = new ArrayList<>();
    exceptionMap.add(new MapExceptionEntry("An error occurred", "Class Name", false));

    // Act and Assert
    assertFalse(ErrorPropagation.mapException(e, execution, exceptionMap));
  }

  /**
   * Test {@link ErrorPropagation#mapException(Exception, ExecutionEntity, List)}.
   * <p>
   * Method under test: {@link ErrorPropagation#mapException(Exception, ExecutionEntity, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ErrorPropagation.mapException(Exception, ExecutionEntity, List)"})
  public void testMapException5() {
    // Arrange
    Exception e = new Exception("foo");

    ArrayList<MapExceptionEntry> exceptionMap = new ArrayList<>();
    exceptionMap.add(new MapExceptionEntry("An error occurred", "", true));

    // Act and Assert
    assertThrows(BpmnError.class, () -> ErrorPropagation.mapException(e, null, exceptionMap));
  }

  /**
   * Test {@link ErrorPropagation#mapException(Exception, ExecutionEntity, List)}.
   * <p>
   * Method under test: {@link ErrorPropagation#mapException(Exception, ExecutionEntity, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ErrorPropagation.mapException(Exception, ExecutionEntity, List)"})
  public void testMapException6() {
    // Arrange
    Exception e = new Exception("foo");
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    ArrayList<MapExceptionEntry> exceptionMap = new ArrayList<>();
    exceptionMap.add(new MapExceptionEntry("An error occurred", "", true));
    exceptionMap.add(new MapExceptionEntry("An error occurred", "", true));

    // Act and Assert
    assertThrows(BpmnError.class, () -> ErrorPropagation.mapException(e, execution, exceptionMap));
  }

  /**
   * Test {@link ErrorPropagation#mapException(Exception, ExecutionEntity, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ErrorPropagation#mapException(Exception, ExecutionEntity, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ErrorPropagation.mapException(Exception, ExecutionEntity, List)"})
  public void testMapException_whenArrayList_thenReturnFalse() {
    // Arrange
    Exception e = new Exception("foo");
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act and Assert
    assertFalse(ErrorPropagation.mapException(e, execution, new ArrayList<>()));
  }

  /**
   * Test {@link ErrorPropagation#findMatchingExceptionMapping(Exception, List)}.
   * <p>
   * Method under test: {@link ErrorPropagation#findMatchingExceptionMapping(Exception, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ErrorPropagation.findMatchingExceptionMapping(Exception, List)"})
  public void testFindMatchingExceptionMapping() {
    // Arrange
    Exception e = new Exception("foo");

    ArrayList<MapExceptionEntry> exceptionMap = new ArrayList<>();
    exceptionMap.add(new MapExceptionEntry("An error occurred", "java.lang.Exception", true));
    exceptionMap.add(new MapExceptionEntry("An error occurred", "Class Name", true));

    // Act and Assert
    assertEquals("An error occurred", ErrorPropagation.findMatchingExceptionMapping(e, exceptionMap));
  }

  /**
   * Test {@link ErrorPropagation#findMatchingExceptionMapping(Exception, List)}.
   * <p>
   * Method under test: {@link ErrorPropagation#findMatchingExceptionMapping(Exception, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ErrorPropagation.findMatchingExceptionMapping(Exception, List)"})
  public void testFindMatchingExceptionMapping2() {
    // Arrange
    Exception e = new Exception("foo");

    ArrayList<MapExceptionEntry> exceptionMap = new ArrayList<>();
    exceptionMap.add(new MapExceptionEntry("", "Class Name", true));

    // Act and Assert
    assertNull(ErrorPropagation.findMatchingExceptionMapping(e, exceptionMap));
  }

  /**
   * Test {@link ErrorPropagation#findMatchingExceptionMapping(Exception, List)}.
   * <p>
   * Method under test: {@link ErrorPropagation#findMatchingExceptionMapping(Exception, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ErrorPropagation.findMatchingExceptionMapping(Exception, List)"})
  public void testFindMatchingExceptionMapping3() {
    // Arrange
    Exception e = new Exception("foo");

    ArrayList<MapExceptionEntry> exceptionMap = new ArrayList<>();
    exceptionMap.add(new MapExceptionEntry("An error occurred", "", true));

    // Act and Assert
    assertEquals("An error occurred", ErrorPropagation.findMatchingExceptionMapping(e, exceptionMap));
  }

  /**
   * Test {@link ErrorPropagation#findMatchingExceptionMapping(Exception, List)}.
   * <p>
   * Method under test: {@link ErrorPropagation#findMatchingExceptionMapping(Exception, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ErrorPropagation.findMatchingExceptionMapping(Exception, List)"})
  public void testFindMatchingExceptionMapping4() {
    // Arrange
    Exception e = new Exception("foo");

    ArrayList<MapExceptionEntry> exceptionMap = new ArrayList<>();
    exceptionMap.add(new MapExceptionEntry("An error occurred", "Class Name", false));

    // Act and Assert
    assertNull(ErrorPropagation.findMatchingExceptionMapping(e, exceptionMap));
  }

  /**
   * Test {@link ErrorPropagation#findMatchingExceptionMapping(Exception, List)}.
   * <p>
   * Method under test: {@link ErrorPropagation#findMatchingExceptionMapping(Exception, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ErrorPropagation.findMatchingExceptionMapping(Exception, List)"})
  public void testFindMatchingExceptionMapping5() {
    // Arrange
    Exception e = new Exception("foo");

    ArrayList<MapExceptionEntry> exceptionMap = new ArrayList<>();
    exceptionMap.add(new MapExceptionEntry("An error occurred", "", true));
    exceptionMap.add(new MapExceptionEntry("An error occurred", "", true));

    // Act and Assert
    assertEquals("An error occurred", ErrorPropagation.findMatchingExceptionMapping(e, exceptionMap));
  }

  /**
   * Test {@link ErrorPropagation#findMatchingExceptionMapping(Exception, List)}.
   * <p>
   * Method under test: {@link ErrorPropagation#findMatchingExceptionMapping(Exception, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ErrorPropagation.findMatchingExceptionMapping(Exception, List)"})
  public void testFindMatchingExceptionMapping6() {
    // Arrange
    Exception e = new Exception("foo");

    ArrayList<MapExceptionEntry> exceptionMap = new ArrayList<>();
    exceptionMap.add(new MapExceptionEntry(null, "", true));

    // Act and Assert
    assertNull(ErrorPropagation.findMatchingExceptionMapping(e, exceptionMap));
  }

  /**
   * Test {@link ErrorPropagation#findMatchingExceptionMapping(Exception, List)}.
   * <ul>
   *   <li>Given {@link Throwable#Throwable()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ErrorPropagation#findMatchingExceptionMapping(Exception, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ErrorPropagation.findMatchingExceptionMapping(Exception, List)"})
  public void testFindMatchingExceptionMapping_givenThrowable() {
    // Arrange
    IOException e = new IOException("foo");
    e.addSuppressed(new Throwable());

    ArrayList<MapExceptionEntry> exceptionMap = new ArrayList<>();
    exceptionMap.add(new MapExceptionEntry("An error occurred", "java.lang.Exception", true));
    exceptionMap.add(new MapExceptionEntry("An error occurred", "Class Name", true));

    // Act and Assert
    assertEquals("An error occurred", ErrorPropagation.findMatchingExceptionMapping(e, exceptionMap));
  }

  /**
   * Test {@link ErrorPropagation#findMatchingExceptionMapping(Exception, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ErrorPropagation#findMatchingExceptionMapping(Exception, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ErrorPropagation.findMatchingExceptionMapping(Exception, List)"})
  public void testFindMatchingExceptionMapping_whenArrayList_thenReturnNull() {
    // Arrange
    Exception e = new Exception("foo");

    // Act and Assert
    assertNull(ErrorPropagation.findMatchingExceptionMapping(e, new ArrayList<>()));
  }

  /**
   * Test {@link ErrorPropagation#retrieveErrorCode(BpmnModel, String)}.
   * <ul>
   *   <li>Given {@code An error occurred}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ErrorPropagation#retrieveErrorCode(BpmnModel, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ErrorPropagation.retrieveErrorCode(BpmnModel, String)"})
  public void testRetrieveErrorCode_givenAnErrorOccurred() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addError("An error occurred", "An error occurred", "An error occurred");

    // Act and Assert
    assertEquals("An error occurred", ErrorPropagation.retrieveErrorCode(bpmnModel, "An error occurred"));
  }

  /**
   * Test {@link ErrorPropagation#retrieveErrorCode(BpmnModel, String)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.</li>
   *   <li>Then calls {@link BpmnModel#containsErrorRef(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ErrorPropagation#retrieveErrorCode(BpmnModel, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ErrorPropagation.retrieveErrorCode(BpmnModel, String)"})
  public void testRetrieveErrorCode_givenHashMap_thenCallsContainsErrorRef() {
    // Arrange
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getErrors()).thenReturn(new HashMap<>());
    when(bpmnModel.containsErrorRef(Mockito.<String>any())).thenReturn(true);

    // Act
    String actualRetrieveErrorCodeResult = ErrorPropagation.retrieveErrorCode(bpmnModel, "An error occurred");

    // Assert
    verify(bpmnModel).containsErrorRef(eq("An error occurred"));
    verify(bpmnModel).getErrors();
    assertEquals("An error occurred", actualRetrieveErrorCodeResult);
  }

  /**
   * Test {@link ErrorPropagation#retrieveErrorCode(BpmnModel, String)}.
   * <ul>
   *   <li>When {@link BpmnModel} (default constructor).</li>
   *   <li>Then return {@code An error occurred}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ErrorPropagation#retrieveErrorCode(BpmnModel, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ErrorPropagation.retrieveErrorCode(BpmnModel, String)"})
  public void testRetrieveErrorCode_whenBpmnModel_thenReturnAnErrorOccurred() {
    // Arrange, Act and Assert
    assertEquals("An error occurred", ErrorPropagation.retrieveErrorCode(new BpmnModel(), "An error occurred"));
  }
}
