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
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class SignalEventDefinitionDiffblueTest {
  /**
   * Test {@link SignalEventDefinition#clone()}.
   * <ul>
   *   <li>Given {@link SignalEventDefinition} (default constructor) Async is {@code true}.</li>
   *   <li>Then return Async.</li>
   * </ul>
   * <p>
   * Method under test: {@link SignalEventDefinition#clone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SignalEventDefinition SignalEventDefinition.clone()"})
  public void testClone_givenSignalEventDefinitionAsyncIsTrue_thenReturnAsync() {
    // Arrange
    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();
    signalEventDefinition.setAsync(true);

    // Act
    SignalEventDefinition actualCloneResult = signalEventDefinition.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getSignalExpression());
    assertNull(actualCloneResult.getSignalRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isAsync());
  }

  /**
   * Test {@link SignalEventDefinition#clone()}.
   * <ul>
   *   <li>Given {@link SignalEventDefinition} (default constructor).</li>
   *   <li>Then return not Async.</li>
   * </ul>
   * <p>
   * Method under test: {@link SignalEventDefinition#clone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SignalEventDefinition SignalEventDefinition.clone()"})
  public void testClone_givenSignalEventDefinition_thenReturnNotAsync() {
    // Arrange and Act
    SignalEventDefinition actualCloneResult = (new SignalEventDefinition()).clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getSignalExpression());
    assertNull(actualCloneResult.getSignalRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.isAsync());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link SignalEventDefinition}
   *   <li>{@link SignalEventDefinition#setAsync(boolean)}
   *   <li>{@link SignalEventDefinition#setSignalExpression(String)}
   *   <li>{@link SignalEventDefinition#setSignalRef(String)}
   *   <li>{@link SignalEventDefinition#getSignalExpression()}
   *   <li>{@link SignalEventDefinition#getSignalRef()}
   *   <li>{@link SignalEventDefinition#isAsync()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SignalEventDefinition.<init>()", "String SignalEventDefinition.getSignalExpression()",
      "String SignalEventDefinition.getSignalRef()", "boolean SignalEventDefinition.isAsync()",
      "void SignalEventDefinition.setAsync(boolean)", "void SignalEventDefinition.setSignalExpression(String)",
      "void SignalEventDefinition.setSignalRef(String)"})
  public void testGettersAndSetters() {
    // Arrange and Act
    SignalEventDefinition actualSignalEventDefinition = new SignalEventDefinition();
    actualSignalEventDefinition.setAsync(true);
    actualSignalEventDefinition.setSignalExpression("Signal Expression");
    actualSignalEventDefinition.setSignalRef("Signal Ref");
    String actualSignalExpression = actualSignalEventDefinition.getSignalExpression();
    String actualSignalRef = actualSignalEventDefinition.getSignalRef();
    boolean actualIsAsyncResult = actualSignalEventDefinition.isAsync();

    // Assert
    assertEquals("Signal Expression", actualSignalExpression);
    assertEquals("Signal Ref", actualSignalRef);
    assertNull(actualSignalEventDefinition.getId());
    assertEquals(0, actualSignalEventDefinition.getXmlColumnNumber());
    assertEquals(0, actualSignalEventDefinition.getXmlRowNumber());
    assertTrue(actualSignalEventDefinition.getAttributes().isEmpty());
    assertTrue(actualSignalEventDefinition.getExtensionElements().isEmpty());
    assertTrue(actualIsAsyncResult);
  }
}
