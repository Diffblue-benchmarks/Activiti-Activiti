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
   * Test {@link DelegateHelper#getBpmnModel(DelegateExecution)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DelegateHelper#getBpmnModel(DelegateExecution)}
   */
  @Test
  public void testGetBpmnModel_whenNull_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> DelegateHelper.getBpmnModel(null));
  }

  /**
   * Test {@link DelegateHelper#getFlowElement(DelegateExecution)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DelegateHelper#getFlowElement(DelegateExecution)}
   */
  @Test
  public void testGetFlowElement_whenNull_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> DelegateHelper.getFlowElement(null));
  }

  /**
   * Test {@link DelegateHelper#isExecutingExecutionListener(DelegateExecution)}.
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor).</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateHelper#isExecutingExecutionListener(DelegateExecution)}
   */
  @Test
  public void testIsExecutingExecutionListener_givenActivitiListener_thenReturnTrue() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(new ActivitiListener());

    // Act and Assert
    assertTrue(DelegateHelper.isExecutingExecutionListener(execution));
  }

  /**
   * Test {@link DelegateHelper#isExecutingExecutionListener(DelegateExecution)}.
   * <ul>
   *   <li>Given {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateHelper#isExecutingExecutionListener(DelegateExecution)}
   */
  @Test
  public void testIsExecutingExecutionListener_givenDate() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setLockTime(mock(Date.class));

    // Act and Assert
    assertFalse(DelegateHelper.isExecutingExecutionListener(execution));
  }

  /**
   * Test {@link DelegateHelper#isExecutingExecutionListener(DelegateExecution)}.
   * <ul>
   *   <li>When createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateHelper#isExecutingExecutionListener(DelegateExecution)}
   */
  @Test
  public void testIsExecutingExecutionListener_whenCreateWithEmptyRelationshipCollections() {
    // Arrange, Act and Assert
    assertFalse(
        DelegateHelper.isExecutingExecutionListener(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link DelegateHelper#getExtensionElements(DelegateExecution)}.
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor).</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateHelper#getExtensionElements(DelegateExecution)}
   */
  @Test
  public void testGetExtensionElements_givenActivitiListener_thenReturnEmpty() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(new ActivitiListener());

    // Act
    Map<String, List<ExtensionElement>> actualExtensionElements = DelegateHelper.getExtensionElements(execution);

    // Assert
    assertTrue(actualExtensionElements.isEmpty());
  }

  /**
   * Test
   * {@link DelegateHelper#getFlowElementExtensionElements(DelegateExecution)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateHelper#getFlowElementExtensionElements(DelegateExecution)}
   */
  @Test
  public void testGetFlowElementExtensionElements_whenNull_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> DelegateHelper.getFlowElementExtensionElements(null));
  }

  /**
   * Test {@link DelegateHelper#getListenerExtensionElements(DelegateExecution)}.
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor).</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateHelper#getListenerExtensionElements(DelegateExecution)}
   */
  @Test
  public void testGetListenerExtensionElements_givenActivitiListener_thenReturnEmpty() {
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
   * Test {@link DelegateHelper#getFields(DelegateExecution)}.
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor).</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link DelegateHelper#getFields(DelegateExecution)}
   */
  @Test
  public void testGetFields_givenActivitiListener_thenReturnEmpty() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(new ActivitiListener());

    // Act
    List<FieldExtension> actualFields = DelegateHelper.getFields(execution);

    // Assert
    assertTrue(actualFields.isEmpty());
  }

  /**
   * Test {@link DelegateHelper#getFlowElementFields(DelegateExecution)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateHelper#getFlowElementFields(DelegateExecution)}
   */
  @Test
  public void testGetFlowElementFields_whenNull_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> DelegateHelper.getFlowElementFields(null));
  }

  /**
   * Test {@link DelegateHelper#getListenerFields(DelegateExecution)}.
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor).</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateHelper#getListenerFields(DelegateExecution)}
   */
  @Test
  public void testGetListenerFields_givenActivitiListener_thenReturnEmpty() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(new ActivitiListener());

    // Act
    List<FieldExtension> actualListenerFields = DelegateHelper.getListenerFields(execution);

    // Assert
    assertTrue(actualListenerFields.isEmpty());
  }

  /**
   * Test {@link DelegateHelper#getField(DelegateExecution, String)}.
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DelegateHelper#getField(DelegateExecution, String)}
   */
  @Test
  public void testGetField_givenActivitiListener() {
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
   * Test {@link DelegateHelper#getField(DelegateExecution, String)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link FieldExtension} (default
   * constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DelegateHelper#getField(DelegateExecution, String)}
   */
  @Test
  public void testGetField_givenArrayListAddFieldExtension_thenReturnNull() {
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
   * Test {@link DelegateHelper#getField(DelegateExecution, String)}.
   * <ul>
   *   <li>Given {@link FieldExtension} {@link FieldExtension#getFieldName()} return
   * {@code Field Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DelegateHelper#getField(DelegateExecution, String)}
   */
  @Test
  public void testGetField_givenFieldExtensionGetFieldNameReturnFieldName() {
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
   * Test {@link DelegateHelper#getField(DelegateExecution, String)}.
   * <ul>
   *   <li>Given {@link FieldExtension} {@link FieldExtension#getFieldName()} return
   * {@code foo}.</li>
   *   <li>Then calls {@link FieldExtension#getFieldName()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DelegateHelper#getField(DelegateExecution, String)}
   */
  @Test
  public void testGetField_givenFieldExtensionGetFieldNameReturnFoo_thenCallsGetFieldName() {
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
   * Test {@link DelegateHelper#getField(DelegateExecution, String)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DelegateHelper#getField(DelegateExecution, String)}
   */
  @Test
  public void testGetField_thenReturnNull() {
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
   * Test {@link DelegateHelper#getField(DelegateExecution, String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DelegateHelper#getField(DelegateExecution, String)}
   */
  @Test
  public void testGetField_thenThrowActivitiException() {
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
   * Test {@link DelegateHelper#getFlowElementField(DelegateExecution, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateHelper#getFlowElementField(DelegateExecution, String)}
   */
  @Test
  public void testGetFlowElementField_whenNull_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> DelegateHelper.getFlowElementField(null, "Field Name"));
  }

  /**
   * Test {@link DelegateHelper#getListenerField(DelegateExecution, String)}.
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateHelper#getListenerField(DelegateExecution, String)}
   */
  @Test
  public void testGetListenerField_givenActivitiListener() {
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
   * Test {@link DelegateHelper#getListenerField(DelegateExecution, String)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link FieldExtension} (default
   * constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateHelper#getListenerField(DelegateExecution, String)}
   */
  @Test
  public void testGetListenerField_givenArrayListAddFieldExtension_thenReturnNull() {
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
   * Test {@link DelegateHelper#getListenerField(DelegateExecution, String)}.
   * <ul>
   *   <li>Given {@link FieldExtension} {@link FieldExtension#getFieldName()} return
   * {@code Field Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateHelper#getListenerField(DelegateExecution, String)}
   */
  @Test
  public void testGetListenerField_givenFieldExtensionGetFieldNameReturnFieldName() {
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
   * Test {@link DelegateHelper#getListenerField(DelegateExecution, String)}.
   * <ul>
   *   <li>Given {@link FieldExtension} {@link FieldExtension#getFieldName()} return
   * {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateHelper#getListenerField(DelegateExecution, String)}
   */
  @Test
  public void testGetListenerField_givenFieldExtensionGetFieldNameReturnFoo() {
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
   * Test {@link DelegateHelper#getListenerField(DelegateExecution, String)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateHelper#getListenerField(DelegateExecution, String)}
   */
  @Test
  public void testGetListenerField_thenReturnNull() {
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
   * Test {@link DelegateHelper#createExpressionForField(FieldExtension)}.
   * <ul>
   *   <li>Given empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateHelper#createExpressionForField(FieldExtension)}
   */
  @Test
  public void testCreateExpressionForField_givenEmptyString() {
    // Arrange
    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("");

    // Act and Assert
    assertTrue(DelegateHelper.createExpressionForField(fieldExtension) instanceof FixedValue);
  }

  /**
   * Test {@link DelegateHelper#createExpressionForField(FieldExtension)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateHelper#createExpressionForField(FieldExtension)}
   */
  @Test
  public void testCreateExpressionForField_givenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("foo", mock(BiFunction.class));

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExtensionElements(extensionElements);

    // Act and Assert
    assertTrue(DelegateHelper.createExpressionForField(fieldExtension) instanceof FixedValue);
  }

  /**
   * Test {@link DelegateHelper#createExpressionForField(FieldExtension)}.
   * <ul>
   *   <li>When {@link FieldExtension} (default constructor).</li>
   *   <li>Then return {@link FixedValue}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateHelper#createExpressionForField(FieldExtension)}
   */
  @Test
  public void testCreateExpressionForField_whenFieldExtension_thenReturnFixedValue() {
    // Arrange, Act and Assert
    assertTrue(DelegateHelper.createExpressionForField(new FieldExtension()) instanceof FixedValue);
  }

  /**
   * Test {@link DelegateHelper#getFieldExpression(DelegateExecution, String)}
   * with {@code execution}, {@code fieldName}.
   * <p>
   * Method under test:
   * {@link DelegateHelper#getFieldExpression(DelegateExecution, String)}
   */
  @Test
  public void testGetFieldExpressionWithExecutionFieldName() {
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
   * Test {@link DelegateHelper#getFieldExpression(DelegateExecution, String)}
   * with {@code execution}, {@code fieldName}.
   * <p>
   * Method under test:
   * {@link DelegateHelper#getFieldExpression(DelegateExecution, String)}
   */
  @Test
  public void testGetFieldExpressionWithExecutionFieldName2() {
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
   * Test {@link DelegateHelper#getFieldExpression(DelegateExecution, String)}
   * with {@code execution}, {@code fieldName}.
   * <p>
   * Method under test:
   * {@link DelegateHelper#getFieldExpression(DelegateExecution, String)}
   */
  @Test
  public void testGetFieldExpressionWithExecutionFieldName3() {
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
   * Test {@link DelegateHelper#getFieldExpression(DelegateExecution, String)}
   * with {@code execution}, {@code fieldName}.
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateHelper#getFieldExpression(DelegateExecution, String)}
   */
  @Test
  public void testGetFieldExpressionWithExecutionFieldName_givenActivitiListener() {
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
   * Test {@link DelegateHelper#getFieldExpression(DelegateExecution, String)}
   * with {@code execution}, {@code fieldName}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link FieldExtension} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateHelper#getFieldExpression(DelegateExecution, String)}
   */
  @Test
  public void testGetFieldExpressionWithExecutionFieldName_givenArrayListAddFieldExtension() {
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
   * Test {@link DelegateHelper#getFieldExpression(DelegateExecution, String)}
   * with {@code execution}, {@code fieldName}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateHelper#getFieldExpression(DelegateExecution, String)}
   */
  @Test
  public void testGetFieldExpressionWithExecutionFieldName_thenReturnNull() {
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
   * Test {@link DelegateHelper#getFieldExpression(DelegateExecution, String)}
   * with {@code execution}, {@code fieldName}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateHelper#getFieldExpression(DelegateExecution, String)}
   */
  @Test
  public void testGetFieldExpressionWithExecutionFieldName_thenThrowActivitiException() {
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
   * Test
   * {@link DelegateHelper#getFlowElementFieldExpression(DelegateExecution, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateHelper#getFlowElementFieldExpression(DelegateExecution, String)}
   */
  @Test
  public void testGetFlowElementFieldExpression_whenNull_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> DelegateHelper.getFlowElementFieldExpression(null, "Field Name"));
  }

  /**
   * Test
   * {@link DelegateHelper#getListenerFieldExpression(DelegateExecution, String)}.
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateHelper#getListenerFieldExpression(DelegateExecution, String)}
   */
  @Test
  public void testGetListenerFieldExpression_givenActivitiListener() {
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
   * Test
   * {@link DelegateHelper#getListenerFieldExpression(DelegateExecution, String)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link FieldExtension} (default
   * constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateHelper#getListenerFieldExpression(DelegateExecution, String)}
   */
  @Test
  public void testGetListenerFieldExpression_givenArrayListAddFieldExtension_thenReturnNull() {
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
   * Test
   * {@link DelegateHelper#getListenerFieldExpression(DelegateExecution, String)}.
   * <ul>
   *   <li>Given {@link FieldExtension} {@link FieldExtension#getExpression()}
   * return empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateHelper#getListenerFieldExpression(DelegateExecution, String)}
   */
  @Test
  public void testGetListenerFieldExpression_givenFieldExtensionGetExpressionReturnEmptyString() {
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

  /**
   * Test
   * {@link DelegateHelper#getListenerFieldExpression(DelegateExecution, String)}.
   * <ul>
   *   <li>Given {@link FieldExtension} {@link FieldExtension#getExpression()}
   * return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateHelper#getListenerFieldExpression(DelegateExecution, String)}
   */
  @Test
  public void testGetListenerFieldExpression_givenFieldExtensionGetExpressionReturnNull() {
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
   * Test
   * {@link DelegateHelper#getListenerFieldExpression(DelegateExecution, String)}.
   * <ul>
   *   <li>Given {@link FieldExtension} {@link FieldExtension#getFieldName()} return
   * {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateHelper#getListenerFieldExpression(DelegateExecution, String)}
   */
  @Test
  public void testGetListenerFieldExpression_givenFieldExtensionGetFieldNameReturnFoo() {
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
   * Test
   * {@link DelegateHelper#getListenerFieldExpression(DelegateExecution, String)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateHelper#getListenerFieldExpression(DelegateExecution, String)}
   */
  @Test
  public void testGetListenerFieldExpression_thenReturnNull() {
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
}
