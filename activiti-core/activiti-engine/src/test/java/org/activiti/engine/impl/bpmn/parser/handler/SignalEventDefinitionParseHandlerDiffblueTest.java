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
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.IntermediateCatchEvent;
import org.activiti.bpmn.model.Signal;
import org.activiti.bpmn.model.SignalEventDefinition;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.test.util.TestProcessUtil;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class SignalEventDefinitionParseHandlerDiffblueTest {
  /**
   * Test {@link SignalEventDefinitionParseHandler#executeParse(BpmnParse, SignalEventDefinition)}
   * with {@code BpmnParse}, {@code SignalEventDefinition}.
   *
   * <p>Method under test: {@link SignalEventDefinitionParseHandler#executeParse(BpmnParse,
   * SignalEventDefinition)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SignalEventDefinitionParseHandler.executeParse(BpmnParse, SignalEventDefinition)"
  })
  public void testExecuteParseWithBpmnParseSignalEventDefinition() {
    // Arrange
    SignalEventDefinitionParseHandler signalEventDefinitionParseHandler =
        new SignalEventDefinitionParseHandler();

    BpmnModel createOneTaskBpmnModelResult = TestProcessUtil.createOneTaskBpmnModel();
    createOneTaskBpmnModelResult.addSignal(null);

    BpmnParse bpmnParse = mock(BpmnParse.class);
    when(bpmnParse.getActivityBehaviorFactory()).thenReturn(new DefaultActivityBehaviorFactory());
    when(bpmnParse.getCurrentFlowElement()).thenReturn(new IntermediateCatchEvent());
    when(bpmnParse.getBpmnModel()).thenReturn(createOneTaskBpmnModelResult);

    // Act
    signalEventDefinitionParseHandler.executeParse(bpmnParse, new SignalEventDefinition());

    // Assert
    verify(bpmnParse).getActivityBehaviorFactory();
    verify(bpmnParse).getBpmnModel();
    verify(bpmnParse, atLeast(1)).getCurrentFlowElement();
  }

  /**
   * Test {@link SignalEventDefinitionParseHandler#executeParse(BpmnParse, SignalEventDefinition)}
   * with {@code BpmnParse}, {@code SignalEventDefinition}.
   *
   * <p>Method under test: {@link SignalEventDefinitionParseHandler#executeParse(BpmnParse,
   * SignalEventDefinition)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SignalEventDefinitionParseHandler.executeParse(BpmnParse, SignalEventDefinition)"
  })
  public void testExecuteParseWithBpmnParseSignalEventDefinition2() {
    // Arrange
    SignalEventDefinitionParseHandler signalEventDefinitionParseHandler =
        new SignalEventDefinitionParseHandler();

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.containsSignalId(Mockito.<String>any())).thenReturn(false);
    doNothing().when(bpmnModel).addSignal(Mockito.<Signal>any());
    bpmnModel.addSignal(null);

    BpmnParse bpmnParse = mock(BpmnParse.class);
    when(bpmnParse.getActivityBehaviorFactory()).thenReturn(new DefaultActivityBehaviorFactory());
    when(bpmnParse.getCurrentFlowElement()).thenReturn(new IntermediateCatchEvent());
    when(bpmnParse.getBpmnModel()).thenReturn(bpmnModel);

    // Act
    signalEventDefinitionParseHandler.executeParse(bpmnParse, new SignalEventDefinition());

    // Assert
    verify(bpmnModel).addSignal(isNull());
    verify(bpmnModel).containsSignalId(null);
    verify(bpmnParse).getActivityBehaviorFactory();
    verify(bpmnParse).getBpmnModel();
    verify(bpmnParse, atLeast(1)).getCurrentFlowElement();
  }

  /**
   * Test {@link SignalEventDefinitionParseHandler#executeParse(BpmnParse, SignalEventDefinition)}
   * with {@code BpmnParse}, {@code SignalEventDefinition}.
   *
   * <p>Method under test: {@link SignalEventDefinitionParseHandler#executeParse(BpmnParse,
   * SignalEventDefinition)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SignalEventDefinitionParseHandler.executeParse(BpmnParse, SignalEventDefinition)"
  })
  public void testExecuteParseWithBpmnParseSignalEventDefinition3() {
    // Arrange
    SignalEventDefinitionParseHandler signalEventDefinitionParseHandler =
        new SignalEventDefinitionParseHandler();

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getSignal(Mockito.<String>any())).thenReturn(new Signal());
    when(bpmnModel.containsSignalId(Mockito.<String>any())).thenReturn(true);
    doNothing().when(bpmnModel).addSignal(Mockito.<Signal>any());
    bpmnModel.addSignal(null);

    BpmnParse bpmnParse = mock(BpmnParse.class);
    when(bpmnParse.getActivityBehaviorFactory()).thenReturn(new DefaultActivityBehaviorFactory());
    when(bpmnParse.getCurrentFlowElement()).thenReturn(new BoundaryEvent());
    when(bpmnParse.getBpmnModel()).thenReturn(bpmnModel);

    // Act
    signalEventDefinitionParseHandler.executeParse(bpmnParse, new SignalEventDefinition());

    // Assert
    verify(bpmnModel).addSignal(isNull());
    verify(bpmnModel).containsSignalId(null);
    verify(bpmnModel).getSignal(null);
    verify(bpmnParse).getActivityBehaviorFactory();
    verify(bpmnParse, atLeast(1)).getBpmnModel();
    verify(bpmnParse, atLeast(1)).getCurrentFlowElement();
  }

  /**
   * Test {@link SignalEventDefinitionParseHandler#executeParse(BpmnParse, SignalEventDefinition)}
   * with {@code BpmnParse}, {@code SignalEventDefinition}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link SignalEventDefinitionParseHandler#executeParse(BpmnParse,
   * SignalEventDefinition)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SignalEventDefinitionParseHandler.executeParse(BpmnParse, SignalEventDefinition)"
  })
  public void testExecuteParseWithBpmnParseSignalEventDefinition_givenAdhocSubProcess() {
    // Arrange
    SignalEventDefinitionParseHandler signalEventDefinitionParseHandler =
        new SignalEventDefinitionParseHandler();

    BpmnModel createOneTaskBpmnModelResult = TestProcessUtil.createOneTaskBpmnModel();
    createOneTaskBpmnModelResult.addSignal(null);

    BpmnParse bpmnParse = mock(BpmnParse.class);
    when(bpmnParse.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());
    when(bpmnParse.getBpmnModel()).thenReturn(createOneTaskBpmnModelResult);

    // Act
    signalEventDefinitionParseHandler.executeParse(bpmnParse, new SignalEventDefinition());

    // Assert
    verify(bpmnParse).getBpmnModel();
    verify(bpmnParse, atLeast(1)).getCurrentFlowElement();
  }

  /**
   * Test {@link SignalEventDefinitionParseHandler#executeParse(BpmnParse, SignalEventDefinition)}
   * with {@code BpmnParse}, {@code SignalEventDefinition}.
   *
   * <ul>
   *   <li>Then calls {@link BpmnModel#getSignal(String)}.
   * </ul>
   *
   * <p>Method under test: {@link SignalEventDefinitionParseHandler#executeParse(BpmnParse,
   * SignalEventDefinition)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SignalEventDefinitionParseHandler.executeParse(BpmnParse, SignalEventDefinition)"
  })
  public void testExecuteParseWithBpmnParseSignalEventDefinition_thenCallsGetSignal() {
    // Arrange
    SignalEventDefinitionParseHandler signalEventDefinitionParseHandler =
        new SignalEventDefinitionParseHandler();

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getSignal(Mockito.<String>any())).thenReturn(new Signal());
    when(bpmnModel.containsSignalId(Mockito.<String>any())).thenReturn(true);
    doNothing().when(bpmnModel).addSignal(Mockito.<Signal>any());
    bpmnModel.addSignal(null);

    BpmnParse bpmnParse = mock(BpmnParse.class);
    when(bpmnParse.getActivityBehaviorFactory()).thenReturn(new DefaultActivityBehaviorFactory());
    when(bpmnParse.getCurrentFlowElement()).thenReturn(new IntermediateCatchEvent());
    when(bpmnParse.getBpmnModel()).thenReturn(bpmnModel);

    // Act
    signalEventDefinitionParseHandler.executeParse(bpmnParse, new SignalEventDefinition());

    // Assert
    verify(bpmnModel).addSignal(isNull());
    verify(bpmnModel).containsSignalId(null);
    verify(bpmnModel).getSignal(null);
    verify(bpmnParse).getActivityBehaviorFactory();
    verify(bpmnParse, atLeast(1)).getBpmnModel();
    verify(bpmnParse, atLeast(1)).getCurrentFlowElement();
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link SignalEventDefinitionParseHandler}
   *   <li>{@link SignalEventDefinitionParseHandler#getHandledType()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SignalEventDefinitionParseHandler.<init>()",
    "Class SignalEventDefinitionParseHandler.getHandledType()"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends BaseElement> actualHandledType =
        new SignalEventDefinitionParseHandler().getHandledType();

    // Assert
    Class<SignalEventDefinition> expectedHandledType = SignalEventDefinition.class;
    assertEquals(expectedHandledType, actualHandledType);
  }
}
