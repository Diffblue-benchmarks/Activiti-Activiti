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
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.AbstractQuery.NullHandlingOnOrder;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.query.QueryProperty;
import org.activiti.engine.runtime.DeadLetterJobQuery;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AbstractQueryDiffblueTest {
  /**
   * Test {@link AbstractQuery#asc()}.
   * <p>
   * Method under test: {@link AbstractQuery#asc()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.query.Query AbstractQuery.asc()"})
  public void testAsc() {
    // Arrange
    QueryProperty property = mock(QueryProperty.class);
    when(property.getName()).thenReturn("Name");

    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.addOrder("", AbstractQuery.SORTORDER_DESC, NullHandlingOnOrder.NULLS_LAST);
    deadLetterJobQueryImpl.orderBy(property);

    // Act
    DeadLetterJobQuery actualAscResult = deadLetterJobQueryImpl.asc();

    // Assert
    verify(property).getName();
    assertSame(deadLetterJobQueryImpl, actualAscResult);
  }

  /**
   * Test {@link AbstractQuery#asc()}.
   * <p>
   * Method under test: {@link AbstractQuery#asc()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.query.Query AbstractQuery.asc()"})
  public void testAsc2() {
    // Arrange
    QueryProperty property = mock(QueryProperty.class);
    when(property.getName()).thenThrow(new ActivitiIllegalArgumentException("An error occurred"));

    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.orderBy(property);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deadLetterJobQueryImpl.asc());
    verify(property).getName();
  }

  /**
   * Test {@link AbstractQuery#asc()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractQuery#asc()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.query.Query AbstractQuery.asc()"})
  public void testAsc_givenDeadLetterJobQueryImpl_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new DeadLetterJobQueryImpl()).asc());
  }

  /**
   * Test {@link AbstractQuery#asc()}.
   * <ul>
   *   <li>Given {@link QueryProperty} {@link QueryProperty#getName()} return {@code Name}.</li>
   *   <li>Then return {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractQuery#asc()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.query.Query AbstractQuery.asc()"})
  public void testAsc_givenQueryPropertyGetNameReturnName_thenReturnDeadLetterJobQueryImpl() {
    // Arrange
    QueryProperty property = mock(QueryProperty.class);
    when(property.getName()).thenReturn("Name");

    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.orderBy(property);

    // Act
    DeadLetterJobQuery actualAscResult = deadLetterJobQueryImpl.asc();

    // Assert
    verify(property).getName();
    assertSame(deadLetterJobQueryImpl, actualAscResult);
  }

  /**
   * Test {@link AbstractQuery#desc()}.
   * <p>
   * Method under test: {@link AbstractQuery#desc()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.query.Query AbstractQuery.desc()"})
  public void testDesc() {
    // Arrange
    QueryProperty property = mock(QueryProperty.class);
    when(property.getName()).thenThrow(new ActivitiIllegalArgumentException("An error occurred"));

    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.orderBy(property);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deadLetterJobQueryImpl.desc());
    verify(property).getName();
  }

  /**
   * Test {@link AbstractQuery#desc()}.
   * <ul>
   *   <li>Given {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractQuery#desc()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.query.Query AbstractQuery.desc()"})
  public void testDesc_givenDeadLetterJobQueryImpl_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new DeadLetterJobQueryImpl()).desc());
  }

  /**
   * Test {@link AbstractQuery#desc()}.
   * <ul>
   *   <li>Then {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()} OrderBy is {@code desc, Name desc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractQuery#desc()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.query.Query AbstractQuery.desc()"})
  public void testDesc_thenDeadLetterJobQueryImplOrderByIsDescNameDesc() {
    // Arrange
    QueryProperty property = mock(QueryProperty.class);
    when(property.getName()).thenReturn("Name");

    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.addOrder("", AbstractQuery.SORTORDER_DESC, NullHandlingOnOrder.NULLS_LAST);
    deadLetterJobQueryImpl.orderBy(property);

    // Act
    DeadLetterJobQuery actualDescResult = deadLetterJobQueryImpl.desc();

    // Assert
    verify(property).getName();
    assertTrue(actualDescResult instanceof DeadLetterJobQueryImpl);
    assertEquals(" desc, Name desc", deadLetterJobQueryImpl.getOrderBy());
    assertEquals(" desc, Name desc", ((DeadLetterJobQueryImpl) actualDescResult).getOrderBy());
    assertEquals(" desc, Name desc", deadLetterJobQueryImpl.getOrderByColumns());
    assertEquals(" desc, Name desc", ((DeadLetterJobQueryImpl) actualDescResult).getOrderByColumns());
    assertEquals(" desc, Name desc", deadLetterJobQueryImpl.orderBy);
    assertEquals(" desc, Name desc", ((DeadLetterJobQueryImpl) actualDescResult).orderBy);
  }

  /**
   * Test {@link AbstractQuery#desc()}.
   * <ul>
   *   <li>Then {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()} OrderBy is {@code Name desc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractQuery#desc()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.query.Query AbstractQuery.desc()"})
  public void testDesc_thenDeadLetterJobQueryImplOrderByIsNameDesc() {
    // Arrange
    QueryProperty property = mock(QueryProperty.class);
    when(property.getName()).thenReturn("Name");

    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.orderBy(property);

    // Act
    DeadLetterJobQuery actualDescResult = deadLetterJobQueryImpl.desc();

    // Assert
    verify(property).getName();
    assertTrue(actualDescResult instanceof DeadLetterJobQueryImpl);
    assertEquals("Name desc", deadLetterJobQueryImpl.getOrderBy());
    assertEquals("Name desc", ((DeadLetterJobQueryImpl) actualDescResult).getOrderBy());
    assertEquals("Name desc", deadLetterJobQueryImpl.getOrderByColumns());
    assertEquals("Name desc", ((DeadLetterJobQueryImpl) actualDescResult).getOrderByColumns());
    assertEquals("Name desc", deadLetterJobQueryImpl.orderBy);
    assertEquals("Name desc", ((DeadLetterJobQueryImpl) actualDescResult).orderBy);
  }

  /**
   * Test {@link AbstractQuery#direction(Direction)}.
   * <p>
   * Method under test: {@link AbstractQuery#direction(Direction)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.query.Query AbstractQuery.direction(Direction)"})
  public void testDirection() {
    // Arrange
    QueryProperty property = mock(QueryProperty.class);
    when(property.getName()).thenReturn("Name");

    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.addOrder("", AbstractQuery.SORTORDER_DESC, NullHandlingOnOrder.NULLS_LAST);
    deadLetterJobQueryImpl.orderBy(property);

    // Act
    DeadLetterJobQuery actualDirectionResult = deadLetterJobQueryImpl.direction(Direction.ASCENDING);

    // Assert
    verify(property).getName();
    assertSame(deadLetterJobQueryImpl, actualDirectionResult);
  }

  /**
   * Test {@link AbstractQuery#direction(Direction)}.
   * <p>
   * Method under test: {@link AbstractQuery#direction(Direction)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.query.Query AbstractQuery.direction(Direction)"})
  public void testDirection2() {
    // Arrange
    QueryProperty property = mock(QueryProperty.class);
    when(property.getName()).thenThrow(new ActivitiIllegalArgumentException("An error occurred"));

    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.orderBy(property);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deadLetterJobQueryImpl.direction(Direction.ASCENDING));
    verify(property).getName();
  }

  /**
   * Test {@link AbstractQuery#direction(Direction)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractQuery#direction(Direction)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.query.Query AbstractQuery.direction(Direction)"})
  public void testDirection_givenDeadLetterJobQueryImpl() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new DeadLetterJobQueryImpl()).direction(Direction.ASCENDING));
  }

  /**
   * Test {@link AbstractQuery#direction(Direction)}.
   * <ul>
   *   <li>Then return {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractQuery#direction(Direction)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.query.Query AbstractQuery.direction(Direction)"})
  public void testDirection_thenReturnDeadLetterJobQueryImpl() {
    // Arrange
    QueryProperty property = mock(QueryProperty.class);
    when(property.getName()).thenReturn("Name");

    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.orderBy(property);

    // Act
    DeadLetterJobQuery actualDirectionResult = deadLetterJobQueryImpl.direction(Direction.ASCENDING);

    // Assert
    verify(property).getName();
    assertSame(deadLetterJobQueryImpl, actualDirectionResult);
  }

  /**
   * Test {@link AbstractQuery#checkQueryOk()}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractQuery#checkQueryOk()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractQuery.checkQueryOk()"})
  public void testCheckQueryOk_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.orderBy(mock(QueryProperty.class));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deadLetterJobQueryImpl.checkQueryOk());
  }

  /**
   * Test {@link AbstractQuery#singleResult()}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractQuery#singleResult()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.Object AbstractQuery.singleResult()"})
  public void testSingleResult_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.orderBy(mock(QueryProperty.class));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deadLetterJobQueryImpl.singleResult());
  }

  /**
   * Test {@link AbstractQuery#list()}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractQuery#list()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.util.List AbstractQuery.list()"})
  public void testList_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.orderBy(mock(QueryProperty.class));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deadLetterJobQueryImpl.list());
  }

  /**
   * Test {@link AbstractQuery#listPage(int, int)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractQuery#listPage(int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.util.List AbstractQuery.listPage(int, int)"})
  public void testListPage_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.orderBy(mock(QueryProperty.class));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deadLetterJobQueryImpl.listPage(1, 3));
  }

  /**
   * Test {@link AbstractQuery#count()}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractQuery#count()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"long AbstractQuery.count()"})
  public void testCount_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.orderBy(mock(QueryProperty.class));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deadLetterJobQueryImpl.count());
  }

  /**
   * Test {@link AbstractQuery#execute(CommandContext)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractQuery#execute(CommandContext)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.Object AbstractQuery.execute(CommandContext)"})
  public void testExecute_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.orderBy(mock(QueryProperty.class));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deadLetterJobQueryImpl.execute(null));
  }

  /**
   * Test {@link AbstractQuery#executeSingleResult(CommandContext)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractQuery#executeSingleResult(CommandContext)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.Object AbstractQuery.executeSingleResult(CommandContext)"})
  public void testExecuteSingleResult_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.orderBy(mock(QueryProperty.class));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deadLetterJobQueryImpl.executeSingleResult(null));
  }

  /**
   * Test {@link AbstractQuery#addOrder(String, String, NullHandlingOnOrder)}.
   * <p>
   * Method under test: {@link AbstractQuery#addOrder(String, String, NullHandlingOnOrder)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractQuery.addOrder(String, String, NullHandlingOnOrder)"})
  public void testAddOrder() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.setDatabaseType("db2");
    deadLetterJobQueryImpl.addOrder("h2", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_FIRST);

    // Act
    deadLetterJobQueryImpl.addOrder("Column", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_FIRST);

    // Assert
    assertEquals("case when h2 is null then 0 else 1 end,h2 asc, case when Column is null then 0 else 1 end,Column asc",
        deadLetterJobQueryImpl.getOrderBy());
    assertEquals("case when h2 is null then 0 else 1 end,h2 asc, case when Column is null then 0 else 1 end,Column asc",
        deadLetterJobQueryImpl.getOrderByColumns());
    assertEquals("case when h2 is null then 0 else 1 end,h2 asc, case when Column is null then 0 else 1 end,Column asc",
        deadLetterJobQueryImpl.orderBy);
  }

  /**
   * Test {@link AbstractQuery#addOrder(String, String, NullHandlingOnOrder)}.
   * <p>
   * Method under test: {@link AbstractQuery#addOrder(String, String, NullHandlingOnOrder)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractQuery.addOrder(String, String, NullHandlingOnOrder)"})
  public void testAddOrder2() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.setDatabaseType("db2");
    deadLetterJobQueryImpl.addOrder("h2", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_FIRST);

    // Act
    deadLetterJobQueryImpl.addOrder("Column", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_LAST);

    // Assert
    assertEquals("case when h2 is null then 0 else 1 end,h2 asc, case when Column is null then 1 else 0 end,Column asc",
        deadLetterJobQueryImpl.getOrderBy());
    assertEquals("case when h2 is null then 0 else 1 end,h2 asc, case when Column is null then 1 else 0 end,Column asc",
        deadLetterJobQueryImpl.getOrderByColumns());
    assertEquals("case when h2 is null then 0 else 1 end,h2 asc, case when Column is null then 1 else 0 end,Column asc",
        deadLetterJobQueryImpl.orderBy);
  }

  /**
   * Test {@link AbstractQuery#addOrder(String, String, NullHandlingOnOrder)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()} DatabaseType is {@code h2}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractQuery#addOrder(String, String, NullHandlingOnOrder)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractQuery.addOrder(String, String, NullHandlingOnOrder)"})
  public void testAddOrder_givenDeadLetterJobQueryImplDatabaseTypeIsH2() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.setDatabaseType("h2");
    deadLetterJobQueryImpl.addOrder("h2", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_FIRST);

    // Act
    deadLetterJobQueryImpl.addOrder("Column", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_FIRST);

    // Assert
    assertEquals("h2 asc NULLS FIRST, Column asc NULLS FIRST", deadLetterJobQueryImpl.getOrderBy());
    assertEquals("h2 asc NULLS FIRST, Column asc NULLS FIRST", deadLetterJobQueryImpl.getOrderByColumns());
    assertEquals("h2 asc NULLS FIRST, Column asc NULLS FIRST", deadLetterJobQueryImpl.orderBy);
  }

  /**
   * Test {@link AbstractQuery#addOrder(String, String, NullHandlingOnOrder)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()} DatabaseType is {@code h2}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractQuery#addOrder(String, String, NullHandlingOnOrder)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractQuery.addOrder(String, String, NullHandlingOnOrder)"})
  public void testAddOrder_givenDeadLetterJobQueryImplDatabaseTypeIsH22() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.setDatabaseType("h2");
    deadLetterJobQueryImpl.addOrder("h2", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_FIRST);

    // Act
    deadLetterJobQueryImpl.addOrder("Column", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_LAST);

    // Assert
    assertEquals("h2 asc NULLS FIRST, Column asc NULLS LAST", deadLetterJobQueryImpl.getOrderBy());
    assertEquals("h2 asc NULLS FIRST, Column asc NULLS LAST", deadLetterJobQueryImpl.getOrderByColumns());
    assertEquals("h2 asc NULLS FIRST, Column asc NULLS LAST", deadLetterJobQueryImpl.orderBy);
  }

  /**
   * Test {@link AbstractQuery#addOrder(String, String, NullHandlingOnOrder)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()} DatabaseType is {@code hsql}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractQuery#addOrder(String, String, NullHandlingOnOrder)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractQuery.addOrder(String, String, NullHandlingOnOrder)"})
  public void testAddOrder_givenDeadLetterJobQueryImplDatabaseTypeIsHsql() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.setDatabaseType("hsql");
    deadLetterJobQueryImpl.addOrder("h2", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_FIRST);

    // Act
    deadLetterJobQueryImpl.addOrder("Column", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_FIRST);

    // Assert
    assertEquals("h2 asc NULLS FIRST, Column asc NULLS FIRST", deadLetterJobQueryImpl.getOrderBy());
    assertEquals("h2 asc NULLS FIRST, Column asc NULLS FIRST", deadLetterJobQueryImpl.getOrderByColumns());
    assertEquals("h2 asc NULLS FIRST, Column asc NULLS FIRST", deadLetterJobQueryImpl.orderBy);
  }

  /**
   * Test {@link AbstractQuery#addOrder(String, String, NullHandlingOnOrder)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()} DatabaseType is {@code hsql}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractQuery#addOrder(String, String, NullHandlingOnOrder)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractQuery.addOrder(String, String, NullHandlingOnOrder)"})
  public void testAddOrder_givenDeadLetterJobQueryImplDatabaseTypeIsHsql2() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.setDatabaseType("hsql");
    deadLetterJobQueryImpl.addOrder("h2", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_FIRST);

    // Act
    deadLetterJobQueryImpl.addOrder("Column", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_LAST);

    // Assert
    assertEquals("h2 asc NULLS FIRST, Column asc NULLS LAST", deadLetterJobQueryImpl.getOrderBy());
    assertEquals("h2 asc NULLS FIRST, Column asc NULLS LAST", deadLetterJobQueryImpl.getOrderByColumns());
    assertEquals("h2 asc NULLS FIRST, Column asc NULLS LAST", deadLetterJobQueryImpl.orderBy);
  }

  /**
   * Test {@link AbstractQuery#addOrder(String, String, NullHandlingOnOrder)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()} DatabaseType is {@code mariadb}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractQuery#addOrder(String, String, NullHandlingOnOrder)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractQuery.addOrder(String, String, NullHandlingOnOrder)"})
  public void testAddOrder_givenDeadLetterJobQueryImplDatabaseTypeIsMariadb() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.setDatabaseType("mariadb");
    deadLetterJobQueryImpl.addOrder("h2", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_FIRST);

    // Act
    deadLetterJobQueryImpl.addOrder("Column", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_LAST);

    // Assert
    assertEquals("isnull(h2) desc,h2 asc, isnull(Column) asc,Column asc", deadLetterJobQueryImpl.getOrderBy());
    assertEquals("isnull(h2) desc,h2 asc, isnull(Column) asc,Column asc", deadLetterJobQueryImpl.getOrderByColumns());
    assertEquals("isnull(h2) desc,h2 asc, isnull(Column) asc,Column asc", deadLetterJobQueryImpl.orderBy);
  }

  /**
   * Test {@link AbstractQuery#addOrder(String, String, NullHandlingOnOrder)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()} DatabaseType is {@code mysql}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractQuery#addOrder(String, String, NullHandlingOnOrder)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractQuery.addOrder(String, String, NullHandlingOnOrder)"})
  public void testAddOrder_givenDeadLetterJobQueryImplDatabaseTypeIsMysql() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.setDatabaseType("mysql");
    deadLetterJobQueryImpl.addOrder("h2", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_FIRST);

    // Act
    deadLetterJobQueryImpl.addOrder("Column", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_LAST);

    // Assert
    assertEquals("isnull(h2) desc,h2 asc, isnull(Column) asc,Column asc", deadLetterJobQueryImpl.getOrderBy());
    assertEquals("isnull(h2) desc,h2 asc, isnull(Column) asc,Column asc", deadLetterJobQueryImpl.getOrderByColumns());
    assertEquals("isnull(h2) desc,h2 asc, isnull(Column) asc,Column asc", deadLetterJobQueryImpl.orderBy);
  }

  /**
   * Test {@link AbstractQuery#addOrder(String, String, NullHandlingOnOrder)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()} DatabaseType is {@code oracle}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractQuery#addOrder(String, String, NullHandlingOnOrder)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractQuery.addOrder(String, String, NullHandlingOnOrder)"})
  public void testAddOrder_givenDeadLetterJobQueryImplDatabaseTypeIsOracle() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.setDatabaseType("oracle");
    deadLetterJobQueryImpl.addOrder("h2", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_FIRST);

    // Act
    deadLetterJobQueryImpl.addOrder("Column", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_LAST);

    // Assert
    assertEquals("h2 asc NULLS FIRST, Column asc NULLS LAST", deadLetterJobQueryImpl.getOrderBy());
    assertEquals("h2 asc NULLS FIRST, Column asc NULLS LAST", deadLetterJobQueryImpl.getOrderByColumns());
    assertEquals("h2 asc NULLS FIRST, Column asc NULLS LAST", deadLetterJobQueryImpl.orderBy);
  }

  /**
   * Test {@link AbstractQuery#addOrder(String, String, NullHandlingOnOrder)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()} DatabaseType is {@code oracle}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractQuery#addOrder(String, String, NullHandlingOnOrder)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractQuery.addOrder(String, String, NullHandlingOnOrder)"})
  public void testAddOrder_givenDeadLetterJobQueryImplDatabaseTypeIsOracle2() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.setDatabaseType("oracle");
    deadLetterJobQueryImpl.addOrder("h2", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_FIRST);

    // Act
    deadLetterJobQueryImpl.addOrder("Column", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_FIRST);

    // Assert
    assertEquals("h2 asc NULLS FIRST, Column asc NULLS FIRST", deadLetterJobQueryImpl.getOrderBy());
    assertEquals("h2 asc NULLS FIRST, Column asc NULLS FIRST", deadLetterJobQueryImpl.getOrderByColumns());
    assertEquals("h2 asc NULLS FIRST, Column asc NULLS FIRST", deadLetterJobQueryImpl.orderBy);
  }

  /**
   * Test {@link AbstractQuery#addOrder(String, String, NullHandlingOnOrder)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()} DatabaseType is {@code postgres}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractQuery#addOrder(String, String, NullHandlingOnOrder)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractQuery.addOrder(String, String, NullHandlingOnOrder)"})
  public void testAddOrder_givenDeadLetterJobQueryImplDatabaseTypeIsPostgres() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.setDatabaseType("postgres");
    deadLetterJobQueryImpl.addOrder("h2", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_FIRST);

    // Act
    deadLetterJobQueryImpl.addOrder("Column", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_LAST);

    // Assert
    assertEquals("h2 asc NULLS FIRST, Column asc NULLS LAST", deadLetterJobQueryImpl.getOrderBy());
    assertEquals("h2 asc NULLS FIRST, Column asc NULLS LAST", deadLetterJobQueryImpl.getOrderByColumns());
    assertEquals("h2 asc NULLS FIRST, Column asc NULLS LAST", deadLetterJobQueryImpl.orderBy);
  }

  /**
   * Test {@link AbstractQuery#addOrder(String, String, NullHandlingOnOrder)}.
   * <ul>
   *   <li>Given {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()} DatabaseType is {@code postgres}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractQuery#addOrder(String, String, NullHandlingOnOrder)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractQuery.addOrder(String, String, NullHandlingOnOrder)"})
  public void testAddOrder_givenDeadLetterJobQueryImplDatabaseTypeIsPostgres2() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.setDatabaseType("postgres");
    deadLetterJobQueryImpl.addOrder("h2", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_FIRST);

    // Act
    deadLetterJobQueryImpl.addOrder("Column", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_FIRST);

    // Assert
    assertEquals("h2 asc NULLS FIRST, Column asc NULLS FIRST", deadLetterJobQueryImpl.getOrderBy());
    assertEquals("h2 asc NULLS FIRST, Column asc NULLS FIRST", deadLetterJobQueryImpl.getOrderByColumns());
    assertEquals("h2 asc NULLS FIRST, Column asc NULLS FIRST", deadLetterJobQueryImpl.orderBy);
  }

  /**
   * Test {@link AbstractQuery#addOrder(String, String, NullHandlingOnOrder)}.
   * <ul>
   *   <li>Then {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()} OrderBy is {@code Column asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractQuery#addOrder(String, String, NullHandlingOnOrder)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractQuery.addOrder(String, String, NullHandlingOnOrder)"})
  public void testAddOrder_thenDeadLetterJobQueryImplOrderByIsColumnAsc() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();

    // Act
    deadLetterJobQueryImpl.addOrder("Column", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_FIRST);

    // Assert
    assertEquals("Column asc", deadLetterJobQueryImpl.getOrderBy());
    assertEquals("Column asc", deadLetterJobQueryImpl.getOrderByColumns());
    assertEquals("Column asc", deadLetterJobQueryImpl.orderBy);
  }

  /**
   * Test {@link AbstractQuery#addOrder(String, String, NullHandlingOnOrder)}.
   * <ul>
   *   <li>Then {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()} OrderBy is {@code Column asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractQuery#addOrder(String, String, NullHandlingOnOrder)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractQuery.addOrder(String, String, NullHandlingOnOrder)"})
  public void testAddOrder_thenDeadLetterJobQueryImplOrderByIsColumnAsc2() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();

    // Act
    deadLetterJobQueryImpl.addOrder("Column", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_LAST);

    // Assert
    assertEquals("Column asc", deadLetterJobQueryImpl.getOrderBy());
    assertEquals("Column asc", deadLetterJobQueryImpl.getOrderByColumns());
    assertEquals("Column asc", deadLetterJobQueryImpl.orderBy);
  }

  /**
   * Test {@link AbstractQuery#addOrder(String, String, NullHandlingOnOrder)}.
   * <ul>
   *   <li>Then {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()} OrderBy is {@code h2 asc, Column asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractQuery#addOrder(String, String, NullHandlingOnOrder)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractQuery.addOrder(String, String, NullHandlingOnOrder)"})
  public void testAddOrder_thenDeadLetterJobQueryImplOrderByIsH2AscColumnAsc() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.addOrder("h2", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_FIRST);

    // Act
    deadLetterJobQueryImpl.addOrder("Column", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_FIRST);

    // Assert
    assertEquals("h2 asc, Column asc", deadLetterJobQueryImpl.getOrderBy());
    assertEquals("h2 asc, Column asc", deadLetterJobQueryImpl.getOrderByColumns());
    assertEquals("h2 asc, Column asc", deadLetterJobQueryImpl.orderBy);
  }

  /**
   * Test {@link AbstractQuery#addOrder(String, String, NullHandlingOnOrder)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()} OrderBy is {@code Column asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractQuery#addOrder(String, String, NullHandlingOnOrder)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractQuery.addOrder(String, String, NullHandlingOnOrder)"})
  public void testAddOrder_whenNull_thenDeadLetterJobQueryImplOrderByIsColumnAsc() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();

    // Act
    deadLetterJobQueryImpl.addOrder("Column", AbstractQuery.SORTORDER_ASC, null);

    // Assert
    assertEquals("Column asc", deadLetterJobQueryImpl.getOrderBy());
    assertEquals("Column asc", deadLetterJobQueryImpl.getOrderByColumns());
    assertEquals("Column asc", deadLetterJobQueryImpl.orderBy);
  }

  /**
   * Test {@link AbstractQuery#getOrderBy()}.
   * <ul>
   *   <li>Then return {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractQuery#getOrderBy()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String AbstractQuery.getOrderBy()"})
  public void testGetOrderBy_thenReturnResIdAsc() {
    // Arrange, Act and Assert
    assertEquals("RES.ID_ asc", (new DeadLetterJobQueryImpl()).getOrderBy());
  }

  /**
   * Test {@link AbstractQuery#getOrderBy()}.
   * <ul>
   *   <li>Then return {@code RES.ID_ asc asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractQuery#getOrderBy()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String AbstractQuery.getOrderBy()"})
  public void testGetOrderBy_thenReturnResIdAscAsc() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.addOrder("RES.ID_ asc", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_FIRST);

    // Act and Assert
    assertEquals("RES.ID_ asc asc", deadLetterJobQueryImpl.getOrderBy());
  }

  /**
   * Test {@link AbstractQuery#getOrderByColumns()}.
   * <ul>
   *   <li>Then return {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractQuery#getOrderByColumns()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String AbstractQuery.getOrderByColumns()"})
  public void testGetOrderByColumns_thenReturnResIdAsc() {
    // Arrange, Act and Assert
    assertEquals("RES.ID_ asc", (new DeadLetterJobQueryImpl()).getOrderByColumns());
  }

  /**
   * Test {@link AbstractQuery#getOrderByColumns()}.
   * <ul>
   *   <li>Then return {@code RES.ID_ asc asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractQuery#getOrderByColumns()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String AbstractQuery.getOrderByColumns()"})
  public void testGetOrderByColumns_thenReturnResIdAscAsc() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.addOrder("RES.ID_ asc", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_FIRST);

    // Act and Assert
    assertEquals("RES.ID_ asc asc", deadLetterJobQueryImpl.getOrderByColumns());
  }

  /**
   * Test {@link AbstractQuery#getDatabaseType()}.
   * <p>
   * Method under test: {@link AbstractQuery#getDatabaseType()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String AbstractQuery.getDatabaseType()"})
  public void testGetDatabaseType() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobQueryImpl()).getDatabaseType());
  }

  /**
   * Test {@link AbstractQuery#setDatabaseType(String)}.
   * <p>
   * Method under test: {@link AbstractQuery#setDatabaseType(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractQuery.setDatabaseType(String)"})
  public void testSetDatabaseType() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();

    // Act
    deadLetterJobQueryImpl.setDatabaseType("Database Type");

    // Assert
    assertEquals("Database Type", deadLetterJobQueryImpl.getDatabaseType());
  }
}
