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
package org.activiti.engine.impl.cmd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import org.junit.Test;

public class SaveProcessDefinitionInfoCmdDiffblueTest {
  /**
   * Test
   * {@link SaveProcessDefinitionInfoCmd#SaveProcessDefinitionInfoCmd(String, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link SaveProcessDefinitionInfoCmd#SaveProcessDefinitionInfoCmd(String, ObjectNode)}
   */
  @Test
  public void testNewSaveProcessDefinitionInfoCmd() {
    // Arrange, Act and Assert
    ObjectNode objectNode = (new SaveProcessDefinitionInfoCmd("42",
        new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)))).infoNode;
    assertTrue(objectNode.traverse() instanceof TreeTraversingParser);
    assertEquals("{ }", objectNode.toPrettyString());
    assertEquals(0, objectNode.size());
    assertEquals(JsonNodeType.OBJECT, objectNode.getNodeType());
    assertFalse(objectNode.isArray());
    assertFalse(objectNode.isBigDecimal());
    assertFalse(objectNode.isBigInteger());
    assertFalse(objectNode.isBinary());
    assertFalse(objectNode.isBoolean());
    assertFalse(objectNode.isDouble());
    assertFalse(objectNode.isFloat());
    assertFalse(objectNode.isFloatingPointNumber());
    assertFalse(objectNode.isInt());
    assertFalse(objectNode.isIntegralNumber());
    assertFalse(objectNode.isLong());
    assertFalse(objectNode.isMissingNode());
    assertFalse(objectNode.isNull());
    assertFalse(objectNode.isNumber());
    assertFalse(objectNode.isPojo());
    assertFalse(objectNode.isShort());
    assertFalse(objectNode.isTextual());
    assertFalse(objectNode.isValueNode());
    assertFalse(objectNode.iterator().hasNext());
    assertTrue(objectNode.isContainerNode());
    assertTrue(objectNode.isEmpty());
    assertTrue(objectNode.isObject());
  }
}
