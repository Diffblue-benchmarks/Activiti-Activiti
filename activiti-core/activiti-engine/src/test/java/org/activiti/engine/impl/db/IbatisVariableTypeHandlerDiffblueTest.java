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

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.variable.BigDecimalType;
import org.activiti.engine.impl.variable.VariableType;
import org.apache.ibatis.type.JdbcType;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class IbatisVariableTypeHandlerDiffblueTest {
  /**
   * Test {@link IbatisVariableTypeHandler#getResult(CallableStatement, int)} with {@code cs},
   * {@code columnIndex}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link IbatisVariableTypeHandler#getResult(CallableStatement, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"VariableType IbatisVariableTypeHandler.getResult(CallableStatement, int)"})
  public void testGetResultWithCsColumnIndex_thenThrowActivitiException() throws SQLException {
    // Arrange
    IbatisVariableTypeHandler ibatisVariableTypeHandler = new IbatisVariableTypeHandler();

    CallableStatement cs = mock(CallableStatement.class);
    when(cs.getString(anyInt())).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> ibatisVariableTypeHandler.getResult(cs, 1));
    verify(cs).getString(1);
  }

  /**
   * Test {@link IbatisVariableTypeHandler#setParameter(PreparedStatement, int, VariableType,
   * JdbcType)} with {@code PreparedStatement}, {@code int}, {@code VariableType}, {@code JdbcType}.
   *
   * <p>Method under test: {@link IbatisVariableTypeHandler#setParameter(PreparedStatement, int,
   * VariableType, JdbcType)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void IbatisVariableTypeHandler.setParameter(PreparedStatement, int, VariableType, JdbcType)"
  })
  public void testSetParameterWithPreparedStatementIntVariableTypeJdbcType() throws SQLException {
    // Arrange
    IbatisVariableTypeHandler ibatisVariableTypeHandler = new IbatisVariableTypeHandler();

    PreparedStatement ps = mock(PreparedStatement.class);
    doThrow(new ActivitiException("An error occurred"))
        .when(ps)
        .setString(anyInt(), Mockito.<String>any());

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> ibatisVariableTypeHandler.setParameter(ps, 1, new BigDecimalType(), JdbcType.ARRAY));
    verify(ps).setString(1, "bigdecimal");
  }

  /**
   * Test {@link IbatisVariableTypeHandler#setParameter(PreparedStatement, int, VariableType,
   * JdbcType)} with {@code PreparedStatement}, {@code int}, {@code VariableType}, {@code JdbcType}.
   *
   * <ul>
   *   <li>Then calls {@link PreparedStatement#setString(int, String)}.
   * </ul>
   *
   * <p>Method under test: {@link IbatisVariableTypeHandler#setParameter(PreparedStatement, int,
   * VariableType, JdbcType)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void IbatisVariableTypeHandler.setParameter(PreparedStatement, int, VariableType, JdbcType)"
  })
  public void testSetParameterWithPreparedStatementIntVariableTypeJdbcType_thenCallsSetString()
      throws SQLException {
    // Arrange
    IbatisVariableTypeHandler ibatisVariableTypeHandler = new IbatisVariableTypeHandler();

    PreparedStatement ps = mock(PreparedStatement.class);
    doNothing().when(ps).setString(anyInt(), Mockito.<String>any());

    // Act
    ibatisVariableTypeHandler.setParameter(ps, 1, new BigDecimalType(), JdbcType.ARRAY);

    // Assert
    verify(ps).setString(1, "bigdecimal");
  }

  /**
   * Test new {@link IbatisVariableTypeHandler} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link IbatisVariableTypeHandler}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void IbatisVariableTypeHandler.<init>()"})
  public void testNewIbatisVariableTypeHandler() {
    // Arrange, Act and Assert
    assertNull(new IbatisVariableTypeHandler().variableTypes);
  }
}
