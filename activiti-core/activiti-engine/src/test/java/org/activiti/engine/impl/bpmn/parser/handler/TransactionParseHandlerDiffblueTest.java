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
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.DataObject;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.Transaction;
import org.activiti.engine.impl.bpmn.behavior.TransactionActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParseHandlers;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.test.util.TestProcessUtil;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class TransactionParseHandlerDiffblueTest {
  /**
   * Test {@link TransactionParseHandler#executeParse(BpmnParse, Transaction)} with {@code
   * BpmnParse}, {@code Transaction}.
   *
   * <p>Method under test: {@link TransactionParseHandler#executeParse(BpmnParse, Transaction)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TransactionParseHandler.executeParse(BpmnParse, Transaction)"})
  public void testExecuteParseWithBpmnParseTransaction() {
    // Arrange
    TransactionParseHandler transactionParseHandler = new TransactionParseHandler();

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setBpmnParserHandlers(new BpmnParseHandlers());
    bpmnParse.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());

    Transaction transaction = new Transaction();
    AdhocSubProcess element = new AdhocSubProcess();
    transaction.addFlowElement(element);

    // Act
    transactionParseHandler.executeParse(bpmnParse, transaction);

    // Assert
    assertSame(element, bpmnParse.getCurrentFlowElement());
  }

  /**
   * Test {@link TransactionParseHandler#executeParse(BpmnParse, Transaction)} with {@code
   * BpmnParse}, {@code Transaction}.
   *
   * <p>Method under test: {@link TransactionParseHandler#executeParse(BpmnParse, Transaction)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TransactionParseHandler.executeParse(BpmnParse, Transaction)"})
  public void testExecuteParseWithBpmnParseTransaction2() {
    // Arrange
    TransactionParseHandler transactionParseHandler = new TransactionParseHandler();

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setBpmnParserHandlers(new BpmnParseHandlers());
    bpmnParse.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());

    Transaction transaction = new Transaction();
    SequenceFlow element = new SequenceFlow("Source Ref", "Target Ref");
    transaction.addFlowElement(element);

    // Act
    transactionParseHandler.executeParse(bpmnParse, transaction);

    // Assert
    assertSame(element, bpmnParse.getCurrentFlowElement());
  }

  /**
   * Test {@link TransactionParseHandler#executeParse(BpmnParse, Transaction)} with {@code
   * BpmnParse}, {@code Transaction}.
   *
   * <p>Method under test: {@link TransactionParseHandler#executeParse(BpmnParse, Transaction)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TransactionParseHandler.executeParse(BpmnParse, Transaction)"})
  public void testExecuteParseWithBpmnParseTransaction3() {
    // Arrange
    TransactionParseHandler transactionParseHandler = new TransactionParseHandler();

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setBpmnParserHandlers(new BpmnParseHandlers());
    bpmnParse.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());

    Transaction transaction = new Transaction();
    BoundaryEvent element = new BoundaryEvent();
    transaction.addFlowElement(element);

    // Act
    transactionParseHandler.executeParse(bpmnParse, transaction);

    // Assert
    assertSame(element, bpmnParse.getCurrentFlowElement());
  }

  /**
   * Test {@link TransactionParseHandler#executeParse(BpmnParse, Transaction)} with {@code
   * BpmnParse}, {@code Transaction}.
   *
   * <p>Method under test: {@link TransactionParseHandler#executeParse(BpmnParse, Transaction)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TransactionParseHandler.executeParse(BpmnParse, Transaction)"})
  public void testExecuteParseWithBpmnParseTransaction4() {
    // Arrange
    TransactionParseHandler transactionParseHandler = new TransactionParseHandler();

    BpmnParseHandlers bpmnParserHandlers = new BpmnParseHandlers();
    bpmnParserHandlers.addHandler(new AdhocSubProcessParseHandler());

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setBpmnParserHandlers(bpmnParserHandlers);
    bpmnParse.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());

    Transaction transaction = new Transaction();
    AdhocSubProcess element = new AdhocSubProcess();
    transaction.addFlowElement(element);

    // Act
    transactionParseHandler.executeParse(bpmnParse, transaction);

    // Assert
    assertSame(element, bpmnParse.getCurrentFlowElement());
  }

  /**
   * Test {@link TransactionParseHandler#executeParse(BpmnParse, Transaction)} with {@code
   * BpmnParse}, {@code Transaction}.
   *
   * <ul>
   *   <li>Given createOneTaskBpmnModel.
   * </ul>
   *
   * <p>Method under test: {@link TransactionParseHandler#executeParse(BpmnParse, Transaction)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TransactionParseHandler.executeParse(BpmnParse, Transaction)"})
  public void testExecuteParseWithBpmnParseTransaction_givenCreateOneTaskBpmnModel() {
    // Arrange
    TransactionParseHandler transactionParseHandler = new TransactionParseHandler();

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setBpmnModel(TestProcessUtil.createOneTaskBpmnModel());
    bpmnParse.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());

    Transaction transaction = new Transaction();
    transaction.addArtifact(new Association());

    // Act
    transactionParseHandler.executeParse(bpmnParse, transaction);

    // Assert
    Object behavior = transaction.getBehavior();
    assertTrue(behavior instanceof TransactionActivityBehavior);
    assertNull(bpmnParse.getCurrentFlowElement());
    assertNull(((TransactionActivityBehavior) behavior).getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link TransactionParseHandler#executeParse(BpmnParse, Transaction)} with {@code
   * BpmnParse}, {@code Transaction}.
   *
   * <ul>
   *   <li>Given createOneTaskBpmnModel.
   * </ul>
   *
   * <p>Method under test: {@link TransactionParseHandler#executeParse(BpmnParse, Transaction)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TransactionParseHandler.executeParse(BpmnParse, Transaction)"})
  public void testExecuteParseWithBpmnParseTransaction_givenCreateOneTaskBpmnModel2() {
    // Arrange
    TransactionParseHandler transactionParseHandler = new TransactionParseHandler();

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setBpmnModel(TestProcessUtil.createOneTaskBpmnModel());
    bpmnParse.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());

    Transaction transaction = new Transaction();
    transaction.addArtifact(new Association());
    transaction.addArtifact(new Association());

    // Act
    transactionParseHandler.executeParse(bpmnParse, transaction);

    // Assert
    Object behavior = transaction.getBehavior();
    assertTrue(behavior instanceof TransactionActivityBehavior);
    assertNull(bpmnParse.getCurrentFlowElement());
    assertNull(((TransactionActivityBehavior) behavior).getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link TransactionParseHandler#executeParse(BpmnParse, Transaction)} with {@code
   * BpmnParse}, {@code Transaction}.
   *
   * <ul>
   *   <li>Given {@link DataObject} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link TransactionParseHandler#executeParse(BpmnParse, Transaction)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TransactionParseHandler.executeParse(BpmnParse, Transaction)"})
  public void testExecuteParseWithBpmnParseTransaction_givenDataObject() {
    // Arrange
    TransactionParseHandler transactionParseHandler = new TransactionParseHandler();

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setBpmnParserHandlers(new BpmnParseHandlers());
    bpmnParse.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());

    Transaction transaction = new Transaction();
    transaction.addFlowElement(new DataObject());

    // Act
    transactionParseHandler.executeParse(bpmnParse, transaction);

    // Assert
    Object behavior = transaction.getBehavior();
    assertTrue(behavior instanceof TransactionActivityBehavior);
    assertNull(bpmnParse.getCurrentFlowElement());
    assertNull(((TransactionActivityBehavior) behavior).getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link TransactionParseHandler#executeParse(BpmnParse, Transaction)} with {@code
   * BpmnParse}, {@code Transaction}.
   *
   * <ul>
   *   <li>When {@link Transaction} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link TransactionParseHandler#executeParse(BpmnParse, Transaction)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TransactionParseHandler.executeParse(BpmnParse, Transaction)"})
  public void testExecuteParseWithBpmnParseTransaction_whenTransaction() {
    // Arrange
    TransactionParseHandler transactionParseHandler = new TransactionParseHandler();

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    Transaction transaction = new Transaction();

    // Act
    transactionParseHandler.executeParse(bpmnParse, transaction);

    // Assert
    Object behavior = transaction.getBehavior();
    assertTrue(behavior instanceof TransactionActivityBehavior);
    assertNull(bpmnParse.getCurrentFlowElement());
    assertNull(((TransactionActivityBehavior) behavior).getMultiInstanceActivityBehavior());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link TransactionParseHandler}
   *   <li>{@link TransactionParseHandler#getHandledType()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void TransactionParseHandler.<init>()",
    "Class TransactionParseHandler.getHandledType()"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends BaseElement> actualHandledType = new TransactionParseHandler().getHandledType();

    // Assert
    Class<Transaction> expectedHandledType = Transaction.class;
    assertEquals(expectedHandledType, actualHandledType);
  }
}
