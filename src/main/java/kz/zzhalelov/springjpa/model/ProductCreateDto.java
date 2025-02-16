package kz.zzhalelov.springjpa.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCreateDto {
    String name;
    double price;
    Integer categoryId;
}