package kz.zzhalelov.springjpa.repository;

import kz.zzhalelov.springjpa.model.Value;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValueRepository extends JpaRepository<Value, Integer> {
}