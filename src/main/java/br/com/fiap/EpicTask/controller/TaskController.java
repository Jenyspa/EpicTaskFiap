package br.com.fiap.EpicTask.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fiap.EpicTask.model.Task;
import br.com.fiap.EpicTask.model.User;
import br.com.fiap.EpicTask.repository.TaskRepository;

@Controller
@RequestMapping("/task")
public class TaskController {
	
	@Autowired
	private TaskRepository repository;
	
	@GetMapping()
	public ModelAndView tasks() {
		List<Task> tasksList = repository.findAll();
		ModelAndView modelAndView = new ModelAndView("tasks");
		modelAndView.addObject("tasks", tasksList);
		return modelAndView;
	}
	
	@RequestMapping("new")
	public String formUser(Task task) {
		return "task_new";
	}
	
	@PostMapping()
	public String save(@Valid Task task, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return "task_new";
		}
		repository.save(task);
		attributes.addFlashAttribute("message", "Task cadastrada com sucesso");
		return "redirect:/task";
	}
	
	@GetMapping("delete/{id}")
	public String deleteUser(@PathVariable("id") Long id) {
		repository.deleteById(id);
		return "redirect:/task";
	}
	
	@GetMapping("/{id}")
	public ModelAndView editUserForm(@PathVariable Long id) {
		Optional<Task> task = repository.findById(id);
		ModelAndView modelAndView = new ModelAndView("task_edit");
		modelAndView.addObject("task", task);
		return modelAndView;		
	}
	
	@PostMapping("/update")
	public String updateUser(@Valid Task task, BindingResult result, RedirectAttributes redirect) {
		if (result.hasErrors()) return "task_edit";
		repository.save(task);
		redirect.addFlashAttribute("message", "Task editada com sucesso");
		return "redirect:/task"; 
	}
}
