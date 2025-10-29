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

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.history.DefaultHistoryManager;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.persistence.entity.data.CommentDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisCommentDataManager;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Event;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CommentEntityManagerImplDiffblueTest {
  @Mock
  private CommentDataManager commentDataManager;

  @InjectMocks
  private CommentEntityManagerImpl commentEntityManagerImpl;

  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Method under test: {@link CommentEntityManagerImpl#insert(CommentEntity)}
   */
  @Test
  public void testInsert() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration
        .setHistoryManager(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    CommentEntityManagerImpl commentEntityManagerImpl = new CommentEntityManagerImpl(processEngineConfiguration,
        new MybatisCommentDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.insert(new CommentEntityImpl()));
  }

  /**
   * Method under test:
   * {@link CommentEntityManagerImpl#findCommentsByTaskId(String)}
   */
  @Test
  public void testFindCommentsByTaskId() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.findCommentsByTaskId("42"));
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Method under test:
   * {@link CommentEntityManagerImpl#findCommentsByTaskId(String)}
   */
  @Test
  public void testFindCommentsByTaskId2() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    ArrayList<Comment> commentList = new ArrayList<>();
    when(commentDataManager.findCommentsByTaskId(Mockito.<String>any())).thenReturn(commentList);

    // Act
    List<Comment> actualFindCommentsByTaskIdResult = commentEntityManagerImpl.findCommentsByTaskId("42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).findCommentsByTaskId(eq("42"));
    assertTrue(actualFindCommentsByTaskIdResult.isEmpty());
    assertSame(commentList, actualFindCommentsByTaskIdResult);
  }

  /**
   * Method under test:
   * {@link CommentEntityManagerImpl#findCommentsByTaskId(String)}
   */
  @Test
  public void testFindCommentsByTaskId3() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    when(commentDataManager.findCommentsByTaskId(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.findCommentsByTaskId("42"));
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).findCommentsByTaskId(eq("42"));
  }

  /**
   * Method under test:
   * {@link CommentEntityManagerImpl#findCommentsByTaskIdAndType(String, String)}
   */
  @Test
  public void testFindCommentsByTaskIdAndType() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.findCommentsByTaskIdAndType("42", "Type"));
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Method under test:
   * {@link CommentEntityManagerImpl#findCommentsByTaskIdAndType(String, String)}
   */
  @Test
  public void testFindCommentsByTaskIdAndType2() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    ArrayList<Comment> commentList = new ArrayList<>();
    when(commentDataManager.findCommentsByTaskIdAndType(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(commentList);

    // Act
    List<Comment> actualFindCommentsByTaskIdAndTypeResult = commentEntityManagerImpl.findCommentsByTaskIdAndType("42",
        "Type");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).findCommentsByTaskIdAndType(eq("42"), eq("Type"));
    assertTrue(actualFindCommentsByTaskIdAndTypeResult.isEmpty());
    assertSame(commentList, actualFindCommentsByTaskIdAndTypeResult);
  }

  /**
   * Method under test:
   * {@link CommentEntityManagerImpl#findCommentsByTaskIdAndType(String, String)}
   */
  @Test
  public void testFindCommentsByTaskIdAndType3() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    when(commentDataManager.findCommentsByTaskIdAndType(Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.findCommentsByTaskIdAndType("42", "Type"));
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).findCommentsByTaskIdAndType(eq("42"), eq("Type"));
  }

  /**
   * Method under test:
   * {@link CommentEntityManagerImpl#findCommentsByType(String)}
   */
  @Test
  public void testFindCommentsByType() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.findCommentsByType("Type"));
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Method under test:
   * {@link CommentEntityManagerImpl#findCommentsByType(String)}
   */
  @Test
  public void testFindCommentsByType2() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    ArrayList<Comment> commentList = new ArrayList<>();
    when(commentDataManager.findCommentsByType(Mockito.<String>any())).thenReturn(commentList);

    // Act
    List<Comment> actualFindCommentsByTypeResult = commentEntityManagerImpl.findCommentsByType("Type");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).findCommentsByType(eq("Type"));
    assertTrue(actualFindCommentsByTypeResult.isEmpty());
    assertSame(commentList, actualFindCommentsByTypeResult);
  }

  /**
   * Method under test:
   * {@link CommentEntityManagerImpl#findCommentsByType(String)}
   */
  @Test
  public void testFindCommentsByType3() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    when(commentDataManager.findCommentsByType(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.findCommentsByType("Type"));
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).findCommentsByType(eq("Type"));
  }

  /**
   * Method under test:
   * {@link CommentEntityManagerImpl#findEventsByTaskId(String)}
   */
  @Test
  public void testFindEventsByTaskId() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.findEventsByTaskId("42"));
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Method under test:
   * {@link CommentEntityManagerImpl#findEventsByTaskId(String)}
   */
  @Test
  public void testFindEventsByTaskId2() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    ArrayList<Event> eventList = new ArrayList<>();
    when(commentDataManager.findEventsByTaskId(Mockito.<String>any())).thenReturn(eventList);

    // Act
    List<Event> actualFindEventsByTaskIdResult = commentEntityManagerImpl.findEventsByTaskId("42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).findEventsByTaskId(eq("42"));
    assertTrue(actualFindEventsByTaskIdResult.isEmpty());
    assertSame(eventList, actualFindEventsByTaskIdResult);
  }

  /**
   * Method under test:
   * {@link CommentEntityManagerImpl#findEventsByTaskId(String)}
   */
  @Test
  public void testFindEventsByTaskId3() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    when(commentDataManager.findEventsByTaskId(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.findEventsByTaskId("42"));
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).findEventsByTaskId(eq("42"));
  }

  /**
   * Method under test:
   * {@link CommentEntityManagerImpl#findEventsByProcessInstanceId(String)}
   */
  @Test
  public void testFindEventsByProcessInstanceId() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.findEventsByProcessInstanceId("42"));
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Method under test:
   * {@link CommentEntityManagerImpl#findEventsByProcessInstanceId(String)}
   */
  @Test
  public void testFindEventsByProcessInstanceId2() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    ArrayList<Event> eventList = new ArrayList<>();
    when(commentDataManager.findEventsByProcessInstanceId(Mockito.<String>any())).thenReturn(eventList);

    // Act
    List<Event> actualFindEventsByProcessInstanceIdResult = commentEntityManagerImpl
        .findEventsByProcessInstanceId("42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).findEventsByProcessInstanceId(eq("42"));
    assertTrue(actualFindEventsByProcessInstanceIdResult.isEmpty());
    assertSame(eventList, actualFindEventsByProcessInstanceIdResult);
  }

  /**
   * Method under test:
   * {@link CommentEntityManagerImpl#findEventsByProcessInstanceId(String)}
   */
  @Test
  public void testFindEventsByProcessInstanceId3() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    when(commentDataManager.findEventsByProcessInstanceId(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.findEventsByProcessInstanceId("42"));
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).findEventsByProcessInstanceId(eq("42"));
  }

  /**
   * Method under test:
   * {@link CommentEntityManagerImpl#deleteCommentsByTaskId(String)}
   */
  @Test
  public void testDeleteCommentsByTaskId() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.deleteCommentsByTaskId("42"));
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Method under test:
   * {@link CommentEntityManagerImpl#deleteCommentsByTaskId(String)}
   */
  @Test
  public void testDeleteCommentsByTaskId2() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    doNothing().when(commentDataManager).deleteCommentsByTaskId(Mockito.<String>any());

    // Act
    commentEntityManagerImpl.deleteCommentsByTaskId("42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).deleteCommentsByTaskId(eq("42"));
  }

  /**
   * Method under test:
   * {@link CommentEntityManagerImpl#deleteCommentsByTaskId(String)}
   */
  @Test
  public void testDeleteCommentsByTaskId3() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    doThrow(new ActivitiException("An error occurred")).when(commentDataManager)
        .deleteCommentsByTaskId(Mockito.<String>any());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.deleteCommentsByTaskId("42"));
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).deleteCommentsByTaskId(eq("42"));
  }

  /**
   * Method under test:
   * {@link CommentEntityManagerImpl#deleteCommentsByProcessInstanceId(String)}
   */
  @Test
  public void testDeleteCommentsByProcessInstanceId() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.deleteCommentsByProcessInstanceId("42"));
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Method under test:
   * {@link CommentEntityManagerImpl#deleteCommentsByProcessInstanceId(String)}
   */
  @Test
  public void testDeleteCommentsByProcessInstanceId2() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    doNothing().when(commentDataManager).deleteCommentsByProcessInstanceId(Mockito.<String>any());

    // Act
    commentEntityManagerImpl.deleteCommentsByProcessInstanceId("42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).deleteCommentsByProcessInstanceId(eq("42"));
  }

  /**
   * Method under test:
   * {@link CommentEntityManagerImpl#deleteCommentsByProcessInstanceId(String)}
   */
  @Test
  public void testDeleteCommentsByProcessInstanceId3() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    doThrow(new ActivitiException("An error occurred")).when(commentDataManager)
        .deleteCommentsByProcessInstanceId(Mockito.<String>any());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.deleteCommentsByProcessInstanceId("42"));
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).deleteCommentsByProcessInstanceId(eq("42"));
  }

  /**
   * Method under test:
   * {@link CommentEntityManagerImpl#findCommentsByProcessInstanceId(String)}
   */
  @Test
  public void testFindCommentsByProcessInstanceId() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.findCommentsByProcessInstanceId("42"));
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Method under test:
   * {@link CommentEntityManagerImpl#findCommentsByProcessInstanceId(String)}
   */
  @Test
  public void testFindCommentsByProcessInstanceId2() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    ArrayList<Comment> commentList = new ArrayList<>();
    when(commentDataManager.findCommentsByProcessInstanceId(Mockito.<String>any())).thenReturn(commentList);

    // Act
    List<Comment> actualFindCommentsByProcessInstanceIdResult = commentEntityManagerImpl
        .findCommentsByProcessInstanceId("42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).findCommentsByProcessInstanceId(eq("42"));
    assertTrue(actualFindCommentsByProcessInstanceIdResult.isEmpty());
    assertSame(commentList, actualFindCommentsByProcessInstanceIdResult);
  }

  /**
   * Method under test:
   * {@link CommentEntityManagerImpl#findCommentsByProcessInstanceId(String)}
   */
  @Test
  public void testFindCommentsByProcessInstanceId3() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    when(commentDataManager.findCommentsByProcessInstanceId(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.findCommentsByProcessInstanceId("42"));
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).findCommentsByProcessInstanceId(eq("42"));
  }

  /**
   * Method under test:
   * {@link CommentEntityManagerImpl#findCommentsByProcessInstanceId(String, String)}
   */
  @Test
  public void testFindCommentsByProcessInstanceId4() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.findCommentsByProcessInstanceId("42", "Type"));
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Method under test:
   * {@link CommentEntityManagerImpl#findCommentsByProcessInstanceId(String, String)}
   */
  @Test
  public void testFindCommentsByProcessInstanceId5() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    ArrayList<Comment> commentList = new ArrayList<>();
    when(commentDataManager.findCommentsByProcessInstanceId(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(commentList);

    // Act
    List<Comment> actualFindCommentsByProcessInstanceIdResult = commentEntityManagerImpl
        .findCommentsByProcessInstanceId("42", "Type");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).findCommentsByProcessInstanceId(eq("42"), eq("Type"));
    assertTrue(actualFindCommentsByProcessInstanceIdResult.isEmpty());
    assertSame(commentList, actualFindCommentsByProcessInstanceIdResult);
  }

  /**
   * Method under test:
   * {@link CommentEntityManagerImpl#findCommentsByProcessInstanceId(String, String)}
   */
  @Test
  public void testFindCommentsByProcessInstanceId6() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    when(commentDataManager.findCommentsByProcessInstanceId(Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.findCommentsByProcessInstanceId("42", "Type"));
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).findCommentsByProcessInstanceId(eq("42"), eq("Type"));
  }

  /**
   * Method under test: {@link CommentEntityManagerImpl#findComment(String)}
   */
  @Test
  public void testFindComment() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();
    when(commentDataManager.findComment(Mockito.<String>any())).thenReturn(commentEntityImpl);

    // Act
    Comment actualFindCommentResult = commentEntityManagerImpl.findComment("42");

    // Assert
    verify(commentDataManager).findComment(eq("42"));
    assertSame(commentEntityImpl, actualFindCommentResult);
  }

  /**
   * Method under test: {@link CommentEntityManagerImpl#findComment(String)}
   */
  @Test
  public void testFindComment2() {
    // Arrange
    when(commentDataManager.findComment(Mockito.<String>any())).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.findComment("42"));
    verify(commentDataManager).findComment(eq("42"));
  }

  /**
   * Method under test: {@link CommentEntityManagerImpl#findEvent(String)}
   */
  @Test
  public void testFindEvent() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();
    when(commentDataManager.findEvent(Mockito.<String>any())).thenReturn(commentEntityImpl);

    // Act
    Event actualFindEventResult = commentEntityManagerImpl.findEvent("42");

    // Assert
    verify(commentDataManager).findEvent(eq("42"));
    assertSame(commentEntityImpl, actualFindEventResult);
  }

  /**
   * Method under test: {@link CommentEntityManagerImpl#findEvent(String)}
   */
  @Test
  public void testFindEvent2() {
    // Arrange
    when(commentDataManager.findEvent(Mockito.<String>any())).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.findEvent("42"));
    verify(commentDataManager).findEvent(eq("42"));
  }

  /**
   * Method under test: {@link CommentEntityManagerImpl#delete(CommentEntity)}
   */
  @Test
  public void testDelete() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration
        .setHistoryManager(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    CommentEntityManagerImpl commentEntityManagerImpl = new CommentEntityManagerImpl(processEngineConfiguration,
        new MybatisCommentDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.delete(new CommentEntityImpl()));
  }

  /**
   * Method under test: {@link CommentEntityManagerImpl#checkHistoryEnabled()}
   */
  @Test
  public void testCheckHistoryEnabled() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration
        .setHistoryManager(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> (new CommentEntityManagerImpl(processEngineConfiguration,
        new MybatisCommentDataManager(new JtaProcessEngineConfiguration()))).checkHistoryEnabled());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link CommentEntityManagerImpl#CommentEntityManagerImpl(ProcessEngineConfigurationImpl, CommentDataManager)}
   *   <li>
   * {@link CommentEntityManagerImpl#setCommentDataManager(CommentDataManager)}
   *   <li>{@link CommentEntityManagerImpl#getCommentDataManager()}
   *   <li>{@link CommentEntityManagerImpl#getDataManager()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    CommentEntityManagerImpl actualCommentEntityManagerImpl = new CommentEntityManagerImpl(processEngineConfiguration,
        new MybatisCommentDataManager(new JtaProcessEngineConfiguration()));
    MybatisCommentDataManager commentDataManager = new MybatisCommentDataManager(new JtaProcessEngineConfiguration());
    actualCommentEntityManagerImpl.setCommentDataManager(commentDataManager);
    CommentDataManager actualCommentDataManager = actualCommentEntityManagerImpl.getCommentDataManager();

    // Assert that nothing has changed
    assertSame(commentDataManager, actualCommentDataManager);
    assertSame(commentDataManager, actualCommentEntityManagerImpl.getDataManager());
  }
}
