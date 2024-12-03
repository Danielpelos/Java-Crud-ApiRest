package com.danielvelasco.apirest.apirest.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danielvelasco.apirest.apirest.Repositories.ProductoRepository;
import com.danielvelasco.apirest.apirest.Entities.Producto;

@RestController
@RequestMapping("/productos")
public class ProductoController {
    
    //Con esta etiqueta nos resuelve para que repositorio vamos a utilizar
    @Autowired
    private ProductoRepository productoRepository;

    //Este trae todo los productos, este sirve para traer todo
    @GetMapping
    public List<Producto> getAllProducts(){ //En una lista pongo todos los productos que hay en la BD
        return productoRepository.findAll(); //Con el findAll se busca todo
    }
    
    @GetMapping("/{id}") // Aqui se hace que se busque un producto por ID 
    public Producto getProductbyId(@PathVariable Long id){
        return productoRepository.findById(id) //En este caso se busca por id
        .orElseThrow(() -> new RuntimeException("No se encontró el producto con el ID: " + id)); //Por si no encuentra el ID entonces muestre un error
    }

    @PostMapping
    public Producto createProducto(@RequestBody Producto producto){ //Con este es para crear un producto
        return productoRepository.save(producto); //Con save se guarda
    }

    @PutMapping("/{id}")
    public Producto updateProducto(@PathVariable Long id, @RequestBody Producto productoDetails){
        Producto producto = productoRepository.findById(id)//En este caso se busca por id y se guarda en una variable
        .orElseThrow(() -> new RuntimeException("No se encontró el producto con el ID: " + id)); //Por si no encuentra el ID entonces muestre un error

        producto.setNombre(productoDetails.getNombre()); //Con la variable traemos los datos que se pueden editar, en este caso nombre
        producto.setPrecio(productoDetails.getPrecio());//Con la variable traemos los datos que se pueden editar, en este caso precio

        return productoRepository.save(producto); //Aqui se guarda lo que se actualiza y se muestra
    }

    @DeleteMapping("/{id}")
    public String DeleteProduct(@PathVariable Long id){
        Producto producto = productoRepository.findById(id)//En este caso se busca por id y se guarda en una variable
        .orElseThrow(() -> new RuntimeException("No se encontró el producto con el ID: " + id)); //Por si no encuentra el ID entonces muestre un error

        productoRepository.delete(producto); //Aqui ya se elimina 
        return "El producto con el id: " + id + " fue eliminado correctamente"; //MUestro mensaje de eliminacion de producto aunque puedo mostrar el producto que se elimina 
    }
}   
