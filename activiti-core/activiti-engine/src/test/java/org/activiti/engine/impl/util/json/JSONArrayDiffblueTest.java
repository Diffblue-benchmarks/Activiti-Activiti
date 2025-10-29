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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.io.StringWriter;
import java.io.Writer;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.Test;

public class JSONArrayDiffblueTest {
  /**
   * Method under test: {@link JSONArray#get(int)}
   */
  @Test
  public void testGet() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).get(1));
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).get(-1));
  }

  /**
   * Method under test: {@link JSONArray#getBoolean(int)}
   */
  @Test
  public void testGetBoolean() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).getBoolean(1));
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).getBoolean(-1));
  }

  /**
   * Method under test: {@link JSONArray#getBoolean(int)}
   */
  @Test
  public void testGetBoolean2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act and Assert
    assertFalse(jsonArray.getBoolean(1));
  }

  /**
   * Method under test: {@link JSONArray#getBoolean(int)}
   */
  @Test
  public void testGetBoolean3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(40, false);

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonArray.getBoolean(1));
  }

  /**
   * Method under test: {@link JSONArray#getBoolean(int)}
   */
  @Test
  public void testGetBoolean4() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act and Assert
    assertTrue(jsonArray.getBoolean(1));
  }

  /**
   * Method under test: {@link JSONArray#getDouble(int)}
   */
  @Test
  public void testGetDouble() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).getDouble(1));
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).getDouble(-1));
  }

  /**
   * Method under test: {@link JSONArray#getDouble(int)}
   */
  @Test
  public void testGetDouble2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonArray.getDouble(1));
  }

  /**
   * Method under test: {@link JSONArray#getDouble(int)}
   */
  @Test
  public void testGetDouble3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, 0.5d);

    // Act and Assert
    assertEquals(0.5d, jsonArray.getDouble(1), 0.0);
  }

  /**
   * Method under test: {@link JSONArray#getInt(int)}
   */
  @Test
  public void testGetInt() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).getInt(1));
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).getInt(-1));
  }

  /**
   * Method under test: {@link JSONArray#getInt(int)}
   */
  @Test
  public void testGetInt2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonArray.getInt(1));
  }

  /**
   * Method under test: {@link JSONArray#getInt(int)}
   */
  @Test
  public void testGetInt3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, 0.5d);

    // Act and Assert
    assertEquals(0, jsonArray.getInt(1));
  }

  /**
   * Method under test: {@link JSONArray#getJSONArray(int)}
   */
  @Test
  public void testGetJSONArray() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).getJSONArray(1));
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).getJSONArray(-1));
  }

  /**
   * Method under test: {@link JSONArray#getJSONArray(int)}
   */
  @Test
  public void testGetJSONArray2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonArray.getJSONArray(1));
  }

  /**
   * Method under test: {@link JSONArray#getJSONArray(int)}
   */
  @Test
  public void testGetJSONArray3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) new ArrayList<>());

    // Act and Assert
    assertEquals(0, jsonArray.getJSONArray(1).length());
  }

  /**
   * Method under test: {@link JSONArray#getJSONObject(int)}
   */
  @Test
  public void testGetJSONObject() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).getJSONObject(1));
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).getJSONObject(-1));
  }

  /**
   * Method under test: {@link JSONArray#getJSONObject(int)}
   */
  @Test
  public void testGetJSONObject2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonArray.getJSONObject(1));
  }

  /**
   * Method under test: {@link JSONArray#getJSONObject(int)}
   */
  @Test
  public void testGetJSONObject3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Map) new HashMap<>());

    // Act and Assert
    assertEquals(0, jsonArray.getJSONObject(1).length());
  }

  /**
   * Method under test: {@link JSONArray#getJSONObject(int)}
   */
  @Test
  public void testGetJSONObject4() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Map) value);

    // Act and Assert
    assertEquals(0, jsonArray.getJSONObject(1).length());
  }

  /**
   * Method under test: {@link JSONArray#getLong(int)}
   */
  @Test
  public void testGetLong() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).getLong(1));
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).getLong(-1));
  }

  /**
   * Method under test: {@link JSONArray#getLong(int)}
   */
  @Test
  public void testGetLong2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonArray.getLong(1));
  }

  /**
   * Method under test: {@link JSONArray#getLong(int)}
   */
  @Test
  public void testGetLong3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, 0.5d);

    // Act and Assert
    assertEquals(0L, jsonArray.getLong(1));
  }

  /**
   * Method under test: {@link JSONArray#getString(int)}
   */
  @Test
  public void testGetString() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).getString(1));
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).getString(-1));
  }

  /**
   * Method under test: {@link JSONArray#getString(int)}
   */
  @Test
  public void testGetString2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act
    String actualString = jsonArray.getString(1);

    // Assert
    assertEquals(Boolean.FALSE.toString(), actualString);
  }

  /**
   * Method under test: {@link JSONArray#getString(int)}
   */
  @Test
  public void testGetString3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) new ArrayList<>());

    // Act and Assert
    assertEquals("[]", jsonArray.getString(1));
  }

  /**
   * Method under test: {@link JSONArray#getString(int)}
   */
  @Test
  public void testGetString4() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Map) new HashMap<>());

    // Act and Assert
    assertEquals("{}", jsonArray.getString(1));
  }

  /**
   * Method under test: {@link JSONArray#getString(int)}
   */
  @Test
  public void testGetString5() throws JSONException {
    // Arrange
    ArrayList<Object> value = new ArrayList<>();
    value.add(JSONObject.NULL);

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) value);

    // Act and Assert
    assertEquals("[null]", jsonArray.getString(1));
  }

  /**
   * Method under test: {@link JSONArray#getString(int)}
   */
  @Test
  public void testGetString6() throws JSONException {
    // Arrange
    ArrayList<Object> value = new ArrayList<>();
    value.add(JSONObject.NULL);
    value.add(JSONObject.NULL);

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) value);

    // Act and Assert
    assertEquals("[null,null]", jsonArray.getString(1));
  }

  /**
   * Method under test: {@link JSONArray#getString(int)}
   */
  @Test
  public void testGetString7() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, JSONObject.NULL);

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Map) value);

    // Act and Assert
    assertEquals("{\"null\":null}", jsonArray.getString(1));
  }

  /**
   * Method under test: {@link JSONArray#getString(int)}
   */
  @Test
  public void testGetString8() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));
    value.put(JSONObject.NULL, JSONObject.NULL);

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Map) value);

    // Act and Assert
    assertEquals("{\"null\":null}", jsonArray.getString(1));
  }

  /**
   * Method under test: {@link JSONArray#getString(int)}
   */
  @Test
  public void testGetString9() throws JSONException {
    // Arrange
    ArrayList<Object> value = new ArrayList<>();
    value.add(2);

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) value);

    // Act and Assert
    assertEquals("[2]", jsonArray.getString(1));
  }

  /**
   * Method under test: {@link JSONArray#getString(int)}
   */
  @Test
  public void testGetString10() throws JSONException {
    // Arrange
    ArrayList<Object> value = new ArrayList<>();
    value.add(",");

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) value);

    // Act and Assert
    assertEquals("[\",\"]", jsonArray.getString(1));
  }

  /**
   * Method under test: {@link JSONArray#getString(int)}
   */
  @Test
  public void testGetString11() throws JSONException {
    // Arrange
    ArrayList<Object> value = new ArrayList<>();
    value.add("");

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) value);

    // Act and Assert
    assertEquals("[\"\"]", jsonArray.getString(1));
  }

  /**
   * Method under test: {@link JSONArray#isNull(int)}
   */
  @Test
  public void testIsNull() throws JSONException {
    // Arrange, Act and Assert
    assertTrue((new JSONArray("[]")).isNull(1));
    assertTrue((new JSONArray("[]")).isNull(-1));
  }

  /**
   * Method under test: {@link JSONArray#isNull(int)}
   */
  @Test
  public void testIsNull2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(6, false);

    // Act and Assert
    assertTrue(jsonArray.isNull(1));
  }

  /**
   * Method under test: {@link JSONArray#isNull(int)}
   */
  @Test
  public void testIsNull3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act and Assert
    assertFalse(jsonArray.isNull(1));
  }

  /**
   * Method under test: {@link JSONArray#length()}
   */
  @Test
  public void testLength() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0, (new JSONArray("[]")).length());
  }

  /**
   * Method under test: {@link JSONArray#opt(int)}
   */
  @Test
  public void testOpt() throws JSONException {
    // Arrange, Act and Assert
    assertNull((new JSONArray("[]")).opt(1));
    assertNull((new JSONArray("[]")).opt(-1));
  }

  /**
   * Method under test: {@link JSONArray#optBoolean(int)}
   */
  @Test
  public void testOptBoolean() throws JSONException {
    // Arrange, Act and Assert
    assertFalse((new JSONArray("[]")).optBoolean(1));
    assertFalse((new JSONArray("[]")).optBoolean(-1));
    assertTrue((new JSONArray("[]")).optBoolean(1, true));
    assertTrue((new JSONArray("[]")).optBoolean(-1, true));
  }

  /**
   * Method under test: {@link JSONArray#optBoolean(int)}
   */
  @Test
  public void testOptBoolean2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(9, false);

    // Act and Assert
    assertFalse(jsonArray.optBoolean(1));
  }

  /**
   * Method under test: {@link JSONArray#optBoolean(int)}
   */
  @Test
  public void testOptBoolean3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act and Assert
    assertFalse(jsonArray.optBoolean(1));
  }

  /**
   * Method under test: {@link JSONArray#optBoolean(int)}
   */
  @Test
  public void testOptBoolean4() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);
    jsonArray.put(9, false);

    // Act and Assert
    assertTrue(jsonArray.optBoolean(1));
  }

  /**
   * Method under test: {@link JSONArray#optBoolean(int, boolean)}
   */
  @Test
  public void testOptBoolean5() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(9, false);

    // Act and Assert
    assertTrue(jsonArray.optBoolean(1, true));
  }

  /**
   * Method under test: {@link JSONArray#optBoolean(int, boolean)}
   */
  @Test
  public void testOptBoolean6() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act and Assert
    assertFalse(jsonArray.optBoolean(1, true));
  }

  /**
   * Method under test: {@link JSONArray#optBoolean(int, boolean)}
   */
  @Test
  public void testOptBoolean7() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);
    jsonArray.put(9, false);

    // Act and Assert
    assertTrue(jsonArray.optBoolean(1, true));
  }

  /**
   * Method under test: {@link JSONArray#optDouble(int)}
   */
  @Test
  public void testOptDouble() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(Double.NaN, (new JSONArray("[]")).optDouble(1), 0.0);
    assertEquals(Double.NaN, (new JSONArray("[]")).optDouble(-1), 0.0);
    assertEquals(10.0d, (new JSONArray("[]")).optDouble(1, 10.0d), 0.0);
    assertEquals(10.0d, (new JSONArray("[]")).optDouble(3, 10.0d), 0.0);
    assertEquals(10.0d, (new JSONArray("[]")).optDouble(0, 10.0d), 0.0);
    assertEquals(10.0d, (new JSONArray("[]")).optDouble(-1, 10.0d), 0.0);
  }

  /**
   * Method under test: {@link JSONArray#optDouble(int)}
   */
  @Test
  public void testOptDouble2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act and Assert
    assertEquals(Double.NaN, jsonArray.optDouble(1), 0.0);
  }

  /**
   * Method under test: {@link JSONArray#optDouble(int)}
   */
  @Test
  public void testOptDouble3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, 10.0d);

    // Act and Assert
    assertEquals(10.0d, jsonArray.optDouble(1), 0.0);
  }

  /**
   * Method under test: {@link JSONArray#optDouble(int, double)}
   */
  @Test
  public void testOptDouble4() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(3, false);

    // Act and Assert
    assertEquals(10.0d, jsonArray.optDouble(1, 10.0d), 0.0);
  }

  /**
   * Method under test: {@link JSONArray#optDouble(int, double)}
   */
  @Test
  public void testOptDouble5() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, 0.5d);

    // Act and Assert
    assertEquals(0.5d, jsonArray.optDouble(1, 10.0d), 0.0);
  }

  /**
   * Method under test: {@link JSONArray#optInt(int)}
   */
  @Test
  public void testOptInt() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0, (new JSONArray("[]")).optInt(1));
    assertEquals(0, (new JSONArray("[]")).optInt(-1));
    assertEquals(42, (new JSONArray("[]")).optInt(1, 42));
    assertEquals(42, (new JSONArray("[]")).optInt(3, 42));
    assertEquals(42, (new JSONArray("[]")).optInt(0, 42));
    assertEquals(42, (new JSONArray("[]")).optInt(-1, 42));
  }

  /**
   * Method under test: {@link JSONArray#optInt(int)}
   */
  @Test
  public void testOptInt2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act and Assert
    assertEquals(0, jsonArray.optInt(1));
  }

  /**
   * Method under test: {@link JSONArray#optInt(int)}
   */
  @Test
  public void testOptInt3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, 0.5d);

    // Act and Assert
    assertEquals(0, jsonArray.optInt(1));
  }

  /**
   * Method under test: {@link JSONArray#optInt(int, int)}
   */
  @Test
  public void testOptInt4() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(3, false);

    // Act and Assert
    assertEquals(42, jsonArray.optInt(1, 42));
  }

  /**
   * Method under test: {@link JSONArray#optInt(int, int)}
   */
  @Test
  public void testOptInt5() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, 0.5d);

    // Act and Assert
    assertEquals(0, jsonArray.optInt(1, 42));
  }

  /**
   * Method under test: {@link JSONArray#optJSONArray(int)}
   */
  @Test
  public void testOptJSONArray() throws JSONException {
    // Arrange, Act and Assert
    assertNull((new JSONArray("[]")).optJSONArray(1));
    assertNull((new JSONArray("[]")).optJSONArray(-1));
  }

  /**
   * Method under test: {@link JSONArray#optJSONArray(int)}
   */
  @Test
  public void testOptJSONArray2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act and Assert
    assertNull(jsonArray.optJSONArray(1));
  }

  /**
   * Method under test: {@link JSONArray#optJSONArray(int)}
   */
  @Test
  public void testOptJSONArray3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) new ArrayList<>());

    // Act and Assert
    assertEquals(0, jsonArray.optJSONArray(1).length());
  }

  /**
   * Method under test: {@link JSONArray#optJSONObject(int)}
   */
  @Test
  public void testOptJSONObject() throws JSONException {
    // Arrange, Act and Assert
    assertNull((new JSONArray("[]")).optJSONObject(1));
    assertNull((new JSONArray("[]")).optJSONObject(-1));
  }

  /**
   * Method under test: {@link JSONArray#optJSONObject(int)}
   */
  @Test
  public void testOptJSONObject2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act and Assert
    assertNull(jsonArray.optJSONObject(1));
  }

  /**
   * Method under test: {@link JSONArray#optJSONObject(int)}
   */
  @Test
  public void testOptJSONObject3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Map) new HashMap<>());

    // Act and Assert
    assertEquals(0, jsonArray.optJSONObject(1).length());
  }

  /**
   * Method under test: {@link JSONArray#optJSONObject(int)}
   */
  @Test
  public void testOptJSONObject4() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Map) value);

    // Act and Assert
    assertEquals(0, jsonArray.optJSONObject(1).length());
  }

  /**
   * Method under test: {@link JSONArray#optLong(int)}
   */
  @Test
  public void testOptLong() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0L, (new JSONArray("[]")).optLong(1));
    assertEquals(0L, (new JSONArray("[]")).optLong(-1));
    assertEquals(42L, (new JSONArray("[]")).optLong(1, 42L));
    assertEquals(42L, (new JSONArray("[]")).optLong(3, 42L));
    assertEquals(42L, (new JSONArray("[]")).optLong(0, 42L));
    assertEquals(42L, (new JSONArray("[]")).optLong(-1, 42L));
  }

  /**
   * Method under test: {@link JSONArray#optLong(int)}
   */
  @Test
  public void testOptLong2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act and Assert
    assertEquals(0L, jsonArray.optLong(1));
  }

  /**
   * Method under test: {@link JSONArray#optLong(int)}
   */
  @Test
  public void testOptLong3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, 0.5d);

    // Act and Assert
    assertEquals(0L, jsonArray.optLong(1));
  }

  /**
   * Method under test: {@link JSONArray#optLong(int, long)}
   */
  @Test
  public void testOptLong4() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(3, false);

    // Act and Assert
    assertEquals(42L, jsonArray.optLong(1, 42L));
  }

  /**
   * Method under test: {@link JSONArray#optLong(int, long)}
   */
  @Test
  public void testOptLong5() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, 0.5d);

    // Act and Assert
    assertEquals(0L, jsonArray.optLong(1, 42L));
  }

  /**
   * Method under test: {@link JSONArray#optString(int)}
   */
  @Test
  public void testOptString() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("", (new JSONArray("[]")).optString(1));
    assertEquals("", (new JSONArray("[]")).optString(-1));
  }

  /**
   * Method under test: {@link JSONArray#optString(int)}
   */
  @Test
  public void testOptString2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act
    String actualOptStringResult = jsonArray.optString(1);

    // Assert
    assertEquals(Boolean.FALSE.toString(), actualOptStringResult);
  }

  /**
   * Method under test: {@link JSONArray#optString(int)}
   */
  @Test
  public void testOptString3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) new ArrayList<>());

    // Act and Assert
    assertEquals("[]", jsonArray.optString(1));
  }

  /**
   * Method under test: {@link JSONArray#optString(int)}
   */
  @Test
  public void testOptString4() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Map) new HashMap<>());

    // Act and Assert
    assertEquals("{}", jsonArray.optString(1));
  }

  /**
   * Method under test: {@link JSONArray#optString(int)}
   */
  @Test
  public void testOptString5() throws JSONException {
    // Arrange
    ArrayList<Object> value = new ArrayList<>();
    value.add(JSONObject.NULL);

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) value);

    // Act and Assert
    assertEquals("[null]", jsonArray.optString(1));
  }

  /**
   * Method under test: {@link JSONArray#optString(int)}
   */
  @Test
  public void testOptString6() throws JSONException {
    // Arrange
    ArrayList<Object> value = new ArrayList<>();
    value.add(JSONObject.NULL);
    value.add(JSONObject.NULL);

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) value);

    // Act and Assert
    assertEquals("[null,null]", jsonArray.optString(1));
  }

  /**
   * Method under test: {@link JSONArray#optString(int)}
   */
  @Test
  public void testOptString7() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, JSONObject.NULL);

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Map) value);

    // Act and Assert
    assertEquals("{\"null\":null}", jsonArray.optString(1));
  }

  /**
   * Method under test: {@link JSONArray#optString(int)}
   */
  @Test
  public void testOptString8() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));
    value.put(JSONObject.NULL, JSONObject.NULL);

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Map) value);

    // Act and Assert
    assertEquals("{\"null\":null}", jsonArray.optString(1));
  }

  /**
   * Method under test: {@link JSONArray#optString(int)}
   */
  @Test
  public void testOptString9() throws JSONException {
    // Arrange
    ArrayList<Object> value = new ArrayList<>();
    value.add(2);

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) value);

    // Act and Assert
    assertEquals("[2]", jsonArray.optString(1));
  }

  /**
   * Method under test: {@link JSONArray#optString(int)}
   */
  @Test
  public void testOptString10() throws JSONException {
    // Arrange
    ArrayList<Object> value = new ArrayList<>();
    value.add("42");

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) value);

    // Act and Assert
    assertEquals("[\"42\"]", jsonArray.optString(1));
  }

  /**
   * Method under test: {@link JSONArray#optString(int)}
   */
  @Test
  public void testOptString11() throws JSONException {
    // Arrange
    ArrayList<Object> value = new ArrayList<>();
    value.add("");

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) value);

    // Act and Assert
    assertEquals("[\"\"]", jsonArray.optString(1));
  }

  /**
   * Method under test: {@link JSONArray#put(double)}
   */
  @Test
  public void testPut() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(10.0d);

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int)}
   */
  @Test
  public void testPut2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(42);

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, double)}
   */
  @Test
  public void testPut3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(1, 10.0d);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, double)}
   */
  @Test
  public void testPut4() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, 10.0d);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, double)}
   */
  @Test
  public void testPut5() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).put(-1, 10.0d));
  }

  /**
   * Method under test: {@link JSONArray#put(int, int)}
   */
  @Test
  public void testPut6() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(1, 42);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, int)}
   */
  @Test
  public void testPut7() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, 42);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, int)}
   */
  @Test
  public void testPut8() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).put(-1, 42));
  }

  /**
   * Method under test: {@link JSONArray#put(int, long)}
   */
  @Test
  public void testPut9() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(1, 42L);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, long)}
   */
  @Test
  public void testPut10() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, 42L);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, long)}
   */
  @Test
  public void testPut11() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).put(-1, 42L));
  }

  /**
   * Method under test: {@link JSONArray#put(int, Object)}
   */
  @Test
  public void testPut12() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(1, JSONObject.NULL);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Object)}
   */
  @Test
  public void testPut13() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, JSONObject.NULL);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Object)}
   */
  @Test
  public void testPut14() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).put(-1, JSONObject.NULL));
  }

  /**
   * Method under test: {@link JSONArray#put(int, Object)}
   */
  @Test
  public void testPut15() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Object) 10.0d);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Object)}
   */
  @Test
  public void testPut16() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(1, 10.0f);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Object)}
   */
  @Test
  public void testPut17() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(0, (Object) null);

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  public void testPut18() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Collection) new ArrayList<>());

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  public void testPut19() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Collection) new ArrayList<>());

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  public void testPut20() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonArray.put(-1, (Collection) new ArrayList<>()));
  }

  /**
   * Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  public void testPut21() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(JSONObject.NULL);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Collection) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  public void testPut22() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(JSONObject.NULL);
    value.add(JSONObject.NULL);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Collection) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  public void testPut23() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add((byte) 'A');

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Collection) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  public void testPut24() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(2);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Collection) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  public void testPut25() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(true);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Collection) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  public void testPut26() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add('\u0001');

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Collection) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  public void testPut27() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add((short) 1);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Collection) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  public void testPut28() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(1L);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Collection) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  public void testPut29() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(new JSONObject());

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Collection) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  public void testPut30() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(new JSONArray());

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Collection) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  public void testPut31() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(10.0f);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Collection) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  public void testPut32() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(10.0d);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Collection) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  public void testPut33() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(null);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Collection) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  public void testPut34() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Map) new HashMap<>());

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  public void testPut35() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Map) new HashMap<>());

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  public void testPut36() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonArray.put(-1, (Map) new HashMap<>()));
  }

  /**
   * Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  public void testPut37() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, JSONObject.NULL);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Map) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  public void testPut38() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));
    value.put(JSONObject.NULL, JSONObject.NULL);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Map) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  public void testPut39() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, (byte) 'A');

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Map) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  public void testPut40() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, true);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Map) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  public void testPut41() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, '\u0001');

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Map) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  public void testPut42() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, (short) 1);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Map) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  public void testPut43() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, 1);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Map) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  public void testPut44() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, 1L);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Map) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  public void testPut45() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, new AbstractMap.SimpleEntry<>(JSONObject.NULL, JSONObject.NULL));

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Map) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  public void testPut46() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, null);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Map) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  public void testPut47() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, new JSONObject());

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Map) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  public void testPut48() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, new JSONArray());

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Map) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  public void testPut49() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, 10.0f);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Map) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, boolean)}
   */
  @Test
  public void testPut50() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(1, true);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, boolean)}
   */
  @Test
  public void testPut51() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, true);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(int, boolean)}
   */
  @Test
  public void testPut52() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).put(-1, true));
  }

  /**
   * Method under test: {@link JSONArray#put(int, boolean)}
   */
  @Test
  public void testPut53() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(1, false);

    // Assert
    assertEquals(2, jsonArray.length());
    assertEquals(2, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(long)}
   */
  @Test
  public void testPut54() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(42L);

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(Object)}
   */
  @Test
  public void testPut55() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(JSONObject.NULL);

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  public void testPut56() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put((Collection) new ArrayList<>());

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  public void testPut57() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(JSONObject.NULL);

    // Act
    JSONArray actualPutResult = jsonArray.put((Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  public void testPut58() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(JSONObject.NULL);
    value.add(JSONObject.NULL);

    // Act
    JSONArray actualPutResult = jsonArray.put((Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  public void testPut59() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add((byte) 'A');

    // Act
    JSONArray actualPutResult = jsonArray.put((Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  public void testPut60() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(2);

    // Act
    JSONArray actualPutResult = jsonArray.put((Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  public void testPut61() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(true);

    // Act
    JSONArray actualPutResult = jsonArray.put((Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  public void testPut62() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add('\u0001');

    // Act
    JSONArray actualPutResult = jsonArray.put((Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  public void testPut63() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add((short) 1);

    // Act
    JSONArray actualPutResult = jsonArray.put((Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  public void testPut64() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(1L);

    // Act
    JSONArray actualPutResult = jsonArray.put((Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  public void testPut65() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(new JSONObject());

    // Act
    JSONArray actualPutResult = jsonArray.put((Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  public void testPut66() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(new JSONArray());

    // Act
    JSONArray actualPutResult = jsonArray.put((Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  public void testPut67() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(10.0f);

    // Act
    JSONArray actualPutResult = jsonArray.put((Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  public void testPut68() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(10.0d);

    // Act
    JSONArray actualPutResult = jsonArray.put((Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  public void testPut69() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(null);

    // Act
    JSONArray actualPutResult = jsonArray.put((Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  public void testPut70() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put((Map) new HashMap<>());

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  public void testPut71() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, JSONObject.NULL);

    // Act
    JSONArray actualPutResult = jsonArray.put((Map) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  public void testPut72() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));
    value.put(JSONObject.NULL, JSONObject.NULL);

    // Act
    JSONArray actualPutResult = jsonArray.put((Map) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  public void testPut73() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, (byte) 'A');

    // Act
    JSONArray actualPutResult = jsonArray.put((Map) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  public void testPut74() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, true);

    // Act
    JSONArray actualPutResult = jsonArray.put((Map) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  public void testPut75() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, '\u0001');

    // Act
    JSONArray actualPutResult = jsonArray.put((Map) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  public void testPut76() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, (short) 1);

    // Act
    JSONArray actualPutResult = jsonArray.put((Map) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  public void testPut77() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, 1);

    // Act
    JSONArray actualPutResult = jsonArray.put((Map) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  public void testPut78() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, 1L);

    // Act
    JSONArray actualPutResult = jsonArray.put((Map) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  public void testPut79() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, new AbstractMap.SimpleEntry<>(JSONObject.NULL, JSONObject.NULL));

    // Act
    JSONArray actualPutResult = jsonArray.put((Map) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  public void testPut80() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, null);

    // Act
    JSONArray actualPutResult = jsonArray.put((Map) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  public void testPut81() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, new JSONObject());

    // Act
    JSONArray actualPutResult = jsonArray.put((Map) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  public void testPut82() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, new JSONArray());

    // Act
    JSONArray actualPutResult = jsonArray.put((Map) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  public void testPut83() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, 10.0f);

    // Act
    JSONArray actualPutResult = jsonArray.put((Map) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(boolean)}
   */
  @Test
  public void testPut84() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(true);

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#put(boolean)}
   */
  @Test
  public void testPut85() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(false);

    // Assert
    assertEquals(1, jsonArray.length());
    assertEquals(1, actualPutResult.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Method under test: {@link JSONArray#remove(int)}
   */
  @Test
  public void testRemove() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act
    jsonArray.remove(1);

    // Assert
    assertEquals(1, jsonArray.length());
  }

  /**
   * Method under test: {@link JSONArray#remove(int)}
   */
  @Test
  public void testRemove2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act
    jsonArray.remove(1);

    // Assert
    assertEquals(1, jsonArray.length());
  }

  /**
   * Method under test: {@link JSONArray#toString(int)}
   */
  @Test
  public void testToString() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("[]", (new JSONArray("[]")).toString(3));
    assertEquals("[]", (new JSONArray("[]")).toString(3, 1));
  }

  /**
   * Method under test: {@link JSONArray#toString(int)}
   */
  @Test
  public void testToString2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(false);

    // Act and Assert
    assertEquals("[false]", jsonArray.toString(3));
  }

  /**
   * Method under test: {@link JSONArray#toString(int)}
   */
  @Test
  public void testToString3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put((Collection) new ArrayList<>());

    // Act and Assert
    assertEquals("[[]]", jsonArray.toString(3));
  }

  /**
   * Method under test: {@link JSONArray#toString(int)}
   */
  @Test
  public void testToString4() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(0.5d);

    // Act and Assert
    assertEquals("[0.5]", jsonArray.toString(3));
  }

  /**
   * Method under test: {@link JSONArray#toString(int)}
   */
  @Test
  public void testToString5() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1);

    // Act and Assert
    assertEquals("[1]", jsonArray.toString(3));
  }

  /**
   * Method under test: {@link JSONArray#toString(int)}
   */
  @Test
  public void testToString6() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put((Map) new HashMap<>());

    // Act and Assert
    assertEquals("[{}]", jsonArray.toString(3));
  }

  /**
   * Method under test: {@link JSONArray#toString(int)}
   */
  @Test
  public void testToString7() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(JSONObject.NULL);

    // Act and Assert
    assertEquals("[null]", jsonArray.toString(3));
  }

  /**
   * Method under test: {@link JSONArray#toString(int)}
   */
  @Test
  public void testToString8() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put((Collection) new ArrayList<>());
    jsonArray.put(false);

    // Act and Assert
    assertEquals("[\n   [],\n   false\n]", jsonArray.toString(3));
  }

  /**
   * Method under test: {@link JSONArray#toString(int)}
   */
  @Test
  public void testToString9() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(10.0d);
    jsonArray.put((Collection) new ArrayList<>());

    // Act and Assert
    assertEquals("[\n   10,\n   []\n]", jsonArray.toString(3));
  }

  /**
   * Method under test: {@link JSONArray#toString(int, int)}
   */
  @Test
  public void testToString10() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(false);

    // Act and Assert
    assertEquals("[false]", jsonArray.toString(3, 1));
  }

  /**
   * Method under test: {@link JSONArray#toString(int, int)}
   */
  @Test
  public void testToString11() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put((Collection) new ArrayList<>());

    // Act and Assert
    assertEquals("[[]]", jsonArray.toString(3, 1));
  }

  /**
   * Method under test: {@link JSONArray#toString(int, int)}
   */
  @Test
  public void testToString12() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(0.5d);

    // Act and Assert
    assertEquals("[0.5]", jsonArray.toString(3, 1));
  }

  /**
   * Method under test: {@link JSONArray#toString(int, int)}
   */
  @Test
  public void testToString13() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1);

    // Act and Assert
    assertEquals("[1]", jsonArray.toString(3, 1));
  }

  /**
   * Method under test: {@link JSONArray#toString(int, int)}
   */
  @Test
  public void testToString14() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put((Map) new HashMap<>());

    // Act and Assert
    assertEquals("[{}]", jsonArray.toString(3, 1));
  }

  /**
   * Method under test: {@link JSONArray#toString(int, int)}
   */
  @Test
  public void testToString15() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(JSONObject.NULL);

    // Act and Assert
    assertEquals("[null]", jsonArray.toString(3, 1));
  }

  /**
   * Method under test: {@link JSONArray#toString(int, int)}
   */
  @Test
  public void testToString16() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put((Collection) new ArrayList<>());
    jsonArray.put(false);

    // Act and Assert
    assertEquals("[\n    [],\n    false\n ]", jsonArray.toString(3, 1));
  }

  /**
   * Method under test: {@link JSONArray#toString(int, int)}
   */
  @Test
  public void testToString17() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(10.0d);
    jsonArray.put((Collection) new ArrayList<>());

    // Act and Assert
    assertEquals("[\n    10,\n    []\n ]", jsonArray.toString(3, 1));
  }

  /**
   * Method under test: {@link JSONArray#write(Writer)}
   */
  @Test
  public void testWrite() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = jsonArray.write(writer);

    // Assert
    assertEquals("[]", writer.toString());
    assertEquals("[]", actualWriteResult.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Method under test: {@link JSONArray#write(Writer)}
   */
  @Test
  public void testWrite2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(false);
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = jsonArray.write(writer);

    // Assert
    assertEquals("[false]", writer.toString());
    assertEquals("[false]", actualWriteResult.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Method under test: {@link JSONArray#write(Writer)}
   */
  @Test
  public void testWrite3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put((Collection) new ArrayList<>());
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = jsonArray.write(writer);

    // Assert
    assertEquals("[[]]", writer.toString());
    assertEquals("[[]]", actualWriteResult.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Method under test: {@link JSONArray#write(Writer)}
   */
  @Test
  public void testWrite4() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(0.5d);
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = jsonArray.write(writer);

    // Assert
    assertEquals("[0.5]", writer.toString());
    assertEquals("[0.5]", actualWriteResult.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Method under test: {@link JSONArray#write(Writer)}
   */
  @Test
  public void testWrite5() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(91);
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = jsonArray.write(writer);

    // Assert
    assertEquals("[91]", writer.toString());
    assertEquals("[91]", actualWriteResult.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Method under test: {@link JSONArray#write(Writer)}
   */
  @Test
  public void testWrite6() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put((Map) new HashMap<>());
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = jsonArray.write(writer);

    // Assert
    assertEquals("[{}]", writer.toString());
    assertEquals("[{}]", actualWriteResult.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Method under test: {@link JSONArray#write(Writer)}
   */
  @Test
  public void testWrite7() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(JSONObject.NULL);
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = jsonArray.write(writer);

    // Assert
    assertEquals("[null]", writer.toString());
    assertEquals("[null]", actualWriteResult.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Method under test: {@link JSONArray#write(Writer)}
   */
  @Test
  public void testWrite8() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(91, false);
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = jsonArray.write(writer);

    // Assert
    assertEquals("[null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null"
        + ",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null"
        + ",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null"
        + ",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null"
        + ",null,null,null,null,null,null,null,null,null,null,null,false]", writer.toString());
    assertEquals("[null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null"
        + ",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null"
        + ",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null"
        + ",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null"
        + ",null,null,null,null,null,null,null,null,null,null,null,false]", actualWriteResult.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Method under test: {@link JSONArray#write(Writer)}
   */
  @Test
  public void testWrite9() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(10.0d);
    jsonArray.put((Collection) new ArrayList<>());
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = jsonArray.write(writer);

    // Assert
    assertEquals("[10,[]]", writer.toString());
    assertEquals("[10,[]]", actualWriteResult.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link JSONArray#JSONArray()}
   *   <li>{@link JSONArray#toString()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("[]", (new JSONArray()).toString());
  }

  /**
   * Method under test: {@link JSONArray#JSONArray(Object)}
   */
  @Test
  public void testNewJSONArray() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONArray(JSONObject.NULL));
    assertEquals(0, (new JSONArray((Collection) new ArrayList<>())).length());
  }

  /**
   * Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  public void testNewJSONArray2() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(JSONObject.NULL);

    // Act and Assert
    assertEquals(1, (new JSONArray((Collection) collection)).length());
  }

  /**
   * Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  public void testNewJSONArray3() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(JSONObject.NULL);
    collection.add(JSONObject.NULL);

    // Act and Assert
    assertEquals(2, (new JSONArray((Collection) collection)).length());
  }

  /**
   * Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  public void testNewJSONArray4() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add((byte) 'A');

    // Act and Assert
    assertEquals(1, (new JSONArray((Collection) collection)).length());
  }

  /**
   * Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  public void testNewJSONArray5() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(2);

    // Act and Assert
    assertEquals(1, (new JSONArray((Collection) collection)).length());
  }

  /**
   * Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  public void testNewJSONArray6() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(true);

    // Act and Assert
    assertEquals(1, (new JSONArray((Collection) collection)).length());
  }

  /**
   * Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  public void testNewJSONArray7() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add('\u0001');

    // Act and Assert
    assertEquals(1, (new JSONArray((Collection) collection)).length());
  }

  /**
   * Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  public void testNewJSONArray8() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add((short) 1);

    // Act and Assert
    assertEquals(1, (new JSONArray((Collection) collection)).length());
  }

  /**
   * Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  public void testNewJSONArray9() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(1L);

    // Act and Assert
    assertEquals(1, (new JSONArray((Collection) collection)).length());
  }

  /**
   * Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  public void testNewJSONArray10() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(new JSONObject());

    // Act and Assert
    assertEquals(1, (new JSONArray((Collection) collection)).length());
  }

  /**
   * Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  public void testNewJSONArray11() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(new JSONArray());

    // Act and Assert
    assertEquals(1, (new JSONArray((Collection) collection)).length());
  }

  /**
   * Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  public void testNewJSONArray12() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(10.0f);

    // Act and Assert
    assertEquals(1, (new JSONArray((Collection) collection)).length());
  }

  /**
   * Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  public void testNewJSONArray13() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(10.0d);

    // Act and Assert
    assertEquals(1, (new JSONArray((Collection) collection)).length());
  }

  /**
   * Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  public void testNewJSONArray14() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(null);

    // Act and Assert
    assertEquals(1, (new JSONArray((Collection) collection)).length());
  }
}
