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
import org.junit.Test;

public class ExtensionAttributeDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ExtensionAttribute#ExtensionAttribute()}
   *   <li>{@link ExtensionAttribute#setName(String)}
   *   <li>{@link ExtensionAttribute#setNamespace(String)}
   *   <li>{@link ExtensionAttribute#setNamespacePrefix(String)}
   *   <li>{@link ExtensionAttribute#setValue(String)}
   *   <li>{@link ExtensionAttribute#getName()}
   *   <li>{@link ExtensionAttribute#getNamespace()}
   *   <li>{@link ExtensionAttribute#getNamespacePrefix()}
   *   <li>{@link ExtensionAttribute#getValue()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    ExtensionAttribute actualExtensionAttribute = new ExtensionAttribute();
    actualExtensionAttribute.setName("Name");
    actualExtensionAttribute.setNamespace("Namespace");
    actualExtensionAttribute.setNamespacePrefix("Namespace Prefix");
    actualExtensionAttribute.setValue("42");
    String actualName = actualExtensionAttribute.getName();
    String actualNamespace = actualExtensionAttribute.getNamespace();
    String actualNamespacePrefix = actualExtensionAttribute.getNamespacePrefix();

    // Assert that nothing has changed
    assertEquals("42", actualExtensionAttribute.getValue());
    assertEquals("Name", actualName);
    assertEquals("Namespace Prefix", actualNamespacePrefix);
    assertEquals("Namespace", actualNamespace);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code Name}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ExtensionAttribute#ExtensionAttribute(String)}
   *   <li>{@link ExtensionAttribute#setName(String)}
   *   <li>{@link ExtensionAttribute#setNamespace(String)}
   *   <li>{@link ExtensionAttribute#setNamespacePrefix(String)}
   *   <li>{@link ExtensionAttribute#setValue(String)}
   *   <li>{@link ExtensionAttribute#getName()}
   *   <li>{@link ExtensionAttribute#getNamespace()}
   *   <li>{@link ExtensionAttribute#getNamespacePrefix()}
   *   <li>{@link ExtensionAttribute#getValue()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_whenName() {
    // Arrange and Act
    ExtensionAttribute actualExtensionAttribute = new ExtensionAttribute("Name");
    actualExtensionAttribute.setName("Name");
    actualExtensionAttribute.setNamespace("Namespace");
    actualExtensionAttribute.setNamespacePrefix("Namespace Prefix");
    actualExtensionAttribute.setValue("42");
    String actualName = actualExtensionAttribute.getName();
    String actualNamespace = actualExtensionAttribute.getNamespace();
    String actualNamespacePrefix = actualExtensionAttribute.getNamespacePrefix();

    // Assert that nothing has changed
    assertEquals("42", actualExtensionAttribute.getValue());
    assertEquals("Name", actualName);
    assertEquals("Namespace Prefix", actualNamespacePrefix);
    assertEquals("Namespace", actualNamespace);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code Namespace}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ExtensionAttribute#ExtensionAttribute(String, String)}
   *   <li>{@link ExtensionAttribute#setName(String)}
   *   <li>{@link ExtensionAttribute#setNamespace(String)}
   *   <li>{@link ExtensionAttribute#setNamespacePrefix(String)}
   *   <li>{@link ExtensionAttribute#setValue(String)}
   *   <li>{@link ExtensionAttribute#getName()}
   *   <li>{@link ExtensionAttribute#getNamespace()}
   *   <li>{@link ExtensionAttribute#getNamespacePrefix()}
   *   <li>{@link ExtensionAttribute#getValue()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_whenNamespace() {
    // Arrange and Act
    ExtensionAttribute actualExtensionAttribute = new ExtensionAttribute("Namespace", "Name");
    actualExtensionAttribute.setName("Name");
    actualExtensionAttribute.setNamespace("Namespace");
    actualExtensionAttribute.setNamespacePrefix("Namespace Prefix");
    actualExtensionAttribute.setValue("42");
    String actualName = actualExtensionAttribute.getName();
    String actualNamespace = actualExtensionAttribute.getNamespace();
    String actualNamespacePrefix = actualExtensionAttribute.getNamespacePrefix();

    // Assert that nothing has changed
    assertEquals("42", actualExtensionAttribute.getValue());
    assertEquals("Name", actualName);
    assertEquals("Namespace Prefix", actualNamespacePrefix);
    assertEquals("Namespace", actualNamespace);
  }

  /**
   * Test {@link ExtensionAttribute#toString()}.
   * <ul>
   *   <li>Given {@link ExtensionAttribute#ExtensionAttribute(String)} with
   * {@code Name} Name is {@code foo}.</li>
   *   <li>Then return {@code foo:foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExtensionAttribute#toString()}
   */
  @Test
  public void testToString_givenExtensionAttributeWithNameNameIsFoo_thenReturnFooFoo() {
    // Arrange
    ExtensionAttribute extensionAttribute = new ExtensionAttribute("Name");
    extensionAttribute.setNamespacePrefix("foo");
    extensionAttribute.setName("foo");
    extensionAttribute.setValue(null);

    // Act and Assert
    assertEquals("foo:foo", extensionAttribute.toString());
  }

  /**
   * Test {@link ExtensionAttribute#toString()}.
   * <ul>
   *   <li>Given {@link ExtensionAttribute#ExtensionAttribute(String)} with
   * {@code Name} NamespacePrefix is {@code foo}.</li>
   *   <li>Then return {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExtensionAttribute#toString()}
   */
  @Test
  public void testToString_givenExtensionAttributeWithNameNamespacePrefixIsFoo_thenReturnFoo() {
    // Arrange
    ExtensionAttribute extensionAttribute = new ExtensionAttribute("Name");
    extensionAttribute.setNamespacePrefix("foo");
    extensionAttribute.setName(null);
    extensionAttribute.setValue(null);

    // Act and Assert
    assertEquals("foo", extensionAttribute.toString());
  }

  /**
   * Test {@link ExtensionAttribute#toString()}.
   * <ul>
   *   <li>Given {@link ExtensionAttribute#ExtensionAttribute(String)} with
   * {@code Name}.</li>
   *   <li>Then return {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExtensionAttribute#toString()}
   */
  @Test
  public void testToString_givenExtensionAttributeWithName_thenReturnName() {
    // Arrange, Act and Assert
    assertEquals("Name", (new ExtensionAttribute("Name")).toString());
  }

  /**
   * Test {@link ExtensionAttribute#toString()}.
   * <ul>
   *   <li>Then return {@code null=foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExtensionAttribute#toString()}
   */
  @Test
  public void testToString_thenReturnNullFoo() {
    // Arrange
    ExtensionAttribute extensionAttribute = new ExtensionAttribute("Name");
    extensionAttribute.setNamespacePrefix(null);
    extensionAttribute.setName(null);
    extensionAttribute.setValue("foo");

    // Act and Assert
    assertEquals("null=foo", extensionAttribute.toString());
  }

  /**
   * Test {@link ExtensionAttribute#clone()}.
   * <p>
   * Method under test: {@link ExtensionAttribute#clone()}
   */
  @Test
  public void testClone() {
    // Arrange and Act
    ExtensionAttribute actualCloneResult = (new ExtensionAttribute("Name")).clone();

    // Assert
    assertEquals("Name", actualCloneResult.getName());
    assertNull(actualCloneResult.getNamespace());
    assertNull(actualCloneResult.getNamespacePrefix());
    assertNull(actualCloneResult.getValue());
  }
}
