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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
    private Employee alice;
    private List<Employee> employees = new ArrayList<>();

    @BeforeEach
    void setUp() {
        alice = new Employee();
        alice.setEmployeeID(1);
        alice.setEmployeeName("Alice");
        alice.setHourlyPayRate(500f);
        alice.setSkills(new ArrayList<>(List.of("Java", "SQL")));
        employees.add(alice);
        when(serviceProject.fetchEmployee(1)).thenReturn(alice);
        when(serviceProject.readSkills()).thenReturn(List.of("Java", "SQL", "Python"));

    }

    @AfterEach
    void tearDown() {
    }

//    @Test
//    void addProject() throws Exception{
//        this.session = new MockHttpSession();
//        session.setAttribute("skills", "");
//        session.setAttribute("employees", "");
//        mockMvc.perform(get("/addProject"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("add-project"));
//    }

//    @Test
//    void addValidProjectPost() throws Exception{
//        Project projectToAdd = new Project();
//        projectToAdd.setName("test project name");
//        projectToAdd.setStartdate(LocalDate.parse("2026-12-24"));
//        projectToAdd.setDeadline(LocalDate.parse("2026-11-24"));
//        mockMvc.perform(post("/addProject")
//                .contentType("application/x-www-form-urlencoded")
//                        .param("name", projectToAdd.getName())
//                        .param("startdate", projectToAdd.getStartdate().toString())
//                        .param("deadline", projectToAdd.getDeadline().toString()))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/show-project"));
//
//        ArgumentCaptor<Project> captor = ArgumentCaptor.forClass(Project.class);
//        verify(serviceProject).addProject(captor.capture());
//
//        Project projectResult = captor.getValue();
//        assertEquals(projectToAdd.getName(), projectResult.getName());
//        assertEquals(projectToAdd.getStartdate(), projectResult.getStartdate());
//        assertEquals(projectToAdd.getDeadline(), projectResult.getDeadline());
//
//    }

    //Test should redirect back to add-project when InvalidDateException is thrown, but does not.
//    @Test
//    void addInvalidProjectPost() throws Exception{
//        Project projectToAdd = new Project();
//        projectToAdd.setName("test project name");
//        projectToAdd.setStartdate(LocalDate.parse("2026-12-24"));
//        projectToAdd.setDeadline(LocalDate.parse("2026-11-24"));
//        when(serviceProject.addProject(projectToAdd)).thenThrow(InvalidDateException.class);
//        mockMvc.perform(post("/addProject")
//                        .contentType("application/x-www-form-urlencoded")
//                        .param("name", projectToAdd.getName())
//                        .param("startdate", projectToAdd.getStartdate().toString())
//                        .param("deadline", projectToAdd.getDeadline().toString()))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/show-project"));
//
//    }

    @Test
    void deleteEmployee() throws Exception {
        mockMvc.perform(post("/employee/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employees"));


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
        Mockito.verify(serviceProject).readSkills();
    }

    @Test
    void updateEmployeeAction() throws Exception {
        mockMvc.perform(post("/employee/update")
                        .param("employeeID", "1")
                        .param("employeeName", "Alice")
                        .param("hourlyPayRate", "500.0")
                        .param("skills", "Java", "SQL"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employees"));

        // Capturing the actual employee thats being sent through the mock form
        ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);
        verify(serviceProject).updateEmployee(captor.capture());

        Employee alice = captor.getValue();
        assertEquals(1, alice.getEmployeeID());
        assertEquals("Alice", alice.getEmployeeName());
        assertEquals(500f, alice.getHourlyPayRate());
        // Comparing it as a set since order from the DB is not guaranteed.
        assertEquals(Set.of("Java", "SQL"), new HashSet<>(alice.getSkills()));
    }

    //Test should redirect back to add-project when InvalidDateException is thrown, but does not.
    @Test
    void employeePage() throws Exception {
        List<Employee> employees = new ArrayList<>();
        mockMvc.perform(get("/employees"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(request().sessionAttribute("employees", employees))
                .andExpect(view().name("employee-page"));

        Mockito.verify(serviceProject).readEmployees();
    }

    @Test
    void addAssignmentGet() throws Exception{
        mockMvc.perform(get("/assignEmployee/1/10"))
                .andExpect(status().isOk())
                .andExpect(view().name("assign-employee"));
    }


}