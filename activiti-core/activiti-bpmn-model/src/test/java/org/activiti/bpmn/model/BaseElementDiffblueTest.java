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
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class BaseElementDiffblueTest {
  /**
   * Test {@link BaseElement#getId()}.
   *
   * <p>Method under test: {@link BaseElement#getId()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String BaseElement.getId()"})
  public void testGetId() {
    // Arrange, Act and Assert
    assertNull(new ActivitiListener().getId());
  }

  /**
   * Test {@link BaseElement#setId(String)}.
   *
   * <p>Method under test: {@link BaseElement#setId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseElement.setId(String)"})
  public void testSetId() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    // Act
    activitiListener.setId("42");

    // Assert
    assertEquals("42", activitiListener.getId());
  }

  /**
   * Test {@link BaseElement#getXmlRowNumber()}.
   *
   * <p>Method under test: {@link BaseElement#getXmlRowNumber()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int BaseElement.getXmlRowNumber()"})
  public void testGetXmlRowNumber() {
    // Arrange, Act and Assert
    assertEquals(0, new ActivitiListener().getXmlRowNumber());
  }

  /**
   * Test {@link BaseElement#setXmlRowNumber(int)}.
   *
   * <p>Method under test: {@link BaseElement#setXmlRowNumber(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseElement.setXmlRowNumber(int)"})
  public void testSetXmlRowNumber() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    // Act
    activitiListener.setXmlRowNumber(10);

    // Assert
    assertEquals(10, activitiListener.getXmlRowNumber());
  }

  /**
   * Test {@link BaseElement#getXmlColumnNumber()}.
   *
   * <p>Method under test: {@link BaseElement#getXmlColumnNumber()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int BaseElement.getXmlColumnNumber()"})
  public void testGetXmlColumnNumber() {
    // Arrange, Act and Assert
    assertEquals(0, new ActivitiListener().getXmlColumnNumber());
  }

  /**
   * Test {@link BaseElement#setXmlColumnNumber(int)}.
   *
   * <p>Method under test: {@link BaseElement#setXmlColumnNumber(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseElement.setXmlColumnNumber(int)"})
  public void testSetXmlColumnNumber() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    // Act
    activitiListener.setXmlColumnNumber(10);

    // Assert
    assertEquals(10, activitiListener.getXmlColumnNumber());
  }

  /**
   * Test {@link BaseElement#getExtensionElements()}.
   *
   * <p>Method under test: {@link BaseElement#getExtensionElements()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map BaseElement.getExtensionElements()"})
  public void testGetExtensionElements() {
    // Arrange, Act and Assert
    assertTrue(new ActivitiListener().getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BaseElement#addExtensionElement(ExtensionElement)}.
   *
   * <ul>
   *   <li>Given empty string.
   *   <li>When {@link ExtensionElement} (default constructor) Name is empty string.
   * </ul>
   *
   * <p>Method under test: {@link BaseElement#addExtensionElement(ExtensionElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseElement.addExtensionElement(ExtensionElement)"})
  public void testAddExtensionElement_givenEmptyString_whenExtensionElementNameIsEmptyString() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    ExtensionElement extensionElement = new ExtensionElement();
    extensionElement.setName("");

    // Act
    activitiListener.addExtensionElement(extensionElement);

    // Assert that nothing has changed
    assertTrue(activitiListener.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BaseElement#addExtensionElement(ExtensionElement)}.
   *
   * <ul>
   *   <li>Then {@link ActivitiListener} (default constructor) ExtensionElements size is one.
   * </ul>
   *
   * <p>Method under test: {@link BaseElement#addExtensionElement(ExtensionElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseElement.addExtensionElement(ExtensionElement)"})
  public void testAddExtensionElement_thenActivitiListenerExtensionElementsSizeIsOne() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    ExtensionElement extensionElement = new ExtensionElement();
    extensionElement.setName("not empty");

    // Act
    activitiListener.addExtensionElement(extensionElement);

    // Assert
    Map<String, List<ExtensionElement>> extensionElements = activitiListener.getExtensionElements();
    assertEquals(1, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("not empty");
    assertEquals(1, getResult.size());
    assertSame(extensionElement, getResult.get(0));
  }

  /**
   * Test {@link BaseElement#addExtensionElement(ExtensionElement)}.
   *
   * <ul>
   *   <li>When {@link ExtensionElement} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BaseElement#addExtensionElement(ExtensionElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseElement.addExtensionElement(ExtensionElement)"})
  public void testAddExtensionElement_whenExtensionElement() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    // Act
    activitiListener.addExtensionElement(new ExtensionElement());

    // Assert that nothing has changed
    assertTrue(activitiListener.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BaseElement#addExtensionElement(ExtensionElement)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then {@link ActivitiListener} (default constructor) ExtensionElements Empty.
   * </ul>
   *
   * <p>Method under test: {@link BaseElement#addExtensionElement(ExtensionElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseElement.addExtensionElement(ExtensionElement)"})
  public void testAddExtensionElement_whenNull_thenActivitiListenerExtensionElementsEmpty() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    // Act
    activitiListener.addExtensionElement(null);

    // Assert that nothing has changed
    assertTrue(activitiListener.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BaseElement#setExtensionElements(Map)}.
   *
   * <p>Method under test: {@link BaseElement#setExtensionElements(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseElement.setExtensionElements(Map)"})
  public void testSetExtensionElements() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();

    // Act
    activitiListener.setExtensionElements(extensionElements);

    // Assert
    assertSame(extensionElements, activitiListener.getExtensionElements());
  }

  /**
   * Test {@link BaseElement#getAttributes()}.
   *
   * <p>Method under test: {@link BaseElement#getAttributes()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map BaseElement.getAttributes()"})
  public void testGetAttributes() {
    // Arrange, Act and Assert
    assertTrue(new ActivitiListener().getAttributes().isEmpty());
  }

  /**
   * Test {@link BaseElement#getAttributeValue(String, String)}.
   *
   * <p>Method under test: {@link BaseElement#getAttributeValue(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String BaseElement.getAttributeValue(String, String)"})
  public void testGetAttributeValue() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.addAttribute(new ExtensionAttribute("Name"));

    // Act and Assert
    assertNull(activitiListener.getAttributeValue("Namespace", "Name"));
  }

  /**
   * Test {@link BaseElement#getAttributeValue(String, String)}.
   *
   * <p>Method under test: {@link BaseElement#getAttributeValue(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String BaseElement.getAttributeValue(String, String)"})
  public void testGetAttributeValue2() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.addAttribute(new ExtensionAttribute("Name"));
    activitiListener.addAttribute(new ExtensionAttribute("Name"));

    // Act and Assert
    assertNull(activitiListener.getAttributeValue("Namespace", "Name"));
  }

  /**
   * Test {@link BaseElement#getAttributeValue(String, String)}.
   *
   * <p>Method under test: {@link BaseElement#getAttributeValue(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String BaseElement.getAttributeValue(String, String)"})
  public void testGetAttributeValue3() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.addAttribute(new ExtensionAttribute("Namespace", "Name"));

    // Act and Assert
    assertNull(activitiListener.getAttributeValue(null, "Name"));
  }

  /**
   * Test {@link BaseElement#getAttributeValue(String, String)}.
   *
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor).
   *   <li>When {@code Namespace}.
   * </ul>
   *
   * <p>Method under test: {@link BaseElement#getAttributeValue(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String BaseElement.getAttributeValue(String, String)"})
  public void testGetAttributeValue_givenActivitiListener_whenNamespace() {
    // Arrange, Act and Assert
    assertNull(new ActivitiListener().getAttributeValue("Namespace", "Name"));
  }

  /**
   * Test {@link BaseElement#getAttributeValue(String, String)}.
   *
   * <ul>
   *   <li>Given {@link ExtensionAttribute#ExtensionAttribute(String)} with {@code Name} Namespace
   *       is {@code Namespace}.
   * </ul>
   *
   * <p>Method under test: {@link BaseElement#getAttributeValue(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String BaseElement.getAttributeValue(String, String)"})
  public void testGetAttributeValue_givenExtensionAttributeWithNameNamespaceIsNamespace() {
    // Arrange
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    attribute.setNamespace("Namespace");

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.addAttribute(attribute);

    // Act and Assert
    assertNull(activitiListener.getAttributeValue("Namespace", "Name"));
  }

  /**
   * Test {@link BaseElement#getAttributeValue(String, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseElement#getAttributeValue(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String BaseElement.getAttributeValue(String, String)"})
  public void testGetAttributeValue_whenNull() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.addAttribute(new ExtensionAttribute("Name"));

    // Act and Assert
    assertNull(activitiListener.getAttributeValue(null, "Name"));
  }

  /**
   * Test {@link BaseElement#addAttribute(ExtensionAttribute)}.
   *
   * <ul>
   *   <li>Then {@link ActivitiListener} (default constructor) Attributes {@code Name} size is one.
   * </ul>
   *
   * <p>Method under test: {@link BaseElement#addAttribute(ExtensionAttribute)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseElement.addAttribute(ExtensionAttribute)"})
  public void testAddAttribute_thenActivitiListenerAttributesNameSizeIsOne() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    ExtensionAttribute attribute = new ExtensionAttribute("Name");

    // Act
    activitiListener.addAttribute(attribute);

    // Assert
    Map<String, List<ExtensionAttribute>> attributes = activitiListener.getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link BaseElement#addAttribute(ExtensionAttribute)}.
   *
   * <ul>
   *   <li>Then {@link ActivitiListener} (default constructor) Attributes {@code Name} size is two.
   * </ul>
   *
   * <p>Method under test: {@link BaseElement#addAttribute(ExtensionAttribute)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseElement.addAttribute(ExtensionAttribute)"})
  public void testAddAttribute_thenActivitiListenerAttributesNameSizeIsTwo() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    activitiListener.addAttribute(attribute);
    ExtensionAttribute attribute2 = new ExtensionAttribute("Name");

    // Act
    activitiListener.addAttribute(attribute2);

    // Assert
    Map<String, List<ExtensionAttribute>> attributes = activitiListener.getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(2, getResult.size());
    assertSame(attribute, getResult.get(0));
    assertSame(attribute2, getResult.get(1));
  }

  /**
   * Test {@link BaseElement#addAttribute(ExtensionAttribute)}.
   *
   * <ul>
   *   <li>When {@link ExtensionAttribute#ExtensionAttribute(String)} with name is empty string.
   * </ul>
   *
   * <p>Method under test: {@link BaseElement#addAttribute(ExtensionAttribute)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseElement.addAttribute(ExtensionAttribute)"})
  public void testAddAttribute_whenExtensionAttributeWithNameIsEmptyString() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    // Act
    activitiListener.addAttribute(new ExtensionAttribute(""));

    // Assert that nothing has changed
    assertTrue(activitiListener.getAttributes().isEmpty());
  }

  /**
   * Test {@link BaseElement#addAttribute(ExtensionAttribute)}.
   *
   * <ul>
   *   <li>When {@link ExtensionAttribute#ExtensionAttribute(String)} with name is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseElement#addAttribute(ExtensionAttribute)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseElement.addAttribute(ExtensionAttribute)"})
  public void testAddAttribute_whenExtensionAttributeWithNameIsNull() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    // Act
    activitiListener.addAttribute(new ExtensionAttribute(null));

    // Assert that nothing has changed
    assertTrue(activitiListener.getAttributes().isEmpty());
  }

  /**
   * Test {@link BaseElement#addAttribute(ExtensionAttribute)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then {@link ActivitiListener} (default constructor) Attributes Empty.
   * </ul>
   *
   * <p>Method under test: {@link BaseElement#addAttribute(ExtensionAttribute)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseElement.addAttribute(ExtensionAttribute)"})
  public void testAddAttribute_whenNull_thenActivitiListenerAttributesEmpty() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    // Act
    activitiListener.addAttribute(null);

    // Assert that nothing has changed
    assertTrue(activitiListener.getAttributes().isEmpty());
  }

  /**
   * Test {@link BaseElement#setAttributes(Map)}.
   *
   * <p>Method under test: {@link BaseElement#setAttributes(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseElement.setAttributes(Map)"})
  public void testSetAttributes() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();

    // Act
    activitiListener.setAttributes(attributes);

    // Assert
    assertSame(attributes, activitiListener.getAttributes());
  }

  /**
   * Test {@link BaseElement#setValues(BaseElement)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>Then {@link ActivitiListener} (default constructor) Id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link BaseElement#setValues(BaseElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseElement.setValues(BaseElement)"})
  public void testSetValues_given42_thenActivitiListenerIdIs42() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    AdhocSubProcess otherElement = mock(AdhocSubProcess.class);
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getAttributes()).thenReturn(new HashMap<>());
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());

    // Act
    activitiListener.setValues(otherElement);

    // Assert
    verify(otherElement, atLeast(1)).getAttributes();
    verify(otherElement, atLeast(1)).getExtensionElements();
    verify(otherElement).getId();
    assertEquals("42", activitiListener.getId());
    assertTrue(activitiListener.getAttributes().isEmpty());
  }

  /**
   * Test {@link BaseElement#setValues(BaseElement)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>Then {@link ActivitiListener} (default constructor) Id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link BaseElement#setValues(BaseElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseElement.setValues(BaseElement)"})
  public void testSetValues_given42_thenActivitiListenerIdIs422() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("Key", new ArrayList<>());

    AdhocSubProcess otherElement = mock(AdhocSubProcess.class);
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getAttributes()).thenReturn(stringListMap);
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());

    // Act
    activitiListener.setValues(otherElement);

    // Assert
    verify(otherElement, atLeast(1)).getAttributes();
    verify(otherElement, atLeast(1)).getExtensionElements();
    verify(otherElement).getId();
    assertEquals("42", activitiListener.getId());
    assertTrue(activitiListener.getAttributes().isEmpty());
  }

  /**
   * Test {@link BaseElement#setValues(BaseElement)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>Then {@link ActivitiListener} (default constructor) Id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link BaseElement#setValues(BaseElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseElement.setValues(BaseElement)"})
  public void testSetValues_given42_thenActivitiListenerIdIs423() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("Key", new ArrayList<>());

    AdhocSubProcess otherElement = mock(AdhocSubProcess.class);
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getAttributes()).thenReturn(new HashMap<>());
    when(otherElement.getExtensionElements()).thenReturn(stringListMap);

    // Act
    activitiListener.setValues(otherElement);

    // Assert
    verify(otherElement, atLeast(1)).getAttributes();
    verify(otherElement, atLeast(1)).getExtensionElements();
    verify(otherElement).getId();
    assertEquals("42", activitiListener.getId());
    assertTrue(activitiListener.getAttributes().isEmpty());
  }

  /**
   * Test {@link BaseElement#setValues(BaseElement)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link
   *       ExtensionAttribute#ExtensionAttribute(String)} with {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link BaseElement#setValues(BaseElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseElement.setValues(BaseElement)"})
  public void testSetValues_givenArrayListAddExtensionAttributeWithName() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("Name"));

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("Key", extensionAttributeList);

    AdhocSubProcess otherElement = mock(AdhocSubProcess.class);
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getAttributes()).thenReturn(stringListMap);
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());

    // Act
    activitiListener.setValues(otherElement);

    // Assert
    verify(otherElement, atLeast(1)).getAttributes();
    verify(otherElement, atLeast(1)).getExtensionElements();
    verify(otherElement).getId();
    assertEquals(stringListMap, activitiListener.getAttributes());
  }

  /**
   * Test {@link BaseElement#setValues(BaseElement)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 42} is {@link ArrayList#ArrayList()}.
   *   <li>Then {@link ActivitiListener} (default constructor) Id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link BaseElement#setValues(BaseElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseElement.setValues(BaseElement)"})
  public void testSetValues_givenHashMap42IsArrayList_thenActivitiListenerIdIs42() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("42", new ArrayList<>());
    stringListMap.put("Key", new ArrayList<>());

    AdhocSubProcess otherElement = mock(AdhocSubProcess.class);
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getAttributes()).thenReturn(stringListMap);
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());

    // Act
    activitiListener.setValues(otherElement);

    // Assert
    verify(otherElement, atLeast(1)).getAttributes();
    verify(otherElement, atLeast(1)).getExtensionElements();
    verify(otherElement).getId();
    assertEquals("42", activitiListener.getId());
    assertTrue(activitiListener.getAttributes().isEmpty());
  }

  /**
   * Test {@link BaseElement#setValues(BaseElement)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 42} is {@link ArrayList#ArrayList()}.
   *   <li>Then {@link ActivitiListener} (default constructor) Id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link BaseElement#setValues(BaseElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseElement.setValues(BaseElement)"})
  public void testSetValues_givenHashMap42IsArrayList_thenActivitiListenerIdIs422() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("42", new ArrayList<>());
    stringListMap.put("Key", new ArrayList<>());

    AdhocSubProcess otherElement = mock(AdhocSubProcess.class);
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getAttributes()).thenReturn(new HashMap<>());
    when(otherElement.getExtensionElements()).thenReturn(stringListMap);

    // Act
    activitiListener.setValues(otherElement);

    // Assert
    verify(otherElement, atLeast(1)).getAttributes();
    verify(otherElement, atLeast(1)).getExtensionElements();
    verify(otherElement).getId();
    assertEquals("42", activitiListener.getId());
    assertTrue(activitiListener.getAttributes().isEmpty());
  }

  /**
   * Test {@link BaseElement#setValues(BaseElement)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link ActivitiListener} (default constructor) ExtensionElements is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseElement#setValues(BaseElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseElement.setValues(BaseElement)"})
  public void testSetValues_givenNull_whenActivitiListenerExtensionElementsIsNull() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    ActivitiListener otherElement = new ActivitiListener();
    otherElement.setExtensionElements(null);
    otherElement.setAttributes(new HashMap<>());

    // Act
    activitiListener.setValues((BaseElement) otherElement);

    // Assert that nothing has changed
    assertTrue(otherElement.getAttributes().isEmpty());
  }

  /**
   * Test {@link BaseElement#setValues(BaseElement)}.
   *
   * <ul>
   *   <li>Then {@link ActivitiListener} (default constructor) Attributes size is one.
   * </ul>
   *
   * <p>Method under test: {@link BaseElement#setValues(BaseElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseElement.setValues(BaseElement)"})
  public void testSetValues_thenActivitiListenerAttributesSizeIsOne() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.put("Key", new ArrayList<>());

    ActivitiListener otherElement = new ActivitiListener();
    otherElement.setExtensionElements(null);
    otherElement.setAttributes(attributes);

    // Act
    activitiListener.setValues((BaseElement) otherElement);

    // Assert that nothing has changed
    Map<String, List<ExtensionAttribute>> attributes2 = otherElement.getAttributes();
    assertEquals(1, attributes2.size());
    assertTrue(attributes2.get("Key").isEmpty());
    assertSame(attributes, attributes2);
  }

  /**
   * Test {@link BaseElement#setValues(BaseElement)}.
   *
   * <ul>
   *   <li>Then {@link ActivitiListener} (default constructor) Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseElement#setValues(BaseElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseElement.setValues(BaseElement)"})
  public void testSetValues_thenActivitiListenerIdIsNull() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    ActivitiListener otherElement = new ActivitiListener();
    otherElement.setExtensionElements(new HashMap<>());
    otherElement.setAttributes(null);

    // Act
    activitiListener.setValues((BaseElement) otherElement);

    // Assert that nothing has changed
    assertNull(otherElement.getId());
    assertNull(otherElement.getAttributes());
  }

  /**
   * Test {@link BaseElement#setValues(BaseElement)}.
   *
   * <ul>
   *   <li>Then {@link ActivitiListener} (default constructor) Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseElement#setValues(BaseElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseElement.setValues(BaseElement)"})
  public void testSetValues_thenActivitiListenerIdIsNull2() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("Key", new ArrayList<>());

    ActivitiListener otherElement = new ActivitiListener();
    otherElement.setExtensionElements(extensionElements);
    otherElement.setAttributes(null);

    // Act
    activitiListener.setValues((BaseElement) otherElement);

    // Assert that nothing has changed
    assertNull(otherElement.getId());
    assertNull(otherElement.getAttributes());
  }

  /**
   * Test {@link BaseElement#setValues(BaseElement)}.
   *
   * <ul>
   *   <li>When {@link ActivitiListener} (default constructor).
   *   <li>Then {@link ActivitiListener} (default constructor) Attributes Empty.
   * </ul>
   *
   * <p>Method under test: {@link BaseElement#setValues(BaseElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseElement.setValues(BaseElement)"})
  public void testSetValues_whenActivitiListener_thenActivitiListenerAttributesEmpty() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    ActivitiListener otherElement = new ActivitiListener();

    // Act
    activitiListener.setValues((BaseElement) otherElement);

    // Assert that nothing has changed
    assertTrue(otherElement.getAttributes().isEmpty());
  }
}
