package ru.yandex.practicum.catsgram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.dao.PostDao;
import ru.yandex.practicum.catsgram.exception.PostNotFoundException;
import ru.yandex.practicum.catsgram.exception.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.yandex.practicum.catsgram.Constants.DESCENDING_ORDER;

@Service
public class PostService {

    private final PostDao postDao;
    private final UserService userService;
//    private static Integer globalId = 0;

    @Autowired
    public PostService(PostDao postDao, UserService userService) {
        this.postDao = postDao;
        this.userService = userService;
    }

    public Collection<Post> findPostsByUser(String userId) {
        User user = userService.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("Пользователь %s не найден", userId)));
        return postDao.findPostsByUser(user);
    }

    public Collection<Post> findPostsByUser(String authorId, Integer size, String sort) {
        return findPostsByUser(authorId).stream()
                .sorted((p0, p1) -> {
                    int result = p0.getCreationDate().compareTo(p1.getCreationDate()); //прямой порядок сортировки
                    if (sort.equals("desc")) {
                        result = -1 * result; //обратный порядок сортировки
                    }
                    return result;
                })
                .limit(size)
                .collect(Collectors.toList());
    }

//    public Post create(Post post) {
//        Optional<User> postAuthor = userService.findUserById(post.getAuthor().getId());
//        if (postAuthor == null) {
//            throw new UserNotFoundException(String.format(
//                    "Пользователь %s не найден",
//                    post.getAuthor()));
//        }
//
//        post.setId(getNextId());
//        posts.add(post);
//        return post;
//    }
//
//    public Post findPostById(Integer postId) {
//        return posts.stream()
//                .filter(p -> p.getId().equals(postId))
//                .findFirst()
//                .orElseThrow(() -> new PostNotFoundException(String.format("Пост № %d не найден", postId)));
//    }
//
//    public List<Post> findAll(Integer size, Integer from, String sort) {
//        return posts.stream()
//                .sorted((p0, p1) -> compare(p0, p1, sort))
//                .skip(from)
//                .limit(size)
//                .collect(Collectors.toList());
//    }
//
//    public List<Post> findAllByUserEmail(String email, Integer size, String sort) {
//        return posts.stream()
//                .filter(p -> email.equals(p.getAuthor()))
//                .sorted((p0, p1) -> compare(p0, p1, sort))
//                .limit(size)
//                .collect(Collectors.toList());
//    }
//
//    private static Integer getNextId() {
//        return globalId++;
//    }
//
//    private int compare(Post p0, Post p1, String sort) {
//        int result = p0.getCreationDate().compareTo(p1.getCreationDate()); //прямой порядок сортировки
//        if (sort.equals(DESCENDING_ORDER)) {
//            result = -1 * result; //обратный порядок сортировки
//        }
//        return result;
//    }
}
