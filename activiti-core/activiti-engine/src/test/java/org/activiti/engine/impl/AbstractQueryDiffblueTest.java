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
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.query.QueryProperty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AbstractQueryDiffblueTest {
  @InjectMocks
  private DeadLetterJobQueryImpl deadLetterJobQueryImpl;

  /**
   * Method under test: {@link AbstractQuery#asc()}
   */
  @Test
  public void testAsc() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new DeadLetterJobQueryImpl()).asc());
  }

  /**
   * Method under test: {@link AbstractQuery#desc()}
   */
  @Test
  public void testDesc() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new DeadLetterJobQueryImpl()).desc());
  }

  /**
   * Method under test: {@link AbstractQuery#direction(Direction)}
   */
  @Test
  public void testDirection() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new DeadLetterJobQueryImpl()).direction(Direction.ASCENDING));
  }

  /**
   * Method under test: {@link AbstractQuery#checkQueryOk()}
   */
  @Test
  public void testCheckQueryOk() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.orderBy(mock(QueryProperty.class));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deadLetterJobQueryImpl.checkQueryOk());
  }

  /**
   * Method under test: {@link AbstractQuery#execute(CommandContext)}
   */
  @Test
  public void testExecute() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.orderBy(mock(QueryProperty.class));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deadLetterJobQueryImpl.execute(null));
  }

  /**
   * Method under test: {@link AbstractQuery#executeSingleResult(CommandContext)}
   */
  @Test
  public void testExecuteSingleResult() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    deadLetterJobQueryImpl.orderBy(mock(QueryProperty.class));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deadLetterJobQueryImpl.executeSingleResult(null));
  }

  /**
   * Method under test:
   * {@link AbstractQuery#addOrder(String, String, AbstractQuery.NullHandlingOnOrder)}
   */
  @Test
  public void testAddOrder() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();

    // Act
    deadLetterJobQueryImpl.addOrder("Column", AbstractQuery.SORTORDER_ASC,
        AbstractQuery.NullHandlingOnOrder.NULLS_FIRST);

    // Assert
    assertEquals("Column asc", deadLetterJobQueryImpl.getOrderBy());
    assertEquals("Column asc", deadLetterJobQueryImpl.getOrderByColumns());
    assertEquals("Column asc", deadLetterJobQueryImpl.orderBy);
  }

  /**
   * Method under test: {@link AbstractQuery#getOrderBy()}
   */
  @Test
  public void testGetOrderBy() {
    // Arrange, Act and Assert
    assertEquals("RES.ID_ asc", (new DeadLetterJobQueryImpl()).getOrderBy());
  }

  /**
   * Method under test: {@link AbstractQuery#getOrderByColumns()}
   */
  @Test
  public void testGetOrderByColumns() {
    // Arrange, Act and Assert
    assertEquals("RES.ID_ asc", (new DeadLetterJobQueryImpl()).getOrderByColumns());
  }

  /**
   * Method under test: {@link AbstractQuery#getDatabaseType()}
   */
  @Test
  public void testGetDatabaseType() {
    // Arrange, Act and Assert
    assertNull((new DeadLetterJobQueryImpl()).getDatabaseType());
  }

  /**
   * Method under test: {@link AbstractQuery#setDatabaseType(String)}
   */
  @Test
  public void testSetDatabaseType() {
    // Arrange and Act
    deadLetterJobQueryImpl.setDatabaseType("Database Type");

    // Assert
    assertEquals("Database Type", deadLetterJobQueryImpl.getDatabaseType());
  }
}
