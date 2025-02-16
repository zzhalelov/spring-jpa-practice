package kz.zzhalelov.springjpa.model;

import lombok.Data;

@Data
public class ProductDto {
    int id;
    String name;
    double price;
    String category;
}