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
package org.activiti.engine.impl.bpmn.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class FieldDeclarationDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link FieldDeclaration#FieldDeclaration()}
   *   <li>{@link FieldDeclaration#setName(String)}
   *   <li>{@link FieldDeclaration#setType(String)}
   *   <li>{@link FieldDeclaration#setValue(Object)}
   *   <li>{@link FieldDeclaration#getName()}
   *   <li>{@link FieldDeclaration#getType()}
   *   <li>{@link FieldDeclaration#getValue()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void FieldDeclaration.<init>()", "void FieldDeclaration.<init>(String, String, Object)",
      "String FieldDeclaration.getName()", "String FieldDeclaration.getType()", "Object FieldDeclaration.getValue()",
      "void FieldDeclaration.setName(String)", "void FieldDeclaration.setType(String)",
      "void FieldDeclaration.setValue(Object)"})
  public void testGettersAndSetters() {
    // Arrange and Act
    FieldDeclaration actualFieldDeclaration = new FieldDeclaration();
    actualFieldDeclaration.setName("Name");
    actualFieldDeclaration.setType("Type");
    Object object = JSONObject.NULL;
    actualFieldDeclaration.setValue(object);
    String actualName = actualFieldDeclaration.getName();
    String actualType = actualFieldDeclaration.getType();

    // Assert
    assertEquals("Name", actualName);
    assertEquals("Type", actualType);
    assertSame(object, actualFieldDeclaration.getValue());
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code Name}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link FieldDeclaration#FieldDeclaration(String, String, Object)}
   *   <li>{@link FieldDeclaration#setName(String)}
   *   <li>{@link FieldDeclaration#setType(String)}
   *   <li>{@link FieldDeclaration#setValue(Object)}
   *   <li>{@link FieldDeclaration#getName()}
   *   <li>{@link FieldDeclaration#getType()}
   *   <li>{@link FieldDeclaration#getValue()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void FieldDeclaration.<init>()", "void FieldDeclaration.<init>(String, String, Object)",
      "String FieldDeclaration.getName()", "String FieldDeclaration.getType()", "Object FieldDeclaration.getValue()",
      "void FieldDeclaration.setName(String)", "void FieldDeclaration.setType(String)",
      "void FieldDeclaration.setValue(Object)"})
  public void testGettersAndSetters_whenName() {
    // Arrange and Act
    FieldDeclaration actualFieldDeclaration = new FieldDeclaration("Name", "Type", JSONObject.NULL);
    actualFieldDeclaration.setName("Name");
    actualFieldDeclaration.setType("Type");
    Object object = JSONObject.NULL;
    actualFieldDeclaration.setValue(object);
    String actualName = actualFieldDeclaration.getName();
    String actualType = actualFieldDeclaration.getType();

    // Assert
    assertEquals("Name", actualName);
    assertEquals("Type", actualType);
    assertSame(object, actualFieldDeclaration.getValue());
  }
}
