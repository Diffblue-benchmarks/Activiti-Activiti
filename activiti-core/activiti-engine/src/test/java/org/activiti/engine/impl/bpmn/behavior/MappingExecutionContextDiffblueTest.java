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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class MappingExecutionContextDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link MappingExecutionContext#MappingExecutionContext(String, String)}
   *   <li>{@link MappingExecutionContext#getActivityId()}
   *   <li>{@link MappingExecutionContext#getExecution()}
   *   <li>{@link MappingExecutionContext#getProcessDefinitionId()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MappingExecutionContext.<init>(String, String)",
    "String MappingExecutionContext.getActivityId()",
    "DelegateExecution MappingExecutionContext.getExecution()",
    "String MappingExecutionContext.getProcessDefinitionId()"
  })
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
   * Test {@link MappingExecutionContext#MappingExecutionContext(DelegateExecution)}.
   *
   * <ul>
   *   <li>Then Execution return {@link ExecutionEntityImpl}.
   * </ul>
   *
   * <p>Method under test: {@link
   * MappingExecutionContext#MappingExecutionContext(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MappingExecutionContext.<init>(DelegateExecution)"})
  public void testNewMappingExecutionContext_thenExecutionReturnExecutionEntityImpl() {
    // Arrange
    ExecutionEntityImpl delegateExecution =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    MappingExecutionContext actualMappingExecutionContext =
        new MappingExecutionContext(delegateExecution);

    // Assert
    DelegateExecution execution = actualMappingExecutionContext.getExecution();
    assertTrue(execution instanceof ExecutionEntityImpl);
    assertNull(actualMappingExecutionContext.getActivityId());
    assertNull(actualMappingExecutionContext.getProcessDefinitionId());
    assertTrue(actualMappingExecutionContext.hasExecution());
    assertSame(delegateExecution, execution);
  }

  /**
   * Test {@link MappingExecutionContext#hasExecution()}.
   *
   * <ul>
   *   <li>Given buildMappingExecutionContext {@code 42} and {@code 42}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link MappingExecutionContext#hasExecution()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MappingExecutionContext.hasExecution()"})
  public void testHasExecution_givenBuildMappingExecutionContext42And42_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(MappingExecutionContext.buildMappingExecutionContext("42", "42").hasExecution());
  }

  /**
   * Test {@link MappingExecutionContext#hasExecution()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link MappingExecutionContext#hasExecution()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MappingExecutionContext.hasExecution()"})
  public void testHasExecution_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(
        new MappingExecutionContext(ExecutionEntityImpl.createWithEmptyRelationshipCollections())
            .hasExecution());
  }

  /**
   * Test {@link MappingExecutionContext#buildMappingExecutionContext(DelegateExecution)} with
   * {@code delegateExecution}.
   *
   * <p>Method under test: {@link
   * MappingExecutionContext#buildMappingExecutionContext(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MappingExecutionContext MappingExecutionContext.buildMappingExecutionContext(DelegateExecution)"
  })
  public void testBuildMappingExecutionContextWithDelegateExecution() {
    // Arrange
    ExecutionEntityImpl delegateExecution =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    MappingExecutionContext actualBuildMappingExecutionContextResult =
        MappingExecutionContext.buildMappingExecutionContext(delegateExecution);

    // Assert
    DelegateExecution execution = actualBuildMappingExecutionContextResult.getExecution();
    assertTrue(execution instanceof ExecutionEntityImpl);
    assertNull(actualBuildMappingExecutionContextResult.getActivityId());
    assertNull(actualBuildMappingExecutionContextResult.getProcessDefinitionId());
    assertTrue(actualBuildMappingExecutionContextResult.hasExecution());
    assertSame(delegateExecution, execution);
  }

  /**
   * Test {@link MappingExecutionContext#buildMappingExecutionContext(String, String)} with {@code
   * processDefinitionId}, {@code activityId}.
   *
   * <p>Method under test: {@link MappingExecutionContext#buildMappingExecutionContext(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MappingExecutionContext MappingExecutionContext.buildMappingExecutionContext(String, String)"
  })
  public void testBuildMappingExecutionContextWithProcessDefinitionIdActivityId() {
    // Arrange and Act
    MappingExecutionContext actualBuildMappingExecutionContextResult =
        MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Assert
    assertEquals("42", actualBuildMappingExecutionContextResult.getActivityId());
    assertEquals("42", actualBuildMappingExecutionContextResult.getProcessDefinitionId());
    assertNull(actualBuildMappingExecutionContextResult.getExecution());
    assertFalse(actualBuildMappingExecutionContextResult.hasExecution());
  }

  /**
   * Test {@link MappingExecutionContext#equals(Object)}, and {@link
   * MappingExecutionContext#hashCode()}.
   *
   * <ul>
   *   <li>When other is equal.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link MappingExecutionContext#equals(Object)}
   *   <li>{@link MappingExecutionContext#hashCode()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MappingExecutionContext.equals(Object)",
    "int MappingExecutionContext.hashCode()"
  })
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    MappingExecutionContext buildMappingExecutionContextResult =
        MappingExecutionContext.buildMappingExecutionContext("42", "42");
    MappingExecutionContext buildMappingExecutionContextResult2 =
        MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertEquals(buildMappingExecutionContextResult, buildMappingExecutionContextResult2);
    assertEquals(
        buildMappingExecutionContextResult.hashCode(),
        buildMappingExecutionContextResult2.hashCode());
  }

  /**
   * Test {@link MappingExecutionContext#equals(Object)}, and {@link
   * MappingExecutionContext#hashCode()}.
   *
   * <ul>
   *   <li>When other is same.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link MappingExecutionContext#equals(Object)}
   *   <li>{@link MappingExecutionContext#hashCode()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MappingExecutionContext.equals(Object)",
    "int MappingExecutionContext.hashCode()"
  })
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    MappingExecutionContext buildMappingExecutionContextResult =
        MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertEquals(buildMappingExecutionContextResult, buildMappingExecutionContextResult);
    int expectedHashCodeResult = buildMappingExecutionContextResult.hashCode();
    assertEquals(expectedHashCodeResult, buildMappingExecutionContextResult.hashCode());
  }

  /**
   * Test {@link MappingExecutionContext#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link MappingExecutionContext#equals(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MappingExecutionContext.equals(Object)",
    "int MappingExecutionContext.hashCode()"
  })
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(
        MappingExecutionContext.buildMappingExecutionContext(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections()),
        MappingExecutionContext.buildMappingExecutionContext("42", "42"));
  }

  /**
   * Test {@link MappingExecutionContext#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link MappingExecutionContext#equals(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MappingExecutionContext.equals(Object)",
    "int MappingExecutionContext.hashCode()"
  })
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange, Act and Assert
    assertNotEquals(
        MappingExecutionContext.buildMappingExecutionContext("42", "Activity Id"),
        MappingExecutionContext.buildMappingExecutionContext("42", "42"));
  }

  /**
   * Test {@link MappingExecutionContext#equals(Object)}.
   *
   * <ul>
   *   <li>When other is {@code null}.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link MappingExecutionContext#equals(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MappingExecutionContext.equals(Object)",
    "int MappingExecutionContext.hashCode()"
  })
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(MappingExecutionContext.buildMappingExecutionContext("42", "42"), null);
  }

  /**
   * Test {@link MappingExecutionContext#equals(Object)}.
   *
   * <ul>
   *   <li>When other is wrong type.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link MappingExecutionContext#equals(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MappingExecutionContext.equals(Object)",
    "int MappingExecutionContext.hashCode()"
  })
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(
        MappingExecutionContext.buildMappingExecutionContext("42", "42"),
        "Different type to MappingExecutionContext");
  }
}
