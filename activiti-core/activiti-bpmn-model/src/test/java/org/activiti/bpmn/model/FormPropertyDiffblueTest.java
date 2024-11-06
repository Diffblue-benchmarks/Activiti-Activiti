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
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.mockito.Mockito;

public class FormPropertyDiffblueTest {
  /**
   * Test {@link FormProperty#clone()}.
   * <ul>
   *   <li>Given {@link FormProperty} (default constructor) ExtensionElements is
   * {@code null}.</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FormProperty#clone()}
   */
  @Test
  public void testClone_givenFormPropertyExtensionElementsIsNull_thenReturnIdIsNull() {
    // Arrange
    FormProperty formProperty = new FormProperty();
    formProperty.setExtensionElements(null);
    formProperty.setAttributes(null);

    // Act
    FormProperty actualCloneResult = formProperty.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDatePattern());
    assertNull(actualCloneResult.getDefaultExpression());
    assertNull(actualCloneResult.getExpression());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getType());
    assertNull(actualCloneResult.getVariable());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.isRequired());
    assertTrue(actualCloneResult.getFormValues().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isReadable());
    assertTrue(actualCloneResult.isWriteable());
  }

  /**
   * Test {@link FormProperty#clone()}.
   * <ul>
   *   <li>Given {@link FormProperty} (default constructor) FormValues is
   * {@code null}.</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FormProperty#clone()}
   */
  @Test
  public void testClone_givenFormPropertyFormValuesIsNull_thenReturnIdIsNull() {
    // Arrange
    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(null);

    // Act
    FormProperty actualCloneResult = formProperty.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDatePattern());
    assertNull(actualCloneResult.getDefaultExpression());
    assertNull(actualCloneResult.getExpression());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getType());
    assertNull(actualCloneResult.getVariable());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.isRequired());
    assertTrue(actualCloneResult.getFormValues().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isReadable());
    assertTrue(actualCloneResult.isWriteable());
  }

  /**
   * Test {@link FormProperty#clone()}.
   * <ul>
   *   <li>Given {@link FormProperty} (default constructor).</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FormProperty#clone()}
   */
  @Test
  public void testClone_givenFormProperty_thenReturnIdIsNull() {
    // Arrange and Act
    FormProperty actualCloneResult = (new FormProperty()).clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDatePattern());
    assertNull(actualCloneResult.getDefaultExpression());
    assertNull(actualCloneResult.getExpression());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getType());
    assertNull(actualCloneResult.getVariable());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.isRequired());
    assertTrue(actualCloneResult.getFormValues().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isReadable());
    assertTrue(actualCloneResult.isWriteable());
  }

  /**
   * Test {@link FormProperty#clone()}.
   * <ul>
   *   <li>Given {@link FormValue} (default constructor) ExtensionElements is
   * {@code null}.</li>
   *   <li>Then return FormValues size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link FormProperty#clone()}
   */
  @Test
  public void testClone_givenFormValueExtensionElementsIsNull_thenReturnFormValuesSizeIsOne() {
    // Arrange
    FormValue formValue = new FormValue();
    formValue.setExtensionElements(null);
    formValue.setAttributes(null);

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(formValue);

    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(formValues);

    // Act and Assert
    List<FormValue> formValues2 = formProperty.clone().getFormValues();
    assertEquals(1, formValues2.size());
    FormValue getResult = formValues2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link FormProperty#clone()}.
   * <ul>
   *   <li>Then return Attributes size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link FormProperty#clone()}
   */
  @Test
  public void testClone_thenReturnAttributesSizeIsOne() {
    // Arrange
    FormProperty formProperty = new FormProperty();
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    formProperty.addAttribute(attribute);

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = formProperty.clone().getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link FormProperty#clone()}.
   * <ul>
   *   <li>Then return Attributes size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link FormProperty#clone()}
   */
  @Test
  public void testClone_thenReturnAttributesSizeIsTwo() {
    // Arrange
    FormProperty formProperty = new FormProperty();
    ExtensionAttribute attribute = new ExtensionAttribute("42");
    formProperty.addAttribute(attribute);
    formProperty.addAttribute(new ExtensionAttribute("Name"));

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = formProperty.clone().getAttributes();
    assertEquals(2, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("42");
    assertEquals(1, getResult.size());
    assertTrue(attributes.containsKey("Name"));
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link FormProperty#setValues(FormProperty)} with {@code otherProperty}.
   * <ul>
   *   <li>Then calls {@link ExtensionAttribute#getName()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FormProperty#setValues(FormProperty)}
   */
  @Test
  public void testSetValuesWithOtherProperty_thenCallsGetName() {
    // Arrange
    FormProperty formProperty = new FormProperty();
    ExtensionAttribute attribute = mock(ExtensionAttribute.class);
    when(attribute.getName()).thenReturn("Name");

    FormProperty otherProperty = new FormProperty();
    otherProperty.addAttribute(attribute);

    // Act
    formProperty.setValues(otherProperty);

    // Assert
    verify(attribute, atLeast(1)).getName();
  }

  /**
   * Test {@link FormProperty#setValues(FormProperty)} with {@code otherProperty}.
   * <ul>
   *   <li>Then calls {@link BaseElement#setAttributes(Map)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FormProperty#setValues(FormProperty)}
   */
  @Test
  public void testSetValuesWithOtherProperty_thenCallsSetAttributes() {
    // Arrange
    FormProperty formProperty = new FormProperty();
    FormValue formValue = mock(FormValue.class);
    when(formValue.clone()).thenReturn(new FormValue());
    doNothing().when(formValue).setAttributes(Mockito.<Map<String, List<ExtensionAttribute>>>any());
    doNothing().when(formValue).setExtensionElements(Mockito.<Map<String, List<ExtensionElement>>>any());
    formValue.setExtensionElements(null);
    formValue.setAttributes(null);

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(formValue);

    FormProperty otherProperty = new FormProperty();
    otherProperty.setFormValues(formValues);

    // Act
    formProperty.setValues(otherProperty);

    // Assert
    verify(formValue).setAttributes(isNull());
    verify(formValue).setExtensionElements(isNull());
    verify(formValue).clone();
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link FormProperty}
   *   <li>{@link FormProperty#setDatePattern(String)}
   *   <li>{@link FormProperty#setDefaultExpression(String)}
   *   <li>{@link FormProperty#setExpression(String)}
   *   <li>{@link FormProperty#setFormValues(List)}
   *   <li>{@link FormProperty#setName(String)}
   *   <li>{@link FormProperty#setReadable(boolean)}
   *   <li>{@link FormProperty#setRequired(boolean)}
   *   <li>{@link FormProperty#setType(String)}
   *   <li>{@link FormProperty#setVariable(String)}
   *   <li>{@link FormProperty#setWriteable(boolean)}
   *   <li>{@link FormProperty#getDatePattern()}
   *   <li>{@link FormProperty#getDefaultExpression()}
   *   <li>{@link FormProperty#getExpression()}
   *   <li>{@link FormProperty#getFormValues()}
   *   <li>{@link FormProperty#getName()}
   *   <li>{@link FormProperty#getType()}
   *   <li>{@link FormProperty#getVariable()}
   *   <li>{@link FormProperty#isReadable()}
   *   <li>{@link FormProperty#isRequired()}
   *   <li>{@link FormProperty#isWriteable()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    FormProperty actualFormProperty = new FormProperty();
    actualFormProperty.setDatePattern("2020-03-01");
    actualFormProperty.setDefaultExpression("Default Expression");
    actualFormProperty.setExpression("Expression");
    ArrayList<FormValue> formValues = new ArrayList<>();
    actualFormProperty.setFormValues(formValues);
    actualFormProperty.setName("Name");
    actualFormProperty.setReadable(true);
    actualFormProperty.setRequired(true);
    actualFormProperty.setType("Type");
    actualFormProperty.setVariable("Variable");
    actualFormProperty.setWriteable(true);
    String actualDatePattern = actualFormProperty.getDatePattern();
    String actualDefaultExpression = actualFormProperty.getDefaultExpression();
    String actualExpression = actualFormProperty.getExpression();
    List<FormValue> actualFormValues = actualFormProperty.getFormValues();
    String actualName = actualFormProperty.getName();
    String actualType = actualFormProperty.getType();
    String actualVariable = actualFormProperty.getVariable();
    boolean actualIsReadableResult = actualFormProperty.isReadable();
    boolean actualIsRequiredResult = actualFormProperty.isRequired();
    boolean actualIsWriteableResult = actualFormProperty.isWriteable();

    // Assert that nothing has changed
    assertEquals("2020-03-01", actualDatePattern);
    assertEquals("Default Expression", actualDefaultExpression);
    assertEquals("Expression", actualExpression);
    assertEquals("Name", actualName);
    assertEquals("Type", actualType);
    assertEquals("Variable", actualVariable);
    assertEquals(0, actualFormProperty.getXmlColumnNumber());
    assertEquals(0, actualFormProperty.getXmlRowNumber());
    assertTrue(actualFormValues.isEmpty());
    assertTrue(actualFormProperty.getAttributes().isEmpty());
    assertTrue(actualFormProperty.getExtensionElements().isEmpty());
    assertTrue(actualIsReadableResult);
    assertTrue(actualIsRequiredResult);
    assertTrue(actualIsWriteableResult);
    assertSame(formValues, actualFormValues);
  }
}
