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
package org.activiti.engine.impl.variable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class JodaDateTypeDiffblueTest {
  /**
   * Test {@link JodaDateType#isAbleToStore(Object)}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JodaDateType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JodaDateType.isAbleToStore(Object)"})
  public void testIsAbleToStore_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new JodaDateType().isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link JodaDateType#isAbleToStore(Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link JodaDateType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JodaDateType.isAbleToStore(Object)"})
  public void testIsAbleToStore_whenNull_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(new JodaDateType().isAbleToStore(null));
  }

  /**
   * Test {@link JodaDateType#getValue(ValueFields)}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.
   *   <li>Then return toString is {@code 1970-01-01}.
   * </ul>
   *
   * <p>Method under test: {@link JodaDateType#getValue(ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JodaDateType.getValue(ValueFields)"})
  public void testGetValue_givenNull_thenReturnToStringIs19700101() {
    // Arrange
    JodaDateType jodaDateType = new JodaDateType();

    HistoricVariableInstanceEntityImpl valueFields = new HistoricVariableInstanceEntityImpl();
    valueFields.setCachedValue(JSONObject.NULL);
    valueFields.setCreateTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setDeleted(true);
    valueFields.setDoubleValue(10.0d);
    valueFields.setExecutionId("42");
    valueFields.setId("42");
    valueFields.setInserted(true);
    valueFields.setLastUpdatedTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setLongValue(42L);
    valueFields.setName("Name");
    valueFields.setProcessInstanceId("42");
    valueFields.setRevision(1);
    valueFields.setTaskId("42");
    valueFields.setTextValue("42");
    valueFields.setTextValue2("42");
    valueFields.setUpdated(true);
    valueFields.setVariableType(new BigDecimalType());

    // Act and Assert
    assertEquals("1970-01-01", jodaDateType.getValue(valueFields).toString());
  }

  /**
   * Test {@link JodaDateType#getValue(ValueFields)}.
   *
   * <ul>
   *   <li>When {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JodaDateType#getValue(ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JodaDateType.getValue(ValueFields)"})
  public void testGetValue_whenHistoricDetailVariableInstanceUpdateEntityImpl_thenReturnNull() {
    // Arrange
    JodaDateType jodaDateType = new JodaDateType();

    // Act and Assert
    assertNull(jodaDateType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link JodaDateType#setValue(Object, ValueFields)}.
   *
   * <p>Method under test: {@link JodaDateType#setValue(Object, ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JodaDateType.setValue(Object, ValueFields)"})
  public void testSetValue() {
    // Arrange
    JodaDateType jodaDateType = new JodaDateType();
    org.joda.time.LocalDate localDate = new org.joda.time.LocalDate(1970, 1, 1);
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields =
        new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    jodaDateType.setValue(localDate, valueFields);

    // Assert
    assertEquals(0L, valueFields.getLongValue().longValue());
  }

  /**
   * Test {@link JodaDateType#setValue(Object, ValueFields)}.
   *
   * <ul>
   *   <li>Then {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default constructor)
   *       LongValue is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JodaDateType#setValue(Object, ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JodaDateType.setValue(Object, ValueFields)"})
  public void testSetValue_thenHistoricDetailVariableInstanceUpdateEntityImplLongValueIsNull() {
    // Arrange
    JodaDateType jodaDateType = new JodaDateType();
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields =
        new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    jodaDateType.setValue(null, valueFields);

    // Assert that nothing has changed
    assertNull(valueFields.getLongValue());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link JodaDateType}
   *   <li>{@link JodaDateType#getTypeName()}
   *   <li>{@link JodaDateType#isCachable()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void JodaDateType.<init>()",
    "String JodaDateType.getTypeName()",
    "boolean JodaDateType.isCachable()"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    JodaDateType actualJodaDateType = new JodaDateType();
    String actualTypeName = actualJodaDateType.getTypeName();

    // Assert
    assertEquals("jodadate", actualTypeName);
    assertTrue(actualJodaDateType.isCachable());
  }
}
