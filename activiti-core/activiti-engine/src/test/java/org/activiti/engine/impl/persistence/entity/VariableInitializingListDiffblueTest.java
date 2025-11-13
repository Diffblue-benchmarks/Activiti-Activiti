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
package org.activiti.engine.impl.persistence.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class VariableInitializingListDiffblueTest {
  /**
   * Test {@link VariableInitializingList#add(int, VariableInstanceEntity)} with {@code int}, {@code
   * VariableInstanceEntity}.
   *
   * <p>Method under test: {@link VariableInitializingList#add(int, VariableInstanceEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void VariableInitializingList.add(int, VariableInstanceEntity)"})
  public void testAddWithIntVariableInstanceEntity() {
    // Arrange
    VariableInitializingList variableInitializingList = new VariableInitializingList();
    VariableInstanceEntityImpl e = new VariableInstanceEntityImpl();

    // Act
    variableInitializingList.add(0, e);

    // Assert
    assertEquals(1, variableInitializingList.size());
    assertSame(e, variableInitializingList.get(0));
  }

  /**
   * Test {@link VariableInitializingList#add(int, VariableInstanceEntity)} with {@code int}, {@code
   * VariableInstanceEntity}.
   *
   * <ul>
   *   <li>Then {@link VariableInitializingList} (default constructor) first is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link VariableInitializingList#add(int, VariableInstanceEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void VariableInitializingList.add(int, VariableInstanceEntity)"})
  public void testAddWithIntVariableInstanceEntity_thenVariableInitializingListFirstIsNull() {
    // Arrange
    VariableInitializingList variableInitializingList = new VariableInitializingList();

    // Act
    variableInitializingList.add(0, null);

    // Assert
    assertEquals(1, variableInitializingList.size());
    assertNull(variableInitializingList.get(0));
  }

  /**
   * Test {@link VariableInitializingList#add(VariableInstanceEntity)} with {@code
   * VariableInstanceEntity}.
   *
   * <p>Method under test: {@link VariableInitializingList#add(VariableInstanceEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VariableInitializingList.add(VariableInstanceEntity)"})
  public void testAddWithVariableInstanceEntity() {
    // Arrange
    VariableInitializingList variableInitializingList = new VariableInitializingList();
    VariableInstanceEntityImpl e = new VariableInstanceEntityImpl();

    // Act
    boolean actualAddResult = variableInitializingList.add(e);

    // Assert
    assertEquals(1, variableInitializingList.size());
    assertTrue(actualAddResult);
    assertSame(e, variableInitializingList.get(0));
  }

  /**
   * Test {@link VariableInitializingList#addAll(int, Collection)} with {@code index}, {@code c}.
   *
   * <ul>
   *   <li>Given {@link VariableInstanceEntityImpl} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link VariableInitializingList#addAll(int, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VariableInitializingList.addAll(int, Collection)"})
  public void testAddAllWithIndexC_givenVariableInstanceEntityImpl_thenArrayListSizeIsOne() {
    // Arrange
    VariableInitializingList variableInitializingList = new VariableInitializingList();

    ArrayList<VariableInstanceEntity> c = new ArrayList<>();
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    c.add(variableInstanceEntityImpl);

    // Act
    boolean actualAddAllResult = variableInitializingList.addAll(0, c);

    // Assert
    assertEquals(1, c.size());
    assertEquals(1, variableInitializingList.size());
    assertTrue(actualAddAllResult);
    assertSame(variableInstanceEntityImpl, variableInitializingList.get(0));
  }

  /**
   * Test {@link VariableInitializingList#addAll(int, Collection)} with {@code index}, {@code c}.
   *
   * <ul>
   *   <li>Then {@link VariableInitializingList} (default constructor) size is two.
   * </ul>
   *
   * <p>Method under test: {@link VariableInitializingList#addAll(int, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VariableInitializingList.addAll(int, Collection)"})
  public void testAddAllWithIndexC_thenVariableInitializingListSizeIsTwo() {
    // Arrange
    VariableInitializingList variableInitializingList = new VariableInitializingList();

    ArrayList<VariableInstanceEntity> c = new ArrayList<>();
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    c.add(variableInstanceEntityImpl);
    VariableInstanceEntityImpl variableInstanceEntityImpl2 = new VariableInstanceEntityImpl();
    c.add(variableInstanceEntityImpl2);

    // Act
    boolean actualAddAllResult = variableInitializingList.addAll(0, c);

    // Assert
    assertEquals(2, variableInitializingList.size());
    assertTrue(actualAddAllResult);
    assertEquals(variableInitializingList, c);
    assertSame(variableInstanceEntityImpl, variableInitializingList.get(0));
    assertSame(variableInstanceEntityImpl2, variableInitializingList.get(1));
  }

  /**
   * Test {@link VariableInitializingList#addAll(int, Collection)} with {@code index}, {@code c}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link VariableInitializingList#addAll(int, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VariableInitializingList.addAll(int, Collection)"})
  public void testAddAllWithIndexC_whenArrayList_thenReturnFalse() {
    // Arrange
    VariableInitializingList variableInitializingList = new VariableInitializingList();
    ArrayList<VariableInstanceEntity> c = new ArrayList<>();

    // Act and Assert
    assertFalse(variableInitializingList.addAll(0, c));
    assertTrue(c.isEmpty());
    assertTrue(variableInitializingList.isEmpty());
  }

  /**
   * Test new {@link VariableInitializingList} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link VariableInitializingList}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void VariableInitializingList.<init>()"})
  public void testNewVariableInitializingList() {
    // Arrange, Act and Assert
    assertTrue(new VariableInitializingList().isEmpty());
  }
}
