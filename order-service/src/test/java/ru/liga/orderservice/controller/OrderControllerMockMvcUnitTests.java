package ru.liga.orderservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.liga.common.entity.Status;
import ru.liga.common.exceptions.NoSuchEntityException;
import ru.liga.orderservice.OrderServiceApplication;
import ru.liga.orderservice.dto.ChangeStatusDTO;
import ru.liga.orderservice.dto.FullOrderDTO;
import ru.liga.orderservice.service.OrderService;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderRestController.class)
@ContextConfiguration(classes = {OrderServiceApplication.class})
public class OrderControllerMockMvcUnitTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OrderService orderService;

    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testSetOrderStatusIfDTOIsOk() {
        ChangeStatusDTO testDTO = new ChangeStatusDTO();
        testDTO.setOrderAction(Status.DELIVERY_PENDING.toString());
        Mockito.when(orderService.setOrderStatus(Mockito.any(), Mockito.any())).thenReturn(new FullOrderDTO());
        mockMvc.perform(post("/order/status/{id}", 1).with(csrf()).content(getJsonFromObject(testDTO)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new FullOrderDTO())));
    }
    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testSetOrderStatusIfThereIsNoDTOSomeRandomString() {
        String testString = "test";
        Mockito.when(orderService.setOrderStatus(Mockito.any(), Mockito.any())).thenReturn(new FullOrderDTO());
        mockMvc.perform(post("/order/status/{id}", 1).content(getJsonFromObject(testString)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testSetOrderStatusIfDTOIsEmpty() {
        ChangeStatusDTO testDTO = new ChangeStatusDTO();
        testDTO.setOrderAction(null);
        Mockito.when(orderService.setOrderStatus(Mockito.any(), Mockito.any())).thenReturn(new FullOrderDTO());
        mockMvc.perform(post("/order/status/{id}", 1).content(getJsonFromObject(testDTO)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testSetOrderStatusIfDTOContainsWrongData() {
        @Data
        class TestChangeStatusDTO {
            private String orderAction;
        }
        TestChangeStatusDTO testDTO = new TestChangeStatusDTO();
        testDTO.setOrderAction("random");
        Mockito.when(orderService.setOrderStatus(Mockito.any(), Mockito.any())).thenReturn(new FullOrderDTO());
        mockMvc.perform(post("/order/status/{id}", 1).content(getJsonFromObject(testDTO)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testSetOrderStatusIfIdIsNotNumeric() {
        ChangeStatusDTO testDTO = new ChangeStatusDTO();
        testDTO.setOrderAction(Status.DELIVERY_PENDING.toString());
        Mockito.when(orderService.setOrderStatus(Mockito.any(), Mockito.any())).thenReturn(new FullOrderDTO());
        mockMvc.perform(post("/order/status/{id}", "testInfo").content(getJsonFromObject(testDTO)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testSetOrderStatusIfIdNotFound() {
        ChangeStatusDTO testDTO = new ChangeStatusDTO();
        testDTO.setOrderAction(Status.DELIVERY_PENDING.toString());
        Mockito.when(orderService.setOrderStatus(Mockito.any(), Mockito.any())).thenThrow(new NoSuchEntityException("test info"));
        mockMvc.perform(post("/order/status/{id}", 50).content(getJsonFromObject(testDTO)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }



    @SneakyThrows
    protected String getJsonFromObject(Object obj) {
        return objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(obj);
    }
}
