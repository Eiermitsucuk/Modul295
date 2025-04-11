package ch.halil.cankilic.gymprodukteapp;

import ch.halil.cankilic.gymprodukteapp.entity.Product;
import ch.halil.cankilic.gymprodukteapp.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllPublicProducts() throws Exception {
        mockMvc.perform(get("/api/public/products"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"admin"})
    public void testCreateProductValidation() throws Exception {
        // Test case: Missing name field
        Product invalidProduct = new Product();
        invalidProduct.setDescription("Test product without a name");
        invalidProduct.setPrice(10.0);

        String jsonInvalid = objectMapper.writeValueAsString(invalidProduct);

        mockMvc.perform(post("/api/admin/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInvalid))
                .andExpect(status().isBadRequest());

        // Test case: Negative price
        invalidProduct.setName("Invalid Product");
        invalidProduct.setPrice(-5.0);

        String jsonNegativePrice = objectMapper.writeValueAsString(invalidProduct);

        mockMvc.perform(post("/api/admin/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonNegativePrice))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"admin"})
    public void testCreateValidProduct() throws Exception {
        // Test case: Valid product
        Product validProduct = new Product();
        validProduct.setName("Valid Product");
        validProduct.setDescription("This is a valid product");
        validProduct.setPrice(15.0);

        String jsonValid = objectMapper.writeValueAsString(validProduct);

        mockMvc.perform(post("/api/admin/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonValid))
                .andExpect(status().isCreated());
    }
}