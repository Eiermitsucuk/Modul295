package ch.halil.cankilic.gymprodukteapp.repository;

import ch.halil.cankilic.gymprodukteapp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Additional query methods can be added if needed
}
