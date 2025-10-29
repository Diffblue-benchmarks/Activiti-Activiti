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
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import org.junit.Test;

public class ParallelGatewayDiffblueTest {
  /**
   * Method under test: {@link ParallelGateway#clone()}
   */
  @Test
  public void testClone() {
    // Arrange and Act
    ParallelGateway actualCloneResult = (new ParallelGateway()).clone();

    // Assert
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Method under test: {@link ParallelGateway#clone()}
   */
  @Test
  public void testClone2() {
    // Arrange
    ParallelGateway parallelGateway = new ParallelGateway();
    parallelGateway.setAsynchronous(true);

    // Act
    ParallelGateway actualCloneResult = parallelGateway.clone();

    // Assert
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.isNotExclusive());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isAsynchronous());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Method under test: {@link ParallelGateway#clone()}
   */
  @Test
  public void testClone3() {
    // Arrange
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.computeIfPresent("foo", mock(BiFunction.class));

    ParallelGateway parallelGateway = new ParallelGateway();
    parallelGateway.setExtensionElements(null);
    parallelGateway.setAttributes(attributes);

    // Act
    ParallelGateway actualCloneResult = parallelGateway.clone();

    // Assert
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link ParallelGateway}
   */
  @Test
  public void testNewParallelGateway() {
    // Arrange and Act
    ParallelGateway actualParallelGateway = new ParallelGateway();

    // Assert
    assertNull(actualParallelGateway.getBehavior());
    assertNull(actualParallelGateway.getId());
    assertNull(actualParallelGateway.getDocumentation());
    assertNull(actualParallelGateway.getName());
    assertNull(actualParallelGateway.getDefaultFlow());
    assertNull(actualParallelGateway.getParentContainer());
    assertEquals(0, actualParallelGateway.getXmlColumnNumber());
    assertEquals(0, actualParallelGateway.getXmlRowNumber());
    assertFalse(actualParallelGateway.isAsynchronous());
    assertFalse(actualParallelGateway.isNotExclusive());
    assertTrue(actualParallelGateway.getExecutionListeners().isEmpty());
    assertTrue(actualParallelGateway.getIncomingFlows().isEmpty());
    assertTrue(actualParallelGateway.getOutgoingFlows().isEmpty());
    assertTrue(actualParallelGateway.getAttributes().isEmpty());
    assertTrue(actualParallelGateway.getExtensionElements().isEmpty());
  }
}
