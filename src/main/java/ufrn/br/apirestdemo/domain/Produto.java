package ufrn.br.apirestdemo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;
import ufrn.br.apirestdemo.controller.ProdutoController;
import ufrn.br.apirestdemo.controller.UsuarioController;

import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@SQLDelete(sql = "UPDATE produto SET deleted_at = CURRENT_TIMESTAMP WHERE id=?")
@Where(clause = "deleted_at is null")
public class Produto extends AbstractEntity {

    String nome;
    String cor;
    Integer tamanho;
    float preco;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToMany(mappedBy = "produtos")
    private Set<Compra> compras;

    @Override
    public void partialUpdate(AbstractEntity e) {
        if (e instanceof Produto produto) {
            this.nome = produto.nome;
            this.cor = produto.cor;
            this.tamanho = produto.tamanho;
            this.preco = produto.preco;

        }
    }

    @Data
    public static class DtoRequest {
        @NotBlank(message = "Produto em branco")
        String nome;
        @NotBlank(message = "Cor em branco")
        String cor;
        @NotBlank(message = "Tamanho em branco")
        Integer tamanho;
        @NotBlank(message = "Preço em branco")
        float preco;


        public static Produto convertToEntity(Produto.DtoRequest dto, ModelMapper mapper) {
            return mapper.map(dto, Produto.class);
        }
    }

    @Data
    public static class DtoResponse extends RepresentationModel<Produto.DtoResponse> {
        String nome;
        String cor;
        Integer tamanho;
        float preco;

        public static DtoResponse convertToDto(Produto p, ModelMapper mapper) {
            return mapper.map(p, Produto.DtoResponse.class);
        }

        public void generateLinks(Long id) {
            add(linkTo(ProdutoController.class).slash(id).withSelfRel());
            add(linkTo(ProdutoController.class).withRel("produto"));
            add(linkTo(ProdutoController.class).slash(id).withRel("delete"));
        }
    }

}
