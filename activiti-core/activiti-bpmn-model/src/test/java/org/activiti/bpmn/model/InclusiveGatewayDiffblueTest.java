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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class InclusiveGatewayDiffblueTest {
  /**
   * Test {@link InclusiveGateway#clone()}.
   *
   * <ul>
   *   <li>Given {@link InclusiveGateway} (default constructor) Asynchronous is {@code true}.
   *   <li>Then return Asynchronous.
   * </ul>
   *
   * <p>Method under test: {@link InclusiveGateway#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"InclusiveGateway InclusiveGateway.clone()"})
  public void testClone_givenInclusiveGatewayAsynchronousIsTrue_thenReturnAsynchronous() {
    // Arrange
    InclusiveGateway inclusiveGateway = new InclusiveGateway();
    inclusiveGateway.setAsynchronous(true);

    // Act
    InclusiveGateway actualCloneResult = inclusiveGateway.clone();

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
   * Test {@link InclusiveGateway#clone()}.
   *
   * <ul>
   *   <li>Given {@link InclusiveGateway} (default constructor).
   *   <li>Then return not Asynchronous.
   * </ul>
   *
   * <p>Method under test: {@link InclusiveGateway#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"InclusiveGateway InclusiveGateway.clone()"})
  public void testClone_givenInclusiveGateway_thenReturnNotAsynchronous() {
    // Arrange and Act
    InclusiveGateway actualCloneResult = new InclusiveGateway().clone();

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
   * Test new {@link InclusiveGateway} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link InclusiveGateway}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void InclusiveGateway.<init>()"})
  public void testNewInclusiveGateway() {
    // Arrange and Act
    InclusiveGateway actualInclusiveGateway = new InclusiveGateway();

    // Assert
    assertNull(actualInclusiveGateway.getBehavior());
    assertNull(actualInclusiveGateway.getId());
    assertNull(actualInclusiveGateway.getDocumentation());
    assertNull(actualInclusiveGateway.getName());
    assertNull(actualInclusiveGateway.getDefaultFlow());
    assertNull(actualInclusiveGateway.getParentContainer());
    assertEquals(0, actualInclusiveGateway.getXmlColumnNumber());
    assertEquals(0, actualInclusiveGateway.getXmlRowNumber());
    assertFalse(actualInclusiveGateway.isAsynchronous());
    assertFalse(actualInclusiveGateway.isNotExclusive());
    assertTrue(actualInclusiveGateway.getExecutionListeners().isEmpty());
    assertTrue(actualInclusiveGateway.getIncomingFlows().isEmpty());
    assertTrue(actualInclusiveGateway.getOutgoingFlows().isEmpty());
    assertTrue(actualInclusiveGateway.getAttributes().isEmpty());
    assertTrue(actualInclusiveGateway.getExtensionElements().isEmpty());
  }
}
