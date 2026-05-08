package qualnna.dascalculator.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import qualnna.dascalculator.service.ServiceProject;

import static org.junit.jupiter.api.Assertions.*;

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
}