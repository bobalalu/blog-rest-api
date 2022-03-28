package com.bobalalu.blog.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@EnableMapRepositories
public class PostService {
    @Autowired
    private final CrudRepository<Post, Long> repository;

    @Autowired
    private IPostRepository iPostRepository;

    public PostService(CrudRepository<Post, Long> repository, IPostRepository iPostRepository)
    {
        this.repository = repository;
        this.iPostRepository = iPostRepository;
        //this.repository.saveAll(InitialPosts());
    }

    private static List<Post> InitialPosts()
    {
        /*return List.of(
            new Post("Blog Post One", "blog-post-one", "Blog Post Content One."),
            new Post("Blog Post Two", "blog-post-two", "Blog Post Content Two."),
            new Post("Blog Post Three", "blog-post-three", "Blog Post Content Three."),
            new Post("Blog Post Four", "blog-post-four", "Blog Post Content Four."),
            new Post("Blog Post Five", "blog-post-five", "Blog Post Content Five.")
        );*/
        return null;
    }

    public List<Post> findAll()
    {
        List<Post> list = new ArrayList<>();
        Iterable<Post> posts = repository.findAll();
        posts.forEach(list::add);

        return list;
    }

    public Optional<Post> find(long id)
    {
        return repository.findById(id);
    }

    public Post create(Post post)
    {
        return repository.save(post);
    }

    public Post update(Post post)
    {
        return repository.save(post);
    }

    public void delete(Long id)
    {
        repository.deleteById(id);
    }

    public List<Post> findTitle(String title)
    {
        List<Post> list = new ArrayList<>();
        Iterable<Post> posts = iPostRepository.findByTitle(title);
        posts.forEach(list::add);

        return list;
    }

    public void deleteAll()
    {
        this.repository.deleteAll();
    }
}