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
package org.activiti.engine.management;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.Test;

public class TablePageDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link TablePage}
   *   <li>{@link TablePage#setFirstResult(long)}
   *   <li>{@link TablePage#setRows(List)}
   *   <li>{@link TablePage#setTableName(String)}
   *   <li>{@link TablePage#setTotal(long)}
   *   <li>{@link TablePage#getFirstResult()}
   *   <li>{@link TablePage#getRows()}
   *   <li>{@link TablePage#getTableName()}
   *   <li>{@link TablePage#getTotal()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    TablePage actualTablePage = new TablePage();
    actualTablePage.setFirstResult(1L);
    ArrayList<Map<String, Object>> rowData = new ArrayList<>();
    actualTablePage.setRows(rowData);
    actualTablePage.setTableName("Table Name");
    actualTablePage.setTotal(1L);
    long actualFirstResult = actualTablePage.getFirstResult();
    List<Map<String, Object>> actualRows = actualTablePage.getRows();
    String actualTableName = actualTablePage.getTableName();

    // Assert that nothing has changed
    assertEquals("Table Name", actualTableName);
    assertEquals(1L, actualFirstResult);
    assertEquals(1L, actualTablePage.getTotal());
    assertTrue(actualRows.isEmpty());
    assertSame(rowData, actualRows);
  }

  /**
   * Test {@link TablePage#getSize()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   *   <li>Then return one.</li>
   * </ul>
   * <p>
   * Method under test: {@link TablePage#getSize()}
   */
  @Test
  public void testGetSize_givenHashMapComputeIfPresentFooAndBiFunction_thenReturnOne() {
    // Arrange
    HashMap<String, Object> stringObjectMap = new HashMap<>();
    stringObjectMap.computeIfPresent("foo", mock(BiFunction.class));

    ArrayList<Map<String, Object>> rowData = new ArrayList<>();
    rowData.add(stringObjectMap);

    TablePage tablePage = new TablePage();
    tablePage.setFirstResult(1L);
    tablePage.setRows(rowData);
    tablePage.setTableName("Table Name");
    tablePage.setTotal(1L);

    // Act and Assert
    assertEquals(1L, tablePage.getSize());
  }

  /**
   * Test {@link TablePage#getSize()}.
   * <ul>
   *   <li>Given {@link TablePage} (default constructor) FirstResult is one.</li>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link TablePage#getSize()}
   */
  @Test
  public void testGetSize_givenTablePageFirstResultIsOne_thenReturnZero() {
    // Arrange
    TablePage tablePage = new TablePage();
    tablePage.setFirstResult(1L);
    tablePage.setRows(new ArrayList<>());
    tablePage.setTableName("Table Name");
    tablePage.setTotal(1L);

    // Act and Assert
    assertEquals(0L, tablePage.getSize());
  }
}
