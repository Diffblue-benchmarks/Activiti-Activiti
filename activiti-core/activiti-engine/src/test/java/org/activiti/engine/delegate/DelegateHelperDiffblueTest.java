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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.FieldExtension;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class DelegateHelperDiffblueTest {
  /**
   * Test {@link DelegateHelper#getBpmnModel(DelegateExecution)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#getBpmnModel(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.bpmn.model.BpmnModel DelegateHelper.getBpmnModel(DelegateExecution)"
  })
  public void testGetBpmnModel_whenNull_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> DelegateHelper.getBpmnModel(null));
  }

  /**
   * Test {@link DelegateHelper#getFlowElement(DelegateExecution)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#getFlowElement(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.bpmn.model.FlowElement DelegateHelper.getFlowElement(DelegateExecution)"
  })
  public void testGetFlowElement_whenNull_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> DelegateHelper.getFlowElement(null));
  }

  /**
   * Test {@link DelegateHelper#isExecutingExecutionListener(DelegateExecution)}.
   *
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor).
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#isExecutingExecutionListener(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DelegateHelper.isExecutingExecutionListener(DelegateExecution)"})
  public void testIsExecutingExecutionListener_givenActivitiListener_thenReturnTrue() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(new ActivitiListener());

    // Act and Assert
    assertTrue(DelegateHelper.isExecutingExecutionListener(execution));
  }

  /**
   * Test {@link DelegateHelper#isExecutingExecutionListener(DelegateExecution)}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#isExecutingExecutionListener(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DelegateHelper.isExecutingExecutionListener(DelegateExecution)"})
  public void testIsExecutingExecutionListener_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(
        DelegateHelper.isExecutingExecutionListener(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link DelegateHelper#getExtensionElements(DelegateExecution)}.
   *
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor).
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#getExtensionElements(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map DelegateHelper.getExtensionElements(DelegateExecution)"})
  public void testGetExtensionElements_givenActivitiListener_thenReturnEmpty() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(new ActivitiListener());

    // Act
    Map<String, List<ExtensionElement>> actualExtensionElements =
        DelegateHelper.getExtensionElements(execution);

    // Assert
    assertTrue(actualExtensionElements.isEmpty());
  }

  /**
   * Test {@link DelegateHelper#getFlowElementExtensionElements(DelegateExecution)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#getFlowElementExtensionElements(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map DelegateHelper.getFlowElementExtensionElements(DelegateExecution)"})
  public void testGetFlowElementExtensionElements_whenNull_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class, () -> DelegateHelper.getFlowElementExtensionElements(null));
  }

  /**
   * Test {@link DelegateHelper#getListenerExtensionElements(DelegateExecution)}.
   *
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor).
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#getListenerExtensionElements(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map DelegateHelper.getListenerExtensionElements(DelegateExecution)"})
  public void testGetListenerExtensionElements_givenActivitiListener_thenReturnEmpty() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(new ActivitiListener());

    // Act
    Map<String, List<ExtensionElement>> actualListenerExtensionElements =
        DelegateHelper.getListenerExtensionElements(execution);

    // Assert
    assertTrue(actualListenerExtensionElements.isEmpty());
  }

  /**
   * Test {@link DelegateHelper#getFields(DelegateExecution)}.
   *
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor).
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#getFields(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List DelegateHelper.getFields(DelegateExecution)"})
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
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#getFlowElementFields(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List DelegateHelper.getFlowElementFields(DelegateExecution)"})
  public void testGetFlowElementFields_whenNull_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> DelegateHelper.getFlowElementFields(null));
  }

  /**
   * Test {@link DelegateHelper#getListenerFields(DelegateExecution)}.
   *
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor).
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#getListenerFields(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List DelegateHelper.getListenerFields(DelegateExecution)"})
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
   *
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#getField(DelegateExecution, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FieldExtension DelegateHelper.getField(DelegateExecution, String)"})
  public void testGetField_givenActivitiListener() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(new ActivitiListener());

    // Act and Assert
    assertNull(DelegateHelper.getField(execution, "Field Name"));
  }

  /**
   * Test {@link DelegateHelper#getField(DelegateExecution, String)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link FieldExtension} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#getField(DelegateExecution, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FieldExtension DelegateHelper.getField(DelegateExecution, String)"})
  public void testGetField_givenArrayListAddFieldExtension_thenReturnNull() {
    // Arrange
    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(new FieldExtension());

    ActivitiListener currentActivitiListener = mock(ActivitiListener.class);
    when(currentActivitiListener.getFieldExtensions()).thenReturn(fieldExtensionList);

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(currentActivitiListener);

    // Act
    FieldExtension actualField = DelegateHelper.getField(execution, "Field Name");

    // Assert
    verify(currentActivitiListener).getFieldExtensions();
    assertNull(actualField);
  }

  /**
   * Test {@link DelegateHelper#getField(DelegateExecution, String)}.
   *
   * <ul>
   *   <li>Given {@link FieldExtension} {@link FieldExtension#getFieldName()} return {@code Field
   *       Name}.
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#getField(DelegateExecution, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FieldExtension DelegateHelper.getField(DelegateExecution, String)"})
  public void testGetField_givenFieldExtensionGetFieldNameReturnFieldName() {
    // Arrange
    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.getFieldName()).thenReturn("Field Name");

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(fieldExtension);

    ActivitiListener currentActivitiListener = mock(ActivitiListener.class);
    when(currentActivitiListener.getFieldExtensions()).thenReturn(fieldExtensionList);

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(currentActivitiListener);

    // Act
    DelegateHelper.getField(execution, "Field Name");

    // Assert
    verify(currentActivitiListener).getFieldExtensions();
    verify(fieldExtension, atLeast(1)).getFieldName();
  }

  /**
   * Test {@link DelegateHelper#getField(DelegateExecution, String)}.
   *
   * <ul>
   *   <li>Given {@link FieldExtension} {@link FieldExtension#getFieldName()} return {@code foo}.
   *   <li>Then calls {@link FieldExtension#getFieldName()}.
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#getField(DelegateExecution, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FieldExtension DelegateHelper.getField(DelegateExecution, String)"})
  public void testGetField_givenFieldExtensionGetFieldNameReturnFoo_thenCallsGetFieldName() {
    // Arrange
    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.getFieldName()).thenReturn("foo");

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(fieldExtension);

    ActivitiListener currentActivitiListener = mock(ActivitiListener.class);
    when(currentActivitiListener.getFieldExtensions()).thenReturn(fieldExtensionList);

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(currentActivitiListener);

    // Act
    FieldExtension actualField = DelegateHelper.getField(execution, "Field Name");

    // Assert
    verify(currentActivitiListener).getFieldExtensions();
    verify(fieldExtension, atLeast(1)).getFieldName();
    assertNull(actualField);
  }

  /**
   * Test {@link DelegateHelper#getField(DelegateExecution, String)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#getField(DelegateExecution, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FieldExtension DelegateHelper.getField(DelegateExecution, String)"})
  public void testGetField_thenReturnNull() {
    // Arrange
    ActivitiListener currentActivitiListener = mock(ActivitiListener.class);
    when(currentActivitiListener.getFieldExtensions()).thenReturn(new ArrayList<>());

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(currentActivitiListener);

    // Act
    FieldExtension actualField = DelegateHelper.getField(execution, "Field Name");

    // Assert
    verify(currentActivitiListener).getFieldExtensions();
    assertNull(actualField);
  }

  /**
   * Test {@link DelegateHelper#getField(DelegateExecution, String)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#getField(DelegateExecution, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FieldExtension DelegateHelper.getField(DelegateExecution, String)"})
  public void testGetField_thenThrowActivitiException() {
    // Arrange
    ActivitiListener currentActivitiListener = mock(ActivitiListener.class);
    when(currentActivitiListener.getFieldExtensions())
        .thenThrow(new ActivitiException("An error occurred"));

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(currentActivitiListener);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> DelegateHelper.getField(execution, "Field Name"));
    verify(currentActivitiListener).getFieldExtensions();
  }

  /**
   * Test {@link DelegateHelper#getFlowElementField(DelegateExecution, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#getFlowElementField(DelegateExecution, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FieldExtension DelegateHelper.getFlowElementField(DelegateExecution, String)"
  })
  public void testGetFlowElementField_whenNull_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class, () -> DelegateHelper.getFlowElementField(null, "Field Name"));
  }

  /**
   * Test {@link DelegateHelper#getListenerField(DelegateExecution, String)}.
   *
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor) FieldExtensions is {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#getListenerField(DelegateExecution, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FieldExtension DelegateHelper.getListenerField(DelegateExecution, String)"})
  public void testGetListenerField_givenActivitiListenerFieldExtensionsIsArrayList() {
    // Arrange
    ActivitiListener currentActivitiListener = new ActivitiListener();
    currentActivitiListener.setFieldExtensions(new ArrayList<>());

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(currentActivitiListener);

    // Act and Assert
    assertNull(DelegateHelper.getListenerField(execution, "Field Name"));
  }

  /**
   * Test {@link DelegateHelper#getListenerField(DelegateExecution, String)}.
   *
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor) FieldExtensions is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#getListenerField(DelegateExecution, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FieldExtension DelegateHelper.getListenerField(DelegateExecution, String)"})
  public void testGetListenerField_givenActivitiListenerFieldExtensionsIsNull() {
    // Arrange
    ActivitiListener currentActivitiListener = new ActivitiListener();
    currentActivitiListener.setFieldExtensions(null);

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(currentActivitiListener);

    // Act and Assert
    assertNull(DelegateHelper.getListenerField(execution, "Field Name"));
  }

  /**
   * Test {@link DelegateHelper#getListenerField(DelegateExecution, String)}.
   *
   * <ul>
   *   <li>Given {@link ActivitiListener} {@link ActivitiListener#getFieldExtensions()} return
   *       {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#getListenerField(DelegateExecution, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FieldExtension DelegateHelper.getListenerField(DelegateExecution, String)"})
  public void testGetListenerField_givenActivitiListenerGetFieldExtensionsReturnNull() {
    // Arrange
    ActivitiListener currentActivitiListener = mock(ActivitiListener.class);
    when(currentActivitiListener.getFieldExtensions()).thenReturn(null);
    doNothing()
        .when(currentActivitiListener)
        .setFieldExtensions(Mockito.<List<FieldExtension>>any());
    currentActivitiListener.setFieldExtensions(null);

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(currentActivitiListener);

    // Act
    FieldExtension actualListenerField = DelegateHelper.getListenerField(execution, "Field Name");

    // Assert
    verify(currentActivitiListener).getFieldExtensions();
    verify(currentActivitiListener).setFieldExtensions(isNull());
    assertNull(actualListenerField);
  }

  /**
   * Test {@link DelegateHelper#getListenerField(DelegateExecution, String)}.
   *
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor) FieldName is {@code Execution}.
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#getListenerField(DelegateExecution, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FieldExtension DelegateHelper.getListenerField(DelegateExecution, String)"})
  public void testGetListenerField_givenFieldExtensionFieldNameIsExecution() {
    // Arrange
    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setFieldName("Execution");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    ActivitiListener currentActivitiListener = new ActivitiListener();
    currentActivitiListener.setFieldExtensions(fieldExtensions);

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(currentActivitiListener);

    // Act and Assert
    assertNull(DelegateHelper.getListenerField(execution, "Field Name"));
  }

  /**
   * Test {@link DelegateHelper#getListenerField(DelegateExecution, String)}.
   *
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor) FieldName is {@code Field Name}.
   *   <li>Then return {@code Field Name}.
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#getListenerField(DelegateExecution, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FieldExtension DelegateHelper.getListenerField(DelegateExecution, String)"})
  public void testGetListenerField_givenFieldExtensionFieldNameIsFieldName_thenReturnFieldName() {
    // Arrange
    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setFieldName("Field Name");

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(fieldExtension);

    ActivitiListener currentActivitiListener = mock(ActivitiListener.class);
    when(currentActivitiListener.getFieldExtensions()).thenReturn(fieldExtensionList);
    doNothing()
        .when(currentActivitiListener)
        .setFieldExtensions(Mockito.<List<FieldExtension>>any());
    currentActivitiListener.setFieldExtensions(null);

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(currentActivitiListener);

    // Act
    FieldExtension actualListenerField = DelegateHelper.getListenerField(execution, "Field Name");

    // Assert
    verify(currentActivitiListener).getFieldExtensions();
    verify(currentActivitiListener).setFieldExtensions(isNull());
    assertEquals("Field Name", actualListenerField.getFieldName());
    assertNull(actualListenerField.getId());
    assertNull(actualListenerField.getExpression());
    assertNull(actualListenerField.getStringValue());
    assertEquals(0, actualListenerField.getXmlColumnNumber());
    assertEquals(0, actualListenerField.getXmlRowNumber());
    assertTrue(actualListenerField.getAttributes().isEmpty());
    assertTrue(actualListenerField.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link DelegateHelper#getListenerField(DelegateExecution, String)}.
   *
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor) FieldName is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#getListenerField(DelegateExecution, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FieldExtension DelegateHelper.getListenerField(DelegateExecution, String)"})
  public void testGetListenerField_givenFieldExtensionFieldNameIsNull() {
    // Arrange
    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setFieldName(null);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    ActivitiListener currentActivitiListener = new ActivitiListener();
    currentActivitiListener.setFieldExtensions(fieldExtensions);

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(currentActivitiListener);

    // Act and Assert
    assertNull(DelegateHelper.getListenerField(execution, "Field Name"));
  }

  /**
   * Test {@link DelegateHelper#createExpressionForField(FieldExtension)}.
   *
   * <ul>
   *   <li>Given empty string.
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#createExpressionForField(FieldExtension)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Expression DelegateHelper.createExpressionForField(FieldExtension)"})
  public void testCreateExpressionForField_givenEmptyString() {
    // Arrange
    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("");

    // Act and Assert
    assertTrue(DelegateHelper.createExpressionForField(fieldExtension) instanceof FixedValue);
  }

  /**
   * Test {@link DelegateHelper#createExpressionForField(FieldExtension)}.
   *
   * <ul>
   *   <li>When {@link FieldExtension} (default constructor).
   *   <li>Then return {@link FixedValue}.
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#createExpressionForField(FieldExtension)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Expression DelegateHelper.createExpressionForField(FieldExtension)"})
  public void testCreateExpressionForField_whenFieldExtension_thenReturnFixedValue() {
    // Arrange, Act and Assert
    assertTrue(DelegateHelper.createExpressionForField(new FieldExtension()) instanceof FixedValue);
  }

  /**
   * Test {@link DelegateHelper#getFieldExpression(DelegateExecution, String)} with {@code
   * execution}, {@code fieldName}.
   *
   * <p>Method under test: {@link DelegateHelper#getFieldExpression(DelegateExecution, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Expression DelegateHelper.getFieldExpression(DelegateExecution, String)"})
  public void testGetFieldExpressionWithExecutionFieldName() {
    // Arrange
    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setFieldName("Execution");
    fieldExtension.setExpression("not empty");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    ActivitiListener currentActivitiListener = new ActivitiListener();
    currentActivitiListener.setFieldExtensions(fieldExtensions);

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(currentActivitiListener);

    // Act and Assert
    assertNull(DelegateHelper.getFieldExpression(execution, "Field Name"));
  }

  /**
   * Test {@link DelegateHelper#getFieldExpression(DelegateExecution, String)} with {@code
   * execution}, {@code fieldName}.
   *
   * <p>Method under test: {@link DelegateHelper#getFieldExpression(DelegateExecution, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Expression DelegateHelper.getFieldExpression(DelegateExecution, String)"})
  public void testGetFieldExpressionWithExecutionFieldName2() {
    // Arrange
    ActivitiListener currentActivitiListener = new ActivitiListener();
    currentActivitiListener.setFieldExtensions(null);

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(currentActivitiListener);

    // Act and Assert
    assertNull(DelegateHelper.getFieldExpression(execution, "Field Name"));
  }

  /**
   * Test {@link DelegateHelper#getFieldExpression(DelegateExecution, String)} with {@code
   * execution}, {@code fieldName}.
   *
   * <p>Method under test: {@link DelegateHelper#getFieldExpression(DelegateExecution, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Expression DelegateHelper.getFieldExpression(DelegateExecution, String)"})
  public void testGetFieldExpressionWithExecutionFieldName3() {
    // Arrange
    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.getExpression()).thenReturn("");
    when(fieldExtension.getStringValue()).thenReturn("42");
    when(fieldExtension.getFieldName()).thenReturn("Field Name");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    ActivitiListener currentActivitiListener = new ActivitiListener();
    currentActivitiListener.setFieldExtensions(fieldExtensions);

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(currentActivitiListener);

    // Act
    Expression actualFieldExpression = DelegateHelper.getFieldExpression(execution, "Field Name");

    // Assert
    verify(fieldExtension).getExpression();
    verify(fieldExtension, atLeast(1)).getFieldName();
    verify(fieldExtension).getStringValue();
    assertTrue(actualFieldExpression instanceof FixedValue);
    assertEquals("42", actualFieldExpression.getExpressionText());
  }

  /**
   * Test {@link DelegateHelper#getFieldExpression(DelegateExecution, String)} with {@code
   * execution}, {@code fieldName}.
   *
   * <p>Method under test: {@link DelegateHelper#getFieldExpression(DelegateExecution, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Expression DelegateHelper.getFieldExpression(DelegateExecution, String)"})
  public void testGetFieldExpressionWithExecutionFieldName4() {
    // Arrange
    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.getExpression()).thenReturn(null);
    when(fieldExtension.getStringValue()).thenReturn("42");
    when(fieldExtension.getFieldName()).thenReturn("Field Name");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    ActivitiListener currentActivitiListener = new ActivitiListener();
    currentActivitiListener.setFieldExtensions(fieldExtensions);

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(currentActivitiListener);

    // Act
    Expression actualFieldExpression = DelegateHelper.getFieldExpression(execution, "Field Name");

    // Assert
    verify(fieldExtension).getExpression();
    verify(fieldExtension, atLeast(1)).getFieldName();
    verify(fieldExtension).getStringValue();
    assertTrue(actualFieldExpression instanceof FixedValue);
    assertEquals("42", actualFieldExpression.getExpressionText());
  }

  /**
   * Test {@link DelegateHelper#getFieldExpression(DelegateExecution, String)} with {@code
   * execution}, {@code fieldName}.
   *
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#getFieldExpression(DelegateExecution, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Expression DelegateHelper.getFieldExpression(DelegateExecution, String)"})
  public void testGetFieldExpressionWithExecutionFieldName_givenActivitiListener() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(new ActivitiListener());

    // Act and Assert
    assertNull(DelegateHelper.getFieldExpression(execution, "Field Name"));
  }

  /**
   * Test {@link DelegateHelper#getFieldExpression(DelegateExecution, String)} with {@code
   * execution}, {@code fieldName}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link FieldExtension} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#getFieldExpression(DelegateExecution, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Expression DelegateHelper.getFieldExpression(DelegateExecution, String)"})
  public void testGetFieldExpressionWithExecutionFieldName_givenArrayListAddFieldExtension() {
    // Arrange
    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener currentActivitiListener = new ActivitiListener();
    currentActivitiListener.setFieldExtensions(fieldExtensions);

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(currentActivitiListener);

    // Act and Assert
    assertNull(DelegateHelper.getFieldExpression(execution, "Field Name"));
  }

  /**
   * Test {@link DelegateHelper#getFieldExpression(DelegateExecution, String)} with {@code
   * execution}, {@code fieldName}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiListener#getFieldExtensions()}.
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#getFieldExpression(DelegateExecution, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Expression DelegateHelper.getFieldExpression(DelegateExecution, String)"})
  public void testGetFieldExpressionWithExecutionFieldName_thenCallsGetFieldExtensions() {
    // Arrange
    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(mock(FieldExtension.class));

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(new FieldExtension());

    ActivitiListener currentActivitiListener = mock(ActivitiListener.class);
    when(currentActivitiListener.getFieldExtensions()).thenReturn(fieldExtensionList);
    doNothing()
        .when(currentActivitiListener)
        .setFieldExtensions(Mockito.<List<FieldExtension>>any());
    currentActivitiListener.setFieldExtensions(fieldExtensions);

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(currentActivitiListener);

    // Act
    Expression actualFieldExpression = DelegateHelper.getFieldExpression(execution, "Field Name");

    // Assert
    verify(currentActivitiListener).getFieldExtensions();
    verify(currentActivitiListener).setFieldExtensions(isA(List.class));
    assertNull(actualFieldExpression);
  }

  /**
   * Test {@link DelegateHelper#getFlowElementFieldExpression(DelegateExecution, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#getFlowElementFieldExpression(DelegateExecution,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Expression DelegateHelper.getFlowElementFieldExpression(DelegateExecution, String)"
  })
  public void testGetFlowElementFieldExpression_whenNull_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> DelegateHelper.getFlowElementFieldExpression(null, "Field Name"));
  }

  /**
   * Test {@link DelegateHelper#getListenerFieldExpression(DelegateExecution, String)}.
   *
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor) FieldExtensions is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#getListenerFieldExpression(DelegateExecution,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Expression DelegateHelper.getListenerFieldExpression(DelegateExecution, String)"
  })
  public void testGetListenerFieldExpression_givenActivitiListenerFieldExtensionsIsNull() {
    // Arrange
    ActivitiListener currentActivitiListener = new ActivitiListener();
    currentActivitiListener.setFieldExtensions(null);

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(currentActivitiListener);

    // Act and Assert
    assertNull(DelegateHelper.getListenerFieldExpression(execution, "Field Name"));
  }

  /**
   * Test {@link DelegateHelper#getListenerFieldExpression(DelegateExecution, String)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link FieldExtension} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#getListenerFieldExpression(DelegateExecution,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Expression DelegateHelper.getListenerFieldExpression(DelegateExecution, String)"
  })
  public void testGetListenerFieldExpression_givenArrayListAddFieldExtension_thenReturnNull() {
    // Arrange
    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener currentActivitiListener = new ActivitiListener();
    currentActivitiListener.setFieldExtensions(fieldExtensions);

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(currentActivitiListener);

    // Act and Assert
    assertNull(DelegateHelper.getListenerFieldExpression(execution, "Field Name"));
  }

  /**
   * Test {@link DelegateHelper#getListenerFieldExpression(DelegateExecution, String)}.
   *
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor) FieldName is {@code Execution}.
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#getListenerFieldExpression(DelegateExecution,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Expression DelegateHelper.getListenerFieldExpression(DelegateExecution, String)"
  })
  public void testGetListenerFieldExpression_givenFieldExtensionFieldNameIsExecution() {
    // Arrange
    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setFieldName("Execution");
    fieldExtension.setExpression("not empty");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    ActivitiListener currentActivitiListener = new ActivitiListener();
    currentActivitiListener.setFieldExtensions(fieldExtensions);

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(currentActivitiListener);

    // Act and Assert
    assertNull(DelegateHelper.getListenerFieldExpression(execution, "Field Name"));
  }

  /**
   * Test {@link DelegateHelper#getListenerFieldExpression(DelegateExecution, String)}.
   *
   * <ul>
   *   <li>Given {@link FieldExtension} {@link FieldExtension#getExpression()} return empty string.
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#getListenerFieldExpression(DelegateExecution,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Expression DelegateHelper.getListenerFieldExpression(DelegateExecution, String)"
  })
  public void testGetListenerFieldExpression_givenFieldExtensionGetExpressionReturnEmptyString() {
    // Arrange
    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.getExpression()).thenReturn("");
    when(fieldExtension.getStringValue()).thenReturn("42");
    when(fieldExtension.getFieldName()).thenReturn("Field Name");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    ActivitiListener currentActivitiListener = new ActivitiListener();
    currentActivitiListener.setFieldExtensions(fieldExtensions);

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(currentActivitiListener);

    // Act
    Expression actualListenerFieldExpression =
        DelegateHelper.getListenerFieldExpression(execution, "Field Name");

    // Assert
    verify(fieldExtension).getExpression();
    verify(fieldExtension, atLeast(1)).getFieldName();
    verify(fieldExtension).getStringValue();
    assertTrue(actualListenerFieldExpression instanceof FixedValue);
    assertEquals("42", actualListenerFieldExpression.getExpressionText());
  }

  /**
   * Test {@link DelegateHelper#getListenerFieldExpression(DelegateExecution, String)}.
   *
   * <ul>
   *   <li>Given {@link FieldExtension} {@link FieldExtension#getExpression()} return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#getListenerFieldExpression(DelegateExecution,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Expression DelegateHelper.getListenerFieldExpression(DelegateExecution, String)"
  })
  public void testGetListenerFieldExpression_givenFieldExtensionGetExpressionReturnNull() {
    // Arrange
    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.getExpression()).thenReturn(null);
    when(fieldExtension.getStringValue()).thenReturn("42");
    when(fieldExtension.getFieldName()).thenReturn("Field Name");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    ActivitiListener currentActivitiListener = new ActivitiListener();
    currentActivitiListener.setFieldExtensions(fieldExtensions);

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(currentActivitiListener);

    // Act
    Expression actualListenerFieldExpression =
        DelegateHelper.getListenerFieldExpression(execution, "Field Name");

    // Assert
    verify(fieldExtension).getExpression();
    verify(fieldExtension, atLeast(1)).getFieldName();
    verify(fieldExtension).getStringValue();
    assertTrue(actualListenerFieldExpression instanceof FixedValue);
    assertEquals("42", actualListenerFieldExpression.getExpressionText());
  }

  /**
   * Test {@link DelegateHelper#getListenerFieldExpression(DelegateExecution, String)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiListener#getFieldExtensions()}.
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#getListenerFieldExpression(DelegateExecution,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Expression DelegateHelper.getListenerFieldExpression(DelegateExecution, String)"
  })
  public void testGetListenerFieldExpression_thenCallsGetFieldExtensions() {
    // Arrange
    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(mock(FieldExtension.class));

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(new FieldExtension());

    ActivitiListener currentActivitiListener = mock(ActivitiListener.class);
    when(currentActivitiListener.getFieldExtensions()).thenReturn(fieldExtensionList);
    doNothing()
        .when(currentActivitiListener)
        .setFieldExtensions(Mockito.<List<FieldExtension>>any());
    currentActivitiListener.setFieldExtensions(fieldExtensions);

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(currentActivitiListener);

    // Act
    Expression actualListenerFieldExpression =
        DelegateHelper.getListenerFieldExpression(execution, "Field Name");

    // Assert
    verify(currentActivitiListener).getFieldExtensions();
    verify(currentActivitiListener).setFieldExtensions(isA(List.class));
    assertNull(actualListenerFieldExpression);
  }

  /**
   * Test {@link DelegateHelper#getListenerFieldExpression(DelegateExecution, String)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DelegateHelper#getListenerFieldExpression(DelegateExecution,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Expression DelegateHelper.getListenerFieldExpression(DelegateExecution, String)"
  })
  public void testGetListenerFieldExpression_thenReturnNull() {
    // Arrange
    ActivitiListener currentActivitiListener = new ActivitiListener();
    currentActivitiListener.setFieldExtensions(new ArrayList<>());

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setCurrentActivitiListener(currentActivitiListener);

    // Act and Assert
    assertNull(DelegateHelper.getListenerFieldExpression(execution, "Field Name"));
  }
}
