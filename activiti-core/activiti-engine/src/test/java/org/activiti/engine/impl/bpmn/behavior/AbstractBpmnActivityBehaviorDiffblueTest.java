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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AbstractBpmnActivityBehaviorDiffblueTest {
  /**
   * Test {@link AbstractBpmnActivityBehavior#hasLoopCharacteristics()}.
   *
   * <ul>
   *   <li>Given {@link AbstractBpmnActivityBehavior} (default constructor).
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractBpmnActivityBehavior#hasLoopCharacteristics()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AbstractBpmnActivityBehavior.hasLoopCharacteristics()"})
  public void testHasLoopCharacteristics_givenAbstractBpmnActivityBehavior_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new AbstractBpmnActivityBehavior().hasLoopCharacteristics());
  }

  /**
   * Test {@link AbstractBpmnActivityBehavior#hasLoopCharacteristics()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractBpmnActivityBehavior#hasLoopCharacteristics()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AbstractBpmnActivityBehavior.hasLoopCharacteristics()"})
  public void testHasLoopCharacteristics_thenReturnTrue() {
    // Arrange
    CallActivityBehavior callActivityBehavior =
        new CallActivityBehavior("Process Definition Key", new ArrayList<>());
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior multiInstanceActivityBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    callActivityBehavior.setMultiInstanceActivityBehavior(multiInstanceActivityBehavior);

    // Act and Assert
    assertTrue(callActivityBehavior.hasLoopCharacteristics());
  }

  /**
   * Test {@link AbstractBpmnActivityBehavior#hasMultiInstanceCharacteristics()}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractBpmnActivityBehavior#hasMultiInstanceCharacteristics()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AbstractBpmnActivityBehavior.hasMultiInstanceCharacteristics()"})
  public void testHasMultiInstanceCharacteristics_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new AbstractBpmnActivityBehavior().hasMultiInstanceCharacteristics());
  }

  /**
   * Test {@link AbstractBpmnActivityBehavior#hasMultiInstanceCharacteristics()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractBpmnActivityBehavior#hasMultiInstanceCharacteristics()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AbstractBpmnActivityBehavior.hasMultiInstanceCharacteristics()"})
  public void testHasMultiInstanceCharacteristics_thenReturnTrue() {
    // Arrange
    CallActivityBehavior callActivityBehavior =
        new CallActivityBehavior("Process Definition Key", new ArrayList<>());
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior multiInstanceActivityBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    callActivityBehavior.setMultiInstanceActivityBehavior(multiInstanceActivityBehavior);

    // Act and Assert
    assertTrue(callActivityBehavior.hasMultiInstanceCharacteristics());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link
   *       AbstractBpmnActivityBehavior#setMultiInstanceActivityBehavior(MultiInstanceActivityBehavior)}
   *   <li>{@link AbstractBpmnActivityBehavior#getMultiInstanceActivityBehavior()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MultiInstanceActivityBehavior AbstractBpmnActivityBehavior.getMultiInstanceActivityBehavior()",
    "void AbstractBpmnActivityBehavior.setMultiInstanceActivityBehavior(MultiInstanceActivityBehavior)"
  })
  public void testGettersAndSetters() {
    // Arrange
    AbstractBpmnActivityBehavior abstractBpmnActivityBehavior = new AbstractBpmnActivityBehavior();
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior multiInstanceActivityBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());

    // Act
    abstractBpmnActivityBehavior.setMultiInstanceActivityBehavior(multiInstanceActivityBehavior);

    // Assert
    assertSame(
        multiInstanceActivityBehavior,
        abstractBpmnActivityBehavior.getMultiInstanceActivityBehavior());
  }

  /**
   * Test new {@link AbstractBpmnActivityBehavior} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link
   * AbstractBpmnActivityBehavior}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractBpmnActivityBehavior.<init>()"})
  public void testNewAbstractBpmnActivityBehavior() {
    // Arrange and Act
    AbstractBpmnActivityBehavior actualAbstractBpmnActivityBehavior =
        new AbstractBpmnActivityBehavior();

    // Assert
    assertNull(actualAbstractBpmnActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualAbstractBpmnActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualAbstractBpmnActivityBehavior.hasMultiInstanceCharacteristics());
  }
}
