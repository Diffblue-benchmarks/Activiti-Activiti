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
package org.activiti.engine.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.management.TablePage;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class TablePageQueryImplDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link TablePageQueryImpl#TablePageQueryImpl()}
   *   <li>{@link TablePageQueryImpl#getOrder()}
   *   <li>{@link TablePageQueryImpl#getTableName()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void TablePageQueryImpl.<init>()", "void TablePageQueryImpl.<init>(CommandExecutor)",
      "String TablePageQueryImpl.getOrder()", "String TablePageQueryImpl.getTableName()"})
  public void testGettersAndSetters() {
    // Arrange and Act
    TablePageQueryImpl actualTablePageQueryImpl = new TablePageQueryImpl();
    String actualOrder = actualTablePageQueryImpl.getOrder();

    // Assert
    assertNull(actualOrder);
    assertNull(actualTablePageQueryImpl.getTableName());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link TablePageQueryImpl#TablePageQueryImpl(CommandExecutor)}
   *   <li>{@link TablePageQueryImpl#getOrder()}
   *   <li>{@link TablePageQueryImpl#getTableName()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void TablePageQueryImpl.<init>()", "void TablePageQueryImpl.<init>(CommandExecutor)",
      "String TablePageQueryImpl.getOrder()", "String TablePageQueryImpl.getTableName()"})
  public void testGettersAndSetters2() {
    // Arrange
    CommandConfig defaultConfig = new CommandConfig();

    // Act
    TablePageQueryImpl actualTablePageQueryImpl = new TablePageQueryImpl(
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    String actualOrder = actualTablePageQueryImpl.getOrder();

    // Assert
    assertNull(actualOrder);
    assertNull(actualTablePageQueryImpl.getTableName());
  }

  /**
   * Test {@link TablePageQueryImpl#tableName(String)}.
   * <p>
   * Method under test: {@link TablePageQueryImpl#tableName(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TablePageQueryImpl TablePageQueryImpl.tableName(String)"})
  public void testTableName() {
    // Arrange
    TablePageQueryImpl tablePageQueryImpl = new TablePageQueryImpl();

    // Act
    TablePageQueryImpl actualTableNameResult = tablePageQueryImpl.tableName("Table Name");

    // Assert
    assertEquals("Table Name", tablePageQueryImpl.getTableName());
    assertSame(tablePageQueryImpl, actualTableNameResult);
  }

  /**
   * Test {@link TablePageQueryImpl#orderAsc(String)}.
   * <ul>
   *   <li>Given {@link TablePageQueryImpl#TablePageQueryImpl()}.</li>
   *   <li>Then {@link TablePageQueryImpl#TablePageQueryImpl()} Order is {@code Column asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TablePageQueryImpl#orderAsc(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TablePageQueryImpl TablePageQueryImpl.orderAsc(String)"})
  public void testOrderAsc_givenTablePageQueryImpl_thenTablePageQueryImplOrderIsColumnAsc() {
    // Arrange
    TablePageQueryImpl tablePageQueryImpl = new TablePageQueryImpl();

    // Act
    TablePageQueryImpl actualOrderAscResult = tablePageQueryImpl.orderAsc("Column");

    // Assert
    assertEquals("Column asc", tablePageQueryImpl.getOrder());
    assertSame(tablePageQueryImpl, actualOrderAscResult);
  }

  /**
   * Test {@link TablePageQueryImpl#orderAsc(String)}.
   * <ul>
   *   <li>Then {@link TablePageQueryImpl#TablePageQueryImpl()} Order is {@code asc asc, Column asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TablePageQueryImpl#orderAsc(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TablePageQueryImpl TablePageQueryImpl.orderAsc(String)"})
  public void testOrderAsc_thenTablePageQueryImplOrderIsAscAscColumnAsc() {
    // Arrange
    TablePageQueryImpl tablePageQueryImpl = new TablePageQueryImpl();
    tablePageQueryImpl.addOrder(AbstractQuery.SORTORDER_ASC, AbstractQuery.SORTORDER_ASC);

    // Act
    TablePageQueryImpl actualOrderAscResult = tablePageQueryImpl.orderAsc("Column");

    // Assert
    assertEquals("asc asc, Column asc", tablePageQueryImpl.getOrder());
    assertSame(tablePageQueryImpl, actualOrderAscResult);
  }

  /**
   * Test {@link TablePageQueryImpl#orderDesc(String)}.
   * <ul>
   *   <li>Given {@link TablePageQueryImpl#TablePageQueryImpl()}.</li>
   *   <li>Then {@link TablePageQueryImpl#TablePageQueryImpl()} Order is {@code Column desc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TablePageQueryImpl#orderDesc(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TablePageQueryImpl TablePageQueryImpl.orderDesc(String)"})
  public void testOrderDesc_givenTablePageQueryImpl_thenTablePageQueryImplOrderIsColumnDesc() {
    // Arrange
    TablePageQueryImpl tablePageQueryImpl = new TablePageQueryImpl();

    // Act
    TablePageQueryImpl actualOrderDescResult = tablePageQueryImpl.orderDesc("Column");

    // Assert
    assertEquals("Column desc", tablePageQueryImpl.getOrder());
    assertSame(tablePageQueryImpl, actualOrderDescResult);
  }

  /**
   * Test {@link TablePageQueryImpl#orderDesc(String)}.
   * <ul>
   *   <li>Then {@link TablePageQueryImpl#TablePageQueryImpl()} Order is {@code desc asc, Column desc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TablePageQueryImpl#orderDesc(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TablePageQueryImpl TablePageQueryImpl.orderDesc(String)"})
  public void testOrderDesc_thenTablePageQueryImplOrderIsDescAscColumnDesc() {
    // Arrange
    TablePageQueryImpl tablePageQueryImpl = new TablePageQueryImpl();
    tablePageQueryImpl.addOrder(AbstractQuery.SORTORDER_DESC, AbstractQuery.SORTORDER_ASC);

    // Act
    TablePageQueryImpl actualOrderDescResult = tablePageQueryImpl.orderDesc("Column");

    // Assert
    assertEquals("desc asc, Column desc", tablePageQueryImpl.getOrder());
    assertSame(tablePageQueryImpl, actualOrderDescResult);
  }

  /**
   * Test {@link TablePageQueryImpl#addOrder(String, String)}.
   * <ul>
   *   <li>Given {@link TablePageQueryImpl#TablePageQueryImpl()}.</li>
   *   <li>Then {@link TablePageQueryImpl#TablePageQueryImpl()} Order is {@code Column asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TablePageQueryImpl#addOrder(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void TablePageQueryImpl.addOrder(String, String)"})
  public void testAddOrder_givenTablePageQueryImpl_thenTablePageQueryImplOrderIsColumnAsc() {
    // Arrange
    TablePageQueryImpl tablePageQueryImpl = new TablePageQueryImpl();

    // Act
    tablePageQueryImpl.addOrder("Column", AbstractQuery.SORTORDER_ASC);

    // Assert
    assertEquals("Column asc", tablePageQueryImpl.getOrder());
  }

  /**
   * Test {@link TablePageQueryImpl#addOrder(String, String)}.
   * <ul>
   *   <li>Then {@link TablePageQueryImpl#TablePageQueryImpl()} Order is {@code Column asc, Column asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TablePageQueryImpl#addOrder(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void TablePageQueryImpl.addOrder(String, String)"})
  public void testAddOrder_thenTablePageQueryImplOrderIsColumnAscColumnAsc() {
    // Arrange
    TablePageQueryImpl tablePageQueryImpl = new TablePageQueryImpl();
    tablePageQueryImpl.addOrder("Column", AbstractQuery.SORTORDER_ASC);

    // Act
    tablePageQueryImpl.addOrder("Column", AbstractQuery.SORTORDER_ASC);

    // Assert
    assertEquals("Column asc, Column asc", tablePageQueryImpl.getOrder());
  }

  /**
   * Test {@link TablePageQueryImpl#listPage(int, int)}.
   * <p>
   * Method under test: {@link TablePageQueryImpl#listPage(int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TablePage TablePageQueryImpl.listPage(int, int)"})
  public void testListPage() {
    // Arrange
    TablePage tablePage = new TablePage();
    tablePage.setFirstResult(1L);
    tablePage.setRows(new ArrayList<>());
    tablePage.setTableName("Table Name");
    tablePage.setTotal(1L);
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<TablePage>>any())).thenReturn(tablePage);
    TablePageQueryImpl tablePageQueryImpl = new TablePageQueryImpl(new CommandExecutorImpl(new CommandConfig(), first));

    // Act
    TablePage actualListPageResult = tablePageQueryImpl.listPage(1, 3);

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertEquals(1, tablePageQueryImpl.firstResult);
    assertEquals(3, tablePageQueryImpl.maxResults);
    assertSame(tablePage, actualListPageResult);
  }
}
