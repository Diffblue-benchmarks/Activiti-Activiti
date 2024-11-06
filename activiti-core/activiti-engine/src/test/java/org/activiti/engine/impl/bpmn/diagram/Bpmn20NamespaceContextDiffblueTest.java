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
   * Test new {@link Bpmn20NamespaceContext} (default constructor).
   * <p>
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

  /**
   * Test {@link Bpmn20NamespaceContext#getNamespaceURI(String)}.
   * <p>
   * Method under test: {@link Bpmn20NamespaceContext#getNamespaceURI(String)}
   */
  @Test
  public void testGetNamespaceURI() {
    // Arrange, Act and Assert
    assertNull((new Bpmn20NamespaceContext()).getNamespaceURI("Prefix"));
  }

  /**
   * Test {@link Bpmn20NamespaceContext#getPrefix(String)}.
   * <ul>
   *   <li>When {@code http://www.omg.org/spec/BPMN/20100524/MODEL}.</li>
   *   <li>Then return {@link Bpmn20NamespaceContext#BPMN}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Bpmn20NamespaceContext#getPrefix(String)}
   */
  @Test
  public void testGetPrefix_whenHttpWwwOmgOrgSpecBpmn20100524Model_thenReturnBpmn() {
    // Arrange, Act and Assert
    assertEquals(Bpmn20NamespaceContext.BPMN,
        (new Bpmn20NamespaceContext()).getPrefix("http://www.omg.org/spec/BPMN/20100524/MODEL"));
  }

  /**
   * Test {@link Bpmn20NamespaceContext#getPrefix(String)}.
   * <ul>
   *   <li>When {@code Namespace URI}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Bpmn20NamespaceContext#getPrefix(String)}
   */
  @Test
  public void testGetPrefix_whenNamespaceUri_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new Bpmn20NamespaceContext()).getPrefix("Namespace URI"));
  }

  /**
   * Test {@link Bpmn20NamespaceContext#getPrefixes(String)}.
   * <ul>
   *   <li>When {@code http://www.omg.org/spec/BPMN/20100524/MODEL}.</li>
   *   <li>Then return next is {@link Bpmn20NamespaceContext#BPMN}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Bpmn20NamespaceContext#getPrefixes(String)}
   */
  @Test
  public void testGetPrefixes_whenHttpWwwOmgOrgSpecBpmn20100524Model_thenReturnNextIsBpmn() {
    // Arrange and Act
    Iterator<String> actualPrefixes = (new Bpmn20NamespaceContext())
        .getPrefixes("http://www.omg.org/spec/BPMN/20100524/MODEL");

    // Assert
    String actualNextResult = actualPrefixes.next();
    assertFalse(actualPrefixes.hasNext());
    assertEquals(Bpmn20NamespaceContext.BPMN, actualNextResult);
  }

  /**
   * Test {@link Bpmn20NamespaceContext#getPrefixes(String)}.
   * <ul>
   *   <li>When {@code Namespace URI}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Bpmn20NamespaceContext#getPrefixes(String)}
   */
  @Test
  public void testGetPrefixes_whenNamespaceUri() {
    // Arrange, Act and Assert
    assertFalse((new Bpmn20NamespaceContext()).getPrefixes("Namespace URI").hasNext());
  }
}
