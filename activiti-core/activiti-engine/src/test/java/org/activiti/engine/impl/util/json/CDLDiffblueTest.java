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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
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
   * <p>
   * Method under test: {@link CDL#rowToJSONArray(JSONTokener)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * <ul>
   *   <li>When {@link JSONTokener#JSONTokener(String)} with s is empty string.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#rowToJSONArray(JSONTokener)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONArray CDL.rowToJSONArray(JSONTokener)"})
  public void testRowToJSONArray_whenJSONTokenerWithSIsEmptyString_thenReturnNull() throws JSONException {
    // Arrange
    JSONTokener x = new JSONTokener("");

    // Act and Assert
    assertNull(CDL.rowToJSONArray(x));
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#rowToJSONArray(JSONTokener)}.
   * <ul>
   *   <li>When {@link JSONTokener#JSONTokener(String)} with s is {@code foo,:]}/\"[{;=#}.</li>
   *   <li>Then {@link JSONTokener#JSONTokener(String)} with s is {@code foo,:]}/\"[{;=#} end.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#rowToJSONArray(JSONTokener)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONArray CDL.rowToJSONArray(JSONTokener)"})
  public void testRowToJSONArray_whenJSONTokenerWithSIsFoo_thenJSONTokenerWithSIsFooEnd() throws JSONException {
    // Arrange
    JSONTokener x = new JSONTokener("foo,:]}/\\\"[{;=#");

    // Act and Assert
    assertEquals(2, CDL.rowToJSONArray(x).length());
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#rowToJSONArray(JSONTokener)}.
   * <ul>
   *   <li>When {@link JSONTokener#JSONTokener(String)} with s is {@code foo}.</li>
   *   <li>Then return length is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#rowToJSONArray(JSONTokener)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONArray CDL.rowToJSONArray(JSONTokener)"})
  public void testRowToJSONArray_whenJSONTokenerWithSIsFoo_thenReturnLengthIsOne() throws JSONException {
    // Arrange
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertEquals(1, CDL.rowToJSONArray(x).length());
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#rowToJSONObject(JSONArray, JSONTokener)}.
   * <p>
   * Method under test: {@link CDL#rowToJSONObject(JSONArray, JSONTokener)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONObject CDL.rowToJSONObject(JSONArray, JSONTokener)"})
  public void testRowToJSONObject() throws JSONException {
    // Arrange
    JSONArray names = new JSONArray("[]");
    names.put(false);
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertEquals(1, CDL.rowToJSONObject(names, x).length());
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#rowToJSONObject(JSONArray, JSONTokener)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#rowToJSONObject(JSONArray, JSONTokener)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONObject CDL.rowToJSONObject(JSONArray, JSONTokener)"})
  public void testRowToJSONObject_givenArrayList() throws JSONException {
    // Arrange
    JSONArray names = new JSONArray("[]");
    names.put((Collection) new ArrayList<>());
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertEquals(1, CDL.rowToJSONObject(names, x).length());
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#rowToJSONObject(JSONArray, JSONTokener)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#rowToJSONObject(JSONArray, JSONTokener)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONObject CDL.rowToJSONObject(JSONArray, JSONTokener)"})
  public void testRowToJSONObject_givenHashMap() throws JSONException {
    // Arrange
    JSONArray names = new JSONArray("[]");
    names.put((Map) new HashMap<>());
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertEquals(1, CDL.rowToJSONObject(names, x).length());
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#rowToJSONObject(JSONArray, JSONTokener)}.
   * <ul>
   *   <li>Given one hundred two.</li>
   *   <li>Then return length is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#rowToJSONObject(JSONArray, JSONTokener)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONObject CDL.rowToJSONObject(JSONArray, JSONTokener)"})
  public void testRowToJSONObject_givenOneHundredTwo_thenReturnLengthIsZero() throws JSONException {
    // Arrange
    JSONArray names = new JSONArray("[]");
    names.put(102, false);
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertEquals(0, CDL.rowToJSONObject(names, x).length());
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#rowToJSONObject(JSONArray, JSONTokener)}.
   * <ul>
   *   <li>When {@link JSONArray#JSONArray(String)} with source is {@code []}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#rowToJSONObject(JSONArray, JSONTokener)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONObject CDL.rowToJSONObject(JSONArray, JSONTokener)"})
  public void testRowToJSONObject_whenJSONArrayWithSourceIsLeftSquareBracketRightSquareBracket() throws JSONException {
    // Arrange
    JSONArray names = new JSONArray("[]");
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertNull(CDL.rowToJSONObject(names, x));
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#rowToJSONObject(JSONArray, JSONTokener)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#rowToJSONObject(JSONArray, JSONTokener)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONObject CDL.rowToJSONObject(JSONArray, JSONTokener)"})
  public void testRowToJSONObject_whenNull_thenReturnNull() throws JSONException {
    // Arrange
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertNull(CDL.rowToJSONObject(null, x));
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#toJSONArray(JSONArray, String)} with {@code names}, {@code string}.
   * <p>
   * Method under test: {@link CDL#toJSONArray(JSONArray, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(JSONArray, String)"})
  public void testToJSONArrayWithNamesString() throws JSONException {
    // Arrange, Act and Assert
    assertNull(CDL.toJSONArray(new JSONArray("[]"), "String"));
  }

  /**
   * Test {@link CDL#toJSONArray(JSONArray, String)} with {@code names}, {@code string}.
   * <ul>
   *   <li>Given {@code String}.</li>
   *   <li>Then return length is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toJSONArray(JSONArray, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(JSONArray, String)"})
  public void testToJSONArrayWithNamesString_givenString_thenReturnLengthIsOne() throws JSONException {
    // Arrange
    JSONArray names = mock(JSONArray.class);
    when(names.getString(anyInt())).thenReturn("String");
    when(names.length()).thenReturn(3);

    // Act
    JSONArray actualToJSONArrayResult = CDL.toJSONArray(names, "String");

    // Assert
    verify(names, atLeast(1)).getString(anyInt());
    verify(names, atLeast(1)).length();
    assertEquals(1, actualToJSONArrayResult.length());
  }

  /**
   * Test {@link CDL#toJSONArray(JSONArray, String)} with {@code names}, {@code string}.
   * <ul>
   *   <li>Given three.</li>
   *   <li>When empty string.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toJSONArray(JSONArray, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(JSONArray, String)"})
  public void testToJSONArrayWithNamesString_givenThree_whenEmptyString_thenReturnNull() throws JSONException {
    // Arrange
    JSONArray names = mock(JSONArray.class);
    when(names.length()).thenReturn(3);

    // Act
    JSONArray actualToJSONArrayResult = CDL.toJSONArray(names, "");

    // Assert
    verify(names).length();
    assertNull(actualToJSONArrayResult);
  }

  /**
   * Test {@link CDL#toJSONArray(JSONArray, String)} with {@code names}, {@code string}.
   * <ul>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toJSONArray(JSONArray, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(JSONArray, String)"})
  public void testToJSONArrayWithNamesString_thenThrowJSONException() throws JSONException {
    // Arrange
    JSONArray names = mock(JSONArray.class);
    when(names.getString(anyInt())).thenThrow(new JSONException("An error occurred"));
    when(names.length()).thenReturn(3);

    // Act and Assert
    assertThrows(JSONException.class, () -> CDL.toJSONArray(names, "String"));
    verify(names).getString(eq(0));
    verify(names, atLeast(1)).length();
  }

  /**
   * Test {@link CDL#toJSONArray(JSONArray, String)} with {@code names}, {@code string}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toJSONArray(JSONArray, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(JSONArray, String)"})
  public void testToJSONArrayWithNamesString_whenNull_thenReturnNull() throws JSONException {
    // Arrange, Act and Assert
    assertNull(CDL.toJSONArray(null, "String"));
  }

  /**
   * Test {@link CDL#toJSONArray(JSONArray, JSONTokener)} with {@code names}, {@code x}.
   * <p>
   * Method under test: {@link CDL#toJSONArray(JSONArray, JSONTokener)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(JSONArray, JSONTokener)"})
  public void testToJSONArrayWithNamesX() throws JSONException {
    // Arrange
    JSONArray names = new JSONArray("[]");
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertNull(CDL.toJSONArray(names, x));
    assertFalse(x.end());
  }

  /**
   * Test {@link CDL#toJSONArray(JSONArray, JSONTokener)} with {@code names}, {@code x}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toJSONArray(JSONArray, JSONTokener)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(JSONArray, JSONTokener)"})
  public void testToJSONArrayWithNamesX_givenArrayList() throws JSONException {
    // Arrange
    JSONArray names = new JSONArray("[]");
    names.put((Collection) new ArrayList<>());
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertEquals(1, CDL.toJSONArray(names, x).length());
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#toJSONArray(JSONArray, JSONTokener)} with {@code names}, {@code x}.
   * <ul>
   *   <li>Given {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toJSONArray(JSONArray, JSONTokener)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(JSONArray, JSONTokener)"})
  public void testToJSONArrayWithNamesX_givenFalse() throws JSONException {
    // Arrange
    JSONArray names = new JSONArray("[]");
    names.put(false);
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertEquals(1, CDL.toJSONArray(names, x).length());
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#toJSONArray(JSONArray, JSONTokener)} with {@code names}, {@code x}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toJSONArray(JSONArray, JSONTokener)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(JSONArray, JSONTokener)"})
  public void testToJSONArrayWithNamesX_givenHashMap() throws JSONException {
    // Arrange
    JSONArray names = new JSONArray("[]");
    names.put((Map) new HashMap<>());
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertEquals(1, CDL.toJSONArray(names, x).length());
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#toJSONArray(JSONArray, JSONTokener)} with {@code names}, {@code x}.
   * <ul>
   *   <li>Given {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toJSONArray(JSONArray, JSONTokener)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(JSONArray, JSONTokener)"})
  public void testToJSONArrayWithNamesX_givenTrue() throws JSONException {
    // Arrange
    JSONArray names = new JSONArray("[]");
    names.put(true);
    names.put(false);
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertEquals(1, CDL.toJSONArray(names, x).length());
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#toJSONArray(JSONArray, JSONTokener)} with {@code names}, {@code x}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toJSONArray(JSONArray, JSONTokener)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(JSONArray, JSONTokener)"})
  public void testToJSONArrayWithNamesX_whenNull_thenReturnNull() throws JSONException {
    // Arrange
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertNull(CDL.toJSONArray(null, x));
    assertFalse(x.end());
  }

  /**
   * Test {@link CDL#toJSONArray(String)} with {@code string}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toJSONArray(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(String)"})
  public void testToJSONArrayWithString_whenEmptyString_thenReturnNull() throws JSONException {
    // Arrange, Act and Assert
    assertNull(CDL.toJSONArray(""));
  }

  /**
   * Test {@link CDL#toJSONArray(String)} with {@code string}.
   * <ul>
   *   <li>When {@code String}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toJSONArray(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(String)"})
  public void testToJSONArrayWithString_whenString_thenReturnNull() throws JSONException {
    // Arrange, Act and Assert
    assertNull(CDL.toJSONArray("String"));
  }

  /**
   * Test {@link CDL#toJSONArray(JSONTokener)} with {@code x}.
   * <p>
   * Method under test: {@link CDL#toJSONArray(JSONTokener)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * <ul>
   *   <li>Then {@link JSONTokener#JSONTokener(String)} with s is empty string end.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toJSONArray(JSONTokener)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * <ul>
   *   <li>When {@link JSONTokener#JSONTokener(String)} with s is {@code foo}.</li>
   *   <li>Then {@link JSONTokener#JSONTokener(String)} with s is {@code foo} end.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toJSONArray(JSONTokener)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(JSONTokener)"})
  public void testToJSONArrayWithX_whenJSONTokenerWithSIsFoo_thenJSONTokenerWithSIsFooEnd() throws JSONException {
    // Arrange
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertNull(CDL.toJSONArray(x));
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#toJSONArray(JSONTokener)} with {@code x}.
   * <ul>
   *   <li>When {@link JSONTokener#JSONTokener(String)} with s is {@code foo,:]}/\"[{;=#}.</li>
   *   <li>Then {@link JSONTokener#JSONTokener(String)} with s is {@code foo,:]}/\"[{;=#} end.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toJSONArray(JSONTokener)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONArray CDL.toJSONArray(JSONTokener)"})
  public void testToJSONArrayWithX_whenJSONTokenerWithSIsFoo_thenJSONTokenerWithSIsFooEnd2() throws JSONException {
    // Arrange
    JSONTokener x = new JSONTokener("foo,:]}/\\\"[{;=#");

    // Act and Assert
    assertNull(CDL.toJSONArray(x));
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#rowToString(JSONArray)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return {@code []}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#rowToString(JSONArray)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String CDL.rowToString(JSONArray)"})
  public void testRowToString_givenArrayList_thenReturnLeftSquareBracketRightSquareBracket() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put((Collection) new ArrayList<>());

    // Act and Assert
    assertEquals("[]\n", CDL.rowToString(ja));
  }

  /**
   * Test {@link CDL#rowToString(JSONArray)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.</li>
   *   <li>Then return {@code {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#rowToString(JSONArray)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String CDL.rowToString(JSONArray)"})
  public void testRowToString_givenHashMap_thenReturnLeftCurlyBracketRightCurlyBracket() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put((Map) new HashMap<>());

    // Act and Assert
    assertEquals("{}\n", CDL.rowToString(ja));
  }

  /**
   * Test {@link CDL#rowToString(JSONArray)}.
   * <ul>
   *   <li>Given ten.</li>
   *   <li>Then return {@code null,null,null,null,null,null,null,null,null,null,false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#rowToString(JSONArray)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String CDL.rowToString(JSONArray)"})
  public void testRowToString_givenTen_thenReturnNullNullNullNullNullNullNullNullNullNullFalse() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put(10, false);

    // Act and Assert
    assertEquals("null,null,null,null,null,null,null,null,null,null,false\n", CDL.rowToString(ja));
  }

  /**
   * Test {@link CDL#rowToString(JSONArray)}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#rowToString(JSONArray)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String CDL.rowToString(JSONArray)"})
  public void testRowToString_thenReturnFalse() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put(false);

    // Act and Assert
    assertEquals("false\n", CDL.rowToString(ja));
  }

  /**
   * Test {@link CDL#rowToString(JSONArray)}.
   * <ul>
   *   <li>Then return lf.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#rowToString(JSONArray)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String CDL.rowToString(JSONArray)"})
  public void testRowToString_thenReturnLf() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("\n", CDL.rowToString(new JSONArray("[]")));
  }

  /**
   * Test {@link CDL#toString(JSONArray)} with {@code ja}.
   * <p>
   * Method under test: {@link CDL#toString(JSONArray)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String CDL.toString(JSONArray)"})
  public void testToStringWithJa() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.put(HTTP.toJSONObject("https://example.org/example"), JSONObject.NULL);

    JSONArray ja = new JSONArray("[]");
    ja.put((Map) value);

    // Act and Assert
    assertEquals("\"{HTTP-Version:https://example.org/example,Status-Code:,Reason-Phrase:}\"\n\n", CDL.toString(ja));
  }

  /**
   * Test {@link CDL#toString(JSONArray)} with {@code ja}.
   * <ul>
   *   <li>Given {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toString(JSONArray)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String CDL.toString(JSONArray)"})
  public void testToStringWithJa_givenFalse() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put(false);

    // Act and Assert
    assertNull(CDL.toString(ja));
  }

  /**
   * Test {@link CDL#toString(JSONArray)} with {@code ja}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} empty string is {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toString(JSONArray)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String CDL.toString(JSONArray)"})
  public void testToStringWithJa_givenHashMapEmptyStringIsNull_thenReturnNull() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.put("", JSONObject.NULL);

    JSONArray ja = new JSONArray("[]");
    ja.put((Map) value);

    // Act and Assert
    assertEquals("\nnull\n", CDL.toString(ja));
  }

  /**
   * Test {@link CDL#toString(JSONArray)} with {@code ja}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toString(JSONArray)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String CDL.toString(JSONArray)"})
  public void testToStringWithJa_givenHashMapNullIsNull_thenReturnNull() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, JSONObject.NULL);

    JSONArray ja = new JSONArray("[]");
    ja.put((Map) value);

    // Act and Assert
    assertEquals("null\n\n", CDL.toString(ja));
  }

  /**
   * Test {@link CDL#toString(JSONArray)} with {@code ja}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toString(JSONArray)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String CDL.toString(JSONArray)"})
  public void testToStringWithJa_givenHashMap_thenReturnNull() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put((Map) new HashMap<>());

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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String CDL.toString(JSONArray)"})
  public void testToStringWithJa_givenJSONObjectAppendLeftCurlyBracketAndNull_thenReturnNull() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("{", JSONObject.NULL);

    HashMap<Object, Object> value = new HashMap<>();
    value.put(jsonObject, JSONObject.NULL);

    JSONArray ja = new JSONArray("[]");
    ja.put((Map) value);

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
  @Category(MaintainedByDiffblue.class)
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String CDL.toString(JSONArray)"})
  public void testToStringWithJa_givenJSONObjectCommaIsFalse_thenReturnNullFalse() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.put(",", false);
    jsonObject.append("{", JSONObject.NULL);

    HashMap<Object, Object> value = new HashMap<>();
    value.put(jsonObject, JSONObject.NULL);

    JSONArray ja = new JSONArray("[]");
    ja.put((Map) value);

    // Act and Assert
    assertEquals("\"{{:[null],,:false}\"\n\n", CDL.toString(ja));
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String CDL.toString(JSONArray)"})
  public void testToStringWithJa_givenJSONObjectCommaIsHashMap_thenReturnNull() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.put(",", (Map) new HashMap<>());
    jsonObject.append("{", JSONObject.NULL);

    HashMap<Object, Object> value = new HashMap<>();
    value.put(jsonObject, JSONObject.NULL);

    JSONArray ja = new JSONArray("[]");
    ja.put((Map) value);

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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String CDL.toString(JSONArray)"})
  public void testToStringWithJa_givenJSONObjectIncrementComma_thenReturnNull1() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.increment(",");
    jsonObject.append("{", JSONObject.NULL);

    HashMap<Object, Object> value = new HashMap<>();
    value.put(jsonObject, JSONObject.NULL);

    JSONArray ja = new JSONArray("[]");
    ja.put((Map) value);

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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String CDL.toString(JSONArray)"})
  public void testToStringWithJa_givenJSONObjectLeftCurlyBracketIsTen_thenReturn10() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("{", 10.0d);

    HashMap<Object, Object> value = new HashMap<>();
    value.put(jsonObject, JSONObject.NULL);

    JSONArray ja = new JSONArray("[]");
    ja.put((Map) value);

    // Act and Assert
    assertEquals("{\"{\":10}\n\n", CDL.toString(ja));
  }

  /**
   * Test {@link CDL#toString(JSONArray)} with {@code ja}.
   * <ul>
   *   <li>Then return {@code {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toString(JSONArray)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String CDL.toString(JSONArray)"})
  public void testToStringWithJa_thenReturnLeftCurlyBracketRightCurlyBracket() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.put(new JSONObject(), JSONObject.NULL);

    JSONArray ja = new JSONArray("[]");
    ja.put((Map) value);

    // Act and Assert
    assertEquals("{}\n\n", CDL.toString(ja));
  }

  /**
   * Test {@link CDL#toString(JSONArray)} with {@code ja}.
   * <ul>
   *   <li>When {@link JSONArray#JSONArray(String)} with source is {@code []}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toString(JSONArray)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String CDL.toString(JSONArray)"})
  public void testToStringWithJa_whenJSONArrayWithSourceIsLeftSquareBracketRightSquareBracket() throws JSONException {
    // Arrange, Act and Assert
    assertNull(CDL.toString(new JSONArray("[]")));
  }
}
