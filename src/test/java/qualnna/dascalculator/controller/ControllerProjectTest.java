package qualnna.dascalculator.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import qualnna.dascalculator.model.Employee;
import qualnna.dascalculator.service.ServiceProject;

import java.security.Provider;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        Employee alice = new Employee();
        alice.setEmployeeId(1);
        alice.setName("Alice");
        alice.setHourlyRate(500f);
        alice.setSkills(new ArrayList<>(List.of("Java", "SQL")));
        alice.setAssignedTasks(new ArrayList<>());
        when(serviceProject.fetchEmployee(1)).thenReturn(alice);
        when(serviceProject.getSkills()).thenReturn(List.of("Java", "SQL", "Python"));

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void deleteEmployee() throws Exception {
        mockMvc.perform(post("/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));


        // Checks that the service is called to delete Alice
        Mockito.verify(serviceProject).deleteEmployee(1);

    }

    @Test
    void employeeView() throws Exception {
        mockMvc.perform(get("/employee/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-employee"))
                .andExpect(model().attributeExists("employee"))
                .andExpect(model().attributeExists("skills"));

        Mockito.verify(serviceProject).fetchEmployee(1);
        Mockito.verify(serviceProject).getSkills();
    }

    @Test
    void updateEmployeeAction() throws Exception {
        mockMvc.perform(post("/employee/update")
                        .param("employeeId", "1")
                        .param("name", "Alice")
                        .param("hourlyRate", "500.0")
                        .param("skills", "Java", "SQL"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employees"));

        // Capturing the actual employee thats being sent through the mock form
        ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);
        verify(serviceProject).updateEmployee(captor.capture());

        Employee alice = captor.getValue();
        assertEquals(1, alice.getEmployeeId());
        assertEquals("Alice", alice.getName());
        assertEquals(500f, alice.getHourlyRate());
        // Comparing it as a set since order from the DB is not guaranteed.
        assertEquals(Set.of("Java", "SQL"), new HashSet<>(alice.getSkills()));
    }

    @Test
    void employeePage() throws Exception {
        mockMvc.perform(get("/employees"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("employees"))
                .andExpect(view().name("employee-page"));

        Mockito.verify(serviceProject).fetchEmployees();
    }
}