package org.activiti.examples;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.payloads.StartProcessPayload;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.process.runtime.connector.Connector;
import org.activiti.api.runtime.model.impl.IntegrationContextImpl;
import org.activiti.api.runtime.model.impl.ProcessInstanceImpl;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.runtime.api.query.impl.PageImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DemoApplicationDiffblueTest {
  /**
   * Test {@link DemoApplication#processFile(String)}.
   * <ul>
   *   <li>Then return a string.</li>
   * </ul>
   * <p>
   * Method under test: {@link DemoApplication#processFile(String)}
   */
  @Test
  @DisplayName("Test processFile(String); then return a string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DemoApplication.processFile(String)"})
  void testProcessFile_thenReturnAString() {
    // Arrange
    ProcessRuntime processRuntime = mock(ProcessRuntime.class);
    when(processRuntime.start(Mockito.<StartProcessPayload>any())).thenReturn(new ProcessInstanceImpl());

    // Act
    String actualProcessFileResult = (new DemoApplication(processRuntime)).processFile("Not all who wander are lost");

    // Assert
    verify(processRuntime).start(isA(StartProcessPayload.class));
    assertEquals(
        ">>> Created Process Instance: ProcessInstance{id='null', name='null', processDefinitionId='null',"
            + " processDefinitionKey='null', parentId='null', initiator='null', startDate=null, completedDate=null,"
            + " businessKey='null', status=null, processDefinitionVersion='null', processDefinitionName='null'}",
        actualProcessFileResult);
  }

  /**
   * Test {@link DemoApplication#getProcessDefinition()}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link DemoApplication#getProcessDefinition()}
   */
  @Test
  @DisplayName("Test getProcessDefinition(); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List DemoApplication.getProcessDefinition()"})
  void testGetProcessDefinition_thenReturnEmpty() {
    // Arrange
    ProcessRuntime processRuntime = mock(ProcessRuntime.class);
    when(processRuntime.processDefinitions(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>(), 1000));

    // Act
    List<ProcessDefinition> actualProcessDefinition = (new DemoApplication(processRuntime)).getProcessDefinition();

    // Assert
    verify(processRuntime).processDefinitions(isA(Pageable.class));
    assertTrue(actualProcessDefinition.isEmpty());
  }

  /**
   * Test {@link DemoApplication#processTextConnector()}.
   * <p>
   * Method under test: {@link DemoApplication#processTextConnector()}
   */
  @Test
  @DisplayName("Test processTextConnector()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Connector DemoApplication.processTextConnector()"})
  void testProcessTextConnector() {
    // Arrange and Act
    Connector actualProcessTextConnectorResult = (new DemoApplication(null)).processTextConnector();
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    integrationContextImpl.addInBoundVariable("fileContent", "Value");

    // Assert
    assertSame(integrationContextImpl, actualProcessTextConnectorResult.apply(integrationContextImpl));
  }

  /**
   * Test {@link DemoApplication#tagTextConnector()}.
   * <p>
   * Method under test: {@link DemoApplication#tagTextConnector()}
   */
  @Test
  @DisplayName("Test tagTextConnector()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Connector DemoApplication.tagTextConnector()"})
  void testTagTextConnector() {
    // Arrange and Act
    Connector actualTagTextConnectorResult = (new DemoApplication(null)).tagTextConnector();
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();

    // Assert
    assertSame(integrationContextImpl, actualTagTextConnectorResult.apply(integrationContextImpl));
  }

  /**
   * Test {@link DemoApplication#discardTextConnector()}.
   * <p>
   * Method under test: {@link DemoApplication#discardTextConnector()}
   */
  @Test
  @DisplayName("Test discardTextConnector()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Connector DemoApplication.discardTextConnector()"})
  void testDiscardTextConnector() {
    // Arrange and Act
    Connector actualDiscardTextConnectorResult = (new DemoApplication(null)).discardTextConnector();
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();

    // Assert
    assertSame(integrationContextImpl, actualDiscardTextConnectorResult.apply(integrationContextImpl));
  }
}
