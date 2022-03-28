package com.bobalalu.blog;

import com.bobalalu.blog.post.IPostRepository;
import com.bobalalu.blog.post.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
//@EntityScan
public class BlogApplication {
	private static final Logger log = LoggerFactory.getLogger(BlogApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(IPostRepository iPostRepository) {
		return (args) -> {
			// Add a few blog posts
			iPostRepository.save(new Post(1L , "Digital World", "digital-world", "The life of a digital human being."));
			iPostRepository.save(new Post(2L , "Build cancelled", "build-cancelled", "Build cancelled while executing task :BlogApplication.main()"));
			iPostRepository.save(new Post(3L , "Stack Trace Try", "stack-trace-try", "Option to get the stack trace."));
			iPostRepository.save(new Post(4L , "Hibernate Cascade", "hibernate-cascade", "Hibernate: drop table if exists posts CASCADE."));

			// Retrieve all blog posts
			log.info("Blog Posts::findAll()");
			log.info("------------------------------");
			for (Post post : iPostRepository.findAll()) {
				log.info(post.toFormat());
			}
			log.info("");

			// Retrieve a blog post by ID
			log.info("Blog Posts::findById(2L)");
			log.info("------------------------------");
			Optional<Post> post = iPostRepository.findById(2L);
			log.info(post.get().toFormat());
			log.info("");

			// Search a blog post by title
			log.info("Blog Posts::findByTitle('ca')");
			log.info("------------------------------");
			iPostRepository.findByTitle("ca").forEach(pp -> {
				log.info(post.get().toFormat());
			});
			log.info("");
		};
	}
}
