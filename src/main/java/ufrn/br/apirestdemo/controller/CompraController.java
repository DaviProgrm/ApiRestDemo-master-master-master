package ufrn.br.apirestdemo.controller;


import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ufrn.br.apirestdemo.domain.Compra;
import ufrn.br.apirestdemo.domain.Produto;
import ufrn.br.apirestdemo.service.CompraService;
import ufrn.br.apirestdemo.service.UsuarioService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/compra")
public class CompraController {

    CompraService service;
    ModelMapper mapper;

    public CompraController(CompraService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Compra.DtoResponse create(@RequestBody Compra.DtoRequest c){

        Compra compra = this.service.create(Compra.DtoRequest.convertToEntity(c, mapper));

        Compra.DtoResponse response = Compra.DtoResponse.convertToDto(compra, mapper);
        response.generateLinks(compra.getId());

        return response;
    }
    @GetMapping
    public List<Compra.DtoResponse> list(){

        return this.service.list().stream().map(
                elementoAtual -> {
                    Compra.DtoResponse response = Compra.DtoResponse.convertToDto(elementoAtual, mapper);
                    response.generateLinks(elementoAtual.getId());
                    return response;
                }).collect(Collectors.toList());
    }
    @GetMapping("{id}")
    public Compra.DtoResponse getById(@PathVariable Long id){

        Compra compra = this.service.getById(id);
        Compra.DtoResponse response = Compra.DtoResponse.convertToDto(compra, mapper);
        response.generateLinks(compra.getId());

        return response;
    }
    @PutMapping("{id}")
    public Compra.DtoResponse update(@RequestBody Compra.DtoRequest dtoRequest, @PathVariable Long id){
        Compra c = Compra.DtoRequest.convertToEntity(dtoRequest, mapper);
        Compra.DtoResponse response = Compra.DtoResponse.convertToDto(this.service.update(c, id), mapper);
        response.generateLinks(id);

        return response;
    }
    public void delete(@PathVariable Long id){
        this.service.delete(id);
    }
}
