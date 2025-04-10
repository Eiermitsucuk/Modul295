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

    // Use @WithMockUser to simulate an ADMIN user so that authentication passes
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testCreateProductValidation() throws Exception {
        Product product = new Product();
        // Intentionally omit the name to trigger a validation error
        product.setDescription("Test product without a name");
        product.setPrice(10.0);

        String json = objectMapper.writeValueAsString(product);

        // Expect 400 Bad Request due to validation error
        mockMvc.perform(post("/api/admin/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }
}
