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
package org.activiti.engine.impl.bpmn.diagram;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import java.util.Iterator;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class Bpmn20NamespaceContextDiffblueTest {
  @InjectMocks
  private Bpmn20NamespaceContext bpmn20NamespaceContext;

  /**
   * Method under test: {@link Bpmn20NamespaceContext#getNamespaceURI(String)}
   */
  @Test
  public void testGetNamespaceURI() {
    // Arrange, Act and Assert
    assertNull((new Bpmn20NamespaceContext()).getNamespaceURI("Prefix"));
  }

  /**
   * Method under test: {@link Bpmn20NamespaceContext#getPrefix(String)}
   */
  @Test
  public void testGetPrefix() {
    // Arrange, Act and Assert
    assertNull((new Bpmn20NamespaceContext()).getPrefix("Namespace URI"));
    assertEquals(Bpmn20NamespaceContext.BPMN,
        (new Bpmn20NamespaceContext()).getPrefix("http://www.omg.org/spec/BPMN/20100524/MODEL"));
  }

  /**
   * Method under test: {@link Bpmn20NamespaceContext#getPrefixes(String)}
   */
  @Test
  public void testGetPrefixes() {
    // Arrange, Act and Assert
    assertFalse((new Bpmn20NamespaceContext()).getPrefixes("Namespace URI").hasNext());
  }

  /**
   * Method under test: {@link Bpmn20NamespaceContext#getPrefixes(String)}
   */
  @Test
  public void testGetPrefixes2() {
    // Arrange and Act
    Iterator<String> actualPrefixes = (new Bpmn20NamespaceContext())
        .getPrefixes("http://www.omg.org/spec/BPMN/20100524/MODEL");

    // Assert
    String actualNextResult = actualPrefixes.next();
    assertFalse(actualPrefixes.hasNext());
    assertEquals(Bpmn20NamespaceContext.BPMN, actualNextResult);
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link Bpmn20NamespaceContext}
   */
  @Test
  public void testNewBpmn20NamespaceContext() {
    // Arrange, Act and Assert
    Map<String, String> stringStringMap = (new Bpmn20NamespaceContext()).namespaceUris;
    assertEquals(4, stringStringMap.size());
    assertEquals("http://www.omg.org/spec/BPMN/20100524/DI", stringStringMap.get(Bpmn20NamespaceContext.BPMNDI));
    assertEquals("http://www.omg.org/spec/BPMN/20100524/MODEL", stringStringMap.get(Bpmn20NamespaceContext.BPMN));
    assertEquals("http://www.omg.org/spec/DD/20100524/DC", stringStringMap.get(Bpmn20NamespaceContext.OMGDI));
    assertEquals("http://www.omg.org/spec/DD/20100524/DI", stringStringMap.get(Bpmn20NamespaceContext.OMGDC));
  }
}
