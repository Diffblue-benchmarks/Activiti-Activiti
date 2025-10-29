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
package org.activiti.engine.delegate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.FieldExtension;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DelegateHelperDiffblueTest {
  @InjectMocks
  private DelegateHelper delegateHelper;

  /**
   * Method under test: {@link DelegateHelper#getBpmnModel(DelegateExecution)}
   */
  @Test
  public void testGetBpmnModel() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> DelegateHelper.getBpmnModel(null));
  }

  /**
   * Method under test: {@link DelegateHelper#getFlowElement(DelegateExecution)}
   */
  @Test
  public void testGetFlowElement() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> DelegateHelper.getFlowElement(null));
  }

  /**
   * Method under test:
   * {@link DelegateHelper#isExecutingExecutionListener(DelegateExecution)}
   */
  @Test
  public void testIsExecutingExecutionListener() {
    // Arrange, Act and Assert
    assertFalse(
        DelegateHelper.isExecutingExecutionListener(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link DelegateHelper#isExecutingExecutionListener(DelegateExecution)}
   */
  @Test
  public void testIsExecutingExecutionListener2() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(new ActivitiListener());

    // Act and Assert
    assertTrue(DelegateHelper.isExecutingExecutionListener(execution));
  }

  /**
   * Method under test:
   * {@link DelegateHelper#isExecutingExecutionListener(DelegateExecution)}
   */
  @Test
  public void testIsExecutingExecutionListener3() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setLockTime(mock(Date.class));

    // Act and Assert
    assertFalse(DelegateHelper.isExecutingExecutionListener(execution));
  }

  /**
   * Method under test:
   * {@link DelegateHelper#getExtensionElements(DelegateExecution)}
   */
  @Test
  public void testGetExtensionElements() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(new ActivitiListener());

    // Act
    Map<String, List<ExtensionElement>> actualExtensionElements = DelegateHelper.getExtensionElements(execution);

    // Assert
    assertTrue(actualExtensionElements.isEmpty());
  }

  /**
   * Method under test:
   * {@link DelegateHelper#getFlowElementExtensionElements(DelegateExecution)}
   */
  @Test
  public void testGetFlowElementExtensionElements() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> DelegateHelper.getFlowElementExtensionElements(null));
  }

  /**
   * Method under test:
   * {@link DelegateHelper#getListenerExtensionElements(DelegateExecution)}
   */
  @Test
  public void testGetListenerExtensionElements() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(new ActivitiListener());

    // Act
    Map<String, List<ExtensionElement>> actualListenerExtensionElements = DelegateHelper
        .getListenerExtensionElements(execution);

    // Assert
    assertTrue(actualListenerExtensionElements.isEmpty());
  }

  /**
   * Method under test: {@link DelegateHelper#getFields(DelegateExecution)}
   */
  @Test
  public void testGetFields() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(new ActivitiListener());

    // Act
    List<FieldExtension> actualFields = DelegateHelper.getFields(execution);

    // Assert
    assertTrue(actualFields.isEmpty());
  }

  /**
   * Method under test:
   * {@link DelegateHelper#getFlowElementFields(DelegateExecution)}
   */
  @Test
  public void testGetFlowElementFields() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> DelegateHelper.getFlowElementFields(null));
  }

  /**
   * Method under test:
   * {@link DelegateHelper#getListenerFields(DelegateExecution)}
   */
  @Test
  public void testGetListenerFields() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(new ActivitiListener());

    // Act
    List<FieldExtension> actualListenerFields = DelegateHelper.getListenerFields(execution);

    // Assert
    assertTrue(actualListenerFields.isEmpty());
  }

  /**
   * Method under test: {@link DelegateHelper#getField(DelegateExecution, String)}
   */
  @Test
  public void testGetField() {
    // Arrange
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getCurrentActivitiListener()).thenReturn(new ActivitiListener());

    // Act
    FieldExtension actualField = DelegateHelper.getField(execution, "Field Name");

    // Assert
    verify(execution, atLeast(1)).getCurrentActivitiListener();
    assertNull(actualField);
  }

  /**
   * Method under test: {@link DelegateHelper#getField(DelegateExecution, String)}
   */
  @Test
  public void testGetField2() {
    // Arrange
    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getFieldExtensions()).thenReturn(new ArrayList<>());
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getCurrentActivitiListener()).thenReturn(activitiListener);

    // Act
    FieldExtension actualField = DelegateHelper.getField(execution, "Field Name");

    // Assert
    verify(activitiListener).getFieldExtensions();
    verify(execution, atLeast(1)).getCurrentActivitiListener();
    assertNull(actualField);
  }

  /**
   * Method under test: {@link DelegateHelper#getField(DelegateExecution, String)}
   */
  @Test
  public void testGetField3() {
    // Arrange
    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(new FieldExtension());
    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getFieldExtensions()).thenReturn(fieldExtensionList);
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getCurrentActivitiListener()).thenReturn(activitiListener);

    // Act
    FieldExtension actualField = DelegateHelper.getField(execution, "Field Name");

    // Assert
    verify(activitiListener).getFieldExtensions();
    verify(execution, atLeast(1)).getCurrentActivitiListener();
    assertNull(actualField);
  }

  /**
   * Method under test: {@link DelegateHelper#getField(DelegateExecution, String)}
   */
  @Test
  public void testGetField4() {
    // Arrange
    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getFieldExtensions()).thenThrow(new ActivitiException("An error occurred"));
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getCurrentActivitiListener()).thenReturn(activitiListener);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> DelegateHelper.getField(execution, "Field Name"));
    verify(activitiListener).getFieldExtensions();
    verify(execution, atLeast(1)).getCurrentActivitiListener();
  }

  /**
   * Method under test: {@link DelegateHelper#getField(DelegateExecution, String)}
   */
  @Test
  public void testGetField5() {
    // Arrange
    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.getFieldName()).thenReturn("Field Name");

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(fieldExtension);
    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getFieldExtensions()).thenReturn(fieldExtensionList);
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getCurrentActivitiListener()).thenReturn(activitiListener);

    // Act
    DelegateHelper.getField(execution, "Field Name");

    // Assert
    verify(activitiListener).getFieldExtensions();
    verify(fieldExtension, atLeast(1)).getFieldName();
    verify(execution, atLeast(1)).getCurrentActivitiListener();
  }

  /**
   * Method under test: {@link DelegateHelper#getField(DelegateExecution, String)}
   */
  @Test
  public void testGetField6() {
    // Arrange
    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.getFieldName()).thenReturn("foo");

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(fieldExtension);
    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getFieldExtensions()).thenReturn(fieldExtensionList);
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getCurrentActivitiListener()).thenReturn(activitiListener);

    // Act
    FieldExtension actualField = DelegateHelper.getField(execution, "Field Name");

    // Assert
    verify(activitiListener).getFieldExtensions();
    verify(fieldExtension, atLeast(1)).getFieldName();
    verify(execution, atLeast(1)).getCurrentActivitiListener();
    assertNull(actualField);
  }

  /**
   * Method under test:
   * {@link DelegateHelper#getFlowElementField(DelegateExecution, String)}
   */
  @Test
  public void testGetFlowElementField() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> DelegateHelper.getFlowElementField(null, "Field Name"));
  }

  /**
   * Method under test:
   * {@link DelegateHelper#getListenerField(DelegateExecution, String)}
   */
  @Test
  public void testGetListenerField() {
    // Arrange
    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getCurrentActivitiListener()).thenReturn(new ActivitiListener());

    // Act
    FieldExtension actualListenerField = DelegateHelper.getListenerField(execution, "Field Name");

    // Assert
    verify(execution).getCurrentActivitiListener();
    assertNull(actualListenerField);
  }

  /**
   * Method under test:
   * {@link DelegateHelper#getListenerField(DelegateExecution, String)}
   */
  @Test
  public void testGetListenerField2() {
    // Arrange
    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getFieldExtensions()).thenReturn(new ArrayList<>());
    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getCurrentActivitiListener()).thenReturn(activitiListener);

    // Act
    FieldExtension actualListenerField = DelegateHelper.getListenerField(execution, "Field Name");

    // Assert
    verify(activitiListener).getFieldExtensions();
    verify(execution).getCurrentActivitiListener();
    assertNull(actualListenerField);
  }

  /**
   * Method under test:
   * {@link DelegateHelper#getListenerField(DelegateExecution, String)}
   */
  @Test
  public void testGetListenerField3() {
    // Arrange
    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(new FieldExtension());
    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getFieldExtensions()).thenReturn(fieldExtensionList);
    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getCurrentActivitiListener()).thenReturn(activitiListener);

    // Act
    FieldExtension actualListenerField = DelegateHelper.getListenerField(execution, "Field Name");

    // Assert
    verify(activitiListener).getFieldExtensions();
    verify(execution).getCurrentActivitiListener();
    assertNull(actualListenerField);
  }

  /**
   * Method under test:
   * {@link DelegateHelper#getListenerField(DelegateExecution, String)}
   */
  @Test
  public void testGetListenerField4() {
    // Arrange
    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.getFieldName()).thenReturn("Field Name");

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(fieldExtension);
    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getFieldExtensions()).thenReturn(fieldExtensionList);
    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getCurrentActivitiListener()).thenReturn(activitiListener);

    // Act
    DelegateHelper.getListenerField(execution, "Field Name");

    // Assert
    verify(activitiListener).getFieldExtensions();
    verify(fieldExtension, atLeast(1)).getFieldName();
    verify(execution).getCurrentActivitiListener();
  }

  /**
   * Method under test:
   * {@link DelegateHelper#getListenerField(DelegateExecution, String)}
   */
  @Test
  public void testGetListenerField5() {
    // Arrange
    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.getFieldName()).thenReturn("foo");

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(fieldExtension);
    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getFieldExtensions()).thenReturn(fieldExtensionList);
    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getCurrentActivitiListener()).thenReturn(activitiListener);

    // Act
    FieldExtension actualListenerField = DelegateHelper.getListenerField(execution, "Field Name");

    // Assert
    verify(activitiListener).getFieldExtensions();
    verify(fieldExtension, atLeast(1)).getFieldName();
    verify(execution).getCurrentActivitiListener();
    assertNull(actualListenerField);
  }

  /**
   * Method under test:
   * {@link DelegateHelper#createExpressionForField(FieldExtension)}
   */
  @Test
  public void testCreateExpressionForField() {
    // Arrange, Act and Assert
    assertTrue(DelegateHelper.createExpressionForField(new FieldExtension()) instanceof FixedValue);
  }

  /**
   * Method under test:
   * {@link DelegateHelper#createExpressionForField(FieldExtension)}
   */
  @Test
  public void testCreateExpressionForField2() {
    // Arrange
    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("");

    // Act and Assert
    assertTrue(DelegateHelper.createExpressionForField(fieldExtension) instanceof FixedValue);
  }

  /**
   * Method under test:
   * {@link DelegateHelper#createExpressionForField(FieldExtension)}
   */
  @Test
  public void testCreateExpressionForField3() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("foo", mock(BiFunction.class));

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExtensionElements(extensionElements);

    // Act and Assert
    assertTrue(DelegateHelper.createExpressionForField(fieldExtension) instanceof FixedValue);
  }

  /**
   * Method under test:
   * {@link DelegateHelper#getFieldExpression(DelegateExecution, String)}
   */
  @Test
  public void testGetFieldExpression() {
    // Arrange
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getCurrentActivitiListener()).thenReturn(new ActivitiListener());

    // Act
    Expression actualFieldExpression = DelegateHelper.getFieldExpression(execution, "Field Name");

    // Assert
    verify(execution, atLeast(1)).getCurrentActivitiListener();
    assertNull(actualFieldExpression);
  }

  /**
   * Method under test:
   * {@link DelegateHelper#getFieldExpression(DelegateExecution, String)}
   */
  @Test
  public void testGetFieldExpression2() {
    // Arrange
    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getFieldExtensions()).thenReturn(new ArrayList<>());
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getCurrentActivitiListener()).thenReturn(activitiListener);

    // Act
    Expression actualFieldExpression = DelegateHelper.getFieldExpression(execution, "Field Name");

    // Assert
    verify(activitiListener).getFieldExtensions();
    verify(execution, atLeast(1)).getCurrentActivitiListener();
    assertNull(actualFieldExpression);
  }

  /**
   * Method under test:
   * {@link DelegateHelper#getFieldExpression(DelegateExecution, String)}
   */
  @Test
  public void testGetFieldExpression3() {
    // Arrange
    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(new FieldExtension());
    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getFieldExtensions()).thenReturn(fieldExtensionList);
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getCurrentActivitiListener()).thenReturn(activitiListener);

    // Act
    Expression actualFieldExpression = DelegateHelper.getFieldExpression(execution, "Field Name");

    // Assert
    verify(activitiListener).getFieldExtensions();
    verify(execution, atLeast(1)).getCurrentActivitiListener();
    assertNull(actualFieldExpression);
  }

  /**
   * Method under test:
   * {@link DelegateHelper#getFieldExpression(DelegateExecution, String)}
   */
  @Test
  public void testGetFieldExpression4() {
    // Arrange
    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getFieldExtensions()).thenThrow(new ActivitiException("An error occurred"));
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getCurrentActivitiListener()).thenReturn(activitiListener);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> DelegateHelper.getFieldExpression(execution, "Field Name"));
    verify(activitiListener).getFieldExtensions();
    verify(execution, atLeast(1)).getCurrentActivitiListener();
  }

  /**
   * Method under test:
   * {@link DelegateHelper#getFieldExpression(DelegateExecution, String)}
   */
  @Test
  public void testGetFieldExpression5() {
    // Arrange
    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.getFieldName()).thenReturn("foo");

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(fieldExtension);
    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getFieldExtensions()).thenReturn(fieldExtensionList);
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getCurrentActivitiListener()).thenReturn(activitiListener);

    // Act
    Expression actualFieldExpression = DelegateHelper.getFieldExpression(execution, "Field Name");

    // Assert
    verify(activitiListener).getFieldExtensions();
    verify(fieldExtension, atLeast(1)).getFieldName();
    verify(execution, atLeast(1)).getCurrentActivitiListener();
    assertNull(actualFieldExpression);
  }

  /**
   * Method under test:
   * {@link DelegateHelper#getFieldExpression(DelegateExecution, String)}
   */
  @Test
  public void testGetFieldExpression6() {
    // Arrange
    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.getExpression()).thenReturn(null);
    when(fieldExtension.getStringValue()).thenReturn("42");
    when(fieldExtension.getFieldName()).thenReturn("Field Name");

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(fieldExtension);
    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getFieldExtensions()).thenReturn(fieldExtensionList);
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getCurrentActivitiListener()).thenReturn(activitiListener);

    // Act
    Expression actualFieldExpression = DelegateHelper.getFieldExpression(execution, "Field Name");

    // Assert
    verify(activitiListener).getFieldExtensions();
    verify(fieldExtension).getExpression();
    verify(fieldExtension, atLeast(1)).getFieldName();
    verify(fieldExtension).getStringValue();
    verify(execution, atLeast(1)).getCurrentActivitiListener();
    assertTrue(actualFieldExpression instanceof FixedValue);
    assertEquals("42", actualFieldExpression.getExpressionText());
  }

  /**
   * Method under test:
   * {@link DelegateHelper#getFieldExpression(DelegateExecution, String)}
   */
  @Test
  public void testGetFieldExpression7() {
    // Arrange
    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.getExpression()).thenReturn("");
    when(fieldExtension.getStringValue()).thenReturn("42");
    when(fieldExtension.getFieldName()).thenReturn("Field Name");

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(fieldExtension);
    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getFieldExtensions()).thenReturn(fieldExtensionList);
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getCurrentActivitiListener()).thenReturn(activitiListener);

    // Act
    Expression actualFieldExpression = DelegateHelper.getFieldExpression(execution, "Field Name");

    // Assert
    verify(activitiListener).getFieldExtensions();
    verify(fieldExtension).getExpression();
    verify(fieldExtension, atLeast(1)).getFieldName();
    verify(fieldExtension).getStringValue();
    verify(execution, atLeast(1)).getCurrentActivitiListener();
    assertTrue(actualFieldExpression instanceof FixedValue);
    assertEquals("42", actualFieldExpression.getExpressionText());
  }

  /**
   * Method under test:
   * {@link DelegateHelper#getFlowElementFieldExpression(DelegateExecution, String)}
   */
  @Test
  public void testGetFlowElementFieldExpression() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> DelegateHelper.getFlowElementFieldExpression(null, "Field Name"));
  }

  /**
   * Method under test:
   * {@link DelegateHelper#getListenerFieldExpression(DelegateExecution, String)}
   */
  @Test
  public void testGetListenerFieldExpression() {
    // Arrange
    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getCurrentActivitiListener()).thenReturn(new ActivitiListener());

    // Act
    Expression actualListenerFieldExpression = DelegateHelper.getListenerFieldExpression(execution, "Field Name");

    // Assert
    verify(execution).getCurrentActivitiListener();
    assertNull(actualListenerFieldExpression);
  }

  /**
   * Method under test:
   * {@link DelegateHelper#getListenerFieldExpression(DelegateExecution, String)}
   */
  @Test
  public void testGetListenerFieldExpression2() {
    // Arrange
    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getFieldExtensions()).thenReturn(new ArrayList<>());
    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getCurrentActivitiListener()).thenReturn(activitiListener);

    // Act
    Expression actualListenerFieldExpression = DelegateHelper.getListenerFieldExpression(execution, "Field Name");

    // Assert
    verify(activitiListener).getFieldExtensions();
    verify(execution).getCurrentActivitiListener();
    assertNull(actualListenerFieldExpression);
  }

  /**
   * Method under test:
   * {@link DelegateHelper#getListenerFieldExpression(DelegateExecution, String)}
   */
  @Test
  public void testGetListenerFieldExpression3() {
    // Arrange
    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(new FieldExtension());
    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getFieldExtensions()).thenReturn(fieldExtensionList);
    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getCurrentActivitiListener()).thenReturn(activitiListener);

    // Act
    Expression actualListenerFieldExpression = DelegateHelper.getListenerFieldExpression(execution, "Field Name");

    // Assert
    verify(activitiListener).getFieldExtensions();
    verify(execution).getCurrentActivitiListener();
    assertNull(actualListenerFieldExpression);
  }

  /**
   * Method under test:
   * {@link DelegateHelper#getListenerFieldExpression(DelegateExecution, String)}
   */
  @Test
  public void testGetListenerFieldExpression4() {
    // Arrange
    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.getFieldName()).thenReturn("foo");

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(fieldExtension);
    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getFieldExtensions()).thenReturn(fieldExtensionList);
    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getCurrentActivitiListener()).thenReturn(activitiListener);

    // Act
    Expression actualListenerFieldExpression = DelegateHelper.getListenerFieldExpression(execution, "Field Name");

    // Assert
    verify(activitiListener).getFieldExtensions();
    verify(fieldExtension, atLeast(1)).getFieldName();
    verify(execution).getCurrentActivitiListener();
    assertNull(actualListenerFieldExpression);
  }

  /**
   * Method under test:
   * {@link DelegateHelper#getListenerFieldExpression(DelegateExecution, String)}
   */
  @Test
  public void testGetListenerFieldExpression5() {
    // Arrange
    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.getExpression()).thenReturn(null);
    when(fieldExtension.getStringValue()).thenReturn("42");
    when(fieldExtension.getFieldName()).thenReturn("Field Name");

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(fieldExtension);
    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getFieldExtensions()).thenReturn(fieldExtensionList);
    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getCurrentActivitiListener()).thenReturn(activitiListener);

    // Act
    Expression actualListenerFieldExpression = DelegateHelper.getListenerFieldExpression(execution, "Field Name");

    // Assert
    verify(activitiListener).getFieldExtensions();
    verify(fieldExtension).getExpression();
    verify(fieldExtension, atLeast(1)).getFieldName();
    verify(fieldExtension).getStringValue();
    verify(execution).getCurrentActivitiListener();
    assertTrue(actualListenerFieldExpression instanceof FixedValue);
    assertEquals("42", actualListenerFieldExpression.getExpressionText());
  }

  /**
   * Method under test:
   * {@link DelegateHelper#getListenerFieldExpression(DelegateExecution, String)}
   */
  @Test
  public void testGetListenerFieldExpression6() {
    // Arrange
    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.getExpression()).thenReturn("");
    when(fieldExtension.getStringValue()).thenReturn("42");
    when(fieldExtension.getFieldName()).thenReturn("Field Name");

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(fieldExtension);
    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getFieldExtensions()).thenReturn(fieldExtensionList);
    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getCurrentActivitiListener()).thenReturn(activitiListener);

    // Act
    Expression actualListenerFieldExpression = DelegateHelper.getListenerFieldExpression(execution, "Field Name");

    // Assert
    verify(activitiListener).getFieldExtensions();
    verify(fieldExtension).getExpression();
    verify(fieldExtension, atLeast(1)).getFieldName();
    verify(fieldExtension).getStringValue();
    verify(execution).getCurrentActivitiListener();
    assertTrue(actualListenerFieldExpression instanceof FixedValue);
    assertEquals("42", actualListenerFieldExpression.getExpressionText());
  }
}
