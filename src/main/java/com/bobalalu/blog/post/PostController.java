package com.bobalalu.blog.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Babatunde Obalalu - bobalalu@yahoo.com - +2348034627801
 */
@CrossOrigin(origins = "*") // * or https://blog.bobalalu.ng
@RestController
@RequestMapping("api/blog") // Ensure all the endpoints share the same resource path.
public class PostController
{
    // IoC/DI injects a PostService instance which will help implement the methods denoting the endpoints.
    @Autowired
    private final PostService service;

    @Autowired
    private final IPostRepository iPostRepository;

    protected Logger logger;

    public PostController(PostService service, IPostRepository iPostRepository) {
        this.service = service;
        this.iPostRepository = iPostRepository;
        logger = LoggerFactory.getLogger(PostController.class);
    }

    @GetMapping("/posts")
    public ResponseEntity findAll() throws ResourceNotFoundException {
        try {
            List<Post> posts = service.findAll();
            if ( ! posts.isEmpty()) {
                return ResponseEntity.ok().body(posts);
            } else {
                return new ResponseEntity("Oops! There are no blog post(s) at the moment. Try again later!", HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("Blog API Call::Failed -> Request Failed at '/posts'.");
            throw new ResourceNotFoundException("Blog API Call::Failed -> Request Failed at '/posts'.");
        }
    }

    @GetMapping("/post/{id}/view")
    public ResponseEntity<Post> find(@PathVariable("id") long id) throws ResourceNotFoundException {
        try {
            Post post = service.find(id).orElseThrow(() -> new ResourceNotFoundException("Blog API Call::Error -> Blog post not found at '/post/" + id + "/view'."));

            logger.info("Blog API Call::Success -> Blog post found successfully at '/post/" + id + "/view'.");
            return ResponseEntity.ok().body(post);
        } catch (Exception ex) {
            logger.error("Blog API Call::Failed -> Request Failed at '/post/" + id + "/view'.");
            throw new ResourceNotFoundException("Blog API Call::Error -> Request Failed at '/post/" + id + "/view'.");
        }
    }

    @GetMapping("/posts/{title}/search")
    public ResponseEntity findByTitle(@PathVariable("title") String title) throws ResourceNotFoundException {
        try {
            List<Post> posts = service.findTitle(title.toLowerCase());
            if ( ! posts.isEmpty()) {
                logger.info("Blog API Call::Success -> Blog post(s) found successfully at '/post/" + title + "/search'.");
                return ResponseEntity.ok().body(posts);
            } else {
                logger.info("Blog API Call::Error -> Blog post(s) found successfully at '/post/" + title + "/search'.");
                return new ResponseEntity("Oops! Blog post(s) NOT found. Try again later!", HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("Blog API Call::Failed -> Request Failed at '/post/" + title + "/search'.");
            throw new ResourceNotFoundException("Blog API Call::Failed -> Request Failed at '/post/" + title + "/search'.");
        }
    }

    @PostMapping("/post/add")
    public ResponseEntity<Object> create(@Valid @RequestBody Post post)
    {
        try {
            post.setSlug(post.getTitle().toLowerCase().replace(" ", "-"));
            Post _post = service.create(post);
            //URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(_post.getId()).toUri();
            logger.info("Blog API Call::Success -> Blog post created successfully at '/post/add'.");
            return new ResponseEntity<>("Blog post created successfully.", HttpStatus.CREATED);
        } catch (Exception ex) {
            logger.error("Blog API Call::Failed -> Request Failed at '/post/add'.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/post/{id}/edit")
    public ResponseEntity<Post> update(@PathVariable("id") long id, @Valid @RequestBody Post post) throws ResourceNotFoundException {
        try {
            Post p = service.find(id).orElseThrow(() -> new ResourceNotFoundException("Blog API Call::Error -> Oops! Unable to update Blog post successfully at '/post/" + id + "/edit'."));

            p.setTitle(post.getTitle());
            p.setSlug(post.getTitle().toLowerCase().replace(" ", "-"));
            p.setContent(post.getContent());
            final Post updated = service.update(p);
            logger.info("Blog API Call::Success -> Blog post has been updated successfully at '/post/" + id + "/edit'.");
            return ResponseEntity.ok(updated);
        } catch (Exception ex) {
            logger.error("Blog API Call::Failed -> Request Failed at '/post/" + id + "/edit'.");
            throw new ResourceNotFoundException("Blog API Call::Failed -> Request Failed at '/post/" + id + "/edit'.");
        }
    }

    @DeleteMapping("/post/{id}/delete")
    public ResponseEntity delete(@PathVariable("id") long id) throws ResourceNotFoundException {
        try {
            Post post = service.find(id).orElseThrow(() -> new ResourceNotFoundException("Blog API Call::Error -> Oops! Unable to delete blog post successfully at '/post/" + id + "/delete'."));

            service.delete(id);
            logger.info("Blog API Call::Success -> Blog post has been deleted successfully at '/post/" + id + "/delete'.");
            return new ResponseEntity("Blog post has been deleted successfully.", HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Blog API Call::Failed -> Request Failed at '/post/" + id + "/delete'.");
            throw new ResourceNotFoundException("Blog API Call::Error -> Request Failed at '/post/" + id + "/delete'.");
        }
    }

    @DeleteMapping("/posts/delete")
    public ResponseEntity<HttpStatus> deleteAll() throws ResourceNotFoundException {
        String message = null;
        String log = null;

        try {
            List<Post> posts = service.findAll();
            if ( ! posts.isEmpty()) {
                service.deleteAll();
                log = "Blog API Call::Success -> Blog post(s) has been deleted successfully at '/posts/delete'.";
                message = "Blog post(s) has been deleted successfully.";
            } else {
                log = "Blog API Call::Error -> Unable to delete blog post(s) successfully at '/posts/delete'.";
                message = "Oops! Unable to delete blog post(s). Try again later!";
            }
            logger.info(log);
            return new ResponseEntity(message, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Blog API Call::Failed -> Request Failed at '/posts/delete'.");
            throw new ResourceNotFoundException("Blog API Call::Failed -> Request Failed at '/posts/delete'.");
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex)
    {
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        Map<String, String> map = new HashMap<>(errors.size());
        errors.forEach((error) -> {
           String key = ((FieldError) error).getField();
           String val = error.getDefaultMessage();
           map.put(key, val);
        });
        return ResponseEntity.badRequest().body(map);
    }
}