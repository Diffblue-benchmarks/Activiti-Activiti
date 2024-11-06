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
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import org.junit.Test;

public class HistoricVariableInitializingListDiffblueTest {
  /**
   * Test
   * {@link HistoricVariableInitializingList#add(HistoricVariableInstanceEntity)}
   * with {@code HistoricVariableInstanceEntity}.
   * <p>
   * Method under test:
   * {@link HistoricVariableInitializingList#add(HistoricVariableInstanceEntity)}
   */
  @Test
  public void testAddWithHistoricVariableInstanceEntity() {
    // Arrange
    HistoricVariableInitializingList historicVariableInitializingList = new HistoricVariableInitializingList();
    HistoricVariableInstanceEntityImpl e = new HistoricVariableInstanceEntityImpl();

    // Act
    boolean actualAddResult = historicVariableInitializingList.add(e);

    // Assert
    assertEquals(1, historicVariableInitializingList.size());
    assertTrue(actualAddResult);
    assertSame(e, historicVariableInitializingList.get(0));
  }

  /**
   * Test
   * {@link HistoricVariableInitializingList#add(HistoricVariableInstanceEntity)}
   * with {@code HistoricVariableInstanceEntity}.
   * <p>
   * Method under test:
   * {@link HistoricVariableInitializingList#add(HistoricVariableInstanceEntity)}
   */
  @Test
  public void testAddWithHistoricVariableInstanceEntity2() {
    // Arrange
    HistoricVariableInitializingList historicVariableInitializingList = new HistoricVariableInitializingList();
    HistoricVariableInstanceEntity e = mock(HistoricVariableInstanceEntity.class);

    // Act
    boolean actualAddResult = historicVariableInitializingList.add(e);

    // Assert
    assertEquals(1, historicVariableInitializingList.size());
    assertTrue(actualAddResult);
    assertSame(e, historicVariableInitializingList.get(0));
  }

  /**
   * Test
   * {@link HistoricVariableInitializingList#add(HistoricVariableInstanceEntity)}
   * with {@code HistoricVariableInstanceEntity}.
   * <p>
   * Method under test:
   * {@link HistoricVariableInitializingList#add(HistoricVariableInstanceEntity)}
   */
  @Test
  public void testAddWithHistoricVariableInstanceEntity3() {
    // Arrange
    ArrayList<HistoricVariableInstanceEntity> c = new ArrayList<>();
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    c.add(historicVariableInstanceEntityImpl);

    HistoricVariableInitializingList historicVariableInitializingList = new HistoricVariableInitializingList();
    historicVariableInitializingList.addAll(c);
    HistoricVariableInstanceEntityImpl e = new HistoricVariableInstanceEntityImpl();

    // Act
    historicVariableInitializingList.add(e);

    // Assert
    assertEquals(2, historicVariableInitializingList.size());
    assertSame(historicVariableInstanceEntityImpl, historicVariableInitializingList.get(0));
    assertSame(e, historicVariableInitializingList.get(1));
  }

  /**
   * Test
   * {@link HistoricVariableInitializingList#add(int, HistoricVariableInstanceEntity)}
   * with {@code int}, {@code HistoricVariableInstanceEntity}.
   * <p>
   * Method under test:
   * {@link HistoricVariableInitializingList#add(int, HistoricVariableInstanceEntity)}
   */
  @Test
  public void testAddWithIntHistoricVariableInstanceEntity() {
    // Arrange
    HistoricVariableInitializingList historicVariableInitializingList = new HistoricVariableInitializingList();
    HistoricVariableInstanceEntityImpl e = new HistoricVariableInstanceEntityImpl();

    // Act
    historicVariableInitializingList.add(0, e);

    // Assert
    assertEquals(1, historicVariableInitializingList.size());
    assertSame(e, historicVariableInitializingList.get(0));
  }

  /**
   * Test {@link HistoricVariableInitializingList#addAll(Collection)} with
   * {@code c}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>Then {@link HistoricVariableInitializingList} (default constructor) first
   * is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInitializingList#addAll(Collection)}
   */
  @Test
  public void testAddAllWithC_givenNull_thenHistoricVariableInitializingListFirstIsNull() {
    // Arrange
    HistoricVariableInitializingList historicVariableInitializingList = new HistoricVariableInitializingList();

    LinkedHashSet<? extends HistoricVariableInstanceEntity> c = new LinkedHashSet<>();
    c.add(null);

    // Act
    boolean actualAddAllResult = historicVariableInitializingList.addAll(c);

    // Assert
    assertEquals(1, historicVariableInitializingList.size());
    assertNull(historicVariableInitializingList.get(0));
    assertEquals(1, c.size());
    assertTrue(actualAddAllResult);
  }

