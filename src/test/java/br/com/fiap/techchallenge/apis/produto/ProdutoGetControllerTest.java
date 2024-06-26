package br.com.fiap.techchallenge.apis.produto;

import br.com.fiap.techchallenge.apis.ProdutoController;
import br.com.fiap.techchallenge.domain.entities.Categoria;
import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.model.mapper.produto.ProdutoMapper;
import br.com.fiap.techchallenge.infra.exception.CategoriaException;
import br.com.fiap.techchallenge.infra.repositories.CategoriaRepository;
import br.com.fiap.techchallenge.infra.repositories.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProdutoGetControllerTest {

    @Mock
    ProdutoRepository produtoRepository;

    @Mock
    CategoriaRepository categoriaRepository;

    @Autowired
    ProdutoMapper produtoMapper;

    @Test
    void shouldListarProdutosPorCategoriaInformada() {
        Optional<List<Produto>> optionalProdutos = Optional.of(criarListaDeProdutos());
        when(produtoRepository.findAllByCategoriaId(anyInt())).thenReturn(optionalProdutos);

        ProdutoController produtoController = new ProdutoController(categoriaRepository, produtoRepository, produtoMapper);
        ResponseEntity<List<Produto>> response = produtoController.buscarProdutosPorCategoria("lanche");

        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void mustLancarCategoriaExceptionQuandoACategoriaNaoForValida() {
        ProdutoController produtoController = new ProdutoController(categoriaRepository, produtoRepository, produtoMapper);
        assertThrows(CategoriaException.class, () -> produtoController.buscarProdutosPorCategoria("inexistente"));
    }

    private List<Produto> criarListaDeProdutos() {
        return List.of(
                Produto.builder().categoria(Categoria.builder().id(1L).nome("lanche").build()).build(),
                Produto.builder().categoria(Categoria.builder().id(1L).nome("lanche").build()).build()
        );
    }

}
