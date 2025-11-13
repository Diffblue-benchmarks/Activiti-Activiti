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
package org.activiti.engine.impl.bpmn.behavior;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.Activity;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(MockitoJUnitRunner.class)
public class MultiInstanceActivityBehaviorDiffblueTest {
  @Mock private AbstractBpmnActivityBehavior abstractBpmnActivityBehavior;

  @Mock private Activity activity;

  /**
   * Test {@link MultiInstanceActivityBehavior#execute(DelegateExecution)}.
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MultiInstanceActivityBehavior.execute(DelegateExecution)"})
  public void testExecute() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            parallelMultiInstanceBehavior.execute(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#execute(DelegateExecution)}.
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MultiInstanceActivityBehavior.execute(DelegateExecution)"})
  public void testExecute2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopDataOutputRef("");

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setParentId("Execution");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> parallelMultiInstanceBehavior.execute(execution));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#execute(DelegateExecution)}.
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MultiInstanceActivityBehavior.execute(DelegateExecution)"})
  public void testExecute3() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopCardinalityExpression(new FixedValue(JSONObject.NULL));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            parallelMultiInstanceBehavior.execute(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#execute(DelegateExecution)}.
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MultiInstanceActivityBehavior.execute(DelegateExecution)"})
  public void testExecute4() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setCollectionExpression(new FixedValue(JSONObject.NULL));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            parallelMultiInstanceBehavior.execute(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#execute(DelegateExecution)}.
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MultiInstanceActivityBehavior.execute(DelegateExecution)"})
  public void testExecute5() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setCollectionVariable(
        "Couldn't resolve collection expression nor variable reference");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            parallelMultiInstanceBehavior.execute(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#trigger(DelegateExecution, String, Object)}.
   *
   * <ul>
   *   <li>Then calls {@link
   *       AbstractBpmnActivityBehavior#setMultiInstanceActivityBehavior(MultiInstanceActivityBehavior)}.
   * </ul>
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#trigger(DelegateExecution, String,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MultiInstanceActivityBehavior.trigger(DelegateExecution, String, Object)"
  })
  public void testTrigger_thenCallsSetMultiInstanceActivityBehavior() {
    // Arrange
    AbstractBpmnActivityBehavior originalActivityBehavior =
        mock(AbstractBpmnActivityBehavior.class);
    doNothing()
        .when(originalActivityBehavior)
        .setMultiInstanceActivityBehavior(Mockito.<MultiInstanceActivityBehavior>any());
    doNothing()
        .when(originalActivityBehavior)
        .trigger(Mockito.<DelegateExecution>any(), Mockito.<String>any(), Mockito.<Object>any());
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(new AdhocSubProcess(), originalActivityBehavior);

    // Act
    parallelMultiInstanceBehavior.trigger(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections(),
        "Signal Name",
        JSONObject.NULL);

    // Assert
    verify(originalActivityBehavior)
        .setMultiInstanceActivityBehavior(isA(MultiInstanceActivityBehavior.class));
    verify(originalActivityBehavior)
        .trigger(isA(DelegateExecution.class), eq("Signal Name"), isA(Object.class));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#trigger(DelegateExecution, String, Object)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#trigger(DelegateExecution, String,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MultiInstanceActivityBehavior.trigger(DelegateExecution, String, Object)"
  })
  public void testTrigger_thenThrowActivitiException() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            parallelMultiInstanceBehavior.trigger(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections(),
                "Signal Name",
                JSONObject.NULL));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#lastExecutionEnded(DelegateExecution)}.
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#lastExecutionEnded(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MultiInstanceActivityBehavior.lastExecutionEnded(DelegateExecution)"})
  public void testLastExecutionEnded() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            parallelMultiInstanceBehavior.lastExecutionEnded(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#lastExecutionEnded(DelegateExecution)}.
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#lastExecutionEnded(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MultiInstanceActivityBehavior.lastExecutionEnded(DelegateExecution)"})
  public void testLastExecutionEnded2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopCardinalityExpression(new FixedValue(JSONObject.NULL));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            parallelMultiInstanceBehavior.lastExecutionEnded(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#lastExecutionEnded(DelegateExecution)}.
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#lastExecutionEnded(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MultiInstanceActivityBehavior.lastExecutionEnded(DelegateExecution)"})
  public void testLastExecutionEnded3() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setCollectionExpression(new FixedValue(JSONObject.NULL));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            parallelMultiInstanceBehavior.lastExecutionEnded(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#lastExecutionEnded(DelegateExecution)}.
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#lastExecutionEnded(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MultiInstanceActivityBehavior.lastExecutionEnded(DelegateExecution)"})
  public void testLastExecutionEnded4() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setCollectionVariable(
        "Couldn't resolve collection expression nor variable reference");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            parallelMultiInstanceBehavior.lastExecutionEnded(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#completed(DelegateExecution)}.
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#completed(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MultiInstanceActivityBehavior.completed(DelegateExecution)"})
  public void testCompleted() throws Exception {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            parallelMultiInstanceBehavior.completed(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#completed(DelegateExecution)}.
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#completed(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MultiInstanceActivityBehavior.completed(DelegateExecution)"})
  public void testCompleted2() throws Exception {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopCardinalityExpression(new FixedValue(JSONObject.NULL));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            parallelMultiInstanceBehavior.completed(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#completed(DelegateExecution)}.
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#completed(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MultiInstanceActivityBehavior.completed(DelegateExecution)"})
  public void testCompleted3() throws Exception {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setCollectionExpression(new FixedValue(JSONObject.NULL));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            parallelMultiInstanceBehavior.completed(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#completed(DelegateExecution)}.
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#completed(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MultiInstanceActivityBehavior.completed(DelegateExecution)"})
  public void testCompleted4() throws Exception {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setCollectionVariable(
        "Couldn't resolve collection expression nor variable reference");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            parallelMultiInstanceBehavior.completed(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#resolveNrOfInstances(DelegateExecution)}.
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#resolveNrOfInstances(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int MultiInstanceActivityBehavior.resolveNrOfInstances(DelegateExecution)"})
  public void testResolveNrOfInstances() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            parallelMultiInstanceBehavior.resolveNrOfInstances(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#resolveNrOfInstances(DelegateExecution)}.
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#resolveNrOfInstances(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int MultiInstanceActivityBehavior.resolveNrOfInstances(DelegateExecution)"})
  public void testResolveNrOfInstances2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopCardinalityExpression(new FixedValue(JSONObject.NULL));
    parallelMultiInstanceBehavior.setCollectionExpression(new FixedValue(JSONObject.NULL));
    parallelMultiInstanceBehavior.setCollectionVariable("foo");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            parallelMultiInstanceBehavior.resolveNrOfInstances(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#resolveNrOfInstances(DelegateExecution)}.
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#resolveNrOfInstances(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int MultiInstanceActivityBehavior.resolveNrOfInstances(DelegateExecution)"})
  public void testResolveNrOfInstances3() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopCardinalityExpression(null);
    parallelMultiInstanceBehavior.setCollectionExpression(null);
    parallelMultiInstanceBehavior.setCollectionVariable("foo");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            parallelMultiInstanceBehavior.resolveNrOfInstances(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#resolveNrOfInstances(DelegateExecution)}.
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#resolveNrOfInstances(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int MultiInstanceActivityBehavior.resolveNrOfInstances(DelegateExecution)"})
  public void testResolveNrOfInstances4() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopCardinalityExpression(new FixedValue(42));
    parallelMultiInstanceBehavior.setCollectionExpression(new FixedValue(JSONObject.NULL));
    parallelMultiInstanceBehavior.setCollectionVariable("foo");

    // Act and Assert
    assertEquals(
        42,
        parallelMultiInstanceBehavior.resolveNrOfInstances(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#resolveNrOfInstances(DelegateExecution)}.
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#resolveNrOfInstances(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int MultiInstanceActivityBehavior.resolveNrOfInstances(DelegateExecution)"})
  public void testResolveNrOfInstances5() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopCardinalityExpression(new FixedValue("42"));
    parallelMultiInstanceBehavior.setCollectionExpression(new FixedValue(JSONObject.NULL));
    parallelMultiInstanceBehavior.setCollectionVariable("foo");

    // Act and Assert
    assertEquals(
        42,
        parallelMultiInstanceBehavior.resolveNrOfInstances(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#resolveNrOfInstances(DelegateExecution)}.
   *
   * <ul>
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#resolveNrOfInstances(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int MultiInstanceActivityBehavior.resolveNrOfInstances(DelegateExecution)"})
  public void testResolveNrOfInstances_thenReturnZero() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopCardinalityExpression(null);
    parallelMultiInstanceBehavior.setCollectionExpression(new FixedValue(new ArrayList<>()));
    parallelMultiInstanceBehavior.setCollectionVariable("foo");

    // Act and Assert
    assertEquals(
        0,
        parallelMultiInstanceBehavior.resolveNrOfInstances(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#resolveNrOfInstances(DelegateExecution)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#resolveNrOfInstances(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int MultiInstanceActivityBehavior.resolveNrOfInstances(DelegateExecution)"})
  public void testResolveNrOfInstances_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopCardinalityExpression(null);
    parallelMultiInstanceBehavior.setCollectionExpression(new FixedValue(JSONObject.NULL));
    parallelMultiInstanceBehavior.setCollectionVariable("foo");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            parallelMultiInstanceBehavior.resolveNrOfInstances(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#resolveAndValidateCollection(DelegateExecution)}.
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#resolveAndValidateCollection(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Collection MultiInstanceActivityBehavior.resolveAndValidateCollection(DelegateExecution)"
  })
  public void testResolveAndValidateCollection() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            parallelMultiInstanceBehavior.resolveAndValidateCollection(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#resolveAndValidateCollection(DelegateExecution)}.
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#resolveAndValidateCollection(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Collection MultiInstanceActivityBehavior.resolveAndValidateCollection(DelegateExecution)"
  })
  public void testResolveAndValidateCollection2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setCollectionExpression(new FixedValue(JSONObject.NULL));
    parallelMultiInstanceBehavior.setCollectionVariable("foo");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            parallelMultiInstanceBehavior.resolveAndValidateCollection(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#resolveAndValidateCollection(DelegateExecution)}.
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#resolveAndValidateCollection(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Collection MultiInstanceActivityBehavior.resolveAndValidateCollection(DelegateExecution)"
  })
  public void testResolveAndValidateCollection3() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setCollectionExpression(null);
    parallelMultiInstanceBehavior.setCollectionVariable("foo");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            parallelMultiInstanceBehavior.resolveAndValidateCollection(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#resolveAndValidateCollection(DelegateExecution)}.
   *
   * <ul>
   *   <li>Then return {@link List}.
   * </ul>
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#resolveAndValidateCollection(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Collection MultiInstanceActivityBehavior.resolveAndValidateCollection(DelegateExecution)"
  })
  public void testResolveAndValidateCollection_thenReturnList() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    ArrayList<Object> objectList = new ArrayList<>();
    parallelMultiInstanceBehavior.setCollectionExpression(new FixedValue(objectList));
    parallelMultiInstanceBehavior.setCollectionVariable("foo");

    // Act
    Collection actualResolveAndValidateCollectionResult =
        parallelMultiInstanceBehavior.resolveAndValidateCollection(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    assertTrue(actualResolveAndValidateCollectionResult instanceof List);
    assertTrue(actualResolveAndValidateCollectionResult.isEmpty());
    assertSame(objectList, actualResolveAndValidateCollectionResult);
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#resolveCollection(DelegateExecution)}.
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#resolveCollection(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object MultiInstanceActivityBehavior.resolveCollection(DelegateExecution)"})
  public void testResolveCollection() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertNull(
        parallelMultiInstanceBehavior.resolveCollection(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#resolveCollection(DelegateExecution)}.
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#resolveCollection(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object MultiInstanceActivityBehavior.resolveCollection(DelegateExecution)"})
  public void testResolveCollection2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setCollectionExpression(null);
    parallelMultiInstanceBehavior.setCollectionVariable("foo");

    // Act and Assert
    assertNull(
        parallelMultiInstanceBehavior.resolveCollection(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#usesCollection()}.
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#usesCollection()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MultiInstanceActivityBehavior.usesCollection()"})
  public void testUsesCollection() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setCollectionExpression(new FixedValue(JSONObject.NULL));
    parallelMultiInstanceBehavior.setCollectionVariable("foo");

    // Act and Assert
    assertTrue(parallelMultiInstanceBehavior.usesCollection());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#usesCollection()}.
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#usesCollection()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MultiInstanceActivityBehavior.usesCollection()"})
  public void testUsesCollection2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setCollectionExpression(null);
    parallelMultiInstanceBehavior.setCollectionVariable("foo");

    // Act and Assert
    assertTrue(parallelMultiInstanceBehavior.usesCollection());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#usesCollection()}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#usesCollection()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MultiInstanceActivityBehavior.usesCollection()"})
  public void testUsesCollection_thenReturnFalse() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertFalse(parallelMultiInstanceBehavior.usesCollection());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#isExtraScopeNeeded(FlowNode)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#isExtraScopeNeeded(FlowNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MultiInstanceActivityBehavior.isExtraScopeNeeded(FlowNode)"})
  public void testIsExtraScopeNeeded_givenAdhocSubProcess_thenReturnTrue() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    AdhocSubProcess flowNode = new AdhocSubProcess();
    flowNode.setParentContainer(new AdhocSubProcess());

    // Act and Assert
    assertTrue(parallelMultiInstanceBehavior.isExtraScopeNeeded(flowNode));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#isExtraScopeNeeded(FlowNode)}.
   *
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#isExtraScopeNeeded(FlowNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MultiInstanceActivityBehavior.isExtraScopeNeeded(FlowNode)"})
  public void testIsExtraScopeNeeded_whenAdhocSubProcess_thenReturnFalse() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertFalse(parallelMultiInstanceBehavior.isExtraScopeNeeded(new AdhocSubProcess()));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#resolveLoopCardinality(DelegateExecution)}.
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#resolveLoopCardinality(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int MultiInstanceActivityBehavior.resolveLoopCardinality(DelegateExecution)"})
  public void testResolveLoopCardinality() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopCardinalityExpression(new FixedValue(42));

    // Act and Assert
    assertEquals(
        42,
        parallelMultiInstanceBehavior.resolveLoopCardinality(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#resolveLoopCardinality(DelegateExecution)}.
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#resolveLoopCardinality(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int MultiInstanceActivityBehavior.resolveLoopCardinality(DelegateExecution)"})
  public void testResolveLoopCardinality2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopCardinalityExpression(new FixedValue("42"));

    // Act and Assert
    assertEquals(
        42,
        parallelMultiInstanceBehavior.resolveLoopCardinality(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#resolveLoopCardinality(DelegateExecution)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#resolveLoopCardinality(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int MultiInstanceActivityBehavior.resolveLoopCardinality(DelegateExecution)"})
  public void testResolveLoopCardinality_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopCardinalityExpression(new FixedValue(JSONObject.NULL));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            parallelMultiInstanceBehavior.resolveLoopCardinality(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#completionConditionSatisfied(DelegateExecution)}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#completionConditionSatisfied(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MultiInstanceActivityBehavior.completionConditionSatisfied(DelegateExecution)"
  })
  public void testCompletionConditionSatisfied_thenReturnFalse() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertFalse(
        parallelMultiInstanceBehavior.completionConditionSatisfied(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#completionConditionSatisfied(DelegateExecution)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#completionConditionSatisfied(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MultiInstanceActivityBehavior.completionConditionSatisfied(DelegateExecution)"
  })
  public void testCompletionConditionSatisfied_thenReturnTrue() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setCompletionConditionExpression(new FixedValue(true));

    // Act and Assert
    assertTrue(
        parallelMultiInstanceBehavior.completionConditionSatisfied(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#completionConditionSatisfied(DelegateExecution)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#completionConditionSatisfied(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MultiInstanceActivityBehavior.completionConditionSatisfied(DelegateExecution)"
  })
  public void testCompletionConditionSatisfied_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setCompletionConditionExpression(new FixedValue(JSONObject.NULL));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            parallelMultiInstanceBehavior.completionConditionSatisfied(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#setLoopVariable(DelegateExecution, String, Object)}.
   *
   * <ul>
   *   <li>Then calls {@link DelegateExecution#setVariableLocal(String, Object)}.
   * </ul>
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#setLoopVariable(DelegateExecution,
   * String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MultiInstanceActivityBehavior.setLoopVariable(DelegateExecution, String, Object)"
  })
  public void testSetLoopVariable_thenCallsSetVariableLocal() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.setVariableLocal(Mockito.<String>any(), Mockito.<Object>any()))
        .thenReturn(JSONObject.NULL);

    // Act
    parallelMultiInstanceBehavior.setLoopVariable(execution, "Variable Name", JSONObject.NULL);

    // Assert
    verify(execution).setVariableLocal(eq("Variable Name"), isA(Object.class));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#getLoopVariable(DelegateExecution, String)}.
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#getLoopVariable(DelegateExecution,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Integer MultiInstanceActivityBehavior.getLoopVariable(DelegateExecution, String)"
  })
  public void testGetLoopVariable() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertEquals(
        0,
        parallelMultiInstanceBehavior
            .getLoopVariable(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Variable Name")
            .intValue());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#getLoopVariable(DelegateExecution, String)}.
   *
   * <ul>
   *   <li>Given one.
   *   <li>Then return intValue is one.
   * </ul>
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#getLoopVariable(DelegateExecution,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Integer MultiInstanceActivityBehavior.getLoopVariable(DelegateExecution, String)"
  })
  public void testGetLoopVariable_givenOne_thenReturnIntValueIsOne() {
    // Arrange
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(null, new AbstractBpmnActivityBehavior());

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getVariableLocal(Mockito.<String>any())).thenReturn(1);
    when(execution.getParent())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    doNothing().when(execution).addChildExecution(Mockito.<ExecutionEntity>any());
    doNothing()
        .when(execution)
        .setTransientVariableLocal(Mockito.<String>any(), Mockito.<Object>any());
    execution.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    execution.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act
    Integer actualLoopVariable =
        parallelMultiInstanceBehavior.getLoopVariable(execution, "Variable Name");

    // Assert
    verify(execution).addChildExecution(isA(ExecutionEntity.class));
    verify(execution).getParent();
    verify(execution).getVariableLocal("Variable Name");
    verify(execution).setTransientVariableLocal(eq("Variable Name"), isA(Object.class));
    assertEquals(1, actualLoopVariable.intValue());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#getLoopVariable(DelegateExecution, String)}.
   *
   * <ul>
   *   <li>Then throw {@link BpmnError}.
   * </ul>
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#getLoopVariable(DelegateExecution,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Integer MultiInstanceActivityBehavior.getLoopVariable(DelegateExecution, String)"
  })
  public void testGetLoopVariable_thenThrowBpmnError() {
    // Arrange
    doNothing()
        .when(abstractBpmnActivityBehavior)
        .setMultiInstanceActivityBehavior(Mockito.<MultiInstanceActivityBehavior>any());

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, abstractBpmnActivityBehavior);

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getVariableLocal(Mockito.<String>any()))
        .thenThrow(new BpmnError("An error occurred"));
    doNothing().when(executionEntityImpl).addChildExecution(Mockito.<ExecutionEntity>any());
    executionEntityImpl.addChildExecution(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getVariableLocal(Mockito.<String>any())).thenReturn(null);
    when(execution.getParent()).thenReturn(executionEntityImpl);

    // Act and Assert
    assertThrows(
        BpmnError.class,
        () -> parallelMultiInstanceBehavior.getLoopVariable(execution, "Variable Name"));
    verify(execution).getParent();
    verify(execution).getVariableLocal("Variable Name");
    verify(abstractBpmnActivityBehavior)
        .setMultiInstanceActivityBehavior(isA(MultiInstanceActivityBehavior.class));
    verify(executionEntityImpl).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntityImpl).getVariableLocal("Variable Name");
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#getLoopVariable(DelegateExecution, String)}.
   *
   * <ul>
   *   <li>When {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#getVariableLocal(String)}
   *       return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#getLoopVariable(DelegateExecution,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Integer MultiInstanceActivityBehavior.getLoopVariable(DelegateExecution, String)"
  })
  public void testGetLoopVariable_whenExecutionEntityImplGetVariableLocalReturnNull() {
    // Arrange
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(null, new AbstractBpmnActivityBehavior());

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getVariableLocal(Mockito.<String>any())).thenReturn(null);
    when(execution.getParent())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    doNothing().when(execution).addChildExecution(Mockito.<ExecutionEntity>any());
    doNothing()
        .when(execution)
        .setTransientVariableLocal(Mockito.<String>any(), Mockito.<Object>any());
    execution.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    execution.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act
    Integer actualLoopVariable =
        parallelMultiInstanceBehavior.getLoopVariable(execution, "Variable Name");

    // Assert
    verify(execution).addChildExecution(isA(ExecutionEntity.class));
    verify(execution).getParent();
    verify(execution).getVariableLocal("Variable Name");
    verify(execution).setTransientVariableLocal(eq("Variable Name"), isA(Object.class));
    assertEquals(0, actualLoopVariable.intValue());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#getLocalLoopVariable(DelegateExecution, String)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#getLocalLoopVariable(DelegateExecution, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Integer MultiInstanceActivityBehavior.getLocalLoopVariable(DelegateExecution, String)"
  })
  public void testGetLocalLoopVariable_thenReturnNull() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertNull(
        parallelMultiInstanceBehavior.getLocalLoopVariable(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Variable Name"));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#removeLocalLoopVariable(DelegateExecution, String)}.
   *
   * <ul>
   *   <li>Then calls {@link
   *       AbstractBpmnActivityBehavior#setMultiInstanceActivityBehavior(MultiInstanceActivityBehavior)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#removeLocalLoopVariable(DelegateExecution, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MultiInstanceActivityBehavior.removeLocalLoopVariable(DelegateExecution, String)"
  })
  public void testRemoveLocalLoopVariable_thenCallsSetMultiInstanceActivityBehavior() {
    // Arrange
    doNothing()
        .when(abstractBpmnActivityBehavior)
        .setMultiInstanceActivityBehavior(Mockito.<MultiInstanceActivityBehavior>any());

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, abstractBpmnActivityBehavior);

    // Act
    parallelMultiInstanceBehavior.removeLocalLoopVariable(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Variable Name");

    // Assert
    verify(abstractBpmnActivityBehavior)
        .setMultiInstanceActivityBehavior(isA(MultiInstanceActivityBehavior.class));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#logLoopDetails(DelegateExecution, String, int, int,
   * int, int)}.
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#logLoopDetails(DelegateExecution,
   * String, int, int, int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MultiInstanceActivityBehavior.logLoopDetails(DelegateExecution, String, int, int, int, int)"
  })
  public void testLogLoopDetails() {
    // Arrange
    doNothing()
        .when(abstractBpmnActivityBehavior)
        .setMultiInstanceActivityBehavior(Mockito.<MultiInstanceActivityBehavior>any());

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, abstractBpmnActivityBehavior);

    // Act
    parallelMultiInstanceBehavior.logLoopDetails(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Custom", 3, 1, 1, 1);

    // Assert
    verify(abstractBpmnActivityBehavior)
        .setMultiInstanceActivityBehavior(isA(MultiInstanceActivityBehavior.class));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#getMultiInstanceRootExecution(DelegateExecution)}.
   *
   * <ul>
   *   <li>When createWithEmptyRelationshipCollections.
   * </ul>
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#getMultiInstanceRootExecution(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DelegateExecution MultiInstanceActivityBehavior.getMultiInstanceRootExecution(DelegateExecution)"
  })
  public void testGetMultiInstanceRootExecution_whenCreateWithEmptyRelationshipCollections() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertNull(
        parallelMultiInstanceBehavior.getMultiInstanceRootExecution(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#getMultiInstanceRootExecution(DelegateExecution)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#getMultiInstanceRootExecution(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DelegateExecution MultiInstanceActivityBehavior.getMultiInstanceRootExecution(DelegateExecution)"
  })
  public void testGetMultiInstanceRootExecution_whenNull_thenReturnNull() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertNull(parallelMultiInstanceBehavior.getMultiInstanceRootExecution(null));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#getLoopCardinalityExpression()}.
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#getLoopCardinalityExpression()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Expression MultiInstanceActivityBehavior.getLoopCardinalityExpression()"})
  public void testGetLoopCardinalityExpression() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertNull(parallelMultiInstanceBehavior.getLoopCardinalityExpression());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#setLoopCardinalityExpression(Expression)}.
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#setLoopCardinalityExpression(Expression)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MultiInstanceActivityBehavior.setLoopCardinalityExpression(Expression)"})
  public void testSetLoopCardinalityExpression() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    FixedValue loopCardinalityExpression = new FixedValue(JSONObject.NULL);

    // Act
    parallelMultiInstanceBehavior.setLoopCardinalityExpression(loopCardinalityExpression);

    // Assert
    assertSame(
        loopCardinalityExpression, parallelMultiInstanceBehavior.getLoopCardinalityExpression());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#getCompletionConditionExpression()}.
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#getCompletionConditionExpression()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Expression MultiInstanceActivityBehavior.getCompletionConditionExpression()"})
  public void testGetCompletionConditionExpression() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertNull(parallelMultiInstanceBehavior.getCompletionConditionExpression());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#setCompletionConditionExpression(Expression)}.
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#setCompletionConditionExpression(Expression)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MultiInstanceActivityBehavior.setCompletionConditionExpression(Expression)"
  })
  public void testSetCompletionConditionExpression() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    FixedValue completionConditionExpression = new FixedValue(JSONObject.NULL);

    // Act
    parallelMultiInstanceBehavior.setCompletionConditionExpression(completionConditionExpression);

    // Assert
    assertSame(
        completionConditionExpression,
        parallelMultiInstanceBehavior.getCompletionConditionExpression());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#getCollectionExpression()}.
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#getCollectionExpression()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Expression MultiInstanceActivityBehavior.getCollectionExpression()"})
  public void testGetCollectionExpression() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertNull(parallelMultiInstanceBehavior.getCollectionExpression());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#setCollectionExpression(Expression)}.
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#setCollectionExpression(Expression)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MultiInstanceActivityBehavior.setCollectionExpression(Expression)"})
  public void testSetCollectionExpression() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    FixedValue collectionExpression = new FixedValue(JSONObject.NULL);

    // Act
    parallelMultiInstanceBehavior.setCollectionExpression(collectionExpression);

    // Assert
    assertSame(collectionExpression, parallelMultiInstanceBehavior.getCollectionExpression());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#getCollectionVariable()}.
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#getCollectionVariable()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String MultiInstanceActivityBehavior.getCollectionVariable()"})
  public void testGetCollectionVariable() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertNull(parallelMultiInstanceBehavior.getCollectionVariable());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#setCollectionVariable(String)}.
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#setCollectionVariable(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MultiInstanceActivityBehavior.setCollectionVariable(String)"})
  public void testSetCollectionVariable() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act
    parallelMultiInstanceBehavior.setCollectionVariable("Collection Variable");

    // Assert
    assertEquals("Collection Variable", parallelMultiInstanceBehavior.getCollectionVariable());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#getCollectionElementVariable()}.
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#getCollectionElementVariable()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String MultiInstanceActivityBehavior.getCollectionElementVariable()"})
  public void testGetCollectionElementVariable() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertNull(parallelMultiInstanceBehavior.getCollectionElementVariable());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#setCollectionElementVariable(String)}.
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#setCollectionElementVariable(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MultiInstanceActivityBehavior.setCollectionElementVariable(String)"})
  public void testSetCollectionElementVariable() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act
    parallelMultiInstanceBehavior.setCollectionElementVariable("Collection Element Variable");

    // Assert
    assertEquals(
        "Collection Element Variable",
        parallelMultiInstanceBehavior.getCollectionElementVariable());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#getCollectionElementIndexVariable()}.
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#getCollectionElementIndexVariable()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String MultiInstanceActivityBehavior.getCollectionElementIndexVariable()"})
  public void testGetCollectionElementIndexVariable() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertEquals("loopCounter", parallelMultiInstanceBehavior.getCollectionElementIndexVariable());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#setCollectionElementIndexVariable(String)}.
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#setCollectionElementIndexVariable(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MultiInstanceActivityBehavior.setCollectionElementIndexVariable(String)"
  })
  public void testSetCollectionElementIndexVariable() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act
    parallelMultiInstanceBehavior.setCollectionElementIndexVariable(
        "Collection Element Index Variable");

    // Assert
    assertEquals(
        "Collection Element Index Variable",
        parallelMultiInstanceBehavior.getCollectionElementIndexVariable());
  }

  /**
   * Test {@link
   * MultiInstanceActivityBehavior#setInnerActivityBehavior(AbstractBpmnActivityBehavior)}.
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#setInnerActivityBehavior(AbstractBpmnActivityBehavior)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MultiInstanceActivityBehavior.setInnerActivityBehavior(AbstractBpmnActivityBehavior)"
  })
  public void testSetInnerActivityBehavior() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    AbstractBpmnActivityBehavior innerActivityBehavior = new AbstractBpmnActivityBehavior();

    // Act
    parallelMultiInstanceBehavior.setInnerActivityBehavior(innerActivityBehavior);

    // Assert
    assertTrue(innerActivityBehavior.hasLoopCharacteristics());
    assertTrue(innerActivityBehavior.hasMultiInstanceCharacteristics());
    assertSame(
        parallelMultiInstanceBehavior, innerActivityBehavior.getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#getInnerActivityBehavior()}.
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#getInnerActivityBehavior()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "AbstractBpmnActivityBehavior MultiInstanceActivityBehavior.getInnerActivityBehavior()"
  })
  public void testGetInnerActivityBehavior() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act
    AbstractBpmnActivityBehavior actualInnerActivityBehavior =
        parallelMultiInstanceBehavior.getInnerActivityBehavior();

    // Assert
    assertSame(parallelMultiInstanceBehavior.innerActivityBehavior, actualInnerActivityBehavior);
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#getLoopDataOutputRef()}.
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#getLoopDataOutputRef()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String MultiInstanceActivityBehavior.getLoopDataOutputRef()"})
  public void testGetLoopDataOutputRef() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertNull(parallelMultiInstanceBehavior.getLoopDataOutputRef());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#hasLoopDataOutputRef()}.
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#hasLoopDataOutputRef()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MultiInstanceActivityBehavior.hasLoopDataOutputRef()"})
  public void testHasLoopDataOutputRef() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertFalse(parallelMultiInstanceBehavior.hasLoopDataOutputRef());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#hasLoopDataOutputRef()}.
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#hasLoopDataOutputRef()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MultiInstanceActivityBehavior.hasLoopDataOutputRef()"})
  public void testHasLoopDataOutputRef2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopDataOutputRef("");

    // Act and Assert
    assertFalse(parallelMultiInstanceBehavior.hasLoopDataOutputRef());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#hasLoopDataOutputRef()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#hasLoopDataOutputRef()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MultiInstanceActivityBehavior.hasLoopDataOutputRef()"})
  public void testHasLoopDataOutputRef_thenReturnTrue() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopDataOutputRef("Loop Data Output Ref");

    // Act and Assert
    assertTrue(parallelMultiInstanceBehavior.hasLoopDataOutputRef());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#setLoopDataOutputRef(String)}.
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#setLoopDataOutputRef(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MultiInstanceActivityBehavior.setLoopDataOutputRef(String)"})
  public void testSetLoopDataOutputRef() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act
    parallelMultiInstanceBehavior.setLoopDataOutputRef("Loop Data Output Ref");

    // Assert
    assertEquals("Loop Data Output Ref", parallelMultiInstanceBehavior.getLoopDataOutputRef());
    assertTrue(parallelMultiInstanceBehavior.hasLoopDataOutputRef());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#getOutputDataItem()}.
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#getOutputDataItem()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String MultiInstanceActivityBehavior.getOutputDataItem()"})
  public void testGetOutputDataItem() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertNull(parallelMultiInstanceBehavior.getOutputDataItem());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#hasOutputDataItem()}.
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#hasOutputDataItem()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MultiInstanceActivityBehavior.hasOutputDataItem()"})
  public void testHasOutputDataItem() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertFalse(parallelMultiInstanceBehavior.hasOutputDataItem());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#hasOutputDataItem()}.
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#hasOutputDataItem()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MultiInstanceActivityBehavior.hasOutputDataItem()"})
  public void testHasOutputDataItem2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setOutputDataItem("");

    // Act and Assert
    assertFalse(parallelMultiInstanceBehavior.hasOutputDataItem());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#hasOutputDataItem()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#hasOutputDataItem()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MultiInstanceActivityBehavior.hasOutputDataItem()"})
  public void testHasOutputDataItem_thenReturnTrue() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setOutputDataItem("Output Data Item");

    // Act and Assert
    assertTrue(parallelMultiInstanceBehavior.hasOutputDataItem());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#setOutputDataItem(String)}.
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#setOutputDataItem(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MultiInstanceActivityBehavior.setOutputDataItem(String)"})
  public void testSetOutputDataItem() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act
    parallelMultiInstanceBehavior.setOutputDataItem("Output Data Item");

    // Assert
    assertEquals("Output Data Item", parallelMultiInstanceBehavior.getOutputDataItem());
    assertTrue(parallelMultiInstanceBehavior.hasOutputDataItem());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#updateResultCollection(DelegateExecution,
   * DelegateExecution)}.
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#updateResultCollection(DelegateExecution, DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MultiInstanceActivityBehavior.updateResultCollection(DelegateExecution, DelegateExecution)"
  })
  public void testUpdateResultCollection() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    ExecutionEntityImpl childExecution =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    parallelMultiInstanceBehavior.updateResultCollection(
        childExecution, ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert that nothing has changed
    Activity activity2 = parallelMultiInstanceBehavior.activity;
    Collection<Artifact> artifacts = ((AdhocSubProcess) activity2).getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = ((AdhocSubProcess) activity2).getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(activity2 instanceof AdhocSubProcess);
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#updateResultCollection(DelegateExecution,
   * DelegateExecution)}.
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#updateResultCollection(DelegateExecution, DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MultiInstanceActivityBehavior.updateResultCollection(DelegateExecution, DelegateExecution)"
  })
  public void testUpdateResultCollection2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopDataOutputRef("");
    parallelMultiInstanceBehavior.setOutputDataItem("");
    ExecutionEntityImpl childExecution =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    parallelMultiInstanceBehavior.updateResultCollection(
        childExecution, ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert that nothing has changed
    Activity activity2 = parallelMultiInstanceBehavior.activity;
    Collection<Artifact> artifacts = ((AdhocSubProcess) activity2).getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = ((AdhocSubProcess) activity2).getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(activity2 instanceof AdhocSubProcess);
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#updateResultCollection(DelegateExecution,
   * DelegateExecution)}.
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#updateResultCollection(DelegateExecution, DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MultiInstanceActivityBehavior.updateResultCollection(DelegateExecution, DelegateExecution)"
  })
  public void testUpdateResultCollection3() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopDataOutputRef("");
    parallelMultiInstanceBehavior.setOutputDataItem("");

    // Act
    parallelMultiInstanceBehavior.updateResultCollection(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections(), null);

    // Assert that nothing has changed
    Activity activity2 = parallelMultiInstanceBehavior.activity;
    Collection<Artifact> artifacts = ((AdhocSubProcess) activity2).getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = ((AdhocSubProcess) activity2).getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(activity2 instanceof AdhocSubProcess);
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#updateResultCollection(DelegateExecution,
   * DelegateExecution)}.
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#updateResultCollection(DelegateExecution, DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MultiInstanceActivityBehavior.updateResultCollection(DelegateExecution, DelegateExecution)"
  })
  public void testUpdateResultCollection4() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setOutputDataItem("nrOfInstances");
    parallelMultiInstanceBehavior.setLoopDataOutputRef("Loop Data Output Ref");
    ExecutionEntityImpl childExecution =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    DelegateExecution miRootExecution = mock(DelegateExecution.class);
    when(miRootExecution.getVariableLocal(Mockito.<String>any())).thenReturn(JSONObject.NULL);
    when(miRootExecution.setVariableLocal(Mockito.<String>any(), Mockito.<Object>any()))
        .thenReturn(JSONObject.NULL);

    // Act
    parallelMultiInstanceBehavior.updateResultCollection(childExecution, miRootExecution);

    // Assert that nothing has changed
    verify(miRootExecution).getVariableLocal("Loop Data Output Ref");
    verify(miRootExecution).setVariableLocal(eq("Loop Data Output Ref"), isA(Object.class));
    Activity activity2 = parallelMultiInstanceBehavior.activity;
    Collection<Artifact> artifacts = ((AdhocSubProcess) activity2).getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = ((AdhocSubProcess) activity2).getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(activity2 instanceof AdhocSubProcess);
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#updateResultCollection(DelegateExecution,
   * DelegateExecution)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#updateResultCollection(DelegateExecution, DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MultiInstanceActivityBehavior.updateResultCollection(DelegateExecution, DelegateExecution)"
  })
  public void testUpdateResultCollection_givenArrayList() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopDataOutputRef("Loop Data Output Ref");
    ExecutionEntityImpl childExecution =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    DelegateExecution miRootExecution = mock(DelegateExecution.class);
    when(miRootExecution.getVariableLocal(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(miRootExecution.setVariableLocal(Mockito.<String>any(), Mockito.<Object>any()))
        .thenReturn(JSONObject.NULL);

    // Act
    parallelMultiInstanceBehavior.updateResultCollection(childExecution, miRootExecution);

    // Assert
    verify(miRootExecution).getVariableLocal("Loop Data Output Ref");
    verify(miRootExecution).setVariableLocal(eq("Loop Data Output Ref"), isA(Object.class));
    Activity activity2 = parallelMultiInstanceBehavior.activity;
    Collection<Artifact> artifacts = ((AdhocSubProcess) activity2).getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = ((AdhocSubProcess) activity2).getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(activity2 instanceof AdhocSubProcess);
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#updateResultCollection(DelegateExecution,
   * DelegateExecution)}.
   *
   * <ul>
   *   <li>When {@link DelegateExecution} {@link DelegateExecution#getVariableLocal(String)} return
   *       {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#updateResultCollection(DelegateExecution, DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MultiInstanceActivityBehavior.updateResultCollection(DelegateExecution, DelegateExecution)"
  })
  public void testUpdateResultCollection_whenDelegateExecutionGetVariableLocalReturnNull() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopDataOutputRef("Loop Data Output Ref");
    ExecutionEntityImpl childExecution =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    DelegateExecution miRootExecution = mock(DelegateExecution.class);
    when(miRootExecution.getVariableLocal(Mockito.<String>any())).thenReturn(JSONObject.NULL);
    when(miRootExecution.setVariableLocal(Mockito.<String>any(), Mockito.<Object>any()))
        .thenReturn(JSONObject.NULL);

    // Act
    parallelMultiInstanceBehavior.updateResultCollection(childExecution, miRootExecution);

    // Assert that nothing has changed
    verify(miRootExecution).getVariableLocal("Loop Data Output Ref");
    verify(miRootExecution).setVariableLocal(eq("Loop Data Output Ref"), isA(Object.class));
    Activity activity2 = parallelMultiInstanceBehavior.activity;
    Collection<Artifact> artifacts = ((AdhocSubProcess) activity2).getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = ((AdhocSubProcess) activity2).getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(activity2 instanceof AdhocSubProcess);
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#getResultElementItem(Map)} with {@code
   * availableVariables}.
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#getResultElementItem(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object MultiInstanceActivityBehavior.getResultElementItem(Map)"})
  public void testGetResultElementItemWithAvailableVariables() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act
    Object actualResultElementItem =
        parallelMultiInstanceBehavior.getResultElementItem(new HashMap<>());

    // Assert
    assertTrue(actualResultElementItem instanceof Map);
    assertTrue(((Map<Object, Object>) actualResultElementItem).isEmpty());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#getResultElementItem(Map)} with {@code
   * availableVariables}.
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#getResultElementItem(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object MultiInstanceActivityBehavior.getResultElementItem(Map)"})
  public void testGetResultElementItemWithAvailableVariables2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setOutputDataItem("");

    // Act
    Object actualResultElementItem =
        parallelMultiInstanceBehavior.getResultElementItem(new HashMap<>());

    // Assert
    assertTrue(actualResultElementItem instanceof Map);
    assertTrue(((Map<Object, Object>) actualResultElementItem).isEmpty());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#getResultElementItem(Map)} with {@code
   * availableVariables}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#getResultElementItem(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object MultiInstanceActivityBehavior.getResultElementItem(Map)"})
  public void testGetResultElementItemWithAvailableVariables_thenReturnNull() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setOutputDataItem("nrOfInstances");

    // Act and Assert
    assertNull(parallelMultiInstanceBehavior.getResultElementItem(new HashMap<>()));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#getResultElementItem(DelegateExecution)} with {@code
   * childExecution}.
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#getResultElementItem(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Object MultiInstanceActivityBehavior.getResultElementItem(DelegateExecution)"
  })
  public void testGetResultElementItemWithChildExecution() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setOutputDataItem("");

    // Act
    Object actualResultElementItem =
        parallelMultiInstanceBehavior.getResultElementItem(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    assertTrue(actualResultElementItem instanceof Map);
    assertTrue(((Map<Object, Object>) actualResultElementItem).isEmpty());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#getResultElementItem(DelegateExecution)} with {@code
   * childExecution}.
   *
   * <ul>
   *   <li>Then return {@link Map}.
   * </ul>
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#getResultElementItem(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Object MultiInstanceActivityBehavior.getResultElementItem(DelegateExecution)"
  })
  public void testGetResultElementItemWithChildExecution_thenReturnMap() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act
    Object actualResultElementItem =
        parallelMultiInstanceBehavior.getResultElementItem(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    assertTrue(actualResultElementItem instanceof Map);
    assertTrue(((Map<Object, Object>) actualResultElementItem).isEmpty());
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#getResultElementItem(DelegateExecution)} with {@code
   * childExecution}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#getResultElementItem(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Object MultiInstanceActivityBehavior.getResultElementItem(DelegateExecution)"
  })
  public void testGetResultElementItemWithChildExecution_thenReturnNull() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setOutputDataItem("nrOfInstances");

    // Act and Assert
    assertNull(
        parallelMultiInstanceBehavior.getResultElementItem(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link
   * MultiInstanceActivityBehavior#propagateLoopDataOutputRefToProcessInstance(ExecutionEntity)}.
   *
   * <ul>
   *   <li>Then calls {@link ExecutionEntity#getVariable(String)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * MultiInstanceActivityBehavior#propagateLoopDataOutputRefToProcessInstance(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MultiInstanceActivityBehavior.propagateLoopDataOutputRefToProcessInstance(ExecutionEntity)"
  })
  public void testPropagateLoopDataOutputRefToProcessInstance_thenCallsGetVariable() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopDataOutputRef("Loop Data Output Ref");

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    doNothing().when(executionEntityImpl).setVariable(Mockito.<String>any(), Mockito.<Object>any());

    ExecutionEntity miRootExecution = mock(ExecutionEntity.class);
    when(miRootExecution.getVariable(Mockito.<String>any())).thenReturn(JSONObject.NULL);
    when(miRootExecution.getProcessInstance()).thenReturn(executionEntityImpl);

    // Act
    parallelMultiInstanceBehavior.propagateLoopDataOutputRefToProcessInstance(miRootExecution);

    // Assert
    verify(miRootExecution).getVariable("Loop Data Output Ref");
    verify(miRootExecution).getProcessInstance();
    verify(executionEntityImpl).setVariable(eq("Loop Data Output Ref"), isA(Object.class));
  }

  /**
   * Test {@link MultiInstanceActivityBehavior#getCommandContext()}.
   *
   * <p>Method under test: {@link MultiInstanceActivityBehavior#getCommandContext()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.interceptor.CommandContext MultiInstanceActivityBehavior.getCommandContext()"
  })
  public void testGetCommandContext() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertNull(parallelMultiInstanceBehavior.getCommandContext());
  }
}
