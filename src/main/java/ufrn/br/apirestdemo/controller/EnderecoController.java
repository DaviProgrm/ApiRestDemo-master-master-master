package ufrn.br.apirestdemo.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ufrn.br.apirestdemo.domain.Endereco;
import ufrn.br.apirestdemo.domain.Produto;
import ufrn.br.apirestdemo.service.EnderecoService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    EnderecoService service;
    ModelMapper mapper;

    public EnderecoController(EnderecoService service, ModelMapper mapper){
        this.service = service;
        this.mapper = mapper;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Endereco.DtoResponse create(@RequestBody Endereco.DtoRequest e){

        Endereco endereco = this.service.create(Endereco.DtoRequest.convertToEntity(e, mapper));

        Endereco.DtoResponse response = Endereco.DtoResponse.convertToDto(endereco, mapper);
        response.generateLinks(endereco.getId());

        return response;
    }
    @GetMapping
    public List<Endereco.DtoResponse> list(){

        return this.service.list().stream().map(
                elementoAtual -> {
                    Endereco.DtoResponse response = Endereco.DtoResponse.convertToDto(elementoAtual, mapper);
                    response.generateLinks(elementoAtual.getId());
                    return response;
                }).collect(Collectors.toList());
    }
    @GetMapping("{id}")
    public Endereco.DtoResponse getById(@PathVariable Long id){

        Endereco endereco = this.service.getById(id);
        Endereco.DtoResponse response = Endereco.DtoResponse.convertToDto(endereco, mapper);
        response.generateLinks(endereco.getId());

        return response;
    }
    @PutMapping("{id}")
    public Endereco.DtoResponse update(@RequestBody Endereco.DtoRequest dtoRequest, @PathVariable Long id){
        Endereco e = Endereco.DtoRequest.convertToEntity(dtoRequest, mapper);
        Endereco.DtoResponse response = Endereco.DtoResponse.convertToDto(this.service.update(e, id), mapper);
        response.generateLinks(id);

        return response;
    }
    public void delete(@PathVariable Long id){
        this.service.delete(id);
    }
}
