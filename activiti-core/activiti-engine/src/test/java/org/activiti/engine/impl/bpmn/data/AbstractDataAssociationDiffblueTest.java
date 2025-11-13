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
package org.activiti.engine.impl.bpmn.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AbstractDataAssociationDiffblueTest {
  /**
   * Test {@link AbstractDataAssociation#getSource()}.
   *
   * <p>Method under test: {@link AbstractDataAssociation#getSource()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String AbstractDataAssociation.getSource()"})
  public void testGetSource() {
    // Arrange, Act and Assert
    assertEquals("Source", new SimpleDataInputAssociation("Source", "Target").getSource());
  }

  /**
   * Test {@link AbstractDataAssociation#getTarget()}.
   *
   * <p>Method under test: {@link AbstractDataAssociation#getTarget()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String AbstractDataAssociation.getTarget()"})
  public void testGetTarget() {
    // Arrange, Act and Assert
    assertEquals("Target", new SimpleDataInputAssociation("Source", "Target").getTarget());
  }

  /**
   * Test {@link AbstractDataAssociation#getSourceExpression()}.
   *
   * <p>Method under test: {@link AbstractDataAssociation#getSourceExpression()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.delegate.Expression AbstractDataAssociation.getSourceExpression()"
  })
  public void testGetSourceExpression() {
    // Arrange, Act and Assert
    assertNull(new SimpleDataInputAssociation("Source", "Target").getSourceExpression());
  }
}
