package kz.zzhalelov.springjpa.repository;

import kz.zzhalelov.springjpa.model.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Integer> {

}