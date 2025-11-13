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
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.bpmn.model.Signal;
import org.activiti.bpmn.model.SignalEventDefinition;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class IntermediateThrowSignalEventActivityBehaviorDiffblueTest {
  /**
   * Test {@link
   * IntermediateThrowSignalEventActivityBehavior#IntermediateThrowSignalEventActivityBehavior(SignalEventDefinition,
   * Signal)}.
   *
   * <p>Method under test: {@link
   * IntermediateThrowSignalEventActivityBehavior#IntermediateThrowSignalEventActivityBehavior(SignalEventDefinition,
   * Signal)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void IntermediateThrowSignalEventActivityBehavior.<init>(SignalEventDefinition, Signal)"
  })
  public void testNewIntermediateThrowSignalEventActivityBehavior() {
    // Arrange
    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();
    signalEventDefinition.setSignalRef("not empty");

    Signal signal = new Signal("42", "Name");
    signal.setScope(Signal.SCOPE_PROCESS_INSTANCE);

    // Act
    IntermediateThrowSignalEventActivityBehavior
        actualIntermediateThrowSignalEventActivityBehavior =
            new IntermediateThrowSignalEventActivityBehavior(signalEventDefinition, signal);

    // Assert
    assertEquals("Name", actualIntermediateThrowSignalEventActivityBehavior.signalEventName);
    SignalEventDefinition signalEventDefinition2 =
        actualIntermediateThrowSignalEventActivityBehavior.signalEventDefinition;
    assertEquals("not empty", signalEventDefinition2.getSignalRef());
    assertNull(signalEventDefinition2.getId());
    assertNull(signalEventDefinition2.getSignalExpression());
    assertNull(actualIntermediateThrowSignalEventActivityBehavior.signalExpression);
    assertNull(
        actualIntermediateThrowSignalEventActivityBehavior.getMultiInstanceActivityBehavior());
    assertEquals(0, signalEventDefinition2.getXmlColumnNumber());
    assertEquals(0, signalEventDefinition2.getXmlRowNumber());
    assertFalse(signalEventDefinition2.isAsync());
    assertFalse(actualIntermediateThrowSignalEventActivityBehavior.hasLoopCharacteristics());
    assertFalse(
        actualIntermediateThrowSignalEventActivityBehavior.hasMultiInstanceCharacteristics());
    assertTrue(signalEventDefinition2.getAttributes().isEmpty());
    assertTrue(signalEventDefinition2.getExtensionElements().isEmpty());
    assertTrue(actualIntermediateThrowSignalEventActivityBehavior.processInstanceScope);
  }

  /**
   * Test {@link
   * IntermediateThrowSignalEventActivityBehavior#IntermediateThrowSignalEventActivityBehavior(SignalEventDefinition,
   * Signal)}.
   *
   * <p>Method under test: {@link
   * IntermediateThrowSignalEventActivityBehavior#IntermediateThrowSignalEventActivityBehavior(SignalEventDefinition,
   * Signal)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void IntermediateThrowSignalEventActivityBehavior.<init>(SignalEventDefinition, Signal)"
  })
  public void testNewIntermediateThrowSignalEventActivityBehavior2() {
    // Arrange
    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();
    signalEventDefinition.setSignalRef("not empty");

    // Act
    IntermediateThrowSignalEventActivityBehavior
        actualIntermediateThrowSignalEventActivityBehavior =
            new IntermediateThrowSignalEventActivityBehavior(signalEventDefinition, null);

    // Assert
    SignalEventDefinition signalEventDefinition2 =
        actualIntermediateThrowSignalEventActivityBehavior.signalEventDefinition;
    assertEquals("not empty", signalEventDefinition2.getSignalRef());
    assertEquals("not empty", actualIntermediateThrowSignalEventActivityBehavior.signalEventName);
    assertNull(signalEventDefinition2.getId());
    assertNull(signalEventDefinition2.getSignalExpression());
    assertNull(actualIntermediateThrowSignalEventActivityBehavior.signalExpression);
    assertNull(
        actualIntermediateThrowSignalEventActivityBehavior.getMultiInstanceActivityBehavior());
    assertEquals(0, signalEventDefinition2.getXmlColumnNumber());
    assertEquals(0, signalEventDefinition2.getXmlRowNumber());
    assertFalse(signalEventDefinition2.isAsync());
    assertFalse(actualIntermediateThrowSignalEventActivityBehavior.hasLoopCharacteristics());
    assertFalse(
        actualIntermediateThrowSignalEventActivityBehavior.hasMultiInstanceCharacteristics());
    assertFalse(actualIntermediateThrowSignalEventActivityBehavior.processInstanceScope);
    assertTrue(signalEventDefinition2.getAttributes().isEmpty());
    assertTrue(signalEventDefinition2.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link
   * IntermediateThrowSignalEventActivityBehavior#IntermediateThrowSignalEventActivityBehavior(SignalEventDefinition,
   * Signal)}.
   *
   * <p>Method under test: {@link
   * IntermediateThrowSignalEventActivityBehavior#IntermediateThrowSignalEventActivityBehavior(SignalEventDefinition,
   * Signal)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void IntermediateThrowSignalEventActivityBehavior.<init>(SignalEventDefinition, Signal)"
  })
  public void testNewIntermediateThrowSignalEventActivityBehavior3() {
    // Arrange
    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();
    signalEventDefinition.setSignalRef("");

    // Act
    IntermediateThrowSignalEventActivityBehavior
        actualIntermediateThrowSignalEventActivityBehavior =
            new IntermediateThrowSignalEventActivityBehavior(signalEventDefinition, null);

    // Assert
    SignalEventDefinition signalEventDefinition2 =
        actualIntermediateThrowSignalEventActivityBehavior.signalEventDefinition;
    assertEquals("", signalEventDefinition2.getSignalRef());
    assertNull(signalEventDefinition2.getId());
    assertNull(signalEventDefinition2.getSignalExpression());
    assertNull(actualIntermediateThrowSignalEventActivityBehavior.signalEventName);
    assertNull(actualIntermediateThrowSignalEventActivityBehavior.signalExpression);
    assertNull(
        actualIntermediateThrowSignalEventActivityBehavior.getMultiInstanceActivityBehavior());
    assertEquals(0, signalEventDefinition2.getXmlColumnNumber());
    assertEquals(0, signalEventDefinition2.getXmlRowNumber());
    assertFalse(signalEventDefinition2.isAsync());
    assertFalse(actualIntermediateThrowSignalEventActivityBehavior.hasLoopCharacteristics());
    assertFalse(
        actualIntermediateThrowSignalEventActivityBehavior.hasMultiInstanceCharacteristics());
    assertFalse(actualIntermediateThrowSignalEventActivityBehavior.processInstanceScope);
    assertTrue(signalEventDefinition2.getAttributes().isEmpty());
    assertTrue(signalEventDefinition2.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link
   * IntermediateThrowSignalEventActivityBehavior#IntermediateThrowSignalEventActivityBehavior(SignalEventDefinition,
   * Signal)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * IntermediateThrowSignalEventActivityBehavior#IntermediateThrowSignalEventActivityBehavior(SignalEventDefinition,
   * Signal)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void IntermediateThrowSignalEventActivityBehavior.<init>(SignalEventDefinition, Signal)"
  })
  public void testNewIntermediateThrowSignalEventActivityBehavior_givenNull() {
    // Arrange
    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();
    signalEventDefinition.setSignalRef(null);

    // Act
    IntermediateThrowSignalEventActivityBehavior
        actualIntermediateThrowSignalEventActivityBehavior =
            new IntermediateThrowSignalEventActivityBehavior(signalEventDefinition, null);

    // Assert
    SignalEventDefinition signalEventDefinition2 =
        actualIntermediateThrowSignalEventActivityBehavior.signalEventDefinition;
    assertNull(signalEventDefinition2.getId());
    assertNull(signalEventDefinition2.getSignalExpression());
    assertNull(signalEventDefinition2.getSignalRef());
    assertNull(actualIntermediateThrowSignalEventActivityBehavior.signalEventName);
    assertNull(actualIntermediateThrowSignalEventActivityBehavior.signalExpression);
    assertNull(
        actualIntermediateThrowSignalEventActivityBehavior.getMultiInstanceActivityBehavior());
    assertEquals(0, signalEventDefinition2.getXmlColumnNumber());
    assertEquals(0, signalEventDefinition2.getXmlRowNumber());
    assertFalse(signalEventDefinition2.isAsync());
    assertFalse(actualIntermediateThrowSignalEventActivityBehavior.hasLoopCharacteristics());
    assertFalse(
        actualIntermediateThrowSignalEventActivityBehavior.hasMultiInstanceCharacteristics());
    assertFalse(actualIntermediateThrowSignalEventActivityBehavior.processInstanceScope);
    assertTrue(signalEventDefinition2.getAttributes().isEmpty());
    assertTrue(signalEventDefinition2.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link
   * IntermediateThrowSignalEventActivityBehavior#IntermediateThrowSignalEventActivityBehavior(SignalEventDefinition,
   * Signal)}.
   *
   * <ul>
   *   <li>When {@link SignalEventDefinition} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link
   * IntermediateThrowSignalEventActivityBehavior#IntermediateThrowSignalEventActivityBehavior(SignalEventDefinition,
   * Signal)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void IntermediateThrowSignalEventActivityBehavior.<init>(SignalEventDefinition, Signal)"
  })
  public void testNewIntermediateThrowSignalEventActivityBehavior_whenSignalEventDefinition() {
    // Arrange
    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();

    // Act
    IntermediateThrowSignalEventActivityBehavior
        actualIntermediateThrowSignalEventActivityBehavior =
            new IntermediateThrowSignalEventActivityBehavior(signalEventDefinition, new Signal());

    // Assert
    SignalEventDefinition signalEventDefinition2 =
        actualIntermediateThrowSignalEventActivityBehavior.signalEventDefinition;
    assertNull(signalEventDefinition2.getId());
    assertNull(signalEventDefinition2.getSignalExpression());
    assertNull(signalEventDefinition2.getSignalRef());
    assertNull(actualIntermediateThrowSignalEventActivityBehavior.signalEventName);
    assertNull(actualIntermediateThrowSignalEventActivityBehavior.signalExpression);
    assertNull(
        actualIntermediateThrowSignalEventActivityBehavior.getMultiInstanceActivityBehavior());
    assertEquals(0, signalEventDefinition2.getXmlColumnNumber());
    assertEquals(0, signalEventDefinition2.getXmlRowNumber());
    assertFalse(signalEventDefinition2.isAsync());
    assertFalse(actualIntermediateThrowSignalEventActivityBehavior.hasLoopCharacteristics());
    assertFalse(
        actualIntermediateThrowSignalEventActivityBehavior.hasMultiInstanceCharacteristics());
    assertFalse(actualIntermediateThrowSignalEventActivityBehavior.processInstanceScope);
    assertTrue(signalEventDefinition2.getAttributes().isEmpty());
    assertTrue(signalEventDefinition2.getExtensionElements().isEmpty());
  }
}
