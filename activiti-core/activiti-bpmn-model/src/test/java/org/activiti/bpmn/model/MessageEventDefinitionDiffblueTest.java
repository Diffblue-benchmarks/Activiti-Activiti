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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class MessageEventDefinitionDiffblueTest {
  /**
   * Test {@link MessageEventDefinition#clone()}.
   *
   * <p>Method under test: {@link MessageEventDefinition#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"MessageEventDefinition MessageEventDefinition.clone()"})
  public void testClone() {
    // Arrange and Act
    MessageEventDefinition actualCloneResult = new MessageEventDefinition().clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getCorrelationKey());
    assertNull(actualCloneResult.getMessageExpression());
    assertNull(actualCloneResult.getMessageRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getFieldExtensions().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link MessageEventDefinition}
   *   <li>{@link MessageEventDefinition#setCorrelationKey(String)}
   *   <li>{@link MessageEventDefinition#setFieldExtensions(List)}
   *   <li>{@link MessageEventDefinition#setMessageExpression(String)}
   *   <li>{@link MessageEventDefinition#setMessageRef(String)}
   *   <li>{@link MessageEventDefinition#getCorrelationKey()}
   *   <li>{@link MessageEventDefinition#getFieldExtensions()}
   *   <li>{@link MessageEventDefinition#getMessageExpression()}
   *   <li>{@link MessageEventDefinition#getMessageRef()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MessageEventDefinition.<init>()",
    "String MessageEventDefinition.getCorrelationKey()",
    "List MessageEventDefinition.getFieldExtensions()",
    "String MessageEventDefinition.getMessageExpression()",
    "String MessageEventDefinition.getMessageRef()",
    "void MessageEventDefinition.setCorrelationKey(String)",
    "void MessageEventDefinition.setFieldExtensions(List)",
    "void MessageEventDefinition.setMessageExpression(String)",
    "void MessageEventDefinition.setMessageRef(String)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    MessageEventDefinition actualMessageEventDefinition = new MessageEventDefinition();
    actualMessageEventDefinition.setCorrelationKey("Correlation Key");
    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    actualMessageEventDefinition.setFieldExtensions(fieldExtensions);
    actualMessageEventDefinition.setMessageExpression("Message Expression");
    actualMessageEventDefinition.setMessageRef("Message Ref");
    String actualCorrelationKey = actualMessageEventDefinition.getCorrelationKey();
    List<FieldExtension> actualFieldExtensions = actualMessageEventDefinition.getFieldExtensions();
    String actualMessageExpression = actualMessageEventDefinition.getMessageExpression();

    // Assert
    assertEquals("Correlation Key", actualCorrelationKey);
    assertEquals("Message Expression", actualMessageExpression);
    assertEquals("Message Ref", actualMessageEventDefinition.getMessageRef());
    assertNull(actualMessageEventDefinition.getId());
    assertEquals(0, actualMessageEventDefinition.getXmlColumnNumber());
    assertEquals(0, actualMessageEventDefinition.getXmlRowNumber());
    assertTrue(actualFieldExtensions.isEmpty());
    assertTrue(actualMessageEventDefinition.getAttributes().isEmpty());
    assertTrue(actualMessageEventDefinition.getExtensionElements().isEmpty());
    assertSame(fieldExtensions, actualFieldExtensions);
  }
}
