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
package org.activiti.engine.impl.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import org.activiti.engine.impl.persistence.entity.ByteArrayRef;
import org.apache.ibatis.type.JdbcType;
import org.h2.jdbc.JdbcResultSet;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class ByteArrayRefTypeHandlerDiffblueTest {
  /**
   * Test {@link ByteArrayRefTypeHandler#setParameter(PreparedStatement, int, ByteArrayRef,
   * JdbcType)} with {@code PreparedStatement}, {@code int}, {@code ByteArrayRef}, {@code JdbcType}.
   *
   * <p>Method under test: {@link ByteArrayRefTypeHandler#setParameter(PreparedStatement, int,
   * ByteArrayRef, JdbcType)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ByteArrayRefTypeHandler.setParameter(PreparedStatement, int, ByteArrayRef, JdbcType)"
  })
  public void testSetParameterWithPreparedStatementIntByteArrayRefJdbcType() throws SQLException {
    // Arrange
    ByteArrayRefTypeHandler byteArrayRefTypeHandler = new ByteArrayRefTypeHandler();

    PreparedStatement ps = mock(PreparedStatement.class);
    doNothing().when(ps).setString(anyInt(), Mockito.<String>any());

    // Act
    byteArrayRefTypeHandler.setParameter(ps, 1, new ByteArrayRef("42"), JdbcType.ARRAY);

    // Assert
    verify(ps).setString(1, "42");
  }

  /**
   * Test {@link ByteArrayRefTypeHandler#setParameter(PreparedStatement, int, ByteArrayRef,
   * JdbcType)} with {@code PreparedStatement}, {@code int}, {@code ByteArrayRef}, {@code JdbcType}.
   *
   * <p>Method under test: {@link ByteArrayRefTypeHandler#setParameter(PreparedStatement, int,
   * ByteArrayRef, JdbcType)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ByteArrayRefTypeHandler.setParameter(PreparedStatement, int, ByteArrayRef, JdbcType)"
  })
  public void testSetParameterWithPreparedStatementIntByteArrayRefJdbcType2() throws SQLException {
    // Arrange
    ByteArrayRefTypeHandler byteArrayRefTypeHandler = new ByteArrayRefTypeHandler();

    PreparedStatement ps = mock(PreparedStatement.class);
    doThrow(new SQLException()).when(ps).setString(anyInt(), Mockito.<String>any());

    // Act and Assert
    assertThrows(
        SQLException.class,
        () -> byteArrayRefTypeHandler.setParameter(ps, 1, new ByteArrayRef("42"), JdbcType.ARRAY));
    verify(ps).setString(1, "42");
  }

  /**
   * Test {@link ByteArrayRefTypeHandler#setParameter(PreparedStatement, int, ByteArrayRef,
   * JdbcType)} with {@code PreparedStatement}, {@code int}, {@code ByteArrayRef}, {@code JdbcType}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ByteArrayRefTypeHandler#setParameter(PreparedStatement, int,
   * ByteArrayRef, JdbcType)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ByteArrayRefTypeHandler.setParameter(PreparedStatement, int, ByteArrayRef, JdbcType)"
  })
  public void testSetParameterWithPreparedStatementIntByteArrayRefJdbcType_whenNull()
      throws SQLException {
    // Arrange
    ByteArrayRefTypeHandler byteArrayRefTypeHandler = new ByteArrayRefTypeHandler();

    PreparedStatement ps = mock(PreparedStatement.class);
    doNothing().when(ps).setString(anyInt(), Mockito.<String>any());

    // Act
    byteArrayRefTypeHandler.setParameter(ps, 1, null, JdbcType.ARRAY);

    // Assert
    verify(ps).setString(1, null);
  }

  /**
   * Test {@link ByteArrayRefTypeHandler#getResult(CallableStatement, int)} with {@code cs}, {@code
   * columnIndex}.
   *
   * <ul>
   *   <li>Given {@link SQLException#SQLException()}.
   *   <li>Then throw {@link SQLException}.
   * </ul>
   *
   * <p>Method under test: {@link ByteArrayRefTypeHandler#getResult(CallableStatement, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ByteArrayRef ByteArrayRefTypeHandler.getResult(CallableStatement, int)"})
  public void testGetResultWithCsColumnIndex_givenSQLException_thenThrowSQLException()
      throws SQLException {
    // Arrange
    ByteArrayRefTypeHandler byteArrayRefTypeHandler = new ByteArrayRefTypeHandler();

    CallableStatement cs = mock(CallableStatement.class);
    when(cs.getString(anyInt())).thenThrow(new SQLException());

    // Act and Assert
    assertThrows(SQLException.class, () -> byteArrayRefTypeHandler.getResult(cs, 1));
    verify(cs).getString(1);
  }

  /**
   * Test {@link ByteArrayRefTypeHandler#getResult(CallableStatement, int)} with {@code cs}, {@code
   * columnIndex}.
   *
   * <ul>
   *   <li>Given {@code String}.
   *   <li>Then return Id is {@code String}.
   * </ul>
   *
   * <p>Method under test: {@link ByteArrayRefTypeHandler#getResult(CallableStatement, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ByteArrayRef ByteArrayRefTypeHandler.getResult(CallableStatement, int)"})
  public void testGetResultWithCsColumnIndex_givenString_thenReturnIdIsString()
      throws SQLException {
    // Arrange
    ByteArrayRefTypeHandler byteArrayRefTypeHandler = new ByteArrayRefTypeHandler();

    CallableStatement cs = mock(CallableStatement.class);
    when(cs.getString(anyInt())).thenReturn("String");

    // Act
    ByteArrayRef actualResult = byteArrayRefTypeHandler.getResult(cs, 1);

    // Assert
    verify(cs).getString(1);
    assertEquals("String", actualResult.getId());
    assertNull(actualResult.getName());
    assertFalse(actualResult.isDeleted());
  }

  /**
   * Test {@link ByteArrayRefTypeHandler#getResult(ResultSet, int)} with {@code rs}, {@code
   * columnIndex}.
   *
   * <ul>
   *   <li>Given {@code String}.
   *   <li>Then return Id is {@code String}.
   * </ul>
   *
   * <p>Method under test: {@link ByteArrayRefTypeHandler#getResult(ResultSet, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ByteArrayRef ByteArrayRefTypeHandler.getResult(ResultSet, int)"})
  public void testGetResultWithRsColumnIndex_givenString_thenReturnIdIsString()
      throws SQLException {
    // Arrange
    ByteArrayRefTypeHandler byteArrayRefTypeHandler = new ByteArrayRefTypeHandler();

    JdbcResultSet rs = mock(JdbcResultSet.class);
    when(rs.getString(anyInt())).thenReturn("String");

    // Act
    ByteArrayRef actualResult = byteArrayRefTypeHandler.getResult(rs, 1);

    // Assert
    verify(rs).getString(1);
    assertEquals("String", actualResult.getId());
    assertNull(actualResult.getName());
    assertFalse(actualResult.isDeleted());
  }

  /**
   * Test {@link ByteArrayRefTypeHandler#getResult(ResultSet, String)} with {@code rs}, {@code
   * columnName}.
   *
   * <ul>
   *   <li>Given {@code String}.
   *   <li>Then return Id is {@code String}.
   * </ul>
   *
   * <p>Method under test: {@link ByteArrayRefTypeHandler#getResult(ResultSet, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ByteArrayRef ByteArrayRefTypeHandler.getResult(ResultSet, String)"})
  public void testGetResultWithRsColumnName_givenString_thenReturnIdIsString() throws SQLException {
    // Arrange
    ByteArrayRefTypeHandler byteArrayRefTypeHandler = new ByteArrayRefTypeHandler();

    JdbcResultSet rs = mock(JdbcResultSet.class);
    when(rs.getString(Mockito.<String>any())).thenReturn("String");

    // Act
    ByteArrayRef actualResult = byteArrayRefTypeHandler.getResult(rs, "Column Name");

    // Assert
    verify(rs).getString("Column Name");
    assertEquals("String", actualResult.getId());
    assertNull(actualResult.getName());
    assertFalse(actualResult.isDeleted());
  }
}
