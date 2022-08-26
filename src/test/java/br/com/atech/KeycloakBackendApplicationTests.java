package br.com.atech;

import br.com.atech.model.User;
import br.com.atech.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles="role-admin")
class KeycloakBackendApplicationTests {

	@Autowired
	public MockMvc mockMvc;

	@MockBean
	private UserService userServiceMockBean;

	@Test
	void mustReturnAllRecords() throws Exception {
		List<User> userList = new ArrayList<>();
		when(userServiceMockBean.findAll()).thenReturn(userList);
		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get("/user");
		mockMvc.perform(mockHttpServletRequestBuilder).andExpect(status().isOk());
		verify(userServiceMockBean, times(1)).findAll();
	}

	@Test
	void mustSaveRecord() throws Exception {
		when(userServiceMockBean.save(any())).thenReturn(new User());
		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post("/user");
		mockHttpServletRequestBuilder.contentType(MediaType.APPLICATION_JSON);
		mockHttpServletRequestBuilder.content("{\"id\":123,\"name\":\"name\",\"email\":\"email\",\"password\":\"password\"}");
		mockMvc.perform(mockHttpServletRequestBuilder).andExpect(status().isOk());
		verify(userServiceMockBean, times(1)).save(any());
	}


	@Test
	void mustReturnRecord() throws Exception {
		Optional<User> user = Optional.of(new User());
		when(userServiceMockBean.findById(any())).thenReturn(user);
		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get("/user/{id}", 42L).param("id", "1");
		mockMvc.perform(mockHttpServletRequestBuilder).andExpect(status().isOk());
		verify(userServiceMockBean, times(1)).findById(any(Integer.class));
	}


	@Test
	void mustUpdateRecord() throws Exception {
		Optional<User> user = Optional.of(new User());
		when(userServiceMockBean.findById(any())).thenReturn(user);
		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = put("/user/{id}", 42L).param("id", "1");
		mockHttpServletRequestBuilder.contentType(MediaType.APPLICATION_JSON);
		mockHttpServletRequestBuilder.content("{\"id\":123,\"name\":\"name\",\"email\":\"email\",\"password\":\"password\"}");
		mockMvc.perform(mockHttpServletRequestBuilder).andExpect(status().isOk());
		verify(userServiceMockBean, times(1)).findById(any(Integer.class));
	}


	@Test
	void mustDeleteRecord() throws Exception {
		Optional<User> user = Optional.of(new User());
		when(userServiceMockBean.findById(any())).thenReturn(user);
		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = delete("/user/{id}", 42L).param("id", "1");
		mockMvc.perform(mockHttpServletRequestBuilder).andExpect(status().isOk());
		verify(userServiceMockBean, times(1)).findById(any(Integer.class));
		verify(userServiceMockBean, times(1)).delete(any(User.class));
	}

}
