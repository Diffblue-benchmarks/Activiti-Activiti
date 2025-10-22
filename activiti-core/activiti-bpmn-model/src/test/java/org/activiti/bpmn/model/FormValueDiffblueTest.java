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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class FormValueDiffblueTest {
  /**
   * Test {@link FormValue#clone()}.
   * <ul>
   *   <li>Given {@link FormValue} (default constructor) ExtensionElements is {@code null}.</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FormValue#clone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"FormValue FormValue.clone()"})
  public void testClone_givenFormValueExtensionElementsIsNull_thenReturnIdIsNull() {
    // Arrange
    FormValue formValue = new FormValue();
    formValue.setExtensionElements(null);
    formValue.setAttributes(null);

    // Act
    FormValue actualCloneResult = formValue.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getName());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link FormValue#clone()}.
   * <ul>
   *   <li>Given {@link FormValue} (default constructor).</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FormValue#clone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"FormValue FormValue.clone()"})
  public void testClone_givenFormValue_thenReturnIdIsNull() {
    // Arrange and Act
    FormValue actualCloneResult = (new FormValue()).clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getName());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link FormValue#clone()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 42} is {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Attributes size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link FormValue#clone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"FormValue FormValue.clone()"})
  public void testClone_givenHashMap42IsArrayList_thenReturnAttributesSizeIsOne() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("42", new ArrayList<>());
    extensionElements.put("foo", new ArrayList<>());

    FormValue formValue = new FormValue();
    formValue.setExtensionElements(extensionElements);
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    formValue.addAttribute(attribute);

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = formValue.clone().getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link FormValue#clone()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code foo} is {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Attributes size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link FormValue#clone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"FormValue FormValue.clone()"})
  public void testClone_givenHashMapFooIsArrayList_thenReturnAttributesSizeIsOne() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("foo", new ArrayList<>());

    FormValue formValue = new FormValue();
    formValue.setExtensionElements(extensionElements);
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    formValue.addAttribute(attribute);

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = formValue.clone().getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link FormValue#clone()}.
   * <ul>
   *   <li>Then return Attributes size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link FormValue#clone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"FormValue FormValue.clone()"})
  public void testClone_thenReturnAttributesSizeIsOne() {
    // Arrange
    FormValue formValue = new FormValue();
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    formValue.addAttribute(attribute);

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = formValue.clone().getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link FormValue#clone()}.
   * <ul>
   *   <li>Then return Attributes size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link FormValue#clone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"FormValue FormValue.clone()"})
  public void testClone_thenReturnAttributesSizeIsTwo() {
    // Arrange
    FormValue formValue = new FormValue();
    ExtensionAttribute attribute = new ExtensionAttribute("42");
    formValue.addAttribute(attribute);
    formValue.addAttribute(new ExtensionAttribute("Name"));

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = formValue.clone().getAttributes();
    assertEquals(2, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("42");
    assertEquals(1, getResult.size());
    assertTrue(attributes.containsKey("Name"));
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link FormValue}
   *   <li>{@link FormValue#setName(String)}
   *   <li>{@link FormValue#getName()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void FormValue.<init>()", "String FormValue.getName()", "void FormValue.setName(String)"})
  public void testGettersAndSetters() {
    // Arrange and Act
    FormValue actualFormValue = new FormValue();
    actualFormValue.setName("Name");

    // Assert
    assertEquals("Name", actualFormValue.getName());
    assertNull(actualFormValue.getId());
    assertEquals(0, actualFormValue.getXmlColumnNumber());
    assertEquals(0, actualFormValue.getXmlRowNumber());
    assertTrue(actualFormValue.getAttributes().isEmpty());
    assertTrue(actualFormValue.getExtensionElements().isEmpty());
  }
}
