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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import java.io.UnsupportedEncodingException;
import org.junit.Test;

public class AddEditorSourceExtraForModelCmdDiffblueTest {
  /**
   * Test
   * {@link AddEditorSourceExtraForModelCmd#AddEditorSourceExtraForModelCmd(String, byte[])}.
   * <p>
   * Method under test:
   * {@link AddEditorSourceExtraForModelCmd#AddEditorSourceExtraForModelCmd(String, byte[])}
   */
  @Test
  public void testNewAddEditorSourceExtraForModelCmd() throws UnsupportedEncodingException {
    // Arrange and Act
    AddEditorSourceExtraForModelCmd actualAddEditorSourceExtraForModelCmd = new AddEditorSourceExtraForModelCmd("42",
        "AXAXAXAX".getBytes("UTF-8"));

    // Assert
    assertEquals("42", actualAddEditorSourceExtraForModelCmd.modelId);
    assertArrayEquals("AXAXAXAX".getBytes("UTF-8"), actualAddEditorSourceExtraForModelCmd.bytes);
  }
}
