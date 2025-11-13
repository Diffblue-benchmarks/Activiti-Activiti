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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.cfg.BpmnParseFactory;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityImpl;
import org.activiti.engine.impl.persistence.entity.ResourceEntity;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class ParsedDeploymentBuilderDiffblueTest {
  /**
   * Test {@link ParsedDeploymentBuilder#ParsedDeploymentBuilder(DeploymentEntity, BpmnParser,
   * Map)}.
   *
   * <p>Method under test: {@link ParsedDeploymentBuilder#ParsedDeploymentBuilder(DeploymentEntity,
   * BpmnParser, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ParsedDeploymentBuilder.<init>(DeploymentEntity, BpmnParser, Map)",
    "org.activiti.engine.impl.bpmn.deployer.ParsedDeployment ParsedDeploymentBuilder.build()"
  })
  public void testNewParsedDeploymentBuilder() {
    // Arrange
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    BpmnParser bpmnParser = new BpmnParser();

    // Act
    ParsedDeploymentBuilder actualParsedDeploymentBuilder =
        new ParsedDeploymentBuilder(deployment, bpmnParser, new HashMap<>());

    // Assert
    BpmnParser bpmnParser2 = actualParsedDeploymentBuilder.bpmnParser;
    assertNull(bpmnParser2.getBpmnParserHandlers());
    assertNull(bpmnParser2.getActivityBehaviorFactory());
    assertNull(bpmnParser2.getListenerFactory());
    assertNull(bpmnParser2.getBpmnParseFactory());
    assertTrue(actualParsedDeploymentBuilder.deploymentSettings.isEmpty());
  }

  /**
   * Test {@link ParsedDeploymentBuilder#createBpmnParseFromResource(ResourceEntity)}.
   *
   * <ul>
   *   <li>Then calls {@link BpmnParse#deployment(DeploymentEntity)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ParsedDeploymentBuilder#createBpmnParseFromResource(ResourceEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BpmnParse ParsedDeploymentBuilder.createBpmnParseFromResource(ResourceEntity)"
  })
  public void testCreateBpmnParseFromResource_thenCallsDeployment()
      throws UnsupportedEncodingException {
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

    BpmnParseFactory bpmnParseFactory = mock(BpmnParseFactory.class);
    when(bpmnParseFactory.createBpmnParse(Mockito.<BpmnParser>any())).thenReturn(bpmnParse5);

    BpmnParser bpmnParser = new BpmnParser();
    bpmnParser.setBpmnParseFactory(bpmnParseFactory);
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();

    ParsedDeploymentBuilder parsedDeploymentBuilder =
        new ParsedDeploymentBuilder(deployment, bpmnParser, new HashMap<>());

    ResourceEntity resource = mock(ResourceEntity.class);
    when(resource.getBytes()).thenReturn("AXAXAXAX".getBytes("UTF-8"));
    when(resource.getName()).thenReturn("Name");

    // Act
    parsedDeploymentBuilder.createBpmnParseFromResource(resource);

    // Assert
    verify(bpmnParse3).deployment(isA(DeploymentEntity.class));
    verify(bpmnParse).execute();
    verify(bpmnParse2).name("Name");
    verify(bpmnParse4).setSourceSystemId("Name");
    verify(bpmnParse5).sourceInputStream(isA(InputStream.class));
    verify(bpmnParseFactory).createBpmnParse(isA(BpmnParser.class));
    verify(resource).getBytes();
    verify(resource).getName();
  }

  /**
   * Test {@link ParsedDeploymentBuilder#isBpmnResource(String)}.
   *
   * <p>Method under test: {@link ParsedDeploymentBuilder#isBpmnResource(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ParsedDeploymentBuilder.isBpmnResource(String)"})
  public void testIsBpmnResource() {
    // Arrange
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    BpmnParser bpmnParser = new BpmnParser();

    ParsedDeploymentBuilder parsedDeploymentBuilder =
        new ParsedDeploymentBuilder(deployment, bpmnParser, new HashMap<>());

    // Act and Assert
    assertFalse(parsedDeploymentBuilder.isBpmnResource("Resource Name"));
  }
}
