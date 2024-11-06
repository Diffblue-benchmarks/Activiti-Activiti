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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CDLDiffblueTest {
  @InjectMocks
  private CDL cDL;

  /**
   * Test {@link CDL#rowToJSONArray(JSONTokener)}.
   * <p>
   * Method under test: {@link CDL#rowToJSONArray(JSONTokener)}
   */
  @Test
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
   *   <li>When {@link JSONTokener#JSONTokener(String)} with s is
   * {@code foo,:]}/\"[{;=#}.</li>
   *   <li>Then {@link JSONTokener#JSONTokener(String)} with s is
   * {@code foo,:]}/\"[{;=#} end.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#rowToJSONArray(JSONTokener)}
   */
  @Test
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
  public void testRowToJSONObject_whenNull_thenReturnNull() throws JSONException {
    // Arrange
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertNull(CDL.rowToJSONObject(null, x));
    assertTrue(x.end());
  }

  /**
   * Test {@link CDL#toJSONArray(JSONArray, String)} with {@code names},
   * {@code string}.
   * <p>
   * Method under test: {@link CDL#toJSONArray(JSONArray, String)}
   */
  @Test
  public void testToJSONArrayWithNamesString() throws JSONException {
    // Arrange, Act and Assert
    assertNull(CDL.toJSONArray(new JSONArray("[]"), "String"));
  }

  /**
   * Test {@link CDL#toJSONArray(JSONArray, String)} with {@code names},
   * {@code string}.
   * <ul>
   *   <li>Given {@code String}.</li>
   *   <li>Then return length is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toJSONArray(JSONArray, String)}
   */
  @Test
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
   * Test {@link CDL#toJSONArray(JSONArray, String)} with {@code names},
   * {@code string}.
   * <ul>
   *   <li>Given three.</li>
   *   <li>When empty string.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toJSONArray(JSONArray, String)}
   */
  @Test
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
   * Test {@link CDL#toJSONArray(JSONArray, String)} with {@code names},
   * {@code string}.
   * <ul>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toJSONArray(JSONArray, String)}
   */
  @Test
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
   * Test {@link CDL#toJSONArray(JSONArray, String)} with {@code names},
   * {@code string}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toJSONArray(JSONArray, String)}
   */
  @Test
  public void testToJSONArrayWithNamesString_whenNull_thenReturnNull() throws JSONException {
    // Arrange, Act and Assert
    assertNull(CDL.toJSONArray(null, "String"));
  }

  /**
   * Test {@link CDL#toJSONArray(JSONArray, JSONTokener)} with {@code names},
   * {@code x}.
   * <p>
   * Method under test: {@link CDL#toJSONArray(JSONArray, JSONTokener)}
   */
  @Test
  public void testToJSONArrayWithNamesX() throws JSONException {
    // Arrange
    JSONArray names = new JSONArray("[]");
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertNull(CDL.toJSONArray(names, x));
    assertFalse(x.end());
  }

  /**
   * Test {@link CDL#toJSONArray(JSONArray, JSONTokener)} with {@code names},
   * {@code x}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toJSONArray(JSONArray, JSONTokener)}
   */
  @Test
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
   * Test {@link CDL#toJSONArray(JSONArray, JSONTokener)} with {@code names},
   * {@code x}.
   * <ul>
   *   <li>Given {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toJSONArray(JSONArray, JSONTokener)}
   */
  @Test
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
   * Test {@link CDL#toJSONArray(JSONArray, JSONTokener)} with {@code names},
   * {@code x}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toJSONArray(JSONArray, JSONTokener)}
   */
  @Test
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
   * Test {@link CDL#toJSONArray(JSONArray, JSONTokener)} with {@code names},
   * {@code x}.
   * <ul>
   *   <li>Given {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toJSONArray(JSONArray, JSONTokener)}
   */
  @Test
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
   * Test {@link CDL#toJSONArray(JSONArray, JSONTokener)} with {@code names},
   * {@code x}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toJSONArray(JSONArray, JSONTokener)}
   */
  @Test
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
   *   <li>Then {@link JSONTokener#JSONTokener(String)} with s is empty string
   * end.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toJSONArray(JSONTokener)}
   */
  @Test
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
   *   <li>Then {@link JSONTokener#JSONTokener(String)} with s is {@code foo}
   * end.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toJSONArray(JSONTokener)}
   */
  @Test
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
   *   <li>When {@link JSONTokener#JSONTokener(String)} with s is
   * {@code foo,:]}/\"[{;=#}.</li>
   *   <li>Then {@link JSONTokener#JSONTokener(String)} with s is
   * {@code foo,:]}/\"[{;=#} end.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toJSONArray(JSONTokener)}
   */
  @Test
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
   *   <li>Then return
   * {@code null,null,null,null,null,null,null,null,null,null,false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#rowToString(JSONArray)}
   */
  @Test
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
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@link JSONObject#NULL}
   * and {@link BiFunction}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toString(JSONArray)}
   */
  @Test
  public void testToStringWithJa_givenHashMapComputeIfPresentNullAndBiFunction_thenReturnNull() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));
    value.put(JSONObject.NULL, JSONObject.NULL);

    JSONArray ja = new JSONArray("[]");
    ja.put((Map) value);

    // Act and Assert
    assertEquals("null\n\n", CDL.toString(ja));
  }

  /**
   * Test {@link CDL#toString(JSONArray)} with {@code ja}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} empty string is
   * {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toString(JSONArray)}
   */
  @Test
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
   *   <li>Given {@link HashMap#HashMap()} {@link JSONObject#NULL} is
   * {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toString(JSONArray)}
   */
  @Test
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
   *   <li>Then return {@code {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toString(JSONArray)}
   */
  @Test
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
  public void testToStringWithJa_whenJSONArrayWithSourceIsLeftSquareBracketRightSquareBracket() throws JSONException {
    // Arrange, Act and Assert
    assertNull(CDL.toString(new JSONArray("[]")));
  }

  /**
   * Test {@link CDL#toString(JSONArray, JSONArray)} with {@code names},
   * {@code ja}.
   * <p>
   * Method under test: {@link CDL#toString(JSONArray, JSONArray)}
   */
  @Test
  public void testToStringWithNamesJa() throws JSONException {
    // Arrange
    JSONArray names = mock(JSONArray.class);
    when(names.length()).thenReturn(3);
    JSONArray jsonArray = mock(JSONArray.class);
    when(jsonArray.opt(anyInt())).thenThrow(new JSONException("An error occurred"));
    when(jsonArray.length()).thenReturn(3);
    JSONObject jsonObject = mock(JSONObject.class);
    when(jsonObject.toJSONArray(Mockito.<JSONArray>any())).thenReturn(jsonArray);
    JSONArray ja = mock(JSONArray.class);
    when(ja.optJSONObject(anyInt())).thenReturn(jsonObject);
    when(ja.length()).thenReturn(3);

    // Act and Assert
    assertThrows(JSONException.class, () -> CDL.toString(names, ja));
    verify(names).length();
    verify(ja).length();
    verify(jsonArray).length();
    verify(jsonArray).opt(eq(0));
    verify(ja).optJSONObject(eq(0));
    verify(jsonObject).toJSONArray(isA(JSONArray.class));
  }

  /**
   * Test {@link CDL#toString(JSONArray, JSONArray)} with {@code names},
   * {@code ja}.
   * <ul>
   *   <li>Given {@link JSONArray} {@link JSONArray#opt(int)} return empty
   * string.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toString(JSONArray, JSONArray)}
   */
  @Test
  public void testToStringWithNamesJa_givenJSONArrayOptReturnEmptyString() throws JSONException {
    // Arrange
    JSONArray names = mock(JSONArray.class);
    when(names.length()).thenReturn(3);
    JSONArray jsonArray = mock(JSONArray.class);
    when(jsonArray.opt(anyInt())).thenReturn("");
    when(jsonArray.length()).thenReturn(3);
    JSONObject jsonObject = mock(JSONObject.class);
    when(jsonObject.toJSONArray(Mockito.<JSONArray>any())).thenReturn(jsonArray);
    JSONArray ja = mock(JSONArray.class);
    when(ja.optJSONObject(anyInt())).thenReturn(jsonObject);
    when(ja.length()).thenReturn(3);

    // Act
    String actualToStringResult = CDL.toString(names, ja);

    // Assert
    verify(names).length();
    verify(ja, atLeast(1)).length();
    verify(jsonArray, atLeast(1)).length();
    verify(jsonArray, atLeast(1)).opt(anyInt());
    verify(ja, atLeast(1)).optJSONObject(anyInt());
    verify(jsonObject, atLeast(1)).toJSONArray(isA(JSONArray.class));
    assertEquals(",,\n,,\n,,\n", actualToStringResult);
  }

  /**
   * Test {@link CDL#toString(JSONArray, JSONArray)} with {@code names},
   * {@code ja}.
   * <ul>
   *   <li>Given {@link JSONException#JSONException(String)} with message is
   * {@code An error occurred}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toString(JSONArray, JSONArray)}
   */
  @Test
  public void testToStringWithNamesJa_givenJSONExceptionWithMessageIsAnErrorOccurred() throws JSONException {
    // Arrange
    JSONArray names = mock(JSONArray.class);
    when(names.length()).thenReturn(3);
    JSONArray ja = mock(JSONArray.class);
    when(ja.optJSONObject(anyInt())).thenThrow(new JSONException("An error occurred"));
    when(ja.length()).thenReturn(3);

    // Act and Assert
    assertThrows(JSONException.class, () -> CDL.toString(names, ja));
    verify(names).length();
    verify(ja).length();
    verify(ja).optJSONObject(eq(0));
  }

  /**
   * Test {@link CDL#toString(JSONArray, JSONArray)} with {@code names},
   * {@code ja}.
   * <ul>
   *   <li>Given {@code String}.</li>
   *   <li>Then calls {@link JSONArray#getString(int)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toString(JSONArray, JSONArray)}
   */
  @Test
  public void testToStringWithNamesJa_givenString_thenCallsGetString() throws JSONException {
    // Arrange
    JSONArray names = mock(JSONArray.class);
    when(names.getString(anyInt())).thenReturn("String");
    when(names.length()).thenReturn(3);
    JSONArray ja = mock(JSONArray.class);
    when(ja.optJSONObject(anyInt())).thenReturn(Cookie.toJSONObject("=;"));
    when(ja.length()).thenReturn(3);

    // Act
    String actualToStringResult = CDL.toString(names, ja);

    // Assert
    verify(names, atLeast(1)).getString(anyInt());
    verify(ja, atLeast(1)).length();
    verify(names, atLeast(1)).length();
    verify(ja, atLeast(1)).optJSONObject(anyInt());
    assertEquals(",,\n,,\n,,\n", actualToStringResult);
  }

  /**
   * Test {@link CDL#toString(JSONArray, JSONArray)} with {@code names},
   * {@code ja}.
   * <ul>
   *   <li>Given zero.</li>
   *   <li>When {@link JSONArray} {@link JSONArray#length()} return zero.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toString(JSONArray, JSONArray)}
   */
  @Test
  public void testToStringWithNamesJa_givenZero_whenJSONArrayLengthReturnZero_thenReturnNull() throws JSONException {
    // Arrange
    JSONArray names = mock(JSONArray.class);
    when(names.length()).thenReturn(0);

    // Act
    String actualToStringResult = CDL.toString(names, new JSONArray("[]"));

    // Assert
    verify(names).length();
    assertNull(actualToStringResult);
  }

  /**
   * Test {@link CDL#toString(JSONArray, JSONArray)} with {@code names},
   * {@code ja}.
   * <ul>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toString(JSONArray, JSONArray)}
   */
  @Test
  public void testToStringWithNamesJa_thenReturnEmptyString() throws JSONException {
    // Arrange
    JSONArray names = mock(JSONArray.class);
    when(names.length()).thenReturn(3);

    // Act
    String actualToStringResult = CDL.toString(names, new JSONArray("[]"));

    // Assert
    verify(names).length();
    assertEquals("", actualToStringResult);
  }

  /**
   * Test {@link CDL#toString(JSONArray, JSONArray)} with {@code names},
   * {@code ja}.
   * <ul>
   *   <li>Then return lf lf lf.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toString(JSONArray, JSONArray)}
   */
  @Test
  public void testToStringWithNamesJa_thenReturnLfLfLf() throws JSONException {
    // Arrange
    JSONArray names = mock(JSONArray.class);
    when(names.length()).thenReturn(3);
    JSONObject jsonObject = mock(JSONObject.class);
    when(jsonObject.toJSONArray(Mockito.<JSONArray>any())).thenReturn(new JSONArray("[]"));
    JSONArray ja = mock(JSONArray.class);
    when(ja.optJSONObject(anyInt())).thenReturn(jsonObject);
    when(ja.length()).thenReturn(3);

    // Act
    String actualToStringResult = CDL.toString(names, ja);

    // Assert
    verify(names).length();
    verify(ja, atLeast(1)).length();
    verify(ja, atLeast(1)).optJSONObject(anyInt());
    verify(jsonObject, atLeast(1)).toJSONArray(isA(JSONArray.class));
    assertEquals("\n\n\n", actualToStringResult);
  }

  /**
   * Test {@link CDL#toString(JSONArray, JSONArray)} with {@code names},
   * {@code ja}.
   * <ul>
   *   <li>Then return {@code null,null,null null,null,null null,null,null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toString(JSONArray, JSONArray)}
   */
  @Test
  public void testToStringWithNamesJa_thenReturnNullNullNullNullNullNullNullNullNull() throws JSONException {
    // Arrange
    JSONArray names = mock(JSONArray.class);
    when(names.length()).thenReturn(3);
    JSONArray jsonArray = mock(JSONArray.class);
    when(jsonArray.opt(anyInt())).thenReturn(JSONObject.NULL);
    when(jsonArray.length()).thenReturn(3);
    JSONObject jsonObject = mock(JSONObject.class);
    when(jsonObject.toJSONArray(Mockito.<JSONArray>any())).thenReturn(jsonArray);
    JSONArray ja = mock(JSONArray.class);
    when(ja.optJSONObject(anyInt())).thenReturn(jsonObject);
    when(ja.length()).thenReturn(3);

    // Act
    String actualToStringResult = CDL.toString(names, ja);

    // Assert
    verify(names).length();
    verify(ja, atLeast(1)).length();
    verify(jsonArray, atLeast(1)).length();
    verify(jsonArray, atLeast(1)).opt(anyInt());
    verify(ja, atLeast(1)).optJSONObject(anyInt());
    verify(jsonObject, atLeast(1)).toJSONArray(isA(JSONArray.class));
    assertEquals("null,null,null\nnull,null,null\nnull,null,null\n", actualToStringResult);
  }

  /**
   * Test {@link CDL#toString(JSONArray, JSONArray)} with {@code names},
   * {@code ja}.
   * <ul>
   *   <li>When {@link JSONArray} {@link JSONArray#getString(int)} return
   * {@code null}.</li>
   *   <li>Then calls {@link JSONArray#getString(int)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toString(JSONArray, JSONArray)}
   */
  @Test
  public void testToStringWithNamesJa_whenJSONArrayGetStringReturnNull_thenCallsGetString() throws JSONException {
    // Arrange
    JSONArray names = mock(JSONArray.class);
    when(names.getString(anyInt())).thenReturn(null);
    when(names.length()).thenReturn(3);
    JSONArray ja = mock(JSONArray.class);
    when(ja.optJSONObject(anyInt())).thenReturn(Cookie.toJSONObject("=;"));
    when(ja.length()).thenReturn(3);

    // Act
    String actualToStringResult = CDL.toString(names, ja);

    // Assert
    verify(names, atLeast(1)).getString(anyInt());
    verify(ja, atLeast(1)).length();
    verify(names, atLeast(1)).length();
    verify(ja, atLeast(1)).optJSONObject(anyInt());
    assertEquals(",,\n,,\n,,\n", actualToStringResult);
  }

  /**
   * Test {@link CDL#toString(JSONArray, JSONArray)} with {@code names},
   * {@code ja}.
   * <ul>
   *   <li>When {@link JSONArray} {@link JSONArray#optJSONObject(int)} return
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CDL#toString(JSONArray, JSONArray)}
   */
  @Test
  public void testToStringWithNamesJa_whenJSONArrayOptJSONObjectReturnNull() throws JSONException {
    // Arrange
    JSONArray names = mock(JSONArray.class);
    when(names.length()).thenReturn(3);
    JSONArray ja = mock(JSONArray.class);
    when(ja.optJSONObject(anyInt())).thenReturn(null);
    when(ja.length()).thenReturn(3);

    // Act
    String actualToStringResult = CDL.toString(names, ja);

    // Assert
    verify(names).length();
    verify(ja, atLeast(1)).length();
    verify(ja, atLeast(1)).optJSONObject(anyInt());
    assertEquals("", actualToStringResult);
  }
}
