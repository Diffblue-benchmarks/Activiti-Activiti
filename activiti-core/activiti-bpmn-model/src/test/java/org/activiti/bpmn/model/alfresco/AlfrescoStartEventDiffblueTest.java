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
package org.activiti.bpmn.model.alfresco;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AlfrescoStartEventDiffblueTest {
  /**
   * Test {@link AlfrescoStartEvent#clone()}.
   * <ul>
   *   <li>Given {@link AlfrescoStartEvent} (default constructor).</li>
   *   <li>Then return Behavior is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AlfrescoStartEvent#clone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AlfrescoStartEvent AlfrescoStartEvent.clone()"})
  public void testClone_givenAlfrescoStartEvent_thenReturnBehaviorIsNull() {
    // Arrange and Act
    AlfrescoStartEvent actualCloneResult = (new AlfrescoStartEvent()).clone();

    // Assert
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getFormKey());
    assertNull(actualCloneResult.getInitiator());
    assertNull(actualCloneResult.getRunAs());
    assertNull(actualCloneResult.getScriptProcessor());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertFalse(actualCloneResult.isInterrupting());
    assertTrue(actualCloneResult.getEventDefinitions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getFormProperties().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link AlfrescoStartEvent}
   *   <li>{@link AlfrescoStartEvent#setRunAs(String)}
   *   <li>{@link AlfrescoStartEvent#setScriptProcessor(String)}
   *   <li>{@link AlfrescoStartEvent#getRunAs()}
   *   <li>{@link AlfrescoStartEvent#getScriptProcessor()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AlfrescoStartEvent.<init>()", "String AlfrescoStartEvent.getRunAs()",
      "String AlfrescoStartEvent.getScriptProcessor()", "void AlfrescoStartEvent.setRunAs(String)",
      "void AlfrescoStartEvent.setScriptProcessor(String)"})
  public void testGettersAndSetters() {
    // Arrange and Act
    AlfrescoStartEvent actualAlfrescoStartEvent = new AlfrescoStartEvent();
    actualAlfrescoStartEvent.setRunAs("Run As");
    actualAlfrescoStartEvent.setScriptProcessor("Script Processor");
    String actualRunAs = actualAlfrescoStartEvent.getRunAs();

    // Assert
    assertEquals("Run As", actualRunAs);
    assertEquals("Script Processor", actualAlfrescoStartEvent.getScriptProcessor());
    assertNull(actualAlfrescoStartEvent.getBehavior());
    assertNull(actualAlfrescoStartEvent.getId());
    assertNull(actualAlfrescoStartEvent.getDocumentation());
    assertNull(actualAlfrescoStartEvent.getName());
    assertNull(actualAlfrescoStartEvent.getFormKey());
    assertNull(actualAlfrescoStartEvent.getInitiator());
    assertNull(actualAlfrescoStartEvent.getParentContainer());
    assertEquals(0, actualAlfrescoStartEvent.getXmlColumnNumber());
    assertEquals(0, actualAlfrescoStartEvent.getXmlRowNumber());
    assertFalse(actualAlfrescoStartEvent.isAsynchronous());
    assertFalse(actualAlfrescoStartEvent.isNotExclusive());
    assertFalse(actualAlfrescoStartEvent.isInterrupting());
    assertTrue(actualAlfrescoStartEvent.getEventDefinitions().isEmpty());
    assertTrue(actualAlfrescoStartEvent.getExecutionListeners().isEmpty());
    assertTrue(actualAlfrescoStartEvent.getIncomingFlows().isEmpty());
    assertTrue(actualAlfrescoStartEvent.getOutgoingFlows().isEmpty());
    assertTrue(actualAlfrescoStartEvent.getFormProperties().isEmpty());
    assertTrue(actualAlfrescoStartEvent.getAttributes().isEmpty());
    assertTrue(actualAlfrescoStartEvent.getExtensionElements().isEmpty());
  }
}
