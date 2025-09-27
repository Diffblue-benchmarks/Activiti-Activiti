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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class CancelEventDefinitionDiffblueTest {
  /**
   * Test {@link CancelEventDefinition#clone()}.
   *
   * <p>Method under test: {@link CancelEventDefinition#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"CancelEventDefinition CancelEventDefinition.clone()"})
  public void testClone() {
    // Arrange and Act
    CancelEventDefinition actualCloneResult = new CancelEventDefinition().clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test new {@link CancelEventDefinition} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link CancelEventDefinition}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CancelEventDefinition.<init>()"})
  public void testNewCancelEventDefinition() {
    // Arrange and Act
    CancelEventDefinition actualCancelEventDefinition = new CancelEventDefinition();

    // Assert
    assertNull(actualCancelEventDefinition.getId());
    assertEquals(0, actualCancelEventDefinition.getXmlColumnNumber());
    assertEquals(0, actualCancelEventDefinition.getXmlRowNumber());
    assertTrue(actualCancelEventDefinition.getAttributes().isEmpty());
    assertTrue(actualCancelEventDefinition.getExtensionElements().isEmpty());
  }
}
