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
package org.activiti.engine.impl.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ListQueryParameterObjectDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ListQueryParameterObject#ListQueryParameterObject()}
   *   <li>{@link ListQueryParameterObject#setDatabaseType(String)}
   *   <li>{@link ListQueryParameterObject#setFirstResult(int)}
   *   <li>{@link ListQueryParameterObject#setMaxResults(int)}
   *   <li>{@link ListQueryParameterObject#setParameter(Object)}
   *   <li>{@link ListQueryParameterObject#getDatabaseType()}
   *   <li>{@link ListQueryParameterObject#getFirstResult()}
   *   <li>{@link ListQueryParameterObject#getMaxResults()}
   *   <li>{@link ListQueryParameterObject#getOrderBy()}
   *   <li>{@link ListQueryParameterObject#getParameter()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ListQueryParameterObject.<init>()",
    "void ListQueryParameterObject.<init>(Object, int, int)",
    "String ListQueryParameterObject.getDatabaseType()",
    "int ListQueryParameterObject.getFirstResult()",
    "int ListQueryParameterObject.getMaxResults()",
    "String ListQueryParameterObject.getOrderBy()",
    "Object ListQueryParameterObject.getParameter()",
    "void ListQueryParameterObject.setDatabaseType(String)",
    "void ListQueryParameterObject.setFirstResult(int)",
    "void ListQueryParameterObject.setMaxResults(int)",
    "void ListQueryParameterObject.setParameter(Object)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    ListQueryParameterObject actualListQueryParameterObject = new ListQueryParameterObject();
    actualListQueryParameterObject.setDatabaseType("Database Type");
    actualListQueryParameterObject.setFirstResult(1);
    actualListQueryParameterObject.setMaxResults(3);
    Object object = JSONObject.NULL;
    actualListQueryParameterObject.setParameter(object);
    String actualDatabaseType = actualListQueryParameterObject.getDatabaseType();
    int actualFirstResult = actualListQueryParameterObject.getFirstResult();
    int actualMaxResults = actualListQueryParameterObject.getMaxResults();
    String actualOrderBy = actualListQueryParameterObject.getOrderBy();

    // Assert
    assertEquals("Database Type", actualDatabaseType);
    assertEquals("RES.ID_ asc", actualOrderBy);
    assertEquals(1, actualFirstResult);
    assertEquals(3, actualMaxResults);
    assertSame(object, actualListQueryParameterObject.getParameter());
  }

  /**
   * Test getters and setters.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ListQueryParameterObject#ListQueryParameterObject(Object, int, int)}
   *   <li>{@link ListQueryParameterObject#setDatabaseType(String)}
   *   <li>{@link ListQueryParameterObject#setFirstResult(int)}
   *   <li>{@link ListQueryParameterObject#setMaxResults(int)}
   *   <li>{@link ListQueryParameterObject#setParameter(Object)}
   *   <li>{@link ListQueryParameterObject#getDatabaseType()}
   *   <li>{@link ListQueryParameterObject#getFirstResult()}
   *   <li>{@link ListQueryParameterObject#getMaxResults()}
   *   <li>{@link ListQueryParameterObject#getOrderBy()}
   *   <li>{@link ListQueryParameterObject#getParameter()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ListQueryParameterObject.<init>()",
    "void ListQueryParameterObject.<init>(Object, int, int)",
    "String ListQueryParameterObject.getDatabaseType()",
    "int ListQueryParameterObject.getFirstResult()",
    "int ListQueryParameterObject.getMaxResults()",
    "String ListQueryParameterObject.getOrderBy()",
    "Object ListQueryParameterObject.getParameter()",
    "void ListQueryParameterObject.setDatabaseType(String)",
    "void ListQueryParameterObject.setFirstResult(int)",
    "void ListQueryParameterObject.setMaxResults(int)",
    "void ListQueryParameterObject.setParameter(Object)"
  })
  public void testGettersAndSetters_whenNull() {
    // Arrange and Act
    ListQueryParameterObject actualListQueryParameterObject =
        new ListQueryParameterObject(JSONObject.NULL, 1, 3);
    actualListQueryParameterObject.setDatabaseType("Database Type");
    actualListQueryParameterObject.setFirstResult(1);
    actualListQueryParameterObject.setMaxResults(3);
    Object object = JSONObject.NULL;
    actualListQueryParameterObject.setParameter(object);
    String actualDatabaseType = actualListQueryParameterObject.getDatabaseType();
    int actualFirstResult = actualListQueryParameterObject.getFirstResult();
    int actualMaxResults = actualListQueryParameterObject.getMaxResults();
    String actualOrderBy = actualListQueryParameterObject.getOrderBy();

    // Assert
    assertEquals("Database Type", actualDatabaseType);
    assertEquals("RES.ID_ asc", actualOrderBy);
    assertEquals(1, actualFirstResult);
    assertEquals(3, actualMaxResults);
    assertSame(object, actualListQueryParameterObject.getParameter());
  }

  /**
   * Test {@link ListQueryParameterObject#getFirstRow()}.
   *
   * <p>Method under test: {@link ListQueryParameterObject#getFirstRow()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ListQueryParameterObject.getFirstRow()"})
  public void testGetFirstRow() {
    // Arrange, Act and Assert
    assertEquals(1, new ListQueryParameterObject().getFirstRow());
  }

  /**
   * Test {@link ListQueryParameterObject#getLastRow()}.
   *
   * <ul>
   *   <li>Given {@link ListQueryParameterObject#ListQueryParameterObject()}.
   *   <li>Then return {@link Integer#MAX_VALUE}.
   * </ul>
   *
   * <p>Method under test: {@link ListQueryParameterObject#getLastRow()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ListQueryParameterObject.getLastRow()"})
  public void testGetLastRow_givenListQueryParameterObject_thenReturnMax_value() {
    // Arrange, Act and Assert
    assertEquals(Integer.MAX_VALUE, new ListQueryParameterObject().getLastRow());
  }

  /**
   * Test {@link ListQueryParameterObject#getLastRow()}.
   *
   * <ul>
   *   <li>Then return {@code -2147483645}.
   * </ul>
   *
   * <p>Method under test: {@link ListQueryParameterObject#getLastRow()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ListQueryParameterObject.getLastRow()"})
  public void testGetLastRow_thenReturn2147483645() {
    // Arrange
    ListQueryParameterObject listQueryParameterObject =
        new ListQueryParameterObject(JSONObject.NULL, Integer.MAX_VALUE, 3);

    // Act and Assert
    assertEquals(-2147483645, listQueryParameterObject.getLastRow());
  }

  /**
   * Test {@link ListQueryParameterObject#getOrderByColumns()}.
   *
   * <ul>
   *   <li>Given {@link ListQueryParameterObject#ListQueryParameterObject()}.
   * </ul>
   *
   * <p>Method under test: {@link ListQueryParameterObject#getOrderByColumns()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ListQueryParameterObject.getOrderByColumns()"})
  public void testGetOrderByColumns_givenListQueryParameterObject() {
    // Arrange, Act and Assert
    assertEquals("RES.ID_ asc", new ListQueryParameterObject().getOrderByColumns());
  }
}
