package in.ashishgoyal.springbootmongodb.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.ashishgoyal.springbootmongodb.exception.TodoCollectionException;
import in.ashishgoyal.springbootmongodb.model.TodoDTO;
import in.ashishgoyal.springbootmongodb.repository.TodoRepository;
import in.ashishgoyal.springbootmongodb.service.TodoService;
import jakarta.validation.ConstraintViolationException;

@RestController
public class TodoController {
	
	@Autowired
	private TodoRepository todoRepo;
	
	@Autowired
	private TodoService todoService;
	
	@GetMapping("/todos")
	public ResponseEntity<?> getAllTodos() {
		/*
		 * List<TodoDTO> todos = todoRepo.findAll(); if(todos.size() > 0 ) { return new
		 * ResponseEntity<List<TodoDTO>>(todos, HttpStatus.OK); } else { return new
		 * ResponseEntity<>("No todos available", HttpStatus.NOT_FOUND); }
		 */
		List<TodoDTO> todos = todoService.getAllTodos();
		return new ResponseEntity<>(todos, todos.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
		
	}
	
	
	@PostMapping("/todos")
	public ResponseEntity<?> createTodo(@RequestBody TodoDTO todo){
		try {
			//todo.setCreatedAt(new Date(System.currentTimeMillis()));
			//todoRepo.save(todo);
			todoService.createTodo(todo);
			return new ResponseEntity<TodoDTO>(todo, HttpStatus.OK);
		} catch(ConstraintViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		} catch(TodoCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("/todos/{id}")
	public ResponseEntity<?> getSingleTodo(@PathVariable("id") String id){
		/*
		 * Optional<TodoDTO> todoOptional = todoRepo.findById(id);
		 * if(todoOptional.isPresent()) { return new
		 * ResponseEntity<>(todoOptional.get(), HttpStatus.OK); } else { return new
		 * ResponseEntity<>("Todo not found with id :" + id, HttpStatus.NOT_FOUND); }
		 */
		try {
			return new ResponseEntity<>(todoService.getSingleTodo(id), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PutMapping("/todos/{id}")
	public ResponseEntity<?> updateById(@PathVariable("id") String id, @RequestBody TodoDTO todo){
		/*
		 * Optional<TodoDTO> todoOptional = todoRepo.findById(id);
		 * if(todoOptional.isPresent()) { TodoDTO todoToSave = todoOptional.get();
		 * todoToSave.setCompleted(todo.getCompleted() !=null ? todo.getCompleted() :
		 * todoToSave.getCompleted()); todoToSave.setTodo(todo.getTodo() !=null ?
		 * todo.getTodo() : todoToSave.getTodo());
		 * todoToSave.setDescription(todo.getDescription()!=null ?
		 * todo.getDescription(): todoToSave.getDescription());
		 * todoToSave.setUpdatedAt(new Date(System.currentTimeMillis()));
		 * todoRepo.save(todoToSave); return new ResponseEntity<>(todoToSave,
		 * HttpStatus.OK); } else { return new
		 * ResponseEntity<>("Todo not found with id :" + id, HttpStatus.NOT_FOUND); }
		 */
		
		try {
			todoService.updateTodo(id, todo);
			return new ResponseEntity<>("Update todo with id "+id, HttpStatus.OK);
		} catch(ConstraintViolationException e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		}catch(TodoCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	
	@DeleteMapping("/todos/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
		try{
			//todoRepo.deleteById(id);
			todoService.deleteTodoById(id);
			return new ResponseEntity<>("Successfully deleted with id "+id, HttpStatus.OK);
		}catch(TodoCollectionException e){
				return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
			}
		}
	

}
