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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

public class CreateAttachmentCmdDiffblueTest {
  /**
   * Test
   * {@link CreateAttachmentCmd#CreateAttachmentCmd(String, String, String, String, String, InputStream, String)}.
   * <p>
   * Method under test:
   * {@link CreateAttachmentCmd#CreateAttachmentCmd(String, String, String, String, String, InputStream, String)}
   */
  @Test
  public void testNewCreateAttachmentCmd() throws IOException {
    // Arrange, Act and Assert
    assertEquals(8,
        (new CreateAttachmentCmd("Attachment Type", "42", "42", "Attachment Name", "Attachment Description",
            new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")), "https://example.org/example")).content
            .read(new byte[8]));
  }
}