  /**
   * Test {@link HistoricVariableInitializingList#addAll(Collection)} with
   * {@code c}.
   * <ul>
   *   <li>Then {@link HistoricVariableInitializingList} (default constructor) size
   * is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInitializingList#addAll(Collection)}
   */
  @Test
  public void testAddAllWithC_thenHistoricVariableInitializingListSizeIsTwo() {
    // Arrange
    HistoricVariableInitializingList historicVariableInitializingList = new HistoricVariableInitializingList();

    ArrayList<HistoricVariableInstanceEntity> c = new ArrayList<>();
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    c.add(historicVariableInstanceEntityImpl);
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl2 = new HistoricVariableInstanceEntityImpl();
    c.add(historicVariableInstanceEntityImpl2);

    // Act
    historicVariableInitializingList.addAll(c);

    // Assert
    assertEquals(2, historicVariableInitializingList.size());
    assertEquals(historicVariableInitializingList, c);
    assertSame(historicVariableInstanceEntityImpl, historicVariableInitializingList.get(0));
    assertSame(historicVariableInstanceEntityImpl2, historicVariableInitializingList.get(1));
  }

  /**
   * Test {@link HistoricVariableInitializingList#addAll(Collection)} with
   * {@code c}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInitializingList#addAll(Collection)}
   */
  @Test
  public void testAddAllWithC_whenArrayList_thenReturnFalse() {
    // Arrange
    HistoricVariableInitializingList historicVariableInitializingList = new HistoricVariableInitializingList();
    ArrayList<HistoricVariableInstanceEntity> c = new ArrayList<>();

    // Act and Assert
    assertFalse(historicVariableInitializingList.addAll(c));
    assertTrue(c.isEmpty());
    assertTrue(historicVariableInitializingList.isEmpty());
  }

  /**
   * Test {@link HistoricVariableInitializingList#addAll(int, Collection)} with
   * {@code index}, {@code c}.
   * <p>
   * Method under test:
   * {@link HistoricVariableInitializingList#addAll(int, Collection)}
   */
  @Test
  public void testAddAllWithIndexC() {
    // Arrange
    HistoricVariableInitializingList historicVariableInitializingList = new HistoricVariableInitializingList();

    ArrayList<HistoricVariableInstanceEntity> c = new ArrayList<>();
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    c.add(historicVariableInstanceEntityImpl);

    // Act
    historicVariableInitializingList.addAll(0, c);

    // Assert
    assertEquals(1, c.size());
    assertEquals(1, historicVariableInitializingList.size());
    assertSame(historicVariableInstanceEntityImpl, historicVariableInitializingList.get(0));
  }

  /**
   * Test {@link HistoricVariableInitializingList#addAll(int, Collection)} with
   * {@code index}, {@code c}.
   * <ul>
   *   <li>Given {@link HistoricVariableInstanceEntity}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInitializingList#addAll(int, Collection)}
   */
  @Test
  public void testAddAllWithIndexC_givenHistoricVariableInstanceEntity_thenReturnTrue() {
    // Arrange
    HistoricVariableInitializingList historicVariableInitializingList = new HistoricVariableInitializingList();

    ArrayList<HistoricVariableInstanceEntity> c = new ArrayList<>();
    c.add(mock(HistoricVariableInstanceEntity.class));

    // Act
    boolean actualAddAllResult = historicVariableInitializingList.addAll(0, c);

    // Assert
    assertEquals(1, c.size());
    assertEquals(1, historicVariableInitializingList.size());
    assertTrue(actualAddAllResult);
    HistoricVariableInstanceEntity expectedGetResult = c.get(0);
    assertSame(expectedGetResult, historicVariableInitializingList.get(0));
  }

  /**
   * Test {@link HistoricVariableInitializingList#addAll(int, Collection)} with
   * {@code index}, {@code c}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInitializingList#addAll(int, Collection)}
   */
  @Test
  public void testAddAllWithIndexC_thenArrayListSizeIsTwo() {
    // Arrange
    HistoricVariableInitializingList historicVariableInitializingList = new HistoricVariableInitializingList();

    ArrayList<HistoricVariableInstanceEntity> c = new ArrayList<>();
    c.add(new HistoricVariableInstanceEntityImpl());
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    c.add(historicVariableInstanceEntityImpl);

    // Act
    historicVariableInitializingList.addAll(0, c);

    // Assert
    assertEquals(2, c.size());
    assertEquals(2, historicVariableInitializingList.size());
    assertSame(historicVariableInstanceEntityImpl, historicVariableInitializingList.get(1));
  }

  /**
   * Test {@link HistoricVariableInitializingList#addAll(int, Collection)} with
   * {@code index}, {@code c}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInitializingList#addAll(int, Collection)}
   */
  @Test
  public void testAddAllWithIndexC_whenArrayList_thenReturnFalse() {
    // Arrange
    HistoricVariableInitializingList historicVariableInitializingList = new HistoricVariableInitializingList();
    ArrayList<HistoricVariableInstanceEntity> c = new ArrayList<>();

    // Act and Assert
    assertFalse(historicVariableInitializingList.addAll(0, c));
    assertTrue(c.isEmpty());
    assertTrue(historicVariableInitializingList.isEmpty());
  }

  /**
   * Test new {@link HistoricVariableInitializingList} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link HistoricVariableInitializingList}
   */
  @Test
  public void testNewHistoricVariableInitializingList() {
    // Arrange, Act and Assert
    assertTrue((new HistoricVariableInitializingList()).isEmpty());
  }
}
