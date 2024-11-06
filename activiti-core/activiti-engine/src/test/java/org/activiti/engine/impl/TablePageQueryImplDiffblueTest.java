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
import java.util.ArrayList;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.management.TablePage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TablePageQueryImplDiffblueTest {
  @InjectMocks
  private TablePageQueryImpl tablePageQueryImpl;

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
  public void testTableName() {
    // Arrange and Act
    TablePageQueryImpl actualTableNameResult = tablePageQueryImpl.tableName("Table Name");

    // Assert
    assertEquals("Table Name", tablePageQueryImpl.getTableName());
    assertSame(tablePageQueryImpl, actualTableNameResult);
  }

  /**
   * Test {@link TablePageQueryImpl#orderAsc(String)}.
   * <p>
   * Method under test: {@link TablePageQueryImpl#orderAsc(String)}
   */
  @Test
  public void testOrderAsc() {
    // Arrange, Act and Assert
    assertSame(tablePageQueryImpl, tablePageQueryImpl.orderAsc("Column"));
  }

  /**
   * Test {@link TablePageQueryImpl#orderDesc(String)}.
   * <p>
   * Method under test: {@link TablePageQueryImpl#orderDesc(String)}
   */
  @Test
  public void testOrderDesc() {
    // Arrange, Act and Assert
    assertSame(tablePageQueryImpl, tablePageQueryImpl.orderDesc("Column"));
  }

  /**
   * Test {@link TablePageQueryImpl#addOrder(String, String)}.
   * <p>
   * Method under test: {@link TablePageQueryImpl#addOrder(String, String)}
   */
  @Test
  public void testAddOrder() {
    // Arrange
    CommandConfig defaultConfig = mock(CommandConfig.class);
    TablePageQueryImpl tablePageQueryImpl = new TablePageQueryImpl(
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    // Act
    tablePageQueryImpl.addOrder("Column", AbstractQuery.SORTORDER_ASC);

    // Assert
    assertEquals("Column asc", tablePageQueryImpl.getOrder());
  }

  /**
   * Test {@link TablePageQueryImpl#addOrder(String, String)}.
   * <ul>
   *   <li>Given {@link TablePageQueryImpl#TablePageQueryImpl()}.</li>
   *   <li>Then {@link TablePageQueryImpl#TablePageQueryImpl()} Order is
   * {@code Column asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TablePageQueryImpl#addOrder(String, String)}
   */
  @Test
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
   *   <li>Then {@link TablePageQueryImpl#TablePageQueryImpl()} Order is
   * {@code Column asc, Column asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TablePageQueryImpl#addOrder(String, String)}
   */
  @Test
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
  public void testListPage() {
    // Arrange
    TablePage tablePage = new TablePage();
    tablePage.setFirstResult(2L);
    tablePage.setRows(new ArrayList<>());
    tablePage.setTableName("Table Name");
    tablePage.setTotal(2L);
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(tablePage);
    TablePageQueryImpl tablePageQueryImpl = new TablePageQueryImpl(
        new CommandExecutorImpl(mock(CommandConfig.class), first));

    // Act
    TablePage actualListPageResult = tablePageQueryImpl.listPage(1, 3);

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertEquals(1, tablePageQueryImpl.firstResult);
    assertEquals(3, tablePageQueryImpl.maxResults);
    assertSame(tablePage, actualListPageResult);
  }
}
