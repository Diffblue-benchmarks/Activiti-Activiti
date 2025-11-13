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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.Association;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.SubProcess;
import org.activiti.engine.impl.bpmn.behavior.SubProcessActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParseHandlers;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.test.util.TestProcessUtil;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class SubProcessParseHandlerDiffblueTest {
  /**
   * Test {@link SubProcessParseHandler#executeParse(BpmnParse, SubProcess)} with {@code BpmnParse},
   * {@code SubProcess}.
   *
   * <p>Method under test: {@link SubProcessParseHandler#executeParse(BpmnParse, SubProcess)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcessParseHandler.executeParse(BpmnParse, SubProcess)"})
  public void testExecuteParseWithBpmnParseSubProcess() {
    // Arrange
    SubProcessParseHandler subProcessParseHandler = new SubProcessParseHandler();

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setBpmnParserHandlers(new BpmnParseHandlers());
    bpmnParse.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());

    SubProcess subProcess = new SubProcess();
    AdhocSubProcess element = new AdhocSubProcess();
    subProcess.addFlowElement(element);

    // Act
    subProcessParseHandler.executeParse(bpmnParse, subProcess);

    // Assert
    assertSame(element, bpmnParse.getCurrentFlowElement());
  }

  /**
   * Test {@link SubProcessParseHandler#executeParse(BpmnParse, SubProcess)} with {@code BpmnParse},
   * {@code SubProcess}.
   *
   * <p>Method under test: {@link SubProcessParseHandler#executeParse(BpmnParse, SubProcess)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcessParseHandler.executeParse(BpmnParse, SubProcess)"})
  public void testExecuteParseWithBpmnParseSubProcess2() {
    // Arrange
    SubProcessParseHandler subProcessParseHandler = new SubProcessParseHandler();

    BpmnParseHandlers bpmnParserHandlers = new BpmnParseHandlers();
    bpmnParserHandlers.addHandler(new AdhocSubProcessParseHandler());

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setBpmnParserHandlers(bpmnParserHandlers);
    bpmnParse.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());

    SubProcess subProcess = new SubProcess();
    AdhocSubProcess element = new AdhocSubProcess();
    subProcess.addFlowElement(element);

    // Act
    subProcessParseHandler.executeParse(bpmnParse, subProcess);

    // Assert
    assertSame(element, bpmnParse.getCurrentFlowElement());
  }

  /**
   * Test {@link SubProcessParseHandler#executeParse(BpmnParse, SubProcess)} with {@code BpmnParse},
   * {@code SubProcess}.
   *
   * <p>Method under test: {@link SubProcessParseHandler#executeParse(BpmnParse, SubProcess)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcessParseHandler.executeParse(BpmnParse, SubProcess)"})
  public void testExecuteParseWithBpmnParseSubProcess3() {
    // Arrange
    SubProcessParseHandler subProcessParseHandler = new SubProcessParseHandler();

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setBpmnParserHandlers(new BpmnParseHandlers());
    bpmnParse.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());

    SubProcess subProcess = new SubProcess();
    BoundaryEvent element = new BoundaryEvent();
    subProcess.addFlowElement(element);

    // Act
    subProcessParseHandler.executeParse(bpmnParse, subProcess);

    // Assert
    assertSame(element, bpmnParse.getCurrentFlowElement());
  }

  /**
   * Test {@link SubProcessParseHandler#executeParse(BpmnParse, SubProcess)} with {@code BpmnParse},
   * {@code SubProcess}.
   *
   * <ul>
   *   <li>Given {@link BooleanDataObject} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link SubProcessParseHandler#executeParse(BpmnParse, SubProcess)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcessParseHandler.executeParse(BpmnParse, SubProcess)"})
  public void testExecuteParseWithBpmnParseSubProcess_givenBooleanDataObject() {
    // Arrange
    SubProcessParseHandler subProcessParseHandler = new SubProcessParseHandler();

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setBpmnParserHandlers(new BpmnParseHandlers());
    bpmnParse.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());

    SubProcess subProcess = new SubProcess();
    subProcess.addFlowElement(new BooleanDataObject());

    // Act
    subProcessParseHandler.executeParse(bpmnParse, subProcess);

    // Assert
    Object behavior = subProcess.getBehavior();
    assertTrue(behavior instanceof SubProcessActivityBehavior);
    assertNull(bpmnParse.getCurrentFlowElement());
    assertNull(((SubProcessActivityBehavior) behavior).getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link SubProcessParseHandler#executeParse(BpmnParse, SubProcess)} with {@code BpmnParse},
   * {@code SubProcess}.
   *
   * <ul>
   *   <li>Given createOneTaskBpmnModel.
   * </ul>
   *
   * <p>Method under test: {@link SubProcessParseHandler#executeParse(BpmnParse, SubProcess)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcessParseHandler.executeParse(BpmnParse, SubProcess)"})
  public void testExecuteParseWithBpmnParseSubProcess_givenCreateOneTaskBpmnModel() {
    // Arrange
    SubProcessParseHandler subProcessParseHandler = new SubProcessParseHandler();

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setBpmnModel(TestProcessUtil.createOneTaskBpmnModel());
    bpmnParse.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());

    SubProcess subProcess = new SubProcess();
    subProcess.addArtifact(new Association());

    // Act
    subProcessParseHandler.executeParse(bpmnParse, subProcess);

    // Assert
    Object behavior = subProcess.getBehavior();
    assertTrue(behavior instanceof SubProcessActivityBehavior);
    assertNull(bpmnParse.getCurrentFlowElement());
    assertNull(((SubProcessActivityBehavior) behavior).getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link SubProcessParseHandler#executeParse(BpmnParse, SubProcess)} with {@code BpmnParse},
   * {@code SubProcess}.
   *
   * <ul>
   *   <li>Given createOneTaskBpmnModel.
   * </ul>
   *
   * <p>Method under test: {@link SubProcessParseHandler#executeParse(BpmnParse, SubProcess)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcessParseHandler.executeParse(BpmnParse, SubProcess)"})
  public void testExecuteParseWithBpmnParseSubProcess_givenCreateOneTaskBpmnModel2() {
    // Arrange
    SubProcessParseHandler subProcessParseHandler = new SubProcessParseHandler();

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setBpmnModel(TestProcessUtil.createOneTaskBpmnModel());
    bpmnParse.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());

    SubProcess subProcess = new SubProcess();
    subProcess.addArtifact(new Association());
    subProcess.addArtifact(new Association());

    // Act
    subProcessParseHandler.executeParse(bpmnParse, subProcess);

    // Assert
    Object behavior = subProcess.getBehavior();
    assertTrue(behavior instanceof SubProcessActivityBehavior);
    assertNull(bpmnParse.getCurrentFlowElement());
    assertNull(((SubProcessActivityBehavior) behavior).getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link SubProcessParseHandler#executeParse(BpmnParse, SubProcess)} with {@code BpmnParse},
   * {@code SubProcess}.
   *
   * <ul>
   *   <li>When {@link SubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link SubProcessParseHandler#executeParse(BpmnParse, SubProcess)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcessParseHandler.executeParse(BpmnParse, SubProcess)"})
  public void testExecuteParseWithBpmnParseSubProcess_whenSubProcess() {
    // Arrange
    SubProcessParseHandler subProcessParseHandler = new SubProcessParseHandler();

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    SubProcess subProcess = new SubProcess();

    // Act
    subProcessParseHandler.executeParse(bpmnParse, subProcess);

    // Assert
    Object behavior = subProcess.getBehavior();
    assertTrue(behavior instanceof SubProcessActivityBehavior);
    assertNull(bpmnParse.getCurrentFlowElement());
    assertNull(((SubProcessActivityBehavior) behavior).getMultiInstanceActivityBehavior());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link SubProcessParseHandler}
   *   <li>{@link SubProcessParseHandler#getHandledType()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SubProcessParseHandler.<init>()",
    "Class SubProcessParseHandler.getHandledType()"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends BaseElement> actualHandledType = new SubProcessParseHandler().getHandledType();

    // Assert
    Class<SubProcess> expectedHandledType = SubProcess.class;
    assertEquals(expectedHandledType, actualHandledType);
  }
}
