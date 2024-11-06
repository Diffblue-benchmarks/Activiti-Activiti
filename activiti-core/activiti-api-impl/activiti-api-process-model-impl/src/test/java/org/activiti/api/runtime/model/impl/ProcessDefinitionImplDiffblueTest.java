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
package org.activiti.api.runtime.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProcessDefinitionImplDiffblueTest {
  /**
   * Test {@link ProcessDefinitionImpl#equals(Object)}, and
   * {@link ProcessDefinitionImpl#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ProcessDefinitionImpl#equals(Object)}
   *   <li>{@link ProcessDefinitionImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ProcessDefinitionImpl processDefinitionImpl = new ProcessDefinitionImpl();
    processDefinitionImpl.setAppVersion("1.0.2");
    processDefinitionImpl.setCategory("Category");
    processDefinitionImpl.setDescription("The characteristics of someone or something");
    processDefinitionImpl.setFormKey("Form Key");
    processDefinitionImpl.setId("42");
    processDefinitionImpl.setKey("Key");
    processDefinitionImpl.setName("Name");
    processDefinitionImpl.setVersion(1);

    ProcessDefinitionImpl processDefinitionImpl2 = new ProcessDefinitionImpl();
    processDefinitionImpl2.setAppVersion("1.0.2");
    processDefinitionImpl2.setCategory("Category");
    processDefinitionImpl2.setDescription("The characteristics of someone or something");
    processDefinitionImpl2.setFormKey("Form Key");
    processDefinitionImpl2.setId("42");
    processDefinitionImpl2.setKey("Key");
    processDefinitionImpl2.setName("Name");
    processDefinitionImpl2.setVersion(1);

    // Act and Assert
    assertEquals(processDefinitionImpl, processDefinitionImpl2);
    int expectedHashCodeResult = processDefinitionImpl.hashCode();
    assertEquals(expectedHashCodeResult, processDefinitionImpl2.hashCode());
  }

  /**
   * Test {@link ProcessDefinitionImpl#equals(Object)}, and
   * {@link ProcessDefinitionImpl#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ProcessDefinitionImpl#equals(Object)}
   *   <li>{@link ProcessDefinitionImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ProcessDefinitionImpl processDefinitionImpl = new ProcessDefinitionImpl();
    processDefinitionImpl.setAppVersion("1.0.2");
    processDefinitionImpl.setCategory("Category");
    processDefinitionImpl.setDescription("The characteristics of someone or something");
    processDefinitionImpl.setFormKey("Form Key");
    processDefinitionImpl.setId("42");
    processDefinitionImpl.setKey("Key");
    processDefinitionImpl.setName("Name");
    processDefinitionImpl.setVersion(1);

    // Act and Assert
    assertEquals(processDefinitionImpl, processDefinitionImpl);
    int expectedHashCodeResult = processDefinitionImpl.hashCode();
    assertEquals(expectedHashCodeResult, processDefinitionImpl.hashCode());
  }

  /**
   * Test {@link ProcessDefinitionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ProcessDefinitionImpl processDefinitionImpl = new ProcessDefinitionImpl();
    processDefinitionImpl.setAppVersion("App Version");
    processDefinitionImpl.setCategory("Category");
    processDefinitionImpl.setDescription("The characteristics of someone or something");
    processDefinitionImpl.setFormKey("Form Key");
    processDefinitionImpl.setId("42");
    processDefinitionImpl.setKey("Key");
    processDefinitionImpl.setName("Name");
    processDefinitionImpl.setVersion(1);

    ProcessDefinitionImpl processDefinitionImpl2 = new ProcessDefinitionImpl();
    processDefinitionImpl2.setAppVersion("1.0.2");
    processDefinitionImpl2.setCategory("Category");
    processDefinitionImpl2.setDescription("The characteristics of someone or something");
    processDefinitionImpl2.setFormKey("Form Key");
    processDefinitionImpl2.setId("42");
    processDefinitionImpl2.setKey("Key");
    processDefinitionImpl2.setName("Name");
    processDefinitionImpl2.setVersion(1);

    // Act and Assert
    assertNotEquals(processDefinitionImpl, processDefinitionImpl2);
  }

  /**
   * Test {@link ProcessDefinitionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ProcessDefinitionImpl processDefinitionImpl = new ProcessDefinitionImpl();
    processDefinitionImpl.setAppVersion("1.0.2");
    processDefinitionImpl.setCategory("Category");
    processDefinitionImpl.setDescription("Description");
    processDefinitionImpl.setFormKey("Form Key");
    processDefinitionImpl.setId("42");
    processDefinitionImpl.setKey("Key");
    processDefinitionImpl.setName("Name");
    processDefinitionImpl.setVersion(1);

    ProcessDefinitionImpl processDefinitionImpl2 = new ProcessDefinitionImpl();
    processDefinitionImpl2.setAppVersion("1.0.2");
    processDefinitionImpl2.setCategory("Category");
    processDefinitionImpl2.setDescription("The characteristics of someone or something");
    processDefinitionImpl2.setFormKey("Form Key");
    processDefinitionImpl2.setId("42");
    processDefinitionImpl2.setKey("Key");
    processDefinitionImpl2.setName("Name");
    processDefinitionImpl2.setVersion(1);

    // Act and Assert
    assertNotEquals(processDefinitionImpl, processDefinitionImpl2);
  }

  /**
   * Test {@link ProcessDefinitionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    ProcessDefinitionImpl processDefinitionImpl = new ProcessDefinitionImpl();
    processDefinitionImpl.setAppVersion("1.0.2");
    processDefinitionImpl.setCategory("Category");
    processDefinitionImpl.setDescription("The characteristics of someone or something");
    processDefinitionImpl.setFormKey(null);
    processDefinitionImpl.setId("42");
    processDefinitionImpl.setKey("Key");
    processDefinitionImpl.setName("Name");
    processDefinitionImpl.setVersion(1);

    ProcessDefinitionImpl processDefinitionImpl2 = new ProcessDefinitionImpl();
    processDefinitionImpl2.setAppVersion("1.0.2");
    processDefinitionImpl2.setCategory("Category");
    processDefinitionImpl2.setDescription("The characteristics of someone or something");
    processDefinitionImpl2.setFormKey("Form Key");
    processDefinitionImpl2.setId("42");
    processDefinitionImpl2.setKey("Key");
    processDefinitionImpl2.setName("Name");
    processDefinitionImpl2.setVersion(1);

    // Act and Assert
    assertNotEquals(processDefinitionImpl, processDefinitionImpl2);
  }

  /**
   * Test {@link ProcessDefinitionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    ProcessDefinitionImpl processDefinitionImpl = new ProcessDefinitionImpl();
    processDefinitionImpl.setAppVersion("1.0.2");
    processDefinitionImpl.setCategory("Category");
    processDefinitionImpl.setDescription("The characteristics of someone or something");
    processDefinitionImpl.setFormKey("Form Key");
    processDefinitionImpl.setId("Id");
    processDefinitionImpl.setKey("Key");
    processDefinitionImpl.setName("Name");
    processDefinitionImpl.setVersion(1);

    ProcessDefinitionImpl processDefinitionImpl2 = new ProcessDefinitionImpl();
    processDefinitionImpl2.setAppVersion("1.0.2");
    processDefinitionImpl2.setCategory("Category");
    processDefinitionImpl2.setDescription("The characteristics of someone or something");
    processDefinitionImpl2.setFormKey("Form Key");
    processDefinitionImpl2.setId("42");
    processDefinitionImpl2.setKey("Key");
    processDefinitionImpl2.setName("Name");
    processDefinitionImpl2.setVersion(1);

    // Act and Assert
    assertNotEquals(processDefinitionImpl, processDefinitionImpl2);
  }

  /**
   * Test {@link ProcessDefinitionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    ProcessDefinitionImpl processDefinitionImpl = new ProcessDefinitionImpl();
    processDefinitionImpl.setAppVersion("1.0.2");
    processDefinitionImpl.setCategory("Category");
    processDefinitionImpl.setDescription("The characteristics of someone or something");
    processDefinitionImpl.setFormKey("Form Key");
    processDefinitionImpl.setId("42");
    processDefinitionImpl.setKey(null);
    processDefinitionImpl.setName("Name");
    processDefinitionImpl.setVersion(1);

    ProcessDefinitionImpl processDefinitionImpl2 = new ProcessDefinitionImpl();
    processDefinitionImpl2.setAppVersion("1.0.2");
    processDefinitionImpl2.setCategory("Category");
    processDefinitionImpl2.setDescription("The characteristics of someone or something");
    processDefinitionImpl2.setFormKey("Form Key");
    processDefinitionImpl2.setId("42");
    processDefinitionImpl2.setKey("Key");
    processDefinitionImpl2.setName("Name");
    processDefinitionImpl2.setVersion(1);

    // Act and Assert
    assertNotEquals(processDefinitionImpl, processDefinitionImpl2);
  }

  /**
   * Test {@link ProcessDefinitionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    ProcessDefinitionImpl processDefinitionImpl = new ProcessDefinitionImpl();
    processDefinitionImpl.setAppVersion("1.0.2");
    processDefinitionImpl.setCategory("Category");
    processDefinitionImpl.setDescription("The characteristics of someone or something");
    processDefinitionImpl.setFormKey("Form Key");
    processDefinitionImpl.setId("42");
    processDefinitionImpl.setKey("Key");
    processDefinitionImpl.setName(null);
    processDefinitionImpl.setVersion(1);

    ProcessDefinitionImpl processDefinitionImpl2 = new ProcessDefinitionImpl();
    processDefinitionImpl2.setAppVersion("1.0.2");
    processDefinitionImpl2.setCategory("Category");
    processDefinitionImpl2.setDescription("The characteristics of someone or something");
    processDefinitionImpl2.setFormKey("Form Key");
    processDefinitionImpl2.setId("42");
    processDefinitionImpl2.setKey("Key");
    processDefinitionImpl2.setName("Name");
    processDefinitionImpl2.setVersion(1);

    // Act and Assert
    assertNotEquals(processDefinitionImpl, processDefinitionImpl2);
  }

  /**
   * Test {@link ProcessDefinitionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    ProcessDefinitionImpl processDefinitionImpl = new ProcessDefinitionImpl();
    processDefinitionImpl.setAppVersion("1.0.2");
    processDefinitionImpl.setCategory("Category");
    processDefinitionImpl.setDescription("The characteristics of someone or something");
    processDefinitionImpl.setFormKey("Form Key");
    processDefinitionImpl.setId("42");
    processDefinitionImpl.setKey("Key");
    processDefinitionImpl.setName("Name");
    processDefinitionImpl.setVersion(0);

    ProcessDefinitionImpl processDefinitionImpl2 = new ProcessDefinitionImpl();
    processDefinitionImpl2.setAppVersion("1.0.2");
    processDefinitionImpl2.setCategory("Category");
    processDefinitionImpl2.setDescription("The characteristics of someone or something");
    processDefinitionImpl2.setFormKey("Form Key");
    processDefinitionImpl2.setId("42");
    processDefinitionImpl2.setKey("Key");
    processDefinitionImpl2.setName("Name");
    processDefinitionImpl2.setVersion(1);

    // Act and Assert
    assertNotEquals(processDefinitionImpl, processDefinitionImpl2);
  }

  /**
   * Test {@link ProcessDefinitionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    ProcessDefinitionImpl processDefinitionImpl = new ProcessDefinitionImpl();
    processDefinitionImpl.setAppVersion("1.0.2");
    processDefinitionImpl.setCategory("Category");
    processDefinitionImpl.setDescription("The characteristics of someone or something");
    processDefinitionImpl.setFormKey("Form Key");
    processDefinitionImpl.setId("42");
    processDefinitionImpl.setKey("Key");
    processDefinitionImpl.setName("Name");
    processDefinitionImpl.setVersion(1);

    // Act and Assert
    assertNotEquals(processDefinitionImpl, null);
  }

  /**
   * Test {@link ProcessDefinitionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    ProcessDefinitionImpl processDefinitionImpl = new ProcessDefinitionImpl();
    processDefinitionImpl.setAppVersion("1.0.2");
    processDefinitionImpl.setCategory("Category");
    processDefinitionImpl.setDescription("The characteristics of someone or something");
    processDefinitionImpl.setFormKey("Form Key");
    processDefinitionImpl.setId("42");
    processDefinitionImpl.setKey("Key");
    processDefinitionImpl.setName("Name");
    processDefinitionImpl.setVersion(1);

    // Act and Assert
    assertNotEquals(processDefinitionImpl, "Different type to ProcessDefinitionImpl");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ProcessDefinitionImpl}
   *   <li>{@link ProcessDefinitionImpl#setCategory(String)}
   *   <li>{@link ProcessDefinitionImpl#setDescription(String)}
   *   <li>{@link ProcessDefinitionImpl#setFormKey(String)}
   *   <li>{@link ProcessDefinitionImpl#setId(String)}
   *   <li>{@link ProcessDefinitionImpl#setKey(String)}
   *   <li>{@link ProcessDefinitionImpl#setName(String)}
   *   <li>{@link ProcessDefinitionImpl#setVersion(int)}
   *   <li>{@link ProcessDefinitionImpl#toString()}
   *   <li>{@link ProcessDefinitionImpl#getCategory()}
   *   <li>{@link ProcessDefinitionImpl#getDescription()}
   *   <li>{@link ProcessDefinitionImpl#getFormKey()}
   *   <li>{@link ProcessDefinitionImpl#getId()}
   *   <li>{@link ProcessDefinitionImpl#getKey()}
   *   <li>{@link ProcessDefinitionImpl#getName()}
   *   <li>{@link ProcessDefinitionImpl#getVersion()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    ProcessDefinitionImpl actualProcessDefinitionImpl = new ProcessDefinitionImpl();
    actualProcessDefinitionImpl.setCategory("Category");
    actualProcessDefinitionImpl.setDescription("The characteristics of someone or something");
    actualProcessDefinitionImpl.setFormKey("Form Key");
    actualProcessDefinitionImpl.setId("42");
    actualProcessDefinitionImpl.setKey("Key");
    actualProcessDefinitionImpl.setName("Name");
    actualProcessDefinitionImpl.setVersion(1);
    String actualToStringResult = actualProcessDefinitionImpl.toString();
    String actualCategory = actualProcessDefinitionImpl.getCategory();
    String actualDescription = actualProcessDefinitionImpl.getDescription();
    String actualFormKey = actualProcessDefinitionImpl.getFormKey();
    String actualId = actualProcessDefinitionImpl.getId();
    String actualKey = actualProcessDefinitionImpl.getKey();
    String actualName = actualProcessDefinitionImpl.getName();

    // Assert that nothing has changed
    assertEquals("42", actualId);
    assertEquals("Category", actualCategory);
    assertEquals("Form Key", actualFormKey);
    assertEquals("Key", actualKey);
    assertEquals("Name", actualName);
    assertEquals("ProcessDefinition{id='42', name='Name', key='Key', description='The characteristics of someone or"
        + " something', formKey='Form Key', version=1}", actualToStringResult);
    assertEquals("The characteristics of someone or something", actualDescription);
    assertEquals(1, actualProcessDefinitionImpl.getVersion());
  }
}
