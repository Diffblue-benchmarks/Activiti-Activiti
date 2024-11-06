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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.Test;

public class BaseElementDiffblueTest {
  /**
   * Test {@link BaseElement#getId()}.
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#getId()}
   */
  @Test
  public void testGetId_givenActivitiListener() {
    // Arrange, Act and Assert
    assertNull((new ActivitiListener()).getId());
  }

  /**
   * Test {@link BaseElement#getId()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#getId()}
   */
  @Test
  public void testGetId_givenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("foo", mock(BiFunction.class));

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setExtensionElements(extensionElements);

    // Act and Assert
    assertNull(activitiListener.getId());
  }

  /**
   * Test {@link BaseElement#setId(String)}.
   * <p>
   * Method under test: {@link BaseElement#setId(String)}
   */
  @Test
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
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#getXmlRowNumber()}
   */
  @Test
  public void testGetXmlRowNumber_givenActivitiListener() {
    // Arrange, Act and Assert
    assertEquals(0, (new ActivitiListener()).getXmlRowNumber());
  }

  /**
   * Test {@link BaseElement#getXmlRowNumber()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#getXmlRowNumber()}
   */
  @Test
  public void testGetXmlRowNumber_givenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("foo", mock(BiFunction.class));

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setExtensionElements(extensionElements);

    // Act and Assert
    assertEquals(0, activitiListener.getXmlRowNumber());
  }

  /**
   * Test {@link BaseElement#setXmlRowNumber(int)}.
   * <p>
   * Method under test: {@link BaseElement#setXmlRowNumber(int)}
   */
  @Test
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
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#getXmlColumnNumber()}
   */
  @Test
  public void testGetXmlColumnNumber_givenActivitiListener() {
    // Arrange, Act and Assert
    assertEquals(0, (new ActivitiListener()).getXmlColumnNumber());
  }

  /**
   * Test {@link BaseElement#getXmlColumnNumber()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#getXmlColumnNumber()}
   */
  @Test
  public void testGetXmlColumnNumber_givenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("foo", mock(BiFunction.class));

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setExtensionElements(extensionElements);

    // Act and Assert
    assertEquals(0, activitiListener.getXmlColumnNumber());
  }

  /**
   * Test {@link BaseElement#setXmlColumnNumber(int)}.
   * <p>
   * Method under test: {@link BaseElement#setXmlColumnNumber(int)}
   */
  @Test
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
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#getExtensionElements()}
   */
  @Test
  public void testGetExtensionElements_givenActivitiListener() {
    // Arrange, Act and Assert
    assertTrue((new ActivitiListener()).getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BaseElement#getExtensionElements()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#getExtensionElements()}
   */
  @Test
  public void testGetExtensionElements_givenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("foo", mock(BiFunction.class));

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setExtensionElements(extensionElements);

    // Act and Assert
    assertTrue(activitiListener.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BaseElement#addExtensionElement(ExtensionElement)}.
   * <ul>
   *   <li>Given empty string.</li>
   *   <li>When {@link ExtensionElement} (default constructor) Name is empty
   * string.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#addExtensionElement(ExtensionElement)}
   */
  @Test
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
   * <ul>
   *   <li>Given {@code Name}.</li>
   *   <li>Then {@link ActivitiListener} (default constructor) ExtensionElements
   * size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#addExtensionElement(ExtensionElement)}
   */
  @Test
  public void testAddExtensionElement_givenName_thenActivitiListenerExtensionElementsSizeIsOne() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    ExtensionElement extensionElement = new ExtensionElement();
    extensionElement.setName("Name");

    // Act
    activitiListener.addExtensionElement(extensionElement);

    // Assert
    Map<String, List<ExtensionElement>> extensionElements = activitiListener.getExtensionElements();
    assertEquals(1, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("Name");
    assertEquals(1, getResult.size());
    assertSame(extensionElement, getResult.get(0));
  }

  /**
   * Test {@link BaseElement#addExtensionElement(ExtensionElement)}.
   * <ul>
   *   <li>When {@link ExtensionElement} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#addExtensionElement(ExtensionElement)}
   */
  @Test
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
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link ActivitiListener} (default constructor) ExtensionElements
   * Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#addExtensionElement(ExtensionElement)}
   */
  @Test
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
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#setExtensionElements(Map)}
   */
  @Test
  public void testSetExtensionElements_givenFoo_whenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("foo", mock(BiFunction.class));

    // Act
    activitiListener.setExtensionElements(extensionElements);

    // Assert
    assertSame(extensionElements, activitiListener.getExtensionElements());
  }

  /**
   * Test {@link BaseElement#setExtensionElements(Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#setExtensionElements(Map)}
   */
  @Test
  public void testSetExtensionElements_whenHashMap() {
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
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#getAttributes()}
   */
  @Test
  public void testGetAttributes_givenActivitiListener() {
    // Arrange, Act and Assert
    assertTrue((new ActivitiListener()).getAttributes().isEmpty());
  }

  /**
   * Test {@link BaseElement#getAttributes()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#getAttributes()}
   */
  @Test
  public void testGetAttributes_givenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("foo", mock(BiFunction.class));

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setExtensionElements(extensionElements);

    // Act and Assert
    assertTrue(activitiListener.getAttributes().isEmpty());
  }

  /**
   * Test {@link BaseElement#getAttributeValue(String, String)}.
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor).</li>
   *   <li>When {@code Namespace}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#getAttributeValue(String, String)}
   */
  @Test
  public void testGetAttributeValue_givenActivitiListener_whenNamespace() {
    // Arrange, Act and Assert
    assertNull((new ActivitiListener()).getAttributeValue("Namespace", "Name"));
  }

  /**
   * Test {@link BaseElement#getAttributeValue(String, String)}.
   * <ul>
   *   <li>Given {@link ExtensionAttribute#ExtensionAttribute(String)} with
   * {@code Name} Namespace is {@code Namespace}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#getAttributeValue(String, String)}
   */
  @Test
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
   * <ul>
   *   <li>Given {@link ExtensionAttribute#ExtensionAttribute(String)} with
   * {@code Name} Namespace is {@code Namespace}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#getAttributeValue(String, String)}
   */
  @Test
  public void testGetAttributeValue_givenExtensionAttributeWithNameNamespaceIsNamespace2() {
    // Arrange
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    attribute.setNamespace("Namespace");

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.addAttribute(attribute);

    // Act and Assert
    assertNull(activitiListener.getAttributeValue(null, "Name"));
  }

  /**
   * Test {@link BaseElement#getAttributeValue(String, String)}.
   * <ul>
   *   <li>When {@code Namespace}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#getAttributeValue(String, String)}
   */
  @Test
  public void testGetAttributeValue_whenNamespace() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.addAttribute(new ExtensionAttribute("Name"));

    // Act and Assert
    assertNull(activitiListener.getAttributeValue("Namespace", "Name"));
  }

  /**
   * Test {@link BaseElement#getAttributeValue(String, String)}.
   * <ul>
   *   <li>When {@code Namespace}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#getAttributeValue(String, String)}
   */
  @Test
  public void testGetAttributeValue_whenNamespace2() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.addAttribute(new ExtensionAttribute("Name"));
    activitiListener.addAttribute(new ExtensionAttribute("Name"));

    // Act and Assert
    assertNull(activitiListener.getAttributeValue("Namespace", "Name"));
  }

  /**
   * Test {@link BaseElement#getAttributeValue(String, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#getAttributeValue(String, String)}
   */
  @Test
  public void testGetAttributeValue_whenNull() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.addAttribute(new ExtensionAttribute("Name"));

    // Act and Assert
    assertNull(activitiListener.getAttributeValue(null, "Name"));
  }

  /**
   * Test {@link BaseElement#addAttribute(ExtensionAttribute)}.
   * <ul>
   *   <li>Then {@link ActivitiListener} (default constructor) Attributes
   * {@code Name} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#addAttribute(ExtensionAttribute)}
   */
  @Test
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
   * <ul>
   *   <li>Then {@link ActivitiListener} (default constructor) Attributes
   * {@code Name} size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#addAttribute(ExtensionAttribute)}
   */
  @Test
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
   * <ul>
   *   <li>When {@link ExtensionAttribute#ExtensionAttribute(String)} with name is
   * empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#addAttribute(ExtensionAttribute)}
   */
  @Test
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
   * <ul>
   *   <li>When {@link ExtensionAttribute#ExtensionAttribute(String)} with name is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#addAttribute(ExtensionAttribute)}
   */
  @Test
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
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link ActivitiListener} (default constructor) Attributes
   * Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#addAttribute(ExtensionAttribute)}
   */
  @Test
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
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#setAttributes(Map)}
   */
  @Test
  public void testSetAttributes_givenFoo_whenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.computeIfPresent("foo", mock(BiFunction.class));

    // Act
    activitiListener.setAttributes(attributes);

    // Assert
    assertSame(attributes, activitiListener.getAttributes());
  }

  /**
   * Test {@link BaseElement#setAttributes(Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#setAttributes(Map)}
   */
  @Test
  public void testSetAttributes_whenHashMap() {
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
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>Then {@link ActivitiListener} (default constructor) Id is
   * {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#setValues(BaseElement)}
   */
  @Test
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
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 42} is
   * {@link ArrayList#ArrayList()}.</li>
   *   <li>Then {@link ActivitiListener} (default constructor) Id is
   * {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#setValues(BaseElement)}
   */
  @Test
  public void testSetValues_givenHashMap42IsArrayList_thenActivitiListenerIdIs42() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("42", new ArrayList<>());
    stringListMap.put("foo", new ArrayList<>());
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
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 42} is
   * {@link ArrayList#ArrayList()}.</li>
   *   <li>Then {@link ActivitiListener} (default constructor) Id is
   * {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#setValues(BaseElement)}
   */
  @Test
  public void testSetValues_givenHashMap42IsArrayList_thenActivitiListenerIdIs422() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("42", new ArrayList<>());
    stringListMap.put("foo", new ArrayList<>());
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
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code foo} is
   * {@link ArrayList#ArrayList()}.</li>
   *   <li>Then {@link ActivitiListener} (default constructor) Id is
   * {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#setValues(BaseElement)}
   */
  @Test
  public void testSetValues_givenHashMapFooIsArrayList_thenActivitiListenerIdIs42() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("foo", new ArrayList<>());
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
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code foo} is
   * {@link ArrayList#ArrayList()}.</li>
   *   <li>Then {@link ActivitiListener} (default constructor) Id is
   * {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#setValues(BaseElement)}
   */
  @Test
  public void testSetValues_givenHashMapFooIsArrayList_thenActivitiListenerIdIs422() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("foo", new ArrayList<>());
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
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>Then {@link ActivitiListener} (default constructor) Attributes is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#setValues(BaseElement)}
   */
  @Test
  public void testSetValues_givenNull_thenActivitiListenerAttributesIsNull() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    ActivitiListener otherElement = new ActivitiListener();
    otherElement.setExtensionElements(null);
    otherElement.setAttributes(null);

    // Act
    activitiListener.setValues((BaseElement) otherElement);

    // Assert
    assertNull(otherElement.getId());
    assertNull(otherElement.getAttributes());
  }

  /**
   * Test {@link BaseElement#setValues(BaseElement)}.
   * <ul>
   *   <li>Then {@link ActivitiListener} (default constructor) Attributes is
   * {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#setValues(BaseElement)}
   */
  @Test
  public void testSetValues_thenActivitiListenerAttributesIsHashMap() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("Name"));

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("foo", extensionAttributeList);
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
   * <ul>
   *   <li>When {@link ActivitiListener} (default constructor).</li>
   *   <li>Then {@link ActivitiListener} (default constructor) Id is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseElement#setValues(BaseElement)}
   */
  @Test
  public void testSetValues_whenActivitiListener_thenActivitiListenerIdIsNull() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    ActivitiListener otherElement = new ActivitiListener();

    // Act
    activitiListener.setValues((BaseElement) otherElement);

    // Assert
    assertNull(otherElement.getId());
    assertTrue(otherElement.getAttributes().isEmpty());
  }
}
