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
package org.activiti.bpmn.converter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;
import org.junit.jupiter.api.Test;

class XMLStreamReaderUtilDiffblueTest {
  /**
   * Method under test: {@link XMLStreamReaderUtil#moveDown(XMLStreamReader)}
   */
  @Test
  void testMoveDown() {
    // Arrange, Act and Assert
    assertNull(XMLStreamReaderUtil.moveDown(new StreamReaderDelegate()));
  }

  /**
   * Method under test:
   * {@link XMLStreamReaderUtil#moveToEndOfElement(XMLStreamReader, String)}
   */
  @Test
  void testMoveToEndOfElement() {
    // Arrange, Act and Assert
    assertFalse(XMLStreamReaderUtil.moveToEndOfElement(new StreamReaderDelegate(), "Element Name"));
  }
}
