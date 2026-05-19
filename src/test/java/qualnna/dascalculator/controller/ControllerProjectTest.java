package qualnna.dascalculator.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import qualnna.dascalculator.exceptions.InvalidDateException;
import qualnna.dascalculator.model.Project;
import qualnna.dascalculator.service.ServiceProject;

import java.sql.Date;
import java.time.LocalDate;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ControllerProject.class)
class ControllerProjectTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ServiceProject serviceProject;

    @Autowired
    private ControllerProject controllerProject;

    private MockHttpSession session;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addProject() throws Exception{
        mockMvc.perform(get("/addProject"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-project"));
    }

    @Test
    void addValidProjectPost() throws Exception{
        Project projectToAdd = new Project();
        projectToAdd.setName("test project name");
        projectToAdd.setStartdate(LocalDate.parse("2026-12-24"));
        projectToAdd.setDeadline(LocalDate.parse("2026-11-24"));
        mockMvc.perform(post("/addProject")
                .contentType("application/x-www-form-urlencoded")
                        .param("name", projectToAdd.getName())
                        .param("startdate", projectToAdd.getStartdate().toString())
                        .param("deadline", projectToAdd.getDeadline().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/show-project"));

        ArgumentCaptor<Project> captor = ArgumentCaptor.forClass(Project.class);
        verify(serviceProject).addProject(captor.capture());

        Project projectResult = captor.getValue();
        assertEquals(projectToAdd.getName(), projectResult.getName());
        assertEquals(projectToAdd.getStartdate(), projectResult.getStartdate());
        assertEquals(projectToAdd.getDeadline(), projectResult.getDeadline());

    }

    //Test should redirect back to add-project when InvalidDateException is thrown, but does not.
    @Test
    void addInvalidProjectPost() throws Exception{
        Project projectToAdd = new Project();
        projectToAdd.setName("test project name");
        projectToAdd.setStartdate(LocalDate.parse("2026-12-24"));
        projectToAdd.setDeadline(LocalDate.parse("2026-11-24"));
        when(serviceProject.addProject(projectToAdd)).thenThrow(InvalidDateException.class);
        mockMvc.perform(post("/addProject")
                        .contentType("application/x-www-form-urlencoded")
                        .param("name", projectToAdd.getName())
                        .param("startdate", projectToAdd.getStartdate().toString())
                        .param("deadline", projectToAdd.getDeadline().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/show-project"));

    }
}