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

public class FieldExtensionDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link FieldExtension}
   *   <li>{@link FieldExtension#setExpression(String)}
   *   <li>{@link FieldExtension#setFieldName(String)}
   *   <li>{@link FieldExtension#setStringValue(String)}
   *   <li>{@link FieldExtension#getExpression()}
   *   <li>{@link FieldExtension#getFieldName()}
   *   <li>{@link FieldExtension#getStringValue()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void FieldExtension.<init>()",
    "String FieldExtension.getExpression()",
    "String FieldExtension.getFieldName()",
    "String FieldExtension.getStringValue()",
    "void FieldExtension.setExpression(String)",
    "void FieldExtension.setFieldName(String)",
    "void FieldExtension.setStringValue(String)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    FieldExtension actualFieldExtension = new FieldExtension();
    actualFieldExtension.setExpression("Expression");
    actualFieldExtension.setFieldName("Field Name");
    actualFieldExtension.setStringValue("42");
    String actualExpression = actualFieldExtension.getExpression();
    String actualFieldName = actualFieldExtension.getFieldName();

    // Assert
    assertEquals("42", actualFieldExtension.getStringValue());
    assertEquals("Expression", actualExpression);
    assertEquals("Field Name", actualFieldName);
    assertNull(actualFieldExtension.getId());
    assertEquals(0, actualFieldExtension.getXmlColumnNumber());
    assertEquals(0, actualFieldExtension.getXmlRowNumber());
    assertTrue(actualFieldExtension.getAttributes().isEmpty());
    assertTrue(actualFieldExtension.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link FieldExtension#clone()}.
   *
   * <p>Method under test: {@link FieldExtension#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FieldExtension FieldExtension.clone()"})
  public void testClone() {
    // Arrange and Act
    FieldExtension actualCloneResult = new FieldExtension().clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getExpression());
    assertNull(actualCloneResult.getFieldName());
    assertNull(actualCloneResult.getStringValue());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }
}
