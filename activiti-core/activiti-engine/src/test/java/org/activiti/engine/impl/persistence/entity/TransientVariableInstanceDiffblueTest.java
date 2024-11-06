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
package org.activiti.engine.impl.persistence.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import java.io.UnsupportedEncodingException;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class TransientVariableInstanceDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link TransientVariableInstance#TransientVariableInstance(String, Object)}
   *   <li>{@link TransientVariableInstance#setValue(Object)}
   *   <li>{@link TransientVariableInstance#setBytes(byte[])}
   *   <li>{@link TransientVariableInstance#setCachedValue(Object)}
   *   <li>{@link TransientVariableInstance#setDeleted(boolean)}
   *   <li>{@link TransientVariableInstance#setDoubleValue(Double)}
   *   <li>{@link TransientVariableInstance#setExecutionId(String)}
   *   <li>{@link TransientVariableInstance#setId(String)}
   *   <li>{@link TransientVariableInstance#setInserted(boolean)}
   *   <li>{@link TransientVariableInstance#setLongValue(Long)}
   *   <li>{@link TransientVariableInstance#setName(String)}
   *   <li>{@link TransientVariableInstance#setProcessInstanceId(String)}
   *   <li>{@link TransientVariableInstance#setRevision(int)}
   *   <li>{@link TransientVariableInstance#setTaskId(String)}
   *   <li>{@link TransientVariableInstance#setTextValue2(String)}
   *   <li>{@link TransientVariableInstance#setTextValue(String)}
   *   <li>{@link TransientVariableInstance#setTypeName(String)}
   *   <li>{@link TransientVariableInstance#setUpdated(boolean)}
   *   <li>{@link TransientVariableInstance#getBytes()}
   *   <li>{@link TransientVariableInstance#getCachedValue()}
   *   <li>{@link TransientVariableInstance#getDoubleValue()}
   *   <li>{@link TransientVariableInstance#getExecutionId()}
   *   <li>{@link TransientVariableInstance#getId()}
   *   <li>{@link TransientVariableInstance#getLongValue()}
   *   <li>{@link TransientVariableInstance#getName()}
   *   <li>{@link TransientVariableInstance#getPersistentState()}
   *   <li>{@link TransientVariableInstance#getProcessInstanceId()}
   *   <li>{@link TransientVariableInstance#getRevision()}
   *   <li>{@link TransientVariableInstance#getRevisionNext()}
   *   <li>{@link TransientVariableInstance#getTaskId()}
   *   <li>{@link TransientVariableInstance#getTextValue()}
   *   <li>{@link TransientVariableInstance#getTextValue2()}
   *   <li>{@link TransientVariableInstance#getTypeName()}
   *   <li>{@link TransientVariableInstance#getValue()}
   *   <li>{@link TransientVariableInstance#isDeleted()}
   *   <li>{@link TransientVariableInstance#isInserted()}
   *   <li>{@link TransientVariableInstance#isUpdated()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() throws UnsupportedEncodingException {
    // Arrange and Act
    TransientVariableInstance actualTransientVariableInstance = new TransientVariableInstance("Variable Name",
        JSONObject.NULL);
    actualTransientVariableInstance.setValue(JSONObject.NULL);
    actualTransientVariableInstance.setBytes("AXAXAXAX".getBytes("UTF-8"));
    Object object = JSONObject.NULL;
    actualTransientVariableInstance.setCachedValue(object);
    actualTransientVariableInstance.setDeleted(true);
    actualTransientVariableInstance.setDoubleValue(10.0d);
    actualTransientVariableInstance.setExecutionId("42");
    actualTransientVariableInstance.setId("42");
    actualTransientVariableInstance.setInserted(true);
    actualTransientVariableInstance.setLongValue(42L);
    actualTransientVariableInstance.setName("Name");
    actualTransientVariableInstance.setProcessInstanceId("42");
    actualTransientVariableInstance.setRevision(1);
    actualTransientVariableInstance.setTaskId("42");
    actualTransientVariableInstance.setTextValue2("42");
    actualTransientVariableInstance.setTextValue("42");
    actualTransientVariableInstance.setTypeName("Type Name");
    actualTransientVariableInstance.setUpdated(true);
    actualTransientVariableInstance.getBytes();
    actualTransientVariableInstance.getCachedValue();
    actualTransientVariableInstance.getDoubleValue();
    actualTransientVariableInstance.getExecutionId();
    actualTransientVariableInstance.getId();
    actualTransientVariableInstance.getLongValue();
    String actualName = actualTransientVariableInstance.getName();
    actualTransientVariableInstance.getPersistentState();
    actualTransientVariableInstance.getProcessInstanceId();
    int actualRevision = actualTransientVariableInstance.getRevision();
    int actualRevisionNext = actualTransientVariableInstance.getRevisionNext();
    actualTransientVariableInstance.getTaskId();
    actualTransientVariableInstance.getTextValue();
    actualTransientVariableInstance.getTextValue2();
    String actualTypeName = actualTransientVariableInstance.getTypeName();
    Object actualValue = actualTransientVariableInstance.getValue();
    boolean actualIsDeletedResult = actualTransientVariableInstance.isDeleted();
    boolean actualIsInsertedResult = actualTransientVariableInstance.isInserted();

    // Assert that nothing has changed
    assertEquals("Variable Name", actualName);
    assertEquals("transient", actualTypeName);
    assertEquals(0, actualRevision);
    assertEquals(0, actualRevisionNext);
    assertFalse(actualIsDeletedResult);
    assertFalse(actualIsInsertedResult);
    assertFalse(actualTransientVariableInstance.isUpdated());
    assertSame(object, actualValue);
  }
}
