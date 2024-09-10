package com.GreenThumb.Repositories;

import com.GreenThumb.Models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
