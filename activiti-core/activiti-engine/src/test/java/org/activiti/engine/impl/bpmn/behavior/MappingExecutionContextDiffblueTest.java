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
package org.activiti.engine.impl.bpmn.behavior;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.sql.Date;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MappingExecutionContextDiffblueTest {
  @InjectMocks
  private MappingExecutionContext mappingExecutionContext;

  /**
   * Method under test: {@link MappingExecutionContext#hasExecution()}
   */
  @Test
  public void testHasExecution() {
    // Arrange, Act and Assert
    assertFalse(MappingExecutionContext.buildMappingExecutionContext("42", "42").hasExecution());
    assertTrue(MappingExecutionContext
        .buildMappingExecutionContext(ExecutionEntityImpl.createWithEmptyRelationshipCollections())
        .hasExecution());
  }

  /**
   * Method under test:
   * {@link MappingExecutionContext#buildMappingExecutionContext(DelegateExecution)}
   */
  @Test
  public void testBuildMappingExecutionContext() {
    // Arrange
    ExecutionEntityImpl delegateExecution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    MappingExecutionContext actualBuildMappingExecutionContextResult = MappingExecutionContext
        .buildMappingExecutionContext(delegateExecution);

    // Assert
    assertNull(actualBuildMappingExecutionContextResult.getActivityId());
    assertNull(actualBuildMappingExecutionContextResult.getProcessDefinitionId());
    assertTrue(actualBuildMappingExecutionContextResult.hasExecution());
    assertSame(delegateExecution, actualBuildMappingExecutionContextResult.getExecution());
  }

  /**
   * Method under test:
   * {@link MappingExecutionContext#buildMappingExecutionContext(DelegateExecution)}
   */
  @Test
  public void testBuildMappingExecutionContext2() {
    // Arrange
    ExecutionEntityImpl delegateExecution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    delegateExecution.setLockTime(mock(Date.class));

    // Act
    MappingExecutionContext actualBuildMappingExecutionContextResult = MappingExecutionContext
        .buildMappingExecutionContext(delegateExecution);

    // Assert
    assertNull(actualBuildMappingExecutionContextResult.getActivityId());
    assertNull(actualBuildMappingExecutionContextResult.getProcessDefinitionId());
    assertTrue(actualBuildMappingExecutionContextResult.hasExecution());
    assertSame(delegateExecution, actualBuildMappingExecutionContextResult.getExecution());
  }

  /**
   * Method under test:
   * {@link MappingExecutionContext#buildMappingExecutionContext(String, String)}
   */
  @Test
  public void testBuildMappingExecutionContext3() {
    // Arrange and Act
    MappingExecutionContext actualBuildMappingExecutionContextResult = MappingExecutionContext
        .buildMappingExecutionContext("42", "42");

    // Assert
    assertEquals("42", actualBuildMappingExecutionContextResult.getActivityId());
    assertEquals("42", actualBuildMappingExecutionContextResult.getProcessDefinitionId());
    assertNull(actualBuildMappingExecutionContextResult.getExecution());
    assertFalse(actualBuildMappingExecutionContextResult.hasExecution());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MappingExecutionContext#equals(Object)}
   *   <li>{@link MappingExecutionContext#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    MappingExecutionContext buildMappingExecutionContextResult = MappingExecutionContext
        .buildMappingExecutionContext("42", "42");
    MappingExecutionContext buildMappingExecutionContextResult2 = MappingExecutionContext
        .buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertEquals(buildMappingExecutionContextResult, buildMappingExecutionContextResult2);
    int expectedHashCodeResult = buildMappingExecutionContextResult.hashCode();
    assertEquals(expectedHashCodeResult, buildMappingExecutionContextResult2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MappingExecutionContext#equals(Object)}
   *   <li>{@link MappingExecutionContext#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    ExecutionEntityImpl delegateExecution = mock(ExecutionEntityImpl.class);
    when(delegateExecution.getCurrentActivityId()).thenReturn("42");
    when(delegateExecution.getProcessDefinitionId()).thenReturn("42");
    MappingExecutionContext buildMappingExecutionContextResult = MappingExecutionContext
        .buildMappingExecutionContext(delegateExecution);
    MappingExecutionContext buildMappingExecutionContextResult2 = MappingExecutionContext
        .buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertEquals(buildMappingExecutionContextResult, buildMappingExecutionContextResult2);
    int expectedHashCodeResult = buildMappingExecutionContextResult.hashCode();
    assertEquals(expectedHashCodeResult, buildMappingExecutionContextResult2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MappingExecutionContext#equals(Object)}
   *   <li>{@link MappingExecutionContext#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    MappingExecutionContext buildMappingExecutionContextResult = MappingExecutionContext
        .buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertEquals(buildMappingExecutionContextResult, buildMappingExecutionContextResult);
    int expectedHashCodeResult = buildMappingExecutionContextResult.hashCode();
    assertEquals(expectedHashCodeResult, buildMappingExecutionContextResult.hashCode());
  }

  /**
   * Method under test: {@link MappingExecutionContext#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    MappingExecutionContext buildMappingExecutionContextResult = MappingExecutionContext
        .buildMappingExecutionContext("Process Definition Id", "42");

    // Act and Assert
    assertNotEquals(buildMappingExecutionContextResult,
        MappingExecutionContext.buildMappingExecutionContext("42", "42"));
  }

  /**
   * Method under test: {@link MappingExecutionContext#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    MappingExecutionContext buildMappingExecutionContextResult = MappingExecutionContext
        .buildMappingExecutionContext("42", "Activity Id");

    // Act and Assert
    assertNotEquals(buildMappingExecutionContextResult,
        MappingExecutionContext.buildMappingExecutionContext("42", "42"));
  }

  /**
   * Method under test: {@link MappingExecutionContext#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(MappingExecutionContext.buildMappingExecutionContext("42", "42"), null);
  }

  /**
   * Method under test: {@link MappingExecutionContext#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(MappingExecutionContext.buildMappingExecutionContext("42", "42"),
        "Different type to MappingExecutionContext");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MappingExecutionContext#MappingExecutionContext(String, String)}
   *   <li>{@link MappingExecutionContext#getActivityId()}
   *   <li>{@link MappingExecutionContext#getExecution()}
   *   <li>{@link MappingExecutionContext#getProcessDefinitionId()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    MappingExecutionContext actualMappingExecutionContext = new MappingExecutionContext("42", "42");
    String actualActivityId = actualMappingExecutionContext.getActivityId();
    DelegateExecution actualExecution = actualMappingExecutionContext.getExecution();

    // Assert
    assertEquals("42", actualActivityId);
    assertEquals("42", actualMappingExecutionContext.getProcessDefinitionId());
    assertNull(actualExecution);
  }

  /**
   * Method under test:
   * {@link MappingExecutionContext#MappingExecutionContext(DelegateExecution)}
   */
  @Test
  public void testNewMappingExecutionContext() {
    // Arrange
    ExecutionEntityImpl delegateExecution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    MappingExecutionContext actualMappingExecutionContext = new MappingExecutionContext(delegateExecution);

    // Assert
    assertNull(actualMappingExecutionContext.getActivityId());
    assertNull(actualMappingExecutionContext.getProcessDefinitionId());
    assertTrue(actualMappingExecutionContext.hasExecution());
    assertSame(delegateExecution, actualMappingExecutionContext.getExecution());
  }

  /**
   * Method under test:
   * {@link MappingExecutionContext#MappingExecutionContext(DelegateExecution)}
   */
  @Test
  public void testNewMappingExecutionContext2() {
    // Arrange
    ExecutionEntityImpl delegateExecution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    delegateExecution.setLockTime(mock(Date.class));

    // Act
    MappingExecutionContext actualMappingExecutionContext = new MappingExecutionContext(delegateExecution);

    // Assert
    assertNull(actualMappingExecutionContext.getActivityId());
    assertNull(actualMappingExecutionContext.getProcessDefinitionId());
    assertTrue(actualMappingExecutionContext.hasExecution());
    assertSame(delegateExecution, actualMappingExecutionContext.getExecution());
  }
}
