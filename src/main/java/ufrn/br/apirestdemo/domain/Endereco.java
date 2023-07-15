package ufrn.br.apirestdemo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;
import ufrn.br.apirestdemo.controller.EnderecoController;
import ufrn.br.apirestdemo.controller.ProdutoController;

import java.io.Serializable;
import java.time.LocalDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Endereco extends AbstractEntity implements Serializable {

    String cep;
    String cidade;
    String nCasa;

    @Override
    public void partialUpdate(AbstractEntity e) {
        if (e instanceof Endereco endereco) {
            this.cep = endereco.cep;
            this.cidade = endereco.cidade;
            this.nCasa = endereco.nCasa;

        }
    }

    @Data
    public static class DtoRequest {
        @NotBlank(message = "Cep em branco")
        String cep;
        @NotBlank(message = "Cidade em branco")
        String cidade;
        @NotBlank(message = "Numero em branco")
        Integer nCasa;

        public static Endereco convertToEntity(Endereco.DtoRequest dto, ModelMapper mapper) {
            return mapper.map(dto, Endereco.class);
        }
    }

    @Data
    public static class DtoResponse extends RepresentationModel<Endereco.DtoResponse> {
        String cep;
        String cidade;
        String nCasa;

        public static Endereco.DtoResponse convertToDto(Endereco e, ModelMapper mapper) {
            return mapper.map(e, Endereco.DtoResponse.class);
        }

        public void generateLinks(Long id) {
            add(linkTo(EnderecoController.class).slash(id).withSelfRel());
            add(linkTo(EnderecoController.class).withRel("endereco"));
            add(linkTo(EnderecoController.class).slash(id).withRel("delete"));
        }
    }
}
