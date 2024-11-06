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
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TableMetaDataDiffblueTest {
  @InjectMocks
  private TableMetaData tableMetaData;

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link TableMetaData#TableMetaData()}
   *   <li>{@link TableMetaData#setColumnNames(List)}
   *   <li>{@link TableMetaData#setColumnTypes(List)}
   *   <li>{@link TableMetaData#setTableName(String)}
   *   <li>{@link TableMetaData#getColumnNames()}
   *   <li>{@link TableMetaData#getColumnTypes()}
   *   <li>{@link TableMetaData#getTableName()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    TableMetaData actualTableMetaData = new TableMetaData();
    ArrayList<String> columnNames = new ArrayList<>();
    actualTableMetaData.setColumnNames(columnNames);
    ArrayList<String> columnTypes = new ArrayList<>();
    actualTableMetaData.setColumnTypes(columnTypes);
    actualTableMetaData.setTableName("Table Name");
    List<String> actualColumnNames = actualTableMetaData.getColumnNames();
    List<String> actualColumnTypes = actualTableMetaData.getColumnTypes();

    // Assert that nothing has changed
    assertEquals("Table Name", actualTableMetaData.getTableName());
    assertTrue(actualColumnNames.isEmpty());
    assertTrue(actualColumnTypes.isEmpty());
    assertSame(columnNames, actualColumnNames);
    assertSame(columnTypes, actualColumnTypes);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code Table Name}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link TableMetaData#TableMetaData(String)}
   *   <li>{@link TableMetaData#setColumnNames(List)}
   *   <li>{@link TableMetaData#setColumnTypes(List)}
   *   <li>{@link TableMetaData#setTableName(String)}
   *   <li>{@link TableMetaData#getColumnNames()}
   *   <li>{@link TableMetaData#getColumnTypes()}
   *   <li>{@link TableMetaData#getTableName()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_whenTableName() {
    // Arrange and Act
    TableMetaData actualTableMetaData = new TableMetaData("Table Name");
    ArrayList<String> columnNames = new ArrayList<>();
    actualTableMetaData.setColumnNames(columnNames);
    ArrayList<String> columnTypes = new ArrayList<>();
    actualTableMetaData.setColumnTypes(columnTypes);
    actualTableMetaData.setTableName("Table Name");
    List<String> actualColumnNames = actualTableMetaData.getColumnNames();
    List<String> actualColumnTypes = actualTableMetaData.getColumnTypes();

    // Assert that nothing has changed
    assertEquals("Table Name", actualTableMetaData.getTableName());
    assertTrue(actualColumnNames.isEmpty());
    assertTrue(actualColumnTypes.isEmpty());
    assertSame(columnNames, actualColumnNames);
    assertSame(columnTypes, actualColumnTypes);
  }

  /**
   * Test {@link TableMetaData#addColumnMetaData(String, String)}.
   * <p>
   * Method under test: {@link TableMetaData#addColumnMetaData(String, String)}
   */
  @Test
  public void testAddColumnMetaData() {
    // Arrange and Act
    tableMetaData.addColumnMetaData("Column Name", "Column Type");

    // Assert
    assertEquals("Column Name", tableMetaData.getColumnNames().get(0));
    assertEquals("Column Type", tableMetaData.getColumnTypes().get(0));
  }
}
