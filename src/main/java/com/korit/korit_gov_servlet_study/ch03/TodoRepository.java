package com.korit.korit_gov_servlet_study.ch03;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TodoRepository {
    private static TodoRepository instance;
    private List<Todo> todoList;

    private TodoRepository() {
        this.todoList = new ArrayList<>();
    }

    public static TodoRepository getInstance() {
        if (instance == null) instance = new TodoRepository();
        return instance;
    }

    public Todo findByTitle (TodoDto todoDto) {
        System.out.print("find by title >>>>> ");
        todoList.forEach(System.out::println);
        return todoList.stream()
                .filter(todo -> todo.getTitle().equals(todoDto.getTitle()))
                .findFirst()
                .orElse(null);
    }

    public Todo addTodo(TodoDto todoDto) {
        Todo todo = todoDto.toEntity();
        System.out.println("add todo : " + todo);
        todoList.add(todo);
        return todo;
    }


    public List<Todo> getAllTodoList() {
        System.out.println("get all todo list >>>>> ");
        todoList.forEach(System.out::println);
        return todoList;
    }
}
