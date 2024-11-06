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
package org.activiti.engine.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class DataObjectImplDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link DataObjectImpl#DataObjectImpl(String, Object, String, String, String, String, String)}
   *   <li>{@link DataObjectImpl#setDataObjectDefinitionKey(String)}
   *   <li>{@link DataObjectImpl#setDescription(String)}
   *   <li>{@link DataObjectImpl#setLocalizedName(String)}
   *   <li>{@link DataObjectImpl#setName(String)}
   *   <li>{@link DataObjectImpl#setType(String)}
   *   <li>{@link DataObjectImpl#setValue(Object)}
   *   <li>{@link DataObjectImpl#getDataObjectDefinitionKey()}
   *   <li>{@link DataObjectImpl#getName()}
   *   <li>{@link DataObjectImpl#getType()}
   *   <li>{@link DataObjectImpl#getValue()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    DataObjectImpl actualDataObjectImpl = new DataObjectImpl("Name", JSONObject.NULL,
        "The characteristics of someone or something", "Type", "Localized Name", "Localized Description",
        "Data Object Definition Key");
    actualDataObjectImpl.setDataObjectDefinitionKey("Data Object Definition Key");
    actualDataObjectImpl.setDescription("The characteristics of someone or something");
    actualDataObjectImpl.setLocalizedName("Localized Name");
    actualDataObjectImpl.setName("Name");
    actualDataObjectImpl.setType("Type");
    Object object = JSONObject.NULL;
    actualDataObjectImpl.setValue(object);
    String actualDataObjectDefinitionKey = actualDataObjectImpl.getDataObjectDefinitionKey();
    String actualName = actualDataObjectImpl.getName();
    String actualType = actualDataObjectImpl.getType();

    // Assert that nothing has changed
    assertEquals("Data Object Definition Key", actualDataObjectDefinitionKey);
    assertEquals("Name", actualName);
    assertEquals("Type", actualType);
    assertSame(object, actualDataObjectImpl.getValue());
  }

  /**
   * Test {@link DataObjectImpl#getLocalizedName()}.
   * <p>
   * Method under test: {@link DataObjectImpl#getLocalizedName()}
   */
  @Test
  public void testGetLocalizedName() {
    // Arrange
    DataObjectImpl dataObjectImpl = new DataObjectImpl("Name", JSONObject.NULL,
        "The characteristics of someone or something", "Type", "Localized Name", "Localized Description",
        "Data Object Definition Key");
    dataObjectImpl.setLocalizedName(null);

    // Act and Assert
    assertEquals("Name", dataObjectImpl.getLocalizedName());
  }

  /**
   * Test {@link DataObjectImpl#getLocalizedName()}.
   * <p>
   * Method under test: {@link DataObjectImpl#getLocalizedName()}
   */
  @Test
  public void testGetLocalizedName2() {
    // Arrange, Act and Assert
    assertEquals("Name", (new DataObjectImpl("Name", JSONObject.NULL, "The characteristics of someone or something",
        "Type", "", "Localized Description", "Data Object Definition Key")).getLocalizedName());
  }

  /**
   * Test {@link DataObjectImpl#getLocalizedName()}.
   * <ul>
   *   <li>Then return {@code Localized Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataObjectImpl#getLocalizedName()}
   */
  @Test
  public void testGetLocalizedName_thenReturnLocalizedName() {
    // Arrange, Act and Assert
    assertEquals("Localized Name",
        (new DataObjectImpl("Name", JSONObject.NULL, "The characteristics of someone or something", "Type",
            "Localized Name", "Localized Description", "Data Object Definition Key")).getLocalizedName());
  }

  /**
   * Test {@link DataObjectImpl#getDescription()}.
   * <p>
   * Method under test: {@link DataObjectImpl#getDescription()}
   */
  @Test
  public void testGetDescription() {
    // Arrange, Act and Assert
    assertEquals("The characteristics of someone or something",
        (new DataObjectImpl("Name", JSONObject.NULL, "The characteristics of someone or something", "Type",
            "Localized Name", null, "Data Object Definition Key")).getDescription());
    assertEquals("The characteristics of someone or something",
        (new DataObjectImpl("Name", JSONObject.NULL, "The characteristics of someone or something", "Type",
            "Localized Name", "", "Data Object Definition Key")).getDescription());
  }

  /**
   * Test {@link DataObjectImpl#getDescription()}.
   * <ul>
   *   <li>Then return {@code Localized Description}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataObjectImpl#getDescription()}
   */
  @Test
  public void testGetDescription_thenReturnLocalizedDescription() {
    // Arrange, Act and Assert
    assertEquals("Localized Description",
        (new DataObjectImpl("Name", JSONObject.NULL, "The characteristics of someone or something", "Type",
            "Localized Name", "Localized Description", "Data Object Definition Key")).getDescription());
  }
}
