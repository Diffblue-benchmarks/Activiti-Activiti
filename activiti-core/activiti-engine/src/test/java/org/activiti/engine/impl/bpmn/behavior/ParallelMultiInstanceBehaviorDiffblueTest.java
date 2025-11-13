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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.bpmn.model.Activity;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ParallelMultiInstanceBehaviorDiffblueTest {
  /**
   * Test {@link ParallelMultiInstanceBehavior#ParallelMultiInstanceBehavior(Activity,
   * AbstractBpmnActivityBehavior)}.
   *
   * <ul>
   *   <li>Then {@link MultiInstanceActivityBehavior#activity} return {@link AdhocSubProcess}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ParallelMultiInstanceBehavior#ParallelMultiInstanceBehavior(Activity,
   * AbstractBpmnActivityBehavior)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ParallelMultiInstanceBehavior.<init>(Activity, AbstractBpmnActivityBehavior)"
  })
  public void testNewParallelMultiInstanceBehavior_thenActivityReturnAdhocSubProcess() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    AbstractBpmnActivityBehavior originalActivityBehavior = new AbstractBpmnActivityBehavior();

    // Act
    ParallelMultiInstanceBehavior actualParallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, originalActivityBehavior);

    // Assert
    assertTrue(actualParallelMultiInstanceBehavior.activity instanceof AdhocSubProcess);
    assertEquals(
        "loopCounter", actualParallelMultiInstanceBehavior.getCollectionElementIndexVariable());
    assertNull(actualParallelMultiInstanceBehavior.getCollectionElementVariable());
    assertNull(actualParallelMultiInstanceBehavior.getCollectionVariable());
    assertNull(actualParallelMultiInstanceBehavior.getLoopDataOutputRef());
    assertNull(actualParallelMultiInstanceBehavior.getOutputDataItem());
    assertNull(actualParallelMultiInstanceBehavior.getCollectionExpression());
    assertNull(actualParallelMultiInstanceBehavior.getCompletionConditionExpression());
    assertNull(actualParallelMultiInstanceBehavior.getLoopCardinalityExpression());
    assertNull(actualParallelMultiInstanceBehavior.getCommandContext());
    assertFalse(actualParallelMultiInstanceBehavior.hasLoopDataOutputRef());
    assertFalse(actualParallelMultiInstanceBehavior.hasOutputDataItem());
    assertTrue(originalActivityBehavior.hasLoopCharacteristics());
    assertTrue(originalActivityBehavior.hasMultiInstanceCharacteristics());
    assertSame(
        originalActivityBehavior, actualParallelMultiInstanceBehavior.getInnerActivityBehavior());
  }

  /**
   * Test {@link ParallelMultiInstanceBehavior#createInstances(DelegateExecution)}.
   *
   * <p>Method under test: {@link ParallelMultiInstanceBehavior#createInstances(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ParallelMultiInstanceBehavior.createInstances(DelegateExecution)"})
  public void testCreateInstances() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            parallelMultiInstanceBehavior.createInstances(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link ParallelMultiInstanceBehavior#createInstances(DelegateExecution)}.
   *
   * <p>Method under test: {@link ParallelMultiInstanceBehavior#createInstances(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ParallelMultiInstanceBehavior.createInstances(DelegateExecution)"})
  public void testCreateInstances2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopCardinalityExpression(new FixedValue(JSONObject.NULL));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            parallelMultiInstanceBehavior.createInstances(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link ParallelMultiInstanceBehavior#createInstances(DelegateExecution)}.
   *
   * <p>Method under test: {@link ParallelMultiInstanceBehavior#createInstances(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ParallelMultiInstanceBehavior.createInstances(DelegateExecution)"})
  public void testCreateInstances3() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setCollectionExpression(new FixedValue(JSONObject.NULL));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            parallelMultiInstanceBehavior.createInstances(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link ParallelMultiInstanceBehavior#createInstances(DelegateExecution)}.
   *
   * <p>Method under test: {@link ParallelMultiInstanceBehavior#createInstances(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ParallelMultiInstanceBehavior.createInstances(DelegateExecution)"})
  public void testCreateInstances4() {
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
            parallelMultiInstanceBehavior.createInstances(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link ParallelMultiInstanceBehavior#createInstances(DelegateExecution)}.
   *
   * <p>Method under test: {@link ParallelMultiInstanceBehavior#createInstances(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ParallelMultiInstanceBehavior.createInstances(DelegateExecution)"})
  public void testCreateInstances5() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopCardinalityExpression(new FixedValue(-1));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            parallelMultiInstanceBehavior.createInstances(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link ParallelMultiInstanceBehavior#leave(DelegateExecution)}.
   *
   * <p>Method under test: {@link ParallelMultiInstanceBehavior#leave(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ParallelMultiInstanceBehavior.leave(DelegateExecution)"})
  public void testLeave() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            parallelMultiInstanceBehavior.leave(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link ParallelMultiInstanceBehavior#leave(DelegateExecution)}.
   *
   * <p>Method under test: {@link ParallelMultiInstanceBehavior#leave(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ParallelMultiInstanceBehavior.leave(DelegateExecution)"})
  public void testLeave2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopCardinalityExpression(new FixedValue(JSONObject.NULL));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            parallelMultiInstanceBehavior.leave(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link ParallelMultiInstanceBehavior#leave(DelegateExecution)}.
   *
   * <p>Method under test: {@link ParallelMultiInstanceBehavior#leave(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ParallelMultiInstanceBehavior.leave(DelegateExecution)"})
  public void testLeave3() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setCollectionExpression(new FixedValue(JSONObject.NULL));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            parallelMultiInstanceBehavior.leave(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link ParallelMultiInstanceBehavior#leave(DelegateExecution)}.
   *
   * <p>Method under test: {@link ParallelMultiInstanceBehavior#leave(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ParallelMultiInstanceBehavior.leave(DelegateExecution)"})
  public void testLeave4() {
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
            parallelMultiInstanceBehavior.leave(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }
}
