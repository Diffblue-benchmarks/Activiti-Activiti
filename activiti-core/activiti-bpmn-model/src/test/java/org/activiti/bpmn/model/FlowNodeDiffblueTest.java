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
package org.activiti.bpmn.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;

public class FlowNodeDiffblueTest {
  /**
   * Test {@link FlowNode#isAsynchronous()}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) Asynchronous is
   * {@code true}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowNode#isAsynchronous()}
   */
  @Test
  public void testIsAsynchronous_givenAdhocSubProcessAsynchronousIsTrue_thenReturnTrue() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    adhocSubProcess.setAsynchronous(true);

    // Act and Assert
    assertTrue(adhocSubProcess.isAsynchronous());
  }

  /**
   * Test {@link FlowNode#isAsynchronous()}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowNode#isAsynchronous()}
   */
  @Test
  public void testIsAsynchronous_givenAdhocSubProcess_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new AdhocSubProcess()).isAsynchronous());
  }

  /**
   * Test {@link FlowNode#setAsynchronous(boolean)}.
   * <p>
   * Method under test: {@link FlowNode#setAsynchronous(boolean)}
   */
  @Test
  public void testSetAsynchronous() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    // Act
    adhocSubProcess.setAsynchronous(true);

    // Assert
    assertTrue(adhocSubProcess.isAsynchronous());
  }

  /**
   * Test {@link FlowNode#isExclusive()}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) Exclusive is
   * {@code false}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowNode#isExclusive()}
   */
  @Test
  public void testIsExclusive_givenAdhocSubProcessExclusiveIsFalse_thenReturnFalse() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    adhocSubProcess.setExclusive(false);

    // Act and Assert
    assertFalse(adhocSubProcess.isExclusive());
  }

  /**
   * Test {@link FlowNode#isExclusive()}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowNode#isExclusive()}
   */
  @Test
  public void testIsExclusive_givenAdhocSubProcess_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((new AdhocSubProcess()).isExclusive());
  }

  /**
   * Test {@link FlowNode#setExclusive(boolean)}.
   * <ul>
   *   <li>When {@code false}.</li>
   *   <li>Then not {@link AdhocSubProcess} (default constructor) Exclusive.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowNode#setExclusive(boolean)}
   */
  @Test
  public void testSetExclusive_whenFalse_thenNotAdhocSubProcessExclusive() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    // Act
    adhocSubProcess.setExclusive(false);

    // Assert
    assertFalse(adhocSubProcess.isExclusive());
    assertTrue(adhocSubProcess.isNotExclusive());
  }

  /**
   * Test {@link FlowNode#setExclusive(boolean)}.
   * <ul>
   *   <li>When {@code true}.</li>
   *   <li>Then not {@link AdhocSubProcess} (default constructor) NotExclusive.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowNode#setExclusive(boolean)}
   */
  @Test
  public void testSetExclusive_whenTrue_thenNotAdhocSubProcessNotExclusive() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    // Act
    adhocSubProcess.setExclusive(true);

    // Assert
    assertFalse(adhocSubProcess.isNotExclusive());
    assertTrue(adhocSubProcess.isExclusive());
  }

  /**
   * Test {@link FlowNode#isNotExclusive()}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) NotExclusive is
   * {@code true}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowNode#isNotExclusive()}
   */
  @Test
  public void testIsNotExclusive_givenAdhocSubProcessNotExclusiveIsTrue_thenReturnTrue() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    adhocSubProcess.setNotExclusive(true);

    // Act and Assert
    assertTrue(adhocSubProcess.isNotExclusive());
  }

  /**
   * Test {@link FlowNode#isNotExclusive()}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowNode#isNotExclusive()}
   */
  @Test
  public void testIsNotExclusive_givenAdhocSubProcess_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new AdhocSubProcess()).isNotExclusive());
  }

  /**
   * Test {@link FlowNode#setNotExclusive(boolean)}.
   * <p>
   * Method under test: {@link FlowNode#setNotExclusive(boolean)}
   */
  @Test
  public void testSetNotExclusive() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    // Act
    adhocSubProcess.setNotExclusive(true);

    // Assert
    assertFalse(adhocSubProcess.isExclusive());
    assertTrue(adhocSubProcess.isNotExclusive());
  }

  /**
   * Test {@link FlowNode#getBehavior()}.
   * <p>
   * Method under test: {@link FlowNode#getBehavior()}
   */
  @Test
  public void testGetBehavior() {
    // Arrange, Act and Assert
    assertNull((new AdhocSubProcess()).getBehavior());
  }

  /**
   * Test {@link FlowNode#setBehavior(Object)}.
   * <p>
   * Method under test: {@link FlowNode#setBehavior(Object)}
   */
  @Test
  public void testSetBehavior() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    // Act
    adhocSubProcess.setBehavior("Behavior");

    // Assert
    assertEquals("Behavior", adhocSubProcess.getBehavior());
  }

  /**
   * Test {@link FlowNode#getIncomingFlows()}.
   * <p>
   * Method under test: {@link FlowNode#getIncomingFlows()}
   */
  @Test
  public void testGetIncomingFlows() {
    // Arrange, Act and Assert
    assertTrue((new AdhocSubProcess()).getIncomingFlows().isEmpty());
  }

  /**
   * Test {@link FlowNode#setIncomingFlows(List)}.
   * <ul>
   *   <li>Given {@link SequenceFlow#SequenceFlow(String, String)} with
   * {@code Source Ref} and {@code Target Ref}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowNode#setIncomingFlows(List)}
   */
  @Test
  public void testSetIncomingFlows_givenSequenceFlowWithSourceRefAndTargetRef() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<SequenceFlow> incomingFlows = new ArrayList<>();
    incomingFlows.add(new SequenceFlow("Source Ref", "Target Ref"));

    // Act
    adhocSubProcess.setIncomingFlows(incomingFlows);

    // Assert
    assertSame(incomingFlows, adhocSubProcess.getIncomingFlows());
  }

  /**
   * Test {@link FlowNode#setIncomingFlows(List)}.
   * <ul>
   *   <li>Given {@link SequenceFlow#SequenceFlow(String, String)} with
   * {@code Source Ref} and {@code Target Ref}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowNode#setIncomingFlows(List)}
   */
  @Test
  public void testSetIncomingFlows_givenSequenceFlowWithSourceRefAndTargetRef2() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<SequenceFlow> incomingFlows = new ArrayList<>();
    incomingFlows.add(new SequenceFlow("Source Ref", "Target Ref"));
    incomingFlows.add(new SequenceFlow("Source Ref", "Target Ref"));

    // Act
    adhocSubProcess.setIncomingFlows(incomingFlows);

    // Assert
    assertSame(incomingFlows, adhocSubProcess.getIncomingFlows());
  }

  /**
   * Test {@link FlowNode#setIncomingFlows(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowNode#setIncomingFlows(List)}
   */
  @Test
  public void testSetIncomingFlows_whenArrayList() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    ArrayList<SequenceFlow> incomingFlows = new ArrayList<>();

    // Act
    adhocSubProcess.setIncomingFlows(incomingFlows);

    // Assert
    assertSame(incomingFlows, adhocSubProcess.getIncomingFlows());
  }

  /**
   * Test {@link FlowNode#getOutgoingFlows()}.
   * <p>
   * Method under test: {@link FlowNode#getOutgoingFlows()}
   */
  @Test
  public void testGetOutgoingFlows() {
    // Arrange, Act and Assert
    assertTrue((new AdhocSubProcess()).getOutgoingFlows().isEmpty());
  }

  /**
   * Test {@link FlowNode#setOutgoingFlows(List)}.
   * <ul>
   *   <li>Given {@link SequenceFlow#SequenceFlow(String, String)} with
   * {@code Source Ref} and {@code Target Ref}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowNode#setOutgoingFlows(List)}
   */
  @Test
  public void testSetOutgoingFlows_givenSequenceFlowWithSourceRefAndTargetRef() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<SequenceFlow> outgoingFlows = new ArrayList<>();
    outgoingFlows.add(new SequenceFlow("Source Ref", "Target Ref"));

    // Act
    adhocSubProcess.setOutgoingFlows(outgoingFlows);

    // Assert
    assertSame(outgoingFlows, adhocSubProcess.getOutgoingFlows());
  }

  /**
   * Test {@link FlowNode#setOutgoingFlows(List)}.
   * <ul>
   *   <li>Given {@link SequenceFlow#SequenceFlow(String, String)} with
   * {@code Source Ref} and {@code Target Ref}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowNode#setOutgoingFlows(List)}
   */
  @Test
  public void testSetOutgoingFlows_givenSequenceFlowWithSourceRefAndTargetRef2() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<SequenceFlow> outgoingFlows = new ArrayList<>();
    outgoingFlows.add(new SequenceFlow("Source Ref", "Target Ref"));
    outgoingFlows.add(new SequenceFlow("Source Ref", "Target Ref"));

    // Act
    adhocSubProcess.setOutgoingFlows(outgoingFlows);

    // Assert
    assertSame(outgoingFlows, adhocSubProcess.getOutgoingFlows());
  }

  /**
   * Test {@link FlowNode#setOutgoingFlows(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowNode#setOutgoingFlows(List)}
   */
  @Test
  public void testSetOutgoingFlows_whenArrayList() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    ArrayList<SequenceFlow> outgoingFlows = new ArrayList<>();

    // Act
    adhocSubProcess.setOutgoingFlows(outgoingFlows);

    // Assert
    assertSame(outgoingFlows, adhocSubProcess.getOutgoingFlows());
  }

  /**
   * Test {@link FlowNode#setValues(FlowNode)} with {@code FlowNode}.
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>Then {@link AdhocSubProcess} (default constructor) Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowNode#setValues(FlowNode)}
   */
  @Test
  public void testSetValuesWithFlowNode_givenTrue_thenAdhocSubProcessIdIs42() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    BoundaryEvent otherNode = mock(BoundaryEvent.class);
    when(otherNode.isAsynchronous()).thenReturn(true);
    when(otherNode.isNotExclusive()).thenReturn(true);
    when(otherNode.getId()).thenReturn("42");
    when(otherNode.getDocumentation()).thenReturn("Documentation");
    when(otherNode.getName()).thenReturn("Name");
    when(otherNode.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherNode.getAttributes()).thenReturn(new HashMap<>());
    when(otherNode.getExtensionElements()).thenReturn(new HashMap<>());

    // Act
    adhocSubProcess.setValues(otherNode);

    // Assert
    verify(otherNode, atLeast(1)).getAttributes();
    verify(otherNode, atLeast(1)).getExtensionElements();
    verify(otherNode).getId();
    verify(otherNode).getDocumentation();
    verify(otherNode, atLeast(1)).getExecutionListeners();
    verify(otherNode).getName();
    verify(otherNode).isAsynchronous();
    verify(otherNode).isNotExclusive();
    assertEquals("42", adhocSubProcess.getId());
    assertEquals("Documentation", adhocSubProcess.getDocumentation());
    assertEquals("Name", adhocSubProcess.getName());
    assertFalse(adhocSubProcess.isExclusive());
    assertTrue(adhocSubProcess.isAsynchronous());
    assertTrue(adhocSubProcess.isNotExclusive());
  }

  /**
   * Test {@link FlowNode#setValues(FlowNode)} with {@code FlowNode}.
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).</li>
   *   <li>Then {@link AdhocSubProcess} (default constructor) Id is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowNode#setValues(FlowNode)}
   */
  @Test
  public void testSetValuesWithFlowNode_whenAdhocSubProcess_thenAdhocSubProcessIdIsNull() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    AdhocSubProcess otherNode = new AdhocSubProcess();

    // Act
    adhocSubProcess.setValues((FlowNode) otherNode);

    // Assert
    assertNull(otherNode.getId());
    assertNull(otherNode.getDocumentation());
    assertNull(otherNode.getName());
    assertFalse(otherNode.isAsynchronous());
    assertFalse(otherNode.isNotExclusive());
    assertTrue(otherNode.isExclusive());
  }
}
