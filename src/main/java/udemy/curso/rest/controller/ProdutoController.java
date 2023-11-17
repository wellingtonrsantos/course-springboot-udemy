package udemy.curso.rest.controller;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import udemy.curso.domain.entity.Produto;
import udemy.curso.domain.repository.ProdutoRepository;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("api/produtos")
public class ProdutoController {

  private final ProdutoRepository produtoRepository;

  public ProdutoController(ProdutoRepository produtoRepository) {
    this.produtoRepository = produtoRepository;
  }

  @GetMapping("{id}")
  public Produto getById(@PathVariable Integer id) {
    return produtoRepository
        .findById(id)
        .orElseThrow(() ->
              new ResponseStatusException(
                  NOT_FOUND,
                  "Produto não encontrado"
              )
        );
  }

  @PostMapping
  @ResponseStatus(CREATED)
  public Produto save(@RequestBody @Valid Produto produto) {
    return produtoRepository.save(produto);
  }

  @PutMapping("{id}")
  @ResponseStatus(NO_CONTENT)
  public void update(@PathVariable Integer id,
                     @RequestBody @Valid Produto produto
  ) {
    produtoRepository
        .findById(id)
        .map(produtoExistente -> {
          produto.setId(produtoExistente.getId());
          return produtoRepository.save(produto);
        }
    ).orElseThrow(() ->
      new ResponseStatusException(
          NOT_FOUND,
          "Produto não encontrado"
      )
    );
  }

  @DeleteMapping("{id}")
  @ResponseStatus(NO_CONTENT)
  public void delete(@PathVariable Integer id) {
    produtoRepository
        .findById(id)
        .map(produto -> {
           produtoRepository.delete(produto);
           return produto;
        }
    ).orElseThrow(() ->
        new ResponseStatusException(
            NOT_FOUND,
            "Produto não encontrado"
        )
      );
  }

  @GetMapping
  public List<Produto> find(Produto filtro) {
    ExampleMatcher matcher = ExampleMatcher
        .matching()
        .withIgnoreCase()
        .withStringMatcher(
            ExampleMatcher.StringMatcher.CONTAINING
        );

    Example example = Example.of(filtro, matcher);
    return produtoRepository.findAll(example);
  }

}
