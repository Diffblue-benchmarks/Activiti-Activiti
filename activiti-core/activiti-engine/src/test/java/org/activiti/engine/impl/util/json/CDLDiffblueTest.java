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
   * Method under test: {@link CDL#rowToJSONArray(JSONTokener)}
   */
  @Test
  public void testRowToJSONArray() throws JSONException {
    // Arrange
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertEquals(1, CDL.rowToJSONArray(x).length());
    assertTrue(x.end());
  }

  /**
   * Method under test: {@link CDL#rowToJSONArray(JSONTokener)}
   */
  @Test
  public void testRowToJSONArray2() throws JSONException {
    // Arrange
    JSONTokener x = new JSONTokener("");

    // Act and Assert
    assertNull(CDL.rowToJSONArray(x));
    assertTrue(x.end());
  }

  /**
   * Method under test: {@link CDL#rowToJSONArray(JSONTokener)}
   */
  @Test
  public void testRowToJSONArray3() throws JSONException {
    // Arrange
    JSONTokener x = new JSONTokener(",:]}/\\\"[{;=#");

    // Act and Assert
    assertEquals(2, CDL.rowToJSONArray(x).length());
    assertTrue(x.end());
  }

  /**
   * Method under test: {@link CDL#rowToJSONArray(JSONTokener)}
   */
  @Test
  public void testRowToJSONArray4() throws JSONException {
    // Arrange
    JSONTokener x = new JSONTokener("foo,:]}/\\\"[{;=#");

    // Act and Assert
    assertEquals(2, CDL.rowToJSONArray(x).length());
    assertTrue(x.end());
  }

  /**
   * Method under test: {@link CDL#rowToJSONObject(JSONArray, JSONTokener)}
   */
  @Test
  public void testRowToJSONObject() throws JSONException {
    // Arrange
    JSONArray names = new JSONArray("[]");
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertNull(CDL.rowToJSONObject(names, x));
    assertTrue(x.end());
  }

  /**
   * Method under test: {@link CDL#rowToJSONObject(JSONArray, JSONTokener)}
   */
  @Test
  public void testRowToJSONObject2() throws JSONException {
    // Arrange
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertNull(CDL.rowToJSONObject(null, x));
    assertTrue(x.end());
  }

  /**
   * Method under test: {@link CDL#rowToJSONObject(JSONArray, JSONTokener)}
   */
  @Test
  public void testRowToJSONObject3() throws JSONException {
    // Arrange
    JSONArray names = new JSONArray("[]");
    names.put(false);
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertEquals(1, CDL.rowToJSONObject(names, x).length());
    assertTrue(x.end());
  }

  /**
   * Method under test: {@link CDL#rowToJSONObject(JSONArray, JSONTokener)}
   */
  @Test
  public void testRowToJSONObject4() throws JSONException {
    // Arrange
    JSONArray names = new JSONArray("[]");
    names.put((Collection) new ArrayList<>());
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertEquals(1, CDL.rowToJSONObject(names, x).length());
    assertTrue(x.end());
  }

  /**
   * Method under test: {@link CDL#rowToJSONObject(JSONArray, JSONTokener)}
   */
  @Test
  public void testRowToJSONObject5() throws JSONException {
    // Arrange
    JSONArray names = new JSONArray("[]");
    names.put((Map) new HashMap<>());
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertEquals(1, CDL.rowToJSONObject(names, x).length());
    assertTrue(x.end());
  }

  /**
   * Method under test: {@link CDL#rowToJSONObject(JSONArray, JSONTokener)}
   */
  @Test
  public void testRowToJSONObject6() throws JSONException {
    // Arrange
    JSONArray names = new JSONArray("[]");
    names.put(102, false);
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertEquals(0, CDL.rowToJSONObject(names, x).length());
    assertTrue(x.end());
  }

  /**
   * Method under test: {@link CDL#toJSONArray(JSONArray, JSONTokener)}
   */
  @Test
  public void testToJSONArray() throws JSONException {
    // Arrange
    JSONArray names = new JSONArray("[]");
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertNull(CDL.toJSONArray(names, x));
    assertFalse(x.end());
  }

  /**
   * Method under test: {@link CDL#toJSONArray(JSONArray, JSONTokener)}
   */
  @Test
  public void testToJSONArray2() throws JSONException {
    // Arrange
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertNull(CDL.toJSONArray(null, x));
    assertFalse(x.end());
  }

  /**
   * Method under test: {@link CDL#toJSONArray(JSONArray, JSONTokener)}
   */
  @Test
  public void testToJSONArray3() throws JSONException {
    // Arrange
    JSONArray names = new JSONArray("[]");
    names.put(false);
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertEquals(1, CDL.toJSONArray(names, x).length());
    assertTrue(x.end());
  }

  /**
   * Method under test: {@link CDL#toJSONArray(JSONArray, JSONTokener)}
   */
  @Test
  public void testToJSONArray4() throws JSONException {
    // Arrange
    JSONArray names = new JSONArray("[]");
    names.put((Collection) new ArrayList<>());
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertEquals(1, CDL.toJSONArray(names, x).length());
    assertTrue(x.end());
  }

  /**
   * Method under test: {@link CDL#toJSONArray(JSONArray, JSONTokener)}
   */
  @Test
  public void testToJSONArray5() throws JSONException {
    // Arrange
    JSONArray names = new JSONArray("[]");
    names.put((Map) new HashMap<>());
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertEquals(1, CDL.toJSONArray(names, x).length());
    assertTrue(x.end());
  }

  /**
   * Method under test: {@link CDL#toJSONArray(JSONArray, JSONTokener)}
   */
  @Test
  public void testToJSONArray6() throws JSONException {
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
   * Method under test: {@link CDL#toJSONArray(JSONTokener)}
   */
  @Test
  public void testToJSONArray7() throws JSONException {
    // Arrange
    JSONTokener x = new JSONTokener("foo");

    // Act and Assert
    assertNull(CDL.toJSONArray(x));
    assertTrue(x.end());
  }

  /**
   * Method under test: {@link CDL#toJSONArray(JSONTokener)}
   */
  @Test
  public void testToJSONArray8() throws JSONException {
    // Arrange
    JSONTokener x = new JSONTokener("");

    // Act and Assert
    assertNull(CDL.toJSONArray(x));
    assertTrue(x.end());
  }

  /**
   * Method under test: {@link CDL#toJSONArray(JSONTokener)}
   */
  @Test
  public void testToJSONArray9() throws JSONException {
    // Arrange
    JSONTokener x = new JSONTokener(",:]}/\\\"[{;=#");

    // Act and Assert
    assertNull(CDL.toJSONArray(x));
    assertTrue(x.end());
  }

  /**
   * Method under test: {@link CDL#toJSONArray(JSONTokener)}
   */
  @Test
  public void testToJSONArray10() throws JSONException {
    // Arrange
    JSONTokener x = new JSONTokener("foo,:]}/\\\"[{;=#");

    // Act and Assert
    assertNull(CDL.toJSONArray(x));
    assertTrue(x.end());
  }

  /**
   * Method under test: {@link CDL#toJSONArray(String)}
   */
  @Test
  public void testToJSONArray11() throws JSONException {
    // Arrange, Act and Assert
    assertNull(CDL.toJSONArray("String"));
  }

  /**
   * Method under test: {@link CDL#toJSONArray(String)}
   */
  @Test
  public void testToJSONArray12() throws JSONException {
    // Arrange, Act and Assert
    assertNull(CDL.toJSONArray(""));
  }

  /**
   * Method under test: {@link CDL#toJSONArray(JSONArray, String)}
   */
  @Test
  public void testToJSONArray13() throws JSONException {
    // Arrange, Act and Assert
    assertNull(CDL.toJSONArray(new JSONArray("[]"), "String"));
  }

  /**
   * Method under test: {@link CDL#toJSONArray(JSONArray, String)}
   */
  @Test
  public void testToJSONArray14() throws JSONException {
    // Arrange, Act and Assert
    assertNull(CDL.toJSONArray(null, "String"));
  }

  /**
   * Method under test: {@link CDL#toJSONArray(JSONArray, String)}
   */
  @Test
  public void testToJSONArray15() throws JSONException {
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
   * Method under test: {@link CDL#toJSONArray(JSONArray, String)}
   */
  @Test
  public void testToJSONArray16() throws JSONException {
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
   * Method under test: {@link CDL#toJSONArray(JSONArray, String)}
   */
  @Test
  public void testToJSONArray17() throws JSONException {
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
   * Method under test: {@link CDL#rowToString(JSONArray)}
   */
  @Test
  public void testRowToString() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("\n", CDL.rowToString(new JSONArray("[]")));
  }

  /**
   * Method under test: {@link CDL#rowToString(JSONArray)}
   */
  @Test
  public void testRowToString2() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put(false);

    // Act and Assert
    assertEquals("false\n", CDL.rowToString(ja));
  }

  /**
   * Method under test: {@link CDL#rowToString(JSONArray)}
   */
  @Test
  public void testRowToString3() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put((Collection) new ArrayList<>());

    // Act and Assert
    assertEquals("[]\n", CDL.rowToString(ja));
  }

  /**
   * Method under test: {@link CDL#rowToString(JSONArray)}
   */
  @Test
  public void testRowToString4() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put((Map) new HashMap<>());

    // Act and Assert
    assertEquals("{}\n", CDL.rowToString(ja));
  }

  /**
   * Method under test: {@link CDL#rowToString(JSONArray)}
   */
  @Test
  public void testRowToString5() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put(10, false);

    // Act and Assert
    assertEquals("null,null,null,null,null,null,null,null,null,null,false\n", CDL.rowToString(ja));
  }

  /**
   * Method under test: {@link CDL#toString(JSONArray)}
   */
  @Test
  public void testToString() throws JSONException {
    // Arrange, Act and Assert
    assertNull(CDL.toString(new JSONArray("[]")));
  }

  /**
   * Method under test: {@link CDL#toString(JSONArray)}
   */
  @Test
  public void testToString2() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put(false);

    // Act and Assert
    assertNull(CDL.toString(ja));
  }

  /**
   * Method under test: {@link CDL#toString(JSONArray)}
   */
  @Test
  public void testToString3() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put((Map) new HashMap<>());

    // Act and Assert
    assertNull(CDL.toString(ja));
  }

  /**
   * Method under test: {@link CDL#toString(JSONArray)}
   */
  @Test
  public void testToString4() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, JSONObject.NULL);

    JSONArray ja = new JSONArray("[]");
    ja.put((Map) value);

    // Act and Assert
    assertEquals("null\n\n", CDL.toString(ja));
  }

  /**
   * Method under test: {@link CDL#toString(JSONArray)}
   */
  @Test
  public void testToString5() throws JSONException {
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
   * Method under test: {@link CDL#toString(JSONArray)}
   */
  @Test
  public void testToString6() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.put(new JSONObject(), JSONObject.NULL);

    JSONArray ja = new JSONArray("[]");
    ja.put((Map) value);

    // Act and Assert
    assertEquals("{}\n\n", CDL.toString(ja));
  }

  /**
   * Method under test: {@link CDL#toString(JSONArray)}
   */
  @Test
  public void testToString7() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.put(HTTP.toJSONObject("https://example.org/example"), JSONObject.NULL);

    JSONArray ja = new JSONArray("[]");
    ja.put((Map) value);

    // Act and Assert
    assertEquals("\"{HTTP-Version:https://example.org/example,Status-Code:,Reason-Phrase:}\"\n\n", CDL.toString(ja));
  }

  /**
   * Method under test: {@link CDL#toString(JSONArray)}
   */
  @Test
  public void testToString8() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.put("", JSONObject.NULL);

    JSONArray ja = new JSONArray("[]");
    ja.put((Map) value);

    // Act and Assert
    assertEquals("\nnull\n", CDL.toString(ja));
  }

  /**
   * Method under test: {@link CDL#toString(JSONArray, JSONArray)}
   */
  @Test
  public void testToString9() throws JSONException {
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
   * Method under test: {@link CDL#toString(JSONArray, JSONArray)}
   */
  @Test
  public void testToString10() throws JSONException {
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
   * Method under test: {@link CDL#toString(JSONArray, JSONArray)}
   */
  @Test
  public void testToString11() throws JSONException {
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
   * Method under test: {@link CDL#toString(JSONArray, JSONArray)}
   */
  @Test
  public void testToString12() throws JSONException {
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
   * Method under test: {@link CDL#toString(JSONArray, JSONArray)}
   */
  @Test
  public void testToString13() throws JSONException {
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
   * Method under test: {@link CDL#toString(JSONArray, JSONArray)}
   */
  @Test
  public void testToString14() throws JSONException {
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

  /**
   * Method under test: {@link CDL#toString(JSONArray, JSONArray)}
   */
  @Test
  public void testToString15() throws JSONException {
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
   * Method under test: {@link CDL#toString(JSONArray, JSONArray)}
   */
  @Test
  public void testToString16() throws JSONException {
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
   * Method under test: {@link CDL#toString(JSONArray, JSONArray)}
   */
  @Test
  public void testToString17() throws JSONException {
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
   * Method under test: {@link CDL#toString(JSONArray, JSONArray)}
   */
  @Test
  public void testToString18() throws JSONException {
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
}
