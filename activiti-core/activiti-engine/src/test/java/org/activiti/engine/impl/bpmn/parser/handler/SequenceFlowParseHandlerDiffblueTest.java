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
package org.activiti.engine.impl.bpmn.parser.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.test.util.TestProcessUtil;
import org.activiti.examples.bpmn.executionlistener.CustomSequenceFlowBpmnParseHandler;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class SequenceFlowParseHandlerDiffblueTest {
  /**
   * Test {@link SequenceFlowParseHandler#executeParse(BpmnParse, SequenceFlow)} with {@code
   * BpmnParse}, {@code SequenceFlow}.
   *
   * <p>Method under test: {@link SequenceFlowParseHandler#executeParse(BpmnParse, SequenceFlow)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SequenceFlowParseHandler.executeParse(BpmnParse, SequenceFlow)"})
  public void testExecuteParseWithBpmnParseSequenceFlow() {
    // Arrange
    SequenceFlowParseHandler sequenceFlowParseHandler = new SequenceFlowParseHandler();

    Process process = mock(Process.class);
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(adhocSubProcess);

    BpmnParse bpmnParse = mock(BpmnParse.class);
    when(bpmnParse.getCurrentProcess()).thenReturn(process);
    SequenceFlow sequenceFlow = new SequenceFlow();

    // Act
    sequenceFlowParseHandler.executeParse(bpmnParse, sequenceFlow);

    // Assert
    verify(process, atLeast(1)).getFlowElement(null, true);
    verify(bpmnParse).getCurrentProcess();
    FlowElement sourceFlowElement = sequenceFlow.getSourceFlowElement();
    assertTrue(sourceFlowElement instanceof AdhocSubProcess);
    assertSame(adhocSubProcess, sourceFlowElement);
    assertSame(adhocSubProcess, sequenceFlow.getTargetFlowElement());
  }

  /**
   * Test {@link SequenceFlowParseHandler#executeParse(BpmnParse, SequenceFlow)} with {@code
   * BpmnParse}, {@code SequenceFlow}.
   *
   * <p>Method under test: {@link SequenceFlowParseHandler#executeParse(BpmnParse, SequenceFlow)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SequenceFlowParseHandler.executeParse(BpmnParse, SequenceFlow)"})
  public void testExecuteParseWithBpmnParseSequenceFlow2() {
    // Arrange
    Process process = mock(Process.class);
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(adhocSubProcess);

    BpmnParse bpmnParse = mock(BpmnParse.class);
    when(bpmnParse.getCurrentProcess()).thenReturn(process);
    SequenceFlow sequenceFlow = new SequenceFlow();

    // Act
    ((SequenceFlowParseHandler) new CustomSequenceFlowBpmnParseHandler())
        .executeParse(bpmnParse, sequenceFlow);

    // Assert
    verify(process, atLeast(1)).getFlowElement(null, true);
    verify(bpmnParse).getCurrentProcess();
    FlowElement sourceFlowElement = sequenceFlow.getSourceFlowElement();
    assertTrue(sourceFlowElement instanceof AdhocSubProcess);
    assertSame(adhocSubProcess, sourceFlowElement);
    assertSame(adhocSubProcess, sequenceFlow.getTargetFlowElement());
  }

  /**
   * Test {@link SequenceFlowParseHandler#executeParse(BpmnParse, SequenceFlow)} with {@code
   * BpmnParse}, {@code SequenceFlow}.
   *
   * <ul>
   *   <li>Given createOneTaskProcessWithId {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link SequenceFlowParseHandler#executeParse(BpmnParse, SequenceFlow)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SequenceFlowParseHandler.executeParse(BpmnParse, SequenceFlow)"})
  public void testExecuteParseWithBpmnParseSequenceFlow_givenCreateOneTaskProcessWithId42() {
    // Arrange
    SequenceFlowParseHandler sequenceFlowParseHandler = new SequenceFlowParseHandler();

    BpmnParse bpmnParse = mock(BpmnParse.class);
    when(bpmnParse.getCurrentProcess())
        .thenReturn(TestProcessUtil.createOneTaskProcessWithId("42"));

    // Act
    sequenceFlowParseHandler.executeParse(bpmnParse, new SequenceFlow());

    // Assert that nothing has changed
    verify(bpmnParse).getCurrentProcess();
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link SequenceFlowParseHandler}
   *   <li>{@link SequenceFlowParseHandler#getHandledType()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SequenceFlowParseHandler.<init>()",
    "Class SequenceFlowParseHandler.getHandledType()"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends BaseElement> actualHandledType =
        new SequenceFlowParseHandler().getHandledType();

    // Assert
    Class<SequenceFlow> expectedHandledType = SequenceFlow.class;
    assertEquals(expectedHandledType, actualHandledType);
  }
}
