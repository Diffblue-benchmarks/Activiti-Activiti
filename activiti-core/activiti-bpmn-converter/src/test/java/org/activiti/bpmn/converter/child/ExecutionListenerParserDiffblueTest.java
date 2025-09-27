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
package org.activiti.bpmn.converter.child;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.List;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.SequenceFlow;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ExecutionListenerParserDiffblueTest {
  /**
   * Test {@link ExecutionListenerParser#addListenerToParent(ActivitiListener, BaseElement)}.
   *
   * <ul>
   *   <li>Given empty string.
   *   <li>When {@link ActivitiListener} (default constructor) Event is empty string.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionListenerParser#addListenerToParent(ActivitiListener,
   * BaseElement)}
   */
  @Test
  @DisplayName(
      "Test addListenerToParent(ActivitiListener, BaseElement); given empty string; when ActivitiListener (default constructor) Event is empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionListenerParser.addListenerToParent(ActivitiListener, BaseElement)"
  })
  void testAddListenerToParent_givenEmptyString_whenActivitiListenerEventIsEmptyString() {
    // Arrange
    ExecutionListenerParser executionListenerParser = new ExecutionListenerParser();

    ActivitiListener listener = new ActivitiListener();
    listener.setEvent("");
    SequenceFlow parentElement = new SequenceFlow("Source Ref", "Target Ref");

    // Act
    executionListenerParser.addListenerToParent(listener, parentElement);

    // Assert
    assertEquals("take", listener.getEvent());
    List<ActivitiListener> executionListeners = parentElement.getExecutionListeners();
    assertEquals(1, executionListeners.size());
    assertSame(listener, executionListeners.get(0));
  }

  /**
   * Test {@link ExecutionListenerParser#addListenerToParent(ActivitiListener, BaseElement)}.
   *
   * <ul>
   *   <li>Given {@code Listener}.
   *   <li>Then {@link ActivitiListener} (default constructor) Event is {@code Listener}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionListenerParser#addListenerToParent(ActivitiListener,
   * BaseElement)}
   */
  @Test
  @DisplayName(
      "Test addListenerToParent(ActivitiListener, BaseElement); given 'Listener'; then ActivitiListener (default constructor) Event is 'Listener'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionListenerParser.addListenerToParent(ActivitiListener, BaseElement)"
  })
  void testAddListenerToParent_givenListener_thenActivitiListenerEventIsListener() {
    // Arrange
    ExecutionListenerParser executionListenerParser = new ExecutionListenerParser();

    ActivitiListener listener = new ActivitiListener();
    listener.setEvent("Listener");
    SequenceFlow parentElement = new SequenceFlow("Source Ref", "Target Ref");

    // Act
    executionListenerParser.addListenerToParent(listener, parentElement);

    // Assert
    assertEquals("Listener", listener.getEvent());
    List<ActivitiListener> executionListeners = parentElement.getExecutionListeners();
    assertEquals(1, executionListeners.size());
    assertSame(listener, executionListeners.get(0));
  }

  /**
   * Test {@link ExecutionListenerParser#addListenerToParent(ActivitiListener, BaseElement)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link ActivitiListener} (default constructor) Event is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionListenerParser#addListenerToParent(ActivitiListener,
   * BaseElement)}
   */
  @Test
  @DisplayName(
      "Test addListenerToParent(ActivitiListener, BaseElement); given 'null'; when ActivitiListener (default constructor) Event is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionListenerParser.addListenerToParent(ActivitiListener, BaseElement)"
  })
  void testAddListenerToParent_givenNull_whenActivitiListenerEventIsNull() {
    // Arrange
    ExecutionListenerParser executionListenerParser = new ExecutionListenerParser();

    ActivitiListener listener = new ActivitiListener();
    listener.setEvent(null);
    SequenceFlow parentElement = new SequenceFlow("Source Ref", "Target Ref");

    // Act
    executionListenerParser.addListenerToParent(listener, parentElement);

    // Assert
    assertEquals("take", listener.getEvent());
    List<ActivitiListener> executionListeners = parentElement.getExecutionListeners();
    assertEquals(1, executionListeners.size());
    assertSame(listener, executionListeners.get(0));
  }

  /**
   * Test {@link ExecutionListenerParser#addListenerToParent(ActivitiListener, BaseElement)}.
   *
   * <ul>
   *   <li>Then {@link AdhocSubProcess} (default constructor) ExecutionListeners size is one.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionListenerParser#addListenerToParent(ActivitiListener,
   * BaseElement)}
   */
  @Test
  @DisplayName(
      "Test addListenerToParent(ActivitiListener, BaseElement); then AdhocSubProcess (default constructor) ExecutionListeners size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionListenerParser.addListenerToParent(ActivitiListener, BaseElement)"
  })
  void testAddListenerToParent_thenAdhocSubProcessExecutionListenersSizeIsOne() {
    // Arrange
    ExecutionListenerParser executionListenerParser = new ExecutionListenerParser();
    ActivitiListener listener = new ActivitiListener();
    AdhocSubProcess parentElement = new AdhocSubProcess();

    // Act
    executionListenerParser.addListenerToParent(listener, parentElement);

    // Assert
    assertNull(listener.getEvent());
    List<ActivitiListener> executionListeners = parentElement.getExecutionListeners();
    assertEquals(1, executionListeners.size());
    assertSame(listener, executionListeners.get(0));
  }

  /**
   * Test {@link ExecutionListenerParser#addListenerToParent(ActivitiListener, BaseElement)}.
   *
   * <ul>
   *   <li>When {@link ActivitiListener} (default constructor).
   *   <li>Then {@link ActivitiListener} (default constructor) Event is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionListenerParser#addListenerToParent(ActivitiListener,
   * BaseElement)}
   */
  @Test
  @DisplayName(
      "Test addListenerToParent(ActivitiListener, BaseElement); when ActivitiListener (default constructor); then ActivitiListener (default constructor) Event is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionListenerParser.addListenerToParent(ActivitiListener, BaseElement)"
  })
  void testAddListenerToParent_whenActivitiListener_thenActivitiListenerEventIsNull() {
    // Arrange
    ExecutionListenerParser executionListenerParser = new ExecutionListenerParser();
    ActivitiListener listener = new ActivitiListener();

    // Act
    executionListenerParser.addListenerToParent(listener, new ActivitiListener());

    // Assert that nothing has changed
    assertNull(listener.getEvent());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link ExecutionListenerParser}
   *   <li>{@link ExecutionListenerParser#getElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionListenerParser.<init>()",
    "java.lang.String ExecutionListenerParser.getElementName()"
  })
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("executionListener", new ExecutionListenerParser().getElementName());
  }
}
