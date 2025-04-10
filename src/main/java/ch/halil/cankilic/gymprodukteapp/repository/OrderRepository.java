package ch.halil.cankilic.gymprodukteapp.repository;

import ch.halil.cankilic.gymprodukteapp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Additional query methods can be added if needed
}
