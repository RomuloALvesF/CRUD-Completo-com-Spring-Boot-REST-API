package br.com.apispring.teste_api_spring.controllers;

import java.util.List;

import javax.validation.constraints.Null;
import javax.xml.stream.XMLInputFactory;

import org.jboss.jandex.VoidType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import br.com.apispring.teste_api_spring.model.Usuario;
import br.com.apispring.teste_api_spring.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {
	
	@Autowired //Injeção de dependecia
	private UsuarioRepository usuarioRepository;
   
    @RequestMapping(value = "/mostranome/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Teste Spring Boot API: " + name + "!";
    }
    
    
    @RequestMapping(value = "/olamundo/{nome}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String retornaOlaMundo (@PathVariable String nome) {
    	
    	Usuario usuario = new Usuario();
    	usuario.setNome(nome);
    	
    	usuarioRepository.save(usuario); //grava no bando de dados
    	
    	return "Olá mundo " + nome;
    }
    @GetMapping(value = "listatodos")
    @ResponseBody //Retorna os dados para o corpo da resposta.
    public ResponseEntity<List<Usuario>> listaUsuario(){
    	   	
    	List<Usuario> usuarios = usuarioRepository.findAll(); //executa a consulta no bando de dados
    	return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); //Retorna a lista em JSON	
    }
    
    @PostMapping(value = "salvar") //mapeia a URL
    @ResponseBody //Descreve a resposta
     public ResponseEntity<Usuario> salvar (@RequestBody Usuario usuario){
    	
    	Usuario user = usuarioRepository.save(usuario);
    	return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    	
    }
    
    
    @PutMapping(value = "alterar") //mapeia a URL
    @ResponseBody //Descreve a resposta
     public ResponseEntity<?> alterar (@RequestBody Usuario usuario){
    	
    	if (usuario.getId() == 0) {
    		
    		return new ResponseEntity<String>("Necessita id", HttpStatus.OK);
		}
    	Usuario user = usuarioRepository.saveAndFlush(usuario);
    	
    	return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    	
    }
    
    
    @DeleteMapping(value = "delete") //mapeia a URL
    @ResponseBody //Descreve a resposta
     public ResponseEntity<String> delete (@RequestParam Long iduser){
    	
    	usuarioRepository.deleteById(iduser);
    	
    	return new ResponseEntity<String>(" Usuario deletado com sucesso! ", HttpStatus.OK);
    	
    }
    
    @GetMapping(value = "buscarporid") //mapeia a URL
    @ResponseBody //Descreve a resposta
     public ResponseEntity<Usuario> buscarporid (@RequestParam (name = "iduser") Long iduser){ //recebe os dados para consulta
    	
    	  Usuario usuario =  usuarioRepository.findById(iduser).get();
    	
    	return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    	
    }
    
    @GetMapping(value = "buscarPorNome") //mapeia a URL
    @ResponseBody //Descreve a resposta
     public ResponseEntity<List<Usuario>> buscarPorNome (@RequestParam(name = "name") String name){ //recebe os dados para consulta
    	
        //não está chegando no usuario abaixo	
    	List<Usuario> usuario =  usuarioRepository.buscarPorNome(name.trim().toUpperCase()); //.trim ignora o espaço na hora da busca .toUpperCase ignora letras maiusculas
    	
    	return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);
    	
    }
    
   
    
    
    
    
    
    
    
    
    
    
    
    
}
