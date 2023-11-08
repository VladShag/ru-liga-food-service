package ru.liga.kitchenservice.controller;

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
import ru.liga.kitchenservice.KitchenServiceApplication;
import ru.liga.kitchenservice.controller.OrderController;
import ru.liga.kitchenservice.dto.FullOrderDTO;
import ru.liga.kitchenservice.service.OrderService;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
@ContextConfiguration(classes = {KitchenServiceApplication.class})
public class OrderControllerMockMvcUnitTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OrderService orderService;
    ///Test Accept Method
    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testAcceptOrderIfIdIsOk() {
        Mockito.when(orderService.setOrderStatus(ArgumentMatchers.any(UUID.class), eq(Status.KITCHEN_ACCEPTED.toString()))).thenReturn(new FullOrderDTO());
        mockMvc.perform(post("/kitchen/{id}/accept", UUID.randomUUID()).with(csrf()))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testAcceptOrderIfIdNotFound() {
        Mockito.when(orderService.setOrderStatus(ArgumentMatchers.any(UUID.class), eq(Status.KITCHEN_ACCEPTED.toString()))).thenThrow(new NoSuchEntityException("test string"));
        mockMvc.perform(post("/kitchen/{id}/accept", UUID.randomUUID()).with(csrf()))
                .andExpect(status().is4xxClientError());
    }
    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testAcceptOrderIfIdIsntUUID() {
        Mockito.when(orderService.setOrderStatus(ArgumentMatchers.any(UUID.class), eq(Status.KITCHEN_ACCEPTED.toString()))).thenReturn(new FullOrderDTO());
        mockMvc.perform(post("/kitchen/{id}/decline", "Some random string").with(csrf()))
                .andExpect(status().is4xxClientError());
    }

    ///Test Decline Method
    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testDeclineOrderIfIdIsOk() {
        Mockito.when(orderService.setOrderStatus(ArgumentMatchers.any(UUID.class), eq(Status.KITCHEN_ACCEPTED.toString()))).thenReturn(new FullOrderDTO());
        mockMvc.perform(post("/kitchen/{id}/decline", UUID.randomUUID()).with(csrf()))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testDeclineOrderIfIdIsntUUID() {
        Mockito.when(orderService.setOrderStatus(ArgumentMatchers.any(UUID.class), eq(Status.KITCHEN_ACCEPTED.toString()))).thenReturn(new FullOrderDTO());
        mockMvc.perform(post("/kitchen/{id}/decline", "Some random string").with(csrf()))
                .andExpect(status().is4xxClientError());
    }
    //Test Ready Method
    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testReadyOrderIfIdIsOk() {
        Mockito.when(orderService.setOrderStatus(ArgumentMatchers.any(UUID.class), eq(Status.KITCHEN_ACCEPTED.toString()))).thenReturn(new FullOrderDTO());
        mockMvc.perform(post("/kitchen/{id}/ready", UUID.randomUUID()).with(csrf()))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testReadyOrderIfIdIsntUUID() {
        Mockito.when(orderService.setOrderStatus(ArgumentMatchers.any(UUID.class), eq(Status.KITCHEN_ACCEPTED.toString()))).thenReturn(new FullOrderDTO());
        mockMvc.perform(post("/kitchen/{id}/ready", "Some random string").with(csrf()))
                .andExpect(status().is4xxClientError());
    }
}