package ufrn.br.apirestdemo.controller;


import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ufrn.br.apirestdemo.domain.Produto;
import ufrn.br.apirestdemo.service.ProdutoService;

import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/produto")
public class ProdutoController {

    ProdutoService service;
    ModelMapper mapper;

    public ProdutoController(ProdutoService service, ModelMapper mapper){
        this.service = service;
        this.mapper = mapper;
    }
    @PostMapping("/cadastrarproduto")
    @ResponseStatus(HttpStatus.CREATED)
    public Produto.DtoResponse create(@RequestBody Produto.DtoRequest p){

        Produto produto = this.service.create(Produto.DtoRequest.convertToEntity(p, mapper));

        Produto.DtoResponse response = Produto.DtoResponse.convertToDto(produto, mapper);
        response.generateLinks(produto.getId());

        return response;
    }
    @GetMapping
    public List<Produto.DtoResponse> list(){

        return this.service.list().stream().map(
                elementoAtual -> {
                    Produto.DtoResponse response = Produto.DtoResponse.convertToDto(elementoAtual, mapper);
                    response.generateLinks(elementoAtual.getId());
                    return response;
                }).collect(Collectors.toList());
    }
    @GetMapping("{id}")
    public Produto.DtoResponse getById(@PathVariable Long id){

        Produto produto = this.service.getById(id);
        Produto.DtoResponse response = Produto.DtoResponse.convertToDto(produto, mapper);
        response.generateLinks(produto.getId());

        return response;
    }
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Produto.DtoResponse update(@RequestBody Produto.DtoRequest dtoRequest, @PathVariable Long id){
        Produto p = Produto.DtoRequest.convertToEntity(dtoRequest, mapper);
        Produto.DtoResponse response = Produto.DtoResponse.convertToDto(this.service.update(p, id), mapper);
        response.generateLinks(id);

        return response;
    }
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id){
        this.service.delete(id);
    }

}
