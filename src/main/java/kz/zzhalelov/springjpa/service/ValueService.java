package kz.zzhalelov.springjpa.service;

import kz.zzhalelov.springjpa.model.Option;
import kz.zzhalelov.springjpa.model.Product;
import kz.zzhalelov.springjpa.model.Value;
import kz.zzhalelov.springjpa.repository.OptionRepository;
import kz.zzhalelov.springjpa.repository.ProductRepository;
import kz.zzhalelov.springjpa.repository.ValueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ValueService {
    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;
    private final ValueRepository valueRepository;

    // Написать тест на create
    public Value create(String valueName, int productId, int optionId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Товар не найден"));

        Option option = optionRepository.findById(optionId)
                .orElseThrow(() -> new NoSuchElementException("Характеристика не найдена"));

        Value value = new Value();
        value.setName(valueName);
        value.setOption(option);
        value.setProduct(product);

        valueRepository.save(value);
        return value;
    }
}