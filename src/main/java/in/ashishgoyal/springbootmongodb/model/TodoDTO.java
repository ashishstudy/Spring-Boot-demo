package in.ashishgoyal.springbootmongodb.model;



import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="todos")  // We are working with mongo db thats why
public class TodoDTO {
	@Id
	private String id;
	@NotNull(message="todo cannot be null")
	private String todo;
	@NotNull(message="description cannot be null")
	private String description;
	@NotNull(message="completed cannot be null")
	private Boolean completed;
	private Date createdAt;
	private Date updatedAt;

}
