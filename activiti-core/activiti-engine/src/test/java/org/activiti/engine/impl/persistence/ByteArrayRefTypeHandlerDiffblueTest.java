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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.activiti.engine.impl.persistence.entity.ByteArrayRef;
import org.apache.ibatis.type.JdbcType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ByteArrayRefTypeHandlerDiffblueTest {
  @InjectMocks
  private ByteArrayRefTypeHandler byteArrayRefTypeHandler;

  /**
   * Method under test:
   * {@link ByteArrayRefTypeHandler#setParameter(PreparedStatement, int, ByteArrayRef, JdbcType)}
   */
  @Test
  public void testSetParameter() throws SQLException {
    // Arrange
    ByteArrayRefTypeHandler byteArrayRefTypeHandler = new ByteArrayRefTypeHandler();
    PreparedStatement ps = mock(PreparedStatement.class);
    doNothing().when(ps).setString(anyInt(), Mockito.<String>any());

    // Act
    byteArrayRefTypeHandler.setParameter(ps, 1, new ByteArrayRef("42"), JdbcType.ARRAY);

    // Assert that nothing has changed
    verify(ps).setString(eq(1), eq("42"));
  }

  /**
   * Method under test:
   * {@link ByteArrayRefTypeHandler#setParameter(PreparedStatement, int, ByteArrayRef, JdbcType)}
   */
  @Test
  public void testSetParameter2() throws SQLException {
    // Arrange
    ByteArrayRefTypeHandler byteArrayRefTypeHandler = new ByteArrayRefTypeHandler();
    PreparedStatement ps = mock(PreparedStatement.class);
    doNothing().when(ps).setString(anyInt(), Mockito.<String>any());

    // Act
    byteArrayRefTypeHandler.setParameter(ps, 1, null, JdbcType.ARRAY);

    // Assert that nothing has changed
    verify(ps).setString(eq(1), isNull());
  }

  /**
   * Method under test:
   * {@link ByteArrayRefTypeHandler#setParameter(PreparedStatement, int, ByteArrayRef, JdbcType)}
   */
  @Test
  public void testSetParameter3() throws SQLException {
    // Arrange
    ByteArrayRefTypeHandler byteArrayRefTypeHandler = new ByteArrayRefTypeHandler();
    PreparedStatement ps = mock(PreparedStatement.class);
    doThrow(new SQLException()).when(ps).setString(anyInt(), Mockito.<String>any());

    // Act and Assert
    assertThrows(SQLException.class,
        () -> byteArrayRefTypeHandler.setParameter(ps, 1, new ByteArrayRef("42"), JdbcType.ARRAY));
    verify(ps).setString(eq(1), eq("42"));
  }

  /**
   * Method under test:
   * {@link ByteArrayRefTypeHandler#getResult(CallableStatement, int)}
   */
  @Test
  public void testGetResult() throws SQLException {
    // Arrange
    ByteArrayRefTypeHandler byteArrayRefTypeHandler = new ByteArrayRefTypeHandler();
    CallableStatement cs = mock(CallableStatement.class);
    when(cs.getString(anyInt())).thenReturn("String");

    // Act
    ByteArrayRef actualResult = byteArrayRefTypeHandler.getResult(cs, 1);

    // Assert
    verify(cs).getString(eq(1));
    assertEquals("String", actualResult.getId());
    assertNull(actualResult.getName());
    assertFalse(actualResult.isDeleted());
  }

  /**
   * Method under test:
   * {@link ByteArrayRefTypeHandler#getResult(CallableStatement, int)}
   */
  @Test
  public void testGetResult2() throws SQLException {
    // Arrange
    ByteArrayRefTypeHandler byteArrayRefTypeHandler = new ByteArrayRefTypeHandler();
    CallableStatement cs = mock(CallableStatement.class);
    when(cs.getString(anyInt())).thenThrow(new SQLException());

    // Act and Assert
    assertThrows(SQLException.class, () -> byteArrayRefTypeHandler.getResult(cs, 1));
    verify(cs).getString(eq(1));
  }

  /**
   * Method under test: {@link ByteArrayRefTypeHandler#getResult(ResultSet, int)}
   */
  @Test
  public void testGetResult3() throws SQLException {
    // Arrange
    ByteArrayRefTypeHandler byteArrayRefTypeHandler = new ByteArrayRefTypeHandler();
    ResultSet rs = mock(ResultSet.class);
    when(rs.getString(anyInt())).thenReturn("String");

    // Act
    ByteArrayRef actualResult = byteArrayRefTypeHandler.getResult(rs, 1);

    // Assert
    verify(rs).getString(eq(1));
    assertEquals("String", actualResult.getId());
    assertNull(actualResult.getName());
    assertFalse(actualResult.isDeleted());
  }

  /**
   * Method under test: {@link ByteArrayRefTypeHandler#getResult(ResultSet, int)}
   */
  @Test
  public void testGetResult4() throws SQLException {
    // Arrange
    ByteArrayRefTypeHandler byteArrayRefTypeHandler = new ByteArrayRefTypeHandler();
    ResultSet rs = mock(ResultSet.class);
    when(rs.getString(anyInt())).thenThrow(new SQLException());

    // Act and Assert
    assertThrows(SQLException.class, () -> byteArrayRefTypeHandler.getResult(rs, 1));
    verify(rs).getString(eq(1));
  }

  /**
   * Method under test:
   * {@link ByteArrayRefTypeHandler#getResult(ResultSet, String)}
   */
  @Test
  public void testGetResult5() throws SQLException {
    // Arrange
    ByteArrayRefTypeHandler byteArrayRefTypeHandler2 = new ByteArrayRefTypeHandler();
    ResultSet rs = mock(ResultSet.class);
    when(rs.getString(Mockito.<String>any())).thenReturn("String");

    // Act
    ByteArrayRef actualResult = byteArrayRefTypeHandler2.getResult(rs, "Column Name");

    // Assert
    verify(rs).getString(eq("Column Name"));
    assertEquals("String", actualResult.getId());
    assertNull(actualResult.getName());
    assertFalse(actualResult.isDeleted());
  }

  /**
   * Method under test:
   * {@link ByteArrayRefTypeHandler#getResult(ResultSet, String)}
   */
  @Test
  public void testGetResult6() throws SQLException {
    // Arrange
    ByteArrayRefTypeHandler byteArrayRefTypeHandler2 = new ByteArrayRefTypeHandler();
    ResultSet rs = mock(ResultSet.class);
    when(rs.getString(Mockito.<String>any())).thenThrow(new SQLException());

    // Act and Assert
    assertThrows(SQLException.class, () -> byteArrayRefTypeHandler2.getResult(rs, "Column Name"));
    verify(rs).getString(eq("Column Name"));
  }
}
