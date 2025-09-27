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
package org.activiti.engine.impl.util.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class CDLDiffblueTest {
  /**
   * Test {@link CDL#rowToJSONArray(JSONTokener)}.
   *
   * <p>Method under test: {@link CDL#rowToJSONArray(JSONTokener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray CDL.rowToJSONArray(JSONTokener)"})
  public void testRowToJSONArray() throws JSONException {
    // Arrange
    JSONTokener x = new JSONTokener(",:]}/\\\"[{;=#");

    // Act and Assert
    assertEquals(2, CDL.rowToJSONArray(x).length());
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#rowToJSONArray(JSONTokener)}.
   *
   * <ul>
   *   <li>When {@link JSONTokener#JSONTokener(String)} with s is empty string.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CDL#rowToJSONArray(JSONTokener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray CDL.rowToJSONArray(JSONTokener)"})
  public void testRowToJSONArray_whenJSONTokenerWithSIsEmptyString_thenReturnNull()
      throws JSONException {
    // Arrange
    JSONTokener x = new JSONTokener("");

    // Act and Assert
    assertNull(CDL.rowToJSONArray(x));
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#rowToJSONArray(JSONTokener)}.
   *
   * <ul>
   *   <li>When {@link JSONTokener#JSONTokener(String)} with s is {@code foo,:]}/\"[{;=#}.
   *   <li>Then {@link JSONTokener#JSONTokener(String)} with s is {@code foo,:]}/\"[{;=#} end.
   * </ul>
   *
   * <p>Method under test: {@link CDL#rowToJSONArray(JSONTokener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray CDL.rowToJSONArray(JSONTokener)"})
  public void testRowToJSONArray_whenJSONTokenerWithSIsFoo_thenJSONTokenerWithSIsFooEnd()
      throws JSONException {
    // Arrange
    JSONTokener x = new JSONTokener("foo,:]}/\\\"[{;=#");

    // Act and Assert
    assertEquals(2, CDL.rowToJSONArray(x).length());
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#rowToJSONArray(JSONTokener)}.
   *
   * <ul>
   *   <li>When {@link JSONTokener#JSONTokener(String)} with s is {@code foo}.
   *   <li>Then return length is one.
   * </ul>
   *
   * <p>Method under test: {@link CDL#rowToJSONArray(JSONTokener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray CDL.rowToJSONArray(JSONTokener)"})
  public void testRowToJSONArray_whenJSONTokenerWithSIsFoo_thenReturnLengthIsOne()
      throws JSONException {
    // Arrange
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertEquals(1, CDL.rowToJSONArray(x).length());
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#rowToJSONObject(JSONArray, JSONTokener)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link CDL#rowToJSONObject(JSONArray, JSONTokener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject CDL.rowToJSONObject(JSONArray, JSONTokener)"})
  public void testRowToJSONObject_givenArrayList() throws JSONException {
    // Arrange
    JSONArray names = new JSONArray("[]");
    names.put((Collection) new ArrayList<>());
    names.put(true);
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertEquals(1, CDL.rowToJSONObject(names, x).length());
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#rowToJSONObject(JSONArray, JSONTokener)}.
   *
   * <ul>
   *   <li>Given {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link CDL#rowToJSONObject(JSONArray, JSONTokener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject CDL.rowToJSONObject(JSONArray, JSONTokener)"})
  public void testRowToJSONObject_givenFalse() throws JSONException {
    // Arrange
    JSONArray names = new JSONArray("[]");
    names.put(false);
    names.put(true);
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertEquals(1, CDL.rowToJSONObject(names, x).length());
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#rowToJSONObject(JSONArray, JSONTokener)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link CDL#rowToJSONObject(JSONArray, JSONTokener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject CDL.rowToJSONObject(JSONArray, JSONTokener)"})
  public void testRowToJSONObject_givenHashMap() throws JSONException {
    // Arrange
    JSONArray names = new JSONArray("[]");
    names.put((Map) new HashMap<>());
    names.put(true);
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertEquals(1, CDL.rowToJSONObject(names, x).length());
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#rowToJSONObject(JSONArray, JSONTokener)}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>Then return length is one.
   * </ul>
   *
   * <p>Method under test: {@link CDL#rowToJSONObject(JSONArray, JSONTokener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject CDL.rowToJSONObject(JSONArray, JSONTokener)"})
  public void testRowToJSONObject_givenTrue_thenReturnLengthIsOne() throws JSONException {
    // Arrange
    JSONArray names = new JSONArray("[]");
    names.put(true);
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertEquals(1, CDL.rowToJSONObject(names, x).length());
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#rowToJSONObject(JSONArray, JSONTokener)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CDL#rowToJSONObject(JSONArray, JSONTokener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject CDL.rowToJSONObject(JSONArray, JSONTokener)"})
  public void testRowToJSONObject_thenReturnNull() throws JSONException {
    // Arrange
    JSONArray names = new JSONArray("[]");
    JSONTokener x = new JSONTokener("foo");

    // Act
    JSONObject actualRowToJSONObjectResult = CDL.rowToJSONObject(names, x);

    // Assert
    assertNull(actualRowToJSONObjectResult);
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#rowToJSONObject(JSONArray, JSONTokener)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CDL#rowToJSONObject(JSONArray, JSONTokener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject CDL.rowToJSONObject(JSONArray, JSONTokener)"})
  public void testRowToJSONObject_whenNull_thenReturnNull() throws JSONException {
    // Arrange
    JSONTokener x = new JSONTokener("foo");

    // Act
    JSONObject actualRowToJSONObjectResult = CDL.rowToJSONObject(null, x);

    // Assert
    assertNull(actualRowToJSONObjectResult);
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#toJSONArray(JSONArray, String)} with {@code names}, {@code string}.
   *
   * <p>Method under test: {@link CDL#toJSONArray(JSONArray, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(JSONArray, String)"})
  public void testToJSONArrayWithNamesString() throws JSONException {
    // Arrange, Act and Assert
    assertNull(CDL.toJSONArray(new JSONArray("[]"), "String"));
  }

  /**
   * Test {@link CDL#toJSONArray(JSONArray, String)} with {@code names}, {@code string}.
   *
   * <p>Method under test: {@link CDL#toJSONArray(JSONArray, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(JSONArray, String)"})
  public void testToJSONArrayWithNamesString2() throws JSONException {
    // Arrange
    JSONArray names = mock(JSONArray.class);
    when(names.length()).thenThrow(new JSONException("An error occurred"));

    // Act and Assert
    assertThrows(JSONException.class, () -> CDL.toJSONArray(names, "String"));
    verify(names).length();
  }

  /**
   * Test {@link CDL#toJSONArray(JSONArray, String)} with {@code names}, {@code string}.
   *
   * <p>Method under test: {@link CDL#toJSONArray(JSONArray, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(JSONArray, String)"})
  public void testToJSONArrayWithNamesString3() throws JSONException {
    // Arrange
    JSONArray names = mock(JSONArray.class);
    when(names.getString(anyInt())).thenThrow(new JSONException("An error occurred"));
    when(names.length()).thenReturn(1);

    // Act and Assert
    assertThrows(JSONException.class, () -> CDL.toJSONArray(names, "String"));
    verify(names).getString(0);
    verify(names, atLeast(1)).length();
  }

  /**
   * Test {@link CDL#toJSONArray(JSONArray, String)} with {@code names}, {@code string}.
   *
   * <ul>
   *   <li>Given one.
   *   <li>When empty string.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CDL#toJSONArray(JSONArray, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(JSONArray, String)"})
  public void testToJSONArrayWithNamesString_givenOne_whenEmptyString_thenReturnNull()
      throws JSONException {
    // Arrange
    JSONArray names = mock(JSONArray.class);
    when(names.length()).thenReturn(1);

    // Act
    JSONArray actualToJSONArrayResult = CDL.toJSONArray(names, "");

    // Assert
    verify(names).length();
    assertNull(actualToJSONArrayResult);
  }

  /**
   * Test {@link CDL#toJSONArray(JSONArray, String)} with {@code names}, {@code string}.
   *
   * <ul>
   *   <li>Given {@code String}.
   *   <li>Then return length is one.
   * </ul>
   *
   * <p>Method under test: {@link CDL#toJSONArray(JSONArray, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(JSONArray, String)"})
  public void testToJSONArrayWithNamesString_givenString_thenReturnLengthIsOne()
      throws JSONException {
    // Arrange
    JSONArray names = mock(JSONArray.class);
    when(names.getString(anyInt())).thenReturn("String");
    when(names.length()).thenReturn(1);

    // Act
    JSONArray actualToJSONArrayResult = CDL.toJSONArray(names, "String");

    // Assert
    verify(names).getString(0);
    verify(names, atLeast(1)).length();
    assertEquals(1, actualToJSONArrayResult.length());
  }

  /**
   * Test {@link CDL#toJSONArray(JSONArray, String)} with {@code names}, {@code string}.
   *
   * <ul>
   *   <li>Given two.
   *   <li>When {@link JSONArray} {@link JSONArray#length()} return two.
   * </ul>
   *
   * <p>Method under test: {@link CDL#toJSONArray(JSONArray, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(JSONArray, String)"})
  public void testToJSONArrayWithNamesString_givenTwo_whenJSONArrayLengthReturnTwo()
      throws JSONException {
    // Arrange
    JSONArray names = mock(JSONArray.class);
    when(names.getString(anyInt())).thenReturn("String");
    when(names.length()).thenReturn(2);

    // Act
    JSONArray actualToJSONArrayResult = CDL.toJSONArray(names, "String");

    // Assert
    verify(names, atLeast(1)).getString(anyInt());
    verify(names, atLeast(1)).length();
    assertEquals(1, actualToJSONArrayResult.length());
  }

  /**
   * Test {@link CDL#toJSONArray(JSONArray, String)} with {@code names}, {@code string}.
   *
   * <ul>
   *   <li>Given zero.
   *   <li>When {@link JSONArray} {@link JSONArray#length()} return zero.
   * </ul>
   *
   * <p>Method under test: {@link CDL#toJSONArray(JSONArray, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(JSONArray, String)"})
  public void testToJSONArrayWithNamesString_givenZero_whenJSONArrayLengthReturnZero()
      throws JSONException {
    // Arrange
    JSONArray names = mock(JSONArray.class);
    when(names.length()).thenReturn(0);

    // Act
    JSONArray actualToJSONArrayResult = CDL.toJSONArray(names, "String");

    // Assert
    verify(names).length();
    assertNull(actualToJSONArrayResult);
  }

  /**
   * Test {@link CDL#toJSONArray(JSONArray, String)} with {@code names}, {@code string}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CDL#toJSONArray(JSONArray, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(JSONArray, String)"})
  public void testToJSONArrayWithNamesString_whenNull_thenReturnNull() throws JSONException {
    // Arrange, Act and Assert
    assertNull(CDL.toJSONArray(null, "String"));
  }

  /**
   * Test {@link CDL#toJSONArray(JSONArray, JSONTokener)} with {@code names}, {@code x}.
   *
   * <p>Method under test: {@link CDL#toJSONArray(JSONArray, JSONTokener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(JSONArray, JSONTokener)"})
  public void testToJSONArrayWithNamesX() throws JSONException {
    // Arrange
    JSONArray names = new JSONArray("[]");
    JSONTokener x = new JSONTokener("foo");

    // Act
    JSONArray actualToJSONArrayResult = CDL.toJSONArray(names, x);

    // Assert
    assertNull(actualToJSONArrayResult);
    assertFalse(x.end());
  }

  /**
   * Test {@link CDL#toJSONArray(JSONArray, JSONTokener)} with {@code names}, {@code x}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link CDL#toJSONArray(JSONArray, JSONTokener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(JSONArray, JSONTokener)"})
  public void testToJSONArrayWithNamesX_givenArrayList() throws JSONException {
    // Arrange
    JSONArray names = new JSONArray("[]");
    names.put((Collection) new ArrayList<>());
    names.put(true);
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertEquals(1, CDL.toJSONArray(names, x).length());
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#toJSONArray(JSONArray, JSONTokener)} with {@code names}, {@code x}.
   *
   * <ul>
   *   <li>Given {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link CDL#toJSONArray(JSONArray, JSONTokener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(JSONArray, JSONTokener)"})
  public void testToJSONArrayWithNamesX_givenFalse() throws JSONException {
    // Arrange
    JSONArray names = new JSONArray("[]");
    names.put(false);
    names.put(true);
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertEquals(1, CDL.toJSONArray(names, x).length());
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#toJSONArray(JSONArray, JSONTokener)} with {@code names}, {@code x}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link CDL#toJSONArray(JSONArray, JSONTokener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(JSONArray, JSONTokener)"})
  public void testToJSONArrayWithNamesX_givenHashMap() throws JSONException {
    // Arrange
    JSONArray names = new JSONArray("[]");
    names.put((Map) new HashMap<>());
    names.put(true);
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertEquals(1, CDL.toJSONArray(names, x).length());
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#toJSONArray(JSONArray, JSONTokener)} with {@code names}, {@code x}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>Then return length is one.
   * </ul>
   *
   * <p>Method under test: {@link CDL#toJSONArray(JSONArray, JSONTokener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(JSONArray, JSONTokener)"})
  public void testToJSONArrayWithNamesX_givenTrue_thenReturnLengthIsOne() throws JSONException {
    // Arrange
    JSONArray names = new JSONArray("[]");
    names.put(true);
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertEquals(1, CDL.toJSONArray(names, x).length());
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#toJSONArray(JSONArray, JSONTokener)} with {@code names}, {@code x}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CDL#toJSONArray(JSONArray, JSONTokener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(JSONArray, JSONTokener)"})
  public void testToJSONArrayWithNamesX_whenNull_thenReturnNull() throws JSONException {
    // Arrange
    JSONTokener x = new JSONTokener("foo");

    // Act
    JSONArray actualToJSONArrayResult = CDL.toJSONArray(null, x);

    // Assert
    assertNull(actualToJSONArrayResult);
    assertFalse(x.end());
  }

  /**
   * Test {@link CDL#toJSONArray(String)} with {@code string}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CDL#toJSONArray(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(String)"})
  public void testToJSONArrayWithString_whenEmptyString_thenReturnNull() throws JSONException {
    // Arrange, Act and Assert
    assertNull(CDL.toJSONArray(""));
  }

  /**
   * Test {@link CDL#toJSONArray(String)} with {@code string}.
   *
   * <ul>
   *   <li>When {@code String}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CDL#toJSONArray(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(String)"})
  public void testToJSONArrayWithString_whenString_thenReturnNull() throws JSONException {
    // Arrange, Act and Assert
    assertNull(CDL.toJSONArray("String"));
  }

  /**
   * Test {@link CDL#toJSONArray(JSONTokener)} with {@code x}.
   *
   * <p>Method under test: {@link CDL#toJSONArray(JSONTokener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(JSONTokener)"})
  public void testToJSONArrayWithX() throws JSONException {
    // Arrange
    JSONTokener x = new JSONTokener(",:]}/\\\"[{;=#");

    // Act and Assert
    assertNull(CDL.toJSONArray(x));
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#toJSONArray(JSONTokener)} with {@code x}.
   *
   * <ul>
   *   <li>Then {@link JSONTokener#JSONTokener(String)} with s is empty string end.
   * </ul>
   *
   * <p>Method under test: {@link CDL#toJSONArray(JSONTokener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(JSONTokener)"})
  public void testToJSONArrayWithX_thenJSONTokenerWithSIsEmptyStringEnd() throws JSONException {
    // Arrange
    JSONTokener x = new JSONTokener("");

    // Act and Assert
    assertNull(CDL.toJSONArray(x));
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#toJSONArray(JSONTokener)} with {@code x}.
   *
   * <ul>
   *   <li>When {@link JSONTokener#JSONTokener(String)} with s is {@code foo}.
   *   <li>Then {@link JSONTokener#JSONTokener(String)} with s is {@code foo} end.
   * </ul>
   *
   * <p>Method under test: {@link CDL#toJSONArray(JSONTokener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(JSONTokener)"})
  public void testToJSONArrayWithX_whenJSONTokenerWithSIsFoo_thenJSONTokenerWithSIsFooEnd()
      throws JSONException {
    // Arrange
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertNull(CDL.toJSONArray(x));
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#toJSONArray(JSONTokener)} with {@code x}.
   *
   * <ul>
   *   <li>When {@link JSONTokener#JSONTokener(String)} with s is {@code foo,:]}/\"[{;=#}.
   *   <li>Then {@link JSONTokener#JSONTokener(String)} with s is {@code foo,:]}/\"[{;=#} end.
   * </ul>
   *
   * <p>Method under test: {@link CDL#toJSONArray(JSONTokener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(JSONTokener)"})
  public void testToJSONArrayWithX_whenJSONTokenerWithSIsFoo_thenJSONTokenerWithSIsFooEnd2()
      throws JSONException {
    // Arrange
    JSONTokener x = new JSONTokener("foo,:]}/\\\"[{;=#");

    // Act and Assert
    assertNull(CDL.toJSONArray(x));
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#rowToString(JSONArray)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.
   *   <li>Then return {@code [],true}.
   * </ul>
   *
   * <p>Method under test: {@link CDL#rowToString(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String CDL.rowToString(JSONArray)"})
  public void testRowToString_givenArrayList_thenReturnTrue() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put((Collection) new ArrayList<>());
    ja.put(true);

    // Act and Assert
    assertEquals("[],true\n", CDL.rowToString(ja));
  }

  /**
   * Test {@link CDL#rowToString(JSONArray)}.
   *
   * <ul>
   *   <li>Given {@code false}.
   *   <li>Then return {@code false,true}.
   * </ul>
   *
   * <p>Method under test: {@link CDL#rowToString(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String CDL.rowToString(JSONArray)"})
  public void testRowToString_givenFalse_thenReturnFalseTrue() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put(false);
    ja.put(true);

    // Act and Assert
    assertEquals("false,true\n", CDL.rowToString(ja));
  }

  /**
   * Test {@link CDL#rowToString(JSONArray)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.
   *   <li>Then return {@code {},true}.
   * </ul>
   *
   * <p>Method under test: {@link CDL#rowToString(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String CDL.rowToString(JSONArray)"})
  public void testRowToString_givenHashMap_thenReturnTrue() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put((Map) new HashMap<>());
    ja.put(true);

    // Act and Assert
    assertEquals("{},true\n", CDL.rowToString(ja));
  }

  /**
   * Test {@link CDL#rowToString(JSONArray)}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link CDL#rowToString(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String CDL.rowToString(JSONArray)"})
  public void testRowToString_givenTrue_thenReturnTrue() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put(true);

    // Act and Assert
    assertEquals("true\n", CDL.rowToString(ja));
  }

  /**
   * Test {@link CDL#rowToString(JSONArray)}.
   *
   * <ul>
   *   <li>Then return lf.
   * </ul>
   *
   * <p>Method under test: {@link CDL#rowToString(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String CDL.rowToString(JSONArray)"})
  public void testRowToString_thenReturnLf() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("\n", CDL.rowToString(new JSONArray("[]")));
  }

  /**
   * Test {@link CDL#toString(JSONArray)} with {@code ja}.
   *
   * <p>Method under test: {@link CDL#toString(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String CDL.toString(JSONArray)"})
  public void testToStringWithJa() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.put(HTTP.toJSONObject("https://example.org/example"), JSONObject.NULL);

    JSONArray ja = new JSONArray("[]");
    ja.put((Map) value);
    ja.put(true);

    // Act and Assert
    assertEquals(
        "\"{HTTP-Version:https://example.org/example,Status-Code:,Reason-Phrase:}\"\n\n",
        CDL.toString(ja));
  }

  /**
   * Test {@link CDL#toString(JSONArray)} with {@code ja}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@link JSONObject#NULL}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CDL#toString(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String CDL.toString(JSONArray)"})
  public void testToStringWithJa_givenHashMapNullIsNull_thenReturnNull() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, JSONObject.NULL);

    JSONArray ja = new JSONArray("[]");
    ja.put((Map) value);
    ja.put(true);

    // Act and Assert
    assertEquals("null\n\n", CDL.toString(ja));
  }

  /**
   * Test {@link CDL#toString(JSONArray)} with {@code ja}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CDL#toString(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String CDL.toString(JSONArray)"})
  public void testToStringWithJa_givenHashMap_thenReturnNull() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put((Map) new HashMap<>());
    ja.put(true);

    // Act and Assert
    assertNull(CDL.toString(ja));
  }

  /**
   * Test {@link CDL#toString(JSONArray)} with {@code ja}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} append {@code {} and {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code {"{":[null]}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toString(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String CDL.toString(JSONArray)"})
  public void testToStringWithJa_givenJSONObjectAppendLeftCurlyBracketAndNull_thenReturnNull()
      throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("{", JSONObject.NULL);

    HashMap<Object, Object> value = new HashMap<>();
    value.put(jsonObject, JSONObject.NULL);

    JSONArray ja = new JSONArray("[]");
    ja.put((Map) value);
    ja.put(true);

    // Act and Assert
    assertEquals("{\"{\":[null]}\n\n", CDL.toString(ja));
  }

  /**
   * Test {@link CDL#toString(JSONArray)} with {@code ja}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} {@code ,} is {@code 0.5}.</li>
   *   <li>Then return {@code "{{:[null],,:0.5}"}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toString(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String CDL.toString(JSONArray)"})
  public void testToStringWithJa_givenJSONObjectCommaIs05_thenReturnNull05() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.put(",", 0.5d);
    jsonObject.append("{", JSONObject.NULL);

    HashMap<Object, Object> value = new HashMap<>();
    value.put(jsonObject, JSONObject.NULL);

    JSONArray ja = new JSONArray("[]");
    ja.put((Map) value);
    ja.put(true);

    // Act and Assert
    assertEquals("\"{{:[null],,:0.5}\"\n\n", CDL.toString(ja));
  }

  /**
   * Test {@link CDL#toString(JSONArray)} with {@code ja}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} {@code ,} is {@code false}.</li>
   *   <li>Then return {@code "{{:[null],,:false}"}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toString(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String CDL.toString(JSONArray)"})
  public void testToStringWithJa_givenJSONObjectCommaIsFalse_thenReturnNullFalse()
      throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.put(",", false);
    jsonObject.append("{", JSONObject.NULL);

    HashMap<Object, Object> value = new HashMap<>();
    value.put(jsonObject, JSONObject.NULL);

    JSONArray ja = new JSONArray("[]");
    ja.put((Map) value);
    ja.put(true);

    // Act and Assert
    assertEquals("\"{{:[null],,:false}\"\n\n", CDL.toString(ja));
  }

  /**
   * Test {@link CDL#toString(JSONArray)} with {@code ja}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} {@code ,} is {@code false}.</li>
   *   <li>Then return {@code "{{:[null,null],,:false}"}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toString(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String CDL.toString(JSONArray)"})
  public void testToStringWithJa_givenJSONObjectCommaIsFalse_thenReturnNullNullFalse()
      throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("{", JSONObject.NULL);
    jsonObject.put(",", false);
    jsonObject.append("{", JSONObject.NULL);

    HashMap<Object, Object> value = new HashMap<>();
    value.put(jsonObject, JSONObject.NULL);

    JSONArray ja = new JSONArray("[]");
    ja.put((Map) value);
    ja.put(true);

    // Act and Assert
    assertEquals("\"{{:[null,null],,:false}\"\n\n", CDL.toString(ja));
  }

  /**
   * Test {@link CDL#toString(JSONArray)} with {@code ja}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} {@code ,} is {@link HashMap#HashMap()}.</li>
   *   <li>Then return {@code "{{:[null],,:{}}"}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toString(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String CDL.toString(JSONArray)"})
  public void testToStringWithJa_givenJSONObjectCommaIsHashMap_thenReturnNull()
      throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.put(",", (Map) new HashMap<>());
    jsonObject.append("{", JSONObject.NULL);

    HashMap<Object, Object> value = new HashMap<>();
    value.put(jsonObject, JSONObject.NULL);

    JSONArray ja = new JSONArray("[]");
    ja.put((Map) value);
    ja.put(true);

    // Act and Assert
    assertEquals("\"{{:[null],,:{}}\"\n\n", CDL.toString(ja));
  }

  /**
   * Test {@link CDL#toString(JSONArray)} with {@code ja}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} increment {@code ,}.</li>
   *   <li>Then return {@code "{{:[null],,:1}"}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toString(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String CDL.toString(JSONArray)"})
  public void testToStringWithJa_givenJSONObjectIncrementComma_thenReturnNull1()
      throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.increment(",");
    jsonObject.append("{", JSONObject.NULL);

    HashMap<Object, Object> value = new HashMap<>();
    value.put(jsonObject, JSONObject.NULL);

    JSONArray ja = new JSONArray("[]");
    ja.put((Map) value);
    ja.put(true);

    // Act and Assert
    assertEquals("\"{{:[null],,:1}\"\n\n", CDL.toString(ja));
  }

  /**
   * Test {@link CDL#toString(JSONArray)} with {@code ja}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} {@code {} is ten.</li>
   *   <li>Then return {@code {"{":10}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toString(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String CDL.toString(JSONArray)"})
  public void testToStringWithJa_givenJSONObjectLeftCurlyBracketIsTen_thenReturn10()
      throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("{", 10.0d);

    HashMap<Object, Object> value = new HashMap<>();
    value.put(jsonObject, JSONObject.NULL);

    JSONArray ja = new JSONArray("[]");
    ja.put((Map) value);
    ja.put(true);

    // Act and Assert
    assertEquals("{\"{\":10}\n\n", CDL.toString(ja));
  }

  /**
   * Test {@link CDL#toString(JSONArray)} with {@code ja}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CDL#toString(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String CDL.toString(JSONArray)"})
  public void testToStringWithJa_givenTrue_thenReturnNull() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put(true);

    // Act and Assert
    assertNull(CDL.toString(ja));
  }

  /**
   * Test {@link CDL#toString(JSONArray)} with {@code ja}.
   *
   * <ul>
   *   <li>Then return {@code {}}.
   * </ul>
   *
   * <p>Method under test: {@link CDL#toString(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String CDL.toString(JSONArray)"})
  public void testToStringWithJa_thenReturnLeftCurlyBracketRightCurlyBracket()
      throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.put(new JSONObject(), JSONObject.NULL);

    JSONArray ja = new JSONArray("[]");
    ja.put((Map) value);
    ja.put(true);

    // Act and Assert
    assertEquals("{}\n\n", CDL.toString(ja));
  }

  /**
   * Test {@link CDL#toString(JSONArray)} with {@code ja}.
   *
   * <ul>
   *   <li>Then return {@code []}.
   * </ul>
   *
   * <p>Method under test: {@link CDL#toString(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String CDL.toString(JSONArray)"})
  public void testToStringWithJa_thenReturnLeftSquareBracketRightSquareBracket()
      throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.put(new JSONArray(), JSONObject.NULL);

    JSONArray ja = new JSONArray("[]");
    ja.put((Map) value);
    ja.put(true);

    // Act and Assert
    assertEquals("[]\n\n", CDL.toString(ja));
  }

  /**
   * Test {@link CDL#toString(JSONArray)} with {@code ja}.
   *
   * <ul>
   *   <li>When {@link JSONArray#JSONArray(String)} with source is {@code []}.
   * </ul>
   *
   * <p>Method under test: {@link CDL#toString(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String CDL.toString(JSONArray)"})
  public void testToStringWithJa_whenJSONArrayWithSourceIsLeftSquareBracketRightSquareBracket()
      throws JSONException {
    // Arrange, Act and Assert
    assertNull(CDL.toString(new JSONArray("[]")));
  }
}
