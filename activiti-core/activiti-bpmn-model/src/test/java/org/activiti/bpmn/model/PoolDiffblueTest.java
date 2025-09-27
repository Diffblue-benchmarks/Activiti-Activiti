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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class PoolDiffblueTest {
  /**
   * Test {@link Pool#clone()}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 42} is {@link ArrayList#ArrayList()}.
   *   <li>Then return Attributes size is one.
   * </ul>
   *
   * <p>Method under test: {@link Pool#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Pool Pool.clone()"})
  public void testClone_givenHashMap42IsArrayList_thenReturnAttributesSizeIsOne() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("42", new ArrayList<>());
    extensionElements.put("foo", new ArrayList<>());

    Pool pool = new Pool();
    pool.setExtensionElements(extensionElements);
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    pool.addAttribute(attribute);

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = pool.clone().getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link Pool#clone()}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code foo} is {@link ArrayList#ArrayList()}.
   *   <li>Then return Attributes size is one.
   * </ul>
   *
   * <p>Method under test: {@link Pool#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Pool Pool.clone()"})
  public void testClone_givenHashMapFooIsArrayList_thenReturnAttributesSizeIsOne() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("foo", new ArrayList<>());

    Pool pool = new Pool();
    pool.setExtensionElements(extensionElements);
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    pool.addAttribute(attribute);

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = pool.clone().getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link Pool#clone()}.
   *
   * <ul>
   *   <li>Given {@link Pool} (default constructor) Executable is {@code false}.
   *   <li>Then return not Executable.
   * </ul>
   *
   * <p>Method under test: {@link Pool#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Pool Pool.clone()"})
  public void testClone_givenPoolExecutableIsFalse_thenReturnNotExecutable() {
    // Arrange
    Pool pool = new Pool();
    pool.setExecutable(false);
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    pool.addAttribute(attribute);

    // Act
    Pool actualCloneResult = pool.clone();

    // Assert
    Map<String, List<ExtensionAttribute>> attributes = actualCloneResult.getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertFalse(actualCloneResult.isExecutable());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link Pool#clone()}.
   *
   * <ul>
   *   <li>Given {@link Pool} (default constructor) ExtensionElements is {@code null}.
   *   <li>Then return Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Pool#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Pool Pool.clone()"})
  public void testClone_givenPoolExtensionElementsIsNull_thenReturnIdIsNull() {
    // Arrange
    Pool pool = new Pool();
    pool.setExtensionElements(null);
    pool.setAttributes(null);

    // Act
    Pool actualCloneResult = pool.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getProcessRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExecutable());
  }

  /**
   * Test {@link Pool#clone()}.
   *
   * <ul>
   *   <li>Given {@link Pool} (default constructor).
   *   <li>Then return Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Pool#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Pool Pool.clone()"})
  public void testClone_givenPool_thenReturnIdIsNull() {
    // Arrange and Act
    Pool actualCloneResult = new Pool().clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getProcessRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExecutable());
  }

  /**
   * Test {@link Pool#clone()}.
   *
   * <ul>
   *   <li>Then return Attributes size is one.
   * </ul>
   *
   * <p>Method under test: {@link Pool#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Pool Pool.clone()"})
  public void testClone_thenReturnAttributesSizeIsOne() {
    // Arrange
    Pool pool = new Pool();
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    pool.addAttribute(attribute);

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = pool.clone().getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link Pool#clone()}.
   *
   * <ul>
   *   <li>Then return Attributes size is two.
   * </ul>
   *
   * <p>Method under test: {@link Pool#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Pool Pool.clone()"})
  public void testClone_thenReturnAttributesSizeIsTwo() {
    // Arrange
    Pool pool = new Pool();
    ExtensionAttribute attribute = new ExtensionAttribute("42");
    pool.addAttribute(attribute);
    pool.addAttribute(new ExtensionAttribute("Name"));

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = pool.clone().getAttributes();
    assertEquals(2, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("42");
    assertEquals(1, getResult.size());
    assertTrue(attributes.containsKey("Name"));
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link Pool}
   *   <li>{@link Pool#setExecutable(boolean)}
   *   <li>{@link Pool#setName(String)}
   *   <li>{@link Pool#setProcessRef(String)}
   *   <li>{@link Pool#getName()}
   *   <li>{@link Pool#getProcessRef()}
   *   <li>{@link Pool#isExecutable()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void Pool.<init>()",
    "String Pool.getName()",
    "String Pool.getProcessRef()",
    "boolean Pool.isExecutable()",
    "void Pool.setExecutable(boolean)",
    "void Pool.setName(String)",
    "void Pool.setProcessRef(String)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    Pool actualPool = new Pool();
    actualPool.setExecutable(true);
    actualPool.setName("Name");
    actualPool.setProcessRef("Process Ref");
    String actualName = actualPool.getName();
    String actualProcessRef = actualPool.getProcessRef();
    boolean actualIsExecutableResult = actualPool.isExecutable();

    // Assert
    assertEquals("Name", actualName);
    assertEquals("Process Ref", actualProcessRef);
    assertNull(actualPool.getId());
    assertEquals(0, actualPool.getXmlColumnNumber());
    assertEquals(0, actualPool.getXmlRowNumber());
    assertTrue(actualPool.getAttributes().isEmpty());
    assertTrue(actualPool.getExtensionElements().isEmpty());
    assertTrue(actualIsExecutableResult);
  }
}
