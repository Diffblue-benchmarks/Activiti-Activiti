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

import static org.junit.Assert.assertArrayEquals;
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
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.Test;

public class JSONObjectDiffblueTest {
  /**
   * Method under test: {@link JSONObject#accumulate(String, Object)}
   */
  @Test
  public void testAccumulate() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualAccumulateResult = toJSONObjectResult.accumulate("Key", JSONObject.NULL);

    // Assert
    assertEquals(3, actualAccumulateResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualAccumulateResult);
  }

  /**
   * Method under test: {@link JSONObject#accumulate(String, Object)}
   */
  @Test
  public void testAccumulate2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act
    JSONObject actualAccumulateResult = toJSONObjectResult.accumulate("Key", JSONObject.NULL);

    // Assert
    assertEquals(3, actualAccumulateResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualAccumulateResult);
  }

  /**
   * Method under test: {@link JSONObject#accumulate(String, Object)}
   */
  @Test
  public void testAccumulate3() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act
    JSONObject actualAccumulateResult = toJSONObjectResult.accumulate("Key", JSONObject.NULL);

    // Assert
    assertEquals(3, actualAccumulateResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualAccumulateResult);
  }

  /**
   * Method under test: {@link JSONObject#accumulate(String, Object)}
   */
  @Test
  public void testAccumulate4() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").accumulate(null, JSONObject.NULL));
  }

  /**
   * Method under test: {@link JSONObject#accumulate(String, Object)}
   */
  @Test
  public void testAccumulate5() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualAccumulateResult = toJSONObjectResult.accumulate("Key", 10.0d);

    // Assert
    assertEquals(3, actualAccumulateResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualAccumulateResult);
  }

  /**
   * Method under test: {@link JSONObject#accumulate(String, Object)}
   */
  @Test
  public void testAccumulate6() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualAccumulateResult = toJSONObjectResult.accumulate("Key", 10.0f);

    // Assert
    assertEquals(3, actualAccumulateResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualAccumulateResult);
  }

  /**
   * Method under test: {@link JSONObject#accumulate(String, Object)}
   */
  @Test
  public void testAccumulate7() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualAccumulateResult = toJSONObjectResult.accumulate("Key", new JSONArray());

    // Assert
    assertEquals(3, actualAccumulateResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualAccumulateResult);
  }

  /**
   * Method under test: {@link JSONObject#accumulate(String, Object)}
   */
  @Test
  public void testAccumulate8() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualAccumulateResult = toJSONObjectResult.accumulate("Key", null);

    // Assert
    assertEquals(2, actualAccumulateResult.length());
    assertEquals(2, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualAccumulateResult);
  }

  /**
   * Method under test: {@link JSONObject#accumulate(String, Object)}
   */
  @Test
  public void testAccumulate9() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").accumulate("Key", Double.NaN));
  }

  /**
   * Method under test: {@link JSONObject#accumulate(String, Object)}
   */
  @Test
  public void testAccumulate10() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").accumulate("Key", Float.NaN));
  }

  /**
   * Method under test: {@link JSONObject#accumulate(String, Object)}
   */
  @Test
  public void testAccumulate11() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualAccumulateResult = toJSONObjectResult.accumulate("Key", mock(JSONArray.class));

    // Assert
    assertEquals(3, actualAccumulateResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualAccumulateResult);
  }

  /**
   * Method under test: {@link JSONObject#append(String, Object)}
   */
  @Test
  public void testAppend() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualAppendResult = toJSONObjectResult.append("Key", JSONObject.NULL);

    // Assert
    assertEquals(3, actualAppendResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualAppendResult);
  }

  /**
   * Method under test: {@link JSONObject#append(String, Object)}
   */
  @Test
  public void testAppend2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act
    JSONObject actualAppendResult = toJSONObjectResult.append("Key", JSONObject.NULL);

    // Assert
    assertEquals(3, actualAppendResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualAppendResult);
  }

  /**
   * Method under test: {@link JSONObject#append(String, Object)}
   */
  @Test
  public void testAppend3() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertThrows(JSONException.class, () -> toJSONObjectResult.append("Key", JSONObject.NULL));
  }

  /**
   * Method under test: {@link JSONObject#append(String, Object)}
   */
  @Test
  public void testAppend4() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").append(null, JSONObject.NULL));
  }

  /**
   * Method under test: {@link JSONObject#append(String, Object)}
   */
  @Test
  public void testAppend5() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualAppendResult = toJSONObjectResult.append("Key", 10.0d);

    // Assert
    assertEquals(3, actualAppendResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualAppendResult);
  }

  /**
   * Method under test: {@link JSONObject#append(String, Object)}
   */
  @Test
  public void testAppend6() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualAppendResult = toJSONObjectResult.append("Key", 10.0f);

    // Assert
    assertEquals(3, actualAppendResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualAppendResult);
  }

  /**
   * Method under test: {@link JSONObject#append(String, Object)}
   */
  @Test
  public void testAppend7() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualAppendResult = toJSONObjectResult.append("Key", null);

    // Assert
    assertEquals(3, actualAppendResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualAppendResult);
  }

  /**
   * Method under test: {@link JSONObject#append(String, Object)}
   */
  @Test
  public void testAppend8() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").append("Key", Double.NaN));
  }

  /**
   * Method under test: {@link JSONObject#append(String, Object)}
   */
  @Test
  public void testAppend9() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").append("Key", Float.NaN));
  }

  /**
   * Method under test: {@link JSONObject#append(String, Object)}
   */
  @Test
  public void testAppend10() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", mock(JSONArray.class));

    // Act
    JSONObject actualAppendResult = toJSONObjectResult.append("Key", JSONObject.NULL);

    // Assert
    assertEquals(3, actualAppendResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualAppendResult);
  }

  /**
   * Method under test: {@link JSONObject#doubleToString(double)}
   */
  @Test
  public void testDoubleToString() {
    // Arrange, Act and Assert
    assertEquals("10", JSONObject.doubleToString(10.0d));
    assertEquals("0.5", JSONObject.doubleToString(0.5d));
    assertEquals("null", JSONObject.doubleToString(Double.NaN));
  }

  /**
   * Method under test: {@link JSONObject#get(String)}
   */
  @Test
  public void testGet() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").get("Key"));
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").get(null));
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").get(""));
  }

  /**
   * Method under test: {@link JSONObject#get(String)}
   */
  @Test
  public void testGet2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act
    Object actualGetResult = toJSONObjectResult.get("Key");

    // Assert
    assertTrue(actualGetResult instanceof JSONArray);
    assertEquals(1, ((JSONArray) actualGetResult).length());
  }

  /**
   * Method under test: {@link JSONObject#getBoolean(String)}
   */
  @Test
  public void testGetBoolean() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getBoolean("Key"));
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getBoolean(null));
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getBoolean(""));
  }

  /**
   * Method under test: {@link JSONObject#getBoolean(String)}
   */
  @Test
  public void testGetBoolean2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertThrows(JSONException.class, () -> toJSONObjectResult.getBoolean("Key"));
  }

  /**
   * Method under test: {@link JSONObject#getBoolean(String)}
   */
  @Test
  public void testGetBoolean3() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", false);

    // Act and Assert
    assertFalse(toJSONObjectResult.getBoolean("Key"));
  }

  /**
   * Method under test: {@link JSONObject#getBoolean(String)}
   */
  @Test
  public void testGetBoolean4() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", true);

    // Act and Assert
    assertTrue(toJSONObjectResult.getBoolean("Key"));
  }

  /**
   * Method under test: {@link JSONObject#getDouble(String)}
   */
  @Test
  public void testGetDouble() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getDouble("Key"));
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getDouble(null));
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getDouble(""));
  }

  /**
   * Method under test: {@link JSONObject#getDouble(String)}
   */
  @Test
  public void testGetDouble2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertThrows(JSONException.class, () -> toJSONObjectResult.getDouble("Key"));
  }

  /**
   * Method under test: {@link JSONObject#getDouble(String)}
   */
  @Test
  public void testGetDouble3() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertEquals(1.0d, toJSONObjectResult.getDouble("Key"), 0.0);
  }

  /**
   * Method under test: {@link JSONObject#getInt(String)}
   */
  @Test
  public void testGetInt() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getInt("Key"));
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getInt(null));
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getInt(""));
  }

  /**
   * Method under test: {@link JSONObject#getInt(String)}
   */
  @Test
  public void testGetInt2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertThrows(JSONException.class, () -> toJSONObjectResult.getInt("Key"));
  }

  /**
   * Method under test: {@link JSONObject#getInt(String)}
   */
  @Test
  public void testGetInt3() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertEquals(1, toJSONObjectResult.getInt("Key"));
  }

  /**
   * Method under test: {@link JSONObject#getJSONArray(String)}
   */
  @Test
  public void testGetJSONArray() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getJSONArray("Key"));
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getJSONArray(null));
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getJSONArray(""));
  }

  /**
   * Method under test: {@link JSONObject#getJSONArray(String)}
   */
  @Test
  public void testGetJSONArray2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals(1, toJSONObjectResult.getJSONArray("Key").length());
  }

  /**
   * Method under test: {@link JSONObject#getJSONArray(String)}
   */
  @Test
  public void testGetJSONArray3() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertThrows(JSONException.class, () -> toJSONObjectResult.getJSONArray("Key"));
  }

  /**
   * Method under test: {@link JSONObject#getJSONArray(String)}
   */
  @Test
  public void testGetJSONArray4() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", mock(JSONArray.class));

    // Act and Assert
    assertEquals(1, toJSONObjectResult.getJSONArray("Key").length());
  }

  /**
   * Method under test: {@link JSONObject#getJSONObject(String)}
   */
  @Test
  public void testGetJSONObject() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getJSONObject("Key"));
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getJSONObject(null));
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getJSONObject(""));
  }

  /**
   * Method under test: {@link JSONObject#getJSONObject(String)}
   */
  @Test
  public void testGetJSONObject2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertThrows(JSONException.class, () -> toJSONObjectResult.getJSONObject("Key"));
  }

  /**
   * Method under test: {@link JSONObject#getJSONObject(String)}
   */
  @Test
  public void testGetJSONObject3() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", (Map) new HashMap<>());

    // Act and Assert
    assertEquals(0, toJSONObjectResult.getJSONObject("Key").length());
  }

  /**
   * Method under test: {@link JSONObject#getJSONObject(String)}
   */
  @Test
  public void testGetJSONObject4() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", (Map) value);

    // Act and Assert
    assertEquals(0, toJSONObjectResult.getJSONObject("Key").length());
  }

  /**
   * Method under test: {@link JSONObject#getLong(String)}
   */
  @Test
  public void testGetLong() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getLong("Key"));
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getLong(null));
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getLong(""));
  }

  /**
   * Method under test: {@link JSONObject#getLong(String)}
   */
  @Test
  public void testGetLong2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertThrows(JSONException.class, () -> toJSONObjectResult.getLong("Key"));
  }

  /**
   * Method under test: {@link JSONObject#getLong(String)}
   */
  @Test
  public void testGetLong3() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertEquals(1L, toJSONObjectResult.getLong("Key"));
  }

  /**
   * Method under test: {@link JSONObject#getNames(Object)}
   */
  @Test
  public void testGetNames() {
    // Arrange, Act and Assert
    assertNull(JSONObject.getNames(JSONObject.NULL));
    assertNull(JSONObject.getNames((Object) null));
    assertArrayEquals(new String[]{"CASE_INSENSITIVE_ORDER"}, JSONObject.getNames("Object"));
    assertArrayEquals(new String[]{"name", "value"}, JSONObject.getNames(Cookie.toJSONObject("=;")));
    assertNull(JSONObject.getNames(new JSONObject()));
  }

  /**
   * Method under test: {@link JSONObject#getString(String)}
   */
  @Test
  public void testGetString() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getString("Key"));
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getString(null));
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getString(""));
  }

  /**
   * Method under test: {@link JSONObject#getString(String)}
   */
  @Test
  public void testGetString2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals("[null]", toJSONObjectResult.getString("Key"));
  }

  /**
   * Method under test: {@link JSONObject#getString(String)}
   */
  @Test
  public void testGetString3() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertEquals("1", toJSONObjectResult.getString("Key"));
  }

  /**
   * Method under test: {@link JSONObject#getString(String)}
   */
  @Test
  public void testGetString4() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", (Map) new HashMap<>());

    // Act and Assert
    assertEquals("{}", toJSONObjectResult.getString("Key"));
  }

  /**
   * Method under test: {@link JSONObject#getString(String)}
   */
  @Test
  public void testGetString5() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals("[null,null]", toJSONObjectResult.getString("Key"));
  }

  /**
   * Method under test: {@link JSONObject#getString(String)}
   */
  @Test
  public void testGetString6() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", 42);

    // Act and Assert
    assertEquals("[42]", toJSONObjectResult.getString("Key"));
  }

  /**
   * Method under test: {@link JSONObject#getString(String)}
   */
  @Test
  public void testGetString7() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", "42");

    // Act and Assert
    assertEquals("[\"42\"]", toJSONObjectResult.getString("Key"));
  }

  /**
   * Method under test: {@link JSONObject#getString(String)}
   */
  @Test
  public void testGetString8() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", null);

    // Act and Assert
    assertEquals("[null]", toJSONObjectResult.getString("Key"));
  }

  /**
   * Method under test: {@link JSONObject#getString(String)}
   */
  @Test
  public void testGetString9() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, JSONObject.NULL);
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", (Map) value);

    // Act and Assert
    assertEquals("{\"null\":null}", toJSONObjectResult.getString("Key"));
  }

  /**
   * Method under test: {@link JSONObject#getString(String)}
   */
  @Test
  public void testGetString10() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));
    value.put(JSONObject.NULL, JSONObject.NULL);
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", (Map) value);

    // Act and Assert
    assertEquals("{\"null\":null}", toJSONObjectResult.getString("Key"));
  }

  /**
   * Method under test: {@link JSONObject#has(String)}
   */
  @Test
  public void testHas() throws JSONException {
    // Arrange, Act and Assert
    assertFalse(Cookie.toJSONObject("=;").has("Key"));
  }

  /**
   * Method under test: {@link JSONObject#has(String)}
   */
  @Test
  public void testHas2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertTrue(toJSONObjectResult.has("Key"));
  }

  /**
   * Method under test: {@link JSONObject#increment(String)}
   */
  @Test
  public void testIncrement() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualIncrementResult = toJSONObjectResult.increment("Key");

    // Assert
    assertEquals(3, actualIncrementResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualIncrementResult);
  }

  /**
   * Method under test: {@link JSONObject#increment(String)}
   */
  @Test
  public void testIncrement2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertThrows(JSONException.class, () -> toJSONObjectResult.increment("Key"));
  }

  /**
   * Method under test: {@link JSONObject#increment(String)}
   */
  @Test
  public void testIncrement3() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act
    JSONObject actualIncrementResult = toJSONObjectResult.increment("Key");

    // Assert
    assertEquals(3, actualIncrementResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualIncrementResult);
  }

  /**
   * Method under test: {@link JSONObject#increment(String)}
   */
  @Test
  public void testIncrement4() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", 0.5d);

    // Act
    JSONObject actualIncrementResult = toJSONObjectResult.increment("Key");

    // Assert
    assertEquals(3, actualIncrementResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualIncrementResult);
  }

  /**
   * Method under test: {@link JSONObject#increment(String)}
   */
  @Test
  public void testIncrement5() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", 1L);

    // Act
    JSONObject actualIncrementResult = toJSONObjectResult.increment("Key");

    // Assert
    assertEquals(3, actualIncrementResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualIncrementResult);
  }

  /**
   * Method under test: {@link JSONObject#increment(String)}
   */
  @Test
  public void testIncrement6() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").increment(null));
  }

  /**
   * Method under test: {@link JSONObject#isNull(String)}
   */
  @Test
  public void testIsNull() throws JSONException {
    // Arrange, Act and Assert
    assertTrue(Cookie.toJSONObject("=;").isNull("Key"));
    assertTrue(Cookie.toJSONObject("=;").isNull(null));
  }

  /**
   * Method under test: {@link JSONObject#isNull(String)}
   */
  @Test
  public void testIsNull2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertFalse(toJSONObjectResult.isNull("Key"));
  }

  /**
   * Method under test: {@link JSONObject#keys()}
   */
  @Test
  public void testKeys() throws JSONException {
    // Arrange and Act
    Iterator actualKeysResult = Cookie.toJSONObject("=;").keys();

    // Assert
    assertEquals("name", actualKeysResult.next());
    assertEquals("value", actualKeysResult.next());
    assertFalse(actualKeysResult.hasNext());
  }

  /**
   * Method under test: {@link JSONObject#length()}
   */
  @Test
  public void testLength() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(2, Cookie.toJSONObject("=;").length());
  }

  /**
   * Method under test: {@link JSONObject#names()}
   */
  @Test
  public void testNames() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(2, Cookie.toJSONObject("=;").names().length());
    assertNull((new JSONObject()).names());
  }

  /**
   * Method under test: {@link JSONObject#numberToString(Number)}
   */
  @Test
  public void testNumberToString() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("1", JSONObject.numberToString(Integer.valueOf(1)));
    assertThrows(JSONException.class, () -> JSONObject.numberToString(null));
    assertEquals("10", JSONObject.numberToString(10.0d));
    assertEquals("10", JSONObject.numberToString(10.0f));
    assertEquals("0.5", JSONObject.numberToString(0.5d));
    assertThrows(JSONException.class, () -> JSONObject.numberToString(Double.NaN));
    assertThrows(JSONException.class, () -> JSONObject.numberToString(Float.NaN));
  }

  /**
   * Method under test: {@link JSONObject#opt(String)}
   */
  @Test
  public void testOpt() throws JSONException {
    // Arrange, Act and Assert
    assertNull(Cookie.toJSONObject("=;").opt("Key"));
    assertNull(Cookie.toJSONObject("=;").opt(null));
  }

  /**
   * Method under test: {@link JSONObject#optBoolean(String)}
   */
  @Test
  public void testOptBoolean() throws JSONException {
    // Arrange, Act and Assert
    assertFalse(Cookie.toJSONObject("=;").optBoolean("Key"));
    assertFalse(Cookie.toJSONObject("=;").optBoolean(null));
    assertFalse(Cookie.toJSONObject("=;").optBoolean(""));
    assertTrue(Cookie.toJSONObject("=;").optBoolean("Key", true));
    assertTrue(Cookie.toJSONObject("=;").optBoolean(null, true));
    assertTrue(Cookie.toJSONObject("=;").optBoolean("", true));
  }

  /**
   * Method under test: {@link JSONObject#optBoolean(String)}
   */
  @Test
  public void testOptBoolean2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertFalse(toJSONObjectResult.optBoolean("Key"));
  }

  /**
   * Method under test: {@link JSONObject#optBoolean(String)}
   */
  @Test
  public void testOptBoolean3() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", false);

    // Act and Assert
    assertFalse(toJSONObjectResult.optBoolean("Key"));
  }

  /**
   * Method under test: {@link JSONObject#optBoolean(String)}
   */
  @Test
  public void testOptBoolean4() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", true);

    // Act and Assert
    assertTrue(toJSONObjectResult.optBoolean("Key"));
  }

  /**
   * Method under test: {@link JSONObject#optBoolean(String, boolean)}
   */
  @Test
  public void testOptBoolean5() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertTrue(toJSONObjectResult.optBoolean("Key", true));
  }

  /**
   * Method under test: {@link JSONObject#optBoolean(String, boolean)}
   */
  @Test
  public void testOptBoolean6() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", false);

    // Act and Assert
    assertFalse(toJSONObjectResult.optBoolean("Key", true));
  }

  /**
   * Method under test: {@link JSONObject#optBoolean(String, boolean)}
   */
  @Test
  public void testOptBoolean7() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", true);

    // Act and Assert
    assertTrue(toJSONObjectResult.optBoolean("Key", true));
  }

  /**
   * Method under test: {@link JSONObject#optDouble(String)}
   */
  @Test
  public void testOptDouble() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(Double.NaN, Cookie.toJSONObject("=;").optDouble("Key"), 0.0);
    assertEquals(10.0d, Cookie.toJSONObject("=;").optDouble("Key", 10.0d), 0.0);
  }

  /**
   * Method under test: {@link JSONObject#optDouble(String)}
   */
  @Test
  public void testOptDouble2() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals(Double.NaN, jsonObject.optDouble("Key"), 0.0);
  }

  /**
   * Method under test: {@link JSONObject#optDouble(String)}
   */
  @Test
  public void testOptDouble3() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals(Double.NaN, jsonObject.optDouble(null), 0.0);
  }

  /**
   * Method under test: {@link JSONObject#optDouble(String)}
   */
  @Test
  public void testOptDouble4() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.increment("Key");

    // Act and Assert
    assertEquals(1.0d, jsonObject.optDouble("Key"), 0.0);
  }

  /**
   * Method under test: {@link JSONObject#optDouble(String, double)}
   */
  @Test
  public void testOptDouble5() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals(10.0d, jsonObject.optDouble("Key", 10.0d), 0.0);
  }

  /**
   * Method under test: {@link JSONObject#optDouble(String, double)}
   */
  @Test
  public void testOptDouble6() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals(10.0d, jsonObject.optDouble(null, 10.0d), 0.0);
  }

  /**
   * Method under test: {@link JSONObject#optDouble(String, double)}
   */
  @Test
  public void testOptDouble7() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.increment("Key");

    // Act and Assert
    assertEquals(1.0d, jsonObject.optDouble("Key", 10.0d), 0.0);
  }

  /**
   * Method under test: {@link JSONObject#optInt(String)}
   */
  @Test
  public void testOptInt() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0, Cookie.toJSONObject("=;").optInt("Key"));
    assertEquals(0, Cookie.toJSONObject("=;").optInt(null));
    assertEquals(0, Cookie.toJSONObject("=;").optInt(""));
    assertEquals(42, Cookie.toJSONObject("=;").optInt("Key", 42));
    assertEquals(42, Cookie.toJSONObject("=;").optInt(null, 42));
    assertEquals(42, Cookie.toJSONObject("=;").optInt("", 42));
  }

  /**
   * Method under test: {@link JSONObject#optInt(String)}
   */
  @Test
  public void testOptInt2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals(0, toJSONObjectResult.optInt("Key"));
  }

  /**
   * Method under test: {@link JSONObject#optInt(String)}
   */
  @Test
  public void testOptInt3() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertEquals(1, toJSONObjectResult.optInt("Key"));
  }

  /**
   * Method under test: {@link JSONObject#optInt(String, int)}
   */
  @Test
  public void testOptInt4() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals(42, toJSONObjectResult.optInt("Key", 42));
  }

  /**
   * Method under test: {@link JSONObject#optInt(String, int)}
   */
  @Test
  public void testOptInt5() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertEquals(1, toJSONObjectResult.optInt("Key", 42));
  }

  /**
   * Method under test: {@link JSONObject#optJSONArray(String)}
   */
  @Test
  public void testOptJSONArray() throws JSONException {
    // Arrange, Act and Assert
    assertNull(Cookie.toJSONObject("=;").optJSONArray("Key"));
    assertNull(Cookie.toJSONObject("=;").optJSONArray(null));
  }

  /**
   * Method under test: {@link JSONObject#optJSONArray(String)}
   */
  @Test
  public void testOptJSONArray2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals(1, toJSONObjectResult.optJSONArray("Key").length());
  }

  /**
   * Method under test: {@link JSONObject#optJSONArray(String)}
   */
  @Test
  public void testOptJSONArray3() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", mock(JSONArray.class));

    // Act and Assert
    assertEquals(1, toJSONObjectResult.optJSONArray("Key").length());
  }

  /**
   * Method under test: {@link JSONObject#optJSONObject(String)}
   */
  @Test
  public void testOptJSONObject() throws JSONException {
    // Arrange, Act and Assert
    assertNull(Cookie.toJSONObject("=;").optJSONObject("Key"));
    assertNull(Cookie.toJSONObject("=;").optJSONObject(null));
  }

  /**
   * Method under test: {@link JSONObject#optJSONObject(String)}
   */
  @Test
  public void testOptJSONObject2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", (Map) new HashMap<>());

    // Act and Assert
    assertEquals(0, toJSONObjectResult.optJSONObject("Key").length());
  }

  /**
   * Method under test: {@link JSONObject#optJSONObject(String)}
   */
  @Test
  public void testOptJSONObject3() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", (Map) value);

    // Act and Assert
    assertEquals(0, toJSONObjectResult.optJSONObject("Key").length());
  }

  /**
   * Method under test: {@link JSONObject#optLong(String)}
   */
  @Test
  public void testOptLong() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0L, Cookie.toJSONObject("=;").optLong("Key"));
    assertEquals(0L, Cookie.toJSONObject("=;").optLong(null));
    assertEquals(0L, Cookie.toJSONObject("=;").optLong(""));
    assertEquals(42L, Cookie.toJSONObject("=;").optLong("Key", 42L));
    assertEquals(42L, Cookie.toJSONObject("=;").optLong(null, 42L));
    assertEquals(42L, Cookie.toJSONObject("=;").optLong("", 42L));
  }

  /**
   * Method under test: {@link JSONObject#optLong(String)}
   */
  @Test
  public void testOptLong2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals(0L, toJSONObjectResult.optLong("Key"));
  }

  /**
   * Method under test: {@link JSONObject#optLong(String)}
   */
  @Test
  public void testOptLong3() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertEquals(1L, toJSONObjectResult.optLong("Key"));
  }

  /**
   * Method under test: {@link JSONObject#optLong(String, long)}
   */
  @Test
  public void testOptLong4() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals(42L, toJSONObjectResult.optLong("Key", 42L));
  }

  /**
   * Method under test: {@link JSONObject#optLong(String, long)}
   */
  @Test
  public void testOptLong5() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertEquals(1L, toJSONObjectResult.optLong("Key", 42L));
  }

  /**
   * Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  public void testOptString() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("", Cookie.toJSONObject("=;").optString("Key"));
    assertEquals("", Cookie.toJSONObject("=;").optString(null));
    assertEquals("42", Cookie.toJSONObject("=;").optString("Key", "42"));
    assertEquals("42", Cookie.toJSONObject("=;").optString(null, "42"));
  }

  /**
   * Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  public void testOptString2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals("[null]", toJSONObjectResult.optString("Key"));
  }

  /**
   * Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  public void testOptString3() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertEquals("1", toJSONObjectResult.optString("Key"));
  }

  /**
   * Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  public void testOptString4() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", (Map) new HashMap<>());

    // Act and Assert
    assertEquals("{}", toJSONObjectResult.optString("Key"));
  }

  /**
   * Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  public void testOptString5() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals("[null,null]", toJSONObjectResult.optString("Key"));
  }

  /**
   * Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  public void testOptString6() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", 42);

    // Act and Assert
    assertEquals("[42]", toJSONObjectResult.optString("Key"));
  }

  /**
   * Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  public void testOptString7() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", "42");

    // Act and Assert
    assertEquals("[\"42\"]", toJSONObjectResult.optString("Key"));
  }

  /**
   * Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  public void testOptString8() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", null);

    // Act and Assert
    assertEquals("[null]", toJSONObjectResult.optString("Key"));
  }

  /**
   * Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  public void testOptString9() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", "");

    // Act and Assert
    assertEquals("[\"\"]", toJSONObjectResult.optString("Key"));
  }

  /**
   * Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  public void testOptString10() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, JSONObject.NULL);
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", (Map) value);

    // Act and Assert
    assertEquals("{\"null\":null}", toJSONObjectResult.optString("Key"));
  }

  /**
   * Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  public void testOptString11() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));
    value.put(JSONObject.NULL, JSONObject.NULL);
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", (Map) value);

    // Act and Assert
    assertEquals("{\"null\":null}", toJSONObjectResult.optString("Key"));
  }

  /**
   * Method under test: {@link JSONObject#optString(String, String)}
   */
  @Test
  public void testOptString12() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals("[null]", toJSONObjectResult.optString("Key", "42"));
  }

  /**
   * Method under test: {@link JSONObject#optString(String, String)}
   */
  @Test
  public void testOptString13() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertEquals("1", toJSONObjectResult.optString("Key", "42"));
  }

  /**
   * Method under test: {@link JSONObject#optString(String, String)}
   */
  @Test
  public void testOptString14() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", (Map) new HashMap<>());

    // Act and Assert
    assertEquals("{}", toJSONObjectResult.optString("Key", "42"));
  }

  /**
   * Method under test: {@link JSONObject#optString(String, String)}
   */
  @Test
  public void testOptString15() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals("[null,null]", toJSONObjectResult.optString("Key", "42"));
  }

  /**
   * Method under test: {@link JSONObject#optString(String, String)}
   */
  @Test
  public void testOptString16() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", 42);

    // Act and Assert
    assertEquals("[42]", toJSONObjectResult.optString("Key", "42"));
  }

  /**
   * Method under test: {@link JSONObject#optString(String, String)}
   */
  @Test
  public void testOptString17() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", "42");

    // Act and Assert
    assertEquals("[\"42\"]", toJSONObjectResult.optString("Key", "42"));
  }

  /**
   * Method under test: {@link JSONObject#optString(String, String)}
   */
  @Test
  public void testOptString18() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", null);

    // Act and Assert
    assertEquals("[null]", toJSONObjectResult.optString("Key", "42"));
  }

  /**
   * Method under test: {@link JSONObject#optString(String, String)}
   */
  @Test
  public void testOptString19() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", "");

    // Act and Assert
    assertEquals("[\"\"]", toJSONObjectResult.optString("Key", "42"));
  }

  /**
   * Method under test: {@link JSONObject#optString(String, String)}
   */
  @Test
  public void testOptString20() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, JSONObject.NULL);
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", (Map) value);

    // Act and Assert
    assertEquals("{\"null\":null}", toJSONObjectResult.optString("Key", "42"));
  }

  /**
   * Method under test: {@link JSONObject#optString(String, String)}
   */
  @Test
  public void testOptString21() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));
    value.put(JSONObject.NULL, JSONObject.NULL);
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", (Map) value);

    // Act and Assert
    assertEquals("{\"null\":null}", toJSONObjectResult.optString("Key", "42"));
  }

  /**
   * Method under test: {@link JSONObject#put(String, double)}
   */
  @Test
  public void testPut() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", 10.0d);

    // Assert
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, double)}
   */
  @Test
  public void testPut2() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").put(null, 10.0d));
  }

  /**
   * Method under test: {@link JSONObject#put(String, double)}
   */
  @Test
  public void testPut3() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").put("Key", Double.NaN));
  }

  /**
   * Method under test: {@link JSONObject#put(String, int)}
   */
  @Test
  public void testPut4() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", 42);

    // Assert
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, int)}
   */
  @Test
  public void testPut5() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").put(null, 42));
  }

  /**
   * Method under test: {@link JSONObject#put(String, long)}
   */
  @Test
  public void testPut6() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", 42L);

    // Assert
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, long)}
   */
  @Test
  public void testPut7() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").put(null, 42L));
  }

  /**
   * Method under test: {@link JSONObject#put(String, Object)}
   */
  @Test
  public void testPut8() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", JSONObject.NULL);

    // Assert
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, Object)}
   */
  @Test
  public void testPut9() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").put(null, JSONObject.NULL));
  }

  /**
   * Method under test: {@link JSONObject#put(String, Object)}
   */
  @Test
  public void testPut10() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Object) 10.0d);

    // Assert
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, Object)}
   */
  @Test
  public void testPut11() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", 10.0f);

    // Assert
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, Object)}
   */
  @Test
  public void testPut12() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Object) null);

    // Assert
    assertEquals(2, actualPutResult.length());
    assertEquals(2, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, Object)}
   */
  @Test
  public void testPut13() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").put("Key", (Object) Double.NaN));
  }

  /**
   * Method under test: {@link JSONObject#put(String, Object)}
   */
  @Test
  public void testPut14() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").put("Key", Float.NaN));
  }

  /**
   * Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  public void testPut15() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    ArrayList<Object> value = new ArrayList<>();

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Collection) value);

    // Assert
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertTrue(value.isEmpty());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  public void testPut16() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act and Assert
    assertThrows(JSONException.class, () -> toJSONObjectResult.put(null, (Collection) new ArrayList<>()));
  }

  /**
   * Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  public void testPut17() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    ArrayList<Object> value = new ArrayList<>();
    value.add(JSONObject.NULL);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Collection) value);

    // Assert
    assertEquals(1, value.size());
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
    Object expectedGetResult = actualPutResult.NULL;
    assertSame(expectedGetResult, value.get(0));
  }

  /**
   * Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  public void testPut18() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    ArrayList<Object> value = new ArrayList<>();
    value.add(JSONObject.NULL);
    value.add(JSONObject.NULL);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Collection) value);

    // Assert
    assertEquals(2, value.size());
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
    Object object = actualPutResult.NULL;
    assertSame(object, value.get(0));
    assertSame(object, value.get(1));
  }

  /**
   * Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  public void testPut19() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    ArrayList<Object> value = new ArrayList<>();
    value.add((byte) 'A');

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Collection) value);

    // Assert
    assertEquals(1, value.size());
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  public void testPut20() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    ArrayList<Object> value = new ArrayList<>();
    value.add(2);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Collection) value);

    // Assert
    assertEquals(1, value.size());
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  public void testPut21() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    ArrayList<Object> value = new ArrayList<>();
    value.add(true);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Collection) value);

    // Assert
    assertEquals(1, value.size());
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  public void testPut22() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    ArrayList<Object> value = new ArrayList<>();
    value.add('\u0001');

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Collection) value);

    // Assert
    assertEquals(1, value.size());
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  public void testPut23() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    ArrayList<Object> value = new ArrayList<>();
    value.add((short) 1);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Collection) value);

    // Assert
    assertEquals(1, value.size());
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  public void testPut24() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    ArrayList<Object> value = new ArrayList<>();
    value.add(1L);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Collection) value);

    // Assert
    assertEquals(1, value.size());
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  public void testPut25() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    ArrayList<Object> value = new ArrayList<>();
    JSONObject jsonObject = new JSONObject();
    value.add(jsonObject);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Collection) value);

    // Assert
    assertEquals(1, value.size());
    Object getResult = value.get(0);
    assertTrue(getResult instanceof JSONObject);
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(jsonObject, getResult);
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  public void testPut26() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    ArrayList<Object> value = new ArrayList<>();
    JSONArray jsonArray = new JSONArray();
    value.add(jsonArray);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Collection) value);

    // Assert
    assertEquals(1, value.size());
    Object getResult = value.get(0);
    assertTrue(getResult instanceof JSONArray);
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(jsonArray, getResult);
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  public void testPut27() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    ArrayList<Object> value = new ArrayList<>();
    value.add(10.0f);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Collection) value);

    // Assert
    assertEquals(1, value.size());
    assertEquals(10.0f, ((Float) value.get(0)).floatValue(), 0.0f);
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  public void testPut28() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    ArrayList<Object> value = new ArrayList<>();
    value.add(10.0d);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Collection) value);

    // Assert
    assertEquals(1, value.size());
    assertEquals(10.0d, ((Double) value.get(0)).doubleValue(), 0.0);
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  public void testPut29() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    ArrayList<Object> value = new ArrayList<>();
    value.add(null);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Collection) value);

    // Assert
    assertEquals(1, value.size());
    assertNull(value.get(0));
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  public void testPut30() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    ArrayList<Object> value = new ArrayList<>();
    value.add(mock(JSONArray.class));

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Collection) value);

    // Assert
    assertEquals(1, value.size());
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  public void testPut31() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Map) new HashMap<>());

    // Assert
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  public void testPut32() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act and Assert
    assertThrows(JSONException.class, () -> toJSONObjectResult.put(null, (Map) new HashMap<>()));
  }

  /**
   * Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  public void testPut33() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, JSONObject.NULL);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Map) value);

    // Assert
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  public void testPut34() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    HashMap<Object, Object> value = new HashMap<>();
    value.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));
    value.put(JSONObject.NULL, JSONObject.NULL);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Map) value);

    // Assert
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  public void testPut35() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, (byte) 'A');

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Map) value);

    // Assert
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  public void testPut36() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, true);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Map) value);

    // Assert
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  public void testPut37() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, '\u0001');

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Map) value);

    // Assert
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  public void testPut38() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, (short) 1);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Map) value);

    // Assert
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  public void testPut39() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, 1);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Map) value);

    // Assert
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  public void testPut40() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, 1L);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Map) value);

    // Assert
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  public void testPut41() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, new AbstractMap.SimpleEntry<>(JSONObject.NULL, JSONObject.NULL));

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Map) value);

    // Assert
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  public void testPut42() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, null);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Map) value);

    // Assert
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  public void testPut43() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, new JSONObject());

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Map) value);

    // Assert
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  public void testPut44() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, new JSONArray());

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Map) value);

    // Assert
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  public void testPut45() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, 10.0f);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Map) value);

    // Assert
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, boolean)}
   */
  @Test
  public void testPut46() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", true);

    // Assert
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#put(String, boolean)}
   */
  @Test
  public void testPut47() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").put(null, true));
  }

  /**
   * Method under test: {@link JSONObject#put(String, boolean)}
   */
  @Test
  public void testPut48() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", false);

    // Assert
    assertEquals(3, actualPutResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Method under test: {@link JSONObject#putOnce(String, Object)}
   */
  @Test
  public void testPutOnce() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutOnceResult = toJSONObjectResult.putOnce("Key", JSONObject.NULL);

    // Assert
    assertEquals(3, actualPutOnceResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutOnceResult);
  }

  /**
   * Method under test: {@link JSONObject#putOnce(String, Object)}
   */
  @Test
  public void testPutOnce2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertThrows(JSONException.class, () -> toJSONObjectResult.putOnce("Key", JSONObject.NULL));
  }

  /**
   * Method under test: {@link JSONObject#putOnce(String, Object)}
   */
  @Test
  public void testPutOnce3() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutOnceResult = toJSONObjectResult.putOnce(null, JSONObject.NULL);

    // Assert
    assertEquals(2, actualPutOnceResult.length());
    assertEquals(2, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutOnceResult);
  }

  /**
   * Method under test: {@link JSONObject#putOnce(String, Object)}
   */
  @Test
  public void testPutOnce4() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutOnceResult = toJSONObjectResult.putOnce("Key", 10.0d);

    // Assert
    assertEquals(3, actualPutOnceResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutOnceResult);
  }

  /**
   * Method under test: {@link JSONObject#putOnce(String, Object)}
   */
  @Test
  public void testPutOnce5() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutOnceResult = toJSONObjectResult.putOnce("Key", 10.0f);

    // Assert
    assertEquals(3, actualPutOnceResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutOnceResult);
  }

  /**
   * Method under test: {@link JSONObject#putOnce(String, Object)}
   */
  @Test
  public void testPutOnce6() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutOnceResult = toJSONObjectResult.putOnce("Key", null);

    // Assert
    assertEquals(2, actualPutOnceResult.length());
    assertEquals(2, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutOnceResult);
  }

  /**
   * Method under test: {@link JSONObject#putOnce(String, Object)}
   */
  @Test
  public void testPutOnce7() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").putOnce("Key", Double.NaN));
  }

  /**
   * Method under test: {@link JSONObject#putOnce(String, Object)}
   */
  @Test
  public void testPutOnce8() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").putOnce("Key", Float.NaN));
  }

  /**
   * Method under test: {@link JSONObject#putOpt(String, Object)}
   */
  @Test
  public void testPutOpt() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutOptResult = toJSONObjectResult.putOpt("Key", JSONObject.NULL);

    // Assert
    assertEquals(3, actualPutOptResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutOptResult);
  }

  /**
   * Method under test: {@link JSONObject#putOpt(String, Object)}
   */
  @Test
  public void testPutOpt2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutOptResult = toJSONObjectResult.putOpt(null, JSONObject.NULL);

    // Assert
    assertEquals(2, actualPutOptResult.length());
    assertEquals(2, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutOptResult);
  }

  /**
   * Method under test: {@link JSONObject#putOpt(String, Object)}
   */
  @Test
  public void testPutOpt3() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutOptResult = toJSONObjectResult.putOpt("Key", 10.0d);

    // Assert
    assertEquals(3, actualPutOptResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutOptResult);
  }

  /**
   * Method under test: {@link JSONObject#putOpt(String, Object)}
   */
  @Test
  public void testPutOpt4() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutOptResult = toJSONObjectResult.putOpt("Key", 10.0f);

    // Assert
    assertEquals(3, actualPutOptResult.length());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutOptResult);
  }

  /**
   * Method under test: {@link JSONObject#putOpt(String, Object)}
   */
  @Test
  public void testPutOpt5() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutOptResult = toJSONObjectResult.putOpt("Key", null);

    // Assert
    assertEquals(2, actualPutOptResult.length());
    assertEquals(2, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutOptResult);
  }

  /**
   * Method under test: {@link JSONObject#putOpt(String, Object)}
   */
  @Test
  public void testPutOpt6() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").putOpt("Key", Double.NaN));
  }

  /**
   * Method under test: {@link JSONObject#putOpt(String, Object)}
   */
  @Test
  public void testPutOpt7() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").putOpt("Key", Float.NaN));
  }

  /**
   * Method under test: {@link JSONObject#quote(String)}
   */
  @Test
  public void testQuote() {
    // Arrange, Act and Assert
    assertEquals("\"String\"", JSONObject.quote("String"));
    assertEquals("\"\"", JSONObject.quote(null));
    assertEquals("\"\"", JSONObject.quote(""));
  }

  /**
   * Method under test: {@link JSONObject#remove(String)}
   */
  @Test
  public void testRemove() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act and Assert
    assertNull(toJSONObjectResult.remove("Key"));
    assertEquals(2, toJSONObjectResult.length());
  }

  /**
   * Method under test: {@link JSONObject#remove(String)}
   */
  @Test
  public void testRemove2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", false);

    // Act
    toJSONObjectResult.remove("Key");

    // Assert
    assertEquals(2, toJSONObjectResult.length());
  }

  /**
   * Method under test: {@link JSONObject#remove(String)}
   */
  @Test
  public void testRemove3() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", true);

    // Act
    toJSONObjectResult.remove("Key");

    // Assert
    assertEquals(2, toJSONObjectResult.length());
  }

  /**
   * Method under test: {@link JSONObject#sortedKeys()}
   */
  @Test
  public void testSortedKeys() throws JSONException {
    // Arrange and Act
    Iterator actualSortedKeysResult = Cookie.toJSONObject("=;").sortedKeys();

    // Assert
    assertEquals("name", actualSortedKeysResult.next());
    assertEquals("value", actualSortedKeysResult.next());
    assertFalse(actualSortedKeysResult.hasNext());
  }

  /**
   * Method under test: {@link JSONObject#stringToValue(String)}
   */
  @Test
  public void testStringToValue() {
    // Arrange, Act and Assert
    assertEquals("foo", JSONObject.stringToValue("foo"));
    assertEquals("", JSONObject.stringToValue(""));
    assertEquals(".", JSONObject.stringToValue("."));
    assertEquals("42foo", JSONObject.stringToValue("42foo"));
    assertEquals("42true", JSONObject.stringToValue("42true"));
    assertEquals(42.0d, ((Double) JSONObject.stringToValue("42.")).doubleValue(), 0.0);
    assertEquals("0foo", JSONObject.stringToValue("0foo"));
  }

  /**
   * Method under test: {@link JSONObject#testValidity(Object)}
   */
  @Test
  public void testTestValidity() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> JSONObject.testValidity(Double.NaN));
    assertThrows(JSONException.class, () -> JSONObject.testValidity(Float.NaN));
  }

  /**
   * Method under test: {@link JSONObject#toJSONArray(JSONArray)}
   */
  @Test
  public void testToJSONArray() throws JSONException {
    // Arrange, Act and Assert
    assertNull(Cookie.toJSONObject("=;").toJSONArray(null));
  }

  /**
   * Method under test: {@link JSONObject#toJSONArray(JSONArray)}
   */
  @Test
  public void testToJSONArray2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act and Assert
    assertNull(toJSONObjectResult.toJSONArray(new JSONArray()));
  }

  /**
   * Method under test: {@link JSONObject#toJSONArray(JSONArray)}
   */
  @Test
  public void testToJSONArray3() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    JSONArray names = new JSONArray();
    names.put(true);

    // Act and Assert
    assertEquals(1, toJSONObjectResult.toJSONArray(names).length());
  }

  /**
   * Method under test: {@link JSONObject#toJSONArray(JSONArray)}
   */
  @Test
  public void testToJSONArray4() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    JSONArray names = new JSONArray();
    names.put((Collection) new ArrayList<>());
    names.put(true);

    // Act and Assert
    assertEquals(2, toJSONObjectResult.toJSONArray(names).length());
  }

  /**
   * Method under test: {@link JSONObject#toJSONArray(JSONArray)}
   */
  @Test
  public void testToJSONArray5() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    JSONArray names = new JSONArray();
    names.put((Map) new HashMap<>());
    names.put(true);

    // Act and Assert
    assertEquals(2, toJSONObjectResult.toJSONArray(names).length());
  }

  /**
   * Method under test: {@link JSONObject#toString()}
   */
  @Test
  public void testToString() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("{\"name\":\"\",\"value\":\"\"}", Cookie.toJSONObject("=;").toString());
    assertEquals("{\"HTTP-Version\":\"https://example.org/example\",\"Status-Code\":\"\",\"Reason-Phrase\":\"\"}",
        HTTP.toJSONObject("https://example.org/example").toString());
    assertEquals("{\n   \"name\": \"\",\n   \"value\": \"\"\n}", Cookie.toJSONObject("=;").toString(3));
    assertEquals("{}", (new JSONObject()).toString(3));
    assertEquals(
        "{\n   \"HTTP-Version\": \"https://example.org/example\",\n   \"Reason-Phrase\": \"\",\n   \"Status-Code\": \"\"\n}",
        HTTP.toJSONObject("https://example.org/example").toString(3));
    assertEquals("{\n    \"name\": \"\",\n    \"value\": \"\"\n }", Cookie.toJSONObject("=;").toString(3, 1));
    assertEquals("{}", (new JSONObject()).toString(3, 1));
    assertEquals("{\n" + "    \"HTTP-Version\": \"https://example.org/example\",\n" + "    \"Reason-Phrase\": \"\",\n"
        + "    \"Status-Code\": \"\"\n" + " }", HTTP.toJSONObject("https://example.org/example").toString(3, 1));
  }

  /**
   * Method under test: {@link JSONObject#toString()}
   */
  @Test
  public void testToString2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("\"\"", JSONObject.NULL);

    // Act and Assert
    assertEquals("{\"\\\"\\\"\":[null],\"name\":\"\",\"value\":\"\"}", toJSONObjectResult.toString());
  }

  /**
   * Method under test: {@link JSONObject#toString()}
   */
  @Test
  public void testToString3() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("\"\"");

    // Act and Assert
    assertEquals("{\"\\\"\\\"\":1,\"name\":\"\",\"value\":\"\"}", toJSONObjectResult.toString());
  }

  /**
   * Method under test: {@link JSONObject#toString()}
   */
  @Test
  public void testToString4() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("\"\"", false);

    // Act and Assert
    assertEquals("{\"\\\"\\\"\":false,\"name\":\"\",\"value\":\"\"}", toJSONObjectResult.toString());
  }

  /**
   * Method under test: {@link JSONObject#toString()}
   */
  @Test
  public void testToString5() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("\"\"", 0.5d);

    // Act and Assert
    assertEquals("{\"\\\"\\\"\":0.5,\"name\":\"\",\"value\":\"\"}", toJSONObjectResult.toString());
  }

  /**
   * Method under test: {@link JSONObject#toString()}
   */
  @Test
  public void testToString6() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("\"\"", (Map) new HashMap<>());

    // Act and Assert
    assertEquals("{\"\\\"\\\"\":{},\"name\":\"\",\"value\":\"\"}", toJSONObjectResult.toString());
  }

  /**
   * Method under test: {@link JSONObject#toString()}
   */
  @Test
  public void testToString7() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("{", 10.0d);
    toJSONObjectResult.increment("\"\"");

    // Act and Assert
    assertEquals("{\"\\\"\\\"\":1,\"name\":\"\",\"{\":10,\"value\":\"\"}", toJSONObjectResult.toString());
  }

  /**
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  public void testToString8() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append(": ", JSONObject.NULL);

    // Act and Assert
    assertEquals("{\n   \": \": [null],\n   \"name\": \"\",\n   \"value\": \"\"\n}", toJSONObjectResult.toString(3));
  }

  /**
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  public void testToString9() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment(": ");

    // Act and Assert
    assertEquals("{\n   \": \": 1,\n   \"name\": \"\",\n   \"value\": \"\"\n}", toJSONObjectResult.toString(3));
  }

  /**
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  public void testToString10() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put(": ", false);

    // Act and Assert
    assertEquals("{\n   \": \": false,\n   \"name\": \"\",\n   \"value\": \"\"\n}", toJSONObjectResult.toString(3));
  }

  /**
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  public void testToString11() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put(": ", (Collection) new ArrayList<>());

    // Act and Assert
    assertEquals("{\n   \": \": [],\n   \"name\": \"\",\n   \"value\": \"\"\n}", toJSONObjectResult.toString(3));
  }

  /**
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  public void testToString12() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put(": ", 0.5d);

    // Act and Assert
    assertEquals("{\n   \": \": 0.5,\n   \"name\": \"\",\n   \"value\": \"\"\n}", toJSONObjectResult.toString(3));
  }

  /**
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  public void testToString13() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put(": ", (Map) new HashMap<>());

    // Act and Assert
    assertEquals("{\n   \": \": {},\n   \"name\": \"\",\n   \"value\": \"\"\n}", toJSONObjectResult.toString(3));
  }

  /**
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  public void testToString14() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append(": ", JSONObject.NULL);

    // Act and Assert
    assertEquals("{\": \": [null]}", jsonObject.toString(3));
  }

  /**
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  public void testToString15() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append(",\n", JSONObject.NULL);

    // Act and Assert
    assertEquals("{\n   \",\\n\": [null],\n   \"name\": \"\",\n   \"value\": \"\"\n}", toJSONObjectResult.toString(3));
  }

  /**
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  public void testToString16() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("\"\"", JSONObject.NULL);

    // Act and Assert
    assertEquals("{\n   \"\\\"\\\"\": [null],\n   \"name\": \"\",\n   \"value\": \"\"\n}",
        toJSONObjectResult.toString(3));
  }

  /**
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  public void testToString17() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("{", 10.0d);
    toJSONObjectResult.increment(": ");

    // Act and Assert
    assertEquals("{\n   \": \": 1,\n   \"name\": \"\",\n   \"value\": \"\",\n   \"{\": 10\n}",
        toJSONObjectResult.toString(3));
  }

  /**
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  public void testToString18() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append(": ", JSONObject.NULL);

    // Act and Assert
    assertEquals("{\n    \": \": [null],\n    \"name\": \"\",\n    \"value\": \"\"\n }",
        toJSONObjectResult.toString(3, 1));
  }

  /**
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  public void testToString19() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment(": ");

    // Act and Assert
    assertEquals("{\n    \": \": 1,\n    \"name\": \"\",\n    \"value\": \"\"\n }", toJSONObjectResult.toString(3, 1));
  }

  /**
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  public void testToString20() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put(": ", false);

    // Act and Assert
    assertEquals("{\n    \": \": false,\n    \"name\": \"\",\n    \"value\": \"\"\n }",
        toJSONObjectResult.toString(3, 1));
  }

  /**
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  public void testToString21() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put(": ", (Collection) new ArrayList<>());

    // Act and Assert
    assertEquals("{\n    \": \": [],\n    \"name\": \"\",\n    \"value\": \"\"\n }", toJSONObjectResult.toString(3, 1));
  }

  /**
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  public void testToString22() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put(": ", 0.5d);

    // Act and Assert
    assertEquals("{\n    \": \": 0.5,\n    \"name\": \"\",\n    \"value\": \"\"\n }",
        toJSONObjectResult.toString(3, 1));
  }

  /**
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  public void testToString23() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put(": ", (Map) new HashMap<>());

    // Act and Assert
    assertEquals("{\n    \": \": {},\n    \"name\": \"\",\n    \"value\": \"\"\n }", toJSONObjectResult.toString(3, 1));
  }

  /**
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  public void testToString24() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append(": ", JSONObject.NULL);

    // Act and Assert
    assertEquals("{\": \": [null]}", jsonObject.toString(3, 1));
  }

  /**
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  public void testToString25() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append(",\n", JSONObject.NULL);

    // Act and Assert
    assertEquals("{\n    \",\\n\": [null],\n    \"name\": \"\",\n    \"value\": \"\"\n }",
        toJSONObjectResult.toString(3, 1));
  }

  /**
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  public void testToString26() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("\"\"", JSONObject.NULL);

    // Act and Assert
    assertEquals("{\n    \"\\\"\\\"\": [null],\n    \"name\": \"\",\n    \"value\": \"\"\n }",
        toJSONObjectResult.toString(3, 1));
  }

  /**
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  public void testToString27() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("{", 10.0d);
    toJSONObjectResult.increment(": ");

    // Act and Assert
    assertEquals("{\n    \": \": 1,\n    \"name\": \"\",\n    \"value\": \"\",\n    \"{\": 10\n }",
        toJSONObjectResult.toString(3, 1));
  }

  /**
   * Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  public void testValueToString() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("null", JSONObject.valueToString(JSONObject.NULL));
    assertEquals("null", JSONObject.valueToString(null));
    assertEquals("42", JSONObject.valueToString(42));
    assertEquals("\"42\"", JSONObject.valueToString("42"));
    assertEquals("\"\"", JSONObject.valueToString(""));
    assertEquals("10", JSONObject.valueToString(10.0d));
    assertEquals("10", JSONObject.valueToString(10.0f));
    assertEquals("0.5", JSONObject.valueToString(0.5d));
    assertThrows(JSONException.class, () -> JSONObject.valueToString(Double.NaN));
    assertThrows(JSONException.class, () -> JSONObject.valueToString(Float.NaN));
    assertEquals("{}", JSONObject.valueToString(new JSONObject()));
    assertEquals("[]", JSONObject.valueToString(new JSONArray()));
    assertEquals("{}", JSONObject.valueToString(new HashMap<>()));
    assertEquals("[]", JSONObject.valueToString(new ArrayList<>()));
    assertEquals("{\"HTTP-Version\":\"https://example.org/example\",\"Status-Code\":\"\",\"Reason-Phrase\":\"\"}",
        JSONObject.valueToString(HTTP.toJSONObject("https://example.org/example")));
    assertEquals("null", JSONObject.valueToString(JSONObject.NULL, 3, 1));
    assertEquals("null", JSONObject.valueToString(null, 3, 0));
    assertEquals("42", JSONObject.valueToString(42, 3, 1));
    assertEquals("\"42\"", JSONObject.valueToString("42", 3, 1));
    assertEquals("\"\"", JSONObject.valueToString("", 3, 1));
    assertEquals("10", JSONObject.valueToString(10.0d, 3, 1));
    assertEquals("10", JSONObject.valueToString(10.0f, 3, 1));
    assertEquals("0.5", JSONObject.valueToString(0.5d, 3, 1));
    assertThrows(JSONException.class, () -> JSONObject.valueToString(Double.NaN, 3, 1));
    assertThrows(JSONException.class, () -> JSONObject.valueToString(Float.NaN, 3, 1));
    assertEquals("{}", JSONObject.valueToString(new JSONObject(), 3, 1));
    assertEquals("[]", JSONObject.valueToString(new JSONArray(), 3, 1));
    assertEquals("{}", JSONObject.valueToString(new HashMap<>(), 3, 1));
    assertEquals("[]", JSONObject.valueToString(new ArrayList<>(), 3, 1));
    assertEquals(
        "{\n" + "    \"HTTP-Version\": \"https://example.org/example\",\n" + "    \"Reason-Phrase\": \"\",\n"
            + "    \"Status-Code\": \"\"\n" + " }",
        JSONObject.valueToString(HTTP.toJSONObject("https://example.org/example"), 3, 1));
  }

  /**
   * Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  public void testValueToString2() throws JSONException {
    // Arrange and Act
    String actualValueToStringResult = JSONObject.valueToString(true);

    // Assert
    assertEquals(Boolean.TRUE.toString(), actualValueToStringResult);
  }

  /**
   * Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  public void testValueToString3() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("{", JSONObject.NULL);

    // Act and Assert
    assertEquals("{\"{\":[null]}", JSONObject.valueToString(jsonObject));
  }

  /**
   * Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  public void testValueToString4() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray();
    jsonArray.put(1, true);

    // Act and Assert
    assertEquals("[null,true]", JSONObject.valueToString(jsonArray));
  }

  /**
   * Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  public void testValueToString5() throws JSONException {
    // Arrange
    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put(JSONObject.NULL, JSONObject.NULL);

    // Act and Assert
    assertEquals("{\"null\":null}", JSONObject.valueToString(objectObjectMap));
  }

  /**
   * Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  public void testValueToString6() throws JSONException {
    // Arrange
    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));
    objectObjectMap.put(JSONObject.NULL, JSONObject.NULL);

    // Act and Assert
    assertEquals("{\"null\":null}", JSONObject.valueToString(objectObjectMap));
  }

  /**
   * Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  public void testValueToString7() throws JSONException {
    // Arrange
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(JSONObject.NULL);

    // Act and Assert
    assertEquals("[null]", JSONObject.valueToString(objectList));
  }

  /**
   * Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  public void testValueToString8() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = HTTP.toJSONObject("https://example.org/example");
    toJSONObjectResult.append("\"\"", JSONObject.NULL);

    // Act and Assert
    assertEquals(
        "{\"\\\"\\\"\":[null],\"HTTP-Version\":\"https://example.org/example\",\"Status-Code\":\"\",\"Reason-Phrase\":\"\"}",
        JSONObject.valueToString(toJSONObjectResult));
  }

  /**
   * Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  public void testValueToString9() throws JSONException {
    // Arrange and Act
    String actualValueToStringResult = JSONObject.valueToString(true, 3, 1);

    // Assert
    assertEquals(Boolean.TRUE.toString(), actualValueToStringResult);
  }

  /**
   * Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  public void testValueToString10() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("{}", JSONObject.NULL);

    // Act and Assert
    assertEquals("{\"{}\": [null]}", JSONObject.valueToString(jsonObject, 3, 1));
  }

  /**
   * Method under test: {@link JSONObject#wrap(Object)}
   */
  @Test
  public void testWrap() {
    // Arrange and Act
    Object actualWrapResult = JSONObject.wrap(new JSONObject());

    // Assert
    assertTrue(actualWrapResult instanceof JSONObject);
    assertEquals(0, ((JSONObject) actualWrapResult).length());
  }

  /**
   * Method under test: {@link JSONObject#wrap(Object)}
   */
  @Test
  public void testWrap2() {
    // Arrange and Act
    Object actualWrapResult = JSONObject.wrap(new JSONArray());

    // Assert
    assertTrue(actualWrapResult instanceof JSONArray);
    assertEquals(0, ((JSONArray) actualWrapResult).length());
  }

  /**
   * Method under test: {@link JSONObject#wrap(Object)}
   */
  @Test
  public void testWrap3() {
    // Arrange, Act and Assert
    assertEquals(10.0f, ((Float) JSONObject.wrap(10.0f)).floatValue(), 0.0f);
  }

  /**
   * Method under test: {@link JSONObject#wrap(Object)}
   */
  @Test
  public void testWrap4() {
    // Arrange, Act and Assert
    assertEquals(10.0d, ((Double) JSONObject.wrap(10.0d)).doubleValue(), 0.0);
  }

  /**
   * Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
  public void testWrite() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = toJSONObjectResult.write(writer);

    // Assert
    assertEquals("{\"name\":\"\",\"value\":\"\"}", writer.toString());
    assertEquals("{\"name\":\"\",\"value\":\"\"}", actualWriteResult.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
  public void testWrite2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = HTTP.toJSONObject("https://example.org/example");
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = toJSONObjectResult.write(writer);

    // Assert
    assertEquals("{\"HTTP-Version\":\"https://example.org/example\",\"Status-Code\":\"\",\"Reason-Phrase\":\"\"}",
        writer.toString());
    assertEquals("{\"HTTP-Version\":\"https://example.org/example\",\"Status-Code\":\"\",\"Reason-Phrase\":\"\"}",
        actualWriteResult.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
  public void testWrite3() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = toJSONObjectResult.write(writer);

    // Assert
    assertEquals("{\"name\":\"\",\"value\":\"\",\"Key\":[null]}", writer.toString());
    assertEquals("{\"name\":\"\",\"value\":\"\",\"Key\":[null]}", actualWriteResult.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
  public void testWrite4() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = toJSONObjectResult.write(writer);

    // Assert
    assertEquals("{\"name\":\"\",\"value\":\"\",\"Key\":1}", writer.toString());
    assertEquals("{\"name\":\"\",\"value\":\"\",\"Key\":1}", actualWriteResult.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
  public void testWrite5() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", false);
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = toJSONObjectResult.write(writer);

    // Assert
    assertEquals("{\"name\":\"\",\"value\":\"\",\"Key\":false}", writer.toString());
    assertEquals("{\"name\":\"\",\"value\":\"\",\"Key\":false}", actualWriteResult.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
  public void testWrite6() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", 0.5d);
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = toJSONObjectResult.write(writer);

    // Assert
    assertEquals("{\"name\":\"\",\"value\":\"\",\"Key\":0.5}", writer.toString());
    assertEquals("{\"name\":\"\",\"value\":\"\",\"Key\":0.5}", actualWriteResult.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
  public void testWrite7() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", (Map) new HashMap<>());
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = toJSONObjectResult.write(writer);

    // Assert
    assertEquals("{\"name\":\"\",\"value\":\"\",\"Key\":{}}", writer.toString());
    assertEquals("{\"name\":\"\",\"value\":\"\",\"Key\":{}}", actualWriteResult.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
  public void testWrite8() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);
    toJSONObjectResult.append("Key", JSONObject.NULL);
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = toJSONObjectResult.write(writer);

    // Assert
    assertEquals("{\"name\":\"\",\"value\":\"\",\"Key\":[null,null]}", writer.toString());
    assertEquals("{\"name\":\"\",\"value\":\"\",\"Key\":[null,null]}", actualWriteResult.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
  public void testWrite9() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("\"\"", JSONObject.NULL);
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = toJSONObjectResult.write(writer);

    // Assert
    assertEquals("{\"\\\"\\\"\":[null],\"name\":\"\",\"value\":\"\"}", writer.toString());
    assertEquals("{\"\\\"\\\"\":[null],\"name\":\"\",\"value\":\"\"}", actualWriteResult.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
  public void testWrite10() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("\"\"", 10.0d);
    toJSONObjectResult.increment("Key");
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = toJSONObjectResult.write(writer);

    // Assert
    assertEquals("{\"\\\"\\\"\":10,\"name\":\"\",\"value\":\"\",\"Key\":1}", writer.toString());
    assertEquals("{\"\\\"\\\"\":10,\"name\":\"\",\"value\":\"\",\"Key\":1}", actualWriteResult.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Method under test: {@link JSONObject#JSONObject()}
   */
  @Test
  public void testNewJSONObject() {
    // Arrange, Act and Assert
    assertEquals(0, (new JSONObject()).length());
    assertEquals(0, (new JSONObject(JSONObject.NULL)).length());
    assertEquals(0, (new JSONObject(1)).length());
    assertEquals(3, (new JSONObject((Object) "Bean")).length());
    assertEquals(0, (new JSONObject(JSONObject.NULL, new String[]{"Names"})).length());
    assertEquals(0, (new JSONObject((Map) new HashMap<>())).length());
    assertEquals(0, (new JSONObject(Cookie.toJSONObject("=;"), new String[]{"Names"})).length());
    assertEquals(0, (new JSONObject((JSONObject) null, new String[]{"Names"})).length());
    assertEquals(0, (new JSONObject(Cookie.toJSONObject("=;"), new String[]{null})).length());
  }

  /**
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  public void testNewJSONObject2() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, JSONObject.NULL);

    // Act and Assert
    assertEquals(1, (new JSONObject((Map) map)).length());
  }

  /**
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  public void testNewJSONObject3() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));
    map.put(JSONObject.NULL, JSONObject.NULL);

    // Act and Assert
    assertEquals(1, (new JSONObject((Map) map)).length());
  }

  /**
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  public void testNewJSONObject4() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, (byte) 'A');

    // Act and Assert
    assertEquals(1, (new JSONObject((Map) map)).length());
  }

  /**
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  public void testNewJSONObject5() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, true);

    // Act and Assert
    assertEquals(1, (new JSONObject((Map) map)).length());
  }

  /**
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  public void testNewJSONObject6() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, '\u0001');

    // Act and Assert
    assertEquals(1, (new JSONObject((Map) map)).length());
  }

  /**
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  public void testNewJSONObject7() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, (short) 1);

    // Act and Assert
    assertEquals(1, (new JSONObject((Map) map)).length());
  }

  /**
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  public void testNewJSONObject8() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, 1);

    // Act and Assert
    assertEquals(1, (new JSONObject((Map) map)).length());
  }

  /**
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  public void testNewJSONObject9() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, 1L);

    // Act and Assert
    assertEquals(1, (new JSONObject((Map) map)).length());
  }

  /**
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  public void testNewJSONObject10() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, new AbstractMap.SimpleEntry<>(JSONObject.NULL, JSONObject.NULL));

    // Act and Assert
    assertEquals(1, (new JSONObject((Map) map)).length());
  }

  /**
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  public void testNewJSONObject11() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, null);

    // Act and Assert
    assertEquals(1, (new JSONObject((Map) map)).length());
  }

  /**
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  public void testNewJSONObject12() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, new JSONObject());

    // Act and Assert
    assertEquals(1, (new JSONObject((Map) map)).length());
  }

  /**
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  public void testNewJSONObject13() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, new JSONArray());

    // Act and Assert
    assertEquals(1, (new JSONObject((Map) map)).length());
  }

  /**
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  public void testNewJSONObject14() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, 10.0f);

    // Act and Assert
    assertEquals(1, (new JSONObject((Map) map)).length());
  }
}
