package ru.liga.deliveryservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.liga.common.entity.Status;
import ru.liga.common.exceptions.NoSuchEntityException;
import ru.liga.deliveryservice.DeliveryServiceApplication;
import ru.liga.deliveryservice.dto.DelieveryListDTO;
import ru.liga.deliveryservice.dto.DeliveryDTO;
import ru.liga.deliveryservice.service.DeliveryService;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DeliveryRestController.class)
@ContextConfiguration(classes = {DeliveryServiceApplication.class})
public class DeliveryRestControllerMockMvcUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private DeliveryService deliveryService;

    ///Test Take Method
    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testTakeDeliveryIfIdIsOk() {
        UUID testId = UUID.randomUUID();
        Mockito.when(deliveryService.setDeliveryStatus(ArgumentMatchers.any(UUID.class), eq(Status.DELIVERY_PICKING.toString()))).thenReturn(new DeliveryDTO());
        mockMvc.perform(post("/delivery/{id}/take", testId).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new DeliveryDTO())));
    }
    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testTakeDeliveryIfIdNotFound() {
        Mockito.when(deliveryService.setDeliveryStatus(ArgumentMatchers.any(UUID.class), eq(Status.DELIVERY_PICKING.toString()))).thenThrow(new NoSuchEntityException("test string"));
        mockMvc.perform(post("/delivery/{id}/take", UUID.randomUUID()).with(csrf()))
                .andExpect(status().is4xxClientError());
    }
    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testAcceptOrderIfIdIsntUUID() {
        Mockito.when(deliveryService.setDeliveryStatus(ArgumentMatchers.any(UUID.class), eq(Status.DELIVERY_PICKING.toString()))).thenReturn(new DeliveryDTO());
        mockMvc.perform(post("/delivery/{id}/take", "Some random string").with(csrf()))
                .andExpect(status().is4xxClientError());
    }
    ///Test Complete Method
    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testCompleteDeliveryIfIdIsOk() {
        Mockito.when(deliveryService.setDeliveryStatus(ArgumentMatchers.any(UUID.class), eq(Status.DELIVERY_COMPLETE.toString()))).thenReturn(new DeliveryDTO());
        mockMvc.perform(post("/delivery/{id}/complete", UUID.randomUUID()).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new DeliveryDTO())));
    }
    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testCompleteDeliveryIfIdNotFound() {
        Mockito.when(deliveryService.setDeliveryStatus(ArgumentMatchers.any(UUID.class), eq(Status.DELIVERY_COMPLETE.toString()))).thenThrow(new NoSuchEntityException("test string"));
        mockMvc.perform(post("/delivery/{id}/complete", UUID.randomUUID()).with(csrf()))
                .andExpect(status().is4xxClientError());
    }
    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testCompleteOrderIfIdIsntUUID() {
        Mockito.when(deliveryService.setDeliveryStatus(ArgumentMatchers.any(UUID.class), eq(Status.DELIVERY_COMPLETE.toString()))).thenReturn(new DeliveryDTO());
        mockMvc.perform(post("/delivery/{id}/complete", "Some random string").with(csrf()))
                .andExpect(status().is4xxClientError());
    }

}
