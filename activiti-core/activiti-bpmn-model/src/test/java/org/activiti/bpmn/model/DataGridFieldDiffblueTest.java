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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DataGridFieldDiffblueTest {
  /**
   * Test {@link DataGridField#clone()}.
   * <p>
   * Method under test: {@link DataGridField#clone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DataGridField DataGridField.clone()"})
  public void testClone() {
    // Arrange and Act
    DataGridField actualCloneResult = (new DataGridField()).clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getValue());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link DataGridField}
   *   <li>{@link DataGridField#setName(String)}
   *   <li>{@link DataGridField#setValue(String)}
   *   <li>{@link DataGridField#getName()}
   *   <li>{@link DataGridField#getValue()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DataGridField.<init>()", "String DataGridField.getName()", "String DataGridField.getValue()",
      "void DataGridField.setName(String)", "void DataGridField.setValue(String)"})
  public void testGettersAndSetters() {
    // Arrange and Act
    DataGridField actualDataGridField = new DataGridField();
    actualDataGridField.setName("Name");
    actualDataGridField.setValue("42");
    String actualName = actualDataGridField.getName();

    // Assert
    assertEquals("42", actualDataGridField.getValue());
    assertEquals("Name", actualName);
    assertNull(actualDataGridField.getId());
    assertEquals(0, actualDataGridField.getXmlColumnNumber());
    assertEquals(0, actualDataGridField.getXmlRowNumber());
    assertTrue(actualDataGridField.getAttributes().isEmpty());
    assertTrue(actualDataGridField.getExtensionElements().isEmpty());
  }
}
