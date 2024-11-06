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
package org.activiti.engine.impl.bpmn.deployer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityImpl;
import org.activiti.engine.impl.persistence.entity.ResourceEntity;
import org.activiti.engine.impl.persistence.entity.ResourceEntityImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ParsedDeploymentBuilderDiffblueTest {
  @Mock
  private BpmnParser bpmnParser;

  @Mock
  private DeploymentEntity deploymentEntity;

  @Mock
  private Map<String, Object> map;

  @InjectMocks
  private ParsedDeploymentBuilder parsedDeploymentBuilder;

  /**
   * Test
   * {@link ParsedDeploymentBuilder#ParsedDeploymentBuilder(DeploymentEntity, BpmnParser, Map)}.
   * <p>
   * Method under test:
   * {@link ParsedDeploymentBuilder#ParsedDeploymentBuilder(DeploymentEntity, BpmnParser, Map)}
   */
  @Test
  public void testNewParsedDeploymentBuilder() {
    // Arrange
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    BpmnParser bpmnParser = new BpmnParser();

    // Act
    ParsedDeploymentBuilder actualParsedDeploymentBuilder = new ParsedDeploymentBuilder(deployment, bpmnParser,
        new HashMap<>());

    // Assert
    BpmnParser bpmnParser2 = actualParsedDeploymentBuilder.bpmnParser;
    assertNull(bpmnParser2.getBpmnParserHandlers());
    assertNull(bpmnParser2.getActivityBehaviorFactory());
    assertNull(bpmnParser2.getListenerFactory());
    assertNull(bpmnParser2.getBpmnParseFactory());
    assertTrue(actualParsedDeploymentBuilder.deploymentSettings.isEmpty());
  }

  /**
   * Test
   * {@link ParsedDeploymentBuilder#createBpmnParseFromResource(ResourceEntity)}.
   * <ul>
   *   <li>Then calls {@link BpmnParse#deployment(DeploymentEntity)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ParsedDeploymentBuilder#createBpmnParseFromResource(ResourceEntity)}
   */
  @Test
  public void testCreateBpmnParseFromResource_thenCallsDeployment() throws UnsupportedEncodingException {
    // Arrange
    BpmnParse bpmnParse = mock(BpmnParse.class);
    when(bpmnParse.execute()).thenReturn(new BpmnParse(new BpmnParser()));
    BpmnParse bpmnParse2 = mock(BpmnParse.class);
    when(bpmnParse2.name(Mockito.<String>any())).thenReturn(bpmnParse);
    BpmnParse bpmnParse3 = mock(BpmnParse.class);
    when(bpmnParse3.deployment(Mockito.<DeploymentEntity>any())).thenReturn(bpmnParse2);
    BpmnParse bpmnParse4 = mock(BpmnParse.class);
    when(bpmnParse4.setSourceSystemId(Mockito.<String>any())).thenReturn(bpmnParse3);
    BpmnParse bpmnParse5 = mock(BpmnParse.class);
    when(bpmnParse5.sourceInputStream(Mockito.<InputStream>any())).thenReturn(bpmnParse4);
    BpmnParser bpmnParser = mock(BpmnParser.class);
    when(bpmnParser.createParse()).thenReturn(bpmnParse5);
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    ParsedDeploymentBuilder parsedDeploymentBuilder = new ParsedDeploymentBuilder(deployment, bpmnParser,
        new HashMap<>());
    ResourceEntityImpl resource = mock(ResourceEntityImpl.class);
    when(resource.getBytes()).thenReturn("AXAXAXAX".getBytes("UTF-8"));
    when(resource.getName()).thenReturn("Name");

    // Act
    parsedDeploymentBuilder.createBpmnParseFromResource(resource);

    // Assert
    verify(bpmnParse3).deployment(isA(DeploymentEntity.class));
    verify(bpmnParse).execute();
    verify(bpmnParse2).name(eq("Name"));
    verify(bpmnParse4).setSourceSystemId(eq("Name"));
    verify(bpmnParse5).sourceInputStream(isA(InputStream.class));
    verify(bpmnParser).createParse();
    verify(resource).getBytes();
    verify(resource).getName();
  }

  /**
   * Test {@link ParsedDeploymentBuilder#isBpmnResource(String)}.
   * <p>
   * Method under test: {@link ParsedDeploymentBuilder#isBpmnResource(String)}
   */
  @Test
  public void testIsBpmnResource() {
    // Arrange, Act and Assert
    assertFalse(parsedDeploymentBuilder.isBpmnResource("Resource Name"));
  }
}
