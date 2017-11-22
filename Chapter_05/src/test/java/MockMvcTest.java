import org.melodicdeath.oauth2.controller.HomeController;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by zt.melody on 2017/11/1.
 */
public class MockMvcTest {

    MockMvc mockMvc;

    @BeforeClass
    public void before(){
        mockMvc= MockMvcBuilders.standaloneSetup(new HomeController()).build();
    }

    @Test
    public void testMockMvc() throws Exception {
        mockMvc.perform(get("/")).andExpect(view().name("home"));
    }
}
