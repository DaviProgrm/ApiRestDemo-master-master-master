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
import ufrn.br.apirestdemo.controller.CompraController;
import ufrn.br.apirestdemo.controller.ProdutoController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@SQLDelete(sql = "UPDATE compra SET deleted_at = CURRENT_TIMESTAMP WHERE id=?")
@Where(clause = "deleted_at is null")
public class Compra extends AbstractEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "compra_produto",
            joinColumns = @JoinColumn(name = "compra_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    private Set<Produto> produtos;

    String descricao;
    LocalDate data;
    BigDecimal valorTotal;

    @Override
    public void partialUpdate(AbstractEntity e) {
        if (e instanceof Compra compra) {
            this.descricao = compra.descricao;
            this.data = compra.data;
            this.valorTotal = compra.valorTotal;
        }
    }

    @Data
    public static class DtoRequest {
        @NotBlank(message = "Descrição em branco")
        String descricao;

        public static Compra convertToEntity(Compra.DtoRequest dto, ModelMapper mapper) {
            return mapper.map(dto, Compra.class);
        }
    }

    @Data
    public static class DtoResponse extends RepresentationModel<Produto.DtoResponse> {
        String descricao;
        LocalDate data;
        BigDecimal valorTotal;

        public static Compra.DtoResponse convertToDto(Compra c, ModelMapper mapper) {
            return mapper.map(c, Compra.DtoResponse.class);
        }

        public void generateLinks(Long id) {
            add(linkTo(CompraController.class).slash(id).withSelfRel());
            add(linkTo(CompraController.class).withRel("compra"));
            add(linkTo(CompraController.class).slash(id).withRel("delete"));
        }
    }
}

