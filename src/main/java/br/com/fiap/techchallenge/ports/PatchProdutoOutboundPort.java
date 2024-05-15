package br.com.fiap.techchallenge.ports;

import br.com.fiap.techchallenge.domain.entities.Produto;
import com.github.fge.jsonpatch.JsonPatch;

public interface PatchProdutoOutboundPort {

    Produto atualizarDadosProduto(Long id, JsonPatch patch);

}
